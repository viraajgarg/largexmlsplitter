# largeXMLSplitter
This tool breaks a large xml using streaming API for XML (StAX Parsers)

A lerge xmls in GBs can be split into smaller and smaller chunks using low memory footprint


# Input Criteria
you need to provide following information to perform task

1. NodeSize: How much number of XML nodes you require in output chunks
2. sourceFilepath: Source file which is extremely very large
3. outputFilePath: target folder where files needs to be created
4. splitterNode: The node which is used to split xmls. such nodes are repeated with their own data elements per node
5. splitterParentNode: The wrapping node of  splitterNode


# Utilization
This can be used across the board in following sectors:
1. Processing of Financial markets data
2. Processing of large settlement Files for card industry
3. Data digestion of logistics and shipping data
4. mission critical satellite data for weather forcasting and meteorological purposes.

