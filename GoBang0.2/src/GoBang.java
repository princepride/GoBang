import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class GoBang extends JFrame{

	static int[][] graphMatrix=new int[15][15];
	static WinStep[] wayOfWin=new WinStep[572];
	static int computerScore[][]=new int[15][15];
	static int myScore[][]=new int[15][15];
	static Step[] memory=new Step[225];
	static int stepNum=0;
	static boolean turn=false;
	static boolean isEnd=false;
	static String whoWin="";
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		for(int i=0;i<15;i++)
			g.drawLine(100+i*40, 100, 100+i*40, 660);
		for(int i=0;i<15;i++)
			g.drawLine(100, 100+i*40, 660, 100+i*40);
		for(int i=0;i<15;i++)
			for (int j=0;j<15;j++) {
				if(graphMatrix[i][j]==1) {
					Color color=g.getColor();
					g.setColor(Color.black);
					g.fillOval(85+40*j, 85+40*i, 30, 30);
					g.setColor(color);
				}
				else if(graphMatrix[i][j]==2) {
					if(memory[stepNum-1].x==j&&memory[stepNum-1].y==i) {
						Color color=g.getColor();
						g.setColor(Color.white);
						g.fillOval(85+40*j, 85+40*i, 30, 30);
						g.setColor(color);
						g.drawOval(85+40*j-1, 85+40*i-1, 30+2, 30+2);
						g.drawRect(85+40*j-1, 85+40*i-1, 30+2, 30+2);
					}
					else {
						Color color=g.getColor();
						g.setColor(Color.white);
						g.fillOval(85+40*j, 85+40*i, 30, 30);
						g.setColor(color);
						g.drawOval(85+40*j-1, 85+40*i-1, 30+2, 30+2);
					}
				}
			}
		if(isEnd) {
			Font font=g.getFont();
			g.setFont(new Font("alias", Font.PLAIN, 50));
			g.drawString("WINNER IS "+whoWin, 670, 400);
			g.setFont(font);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GoBang goBang=new GoBang();
		goBang.launch();
	}
	
	public void launch() {
		setBounds(0, 0, 1200, 800);
		setVisible(true);
		createWayOfWin();
/*		for(int i=0;i<572;i++) {
			for(int j=0;j<5;j++) {
				System.out.println(wayOfWin[i].steps[j].x+" "+wayOfWin[i].steps[j].y);
			}
		}*/
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				super.windowClosing(arg0);
				setVisible(false);
				System.exit(0);
			}
			
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
					if(stepNum>0) {
						stepNum--;
						graphMatrix[memory[stepNum].y][memory[stepNum].x]=0;
						turn=!turn;
						repaint();
					}
				}
			}
			
		});
		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if(!isEnd) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				int x=e.getX();
				int y=e.getY();
				int i;
				int j;
				if(x<=670&&x>=90&&y<=670&&y>=90) {
					int k1=(x-90)%40;
					int k2=(y-90)%40;
					if(k1>=0&&k1<=20&&k2>=0&&k2<=20) {
						j=(x-90)/40;
						i=(y-90)/40;
						if(turn==false&&graphMatrix[i][j]==0) {
							graphMatrix[i][j]=1;
							turn=true;
							memory[stepNum]=new Step(j, i);
//							System.out.println(j+" "+i+" "+graphMatrix[i][j]);
							stepNum++;
							if(isWin(i, j, 1)) {
//								System.out.print("black win");
								whoWin="BLACK";
								isEnd=true;
							}
						}
						
						if(turn==true&&isEnd==false) {
							Step step=getNextStep(turn);
							graphMatrix[step.y][step.x]=2;
							turn=false;
							memory[stepNum]=step;
//							System.out.println(step.x+" "+step.y+" "+graphMatrix[step.y][step.x]);
							stepNum++;
							if(isWin(step.y, step.x, 2)) {
//								System.out.print("red win");
								whoWin="White";
								isEnd=true;
							}
						}
/*						else if(turn==true&&graphMatrix[i][j]==0) {
							graphMatrix[i][j]=2;
							turn=false;
							memory[stepNum]=new Step(j, i);
							stepNum++;
							if(isWin(i, j, 2)) {
								System.out.print("red win");
								whoWin="red";
								isEnd=true;
							}
						}*/
						
					}
				}
				repaint();
			}
			}
		});
	}

	public void createWayOfWin() {
		int count=0;
		for (int i = 0; i < 15; i++){
		    for (int j = 0; j < 11; j++){
	        	wayOfWin[count]=new WinStep();
		        for (int k = 0; k < 5; k++){
		        	wayOfWin[count].addStep(j+k, i);
//		        	System.out.println(p);
//		            wayOfWin[i][j+k][count] = true;
		        }
		        count++;
		    }
		}
		for (int i = 0; i < 11; i++){
		    for (int j = 0; j < 15; j++){
	        	wayOfWin[count]=new WinStep();
		        for (int k = 0; k < 5; k++){
		        	wayOfWin[count].addStep(j, i+k);
//		            wayOfWin[i+k][j][count] = true;
		        }
		        count++;
		    }
		}
		for (int i = 0; i < 11; i++){
		    for (int j = 0; j < 11; j++){
	        	wayOfWin[count]=new WinStep();
		        for (int k = 0; k < 5; k++){
		        	wayOfWin[count].addStep(j+k, i+k);
//		            wayOfWin[i+k][j+k][count] = true;
		        }
		        count++;
		    }
		}
		for (int i = 0; i < 11; i++){
		    for (int j = 14; j > 3; j--){
	        	wayOfWin[count]=new WinStep();
		        for (int k = 0; k < 5; k++){
		        	wayOfWin[count].addStep(j-k, i+k);
//		            wayOfWin[i+k][j-k][count] = true;
		        }
		        count++;
		    }
		}
	}

	public Step getNextStep(boolean turn) {
		int max=1;
		int u=7;
		int v=7;
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				myScore[i][j]=0;
				computerScore[i][j]=0;
				if(graphMatrix[i][j]==0) {
					for(int k=0;k<572;k++) {
						int num=wayOfWin[k].matchSteps(!turn,j,i);
						if(num==1) {
							myScore[i][j]+=320;
						}else if(num==2) {
							myScore[i][j]+=420;
						}else if(num==3) {
							myScore[i][j]+=4200;
						}else if(num==4) {
							myScore[i][j]+=20000;
						}
						num=wayOfWin[k].matchSteps(turn, j, i);
						if(num==1) {
							computerScore[i][j]+=200;
						}else if(num==2) {
							computerScore[i][j]+=400;
						}else if(num==3) {
							computerScore[i][j]+=2000;
						}else if(num==4) {
							computerScore[i][j]+=10000;
						}
					}
					if(myScore[i][j]>max) {
						max=myScore[i][j];
						u=i;
						v=j;
					}
					else if(myScore[i][j]==max) {
						if(computerScore[i][j]>computerScore[u][v]) {
							u=i;
							v=j;
						}
					}
					if(computerScore[i][j]>max) {
						max=computerScore[i][j];
						u=i;
						v=j;
					}
					else if(computerScore[i][j]==max) {
						if(myScore[i][j]>myScore[u][v]) {
							u=i;
							v=j;
						}
					}
				}
			}
		}
//		System.out.println(u+" "+v+" "+myScore[u][v]+" "+computerScore[u][v]);
		return new Step(v, u);
	}
 	public boolean isWin(int i,int j,int n) {
		int xbegin=j;
		boolean xy=false;
		for(int t=0;t<4;t++) {
			if(xbegin>0)
				xbegin--;
		}
		int ybegin=i;
		for(int t=0;t<4;t++) {
			if(ybegin>0)
				ybegin--;
		}
		int xend=j;
		for(int t=0;t<4;t++) {
			if(xend<14)
				xend++;
		}
		int yend=i;
		for(int t=0;t<4;t++) {
			if(yend<14)
				yend++;
		}
		int xdbegin=j;
		int ydbegin=i;
		int xdend=j;
		int ydend=i;
		for(int t=0;t<4;t++) {
			if(xdbegin>0&&ydbegin>0) {
				xdbegin--;
				ydbegin--;
			}
		}
		for(int t=0;t<4;t++) {
			if(xdend<14&&ydend<14) {
				xdend++;
				ydend++;
			}
		}
		for(int p=xdbegin,q=ydbegin;p+4<=xdend&&q+4<=ydend;p++,q++) {
			if(graphMatrix[q][p]==n&&graphMatrix[q+1][p+1]==n&&graphMatrix[q+2][p+2]==n&&
					graphMatrix[q+3][p+3]==n&&graphMatrix[q+4][p+4]==n) {
				xy=true;
				break;
			}
		}
		if(xy==false) {
			xdbegin=j;
			ydbegin=i;
			xdend=j;
			ydend=i;
			for(int t=0;t<4;t++) {
				if(xdbegin>0&&ydbegin<14) {
					xdbegin--;
					ydbegin++;
				}
			}
			for(int t=0;t<4;t++) {
				if(xdend<14&&ydend>0) {
					xdend++;
					ydend--;
				}
			}
			for(int p=xbegin,q=ydbegin;p+4<=xdend&&q-4>=ydend;p++,q--) {
				if(graphMatrix[q][p]==n&&graphMatrix[q-1][p+1]==n&&graphMatrix[q-2][p+2]==n&&
						graphMatrix[q-3][p+3]==n&&graphMatrix[q-4][p+4]==n) {
					xy=true;
					break;
				}
			}
		}
		if(xy==false) {
			for(int p=xbegin;p+4<=xend;p++) {
				if(graphMatrix[i][p]==n&&graphMatrix[i][p+1]==n&&graphMatrix[i][p+2]==n&&
					graphMatrix[i][p+3]==n&&graphMatrix[i][p+4]==n) {
					xy=true;
					break;
				}
			}
		}
		if(xy==false) {
			for(int p=ybegin;p+4<=yend;p++) {
				if(graphMatrix[p][j]==n&&graphMatrix[p+1][j]==n&&graphMatrix[p+2][j]==n&&
					graphMatrix[p+3][j]==n&&graphMatrix[p+4][j]==n) {
					xy=true;
					break;
				}
			}
		}
//		System.out.println(xbegin+" "+ybegin+" "+xend+" "+yend);
		return xy;
	}

	class Step{
		int x;
		int y;
		public Step(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}	
	}

	class WinStep{
		Step[] steps=new Step[5];
		int i=0;
		void addStep(int x,int y) {
			steps[i]=new Step(x, y);
			i++;
		}
		boolean isStepExist(int x,int y) {
			boolean isExist=false;
			for(int j=0;j<5;j++) {
				if(steps[j].x==x&&steps[j].y==y) {
					isExist=true;
					break;
				}
			}
			return isExist;
		}
		int matchSteps(boolean turn,int x,int y) {
			int matchs=0;
			if(isStepExist(x, y)) {
			for(int j=0;j<5;j++) {
				if(turn==true) {
						if(graphMatrix[steps[j].y][steps[j].x]==2) {
						matchs++;
					}
						else if(graphMatrix[steps[j].y][steps[j].x]==1) {
							matchs=0;
							break;
						}
				}
				else if(turn==false) {
					if(graphMatrix[steps[j].y][steps[j].x]==1) {
						matchs++;
					}
					else if(graphMatrix[steps[j].y][steps[j].x]==2) {
						matchs=0;
						break;
					}
				}
			}
		}
			return matchs;
		}
	}
}
