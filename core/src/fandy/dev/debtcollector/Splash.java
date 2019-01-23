package fandy.dev.debtcollector;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class Splash implements Screen {
    private SpriteBatch batch;
    private Game myGame;
    private Texture texture;
    private OrthographicCamera camera;
    private long startTime;
    private int rendCount;
    private int scaleSplashX;
    private int scaleSplashY;
    private Music Musik;


    public Splash(Game g)
    {
        Gdx.app.log("my Spash Screen", "constructor called");
        myGame = g; // ** get Game parameter **//
        Musik = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.app.log("my Splash Screen", "show called");
        texture = new Texture(Gdx.files.internal("logostudio.png")); //** texture is now the splash image **//
        startTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture, Gdx.graphics.getWidth()/2-Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2-Gdx.graphics.getHeight()/4,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        batch.end();
        rendCount++;
        if (TimeUtils.millis()>(startTime+5000)) myGame.setScreen(new MyMainScreen(myGame,Musik));
    }

    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }
}
