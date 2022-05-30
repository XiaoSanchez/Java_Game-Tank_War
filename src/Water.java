import java.awt.*;
public class water {
	public static final int waterWidth = 30;
	public static final int waterLength = 30;
	private int x, y;
	Client tc ;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] waterImags = null;
	static {   
		waterImags = new Image[]{
				tk.getImage(walls.class.getResource("Images/water.gif")),
		};
	}
	public water(int x, int y, Client tc) {    
		this.x = x;
		this.y = y;
		this.tc = tc;             
	}
	public void draw(Graphics g) {
		g.drawImage(waterImags[0],x, y, null);            
	}
	public static int getwaterWidth() {
		return waterWidth;
	}
	public static int getwaterLength() {
		return waterLength;
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
	public Rectangle getRect() {
		return new Rectangle(x, y, waterWidth, waterLength);
	}
}
