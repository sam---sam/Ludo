import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class ui {
	int screenx , screeny;//set screen size 
	
	
	ui(int screenx , int screeny){
		JFrame f= new JFrame();
		JPanel panel= new JPanel();

		f.setTitle("My game title");//put the game title here
		f.setSize(screenx,screeny);
		f.setResizable(false);

		panel.setLayout(new GridLayout(1,1));
		panel.add(new graph(screenx,screeny));//graph , control , sound ... make other modules for each elems
		f.add(panel);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
	}
}
