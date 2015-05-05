package generator;

public class Action {

	private String _name;
	
	public Action(String name) {
		_name = name;
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
}
