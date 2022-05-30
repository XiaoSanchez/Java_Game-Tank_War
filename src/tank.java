import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class tank {
	public static  int speedX = 6, speedY =6; 
	public static int count = 0;
	public static final int width = 35, length = 35; 
	private Direction direction = Direction.STOP; 
	private Direction Kdirection = Direction.U; 
	Client tc;
	private boolean good;
	private int x, y;
	private int oldX, oldY;
	private boolean live = true; 
	private int life = 200; 
	private static Random r = new Random();
	private int step = r.nextInt(10)+5 ; 
	private boolean bL = false, bU = false, bR = false, bD = false;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImags = null; 
	static {
		tankImags = new Image[] {
				tk.getImage(bomb.class.getResource("Images/tankD.gif")),
				tk.getImage(bomb.class.getResource("Images/tankU.gif")),
				tk.getImage(bomb.class.getResource("Images/tankL.gif")),
				tk.getImage(bomb.class.getResource("Images/tankR.gif")), };
	}
	public tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	public tank(int x, int y, boolean good, Direction dir, Client tc) {
		this(x, y, good);
		this.direction = dir;
		this.tc = tc;
	}
	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this); 
			}
			return;
		}
		if (good)
			new DrawBloodbBar().draw(g); 
		switch (Kdirection) {
		case D:
			g.drawImage(tankImags[0], x, y, null);
			break;
		case U:
			g.drawImage(tankImags[1], x, y, null);
			break;
		case L:
			g.drawImage(tankImags[2], x, y, null);
			break;
		case R:
			g.drawImage(tankImags[3], x, y, null);
			break;
			case STOP:
				break;
			default:
				break;
		}
		move();   
	}
	void move() {
		this.oldX = x;
		this.oldY = y;
		switch (direction) {  
		case L:
			x -= speedX;
			break;
		case U:
			y -= speedY;
			break;
		case R:
			x += speedX;
			break;
		case D:
			y += speedY;
			break;
		case STOP:
			break;
		}
		if (this.direction != Direction.STOP) {
			this.Kdirection = this.direction;
		}
		if (x < 0)
			x = 0;
		if (y < 40)      
			y = 40;
		if (x + tank.width > Client.Fram_width)  
			x = Client.Fram_width - tank.width;
		if (y + tank.length > Client.Fram_length)
			y = Client.Fram_length - tank.length;
		if (!good) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3;  
				int rn = r.nextInt(directons.length);
				direction = directons[rn];      
			}
			step--;
			if (r.nextInt(40) > 38)
				this.fire();
		}
	}
	private void changToOldDir() {  
		x = oldX;
		y = oldY;
	}
	public void keyPressed(KeyEvent e) {  
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_R:  
			tc.tanks.clear();  
			tc.bullets.clear();
			tc.grasss.clear();
			tc.otherWall.clear();
			tc.homeWall.clear();
			tc.steels.clear();
			tc.hometank.setLive(false);
			if (tc.tanks.size() == 0) {   
				for (int i = 0; i < 20; i++) {
					if (i < 9)                              
						tc.tanks.add(new tank(150 + 70 * i, 40, false,
								Direction.R, tc));
					else if (i < 15)
						tc.tanks.add(new tank(700, 140 + 50 * (i -6), false,
								Direction.D, tc));
					else
						tc.tanks.add(new tank(10,  50 * (i - 12), false,
								Direction.L, tc));
				}
			}
			tc.hometank = new tank(300, 560, true, Direction.STOP, tc);
			if (!tc.home.isLive())  
				tc.home.setLive(true);
			new Client(); 
			break;
		case KeyEvent.VK_RIGHT: 
			bR = true;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:  
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		decideDirection();
	}
	void decideDirection() {
		if (!bL && !bU && bR && !bD)  
			direction = Direction.R;
		else if (bL && !bU && !bR && !bD)   
			direction = Direction.L;
		else if (!bL && bU && !bR && !bD)  
			direction = Direction.U;
		else if (!bL && !bU && !bR && bD) 
			direction = Direction.D;
		else if (!bL && !bU && !bR && !bD)
			direction = Direction.STOP;  
	}
	public void keyReleased(KeyEvent e) {  
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F:
			fire();
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		decideDirection();  
	}
	public Bullets fire() {  
		if (!live)
			return null;
		int x = this.x + tank.width / 2 - Bullets.width / 2;  
		int y = this.y + tank.length / 2 - Bullets.length / 2;
		Bullets m = new Bullets(x, y + 2, good, Kdirection, this.tc);  
		tc.bullets.add(m);                                                
		return m;
	}
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isGood() {
		return good;
	}
	public boolean collideWithWall(walls w) {  
		if (this.live && this.getRect().intersects(w.getRect())) {
			 this.changToOldDir();    
			return true;
		}
		return false;
	}
	public boolean collideWithWall(steels w) {  
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();     
			return true;
		}
		return false;
	}
	public boolean collideWater(Water r) {    
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}
	public boolean collideHome(Home h) {   
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}
	public boolean collideWithtanks(java.util.List<tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.changToOldDir();
					t.changToOldDir();
					return true;
				}
			}
		}
		return false;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	private class DrawBloodbBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(375, 585, width, 10);
			int w = width * life / 200;
			g.fillRect(375, 585, w, 10);
			g.setColor(c);
		}
	}
	public boolean eat(heal b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if(this.life<=100)
			this.life = this.life+100;      
			else
				this.life = 200;
			b.setLive(false);
			return true;
		}
		return false;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}