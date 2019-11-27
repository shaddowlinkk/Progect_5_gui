import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteHandeler {
    private int size;
    private BufferedImage spriteSheet;
    public SpriteHandeler(int _size){
        size=_size;
    }
    public void loadSprites(String file){
        BufferedImage sprites= null;
        try{
            sprites= ImageIO.read(new File(file));
        }catch (IOException e){

        }
        spriteSheet=sprites;
    }
    public BufferedImage getSprite(int x,int y){
        return spriteSheet.getSubimage(x,y,size,size);
    }
}
