package generalStructure.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import general.LogComp;
import generalStructure.interfaces.ILog;

public class LogCompImpl extends LogComp implements ILog {

	private final String _path;
	private FileWriter _writer;
	
	public LogCompImpl(String path) {
		
		_path = path;
		
		try {
			File file = new File(_path);
			if (!file.exists()) {
				file.createNewFile();
			}
			_writer = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected ILog make_log() {
		return this;
	}

	@Override
	public void ecrire(Map<String, String> informations) {
		String contenu = "{";
		
		for (Entry<String, String> e: informations.entrySet()) {
			contenu += "\"" + e.getKey() + "\":";
			contenu += "\"" + e.getValue() + "\",";
		}
		contenu = contenu.substring(0, contenu.length() - 1);
		contenu += "}\n";
		
		writeToFile(contenu);
	}

	@Override
	public void closeFile() {
		try {
			_writer.flush();
			_writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToFile(String contenu) {
		try {
			_writer.write(contenu);
			_writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
