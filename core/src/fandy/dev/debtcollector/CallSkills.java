package fandy.dev.debtcollector;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.audio.Music;

import static com.badlogic.gdx.math.MathUtils.random;

public class CallSkills
    {

    public void CallSkills(Object goblok) {
            GameplayScreen gs = (GameplayScreen) goblok;

            Timer timerattack = new Timer();
            gs.skillsAttack.addListener(new ClickListener() {

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    gs.skillsAttack.setColor(gs.skillsAttack.getColor().r, gs.skillsAttack.getColor().g, gs.skillsAttack.getColor().b, 0.5f);
                    super.enter(event, x, y, pointer, fromActor);
                }
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!gs.attacksound[0].isPlaying() || !gs.attacksound[1].isPlaying())
                        gs.attacksound[random(0, 1)].play();
                    if (gs.yourSide == 0) {
                        if (gs.gerak[gs.yourIndexSide] == 0 || gs.gerak[gs.yourIndexSide] == 2)
                            gs.gerak[gs.yourIndexSide] = 4;
                        else if (gs.gerak[gs.yourIndexSide] == 1 || gs.gerak[gs.yourIndexSide] == 3)
                            gs.gerak[gs.yourIndexSide] = 5;
                    } else {
                        if (gs.gerak[gs.getLength(gs.debtCollector.ip) + gs.yourIndexSide] == 0 || gs.gerak[gs.getLength(gs.debtCollector.ip) + gs.yourIndexSide] == 2)
                            gs.gerak[gs.getLength(gs.debtCollector.ip) + gs.yourIndexSide] = 4;
                        else if (gs.gerak[gs.getLength(gs.debtCollector.ip) + gs.yourIndexSide] == 1 || gs.gerak[gs.getLength(gs.debtCollector.ip) + gs.yourIndexSide] == 3)
                            gs.gerak[gs.getLength(gs.debtCollector.ip) + gs.yourIndexSide] = 5;
                    }

                    if (!gs.taskattack) {
                        gs.taskattack = true;
                        timerattack.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                                 @Override
                                                 public void run() {
                                                     float[] playerattackPos = new float[2];
                                                     float[] playerenemyPos = new float[2];
                                                     playerattackPos[0] = gs.heroes[gs.yourRealSide].posisiX;
                                                     playerattackPos[1] = gs.heroes[gs.yourRealSide].posisiZ;
                                                     if (gs.yourSide == 0)
                                                         for (int i = 0; i < gs.jmlDM; i++) {
                                                             playerenemyPos[0] = gs.heroes[gs.jmlDC + i].posisiX;
                                                             playerenemyPos[1] = gs.heroes[gs.jmlDC + i].posisiZ;
                                                             if (gs.calculateDistance(playerattackPos, playerenemyPos) < 6f) {
                                                                 gs.heroes[gs.jmlDC + i].currentHealth -= gs.heroes[gs.yourIndexSide].damage;
                                                                 if (gs.heroes[gs.jmlDC + i].currentHealth <= 0)
                                                                     gs.heroes[gs.jmlDC + i].currentHealth = 0;
                                                             }
                                                         }
                                                     else for (int i = 0; i < gs.jmlDC; i++) {
                                                         playerenemyPos[0] = gs.heroes[i].posisiX;
                                                         playerenemyPos[1] = gs.heroes[i].posisiZ;
                                                         if (gs.calculateDistance(playerattackPos, playerenemyPos) < 6f) {
                                                             gs.heroes[i].currentHealth -= gs.heroes[gs.jmlDC + gs.yourIndexSide].damage;
                                                             if (gs.heroes[i].currentHealth <= 0)
                                                                 gs.heroes[i].currentHealth = 0;
                                                         }
                                                     }
                                                     for (int j = 0; j < gs.jmlDC; j++)
                                                         gs.KirimDataHeroes(j, gs.debtCollector.ip[j]);
                                                     for (int j = 0; j < gs.jmlDM; j++)
                                                         gs.KirimDataHeroes(j + gs.jmlDC, gs.debtMaker.ip[j]);
                                                 }
                                             }
                                , 0.5f);
                        timerattack.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                                 @Override
                                                 public void run() {

                                                     if (gs.gerak[gs.yourRealSide] == 4) gs.gerak[gs.yourRealSide] = 0;
                                                     else if (gs.gerak[gs.yourRealSide] == 5) gs.gerak[gs.yourRealSide] = 1;

                                                     gs.taskattack = false;
                                                 }
                                             }
                                , 1);
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    gs.skillsAttack.setColor(gs.skillsAttack.getColor().r, gs.skillsAttack.getColor().g, gs.skillsAttack.getColor().b, 1f);
                    super.exit(event, x, y, pointer, toActor);
                }
            });

            gs.skillsImage[0].addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    gs.skillsImage[0].setColor(gs.skillsImage[0].getColor().r, gs.skillsImage[0].getColor().g, gs.skillsImage[0].getColor().b, 0.5f);
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gs.skillsImage[0].setColor(gs.skillsImage[0].getColor().r, gs.skillsImage[0].getColor().g, gs.skillsImage[0].getColor().b, 0.5f);

                    if (gs.heroes[gs.yourRealSide].currentMana >= 75 && gs.heroes[gs.yourRealSide].skillcurrentCD[0] == 0) {
                        if (!gs.skillsound[0].isPlaying()) {
                            gs.skillsound[0].setVolume(0.5f);
                            gs.skillsound[0].play();
                        }
                        gs.heroes[gs.yourRealSide].skillcurrentCD[0] = gs.heroes[gs.yourRealSide].skillCD[0];
                        gs.heroes[gs.yourRealSide].currentMana -= 75;
                        if (gs.gerak[gs.yourRealSide] == 0 || gs.gerak[gs.yourRealSide] == 2)
                            gs.gerak[gs.yourRealSide] = 4;
                        else if (gs.gerak[gs.yourRealSide] == 1 || gs.gerak[gs.yourRealSide] == 3)
                            gs.gerak[gs.yourRealSide] = 5;

                        gs.activateSkillChar0 = 1;
                        for (int j = 0; j < gs.jmlDC; j++) gs.KirimDataHeroes(j, gs.debtCollector.ip[j]);
                        for (int j = 0; j < gs.jmlDM; j++) gs.KirimDataHeroes(j + gs.jmlDC, gs.debtMaker.ip[j]);
                        gs.tmpactivateSkillChar0 = gs.touchpad.getKnobPercentX() / 4;
                        gs.tmpactivateSkillChar02 = gs.touchpad.getKnobPercentY() / 4;
                    } else {
                        if (!gs.notenoughmanaNskillsCD[0].isPlaying() || !gs.notenoughmanaNskillsCD[1].isPlaying()) {
                            if (gs.heroes[gs.yourRealSide].currentMana < 75)
                                gs.notenoughmanaNskillsCD[0].play();
                            if (gs.heroes[gs.yourRealSide].skillcurrentCD[0] > 0)
                                gs.notenoughmanaNskillsCD[1].play();
                        }
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    gs.skillsImage[0].setColor(gs.skillsImage[0].getColor().r, gs.skillsImage[0].getColor().g, gs.skillsImage[0].getColor().b, 1f);
                    super.exit(event, x, y, pointer, toActor);
                }
            });
        }
    }

