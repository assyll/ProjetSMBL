import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import generator.Action;
import generator.State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StateTest {

	private State state1, state2;
	private Action action1;
	
	@Before
	public void start() {
		state1 = new State("etat 1", false);
		state2 = new State("etat 2", true);
		action1 = new Action("action 1");
	}
	
	@After
	public void finish() {
		state1 = null;
		state2 = null;
		action1 = null;
	}
	
	@Test
	public void testAddAction1() {
		state1.addAction(action1, state1);
		assertEquals(state1.getActions().next(), action1);
	}
	
	@Test
	public void testAddAction2() {
		state1.addAction(action1, state1);
		state1.addAction(action1, state1);
		Iterator<Action> it = state1.getActions();
		it.next();
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testGetName() {
		assertEquals(state1.getName(), "etat 1");
	}
	
	@Test
	public void testToString() {
		assertEquals(state1.toString(), "etat 1");
	}
	
	@Test
	public void testGetActionAleat() {
		final int NB_ACTIONS = 5;
		final int NB_ESSAIS = 100;
		
		Action[] actions = new Action[NB_ACTIONS];
		for (int i = 0; i < NB_ACTIONS; i++) {
			actions[i] = new Action("action " + i);
			state1.addAction(actions[i], state2);
			state2.addAction(actions[i], state1);
		}
		
		Map<Action, Integer> actionsAleat1 = new HashMap<Action, Integer>();
		Map<Action, Integer> actionsAleat2 = new HashMap<Action, Integer>();
		for (int i = 0; i < NB_ESSAIS; i++) {
			Action action1 = state1.getActionAleat();
			Action action2 = state2.getActionAleat();
			
			actionsAleat1.put(action1,
					(actionsAleat1.containsKey(action1))
						? actionsAleat1.get(action1) + 1 : 0);
			actionsAleat2.put(action2,
					(actionsAleat2.containsKey(action2))
						? actionsAleat2.get(action2) + 1 : 0);
		}
		
		assertEquals(actionsAleat1.keySet().size(), NB_ACTIONS);
		assertEquals(actionsAleat2.keySet().size(), NB_ACTIONS + 1);
		
		for (Integer nombreFois: actionsAleat1.values()) {
			assertTrue(nombreFois >= 1);
		}
		
		for (Integer nombreFois: actionsAleat2.values()) {
			assertTrue(nombreFois >= 1);
		}
	}
	
	@Test
	public void testExecuteAction() {
		State state3 = state1;
		state3.addAction(action1, state2);
		state3 = state3.executeAction(action1);
		assertEquals(state3, state2);
	}
	
	@Test
	public void testEquals() {
		State state3 = new State("etat 1", false);
		assertEquals(state3, state1);
	}
	
}
