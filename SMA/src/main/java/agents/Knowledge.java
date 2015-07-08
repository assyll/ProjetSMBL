package agents;

import java.util.List;
import java.util.Map;

import agents.impl.Child;

public class Knowledge {

	private List<String> _userNames;
	private List<Child> _childrenWithSohn;
	private List<Child> _childrenWithoutSohn;
	private Map<String, String> _fatherIdList;
	
	public Knowledge(List<String> userNames, List<Child> childrenWithSohn,
			List<Child> childrenWithoutSohn, Map<String, String> fatherIdList) {
		
		_userNames = userNames;
		_fatherIdList = fatherIdList;
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
	
	public Map<String, String> getFatherIdList() {
		return _fatherIdList;
	}
	
}
