import javax.swing.*;
public class Projectile extends JButton {
    private int x, y,dx,dy,_width,_height,count;
    private BoundingBox hitBox;
    public Projectile(int width, int height, int _x, int _y){
        x = _x;
        y = _y;
        _width=width;
        _height=height;
        setLocation(x,y);
        setSize(width, height);
        hitBox = new BoundingBox(x,y,_width,_height);
    }
    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hitCheck(int _x,int _y,int width,int height){
        return hitBox.check(_x,_y,width,height);
    }
    public void move(){
        x = x + dx;
        y = y + dy;
        hitBox.setY(y);
        hitBox.setX(x);
        super.setLocation(x, y);
    }
}
