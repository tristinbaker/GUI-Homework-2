import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class Homework2 extends JFrame {
	private TurtlePanel turtle;
 
	public Homework2() {
		setTitle("Homework 2");
		setSize(640, 480);
		JPanel p = new JPanel();
		add(p, BorderLayout.SOUTH);
 
		turtle = new TurtlePanel();
		add(turtle);
 
		//scale the turtle world
		turtle.setTurtleWorldSize(640, 480);
		turtle.setScaling(true);
 
		JButton btnHW = new JButton("Square Spiral");
		p.add(btnHW);
		btnHW.addActionListener(new ActionListener() {
 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawSpiral(300, 25);
			}
 
		});
		
		JButton btnSier = new JButton("Sierpinski Triangle");
		p.add(btnSier);
		btnSier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < 6; i++) {
					turtle.setpos(-100, 100);
					drawSierpinski(i, 300);
					delay(250);
				}
			}
		});  
		
		JButton btnKoch = new JButton("Koch Snowflake");
		p.add(btnKoch);
		btnKoch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawKoch(4, 300);
				turtle.left(-120);
				drawKoch(4, 300);
				turtle.left(-120);
				drawKoch(4, 300);
			}
		});  
		
		JButton btnLogo = new JButton("Logo Circle");
		p.add(btnLogo);
		btnLogo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < 36; i++) {
					circle(350, 100);
					turtle.right(10);
				}
			}
		});
	}
 
	/**
	 * drawSpiral function:
	 * Draws the square spiral required for homework.
	 * @param length - This is how big you want the spiral to be.
	 * @param times  - This is how many times you want it to spiral. 
	 * 				   ~25 times seems to be what the homework wants.
	 * 				   but 1000 makes a cool shutter effect (try it!).
	 */
	private void drawSpiral(double length, int times) {
		double size = length;
		double forwardLength = length/times;
		
		square(size);
		for(int i = 0; i < times; i++) {
			turtle.forward(forwardLength);
			size = Math.sqrt(Math.pow(size - forwardLength, 2) + Math.pow(forwardLength, 2));
			turtle.right(Math.toDegrees(Math.atan(forwardLength/size)));
			square(size);
		}
	}
 
	/**
	 * drawSierpinski function:
	 * Draws a sierpinski triangle.
	 * @param step   - This is what level of recursion you're on.
	 * @param length - This is the size of the sierpinski triangle.
	 * 				   Change this value to increase the size of the
	 * 	  	  	       main triangle.
	 */
	private void drawSierpinski(int step, int length) {
		if(step == 0) {
			turtle.forward(length);
			turtle.right(120);
			turtle.forward(length);
			turtle.right(120);
			turtle.forward(length);
			turtle.right(-240);
		} else {
			drawSierpinski(step - 1, length/2);
			turtle.forward(length/2);
			drawSierpinski(step - 1, length/2);
			turtle.backward(length/2);
			turtle.right(60);
			turtle.forward(length/2);
			turtle.left(60);
			drawSierpinski(step - 1, length/2);
			turtle.right(60);
			turtle.backward(length/2);
			turtle.left(60);
		}
	}
	
	/**
	 * drawKoch function:
	 * Draws a Koch curve.
	 * @param step   - This is what level of recursion you're on.
	 * @param length - This is how big the Koch curve will be.
	 */
	private void drawKoch(int step, int length) {
		if(step == 0) {
			turtle.forward(length);
		} else {
			drawKoch(step - 1, length/3);
			turtle.left(60);
			drawKoch(step - 1, length/3);
			turtle.right(120);
			drawKoch(step - 1, length/3);
			turtle.left(60);
			drawKoch(step - 1, length/3);
		}
	}
	private void square(double length) {
		for(int i = 0; i < 4; i++) {
			turtle.forward(length);
			turtle.right(90);
		}
	}
	
	private void circle(double size, double increments) {
		for(int i = 0; i < increments; i++) {
			turtle.forward(size/increments);
			turtle.right(360/increments);
		}
	}
	
	private void delay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
 
	public static void main(String [] args) {
		Homework2 window = new Homework2();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}