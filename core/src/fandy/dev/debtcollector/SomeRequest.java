package fandy.dev.debtcollector;

import java.util.ArrayList;

public class SomeRequest {
    public String text;
}

class SomeResponse{
    public String text;
    public String yourIP;
}
class SomeDCResponse{
    public String text;
    public String namaPlayer;
}
class TCPAlive{
    public String text;
}
class DebtCollector3{
    public String nama;
    public String ip;
}
class DebtMaker3{
    public String nama;
    public String ip;
}
class OnlinePlayer3{
    public String nama;
}
class LobiRoomPenuh{
    public int lobiroompenuh;
}
class ClientReady{
    public boolean klienrede;
    public int jmlrede;
}
class ClientReady2{
    public boolean klienrede;
}
class DaftarLobiFullorNot{
    public String isinya;
    public boolean bolehngga = false;
}
class GotoChooseHeroes{
    public boolean gas = false;
}
class MyHeroesNomor{
    public int NoHeroes;
    public int mySide;
    public int myIndexSide;
}
class ClientReadyChoose{
    public boolean klienrede;
    public int activaterender = 0;
}
class PosisiHero{
    public float posisiXhero = 0;
    public float posisiZhero = 0;
    public int gerakhero = 0;
    public String iphero = "";
    public float delta;
}
class PosisiLove{
    public float loves=-10;
}
class PosisiCar{
    public float posisiXcar = 0;
    public float posisiZcar = 0;
    public int gerakcar = 0;
    public String ipcar = "";
    public boolean tabrakan = false;
}
class TimerPickNow{
    public int timemenit = 0;
    public int timedetik = 0;
}
class DataHeroes{
    public String name;
    public float maxHealth;
    public float currentHealth;
    public float maxMana;
    public float currentMana;
    public float movementspd;
    public float damage;
    public float posisiX;
    public float posisiY;
    public float posisiZ;
    public float[] skillCD = new float[4];
    public float[] skillmanacost = new float[4];
    public float[] skillcurrentCD = new float[4];
    public float[] itemsCD = new float[4];
    public float[] itemsmanacost = new float[4];
    public float[] itemscurrentCD = new float[4];
    public int lvl;
    public int exp;
    public int gold;
    public boolean alive = true;
    public float stunned = 0;
    public String iphero = "";
}