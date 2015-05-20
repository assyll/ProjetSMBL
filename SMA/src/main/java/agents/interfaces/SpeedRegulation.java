package agents.interfaces;

public interface SpeedRegulation {
	public void pause();
	public void play();
	
	public void step();
	public void setSpeed(int speed);
	public void setFullSpeed(boolean isfullSpeed);
}