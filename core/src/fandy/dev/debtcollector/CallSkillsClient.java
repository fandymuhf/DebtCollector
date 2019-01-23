package fandy.dev.debtcollector;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import static com.badlogic.gdx.math.MathUtils.random;

public class CallSkillsClient {

    public void CallSkillsClient(Object goblok) {
        GameplayScreenClient gs = (GameplayScreenClient) goblok;

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
                if (gs.yourSide == 0)
                    if (gs.gerak[gs.yourRealSide] == 0 || gs.gerak[gs.yourRealSide] == 2)
                        gs.gerak[gs.yourRealSide] = 4;
                    else if (gs.gerak[gs.yourRealSide] == 1 || gs.gerak[gs.yourRealSide] == 3)
                        gs.gerak[gs.yourRealSide] = 5;

                if (!gs.taskattack) {
                    gs.taskattack = true;
                    timerattack.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                                             @Override
                                             public void run() {
                                                 gs.KirimDataHeroesClient();
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
                super.clicked(event, x, y);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                gs.skillsAttack.setColor(gs.skillsAttack.getColor().r, gs.skillsAttack.getColor().g, gs.skillsAttack.getColor().b, 1f);
                super.exit(event, x, y, pointer, toActor);
            }
        });

        //SKILLS 1
        gs.skillsImage[0].addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                gs.skillsImage[0].setColor(gs.skillsImage[0].getColor().r, gs.skillsImage[0].getColor().g, gs.skillsImage[0].getColor().b, 0.5f);

                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //DC SKills

                //Char 0
                if(gs.heroes[gs.yourRealSide].name.equals("Savior") && gs.heroes[gs.yourRealSide].alive && gs.heroes[gs.yourRealSide].stunned==0) {
                    if (gs.heroes[gs.yourRealSide].currentMana >= gs.heroes[gs.yourRealSide].skillmanacost[0] && gs.heroes[gs.yourRealSide].skillcurrentCD[0] == 0) {
                        if (!gs.skillsound[0].isPlaying()) {
                            gs.skillsound[0].setVolume(0.5f);
                            gs.skillsound[0].play();
                        }
                        if (gs.gerak[gs.yourRealSide] == 0 || gs.gerak[gs.yourRealSide] == 2)
                            gs.gerak[gs.yourRealSide] = 4;
                        else if (gs.gerak[gs.yourRealSide] == 1 || gs.gerak[gs.yourRealSide] == 3)
                            gs.gerak[gs.yourRealSide] = 5;


                    } else {
                        if (!gs.notenoughmanaNskillsCD[0].isPlaying() || !gs.notenoughmanaNskillsCD[1].isPlaying()) {
                            if (gs.heroes[gs.yourRealSide].currentMana < gs.heroes[gs.yourRealSide].skillmanacost[0])
                                gs.notenoughmanaNskillsCD[0].play();
                            if (gs.heroes[gs.yourRealSide].skillcurrentCD[0] > 0)
                                gs.notenoughmanaNskillsCD[1].play();
                        }
                    }
                    super.clicked(event, x, y);
                }

                //Char 1
                else if(gs.heroes[gs.yourRealSide].name.equals("Zombloe") && gs.heroes[gs.yourRealSide].alive && gs.heroes[gs.yourRealSide].stunned==0){
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
                    super.clicked(event, x, y);
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
