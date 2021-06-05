package com.myproject.fileoperations.recursive;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class RecursiveFolders {
	final List<File>  allSubDirs = new ArrayList<>();
	private File startDir;
	
	private RecursiveFolders(File startDir) {
		this.startDir = startDir;
	}
	
	public static RecursiveFolders create(File startDir) {
		return new RecursiveFolders(startDir);
	}

	public List<File> getAllSubDirs() {
		listFolder(startDir);
		return allSubDirs;
	}

	private void listFolder(File dir) {
		File[] subDirs = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});

//		if (!startDir.getAbsolutePath().equals(dir.getAbsolutePath())) {
//			allSubDirs.add(dir);
//		}
		allSubDirs.add(dir);

		for (File subDir : subDirs)  {
			listFolder(subDir);
		}
	}

}
