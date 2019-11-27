/*
 * Created by nathan on 4/24/2017.
 * TODO change spikes to projectiles
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
//TODO clean up extra arrays in shots
public class Board implements KeyListener{
    private Player player;
    private ImageIcon playerImage;
    private JFrame frame;
    private JPanel actionPanel;
    private JPanel playArea;
    private Timer playerTimer;
    private JLabel scoreLabal;
    private int score=0;
    private int debug=0;
    private int[] rDoor;
    private BoundingBox[] door={new BoundingBox(215,415, 60,20),new BoundingBox(475,180, 20,60),new BoundingBox(215,0, 60,20),new BoundingBox(0,180, 20,60)};
    private ArrayList<Mob_2> mobs = new ArrayList();
    private ArrayList<Projectile[]> shots = new ArrayList<>();
    private static final int STEPS = 500;
    private static final int DELAY = 20;
    private int launchCount= 0;

    private boolean activateSpikes=false;
    private int stopcount=0;
    private boolean moving=false;
    private  boolean drawspike;
    private  Boolean roomcleared =false;
    private BoundingBox playeerAttack;
    private Room core= new Room();


    public static final int FRAMEHEIGHT = 500;
    public static final int FRAMEWIDTH = 500;
    private boolean uptodate;

    public Board() {
        frame = new JFrame();
        actionPanel = new JPanel();
        playArea = new JPanel();
        frame.setLayout( null);
        actionPanel.setSize(FRAMEWIDTH, 40);
        actionPanel.setLocation(0, 0);
        actionPanel.setBackground(Color.black);
        scoreLabal =new JLabel();
        scoreLabal.setText("Score:"+score);
        scoreLabal.setSize(20,40);
        scoreLabal.setLocation(0,0);
        scoreLabal.setBackground(Color.white);
        scoreLabal.setForeground(Color.white);
        actionPanel.add(scoreLabal);
        actionPanel.setVisible(true);

        playArea.setSize(FRAMEWIDTH, FRAMEHEIGHT - actionPanel.getHeight());
        playArea.setLocation(0, actionPanel.getHeight());
        playArea.setLayout(null);

        frame.addKeyListener(this);
        frame.add(actionPanel);
        frame.add(playArea);
        frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        playerImage = new ImageIcon("Little_flame_character.gif");
        playerTimer = new Timer(10, new TimerListener());
        playerTimer.start();

    }



    /**
     * This method is used to generate the room and add the player
     * @param doors this is the door array rom the room class
     */
     private void RoomGen(int[] doors){
        rDoor=doors;
        genMob();
         System.out.println(Arrays.toString(doors));
         playArea.add(player);
        for(int i=0;i<doors.length;i++){
            if(doors[i]==1){
           playArea.add((door[i]));}
        }
     }
    /**
     * this gerates the starting Room
     */
    public void genRoom(){
        genMob();
        int[] doors=core.getDoor();
        rDoor=doors;
        playArea.add(player);
        for(int i=0;i<doors.length;i++){
            playArea.add((door[i]));
        }
    }
    /**
     * this method removes all of the doors and player from the board
     */
    private void removeDoor(){
         for(int i=0;i<door.length;i++){
             playArea.remove((door[i]));
         }
         playArea.remove(player);
         player=new Player(playerImage);
     }


    /**
     * not used
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * this is used to register a keyPress and then pass it to player or does action related to the key press
     * @param e keyboard event
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_SPACE){
            playeerAttack=player.attack();
            playArea.add(playeerAttack);
            System.out.println("press");
            e.consume();
        }else if (key==KeyEvent.VK_F2){
            ProjectileDraw();
        }else if (key==KeyEvent.VK_F3){
            shots.add(mobs.get(0).getProjectiles());
            ProjectileDraw();
        }else if (key==KeyEvent.VK_F4){
            System.out.println();
        }else if(key==KeyEvent.VK_F1){
            ProjectilesMovmentHeadeler();
        }else if(key!=KeyEvent.VK_SPACE){
            //System.out.println("no space");
            player.keyPressed(e);
        }


    }

    /**
     * handels key relead evens
     * @param e key event
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_SPACE){
            playArea.remove(playeerAttack);
        }
            player.keyReleased(e);


    }

    /**
     * generate the location and number of mabs
     */
    private void genMob(){
        Random r = new Random();
        roomcleared =false;
       // int mobMumber = r.nextInt(5);
        int mobMumber = 1;
        for (int i =0;i<mobMumber;i++){
            mobCreate(r.nextInt((FRAMEWIDTH-50)),r.nextInt(((FRAMEHEIGHT - actionPanel.getHeight())-50)));
        }
    }
    /**
     * creates a creater on the feild
     */
   /* private void mobCreate(int x,int y){
        mobs.add( new Mob(new ImageIcon("flame.gif"),34,49,x,y));
        mobsBox.add(new BoundingBox (x,y,34,49));
        playArea.add(mobs.get(mobs.size()-1));
        playArea.add(mobsBox.get(0));
    }*/
    private void mobCreate(int x,int y){
        mobs.add( new Mob_2(new ImageIcon("flame.gif"),34,49,x,y));
        playArea.add(mobs.get(mobs.size()-1));
        // playArea.add(mobsBox.get(0));
    }

    /**
     * this is the core game timmer
     */
    private class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(roomcleared){
            DoorColitionDetection();}
            if(mobs.isEmpty()){
                roomcleared =true;
            }
            PlayerColitionDetection();
            ProjectileColitionDetection();
            MobAttackColitionDetection();
            spikeControler();
            ProjectileControler();
            ProjectilesMovmentHeadeler();
            MobMovmentHandeler();
            PlayerMovmentHeadeler();
            playArea.paintImmediately(playArea.getVisibleRect());
            launchCount++;

        }
    }

    private void ProjectileControler(){
        if (stopcount==1 && launchCount >= 75){
            shots.add(mobs.get(0).getProjectiles());
            ProjectileDraw();
            launchCount=0;
        }
    }

    private void spikeControler(){
        if(moving&& mobs.get(0).isStoped()){
            SpikeUpdate();
            moving=false;
            stopcount++;
        }
            if (stopcount==2 && mobs.get(0).isStoped()){
                activateSpikes=true;
                for(int i =0;i<mobs.size();i++) {
                    spikeDraw(i);
                }
            }
            if(activateSpikes==true && mobs.get(0).isAboutMove()){
                activateSpikes=false;
                stopcount=0;
                for(int i =0;i<mobs.size();i++) {
                    spikeDraw(i);
                }
            }
    }
    private void DoorColitionDetection(){
        for (int i=0;i<door.length;i++){
            if(door[i].check(player.getX(),player.getY(),player.getWidth(),player.getHeight())){
                playerTimer.stop();
                removeDoor();
                playArea.paintImmediately(playArea.getVisibleRect());
                core= core.enterRoom(i);
                int newDoors[] =core.getDoor();
               RoomGen(newDoors);
               playerTimer.start();
            }
        }

    }

    /**
     * ueds for detection attacks
     */
    private void ProjectileColitionDetection() {
        for (Projectile[] s : shots) {
            for (Projectile p : s) {
                if(boundY(p.getY(),p.getHeight())||boundX(p.getX(),p.getWidth())){
                    playArea.remove(p);
                }
            }
        }
    }
    private void MobAttackColitionDetection(){
        for(int i =0;i<mobs.size();i++) {
                if (!(playeerAttack == null)) {
                    if (mobs.get(i).hitCheck(playeerAttack.getX(), playeerAttack.getY(), playeerAttack.getWidth(), playeerAttack.getHeight())) {
                        playArea.remove(mobs.get((i)));
                        mobs.remove((i));
                        score+=100;
                        scoreLabal.setText("Score:"+score);
                    }

                }
            }
    }

    private void PlayerColitionDetection(){
        for(int i =0;i<mobs.size();i++) {
                for (int j = 0; j < (mobs.get(i).spikes()).length; j++) {
                    if ( activateSpikes == true){
                        if (player.hitCheck((mobs.get(i).spikes())[j].getX(), (mobs.get(i).spikes())[j].getY(), (mobs.get(i).spikes())[j].getHeight(), (mobs.get(i).spikes())[j].getHeight())) {
                            EndGame();
                            break;

                        }
                    }
                }
        }
        for (Projectile[] s : shots ) {
            for(Projectile p:s){
                if(player.hitCheck(p.getX(),p.getY(),p.getWidth(),p.getHeight())){
                    EndGame();
                    break;
                }
            }

        }

    }
    private void EndGame(){
        playerTimer.stop();
        activateSpikes=false;
        for(int h =0;h<mobs.size();h++) {
            spikeDraw(h);
            playArea.remove(mobs.get((h)));
        }
        removeDoor();
        ProjectileRemove();
        playArea.setBackground(Color.black);
    }
    private void Cleanup(){
        for (Projectile[] group:shots) {
        if(group.length==0){
            System.out.println("found");
        }
        }
    }
    /**
     * makes that plaer move
     */
    private void ProjectilesMovmentHeadeler(){
        for (Projectile[] group:shots) {
            for (Projectile shot :group) {
                shot.move();
            }

        }

    }
    private void PlayerMovmentHeadeler(){
        if (boundY()) {
            player.setDy((0 - 2*(player.getDy())));
            player.move();
        }
        else if (boundX()) {
            player.setDx((0 - 2*(player.getDx())));
            player.move();
        }
        else player.move();
    }

    private void MobMovmentHandeler(){
        if(!moving && !mobs.isEmpty() && !mobs.get(0).isStoped()){
            moving=true;
        }
        for(int i=0;i<mobs.size();i++){
            if (boundY(mobs.get(i).getY(),mobs.get(i).getHeight())&&boundX(mobs.get(i).getX(),mobs.get(i).getWidth())) {
                mobs.get(i).setDx(0);
                mobs.get(i).setDy(0);
                mobs.get(i).move();
            }else if (boundY(mobs.get(i).getY(),mobs.get(i).getHeight())) {
                mobs.get(i).setDy(0);
                mobs.get(i).move();
            }
            else if (boundX(mobs.get(i).getX(),mobs.get(i).getWidth())) {
                mobs.get(i).setDx(0);
                mobs.get(i).move();
            }
            else {
                mobs.get(i).move();
            }


        }
    }

    /**
     * controls when the spikes are in and out
     * @param i the mob that it is controling at the moment
     */
    private void spikeDraw(int i){

        if(activateSpikes&& !Arrays.asList(playArea.getComponents()).contains(mobs.get(i).spikes()[0])){
            playArea.add(mobs.get(i).spikes()[0]);
            playArea.add(mobs.get(i).spikes()[1]);
            playArea.add(mobs.get(i).spikes()[2]);
            playArea.add(mobs.get(i).spikes()[3]);
        }else if (!activateSpikes){
            playArea.remove(mobs.get(i).spikes()[0]);
            playArea.remove(mobs.get(i).spikes()[1]);
            playArea.remove(mobs.get(i).spikes()[2]);
            playArea.remove(mobs.get(i).spikes()[3]);
        }
    }
    private void ProjectileDraw(){
            for (Projectile[] group:shots) {
                for (Projectile shot :group) {
                    playArea.add(shot);
                }
            }
    }
    private void ProjectileRemove(){
        for (int i =0;i<mobs.size();i++){
            for (Projectile[] group:shots) {
                for (Projectile shot :group) {
                    playArea.remove(shot);
                }
            }
        }

    }


    private void SpikeUpdate(){
        for(int i=0;i<mobs.size();i++) {
                mobs.get(i).updateSpike();
        }
    }


    private boolean boundY(int y,int hight) {
        if (y <= 0) {
            return true;
        } else if ((y + hight + 20) >= playArea.getHeight()) {
            return true;
        }
        return false;
    }
    /**
     *  This is for testing x bounds
     * @return is player is hiting a x bound
     */
    private boolean boundX(int x,int width){
        if (x <= 0) {
            return true;
        }
        else if ((x + width) >= playArea.getWidth()) {
            return true;
        }
        return false;
    }

    private boolean boundY() {
        if (player.getY() <= 0) {
            return true;
        } else if ((player.getY() + player.getHeight() + 20) >= playArea.getHeight()) {
            return true;
        }
        return false;
    }
    /**
     *  This is for testing x bounds
     * @return is player is hiting a x bound
     */
    private boolean boundX(){
        if (player.getX() <= 0) {
            return true;
        }
        else if ((player.getX() + player.getWidth()) >= playArea.getWidth()) {
            return true;
        }
        return false;
    }
}
