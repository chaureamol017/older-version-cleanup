package com.myproject.fileoperations.recursive;

import java.io.File;
import java.util.List;

public class RecursiveFilesAndFolders {
	
	public static List<File> getFiles(String path, String fileName, String... dirNamesToExclude) {
		List<File> allSubDirs = getAllSubDirs(path);

		List<File> files = getAllPomFiles(allSubDirs, fileName, dirNamesToExclude);

		return files;
	}

	private static List<File> getAllSubDirs(String path) {
		File startDir = new File(path);
		RecursiveFolders recursiveFolders = RecursiveFolders.create(startDir);
		List<File>  allSubDirs = recursiveFolders.getAllSubDirs();
		return allSubDirs;
	}

	private static List<File> getAllPomFiles(List<File> allSubDirs, String fileName, String... dirNamesToExclude) {
		List<File>  files = RecursiveFiles.getFiles(allSubDirs, fileName, dirNamesToExclude);

		return files;
	}

}
