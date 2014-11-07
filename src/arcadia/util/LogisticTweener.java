package arcadia.util;

public class LogisticTweener extends Tweener {

	private double a;
	private double b;

	public LogisticTweener(double a, double b) {
		this.a = a;
		this.b = b;
	}

	public double value() {
		return (b - a) / (1 + Math.pow(Math.E, 12 - 24 * time)) + a;
	}
}