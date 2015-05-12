package generatorTracesTestAleat;

public class Action {

	private String _name;
	
	public Action(String name) {
		_name = name;
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Action)
			? ((Action)obj).toString().equals(_name) : false;
	}
	
}
