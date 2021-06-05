package com.myproject.fileoperations.test;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.myproject.fileoperations.extractor.UnusedVersionFromAtrifactExtractor;
import com.myproject.fileoperations.extractor.VersionUsedInProjectExtractor;
import com.myproject.fileoperations.recursive.RecursiveFiles;

public class OlderVersionRemovalTest {

	public static void main(String[] args) {
		String projectPath = "/Users/chaurea/cdkGlobal/dap";
		String repoHomePath = "/Users/chaurea/.m2/repository/";
		
		VersionUsedInProjectExtractor extractor = VersionUsedInProjectExtractor.create(projectPath);

		Map<String, Integer> dependencyDetails = extractor.extract("pom.xml", "bin", "META-INF");
		System.out.println("Dependencies: " + dependencyDetails);
		
		for (Map.Entry<String, Integer> entry : dependencyDetails.entrySet()) {
			List<File> versionsToDelete = UnusedVersionFromAtrifactExtractor.getLowerVersionsToDelete(repoHomePath, entry.getKey(), entry.getValue());

			RecursiveFiles.deleteFiles(versionsToDelete);
		}
	}

}
