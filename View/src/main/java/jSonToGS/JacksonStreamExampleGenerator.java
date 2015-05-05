package jSonToGS;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

 
public class JacksonStreamExampleGenerator {
   public static void main(String[] args) {
 
     try {
 
	JsonFactory jfactory = new JsonFactory();
 
	/*** write to file ***/
	JsonGenerator jGenerator = jfactory.createJsonGenerator(new File(
			"C:\\Users\\hugo\\Desktop\\json"), JsonEncoding.UTF8);
	jGenerator.writeStartObject(); // {
 
	jGenerator.writeStringField("name", "hugo"); // "name" : "hugo"
	jGenerator.writeNumberField("age", 24); // "age" : 24
 
	jGenerator.writeFieldName("messages"); // "messages" :
	jGenerator.writeStartArray(); // [
 
	jGenerator.writeString("msg 1"); // "msg 1"
	jGenerator.writeString("msg 2"); // "msg 2"
	jGenerator.writeString("msg 3"); // "msg 3"
 
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