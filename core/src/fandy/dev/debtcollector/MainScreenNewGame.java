package fandy.dev.debtcollector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainScreenNewGame implements Screen {
    private SpriteBatch batch;
    private Game myGame;
    private Texture texture;
    //Texture activeButtonSingle;
    //Texture activeButtonOnline;
    Texture activeButtonWLAN;
    Texture inactiveButtonSingle;
    Texture inactiveButtonOnline;
    Texture inactiveButtonWLAN;
    private OrthographicCamera camera;
    private Music music;
    private Sound klik;
    private Stage stage;

    public MainScreenNewGame(Game g, Music musik) // ** constructor called initially **//
    {
        stage = new Stage();
        Gdx.app.log("my Main Screen", "constructor called");
        myGame = g; // ** get Game parameter **//
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        music = musik;
        //music.setLooping(true);
        //music.setVolume(0.5f);
        //music.play();
        klik = Gdx.audio.newSound(Gdx.files.internal("music/click.mp3"));

        //activeButtonSingle = new Texture("MenuButtonActiveSingleCoomingSoon.png");
        inactiveButtonSingle = new Texture("MenuButtonInactiveSingleCoomingSoon.png");
        //activeButtonOnline = new Texture("MenuButtonActiveOptions.png");
        inactiveButtonOnline = new Texture("MenuButtonInactiveOnlineCoomingSoon.png");
        activeButtonWLAN = new Texture("MenuButtonActiveWLAN.png");
        inactiveButtonWLAN = new Texture("MenuButtonInactiveWLAN.png");

        Gdx.input.setCatchBackKey(true);

        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) )

                // Maybe perform other operations before exiting
                {
                    myGame.setScreen(new MyMainScreen(myGame, music));
                }
                return false;
            }
        };


        InputMultiplexer multiplexer = new InputMultiplexer(stage,
                backProcessor);
        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //button nav back
        /*if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            myGame.setScreen(new MyMainScreen(myGame,false));
        }*/

        //button single
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16) || Gdx.input.getX() > (Gdx.graphics.getWidth()/2)+(Gdx.graphics.getWidth()/16)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20) || Gdx.input.getY() > (Gdx.graphics.getHeight()/2)+(Gdx.graphics.getHeight()/20))
        {
            batch.draw(inactiveButtonSingle, (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        } else {
            if(Gdx.input.justTouched())klik.play();
            batch.draw(inactiveButtonSingle, (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        }

        //button online
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16) || Gdx.input.getX() > (Gdx.graphics.getWidth()/2)+(Gdx.graphics.getWidth()/16)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()*0.66f)-(Gdx.graphics.getHeight()/20) || Gdx.input.getY() > (Gdx.graphics.getHeight()*0.66f)+(Gdx.graphics.getHeight()/20))
        {
            batch.draw(inactiveButtonOnline, (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/3)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        } else {
            if(Gdx.input.justTouched())klik.play();
            batch.draw(inactiveButtonOnline, (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/3)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        }

        //button WLAN
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16) || Gdx.input.getX() > (Gdx.graphics.getWidth()/2)+(Gdx.graphics.getWidth()/16)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()*0.83f)-(Gdx.graphics.getHeight()/20) || Gdx.input.getY() > (Gdx.graphics.getHeight()*0.83f)+(Gdx.graphics.getHeight()/20))
        {
            batch.draw(inactiveButtonWLAN, (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/6)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
        } else {
            if(Gdx.input.justTouched()){
                klik.play();
                myGame.setScreen(new MainScreenLobbyWLAN(myGame,music));
            }
            batch.draw(activeButtonWLAN, (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/6)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
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