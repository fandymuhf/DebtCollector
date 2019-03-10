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
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainScreenLobbyWLANEnter implements Screen {
    private SpriteBatch bgblackbatch;
    private Color colorbgblack;
    private ArrayList<Label> labelChooseSide;
    private SpriteBatch batch;
    private Game myGame;
    private Texture texture;
    Texture chooseSideChecked;
    Texture chooseSide;
    Texture inactiveButtonDebtCollector;
    Texture inactiveButtonDebtMaker;
    Texture activeButtonDebtCollector;
    Texture activeButtonDebtMaker;
    Texture activeButtonOK;
    Texture inactiveButtonOK;
    Texture activeButtonStart;
    Texture inactiveButtonStart;
    Texture BGBlack;
    //Texture DCkoneksi;
    Texture RoomFull;
    private OrthographicCamera camera;
    private Music music;
    private Sound klik;
    private Kryo kryo;
    private String IP;
    private Server server2;
    private Stage stage;
    private Label label2;
    private Label labelOnlinePlayer;
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle labelStyle2;
    private float onlinePlayer = 0;
    private Timer timer;
    private TCPAlive tcpAlive;
    private Klien klien;
    private int jmlklien = 0;
    private Label.LabelStyle labelStyleChooseSide;
    private Label labelBaru;
    private DebtCollector debtCollector;
    private DebtMaker debtMaker;
    private String namaPlayerServer;
    private KlienArray klienArray;
    private OnlinePlayer3 onlinePlayer3;
    private int roompenuh = 0;
    private float readytostart = 0.5f;
    private String namaLobby2;
    private Listener listener;
    private String myip;
    private boolean aktifKlik;

    //Animation Text
    private FloatingText floatingText;

    public MainScreenLobbyWLANEnter(Game g, Music musik , final Server server, String namaLobby, String namaPlayer) // ** constructor called initially **//
    {
        aktifKlik = true;
        Gdx.app.log("Nama Lobbiku: " , namaLobby);
        bgblackbatch = new SpriteBatch();
        colorbgblack = bgblackbatch.getColor();
        klien = new Klien();
        namaPlayerServer = namaPlayer;
        onlinePlayer3 = new OnlinePlayer3();
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
        server2 = server;
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

        activeButtonDebtCollector = new Texture("DebtCollectorFlagClicked.png");
        activeButtonDebtMaker = new Texture("DebtMakerFlagClicked.png");
        activeButtonOK = new Texture("MenuButtonActiveOK.png");
        activeButtonStart = new Texture("MenuButtonActiveStart.png");

        inactiveButtonDebtCollector = new Texture("DebtCollectorFlag.png");
        inactiveButtonDebtMaker = new Texture("DebtMakerFlag.png");
        inactiveButtonOK = new Texture("MenuButtonInactiveOK.png");
        inactiveButtonStart = new Texture("MenuButtonInactiveStart.png");

        chooseSide = new Texture("ChooseSide.png");
        RoomFull = new Texture("RoomFull.png");
        BGBlack = new Texture("BlackBG.png");
        //DCkoneksi = new Texture("DCkoneksi.png");

        chooseSideChecked = new Texture("ChooseSideChecked.png");
        namaLobby2 = namaLobby;


        floatingText = new FloatingText("FarhanGembel has joined the lobby!", TimeUnit.SECONDS.toMillis(3));

        stage.addActor(floatingText);

        Kryo kryo = server.getKryo();
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
        kryo.register(PosisiSoda.class);
        kryo.register(TabrakCoin.class);
        kryo.register(TabrakSoda.class);
        //kryo.register(java.util.ArrayList.class);
        //kryo.register(com.esotericsoftware.kryonet.Connection.class);
        kryo.register(com.esotericsoftware.kryonet.Server.class);
        //kryo.register(com.esotericsoftware.kryonet.Client.class);
        //kryo.register(com.esotericsoftware.kryonet.Connection[].class);



        server.start();
        try {
            server.bind(27960, 27960);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.addListener(listener = new Listener(){
            @Override
            public void received(Connection connection, Object object){
                if(object instanceof DaftarLobiFullorNot) {
                    Gdx.app.log("LOBIbolehga: " , "Connected");
                    DaftarLobiFullorNot response = (DaftarLobiFullorNot) object;
                    if(getLength(klien.namaklien.toArray())>5)response.bolehngga = false;
                    else response.bolehngga=true;
                    connection.sendTCP(response);
                }
                if(object instanceof ClientReady) {
                    ClientReady request = (ClientReady) object;

                    String ipasliclient = ""+connection.getRemoteAddressTCP();
                    ipasliclient = ipasliclient.split(":")[0];
                    ipasliclient = ipasliclient.split("/")[1];
                    int itempos = getposarray(debtCollector.ip,""+ipasliclient);

                    Gdx.app.log("POSarray: ", request.klienrede+"Buang DC");
                    for(int i=0;i<getLength(debtCollector.ip);i++) {
                        Gdx.app.log("Isi DebtCollect ARRAY"+i, debtCollector.ip[i]+"");
                    }
                    if(itempos<2) {
                        debtCollector.ready[itempos] = request.klienrede;
                        server2.sendToAllTCP(debtCollector);
                        int jmlready = 0;
                        for(int k=0;k<getLength(debtCollector.ip);k++)
                            if(debtCollector.ready[k])jmlready++;
                        for(int k=0;k<getLength(debtMaker.ip);k++)
                            if(debtMaker.ready[k])jmlready++;

                        if(getLength(debtCollector.ip)>=1 && getLength(debtMaker.ip)>=1 &&
                                jmlready==(getLength(debtCollector.ip)+getLength(debtMaker.ip)))
                            readytostart=1f;
                        else readytostart=0.5f;
                    }
                }
                if(object instanceof ClientReady2) {
                    ClientReady2 request = (ClientReady2) object;

                    String ipasliclient = ""+connection.getRemoteAddressTCP();
                    ipasliclient = ipasliclient.split(":")[0];
                    ipasliclient = ipasliclient.split("/")[1];
                    int itempos = getposarray(debtMaker.ip,""+ipasliclient);

                    Gdx.app.log("POSarray: ", request.klienrede+"Buang DC");
                    Gdx.app.log("IP: ", connection.getRemoteAddressTCP()+"Buang DC");
                    for(int i=0;i<getLength(debtMaker.ip);i++) {
                        Gdx.app.log("Isi DebtCollect ARRAY"+i, debtMaker.ip[i]+"");
                    }
                    if(itempos<5) {
                        debtMaker.ready[itempos] = request.klienrede;
                        server2.sendToAllTCP(debtMaker);
                        int jmlready = 0;
                        for(int k=0;k<getLength(debtCollector.ip);k++)
                            if(debtCollector.ready[k])jmlready++;
                        for(int k=0;k<getLength(debtMaker.ip);k++)
                            if(debtMaker.ready[k])jmlready++;

                        if(getLength(debtCollector.ip)>=1 && getLength(debtMaker.ip)>=1 &&
                                jmlready==(getLength(debtCollector.ip)+getLength(debtMaker.ip)))
                            readytostart=1f;
                        else readytostart=0.5f;
                    }
                }
                if(object instanceof DebtCollector3) {
                    DebtCollector3 request = (DebtCollector3) object;

                    Gdx.app.log("OK: ", "Buang DC");

                    int cocok = 0;
                    int count = 0;
                    int i=0;
                    //Gdx.app.log("IP HOST: " + debtCollector.ip[0], "Buang DC");

                    String ipasliclient = ""+connection.getRemoteAddressTCP();
                    ipasliclient = ipasliclient.split(":")[0];
                    ipasliclient = ipasliclient.split("/")[1];

                    for(i=0;i<debtCollector.ip.length;i++){
                        if(debtCollector.ip[i] != null) {
                            if (!debtCollector.ip[i].equals(ipasliclient)) {
                                cocok++;
                            }
                            count++;
                        }

                    }
                    if(cocok == count && count < 2) {
                        Gdx.app.log("IP HOST: " + namaPlayerServer, "Buang DC");
                        debtCollector.nama[count] = (request.nama);
                        debtCollector.ip[count] = (ipasliclient);

                        for(int mulai=0;mulai<klien.conclient.size();mulai++){
                            klienArray.conclient[mulai] = ""+klien.conclient.get(mulai);
                            klienArray.namaklien[mulai] = klien.namaklien.get(mulai);
                            klienArray.alamatipklien[mulai] = klien.alamatipklien.get(mulai);
                        }



                        if (count == 0) {
                            labelChooseSide.get(0).setText(request.nama);
                            labelChooseSide.get(0).setColor(Color.YELLOW);

                            //Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                            int debt = ArrayUtils.indexOf(debtMaker.ip, ipasliclient);

                            if(Arrays.asList(debtMaker.ip).contains(ipasliclient)){
                                debtMaker.nama[debt] = null;
                                debtMaker.ip[debt] = null;
                                debtMaker.ready[debt] = false;

                                labelChooseSide.get(debt+2).setText("Join slot");
                                labelChooseSide.get(debt+2).setColor(Color.WHITE);
                                int i7;
                                for(i7 =debt+1;i7<Arrays.asList(debtMaker.nama).size();i7++){
                                    debtMaker.nama[i7-1] = debtMaker.nama[i7];
                                    debtMaker.ip[i7-1] = debtMaker.ip[i7];
                                    debtMaker.ready[i7-1] = debtMaker.ready[i7];

                                    //Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                                    if(debtMaker.nama[i7] == null){
                                        labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                                        labelChooseSide.get(i7-1+2).setColor(Color.WHITE);
                                    }
                                    else{
                                        labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                                        labelChooseSide.get(i7-1+2).setColor(Color.YELLOW);
                                    }

                                }
                                debtMaker.nama[i7-1] = null;
                                debtMaker.ip[i7-1] = null;
                                debtMaker.ready[i7-1] = false;
                                labelChooseSide.get(i7-1+2).setText("Join slot");
                                labelChooseSide.get(i7-1+2).setColor(Color.WHITE);


                            }

                        } else if (count == 1) {
                            labelChooseSide.get(1).setText(request.nama);
                            labelChooseSide.get(1).setColor(Color.YELLOW);

                            //Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                            int debt = ArrayUtils.indexOf(debtMaker.ip, ipasliclient);

                            if(Arrays.asList(debtMaker.ip).contains(ipasliclient)){
                                debtMaker.nama[debt] = null;
                                debtMaker.ip[debt] = null;
                                debtMaker.ready[debt] = false;

                                labelChooseSide.get(debt+2).setText("Join slot");
                                labelChooseSide.get(debt+2).setColor(Color.WHITE);
                                int i7;
                                for(i7 =debt+1;i7<Arrays.asList(debtMaker.nama).size();i7++){
                                    debtMaker.nama[i7-1] = debtMaker.nama[i7];
                                    debtMaker.ip[i7-1] = debtMaker.ip[i7];
                                    debtMaker.ready[i7-1] = debtMaker.ready[i7];

                                    //Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                                    if(debtMaker.nama[i7] == null){
                                        labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                                        labelChooseSide.get(i7-1+2).setColor(Color.WHITE);
                                    }
                                    else{
                                        labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                                        labelChooseSide.get(i7-1+2).setColor(Color.YELLOW);
                                    }

                                }
                                debtMaker.nama[i7-1] = null;
                                debtMaker.ip[i7-1] = null;
                                debtMaker.ready[i7-1] = false;
                                labelChooseSide.get(i7-1+2).setText("Join slot");
                                labelChooseSide.get(i7-1+2).setColor(Color.WHITE);
                            }
                        }

                    }
                    if(count == 2){
                        Gdx.app.log("AGENT ", "MELEBIHI KAPASITAS");
                        LobiRoomPenuh lobiRoomPenuh = new LobiRoomPenuh();
                        lobiRoomPenuh.lobiroompenuh = 1;
                        connection.sendTCP(lobiRoomPenuh);
                    }
                    else {
                        int jmlready = 0;
                        for(int k=0;k<getLength(debtCollector.ip);k++)
                            if(debtCollector.ready[k])jmlready++;
                        for(int k=0;k<getLength(debtMaker.ip);k++)
                            if(debtMaker.ready[k])jmlready++;

                        if(getLength(debtCollector.ip)>=1 && getLength(debtMaker.ip)>=1 &&
                                jmlready==(getLength(debtCollector.ip)+getLength(debtMaker.ip)))
                            readytostart=1f;
                        else readytostart=0.5f;

                        server.sendToAllTCP(debtCollector);

                        server.sendToAllTCP(debtMaker);

                        server.sendToAllTCP(klienArray);
                    }
                }
                if(object instanceof DebtMaker3) {
                    DebtMaker3 request = (DebtMaker3) object;

                    Gdx.app.log("OK: ", "Buang DC");


                    int cocok = 0;
                    int count = 0;
                    int i=0;
                    Gdx.app.log("IP HOST: " + debtMaker.ip[0], "Buang DC");

                    String ipasliclient = ""+connection.getRemoteAddressTCP();
                        ipasliclient = ipasliclient.split(":")[0];
                        ipasliclient = ipasliclient.split("/")[1];

                    for(i=0;i<debtMaker.ip.length;i++){
                        if(debtMaker.ip[i] != null) {
                            if (!debtMaker.ip[i].equals(ipasliclient)) {
                                cocok++;
                            }
                            count++;
                        }

                    }
                    if(cocok == count  && count < 4) {
                        Gdx.app.log("IP HOST: " + namaPlayerServer, "Buang DC");
                        debtMaker.nama[count] = (request.nama);
                        debtMaker.ip[count] = (ipasliclient);

                        for(int mulai=0;mulai<klien.conclient.size();mulai++){
                            klienArray.conclient[mulai] = ""+klien.conclient.get(mulai);
                            klienArray.namaklien[mulai] = klien.namaklien.get(mulai);
                            klienArray.alamatipklien[mulai] = klien.alamatipklien.get(mulai);
                        }

                        if (count == 0) {
                            labelChooseSide.get(2).setText(request.nama);
                            labelChooseSide.get(2).setColor(Color.YELLOW);

                            Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                            int debt = ArrayUtils.indexOf(debtCollector.ip, ipasliclient);

                            if(Arrays.asList(debtCollector.ip).contains(ipasliclient)){
                                debtCollector.nama[debt] = null;
                                debtCollector.ip[debt] = null;
                                debtCollector.ready[debt] = false;

                                labelChooseSide.get(debt).setText("Join slot");
                                labelChooseSide.get(debt).setColor(Color.WHITE);
                                int i7;
                                for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                                    debtCollector.nama[i7-1] = debtCollector.nama[i7];
                                    debtCollector.ip[i7-1] = debtCollector.ip[i7];
                                    debtCollector.ready[i7-1] = debtCollector.ready[i7];

                                    Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                                    if(debtCollector.nama[i7] == null){
                                        labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                        labelChooseSide.get(i7-1).setColor(Color.WHITE);
                                    }
                                    else{
                                        labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                        labelChooseSide.get(i7-1).setColor(Color.YELLOW);
                                    }

                                }

                                debtCollector.nama[i7-1] = null;
                                debtCollector.ip[i7-1] = null;
                                debtCollector.ready[i7-1] = false;
                                labelChooseSide.get(i7-1).setText("Join slot");
                                labelChooseSide.get(i7-1).setColor(Color.WHITE);
                            }
                        } else if (count == 1) {
                            labelChooseSide.get(3).setText(request.nama);
                            labelChooseSide.get(3).setColor(Color.YELLOW);

                            Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                            int debt = ArrayUtils.indexOf(debtCollector.ip, ipasliclient);

                            if(Arrays.asList(debtCollector.ip).contains(ipasliclient)){
                                debtCollector.nama[debt] = null;
                                debtCollector.ip[debt] = null;
                                debtCollector.ready[debt] = false;

                                labelChooseSide.get(debt).setText("Join slot");
                                labelChooseSide.get(debt).setColor(Color.WHITE);
                                int i7;
                                for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                                    debtCollector.nama[i7-1] = debtCollector.nama[i7];
                                    debtCollector.ip[i7-1] = debtCollector.ip[i7];;
                                    debtCollector.ready[i7-1] = debtCollector.ready[i7];

                                    Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                                    if(debtCollector.nama[i7] == null){
                                        labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                        labelChooseSide.get(i7-1).setColor(Color.WHITE);
                                    }
                                    else{
                                        labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                        labelChooseSide.get(i7-1).setColor(Color.YELLOW);
                                    }

                                }

                                debtCollector.nama[i7-1] = null;
                                debtCollector.ip[i7-1] = null;
                                debtCollector.ready[i7-1] = false;
                                labelChooseSide.get(i7-1).setText("Join slot");
                                labelChooseSide.get(i7-1).setColor(Color.WHITE);
                            }
                        } else if (count == 2) {
                            labelChooseSide.get(4).setText(request.nama);
                            labelChooseSide.get(4).setColor(Color.YELLOW);

                            Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                            int debt = ArrayUtils.indexOf(debtCollector.ip, ipasliclient);

                            if(Arrays.asList(debtCollector.ip).contains(ipasliclient)){
                                debtCollector.nama[debt] = null;
                                debtCollector.ip[debt] = null;
                                debtCollector.ready[debt] = false;

                                labelChooseSide.get(debt).setText("Join slot");
                                labelChooseSide.get(debt).setColor(Color.WHITE);
                                int i7;
                                for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                                    debtCollector.nama[i7-1] = debtCollector.nama[i7];
                                    debtCollector.ip[i7-1] = debtCollector.ip[i7];
                                    debtCollector.ready[i7-1] = debtCollector.ready[i7];

                                    Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                                    if(debtCollector.nama[i7] == null){
                                        labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                        labelChooseSide.get(i7-1).setColor(Color.WHITE);
                                    }
                                    else{
                                        labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                        labelChooseSide.get(i7-1).setColor(Color.YELLOW);
                                    }

                                }

                                debtCollector.nama[i7-1] = null;
                                debtCollector.ip[i7-1] = null;
                                debtCollector.ready[i7-1] = false;
                                labelChooseSide.get(i7-1).setText("Join slot");
                                labelChooseSide.get(i7-1).setColor(Color.WHITE);
                            }
                        } else if (count == 3) {
                            labelChooseSide.get(5).setText(request.nama);
                            labelChooseSide.get(5).setColor(Color.YELLOW);

                            Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                            int debt = ArrayUtils.indexOf(debtCollector.ip, ipasliclient);

                            if(Arrays.asList(debtCollector.ip).contains(ipasliclient)){
                                debtCollector.nama[debt] = null;
                                debtCollector.ip[debt] = null;
                                debtCollector.ready[debt] = false;

                                labelChooseSide.get(debt).setText("Join slot");
                                labelChooseSide.get(debt).setColor(Color.WHITE);
                                int i7;
                                for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                                    debtCollector.nama[i7-1] = debtCollector.nama[i7];
                                    debtCollector.ip[i7-1] = debtCollector.ip[i7];
                                    debtCollector.ready[i7-1] = debtCollector.ready[i7];

                                    Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                                    if(debtCollector.nama[i7] == null){
                                        labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                        labelChooseSide.get(i7-1).setColor(Color.WHITE);
                                    }
                                    else{
                                        labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                        labelChooseSide.get(i7-1).setColor(Color.YELLOW);
                                    }

                                }

                                debtCollector.nama[i7-1] = null;
                                debtCollector.ip[i7-1] = null;
                                debtCollector.ready[i7-1] = false;
                                labelChooseSide.get(i7-1).setText("Join slot");
                                labelChooseSide.get(i7-1).setColor(Color.WHITE);
                            }
                        }
                    }
                    if(count == 4){
                        Gdx.app.log("AGENT ", "MELEBIHI KAPASITAS");
                        LobiRoomPenuh lobiRoomPenuh = new LobiRoomPenuh();
                        lobiRoomPenuh.lobiroompenuh = 1;
                        connection.sendTCP(lobiRoomPenuh);
                    }
                    else {
                        int jmlready = 0;
                        for(int k=0;k<getLength(debtCollector.ip);k++)
                            if(debtCollector.ready[k])jmlready++;
                        for(int k=0;k<getLength(debtMaker.ip);k++)
                            if(debtMaker.ready[k])jmlready++;

                        if(getLength(debtCollector.ip)>=1 && getLength(debtMaker.ip)>=1 &&
                                jmlready==(getLength(debtCollector.ip)+getLength(debtMaker.ip)))
                            readytostart=1f;
                        else readytostart=0.5f;

                        server.sendToAllTCP(debtCollector);

                        server.sendToAllTCP(debtMaker);

                        server.sendToAllTCP(klienArray);
                    }
                }
                if(object instanceof SomeRequest) {

                    SomeRequest request = (SomeRequest)object;
                    //System.out.println(request.text);

                    Gdx.app.log("MENU REQ: "+request.text, "Connected");

                    String namaPlayerOnlen = ""+request.text;
                    if(request.text.contains("-"))
                    {
                        namaPlayerOnlen = namaPlayerOnlen.split("-")[1];
                        myip = request.text.split("-")[0];
                        klien.namaklien.set(jmlklien,namaPlayerOnlen);
                        Gdx.app.log("MENU SERVER: ", klien.namaklien.get(jmlklien)+" is ALIVE!!!");
                        Gdx.app.log("MENU SERVER: ", klien.alamatipklien.get(jmlklien)+" is ALIVE!!!");
                        Gdx.app.log("MENU SERVER: ", klien.conclient.get(jmlklien)+" is ALIVE!!!");

                        floatingText.text = klien.namaklien.get(jmlklien)+" has joined the lobby!";
                        GlyphLayout layouto = new GlyphLayout(); //dont do this every frame! Store it as member
                        layouto.setText(floatingText.font,klien.namaklien.get(jmlklien)+" has joined the lobby!");
                        float fwidth = layouto.width;// contains the width of the current set text
                        float fheight = layouto.height; // contains the height of the current set tex

                        floatingText.setPosition((1440*Gdx.graphics.getWidth()/2880)-(fwidth)/2, 20*Gdx.graphics.getHeight()/720);
                        floatingText.setDeltaY(50);
                        floatingText.animate();

                        jmlklien++;

                        labelOnlinePlayer.setText(labelOnlinePlayer.getText()+"\n"+namaPlayerOnlen);
                        onlinePlayer3.nama = ""+labelOnlinePlayer.getText();
                        onlinePlayer3.nama = onlinePlayer3.nama.replace("Online Player: ", "Online Player: \n"+namaPlayerServer+" (HOST)");
                        Gdx.app.log("PLAYERONLEN: "+onlinePlayer3.nama, " Connected");
                        timer.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                                            @Override
                                            public void run() {
                                                server2.sendToAllTCP(onlinePlayer3);
                                            }
                                        }
                                , 2);

                        onlinePlayer++;
                        Gdx.app.log("MENU XAV: "+((1350-(onlinePlayer*25.0f))*Gdx.graphics.getHeight()/1440), "Connected");
                        labelOnlinePlayer.setPosition(2100*Gdx.graphics.getWidth()/2880,((1360-(onlinePlayer*25))*Gdx.graphics.getHeight()/1440));

                        for(int mulai=0;mulai<klien.conclient.size();mulai++){
                            klienArray.conclient[mulai] = ""+klien.conclient.get(mulai);
                            klienArray.namaklien[mulai] = klien.namaklien.get(mulai);
                            klienArray.alamatipklien[mulai] = klien.alamatipklien.get(mulai);
                        }


                        timer.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                                            @Override
                                            public void run() {
                                                for(int i=0;i<debtCollector.nama.length;i++) {
                                                    Gdx.app.log("DC: Isi DebtCollect ARRAY"+i, debtCollector.nama[i]+"");
                                                }

                                                for(int i=0;i<debtMaker.nama.length;i++) {
                                                    Gdx.app.log("DC: Isi DebtMaker ARRAY"+i, debtMaker.nama[i]+"");
                                                }

                                                for(int i=0;i<klienArray.namaklien.length;i++) {
                                                    Gdx.app.log("DC: Isi KlienArray ARRAY"+i, klienArray.namaklien[i]+"");
                                                }

                                                server2.sendToAllTCP(debtCollector);

                                                server2.sendToAllTCP(debtMaker);

                                                server2.sendToAllTCP(klienArray);
                                            }
                                        }
                                , 1);



                    }


                    //label2.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
                    //stage.addActor(labelOnlinePlayer);
                    String ipasliclient = ""+connection.getRemoteAddressTCP();
                    ipasliclient = ipasliclient.split(":")[0];
                    ipasliclient = ipasliclient.split("/")[1];

                    SomeResponse response = new SomeResponse();
                    response.text = namaLobby2+"-"+request.text+"-"+(getLength(klien.namaklien.toArray()));
                    response.yourIP = ipasliclient;
                    IP =namaLobby2+"-"+request.text;
                    connection.sendTCP(response);
                    if (debtMaker.ip[0] == null) {
                            KlikButtonCollect();
                        }
                        else {
                            KlikButtonDebt();
                        }

                }
                if(object instanceof TCPAlive){
                    TCPAlive tcpAlive = (TCPAlive)object;
                    String tcpcheck = ""+tcpAlive.text;
                    //Gdx.app.log("MENU SERVER: ", "IP NYA ->> "+tcpAlive.text);
                    if(tcpAlive.text.contains("OK")){
                        //Gdx.app.log("MENU SERVER: ", tcpAlive.text.split("-")[0]+" is ALIVE!!!");
                    }
                }
            }
            public void connected (Connection connection) {
                klien.conclient.add(connection);
                klien.alamatipklien.add(""+connection.getRemoteAddressTCP());
                klien.namaklien.add("");


            }
            public void disconnected (Connection connection) {
                int posisiindex = getItempos(klien.conclient,connection);
                Gdx.app.log("MENU SERVER: ", klien.namaklien.get(posisiindex)+",pos:"+posisiindex+"ini yg DC");

                if(klien.namaklien.get(posisiindex).equals("")){
                    Gdx.app.log("MENU SERVER: ", "Buang DC");
                    klien.conclient.remove(posisiindex);
                    klien.alamatipklien.remove(posisiindex);
                    klien.namaklien.remove(posisiindex);

                    /*for(int i=0;i<klien.namaklien.size();i++) {
                        Gdx.app.log("DC2: Isi Klien LIST"+i, klien.namaklien.get(i)+"");
                    }*/

                    for(int mulai=0;mulai<klien.conclient.size();mulai++){
                        klienArray.conclient[mulai] = ""+klien.conclient.get(mulai);
                        klienArray.namaklien[mulai] = klien.namaklien.get(mulai);
                        klienArray.alamatipklien[mulai] = klien.alamatipklien.get(mulai);
                    }
                    /*for(int i=0;i<klienArray.namaklien.length;i++) {
                        Gdx.app.log("DC2: Isi KlienArray ARRAY"+i, klienArray.namaklien[i]+"");
                    }*/

                }else {
                    onlinePlayer--;
                    jmlklien--;
                    Gdx.app.log("MENU SERVER: ", "DC");
                    String dcreplace = ""+labelOnlinePlayer.getText();
                    String newaddress = dcreplace;
                    String namaplayer = klien.namaklien.get(posisiindex);
                    newaddress = dcreplace.replace("\n"+namaplayer,"");
                    labelOnlinePlayer.setText(newaddress);
                    labelOnlinePlayer.setPosition(2100*Gdx.graphics.getWidth()/2880,((1360-(onlinePlayer*25))*Gdx.graphics.getHeight()/1440));

                    floatingText.text = klien.namaklien.get(posisiindex)+" has left the lobby!";
                    GlyphLayout layouto = new GlyphLayout(); //dont do this every frame! Store it as member
                    layouto.setText(floatingText.font,klien.namaklien.get(posisiindex)+" has left the lobby!");
                    float fwidth = layouto.width;// contains the width of the current set text
                    float fheight = layouto.height; // contains the height of the current set tex

                    floatingText.setPosition((1440*Gdx.graphics.getWidth()/2880)-(fwidth)/2, 20*Gdx.graphics.getHeight()/720);
                    floatingText.setDeltaY(50);
                    floatingText.animate();

                    String ipclientasli = klien.alamatipklien.get(posisiindex).split(":")[0];
                    ipclientasli = ipclientasli.split("/")[1];

                    klien.conclient.remove(posisiindex);;
                    klien.alamatipklien.remove(posisiindex);
                    klien.namaklien.remove(posisiindex);

                    for(int mulai=0;mulai<klien.conclient.size();mulai++){
                        klienArray.conclient[mulai] = ""+klien.conclient.get(mulai);
                        klienArray.namaklien[mulai] = klien.namaklien.get(mulai);
                        klienArray.alamatipklien[mulai] = klien.alamatipklien.get(mulai);
                    }

                    for(int i=0;i<klienArray.conclient.length;i++) {
                        Gdx.app.log("DC2: Isi KlienArray ARRAY"+i, klienArray.namaklien[i]+"");
                    }
                    for(int i=0;i<klien.conclient.size();i++) {
                        Gdx.app.log("DC2: Isi Klien ARRAY"+i, klien.namaklien.get(i)+"");
                    }
                    int debt=0;
                    int debtm=0;
                    Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                    if(!ArrayUtils.isEmpty(debtCollector.ip))debt = ArrayUtils.indexOf(debtCollector.ip, ipclientasli);
                    if(!ArrayUtils.isEmpty(debtMaker.ip))debtm = ArrayUtils.indexOf(debtMaker.ip, ipclientasli);

                    if(Arrays.asList(debtCollector.ip).contains(ipclientasli)){
                        debtCollector.nama[debt] = null;
                        debtCollector.ip[debt] = null;
                        debtCollector.ready[debt] = false;

                        labelChooseSide.get(debt).setText("Join slot");
                        labelChooseSide.get(debt).setColor(Color.WHITE);
                        int i7;
                        for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                            debtCollector.nama[i7-1] = debtCollector.nama[i7];
                            debtCollector.ip[i7-1] = debtCollector.ip[i7];
                            debtCollector.ready[i7-1] = debtCollector.ready[i7];

                            Gdx.app.log("OK ", "CLIENT DEBT COLECTOR DC");

                            if(debtCollector.nama[i7] == null){
                                labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                labelChooseSide.get(i7-1).setColor(Color.WHITE);
                            }
                            else{
                                labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                                labelChooseSide.get(i7-1).setColor(Color.YELLOW);
                            }

                        }

                        debtCollector.nama[i7-1] = null;
                        debtCollector.ip[i7-1] = null;
                        debtCollector.ready[i7-1] = false;
                        labelChooseSide.get(i7-1).setText("Join slot");
                        labelChooseSide.get(i7-1).setColor(Color.WHITE);
                    }
                    if(Arrays.asList(debtMaker.ip).contains(ipclientasli)){
                        debtMaker.nama[debtm] = null;
                        debtMaker.ip[debtm] = null;
                        debtMaker.ready[debtm] = false;

                        labelChooseSide.get(debtm+2).setText("Join slot");
                        labelChooseSide.get(debtm+2).setColor(Color.WHITE);
                        int i7;
                        for(i7 =debtm+1;i7<Arrays.asList(debtMaker.nama).size();i7++){
                            debtMaker.nama[i7-1] = debtMaker.nama[i7];
                            debtMaker.ip[i7-1] = debtMaker.ip[i7];
                            debtMaker.ready[i7-1] = debtMaker.ready[i7];

                            Gdx.app.log("OK ", "CLIENT DEBT MAKER DC");

                            if(debtMaker.nama[i7] == null){
                                labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                                labelChooseSide.get(i7-1+2).setColor(Color.WHITE);
                            }
                            else{
                                labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                                labelChooseSide.get(i7-1+2).setColor(Color.YELLOW);
                            }

                        }

                        debtMaker.nama[i7-1] = null;
                        debtMaker.ip[i7-1] = null;
                        debtMaker.ready[i7-1] = false;
                        labelChooseSide.get(i7-1+2).setText("Join slot");
                        labelChooseSide.get(i7-1+2).setColor(Color.WHITE);
                    }



                }



                for(int mulai=0;mulai<klien.conclient.size();mulai++){
                    klienArray.conclient[mulai] = ""+klien.conclient.get(mulai);
                    klienArray.namaklien[mulai] = klien.namaklien.get(mulai);
                    klienArray.alamatipklien[mulai] = klien.alamatipklien.get(mulai);
                }

                server.sendToAllTCP(debtCollector);

                server.sendToAllTCP(debtMaker);

                server.sendToAllTCP(klienArray);

                server2.sendToAllTCP(onlinePlayer3);


            }


        });

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        labelStyle.fontColor = Color.BLACK;

        label2 = new Label("Lobby Name: "+namaLobby+"\nPlayer Name: "+namaPlayer,labelStyle);
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
                    server2.stop();
                    stage.dispose();
                    myGame.setScreen(new MainScreenLobbyWLAN(myGame, music));
                }
                return false;
            }
        };


        InputMultiplexer multiplexer = new InputMultiplexer(stage,
                backProcessor);
        Gdx.input.setInputProcessor(multiplexer);

        /*timer.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                            @Override
                            public void run() {
                                tcpAlive = new TCPAlive();
                                tcpAlive.text = "check alive";
                                server.sendToAllTCP(tcpAlive);
                            }
                        }
                , 0 ,5);*/

        KlikButtonCollect();

    }

    public static <T> int getLength(T[] arr){
        int count = 0;
        for(T el : arr)
            if (el != null)
                ++count;
        return count;
    }

    public class FloatingText extends Actor {
        private String text;
        private final long animationDuration;
        private float deltaX;
        private float deltaY;
        private BitmapFont font = new BitmapFont();
        private boolean animated = false;
        private long animationStart;

        public void animate() {
            animated = true;
            animationStart = System.currentTimeMillis();
        }

        public void unanimate() {
            animated = false;
        }

        public boolean isAnimated() {
            return animated;
        }

        public FloatingText(String text, long animationDuration) {
            this.text = text;
            this.animationDuration = animationDuration;
        }

        public void setDeltaX(float deltaX) {
            this.deltaX = deltaX;
        }

        public void setDeltaY(float deltaY) {
            this.deltaY = deltaY;
        }
        @Override
        public void draw(Batch batch, float parentAlpha) {
            if (animated) {
                // The component will auto-destruct when animation is finished.

                float elapsed = System.currentTimeMillis() - animationStart;

                if (parentAlpha * (1 - elapsed / animationDuration)<=0.2f) {
                    Gdx.app.log("Floating Text: ", "END");
                    floatingText.unanimate();
                    return;
                }

                // The text will be fading.
                font.getData().setScale(5.0f*Gdx.graphics.getWidth()/2880,5.0f*Gdx.graphics.getHeight()/1440);
                font.setColor(getColor().r, getColor().g, getColor().b, parentAlpha * (1 - elapsed / animationDuration));

                font.draw(batch, text, getX() + deltaX * elapsed / 1000f, getY() + deltaY * elapsed / 1000f);
            }
        }
    }

    private int getItempos(ArrayList<Connection> mArrayList, Connection connection)
    {
        for(int i=0;i<mArrayList.size();i++)
        {
            if(klien.conclient.get(i) == connection)
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        stage.act(delta);
        //button background
        batch.draw(texture, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //button labelchoose
        batch.draw(chooseSide, labelChooseSide.get(0).getX()-50*Gdx.graphics.getWidth()/2880, labelChooseSide.get(0).getY()-(12.5f*Gdx.graphics.getHeight()/1440)-labelChooseSide.get(0).getHeight()/2,labelChooseSide.get(0).getWidth()+400*Gdx.graphics.getWidth()/2880,labelChooseSide.get(0).getHeight()+50f*Gdx.graphics.getHeight()/1440);
        batch.draw(chooseSide, labelChooseSide.get(1).getX()-50*Gdx.graphics.getWidth()/2880, labelChooseSide.get(1).getY()-(12.5f*Gdx.graphics.getHeight()/1440)-labelChooseSide.get(1).getHeight()/2,labelChooseSide.get(1).getWidth()+400*Gdx.graphics.getWidth()/2880,labelChooseSide.get(1).getHeight()+50f*Gdx.graphics.getHeight()/1440);
        batch.draw(chooseSide, labelChooseSide.get(2).getX()-50*Gdx.graphics.getWidth()/2880, labelChooseSide.get(2).getY()-(12.5f*Gdx.graphics.getHeight()/1440)-labelChooseSide.get(2).getHeight()/2,labelChooseSide.get(2).getWidth()+400*Gdx.graphics.getWidth()/2880,labelChooseSide.get(2).getHeight()+50f*Gdx.graphics.getHeight()/1440);
        batch.draw(chooseSide, labelChooseSide.get(3).getX()-50*Gdx.graphics.getWidth()/2880, labelChooseSide.get(3).getY()-(12.5f*Gdx.graphics.getHeight()/1440)-labelChooseSide.get(3).getHeight()/2,labelChooseSide.get(3).getWidth()+400*Gdx.graphics.getWidth()/2880,labelChooseSide.get(3).getHeight()+50f*Gdx.graphics.getHeight()/1440);
        batch.draw(chooseSide, labelChooseSide.get(4).getX()-50*Gdx.graphics.getWidth()/2880, labelChooseSide.get(4).getY()-(12.5f*Gdx.graphics.getHeight()/1440)-labelChooseSide.get(4).getHeight()/2,labelChooseSide.get(4).getWidth()+400*Gdx.graphics.getWidth()/2880,labelChooseSide.get(4).getHeight()+50f*Gdx.graphics.getHeight()/1440);
        batch.draw(chooseSide, labelChooseSide.get(5).getX()-50*Gdx.graphics.getWidth()/2880, labelChooseSide.get(5).getY()-(12.5f*Gdx.graphics.getHeight()/1440)-labelChooseSide.get(5).getHeight()/2,labelChooseSide.get(5).getWidth()+400*Gdx.graphics.getWidth()/2880,labelChooseSide.get(5).getHeight()+50f*Gdx.graphics.getHeight()/1440);

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

        batch.end();
        //button Start
        bgblackbatch.begin();
        bgblackbatch.setColor(colorbgblack.r, colorbgblack.g, colorbgblack.b, readytostart);
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()* 0.5f)-(Gdx.graphics.getWidth()/10) || Gdx.input.getX() > (Gdx.graphics.getWidth()* 0.5f)+(Gdx.graphics.getWidth()/10)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()* 0.9f)-(Gdx.graphics.getHeight()/20) || Gdx.input.getY() > (Gdx.graphics.getHeight()*0.9)+(Gdx.graphics.getHeight()/20))
        {
            bgblackbatch.draw(inactiveButtonStart, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
        } else {
            bgblackbatch.draw(inactiveButtonStart, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
            if(readytostart==1f) {
                if (Gdx.input.justTouched() && roompenuh == 0) {
                    klik.play();
                    stage.dispose();
                    timer.instance().clear();
                    server2.removeListener(listener);
                    Timer timer = new Timer();
                    timer.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                                        @Override
                                        public void run() {
                                            myGame.setScreen(new MainScreenChooseHeroes(myGame,music,server2,namaLobby2, namaPlayerServer, debtCollector,debtMaker,klienArray,klien,myip));
                                        }
                                    }
                            , 0.1f);

                    GotoChooseHeroes gotoChooseHeroes = new GotoChooseHeroes();
                    gotoChooseHeroes.gas = true;
                    server2.sendToAllTCP(gotoChooseHeroes);
                }
                if (Gdx.input.isTouched() && roompenuh == 0) {
                    bgblackbatch.draw(activeButtonStart, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
                } else
                    bgblackbatch.draw(inactiveButtonStart, (Gdx.graphics.getWidth() * 0.5f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
            }
        }
        bgblackbatch.end();
        batch.begin();

        //button debt collector
        if(Gdx.input.getX() < (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/8) || Gdx.input.getX() > (Gdx.graphics.getWidth()/4)+(Gdx.graphics.getWidth()/8)
                || Gdx.input.getY() < (Gdx.graphics.getHeight()*0.35f)-(Gdx.graphics.getHeight()/8) || Gdx.input.getY() > (Gdx.graphics.getHeight()*0.35f)+(Gdx.graphics.getHeight()/8))
        {
            batch.draw(inactiveButtonDebtCollector, (Gdx.graphics.getWidth()/4)-(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()*0.65f)-(Gdx.graphics.getHeight()/8),Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        } else {
            if(Gdx.input.justTouched() && roompenuh==0){
                KlikButtonCollect();
            }
            if(Gdx.input.isTouched() && roompenuh==0)
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
            if(Gdx.input.justTouched() && roompenuh==0){
                KlikButtonDebt();
            }
            if(Gdx.input.isTouched() && roompenuh==0)
            {
                batch.draw(activeButtonDebtMaker, (Gdx.graphics.getWidth()*0.75f)-(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()*0.65f)-(Gdx.graphics.getHeight()/8),Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
            }
            else
                batch.draw(inactiveButtonDebtMaker, (Gdx.graphics.getWidth()*0.75f)-(Gdx.graphics.getWidth()/8), (Gdx.graphics.getHeight()*0.65f)-(Gdx.graphics.getHeight()/8),Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
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

    public void KlikButtonCollect() {
        klik.play();
        int cocok = 0;
        int count = 0;
        int i=0;

        Gdx.app.log("IP HOST: " + debtCollector.ip[0], "Buang DC");
        for(i=0;i<debtCollector.ip.length;i++){
            if(debtCollector.ip[i] != null) {
                if (!debtCollector.ip[i].equals(myip)) {
                    cocok++;
                }
                count++;
            }

        }
        if(cocok == count && count < 2) {
            Gdx.app.log("IP HOST: " + namaPlayerServer, "Buang DC");
            debtCollector.nama[count] = (namaPlayerServer);
            debtCollector.ip[count] = (myip);
            debtCollector.ready[count] = true;
            //server2.sendToAllTCP(debtCollector);
            if (count == 0) {
                labelChooseSide.get(0).setText(namaPlayerServer);
                labelChooseSide.get(0).setColor(Color.YELLOW);

                int cocok1 = 0;
                int count1 = 0;
                int i1=0;
                int indexHapus = 0;

                Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                int debt = ArrayUtils.indexOf(debtMaker.ip, myip);

                if(Arrays.asList(debtMaker.ip).contains(myip)){
                    debtMaker.nama[debt] = null;
                    debtMaker.ip[debt] = null;
                    debtMaker.ready[debt] = false;

                    labelChooseSide.get(debt+2).setText("Join slot");
                    labelChooseSide.get(debt+2).setColor(Color.WHITE);
                    int i7;
                    for(i7 =debt+1;i7<Arrays.asList(debtMaker.nama).size();i7++){
                        debtMaker.nama[i7-1] = debtMaker.nama[i7];
                        debtMaker.ip[i7-1] = debtMaker.ip[i7];
                        debtMaker.ready[i7-1] = debtMaker.ready[i7];

                        Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                        if(debtMaker.nama[i7] == null){
                            labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                            labelChooseSide.get(i7-1+2).setColor(Color.WHITE);
                        }
                        else{
                            labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                            labelChooseSide.get(i7-1+2).setColor(Color.YELLOW);
                        }

                    }
                    debtMaker.nama[i7-1] = null;
                    debtMaker.ip[i7-1] = null;
                    debtMaker.ready[i7-1] = false;
                    labelChooseSide.get(i7-1+2).setText("Join slot");
                    labelChooseSide.get(i7-1+2).setColor(Color.WHITE);
                }
            } else if (count == 1) {
                labelChooseSide.get(1).setText(namaPlayerServer);
                labelChooseSide.get(1).setColor(Color.YELLOW);

                Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                int debt = ArrayUtils.indexOf(debtMaker.ip, myip);

                if(Arrays.asList(debtMaker.ip).contains(myip)){
                    debtMaker.nama[debt] = null;
                    debtMaker.ip[debt] = null;
                    debtMaker.ready[debt] = false;

                    labelChooseSide.get(debt+2).setText("Join slot");
                    labelChooseSide.get(debt+2).setColor(Color.WHITE);
                    int i7;
                    for(i7 =debt+1;i7<Arrays.asList(debtMaker.nama).size();i7++){
                        debtMaker.nama[i7-1] = debtMaker.nama[i7];
                        debtMaker.ip[i7-1] = debtMaker.ip[i7];
                        debtMaker.ready[i7-1] = debtMaker.ready[i7];

                        Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                        if(debtMaker.nama[i7] == null){
                            labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                            labelChooseSide.get(i7-1+2).setColor(Color.WHITE);
                        }
                        else{
                            labelChooseSide.get(i7-1+2).setText(labelChooseSide.get(i7+2).getText());
                            labelChooseSide.get(i7-1+2).setColor(Color.YELLOW);
                        }

                    }
                    debtMaker.nama[i7-1] = null;
                    debtMaker.ip[i7-1] = null;
                    debtMaker.ready[i7-1] = false;
                    labelChooseSide.get(i7-1+2).setText("Join slot");
                    labelChooseSide.get(i7-1+2).setColor(Color.WHITE);
                }

            }

        }

        if(count == 2){
            Gdx.app.log("AGENT ", "MELEBIHI KAPASITAS");
            roompenuh=1;
        }
        else {
            int jmlready = 0;
            for(int k=0;k<getLength(debtCollector.ip);k++)
                if(debtCollector.ready[k])jmlready++;
            for(int k=0;k<getLength(debtMaker.ip);k++)
                if(debtMaker.ready[k])jmlready++;

            if(getLength(debtCollector.ip)>=1 && getLength(debtMaker.ip)>=1 &&
                    jmlready==(getLength(debtCollector.ip)+getLength(debtMaker.ip)))
                readytostart=1f;
            else readytostart=0.5f;

            server2.sendToAllTCP(debtCollector);

            server2.sendToAllTCP(debtMaker);

            server2.sendToAllTCP(klienArray);

        }
    }
    public void KlikButtonDebt(){
        klik.play();
        int cocok = 0;
        int count = 0;
        int i=0;
        Gdx.app.log("IP HOST: " + debtMaker.ip[0], "Buang DC");
        for(i=0;i<debtMaker.ip.length;i++){
            if(debtMaker.ip[i] != null) {
                if (!debtMaker.ip[i].equals(myip)) {
                    cocok++;
                }
                count++;
            }

        }
        if(cocok == count && count < 4) {
            Gdx.app.log("IP HOST: " + namaPlayerServer, "Buang DC");
            debtMaker.nama[count] = (namaPlayerServer);
            debtMaker.ip[count] = (myip);
            debtMaker.ready[count] = true;
            //server2.sendToAllTCP(debtMaker);
            if (count == 0) {
                labelChooseSide.get(2).setText(namaPlayerServer);
                labelChooseSide.get(2).setColor(Color.YELLOW);

                Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                int debt = ArrayUtils.indexOf(debtCollector.ip, myip);

                if(Arrays.asList(debtCollector.ip).contains(myip)){
                    debtCollector.nama[debt] = null;
                    debtCollector.ip[debt] = null;
                    debtCollector.ready[debt] = false;

                    labelChooseSide.get(debt).setText("Join slot");
                    labelChooseSide.get(debt).setColor(Color.WHITE);
                    int i7;
                    for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                        debtCollector.nama[i7-1] = debtCollector.nama[i7];
                        debtCollector.ip[i7-1] = debtCollector.ip[i7];
                        debtCollector.ready[i7-1] = debtCollector.ready[i7];

                        Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                        if(debtCollector.nama[i7] == null){
                            labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                            labelChooseSide.get(i7-1).setColor(Color.WHITE);
                        }
                        else{
                            labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                            labelChooseSide.get(i7-1).setColor(Color.YELLOW);
                        }

                    }

                    debtCollector.nama[i7-1] = null;
                    debtCollector.ip[i7-1] = null;
                    debtCollector.ready[i7-1] = false;
                    labelChooseSide.get(i7-1).setText("Join slot");
                    labelChooseSide.get(i7-1).setColor(Color.WHITE);
                }

            } else if (count == 1) {
                labelChooseSide.get(3).setText(namaPlayerServer);
                labelChooseSide.get(3).setColor(Color.YELLOW);

                Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                int debt = ArrayUtils.indexOf(debtCollector.ip, myip);

                if(Arrays.asList(debtCollector.ip).contains(myip)){
                    debtCollector.nama[debt] = null;
                    debtCollector.ip[debt] = null;
                    debtCollector.ready[debt] = false;

                    labelChooseSide.get(debt).setText("Join slot");
                    labelChooseSide.get(debt).setColor(Color.WHITE);
                    int i7;
                    for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                        debtCollector.nama[i7-1] = debtCollector.nama[i7];
                        debtCollector.ip[i7-1] = debtCollector.ip[i7];
                        debtCollector.ready[i7-1] = debtCollector.ready[i7];

                        Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                        if(debtCollector.nama[i7] == null){
                            labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                            labelChooseSide.get(i7-1).setColor(Color.WHITE);
                        }
                        else{
                            labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                            labelChooseSide.get(i7-1).setColor(Color.YELLOW);
                        }

                    }

                    debtCollector.nama[i7-1] = null;
                    debtCollector.ip[i7-1] = null;
                    debtCollector.ready[i7-1] = false;
                    labelChooseSide.get(i7-1).setText("Join slot");
                    labelChooseSide.get(i7-1).setColor(Color.WHITE);
                }
            } else if (count == 2) {
                labelChooseSide.get(4).setText(namaPlayerServer);
                labelChooseSide.get(4).setColor(Color.YELLOW);

                Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                int debt = ArrayUtils.indexOf(debtCollector.ip, myip);

                if(Arrays.asList(debtCollector.ip).contains(myip)){
                    debtCollector.nama[debt] = null;
                    debtCollector.ip[debt] = null;
                    debtCollector.ready[debt] = false;

                    labelChooseSide.get(debt).setText("Join slot");
                    labelChooseSide.get(debt).setColor(Color.WHITE);
                    int i7;
                    for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                        debtCollector.nama[i7-1] = debtCollector.nama[i7];
                        debtCollector.ip[i7-1] = debtCollector.ip[i7];
                        debtCollector.ready[i7-1] = debtCollector.ready[i7];

                        Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                        if(debtCollector.nama[i7] == null){
                            labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                            labelChooseSide.get(i7-1).setColor(Color.WHITE);
                        }
                        else{
                            labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                            labelChooseSide.get(i7-1).setColor(Color.YELLOW);
                        }

                    }

                    debtCollector.nama[i7-1] = null;
                    debtCollector.ip[i7-1] = null;
                    debtCollector.ready[i7-1] = false;
                    labelChooseSide.get(i7-1).setText("Join slot");
                    labelChooseSide.get(i7-1).setColor(Color.WHITE);
                }
            } else if (count == 3) {
                labelChooseSide.get(5).setText(namaPlayerServer);
                labelChooseSide.get(5).setColor(Color.YELLOW);

                Gdx.app.log("IP HOST: ", "PINDAH KE DEBT COLECTOR LOBI");
                int debt = ArrayUtils.indexOf(debtCollector.ip, myip);

                if(Arrays.asList(debtCollector.ip).contains(myip)){
                    debtCollector.nama[debt] = null;
                    debtCollector.ip[debt] = null;
                    debtCollector.ready[debt] = false;

                    labelChooseSide.get(debt).setText("Join slot");
                    labelChooseSide.get(debt).setColor(Color.WHITE);
                    int i7;
                    for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                        debtCollector.nama[i7-1] = debtCollector.nama[i7];
                        debtCollector.ip[i7-1] = debtCollector.ip[i7];
                        debtCollector.ready[i7-1] = debtCollector.ready[i7];

                        Gdx.app.log("OK ", "PINDAH KE DEBT COLECTOR LOBI");

                        if(debtCollector.nama[i7] == null){
                            labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                            labelChooseSide.get(i7-1).setColor(Color.WHITE);
                        }
                        else{
                            labelChooseSide.get(i7-1).setText(labelChooseSide.get(i7).getText());
                            labelChooseSide.get(i7-1).setColor(Color.YELLOW);
                        }

                    }

                    debtCollector.nama[i7-1] = null;
                    debtCollector.ip[i7-1] = null;
                    debtCollector.ready[i7-1] = false;
                    labelChooseSide.get(i7-1).setText("Join slot");
                    labelChooseSide.get(i7-1).setColor(Color.WHITE);
                }
            }
        }

        if(count == 4){
            Gdx.app.log("AGENT ", "MELEBIHI KAPASITAS");
            roompenuh=1;
        }
        else {
            int jmlready = 0;
            for(int k=0;k<getLength(debtCollector.ip);k++)
                if(debtCollector.ready[k])jmlready++;
            for(int k=0;k<getLength(debtMaker.ip);k++)
                if(debtMaker.ready[k])jmlready++;

            if(getLength(debtCollector.ip)>=1 && getLength(debtMaker.ip)>=1 &&
                    jmlready==(getLength(debtCollector.ip)+getLength(debtMaker.ip)))
                readytostart=1f;
            else readytostart=0.5f;

            server2.sendToAllTCP(debtCollector);

            server2.sendToAllTCP(debtMaker);

            server2.sendToAllTCP(klienArray);

        }
    }

    public int getposarray(String[] arrneed, String str){
        int index = -1;
        for (int i=0;i<getLength(arrneed);i++) {
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
        stage.dispose();
    }



}