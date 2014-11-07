package arcadia.util;

public class LinearTweener extends Tweener {

	private double a;
	private double b;

	public LinearTweener(double a, double b) {
		this.a = a;
		this.b = b;
	}

	public double value() {
		double t = Math.max(0, Math.min(time, 1));
		return (b - a) * t + a;
	}
}