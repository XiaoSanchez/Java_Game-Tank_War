import java.awt.*;
import java.util.*;
import java.util.List;
public class Bullets {
	public static  int speedX = 10;
	public static  int speedY = 10; 
	public static final int width = 10;
	public static final int length = 10;
	private int x, y;
	Direction diretion;
	private boolean good;
	private boolean live = true;
	private Client tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bulletImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>(); 
	static {
		bulletImages = new Image[] { 
				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletL.gif")),
				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletU.gif")),
				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletR.gif")),
				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletD.gif")),
		};
		imgs.put("L", bulletImages[0]); 
		imgs.put("U", bulletImages[1]);
		imgs.put("R", bulletImages[2]);
		imgs.put("D", bulletImages[3]);
	}
	public Bullets(int x, int y, Direction dir) { 
		this.x = x;
		this.y = y;
		this.diretion = dir;
	}
	public Bullets(int x, int y, boolean good, Direction dir, Client tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}
	private void move() {
		switch (diretion) {
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
		if (x < 0 || y < 0 || x > Client.Fram_width
				|| y > Client.Fram_length) {
			live = false;
		}
	}
	public void draw(Graphics g) {
		if (!live) {
			tc.bullets.remove(this);
			return;
		}
		switch (diretion) { 
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
			case STOP:
				break;
			default:
				break;
		}
		move(); 
	}
	public boolean isLive() { 
		return live;
	}
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}
	public boolean hittanks(List<tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			if (hittank(tanks.get(i))) { 
				return true;
			}
		}
		return false;
	}
	public boolean hittank(tank t) { 
		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {
			bomb e = new bomb(t.getX(), t.getY(), tc);
			tc.bombs.add(e);
			if (t.isGood()) {
				t.setLife(t.getLife() - 50); 
				if (t.getLife() <= 0)
					t.setLive(false); 
			} else {
				t.setLive(false); 
			}
			this.live = false;
			return true; 
		}
		return false; 
	}
	public boolean hitWall(walls w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc.otherWall.remove(w); 
			this.tc.homeWall.remove(w);
			return true;
		}
		return false;
	}
	public boolean hitWall(steels w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	public boolean hitHome() { 
		if (this.live && this.getRect().intersects(tc.home.getRect())) {
			this.live = false;
			this.tc.home.setLive(false); 
			return true;
		}
		return false;
	}
}