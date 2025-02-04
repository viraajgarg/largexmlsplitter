package ady.zem.xml.splitter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import ady.zem.splitter.AbstractSplitter;
import ady.zem.xml.mo.SplitCriteria;
import ady.zem.xml.mo.SplitResultInfo;

public abstract class AbstractXMLSplitter extends AbstractSplitter<SplitResultInfo, SplitCriteria> {
	private static final String HEADER_ELEMENT = "HEADER";
	private static final String FOOTER_ELEMENT = "FOOTER";
	protected List<XMLEvent> header = new ArrayList<XMLEvent>();
	protected List<XMLEvent> footer = new ArrayList<XMLEvent>();

	public void readHeader(XMLEventReader xmlEventReader) throws FileNotFoundException, XMLStreamException {
		boolean headerFinished = false;
		while(xmlEventReader.hasNext() && headerFinished==false) {
			XMLEvent event = xmlEventReader.nextEvent();
			String line = event.toString();
			if(event.isEndElement() && event.asEndElement().toString().contains(HEADER_ELEMENT)) {
				headerFinished = true;
			}
			if(!line.trim().isEmpty()) {
				header.add(event);
			}
		}
	}

	public void readFooter(XMLEventReader xmlEventReader) throws FileNotFoundException, XMLStreamException {
		while(xmlEventReader.hasNext()) {
			XMLEvent event = xmlEventReader.nextEvent();
			if(event.isStartElement() && event.asStartElement().toString().contains(FOOTER_ELEMENT)) {
				footer.add(event);
				while(xmlEventReader.hasNext()) {
					XMLEvent footerEvent = xmlEventReader.nextEvent();
					String line = footerEvent.toString();
					if(!line.trim().isEmpty()) {
						footer.add(footerEvent);
					}
				}
			}
		}
	}
}
