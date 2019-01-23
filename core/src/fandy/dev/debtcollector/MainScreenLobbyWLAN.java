package fandy.dev.debtcollector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import com.badlogic.gdx.utils.Timer;

public class MainScreenLobbyWLAN extends Listener implements Screen {
        private SpriteBatch batch;
        private Game myGame;
        private Texture texture;
        //Texture activeButtonSingle;
        //Texture activeButtonOnline;
        Texture TitleLobby;
        Texture DaftarLobby;
        Texture TitleHost;
        Texture ActiveCreateHost;
        Texture InactiveCreateHost;
        private OrthographicCamera camera;
        private Music music;
        private Sound klik;
        private Socket socket;
        static Server server;
        static final int port = 27960;
        static Map<Integer, Klien> klien = new HashMap<Integer, Klien>();
        private TextField textfieldHost;
        private TextField textfieldPlayer;
        private TextButton buttonDaftarLobby;
        private Skin skin;
        private Stage stage;
        private Table root;
        private Label labelbaru;
        private ArrayList same = new ArrayList();
        private String namaLobby;
        private Timer timer;
        private String adr;
        private int i;
        private int z=0;
        private TextButton[] buttonLobi = new TextButton[30];
        private Input.TextInputListener textListenerHost;
        private Input.TextInputListener textListenerPlayer;
        private int roompenuh = 0;
        Texture RoomFull;
        Texture BGBlack;
        Texture activeButtonOK;
        Texture inactiveButtonOK;
        private SpriteBatch bgblackbatch;
        private Color colorbgblack;
        private InetAddress address;
        private String responfullngga;
        private int pencetbuttonz = 1;


    public MainScreenLobbyWLAN(Game g, Music musik)// ** constructor called initially **//
        {
            bgblackbatch = new SpriteBatch();
            colorbgblack = bgblackbatch.getColor();
            Gdx.app.log("my Main Screen", "constructor called");
            myGame = g; // ** get Game parameter **//
            stage = new Stage();
            Gdx.input.setInputProcessor(stage);
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch = new SpriteBatch();

            klik = Gdx.audio.newSound(Gdx.files.internal("music/click.mp3"));
            music = musik;
            if(!music.isPlaying()){
                music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
                music.setVolume(0.5f);
                music.setLooping(true);
                music.play();
            }

            TitleLobby = new Texture("TitleLobby.png");
            DaftarLobby = new Texture("DaftarLobby.png");
            TitleHost = new Texture("Host.png");
            ActiveCreateHost = new Texture("MenuButtonActiveCreateHost.png");
            InactiveCreateHost = new Texture("MenuButtonInactiveCreateHost.png");
            RoomFull = new Texture("RoomFull.png");
            BGBlack = new Texture("BlackBG.png");
            inactiveButtonOK = new Texture("MenuButtonInactiveOK.png");
            activeButtonOK = new Texture("MenuButtonActiveOK.png");

            skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
            skin.getFont("default-font").getData().setScale(2.0f*Gdx.graphics.getWidth()/2880,2.0f*Gdx.graphics.getHeight()/1440);

            buttonDaftarLobby = new TextButton("",skin);
            final Preferences prefs = Gdx.app.getPreferences("My Preferences");
            prefs.flush();


            if(prefs.getString("nama") != "")
                textfieldPlayer = new TextField(prefs.getString("nama"),skin);
            else
                textfieldPlayer = new TextField("My Name",skin);
            if(prefs.getString("host") != "")
                textfieldHost = new TextField(prefs.getString("host"),skin);
            else
                textfieldHost = new TextField("Lobby Saya",skin);

            textfieldHost.setMaxLength(15);
            textfieldHost.setAlignment(1);
            textfieldPlayer.setMaxLength(15);
            textfieldPlayer.setAlignment(1);
            textfieldHost.setPosition((Gdx.graphics.getWidth()*0.85f)-(Gdx.graphics.getWidth()/10),(Gdx.graphics.getHeight()/2.75f)-(Gdx.graphics.getWidth()/32));
            textfieldPlayer.setPosition(textfieldHost.getX(),textfieldHost.getY()-(Gdx.graphics.getWidth()/32));
            textfieldHost.setSize((Gdx.graphics.getWidth()/5),(Gdx.graphics.getHeight()/16));
            textfieldPlayer.setSize((Gdx.graphics.getWidth()/5),(Gdx.graphics.getHeight()/16));

            textListenerHost = new Input.TextInputListener()
            {
                @Override
                public void input(String input)
                {
                    textfieldHost.setText(input);
                    prefs.putString("host", input);
                    prefs.flush();

                }

                @Override
                public void canceled()
                {
                    System.out.println("Aborted");
                }
            };

            textListenerPlayer = new Input.TextInputListener()
            {
                @Override
                public void input(String input)
                {
                    textfieldPlayer.setText(input);
                    prefs.putString("nama", input);
                    prefs.flush();

                }

                @Override
                public void canceled()
                {
                    System.out.println("Aborted");
                }
            };

            textfieldHost.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    stage.unfocusAll();
                    Gdx.input.getTextInput(textListenerHost, "Lobby Name: ", textfieldHost.getText(), "My Lobby");
                }
            });

            textfieldPlayer.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    stage.unfocusAll();
                    Gdx.input.getTextInput(textListenerPlayer, "Player Name: ", textfieldPlayer.getText(), "My Name");
                }
            });

            stage.addActor(textfieldHost);
            stage.addActor(textfieldPlayer);

            server = new Server();
            //server.getKryo().register(Lobby.class);
            //server.getKryo().register(SomeRequest.class);
            //server.getKryo().register(SomeResponse.class);

            //Gdx.app.log("SocketIO", "The server is ready ");

            root = new Table();
            root.setPosition((Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/5f), (Gdx.graphics.getHeight()/4)-(Gdx.graphics.getHeight()/5f));
            root.setSize(Gdx.graphics.getWidth()/2.5f,Gdx.graphics.getHeight()/2.5f);
            stage.addActor(root);
            skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
            skin.getFont("default-font").getData().setScale(3.0f*Gdx.graphics.getWidth()/2880,3.0f*Gdx.graphics.getHeight()/1440);
            final Table labels = new Table();
            root.add(new ScrollPane(labels, skin)).expand().fill();
            root.row();
            /*for (int i = 0; i < 25; i++) {
                labels.add(new TextButton("Lobby: " + i +" "+ address, skin) {

                    public void draw(Batch batch, float parentAlpha) {
                        super.draw(batch, parentAlpha);
                        //label++;
                    }
                });
                labels.row();
            }*/


            final Client client = new Client();
            timer=new Timer();

            timer.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                            @Override
                            public void run() {
                                labels.clear();
                                same.clear();
                            }
                        }
                    , 0 ,15);

            timer.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                                @Override
                                public void run() {
                                        address = client.discoverHost(27960,500);



                                    int buat = 0;
                                    do{
                                            /*if (same.get(i).equals("" + address)) {

                                            } else*/
                                        if(same.size() == 0){
                                            if (address != null) {
                                                buat++;

                                                Gdx.app.log("OK: ", "Connected");
                                            }
                                        }
                                        else {
                                            if(i < same.size())
                                                if (address != null && !same.get(i).equals("" + address)) {

                                                    buat++;
                                                }
                                        }/*else {
                                                same.clear();
                                                labels.clear();
                                            }*/


                                        //Gdx.app.log("MENU LOBI: ", "same: " + same.get(i));
                                        i++;
                                    } while(i<=same.size());
                                    Gdx.app.log("BUAT: " + buat, "Connected");
                                    Gdx.app.log("SIZE: " + same.size(), "Connected");
                                    if(address != null && (buat == same.size()+1 && same.size() == 0)|| address != null && buat == same.size()){
                                        Gdx.app.log("MENU LOBI: " + adr, "Connected");
                                        same.add("" + address);
                                        if(same.size() == 1)
                                            adr = ""+same.get(buat-1);
                                        else
                                            adr = ""+same.get(buat);
                                        adr = adr.split("/")[1];
                                        labels.row();

                                        Kryo kryo = client.getKryo();
                                        kryo.register(SomeRequest.class);
                                        kryo.register(SomeResponse.class);
                                        kryo.register(DebtCollector3.class);
                                        kryo.register(DebtMaker3.class);
                                        kryo.register(String[].class);
                                        kryo.register(DebtCollector.class);
                                        kryo.register(DebtMaker.class);
                                        kryo.register(KlienArray.class);
                                        kryo.register(OnlinePlayer3.class);
                                        kryo.register(boolean[].class);
                                        kryo.register(ClientReady.class);
                                        kryo.register(ClientReady2.class);
                                        kryo.register(DaftarLobiFullorNot.class);
                                        kryo.register(int[].class);
                                        kryo.register(GotoChooseHeroes.class);
                                        kryo.register(MyHeroesNomor.class);
                                        kryo.register(ClientReadyChoose.class);
                                        kryo.register(float[].class);
                                        kryo.register(PosisiHero.class);
                                        kryo.register(TimerPickNow.class);
                                        kryo.register(LobiRoomPenuh.class);
                                        kryo.register(PosisiCar.class);
                                        //kryo.register(java.util.ArrayList.class);
                                        //kryo.register(com.esotericsoftware.kryonet.Connection.class);
                                        kryo.register(com.esotericsoftware.kryonet.Server.class);
                                        client.start();
                                        try {
                                            client.connect(5000, adr, 27960, 27960);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Gdx.app.log("SIZEK: " + adr, "Connected");
                                        SomeRequest request = new SomeRequest();
                                        request.text = ""+address;
                                        client.sendTCP(request);
                                    }

                                }
                                }
                , 0 ,1);
            /*client.start();
            try {
                client.connect(5000,"192.168.0.101",27960,27960);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            client.addListener(new Listener() {
                @Override
                public void received (Connection connection, Object object) {
                    if (object instanceof DaftarLobiFullorNot) {
                        DaftarLobiFullorNot response = (DaftarLobiFullorNot)object;
                        if(response.bolehngga) {
                                //stage.dispose();
                                timer.instance().clear();
                                client.close();
                                Gdx.app.log("LOBIbolehga: " + responfullngga, "Connected");
                            timer.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                                                @Override
                                                public void run() {

                                                    myGame.setScreen(new MainScreenLobbyWLANClient(myGame, music, responfullngga, textfieldPlayer.getText()));

                                                }
                                            }
                                    , 0);
                        }else {
                            roompenuh = 1;
                            pencetbuttonz=1;
                            client.close();

                        }
                    }
                    if (object instanceof SomeResponse) {
                        final SomeResponse response = (SomeResponse)object;
                        System.out.println(response.text);
                            labels.add(buttonLobi[z] = new TextButton(response.text, skin) {

                                public void draw(Batch batch, float parentAlpha) {
                                    super.draw(batch, parentAlpha);
                                    //label++;
                                }
                            });
                            buttonLobi[z].addListener(new ChangeListener() {
                                public void changed(ChangeEvent event, Actor actor) {
                                    if (pencetbuttonz == 1){
                                        pencetbuttonz=0;
                                        Kryo kryo = client.getKryo();
                                        kryo.register(SomeRequest.class);
                                        kryo.register(SomeResponse.class);
                                        kryo.register(DebtCollector3.class);
                                        kryo.register(DebtMaker3.class);
                                        kryo.register(String[].class);
                                        kryo.register(DebtCollector.class);
                                        kryo.register(DebtMaker.class);
                                        kryo.register(KlienArray.class);
                                        kryo.register(OnlinePlayer3.class);
                                        kryo.register(boolean[].class);
                                        kryo.register(ClientReady.class);
                                        kryo.register(ClientReady2.class);
                                        kryo.register(DaftarLobiFullorNot.class);
                                        //kryo.register(java.util.ArrayList.class);
                                        //kryo.register(com.esotericsoftware.kryonet.Connection.class);
                                        kryo.register(com.esotericsoftware.kryonet.Server.class);
                                        client.start();
                                        try {
                                            client.connect(5000, adr, 27960, 27960);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        DaftarLobiFullorNot request = new DaftarLobiFullorNot();
                                        request.isinya = "" + Utils.getIPAddress(true);
                                        connection.sendTCP(request);
                                        responfullngga = response.text;
                                    }
                                }
                            });
                            z++;
                            client.close();

                    }
                }
            });

            Gdx.input.setCatchBackKey(true);

            InputProcessor backProcessor = new InputAdapter() {
                @Override
                public boolean keyDown(int keycode) {

                    if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) )

                        // Maybe perform other operations before exiting
                    {
                        timer.instance().clear();
                        myGame.setScreen(new MainScreenNewGame(myGame, music));
                    }
                    return false;
                }
            };


            InputMultiplexer multiplexer = new InputMultiplexer(stage,
                    backProcessor);
            Gdx.input.setInputProcessor(multiplexer);

            //connectSocket();
            //configSocketEvents();

        }

        public void connectSocket() {
            try {
                socket = IO.socket("http://192.168.0.101:8080");
                socket.connect();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void configSocketEvents() {
            socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    //Gdx.app.log("SocketIO", "Connecting Error ");
                }
            });

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Gdx.app.log("SocketIO", "Connected");
                }
            }).on("socketID", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String id = data.getString("id");
                        Gdx.app.log("SocketIO", "My ID: " + id);
                    } catch (JSONException e) {
                        Gdx.app.log("SocketIO", "Error getting ID");
                    }

                }
            }).on("newPlayer", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String id = data.getString("id");
                        Gdx.app.log("SocketIO", "New Player Connect: " + id);
                    } catch (JSONException e) {
                        Gdx.app.log("SocketIO", "Error getting New Player ID");
                    }
                }
            });
        }



        @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            stage.act(delta);

            //button nav back
            /*if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                timer.instance().clear();
                myGame.setScreen(new MainScreenNewGame(myGame, false));
            }*/

            batch.draw(DaftarLobby, (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/4), (Gdx.graphics.getHeight()/4)-(Gdx.graphics.getHeight()/4),Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
            batch.draw(TitleLobby, (Gdx.graphics.getWidth()/2)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
            batch.draw(TitleHost, (Gdx.graphics.getWidth()*0.85f)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/2)-(Gdx.graphics.getWidth()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
            //button CreateHost
            if(Gdx.input.getX() < (Gdx.graphics.getWidth()*0.85f)-(Gdx.graphics.getWidth()/16) || Gdx.input.getX() > (Gdx.graphics.getWidth()*0.85f)+(Gdx.graphics.getWidth()/16)
                    || Gdx.input.getY() < (Gdx.graphics.getHeight()*0.83f)-(Gdx.graphics.getHeight()/20) || Gdx.input.getY() > (Gdx.graphics.getHeight()*0.83f)+(Gdx.graphics.getHeight()/20))
            {
                batch.draw(InactiveCreateHost, (Gdx.graphics.getWidth()*0.85f)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/6)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
            } else {
                if(Gdx.input.justTouched()){
                    klik.play();
                    //server.start();
                    namaLobby = textfieldHost.getText();
                    /*try {
                        server.bind(port, port);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    stage.dispose();
                    timer.instance().clear();
                    myGame.setScreen(new MainScreenLobbyWLANEnter(myGame,music ,server,namaLobby, textfieldPlayer.getText()));
                }
                batch.draw(ActiveCreateHost, (Gdx.graphics.getWidth()*0.85f)-(Gdx.graphics.getWidth()/16), (Gdx.graphics.getHeight()/6)-(Gdx.graphics.getHeight()/20),Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/10);
            }
            batch.end();
            stage.draw();

            //Room Penuh
            if(roompenuh==1) {
                bgblackbatch.begin();
                //bgblack
                bgblackbatch.setColor(colorbgblack.r, colorbgblack.g, colorbgblack.b, 0.6f);
                bgblackbatch.draw(BGBlack, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                bgblackbatch.end();

                batch.begin();

                batch.draw(RoomFull, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 4), (Gdx.graphics.getHeight() * 0.50f) - (Gdx.graphics.getHeight() / 4), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

                //button ok
                if (Gdx.input.getX() < (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10) || Gdx.input.getX() > (Gdx.graphics.getWidth() * 0.5f) + (Gdx.graphics.getWidth() / 10)
                        || Gdx.input.getY() < (Gdx.graphics.getHeight() * 0.60f) - (Gdx.graphics.getHeight() / 20) || Gdx.input.getY() > (Gdx.graphics.getHeight() * 0.60f) + (Gdx.graphics.getHeight() / 20)) {
                    batch.draw(inactiveButtonOK, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.40f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
                } else {
                    if (Gdx.input.justTouched()) {
                        klik.play();
                        roompenuh=0;

                    }
                    if (Gdx.input.isTouched()) {
                        batch.draw(activeButtonOK, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.40f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
                    } else
                        batch.draw(inactiveButtonOK, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.40f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
                }
                batch.end();
            }
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
            stage.dispose();

        }

}