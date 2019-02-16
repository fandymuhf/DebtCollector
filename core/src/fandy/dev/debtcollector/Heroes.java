package fandy.dev.debtcollector;

public class Heroes {
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
    public int[] skillLevel = new int[4];
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

    public Heroes(int side, int nomorheroes){
        if(side==0 && nomorheroes==0) {
            this.name = "Savior";
            this.maxHealth = 1000;
            this.currentHealth = 1000;
            this.maxMana = 300;
            this.currentMana = 300;
            this.movementspd = 1f;
            this.damage = 50f;
            this.posisiX = 0;
            this.posisiY = 4;
            this.posisiZ = 0;
            for (int i = 0; i < 4; i++) {
                this.skillCD[i] = 10;
                this.skillcurrentCD[i] = 0;
                this.skillmanacost[i] = 75;
                this.skillLevel[i] = 0;
                this.itemsCD[i] = 10;
                this.itemscurrentCD[i] = 0;
                this.itemsmanacost[i] = 50;
            }
            this.lvl = 1;
            this.exp = 0;
            this.gold = 500;
            this.stunned = 0;
            this.alive = true;
        }

        if(side==0 && nomorheroes==1) {
            this.name = "Zombloe";
            this.maxHealth = 800;
            this.currentHealth = 800;
            this.maxMana = 400;
            this.currentMana = 400;
            this.movementspd = 0.5f;
            this.damage = 65f;
            this.posisiX = 0;
            this.posisiY = 4;
            this.posisiZ = 0;
            for (int i = 0; i < 4; i++) {
                this.skillCD[i] = 10;
                this.skillcurrentCD[i] = 0;
                this.skillmanacost[i] = 75;
                this.skillLevel[i] = 0;
                this.itemsCD[i] = 10;
                this.itemscurrentCD[i] = 0;
                this.itemsmanacost[i] = 50;
            }
            this.lvl = 1;
            this.exp = 0;
            this.gold = 500;
            this.stunned = 0;
            this.alive = true;
        }

        if(side==1 && nomorheroes==0) {
            this.name = "Miranda";
            this.maxHealth = 500;
            this.currentHealth = 500;
            this.maxMana = 350;
            this.currentMana = 350;
            this.movementspd = 1f;
            this.damage = 20f;
            this.posisiX = 0;
            this.posisiY = 4;
            this.posisiZ = 0;
            for (int i = 0; i < 4; i++) {
                this.skillCD[i] = 10;
                this.skillcurrentCD[i] = 0;
                this.skillmanacost[i] = 75;
                this.skillLevel[i] = 0;
                this.itemsCD[i] = 10;
                this.itemscurrentCD[i] = 0;
                this.itemsmanacost[i] = 50;
            }
            this.lvl = 1;
            this.exp = 0;
            this.gold = 500;
            this.stunned = 0;
            this.alive = true;
        }
        if(side==1 && nomorheroes==1) {
            this.name = "Kettie";
            this.maxHealth = 450;
            this.currentHealth = 450;
            this.maxMana = 380;
            this.currentMana = 380;
            this.movementspd = 1f;
            this.damage = 35f;
            this.posisiX = 0;
            this.posisiY = 4;
            this.posisiZ = 0;
            for (int i = 0; i < 4; i++) {
                this.skillCD[i] = 10;
                this.skillcurrentCD[i] = 0;
                this.skillmanacost[i] = 75;
                this.skillLevel[i] = 0;
                this.itemsCD[i] = 10;
                this.itemscurrentCD[i] = 0;
                this.itemsmanacost[i] = 50;
            }
            this.lvl = 1;
            this.exp = 0;
            this.gold = 500;
            this.stunned = 0;
            this.alive = true;
        }

    }

}