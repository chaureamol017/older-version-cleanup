package com.myproject.fileoperations.extractor;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnusedVersionFromAtrifactExtractor {
	static Counter counter = new Counter();

	public static List<File> getLowerVersionsToDelete(String startPath, String groupAndArtifactId, Integer version) {
		List<File> allVersions = getAllVersions(startPath, groupAndArtifactId);

		System.out.println(counter.getAndIncrement() + ": Group And ArtifactId: " + groupAndArtifactId + " Version: " + version);

		List<File> filteredList = filterForLowerVersions(version, allVersions);

		System.out.println("\t All versions count: " + allVersions.size() + " Filtered versions count: " + filteredList.size());
		return filteredList;
	}

	private static List<File> getAllVersions(String startPath, String groupAndArtifactId) {
		String[] dirArr = groupAndArtifactId.split("\\.");
		String dependencyPath = startPath + String.join(File.separator, dirArr);

		File dir = new File(dependencyPath);
		File[] allVersions = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() && (pathname.getName().indexOf(".") > -1);
			}
		});
		return new ArrayList<>(Arrays.asList(allVersions));
	}

	private static List<File> filterForLowerVersions(Integer version, List<File> allVersions) {
		List<File> filteredList = allVersions.stream().filter(versionDir -> {
			String name = versionDir.getName();
			Integer value = Integer.parseInt(name.substring(name.lastIndexOf(".") + 1));
			return value < version;
		}).collect(Collectors.toList());

		return filteredList;
	}

}

class Counter {
	int index = 1;

	public int getAndIncrement() {
		return index++;
	}
	

}
	