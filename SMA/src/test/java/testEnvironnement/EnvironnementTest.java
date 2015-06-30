package testEnvironnement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import environnement.impl.EnvironnementImpl;

import org.junit.Before;
import org.junit.Test;

import trace.Action;

public class EnvironnementTest{

	List<Action> actionListA1, actionListA2, actionListA1A2, actionListA1A2A3;
	Action actionA1, actionA2, actionA3;

	@Before
	public void init() {
		Map<String,String> actionMap = new HashMap<String, String>();
		actionMap.put("action", "A1");
		actionA1 = new Action(actionMap);

		actionMap = new HashMap<String, String>();
		actionMap.put("action", "A2");
		actionA2 = new Action(actionMap);

		actionMap = new HashMap<String, String>();
		actionMap.put("action", "A3");
		actionA2 = new Action(actionMap);

		actionListA1 = new ArrayList<Action>();
		actionListA1.add(actionA1);

		actionListA2 = new ArrayList<Action>();
		actionListA2.add(actionA2);

		actionListA1A2 = new ArrayList<Action>();
		actionListA1A2.add(actionA1);
		actionListA1A2.add(actionA2);

		actionListA1A2A3 = new ArrayList<Action>();
		actionListA1A2A3.add(actionA1);
		actionListA1A2A3.add(actionA2);
		actionListA1A2A3.add(actionA3);
	}

	@Test 
	public void cellCreationTest(){
		EnvironnementImpl environnement = new EnvironnementImpl();

		assertFalse(null == environnement);
		assertTrue(1 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());

		// Ajout d'agents sans actions => pas de création de nouvelles cellules (un niveau et une cellule dans le niveau)
		environnement.addStateAgent("S0");
		assertTrue(1 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());

		environnement.addStateAgent("S1");
		assertTrue(1 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());

		//Ajout d'un nouvel agent ayant une action
		environnement.addStateAgent("S2", actionListA1);
		assertTrue(2 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());
		assertTrue(1 == environnement.getCellsByLevel().get(1).size());

		//Ajout d'un nouvel agent ayant une novuelle action
		environnement.addStateAgent("S3", actionListA2);
		assertTrue(2 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());
		assertTrue(2 == environnement.getCellsByLevel().get(1).size());

		//Ajout d'un nouvel agent ayant plusieurs actions
		environnement.addStateAgent("S4", actionListA1A2A3);
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());
		assertTrue(2 == environnement.getCellsByLevel().get(1).size());
		assertTrue(1 == environnement.getCellsByLevel().get(3).size());

		//Ajout d'un nouvel agent ayant une action dont la cellule existe déjà
		environnement.addStateAgent("S5", actionListA1);
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());
		assertTrue(2 == environnement.getCellsByLevel().get(1).size());
		assertTrue(1 == environnement.getCellsByLevel().get(3).size());
	}

	@Test 
	public void addAndGetAgentsInCellTest(){
		EnvironnementImpl environnement = new EnvironnementImpl();
		environnement.getAllAgentsInCell(new ArrayList<Action>());
		assertTrue(0 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());

		// Ajout d'agents sans actions
		environnement.addStateAgent("S1");
		assertTrue(1 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));

		environnement.addStateAgent("S2");
		assertTrue(2 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));

		//Ajout d'un nouvel agent ayant une action
		List<Action> actionList1 = new ArrayList<Action>();
		Map<String,String> actionMap = new HashMap<String, String>();
		actionMap.put("action", "A1");
		actionList1.add(new Action(actionMap));
		environnement.addStateAgent("S3", actionList1);
		assertTrue(2 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList1).size());
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S3"));

		//Ajout d'un nouvel agent ayant une novuelle action
		List<Action> actionList2 = new ArrayList<Action>();
		actionMap = new HashMap<String, String>();
		actionMap.put("action", "A2");
		actionList2.add(new Action(actionMap));
		environnement.addStateAgent("S4", actionList2);
		assertTrue(2 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList1).size());
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S3"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList2).contains("S4"));

		//Ajout d'un nouvel agent ayant plusieurs actions
		List<Action> actionList3 = new ArrayList<Action>();
		actionMap = new HashMap<String, String>();
		actionMap.put("action", "A2");			
		actionList3.add(new Action(actionMap));
		actionMap = new HashMap<String, String>();
		actionMap.put("action", "A1");
		actionList3.add(new Action(actionMap));
		actionMap = new HashMap<String, String>();
		actionMap.put("action", "A3");
		actionList3.add(new Action(actionMap));
		environnement.addStateAgent("S5", actionList3);
		assertTrue(2 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList1).size());
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S3"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList2).contains("S4"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList3).size());
		assertTrue(environnement.getAllAgentsInCell(actionList3).contains("S5"));

		//Ajout d'un nouvel agent ayant une action dont la cellule existe déjà
		List<Action> actionList4 = new ArrayList<Action>();
		actionMap = new HashMap<String, String>();
		actionMap.put("action", "A1");
		actionList4.add(new Action(actionMap));
		environnement.addStateAgent("S6", actionList4);
		assertTrue(2 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S3"));
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S6"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList2).contains("S4"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList3).size());
		assertTrue(environnement.getAllAgentsInCell(actionList3).contains("S5"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList3).size());

		//Tests avec des valeurs nulls
		environnement.addStateAgent(null, actionList2);
		assertTrue(2 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S3"));
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S6"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList2).contains("S4"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList3).size());
		assertTrue(environnement.getAllAgentsInCell(actionList3).contains("S5"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList3).size());

		environnement.addStateAgent("S7", null);
		assertTrue(2 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S3"));
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S6"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList2).contains("S4"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList3).size());
		assertTrue(environnement.getAllAgentsInCell(actionList3).contains("S5"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList3).size());

		environnement.addStateAgent(null, null);
		assertTrue(2 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S3"));
		assertTrue(environnement.getAllAgentsInCell(actionList1).contains("S6"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList2).size());
		assertTrue(environnement.getAllAgentsInCell(actionList2).contains("S4"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList3).size());
		assertTrue(environnement.getAllAgentsInCell(actionList3).contains("S5"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionList3).size());

	}

	@Test
	public void removeAgentTest(){
		EnvironnementImpl environnement = new EnvironnementImpl();

		// Ajout des agents dans l'environnement
		environnement.addStateAgent("S1");

		environnement.addStateAgent("S3", actionListA1);		
		environnement.addStateAgent("S4", actionListA1);		
		environnement.addStateAgent("S5", actionListA1);	

		environnement.addStateAgent("S2", actionListA2);		


		environnement.addStateAgent("S6", actionListA1A2);	
		environnement.addStateAgent("S7", actionListA1A2);	

		//Tests suppression de l'agent de la cellule représentant l'ensemble vide 
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		environnement.removeAgent("S1", new ArrayList<Action>());
		assertFalse(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(0 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (1 == environnement.getCellsByLevel().get(0).size());

		//Tests suppression de deux agents de la cellule correspondant à l'action A1
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S3"));
		environnement.removeAgent("S3", actionListA1);
		assertFalse(environnement.getAllAgentsInCell(actionListA1).contains("S3"));
		assertTrue(2 == environnement.getAllAgentsInCell(actionListA1).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (2 == environnement.getCellsByLevel().get(1).size());

		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S5"));
		environnement.removeAgent("S5", actionListA1);
		assertFalse(environnement.getAllAgentsInCell(actionListA1).contains("S5"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S4"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionListA1).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (2 == environnement.getCellsByLevel().get(1).size());

		//Tests suppression de l'agent de la cellule représentant correspondant à l'action A2
		assertTrue(environnement.getAllAgentsInCell(actionListA2).contains("S2"));
		environnement.removeAgent("S2", actionListA2);
		assertFalse(environnement.getAllAgentsInCell(actionListA2).contains("S2"));
		assertTrue(0 == environnement.getAllAgentsInCell(actionListA2).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (2 == environnement.getCellsByLevel().get(1).size());

		//Tests suppression de l'agent de la cellule représentant correspondant aux actions A1 et A2
		assertTrue(environnement.getAllAgentsInCell(actionListA1A2).contains("S6"));
		environnement.removeAgent("S6", actionListA1A2);
		assertFalse(environnement.getAllAgentsInCell(actionListA1A2).contains("S6"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionListA1A2).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (1 == environnement.getCellsByLevel().get(2).size());

		//Test suppression d'un agent qui n'existe pas du tout d'une cellule
		environnement.removeAgent("S10", actionListA1A2);
		assertTrue(1 == environnement.getAllAgentsInCell(actionListA1A2).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (1 == environnement.getCellsByLevel().get(2).size());

		environnement.removeAgent("S10", new ArrayList<Action>());
		assertTrue(0 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (1 == environnement.getCellsByLevel().get(0).size());

		//Test suppression d'un agent, qui existe dans une cellule, d'une autre cellule
		environnement.removeAgent("S7", actionListA1);
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S4"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionListA1).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (2 == environnement.getCellsByLevel().get(1).size());
		assertTrue(environnement.getAllAgentsInCell(actionListA1A2).contains("S7"));
		assertTrue (1 == environnement.getCellsByLevel().get(2).size());

		//Tests avec des valeurs nulls
		environnement.removeAgent(null, actionListA1);
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S4"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionListA1).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (2 == environnement.getCellsByLevel().get(1).size());

		environnement.removeAgent("S4", null);
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S4"));
		assertTrue(1 == environnement.getAllAgentsInCell(actionListA1).size());
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue (2 == environnement.getCellsByLevel().get(1).size());

		environnement.removeAgent(null, null);
		assertTrue(3 == environnement.getCellsByLevel().size());
	}

	@Test
	public void moveTest(){
		EnvironnementImpl environnement = new EnvironnementImpl();

		//Deplacement d'un agent donnant lieu à une création d'une nouvelle cellule
		environnement.addStateAgent("S0");

		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S0"));
		assertTrue(1 == environnement.getCellsByLevel().size());

		environnement.move("S0", new ArrayList<Action>(), actionA1);

		assertFalse(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S0"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S0"));
		assertTrue(2 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());
		assertTrue(1 == environnement.getCellsByLevel().get(1).size());

		//Deplacement d'un agent dans une cellule déjà existante
		environnement.addStateAgent("S1");
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(2 == environnement.getCellsByLevel().size());

		environnement.move("S1", new ArrayList<Action>(), actionA1);

		assertFalse(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S0"));
		assertTrue(2 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());
		assertTrue(1 == environnement.getCellsByLevel().get(1).size());

		//Double deplacement d'un agent dans une nouvelle celulle 
		environnement.addStateAgent("S2");
		assertTrue(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));
		assertTrue(2 == environnement.getCellsByLevel().size());

		environnement.move("S2", new ArrayList<Action>(), actionA2);
		
		assertFalse(environnement.getAllAgentsInCell(new ArrayList<Action>()).contains("S2"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S0"));
		assertTrue(environnement.getAllAgentsInCell(actionListA2).contains("S2"));
		
		assertTrue(2 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());
		assertTrue(2 == environnement.getCellsByLevel().get(1).size());
		
		environnement.move("S2", actionListA2, actionA1);
		
		assertFalse(environnement.getAllAgentsInCell(actionListA2).contains("S2"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S0"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1A2).contains("S2"));
		
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());
		assertTrue(2 == environnement.getCellsByLevel().get(1).size());
		assertTrue(1 == environnement.getCellsByLevel().get(2).size());
		assertTrue(1 == environnement.getAllAgentsInCell(actionListA1A2).size());

		//Tests avec parametres ne correspondant pas a la realite
		environnement.move("S4", actionListA1, actionA1);
		
		assertTrue(0 == environnement.getAllAgentsInCell(new ArrayList<Action>()).size());
		assertTrue(2 == environnement.getAllAgentsInCell(actionListA1).size());
		assertTrue(0 == environnement.getAllAgentsInCell(actionListA2).size());
		System.out.println(environnement.getAllAgentsInCell(actionListA1A2).get(0));
		System.out.println(environnement.getAllAgentsInCell(actionListA1A2).get(1));  

		assertTrue(1 == environnement.getAllAgentsInCell(actionListA1A2).size());
		

		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S1"));
		assertTrue(environnement.getAllAgentsInCell(actionListA1).contains("S0"));
		assertTrue(3 == environnement.getCellsByLevel().size());
		assertTrue(1 == environnement.getCellsByLevel().get(0).size());
		assertTrue(2 == environnement.getCellsByLevel().get(1).size());
		assertTrue(1 == environnement.getCellsByLevel().get(2).size());

	}
}
