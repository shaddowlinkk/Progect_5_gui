import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.*;
public class Player extends JButton{
    private int x, y,dx,dy,direction;
    private boolean on=false;
    private BoundingBox attack;
    private BoundingBox hitBox = new BoundingBox(x,y,34,49);
    public Player(ImageIcon s){

        super(s);
        x = 150;
        y = 150;
        setLocation(x,y);
        setSize(34, 49);
        //setForeground(Color.RED);
        setBackground(Color.pink);
       // setEnabled(false);


    }

    /**
     * moves the plaer ia direction
     */
    public void move() {
        x = x + dx;
        y = y + dy;
        hitBox.setY(y);
        hitBox.setX(x);

        super.setLocation(x, y);
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    /**
     * this is use to check  somthing is hiting the player
     * @param _x x of the boox you are cheking with
     * @param _y y of the box you are checking with
     * @param width width of the box youare checking with
     * @param height height od the box you are checking with
     * @return
     */
    public boolean hitCheck(int _x,int _y,int width,int height){
       return hitBox.check(_x,_y,width,height);
    }

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }

    /**
     * this handels key events to move player
     * @param e keyEvent
     */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
            direction=1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
            direction=2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
            direction=3;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
            direction=4;
        }

    }
    /**
     * this handels key events to move player
     * @param e keyEvent
     */
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
        if(key== KeyEvent.VK_SPACE){
            on=false;
            //System.out.println("reset");
        }
    }

    /**
     * this is for creating a attack box to hit mobs with
     * @return BouningBox to chack agenst
     */
    public BoundingBox attack(){
        if(!on) {
            switch (direction) {
                case 1:on=true;return new BoundingBox((x-20),y+(49/2),20,5);
                case 2:on=true;return new BoundingBox((x+34),y+(49/2),20,5);
                case 3:on=true;return new BoundingBox((x+(34/2)),y-20,5,20);
                case 4:on=true;return new BoundingBox((x+(34/2)),y+49,5,20);
                default:on=true;return new BoundingBox((x+(34/2)),y-20,5,20);
            }
        }
        return null;
    }
}
