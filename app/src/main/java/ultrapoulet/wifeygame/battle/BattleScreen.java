package ultrapoulet.wifeygame.battle;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.enemyai.BasicMagicEnemyAI;
import ultrapoulet.wifeygame.battle.enemyai.BasicPhysicalEnemyAI;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI.EnemyAction;
import ultrapoulet.wifeygame.battle.enemyai.OriginalBossAI;

/**
 * Created by John on 3/5/2016.
 */
public class BattleScreen extends Screen {

    public BattleCharacter[] party;
    public Enemy[] enemies;

    public int partyIndex = 0;
    public int enemyIndex = 0;

    private int numHits = 0;
    private static final int SPECIAL_HITS = 60;
    private static final int MAX_HITS = SPECIAL_HITS * 5;

    private static Image background, buttonMenuNormal, buttonMenuSpecial;

    private static Image specialBar, specialBarBase, specialBarTop;

    private int comboHolder;
    private int damageHolder;

    private Paint textPaint;

    private static Image charHolder;
    private static Image pHealthG, pHealthY, pHealthR;
    private static Image[] KOImages;

    private static Image enemyHolder;

    private static final int BUTTON_MENU_Y = 880;

    private static final int ENEMY_IMAGE_X = 200;
    private static final int ENEMY_IMAGE_Y = 100;

    private static final int CHAR_HOLDER_X_DISTANCE = 100;
    private static final int CHAR_HOLDER_SMALL_Y = 760;
    private static final int CHAR_HOLDER_LARGE_Y = 640;

    private static final int SLASH = 10;
    private static final int CHAR_NUMBER_SMALL_X = 13;
    private static final int CHAR_NUMBER_LARGE_X = 26;
    private static final int CHAR_NUMBER_SMALL_DISTANCE_X = 8;
    private static final int CHAR_NUMBER_LARGE_DISTANCE_X = 16;
    private static final int CHAR_NUMBER_SMALL_Y = 855;
    private static final int CHAR_NUMBER_LARGE_Y = 830;

    private static final int CHAR_INTERIOR_SMALL_X = 10;
    private static final int CHAR_IMAGE_SMALL_Y = 770;
    private static final int CHAR_HEALTH_SMALL_Y = 860;

    private static final int CHAR_INTERIOR_LARGE_X = 20;
    private static final int CHAR_IMAGE_LARGE_Y = 660;
    private static final int CHAR_HEALTH_LARGE_Y = 840;

    private static final int ENEMY_HEALTH_HOLDER_X = 195;
    private static final int ENEMY_HEALTH_HOLDER_Y = 508;
    private static final int ENEMY_HEALTH_BAR_X = 200;
    private static final int ENEMY_HEALTH_BAR_Y = 510;

    private static final int ENEMY_NUMBER_X = 250;
    private static final int ENEMY_NUMBER_Y = 495;
    private static final int ENEMY_NUMBER_DISTANCE_X = 20;

    private static final int SPECIAL_BAR_BASE_X = 0;
    private static final int SPECIAL_BAR_BASE_Y = 610;
    private static final int SPECIAL_BAR_X = 100;
    private static final int SPECIAL_BAR_Y = 615;
    private static final int SPECIAL_BAR_TOP_Y = 585;
    private static final int SPECIAL_BAR_NUMBER_X = 390;
    private static final int SPECIAL_BAR_NUMBER_Y = 590;

    private static final int HALF_SCALE = 50;
    private static final int FULL_SCALE = 100;

    private static final int ATTACK_PHASE_WAIT = 20;
    private static final int HEAL_PHASE_WAIT = 40;
    private static final int WAIT_PHASE_WAIT = 60;
    private static final int OTHER_PHASE_WAIT = 5;
    private float phaseTime = 0;
    private boolean phaseEntered = true;

    private static final int ENEMY_DAMAGE_BASE_X = 390;
    private static final int ENEMY_DAMAGE_OFFSET_X = 10;
    private static final int ENEMY_DAMAGE_DISTANCE_X = 20;
    private static final int ENEMY_DAMAGE_START_Y = 260;
    private static final int ENEMY_DAMAGE_INCREASE_Y = 80;

    private static final int CHAR_DAMAGE_BASE_X = 40;
    private static final int CHAR_DAMAGE_OFFSET_X = 10;
    private static final int CHAR_DAMAGE_DISTANCE_X = 20;
    private static final int CHAR_DAMAGE_START_Y = 820;
    private static final int CHAR_DAMAGE_INCREASE_Y = 80;

    private int hitsPerformed = 0;
    private int enemyDamage;
    private int partyDamage[] = new int[7];
    private ButtonPressed commandSelected;

    private static enum ButtonPressed{
        POWER_ATTACK,
        COMBO_ATTACK,
        MAGIC_ATTACK,
        HEALING_MAGIC,
        DEFEND,
        TRANSFORM,
        SPECIAL_ATTACK
    }

    private static class ButtonCoordinate{
        public int LeftX;
        public int LeftY;
        public int RightX;
        public int RightY;
    }

    private enum BattlePhase{
        BATTLE_START,
        WAVE_START,
        ROUND_START,
        WAIT_PLAYER_ACTION,
        ANIMATE_PLAYER_ACTION,
        WAIT_ENEMY_ACTION,
        ANIMATE_ENEMY_ACTION,
        ROUND_END,
        WAVE_END,
        BATTLE_END
    }

    private BattlePhase currentPhase = BattlePhase.BATTLE_START;

    private static Map<ButtonPressed, ButtonCoordinate> buttonMap;

    private static Map<ButtonPressed, String> buttonNames;

    public BattleScreen(Game game){
        super(game);

        buttonMenuNormal = Assets.buttonMenuNormal;
        buttonMenuSpecial = Assets.buttonMenuSpecial;
        charHolder = Assets.charHolder;
        pHealthG = Assets.pHealthG;
        pHealthY = Assets.pHealthY;
        pHealthR = Assets.pHealthR;
        enemyHolder = Assets.enemyHolder;
        specialBar = Assets.specialBar;
        specialBarBase = Assets.specialBarBase;
        specialBarTop = Assets.specialBarTop;
        KOImages = Assets.KOImages;
        createButtonMap();
        createButtonNames();
        createPaint();
    }

    public void createButtonMap(){
        buttonMap = new HashMap<ButtonPressed, ButtonCoordinate>();
        ButtonCoordinate temp;

        //POWER_ATTACK
        temp = new ButtonCoordinate();
        temp.LeftX = 5;
        temp.LeftY = 845;
        temp.RightX = 195;
        temp.RightY = 1035;
        buttonMap.put(ButtonPressed.POWER_ATTACK, temp);

        //COMBO_ATTACK
        temp = new ButtonCoordinate();
        temp.LeftX = 5;
        temp.LeftY = 1045;
        temp.RightX = 195;
        temp.RightY = 1235;
        buttonMap.put(ButtonPressed.COMBO_ATTACK, temp);

        //MAGIC_ATTACK
        temp = new ButtonCoordinate();
        temp.LeftX = 605;
        temp.LeftY = 845;
        temp.RightX = 795;
        temp.RightY = 1035;
        buttonMap.put(ButtonPressed.MAGIC_ATTACK, temp);

        //HEALING_MAGIC
        temp = new ButtonCoordinate();
        temp.LeftX = 605;
        temp.LeftY = 1045;
        temp.RightX = 795;
        temp.RightY = 1235;
        buttonMap.put(ButtonPressed.HEALING_MAGIC, temp);

        //DEFEND
        temp = new ButtonCoordinate();
        temp.LeftX = 205;
        temp.LeftY = 1045;
        temp.RightX = 595;
        temp.RightY = 1235;
        buttonMap.put(ButtonPressed.DEFEND, temp);

        //TRANSFORM
        temp = new ButtonCoordinate();
        temp.LeftX = 205;
        temp.LeftY = 845;
        temp.RightX = 395;
        temp.RightY = 1035;
        buttonMap.put(ButtonPressed.TRANSFORM, temp);

        //SPECIAL_ATTACK
        temp = new ButtonCoordinate();
        temp.LeftX = 405;
        temp.LeftY = 845;
        temp.RightX = 595;
        temp.RightY = 1035;
        buttonMap.put(ButtonPressed.SPECIAL_ATTACK, temp);
    }

    public void createButtonNames(){
        buttonNames = new HashMap<ButtonPressed, String>();
        buttonNames.put(ButtonPressed.POWER_ATTACK, "Power Attack");
        buttonNames.put(ButtonPressed.COMBO_ATTACK, "Combo Attack");
        buttonNames.put(ButtonPressed.MAGIC_ATTACK, "Magic Attack");
        buttonNames.put(ButtonPressed.HEALING_MAGIC, "Heal");
        buttonNames.put(ButtonPressed.SPECIAL_ATTACK, "Special Attack");
        buttonNames.put(ButtonPressed.DEFEND, "Defend");
        buttonNames.put(ButtonPressed.TRANSFORM, "Transformation");
    }

    public void createPaint(){
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(50);
    }

    public void setParty(BattleCharacter[] party){
        this.party = party;
    }

    public void setEnemies(Enemy[] enemies){
        this.enemies = enemies;
    }

    public void setBackground(Image background){
        this.background = background;
    }

    public ButtonPressed getButtonPressed(TouchEvent touch){
        for (ButtonPressed b : ButtonPressed.values()) {
            ButtonCoordinate bc = buttonMap.get(b);
            if(bc.LeftX <= touch.x && bc.RightX >= touch.x && bc.LeftY <= touch.y && bc.RightY >= touch.y){
                return b;
            }
        }
        return null;
    }

    private void incrementHits(){
        numHits = (numHits < MAX_HITS) ? numHits + 1 : numHits;
    }

    private void firstPartyIndex(){
        partyIndex = 0;
        while(party[partyIndex].getCurrentHP() == 0){
            partyIndex++;
        }
    }

    private void nextPartyIndex(){
        partyIndex = (partyIndex + 1) % party.length;
        while(party[partyIndex].getCurrentHP() == 0) {
            partyIndex = (partyIndex + 1) % party.length;
        }
    }

    private void noPartyIndex(){
        partyIndex = party.length;
    }

    //Returns true if the next character partyIndex would roll over, indicating end of round.
    private boolean isEndOfRound(){
        int tempIndex = (partyIndex + 1) % party.length;
        while(party[tempIndex].getCurrentHP() == 0){
            tempIndex = (tempIndex + 1) % party.length;
        }
        return tempIndex <= partyIndex;
    }

    //Returns true if all characters are dead
    private boolean isGameOver(){
        for(int i = 0; i < party.length; i++){
            if(party[i].getCurrentHP() > 0){
                return false;
            }
        }
        return true;
    }

    //Chooses a random character that is alive
    private int chooseRandomChar(){
        int numAlive = 0;
        for(int i = 0; i < party.length; i++){
            if(party[i].getCurrentHP() > 0){ numAlive++; }
        }
        int[] aliveNumbers = new int[numAlive];
        int index = 0;
        for(int i = 0; i < party.length; i++){
            if(party[i].getCurrentHP() > 0) {
                aliveNumbers[index] = i;
                index++;
            }
        }
        int select = (int) (Math.random() * numAlive);
        return aliveNumbers[select];
    }

    @Override
    public void update(float deltaTime) {

        switch (currentPhase) {
            case BATTLE_START:
                //Do anything necessary for battle start
                if (phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;
                    for(int i = 0; i < party.length; i++){
                        party[i].battleStart();
                    }
                } else {
                    phaseTime += deltaTime;
                    if (phaseTime >= OTHER_PHASE_WAIT) {
                        currentPhase = BattlePhase.WAVE_START;
                        phaseEntered = true;
                    }
                }
                break;
            case WAVE_START:
                //Do anything necessary for Start of Wave
                if (phaseEntered) {
                    firstPartyIndex();
                    phaseTime = 0;
                    phaseEntered = false;
                } else {
                    phaseTime += deltaTime;
                    if (phaseTime >= OTHER_PHASE_WAIT) {
                        currentPhase = BattlePhase.ROUND_START;
                        phaseEntered = true;
                    }
                }
                break;
            case ROUND_START:
                //Do things for Start of Round
                if (phaseEntered) {
                    firstPartyIndex();
                    comboHolder = 0;
                    damageHolder = 0;
                    phaseTime = 0;
                    phaseEntered = false;
                } else {
                    phaseTime += deltaTime;
                    if (phaseTime >= OTHER_PHASE_WAIT) {
                        currentPhase = BattlePhase.WAIT_PLAYER_ACTION;
                        phaseEntered = true;
                    }
                }
                break;
            case WAIT_PLAYER_ACTION:
                //If player has made a selection, get it ready for animating
                if(phaseEntered){
                    phaseEntered = false;
                    //Clear the touch input buffer
                    game.getInput().getTouchEvents();
                    party[partyIndex].turnStart();
                    for(int i = 0; i < party.length; i++){
                        partyDamage[i] = 0;
                    }
                    enemyDamage = 0;
                }
                else {
                    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

                    for (int i = 0; i < touchEvents.size(); i++) {
                        TouchEvent t = touchEvents.get(i);
                        if (t.type != TouchEvent.TOUCH_UP) {
                            continue;
                        }
                        ButtonPressed command = getButtonPressed(touchEvents.get(i));
                        if (command == null) {
                            continue;
                        }

                        switch (command) {
                            case SPECIAL_ATTACK:
                                if (numHits < SPECIAL_HITS) {
                                    continue;
                                }
                                numHits -= SPECIAL_HITS;
                            case POWER_ATTACK:
                            case COMBO_ATTACK:
                            case MAGIC_ATTACK:
                            case DEFEND:
                            case HEALING_MAGIC:
                                commandSelected = command;
                                break;
                            case TRANSFORM:
                                continue;
                            default:
                                continue;
                        }
                        currentPhase = BattlePhase.ANIMATE_PLAYER_ACTION;
                        phaseEntered = true;
                        hitsPerformed = 0;
                    }
                }
                break;
            case ANIMATE_PLAYER_ACTION:
                //Calculate damage/healing
                //If a combo attack was used, stay in ANIMATE_PLAYER_ACTION
                //If the enemy was defeated, go to WAVE_END
                //If the player needs to do another action for next char, go back to WAIT_PLAYER_ACTION
                //If the player doesn't have more actions, go to WAIT_ENEMY_ACTION
                if (phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;

                    int baseDamage;
                    int displayDamage;
                    switch (commandSelected) {
                        case POWER_ATTACK:
                            baseDamage = party[partyIndex].PowerAttackDamage();
                            displayDamage = enemies[enemyIndex].takePhysicalDamage(baseDamage);
                            enemyDamage = displayDamage;
                            incrementHits();
                            comboHolder++;
                            damageHolder += enemyDamage;
                            break;
                        case COMBO_ATTACK:
                            baseDamage = party[partyIndex].ComboAttackDamage();
                            displayDamage = enemies[enemyIndex].takePhysicalDamage(baseDamage);
                            enemyDamage = displayDamage;
                            incrementHits();
                            hitsPerformed++;
                            comboHolder++;
                            damageHolder += enemyDamage;
                            break;
                        case MAGIC_ATTACK:
                            baseDamage = party[partyIndex].MagicAttackDamage();
                            displayDamage = enemies[enemyIndex].takeMagicalDamage(baseDamage);
                            enemyDamage = displayDamage;
                            incrementHits();
                            comboHolder++;
                            damageHolder += enemyDamage;
                            break;
                        case HEALING_MAGIC:
                            baseDamage = party[partyIndex].HealAmount();
                            for (int j = 0; j < party.length; j++) {
                                if (party[j].getCurrentHP() > 0) {
                                    displayDamage = party[j].healDamage(baseDamage);
                                    partyDamage[j] = displayDamage;
                                }
                                else{
                                    partyDamage[j] = 0;
                                }
                            }
                            break;
                        case SPECIAL_ATTACK:
                            baseDamage = party[partyIndex].SpecialDamage();
                            displayDamage = enemies[enemyIndex].takeSpecialDamage(baseDamage);
                            enemyDamage = displayDamage;
                            incrementHits();
                            comboHolder++;
                            damageHolder += enemyDamage;
                            break;
                        case TRANSFORM:
                            //I'll need to figure out how to do this.
                            break;
                        case DEFEND:
                            party[partyIndex].Defend();
                            enemyDamage = 0;
                            break;
                        default:
                    }
                } else {
                    phaseTime += deltaTime;
                    int phaseWait;
                    switch(commandSelected){
                        case POWER_ATTACK:
                        case COMBO_ATTACK:
                        case MAGIC_ATTACK:
                        case SPECIAL_ATTACK:
                            phaseWait = ATTACK_PHASE_WAIT;
                            break;
                        case HEALING_MAGIC:
                            phaseWait = HEAL_PHASE_WAIT;
                            break;
                        case DEFEND:
                        case TRANSFORM:
                            phaseWait = WAIT_PHASE_WAIT;
                            break;
                        default:
                            phaseWait = OTHER_PHASE_WAIT;
                    }
                    if (phaseTime >= phaseWait) {
                        if (commandSelected == ButtonPressed.COMBO_ATTACK && hitsPerformed < party[partyIndex].getNumHits()) {
                            currentPhase = BattlePhase.ANIMATE_PLAYER_ACTION;
                            phaseEntered = true;
                        } else if (enemies[enemyIndex].getCurrentHP() == 0) {
                            currentPhase = BattlePhase.WAVE_END;
                            phaseEntered = true;
                        } else if (isEndOfRound()) {
                            currentPhase = BattlePhase.WAIT_ENEMY_ACTION;
                            phaseEntered = true;
                        } else {
                            currentPhase = BattlePhase.WAIT_PLAYER_ACTION;
                            nextPartyIndex();
                            phaseEntered = true;
                        }
                    }
                }
                break;
            case WAIT_ENEMY_ACTION:
                //Select the enemy action
                if (phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;
                    noPartyIndex();
                    comboHolder = 0;
                    damageHolder = 0;
                    for(int i = 0; i < party.length; i++){
                        partyDamage[i] = 0;
                    }
                    enemyDamage = 0;
                    enemies[enemyIndex].startRound();
                    enemies[enemyIndex].determineAction();
                } else {
                    phaseTime += deltaTime;
                    if (phaseTime >= WAIT_PHASE_WAIT) {
                        currentPhase = BattlePhase.ANIMATE_ENEMY_ACTION;
                        phaseEntered = true;
                        hitsPerformed = 0;
                    }
                }
                break;
            case ANIMATE_ENEMY_ACTION:
                //Calculate damage/healing
                //If multiple attacks, stay in ANIMATE_ENEMY_ACTION
                //If all characters defeated, go to BATTLE_END
                //Else, go to ROUND_END
                if (phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;
                    EnemyAction action = enemies[enemyIndex].getAction();
                    int charIndex;
                    int baseDamage;
                    int displayDamage;
                    switch(action){
                        case POWER_ATTACK:
                            charIndex = chooseRandomChar();
                            baseDamage = enemies[enemyIndex].PowerAttackDamage();
                            displayDamage = party[charIndex].takePhysicalDamage(baseDamage);
                            for(int i = 0; i < party.length; i++){
                                if(i != charIndex){
                                    partyDamage[i] = 0;
                                }
                                else{
                                    partyDamage[i] = displayDamage;
                                }
                            }
                            hitsPerformed++;
                            break;
                        case COMBO_ATTACK:
                            charIndex = chooseRandomChar();
                            baseDamage = enemies[enemyIndex].ComboAttackDamage();
                            displayDamage = party[charIndex].takePhysicalDamage(baseDamage);
                            for(int i = 0; i < party.length; i++){
                                if(i != charIndex){
                                    partyDamage[i] = 0;
                                }
                                else{
                                    partyDamage[i] = displayDamage;
                                }
                            }
                            hitsPerformed++;
                            break;
                        case MAGIC_ATTACK:
                            for(int i = 0; i < party.length; i++){
                                if(party[i].getCurrentHP() > 0){
                                    baseDamage = enemies[enemyIndex].MagicAttackDamage();
                                    partyDamage[i] = party[i].takeMagicalDamage(baseDamage);
                                }
                                else{
                                    partyDamage[i] = 0;
                                }
                            }
                            hitsPerformed++;
                            break;
                        case HEALING_MAGIC:
                            baseDamage = enemies[enemyIndex].HealAmount();
                            enemyDamage = baseDamage;
                            enemies[enemyIndex].healDamage(enemyDamage);
                            hitsPerformed++;
                            break;
                        case SPECIAL_ATTACK:
                            for(int i = 0; i < party.length; i++){
                                if(party[i].getCurrentHP() > 0){
                                    baseDamage = enemies[enemyIndex].SpecialAttackDamage();
                                    partyDamage[i] = party[i].takeSpecialDamage(baseDamage);
                                }
                                else{
                                    partyDamage[i] = 0;
                                }
                            }
                            hitsPerformed++;
                            break;
                        case POWER_UP:
                            enemies[enemyIndex].powerUp();
                            hitsPerformed++;
                            break;
                        case POWER_DOWN:
                            enemies[enemyIndex].powerDown();
                            hitsPerformed++;
                            break;
                        case DEFEND:
                            enemies[enemyIndex].defend();
                            hitsPerformed++;
                            break;
                        case WEAKEN:
                            enemies[enemyIndex].weaken();
                            hitsPerformed++;
                            break;
                        case WAIT:
                            hitsPerformed++;
                            break;
                        default:
                            hitsPerformed++;
                            System.out.println("Not supported yet");
                            break;
                    }
                } else {
                    phaseTime += deltaTime;
                    int phaseWait;
                    switch(enemies[enemyIndex].getAction()){
                        case POWER_ATTACK:
                        case COMBO_ATTACK:
                        case MAGIC_ATTACK:
                        case SPECIAL_ATTACK:
                            phaseWait = ATTACK_PHASE_WAIT;
                            break;
                        case HEALING_MAGIC:
                            phaseWait = HEAL_PHASE_WAIT;
                            break;
                        case POWER_UP:
                        case POWER_DOWN:
                        case DEFEND:
                        case WEAKEN:
                        case WAIT:
                        case TRANSFORM:
                            phaseWait = WAIT_PHASE_WAIT;
                            break;
                        default:
                            phaseWait = OTHER_PHASE_WAIT;
                            break;
                    }
                    if (phaseTime >= phaseWait) {
                        if (isGameOver()) {
                            currentPhase = BattlePhase.BATTLE_END;
                            phaseEntered = true;
                        } else if(hitsPerformed < enemies[enemyIndex].getNumHits()){
                            currentPhase = BattlePhase.ANIMATE_ENEMY_ACTION;
                            phaseEntered = true;
                        }
                        else{
                            currentPhase = BattlePhase.ROUND_END;
                            phaseEntered = true;
                        }
                    }
                }
                break;
            case ROUND_END:
                //Perform end of round things
                //Go back to ROUND_START
                if (phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;
                    enemies[enemyIndex].endRound();
                } else {
                    phaseTime += deltaTime;
                    if (phaseTime >= WAIT_PHASE_WAIT) {
                        currentPhase = BattlePhase.ROUND_START;
                        phaseEntered = true;
                    }
                }
                break;
            case WAVE_END:
                //Tally experience and gold
                //Character end of wave bonuses
                //If more enemies, go to WAVE_START
                //If no more enemies, go to BATTLE_END
                if (phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;
                } else {
                    phaseTime += deltaTime;
                    if (phaseTime >= WAIT_PHASE_WAIT) {
                        enemyIndex++;
                        System.out.println(enemyIndex);
                        if (enemyIndex == enemies.length) {
                            enemyIndex--;
                            currentPhase = BattlePhase.BATTLE_END;
                            phaseEntered = true;
                        } else {
                            currentPhase = BattlePhase.WAVE_START;
                            phaseEntered = true;
                        }
                    }
                }
                break;
            case BATTLE_END:
                //Give out rewards
                //If win, do stuff
                //If loss, give other stuff, i dunno
                if (phaseEntered) {
                    if (isGameOver()) {
                        System.out.println("You lost...");
                    } else {
                        System.out.println("You won");
                    }
                    phaseEntered = false;

                    //Temporary go back to Select
                    BattleSelectScreen bss = new BattleSelectScreen(game);
                    game.setScreen(bss);
                }
                break;

        }
    }

    private void drawPercentageImage(Graphics g, Image i, int posX, int posY, int percX, int percY){
        int origWidth = i.getWidth();
        int origHeight = i.getHeight();
        int newWidth = i.getWidth()*percX/100;
        int newHeight = i.getHeight()*percY/100;
        g.drawScaledImage(i, posX, posY, newWidth, newHeight, 0, 0, origWidth, origHeight);
    }

    private Image getPlayerHealthBar(int currentHealth, int maxHealth){
        Double percent = (100.0 * currentHealth)/maxHealth;
        if(percent >= 50.0){
            return Assets.pHealthG;
        }
        else if(percent >= 25.0){
            return Assets.pHealthY;
        }
        else{
            return Assets.pHealthR;
        }
    }

    private Image getEnemyHealthBar(int currentHealth, int maxHealth){
        Double percent = (100.0 * currentHealth) / maxHealth;
        if(percent >= 50.0){
            return Assets.eHealthG;
        }
        else if(percent >= 25.0){
            return Assets.eHealthY;
        }
        else{
            return Assets.eHealthR;
        }
    }

    @Override
    public void paint(float deltaTime) {
        drawBackground();
        drawParty();
        drawEnemy();
        drawSpecial();
        drawCombo();
        if(currentPhase == BattlePhase.ANIMATE_PLAYER_ACTION){
            drawPlayerDamage();
        }
        if(currentPhase == BattlePhase.ANIMATE_ENEMY_ACTION){
            drawEnemyDamage();
        }
    }

    private void drawBackground(){
        Graphics g = game.getGraphics();
        g.clearScreen(0xffffffff);
        g.drawImage(background, 0, 0);
        if(numHits >= SPECIAL_HITS){
            g.drawImage(buttonMenuSpecial, 0, BUTTON_MENU_Y);
        }
        else {
            g.drawImage(buttonMenuNormal, 0, BUTTON_MENU_Y);
        }
    }

    private void drawParty(){
        Graphics g = game.getGraphics();

        int i = 0;
        Image healthBar;
        Double perHealth;
        boolean numberStart;
        int divisor;
        int j;
        //Draw party members before the current member
        for( ; i < partyIndex && i < party.length; i++){
            //Draw the character holder and character image
            drawPercentageImage(g, charHolder, CHAR_HOLDER_X_DISTANCE * i, CHAR_HOLDER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            drawPercentageImage(g, party[i].getImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_SMALL_X, CHAR_IMAGE_SMALL_Y, HALF_SCALE, HALF_SCALE);

            //Draw the health bar
            healthBar = getPlayerHealthBar(party[i].getCurrentHP(), party[i].getMaxHP());
            perHealth = (party[i].getCurrentHP() * 1.0)/(party[i].getMaxHP());
            drawPercentageImage(g, healthBar, CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_SMALL_X, CHAR_HEALTH_SMALL_Y, (int) Math.ceil(perHealth * HALF_SCALE), HALF_SCALE);

            //Draw the CurrentHP / MaxHP
            numberStart = false;
            divisor = 1000;
            j = 0;
            for( ; j < 4; j++){
                int numberPart = (party[i].getCurrentHP() / divisor % 10);
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    drawPercentageImage(g, Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * i + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
                }
                divisor = divisor / 10;
            }
            drawPercentageImage(g, Assets.HPNumbers[SLASH], CHAR_HOLDER_X_DISTANCE * i + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            j++;
            numberStart = false;
            divisor = 1000;
            while(divisor > 0){
                int numberPart = (party[i].getMaxHP() / divisor) % 10;
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    drawPercentageImage(g, Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * i + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
                    j++;
                }
                divisor = divisor / 10;
            }

            //If the party member is defeated, overlay the KO Image
            if(party[i].getCurrentHP() == 0){
                drawPercentageImage(g, KOImages[i], CHAR_HOLDER_X_DISTANCE * i, CHAR_HOLDER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            }

        }
        //Draw the current acting character
        if(i == partyIndex && i < party.length) {
            g.drawImage(charHolder, CHAR_HOLDER_X_DISTANCE * i, CHAR_HOLDER_LARGE_Y);
            g.drawImage(party[i].getImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_LARGE_X, CHAR_IMAGE_LARGE_Y);

            healthBar = getPlayerHealthBar(party[i].getCurrentHP(), party[i].getMaxHP());
            perHealth = (party[i].getCurrentHP() * 1.0) / (party[i].getMaxHP());
            drawPercentageImage(g, healthBar, CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_LARGE_X, CHAR_HEALTH_LARGE_Y, (int) Math.ceil(perHealth * FULL_SCALE), FULL_SCALE);

            //Display CURRENTHP/MAXHP
            numberStart = false;
            divisor = 1000;
            j = 0;
            for (; j < 4; j++) {
                int numberPart = (party[i].getCurrentHP() / divisor) % 10;
                numberStart = (numberStart || (numberPart > 0));
                if (numberStart) {
                    g.drawImage(Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * i + CHAR_NUMBER_LARGE_X + CHAR_NUMBER_LARGE_DISTANCE_X * j, CHAR_NUMBER_LARGE_Y);
                }
                divisor = divisor / 10;
            }
            g.drawImage(Assets.HPNumbers[SLASH], CHAR_HOLDER_X_DISTANCE * i + CHAR_NUMBER_LARGE_X + CHAR_NUMBER_LARGE_DISTANCE_X * j, CHAR_NUMBER_LARGE_Y);
            j++;
            numberStart = false;
            divisor = 1000;
            while (divisor > 0) {
                int numberPart = (party[i].getMaxHP() / divisor) % 10;
                numberStart = (numberStart || (numberPart > 0));
                if (numberStart) {
                    g.drawImage(Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * i + CHAR_NUMBER_LARGE_X + CHAR_NUMBER_LARGE_DISTANCE_X * j, CHAR_NUMBER_LARGE_Y);
                    j++;
                }
                divisor = divisor / 10;
            }

            if (party[i].getCurrentHP() == 0) {
                g.drawImage(KOImages[i], CHAR_HOLDER_X_DISTANCE * i, CHAR_HOLDER_LARGE_Y);
            }
            i++;
        }
        //Draw the characters after the current member
        for ( ; i < party.length; i++) {
            drawPercentageImage(g, charHolder, CHAR_HOLDER_X_DISTANCE * (i + 1), CHAR_HOLDER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            drawPercentageImage(g, party[i].getImage(), CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_INTERIOR_SMALL_X, CHAR_IMAGE_SMALL_Y, HALF_SCALE, HALF_SCALE);

            healthBar = getPlayerHealthBar(party[i].getCurrentHP(), party[i].getMaxHP());
            perHealth = (party[i].getCurrentHP() * 1.0)/(party[i].getMaxHP());
            drawPercentageImage(g, healthBar, CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_INTERIOR_SMALL_X, CHAR_HEALTH_SMALL_Y, (int) Math.ceil(perHealth * HALF_SCALE) , HALF_SCALE);

            //Display CURRENTHP/MAXHP
            numberStart = false;
            divisor = 1000;
            j = 0;
            for( ; j < 4; j++){
                int numberPart = (party[i].getCurrentHP() / divisor) % 10;
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    drawPercentageImage(g, Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
                }
                divisor = divisor / 10;
            }
            drawPercentageImage(g, Assets.HPNumbers[SLASH], CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            j++;
            numberStart = false;
            divisor = 1000;
            while(divisor > 0){
                int numberPart = (party[i].getMaxHP() / divisor) % 10;
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    drawPercentageImage(g, Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
                    j++;
                }
                divisor = divisor / 10;
            }

            if(party[i].getCurrentHP() == 0){
                drawPercentageImage(g, KOImages[i], CHAR_HOLDER_X_DISTANCE * (i + 1), CHAR_HOLDER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            }
        }
    }

    private void drawEnemy(){
        Graphics g = game.getGraphics();
        Double perHealth;
        boolean numberStart;
        int divisor;
        int j;

        g.drawImage(enemies[enemyIndex].getImage(), ENEMY_IMAGE_X, ENEMY_IMAGE_Y);
        g.drawImage(enemyHolder, ENEMY_HEALTH_HOLDER_X, ENEMY_HEALTH_HOLDER_Y);
        Image enemyHealth = getEnemyHealthBar(enemies[enemyIndex].getCurrentHP(), enemies[enemyIndex].getMaxHP());
        perHealth = (enemies[enemyIndex].getCurrentHP() * 1.0)/enemies[enemyIndex].getMaxHP();
        drawPercentageImage(g, enemyHealth, ENEMY_HEALTH_BAR_X, ENEMY_HEALTH_BAR_Y, (int) Math.ceil(FULL_SCALE * perHealth), FULL_SCALE);

        //Display enemy CurrentHP/MaxHP
        numberStart = false;
        divisor = 1000000;
        j = 0;
        for( ; j < 7; j++){
            int numberPart = (enemies[enemyIndex].getCurrentHP() / divisor) % 10;
            numberStart = (numberStart || (numberPart > 0));
            if(numberStart){
                g.drawImage(Assets.HPNumbers[numberPart], ENEMY_NUMBER_X + ENEMY_NUMBER_DISTANCE_X * j, ENEMY_NUMBER_Y);
            }
            divisor = divisor / 10;
        }
        if(enemies[enemyIndex].getCurrentHP() == 0){
            g.drawImage(Assets.HPNumbers[0], ENEMY_NUMBER_X + ENEMY_NUMBER_DISTANCE_X * 6, ENEMY_NUMBER_Y);
        }
        g.drawImage(Assets.HPNumbers[SLASH], ENEMY_NUMBER_X + ENEMY_NUMBER_DISTANCE_X * j, ENEMY_NUMBER_Y);
        j++;
        numberStart = false;
        divisor = 1000000;
        while(divisor > 0){
            int numberPart = (enemies[enemyIndex].getMaxHP() / divisor) % 10;
            numberStart = (numberStart || (numberPart > 0));
            if(numberStart){
                g.drawImage(Assets.HPNumbers[numberPart], ENEMY_NUMBER_X + ENEMY_NUMBER_DISTANCE_X * j, ENEMY_NUMBER_Y);
                j++;
            }
            divisor = divisor / 10;
        }
    }

    private void drawSpecial(){
        Graphics g = game.getGraphics();
        g.drawImage(specialBarBase, SPECIAL_BAR_BASE_X, SPECIAL_BAR_BASE_Y);
        drawPercentageImage(g, specialBar, SPECIAL_BAR_X, SPECIAL_BAR_Y, (int) ((numHits * 100.0) / MAX_HITS) , FULL_SCALE);
        g.drawImage(specialBarTop, SPECIAL_BAR_X, SPECIAL_BAR_TOP_Y);
        int specialCount = numHits / SPECIAL_HITS;
        g.drawImage(Assets.HPNumbers[specialCount], SPECIAL_BAR_NUMBER_X, SPECIAL_BAR_NUMBER_Y);

    }

    private void drawCombo(){
        Graphics g = game.getGraphics();
        int i = 0;
        boolean numberStart;
        int divisor;
        int j;
        //Combo Counter
        if(comboHolder / 10 > 0){
            g.drawImage(Assets.ComboHitsNumbers[comboHolder / 10], 630, 400);
        }
        if(comboHolder > 0){
            g.drawImage(Assets.ComboHitsNumbers[comboHolder % 10], 660, 400);
            if(comboHolder > 1) {
                g.drawImage(Assets.hitsText, 700, 430);
            }
            else{
                g.drawImage(Assets.hitText, 700, 430);
            }
        }
        //Damage Counter
        divisor = 100000;
        j = 0;
        numberStart = false;
        while(divisor > 0){
            int numberPart = (damageHolder / divisor) % 10;
            numberStart = (numberStart || (numberPart > 0));
            if(numberStart){
                g.drawImage(Assets.DamageHitsNumbers[numberPart], 600 + j * 20, 460);
            }
            j++;
            divisor = divisor / 10;
        }
        if(damageHolder > 0){
            g.drawImage(Assets.damageText, 720, 480);
        }
    }

    private void drawPlayerDamage(){
        Graphics g = game.getGraphics();
        int i = 0;
        boolean numberStart;
        int divisor;
        int j;
        g.drawImage(Assets.attackBox, 0, 0);
        g.drawString(buttonNames.get(commandSelected), 400, 70, textPaint);
        divisor = 10000;
        numberStart = false;
        int offset = 4;
        j = 0;
        while(divisor > 0){
            int numberPart = enemyDamage/divisor % 10;
            numberStart = (numberStart || (numberPart > 0));
            if(numberStart){
                int y = ENEMY_DAMAGE_START_Y - (int) (ENEMY_DAMAGE_INCREASE_Y * phaseTime / ATTACK_PHASE_WAIT);
                g.drawImage(Assets.HPNumbers[numberPart], ENEMY_DAMAGE_BASE_X - ENEMY_DAMAGE_OFFSET_X * offset + ENEMY_DAMAGE_DISTANCE_X * j, y);
                j++;
            }
            else{
                offset--;
            }
            divisor = divisor / 10;
        }

        for(i = 0; i < party.length; i++){
            divisor = 1000;
            numberStart = false;
            offset = 3;
            j = 0;
            while(divisor > 0){
                int numberPart = partyDamage[i]/divisor % 10;
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    int y = CHAR_DAMAGE_START_Y - (int) (CHAR_DAMAGE_INCREASE_Y * phaseTime / HEAL_PHASE_WAIT);
                    int x;
                    if(i < partyIndex) {
                        x = CHAR_DAMAGE_BASE_X - CHAR_DAMAGE_OFFSET_X * offset + CHAR_DAMAGE_DISTANCE_X * j + CHAR_HOLDER_X_DISTANCE * i;
                    }
                    else if(i == partyIndex){
                        x = (CHAR_DAMAGE_BASE_X * 2) - CHAR_DAMAGE_OFFSET_X * offset + CHAR_DAMAGE_DISTANCE_X * j + CHAR_HOLDER_X_DISTANCE * i;
                    }
                    else{
                        x = CHAR_DAMAGE_BASE_X - CHAR_DAMAGE_OFFSET_X * offset + CHAR_DAMAGE_DISTANCE_X * j + CHAR_HOLDER_X_DISTANCE * (i + 1);
                    }
                    g.drawImage(Assets.HPHealNumbers[numberPart], x, y);
                    j++;
                }
                else{
                    offset--;
                }
                divisor = divisor / 10;
            }
        }
    }

    private void drawEnemyDamage(){
        Graphics g = game.getGraphics();
        int i = 0;
        boolean numberStart;
        int divisor;
        int j;
        g.drawImage(Assets.attackBox,0,0);
        g.drawString(enemies[enemyIndex].getActionString(), 400, 70, textPaint);
        divisor = 10000;
        numberStart = false;
        int offset = 4;
        j = 0;
        while(divisor > 0){
            int numberPart = enemyDamage/divisor % 10;
            numberStart = (numberStart || (numberPart > 0));
            if(numberStart){
                int y = ENEMY_DAMAGE_START_Y - (int) (ENEMY_DAMAGE_INCREASE_Y * phaseTime / HEAL_PHASE_WAIT);
                g.drawImage(Assets.HPHealNumbers[numberPart], ENEMY_DAMAGE_BASE_X - ENEMY_DAMAGE_OFFSET_X * offset + ENEMY_DAMAGE_DISTANCE_X * j, y);
                j++;
            }
            else{
                offset--;
            }
            divisor = divisor / 10;
        }

        for(i = 0; i < party.length; i++ ){
            divisor = 10000;
            numberStart = false;
            offset = 4;
            j = 0;
            while(divisor > 0){
                int numberPart = partyDamage[i]/divisor % 10;
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    int y = CHAR_DAMAGE_START_Y - (int) (CHAR_DAMAGE_INCREASE_Y * phaseTime / ATTACK_PHASE_WAIT);
                    int x = CHAR_DAMAGE_BASE_X - CHAR_DAMAGE_OFFSET_X * offset + CHAR_DAMAGE_DISTANCE_X * j + CHAR_HOLDER_X_DISTANCE * i;
                    g.drawImage(Assets.HPNumbers[numberPart], x, y);
                    j++;
                }
                else{
                    offset--;
                }
                divisor = divisor / 10;
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}
