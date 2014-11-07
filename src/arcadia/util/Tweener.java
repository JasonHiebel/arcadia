package arcadia.util;


public abstract class Tweener {
	protected double time;
	protected double last;
	
	public Tweener() {
		this.time = 0.;
		this.last = 1.;
	}

	public void tick(double dt) {
		time += dt;
	}
	
	public abstract double value();
	
	public boolean isComplete() {
		return time > last;
	}
}