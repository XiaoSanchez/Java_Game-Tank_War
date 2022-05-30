import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;
public class Client extends Frame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final int Fram_width = 900; 
	public static final int Fram_length = 600;
	public static boolean printable = true;
	MenuBar jmb = null;
	Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null;
	MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
			jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null;
	Image screenImage;
	tank hometank = new tank(300, 560, true, Direction.STOP, this);
	heal blood = new heal(); 
	home home = new home(373, 545, this);
	List<water> thewater = new ArrayList<water>();
	List<tank> tanks = new ArrayList<tank>();
	List<bomb> bombs = new ArrayList<bomb>();
	List<Bullets> bullets = new ArrayList<Bullets>();
	List<grass> grasss = new ArrayList<grass>();
	List<walls> homeWall = new ArrayList<walls>(); 
	List<walls> otherWall = new ArrayList<walls>();
	List<steels> steels = new ArrayList<steels>();
	public void update(Graphics g) {
		screenImage = this.createImage(Fram_width, Fram_length);
		Graphics gps = screenImage.getGraphics();
		Color c = gps.getColor();
		gps.setColor(Color.lightGray);
		gps.fillRect(0, 0, Fram_width, Fram_length);
		gps.setColor(c);
		framPaint(gps);
		g.drawImage(screenImage, 0, 0, null);
	}
	public void framPaint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.green); 
		Font f1 = g.getFont();
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("Enemies Remains: ", 150, 60);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("" + tanks.size(), 360, 60);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("Health: ", 570, 60);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("" + hometank.getLife(), 660, 60);
		g.setFont(f1);
		if (tanks.size() == 0 && home.isLive() && hometank.isLive()) {
			Font f = g.getFont();
			g.setFont(new Font("TimesRoman", Font.BOLD, 60)); 
			this.otherWall.clear();
			g.drawString("You Win!", 310, 300);
			g.setFont(f);
		}
		if (hometank.isLive() == false) {
			Font f = g.getFont();
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			tanks.clear();
			bullets.clear();
			g.setFont(f);
		}
		g.setColor(c);
		for (int i = 0; i < thewater.size(); i++) { 
			water r = thewater.get(i);
			r.draw(g);
		}
		for (int i = 0; i < thewater.size(); i++) {
			water r = thewater.get(i);
			hometank.collidewater(r);
			r.draw(g);
		}
		home.draw(g); 
		hometank.draw(g);
		hometank.eat(blood);
		for (int i = 0; i < bullets.size(); i++) { 
			Bullets m = bullets.get(i);
			m.hittanks(tanks); 
			m.hittank(hometank); 
			m.hitHome(); 
			for (int j = 0; j < steels.size(); j++) { 
				steels mw = steels.get(j);
				m.hitWall(mw);
			}
			for (int j = 0; j < otherWall.size(); j++) {
				walls w = otherWall.get(j);
				m.hitWall(w);
			}
			for (int j = 0; j < homeWall.size(); j++) {
				walls cw = homeWall.get(j);
				m.hitWall(cw);
			}
			m.draw(g); 
		}
		for (int i = 0; i < tanks.size(); i++) {
			tank t = tanks.get(i); 
			for (int j = 0; j < homeWall.size(); j++) {
				walls cw = homeWall.get(j);
				t.collideWithWall(cw); 
				cw.draw(g);
			}
			for (int j = 0; j < otherWall.size(); j++) { 
				walls cw = otherWall.get(j);
				t.collideWithWall(cw);
				cw.draw(g);
			}
			for (int j = 0; j < steels.size(); j++) { 
				steels mw = steels.get(j);
				t.collideWithWall(mw);
				mw.draw(g);
			}
			for (int j = 0; j < thewater.size(); j++) {
				water r = thewater.get(j); 
				t.collidewater(r);
				r.draw(g);
			}
			t.collideWithtanks(tanks); 
			t.collidehome(home);
			t.draw(g);
		}
		blood.draw(g);
		for (int i = 0; i < grasss.size(); i++) { 
			grass tr = grasss.get(i);
			tr.draw(g);
		}
		for (int i = 0; i < bombs.size(); i++) { 
			bomb bt = bombs.get(i);
			bt.draw(g);
		}
		for (int i = 0; i < otherWall.size(); i++) { 
			walls cw = otherWall.get(i);
			cw.draw(g);
		}
		for (int i = 0; i < steels.size(); i++) { 
			steels mw = steels.get(i);
			mw.draw(g);
		}
		hometank.collideWithtanks(tanks);
		hometank.collidehome(home);
		for (int i = 0; i < steels.size(); i++) {
			steels w = steels.get(i);
			hometank.collideWithWall(w);
			w.draw(g);
		}
		for (int i = 0; i < otherWall.size(); i++) {
			walls cw = otherWall.get(i);
			hometank.collideWithWall(cw);
			cw.draw(g);
		}
		for (int i = 0; i < homeWall.size(); i++) { 
			walls w = homeWall.get(i);
			hometank.collideWithWall(w);
			w.draw(g);
		}
	}
	public Client() {
		jmb = new MenuBar();
		jm1 = new Menu("Game");
		jm2 = new Menu("Pulse/Continue");
		jm3 = new Menu("Help");
		jm4 = new Menu("Speed");
		jm1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi1 = new MenuItem("New Game");
		jmi2 = new MenuItem("Exit");
		jmi3 = new MenuItem("Pulse");
		jmi4 = new MenuItem("Continue");
		jmi5 = new MenuItem("Help");
		jmi6 = new MenuItem("x 1");
		jmi7 = new MenuItem("x 2");
		jmi8 = new MenuItem("x 3");
		jmi9 = new MenuItem("x 4");
		jmi1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi5.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm2.add(jmi3);
		jm2.add(jmi4);
		jm3.add(jmi5);
		jm4.add(jmi6);
		jm4.add(jmi7);
		jm4.add(jmi8);
		jm4.add(jmi9);
		jmb.add(jm1);
		jmb.add(jm2);
		jmb.add(jm4);
		jmb.add(jm3);
		jmi1.addActionListener(this);
		jmi1.setActionCommand("NewGame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("Stop");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continue");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("help");
		jmi6.addActionListener(this);
		jmi6.setActionCommand("x1");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("x2");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("x3");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("x4");
		this.setMenuBar(jmb);
		this.setVisible(true);
		for (int i = 0; i < 9; i++) { 
			if (i < 3)
				homeWall.add(new walls(360, 570 - 30 * i, this));
			else if (i < 6)
				homeWall.add(new walls(390 + 30 * (i - 4), 510, this));
			else
				homeWall.add(new walls(420, 540 + (i - 7) * 30, this));
		}
		for (int i = 0; i < 32; i++) {
			if (i < 16) {
				otherWall.add(new walls(510 + 30 * i, 90, this));
				otherWall.add(new walls(210, 390 + 30 * i, this));
			} else if (i < 32) {
				otherWall.add(new walls(210 + 30 * (i - 16), 330, this));
				otherWall.add(new walls(510 + 30 * (i - 16), 210, this));
				otherWall.add(new walls(210, 390 + 30 * (i - 16), this));
				otherWall.add(new walls(510, 390 + 30 * (i - 16), this));
			}
		}
		for (int i = 0; i < 20; i++) { 
			if (i < 10) {
				steels.add(new steels(150 + 30 * i, 150, this));
				steels.add(new steels(600, 390 + 30 * (i), this));
			} else if (i < 20)
				steels.add(new steels(150 + 30 * (i - 10), 180, this));
			else
				steels.add(new steels(510 + 30 * (i - 10), 150, this));
		}
		for (int i = 0; i < 4; i++) { 
			if (i < 4) {
				grasss.add(new grass(0 + 30 * i, 360, this));
				grasss.add(new grass(1200 + 30 * i, 360, this));
				grasss.add(new grass(660 + 30 * i, 360, this));
			}
		}
		for (int i = 0; i < 4; i++) { 
			if (i < 4) {
				thewater.add(new water(90 + 30 * i, 100, this));
				thewater.add(new water(300 + 30 * i, 100, this));
			}
		}
		for (int i = 0; i < 20; i++) {
			if (i < 9) 
				tanks.add(new tank(150 + 70 * i, 40, false, Direction.D, this));
			else if (i < 15)
				tanks.add(new tank(700, 140 + 50 * (i - 6), false, Direction.D,
						this));
			else
				tanks
						.add(new tank(10, 50 * (i - 12), false, Direction.D,
								this));
		}
		this.setSize(Fram_width, Fram_length); 
		this.setLocation(280, 50); 
		this
				.setTitle("tank War(Restart: R  Fire: F)");
		this.addWindowListener(new WindowAdapter() { 
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		this.setResizable(false);
		this.setBackground(Color.white);
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start(); 
	}
	public static void main(String[] args) {
		new Client(); 
	}
	private class PaintThread implements Runnable {
		public void run() {
			while (printable) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private class KeyMonitor extends KeyAdapter {
		public void keyReleased(KeyEvent e) { 
			hometank.keyReleased(e);
		}
		public void keyPressed(KeyEvent e) { 
			hometank.keyPressed(e);
		}
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("NewGame")) {
			printable = false;
			Object[] options = { "Yes", "No" };
			int response = JOptionPane.showOptionDialog(this, "Confirm to Restart:", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				printable = true;
				this.dispose();
				new Client();
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); 
			}
		} else if (e.getActionCommand().endsWith("Stop")) {
			printable = false;
		} else if (e.getActionCommand().equals("Continue")) {
			if (!printable) {
				printable = true;
				new Thread(new PaintThread()).start(); 
			}
		} else if (e.getActionCommand().equals("Exit")) {
			printable = false;
			Object[] options = { "Yes", "No" };
			int response = JOptionPane.showOptionDialog(this, "Confirm to Exit", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.out.println("Exit");
				System.exit(0);
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); 
			}
		} else if (e.getActionCommand().equals("help")) {
			printable = false;
			JOptionPane.showMessageDialog(null, " Arrow key > Direction, F > Shoot, R > Restart",
					"Warn£¡", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); 
		} else if (e.getActionCommand().equals("x1")) {
			tank.count = 12;
			tank.speedX = 6;
			tank.speedY = 6;
			Bullets.speedX = 10;
			Bullets.speedY = 10;
			this.dispose();
			new Client();
		} else if (e.getActionCommand().equals("x2")) {
			tank.count = 12;
			tank.speedX = 10;
			tank.speedY = 10;
			Bullets.speedX = 12;
			Bullets.speedY = 12;
			this.dispose();
			new Client();
		} else if (e.getActionCommand().equals("x3")) {
			tank.count = 20;
			tank.speedX = 14;
			tank.speedY = 14;
			Bullets.speedX = 16;
			Bullets.speedY = 16;
			this.dispose();
			new Client();
		} else if (e.getActionCommand().equals("x4")) {
			tank.count = 20;
			tank.speedX = 16;
			tank.speedY = 16;
			Bullets.speedX = 18;
			Bullets.speedY = 18;
			this.dispose();
			new Client();
		}
	}
}
