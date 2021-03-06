package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Help2 extends BasicGameState {
	
	private Image next, bak;
	private Background back;
	private boolean isBak = false;
	private boolean isNext = false;
	private String name = "res/HelpState2.png";
	
	public Help2(int id) {
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		try {
			back = new Background(name, 1);
			next = new Image("res/aboutNext.png");
			bak = new Image("res/aboutBack.png");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		back.draw(arg2);
		
		if (!isBak) {
			arg2.drawImage(bak, 270, 360);
		} else {
			bak.setFilter(Image.FILTER_LINEAR);
            bak.draw(267, 356, 1.1f);
		}
		
		if (!isNext) {
			arg2.drawImage(next, 1030, 360);
		} else {
			next.setFilter(Image.FILTER_LINEAR);
            next.draw(1027, 356, 1.1f);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
		Input input = gc.getInput();

        if (input.isKeyPressed(Input.KEY_RIGHT)) {
        	isNext = true;
        	arg1.enterState(-5);
        } else if (input.isKeyPressed(Input.KEY_LEFT)) {
        	//System.exit(0);
        	isBak = true;
        	arg1.enterState(-3);
        } else {
        	isBak = isNext = false;
        }
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return -4;
	}

}
