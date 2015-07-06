package trace.impl;

import general.FET;
import generalStructure.interfaces.IInit;
import generalStructure.interfaces.IPath;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import trace.interfaces.TraceElement;


public class FETImpl extends FET implements TraceElement, IInit, IPath {

	private FileReader _reader;
	private String _path;
	private int _index ;

	public FETImpl(String filePath) {
		_path = null;//filePath;
		init();
	}
	
	@Override
	public void setPath(String path) {
		_path = path;
		try {
			_index = 0;
			if (_reader != null) {
				_reader.close();
				_reader = null;
			}
			_reader = new FileReader(_path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasPath() {
		return _path != null;
	}
	
	@Override
	public void init() {
		_path = null;
	}

	public String getNextElement() 
	{
		String traceElmnt = "";
		try {
			if(_reader != null && _reader.ready())
			{		
				boolean stop = false;
				char current = (char) _reader.read();
				int nbAccolades = 0;

				while(current != '{' && _reader.ready())
				{
					current = (char) _reader.read();
				}

				if(current == '{' && _reader.ready())
				{
					nbAccolades++;
					traceElmnt += current;
				}

				while(!stop && _reader.ready())
				{
					current = (char) _reader.read();
					if(current == '{')
					{
						nbAccolades++;
					}else if(current =='}')
					{
						nbAccolades--;

						if(nbAccolades == 0) 
						{
							stop = true;
						}
					}
					
					traceElmnt += current;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return traceElmnt;
	}


	@Override
	protected TraceElement make_traceElement() {
		return this;
	}

	@Override
	protected IInit make_init() {
		return this;
	}

	@Override
	protected IPath make_path() {
		return this;
	}

}
