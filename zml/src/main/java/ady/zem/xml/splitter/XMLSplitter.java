package ady.zem.xml.splitter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.XMLEvent;

import ady.zem.xml.mo.SplitCriteria;
import ady.zem.xml.mo.SplitResultInfo;

public class XMLSplitter extends AbstractXMLSplitter {
	
	private XMLOutputFactory writerfactory = XMLOutputFactory.newInstance();
	private XMLEventFactory eventfactory = XMLEventFactory.newInstance();
	private XMLInputFactory inputfactory =  XMLInputFactory.newInstance();

	@Override
	public List<SplitResultInfo> split(SplitCriteria criteria) throws Exception {
		Reader reader = new FileReader(criteria.getSourceFilePath());
		XMLEventReader xmlEventReader = inputfactory.createXMLEventReader(reader);
		readHeader(xmlEventReader);
		readFooter(xmlEventReader);
		return doSplit(criteria, inputfactory.createXMLEventReader(new FileReader(criteria.getSourceFilePath())));
	}

	protected List<SplitResultInfo> doSplit(SplitCriteria criteria, XMLEventReader xmlEventReader) throws Exception {
		int fileNumber = 0;
		int nodeCounter = 0;
		int nodeIndexCounter = 0;
		XMLEventWriter xmlEventWriter = null;
		SplitResultInfo info = null;
		String fileName = null;
		List<SplitResultInfo> chunks = new ArrayList<SplitResultInfo>();
		while(xmlEventReader.hasNext()) {
			XMLEvent event = xmlEventReader.nextEvent();
			if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(criteria.getSplitterParentNode())) {
				info.setNodeEndIndex(info.getNodeStartIndex() + nodeCounter-1);
				for(XMLEvent footerElement : footer) {
					xmlEventWriter.add(footerElement);
				}
				chunks.add(info);
				xmlEventWriter.flush();
				xmlEventWriter.close();
			}
			
			if(event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(criteria.getSplitterNode())) {
				if(nodeCounter == 0) {
					info = new SplitResultInfo();
					fileName = XMLUtils.getOutputFileAbsolutePath(criteria.getSourceFilePath(), criteria.getOutputFilePath(), fileNumber);
					xmlEventWriter = writerfactory.createXMLEventWriter(new FileWriter(fileName));
					info.setChunkPath(fileName);
					info.setNodeStartIndex(nodeIndexCounter);
					for(XMLEvent headerEvent: header) {
						xmlEventWriter.add(headerEvent);
					}
				}
				while(!(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(criteria.getSplitterNode()))) {
					xmlEventWriter.add(event);
					event = xmlEventReader.nextEvent();
					if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(criteria.getSplitterNode())) {
						xmlEventWriter.add(event);
						if(nodeCounter== (criteria.getNodeSize()-1)) {
							for(XMLEvent footerEvent: footer) {
								xmlEventWriter.add(footerEvent);
							}
							xmlEventWriter.flush();
							xmlEventWriter.close();
							++fileNumber;
							nodeCounter=0;
							info.setNodeEndIndex(nodeIndexCounter);
							chunks.add(info);
							
						} else {
							++nodeCounter;
						}
						nodeIndexCounter++;
					}
				}
			}
		}		
		return chunks;
	}

	public static void main(String[] args) throws Exception {
		SplitCriteria criteria = SplitCriteria.builder().nodeSize(2).sourceFilePath("c:\\Data\\TestData_002435.xml").outputFilePath("c:\\Data\\Results").splitterNode("FNMR").splitterParentNode("BODY").build();
		XMLSplitter splitter = new XMLSplitter();
		List<SplitResultInfo> infos = splitter.split(criteria);
		
	}
}
