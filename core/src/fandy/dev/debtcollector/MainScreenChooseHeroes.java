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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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

public class MainScreenChooseHeroes implements Screen {
    private SpriteBatch bgblackbatch;
    private Color colorbgblack;
    private ArrayList<Label> labelChooseSide;
    private SpriteBatch batch;
    private Game myGame;
    private Texture texture;
    Texture BGSelectionCharacter;
    Texture activeButtonPick;
    Texture inactiveButtonPick;
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
    private Label labeltimepick;
    private Label.LabelStyle labelStyle;
    private Label.LabelStyle labelStyle2;
    private float onlinePlayer = 0;
    private Timer timer;
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
    private float udahklikhero = 0.5f;
    private Table tabelDaftarHeroes;
    private Skin skin;
    private float elapsed;

    //var Heroes
    private int timerpickheroes=10;
    private Texture[] charMiniHeroesDCTexture;
    private Texture[] charMiniHeroesDMTexture;
    private int jumlahheroesDC = 2;
    private int jumlahheroesDM = 2;
    private Animation<TextureRegion>[] animationheroDC;
    private Animation<TextureRegion>[] animationheroDM;
    private Animation<TextureRegion>[] loadingscreen;
    private Texture[] characterframeDC;
    private Texture[] characterframeDM;
    private Image[] charframeimageDC;
    private Image[] charframeimageDM;
    private Image[] charMiniHeroesDCimage;
    private Image[] charMiniHeroesDMimage;
    private int jmlDC;
    private int jmlDM;
    private int jmlPemain;
    private int hitungpemain=1;
    private int yourSide;
    private int yourIndexSide;
    private int MyNomor = -1;
    private int readymulai=0;
    private String namaLobby2;
    private Listener listener;
    private String myip= Utils.getIPAddress(true);

    //Animation Text
    private FloatingText floatingText;

    public MainScreenChooseHeroes(Game g, Music musik , final Server server, String namaLobby, String namaPlayerSvr, DebtCollector dbC, DebtMaker dbM, KlienArray klienArrey,Klien klayen, String myip2) // ** constructor called initially **//
    {
        musik.stop();
        musik.dispose();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/MusikChooseHeroes.mp3"));
        music.setLooping(true);
        music.setVolume(1f);
        music.play();

        bgblackbatch = new SpriteBatch();
        colorbgblack = bgblackbatch.getColor();
        klien = klayen;
        namaPlayerServer = namaPlayerSvr;
        onlinePlayer3 = new OnlinePlayer3();
        debtCollector = new DebtCollector();

        debtCollector = dbC;


        debtMaker = dbM;
        
        myip=myip2;


        jmlDC = getLength(debtCollector.nama);
        jmlDM = getLength(debtMaker.nama);
        jmlPemain = jmlDC+jmlDC;

        debtCollector.heroesnumber = new int[jmlDC];
        debtMaker.heroesnumber = new int[jmlDM];
        Arrays.fill(debtCollector.heroesnumber,-1);
        Arrays.fill(debtMaker.heroesnumber,-1);

        for(int i=0;i<jmlDM;i++)Gdx.app.log("DEBT MAKER", jmlDM+"constructor called");

        charMiniHeroesDCimage = new Image[debtCollector.nama.length];
        charMiniHeroesDMimage = new Image[debtMaker.nama.length];
        charMiniHeroesDCTexture = new Texture[debtCollector.nama.length];
        charMiniHeroesDMTexture = new Texture[debtMaker.nama.length];

        klienArray = klienArrey;
        jmlklien = klienArrey.namaklien.length;

        stage = new Stage();
        server2 = server;
        Gdx.app.log("my Main Screen", "constructor called");
        myGame = g; // ** get Game parameter **//
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        klik = Gdx.audio.newSound(Gdx.files.internal("music/click.mp3"));

        activeButtonPick = new Texture("MenuButtonActivePick.png");

        inactiveButtonPick = new Texture("MenuButtonInactivePick.png");

        BGSelectionCharacter = new Texture("SelectionCharacter.png");
        RoomFull = new Texture("RoomFull.png");
        BGBlack = new Texture("BlackBG.png");

        //your number id DC or DM
        yourSide=-1;
        yourIndexSide=0;
        for(int i=0;i<debtCollector.ip.length;i++){
            Gdx.app.log("Isi DC ARRAY"+i, debtCollector.ip[i]+"");
            if(debtCollector.ip[i]!=null)
            if(debtCollector.ip[i].equals(myip)) {
                yourSide = 0;
                yourIndexSide = i;
                break;
            }
        }
        if(yourSide==-1)
        for(int k=0;k<debtMaker.ip.length;k++){
            if(debtMaker.ip[k]!=null)
            if(debtMaker.ip[k].equals(myip)) {
                yourSide = 1;
                yourIndexSide = k;
                break;
            }
        }
        Gdx.app.log("Side", yourSide+" index: "+yourIndexSide);

        animationheroDC = new Animation[jumlahheroesDC];
        characterframeDC = new Texture[jumlahheroesDC];
        charframeimageDC = new Image[jumlahheroesDC];

        animationheroDM = new Animation[jumlahheroesDM];
        characterframeDM = new Texture[jumlahheroesDM];
        charframeimageDM = new Image[jumlahheroesDM];

        loadingscreen = new Animation[1];
        loadingscreen[0] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,  Gdx.files.internal("loadingscreen.gif").read(),false);

        //character frame
        for(int i=0;i<jumlahheroesDC;i++)
            characterframeDC[i] = new Texture("character/DC/character"+i+"frame.png");
        for(int i=0;i<jumlahheroesDM;i++)
            characterframeDM[i] = new Texture("character/DM/character"+i+"frame.png");

        //character tampilan
        for(int i=0;i<jumlahheroesDC;i++)
            animationheroDC[i] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("character/DC/character"+i+"/character"+i+".gif").read(),false);
        for(int i=0;i<jumlahheroesDM;i++)
            animationheroDM[i] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("character/DM/character"+i+"/character"+i+".gif").read(),false);

        //DCkoneksi = new Texture("DCkoneksi.png");
        //mini heroes atas
        for(int i=0;i<jmlDC;i++)
            charMiniHeroesDCTexture[i] = new Texture("character/characterunknownframe.png");
        for(int i=0;i<jmlDM;i++)
            charMiniHeroesDMTexture[i] = new Texture("character/characterunknownframe.png");

        float fontScalex = (5.0f*Gdx.graphics.getWidth()/2880);
        float fontScaley = (5.0f*Gdx.graphics.getHeight()/1440);

        labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        labelStyle2.fontColor = Color.WHITE;
        labeltimepick = new Label(""+timerpickheroes,labelStyle2);
        labeltimepick.setFontScale(fontScalex,fontScaley);

        labeltimepick.setPosition(1430*Gdx.graphics.getWidth()/2880,1360*Gdx.graphics.getHeight()/1440);

        stage.addActor(labeltimepick);

        tabelDaftarHeroes = new Table();
        tabelDaftarHeroes.setPosition((Gdx.graphics.getWidth()*0.35f)-(Gdx.graphics.getWidth()*0.275f), (Gdx.graphics.getHeight()*0.4f)-(Gdx.graphics.getHeight()*0.3f));
        tabelDaftarHeroes.setSize(Gdx.graphics.getWidth()*0.55f,Gdx.graphics.getHeight()*0.60f);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skin.getFont("default-font").getData().setScale(3.0f*Gdx.graphics.getWidth()/2880,3.0f*Gdx.graphics.getHeight()/1440);
        final Table labels = new Table();
        tabelDaftarHeroes.add(new ScrollPane(labels, skin)).expand().fill();
        tabelDaftarHeroes.row();

        if(yourSide==0)
        for(int i=0;i<jumlahheroesDC;i++) {
            charframeimageDC[i] = new Image(characterframeDC[i]);
            charframeimageDC[i].setSize(250*Gdx.graphics.getWidth()/2880,225*Gdx.graphics.getHeight()/1440);
            labels.add(charframeimageDC[i]).width(300*Gdx.graphics.getWidth()/2880).height(300*Gdx.graphics.getHeight()/1440);
            int finalI = i;
            charframeimageDC[i].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MyNomor =finalI;
                    Gdx.app.log("Heroes", ""+debtCollector.heroesnumber[yourIndexSide]);

                    charframeimageDC[finalI].setColor(charframeimageDC[finalI].getColor().r,charframeimageDC[finalI].getColor().g,charframeimageDC[finalI].getColor().b,0.5f);
                    klik.play();
                    udahklikhero=1f;
                    for(int k = 0;k<jumlahheroesDC;k++){
                        if(k!=finalI){
                            //charTampilanimage[k].setSize(0,0);
                            charframeimageDC[k].setColor(charframeimageDC[k].getColor().r,charframeimageDC[k].getColor().g,charframeimageDC[k].getColor().b,1f);
                        }
                    }
                    System.out.println("You clicked an "+ finalI +"image...");
                }
            });
        }
        else
            for(int i=0;i<jumlahheroesDM;i++) {
                charframeimageDM[i] = new Image(characterframeDM[i]);
                charframeimageDM[i].setSize(250*Gdx.graphics.getWidth()/2880,225*Gdx.graphics.getHeight()/1440);
                labels.add(charframeimageDM[i]).width(300*Gdx.graphics.getWidth()/2880).height(300*Gdx.graphics.getHeight()/1440);
                int finalI = i;
                charframeimageDM[i].addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        MyNomor=finalI;
                        Gdx.app.log("Heroes", ""+debtMaker.heroesnumber[yourIndexSide]);

                        charframeimageDM[finalI].setColor(charframeimageDM[finalI].getColor().r,charframeimageDM[finalI].getColor().g,charframeimageDM[finalI].getColor().b,0.5f);
                        klik.play();
                        udahklikhero=1f;
                        for(int k = 0;k<jumlahheroesDM;k++){
                            if(k!=finalI){
                                //charTampilanimage[k].setSize(0,0);
                                charframeimageDM[k].setColor(charframeimageDM[k].getColor().r,charframeimageDM[k].getColor().g,charframeimageDM[k].getColor().b,1f);
                            }
                        }
                        System.out.println("You clicked an "+ finalI +"image...");
                    }
                });
            }

        for(int i=0;i<jmlDC;i++) {
            charMiniHeroesDCimage[i] = new Image(charMiniHeroesDCTexture[i]);
            charMiniHeroesDCimage[i].setSize(200*Gdx.graphics.getWidth()/2880,170*Gdx.graphics.getHeight()/1440);
            charMiniHeroesDCimage[i].setPosition(labeltimepick.getX()-(i+1)*250*Gdx.graphics.getWidth()/2880,labeltimepick.getY()-80*Gdx.graphics.getHeight()/1440);
            stage.addActor(charMiniHeroesDCimage[i]);
        }
        System.out.println("Jumlah debt maker "+ jmlDM +" image...");
        for(int i=0;i<jmlDM;i++) {
            charMiniHeroesDMimage[i] = new Image(charMiniHeroesDMTexture[i]);
            charMiniHeroesDMimage[i].setSize(200*Gdx.graphics.getWidth()/2880,170*Gdx.graphics.getHeight()/1440);
            charMiniHeroesDMimage[i].setPosition((labeltimepick.getX()-100*Gdx.graphics.getWidth()/2880)+(i+1)*250*Gdx.graphics.getWidth()/2880,labeltimepick.getY()-80*Gdx.graphics.getHeight()/1440);
            stage.addActor(charMiniHeroesDMimage[i]);
        }
        //labels.setSize(Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/8);

        stage.addActor(tabelDaftarHeroes);

        namaLobby2 = namaLobby;

        floatingText = new FloatingText("FarhanGembel has joined the lobby!", TimeUnit.SECONDS.toMillis(3));

        stage.addActor(floatingText);

        final String[] ipkonek = new String[6];
        ipkonek[0] = "server";

        server2.addListener(listener = new Listener(){
            @Override
            public void received(Connection connection, Object object){
                if(object instanceof ClientReady) {

                    for(int i=0;i<getLength2(ipkonek);i++){
                        if(getposarray2(ipkonek,""+connection.getRemoteAddressTCP())==-1){
                                hitungpemain++;
                                ipkonek[hitungpemain-1]= ""+connection.getRemoteAddressTCP();
                                break;
                        }
                    }
                    System.out.println("posaray "+ getposarray(ipkonek,""+connection.getRemoteAddressTCP()) +" ...hitungpmn: "+ hitungpemain+"..jmlpmn:"+(getLength(debtCollector.ip)+getLength(debtMaker.ip)));
                    ClientReady request = (ClientReady) object;
                    //if(request.klienrede)hitungpemain++;
                    ClientReady dahrede = new ClientReady();
                    dahrede.jmlrede=hitungpemain;
                    server2.sendToAllTCP(dahrede);
                    if(hitungpemain==(getLength(debtCollector.ip)+getLength(debtMaker.ip))){
                        AnimateTimeChooseHeroes();
                        readymulai=1;
                        ClientReadyChoose clientReadyChoose = new ClientReadyChoose();
                        clientReadyChoose.klienrede=true;
                        server2.sendToAllTCP(clientReadyChoose);
                        hitungpemain++;
                    }
                }
                if(object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest) object;
                    if(request.text.equals("i need a new dc dm")){
                        connection.sendTCP(debtCollector);
                        connection.sendTCP(debtMaker);
                    }
                }
                if(object instanceof MyHeroesNomor) {
                    Gdx.app.log("Nomorhero: " , "Connected");
                    MyHeroesNomor request = (MyHeroesNomor) object;
                    String ipasliclient = ""+connection.getRemoteAddressTCP();
                    ipasliclient = ipasliclient.split(":")[0];
                    ipasliclient = ipasliclient.split("/")[1];
                    int itempos;
                    if(request.mySide==0){
                        itempos = getposarray(debtCollector.ip,""+ipasliclient);
                        debtCollector.heroesnumber[itempos]=request.NoHeroes;
                        charMiniHeroesDCimage[itempos].setDrawable(new SpriteDrawable(new Sprite(characterframeDC[request.NoHeroes])));
                        server2.sendToAllTCP(debtCollector);
                    }
                    else if(request.mySide==1){
                        itempos = getposarray(debtMaker.ip,""+ipasliclient);
                        debtMaker.heroesnumber[itempos]=request.NoHeroes;
                        charMiniHeroesDMimage[itempos].setDrawable(new SpriteDrawable(new Sprite(characterframeDM[request.NoHeroes])));
                        server2.sendToAllTCP(debtMaker);
                    }else
                        connection.close();
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

                    floatingText.text = klien.namaklien.get(posisiindex)+" has left the game!";
                    GlyphLayout layouto = new GlyphLayout(); //dont do this every frame! Store it as member
                    layouto.setText(floatingText.font,klien.namaklien.get(posisiindex)+" has left the game!");
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

                        int i7;
                        for(i7 =debt+1;i7<Arrays.asList(debtCollector.nama).size();i7++){
                            debtCollector.nama[i7-1] = debtCollector.nama[i7];
                            debtCollector.ip[i7-1] = debtCollector.ip[i7];
                            debtCollector.ready[i7-1] = debtCollector.ready[i7];

                            Gdx.app.log("OK ", "CLIENT DEBT COLECTOR DC");

                            if(debtCollector.nama[i7] == null){
                            }
                            else{
                            }

                        }

                        debtCollector.nama[i7-1] = null;
                        debtCollector.ip[i7-1] = null;
                        debtCollector.ready[i7-1] = false;
                    }
                    if(Arrays.asList(debtMaker.ip).contains(ipclientasli)){
                        debtMaker.nama[debtm] = null;
                        debtMaker.ip[debtm] = null;
                        debtMaker.ready[debtm] = false;

                        int i7;
                        for(i7 =debtm+1;i7<Arrays.asList(debtMaker.nama).size();i7++){
                            debtMaker.nama[i7-1] = debtMaker.nama[i7];
                            debtMaker.ip[i7-1] = debtMaker.ip[i7];
                            debtMaker.ready[i7-1] = debtMaker.ready[i7];

                            Gdx.app.log("OK ", "CLIENT DEBT MAKER DC");

                            if(debtMaker.nama[i7] == null){
                            }
                            else{
                            }

                        }

                        debtMaker.nama[i7-1] = null;
                        debtMaker.ip[i7-1] = null;
                        debtMaker.ready[i7-1] = false;
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



        Gdx.input.setCatchBackKey(true);

        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) )

                // Maybe perform other operations before exiting
                {
                    server2.stop();
                    stage.dispose();
                    music.stop();
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

    }

    public static <T> int getLength(T[] arr){
        int count = 0;
        for(T el : arr)
            if (el != null)
                ++count;
        return count;
    }

    public static <T> int getLength2(T[] arr){
        int count = 0;
        for(T el : arr)
            if (el != null)
                ++count;
        return count;
    }

    public void AnimateTimeChooseHeroes(){
        Timer timepick = new Timer();
        timepick.schedule( new com.badlogic.gdx.utils.Timer.Task(){
                            @Override
                            public void run() {
                                timerpickheroes--;
                                labeltimepick.setText(""+timerpickheroes);
                                if(timerpickheroes<=0){
                                    timepick.instance().clear();
                                    stage.dispose();
                                    texture.dispose();
                                    batch.dispose();
                                    SomeRequest gamestart = new SomeRequest();
                                    gamestart.text = "gamestart";
                                    server2.sendToAllTCP(gamestart);
                                    server2.removeListener(listener);
                                    myGame.setScreen(new GameplayScreen(myGame,music,server2,namaLobby2,namaPlayerServer,debtCollector,debtMaker,klienArray,klien,yourSide,yourIndexSide,myip));
                                }
                            }
                        }
                , 0 ,1);
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
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        if(readymulai==0){
            batch.begin();
            batch.draw(loadingscreen[0].getKeyFrame(elapsed), 0, 0, 2920 * Gdx.graphics.getWidth() / 2880, 1440 * Gdx.graphics.getHeight() / 1440);
            batch.end();
        } else
        if(readymulai==1) {
            batch.begin();

            stage.act(delta);
            //button background
            batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            batch.draw(BGSelectionCharacter, (Gdx.graphics.getWidth() * 0.35f) - (Gdx.graphics.getWidth() * 0.3f), (Gdx.graphics.getHeight() * 0.44f) - (Gdx.graphics.getHeight() * 0.4f), Gdx.graphics.getWidth() * 0.6f, Gdx.graphics.getHeight() * 0.8f);

            batch.end();

            //button Pick
            bgblackbatch.begin();
            bgblackbatch.setColor(colorbgblack.r, colorbgblack.g, colorbgblack.b, udahklikhero);
            if (Gdx.input.getX() < (Gdx.graphics.getWidth() * 0.8f) - (Gdx.graphics.getWidth() / 10) || Gdx.input.getX() > (Gdx.graphics.getWidth() * 0.8f) + (Gdx.graphics.getWidth() / 10)
                    || Gdx.input.getY() < (Gdx.graphics.getHeight() * 0.9f) - (Gdx.graphics.getHeight() / 20) || Gdx.input.getY() > (Gdx.graphics.getHeight() * 0.9) + (Gdx.graphics.getHeight() / 20)) {
                bgblackbatch.draw(inactiveButtonPick, (Gdx.graphics.getWidth() * 0.8f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
            } else {
                bgblackbatch.draw(inactiveButtonPick, (Gdx.graphics.getWidth() * 0.8f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
                if (udahklikhero == 1f) {
                    if (Gdx.input.justTouched() && roompenuh == 0) {
                        klik.play();
                        if (yourSide == 0) {
                            charMiniHeroesDCimage[yourIndexSide].setDrawable(new SpriteDrawable(new Sprite(characterframeDC[MyNomor])));
                            debtCollector.heroesnumber[yourIndexSide] = MyNomor;

                            MyHeroesNomor myHeroesNomor = new MyHeroesNomor();
                            myHeroesNomor.NoHeroes = MyNomor;
                            myHeroesNomor.mySide = yourSide;
                            myHeroesNomor.myIndexSide = yourIndexSide;
                            server2.sendToAllTCP(myHeroesNomor);
                        } else {
                            charMiniHeroesDMimage[yourIndexSide].setDrawable(new SpriteDrawable(new Sprite(characterframeDM[MyNomor])));
                            debtMaker.heroesnumber[yourIndexSide] = MyNomor;

                            MyHeroesNomor myHeroesNomor = new MyHeroesNomor();
                            myHeroesNomor.NoHeroes = MyNomor;
                            myHeroesNomor.mySide = yourSide;
                            myHeroesNomor.myIndexSide = yourIndexSide;
                            server2.sendToAllTCP(myHeroesNomor);
                        }
                        //stage.dispose();
                        //timer.instance().clear();
                        //myGame.setScreen(new MainScreenChooseHeroes(myGame,false ));
                    }
                    if (Gdx.input.isTouched() && roompenuh == 0) {
                        bgblackbatch.draw(activeButtonPick, (Gdx.graphics.getWidth() * 0.8f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
                    } else
                        bgblackbatch.draw(inactiveButtonPick, (Gdx.graphics.getWidth() * 0.8f) - (Gdx.graphics.getWidth() / 10), (Gdx.graphics.getHeight() * 0.1f) - (Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10);
                }
            }
            bgblackbatch.end();
            stage.draw();

            batch.begin();
            if (yourSide == 0) {
                if (udahklikhero == 1)
                    batch.draw(animationheroDC[MyNomor].getKeyFrame(elapsed), 2000 * Gdx.graphics.getWidth() / 2880, 520 * Gdx.graphics.getHeight() / 1440, 680 * Gdx.graphics.getWidth() / 2880, 700 * Gdx.graphics.getHeight() / 1440);
            } else {
                if (udahklikhero == 1)
                    batch.draw(animationheroDM[MyNomor].getKeyFrame(elapsed), 2000 * Gdx.graphics.getWidth() / 2880, 520 * Gdx.graphics.getHeight() / 1440, 680 * Gdx.graphics.getWidth() / 2880, 700 * Gdx.graphics.getHeight() / 1440);
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
    public int getposarray2(String[] arrneed, String str){
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
        texture = new Texture(Gdx.files.internal("BGChooseHeroes.png")); //** texture is now the main image **//
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