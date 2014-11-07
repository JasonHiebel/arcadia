package arcadia.util;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class SkyhookFont {
	private final double size;

	public SkyhookFont(int size) {
		this.size = size;
	}
	
	public Point2D draw(Graphics2D g2d, Point2D ul, String string) {
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke((float)(1f + size / 25), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
	
		AffineTransform base = new AffineTransform();
		base.scale(size / 1.5, -size / 1.5);
		base.translate(ul.getX() / (size / 1.5), -ul.getY() / (size / 1.5) - 1.5);
		
		AffineTransform curr = new AffineTransform(base);
		for(int c = 0; c < string.length(); c += 1) {
			if(glyphs.containsKey(string.charAt(c))) {
				g2d.draw(curr.createTransformedShape(glyphs.get(string.charAt(c))));
				curr.translate(1.35 + size / 10000., 0.0);
			}
			if(string.charAt(c) == '\n') {
				base.translate(0.0, -2.5);
				curr = new AffineTransform(base);
				ul.setLocation(ul.getX(), ul.getY() + 2 * size);
			}
		}
		
		g2d.setStroke(stroke);
		
		return ul;
	}
	
	public void draw(Graphics2D g2d, double x, double y, String string) {
		draw(g2d, new Point2D.Double(x, y), string);
	}
	
	
	private static Path2D.Double glyph(double[]... paths) {
		Path2D.Double glyph = new Path2D.Double();
		for(double[] path : paths) {
			glyph.moveTo(path[0], path[1]);
			
			int x = 2, y = 3;
			for(; x < path.length - 2; x += 2, y += 2) {
				glyph.lineTo(path[x], path[y]);
			}
			
			if(path[x] == path[0] && path[y] == path[1]) { glyph.closePath(); }
			else { 
				glyph.lineTo(path[x], path[y]);
			}
		}
		return glyph;
	}
	
	private static final Map<Character, Path2D.Double> glyphs = new HashMap<>();
	static {
		glyphs.put(' ', glyph());
	
		glyphs.put('!', glyph(
			new double[] {
				0.5, 1.5,
				0.5, 0.4
			},
			new double[] {
				0.5,  0.1,
				0.5, -0.1
			}
		));
		
		glyphs.put('\"', glyph(
			new double[] {
				0.3, 1.5,
				0.3, 1.0
			},
			new double[] {
				0.7, 1.5,
				0.7, 1.0
			}
		));
		
		glyphs.put('#', glyph(
			new double[] {
				0.4, 1.0,
				0.3, 0.0
			},
			new double[] {
				0.7, 1.0,
				0.6, 0.0
			},
			new double[] {
				0.0, 0.3,
				1.0, 0.3
			},
			new double[] {
				0.0, 0.7,
				1.0, 0.7
			}
		));
		
		glyphs.put('$', glyph(
			new double[] {
				0.2, 0.3,
				0.3, 0.2,
				0.7, 0.2,
				0.8, 0.3,
				0.8, 0.4,
				0.2, 0.6,
				0.2, 0.7,
				0.3, 0.8,
				0.7, 0.8,
				0.8, 0.7
			},
			new double[] {
				0.5,  1.1,
				0.5, -0.1
			}
		));
		
		glyphs.put('%', glyph(
			new double[] {
				0.0, 0.0,
				1.0, 1.0
			},
			new double[] {
				0.1, 1.0,
				0.1, 0.8
			},
			new double[] {
				0.9, 0.2,
				0.9, 0.0
			}
		));
		
		glyphs.put('&', glyph(
			new double[] {
				0.7, 0.9,
				0.6, 1.0,
				0.3, 1.0,
				0.2, 0.9,
				0.2, 0.8,
				0.8, 0.1
			},
			new double[] {
				0.3, 0.7,
				0.2, 0.6,
				0.2, 0.2,
				0.3, 0.1,
				0.6, 0.1,
				0.7, 0.2,
				0.7, 0.6
			},
			new double[] {
				0.6, 0.6,
				0.8, 0.6
			}
		));
		
		glyphs.put('\'', glyph(
			new double[] {
				0.5, 1.5,
				0.5, 1.0
			}
		));
		
		glyphs.put('(', glyph(
			new double[] {
				0.8, 1.5,
				0.5, 1.5,
				0.2, 1.1,
				0.2, 0.4,
				0.5, 0.0,
				0.8, 0.0
			}
		));
		
		glyphs.put(')', glyph(
			new double[] {
				0.2, 1.5,
				0.5, 1.5,
				0.8, 1.1,
				0.8, 0.4,
				0.5, 0.0,
				0.2, 0.0
			}
		));
		
		glyphs.put('*', glyph(
			new double[] {
				0.1, 0.2,
				0.9, 0.8
			},
			new double[] {
				0.1, 0.8,
				0.9, 0.2
			},
			new double[] {
				0.5, 0.0,
				0.5, 1.0
			}
		));
		
		glyphs.put('+', glyph(
			new double[] {
				0.0, 0.5,
				1.0, 0.5
			},
			new double[] {
				0.5, 0.0,
				0.5, 1.0
			}
		));
		
		glyphs.put(',', glyph(
			new double[] {
				0.5,  0.1,
				0.4, -0.4
			}
		));
		
		glyphs.put('-', glyph(
			new double[] {
				0.0, 0.5,
				1.0, 0.5
			}
		));
	
		glyphs.put('.', glyph(
			new double[] {
				0.5, 0.2,
				0.5, 0.0
			}
		));
		
		glyphs.put('/', glyph(
			new double[] {
				1.0, 1.0,
				0.0, 0.0
			}
		));
		
		glyphs.put('0', glyph(
			new double[] {
				0.0, 0.1,
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1,
				1.0, 0.9
			}
		));
		
		glyphs.put('1', glyph(
			new double[] {
				0.1, 0.9,
				0.2, 1.0,
				0.5, 1.0,
				0.5, 0.0
			},
			new double[] {
				0.0, 0.0,
				1.0, 0.0
			}
		));
		
		glyphs.put('2', glyph(
			new double[] {
				0.0, 0.9,
				0.1, 1.0,
				0.8, 1.0,
				0.9, 0.9,
				0.9, 0.6,
				0.0, 0.1,
				0.0, 0.0,
				1.0, 0.0
			}
		));
		
		glyphs.put('3', glyph(
			new double[] {
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.6,
				0.9, 0.5,
				0.3, 0.5
			},
			new double[] {
				0.9, 0.5,
				1.0, 0.4,
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1
			}
		));
		
		glyphs.put('4', glyph(
			new double[] {
				0.4, 1.0,
				0.0, 0.5,
				1.0, 0.5
			},
			new double[] {
				0.7,  1.0,
				0.7, -0.1
			}
		));
		
		glyphs.put('5', glyph(
			new double[] {
				0.9, 1.0,
				0.0, 1.0,
				0.0, 0.6,
				0.8, 0.6,
				0.9, 0.5,
				0.9, 0.1,
				0.8, 0.0,
				0.0, 0.0,
				0.0, 0.3
			}
		));
		
		glyphs.put('6', glyph(
			new double[] {
				1.0, 0.9,
				0.9, 1.0,
				0.1, 1.0,
				0.0, 0.9,
				0.0, 0.1,
				0.1, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 0.5,
				0.9, 0.6,
				0.0, 0.6
			}
		));
		
		glyphs.put('7', glyph(
			new double[] {
				0.0,  1.0,
				1.0,  1.0,
				0.5, -0.1,
			}
		));
		
		glyphs.put('8', glyph(
			new double[] {
				0.0, 0.1,
				0.0, 0.5,
				0.1, 0.6,
				0.9, 0.6,
				1.0, 0.5,
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1
			},
			new double[] {
				0.1, 0.6,
				0.0, 0.7,
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.7,
				0.9, 0.6
			}
		));
		
		glyphs.put('9', glyph(
			new double[] {
				0.0, 0.1,
				0.1, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 0.9,
				0.9, 1.0,
				0.1, 1.0,
				0.0, 0.9,
				0.0, 0.7,
				0.1, 0.6,
				1.0, 0.6
			}
		));
		
		glyphs.put(':', glyph(
			new double[] {
				0.5, 0.6,
				0.5, 0.4
			},
			new double[] {
				0.5,  0.1,
				0.5, -0.1
			}
		));
		
		glyphs.put(';', glyph(
			new double[] {
				0.5, 0.6,
				0.5, 0.4
			},
			new double[] {
				0.5,  0.1,
				0.4, -0.4
			}
		));
		
		glyphs.put('<', glyph(
			new double[] {
				0.9, 0.9,
				0.1, 0.5,
				0.9, 0.1
			}
		));
		
		glyphs.put('=', glyph(
			new double[] {
				0.1, 0.8,
				0.9, 0.8
			},
			new double[] {
				0.1, 0.2,
				0.9, 0.2
			}
		));
		
		glyphs.put('>', glyph(
			new double[] {
				0.1, 0.9,
				0.9, 0.5,
				0.1, 0.1
			} 
		));
		
		glyphs.put('?', glyph(
			new double[] {
				0.2, 1.4,
				0.3, 1.5,
				0.7, 1.5,
				0.8, 1.4,
				0.8, 1.0,
				0.5, 0.8,
				0.5, 0.3
			},
			new double[] {
				0.5,  0.1,
				0.5, -0.1
			}
		));
		
		glyphs.put('@', glyph(
			new double[] {
				0.7, 0.1,
				0.3, 0.1,
				0.2, 0.2,
				0.2, 0.9,
				0.3, 1.0,
				0.7, 1.0,
				0.8, 0.9,
				0.8, 0.3
			},
			new double[] {
				0.8, 0.4,
				0.7, 0.3,
				0.6, 0.3,
				0.5, 0.4,
				0.5, 0.6,
				0.6, 0.7,
				0.7, 0.7,
				0.8, 0.6
			}
		));
	
		glyphs.put('A', glyph(
			new double[] { 
				0.0, 0.0, 
				0.3, 1.5, 
				0.7, 1.5, 
				1.0, 0.0 
			},
			new double[] { 
				0.2, 0.8, 
				0.8, 0.8 
			}
		));
		
		glyphs.put('B', glyph(
			new double[] { 
				0.0, 0.0, 
				0.0, 1.5, 
				0.9, 1.5, 
				1.0, 1.4, 
				1.0, 1.0, 
				0.8, 0.8,
				0.0, 0.8,
			},
			new double[] {
				0.8, 0.8, 
				1.0, 0.7, 
				1.0, 0.4, 
				0.6, 0.0, 
				0.0, 0.0 
			}
		));
		
		glyphs.put('C', glyph(
			new double[] {
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1,
				0.0, 1.4,
				0.1, 1.5,
				0.9, 1.5,
				1.0, 1.4
			}
		));
		
		glyphs.put('D', glyph(
			new double[] {
				0.0, 0.0,
				0.0, 1.5,
				1.0, 1.5,
				1.0, 0.4,
				0.6, 0.0,
				0.0, 0.0
			}
		));
		
		glyphs.put('E', glyph(
			new double[] {
				1.0, 0.0,
				0.1, 0.0,
				0.0, 0.1,
				0.0, 1.5,
				1.0, 1.5
			},
			new double[] {
				0.0, 0.8,
				0.8, 0.8
			}
		));
		
		glyphs.put('F', glyph(
			new double[] {
				0.0, -0.1,
				0.0,  1.4,
				0.1,  1.5,
				1.0,  1.5
			},
			new double[] {
				0.0, 0.8,
				0.8, 0.8
			}
		));
		
		glyphs.put('G', glyph(
			new double[] {
				1.0, 1.4,
				0.9, 1.5,
				0.1, 1.5,
				0.0, 1.4,
				0.0, 0.1,
				0.1, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 0.8,
				0.5, 0.8
			}
		));
		
		glyphs.put('H', glyph(
			new double[] {
				0.0, -0.1,
				0.0,  1.6
			},
			new double[] {
				0.0, 0.8,
				1.0, 0.8
			},
			new double[] {
				1.0, -0.1,
				1.0,  1.6
			}
		));
		
		glyphs.put('I', glyph(
			new double[] {
				0.0, 1.5,
				1.0, 1.5
			},
			new double[] {
				0.5, 1.5,
				0.5, 0.0
			},
			new double[] {
				0.0, 0.0,
				1.0, 0.0
			}
		));
		
		glyphs.put('J', glyph(
			new double[] {
				0.5, 1.5,
				1.0, 1.5,
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1
			}
		));
		
		glyphs.put('K', glyph(
			new double[] {
				0.0, 0.0,
				0.0, 1.5
			},
			new double[] {
				0.0, 0.5,
				1.0, 1.5
			},
			new double[] {
				0.4, 0.9,
				1.0, 0.0
			}
		));
		
		glyphs.put('L', glyph(
			new double[] {
				0.0, 1.5,
				0.0, 0.1,
				0.1, 0.0,
				1.0, 0.0
			}
		));
		
		glyphs.put('M', glyph(
			new double[] {
				0.0, 0.0,
				0.0, 1.5,
				0.5, 0.7,
				1.0, 1.5,
				1.0, 0.0
			}
		));
		
		glyphs.put('N', glyph(
			new double[] {
				0.0, 0.0,
				0.0, 1.5,
				1.0, 0.0,
				1.0, 1.5
			}
		));
		
		glyphs.put('O', glyph(
			new double[] {
				0.0, 0.1,
				0.0, 1.4,
				0.1, 1.5,
				0.9, 1.5,
				1.0, 1.4,
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1
			}
		));
		
		glyphs.put('P', glyph(
			new double[] {
				0.0, 0.0,
				0.0, 1.5,
				0.9, 1.5,
				1.0, 1.4,
				1.0, 1.2,
				0.0, 0.5
			}
		));
		
		glyphs.put('Q', glyph(
			new double[] {
				0.0, 0.1,
				0.0, 1.4,
				0.1, 1.5,
				0.9, 1.5,
				1.0, 1.4,
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1
			},
			new double[] {
				0.5,  0.0,
				0.0, -0.5
			},
			new double[] {
				0.2, -0.3,
				0.9, -0.5,
				1.1, -0.3
			}
		));
		
		glyphs.put('R', glyph(
			new double[] {
				0.0, 0.0,
				0.0, 1.5,
				0.9, 1.5,
				1.0, 1.4,
				1.0, 1.2,
				0.0, 0.5
			},
			new double[] {
				0.4, 0.7,
				1.0, 0.0
			}
		));
		
		glyphs.put('S', glyph(
			new double[] {
				0.0, 0.1,
				0.1, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 0.4,
				0.0, 1.1,
				0.0, 1.4,
				0.1, 1.5,
				0.9, 1.5,
				1.0, 1.4
			}
		));
		
		glyphs.put('T', glyph(
			new double[] {
				0.0, 1.5,
				1.0, 1.5
			},
			new double[] {
				0.5, 1.5,
				0.5, 0.0
			}
		));
		
		glyphs.put('U', glyph(
			new double[] {
				0.0, 1.5,
				0.0, 0.1,
				0.1, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 1.5
			}
		));
		
		glyphs.put('V', glyph(
			new double[] {
				0.0, 1.5,
				0.4, 0.0,
				0.6, 0.0,
				1.0, 1.5
			}
		));
		
		glyphs.put('W', glyph(
			new double[] {
				0.0, 1.5,
				0.0, 0.1,
				0.1, 0.0,
				0.4, 0.0,
				0.5, 0.1,
				0.5, 0.8
			},
			new double[] {
				0.5, 0.1,
				0.6, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 1.5
			}
		));
		
		glyphs.put('X', glyph(
			new double[] {
				0.0, 1.5,
				1.0, 0.0
			},
			new double[] {
				0.0, 0.0,
				1.0, 1.5
			}
		));
		
		glyphs.put('Y', glyph(
			new double[] {
				0.0, 1.5,
				0.0, 0.9,
				0.1, 0.8,
				0.9, 0.8,
				1.0, 0.9,
				1.0, 1.5
			},
			new double[] {
				0.5, 0.8,
				0.5, 0.0
			}
		));
		
		glyphs.put('Z', glyph(
			new double[] {
				0.0, 1.5,
				1.0, 1.5,
				0.0, 0.0,
				1.0, 0.0
			}
		));
		
		glyphs.put('[', glyph(
			new double[] {
				0.7, 1.5,
				0.3, 1.5,
				0.3, 0.0,
				0.7, 0.0
			}
		));
		
		glyphs.put('\\', glyph(
			new double[] {
				0.0, 1.0,
				1.0, 0.0
			}
		));
		
		glyphs.put(']', glyph(
			new double[] {
				0.3, 1.5,
				0.7, 1.5,
				0.7, 0.0,
				0.3, 0.0
			}
		));
		
		glyphs.put('^', glyph(
			new double[] {
				0.1, 0.5,
				0.5, 1.0,
				0.9, 0.5
			}
		));
		
		glyphs.put('_', glyph(
			new double[] {
				0.1, 0.0,
				0.9, 0.0
			}
		));
		
		glyphs.put('`', glyph(
			new double[] {
				0.3, 1.5,
				0.6, 1.0
			}
		));
		
		
		glyphs.put('a', glyph(
			new double[] {
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, -0.05
			},
			new double[] {
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1,
				0.0, 0.4,
				0.1, 0.5,
				1.0, 0.5
			}
		));
		
		glyphs.put('b', glyph(
			new double[] {
				0.0, 1.5,
				0.0, 0.0
			},
			new double[] {
				0.0, 0.1,
				0.1, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 0.9,
				0.9, 1.0,
				0.1, 1.0,
				0.0, 0.9
			}
		));
		
		glyphs.put('c', glyph(
			new double[] {
				1.0, 0.9,
				0.9, 1.0,
				0.1, 1.0,
				0.0, 0.9,
				0.0, 0.1,
				0.1, 0.0,
				0.9, 0.0,
				1.0, 0.1
			}
		));
		
		glyphs.put('d', glyph(
			new double[] {
				1.0, 1.5,
				1.0, 0.0
			},
			new double[] {
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1,
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9
			}
		));
		
		glyphs.put('e', glyph(
			new double[] {
				1.0, 0.1,
				1.0, 0.0,
				0.1, 0.0,
				0.0, 0.1,
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.6,
				0.0, 0.4
			}
		));
		
		glyphs.put('f', glyph(
			new double[] {
				1.0, 1.5,
				0.6, 1.5,
				0.5, 1.4,
				0.5, 0.0
			},
			new double[] {
				0.2, 1.0,
				0.8, 1.0
			}
		));
		
		glyphs.put('g', glyph(
			new double[] {
				1.0, 1.0,
				0.1, 1.0,
				0.0, 0.9,
				0.0, 0.4,
				0.1, 0.3,
				0.9, 0.3,
				1.0, 0.4,
				1.0, 0.9,
				0.9, 1.0
			},
			new double[] {
				0.2,  0.3,
				0.0,  0.1,
				0.2, -0.1,
				0.9, -0.1,
				1.0, -0.2,
				1.0, -0.4,
				0.9, -0.5,
				0.2, -0.5,
				0.0, -0.3
			}
		));
		
		glyphs.put('h', glyph(
			new double[] {
				0.0, 1.5,
				0.0, 0.0
			},
			new double[] {
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.0
			}
		));
		
		glyphs.put('i', glyph(
			new double[] {
				0.0, 1.0,
				0.5, 1.0,
				0.5, 0.0
			},
			new double[] {
				0.0, 0.0,
				1.0, 0.0,
			},
			new double[] {
				0.5, 1.5,
				0.5, 1.3
			}
		));
		
		glyphs.put('j', glyph(
			new double[] {
				0.3,  1.0,
				0.8,  1.0,
				0.8,  0.0,
				0.3, -0.5,
				0.0, -0.5
			},
			new double[] {
				0.8, 1.5,
				0.8, 1.3
			}
		));
		
		glyphs.put('k', glyph(
			new double[] {
				0.0, 0.0,
				0.0, 1.5
			},
			new double[] {
				0.0, 0.3,
				1.0, 1.0
			},
			new double[] {
				0.5, 0.6,
				1.0, 0.0
			}
		));
		
		glyphs.put('l', glyph(
			new double[] {
				0.0, 1.5,
				0.5, 1.5,
				0.5, 0.0
			},
			new double[] {
				0.0, 0.0,
				1.0, 0.0
			}
		));
		
		glyphs.put('m', glyph(
			new double[] {
				0.0, 0.0,
				0.0, 1.0,
				0.4, 1.0,
				0.5, 0.9,
				0.5, 0.0
			},
			new double[] {
				0.5, 0.9,
				0.6, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.0
			}
		));
		
		glyphs.put('n', glyph(
			new double[] {
				0.0, 1.0,
				0.0, 0.0
			},
			new double[] {
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.0
			}
		));
		
		glyphs.put('o', glyph(
			new double[] {
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1,
				0.0, 0.9
			}
		));
		
		glyphs.put('p', glyph(
			new double[] {
				0.0,  1.0,
				0.0, -0.5
			},
			new double[] {
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1
			}
		));
		
		glyphs.put('q', glyph(
			new double[] {
				1.0,  1.0,
				1.0, -0.5
			},
			new double[] {
				1.0, 0.1,
				0.9, 0.0,
				0.1, 0.0,
				0.0, 0.1,
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9
			}
		));
		
		glyphs.put('r', glyph(
			new double[] {
				0.0, 1.0,
				0.4, 1.0,
				0.4, 0.0
			},
			new double[] {
				0.0, 0.0,
				0.8, 0.0
			},
			new double[] {
				0.4, 0.8,
				0.6, 1.0,
				0.9, 1.0,
				1.0, 0.9,
				1.0, 0.8
			}
		));
		
		glyphs.put('s', glyph(
			new double[] {
				0.0, 0.1,
				0.1, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 0.3,
				0.0, 0.7,
				0.0, 0.9,
				0.1, 1.0,
				0.9, 1.0,
				1.0, 0.9
			}
		));
		
		glyphs.put('t', glyph(
			new double[] {
				0.0, 1.0,
				1.0, 1.0
			},
			new double[] {
				0.3, 1.3,
				0.3, 0.1,
				0.4, 0.0,
				0.9, 0.0,
				1.0, 0.1
			}
		));
		
		glyphs.put('u', glyph(
			new double[] {
				0.0, 1.0,
				0.0, 0.1,
				0.1, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 1.0
			}
		));
		
		glyphs.put('v', glyph(
			new double[] {
				0.0, 1.0,
				0.4, 0.0,
				0.6, 0.0,
				1.0, 1.0
			}
		));
		
		glyphs.put('w', glyph(
			new double[] {
				0.0, 1.0,
				0.0, 0.1,
				0.1, 0.0,
				0.4, 0.0,
				0.5, 0.1,
				0.5, 0.5
			},
			new double[] {
				0.5, 0.1,
				0.6, 0.0,
				0.9, 0.0,
				1.0, 0.1,
				1.0, 1.0
			}
		));
		
		glyphs.put('x', glyph(
			new double[] {
				0.0, 0.0,
				1.0, 1.0,
			},
			new double[] {
				0.0, 1.0,
				1.0, 0.0
			}
		));
		
		glyphs.put('y', glyph(
			new double[] {
				0.00, 1.0,
				0.40, 0.0,
				0.65, 0.0
			},
			new double[] {
				1.0,  1.0,
				0.5, -0.5,
				0.2, -0.5
			}
		));
		
		glyphs.put('z', glyph(
			new double[] {
				0.0, 1.0,
				1.0, 1.0,
				0.0, 0.0,
				1.0, 0.0
			}
		));
		
		glyphs.put('{', glyph(
			new double[] {
				0.7, 1.5,
				0.5, 1.5,
				0.3, 1.3,
				0.3, 0.9,
				0.1, 0.8,
				0.3, 0.7,
				0.3, 0.2,
				0.5, 0.0,
				0.7, 0.0
			}
		));
		
		glyphs.put('|', glyph(
			new double[] {
				0.5, 1.0,
				0.5, 0.0
			}
		));
		
		glyphs.put('}', glyph(
			new double[] {
				0.3, 1.5,
				0.5, 1.5,
				0.7, 1.3,
				0.7, 0.9,
				0.9, 0.8,
				0.7, 0.7,
				0.7, 0.2,
				0.5, 0.0,
				0.3, 0.0
			}
		));
		
		glyphs.put('~', glyph(
			new double[] {
				0.0, 0.4, 
				0.2, 0.6,
				0.4, 0.6,
				0.6, 0.4,
				0.8, 0.4,
				1.0, 0.6
			}
		));
	}
}