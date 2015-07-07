package environnement.interfaces;

import java.util.List;

import trace.Action;

public interface EnvInfos {
	
	public boolean getToken(List<Action> listeActions);
	public List<String> getAllAgentsInCell(List<Action> listActions);
	
}
