package trace.impl;

import general.FET;
import generalStructure.interfaces.IInit;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import trace.interfaces.TraceElement;


public class FETImpl extends FET implements TraceElement, IInit {

	private FileReader _reader;
	private String _path;
	private int _index ;

	public FETImpl(String filePath) {
		_path = filePath;
		init();
	}
	
	public void setPath(String path) {
		_path = path;
	}
	
	@Override
	public void init() {
		try {
			_index = 0;
			if (_reader != null) {
				_reader.close();
				_reader = null;
			}
			_reader = new FileReader(_path);
		} catch (Exception e) {
			
		}
	}

	public String getNextElement() 
	{
		String traceElmnt = "";

		try {
			if(_reader.ready())
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

}
