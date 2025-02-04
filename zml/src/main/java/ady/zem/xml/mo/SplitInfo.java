package ady.zem.xml.mo;

import java.io.Serializable;

public interface SplitInfo extends Serializable {
	public String getChunkPath();
	public int getNodeStartIndex();
	public int getNodeEndIndex();
}
