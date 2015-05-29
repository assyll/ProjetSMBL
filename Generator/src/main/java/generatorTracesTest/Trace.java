package generatorTracesTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Trace {
	
	public static Map<String, Date> usernames =
			new HashMap<String, Date>();
	public static Map<String, Integer> tracesByUser =
			new HashMap<String, Integer>();
	public static int nbTracesByUser;
	
	public final static String _end = "END";

	private List<Action> _actions;
	
	public Trace() {
		_actions = new ArrayList<Action>();
	}
	
	public void addAction(Action action) {
		_actions.add(action);
	}
	
	public List<Action> getActions() {
		return _actions;
	}
	
	public void addProprietes() {
		String username = chooseUsernameAleat();
		Date date = usernames.get(username);
		
		for (Action action: _actions) {
			// incremente la date entre 1 et 60 secondes
			date.setTime(date.getTime() +
					(1000 * (new Random().nextInt(60) + 1)));
			
			action.addProprietes("userName", username);
			action.addProprietes("timeStamp", getDateFormatString(date));
		}
	}
	
	/**
	 * Choisis un username de facon aleatoire.
	 * Soit il en prend un deja existant,
	 * soit il en cree un nouveau.
	 */
	public static String chooseUsernameAleat() {
		String username;
		if (usernames.isEmpty()) {
			// oblige creer nouveau username
			usernames.put(username = "user0", new Date());
			tracesByUser.put(username, 1);
		} else {
			// verifie dabord si tous les users ont nbTracesByUser traces
			username = getUsernameEnManqueDeTraces();
			if (username != null) {
				tracesByUser.put(username, tracesByUser.get(username) + 1);
				return username;
			}
			
			// sinon tirer au sort sil faut en creer
			//int nbAleat = new Random().nextInt(2);
			//if (nbAleat < 1) {
				// creer un nouveau username
				usernames.put(
						username = "user" + usernames.size(), new Date());
				tracesByUser.put(username, 1);
			/*} else {
				// prend un username existant au hasard
				List<String> usernamesList = new ArrayList<String>();
				usernamesList.addAll(usernames.keySet());
				
				nbAleat = new Random().nextInt(usernamesList.size());
				username = usernamesList.get(nbAleat);
				tracesByUser.put(username, tracesByUser.get(username) + 1);
			}*/
		}
		return username;
	}
	
	/**
	 * Retourne un utilisateur qui n'a pas encore le nombre de traces voulues.
	 * Retourne null si tous les utilisateurs l'ont.
	 * @return username
	 */
	private static String getUsernameEnManqueDeTraces() {
		for (Entry<String, Integer> e: tracesByUser.entrySet()) {
			if (e.getValue() < nbTracesByUser) {
				return e.getKey();
			}
		}
		return null;
	}
	
	public String getDateFormatString(Date date) {
		Timestamp timestamp = new Timestamp(date.getTime());
		DateFormat dateFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a"); 
		return dateFormat.format(timestamp);
	}
	
	@Override
	public String toString() {
		String string = "";
		for (Action action: _actions) {
			string += (action != null) ? action + " -> " : "";
		}
		return string += _end;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Trace)
				? toString().equals(((Trace)obj).toString()) : false;
	}

	public static boolean isKeyAttributBL(String attributKey) {
		List<String> attributsBL = new ArrayList<String>();
		attributsBL.add("agentName");
		attributsBL.add("softwareName");
		attributsBL.add("softwareRelease");
		attributsBL.add("softwareVersion");
		attributsBL.add("userName");
		attributsBL.add("event");
		attributsBL.add("action");
		attributsBL.add("actionTarget");
		attributsBL.add("actionTargetClass");
		attributsBL.add("actionDetail");
		attributsBL.add("data");
		attributsBL.add("timeStamp");
		attributsBL.add("codePhase");
		attributsBL.add("moduleName");
		attributsBL.add("title");
		attributsBL.add("id");
		attributsBL.add("text");
		
		try {
			return attributsBL.contains(attributKey);
		} catch (Exception e) {
			return false;
		}
	}
	
}
