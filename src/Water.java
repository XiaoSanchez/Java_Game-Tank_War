import java.awt.*;
public class Water {
	public static final int WaterWidth = 30;
	public static final int WaterLength = 30;
	private int x, y;
	Client tc ;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] WaterImags = null;
	static {   
		WaterImags = new Image[]{
				tk.getImage(walls.class.getResource("Images/water.gif")),
		};
	}
	public Water(int x, int y, Client tc) {    
		this.x = x;
		this.y = y;
		this.tc = tc;             
	}
	public void draw(Graphics g) {
		g.drawImage(WaterImags[0],x, y, null);            
	}
	public static int getWaterWidth() {
		return WaterWidth;
	}
	public static int getWaterLength() {
		return WaterLength;
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
		return new Rectangle(x, y, WaterWidth, WaterLength);
	}
}
