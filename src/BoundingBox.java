/**
 * Created by sip26 on 4/24/2017.
 */

import java.awt.Color;
import javax.swing.*;

public class BoundingBox extends JButton{
    private int x;
    private int y;
    private int x2;
    private int y2;
    int width;
    int height;

    // uses xy and hght to make bounfing box

    /**
     * This is used to create boxes that can interac with each other used for alot of game machanics
     * @param _x set x of box
     * @param _y set y of box
     * @param _width set width
     * @param _height set height
     */
    public BoundingBox(int _x, int _y, int _width, int _height){
        x=_x;
        y=_y;
        width=_width;
        height=_height;
        x2=x+width;
        y2=y+height;
        setLocation(x,y);
        setSize(width, height);
        setForeground(Color.RED);
        setBackground(Color.BLUE);
        //setEnabled(false);
    }

    /**
     * sets the x value
     * @param x x value to be set
     */
    public void setX(int x) {
        this.x = x;
        x2=x+width;
    }

    public void setY(int y) {
        this.y = y;
        y2=y+height;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }


    /**
     * this is use to check is 2 boxes are colling
     * @param _x x of the boox you are cheking with
     * @param _y y of the box you are checking with
     * @param width width of the box youare checking with
     * @param height height od the box you are checking with
     * @return
     */
    public boolean check(int _x, int _y, int width, int height){
        int px=_x;
        int py=_y;
        int px2=px+width;
        int py2=py+height;
        if ((px>x&&px<x2)&&((py>y&&py<y2)||(py2>y&&py2<y2))){
            return true;
        }else if ((px2>x&&px2<x2)&&((py>y&&py<y2)||(py2>y&&py2<y2))){
            return true;
        }
        return false;
    }
}
