package agents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trace.Action;
public class MainClass {

	public static void main(String[] args) {
	//new BigEcoImpl().newComponent();
		
		Map<String,String> actions = new HashMap<>();
		actions.put("action1", "A1");
		actions.put("action2", "A2");
		actions.put("action3", "A3");
		actions.put("action4", "A4");
		
		Map<String,String> actions1 = new HashMap<>();
		/*actions1.put("action1", "A1");
		actions1.put("action2", "A2");
		actions1.put("action4", "A4");
		actions1.put("action3", "A3");
		
		Map<String,String> actions2 = new HashMap<>();
		actions2.put("action1", "A1");
		actions2.put("action2", "A2");
		
		Map<String,String> actions3 = new HashMap<>();
		actions3.put("action1", "A1");
		actions3.put("action2", "A2");

		
		Action act1 = new Action();
		act1.setActionMap(actions);
		
		Action act2 = new Action();
		act2.setActionMap(actions1);
		
		Action act3 = new Action();
		act3.setActionMap(actions2);
		
		Action act4 = new Action();
		act4.setActionMap(actions3);
		
		List<Action> l1 = new ArrayList<Action>();
		l1.add(act1);
		l1.add(act4);
		
		List<Action> l2 = new ArrayList<Action>();
		l2.add(act3);
		l2.add(act2);

		
		System.out.println(l1.containsAll(l2));*/
		
		actions1 = actions;
		
		actions1.remove("action4");
		
		System.out.println(actions.get("action4"));
		
		

	}

}
