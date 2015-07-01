package testTraceParsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import general.TraceActionParserTestComp;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import trace.Action;
import trace.ActionTrace;

public class TraceActionParserTest {

	private Action actionA1, actionA2, actionA3, actionA4, actionA10;

	@Before
	public void init(){
		Map<String,String> actionMap = new HashMap<>();
		actionMap.put("action", "A1");
		actionA1 = new Action(actionMap);

		actionMap = new HashMap<>();
		actionMap.put("action", "A2");
		actionA2 = new Action(actionMap);

		actionMap = new HashMap<>();
		actionMap.put("action", "A3");
		actionA3 = new Action(actionMap);

		actionMap = new HashMap<>();
		actionMap.put("action", "A4");
		actionA4 = new Action(actionMap);

		actionMap = new HashMap<>();
		actionMap.put("action", "A10");
		actionA10 = new Action(actionMap);
	}

	@Test
	public void getNextElementTest() {

		//Test avec des traces multi-utilisateurs
		String path = getClass().getResource("/simpleActionTrace.txt").getFile();
		TraceActionParserTestComp.Component traceActionParserTest = new TestActionParserTestCompImpl(path).newComponent();

		assertEquals(new ActionTrace("U1", actionA1), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U2", actionA1), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U1", actionA2), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U3", actionA1), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U1", actionA4), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U1", actionA10), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U3", actionA3), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U3", actionA4), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U3", actionA10), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U4", actionA1), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U4", actionA3), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertEquals(new ActionTrace("U2", actionA2), (ActionTrace) traceActionParserTest.actionTrace().getNextElement());
		assertTrue((ActionTrace) traceActionParserTest.actionTrace().getNextElement() == null);
		
	}
}
