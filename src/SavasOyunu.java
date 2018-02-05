import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SavasOyunu extends JPanel implements ActionListener,KeyListener {
	
	class DusmanGemi {

		private int dusmanX,dusmanY;
		public int getDusmanX() {
			return dusmanX;
		}
		public int getDusmanY() {
			return dusmanY;
		}
		public void setDusmanX(int dusmanX) {
			this.dusmanX = dusmanX;
		}
		public void setDusmanY(int dusmanY) {
			this.dusmanY = dusmanY;
		}
		public DusmanGemi(int dusmanX) {
			super();
			this.dusmanX = dusmanX;
			this.dusmanY = 0;
		}
		
		

	}
	
	class Ates{
		private int x,y;

		public Ates(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
		
	}
	
	public boolean carpmaKontrol() {
		for(DusmanGemi gemi:gemiler) {
			if(new Rectangle(uzayGemisiX,uzayGemisiY+10,60,60).intersects(new Rectangle(gemi.getDusmanX(),gemi.getDusmanY(),40,40))) {
				gemiler.remove(gemi);
				return true;
			}
		}
		return false;
	}
	
	public void atesKontrol() {
		for(Ates ates:atesler) {
			for(DusmanGemi gemi:gemiler) {
				if(new Rectangle(ates.getX(),ates.getY(),20,20).intersects(new Rectangle(gemi.getDusmanX(), gemi.getDusmanY(), 30, 30))) {
					gemiler.remove(gemi);
					atesler.remove(ates);
					vurulan_gemi++;
				}
			}
		}
	}
	
	Timer timer=new Timer(10, this);
	private BufferedImage uzayGemisi,dusmanGemisi;
	Random rand=new Random();
	private int uzayGemisiX=370,uzayGemisiY=500;
	private int gemiDirX=20,gemiDirY=20;
	private int atesDirY=1;
	private int dusmanDirY=1;
	private ArrayList<Ates> atesler=new ArrayList<Ates>();
	private ArrayList<DusmanGemi> gemiler=new ArrayList<DusmanGemi>();
	private int vurulan_gemi=0;
	private int can=3,canDirX=30,canX=10;
	
	public SavasOyunu() {
		
		try {
			uzayGemisi=ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
			dusmanGemisi=ImageIO.read(new FileImageInputStream(new File("dusmangemisi.png")));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		timer.start();
		
		setBackground(Color.BLACK);
		
		gemiler.add(new DusmanGemi(rand.nextInt(500)));
		gemiler.add(new DusmanGemi(rand.nextInt(500)));
		gemiler.add(new DusmanGemi(rand.nextInt(500)));
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if(gemiler.size()<8) {
			gemiler.add(new DusmanGemi(rand.nextInt(500)));
		}
	
		g.drawImage(uzayGemisi, uzayGemisiX, uzayGemisiY, 70, 70, this);	
		
		g.setColor(Color.WHITE);
		
		g.drawString("Can = "+can, 10, 20);
		
		g.drawString("Vurulan Gemi Sayýsý = "+vurulan_gemi, 10, 40);
		
		for(DusmanGemi gemi:gemiler) {
			g.drawImage(dusmanGemisi, gemi.getDusmanX(), gemi.getDusmanY(), 40, 40, this);
		}
		
		g.setColor(Color.RED);
		for(Ates ates:atesler) {
			g.fillOval(ates.getX(), ates.getY(), 20, 20);
		}
		
		for(Ates ates:atesler) {
			if(ates.getY()<0) {
				atesler.remove(ates);
			}
		}
		for(DusmanGemi gemi:gemiler) {
			if(gemi.getDusmanY()>=800) {
				gemiler.remove(gemi);
			}
		}
		
		
		if(carpmaKontrol()) {
			if(can>0) {
				can--;
			}else if(can<=0){
				timer.stop();
				int cevap=JOptionPane.showConfirmDialog(this, "Canýnýz Bitti..\nTekrar?");
				if(cevap==0) {
					timer.restart();
					can=3;
					vurulan_gemi=0;
				}else {
					System.exit(0);
				}
			}
		}
		
		atesKontrol();
		
	}



	@Override
	public void repaint() {
		super.repaint();
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		for(Ates ates:atesler) {
			ates.setY(ates.getY()-atesDirY);
		}
		
		for(DusmanGemi gemi:gemiler) {
			gemi.setDusmanY(gemi.getDusmanY()+dusmanDirY);
		}
		
		repaint();
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		
		int c=e.getKeyCode();

		if(c==KeyEvent.VK_LEFT) {
			
			if(uzayGemisiX<=0) {
				uzayGemisiX=0;
			}else {
				uzayGemisiX-=gemiDirX;
			}
			
		}else if(c==KeyEvent.VK_RIGHT) {
			
			if(uzayGemisiX>=720) {
				uzayGemisiX=720;
			}else {
				uzayGemisiX+=gemiDirX;
			}
			
		}else if(c==KeyEvent.VK_UP) {
			if(uzayGemisiY<=0) {
				uzayGemisiY=0;
			}else {
				uzayGemisiY-=gemiDirY;
			}
		}else if(c==KeyEvent.VK_DOWN) {
			if(uzayGemisiY>=500) {
				uzayGemisiY=500;
			}else {
				uzayGemisiY+=gemiDirY;
			}
		}
			else if(c==KeyEvent.VK_SPACE) {
			atesler.add(new Ates(uzayGemisiX+20, uzayGemisiY-10));
		}
		
	}



	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
