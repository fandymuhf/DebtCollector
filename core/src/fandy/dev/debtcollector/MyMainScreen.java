package fandy.dev.debtcollector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyMainScreen implements Screen {
    private SpriteBatch batch;
    private Game myGame;
    private Texture texture;
    Texture activeButtonNewgame;
    Texture activeButtonOptions;
    Texture activeButtonAbout;
    Texture inactiveButtonNewgame;
    Texture inactiveButtonOptions;
    Texture inactiveButtonAbout;
    private OrthographicCamera camera;
    private Music music;
    private Sound klik;

    public MyMainScreen(Game g,Music musik) // ** constructor called initially **//
    {
        Gdx.app.log("my Main Screen", "constructor called");
        myGame = g; // ** get Game parameter **//
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        music = musik;
        if(!music.isPlaying()){
            music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
            music.setVolume(0.5f);
            music.setLooping(true);
            music.play();
        }
        klik = Gdx.audio.newSound(Gdx.files.internal("music/click.mp3"));

        activeButtonNewgame = new Texture("MenuButtonActiveNewGame.png");
        inactiveButtonNewgame = new Texture("MenuButtonInactiveNewGame.png");
        activeButtonOptions = new Texture("MenuButtonActiveOptions.png");
        inactiveButtonOptions = new Texture("MenuButtonInactiveOptions.png");
        activeButtonAbout = new Texture("MenuButtonActiveAbout.png");
        inactiveButtonAbout = new Texture("MenuButtonInactiveAbout.png");
        Gdx.input.setCatchBackKey(true);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //button newgame
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/16) || Gdx.input.getX() > (Gdx.graphics.getWidth()/4)+(Gdx.graphics.getWidth()/16)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20) || Gdx.input.getY() > (Gdx.graphics.getHeight()/2)+(Gdx.graphics.getHeight()/20))
        {
            batch.draw(inactiveButtonNewgame, (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        } else {
            if(Gdx.input.justTouched()){
                klik.play();
                myGame.setScreen(new MainScreenNewGame(myGame,music));
            }
            batch.draw(activeButtonNewgame, (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        }

        //button options
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()*0.75f)-(Gdx.graphics.getWidth()/16) || Gdx.input.getX() > (Gdx.graphics.getWidth()*0.75f)+(Gdx.graphics.getWidth()/16)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20) || Gdx.input.getY() > (Gdx.graphics.getHeight()/2)+(Gdx.graphics.getHeight()/20))
        {
            batch.draw(inactiveButtonOptions, (Gdx.graphics.getWidth()*0.75f)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        } else {
            if(Gdx.input.justTouched())klik.play();
            batch.draw(activeButtonOptions, (Gdx.graphics.getWidth()*0.75f)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        }

        //button about
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/16) || Gdx.input.getX() > (Gdx.graphics.getWidth()/4)+(Gdx.graphics.getWidth()/16)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()*0.66f)-(Gdx.graphics.getHeight()/20) || Gdx.input.getY() > (Gdx.graphics.getHeight()*0.66f)+(Gdx.graphics.getHeight()/20))
        {
            batch.draw(inactiveButtonAbout, (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/3)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        } else {
            if(Gdx.input.justTouched()){
                klik.play();
            }
            batch.draw(activeButtonAbout, (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/3)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.app.log("my Main Screen", "show called");
        texture = new Texture(Gdx.files.internal("MainmenuBG.png")); //** texture is now the main image **//
    }

    @Override
    public void hide() {
        Gdx.app.log("my Main Screen", "hide called");
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Gdx.app.log("my Main Screen", "dispose called");
        texture.dispose();
        batch.dispose();
        music.dispose();
        klik.dispose();
    }

}