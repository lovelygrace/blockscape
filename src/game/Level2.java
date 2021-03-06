package game;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.Sound;

public class Level2 implements Level{
    private Shape levelBase, key, door,bimg;
    private FallingObj obj1;
    private Image k, d, img;
    private ShapeRenderer sr;
    private Platform water, p1, p2, p3, p4, p5, p6, p7, p8, fake;
    private List<Platform> platforms = new ArrayList<>();
    private Sound sound;
    private boolean pDead, pWin, keyMain;
    private int playerX, playerY;
    private boolean stop;
    private String pImg = "res/JumPlatform.png",
    		dImg = "res/DoorPlatform.png",
    		waterImg = "res/water.png",
    	    keyImg = "res/BrownKey.png",
    	    doorImg = "res/brownDoor.png",
    	    soundLevel = "res/level1.wav";

    //position of player, 1250, 430
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        float[] polygonPoints = new float[]
                            {10, 30,
                            50, 30,
                            50, 30,
                            750, 30,
                            750, 30,
                            600, 30,
                            750, 30,
                            750, 30,
                            800, 30,
                            850, 30, 
                            850, 30,
                            900, 30,
                            900, 30,
                            1000, 30,
                            1355, 30, 
                            1355, 750,
                            10, 750};
            levelBase = new Polygon(polygonPoints);
            p1 = new Platform(920, 627, 140, 50, pImg); //okay -spike
            p3 = new Platform(350, 500, 140, 50, pImg); //okay
            p4 = new Platform(1200, 500, 140, 50, dImg); //start
            p5 = new Platform(680, 420, 150, 40, dImg); // door-okay
            p6 = new Platform(1050, 350, 140, 50, pImg); 
            p7 = new Platform(850, 200, 140, 50, pImg);
            p8 = new Platform(540, 200, 140, 50, pImg);
            fake = new Platform(150, 250, 140, 50, pImg);
            p2 = new Platform(50, 620, 140, 50, pImg);
            water = new Platform(10, 683, 1344, 65, waterImg);
            
            key = new Rectangle(80, 540, 110, 90); //okay
            door = new Rectangle(694, 310,120,120); //okay
            bimg = new Rectangle(0,0,1366,768);
            obj1 = new FallingObj();
            k = new Image(keyImg);
            d = new Image(doorImg);
            sr = new ShapeRenderer();
            sound = new Sound(soundLevel);    
            bimg = new Rectangle(10,31,1345,700);
            img = new Image("res/lvl2BG.png");
       
            playerX = 1250;
            playerY = 430;
            
            platforms.add(p1);
            platforms.add(p2);
            platforms.add(p3);
            platforms.add(p4);
            platforms.add(p5);
            platforms.add(p6);
            platforms.add(p7);
            platforms.add(p8);
            platforms.add(fake);
        }
	
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		sr.textureFit(bimg,img); 
        g.draw(bimg);
        sr.textureFit(door,d);
		g.draw(levelBase);
		//sr.textureFit(spike, sp);
        p1.render(gc, g);
        p2.render(gc, g);
        p3.render(gc, g);
        p4.render(gc, g);
        p5.render(gc, g);
        p6.render(gc, g);
        p7.render(gc, g);
        p8.render(gc, g);
        fake.render(gc, g);
        water.render(gc, g);
        if (!keyMain){
            sr.textureFit(key, k);
        }
        obj1.render(gc, g);
                
	}


    public void update(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	obj1.update(water.intersects(obj1.getS()), collidePlatform(obj1.getS()) || collideMovingPlatform(obj1.getS()), collideBox(obj1.getS()));
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
        	sound.stop();
        	stop = true;
            sbg.enterState(2);
        }
        if (!sound.playing() && !stop){
            sound.loop();
        }
        if (pWin){
            pWin = false;
            ((Select)sbg.getState(2)).setCleared(true, 2);
            stop = true;
            sound.stop();
            ((ClearedLevel) sbg.getState(-2)).setCurrentLvl(2);
            ((ClearedLevel) sbg.getState(-2)).init(gc, sbg);
            sbg.enterState(-2);
        }
        if (pDead){
        	sound.stop();
        	stop = true;
            ((FailedLevel) sbg.getState(-1)).setCurrentLvl(2);
            ((FailedLevel) sbg.getState(-1)).init(gc, sbg);
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            sbg.enterState(-1);
        }
    }
	
    // for last level
    public void updateLastLevel(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	obj1.update(water.intersects(obj1.getS()), collidePlatform(obj1.getS()) || collideMovingPlatform(obj1.getS()), collideBox(obj1.getS()));
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
        	sound.stop();
        	stop = true;
            sbg.enterState(2);
        }
        if (!sound.playing() && !stop){
            sound.loop();
        }
        if (pWin){
        	pWin = false;
        	sound.stop();
        	stop = true;
			((Game) sbg.getState(3)).setLastLevel(true);
		    ((Game) sbg.getState(3)).setLevel(3);
		    ((Game) sbg.getState(3)).init(gc, sbg);
		    sbg.enterState(3);
        }
        if (pDead){
        	sound.stop();
        	stop = true;
            ((FailedLevel) sbg.getState(-1)).setCurrentLvl(10);
            ((FailedLevel) sbg.getState(-1)).init(gc, sbg);
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            sbg.enterState(-1);
        }
    }
	
	
    @Override
    public boolean collideBox(Shape s) {
        return levelBase.intersects(s);
                
    }

    
    @Override
    public boolean collidePlatform(Shape s){
    	for (int i = 0; i < platforms.size(); i++){
        	if(platforms.get(i).intersects(s)){
        		return true;
        	}
        }
        return false;

    }
            
    @Override
    public boolean collideMovingPlatform(Shape s){
    	return false;
    }
	
    // dead
    public boolean gameOver(Shape s){
        return obj1.collidesWith(s) || water.intersects(s);
    }

    public void setDead(boolean c){
        pDead = c;
    }
    
    // win
    public void setWin(boolean c){
        pWin = c;
    }
    
    public boolean winLevel(Shape s){
        return door.intersects(s) && keyMain;
        //return false;
    }
    
    // main key
 	public boolean collideKeyMain(Shape s) {
		return s.intersects(key);
	}

 	public boolean getKeyMain() {
		return keyMain;
	}

	
	public void setKeyMain(boolean m) {
		keyMain = m;
	}

	// main door
	public boolean collideDoor(Shape s) {
		return false;
	}

	// teleportation
	public boolean collideKeyTel(Shape s) {
		return false;
	}

	public boolean getKeyTel() {
		return false;
	}

	public void setKeyTel(boolean t) {}
	
	// open teleportation
	public void setOpenTel(boolean o) {}

	public boolean collideTel(Shape s) {
		return false;
	}
	public boolean getOpenTel() {
		return false;
	}
	
	public boolean isWin(){
		return pWin;
	}

	@Override
	public boolean collideSwitch(Shape s) {
		return false;
	}

	@Override
	public boolean getSwitch() {
		return false;
	}

	@Override
	public int getStartX() {
		return playerX;
	}

	@Override
	public int getStartY() {
		return playerY;
	}

	@Override
	public int getTelX() {
		return 0;
	}

	@Override
	public int getTelY() {
		return 0;
	}

	@Override
	public void setSwitch(boolean s) {
		
	}

}
