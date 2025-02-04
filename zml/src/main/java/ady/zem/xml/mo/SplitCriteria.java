package ady.zem.xml.mo;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@Builder
@Getter
public class SplitCriteria implements Criteria {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2648582302099758659L;
	
	private int nodeSize;
	@NonNull
	private String splitterNode;
	@NonNull
	private String splitterParentNode;
	@NonNull
	private String sourceFilePath;
	@NonNull
	private String outputFilePath;
	private int chunkSize;

}
