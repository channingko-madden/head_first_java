package com.github.channingko_madden.head_first_java.chapter12;

/** @brief The Amazing, Shrinking, Blue Rectangle (pg 396) */

import javax.swing.*;
import java.awt.*;

public class Animate
{
	int x = 1;
	int y = 1;

	public static void main(String[] args)
	{
		Animate gui = new Animate();
		gui.go();
	}

	public void go()
	{
		JFrame frame = new JFrame(); // need frame for gui stuff!
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MyDrawP drawP = new MyDrawP();
		frame.getContentPane().add(drawP);
		frame.setSize(500, 270);
		frame.setVisible(true);
		
		for(int i = 0; i < 125; i++, y++, x++) // learned that you can increment multiple variables in update section of the for loop
		{
			x++; // incremting width 2x faster than height b/c it's a rectangle with width ~= 2*height
			drawP.repaint();
			try
			{
				Thread.sleep(50);
			}
			catch(Exception ex)
			{
			}
		}
	}

	class MyDrawP extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g)
		{
			g.setColor(Color.white);
			g.fillRect(0,0, 500, 270);

			g.setColor(Color.blue);
			g.fillRect(x, y, 500 - x*2, 250 - y*2);

		}
	}
}
