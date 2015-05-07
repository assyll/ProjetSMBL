package jSonAndGS;

import java.util.LinkedList;
import java.util.List;

public class MyListTransitions {
	private List<MyJsonTransition> _listTransitions;

	public MyListTransitions() {
		_listTransitions = new LinkedList<MyJsonTransition>();
	}

	public void addTransition(MyJsonTransition... transitions) {
		for (MyJsonTransition transition : transitions) {
			_listTransitions.add(transition);
		}
	}

	public List<MyJsonTransition> get_listTransitions() {
		return _listTransitions;
	}

}
