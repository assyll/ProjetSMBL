package convertGraph;

import java.io.File;

public class Fichier {

	public static void deleteFileOrDirectory(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File child: file.listFiles()) {
					deleteFileOrDirectory(child);
				}
			}
			file.delete();
		}
	}

	public static boolean isFolderNeo4j(String path) {
		try {
			File file = new File(path);
			if (file.exists() && file.isDirectory()) {
				for (File child: file.listFiles()) {
					if (child.toString().equals(path + "/neostore")) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception exception) {
			return false;
		}
	}
	
}
