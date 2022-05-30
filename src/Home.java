import java.awt.*;
public class Home {
	private int x, y;
	private Client tc;
	public static final int width = 60, length = 45; 
	private boolean live = true;
	private static Toolkit tk = Toolkit.getDefaultToolkit(); 
	private static Image[] homeImags = null;
	static {
		homeImags = new Image[] { tk.getImage(walls.class
				.getResource("Images/home.gif")), };
	}
	public Home(int x, int y, Client tc) {
		this.x = x;
		this.y = y;
		this.tc = tc; 
	}
	public void gameOver(Graphics g) {
		tc.tanks.clear();
		tc.steels.clear();
		tc.otherWall.clear();
		tc.bombs.clear();
		tc.theWater.clear();
		tc.grasss.clear();
		tc.bullets.clear();
		tc.hometank.setLive(false);
		Color c = g.getColor(); 
		g.setColor(Color.green);
		Font f = g.getFont();
		g.setFont(new Font(" ", Font.PLAIN, 40));
		g.drawString("You lose!", 220, 250);
		g.drawString("  Game Over! ", 220, 300);
		g.setFont(f);
		g.setColor(c);
	}
	public void draw(Graphics g) {
		if (live) { 
			g.drawImage(homeImags[0], x, y, null);
			for (int i = 0; i < tc.homeWall.size(); i++) {
				walls w = tc.homeWall.get(i);
				w.draw(g);
			}
		} else {
			gameOver(g); 
		}
	}
	public boolean isLive() { 
		return live;
	}
	public void setLive(boolean live) { 
		this.live = live;
	}
	public Rectangle getRect() { 
		return new Rectangle(x, y, width, length);
	}
}