import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * Created by nathan on 4/24/2017.
 */
public class Mob_2 extends JButton{
    private BoundingBox[]spikes = new BoundingBox[4];
    private int x, y,dx,dy,_width,_height,count;
    private MobAI AI = new MobAI();
    private boolean stoped,aboutMove;
    private BoundingBox hitBox;


    public Mob_2(ImageIcon s, int width, int height, int _x, int _y){

        super(s);
        x = _x;
        y = _y;
        _width=width;
        _height=height;
        setLocation(x,y);
        setSize(width, height);
        setForeground(Color.RED);
        setBackground(Color.green);
        /// spikes
        spikes[0]= new BoundingBox((x+(_width/2)),y-20,5,20);
        spikes[1]= new BoundingBox((x+(_width/2)),y+_height,5,20);
        spikes[2]= new BoundingBox((x-20),y+(_height/2),20,5);
        spikes[3]= new BoundingBox((x+_width),y+(_height/2),20,5);
        // projectiles


        hitBox= new BoundingBox(x,y,_width,_height);

    }

    /**
     * retreves the spikes of  mob
     * @return spikes
     */
    public BoundingBox[] spikes(){
         return spikes;
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

    public void move() {
        if(count==1){
            dx=(AI.getDx()==0)?-1:1;
            dy=(AI.getDy()==0)?-1:1;
        }
        x = x + dx;
        y = y + dy;
        hitBox.setY(y);
        hitBox.setX(x);
        super.setLocation(x, y);
        stoped=moveDone();
        count++;
    }
    private boolean moveDone(){
        boolean cheack= false;
        if(count>=100&&count<300){
            cheack= true;
            dx=0;
            dy=0;
        }
        else if(count >=300)
        {
            aboutMove=false;
            count=0;
        }
        if (count>=260&&count<300){
            aboutMove=true;
        }
        return cheack;
    }
    public boolean hitCheck(int _x,int _y,int width,int height){
        return hitBox.check(_x,_y,width,height);
    }
    public boolean isStoped(){
        return stoped;
    }
    public boolean isAboutMove(){
        return aboutMove;
    }
    public Projectile[] getProjectiles(){
        Projectile[] proj = new Projectile[4];
        proj[0]= new Projectile((x+(_width/2)),y-5,5,5);
        proj[0].setDy(-1);
        proj[1]= new Projectile((x+(_width/2)),y+_height,5,5);
        proj[1].setDy(1);
        proj[2]= new Projectile((x-5),y+(_height/2),5,5);
        proj[2].setDx(-1);
        proj[3]= new Projectile((x+_width),y+(_height/2),5,5);
        proj[3].setDx(1);
        return proj;
    }
    public void updateSpike(){
        spikes[0]= new BoundingBox((x+(_width/2)),y-20,5,20);
        spikes[1]= new BoundingBox((x+(_width/2)),y+_height,5,20);
        spikes[2]= new BoundingBox((x-20),y+(_height/2),20,5);
        spikes[3]= new BoundingBox((x+_width),y+(_height/2),20,5);
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_D) {
            dx = -1;
        }

        if (key == KeyEvent.VK_A) {
            dx = 1;

        }

        if (key == KeyEvent.VK_W) {
            dy = -1;

        }

        if (key == KeyEvent.VK_S) {
            dy = 1;
        }

    }
    /**
     * this handels key events to move player
     * @param e keyEvent
     */
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_S) {
            dy = 0;
        }

    }






}
