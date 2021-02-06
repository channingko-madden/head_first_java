/** @brief Playing around creating a widget
 *  Pass a CLI argument of 1, 2, 3, or 4 to display
 *  a rectange, jpeg, circle, gradient circle */

import java.awt.*;
import javax.swing.*;

public class SimpleWidget extends JPanel
{
	private int m_cli_arg;

	public static void main(String[] args)
	{
		if(args.length < 1)
		{
			System.out.println("Don't forget to choose what to paint");
		}
		else
		{
			try
			{
				SimpleWidget widget = new SimpleWidget(Integer.parseInt(args[0]));
				JFrame frame = new JFrame();

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(widget);
				frame.setSize(300, 300);
				frame.setVisible(true);
			}
			catch (NumberFormatException exp)
			{
				System.out.println("INPUT ERROR" + exp.toString());
			}
		}
		
	}

	SimpleWidget(int cli_arg)
	{
		m_cli_arg = cli_arg;
	}

	public void paintComponent(Graphics g)
	{
		if(m_cli_arg == 1)
		{
			g.setColor(Color.orange);
			g.fillRect(20, 50, 100, 100);
		}
		else if(m_cli_arg == 2)
		{
			Image image = new ImageIcon("michalka.jpg").getImage();
			g.drawImage(image, 3, 4, this);
		}
		else if(m_cli_arg == 3)
		{
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			int red = (int) (Math.random() * 256);
			int green = (int) (Math.random() * 256);
			int blue = (int) (Math.random() * 256);
			Color randomColor = new Color(red, green, blue);
			g.setColor(randomColor);
			g.fillOval(70, 70, 100, 100);
		}
		else
		{
			Graphics2D g2d = (Graphics2D) g;
			int red = (int) (Math.random() * 256);
			int green = (int) (Math.random() * 256);
			int blue = (int) (Math.random() * 256);
			Color startColor = new Color(red, green, blue);

			red = (int) (Math.random() * 256);
			green = (int) (Math.random() * 256);
			blue = (int) (Math.random() * 256);
			Color endColor = new Color(red, green, blue);

			GradientPaint gradient = new GradientPaint(70, 70, startColor, 150, 150, endColor);
			g2d.setPaint(gradient);
			g2d.fillOval(70, 70, 100, 100);
		}
	}

	
}
