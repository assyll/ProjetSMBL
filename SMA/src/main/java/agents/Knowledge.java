package agents;

import java.util.List;

import agents.impl.Child;

public class Knowledge {

	private List<String> _userNames;
	private List<Child> _childrenWithSohn;
	private List<Child> _childrenWithoutSohn;
	
	public Knowledge(List<String> userNames, List<Child> childrenWithSohn,
			List<Child> childrenWithoutSohn) {
		
		_userNames = userNames;
		_childrenWithSohn = childrenWithSohn;
		_childrenWithoutSohn = childrenWithoutSohn;
	}
	
	public List<String> getUserNames() {
		return _userNames;
	}
	
	public List<Child> getChildrenWithSohn() {
		return _childrenWithSohn;
	}
	
	public List<Child> getChildrenWithoutSohn() {
		return _childrenWithoutSohn;
	}
	
}
