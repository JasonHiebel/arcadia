package arcadia;

import java.awt.*;
import java.awt.geom.*;

public enum Button {
	U(new double[] { 0.5, 0.9, 0.4, 0.5, 0.1, 0.5, 0.5, 0.1, 0.9, 0.5, 0.6, 0.5, 0.5, 0.9 }),	
	D(new double[] { 0.5, 0.1, 0.4, 0.5, 0.1, 0.5, 0.5, 0.9, 0.9, 0.5, 0.6, 0.5, 0.5, 0.1 }),
	L(new double[] { 0.9, 0.5, 0.5, 0.4, 0.5, 0.1, 0.1, 0.5, 0.5, 0.9, 0.5, 0.6, 0.9, 0.5 }),
	R(new double[] { 0.1, 0.5, 0.5, 0.4, 0.5, 0.1, 0.9, 0.5, 0.5, 0.9, 0.5, 0.6, 0.1, 0.5 }),
	A(new double[] { 0.2, 0.8, 0.4, 0.2, 0.6, 0.2, 0.8, 0.8 }, new double[] { 0.3, 0.5, 0.7, 0.5 }),
	B(new double[] { 0.2, 0.2, 0.2, 0.8, 0.7, 0.8, 0.8, 0.7, 0.8, 0.6, 0.7, 0.5, 0.8, 0.4, 0.8, 0.3, 0.7, 0.2, 0.2, 0.2 }, new double[] { 0.2, 0.5, 0.7, 0.5 }),
	C(new double[] { 0.8, 0.7, 0.7, 0.8, 0.3, 0.8, 0.2, 0.7, 0.2, 0.3, 0.3, 0.2, 0.7, 0.2, 0.8, 0.3 }),
	S(new double[] { 0.8, 0.3, 0.7, 0.2, 0.3, 0.2, 0.2, 0.3, 0.2, 0.4, 0.8, 0.6, 0.8, 0.7, 0.7, 0.8, 0.3, 0.8, 0.2, 0.7 });
	
	private static final RoundRectangle2D outline = new RoundRectangle2D.Double(0.0, 0.0, 1.0, 1.0, 0.3, 0.3); 
	private Path2D.Double glyph;
	
	private Button(double[]... paths) {
		glyph = new Path2D.Double();
		for(double[] path : paths) {
			glyph.moveTo(path[0], path[1]);
			
			int x = 2, y = 3;
			for(; x < path.length - 2; x += 2, y += 2) {
				glyph.lineTo(path[x], path[y]);
			}
			
			if(path[x] == path[0] && path[y] == path[1]) { glyph.closePath(); }
			else { glyph.lineTo(path[x], path[y]); }
		}
	}
	
	public void draw(Graphics2D g2d, double x, double y, double w, double h) {
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
	
		AffineTransform base = new AffineTransform();
		base.scale(w, h);
		base.translate(x / w, y / h);
		
		g2d.draw(base.createTransformedShape(glyph));
		g2d.draw(base.createTransformedShape(outline));
		
		g2d.setStroke(stroke);
	}
}