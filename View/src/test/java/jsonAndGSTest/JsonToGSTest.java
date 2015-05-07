package jsonAndGSTest;

import org.graphstream.graph.Graph;

import jsonAndGS.JsonToGS;
import junit.framework.TestCase;

public class JsonToGSTest extends TestCase {

	public void testJsonToGS(){
		
		String filePath = "C:\\Users\\hugo\\Desktop\\jsonTest";
		JsonToGS jTGS = new JsonToGS();
		Graph graph = jTGS.generateGraph(filePath);
		
		while(true){}
	}
	
}
