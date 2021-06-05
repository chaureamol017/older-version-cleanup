package com.myproject.fileoperations.test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.myproject.fileoperations.extractor.UnusedVersionFromAtrifactExtractor;
import com.myproject.fileoperations.extractor.VersionUsedInProjectExtractor;
import com.myproject.fileoperations.recursive.RecursiveFiles;

public class TempTest {

	public static void main(String[] args) {
		String startPath = "/Users/chaurea/.m2/repository/";
		String groupAndArtifactId = "com.cobalt.dap.dap-persistence";
		Integer version = 6462;

//		List<File> versionsToDelete = UnusedVersionFromAtrifactExtractor.getLowerVersionsToDelete(startPath, groupAndArtifactId, version);
//		System.out.println(" Filtered versions: " + versionsToDelete);

//		RecursiveFiles.deleteFiles(versionsToDelete);

		File startDir = new File("/Users/chaurea/.m2/repository/com/cobalt/dap/dap-persistence/2.0.2568");
		List<File> files = new ArrayList<>();
		
		files.add(startDir);
		RecursiveFiles.deleteFiles(files);
	}

}
