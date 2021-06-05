package com.myproject.fileoperations.recursive;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecursiveFiles {

	public static List<File> getDirHavingFiles(List<File> dirList, String fileName) {
		FileFilter fileFilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().equals(fileName);
			}
		};

		final List<File>  filteredDirs = filterAndAddParentDirToList(dirList, fileFilter);

		return filteredDirs;
	}

	public static List<File> getDirHavingFiles(List<File> dirList) {
		FileFilter fileFilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		};

		final List<File>  filteredDirs = filterAndAddParentDirToList(dirList, fileFilter);

		return filteredDirs;
	}

	public static List<File> getFiles(List<File> dirList, String fileName, String... dirNamesToExclude) {
		FileFilter fileFilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				boolean isFileOfNameExist = pathname.isFile() && pathname.getName().equals(fileName);
				boolean shoulNotInSkipDir = true;
				if (dirNamesToExclude.length > 0) {
					for (String dirName : dirNamesToExclude) {
						if (pathname.getAbsolutePath().contains(dirName)) {
							shoulNotInSkipDir = false;
						}
					}
				}
				
				return isFileOfNameExist && shoulNotInSkipDir;
			}
		};

		final List<File> filteredDirs = filterAndAddFileToList(dirList, fileFilter);

		return filteredDirs;
	}

	public static List<File> getFiles(List<File> dirList) {
		FileFilter fileFilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		};

		final List<File> filteredDirs = filterAndAddFileToList(dirList, fileFilter);

		return filteredDirs;
	}

	public static void deleteFiles(List<File> files) {
		files.forEach(file -> {
			if (file.isDirectory()) {
				File[] subDir = file.listFiles();
				List<File> filesToDel = new ArrayList<>(Arrays.asList(subDir));
				deleteFiles(filesToDel);
			}
			file.delete();
//			System.out.println(file.delete());
		});
	}

	private static List<File> filterAndAddParentDirToList(List<File> dirList, FileFilter fileFilter) {
		final List<File>  filtered = new ArrayList<>();
		dirList.forEach(dir -> {
			File[] files = dir.listFiles(fileFilter);
			if (files.length > 0) {
				filtered.add(dir);
			}
		});
		return filtered;
	}

	private static List<File> filterAndAddFileToList(List<File> dirList, FileFilter fileFilter) {
		final List<File>  filtered = new ArrayList<>();
		dirList.forEach(dir -> {
			File[] files = dir.listFiles(fileFilter);
			for (File file : files) {
				filtered.add(file);
			}
		});
		return filtered;
	}

}
