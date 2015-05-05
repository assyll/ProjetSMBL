package jSonToGS;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;


public class JSonGenerator {
	public static void main(String[] args) {
		 
	     try {
	 
		JsonFactory jfactory = new JsonFactory();
	 
		/*** write to file ***/
		JsonGenerator jGenerator = jfactory.createJsonGenerator(new File(
				"C:\\Users\\hugo\\Desktop\\jsonTest"), JsonEncoding.UTF8);
		jGenerator.writeStartObject(); // {
	 
		jGenerator.writeStringField("name", "hugo"); // "name" : "hugo"
		jGenerator.writeNumberField("age", 24); // "age" : 24
		jGenerator.writeStringField("know", "none"); // "know" : "none"
	 
		jGenerator.writeEndObject(); // }
		
		jGenerator.writeStartObject(); // {
		
		jGenerator.writeStringField("name", "manu"); // "name" : "hugo"
		jGenerator.writeNumberField("age", 23); // "age" : 23
		jGenerator.writeStringField("know", "hugo"); // "know" : "hugo"
		
		jGenerator.writeEndObject(); // }
	 
		jGenerator.writeStartObject(); // {
		
		jGenerator.writeStringField("name", "Frederic"); // "name" : "Frederic"
		jGenerator.writeNumberField("age", 25); // "age" : 25
		
		jGenerator.writeFieldName("knows"); // "messages" :
		jGenerator.writeStartArray(); // [
		 
		jGenerator.writeString("hugo"); // "hugo"
		jGenerator.writeString("manu"); // "manu"
	 
		jGenerator.writeEndArray(); // ]
		
		jGenerator.writeEndObject(); // }
		
		jGenerator.close();
	 
	     } catch (JsonGenerationException e) {
	 
		e.printStackTrace();
	 
	     } catch (JsonMappingException e) {
	 
		e.printStackTrace();
	 
	     } catch (IOException e) {
	 
		e.printStackTrace();
	 
	     }
	 
	   }
	 
	}