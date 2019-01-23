package fandy.dev.debtcollector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class GameMain extends Game {
	private int rendCount; //** render count **//
	private long startTime; //** time app started **//
	private long endTime; //** time app ended **//

	
	@Override
	public void create () {
		Gdx.app.log("my Splash Game","App created");
		startTime = TimeUtils.millis();
		setScreen(new Splash(this)); //** start SpashSreen, with Game parameter **//
		//setScreen(new GameplayScreen());
	}

	@Override
	public void render () {
		super.render();
		rendCount++; //** another render - inc count **//
	}
	
	@Override
	public void dispose () {
		Gdx.app.log("my Splash Game", "App rendered " + rendCount + " times");
		Gdx.app.log("my Splash Game", "App ended");
		endTime = TimeUtils.millis();
		Gdx.app.log("my Splash Game", "App running for " + (endTime-startTime)/1000 + " seconds.");
	}
}
