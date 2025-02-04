package ady.zem.xml.splitter;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class XMLUtils {
	public static String getOutputFileAbsolutePath(String sourceFileAbsolutePath, String outputFolderPath, int fileCounter) {
		String sourceFileName = sourceFileAbsolutePath.substring( sourceFileAbsolutePath.lastIndexOf(File.separator) +1);
		String sourceFileNameWOExtn = sourceFileName.substring(0, sourceFileName.lastIndexOf("."));
		String extn = sourceFileName.substring(sourceFileName.lastIndexOf("."));
		return outputFolderPath + File.separator + sourceFileNameWOExtn + "_" + StringUtils.leftPad( "" + fileCounter, 6, "0") + extn;
	}
}
