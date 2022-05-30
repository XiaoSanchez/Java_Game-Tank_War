import java.awt.*;
public class grass {
	public static final int width = 30;
	public static final int length = 30;
	int x, y;
	Client tc ;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] grassImags = null;
	static {
		grassImags = new Image[]{
				tk.getImage(walls.class.getResource("Images/grass.gif")),
		};
	}
	public grass(int x, int y, Client tc) {  
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	public void draw(Graphics g) {           
		g.drawImage(grassImags[0],x, y, null);
	}
}