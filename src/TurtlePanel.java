import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
 
public class TurtlePanel extends JPanel {
	//turtle position
	private double x;
	private double y;
	private double angle;
	private LinkedList<TurtleLine> lines;
	private boolean scaleMode;
	private double tw;
	private double th;
	private boolean penDown;
	private Color color;
	private double speed;
	private static final double FPS = 24.0;
 
	public TurtlePanel() {
		x=-100;
		y=100;
		angle=0.0;
 
		lines = new LinkedList<TurtleLine>();
		scaleMode = false;
		penDown = true;
 
		speed = 50;
	}
 
 
	public void forward(double length) {
		double ex, ey;
 
		ex = x + length * Math.cos(Math.toRadians(angle));
		ey = y + length * Math.sin(Math.toRadians(angle));
 
		setpos(ex, ey);
	}
 
 
	public void backward(double length) {
		double ex, ey;
 
		ex = x - length * Math.cos(Math.toRadians(angle));
		ey = y - length * Math.sin(Math.toRadians(angle));
 
		setpos(ex, ey);
	}
 
 
	public void setpos(double x, double y) {
		if (penDown) {
			// create the line
			Point start = new Point();
			Point end = new Point();
			start.setLocation(this.x, this.y);
			end.setLocation(x, y);
			TurtleLine l = new TurtleLine(start, end, color);
 
			// add and draw
			lines.add(l);
			l.draw((Graphics2D) getGraphics());
		}
 
		//update the turtle position
		this.x = x;
		this.y = y;
	}
 
 
	public void left(double a) {
		angle += a;
		correctAngle();
	}
 
	public void right(double a) {
		angle -= a;
		correctAngle();
	}
 
	public void penup() {
		penDown = false;
	}
 
	public void pendown() {
		penDown = true;
	}
 
 
	public void setpencolor(Color color) {
		this.color = color;
	}
 
	public void setScaling(boolean scaleMode) {
		this.scaleMode = scaleMode;
		repaint();  //the animators best friend!
	}
 
 
	public void setTurtleWorldSize(double tw, double th) {
		this.tw = tw;
		this.th = th;
		repaint();
	}
 
 
	public void setSpeed(double speed) {
		this.speed = speed;
	}
 
	private void correctAngle() {
		if(angle > 360) {
			angle %= 360;
		} 
 
		while(angle < 0) {
			angle += 360; 
		}
	}
 
 
 
 
	@Override
	public void paint(Graphics gc) {
		super.paint(gc);
 
		//repaint our lines
		Graphics2D g = (Graphics2D) gc;
		for(TurtleLine l : lines) {
			l.draw(g);
		}
	}
 
 
 
 
	/**
	 * A class which represents a turtle line.
	 * Points are in turtle space
	 * @author robert.lowe
	 *
	 */
	private class TurtleLine {
		private Point start;
		private Point end;
		private Color color;
 
		public TurtleLine(Point start, Point end, Color color) {
			this.start = start;
			this.end = end;
			this.color = color;
		}
 
		public void draw(Graphics2D g) {
			int x, y, ex, ey;
 
			//round off our pixels for the starting point
			Point p = screenPoint(start);
			x = (int) Math.round(p.getX());
			y = (int) Math.round(p.getY());
 
			//round off our pixels for the ending point
			p = screenPoint(end);
			ex = (int) Math.round(p.getX());
			ey = (int) Math.round(p.getY());
 
			//draw the line
			g.setColor(color);
			g.drawLine(x, y, ex, ey);
		}
 
 
		public void drawAnimated(Graphics2D g) {
 
		}
 
 
		/**
		 * Translate a point from turtle space to pixel space
		 * @param p point to translate
		 * @return translated point
		 */
		private Point screenPoint(Point p) {
			double w = getWidth();
			double h = getHeight();
			Point result = new Point();
 
			if(scaleMode) {
				//perform scaling
				double x;
				double y;
 
				//scale
				x = (w/tw) * p.getX();
				y = (h/th) * p.getY();
 
				//tranlsate
				x += w/2.0;
				y = h/2.0 - y;
 
				result.setLocation(x, y);
			} else {
				result.setLocation(w/2.0 + p.getX(), h/2.0 - p.getY());
			}
			return result;
		}
	}
}