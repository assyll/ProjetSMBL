package jsonAndGSTest;

import org.graphstream.graph.Graph;

import jsonAndGS.JsonToGS;
import junit.framework.TestCase;

public class JsonToGSTestRubis extends TestCase {

	public void testJsonToGS(){
		
		String filePath = "C:\\Users\\hugo\\Desktop\\jsonTest";
		Graph graph = JsonToGS.generateGraph(filePath);
		graph.display();
		
		while(true){}
	}
	
}
