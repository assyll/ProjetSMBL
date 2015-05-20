package trace.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import trace.FET;
import trace.interfaces.TraceElement;


public class FETImpl extends FET implements TraceElement {

	FileReader _reader;
	int _index ;

	public FETImpl(String filePath) throws FileNotFoundException 
	{
		_reader = new FileReader(filePath);
		_index = 0;

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
	protected TraceElement make_elementDeTrace() {
		return this;
	}

}
