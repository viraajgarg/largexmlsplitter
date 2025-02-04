package ady.zem.xml.mo;

import java.io.Serializable;

public interface Criteria extends Serializable {
	public int getNodeSize();
	public String getSplitterNode();
	public String getSplitterParentNode();
	public int getChunkSize();
	public String getSourceFilePath();
	public String getOutputFilePath();
}
