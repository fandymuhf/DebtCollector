package fandy.dev.debtcollector;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmConstructionInfo;
import com.badlogic.gdx.physics.bullet.collision.btSphereBoxCollisionAlgorithm;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.esotericsoftware.kryonet.Server;

import java.util.ArrayList;
import java.util.Date;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.utils.Timer.schedule;

public class GameplayScreenClient extends Listener implements Screen {
    public PerspectiveCamera cam;
    public ModelBatch modelBatch;
    public Model[] modellantai = new Model[5];
    public Model modelplayer;
    public Model modelbuilding;
    public Model modelroof;
    public Model modelgrass;
    public Model modelcar;
    public Model modeltree;
    public Model[] modelasphalt = new Model[3];
    public ModelInstance[] instanceLantai = new ModelInstance[5];
    public ModelInstance instanceGrass;
    public Array<ModelInstance> instancePlayer = new Array<ModelInstance>();
    public ModelInstance instanceBuilding;
    public ModelInstance instanceRoof;
    public ModelInstance[] instanceAsphalt = new ModelInstance[13];
    public ModelInstance[] coinInstance = new ModelInstance[5];
    public ModelInstance[] sodaInstance = new ModelInstance[5];


    private TextureAttribute textureAttributeTiles;
    public Environment environment;
    private float x=10f;
    private Texture texture;
    private Sprite sprite;
    private float posX=0, posZ=0;
    private SpriteBatch batch;
    private float elapsed;
    public int[] gerak;
    private Material material;
    private Vector3 tmp;
    private float xPlayer;
    private float zPlayer;
    private int disablex=0;
    private int disablez=0;
    private Music[] footstep;
    public Music[] attacksound;
    public Music[] skillsound;
    private Music[] carsound;
    private Music[] punch;
    public int activateSkillChar0=0;
    public float tmpactivateSkillChar0=0;
    public float tmpactivateSkillChar02=0;
    private ModelLoader loader = new ObjLoader();
    private Model modelTree;
    private ModelInstance instanceTree;
    private DirectionalShadowLight shadowLight;
    private ModelBatch shadowBatch;
    private String myIp;

    //touchpad
    private Stage stage;
    //private SpriteBatch batch;
    public Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private float blockSpeed;
    private BlendingAttribute blendingAttribute;

    public CameraInputController camController;

    //collision
    btCollisionConfiguration collisionConfig;
    btDispatcher dispatcher;
    btCollisionShape groundShape;
    btCollisionShape carShape;
    btCollisionShape playerShape;
    btCollisionObject groundObject;
    Array<btCollisionObject> treeObject = new Array<btCollisionObject>();
    Array<btCollisionObject> catObject = new Array<btCollisionObject>();
    Array<btCollisionObject> playerObject = new Array<btCollisionObject>();
    Array<btCollisionObject> carObject = new Array<btCollisionObject>();
    Array<btCollisionObject> coinObject = new Array<btCollisionObject>();
    Array<btCollisionObject> sodaObject = new Array<btCollisionObject>();
    private boolean collision;
    Vector3 positionBuilding;

    public AssetManager assets;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Array<ModelInstance> instancesobj = new Array<ModelInstance>();
    public Array<ModelInstance> instancesobjTree = new Array<ModelInstance>();
    public Array<ModelInstance> instancesobjCat = new Array<ModelInstance>();
    public Array<ModelInstance> instancesobjCoin = new Array<ModelInstance>();
    public Array<ModelInstance> instancesobjSoda = new Array<ModelInstance>();
    public ModelInstance catInstance;
    public ModelInstance[] treeInstance = new ModelInstance[6];
    public boolean loading;

    //skills
    Image skillsAttack;
    Image[] skillsImage;

    //car
    private Array<Float> posCarX = new Array<Float>();
    private Array<Float> posCarZ = new Array<Float>();
    private boolean ketabrakcar = false;
    private Array<Integer> carbelok = new Array<Integer>();
    private Array<Float> carspeed = new Array<Float>();
    private float[] calculDisPlyr = new float[2];
    private float[] calculDisObj = new float[2];

    //initiate var
    private Game myGame;
    private Music music;
    private Client client2;
    private String namaLobby2;
    private String namaPlayerServer;
    public DebtCollector debtCollector;
    public DebtMaker debtMaker;
    private KlienArray klienArray;
    private Klien klien;
    public int yourSide;
    public int yourIndexSide;
    private int myNomorHeroes;
    private Timer timermatikanChoose;
    private float lovey = -15;
    public boolean LoadAll =  false;
    public Model modelcoin;
    public Model modelsoda;

    private TextureAttribute[] attribute;
    private TextureAtlas[][] atlas;
    private ArrayList<ArrayList<Array<TextureAtlas.AtlasRegion>>> regions1;
    private ArrayList<Array<TextureAtlas.AtlasRegion>> regions2;
    private ArrayList<ArrayList<Array<TextureAtlas.AtlasRegion>>> regionsDM1;
    private ArrayList<Array<TextureAtlas.AtlasRegion>> regionsDM2;
    private Array<TextureAtlas.AtlasRegion> regions3;
    private float[] time;
    private int[] index;
    private ModelInstance[] instancegila;
    private int[] activateRenderOrang = new int[2];

    private int readymulai=0;
    private int jmlPemain;
    private int hitungpemain=1;
    private Animation<TextureRegion>[] loadingscreen;

    //fps
    float timefps = 0;
    float tick = 1 / 30f;
    int maxUpdatesPerFrame = 5;
    float syncGerakFPS = 0;
    private int rede =0;

    //frameatas
    private Label.LabelStyle labelStyle2;
    private Label labeltimeend;
    private int timerminutes=2;
    private int timersecond=30;
    private Image[] charMiniHeroesDCimage;
    private Image[] charMiniHeroesDMimage;
    private Texture[] charMiniHeroesDCTexture;
    private Texture[] charMiniHeroesDMTexture;
    public Music[] notenoughmanaNskillsCD;
    public int jmlDC;
    public int jmlDM;
    public int yourRealSide;
    public boolean taskattack = false;
    private boolean taskketabrak = false;
    private boolean taskskill1char0 = false;

    //Heroes var
    Heroes[] heroes;
    public Label labelGold;
    public Label labelExp;

    //playerName render
    private Model[] namePlayerModel;
    private ModelInstance[] namePlayerInstance;
    private FrameBuffer[] lFbPlayerName;
    private BitmapFont[] playerFontName;
    private GlyphLayout[] glyphLayoutPlayerName;
    private Matrix4[] lm;

    //Health bar
    private NinePatchDrawable[] loadingBarBackground;
    private NinePatchDrawable[] loadingBar;
    private NinePatchDrawable[] loadingManaBarBackground;
    private NinePatchDrawable[] loadingManaBar;

    public GameplayScreenClient(Game g, Music musik , final Client client, String namaLobby, String namaPlayerSvr, DebtCollector dbC, DebtMaker dbM, KlienArray klienArrey, Klien klayen, int mySide, int myIndexSide, String myip2){
        musik.stop();
        musik.dispose();

        //initiate var
        myGame = g;
        client2 = client;
        namaLobby2 = namaLobby;
        namaPlayerServer = namaPlayerSvr;
        debtCollector = dbC;
        debtMaker = dbM;
        jmlDC = getLength(debtCollector.ip);
        jmlDM = getLength(debtMaker.ip);
        jmlPemain = getLength(debtCollector.ip)+getLength(debtMaker.ip);

        namePlayerInstance = new ModelInstance[jmlPemain];
        glyphLayoutPlayerName = new GlyphLayout[jmlPemain];
        playerFontName = new BitmapFont[jmlPemain];
        lm = new Matrix4[jmlPemain];
        loadingBarBackground = new NinePatchDrawable[jmlPemain];
        loadingBar = new NinePatchDrawable[jmlPemain];
        loadingManaBarBackground = new NinePatchDrawable[jmlPemain];
        loadingManaBar = new NinePatchDrawable[jmlPemain];

        time = new float[jmlPemain];
        index = new int[jmlPemain];
        for(int i=0;i<time.length;i++)time[i] = 1;
        for(int i=0;i<index.length;i++)index[i] = -1;

        myIp= myip2;
        activateRenderOrang[0]=0;

        klienArray = klienArrey;
        klien = klayen;
        yourSide = mySide;
        yourIndexSide = myIndexSide;
        if(yourSide==0)yourRealSide = yourIndexSide;
        else yourRealSide = jmlDC+yourIndexSide;
        heroes = new Heroes[jmlPemain];
        for(int i=0;i<jmlDC;i++)heroes[i] = new Heroes(0,debtCollector.heroesnumber[i]);
        for(int i=0;i<jmlDM;i++)heroes[jmlDC+i] = new Heroes(1,debtMaker.heroesnumber[i]);

        if(yourSide==0){
            if(debtCollector.heroesnumber[yourIndexSide]!=-1)
                myNomorHeroes=debtCollector.heroesnumber[yourIndexSide];
            else myNomorHeroes=random(0,1);
        }
        else {
            if(debtMaker.heroesnumber[yourIndexSide]!=-1)
                myNomorHeroes=debtMaker.heroesnumber[yourIndexSide];
            else myNomorHeroes=random(0,1);
        }

        stage = new Stage();

        //frameatas
        float fontScalex = (5.0f*Gdx.graphics.getWidth()/2880);
        float fontScaley = (5.0f*Gdx.graphics.getHeight()/1440);

        labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = new BitmapFont(Gdx.files.internal("skin/default.fnt"));
        labelStyle2.fontColor = Color.WHITE;
        labeltimeend = new Label(timerminutes+":"+timersecond,labelStyle2);
        labeltimeend.setFontScale(fontScalex,fontScaley);

        labeltimeend.setPosition(1430*Gdx.graphics.getWidth()/2880,1360*Gdx.graphics.getHeight()/1440);

        stage.addActor(labeltimeend);

        charMiniHeroesDCimage = new Image[debtCollector.nama.length];
        charMiniHeroesDMimage = new Image[debtMaker.nama.length];
        charMiniHeroesDCTexture = new Texture[debtCollector.nama.length];
        charMiniHeroesDMTexture = new Texture[debtMaker.nama.length];

        for(int i=0;i<jmlDC;i++) {
            charMiniHeroesDCTexture[i] = new Texture("character/DC/character"+debtCollector.heroesnumber[i]+"frame.png");
            charMiniHeroesDCimage[i] = new Image(charMiniHeroesDCTexture[i]);
            charMiniHeroesDCimage[i].setSize(200*Gdx.graphics.getWidth()/2880,170*Gdx.graphics.getHeight()/1440);
            charMiniHeroesDCimage[i].setPosition(labeltimeend.getX()-(i+1)*250*Gdx.graphics.getWidth()/2880,labeltimeend.getY()-80*Gdx.graphics.getHeight()/1440);
            stage.addActor(charMiniHeroesDCimage[i]);
        }
        for(int i=0;i<jmlDM;i++) {
            charMiniHeroesDMTexture[i] = new Texture("character/DM/character"+debtMaker.heroesnumber[i]+"frame.png");
            charMiniHeroesDMimage[i] = new Image(charMiniHeroesDMTexture[i]);
            charMiniHeroesDMimage[i].setSize(200*Gdx.graphics.getWidth()/2880,170*Gdx.graphics.getHeight()/1440);
            charMiniHeroesDMimage[i].setPosition((labeltimeend.getX()-50*Gdx.graphics.getWidth()/2880)+(i+1)*250*Gdx.graphics.getWidth()/2880,labeltimeend.getY()-80*Gdx.graphics.getHeight()/1440);
            stage.addActor(charMiniHeroesDMimage[i]);
        }

        //collision
        Bullet.init();

        footstep = new Music[2];
        notenoughmanaNskillsCD = new Music[2];
        attacksound = new Music[2];
        skillsound = new Music[4];
        carsound = new Music[2];
        punch = new Music[1];
        footstep[0] = Gdx.audio.newMusic(Gdx.files.internal("music/footstep1.mp3"));
        footstep[1] = Gdx.audio.newMusic(Gdx.files.internal("music/footstep2.mp3"));
        attacksound[0] = Gdx.audio.newMusic(Gdx.files.internal("music/attacksword0.mp3"));
        attacksound[1] = Gdx.audio.newMusic(Gdx.files.internal("music/attacksword1.mp3"));
        carsound[0] = Gdx.audio.newMusic(Gdx.files.internal("music/car0.mp3"));
        carsound[1] = Gdx.audio.newMusic(Gdx.files.internal("music/car1.mp3"));
        punch[0] = Gdx.audio.newMusic(Gdx.files.internal("music/punch.mp3"));
        for(int i=0;i<4;i++)
            if(yourSide==0)skillsound[i] = Gdx.audio.newMusic(Gdx.files.internal("character/DC/character"+myNomorHeroes+"/skills/skill"+i+".mp3"));
            else if(yourSide==1)skillsound[i] = Gdx.audio.newMusic(Gdx.files.internal("character/DM/character"+myNomorHeroes+"/skills/skill"+i+".mp3"));
        notenoughmanaNskillsCD[0] = Gdx.audio.newMusic(Gdx.files.internal("music/notenoughmana.mp3"));
        notenoughmanaNskillsCD[1] = Gdx.audio.newMusic(Gdx.files.internal("music/skillsonCD.mp3"));

        carsound[0].setVolume(0);
        carsound[0].setLooping(true);
        carsound[0].play();
        carsound[1].setVolume(0);
        carsound[1].play();
        punch[0].setVolume(1);

        loadingscreen = new Animation[1];
        loadingscreen[0] = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,  Gdx.files.internal("loadingscreen.gif").read(),false);

        gerak = new int[getLength(debtCollector.ip)+getLength(debtMaker.ip)];
        atlas = new TextureAtlas[jmlPemain][8];

        regions1 = new ArrayList<ArrayList<Array<TextureAtlas.AtlasRegion>>>();
        regions2 = new ArrayList<Array<TextureAtlas.AtlasRegion>>();
        regionsDM1 = new ArrayList<ArrayList<Array<TextureAtlas.AtlasRegion>>>();
        regionsDM2 = new ArrayList<Array<TextureAtlas.AtlasRegion>>();

        for(int i=0;i<getLength(debtCollector.ip);i++) {
            for(int k=0;k<8;k++){
                Gdx.app.log("dcheroes ", +debtCollector.heroesnumber[i]+"..k: "+k);
                if(k==0 || k==1)atlas[i][k] = new TextureAtlas(Gdx.files.internal("character/DC/character"+debtCollector.heroesnumber[i]+"/idle.txt"));
                else if(k==2 || k==3)atlas[i][k] = new TextureAtlas(Gdx.files.internal("character/DC/character"+debtCollector.heroesnumber[i]+"/run.txt"));
                else if(k==4 || k==5)atlas[i][k] = new TextureAtlas(Gdx.files.internal("character/DC/character"+debtCollector.heroesnumber[i]+"/attack.txt"));
                else if(k==6 || k==7)atlas[i][k] = new TextureAtlas(Gdx.files.internal("character/DC/character"+debtCollector.heroesnumber[i]+"/dead.txt"));

                if(k%2==0){
                    regions3 = atlas[i][k].getRegions();
                    regions2.add(atlas[i][k].getRegions());
                }
                else {
                    regions3 = atlas[i][k].getRegions();
                    for(int j=0;j<regions3.size;j++)
                        atlas[i][k].getRegions().get(j).flip(true,false);

                    regions2.add(atlas[i][k].getRegions());
                }
            }
            regions1.add(regions2);
            regions2 = new ArrayList<Array<TextureAtlas.AtlasRegion>>();
        }

        for(int i=getLength(debtCollector.ip);i<jmlPemain;i++) {
            for(int k=0;k<8;k++){
                Gdx.app.log("dmheroes ", +debtMaker.heroesnumber[i-getLength(debtCollector.ip)]+"..k: "+k);
                if(k==0 || k==1)atlas[i][k] = new TextureAtlas(Gdx.files.internal("character/DM/character"+debtMaker.heroesnumber[i-getLength(debtCollector.ip)]+"/idle.txt"));
                else if(k==2 || k==3)atlas[i][k] = new TextureAtlas(Gdx.files.internal("character/DM/character"+debtMaker.heroesnumber[i-getLength(debtCollector.ip)]+"/run.txt"));
                else if(k==4 || k==5)atlas[i][k] = new TextureAtlas(Gdx.files.internal("character/DM/character"+debtMaker.heroesnumber[i-getLength(debtCollector.ip)]+"/attack.txt"));
                else if(k==6 || k==7)atlas[i][k] = new TextureAtlas(Gdx.files.internal("character/DM/character"+debtMaker.heroesnumber[i-getLength(debtCollector.ip)]+"/dead.txt"));


                if(k%2==0){
                    regions3 = atlas[i][k].getRegions();
                    regions2.add(atlas[i][k].getRegions());
                }
                else {
                    regions3 = atlas[i][k].getRegions();
                    for(int j=0;j<regions3.size;j++)
                        atlas[i][k].getRegions().get(j).flip(true,false);
                    regions2.add(atlas[i][k].getRegions());
                }
            }
            regionsDM1.add(regions2);
            regions2 = new ArrayList<Array<TextureAtlas.AtlasRegion>>();

        }
        Gdx.app.log("isi ", "regions1 "+regions1.get(0));
        //Gdx.app.log("isi ", "regions1 "+regions1.get(1));
        Gdx.app.log("isi ", "regions2 "+regionsDM1.get(0));
        //Gdx.app.log("isi ", "regions2 "+regionsDM1.get(1));

        //skills
        Array<Texture> skills = new Array<Texture>();
        skillsImage = new Image[4];
        skills.add(new Texture("Skills/attack0.png"));
        for(int i=0;i<4;i++) {
            if(yourSide==0)skills.add(new Texture("character/DC/character"+myNomorHeroes+"/skills/skill"+i+".png"));
            else skills.add(new Texture("character/DM/character"+myNomorHeroes+"/skills/skill"+i+".png"));
            skillsImage[i] = new Image(skills.get(i+1));
        }
        skillsAttack = new Image(skills.get(0));
        skillsAttack.setSize(300*Gdx.graphics.getWidth()/2880,300*Gdx.graphics.getHeight()/1440);
        skillsAttack.setPosition(2500*Gdx.graphics.getWidth()/2880,150*Gdx.graphics.getHeight()/1440);
        skillsImage[0].setSize(150*Gdx.graphics.getWidth()/2880,150*Gdx.graphics.getHeight()/1440);
        skillsImage[0].setPosition(2300*Gdx.graphics.getWidth()/2880,100*Gdx.graphics.getHeight()/1440);
        skillsImage[1].setSize(150*Gdx.graphics.getWidth()/2880,150*Gdx.graphics.getHeight()/1440);
        skillsImage[1].setPosition(2300*Gdx.graphics.getWidth()/2880,300*Gdx.graphics.getHeight()/1440);
        skillsImage[2].setSize(150*Gdx.graphics.getWidth()/2880,150*Gdx.graphics.getHeight()/1440);
        skillsImage[2].setPosition(2500*Gdx.graphics.getWidth()/2880,500*Gdx.graphics.getHeight()/1440);
        skillsImage[3].setSize(150*Gdx.graphics.getWidth()/2880,150*Gdx.graphics.getHeight()/1440);
        skillsImage[3].setPosition(2700*Gdx.graphics.getWidth()/2880,500*Gdx.graphics.getHeight()/1440);
        stage.addActor(skillsAttack);
        for(int i=0;i<4;i++)
            stage.addActor(skillsImage[i]);

        //function SKills
        CallSkillsClient cs = new CallSkillsClient();
        cs.CallSkillsClient(this);

        modelBatch = new ModelBatch();
        batch = new SpriteBatch();
        Texture tiles = new Texture("object/tiles.jpg");
        Texture woodtiles = new Texture("object/woodtiles.jpg");
        Texture grass = new Texture("object/grassgreen.jpg");
        Texture building = new Texture("object/building.jpg");
        Texture roof = new Texture("object/roof.jpg");
        Texture asphalt = new Texture("object/tunnel_road.jpg");
        Texture asphaltcenter = new Texture("object/tunnel_road_center.jpg");
        tiles.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        building.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        roof.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        asphalt.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        asphaltcenter.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        textureAttributeTiles = new TextureAttribute(TextureAttribute.Diffuse, tiles);
        blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        blendingAttribute.opacity= 1f;

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        createtouchpad();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 1f, -1.8f, -0.4f));
        shadowLight = new DirectionalShadowLight(1024, 1024, 200, 200, 1f, 300);
        shadowLight.set(0.5f, 0.5f, 0.5f, 1f, -.8f, -.4f);
        environment.add(shadowLight);
        environment.shadowMap = shadowLight;
        shadowBatch = new ModelBatch(new DepthShaderProvider());

        //camController = new CameraInputController(cam);
        //Gdx.input.setInputProcessor(camController);

        ModelBuilder modelBuilder = new ModelBuilder();
        modellantai[0] = modelBuilder.createBox(100f, 1f, 100f,
                new Material(TextureAttribute.createDiffuse(tiles)),
                Usage.Position | Usage.Normal | Usage.TextureCoordinates);
        modellantai[1] = modelBuilder.createBox(100f, 1f, 100f,
                new Material(TextureAttribute.createDiffuse(woodtiles)),
                Usage.Position | Usage.Normal | Usage.TextureCoordinates);
        modelgrass = modelBuilder.createBox(50, 2f, 40,
                new Material(TextureAttribute.createDiffuse(grass)),
                Usage.Position | Usage.Normal | Usage.TextureCoordinates);
        modelplayer = modelBuilder.createBox(2f, 8f, 2f,
                new Material(ColorAttribute.createDiffuse(1,1,1,0.0f),blendingAttribute),
                Usage.Position | Usage.Normal);
        modelbuilding = modelBuilder.createBox(20f, 20f, 20f,
                new Material(TextureAttribute.createDiffuse(building),blendingAttribute),
                Usage.Position | Usage.Normal | Usage.TextureCoordinates);
        modelroof = modelBuilder.createBox(20f, 1, 20f,
                new Material(TextureAttribute.createDiffuse(roof),blendingAttribute),
                Usage.Position | Usage.Normal | Usage.TextureCoordinates);
        //model aspal horizontal
        modelasphalt[0] = modelBuilder.createBox(100f, 2, 40f,
                new Material(TextureAttribute.createDiffuse(asphalt)),
                Usage.Position | Usage.Normal | Usage.TextureCoordinates);
        //model aspal center
        modelasphalt[2] = modelBuilder.createBox(40, 2, 40f,
                new Material(TextureAttribute.createDiffuse(asphaltcenter)),
                Usage.Position | Usage.Normal | Usage.TextureCoordinates);
        modelcar = modelBuilder.createBox(4f, 4f, 2f,
                new Material(ColorAttribute.createDiffuse(1,1,1,0.5f),blendingAttribute),
                Usage.Position | Usage.Normal);

        instanceLantai[0] = new ModelInstance(modellantai[0],0,0,0);
        instanceLantai[1] = new ModelInstance(modellantai[1],140,0,0);
        instanceGrass = new ModelInstance(modelgrass,20,0.01f,30);

        final Material material = new Material(ColorAttribute.createDiffuse(1f, 1f, 1f, 1f), new TextureAttribute(TextureAttribute.Diffuse),blendingAttribute);
        Model modelgila = modelBuilder.createBox(0.00001f, 5, 8, material, Usage.Position | Usage.Normal | Usage.TextureCoordinates);

        instancegila = new ModelInstance[getLength(debtCollector.ip)+getLength(debtMaker.ip)];
        attribute = new TextureAttribute[getLength(debtCollector.ip)+getLength(debtMaker.ip)];
        //Gdx.app.log("TextureRegion3DTest", getLength(debtCollector.ip)+".."+getLength(debtMaker.ip)+"GILA1");

        for(int i=0;i<getLength(debtCollector.ip);i++) {

            instancegila[i] = new ModelInstance(modelgila);
            instancePlayer.add(new ModelInstance(modelplayer,random(5,15),4,-40));
            attribute[i] = instancegila[i].materials.get(0).get(TextureAttribute.class, TextureAttribute.Diffuse);
            attribute[i].set(regions1.get(i).get(gerak[i]).get(0));
            instancegila[i].transform.setToTranslation(-0.15f,5,12);
            instancegila[i].transform.rotate(Vector3.X,90);
            Gdx.app.log("TextureRegion3DTest", instancegila[i]+"GILA1");
        }
        for(int i=getLength(debtCollector.ip);i<jmlPemain;i++) {

            instancegila[i] = new ModelInstance(modelgila);
            instancePlayer.add(new ModelInstance(modelplayer,random(5,15),4,-20));
            attribute[i] = instancegila[i].materials.get(0).get(TextureAttribute.class, TextureAttribute.Diffuse);
            attribute[i].set(regionsDM1.get(i-jmlDC).get(gerak[i]).get(0));
            instancegila[i].transform.setToTranslation(-0.15f,5,12);
            instancegila[i].transform.rotate(Vector3.X,90);
            Gdx.app.log("TextureRegion3DTest", instancegila[i]+"GILA2");
        }

        //building blue
        instanceBuilding = new ModelInstance(modelbuilding,20f,5f,30);
        positionBuilding = instanceBuilding.transform.getTranslation(new Vector3());
        instancesobj.add(new ModelInstance(modelcar,0,0f,60));
        instanceRoof = new ModelInstance(modelroof,20f,15.1f,30f);
        //asphalt road
        instanceAsphalt[0] = new ModelInstance(modelasphalt[0],0,0,70f);
        instanceAsphalt[1] = new ModelInstance(modelasphalt[0],0,0,-70f);
        instanceAsphalt[2] = new ModelInstance(modelasphalt[0]);
        instanceAsphalt[2].transform.setToRotation(Vector3.Y, 90);
        instanceAsphalt[2].transform.setTranslation(70,0,0);
        instanceAsphalt[7] = new ModelInstance(modelasphalt[0]);
        instanceAsphalt[7].transform.setToRotation(Vector3.Y, 90);
        instanceAsphalt[7].transform.setTranslation(-70,0,0);
        instanceAsphalt[12] = new ModelInstance(modelasphalt[0]);
        instanceAsphalt[12].transform.setToRotation(Vector3.Y, 90);
        instanceAsphalt[12].transform.setTranslation(210,0,0);
        instanceAsphalt[8] = new ModelInstance(modelasphalt[0],140,0,70f);
        instanceAsphalt[9] = new ModelInstance(modelasphalt[0],140,0,-70f);
        //asphalt center
        instanceAsphalt[3] = new ModelInstance(modelasphalt[2],70,0,70f);
        instanceAsphalt[4] = new ModelInstance(modelasphalt[2],70,0,-70f);
        instanceAsphalt[5] = new ModelInstance(modelasphalt[2],-70,0,-70f);
        instanceAsphalt[6] = new ModelInstance(modelasphalt[2],-70,0,70f);
        instanceAsphalt[10] = new ModelInstance(modelasphalt[2],210,0,-70f);
        instanceAsphalt[11] = new ModelInstance(modelasphalt[2],210,0,70f);

        assets = new AssetManager();
        assets.load("object/lowpoyltree.obj", Model.class);
        assets.load("object/Protect_Van/Protect_Van.obj", Model.class);
        assets.load("object/Warehouse/Warehouse.obj", Model.class);
        assets.load("object/House/casa.obj", Model.class);
        assets.load("object/Cat/cat.obj", Model.class);
        assets.load("object/MoneyBag/coin.obj", Model.class);
        assets.load("object/Soda_Can/14025_Soda_Can_v3_l3.obj", Model.class);
        assets.load("object/love/loveintan.obj", Model.class);
        assets.load("object/love/intan.obj", Model.class);
        loading = true;
        assets.finishLoading();

        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        groundShape = new btBoxShape(new Vector3(10f, 10, 10));
        carShape = new btBoxShape(new Vector3(10, 0, 5));
        playerShape = new btBoxShape(new Vector3(2.5f, 0.5f, 2.5f));

        groundObject = new btCollisionObject();
        groundObject.setCollisionShape(groundShape);
        groundObject.setWorldTransform(instanceBuilding.transform);

        carObject.add(new btCollisionObject());
        carObject.get(0).setCollisionShape(carShape);
        carObject.get(0).setWorldTransform(instancesobj.get(0).transform);

        for(int i=0;i<getLength(debtCollector.ip);i++) {
            playerObject.add(new btCollisionObject());
            playerObject.get(i).setCollisionShape(playerShape);
            playerObject.get(i).setWorldTransform(instancePlayer.get(i).transform);
        }
        for(int i=0;i<getLength(debtMaker.ip);i++) {
            playerObject.add(new btCollisionObject());
            playerObject.get(getLength(debtCollector.ip)+i).setCollisionShape(playerShape);
            playerObject.get(getLength(debtCollector.ip)+i).setWorldTransform(instancePlayer.get(getLength(debtCollector.ip)+i).transform);
        }

        //Get name player
        for(int i=0;i<jmlDC;i++) {
            glyphLayoutPlayerName[i] = new GlyphLayout();
            playerFontName[i] = new BitmapFont(Gdx.files.internal("skin/default.fnt"),true);
            playerFontName[i].getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
            Gdx.app.log("Nama player", "Nama = "+glyphLayoutPlayerName[i]+"..jml dc: "+playerFontName[i]);
            glyphLayoutPlayerName[i].setText(playerFontName[i], "" + debtCollector.nama[i]);
            playerFontName[i].setColor(playerFontName[i].getColor().r, playerFontName[i].getColor().g, playerFontName[i].getColor().b, 0.5f);
        }
        for(int i=0;i<jmlDM;i++) {
            glyphLayoutPlayerName[jmlDC+i] = new GlyphLayout();
            playerFontName[jmlDC+i] = new BitmapFont(Gdx.files.internal("skin/default.fnt"),true);
            playerFontName[jmlDC+i].getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
            glyphLayoutPlayerName[jmlDC+i].setText(playerFontName[jmlDC+i], "" + debtMaker.nama[i]);
            playerFontName[jmlDC+i].setColor(playerFontName[jmlDC+i].getColor().r, playerFontName[jmlDC+i].getColor().g, playerFontName[jmlDC+i].getColor().b, 0.5f);
        }

        Material[] lMaterial = new Material[jmlPemain];
        namePlayerModel = new Model[jmlPemain];
        lFbPlayerName = new FrameBuffer[jmlPemain];
        //Generate new framebuffer object of 128x128 px. This will be our texture
        for(int i=0;i<jmlPemain;i++) {
            lm[i] = new Matrix4();
            lFbPlayerName[i] = new FrameBuffer(Pixmap.Format.RGBA4444, 128, 128, false);
            lMaterial[i] = new Material(TextureAttribute.createDiffuse(lFbPlayerName[i].getColorBufferTexture()), blendingAttribute);
            namePlayerModel[i] = modelBuilder.createBox(0.0001f, 10, 3, lMaterial[i],
                    Usage.Position | Usage.Normal | Usage.TextureCoordinates);
        }
        for(int i=0;i<jmlPemain;i++) {
            namePlayerInstance[i] = new ModelInstance(namePlayerModel[i]);
            namePlayerInstance[i].transform.setToTranslation(0,11f,0);
            namePlayerInstance[i].transform.rotate(Vector3.X,90);
        }

        //health bar
        TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
        NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
        NinePatch loadingManaBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
        NinePatch loadingManaBarPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
        for(int i=0;i<jmlPemain;i++) {
            loadingBar[i] = new NinePatchDrawable(loadingBarPatch);
            loadingManaBar[i] = new NinePatchDrawable(loadingManaBarPatch);
            loadingBar[i].getPatch().setColor(Color.valueOf("00ff24"));
            loadingManaBar[i].getPatch().setColor(Color.valueOf("1b65ff"));
            loadingBarBackground[i] = new NinePatchDrawable(loadingBarBackgroundPatch);
            loadingManaBarBackground[i] = new NinePatchDrawable(loadingManaBarBackgroundPatch);
            loadingBarBackground[i].getPatch().setColor(new Color(loadingBarBackground[i].getPatch().getColor().r,loadingBarBackground[i].getPatch().getColor().g,loadingBarBackground[i].getPatch().getColor().b,0.8f));
            loadingManaBarBackground[i].getPatch().setColor(new Color(loadingManaBarBackground[i].getPatch().getColor().r,loadingBarBackground[i].getPatch().getColor().g,loadingBarBackground[i].getPatch().getColor().b,0.8f));
        }

        if(yourSide == 0) {
            labelGold = new Label("Gold : " + heroes[yourIndexSide].gold,labelStyle2);
            labelGold.setFontScale(fontScalex,fontScaley);
            labelGold.setPosition(2430*Gdx.graphics.getWidth()/2880,1360*Gdx.graphics.getHeight()/1440);
            stage.addActor(labelGold);

            labelExp = new Label("Exp : " + heroes[yourIndexSide].exp,labelStyle2);
            labelExp.setFontScale(fontScalex,fontScaley);
            labelExp.setPosition(2430*Gdx.graphics.getWidth()/2880,1260*Gdx.graphics.getHeight()/1440);
            stage.addActor(labelExp);
        }
        else{
            labelGold = new Label("Gold : " + heroes[jmlDC + yourIndexSide].gold,labelStyle2);
            labelGold.setFontScale(fontScalex,fontScaley);
            labelGold.setPosition(2430*Gdx.graphics.getWidth()/2880,1360*Gdx.graphics.getHeight()/1440);
            stage.addActor(labelGold);

            labelExp = new Label("Exp : " + heroes[jmlDC + yourIndexSide].exp,labelStyle2);
            labelExp.setFontScale(fontScalex,fontScaley);
            labelExp.setPosition(2430*Gdx.graphics.getWidth()/2880,1260*Gdx.graphics.getHeight()/1440);
            stage.addActor(labelExp);
        }

        tmp = new Vector3();
        if(yourSide==0)instancePlayer.get(yourIndexSide).transform.getTranslation(tmp);
        else instancePlayer.get(getLength(debtCollector.ip)+yourIndexSide).transform.getTranslation(tmp);
        xPlayer = tmp.x;
        zPlayer = tmp.z;

        cam.position.set(tmp.z - 20f, 20f, tmp.x);
        cam.lookAt(tmp.z, 0, tmp.x);
        cam.rotate(45, 0, 0, 0);
        cam.update();

        posCarX.add(0f);
        posCarZ.add(60f);
        carbelok.add(0);
        carbelok.add(0);
        carspeed.add(0f);



        client2.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof PosisiLove){
                    PosisiLove posisiLove = (PosisiLove) object;
                    loveInstance.transform.setTranslation(-20,posisiLove.loves,20);
                    loveInstance2.transform.setTranslation(-20,posisiLove.loves+3,20);
                }
                if(object instanceof PosisiCoin){
                    PosisiCoin pc = (PosisiCoin) object;
                    for(int i=0;i<5;i++) {
                        int x = pc.x[i];
                        int y = pc.y[i];
                        int z = pc.z[i];



                        coinInstance[i].transform.setTranslation(x, y, z);
                        instancesobjCoin.set(i, new ModelInstance(modelcoin, x, 0, z));
                        coinObject.get(i).setWorldTransform(instancesobjCoin.get(i).transform);


                    }
                }
                if(object instanceof PosisiSoda){
                    PosisiSoda pc = (PosisiSoda) object;
                    for(int i=0;i<5;i++) {
                        int x = pc.x[i];
                        int y = pc.y[i];
                        int z = pc.z[i];



                        sodaInstance[i].transform.setTranslation(x, y, z);
                        instancesobjSoda.set(i, new ModelInstance(modelsoda, x, 0, z));
                        sodaObject.get(i).setWorldTransform(instancesobjSoda.get(i).transform);


                    }
                }

                if(object instanceof DataHeroes) {
                    DataHeroes dataHeroes = (DataHeroes) object;

                    int itempos=-1;
                    int side=0;
                    int realposisi=0;
                    itempos = getposarray2(debtCollector.ip,""+dataHeroes.iphero);
                    if(itempos==-1){
                        itempos = getposarray2(debtMaker.ip,""+dataHeroes.iphero);
                        side=1;
                    }
                    if(side==0)realposisi=itempos;
                    else realposisi=jmlDC+itempos;

                    heroes[realposisi].alive = dataHeroes.alive;
                    heroes[realposisi].currentHealth = dataHeroes.currentHealth;
                    heroes[realposisi].currentMana = dataHeroes.currentMana;
                    heroes[realposisi].damage = dataHeroes.damage;
                    heroes[realposisi].exp = dataHeroes.exp;
                    heroes[realposisi].gold = dataHeroes.gold;
                    heroes[realposisi].itemsCD = dataHeroes.itemsCD;
                    heroes[realposisi].itemscurrentCD = dataHeroes.itemscurrentCD;
                    heroes[realposisi].itemsmanacost= dataHeroes.itemsmanacost;
                    heroes[realposisi].lvl = dataHeroes.lvl;
                    heroes[realposisi].maxHealth = dataHeroes.maxHealth;
                    heroes[realposisi].maxMana = dataHeroes.maxMana;
                    heroes[realposisi].movementspd= dataHeroes.movementspd;
                    heroes[realposisi].name = dataHeroes.name;
                    heroes[realposisi].posisiX = dataHeroes.posisiX;
                    heroes[realposisi].posisiY = dataHeroes.posisiY;
                    heroes[realposisi].posisiZ = dataHeroes.posisiZ;
                    heroes[realposisi].skillCD = dataHeroes.skillCD;
                    heroes[realposisi].skillcurrentCD = dataHeroes.skillcurrentCD;
                    heroes[realposisi].skillmanacost = dataHeroes.skillmanacost;
                    heroes[realposisi].stunned = dataHeroes.stunned;
                }
                if(object instanceof PosisiCar) {
                    PosisiCar response = (PosisiCar) object;
                    posCarX.set(0,response.posisiXcar);
                    posCarZ.set(0,response.posisiZcar);
                }
                if(object instanceof TimerPickNow) {
                    TimerPickNow response = (TimerPickNow) object;
                    if(response.timedetik<10)labeltimeend.setText(response.timemenit+":0"+response.timedetik);
                    else labeltimeend.setText(response.timemenit+":"+response.timedetik);


                }
                if(object instanceof ClientReadyChoose) {
                    ClientReadyChoose response = (ClientReadyChoose) object;
                    hitungpemain++;

                }
                if(object instanceof ClientReady) {
                    ClientReady request = (ClientReady) object;
                    hitungpemain=request.jmlrede;
                    if(hitungpemain==jmlPemain){
                        readymulai=1;

                    }

                }
                if(rede==1 && hitungpemain>=jmlPemain)
                    if(object instanceof PosisiHero) {

                        PosisiHero posisiHero = (PosisiHero) object;
                        /*String ipasliclient = ""+connection.getRemoteAddressTCP();
                        ipasliclient = ipasliclient.split(":")[0];
                        ipasliclient = ipasliclient.split("/")[1];*/
                        int itempos=-1;
                        int side=0;
                        itempos = getposarray2(debtCollector.ip,""+posisiHero.iphero);
                        if(itempos==-1){
                            itempos = getposarray2(debtMaker.ip,""+posisiHero.iphero);
                            side=1;
                        }

                        //Gdx.app.log("posheroX", instancePlayer.get(itempos)+" Current ip server = "+ipasliclient);

                        if(side==0)instancePlayer.get(itempos).transform.setTranslation(posisiHero.posisiXhero+1f,0,posisiHero.posisiZhero);
                        else instancePlayer.get(getLength(debtCollector.ip)+itempos).transform.setTranslation(posisiHero.posisiXhero+1f,0,posisiHero.posisiZhero);

                        if(side==0)playerObject.get(itempos).setWorldTransform(instancePlayer.get(itempos).transform);
                        else playerObject.get(getLength(debtCollector.ip)+itempos).setWorldTransform(instancePlayer.get(getLength(debtCollector.ip)+itempos).transform);

                        if(side==0)gerak[itempos]=posisiHero.gerakhero;
                        else gerak[getLength(debtCollector.ip)+itempos]=posisiHero.gerakhero;

                        float myDelta;
                        if(side==mySide && myIndexSide==yourIndexSide)myDelta=Gdx.graphics.getDeltaTime();
                        else myDelta=posisiHero.delta;

                        if (side == 0){
                            //namePlayerInstance[itempos].transform.setToTranslation(posisiHero.posisiXhero,11f,posisiHero.posisiZhero);
                            //namePlayerInstance[itempos].transform.rotate(Vector3.X,90);
                            Vector3 tmpClientVector2 = namePlayerInstance[itempos].transform.getTranslation(new Vector3());
                            namePlayerInstance[itempos].transform.translate((posisiHero.posisiXhero - tmpClientVector2.x), (posisiHero.posisiZhero - tmpClientVector2.z), 0);

                            Vector3 tmpClientVector = instancegila[itempos].transform.getTranslation(new Vector3());
                            instancegila[itempos].transform.translate((posisiHero.posisiXhero - 0.15f - tmpClientVector.x), (posisiHero.posisiZhero - tmpClientVector.z), 0);
                            //instancegila[itempos].transform.rotate(Vector3.X, 90);
                            if ((time[itempos] += myDelta) >= 0.075f) {
                                time[itempos] -= 0.075f;
                                index[itempos] = (index[itempos] + 1) % regions1.get(itempos).get(gerak[itempos]).size;
                                attribute[itempos].set(regions1.get(itempos).get(gerak[itempos]).get(index[itempos]));
                                //Gdx.app.log("TextureRegion3DTest", "Current region = "+regions.get(index).name);
                            }
                        } else {
                            //namePlayerInstance[jmlDC+itempos].transform.setToTranslation(posisiHero.posisiXhero,11f,posisiHero.posisiZhero);
                            //namePlayerInstance[jmlDC+itempos].transform.rotate(Vector3.X,90);
                            Vector3 tmpClientVector2 = namePlayerInstance[getLength(debtCollector.ip) +itempos].transform.getTranslation(new Vector3());
                            namePlayerInstance[getLength(debtCollector.ip) + itempos].transform.translate((posisiHero.posisiXhero - tmpClientVector2.x), (posisiHero.posisiZhero - tmpClientVector2.z), 0);

                            Vector3 tmpClientVector = instancegila[getLength(debtCollector.ip) +itempos].transform.getTranslation(new Vector3());
                            instancegila[getLength(debtCollector.ip) + itempos].transform.translate((posisiHero.posisiXhero - 0.15f - tmpClientVector.x), (posisiHero.posisiZhero - tmpClientVector.z), 0);
                            //instancegila[getLength(debtCollector.ip) + itempos].transform.rotate(Vector3.X, 90);
                            if ((time[getLength(debtCollector.ip) + itempos] += myDelta) >= 0.075f) {
                                time[getLength(debtCollector.ip) + itempos] -= 0.075f;
                                //Gdx.app.log("TextureRegion3DTest", " region1 = "+itempos);
                                index[getLength(debtCollector.ip) + itempos] = (index[getLength(debtCollector.ip) + itempos] + 1) % regionsDM1.get(itempos).get(gerak[getLength(debtCollector.ip) + itempos]).size;
                                attribute[getLength(debtCollector.ip) + itempos].set(regionsDM1.get(itempos).get(gerak[getLength(debtCollector.ip) + itempos]).get(index[getLength(debtCollector.ip) + itempos]));
                            }
                        }
                        if(yourSide==side && yourIndexSide==itempos) {
                            tmp.z = posisiHero.posisiXhero;
                            tmp.x = posisiHero.posisiZhero;
                        }

                        activateRenderOrang[1]=1;
                    }
            }
        });




    }

    ModelInstance loveInstance;
    ModelInstance loveInstance2;

    private void doneLoading() {
        Model tree = assets.get("object/lowpoyltree.obj", Model.class);
        Model car = assets.get("object/Protect_Van/Protect_Van.obj", Model.class);
        Model warehouse = assets.get("object/Warehouse/Warehouse.obj", Model.class);
        Model house = assets.get("object/House/casa.obj", Model.class);
        Model cat = assets.get("object/Cat/cat.obj", Model.class);
        Model coin = assets.get("object/MoneyBag/coin.obj", Model.class);
        Model soda = assets.get("object/Soda_Can/14025_Soda_Can_v3_l3.obj", Model.class);
        Model love2 = assets.get("object/love/loveintan.obj", Model.class);
        Model love = assets.get("object/love/intan.obj", Model.class);
        treeInstance = new ModelInstance[6];
        ModelInstance carInstance = new ModelInstance(car);
        ModelInstance warehouseInstance = new ModelInstance(warehouse);
        catInstance = new ModelInstance(cat);
        ModelInstance[] HouseInstance = new ModelInstance[6];
        loveInstance = new ModelInstance(love);
        loveInstance2 = new ModelInstance(love2);
        for(int i=0;i<HouseInstance.length;i++)
            HouseInstance[i] = new ModelInstance(house);
        for(int i=0;i<treeInstance.length;i++)
            treeInstance[i] = new ModelInstance(tree);
        for(int i=0,j=40;i<treeInstance.length;i++) {
            treeInstance[i].transform.setToScaling(5, 5, 5);
            treeInstance[i].transform.setTranslation(j, -5, -40);
            j-=10;
        }
        for(int i=0;i<coinInstance.length;i++)
            coinInstance[i] = new ModelInstance(coin);
        for(int i=0;i<sodaInstance.length;i++)
            sodaInstance[i] = new ModelInstance(soda);

        warehouseInstance.transform.setToScaling(5,5,5);
        warehouseInstance.transform.rotate(Vector3.Y,90);
        warehouseInstance.transform.setTranslation(0,0,-150);
        for(int i=0,j=-25;i<HouseInstance.length/2;i++) {
            HouseInstance[i].transform.setToScaling(5, 5, 5);
            HouseInstance[i].transform.rotate(Vector3.Y, 180);
            HouseInstance[i].transform.setTranslation(160, 0, j);
            j+=25;
        }
        for(int k=3,l=-25;k<HouseInstance.length;k++) {
            HouseInstance[k].transform.setToScaling(5, 5, 5);
            HouseInstance[k].transform.rotate(Vector3.Y, 180);
            HouseInstance[k].transform.setTranslation(120, 0, l);
            l+=25;
        }
        catInstance.transform.setToScaling(25,25,25);
        catInstance.transform.rotate(Vector3.Y,270);
        catInstance.transform.setTranslation(0,0,-10);
        carInstance.transform.setToScaling(2,2,2);
        carInstance.transform.rotate(Vector3.Y,90);
        carInstance.transform.setTranslation(0,1,60);
        instances.add(warehouseInstance);
        instances.add(carInstance);
        ModelBuilder modelBuilder = new ModelBuilder();
        modeltree = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(1,1,1,0.0f),blendingAttribute),
                Usage.Position | Usage.Normal);
        Model modelkakitangancat = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(1,1,1,0.0f),blendingAttribute),
                Usage.Position | Usage.Normal);
        modelcoin = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(1,1,1,0.0f),blendingAttribute),
                Usage.Position | Usage.Normal);
        modelsoda = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(1,1,1,0.0f),blendingAttribute),
                Usage.Position | Usage.Normal);
        btBoxShape treeShape = new btBoxShape(new Vector3(2.5f, 2.5f, 2.5f));
        btBoxShape catShape = new btBoxShape(new Vector3(2.5f, 2.5f, 2.5f));
        btBoxShape coinShape = new btBoxShape(new Vector3(2.5f, 2.5f, 2.5f));
        btBoxShape sodaShape = new btBoxShape(new Vector3(2.5f, 2.5f, 2.5f));
        for(int i=0,j=40;i<treeInstance.length;i++) {
            instances.add(treeInstance[i]);
            instancesobjTree.add(new ModelInstance(modeltree,j,0,-40));
            j-=10;
            treeObject.add(new btCollisionObject());
            treeObject.get(i).setCollisionShape(treeShape);
            treeObject.get(i).setWorldTransform(instancesobjTree.get(i).transform);
            treeObject.get(i).setCollisionFlags(treeObject.get(i).getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
        }
        for(int i=0,j=0;i<2;i++) {
            instancesobjCat.add(new ModelInstance(modelkakitangancat,j,0,-10));
            j-=15;
            catObject.add(new btCollisionObject());
            catObject.get(i).setCollisionShape(catShape);
            catObject.get(i).setWorldTransform(instancesobjCat.get(i).transform);
        }
        for(int i=0;i<5;i++){
            coinInstance[i].transform.setToScaling(10,10,10);
            coinInstance[i].transform.rotate(Vector3.X,90);
            coinInstance[i].transform.setTranslation(0,3,-30);

            instancesobjCoin.add(new ModelInstance(modelcoin,0,0,-30));
            coinObject.add(new btCollisionObject());
            coinObject.get(i).setCollisionShape(coinShape);
            coinObject.get(i).setWorldTransform(instancesobjCoin.get(i).transform);
            instances.add(coinInstance[i]);

            PosisiCoin pc = new PosisiCoin();
            client2.sendTCP(pc);

        }
        for(int i=0;i<5;i++){
            sodaInstance[i].transform.rotate(Vector3.X,90);
            sodaInstance[i].transform.setTranslation(0,0,-30);
            sodaInstance[i].transform.setToRotation(Vector3.X,270);
            sodaInstance[i].transform.scale(0.35f,0.35f,0.35f);

            instancesobjSoda.add(new ModelInstance(modelsoda,0,0,-30));
            sodaObject.add(new btCollisionObject());
            sodaObject.get(i).setCollisionShape(sodaShape);
            sodaObject.get(i).setWorldTransform(instancesobjSoda.get(i).transform);
            instances.add(sodaInstance[i]);

            PosisiSoda pc = new PosisiSoda();
            client2.sendTCP(pc);

        }

        for(int i=0;i<HouseInstance.length;i++) instances.add(HouseInstance[i]);
        instances.add(catInstance);

        loveInstance.transform.setToScaling(5,5,5);
        loveInstance.transform.rotate(Vector3.Y,270);
        loveInstance.transform.setTranslation(-20,lovey,20);
        loveInstance2.transform.setToScaling(5,5,5);
        loveInstance2.transform.rotate(Vector3.Y,270);
        loveInstance2.transform.setTranslation(-20,lovey+3,20);
        instances.add(loveInstance);
        instances.add(loveInstance2);
        loading = false;


    }


    public void createtouchpad(){
        batch = new SpriteBatch();
        //Create camera

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        Texture touchbg = new Texture("touchpad/touchBackground.png");
        touchpadSkin.add("touchBackground", touchbg);
        touchpadSkin.newDrawable("touchBackground",0f,0,0,0.5f);
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("touchpad/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(200*Gdx.graphics.getWidth()/2880, 200*Gdx.graphics.getHeight()/1440, 500*Gdx.graphics.getWidth()/2880, 500*Gdx.graphics.getHeight()/1440);
        touchpad.setColor(touchpad.getColor().r,touchpad.getColor().g,touchpad.getColor().b,0.5f);
        //Create a Stage and add TouchPad
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);

        blockSpeed = 5;


    }

    public static float calculateDistance(float[] array1, float[] array2)
    {
        double Sum = 0.0;
        for(int i=0;i<array1.length;i++) {
            Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
        }
        return (float)Math.sqrt(Sum);
    }

    public void KirimDataHeroesClient(){
        SomeRequest request = new SomeRequest();
        request.text = "attack";
        client2.sendTCP(request);
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
    public void show() {

    }

    @Override
    public void render(float delta) {
        syncGerakFPS = Gdx.graphics.getDeltaTime() / 0.016f;
        elapsed += Gdx.graphics.getDeltaTime() * syncGerakFPS;
        //camController.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        timefps += Gdx.graphics.getDeltaTime();
        int updatesThisFrame = 0;
        while (timefps >= tick && updatesThisFrame < maxUpdatesPerFrame) {
            updatesThisFrame++;
            timefps -= tick;
        }

        //Hide objek on player with transparant
        //Gedung
        if (tmp.z - 1 > 30 && tmp.z - 1 < 50 && tmp.x + 1 > 20 && tmp.x + 1 < 40) {
            instanceBuilding.materials.get(0).set(ColorAttribute.createDiffuse(1, 1, 1, 0.25f));
            instanceRoof.materials.get(0).set(ColorAttribute.createDiffuse(1, 1, 1, 0.25f));
        } else {
            instanceBuilding.materials.get(0).set(ColorAttribute.createDiffuse(1, 1, 1, 1f));
            instanceRoof.materials.get(0).set(ColorAttribute.createDiffuse(1, 1, 1, 1f));

        }
        //Pohon
        if (LoadAll == true) {
            for (int i = 0, j = 40; i < treeInstance.length; i++) {
                if (tmp.z - 1 > j + 2.5f && tmp.z - 1 < j + 2.5f + 5f && tmp.x + 1 > -42.5 && tmp.x + 1 < -37.5) {
                    treeInstance[i].materials.get(1).set(ColorAttribute.createDiffuse(1, 1, 1, 0.25f), blendingAttribute);
                    treeInstance[i].materials.get(0).set(ColorAttribute.createDiffuse(1, 1, 1, 0.25f), blendingAttribute);
                } else {
                    treeInstance[i].materials.get(1).set(ColorAttribute.createDiffuse(1, 1, 1, 1.00f), blendingAttribute);
                    treeInstance[i].materials.get(0).set(ColorAttribute.createDiffuse(1, 1, 1, 1.00f), blendingAttribute);
                }
                j -= 10;
            }

            //Kucing
            int cobas = 0;
            for (int i = 0, j = 0; i < 2; i++) {
                if (tmp.z - 1 > j + 2.5f && tmp.z - 1 < j + 2.5f + 5f && tmp.x + 1 > -12.5 && tmp.x + 1 < -7.5) {
                    catInstance.materials.get(0).set(ColorAttribute.createDiffuse(1, 1, 1, 0.25f), blendingAttribute);

                } else {
                    cobas++;
                }
                j -= 15;
            }
            if (cobas == 2) {
                catInstance.materials.get(0).set(ColorAttribute.createDiffuse(1, 1, 1, 1.00f), blendingAttribute);
            }
        }


        //Gdx.app.log("GET DELTA", "Current delta = "+Gdx.graphics.getDeltaTime()+"..RAW: "+Gdx.graphics.getRawDeltaTime());

        if (loading && assets.update()){
            doneLoading();
            LoadAll = true;
            java.util.TimerTask task = new java.util.TimerTask() {
                public void run() {
                    Gdx.app.log("Depth ", "helo");
                    for(int indek=0;indek<5;indek++){
                        coinInstance[indek].transform.rotate(Vector3.Z,10);
                        sodaInstance[indek].transform.rotate(Vector3.Z,10);
                    }
                }
            };
            java.util.Timer timer = new java.util.Timer("Timer");

            timer.schedule(task, new Date(),100L);


        }


        if (readymulai == 0) {
            batch.begin();
            batch.draw(loadingscreen[0].getKeyFrame(elapsed), 0, 0, 2920 * Gdx.graphics.getWidth() / 2880, 1440 * Gdx.graphics.getHeight() / 1440);
            batch.end();
            PosisiHero posisiHero = new PosisiHero();
            posisiHero.posisiXhero = tmp.z;
            posisiHero.posisiZhero = tmp.x;
            if (yourSide == 0) posisiHero.gerakhero = gerak[yourIndexSide];
            else posisiHero.gerakhero = gerak[getLength(debtCollector.ip) + yourIndexSide];
            posisiHero.iphero = myIp;
            posisiHero.delta = Gdx.graphics.getDeltaTime();
            client2.sendTCP(posisiHero);
            xPlayer = tmp.x-0.01f;
            zPlayer = tmp.z-0.01f;

        } else if (readymulai == 1 && !loading){

            shadowLight.begin(Vector3.Zero, cam.direction);
            shadowBatch.begin(shadowLight.getCamera());
            shadowBatch.render(instances);
            shadowBatch.render(instanceBuilding);
            shadowBatch.render(instanceRoof);
            shadowBatch.render(instancePlayer);

            shadowBatch.end();
            shadowLight.end();

            if (yourSide == 0) {
                    PosisiHero posisiHero = new PosisiHero();
                    posisiHero.posisiXhero = tmp.z;
                    posisiHero.posisiZhero = tmp.x;
                    posisiHero.gerakhero = gerak[yourIndexSide];
                    posisiHero.iphero = myIp;
                    posisiHero.delta = Gdx.graphics.getDeltaTime();
                    client2.sendTCP(posisiHero);
            } else {
                    PosisiHero posisiHero = new PosisiHero();
                    posisiHero.posisiXhero = tmp.z;
                    posisiHero.posisiZhero = tmp.x;
                    posisiHero.gerakhero = gerak[getLength(debtCollector.ip) + yourIndexSide];
                    posisiHero.iphero = myIp;
                    posisiHero.delta = Gdx.graphics.getDeltaTime();
                    client2.sendTCP(posisiHero);

            }

            /*
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            posX-=0.1f;
            posZ+=0.1f;
            gerak=1;
            cam.lookAt(tmp.x,0,tmp.z);
            cam.position.set(tmp.x+10f, 20f, tmp.z+10f);
            cam.update();
            instancePlayer.transform.setTranslation(posX,0,posZ);
        }else
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            posX+=0.1f;
            posZ-=0.1f;
            gerak=1;
            cam.lookAt(posX,0,posZ);
            cam.position.set(posX+10f, 20f, posZ+10f);
            instancePlayer.transform.setTranslation(posX,0,posZ);
            cam.update();
        }else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            posX-=0.1f;
            posZ-=0.1f;
            gerak=1;
            cam.lookAt(posX,0,posZ);
            cam.position.set(posX+10f, 20f, posZ+10f);
            instancePlayer.transform.setTranslation(posX,0,posZ);
            cam.update();
        }else
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            posX+=0.1f;
            posZ+=0.1f;
            gerak=1;
            cam.lookAt(posX,0,posZ);
            cam.position.set(posX+10f, 20f, posZ+10f);
            instancePlayer.transform.setTranslation(posX,0,posZ);
            cam.update();
        }*/
            for(int i=0;i<jmlPemain;i++) {
                lFbPlayerName[i].begin();

                lm[i].setToOrtho2D(0, 0, 100, 48);
                batch.setProjectionMatrix(lm[i]);

                batch.begin();
                playerFontName[i].draw(batch, glyphLayoutPlayerName[i], 50-glyphLayoutPlayerName[i].width/2, 32);
                loadingBarBackground[i].draw(batch, 10, 0, 80, glyphLayoutPlayerName[i].height);
                if(heroes[i].currentHealth>0)loadingBar[i].draw(batch, 10, 0, heroes[i].currentHealth/heroes[i].maxHealth * 80, glyphLayoutPlayerName[i].height);
                loadingManaBarBackground[i].draw(batch, 10, 16, 80, glyphLayoutPlayerName[i].height);
                if(heroes[i].currentMana>0)loadingManaBar[i].draw(batch, 10, 16, heroes[i].currentMana/heroes[i].maxMana * 80, glyphLayoutPlayerName[i].height);
                labelGold.setText("Gold : "+heroes[i].gold);
                labelExp.setText("Exp : "+heroes[i].exp);
                batch.end();
                lFbPlayerName[i].end();
            }
            batch.flush();

            modelBatch.begin(cam);
            for (int i = 0; i < getLength(instanceLantai); i++)
                modelBatch.render(instanceLantai[i], environment);
            modelBatch.render(instanceGrass, environment);
            for (int i = 0; i < getLength(instanceAsphalt); i++)
                modelBatch.render(instanceAsphalt[i], environment);
            modelBatch.render(instancePlayer, environment);
            modelBatch.render(instanceBuilding, environment);
            modelBatch.render(instanceRoof, environment);
            modelBatch.render(instances);
            if(activateRenderOrang[1]==1) {
                for (int i = 0; i < getLength(debtCollector.ip); i++) {
                    modelBatch.render(instancegila[i], environment);
                }
                for (int i = 0; i < getLength(debtMaker.ip); i++) {
                    modelBatch.render(instancegila[i + getLength(debtCollector.ip)], environment);
                }
                for(int i=0;i<jmlPemain;i++){
                    modelBatch.render(namePlayerInstance[i],environment);
                }
            }
            modelBatch.end();

            //car move
            if (!loading) {
                calculDisPlyr[0] = tmp.z;
                calculDisPlyr[1] = tmp.x;
                calculDisObj[0] = posCarX.get(0);
                calculDisObj[1] = posCarZ.get(0);
                if (calculateDistance(calculDisPlyr, calculDisObj) < 60) {
                    carsound[0].setVolume(1 - calculateDistance(calculDisPlyr, calculDisObj) / 60);
                    if (calculateDistance(calculDisPlyr, calculDisObj) < 40) {
                        carsound[1].setVolume(1f - calculateDistance(calculDisPlyr, calculDisObj) / 40);
                    }
                    if (calculateDistance(calculDisPlyr, calculDisObj) < 20) {
                        if (!carsound[1].isPlaying()) carsound[1].play();
                    }
                    //Gdx.app.log("Volume", "tabrakan"+calculateDistance(calculDisPlyr,calculDisObj));
                } else carsound[0].setVolume(0);
                /*if (posCarX.get(0) <= 60 && carbelok.get(0) == 0) {
                    carspeed.set(0, random(0.08f, 0.3f));
                    posCarX.set(0, posCarX.get(0) + carspeed.get(0));
                    posCarZ.set(0, posCarZ.get(0));
                } else if (posCarZ.get(0) >= -60 && carbelok.get(0) == 3) {
                    carspeed.set(0, random(0.08f, 0.3f));
                    posCarX.set(0, posCarX.get(0));
                    posCarZ.set(0, posCarZ.get(0) - carspeed.get(0));
                } else if (posCarX.get(0) >= -60 && carbelok.get(0) == 4) {
                    carspeed.set(0, random(0.08f, 0.3f));
                    posCarX.set(0, posCarX.get(0) - carspeed.get(0));
                    posCarZ.set(0, posCarZ.get(0));
                } else if (posCarZ.get(0) <= 60 && carbelok.get(0) == 5) {
                    carspeed.set(0, random(0.08f, 0.3f));
                    posCarX.set(0, posCarX.get(0));
                    posCarZ.set(0, posCarZ.get(0) + carspeed.get(0));
                }*/
                if (posCarX.get(0) >= 60 && carbelok.get(0) == 0) {
                    carspeed.set(0, random(0.08f, 0.12f));
                    //posCarX.set(0, posCarX.get(0));
                    //posCarZ.set(0, posCarZ.get(0) - carspeed.get(0));
                    instancesobj.get(0).transform.rotate(Vector3.Y, 1);
                    instances.get(1).transform.rotate(Vector3.Y, 1);
                    carbelok.set(1, carbelok.get(1) + 1);
                    if (carbelok.get(1) == 90) carbelok.set(0, 3);
                }
                if (posCarZ.get(0) <= -60 && carbelok.get(0) == 3) {
                    carspeed.set(0, random(0.08f, 0.12f));
                    //posCarX.set(0, posCarX.get(0) - carspeed.get(0));
                    //posCarZ.set(0, posCarZ.get(0));
                    instancesobj.get(0).transform.rotate(Vector3.Y, 1);
                    instances.get(1).transform.rotate(Vector3.Y, 1);
                    carbelok.set(1, carbelok.get(1) + 1);
                    if (carbelok.get(1) == 180) carbelok.set(0, 4);
                }
                if (posCarX.get(0) <= -60 && carbelok.get(0) == 4) {
                    carspeed.set(0, random(0.08f, 0.12f));
                    //posCarX.set(0, posCarX.get(0));
                    //posCarZ.set(0, posCarZ.get(0) + carspeed.get(0));
                    instancesobj.get(0).transform.rotate(Vector3.Y, 1);
                    instances.get(1).transform.rotate(Vector3.Y, 1);
                    carbelok.set(1, carbelok.get(1) + 1);
                    if (carbelok.get(1) == 270) carbelok.set(0, 5);
                }
                if (posCarZ.get(0) >= 60 && carbelok.get(0) == 5) {
                    carspeed.set(0, random(0.08f, 0.12f));
                    //posCarX.set(0, posCarX.get(0) + carspeed.get(0));
                    //posCarZ.set(0, posCarZ.get(0));
                    instancesobj.get(0).transform.rotate(Vector3.Y, 1);
                    instances.get(1).transform.rotate(Vector3.Y, 1);
                    carbelok.set(1, carbelok.get(1) + 1);
                    if (carbelok.get(1) == 360) {
                        //posCarX.set(0, -50f);
                        //posCarZ.set(0, 60f);
                        carbelok.set(0, 0);
                        carbelok.set(1, 0);
                    }
                }
                instancesobj.get(0).transform.setTranslation(posCarX.get(0), 0, posCarZ.get(0));
                instances.get(1).transform.setTranslation(posCarX.get(0), 0, posCarZ.get(0));
                carObject.get(0).setWorldTransform(instancesobj.get(0).transform);
            }

            //instancePlayer.transform.setTranslation(tmp.z,5f,tmp.x);
            if (!collision && !ketabrakcar) {
                xPlayer = tmp.x;
                tmp.x = tmp.x + (touchpad.getKnobPercentX() / 4)*syncGerakFPS;
                zPlayer = tmp.z;
                tmp.z = tmp.z + (touchpad.getKnobPercentY() / 4)*syncGerakFPS;
                if (tmp.x < xPlayer) {
                    if (!footstep[0].isPlaying() || footstep[1].isPlaying())
                        footstep[random(0, 1)].play();
                    if(yourSide==0)gerak[yourIndexSide] = 3;
                    else gerak[getLength(debtCollector.ip) + yourIndexSide] = 3;
                } else if (tmp.z > zPlayer || tmp.z < zPlayer) {
                    if(yourSide==0)gerak[yourIndexSide] = 2;
                    else gerak[getLength(debtCollector.ip) + yourIndexSide] = 2;
                    if (!footstep[0].isPlaying() || footstep[1].isPlaying())
                        footstep[random(0, 1)].play();
                } else if(yourSide==0) {
                    if (gerak[yourIndexSide] == 3) gerak[yourIndexSide] = 1;
                    else if (gerak[yourIndexSide] == 2)gerak[yourIndexSide] = 0;
                }
                else if(yourSide==1){
                    if (gerak[getLength(debtCollector.ip)+yourIndexSide] == 3)gerak[getLength(debtCollector.ip)+yourIndexSide] = 1;
                    else if (gerak[getLength(debtCollector.ip)+yourIndexSide] == 2)gerak[getLength(debtCollector.ip)+yourIndexSide] = 0;
                }

                if (yourSide == 0)
                    instancePlayer.get(yourIndexSide).transform.setTranslation(tmp.z + 1f, 0, tmp.x);
                else
                    instancePlayer.get(getLength(debtCollector.ip) + yourIndexSide).transform.setTranslation(tmp.z + 1f, 0, tmp.x);

                if (yourSide == 0)
                    playerObject.get(yourIndexSide).setWorldTransform(instancePlayer.get(yourIndexSide).transform);
                else
                    playerObject.get(getLength(debtCollector.ip) + yourIndexSide).setWorldTransform(instancePlayer.get(getLength(debtCollector.ip) + yourIndexSide).transform);


                cam.position.set(tmp.z - 20f, 20f, tmp.x);
                cam.lookAt(tmp.z, 0, tmp.x);
                cam.rotate(45, 0, 0, 0);
                cam.update();
                collision = checkCollision(groundObject);
                if(collision)positionBuilding = instanceBuilding.transform.getTranslation(new Vector3());
                if (!collision) {
                    collision = checkCollision(carObject.get(0));
                    if (collision) ketabrakcar = true;
                }
                for(int i=0;i<6;i++)
                if (!collision) {
                    collision = checkCollision(treeObject.get(i));
                    positionBuilding = instances.get(i+2).transform.getTranslation(new Vector3());
                }
                for(int i=0;i<2;i++)
                if (!collision) {
                    collision = checkCollision(catObject.get(i));
                    positionBuilding = instancesobjCat.get(i).transform.getTranslation(new Vector3());
                }
                if (!collision) {
                    for(int i=0;i<5;i++) {
                        collision = checkCollision(coinObject.get(i));
                        positionBuilding = instancesobjCoin.get(i).transform.getTranslation(new Vector3());
                        if (collision) {
                            TabrakCoin tc = new TabrakCoin();
                            tc.indexCoin = i;
                            tc.x = tmp.x;
                            tc.y = tmp.x;
                            tc.z = tmp.x;
                            tc.yourSide = yourRealSide;
                            client2.sendTCP(tc);
                            Sound klik = Gdx.audio.newSound(Gdx.files.internal("music/coinsound.wav"));
                            klik.play();


                            /*if (yourSide == 0) {
                                heroes[yourIndexSide].gold += 50;
                                labelGold.setText("Gold : " + heroes[yourIndexSide].gold);

                            } else {
                                heroes[jmlDC + yourIndexSide].gold += 50;
                                labelGold.setText("Gold : " + heroes[jmlDC + yourIndexSide].gold);

                            }*/
                            //randomCoin(coinInstance[i], modelcoin, 3,i);
                            //coinInstance.transform.setTranslation()
                        }
                    }
                }if (!collision) {
                    for(int i=0;i<5;i++) {
                        collision = checkCollision(coinObject.get(i));
                        positionBuilding = instancesobjCoin.get(i).transform.getTranslation(new Vector3());
                        if (collision) {
                            TabrakCoin tc = new TabrakCoin();
                            tc.indexCoin = i;
                            tc.x = tmp.x;
                            tc.y = tmp.x;
                            tc.z = tmp.x;
                            tc.yourSide = yourRealSide;
                            client2.sendTCP(tc);
                            Sound klik = Gdx.audio.newSound(Gdx.files.internal("music/coinsound.wav"));
                            klik.play();


                            /*if (yourSide == 0) {
                                heroes[yourIndexSide].gold += 50;
                                labelGold.setText("Gold : " + heroes[yourIndexSide].gold);

                            } else {
                                heroes[jmlDC + yourIndexSide].gold += 50;
                                labelGold.setText("Gold : " + heroes[jmlDC + yourIndexSide].gold);

                            }*/
                            //randomCoin(coinInstance[i], modelcoin, 3,i);
                            //coinInstance.transform.setTranslation()
                        }
                    }
                }
                if (!collision) {
                    for(int i=0;i<5;i++) {
                        collision = checkCollision(sodaObject.get(i));
                        positionBuilding = instancesobjSoda.get(i).transform.getTranslation(new Vector3());
                        if (collision) {
                            TabrakSoda tc = new TabrakSoda();
                            tc.indexCoin = i;
                            tc.x = tmp.x;
                            tc.y = tmp.x;
                            tc.z = tmp.x;
                            tc.yourSide = yourRealSide;
                            client2.sendTCP(tc);
                            Sound klik = Gdx.audio.newSound(Gdx.files.internal("music/coinsound.wav"));
                            klik.play();


                            /*if (yourSide == 0) {
                                heroes[yourIndexSide].gold += 50;
                                labelGold.setText("Gold : " + heroes[yourIndexSide].gold);

                            } else {
                                heroes[jmlDC + yourIndexSide].gold += 50;
                                labelGold.setText("Gold : " + heroes[jmlDC + yourIndexSide].gold);

                            }*/
                            //randomCoin(coinInstance[i], modelcoin, 3,i);
                            //coinInstance.transform.setTranslation()
                        }
                    }
                }


            } else {
                if (ketabrakcar) {
                    tmp.x = tmp.x + (((tmp.x - posCarZ.get(0)) * 1.03f) - (tmp.x - posCarZ.get(0)));
                    tmp.z = tmp.z + (((tmp.z - posCarX.get(0)) * 1.03f) - (tmp.z - posCarX.get(0)));
                    if (tmp.x > posCarZ.get(0)) {
                        if(yourSide==0)gerak[yourIndexSide] = 7;
                        else gerak[getLength(debtCollector.ip) + yourIndexSide] = 7;
                    }
                    else if (tmp.x <= posCarZ.get(0)) {
                        if(yourSide==0)gerak[yourIndexSide] = 6;
                        else gerak[getLength(debtCollector.ip) + yourIndexSide] = 6;
                    }
                    cam.position.set(tmp.z - 20f, 20f, tmp.x);
                    cam.lookAt(tmp.z, 0, tmp.x);
                    cam.rotate(45, 0, 0, 0);
                    cam.update();
                    punch[0].play();


                    Timer timercar = new Timer();
                    if(!taskketabrak) {
                        taskketabrak=true;
                        timercar.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                              @Override
                                              public void run() {
                                                  timercar.clear();
                                                  ketabrakcar = false;
                                                  if (yourSide == 0) {
                                                      if (gerak[yourIndexSide] == 6) gerak[yourIndexSide] = 0;
                                                      else if (gerak[yourIndexSide] == 7) gerak[yourIndexSide] = 1;
                                                  } else {
                                                      if (gerak[getLength(debtCollector.ip) + yourIndexSide] == 6)
                                                          gerak[getLength(debtCollector.ip) + yourIndexSide] = 0;
                                                      else if (gerak[getLength(debtCollector.ip) + yourIndexSide] == 7)
                                                          gerak[getLength(debtCollector.ip) + yourIndexSide] = 1;
                                                  }
                                                  taskketabrak=false;
                                              }
                                          }
                                , 0.4f);
                    }
                }else {
                    if (tmp.z < positionBuilding.x) tmp.z = tmp.z - 0.2f;
                    if (tmp.z > positionBuilding.x) tmp.z = tmp.z  + 0.2f;
                    if (tmp.x < positionBuilding.z) tmp.x = tmp.x - 0.2f;
                    if (tmp.x > positionBuilding.z) tmp.x = tmp.x + 0.2f;
                }

                Gdx.app.log("Heroes", "tabrakan" + tmp.z);
                collision = false;
            }

            //skills
            if (activateSkillChar0 == 1) {
                if (collision) activateSkillChar0 = 0;
                tmp.z = tmp.z + 2 * tmpactivateSkillChar02;
                tmp.x = tmp.x + 2 * tmpactivateSkillChar0;

                cam.position.set(tmp.z - 20f, 20f, tmp.x);
                cam.lookAt(tmp.z, 0, tmp.x);
                cam.rotate(45, 0, 0, 0);
                cam.update();
                Timer timer = new Timer();
                if(!taskskill1char0) {
                    taskskill1char0 = true;
                    timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                       @Override
                                       public void run() {
                                           activateSkillChar0 = 0;
                                           if (yourSide == 0) {
                                               if (gerak[yourIndexSide] == 4) gerak[yourIndexSide] = 0;
                                               else if (gerak[yourIndexSide] == 5) gerak[yourIndexSide] = 1;
                                           } else {
                                               if (gerak[getLength(debtCollector.ip) + yourIndexSide] == 4)
                                                   gerak[getLength(debtCollector.ip) + yourIndexSide] = 0;
                                               else if (gerak[getLength(debtCollector.ip) + yourIndexSide] == 5)
                                                   gerak[getLength(debtCollector.ip) + yourIndexSide] = 1;
                                           }
                                           timer.clear();
                                           taskskill1char0=false;

                                       }
                                   }
                            , 1);
                }
            }

            if (yourSide == 0) {
                if(xPlayer!=tmp.x || zPlayer!=tmp.z) {
                    PosisiHero posisiHero = new PosisiHero();
                    posisiHero.posisiXhero = tmp.z;
                    posisiHero.posisiZhero = tmp.x;
                    posisiHero.gerakhero = gerak[yourIndexSide];
                    posisiHero.iphero = myIp;
                    posisiHero.delta = Gdx.graphics.getDeltaTime();
                    client2.sendTCP(posisiHero);
                }
            } else {
                if(xPlayer!=tmp.x || zPlayer!=tmp.z) {
                    PosisiHero posisiHero = new PosisiHero();
                    posisiHero.posisiXhero = tmp.z;
                    posisiHero.posisiZhero = tmp.x;
                    posisiHero.gerakhero = gerak[getLength(debtCollector.ip) + yourIndexSide];
                    posisiHero.iphero = myIp;
                    posisiHero.delta = Gdx.graphics.getDeltaTime();
                    client2.sendTCP(posisiHero);
                }

            }

            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();

            if(jmlPemain==hitungpemain) {
                SomeRequest request = new SomeRequest();
                request.text = "redeklien";
                client2.sendTCP(request);
            }
        }
        if (readymulai == 0) {
            batch.begin();
            batch.draw(loadingscreen[0].getKeyFrame(elapsed), 0, 0, 2920 * Gdx.graphics.getWidth() / 2880, 1440 * Gdx.graphics.getHeight() / 1440);
            batch.end();
            ClientReady requestrede = new ClientReady();
            requestrede.klienrede = true;
            client2.sendTCP(requestrede);
            rede=1;
        }
    }

    @Override
    public void resize(int width, int height) {

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
        dispatcher.dispose();
        collisionConfig.dispose();

        modelBatch.dispose();
        batch.dispose();

        footstep[0].dispose();
        footstep[1].dispose();

        attacksound[0].dispose();
        attacksound[1].dispose();

        assets.dispose();
    }

    public void KirimGerakanClient(){
            PosisiHero posisiHero = new PosisiHero();
            posisiHero.posisiXhero = tmp.z;
            posisiHero.posisiZhero = tmp.x;
            if (yourSide == 0) posisiHero.gerakhero = gerak[yourIndexSide];
            else posisiHero.gerakhero = gerak[jmlDC + yourIndexSide];
            posisiHero.iphero = myIp;
            posisiHero.delta = Gdx.graphics.getDeltaTime();
            client2.sendTCP(posisiHero);
    }

    boolean checkCollision(btCollisionObject objekdeket) {
        CollisionObjectWrapper co0;
        if(yourSide==0)co0 = new CollisionObjectWrapper(playerObject.get(yourIndexSide));
        else co0 = new CollisionObjectWrapper(playerObject.get(getLength(debtCollector.ip)+yourIndexSide));
        CollisionObjectWrapper co1 = new CollisionObjectWrapper(objekdeket);

        btCollisionAlgorithmConstructionInfo ci = new btCollisionAlgorithmConstructionInfo();
        ci.setDispatcher1(dispatcher);
        btCollisionAlgorithm algorithm = new btSphereBoxCollisionAlgorithm(null, ci, co0.wrapper, co1.wrapper, false);

        btDispatcherInfo info = new btDispatcherInfo();
        btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);

        algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);

        boolean r = result.getPersistentManifold().getNumContacts() > 0;

        result.dispose();
        info.dispose();
        algorithm.dispose();
        ci.dispose();
        co1.dispose();
        co0.dispose();

        return r;
    }

    public static <T> int getLength(T[] arr){
        int count = 0;
        for(T el : arr)
            if (el != null)
                ++count;
        return count;
    }

    public void CallSkills(){
        Timer timerattack = new Timer();
        skillsAttack.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                skillsAttack.setColor(skillsAttack.getColor().r,skillsAttack.getColor().g,skillsAttack.getColor().b,0.5f);
                if(!attacksound[0].isPlaying() || !attacksound[1].isPlaying())attacksound[random(0,1)].play();
                if(yourSide==0){
                    if(gerak[yourIndexSide]==0 || gerak[yourIndexSide]==2)gerak[yourIndexSide]=4;
                    else if(gerak[yourIndexSide]==1 || gerak[yourIndexSide]==3)gerak[yourIndexSide]=5;
                }else {
                    if(gerak[getLength(debtCollector.ip)+yourIndexSide]==0 || gerak[getLength(debtCollector.ip)+yourIndexSide]==2)gerak[getLength(debtCollector.ip)+yourIndexSide]=4;
                    else if(gerak[getLength(debtCollector.ip)+yourIndexSide]==1 || gerak[getLength(debtCollector.ip)+yourIndexSide]==3)gerak[getLength(debtCollector.ip)+yourIndexSide]=5;
                }

                if(!taskattack) {
                    taskattack=true;
                    timerattack.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                             @Override
                                             public void run() {
                                                 float[] playerattackPos = new float[2];
                                                 float[] playerenemyPos = new float[2];
                                                 playerattackPos[0] = heroes[yourIndexSide].posisiX;
                                                 playerattackPos[1] = heroes[yourIndexSide].posisiZ;
                                                 if(yourSide==0)
                                                     for(int i=0;i<jmlDM;i++){
                                                         playerenemyPos[0] = heroes[jmlDC+i].posisiX;
                                                         playerenemyPos[1] = heroes[jmlDC+i].posisiZ;
                                                         if(calculateDistance(playerattackPos,playerenemyPos)<6f){
                                                             heroes[jmlDC+i].currentHealth-=heroes[yourIndexSide].damage;
                                                             if(heroes[jmlDC+i].currentHealth<=0)heroes[jmlDC+i].currentHealth=0;
                                                         }
                                                     } else for(int i=0;i<jmlDC;i++) {
                                                     playerenemyPos[0] = heroes[i].posisiX;
                                                     playerenemyPos[1] = heroes[i].posisiZ;
                                                     if (calculateDistance(playerattackPos, playerenemyPos) < 6f) {
                                                         heroes[i].currentHealth -= heroes[jmlDC + yourIndexSide].damage;
                                                         if(heroes[i].currentHealth<=0)heroes[i].currentHealth=0;
                                                     }
                                                 }
                                             }
                                         }
                            , 0.5f);
                    timerattack.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                             @Override
                                             public void run() {
                                                 if (yourSide == 0) {
                                                     if (gerak[yourIndexSide] == 4) gerak[yourIndexSide] = 0;
                                                     else if (gerak[yourIndexSide] == 5) gerak[yourIndexSide] = 1;
                                                 } else {
                                                     if (gerak[getLength(debtCollector.ip) + yourIndexSide] == 4)
                                                         gerak[getLength(debtCollector.ip) + yourIndexSide] = 0;
                                                     else if (gerak[getLength(debtCollector.ip) + yourIndexSide] == 5)
                                                         gerak[getLength(debtCollector.ip) + yourIndexSide] = 1;
                                                 }
                                                 KirimGerakanClient();
                                                 taskattack=false;
                                             }
                                         }
                            , 1);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                skillsAttack.setColor(skillsAttack.getColor().r,skillsAttack.getColor().g,skillsAttack.getColor().b,1f);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        skillsImage[0].addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                skillsImage[0].setColor(skillsImage[0].getColor().r,skillsImage[0].getColor().g,skillsImage[0].getColor().b,0.5f);
                if(!skillsound[0].isPlaying()){
                    skillsound[0].setVolume(0.5f);
                    skillsound[0].play();
                }
                if(yourSide==0){
                    if(gerak[yourIndexSide]==0 || gerak[yourIndexSide]==2)gerak[yourIndexSide]=4;
                    else if(gerak[yourIndexSide]==1 || gerak[yourIndexSide]==3)gerak[yourIndexSide]=5;
                }else {
                    if(gerak[getLength(debtCollector.ip)+yourIndexSide]==0 || gerak[getLength(debtCollector.ip)+yourIndexSide]==2)gerak[getLength(debtCollector.ip)+yourIndexSide]=4;
                    else if(gerak[getLength(debtCollector.ip)+yourIndexSide]==1 || gerak[getLength(debtCollector.ip)+yourIndexSide]==3)gerak[getLength(debtCollector.ip)+yourIndexSide]=5;
                }
                activateSkillChar0=1;
                tmpactivateSkillChar0=touchpad.getKnobPercentX()/4;
                tmpactivateSkillChar02=touchpad.getKnobPercentY()/4;

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                skillsImage[0].setColor(skillsImage[0].getColor().r,skillsImage[0].getColor().g,skillsImage[0].getColor().b,1f);
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

}