/**
 * Created by nathan on 5/13/2017.
 */
import java.util.Random;
public class MobAI {
    private Random rand = new Random();
    public MobAI(){

    }
    public int getDx(){
        return rand.nextInt(2);
    }
    public int getDy(){
        return rand.nextInt(2);
    }
}
