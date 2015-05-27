package convertGraph;

import java.io.File;

public class Fichier {

	public static void deleteFileOrDirectory(String path) {
		File file;
		try {
			file = new File(path);
		} catch (Exception e) {
			file = null;
		}
		
		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				for (File child: file.listFiles()) {
					deleteFileOrDirectory(child.toString());
				}
			}
			file.delete();
		}
	}
	
	public static boolean isJSonFile(String path) {
		try {
			File file = new File(path);
			return file.exists() && file.isFile();
		} catch (Exception exception) {
			return false;
		}
	}

	public static boolean isFolderNeo4j(String path) {
		try {
			File file = new File(path);
			if (file.exists() && file.isDirectory()) {
				for (File child: file.listFiles()) {
					if (child.toString().equals(path +
							File.pathSeparator + "neostore")) {
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
