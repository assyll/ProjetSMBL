package testTraceParsing;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import trace.impl.FETImpl;

public class FETTest {

	@Test
	public void getNextElementTest() throws FileNotFoundException {

		//Test sur un fichier de traces sans metadonnees
		FETImpl fet = new FETImpl(getClass().getResource("/traceExemple.txt").getFile());

		assertEquals("{\"timeStamp\":\"2015-06-15 09:54:12 AM\",\"event\":\"event 0\",\"action\":\"action 0\",\"userName\":\"user5\"}", fet.getNextElement());
		assertEquals("{\"timeStamp\":\"2015-06-15 09:54:14 AM\",\"event\":\"event 16\",\"action\":\"action 16\",\"userName\":\"user9\"}", fet.getNextElement());
		assertEquals("{\"timeStamp\":\"2015-06-15 09:54:19 AM\",\"event\":\"event 0\",\"action\":\"action 0\",\"userName\":\"user3\"}", fet.getNextElement());
		assertEquals("{\"timeStamp\":\"2015-06-15 09:54:32 AM\",\"event\":\"event 0\",\"action\":\"action 0\",\"userName\":\"user1\"}", fet.getNextElement());
		assertEquals("{\"timeStamp\":\"2015-06-15 09:54:32 AM\",\"event\":\"event 1\",\"action\":\"action 1\",\"userName\":\"user3\"}", fet.getNextElement());
		assertEquals("", fet.getNextElement());
		assertEquals("", fet.getNextElement());

		//Test sur un fichier vide
		fet = new FETImpl(getClass().getResource("/emptyFile.txt").getFile());
		assertEquals("", fet.getNextElement());

		//Test sur un fichier de traces ayant des metadonnees
		fet = new FETImpl(getClass().getResource("/traceWithMetaDataExpl.txt").getFile());
		assertEquals("{\"userName\":\"#SYS#\",\"event\":\"NONE\",\"action\":\"START\",\"actionTarget\":\"WEBAPP\",\"data\":{},\"timeStamp\":\"2015-01-15 01:00:50 AM\"}", fet.getNextElement());
		assertEquals("{\"userName\":\"lder\",\"event\":\"NONE\",\"action\":\"OPEN\",\"actionTarget\":\"SCREEN\",\"data\":{\"title\":\"Incubator\",\"codePhase\":\"PHASE_ACCUEIL\",\"mode\":\"DESK\"},\"timeStamp\":\"2015-01-15 01:02:54 AM\"}", fet.getNextElement());
		assertEquals("", fet.getNextElement());

		//Test sur un fichier qui n'est pas un fichier de traces
		fet = new FETImpl(getClass().getResource("/notALogFile.txt").getFile());
		assertEquals("", fet.getNextElement());

	}
}
