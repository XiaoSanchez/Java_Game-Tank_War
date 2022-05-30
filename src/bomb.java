import java.awt.*;
public class bomb {
	private int x, y;
	private boolean live = true;
	private Client tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] imgs = {
			tk.getImage(bomb.class.getClassLoader().getResource(
					"images/1.png")),
			tk.getImage(bomb.class.getClassLoader().getResource(
					"images/2.png")),
			tk.getImage(bomb.class.getClassLoader().getResource(
					"images/3.png")),};
	int step = 0;
	public bomb(int x, int y, Client tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	public void draw(Graphics g) {
		if (!live) {
			tc.bombs.remove(this);
			return;
		}
		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}