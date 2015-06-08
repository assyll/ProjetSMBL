package environnement.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trace.Action;
import environnement.interfaces.CellInfo;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import general.Environnement;
import general.Forward;
import generalStructure.impl.BigEcoImpl;

public class EnvironnementImpl extends Environnement<EnvInfos, EnvUpdate>
		implements EnvUpdate, EnvInfos{

	private Map<Integer,List<CellImpl>> cellsByLevel = new HashMap<>();
	
	public EnvironnementImpl() {
		this.newCell(new ArrayList<Action>());
	}
	

	@Override
	protected Cell<EnvInfos, EnvUpdate> make_Cell(List<Action> actionList) {
		int level = actionList.size();
		CellImpl cell = new CellImpl(actionList);


		if (!cellsByLevel.containsKey(level)) {
			cellsByLevel.put(level, new ArrayList<CellImpl>());
		}
		
		cellsByLevel.get(level).add(cell);

		return cell;
	}

	@Override
	protected EnvInfos make_envInfos() {
		return this;
	}


	@Override
	protected EnvUpdate make_envUpdate() {
		return this;
	}
	
	@Override
	public List<String> getAllAgentsInCell(List<Action> listActions) {
		CellImpl cell = getCellByActionList(listActions);
		return cell.getAgentIDList();
	}


	@Override
	public void move(String id, List<Action> currentPositionActions,
			Action newAction) {
		CellImpl cell = getCellByActionList(currentPositionActions);
		cell.removeStateAgent(id);
		Map<Action,CellImpl> childrenMap = cell.getChildrenMap();
		
		if(childrenMap.containsKey(newAction)){
			childrenMap.get(newAction).addNewStateAgent(id);
		} else {
			List<Action> newListOfActions = new ArrayList<Action>(currentPositionActions);
			newListOfActions.add(newAction);
			CellImpl destionationCell = getCellByActionList(newListOfActions);
			
			if(destionationCell != null){
				cell.updateChildMap(newAction, destionationCell);
			} else {
				destionationCell = cell.addChild(newAction);
			}
			
			destionationCell.addNewStateAgent(id);	
		}
		
	}


	@Override
	public void addStateAgent(String id) {
		this.newCell(new ArrayList<Action>()).cellInfos().addNewStateAgent(id);
	}


	@Override
	public void addStateAgent(String id, List<Action> actions) {
		CellImpl cell = getCellByActionList(actions);
		
		if(cell != null) {
			cell.addNewStateAgent(id);
		} else {
			this.newCell(actions).cellInfos().addNewStateAgent(id);
		}
	}


	@Override
	public void removeAgent(String id, List<Action> actions) {
		CellImpl cell = getCellByActionList(actions);
		if(cell != null)
			cell.removeStateAgent(id);
	}
	
	private CellImpl getCellByActionList(List<Action> actionList) {
		
		for(CellImpl cell:  cellsByLevel.get(actionList.size())){
			if(cell.getListOfActions().containsAll(actionList))
				return cell;
		}
		
		return null;
	}

	

	private class CellImpl extends Cell<EnvInfos, EnvUpdate> implements CellInfo {

		private List<Action> cellActionList;
		private Map<Action, CellImpl> childrenMap;
		private List<String> agentsEtatIDList;
		
		public CellImpl(List<Action> actions) {
			cellActionList = new ArrayList<>(actions);
			childrenMap = new HashMap<>();
			agentsEtatIDList = new ArrayList<String>();
			
		}
		
		
		public void updateChildMap(Action action, CellImpl cell){
			childrenMap.put(action, cell);
		}
		
		public CellImpl addChild(Action action)
		{
			List<Action> newListOfActions = new ArrayList<Action>(cellActionList);
			newListOfActions.add(action);
			
			CellImpl child = new CellImpl(newListOfActions);
			childrenMap.put(action, child);
			return child;
		}
		
		@Override
		protected CellInfo make_cellInfos() {
			return this;
		}
		@Override
		public List<Action> getListOfActions() {
			return cellActionList;
		}
		@Override
		public List<String> getAgentIDList() {
			return agentsEtatIDList;
		}

		@Override
		public void addNewStateAgent(String id) {
			agentsEtatIDList.add(id);
			
		}
		
		public  Map<Action, CellImpl> getChildrenMap(){
			return childrenMap;
		}
		@Override
		public void removeStateAgent(String id) {
			agentsEtatIDList.remove(id);
		}

	}

}
