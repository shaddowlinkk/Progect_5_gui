import java.awt.Color;
import java.util.Random;
import javax.swing.*;

/**
 * Created by nathan on 4/24/2017.
 */
public class Mob extends JButton{
    private BoundingBox[]spikes = new BoundingBox[4];
    private int x, y,dx,dy,_width,_height,count;
    private MobAI AI = new MobAI();
    private boolean stoped,aboutMove;

    public Mob(ImageIcon s,int width,int height,int _x,int _y){

        super(s);
        x = _x;
        y = _y;
        _width=width;
        _height=height;
        setLocation(x,y);
        setSize(width, height);
        setForeground(Color.RED);
        setBackground(Color.green);
        spikes[0]= new BoundingBox((x+(_width/2)),y-20,5,20);
        spikes[1]= new BoundingBox((x+(_width/2)),y+_height,5,20);
        spikes[2]= new BoundingBox((x-20),y+(_height/2),20,5);
        spikes[3]= new BoundingBox((x+_width),y+(_height/2),20,5);
        //setEnabled(false);

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
    public boolean isStoped(){
        return stoped;
    }
    public boolean isAboutMove(){
        return aboutMove;
    }
    public void updateSpike(){
        spikes[0]= new BoundingBox((x+(_width/2)),y-20,5,20);
        spikes[1]= new BoundingBox((x+(_width/2)),y+_height,5,20);
        spikes[2]= new BoundingBox((x-20),y+(_height/2),20,5);
        spikes[3]= new BoundingBox((x+_width),y+(_height/2),20,5);
    }





}
