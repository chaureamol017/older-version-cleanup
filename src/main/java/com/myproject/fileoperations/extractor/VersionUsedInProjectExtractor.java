package com.myproject.fileoperations.extractor;

import java.io.File;
import java.util.List;
import java.util.Map;


import com.myproject.fileoperations.json.JsonDataExtractorFromFiles;
import com.myproject.fileoperations.recursive.RecursiveFilesAndFolders;

public class VersionUsedInProjectExtractor {
	private String path;

	private VersionUsedInProjectExtractor(String path) {
		super();
		this.path = path;
	}

	public static VersionUsedInProjectExtractor  create(String path) {
		return new VersionUsedInProjectExtractor(path);
	}

	public Map<String, Integer> extract(String fileName, String... dirNamesToExclude) {
		List<File> files = RecursiveFilesAndFolders.getFiles(path, fileName, dirNamesToExclude);
		System.out.println("Pom: " + files);

		Map<String, Integer> dependencyDetails = getDependencyDetails(files);
		
		return dependencyDetails;
	}

	private static Map<String, Integer> getDependencyDetails(List<File> files) {
		JsonDataExtractorFromFiles jsonDataExtractorFromFiles = JsonDataExtractorFromFiles.create(files);
		Map<String, Integer> dependencyDetails = jsonDataExtractorFromFiles.getDependencyDetails();

		return dependencyDetails;
	}

}
