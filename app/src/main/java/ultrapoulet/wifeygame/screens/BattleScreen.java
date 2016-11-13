package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.battle.BattleWifey;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI.EnemyAction;

/**
 * Created by John on 3/5/2016.
 */
public class BattleScreen extends Screen {

    public BattleWifey[] party;
    public BattleInfo battleInfo;
    public BattleEnemy[] enemies;

    public int partyIndex = 0;
    public int enemyIndex = 0;

    private int numHits = 0;
    private static final int SPECIAL_HITS = 60;
    private static final int MAX_HITS = SPECIAL_HITS * 5;

    private double enemyMultiplier = 1.00;
    private final double roundMultiplier = 0.025;

    private static Image background, buttonMenuNormal, buttonMenuSpecial, buttonMenuTrans;

    private static Image specialBar, specialBarBase, specialBarTop;

    private int comboHolder;
    private int damageHolder;

    private Paint textPaint;

    private static Image charHolder;
    private static Image[] KOImages;

    private static Image enemyHolder;

    private static final int BUTTON_MENU_Y = 880;

    private static final int ENEMY_IMAGE_X = 200;
    private static final int ENEMY_IMAGE_Y = 100;

    private static final int CHAR_HOLDER_X_DISTANCE = 100;
    private static final int CHAR_HOLDER_SMALL_Y = 745;
    private static final int CHAR_HOLDER_LARGE_Y = 610;

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

    private static final int CHAR_IMAGE_SMALL_SIZE = 80;
    private static final int CHAR_IMAGE_LARGE_SIZE = 160;

    private static final int CHAR_LARGE_BASE_ELEM_ATK_X = 39;
    private static final int CHAR_SMALL_BASE_ELEM_ATK_X = 19;
    private static final int CHAR_LARGE_BASE_ELEM_RES_X = 102;
    private static final int CHAR_SMALL_BASE_ELEM_RES_X = 51;
    private static final int CHAR_LARGE_BASE_ELEM_WEAK_X = 165;
    private static final int CHAR_SMALL_BASE_ELEM_WEAK_X = 82;
    private static final int CHAR_LARGE_ELEM_Y = CHAR_HOLDER_LARGE_Y + 3;
    private static final int CHAR_SMALL_ELEM_Y = CHAR_HOLDER_SMALL_Y + 1;
    private static final int CHAR_LARGE_ELEM_SCALE = 25;
    private static final int CHAR_SMALL_ELEM_SCALE = 13;

    private static final int ENEMY_HEALTH_HOLDER_X = 195;
    private static final int ENEMY_HEALTH_HOLDER_Y = 503;
    private static final int ENEMY_HEALTH_BAR_X = 200;
    private static final int ENEMY_HEALTH_BAR_Y = 535;

    private static final int ENEMY_ELEM_ATK_X = 64 + ENEMY_HEALTH_HOLDER_X;
    private static final int ENEMY_ELEM_RES_X = 150 + ENEMY_ELEM_ATK_X;
    private static final int ENEMY_ELEM_WEAK_X = 150 + ENEMY_ELEM_RES_X;
    private static final int ENEMY_ELEM_Y = 3 + ENEMY_HEALTH_HOLDER_Y;
    private static final int ENEMY_ELEM_SCALE = 25;

    private static final int ENEMY_NUMBER_X = 250;
    private static final int ENEMY_NUMBER_Y = 520;
    private static final int ENEMY_NUMBER_DISTANCE_X = 20;

    private static final int SPECIAL_BAR_BASE_X = 0;
    private static final int SPECIAL_BAR_BASE_Y = 580;
    private static final int SPECIAL_BAR_X = 100;
    private static final int SPECIAL_BAR_Y = 585;
    private static final int SPECIAL_BAR_TOP_Y = 555;
    private static final int SPECIAL_BAR_NUMBER_X = 390;
    private static final int SPECIAL_BAR_NUMBER_Y = 560;

    private static final int HALF_SCALE = 50;
    private static final int FULL_SCALE = 100;

    private static final int ATTACK_PHASE_WAIT = 30;
    private static final int HEAL_PHASE_WAIT = 45;
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

    private BattleCharacterInfoScreen charInfo;

    //New Button things
    private static final int COL_1_LEFT_X = 0;
    private static final int COL_1_RIGHT_X = 199;
    private static final int COL_2_LEFT_X = 200;
    private static final int COL_2_RIGHT_X = 399;
    private static final int COL_3_LEFT_X = 400;
    private static final int COL_3_RIGHT_X = 599;
    private static final int COL_4_LEFT_X = 600;
    private static final int COL_4_RIGHT_X = 799;
    private static final int ROW_1_TOP_Y = 840;
    private static final int ROW_1_BOT_Y = 1039;
    private static final int ROW_2_TOP_Y = 1040;
    private static final int ROW_2_BOT_Y = 1239;

    private ButtonList buttonList;
    private Button powerAttackButton;
    private static final int POWER_LEFT_X = COL_1_LEFT_X;
    private static final int POWER_RIGHT_X = COL_1_RIGHT_X;
    private static final int POWER_TOP_Y = ROW_1_TOP_Y;
    private static final int POWER_BOT_Y = ROW_1_BOT_Y;
    private static final String POWER_STRING = "Power Attack";

    private Button comboAttackButton;
    private static final int COMBO_LEFT_X = COL_1_LEFT_X;
    private static final int COMBO_RIGHT_X = COL_1_RIGHT_X;
    private static final int COMBO_TOP_Y = ROW_2_TOP_Y;
    private static final int COMBO_BOT_Y = ROW_2_BOT_Y;
    private static final String COMBO_STRING = "Combo Attack";

    private Button magicAttackButton;
    private static final int MAGIC_LEFT_X = COL_4_LEFT_X;
    private static final int MAGIC_RIGHT_X = COL_4_RIGHT_X;
    private static final int MAGIC_TOP_Y = ROW_1_TOP_Y;
    private static final int MAGIC_BOT_Y = ROW_1_BOT_Y;
    private static final String MAGIC_STRING = "Magic Attack";

    private Button healMagicButton;
    private static final int HEAL_LEFT_X = COL_4_LEFT_X;
    private static final int HEAL_RIGHT_X = COL_4_RIGHT_X;
    private static final int HEAL_TOP_Y = ROW_2_TOP_Y;
    private static final int HEAL_BOT_Y = ROW_2_BOT_Y;
    private static final String HEAL_STRING = "Heal";

    private Button defendButton;
    private static final int DEFEND_LEFT_X = COL_2_LEFT_X;
    private static final int DEFEND_RIGHT_X = COL_3_RIGHT_X;
    private static final int DEFEND_TOP_Y = ROW_2_TOP_Y;
    private static final int DEFEND_BOT_Y = ROW_2_BOT_Y;
    private static final String DEFEND_STRING = "Defend";

    private Button transformButton;
    private static final int TRANSFORM_LEFT_X = COL_2_LEFT_X;
    private static final int TRANSFORM_RIGHT_X = COL_2_RIGHT_X;
    private static final int TRANSFORM_TOP_Y = ROW_1_TOP_Y;
    private static final int TRANSFORM_BOT_Y = ROW_1_BOT_Y;
    private static final String TRANSFORM_STRING = "Transformation";

    private Button specialAttackButton;
    private static final int SPECIAL_LEFT_X = COL_3_LEFT_X;
    private static final int SPECIAL_RIGHT_X = COL_3_RIGHT_X;
    private static final int SPECIAL_TOP_Y = ROW_1_TOP_Y;
    private static final int SPECIAL_BOT_Y = ROW_1_BOT_Y;
    private static final String SPECIAL_STRING = "Special Attack";

    private Button commandSelected;

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

    public BattleScreen(Game game){
        super(game);

        buttonMenuNormal = Assets.buttonMenuNormal;
        buttonMenuSpecial = Assets.buttonMenuSpecial;
        buttonMenuTrans = Assets.buttonMenuBoth;
        charHolder = Assets.charHolder;
        enemyHolder = Assets.enemyHolder;
        specialBar = Assets.specialBar;
        specialBarBase = Assets.specialBarBase;
        specialBarTop = Assets.specialBarTop;
        KOImages = Assets.KOImages;

        createButtonList();
        createPaint();

        charInfo = new BattleCharacterInfoScreen(game);
        charInfo.setPreviousScreen(this);
    }

    public void createButtonList(){
        buttonList = new ButtonList();
        powerAttackButton = new Button(POWER_LEFT_X, POWER_RIGHT_X, POWER_TOP_Y, POWER_BOT_Y, true, POWER_STRING);
        buttonList.addButton(powerAttackButton);
        comboAttackButton = new Button(COMBO_LEFT_X, COMBO_RIGHT_X, COMBO_TOP_Y, COMBO_BOT_Y, true, COMBO_STRING);
        buttonList.addButton(comboAttackButton);
        magicAttackButton = new Button(MAGIC_LEFT_X, MAGIC_RIGHT_X, MAGIC_TOP_Y, MAGIC_BOT_Y, true, MAGIC_STRING);
        buttonList.addButton(magicAttackButton);
        healMagicButton = new Button(HEAL_LEFT_X, HEAL_RIGHT_X, HEAL_TOP_Y, HEAL_BOT_Y, true, HEAL_STRING);
        buttonList.addButton(healMagicButton);
        defendButton = new Button(DEFEND_LEFT_X, DEFEND_RIGHT_X, DEFEND_TOP_Y, DEFEND_BOT_Y, true, DEFEND_STRING);
        buttonList.addButton(defendButton);
        transformButton = new Button(TRANSFORM_LEFT_X, TRANSFORM_RIGHT_X, TRANSFORM_TOP_Y, TRANSFORM_BOT_Y, false, TRANSFORM_STRING);
        buttonList.addButton(transformButton);
        specialAttackButton = new Button(SPECIAL_LEFT_X, SPECIAL_RIGHT_X, SPECIAL_TOP_Y, SPECIAL_BOT_Y, false, SPECIAL_STRING);
        buttonList.addButton(specialAttackButton);
    }

    public void createPaint(){
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(50);
    }

    public void setParty(BattleWifey[] party){
        this.party = party;
    }


    public void setBattleInfo(BattleInfo info){
        this.battleInfo = info;
        this.enemies = battleInfo.getBattleEnemies();
    }

    public void setBackground(Image background){
        this.background = background;
    }

    private void updateButtons(){
        if(numHits >= SPECIAL_HITS){
            specialAttackButton.setActive(true);
            if(party[partyIndex].canTransform()){
                transformButton.setActive(true);
            }
            else{
                transformButton.setActive(false);
            }
        }
        else{
            specialAttackButton.setActive(false);
            transformButton.setActive(false);
        }
    }

    public int getCharacterPressed(TouchEvent touch){
        int i = 0;
        int x = touch.x;
        int y = touch.y;
        for( ; i < partyIndex && i < party.length; i++ ){
            int leftX = CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_SMALL_X;
            int rightX = leftX + CHAR_IMAGE_SMALL_SIZE;
            int topY = CHAR_IMAGE_SMALL_Y;
            int botY = topY + CHAR_IMAGE_SMALL_SIZE;
            if(leftX <= x && rightX >= x && topY <= y && botY >= y){
                return i;
            }
        }
        if(i == partyIndex && i < party.length){
            int leftX = CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_LARGE_X;
            int rightX = leftX + CHAR_IMAGE_LARGE_SIZE;
            int topY = CHAR_IMAGE_LARGE_Y;
            int botY = topY + CHAR_IMAGE_LARGE_SIZE;
            if(leftX <= x && rightX >= x && topY <= y && botY >= y){
                return i;
            }
            i++;
        }
        for( ; i < party.length; i++ ){
            int leftX = CHAR_HOLDER_X_DISTANCE * (i + 1);
            int rightX = leftX + CHAR_IMAGE_SMALL_SIZE;
            int topY = CHAR_IMAGE_SMALL_Y;
            int botY = topY + CHAR_IMAGE_SMALL_SIZE;
            if(leftX <= x && rightX >= x && topY <= y && botY >= y){
                return i;
            }
        }
        return -1;
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
                        if(party[i].getCurrentHP() != 0) {
                            party[i].startBattle(party);
                        }
                    }
                    for(int i = 0; i < enemies.length; i++){
                        enemies[i].startBattle(enemies);
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
                    for(int i = 0; i < party.length; i++){
                        if(party[i].getCurrentHP() != 0){
                            party[i].startWave();
                        }
                    }
                    enemies[enemyIndex].startWave();
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
                    for(int i = 0; i < party.length; i++){
                        if(party[i].getCurrentHP() != 0) {
                            party[i].startRound();
                        }
                    }
                    enemies[enemyIndex].startRound();
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
                    updateButtons();
                }
                else {
                    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

                    for (int i = 0; i < touchEvents.size(); i++) {
                        TouchEvent t = touchEvents.get(i);
                        if (t.type != TouchEvent.TOUCH_UP) {
                            continue;
                        }
                        Button command = buttonList.getButtonPressed(touchEvents.get(i).x, touchEvents.get(i).y);
                        int charPressed = getCharacterPressed(touchEvents.get(i));
                        if(charPressed != -1){
                            charInfo.setChars(party[charPressed], enemies[enemyIndex]);
                            game.setScreen(charInfo);
                            continue;
                        }
                        if (command == null) {
                            continue;
                        }

                        switch (command.getName()) {
                            case TRANSFORM_STRING:
                            case SPECIAL_STRING:
                                numHits -= SPECIAL_HITS;
                            case POWER_STRING:
                            case COMBO_STRING:
                            case MAGIC_STRING:
                            case DEFEND_STRING:
                            case HEAL_STRING:
                                commandSelected = command;
                                break;
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
                    switch (commandSelected.getName()) {
                        case POWER_STRING:
                            baseDamage = party[partyIndex].PowerAttackDamage(enemies[enemyIndex]);
                            displayDamage = enemies[enemyIndex].takePhysicalDamage(baseDamage, party[partyIndex]);
                            enemyDamage = displayDamage;
                            party[partyIndex].onDamageDealt(displayDamage);
                            incrementHits();
                            comboHolder++;
                            damageHolder += enemyDamage;
                            break;
                        case COMBO_STRING:
                            baseDamage = party[partyIndex].ComboAttackDamage(enemies[enemyIndex]);
                            displayDamage = enemies[enemyIndex].takePhysicalDamage(baseDamage, party[partyIndex]);
                            enemyDamage = displayDamage;
                            party[partyIndex].onDamageDealt(displayDamage);
                            incrementHits();
                            hitsPerformed++;
                            comboHolder++;
                            damageHolder += enemyDamage;
                            break;
                        case MAGIC_STRING:
                            baseDamage = party[partyIndex].MagicAttackDamage(enemies[enemyIndex]);
                            displayDamage = enemies[enemyIndex].takeMagicalDamage(baseDamage, party[partyIndex]);
                            enemyDamage = displayDamage;
                            party[partyIndex].onDamageDealt(displayDamage);
                            incrementHits();
                            comboHolder++;
                            damageHolder += enemyDamage;
                            break;
                        case HEAL_STRING:
                            for (int j = 0; j < party.length; j++) {
                                if (party[j].getCurrentHP() > 0) {
                                    baseDamage = party[partyIndex].HealAmount(party[j]);
                                    displayDamage = party[j].healDamage(baseDamage, party[partyIndex]);
                                    partyDamage[j] = displayDamage;
                                }
                                else{
                                    partyDamage[j] = 0;
                                }
                            }
                            break;
                        case SPECIAL_STRING:
                            baseDamage = party[partyIndex].SpecialAttackDamage(enemies[enemyIndex]);
                            displayDamage = enemies[enemyIndex].takeSpecialDamage(baseDamage, party[partyIndex]);
                            enemyDamage = displayDamage;
                            party[partyIndex].onDamageDealt(displayDamage);
                            incrementHits();
                            comboHolder++;
                            damageHolder += enemyDamage;
                            break;
                        case TRANSFORM_STRING:
                            party[partyIndex].transform();
                            for(int i = 0; i < party.length; i++){
                                party[i].resetSkills();
                            }
                            for(int i = 0; i < party.length; i++){
                                party[i].updateParty(party);
                            }
                            break;
                        case DEFEND_STRING:
                            party[partyIndex].Defend();
                            enemyDamage = 0;
                            break;
                        default:
                    }
                } else {
                    phaseTime += deltaTime;
                    int phaseWait;
                    switch(commandSelected.getName()){
                        case POWER_STRING:
                        case COMBO_STRING:
                        case MAGIC_STRING:
                        case SPECIAL_STRING:
                            phaseWait = ATTACK_PHASE_WAIT;
                            break;
                        case HEAL_STRING:
                            phaseWait = HEAL_PHASE_WAIT;
                            break;
                        case DEFEND_STRING:
                        case TRANSFORM_STRING:
                            phaseWait = WAIT_PHASE_WAIT;
                            break;
                        default:
                            phaseWait = OTHER_PHASE_WAIT;
                    }
                    if (phaseTime >= phaseWait) {
                        if (commandSelected == comboAttackButton && hitsPerformed < party[partyIndex].getNumHits()) {
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
                    updateButtons();
                    enemies[enemyIndex].startTurn();
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
                            baseDamage = (int) (enemies[enemyIndex].PowerAttackDamage(party[charIndex]) * enemyMultiplier);
                            displayDamage = party[charIndex].takePhysicalDamage(baseDamage, enemies[enemyIndex]);
                            for(int i = 0; i < party.length; i++){
                                if(i == charIndex){
                                    partyDamage[i] = displayDamage;
                                }
                                else{
                                    partyDamage[i] = 0;
                                }
                            }
                            hitsPerformed++;
                            if(displayDamage > 0) {
                                enemies[enemyIndex].onDamageDealt(displayDamage);
                            }
                            if(party[charIndex].getCurrentHP() == 0){
                                enemies[enemyIndex].onEnemyDefeat(party[charIndex]);
                            }
                            break;
                        case COMBO_ATTACK:
                            charIndex = chooseRandomChar();
                            baseDamage = (int) (enemies[enemyIndex].ComboAttackDamage(party[charIndex]) * enemyMultiplier);
                            displayDamage = party[charIndex].takePhysicalDamage(baseDamage, enemies[enemyIndex]);
                            for(int i = 0; i < party.length; i++){
                                if(i == charIndex){
                                    partyDamage[i] = displayDamage;
                                }
                                else{
                                    partyDamage[i] = 0;
                                }
                            }
                            hitsPerformed++;
                            if(displayDamage > 0) {
                                enemies[enemyIndex].onDamageDealt(displayDamage);
                            }
                            if(party[charIndex].getCurrentHP() == 0){
                                enemies[enemyIndex].onEnemyDefeat(party[charIndex]);
                            }
                            break;
                        case MAGIC_ATTACK:
                            for(int i = 0; i < party.length; i++){
                                if(party[i].getCurrentHP() > 0){
                                    baseDamage = (int) (enemies[enemyIndex].MagicAttackDamage(party[i]) * enemyMultiplier);
                                    displayDamage = party[i].takeMagicalDamage(baseDamage, enemies[enemyIndex]);
                                    partyDamage[i] = displayDamage;
                                }
                                else{
                                    partyDamage[i] = 0;
                                }
                            }
                            hitsPerformed++;
                            for(int i = 0; i < party.length; i++){
                                if(partyDamage[i] > 0){
                                    enemies[enemyIndex].onDamageDealt(partyDamage[i]);
                                }
                            }
                            for(int i = 0; i < party.length; i++){
                                if(partyDamage[i] > 0 && party[i].getCurrentHP() == 0){
                                    enemies[enemyIndex].onEnemyDefeat(party[i]);
                                }
                            }
                            break;
                        case HEALING_MAGIC:
                            baseDamage = (int) (enemies[enemyIndex].HealAmount(enemies[enemyIndex]) * enemyMultiplier);
                            enemyDamage = enemies[enemyIndex].healDamage(baseDamage, enemies[enemyIndex]);
                            hitsPerformed++;
                            break;
                        case SPECIAL_ATTACK:
                            for(int i = 0; i < party.length; i++){
                                if(party[i].getCurrentHP() > 0){
                                    baseDamage = (int) (enemies[enemyIndex].SpecialAttackDamage(party[i]) * enemyMultiplier);
                                    partyDamage[i] = party[i].takeSpecialDamage(baseDamage, enemies[enemyIndex]);
                                }
                                else{
                                    partyDamage[i] = 0;
                                }
                            }
                            hitsPerformed++;
                            for(int i = 0; i < party.length; i++){
                                if(partyDamage[i] > 0){
                                    enemies[enemyIndex].onDamageDealt(partyDamage[i]);
                                }
                            }
                            for(int i = 0; i < party.length; i++){
                                if(partyDamage[i] > 0 && party[i].getCurrentHP() == 0){
                                    enemies[enemyIndex].onEnemyDefeat(party[i]);
                                }
                            }
                            break;
                        case TRANSFORM:
                            enemies[enemyIndex].transform();
                            for(int i = 0; i < enemies.length; i++){
                                enemies[i].resetSkills();
                            }
                            for(int i = 0; i < enemies.length; i++){
                                enemies[i].updateParty(party);
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
                    for(int i = 0; i < party.length; i++){
                        if(party[i].getCurrentHP() != 0){
                            party[i].endRound();
                        }
                    }
                    enemies[enemyIndex].endRound();
                    enemyMultiplier += roundMultiplier;
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
                //WifeyCharacter end of wave bonuses
                //If more enemies, go to WAVE_START
                //If no more enemies, go to BATTLE_END
                if (phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;
                } else {
                    phaseTime += deltaTime;
                    if (phaseTime >= WAIT_PHASE_WAIT) {
                        enemyIndex++;
                        if (enemyIndex == enemies.length) {
                            enemyIndex--;
                            currentPhase = BattlePhase.BATTLE_END;
                            phaseEntered = true;
                        } else {
                            party[partyIndex].onEnemyDefeat(enemies[enemyIndex]);
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
                    phaseEntered = false;

                    //Temporary go back to Select
                    BattleSelectScreen bss = new BattleSelectScreen(game);
                    game.setScreen(bss);
                }
                break;

        }
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
        if(transformButton.isActive()){
            g.drawImage(buttonMenuTrans, 0, BUTTON_MENU_Y);
        }
        else if(specialAttackButton.isActive()){
            g.drawImage(buttonMenuSpecial, 0, BUTTON_MENU_Y);
        }
        else{
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
            g.drawPercentageImage(charHolder, CHAR_HOLDER_X_DISTANCE * i, CHAR_HOLDER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            g.drawPercentageImage(party[i].getImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_SMALL_X, CHAR_IMAGE_SMALL_Y, HALF_SCALE, HALF_SCALE);

            //Draw the elements
            g.drawPercentageImage(party[i].getAttackElement().getElementImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_SMALL_BASE_ELEM_ATK_X, CHAR_SMALL_ELEM_Y, CHAR_SMALL_ELEM_SCALE, CHAR_SMALL_ELEM_SCALE);
            g.drawPercentageImage(party[i].getStrongElement().getElementImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_SMALL_BASE_ELEM_RES_X, CHAR_SMALL_ELEM_Y, CHAR_SMALL_ELEM_SCALE, CHAR_SMALL_ELEM_SCALE);
            g.drawPercentageImage(party[i].getWeakElement().getElementImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_SMALL_BASE_ELEM_WEAK_X, CHAR_SMALL_ELEM_Y, CHAR_SMALL_ELEM_SCALE, CHAR_SMALL_ELEM_SCALE);

            //Draw the health bar
            healthBar = getPlayerHealthBar(party[i].getCurrentHP(), party[i].getMaxHP());
            perHealth = (party[i].getCurrentHP() * 1.0)/(party[i].getMaxHP());
            g.drawPercentageImage(healthBar, CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_SMALL_X, CHAR_HEALTH_SMALL_Y, (int) Math.ceil(perHealth * HALF_SCALE), HALF_SCALE);

            //Draw the CurrentHP / MaxHP
            numberStart = false;
            divisor = 1000;
            j = 0;
            for( ; j < 4; j++){
                int numberPart = (party[i].getCurrentHP() / divisor % 10);
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    g.drawPercentageImage(Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * i + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
                }
                divisor = divisor / 10;
            }
            g.drawPercentageImage(Assets.HPNumbers[SLASH], CHAR_HOLDER_X_DISTANCE * i + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            j++;
            numberStart = false;
            divisor = 1000;
            while(divisor > 0){
                int numberPart = (party[i].getMaxHP() / divisor) % 10;
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    g.drawPercentageImage(Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * i + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
                    j++;
                }
                divisor = divisor / 10;
            }

            //If the party member is defeated, overlay the KO Image
            if(party[i].getCurrentHP() == 0){
                g.drawPercentageImage(KOImages[i], CHAR_HOLDER_X_DISTANCE * i, CHAR_HOLDER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            }

        }
        //Draw the current acting character
        if(i == partyIndex && i < party.length) {
            g.drawImage(charHolder, CHAR_HOLDER_X_DISTANCE * i, CHAR_HOLDER_LARGE_Y);
            g.drawImage(party[i].getImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_LARGE_X, CHAR_IMAGE_LARGE_Y);

            //Draw the elements
            g.drawPercentageImage(party[i].getAttackElement().getElementImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_LARGE_BASE_ELEM_ATK_X, CHAR_LARGE_ELEM_Y, CHAR_LARGE_ELEM_SCALE, CHAR_LARGE_ELEM_SCALE);
            g.drawPercentageImage(party[i].getStrongElement().getElementImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_LARGE_BASE_ELEM_RES_X, CHAR_LARGE_ELEM_Y, CHAR_LARGE_ELEM_SCALE, CHAR_LARGE_ELEM_SCALE);
            g.drawPercentageImage(party[i].getWeakElement().getElementImage(), CHAR_HOLDER_X_DISTANCE * i + CHAR_LARGE_BASE_ELEM_WEAK_X, CHAR_LARGE_ELEM_Y, CHAR_LARGE_ELEM_SCALE, CHAR_LARGE_ELEM_SCALE);

            healthBar = getPlayerHealthBar(party[i].getCurrentHP(), party[i].getMaxHP());
            perHealth = (party[i].getCurrentHP() * 1.0) / (party[i].getMaxHP());
            g.drawPercentageImage(healthBar, CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_LARGE_X, CHAR_HEALTH_LARGE_Y, (int) Math.ceil(perHealth * FULL_SCALE), FULL_SCALE);

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
            g.drawPercentageImage(charHolder, CHAR_HOLDER_X_DISTANCE * (i + 1), CHAR_HOLDER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            g.drawPercentageImage(party[i].getImage(), CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_INTERIOR_SMALL_X, CHAR_IMAGE_SMALL_Y, HALF_SCALE, HALF_SCALE);

            //Draw the elements
            g.drawPercentageImage(party[i].getAttackElement().getElementImage(), CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_SMALL_BASE_ELEM_ATK_X, CHAR_SMALL_ELEM_Y, CHAR_SMALL_ELEM_SCALE, CHAR_SMALL_ELEM_SCALE);
            g.drawPercentageImage(party[i].getStrongElement().getElementImage(), CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_SMALL_BASE_ELEM_RES_X, CHAR_SMALL_ELEM_Y, CHAR_SMALL_ELEM_SCALE, CHAR_SMALL_ELEM_SCALE);
            g.drawPercentageImage(party[i].getWeakElement().getElementImage(), CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_SMALL_BASE_ELEM_WEAK_X, CHAR_SMALL_ELEM_Y, CHAR_SMALL_ELEM_SCALE, CHAR_SMALL_ELEM_SCALE);

            healthBar = getPlayerHealthBar(party[i].getCurrentHP(), party[i].getMaxHP());
            perHealth = (party[i].getCurrentHP() * 1.0)/(party[i].getMaxHP());
            g.drawPercentageImage(healthBar, CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_INTERIOR_SMALL_X, CHAR_HEALTH_SMALL_Y, (int) Math.ceil(perHealth * HALF_SCALE) , HALF_SCALE);

            //Display CURRENTHP/MAXHP
            numberStart = false;
            divisor = 1000;
            j = 0;
            for( ; j < 4; j++){
                int numberPart = (party[i].getCurrentHP() / divisor) % 10;
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    g.drawPercentageImage(Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
                }
                divisor = divisor / 10;
            }
            g.drawPercentageImage(Assets.HPNumbers[SLASH], CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
            j++;
            numberStart = false;
            divisor = 1000;
            while(divisor > 0){
                int numberPart = (party[i].getMaxHP() / divisor) % 10;
                numberStart = (numberStart || (numberPart > 0));
                if(numberStart){
                    g.drawPercentageImage(Assets.HPNumbers[numberPart], CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_NUMBER_SMALL_X + CHAR_NUMBER_SMALL_DISTANCE_X * j, CHAR_NUMBER_SMALL_Y, HALF_SCALE, HALF_SCALE);
                    j++;
                }
                divisor = divisor / 10;
            }

            if(party[i].getCurrentHP() == 0){
                g.drawPercentageImage(KOImages[i], CHAR_HOLDER_X_DISTANCE * (i + 1), CHAR_HOLDER_SMALL_Y, HALF_SCALE, HALF_SCALE);
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
        g.drawPercentageImage(enemyHealth, ENEMY_HEALTH_BAR_X, ENEMY_HEALTH_BAR_Y, (int) Math.ceil(FULL_SCALE * perHealth), FULL_SCALE);
        g.drawPercentageImage(enemies[enemyIndex].getAttackElement().getElementImage(), ENEMY_ELEM_ATK_X, ENEMY_ELEM_Y, ENEMY_ELEM_SCALE, ENEMY_ELEM_SCALE);
        g.drawPercentageImage(enemies[enemyIndex].getStrongElement().getElementImage(), ENEMY_ELEM_RES_X, ENEMY_ELEM_Y, ENEMY_ELEM_SCALE, ENEMY_ELEM_SCALE);
        g.drawPercentageImage(enemies[enemyIndex].getWeakElement().getElementImage(), ENEMY_ELEM_WEAK_X, ENEMY_ELEM_Y, ENEMY_ELEM_SCALE, ENEMY_ELEM_SCALE);

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
        g.drawPercentageImage(specialBar, SPECIAL_BAR_X, SPECIAL_BAR_Y, (int) ((numHits * 100.0) / MAX_HITS) , FULL_SCALE);
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
        g.drawString(commandSelected.getName(), 400, 70, textPaint);
        divisor = 10000;
        numberStart = false;
        int offset = 4;
        j = 0;
        while(divisor > 0){
            int numberPart = enemyDamage/divisor % 10;
            numberStart = (numberStart || (numberPart > 0));
            if(numberStart){
                int y = ENEMY_DAMAGE_START_Y - (int) (ENEMY_DAMAGE_INCREASE_Y * phaseTime / ATTACK_PHASE_WAIT);
                Image hpNum;
                if(party[partyIndex].isWeaknessAttack(enemies[enemyIndex])){
                    hpNum = Assets.WeakNumbers[numberPart];
                }
                else if(party[partyIndex].isStrongAttack(enemies[enemyIndex])){
                    hpNum = Assets.ResistNumbers[numberPart];
                }
                else{
                    hpNum = Assets.HPNumbers[numberPart];
                }
                g.drawImage(hpNum, ENEMY_DAMAGE_BASE_X - ENEMY_DAMAGE_OFFSET_X * offset + ENEMY_DAMAGE_DISTANCE_X * j, y);
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
                    Image hpNum;
                    if(enemies[enemyIndex].isWeaknessAttack(party[i])){
                        hpNum = Assets.WeakNumbers[numberPart];
                    }
                    else if(enemies[enemyIndex].isStrongAttack(party[i])){
                        hpNum = Assets.ResistNumbers[numberPart];
                    }
                    else{
                        hpNum = Assets.HPNumbers[numberPart];
                    }
                    g.drawImage(hpNum, x, y);
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