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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class MainScreenLobbyWLANClient implements Screen {
    private Color colorbgblack;
    private SpriteBatch batch;
    private SpriteBatch bgblackbatch;
    private Game myGame;
    private Texture texture;
    Texture chooseSideChecked;
    Texture chooseSide;
    Texture inactiveButtonDebtCollector;
    Texture inactiveButtonDebtMaker;
    Texture activeButtonDebtCollector;
    Texture activeButtonDebtMaker;
    Texture activeButtonOK;
    Texture activeButtonReady;
    Texture inactiveButtonOK;
    Texture inactiveButtonReady;
    Texture BGBlack;
    Texture DCkoneksi;
    Texture RoomFull;
    private OrthographicCamera camera;
    private Music music;
    private Sound klik;
    private Kryo kryo;
    private String IP;
    private Client client;
    private String address;
    private Stage stage;
    private SomeDCResponse DCresponse;
    private TCPAlive tcpAlive;
    private DebtCollector debtCollector;
    private DebtMaker debtMaker;
    private String namaLobi;
    private ArrayList<Label> labelChooseSide;
    private Label label2;
    private Label labelOnlinePlayer;
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle labelStyle2;
    private Label.LabelStyle labelStyleChooseSide;
    private Label labelBaru;
    private String namaPlayerClient;
    private Klien klien;
    private KlienArray klienArray;
    private int diskonekserper=0;
    private int roompenuh=0;
    private int colectormaker = 0;
    private Listener listener;
    InetAddress address2;
    String myip;

    public MainScreenLobbyWLANClient(Game g, Music musik, final String adr, final String namaPlayer) // ** constructor called initially **//
    {
        klien = new Klien();

        debtCollector = new DebtCollector();
        ((DebtCollector) debtCollector).nama = new String[2];
        ((DebtCollector) debtCollector).ip = new String[2];
        ((DebtCollector) debtCollector).ready = new boolean[2];

        debtMaker = new DebtMaker();
        ((DebtMaker) debtMaker).nama = new String[4];
        ((DebtMaker) debtMaker).ip = new String[4];
        ((DebtMaker) debtMaker).ready = new boolean[4];

        klienArray = new KlienArray();
        ((KlienArray) klienArray).conclient = new String[6];
        ((KlienArray) klienArray).namaklien = new String[6];
        ((KlienArray) klienArray).alamatipklien = new String[6];

        klien.conclient = new ArrayList<Connection>();
        klien.namaklien = new ArrayList<String>();
        klien.alamatipklien = new ArrayList<String>();

        stage = new Stage();
        bgblackbatch = new SpriteBatch();
        colorbgblack = bgblackbatch.getColor();
        Gdx.app.log("my Main Screen", "constructor called");
        myGame = g; // ** get Game parameter **//
        namaPlayerClient = namaPlayer;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        music = musik;
        //music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
        //music.setLooping(true);
        //music.setVolume(0.5f);
        //music.play();
        klik = Gdx.audio.newSound(Gdx.files.internal("music/click.mp3"));

        address = new String();
        address = ""+adr.split("-")[1];
        address = ""+address.split("/")[1];

        namaLobi = ""+adr.split("-")[0];

        Gdx.app.log("my Main Screen", adr+"constructor called");

        client = new Client();
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
        kryo.register(DataHeroes.class);
        kryo.register(PosisiLove.class);
        kryo.register(PosisiCoin.class);
        kryo.register(TabrakCoin.class);
        //kryo.register(java.util.ArrayList.class);
        //kryo.register(com.esotericsoftware.kryonet.Connection.class);
        kryo.register(com.esotericsoftware.kryonet.Server.class);
        //kryo.register(com.esotericsoftware.kryonet.Client.class);
        //kryo.register(com.esotericsoftware.kryonet.Connection[].class);



        client.start();
        try {
            client.connect(5000, address, 27960, 27960);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Gdx.app.log("Client: " + adr, "MASUK PAK EKO Connected");
        SomeRequest request = new SomeRequest();
        request.text = ""+address+"-"+namaPlayer;
        client.sendTCP(request);/*

        DebtCollector3 debtCollector = new DebtCollector3();

        debtCollector.nama = ""+namaPlayer;
        debtCollector.ip = ""+Utils.getIPAddress(true);
        client.sendTCP(debtCollector);

        DebtMaker3 debtMaker3 = new DebtMaker3();

        debtMaker3.nama = ""+namaPlayer;
        debtMaker3.ip = ""+Utils.getIPAddress(true);
        client.sendTCP(debtMaker3);*/
        List<String> addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // Print the contents of our array to a string.  Yeah, should have used StringBuilder
        String ipAddress = new String("");
        int banyak = 0;
        for(String str:addresses)
        {
            ipAddress = ipAddress + str + "-";
            banyak++;
			Gdx.app.log("Client: " + str.toString().split("\\.")[0], "MASUK PAK EKO Connected");
            if(address.toString().split("\\.")[0].equals(str.toString().split("\\.")[0])){
                myip = str;
            }
        }

            //myip = ""+ipAddress.split("-")[banyak-1];
            //myip = address2.getHostAddress();

            System.out.println("IP Address = " + myip);
        System.out.println("IP Address = " + ipAddress);

        activeButtonDebtCollector = new Texture("DebtCollectorFlagClicked.png");
        activeButtonDebtMaker = new Texture("DebtMakerFlagClicked.png");
        activeButtonOK = new Texture("MenuButtonActiveOK.png");
        activeButtonReady = new Texture("MenuButtonActiveReady.png");

        inactiveButtonDebtCollector = new Texture("DebtCollectorFlag.png");
        inactiveButtonDebtMaker = new Texture("DebtMakerFlag.png");
        inactiveButtonOK = new Texture("MenuButtonInactiveOK.png");
        inactiveButtonReady = new Texture("MenuButtonInactiveReady.png");

        chooseSide = new Texture("ChooseSide.png");
        RoomFull = new Texture("RoomFull.png");
        BGBlack = new Texture("BlackBG.png");
        DCkoneksi = new Texture("DCkoneksi.png");

        chooseSideChecked = new Texture("ChooseSideChecked.png");

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        labelStyle.fontColor = Color.BLACK;

        label2 = new Label("Lobby Name: "+namaLobi+"\nPlayer Name: "+namaPlayer,labelStyle);
        float fontScalex = (3.0f*Gdx.graphics.getWidth()/2880);
        float fontScaley = (3.0f*Gdx.graphics.getHeight()/1440);
        label2.setFontScale(fontScalex,fontScaley);
        //label2.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        float posX = (2200*Gdx.graphics.getWidth()/2880);
        float posy = (1350*Gdx.graphics.getHeight()/1440);
        Gdx.app.log("MENU REQ: "+posX, "Connected");
        label2.setPosition(200*Gdx.graphics.getWidth()/2880,1300*Gdx.graphics.getHeight()/1440);
        stage.addActor(label2);

        labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        labelStyle2.fontColor = Color.GREEN;
        labelOnlinePlayer = new Label("Online Player: ",labelStyle2);
        labelOnlinePlayer.setFontScale(fontScalex,fontScaley);

        labelOnlinePlayer.setPosition(2100*Gdx.graphics.getWidth()/2880,1360*Gdx.graphics.getHeight()/1440);

        //labelOnlinePlayer.setText(labelOnlinePlayer.getText()+"\n"+namaHost);
        stage.addActor(labelOnlinePlayer);


        labelStyleChooseSide = new Label.LabelStyle();
        labelStyleChooseSide.font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        labelStyleChooseSide.fontColor = Color.WHITE;

        labelChooseSide = new ArrayList<Label>();
        for(int i=0;i<6;i++) {
            labelBaru = new Label("Join slot", labelStyleChooseSide);
            labelBaru.setFontScale((3.0f*Gdx.graphics.getWidth()/2880), (3.0f*Gdx.graphics.getHeight()/1440));
            labelBaru.setSize((400*Gdx.graphics.getWidth()/2880)/3,(100*Gdx.graphics.getHeight()/1440)/3);
            labelChooseSide.add(labelBaru);
        }

        labelChooseSide.get(0).setPosition(500*Gdx.graphics.getWidth()/2880,700*Gdx.graphics.getHeight()/1440);
        labelChooseSide.get(1).setPosition(500*Gdx.graphics.getWidth()/2880,600*Gdx.graphics.getHeight()/1440);
        labelChooseSide.get(2).setPosition(1950*Gdx.graphics.getWidth()/2880,700*Gdx.graphics.getHeight()/1440);
        labelChooseSide.get(3).setPosition(1950*Gdx.graphics.getWidth()/2880,600*Gdx.graphics.getHeight()/1440);
        labelChooseSide.get(4).setPosition(1950*Gdx.graphics.getWidth()/2880,500*Gdx.graphics.getHeight()/1440);
        labelChooseSide.get(5).setPosition(1950*Gdx.graphics.getWidth()/2880,400*Gdx.graphics.getHeight()/1440);

        for(int i=0;i<6;i++) {
            stage.addActor(labelChooseSide.get(i));
        }

        Gdx.input.setCatchBackKey(true);

        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) )

                // Maybe perform other operations before exiting
                {
                    /*DCresponse = new SomeDCResponse();
                    DCresponse.text = ""+address;
                    DCresponse.namaPlayer = namaPlayer;
                    client.sendTCP(DCresponse);
                    Gdx.app.log("Client: " + address+"--"+namaPlayer, "DC PAK EKO");*/
                    stage.dispose();
                    client.stop();
                    myGame.setScreen(new MainScreenLobbyWLAN(myGame, music));
                }
                return false;
            }
        };


        InputMultiplexer multiplexer = new InputMultiplexer(stage,
                backProcessor);
        Gdx.input.setInputProcessor(multiplexer);

        client.addListener(listener = new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof GotoChooseHeroes) {
                    GotoChooseHeroes gotoChooseHeroes = (GotoChooseHeroes)object;
                    if(gotoChooseHeroes.gas) {
                        //stage.dispose();
                        client.removeListener(listener);
                        Timer timer = new Timer();
                        timer.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                                            @Override
                                            public void run() {
                                                myGame.setScreen(new MainScreenChooseHeroesClient(myGame, music, client, namaLobi, namaPlayerClient, debtCollector, debtMaker, klienArray, klien,address));
                                            }
                                        }
                                , 0.1f);
                    }
                }
                if (object instanceof OnlinePlayer3) {
                    OnlinePlayer3 onlinePlayer3 = (OnlinePlayer3)object;

                    int onlinePlayer = onlinePlayer3.nama.length() - onlinePlayer3.nama.replaceAll("\n","").length();
                    Gdx.app.log("OL PLAYER: " + onlinePlayer3.nama, "OK");
                    labelOnlinePlayer.setText(onlinePlayer3.nama);
                    labelOnlinePlayer.setPosition(2100*Gdx.graphics.getWidth()/2880,((1360-(onlinePlayer*25))*Gdx.graphics.getHeight()/1440));
                }
                if (object instanceof DebtCollector) {
                    DebtCollector debtCollector2 = (DebtCollector)object;

                    for(int i=0;i<getLength(debtCollector2.nama);i++) {
                        Gdx.app.log("Isi DebtCollect ARRAY"+i, debtCollector2.nama[i]+"");
                    }

                    debtCollector = debtCollector2;

                    for(int i=0;i<debtCollector2.ip.length;i++) {
                        labelChooseSide.get(i).setText(debtCollector2.nama[i]);
                        labelChooseSide.get(i).setColor(Color.YELLOW);
                        if(debtCollector2.nama[i] == null){
                            labelChooseSide.get(i).setText("Join slot");
                            labelChooseSide.get(i).setColor(Color.WHITE);
                        }
                    }

                }

                if (object instanceof DebtMaker) {
                    DebtMaker debtMaker2 = (DebtMaker)object;

                    for(int i=0;i<getLength(debtMaker2.nama);i++) {
                        Gdx.app.log("Isi DebtMaker ARRAY"+i, debtMaker2.nama[i]+"");
                    }

                    debtMaker = debtMaker2;

                    for(int i=0;i<debtMaker2.ip.length;i++) {
                        labelChooseSide.get(i+2).setText(debtMaker2.nama[i]);
                        labelChooseSide.get(i+2).setColor(Color.YELLOW);
                        if(debtMaker2.nama[i] == null){
                            labelChooseSide.get(i+2).setText("Join slot");
                            labelChooseSide.get(i+2).setColor(Color.WHITE);
                        }
                    }


                }
                if(object instanceof KlienArray){
                    KlienArray klienArray2 = (KlienArray)object;

                    for(int i=0;i<getLength(klienArray2.namaklien);i++) {
                        Gdx.app.log("Isi Klien ARRAY"+i, klienArray2.namaklien[i]+"");
                    }
                }
            }
            public void disconnected (Connection connection) {
                client.close();
                Gdx.app.log("my Main Screen", "you have been disconnected");

                Timer timer = new Timer();
                timer.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                                    @Override
                                    public void run() {
                                        diskonekserper=1;
                                        //myGame.setScreen(new MainScreenLobbyWLAN(myGame, false));
                                    }
                                }
                        , 5);
            }
        });



    }

    public static <T> int getLength(T[] arr){
        int count = 0;
        for(T el : arr)
            if (el != null)
                ++count;
        return count;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(chooseSide, labelChooseSide.get(0).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(0).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(0).getHeight() / 2, labelChooseSide.get(0).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(0).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        batch.draw(chooseSide, labelChooseSide.get(1).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(1).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(1).getHeight() / 2, labelChooseSide.get(1).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(1).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        batch.draw(chooseSide, labelChooseSide.get(2).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(2).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(2).getHeight() / 2, labelChooseSide.get(2).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(2).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        batch.draw(chooseSide, labelChooseSide.get(3).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(3).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(3).getHeight() / 2, labelChooseSide.get(3).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(3).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        batch.draw(chooseSide, labelChooseSide.get(4).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(4).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(4).getHeight() / 2, labelChooseSide.get(4).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(4).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        batch.draw(chooseSide, labelChooseSide.get(5).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(5).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(5).getHeight() / 2, labelChooseSide.get(5).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(5).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);

        if (debtCollector.ready[0]==true){
            batch.draw(chooseSideChecked, labelChooseSide.get(0).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(0).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(0).getHeight() / 2, labelChooseSide.get(0).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(0).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        }
        if (debtCollector.ready[1]==true){
            batch.draw(chooseSideChecked, labelChooseSide.get(1).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(1).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(1).getHeight() / 2, labelChooseSide.get(1).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(1).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        }
        if (debtMaker.ready[0]==true){
            batch.draw(chooseSideChecked, labelChooseSide.get(2).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(2).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(2).getHeight() / 2, labelChooseSide.get(2).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(2).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        }
        if (debtMaker.ready[1]==true){
            batch.draw(chooseSideChecked, labelChooseSide.get(3).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(3).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(3).getHeight() / 2, labelChooseSide.get(3).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(3).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        }
        if (debtMaker.ready[2]==true){
            batch.draw(chooseSideChecked, labelChooseSide.get(4).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(4).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(4).getHeight() / 2, labelChooseSide.get(4).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(4).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        }
        if (debtMaker.ready[3]==true){
            batch.draw(chooseSideChecked, labelChooseSide.get(5).getX() - 50 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(5).getY() - (12.5f * Gdx.graphics.getHeight() / 1440) - labelChooseSide.get(5).getHeight() / 2, labelChooseSide.get(5).getWidth() + 400 * Gdx.graphics.getWidth() / 2880, labelChooseSide.get(5).getHeight() + 50f * Gdx.graphics.getHeight() / 1440);
        }

        //button Ready
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()*0.5f)-(Gdx.graphics.getWidth()/10) || Gdx.input.getX() > (Gdx.graphics.getWidth()*0.5f)+(Gdx.graphics.getWidth()/10)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()*0.9f)-(Gdx.graphics.getHeight()/20) || Gdx.input.getY() > (Gdx.graphics.getHeight()*0.9f)+(Gdx.graphics.getHeight()/20))
        {
            batch.draw(inactiveButtonReady, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
        } else {
            if(Gdx.input.justTouched() && diskonekserper==0 && roompenuh==0){
                klik.play();

                ClientReady clientReady = new ClientReady();
                ClientReady2 clientReady2 = new ClientReady2();
                int targetrede = 0;

                for(int x=0; x<debtCollector.ip.length;x++){
                    Gdx.app.log("my Main Screen", "you have been disconnected");
                    if(debtCollector.ip[x] != null)
                    if(debtCollector.ip[x].equals(myip)) {
                        colectormaker=1;
                        targetrede = x;
                        break;
                    }
                    colectormaker=-1;

                }
                Gdx.app.log("READY", "target:"+targetrede+" colekmarker: "+colectormaker+ ' '+ debtCollector.ready[targetrede]+" ini ready");

                Gdx.app.log("READY", "ip:"+debtMaker.ip[0]+" target:"+myip);
                if(colectormaker==-1)
                for(int x=0; x<debtMaker.ip.length;x++){
                    if(debtMaker.ip[x]!=null)
                    if(debtMaker.ip[x].equals(myip)) {
                        colectormaker=0;
                        targetrede = x;
                        break;
                    }
                    colectormaker=-1;
                }

                Gdx.app.log("READY", "target:"+targetrede+" colekmarker: "+colectormaker+ ' '+ debtMaker.ready[targetrede]+" ini ready");

                if(colectormaker==1) {
                    if (!debtCollector.ready[targetrede]) {
                        clientReady.klienrede = true;
                        Gdx.app.log("READY", debtCollector.ready[targetrede]+" masuk ready");
                    }
                    if (debtCollector.ready[targetrede]) {
                        clientReady.klienrede = false;
                        Gdx.app.log("READY", debtCollector.ready[targetrede]+" masuk ready");
                    }
                    client.sendTCP(clientReady);
                }else if(colectormaker==0){
                    if (!debtMaker.ready[targetrede]) clientReady2.klienrede = true;
                    if (debtMaker.ready[targetrede]) clientReady2.klienrede = false;
                    client.sendTCP(clientReady2);
                }else {
                    Gdx.app.log("READY", "ready not found");
                }



            }
            if(Gdx.input.isTouched() && diskonekserper==0 && roompenuh==0)
            {
                batch.draw(activeButtonReady, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
            }
            else
                batch.draw(inactiveButtonReady, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
        }

        //button debt collector
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/8) || Gdx.input.getX() > (Gdx.graphics.getWidth()/4)+(Gdx.graphics.getWidth()/8)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()*0.35f)-(Gdx.graphics.getHeight()/8) || Gdx.input.getY() > (Gdx.graphics.getHeight()*0.35f)+(Gdx.graphics.getHeight()/8))
        {
            batch.draw(inactiveButtonDebtCollector, (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()*0.65f)-(Gdx.graphics.getHeight()/8),Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        } else {
            if(Gdx.input.justTouched() && diskonekserper==0 && roompenuh==0){
                klik.play();
                int cocok = 0;
                int count = 0;
                int i=0;

                DebtCollector3 debtCollector = new DebtCollector3();

                debtCollector.nama = ""+namaPlayerClient;
                debtCollector.ip = ""+myip;
                client.sendTCP(debtCollector);

            }
            if(Gdx.input.isTouched() && diskonekserper==0 && roompenuh==0)
            {
                batch.draw(activeButtonDebtCollector, (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()*0.65f)-(Gdx.graphics.getHeight()/8),Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
            }
            else
                batch.draw(inactiveButtonDebtCollector, (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()*0.65f)-(Gdx.graphics.getHeight()/8),Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        }

        //button debt maker
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()*0.75f)-(Gdx.graphics.getWidth()/8) || Gdx.input.getX() > (Gdx.graphics.getWidth()*0.75f)+(Gdx.graphics.getWidth()/8)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()*0.35f)-(Gdx.graphics.getHeight()/8) || Gdx.input.getY() > (Gdx.graphics.getHeight()*0.35f)+(Gdx.graphics.getHeight()/8))
        {
            batch.draw(inactiveButtonDebtMaker, (Gdx.graphics.getWidth()*0.75f)-(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()*0.65f)-(Gdx.graphics.getHeight()/8),Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        } else {
            if(Gdx.input.justTouched() && diskonekserper==0 && roompenuh==0){
                klik.play();
                int cocok = 0;
                int count = 0;
                int i=0;

                DebtMaker3 debtMaker3 = new DebtMaker3();

                debtMaker3.nama = ""+namaPlayerClient;
                debtMaker3.ip = ""+myip;
                client.sendTCP(debtMaker3);

            }
            if(Gdx.input.isTouched() && diskonekserper==0 && roompenuh==0)
            {
                batch.draw(activeButtonDebtMaker, (Gdx.graphics.getWidth()*0.75f)-(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()*0.65f)-(Gdx.graphics.getHeight()/8),Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
            }
            else
                batch.draw(inactiveButtonDebtMaker, (Gdx.graphics.getWidth()*0.75f)-(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()*0.65f)-(Gdx.graphics.getHeight()/8),Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        }



        batch.end();
        stage.draw();

        //koneksi DC
        if(diskonekserper==1) {

            bgblackbatch.begin();
            //bgblack
            bgblackbatch.setColor(colorbgblack.r, colorbgblack.g, colorbgblack.b, 0.6f);
            bgblackbatch.draw(BGBlack, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            bgblackbatch.end();

            batch.begin();

            batch.draw(DCkoneksi, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 4), (Gdx.graphics.getHeight() * 0.50f) - (Gdx.graphics.getHeight() / 4), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

            //button ok
            if (Gdx.input.getX() < (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10) || Gdx.input.getX() > (Gdx.graphics.getWidth() * 0.5f) + (Gdx.graphics.getWidth() / 10)
                    || Gdx.input.getY() < (Gdx.graphics.getHeight() * 0.60f) - (Gdx.graphics.getHeight() / 20) || Gdx.input.getY() > (Gdx.graphics.getHeight() * 0.60f) + (Gdx.graphics.getHeight() / 20)) {
                batch.draw(inactiveButtonOK, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.40f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
            } else {
                if (Gdx.input.justTouched()) {
                    klik.play();
                    myGame.setScreen(new MainScreenLobbyWLAN(myGame, music));

                }
                if (Gdx.input.isTouched()) {
                    batch.draw(activeButtonOK, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.40f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
                } else
                    batch.draw(inactiveButtonOK, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.40f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
            }
            batch.end();
        }

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

    public int getposarray(String[] arrneed, String str){
        int index = -1;
        for (int i=0;i<arrneed.length;i++) {
            if (arrneed[i].equals(str)) {
                index = i;
                break;
            }
        }
        return index;
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