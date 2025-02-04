package ady.zem.xml.mo;

import lombok.Data;

@Data
public class SplitResultInfo implements SplitInfo {	
	private String chunkPath;
	private int nodeStartIndex;
	private int nodeEndIndex;
}
