package environnement.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import trace.Action;
import environnement.interfaces.CellInfo;
import environnement.interfaces.CellUpdate;
import environnement.interfaces.ContextInfos;
import environnement.interfaces.EnvInfos;
import environnement.interfaces.EnvUpdate;
import environnement.interfaces.IWriteEnv;
import general.Environnement;
import general.Forward;
import generalStructure.impl.BigEcoImpl;
import generalStructure.interfaces.IInit;

public class EnvironnementImpl extends Environnement<EnvInfos, EnvUpdate>
implements EnvUpdate, EnvInfos, IInit, IWriteEnv {

	private FileWriter _writer;
	private Map<Integer,List<CellImpl>> cellsByLevel;

	public EnvironnementImpl() {
		init();
	}
	
	@Override
	protected IInit make_init() {
		return this;
	}
	
	@Override
	public void init() {
		cellsByLevel = new HashMap<>();
		this.newCell(new ArrayList<Action>());

		try {
			File file = new File("target" + File.separator + "environnement.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			_writer = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, List<CellImpl>> getCellsByLevel() {
		return cellsByLevel;
	}

	@Override
	public void writeToFile() {

		if (_writer != null && cellsByLevel != null) {
			synchronized (_writer) {
				synchronized (cellsByLevel) {
					String contenu = "------------------------\n";

					int etageMax = 0;
					for (Integer integer: cellsByLevel.keySet()) {
						if (integer > etageMax) {
							etageMax = integer;
						}
					}

					for (int i=0; i <= etageMax; i++) {
						List<CellImpl> cells;
						contenu += "{etage:"+i+"} ";
						if ((cells = cellsByLevel.get(i)) != null) {
							for (CellImpl cell: cells) {
								List<Action> actions = cell.getListOfActions();
								List<String> agents = cell.getAgentIDList();
								contenu += "{actions:";
								for (Action a: actions) {
									contenu += a + ", ";
								}
								contenu += " agents:";
								for (String agent: agents) {
									contenu += agent + ", ";
								}
								contenu += cell.hasToken ? "(o)" : "(x)";
								contenu += "} ";
							}
							contenu += "\n";
						} else {
							contenu += " ----- vide ------\n";
						}
					}

					contenu += "\n";
					try {
						_writer.write(contenu);
						_writer.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	protected Cell<EnvInfos, EnvUpdate> make_Cell(List<Action> actionList) {
		int level = actionList.size();
		CellImpl cell = new CellImpl(actionList);

		synchronized (cellsByLevel) {
			if (!cellsByLevel.containsKey(level)) {
				cellsByLevel.put(level, new ArrayList<CellImpl>());
			}
			cellsByLevel.get(level).add(cell);
		}

		//writeToFile();
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
		return new ArrayList<String> (cell.getAgentIDList());
	}

	@Override
	public void move(String id, List<Action> currentPositionActions,
			Action newAction) {



		if(id != null && newAction != null && currentPositionActions != null 
				&& !currentPositionActions.contains(newAction)) {
			CellImpl cell = getCellByActionList(currentPositionActions);

			if(cell != null &&
					cell.getAgentIDList().contains(id)) {
				List<Action> newActionList = new ArrayList<Action>();
				newActionList.addAll(currentPositionActions);
				newActionList.add(newAction);

				Lock l = new ReentrantLock();
				l.lock();
				try {
					cell.removeStateAgent(id);
					addStateAgent(id, newActionList);
				} finally {
					l.unlock();
				}

				//writeToFile();
			}
		}

	}

	@Override
	public void addStateAgent(String id) {
		if(id != null) {
			cellsByLevel.get(0).get(0).addNewStateAgent(id);
			//writeToFile();
		}
	}

	@Override
	public void addStateAgent(String id, List<Action> actions) {
		if(id != null && actions != null) {
			CellImpl cell = getCellByActionList(actions);

			if(cell != null) {
				cell.addNewStateAgent(id);
			} else {
				this.newCell(actions).cellInfos().addNewStateAgent(id);
			}
			//writeToFile();
		}
	}

	@Override
	public void removeAgent(String id, List<Action> actions) {

		if(id != null && actions != null) {
			CellImpl cell = getCellByActionList(actions);
			if(cell != null)
				cell.removeStateAgent(id);
			//writeToFile();
		}
	}

	@Override
	public void giveToken(List<Action> actions) {
		getCellByActionList(actions).giveToken();
	}
	
	@Override
	public boolean takeToken(List<Action> listeActions) {
		return getCellByActionList(listeActions).takeToken();
	}

	private CellImpl getCellByActionList(List<Action> actionList) {
		synchronized (cellsByLevel) {
			if (actionList == null || cellsByLevel.get(actionList.size()) == null) {
				return null;
			}

			for(CellImpl cell:  cellsByLevel.get(actionList.size())) {
				if(cell.getListOfActions().containsAll(actionList))
					return cell;
			}

			return null;
		}
	}

	private class CellImpl extends Cell<EnvInfos, EnvUpdate>
	implements CellInfo, CellUpdate {

		private Boolean hasToken;
		private List<Action> cellActionList;
		private List<String> agentsEtatIDList;

		public CellImpl(List<Action> actions) {
			hasToken = true;
			cellActionList = new ArrayList<>(actions);
			agentsEtatIDList = new ArrayList<String>();
		}


		@Override
		protected CellInfo make_cellInfos() {
			return this;
		}
		@Override
		public List<Action> getListOfActions() {
			return new ArrayList<Action> (cellActionList);
		}
		@Override
		public List<String> getAgentIDList() {
			return new ArrayList<String> (agentsEtatIDList);
		}

		@Override
		public void addNewStateAgent(String id) {
			agentsEtatIDList.add(id);
		}

		@Override
		public void removeStateAgent(String id) {
			agentsEtatIDList.remove(id);
		}
		
		/**
		 * Remet le jeton qu'il avait pris.
		 */
		@Override
		public void giveToken() {
			synchronized(hasToken) {
				hasToken = new Boolean(true);
			}
		}

		@Override
		protected CellUpdate make_cellUpdate() {
			return this;
		}

		/**
		 * Prend le jeton s'il y est.
		 */
		@Override
		public boolean takeToken() {
			synchronized (hasToken) {
				boolean hadToken = hasToken;
				hasToken = new Boolean(false);
				return hadToken;
			}
		}

	}

	@Override
	protected IWriteEnv make_writeEnv() {
		return this;
	}

}
