package com.myproject.fileoperations.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class JsonDataExtractorFromFiles {
	final Map<String, Integer> dependencyDetailsMap = new HashMap<>();
	List<File> files;

	private JsonDataExtractorFromFiles(List<File> files) {
		this.files = files;
	}

	public static JsonDataExtractorFromFiles create(List<File> files) {
		return new JsonDataExtractorFromFiles(files);
	}

	public Map<String, Integer> getDependencyDetails() {
		files.forEach(file -> {
			System.out.println("\tProcessing: " + file.getAbsolutePath());
			try {
				JSONObject jsonObject = redJsonFromXmlFile(file);
				processJson(jsonObject);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		return dependencyDetailsMap;
	}

	private static JSONObject redJsonFromXmlFile(File dir) throws IOException {
		byte[] b = Files.readAllBytes(dir.toPath());
		String xml = new String(b);

		JSONObject jsonObject = XML.toJSONObject(xml);
		return jsonObject;
	}

	private void processJson(JSONObject jsonObject) {
		JSONObject project = jsonObject.getJSONObject("project");
		if (project.has("dependencies") && project.get("dependencies") instanceof JSONObject) {
			try {
				JSONObject j = project.getJSONObject("dependencies");
				if (j.has("dependency") && j.get("dependency") instanceof JSONArray) {
					JSONArray dependencies = j.getJSONArray("dependency");

					for (int i = 0; i < dependencies.length(); i++) {
						JSONObject dependency = dependencies.getJSONObject(i);
						checkAndAddOrUpdateVersion(dependency);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkAndAddOrUpdateVersion(JSONObject dependency) {
		if (dependency.has("groupId") && dependency.getString("groupId").contains("cobalt")
				&& dependency.has("artifactId") && dependency.has("version")) {
			String version = dependency.getString("version");
			if (version.contains(",")) {
				Integer value = extractVersion(version);
				String key = dependency.getString("groupId") + "." + dependency.getString("artifactId");

				updateDependencyDetailsMap(value, key);
			}
		}
	}

	private Integer extractVersion(String version) {
		version = version.substring(1, version.length() - 1);
		String[] versions = version.split(",");
		Integer value = Integer.parseInt(versions[0].substring(versions[0].lastIndexOf(".") + 1));
		return value;
	}

	private void updateDependencyDetailsMap(Integer value, String key) {
		if (dependencyDetailsMap.containsKey(key)) {
			Integer previousValue = dependencyDetailsMap.get(key);
			if (previousValue < value) {
				value = previousValue;
			}
		}
		dependencyDetailsMap.put(key, value);
	}

}
