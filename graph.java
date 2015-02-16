import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;



public class graph extends JPanel  implements  MouseListener , MouseMotionListener {

	//constructor
	int scrx,scry,size=30 , mat[][] ,move[],dice[];
	lTuple stack[];
	boolean rollDice = true , showPath =false , bailout=true , isIaMove=false;
	int pathPos , iaMove;
	public graph(int scrx,int scry){
		move = new int [16];
		dice = new int [4];

		//pawns
		//red
		move[0]=1;
		move[1]=-1;
		move[2]=-1;
		move[3]=-1;
		//green
		move[4]=14;
		move[5]=-1;
		move[6]=-1;
		move[7]=-1;
		//blue
		move[8]=27;
		move[9]=-1;
		move[10]=-1;
		move[11]=-1;
		//yellow
		move[12]=40;
		move[13]=-1;
		move[14]=-1;
		move[15]=-1;



		//board
		mat =new int [15][15];

		for (int i=0;i<15;i++)
			for (int ii=0;ii<15;ii++)
				mat[i][ii]=0;

		//red green blue yellow
		//1,2 3,4   5, 6   7,8
		//red pawn 
		//10 , 11
		//..
		//50 = goal
		//51 =test 
		for (int i=0;i<6;i++)
			for (int ii=0;ii<6;ii++){
				mat[i][ii]=1;
				mat[i+9][ii]=3;
				mat[i+9][ii+9]=5;
				mat[i][ii+9]=7;
			}
		//goaL
		for (int i=0;i<3;i++)
			for (int ii=0;ii<3;ii++)
				mat[i+6][ii+6]=50;
		//goal path
		for (int i=1;i<6;i++){
			mat[i][7]=2;
			mat[7][i]=4;
			mat[8+i][7]=6;
			mat[7][8+i]=8;

		}

		//test
		//***********************************************
		//stack
		stack = new lTuple[60];
		for (int i=0;i<6;i++){

			stack[i]= new lTuple(i,6);
			stack[i+6]= new lTuple(6,5-i);
			stack[12]= new lTuple(7,0);
			//

			stack[i+13]= new lTuple(8,i);
			stack[i+19]= new lTuple(9+i,6);
			stack[25]= new lTuple(14,7);

			//
			stack[i+26]= new lTuple(14-i,8);
			stack[i+32]= new lTuple(8,9+i);
			stack[38]= new lTuple(7,14);
			//
			stack[i+39]= new lTuple(6,14-i);
			stack[i+45]= new lTuple(5-i,8);
			stack[51]= new lTuple(0,7);



		}

		//

		this.scrx=scrx-50;
		this.scry=scry-50;
		//Listeners
		addMouseMotionListener(this);
		addMouseListener(this);
		setFocusable(true);
	}


	//run


	//------------------------------------------------------------------------
	//------------------<display>--------------------------------------------
	//------------------------------------------------------------------------

	public void paint (Graphics g){

		//30*15=450+50
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		// clear the window
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0,0,scrx,scry);
		//background 
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0,0,scrx-50,scry-50);

		//squares in the board
		g2d.setColor(Color.PINK);
		for (int x=1;x<17;x++){
			g2d.drawLine(x*size, size, x*size, 450+size);
			g2d.drawLine(size,x*size, 450+size , x*size);
		}
		//filling the board
		for (int x=0;x<15;x++)
			for (int y=0;y<15;y++){

				if (mat[x][y]==1){
					g2d.setColor(Color.RED);
					g2d.fillRect((x*size)+size, (y*size)+size, size, size);
				}
				else if (mat[x][y]==2){//path
					g2d.setColor(Color.RED);
					g2d.drawRect((x*size)+size, (y*size)+size, size, size);
				}
				else if (mat[x][y]==3){
					g2d.setColor(Color.green);
					g2d.fillRect((x*size)+size, (y*size)+size, size, size);
				}
				else if (mat[x][y]==4){//path
					g2d.setColor(Color.green);
					g2d.drawRect((x*size)+size, (y*size)+size, size, size);
				}
				else if (mat[x][y]==5){
					g2d.setColor(Color.yellow);
					g2d.fillRect((x*size)+size, (y*size)+size, size, size);
				}
				else if (mat[x][y]==6){//path
					g2d.setColor(Color.yellow);
					g2d.drawRect((x*size)+size, (y*size)+size, size, size);
				}
				else if (mat[x][y]==7){
					g2d.setColor(Color.blue);
					g2d.fillRect((x*size)+size, (y*size)+size, size, size);
				}
				else if (mat[x][y]==8){//path
					g2d.setColor(Color.blue);
					g2d.drawRect((x*size)+size, (y*size)+size, size, size);
				}
				else if (mat[x][y]==50){//goal
					g2d.setColor(Color.MAGENTA);
					g2d.fillRect((x*size)+size, (y*size)+size, size, size);
				}
			}

		//display pawns in houses
		//red
		g2d.setColor(Color.BLACK);
		g2d.fillRect(50, 50, 140, 120);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(54, 54, 132, 112);
		g2d.setColor(Color.BLACK);
		g2d.drawString("n pawns ", 95,110);

		//green
		g2d.setColor(Color.BLACK);
		g2d.fillRect(320, 50, 140, 120);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(324, 54, 132, 112);
		g2d.setColor(Color.BLACK);
		g2d.drawString("n pawns ", 365,110);

		//blue
		g2d.setColor(Color.BLACK);
		g2d.fillRect(50, 330, 140, 120);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(54, 334, 132, 112);
		g2d.setColor(Color.BLACK);
		g2d.drawString("n pawns ", 95,390);

		//yellow
		g2d.setColor(Color.BLACK);
		g2d.fillRect(320, 330, 140, 120);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(324, 334, 132, 112);
		g2d.setColor(Color.BLACK);
		g2d.drawString("n pawns ", 365,390);



		//display pawns
		for (int i=0;i<16;i++){
			if (i<4)
				g2d.setColor(Color.RED);
			else if (i>=4 && i<8)
				g2d.setColor(Color.GREEN);
			if (i>=8 && i<12)
				g2d.setColor(Color.YELLOW);
			if (i>=12)
				g2d.setColor(Color.BLUE);

			if (move[i]!=-1)
				g2d.fillOval((stack[move[i]].x*size)+size, (stack[move[i]].y*size)+size, size, size);
		}


		//display dice
		g2d.setColor(Color.BLACK);
		g2d.drawString("red dice  = "+dice[0], 10,530);
		g2d.drawString("green dice  = "+dice[1], 150,530);
		g2d.drawString("yellow dice  = "+dice[2], 10,545);
		g2d.drawString("blue dice  = "+dice[3], 150,545);

		if (rollDice){

			//display dice
			g2d.setColor(Color.BLACK);
			g2d.fillRect(150, 200, 200, 120);
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(154, 204, 192, 112);
			g2d.setColor(Color.BLACK);
			g2d.drawString("red dice  = "+dice[0], 210,220);
			g2d.drawString("green dice  = "+dice[1], 210,234);
			g2d.drawString("yellow dice  = "+dice[2], 210,248);
			g2d.drawString("blue dice  = "+dice[3], 210,262);

			g2d.drawString("Move your pawn", 200,290);


			if (dice[0]==6){
				bailout=true;
				//bail out
				g2d.setColor(Color.BLACK);
				g2d.fillRect(150, 100, 200, 50);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fillRect(154, 104, 192, 42);
				g2d.setColor(Color.BLACK);
				g2d.drawString("Bailout !!!", 220,130);
			}
		}



		//ghost path
		if (showPath){
			g2d.setColor(Color.RED);
			for (int i=0;i<=dice[0];i++){
				if (move[pathPos]+dice[0]<51)
					g2d.drawOval((stack[move[pathPos]+i].x*size)+size, (stack[move[pathPos]+i].y*size)+size, size, size);
			}
		}



	}

	//------------------------------------------------------------------------

	//control class
	public void mouseClicked(MouseEvent click) {


		if (click.getX()>150 && click.getX()<350 && click.getY()>100 && click.getY()<150 && bailout){

			for (int i=0;i<4;i++)
				if (move[i]==-1){
					move[i]=1;
					rollDice=false;
					bailout=false;
					break;
				}

		}

		else{

			if (!rollDice){


				for (int i=0;i<4;i++)
					dice[i]=BGL.RndInt(6,1);
				rollDice=true;

			}else{
				/*

				 */
				System.out.println(click.getX()/size+" , " +
						click.getY()/size+" === stx "+
						(stack[move[0]].x)+
						" sty "+stack[move[0]].y +
						" move val = "+move[0]+
						" stack = "+stack[move[0]]
						);

				for (int i=0;i<4;i++){

					if (move[i]!=-1)
						if ((click.getX()/size)-1 == stack[move[i]].x  
						&& (click.getY()/size)-1 ==stack[move[i]].y){

							move[i]+=dice[0];
							if (move[i] >51)
								move[i]=0;
							//eat
							for (int iii=4;iii<16;iii++){
								if (move[i]==move[iii]){
									
									System.out.println("move "+iii+" has been eaten "+move[iii]+"");
									move[iii]=-1;
								break;
								}
							}
							
							
							break;
						}
				}
				bailout=false;
				rollDice=false;
			
				//move ia
				//moving pawns
			//ia green
				boolean quit=true;
				while (quit){
					int i=BGL.RndInt(4,4);
				
					System.out.println("green "+i+" move = "+move[i]+" dice = "+dice[1]);
					if (move[i]!=-1 ){
						move[i]+=dice[1];
						if (move[i] >51)
							move[i]-=50;
						
						//eat
						for (int iii=0;iii<16;iii++){
							if (move[i]==move[iii] && i<4 && i>7){
								
								System.out.println("move "+iii+" has been eaten "+move[iii]+"");
								move[iii]=-1;
							break;
							}
						}
						
						
						quit=false;
						System.out.println("move["+i+"]");
						break;
					}else if(dice[1]==6){
						//bail out ia
						
						for (int ii=4;ii<8;ii++)
							if (move[ii]==-1){
								move[ii]=14;
								quit=false;
								break;
							}
						
					}
				}	
			//ia yellow
				boolean quit1=true;
				while (quit1){
					int i=BGL.RndInt(4,8);
					System.out.println("yellow "+i+" move = "+move[i]+" dice = "+dice[2]);
					if (move[i]!=-1 ){
						move[i]+=dice[2];
						if (move[i] >51)
							move[i]-=50;
						quit1=false;
						//eat
						for (int iii=0;iii<16;iii++){
							if (move[i]==move[iii] && i<8 && i>11){
								
								System.out.println("move "+iii+" has been eaten "+move[iii]+"");
								move[iii]=-1;
							break;
							}
						}
						
						System.out.println("move["+i+"]");
						break;
					}else if(dice[2]==6){
						//bail out ia
						
						for (int ii=8;ii<12;ii++)
							if (move[ii]==-1){
								move[ii]=27;
								quit1=false;
								break;
							}
						
					}
				}	
				//blue
				boolean quit2=true;
				while (quit2){
					int i=BGL.RndInt(4,12);
				
					System.out.println("blue "+i+" move "+move[i]+" dice = "+dice[3]);
					if (move[i]!=-1 ){
						move[i]+=dice[3];
						if (move[i] >51)
							move[i]-=50;
						quit2=false;
						//eat
						for (int iii=0;iii<16;iii++){
							if (move[i]==move[iii] && i<12 ){
								
								System.out.println("move "+iii+" has been eaten "+move[iii]+"");
								move[iii]=-1;
							break;
							}
						}
						
						System.out.println("move["+i+"]");
						break;
					}else if(dice[3]==6){
						//bail out ia
						
						for (int ii=12;ii<16;ii++)
							if (move[ii]==-1){
								move[ii]=40;
								quit2=false;
								break;
							}
						
					}
				}	
				
				
				//
			}
		}


		repaint();


	}


	// mouse move listener
	public void mouseMoved(MouseEvent Mouve) {
		showPath=false;
		//System.out.println(Mouve.getX()/size+" , " +Mouve.getY()/size+" === stx "+(stack[move[0]].x)+" sty "+stack[move[0]].y);
		for (int i=0;i<4;i++){
			if (move[i]!=-1)			
				if ((Mouve.getX()/size)-1 == stack[move[i]].x  
				&& (Mouve.getY()/size)-1 ==stack[move[i]].y){
					pathPos=i;		
					showPath=true;
					break;
				}

		}
		repaint();	

	}

	//**************************************************
	//key pressed 
	//**************************************************




	public void mouseDragged(MouseEvent arg0){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
	public void keyReleased(KeyEvent arg0){}
	public void keyTyped(KeyEvent arg0){}
	//end
}
