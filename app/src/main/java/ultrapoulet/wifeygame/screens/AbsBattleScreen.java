package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Animation;
import ultrapoulet.androidgame.framework.helpers.AnimationImages;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
import ultrapoulet.wifeygame.AnimationAssets;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.BattleAssets;
import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.battle.BattleWifey;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI;
import ultrapoulet.wifeygame.character.Element;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.gamestate.Party;

/**
 * Created by John on 5/20/2017.
 */

public abstract class AbsBattleScreen extends Screen {
    //Determine if it is really necessary to make these Lists of BattleCharacters
    public List<BattleCharacter> party;
    public BattleInfo battleInfo;
    public List<BattleCharacter> enemies;

    public int partyIndex = -1;
    public int enemyIndex = 0;

    private int numHits = 0;
    private static final int SPECIAL_HITS = 60;
    private static final int MAX_HITS = SPECIAL_HITS * 5;

    private double enemyMultiplier = 1.00;
    private final double roundMultiplier = 0.025;

    private Image background;

    private int comboHolder;
    private int damageHolder;

    private Paint textPaint;

    private Animation battleAnimation = null;
    private int battleAnimationOffsetX = 0;
    private int battleAnimationOffsetY = 0;
    private Animation healAnimation = null;

    private static final int ENEMY_IMAGE_X = 200;
    private static final int ENEMY_IMAGE_Y = 100;

    private static final int ENEMY_BATTLE_ANIMATION_BASE_OFFSET_X = 100;
    private static final int ENEMY_BATTLE_ANIMATION_BASE_OFFSET_Y = 100;
    private static final int ENEMY_BATTLE_ANIMATION_SIZE = 200;
    private static final int ENEMY_BATTLE_ANIMATION_MAX_OFFSET = 50;

    private static final int CHAR_HOLDER_X_DISTANCE = 100;
    private static final int CHAR_HOLDER_SMALL_Y = 745;
    private static final int CHAR_HOLDER_LARGE_Y = 610;
    private static final int CHAR_HOLDER_KO_SMALL_Y = 760;
    private static final int CHAR_HOLDER_KO_LARGE_Y = 740;

    private static final int CHAR_CUR_HP_SMALL_X = 47;
    private static final int CHAR_HP_SLASH_SMALL_X = 45;
    private static final int CHAR_MAX_HP_SMALL_X = 53;
    private static final int CHAR_HP_SMALL_OFFSET = -2;
    private static final int CHAR_HP_SMALL_Y = 855;
    private static final int CHAR_HP_SMALL_WIDTH = 10;
    private static final int CHAR_HP_SMALL_HEIGHT = 20;

    private static final int CHAR_CUR_HP_LARGE_X = 94;
    private static final int CHAR_HP_SLASH_LARGE_X = 90;
    private static final int CHAR_MAX_HP_LARGE_X = 106;
    private static final int CHAR_HP_LARGE_OFFSET = -4;
    private static final int CHAR_HP_LARGE_Y = 830;
    private static final int CHAR_HP_LARGE_WIDTH = 20;
    private static final int CHAR_HP_LARGE_HEIGHT = 40;

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

    private static final int ENEMY_CUR_HP_X = 390;
    private static final int ENEMY_HP_SLASH_X = 390;
    private static final int ENEMY_MAX_HP_X = 410;
    private static final int ENEMY_HP_Y = 520;
    private static final int ENEMY_HP_WIDTH = 20;
    private static final int ENEMY_HP_HEIGHT = 40;
    private static final int ENEMY_HP_OFFSET = 0;

    private static final int SPECIAL_BAR_BASE_X = 0;
    private static final int SPECIAL_BAR_BASE_Y = 580;
    private static final int SPECIAL_BAR_X = 100;
    private static final int SPECIAL_BAR_Y = 585;
    private static final int SPECIAL_BAR_TOP_Y = 555;
    private static final int SPECIAL_BAR_NUMBER_X = 390;
    private static final int SPECIAL_BAR_NUMBER_Y = 560;
    private static final int SPECIAL_NUMBER_WIDTH = 20;
    private static final int SPECIAL_NUMBER_HEIGHT = 40;
    private static final int SPECIAL_NUMBER_OFFSET = 0;

    private static final int HALF_SCALE = 50;
    private static final int FULL_SCALE = 100;

    private static final int ATTACK_PHASE_WAIT = 30;
    private static final int HEAL_PHASE_WAIT = 45;
    private static final int WAIT_PHASE_WAIT = 60;
    private static final int OTHER_PHASE_WAIT = 5;
    private static final int WAVE_PHASE_WAIT = 60;
    private float phaseTime = 0;
    private boolean phaseEntered = true;

    private static final int ENEMY_DAMAGE_BASE_X = 400;
    private static final int ENEMY_DAMAGE_START_Y = 240;
    private static final int ENEMY_DAMAGE_INCREASE_Y = 80;

    private static final int CHAR_DAMAGE_BASE_X = 50;
    private static final int CHAR_DAMAGE_SMALL_START_Y = 820;
    private static final int CHAR_DAMAGE_LARGE_START_Y = 790;
    private static final int CHAR_DAMAGE_SMALL_INCREASE_Y = 80;
    private static final int CHAR_DAMAGE_LARGE_INCREASE_Y = 160;

    private static final int PLAYER_DAMAGE_WIDTH = 20;
    private static final int PLAYER_DAMAGE_HEIGHT = 40;
    private static final int ENEMY_DAMAGE_WIDTH = 30;
    private static final int ENEMY_DAMAGE_HEIGHT = 60;
    private static final int DAMAGE_OFFSET = 0;

    private static final int COMBO_NUMBER_X = 690;
    private static final int COMBO_NUMBER_Y = 400;
    private static final int COMBO_NUMBER_WIDTH = 30;
    private static final int COMBO_NUMBER_HEIGHT = 60;
    private static final int COMBO_NUMBER_OFFSET = 0;
    private static final int COMBO_TEXT_X = 700;
    private static final int COMBO_TEXT_Y = 430;

    private static final int DAMAGE_HOLDER_NUMBER_X = 720;
    private static final int DAMAGE_HOLDER_NUMBER_Y = 460;
    private static final int DAMAGE_HOLDER_WIDTH = 20;
    private static final int DAMAGE_HOLDER_HEIGHT = 40;
    private static final int DAMAGE_HOLDER_OFFSET = 0;
    private static final int DAMAGE_TEXT_X = 720;
    private static final int DAMAGE_TEXT_Y = 480;

    private static final int ENEMY_STATUS_X = ENEMY_IMAGE_X - 50;
    private static final int ENEMY_STATUS_Y_1 = 450;
    private static final int ENEMY_STATUS_Y_2 = 400;

    private static final int WAVE_TEXT_LARGE_X = 300;
    private static final int WAVE_NUMBER_LARGE_X = WAVE_TEXT_LARGE_X + 121 + 18;
    private static final int WAVE_TEXT_SMALL_X = 315;
    private static final int WAVE_NUMBER_SMALL_X = WAVE_TEXT_SMALL_X + 121 + 18;
    private static final int WAVE_FINAL_TEXT_X = 272;
    private static final int WAVE_TEXT_Y = 270;
    private static final int WAVE_WIDTH = 30;
    private static final int WAVE_HEIGHT = 60;

    private int hitsPerformed = 0;
    //private int enemyDamage;
    //private int partyDamage[] = new int[7];
    private static final int HEAL_DAMAGE = -1;

    private BattleCharacterInfoScreen charInfo;

    //New Button things
    private static final int COL_1_LEFT_X = 0;
    private static final int COL_1_RIGHT_X = 200;
    private static final int COL_2_LEFT_X = 200;
    private static final int COL_2_RIGHT_X = 400;
    private static final int COL_3_LEFT_X = 400;
    private static final int COL_3_RIGHT_X = 600;
    private static final int COL_4_LEFT_X = 600;
    private static final int COL_4_RIGHT_X = 800;
    private static final int ROW_1_TOP_Y = 880;
    private static final int ROW_1_BOT_Y = 1080;
    private static final int ROW_2_TOP_Y = 1080;
    private static final int ROW_2_BOT_Y = 1280;

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

    private Button enemyButton;
    private static final int ENEMY_BUTTON_LEFT_X = ENEMY_IMAGE_X;
    private static final int ENEMY_BUTTON_RIGHT_X = ENEMY_BUTTON_LEFT_X + 400;
    private static final int ENEMY_BUTTON_TOP_Y = ENEMY_IMAGE_Y;
    private static final int ENEMY_BUTTON_BOT_Y = ENEMY_BUTTON_TOP_Y + 400;
    private static final String ENEMY_STRING = "Enemy";

    private EnemyInfoScreen enemyInfoScreen;

    private Button commandSelected;

    private ButtonList partyList;
    private static final int CHAR_INTERIOR_SMALL_X = 10;
    private static final int CHAR_IMAGE_SMALL_Y = 770;
    private static final int CHAR_HEALTH_SMALL_Y = 860;

    private static final int CHAR_INTERIOR_LARGE_X = 20;
    private static final int CHAR_IMAGE_LARGE_Y = 660;
    private static final int CHAR_HEALTH_LARGE_Y = 840;

    private static final int CHAR_IMAGE_SMALL_SIZE = 80;
    private static final int CHAR_IMAGE_LARGE_SIZE = 160;

    private static final int CHAR_BATTLE_ANIMATION_SIZE = 100;
    private static final int CHAR_BATTLE_LARGE_ANIMATION_SIZE = 200;
    private static final int CHAR_BATTLE_ANIMATION_BASE_OFFSET_X = (CHAR_IMAGE_SMALL_SIZE - CHAR_BATTLE_ANIMATION_SIZE) / 2;
    private static final int CHAR_BATTLE_ANIMATION_BASE_OFFSET_Y = (CHAR_IMAGE_SMALL_SIZE - CHAR_BATTLE_ANIMATION_SIZE) / 2;
    private static final int CHAR_BATTLE_ANIMATION_MAX_OFFSET = 25;

    private enum BattlePhase{
        BATTLE_START,
        WAVE_START,
        ROUND_START,
        WAIT_PLAYER_ACTION,
        ANIMATE_PLAYER_ACTION,
        PREVENT_ENEMY_DEFEAT,
        WAIT_ENEMY_ACTION,
        ANIMATE_ENEMY_ACTION,
        PREVENT_PLAYER_DEFEAT,
        ROUND_END,
        WAVE_END,
        BATTLE_END
    }

    private BattlePhase currentPhase = BattlePhase.BATTLE_START;

    public AbsBattleScreen(Game game, BattleInfo info){
        super(game);

        createButtonList();
        createPaint();

        charInfo = new BattleCharacterInfoScreen(game, this);

        setBattleInfo(info);

        //The party is set now
        enemyInfoScreen = new EnemyInfoScreen(game, this, party);
    }

    protected abstract Screen getCompletionScreen();

    public void createButtonList(){
        buttonList = new ButtonList();
        powerAttackButton = new Button(POWER_LEFT_X, POWER_RIGHT_X, POWER_TOP_Y, POWER_BOT_Y, true, POWER_STRING, Assets.PowerAttackEnabled, Assets.PowerAttackDisabled);
        buttonList.addButton(powerAttackButton);
        comboAttackButton = new Button(COMBO_LEFT_X, COMBO_RIGHT_X, COMBO_TOP_Y, COMBO_BOT_Y, true, COMBO_STRING, Assets.ComboAttackEnabled, Assets.ComboAttackDisabled);
        buttonList.addButton(comboAttackButton);
        magicAttackButton = new Button(MAGIC_LEFT_X, MAGIC_RIGHT_X, MAGIC_TOP_Y, MAGIC_BOT_Y, true, MAGIC_STRING, Assets.MagicAttackEnabled, Assets.MagicAttackDisabled);
        buttonList.addButton(magicAttackButton);
        healMagicButton = new Button(HEAL_LEFT_X, HEAL_RIGHT_X, HEAL_TOP_Y, HEAL_BOT_Y, true, HEAL_STRING, Assets.HealMagicEnabled, Assets.HealMagicDisabled);
        buttonList.addButton(healMagicButton);
        defendButton = new Button(DEFEND_LEFT_X, DEFEND_RIGHT_X, DEFEND_TOP_Y, DEFEND_BOT_Y, true, DEFEND_STRING, Assets.DefendEnabled, Assets.DefendDisabled);
        buttonList.addButton(defendButton);
        transformButton = new Button(TRANSFORM_LEFT_X, TRANSFORM_RIGHT_X, TRANSFORM_TOP_Y, TRANSFORM_BOT_Y, false, TRANSFORM_STRING, Assets.TransformEnabled, Assets.TransformDisabled);
        buttonList.addButton(transformButton);
        specialAttackButton = new Button(SPECIAL_LEFT_X, SPECIAL_RIGHT_X, SPECIAL_TOP_Y, SPECIAL_BOT_Y, false, SPECIAL_STRING, Assets.SpecialAttackEnabled, Assets.SpecialAttackDisabled);
        buttonList.addButton(specialAttackButton);
        enemyButton = new Button(ENEMY_BUTTON_LEFT_X, ENEMY_BUTTON_RIGHT_X, ENEMY_BUTTON_TOP_Y, ENEMY_BUTTON_BOT_Y, true, ENEMY_STRING);
        buttonList.addButton(enemyButton);
    }

    public void createPaint(){
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(50);
    }

    private void setParty(List<BattleCharacter> party){
        this.party = party;

        createPartyList();
    }

    private void createPartyList(){
        partyList = new ButtonList();
        for(int i = 0; i < party.size(); i++){
            int leftX = CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_SMALL_X;
            int rightX = leftX + CHAR_IMAGE_SMALL_SIZE;
            int topY = CHAR_IMAGE_SMALL_Y;
            int botY = topY + CHAR_IMAGE_SMALL_SIZE;
            partyList.addButton(new Button(leftX, rightX, topY, botY, true, "PARTY_" + i));
        }
    }


    private void setBattleInfo(BattleInfo info){
        this.battleInfo = info;
        this.enemies = battleInfo.getBattleEnemies(game.getGraphics());
        this.background = battleInfo.getBackground(game.getGraphics());

        setParty(Party.getBattleParty(battleInfo.getPartyMax(), game.getGraphics()));
    }

    private void updateButtons(){
        if(numHits >= SPECIAL_HITS){
            specialAttackButton.setActive(true);
            if(partyIndex != party.size() && party.get(partyIndex).canTransform()){
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
        int i = 0;
        for( ; i < partyIndex && i < party.size(); i++){
            int leftX = CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_SMALL_X;
            int rightX = leftX + CHAR_IMAGE_SMALL_SIZE;
            int topY = CHAR_IMAGE_SMALL_Y;
            int botY = topY + CHAR_IMAGE_SMALL_SIZE;
            partyList.get(i).setCoordinates(leftX, rightX, topY, botY);
        }
        if(i == partyIndex && i < party.size()){
            int leftX = CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_LARGE_X;
            int rightX = leftX + CHAR_IMAGE_LARGE_SIZE;
            int topY = CHAR_IMAGE_LARGE_Y;
            int botY = topY + CHAR_IMAGE_LARGE_SIZE;
            partyList.get(i).setCoordinates(leftX, rightX, topY, botY);
            i++;
        }
        for( ; i < party.size(); i++){
            int leftX = CHAR_HOLDER_X_DISTANCE * (i + 1);
            int rightX = leftX + CHAR_IMAGE_SMALL_SIZE;
            int topY = CHAR_IMAGE_SMALL_Y;
            int botY = topY + CHAR_IMAGE_SMALL_SIZE;
            partyList.get(i).setCoordinates(leftX, rightX, topY, botY);
        }
    }

    private void incrementHits(){
        numHits = (numHits < MAX_HITS) ? numHits + 1 : numHits;
    }

    private void resetPartyIndex(){
        partyIndex = -1;
    }

    private void nextPartyIndex(){
        partyIndex = (partyIndex + 1) % party.size();
        while(party.get(partyIndex).getCurrentHP() == 0) {
            partyIndex = (partyIndex + 1) % party.size();
        }
    }

    private void noPartyIndex(){
        partyIndex = party.size();
    }

    //Returns the first living party member index
    private int getFirstIndex() {
        int returnVal = 0;
        while(party.get(returnVal).getCurrentHP() == 0) {
            returnVal++;
        }
        return returnVal;
    }

    //Returns true if the next character partyIndex would roll over, indicating end of round.
    private boolean isEndOfRound(){
        int tempIndex = (partyIndex + 1) % party.size();
        while(party.get(tempIndex).getCurrentHP() == 0){
            tempIndex = (tempIndex + 1) % party.size();
        }
        return tempIndex <= partyIndex;
    }

    //Returns true if a character can prevent death
    private boolean canPreventPartyDeath() {
        for(int i = 0; i < party.size(); i++){
            if(party.get(i).getCurrentHP() == 0 && party.get(i).canPreventDeath()){
                return true;
            }
        }
        return false;
    }

    //Returns true if all characters are dead
    private boolean isGameOver(){
        for(int i = 0; i < party.size(); i++){
            if(party.get(i).getCurrentHP() > 0){
                return false;
            }
        }
        return true;
    }

    //Chooses a random character that is alive
    private int chooseRandomChar(){
        int numAlive = 0;
        for(int i = 0; i < party.size(); i++){
            if(party.get(i).getCurrentHP() > 0){ numAlive++; }
        }
        int[] aliveNumbers = new int[numAlive];
        int index = 0;
        for(int i = 0; i < party.size(); i++){
            if(party.get(i).getCurrentHP() > 0) {
                aliveNumbers[index] = i;
                index++;
            }
        }
        int select = (int) (Math.random() * numAlive);
        return aliveNumbers[select];
    }

    private void resetDamage() {
        enemies.get(enemyIndex).resetDisplayDamage();
        //enemyDamage = 0;
        /*
        for(int i = 0; i < partyDamage.length; i++){
            partyDamage[i] = 0;
        }
        */
        for(int i = 0; i < party.size(); i++) {
            party.get(i).resetDisplayDamage();
        }
    }

    private int getPlayerPhaseWait(){
        switch(commandSelected.getName()){
            case POWER_STRING:
            case COMBO_STRING:
            case MAGIC_STRING:
            case SPECIAL_STRING:
                return ATTACK_PHASE_WAIT;
            case HEAL_STRING:
                return HEAL_PHASE_WAIT;
            case DEFEND_STRING:
            case TRANSFORM_STRING:
                return WAIT_PHASE_WAIT;
            default:
                return OTHER_PHASE_WAIT;
        }
    }

    private int getEnemyPhaseWait() {
        switch(((BattleEnemy) enemies.get(enemyIndex)).getAction()){
            case POWER_ATTACK:
            case COMBO_ATTACK:
            case MAGIC_ATTACK:
            case SPECIAL_ATTACK:
                return ATTACK_PHASE_WAIT;
            case HEALING_MAGIC:
                return HEAL_PHASE_WAIT;
            case POWER_UP:
            case POWER_DOWN:
            case DEFEND:
            case WEAKEN:
            case WAIT:
            case TRANSFORM:
                return WAIT_PHASE_WAIT;
            default:
                return OTHER_PHASE_WAIT;
        }
    }

    private void clearAnimations() {
        battleAnimation = null;
        healAnimation = null;
    }

    @Override
    public void update(float deltaTime) {

        switch (currentPhase) {
            case BATTLE_START:
                //Do anything necessary for battle start
                if (phaseEntered) {
                    //On Battle Start, increment the Number attempts in the BattleInfo
                    battleInfo.incrementNumAttempts();
                    phaseTime = 0;
                    phaseEntered = false;
                    for(int i = 0; i < party.size(); i++){
                        if(party.get(i).getCurrentHP() != 0) {
                            party.get(i).startBattle(party);
                        }
                    }
                    for(int i = 0; i < enemies.size(); i++){
                        enemies.get(i).startBattle(enemies);
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
                    resetPartyIndex();
                    for(int i = 0; i < party.size(); i++){
                        if(party.get(i).getCurrentHP() != 0){
                            party.get(i).startWave();
                        }
                    }
                    enemies.get(enemyIndex).startWave();
                    resetDamage();
                    clearAnimations();
                    phaseTime = 0;
                    phaseEntered = false;
                    comboHolder = 0;
                    damageHolder = 0;
                } else {
                    phaseTime += deltaTime;
                    if (phaseTime >= WAVE_PHASE_WAIT) {
                        currentPhase = BattlePhase.ROUND_START;
                        phaseEntered = true;
                    }
                }
                break;
            case ROUND_START:
                //Do things for Start of Round
                if (phaseEntered) {
                    resetPartyIndex();
                    clearAnimations();
                    for(int i = 0; i < party.size(); i++){
                        if(party.get(i).getCurrentHP() != 0) {
                            //partyDamage[i] = party.get(i).startRound();
                            party.get(i).setDisplayDamage(party.get(i).startRound());
                        }
                    }
                    //enemyDamage = enemies.get(enemyIndex).startRound();
                    enemies.get(enemyIndex).setDisplayDamage(enemies.get(enemyIndex).startRound());
                    AnimationImages animation = null;
                    for(int i = 0; i < party.size(); i++){
                        //if(partyDamage[i] != 0) {
                        if(party.get(i).getDisplayDamage() != 0) {
                            animation = AnimationAssets.HealAnimation;
                        }
                    }
                    //if(enemyDamage != 0) {
                    if(enemies.get(enemyIndex).getDisplayDamage() != 0) {
                        animation = AnimationAssets.HealAnimation;
                    }
                    if(animation != null){
                        healAnimation = new Animation(animation, HEAL_PHASE_WAIT, false);
                    }
                    //resetDamage();
                    comboHolder = 0;
                    damageHolder = 0;
                    phaseTime = 0;
                    phaseEntered = false;
                } else {
                    phaseTime += deltaTime;
                    if(healAnimation != null){
                        healAnimation.update(deltaTime);
                    }
                    int waitTime = OTHER_PHASE_WAIT;
                    for(int i = 0; i < party.size(); i++){
                        //if(partyDamage[i] != 0) {
                        if(party.get(i).getDisplayDamage() != 0) {
                            waitTime = HEAL_PHASE_WAIT;
                        }
                    }
                    //if(enemyDamage != 0) {
                    if(enemies.get(enemyIndex).getDisplayDamage() != 0) {
                        waitTime = HEAL_PHASE_WAIT;
                    }
                    if (phaseTime >= waitTime) {
                        currentPhase = BattlePhase.WAIT_PLAYER_ACTION;
                        phaseEntered = true;
                    }

                }
                break;
            case WAIT_PLAYER_ACTION:
                //If player has made a selection, get it ready for animating
                if(phaseEntered){
                    nextPartyIndex();
                    clearAnimations();
                    phaseEntered = false;
                    //Clear the touch input buffer
                    game.getInput().getTouchEvents();
                    party.get(partyIndex).startTurn();
                    resetDamage();
                    updateButtons();
                }
                else {
                    List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

                    for (int i = 0; i < touchEvents.size(); i++) {
                        Input.TouchEvent t = touchEvents.get(i);
                        if (t.type != Input.TouchEvent.TOUCH_UP) {
                            continue;
                        }
                        Button command = buttonList.getButtonPressed(t.x, t.y);
                        int charPressed = partyList.getIndexPressed(t.x, t.y);
                        if(charPressed != -1){
                            charInfo.setChars(((BattleWifey) party.get(charPressed)), (BattleEnemy) enemies.get(enemyIndex));
                            game.setScreen(charInfo);
                            continue;
                        }
                        if (command == null) {
                            continue;
                        }
                        if(command == enemyButton){
                            enemyInfoScreen.setEnemy((BattleEnemy) enemies.get(enemyIndex));
                            game.setScreen(enemyInfoScreen);
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
                    clearAnimations();

                    int baseDamage;
                    int displayDamage;
                    AnimationImages animation = null;
                    AnimationImages healingAnimation = null;
                    switch (commandSelected.getName()) {
                        case POWER_STRING:
                            baseDamage = party.get(partyIndex).PowerAttackDamage(enemies.get(enemyIndex));
                            displayDamage = enemies.get(enemyIndex).takePhysicalDamage(baseDamage, party.get(partyIndex));
                            //enemyDamage = displayDamage;
                            enemies.get(enemyIndex).setDisplayDamage(displayDamage);
                            party.get(partyIndex).onDamageDealt(displayDamage);
                            incrementHits();
                            comboHolder++;
                            //damageHolder += enemyDamage;
                            damageHolder += displayDamage;
                            animation = party.get(partyIndex).getBattleAnimation();
                            break;
                        case COMBO_STRING:
                            baseDamage = party.get(partyIndex).ComboAttackDamage(enemies.get(enemyIndex));
                            displayDamage = enemies.get(enemyIndex).takePhysicalDamage(baseDamage, party.get(partyIndex));
                            //enemyDamage = displayDamage;
                            enemies.get(enemyIndex).setDisplayDamage(displayDamage);
                            party.get(partyIndex).onDamageDealt(displayDamage);
                            incrementHits();
                            hitsPerformed++;
                            comboHolder++;
                            //damageHolder += enemyDamage;
                            damageHolder += displayDamage;
                            animation = party.get(partyIndex).getBattleAnimation();
                            break;
                        case MAGIC_STRING:
                            baseDamage = party.get(partyIndex).MagicAttackDamage(enemies.get(enemyIndex));
                            displayDamage = enemies.get(enemyIndex).takeMagicalDamage(baseDamage, party.get(partyIndex));
                            //enemyDamage = displayDamage;
                            enemies.get(enemyIndex).setDisplayDamage(displayDamage);
                            party.get(partyIndex).onDamageDealt(displayDamage);
                            incrementHits();
                            comboHolder++;
                            //damageHolder += enemyDamage;
                            damageHolder += displayDamage;
                            animation = party.get(partyIndex).getAttackElement().getBattleAnimation();
                            break;
                        case HEAL_STRING:
                            for (int j = 0; j < party.size(); j++) {
                                if (party.get(j).getCurrentHP() > 0) {
                                    baseDamage = party.get(partyIndex).HealAmount(party.get(j));
                                    displayDamage = party.get(j).healDamage(baseDamage, party.get(partyIndex));
                                    //partyDamage[j] = HEAL_DAMAGE * displayDamage;
                                    party.get(j).setDisplayDamage(HEAL_DAMAGE * displayDamage);
                                }
                                else{
                                    //partyDamage[j] = 0;
                                    party.get(j).setDisplayDamage(0);
                                }
                            }
                            healingAnimation = AnimationAssets.HealAnimation;
                            break;
                        case SPECIAL_STRING:
                            baseDamage = party.get(partyIndex).SpecialAttackDamage(enemies.get(enemyIndex));
                            displayDamage = enemies.get(enemyIndex).takeSpecialDamage(baseDamage, party.get(partyIndex));
                            //enemyDamage = displayDamage;
                            enemies.get(enemyIndex).setDisplayDamage(displayDamage);
                            party.get(partyIndex).onDamageDealt(displayDamage);
                            incrementHits();
                            comboHolder++;
                            //damageHolder += enemyDamage;
                            damageHolder += displayDamage;
                            animation = party.get(partyIndex).getBattleAnimation();
                            break;
                        case TRANSFORM_STRING:
                            party.get(partyIndex).transform(game.getGraphics());
                            for(int i = 0; i < party.size(); i++){
                                party.get(i).resetSkills();
                            }
                            for(int i = 0; i < party.size(); i++){
                                party.get(i).updateParty(party);
                            }
                            break;
                        case DEFEND_STRING:
                            ((BattleWifey) party.get(partyIndex)).Defend();
                            //enemyDamage = 0;
                            enemies.get(enemyIndex).setDisplayDamage(0);
                            break;
                        default:
                    }
                    if(animation != null) {
                        battleAnimation = new Animation(animation, getPlayerPhaseWait(), false);
                        int multiplier = Math.random() > 0.5 ? 1 : -1;
                        battleAnimationOffsetX = (int) (multiplier * Math.random() * ENEMY_BATTLE_ANIMATION_MAX_OFFSET);
                        multiplier = Math.random() > 0.5 ? 1 : -1;
                        battleAnimationOffsetY = (int) (multiplier * Math.random() * ENEMY_BATTLE_ANIMATION_MAX_OFFSET);
                    }
                    if(healingAnimation != null){
                        healAnimation = new Animation(healingAnimation, getPlayerPhaseWait(), false);
                    }
                } else {
                    phaseTime += deltaTime;
                    if(battleAnimation != null) {
                        battleAnimation.update(deltaTime);
                    }
                    if(healAnimation != null) {
                        healAnimation.update(deltaTime);
                    }
                    if (phaseTime >= getPlayerPhaseWait()) {
                        if (enemies.get(enemyIndex).getCurrentHP() == 0 && enemies.get(enemyIndex).canPreventDeath()) {
                            currentPhase = BattlePhase.PREVENT_ENEMY_DEFEAT;
                            phaseEntered = true;
                        } else if (commandSelected == comboAttackButton && hitsPerformed < party.get(partyIndex).getNumHits()) {
                            currentPhase = BattlePhase.ANIMATE_PLAYER_ACTION;
                            phaseEntered = true;
                        } else if (enemies.get(enemyIndex).getCurrentHP() == 0) {
                            currentPhase = BattlePhase.WAVE_END;
                            phaseEntered = true;
                        } else if (isEndOfRound()) {
                            currentPhase = BattlePhase.WAIT_ENEMY_ACTION;
                            phaseEntered = true;
                        } else {
                            currentPhase = BattlePhase.WAIT_PLAYER_ACTION;
                            phaseEntered = true;
                        }
                    }
                }
                break;
            case PREVENT_ENEMY_DEFEAT:
                if(phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;
                    resetDamage();
                    clearAnimations();
                    //enemyDamage = HEAL_DAMAGE * enemies.get(enemyIndex).preventDeath();
                    enemies.get(enemyIndex).setDisplayDamage(HEAL_DAMAGE * enemies.get(enemyIndex).preventDeath());
                    healAnimation = new Animation(AnimationAssets.ReviveAnimation, WAIT_PHASE_WAIT, false);
                } else {
                    phaseTime += deltaTime;
                    healAnimation.update(deltaTime);
                    if(phaseTime >= WAIT_PHASE_WAIT) {
                        if (isEndOfRound()) {
                            currentPhase = BattlePhase.WAIT_ENEMY_ACTION;
                            phaseEntered = true;
                        } else {
                            currentPhase = BattlePhase.WAIT_PLAYER_ACTION;
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
                    resetDamage();
                    clearAnimations();
                    updateButtons();
                    enemies.get(enemyIndex).startTurn();
                    ((BattleEnemy) enemies.get(enemyIndex)).determineAction();
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
                    clearAnimations();
                    EnemyAI.EnemyAction action = ((BattleEnemy) enemies.get(enemyIndex)).getAction();
                    int charIndex;
                    int baseDamage;
                    int displayDamage;
                    AnimationImages animation = null;
                    AnimationImages healingAnimation = null;
                    switch(action){
                        case POWER_ATTACK:
                            charIndex = chooseRandomChar();
                            baseDamage = (int) (enemies.get(enemyIndex).PowerAttackDamage(party.get(charIndex)) * enemyMultiplier);
                            displayDamage = party.get(charIndex).takePhysicalDamage(baseDamage, enemies.get(enemyIndex));
                            for(int i = 0; i < party.size(); i++){
                                if(i == charIndex){
                                    //partyDamage[i] = displayDamage;
                                    party.get(i).setDisplayDamage(displayDamage);
                                }
                                else{
                                    //partyDamage[i] = 0;
                                    party.get(i).setDisplayDamage(0);
                                }
                            }
                            hitsPerformed++;
                            if(displayDamage > 0) {
                                enemies.get(enemyIndex).onDamageDealt(displayDamage);
                            }
                            if(party.get(charIndex).getCurrentHP() == 0){
                                enemies.get(enemyIndex).onEnemyDefeat(party.get(charIndex));
                            }
                            animation = enemies.get(enemyIndex).getBattleAnimation();
                            break;
                        case COMBO_ATTACK:
                            charIndex = chooseRandomChar();
                            baseDamage = (int) (enemies.get(enemyIndex).ComboAttackDamage(party.get(charIndex)) * enemyMultiplier);
                            displayDamage = party.get(charIndex).takePhysicalDamage(baseDamage, enemies.get(enemyIndex));
                            for(int i = 0; i < party.size(); i++){
                                if(i == charIndex){
                                    //partyDamage[i] = displayDamage;
                                    party.get(i).setDisplayDamage(displayDamage);
                                }
                                else{
                                    //partyDamage[i] = 0;
                                    party.get(i).setDisplayDamage(0);
                                }
                            }
                            hitsPerformed++;
                            if(displayDamage > 0) {
                                enemies.get(enemyIndex).onDamageDealt(displayDamage);
                            }
                            if(party.get(charIndex).getCurrentHP() == 0){
                                enemies.get(enemyIndex).onEnemyDefeat(party.get(charIndex));
                            }
                            animation = enemies.get(enemyIndex).getBattleAnimation();
                            break;
                        case MAGIC_ATTACK:
                            for(int i = 0; i < party.size(); i++){
                                if(party.get(i).getCurrentHP() > 0){
                                    baseDamage = (int) (enemies.get(enemyIndex).MagicAttackDamage(party.get(i)) * enemyMultiplier);
                                    displayDamage = party.get(i).takeMagicalDamage(baseDamage, enemies.get(enemyIndex));
                                    //partyDamage[i] = displayDamage;
                                    party.get(i).setDisplayDamage(displayDamage);
                                }
                                else{
                                    //partyDamage[i] = 0;
                                    party.get(i).setDisplayDamage(0);
                                }
                            }
                            hitsPerformed++;
                            for(int i = 0; i < party.size(); i++){
                                //if(partyDamage[i] > 0){
                                if(party.get(i).getDisplayDamage() > 0) {
                                    enemies.get(enemyIndex).onDamageDealt(party.get(i).getDisplayDamage());
                                }
                            }
                            for(int i = 0; i < party.size(); i++){
                                //if(partyDamage[i] > 0 && party.get(i).getCurrentHP() == 0){
                                if(party.get(i).getDisplayDamage() > 0 && party.get(i).getCurrentHP() == 0){
                                    enemies.get(enemyIndex).onEnemyDefeat(party.get(i));
                                }
                            }
                            animation = enemies.get(enemyIndex).getAttackElement().getBattleAnimation();
                            break;
                        case HEALING_MAGIC:
                            baseDamage = (int) (enemies.get(enemyIndex).HealAmount(enemies.get(enemyIndex)) * enemyMultiplier);
                            //enemyDamage = HEAL_DAMAGE * enemies.get(enemyIndex).healDamage(baseDamage, enemies.get(enemyIndex));
                            enemies.get(enemyIndex).setDisplayDamage(HEAL_DAMAGE * enemies.get(enemyIndex).healDamage(baseDamage, enemies.get(enemyIndex)));
                            hitsPerformed++;//Change to heal animation
                            healingAnimation = AnimationAssets.HealAnimation;
                            break;
                        case SPECIAL_ATTACK:
                            for(int i = 0; i < party.size(); i++){
                                if(party.get(i).getCurrentHP() > 0){
                                    baseDamage = (int) (enemies.get(enemyIndex).SpecialAttackDamage(party.get(i)) * enemyMultiplier);
                                    //partyDamage[i] = party.get(i).takeSpecialDamage(baseDamage, enemies.get(enemyIndex));
                                    party.get(i).setDisplayDamage(party.get(i).takeSpecialDamage(baseDamage, enemies.get(enemyIndex)));
                                }
                                else{
                                    //partyDamage[i] = 0;
                                    party.get(i).setDisplayDamage(0);
                                }
                            }
                            hitsPerformed++;
                            for(int i = 0; i < party.size(); i++){
                                //if(partyDamage[i] > 0){
                                if(party.get(i).getDisplayDamage() > 0) {
                                    //enemies.get(enemyIndex).onDamageDealt(partyDamage[i]);
                                    enemies.get(enemyIndex).onDamageDealt(party.get(i).getDisplayDamage());
                                }
                            }
                            for(int i = 0; i < party.size(); i++){
                                //if(partyDamage[i] > 0 && party.get(i).getCurrentHP() == 0){
                                if(party.get(i).getDisplayDamage() > 0 && party.get(i).getCurrentHP() == 0) {
                                    enemies.get(enemyIndex).onEnemyDefeat(party.get(i));
                                }
                            }
                            animation = enemies.get(enemyIndex).getBattleAnimation();
                            break;
                        case TRANSFORM:
                            enemies.get(enemyIndex).transform(game.getGraphics());
                            for(int i = 0; i < enemies.size(); i++){
                                enemies.get(i).resetSkills();
                            }
                            for(int i = 0; i < enemies.size(); i++){
                                enemies.get(i).updateParty(party);
                            }
                            hitsPerformed++;
                            break;
                        case POWER_UP:
                            ((BattleEnemy) enemies.get(enemyIndex)).powerUp();
                            hitsPerformed++;
                            break;
                        case POWER_DOWN:
                            ((BattleEnemy) enemies.get(enemyIndex)).powerDown();
                            hitsPerformed++;
                            break;
                        case DEFEND:
                            ((BattleEnemy) enemies.get(enemyIndex)).defend();
                            hitsPerformed++;
                            break;
                        case WEAKEN:
                            ((BattleEnemy) enemies.get(enemyIndex)).weaken();
                            hitsPerformed++;
                            break;
                        case WAIT:
                            hitsPerformed++;
                            break;
                        default:
                            hitsPerformed++;
                            //System.out.println("Not supported yet");
                            Log.e("AbsBattleScreen", "Invalid enemy battle action selection");
                            break;
                    }
                    if(animation != null) {
                        battleAnimation = new Animation(animation, getEnemyPhaseWait(), false);
                        int multiplier = Math.random() > 0.5 ? 1 : -1;
                        battleAnimationOffsetX = (int) (multiplier * Math.random() * CHAR_BATTLE_ANIMATION_MAX_OFFSET);
                        multiplier = Math.random() > 0.5 ? 1 : -1;
                        battleAnimationOffsetY = (int) (multiplier * Math.random() * CHAR_BATTLE_ANIMATION_MAX_OFFSET);
                    }
                    if(healingAnimation != null){
                        healAnimation = new Animation(healingAnimation, getEnemyPhaseWait(), false);
                    }
                } else {
                    phaseTime += deltaTime;
                    if(battleAnimation != null) {
                        battleAnimation.update(deltaTime);
                    }
                    if(healAnimation != null) {
                        healAnimation.update(deltaTime);
                    }
                    if (phaseTime >= getEnemyPhaseWait()) {
                        //If the party is dead or the enemy is done acting and somebody can be revived...
                        if ((isGameOver() || hitsPerformed == enemies.get(enemyIndex).getNumHits()) && canPreventPartyDeath()) {
                            currentPhase = BattlePhase.PREVENT_PLAYER_DEFEAT;
                            phaseEntered = true;
                        } else if (isGameOver()) {
                            currentPhase = BattlePhase.BATTLE_END;
                            phaseEntered = true;
                        } else if(hitsPerformed < enemies.get(enemyIndex).getNumHits()){
                            currentPhase = BattlePhase.ANIMATE_ENEMY_ACTION;
                            phaseEntered = true;
                        }  else{
                            currentPhase = BattlePhase.ROUND_END;
                            phaseEntered = true;
                        }
                    }
                }
                break;
            case PREVENT_PLAYER_DEFEAT:
                if(phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;
                    resetDamage();
                    clearAnimations();
                    for(int i = 0; i < party.size(); i++) {
                        if(party.get(i).getCurrentHP() == 0 && party.get(i).canPreventDeath()){
                            //partyDamage[i] = HEAL_DAMAGE * party.get(i).preventDeath();
                            party.get(i).setDisplayDamage(HEAL_DAMAGE * party.get(i).preventDeath());
                        }
                    }
                    healAnimation = new Animation(AnimationAssets.ReviveAnimation, HEAL_PHASE_WAIT, false);
                } else {
                    phaseTime += deltaTime;
                    healAnimation.update(deltaTime);
                    if(phaseTime >= HEAL_PHASE_WAIT) {
                        currentPhase = BattlePhase.ROUND_END;
                        phaseEntered = true;
                    }
                }
                break;
            case ROUND_END:
                //Perform end of round things
                //Go back to ROUND_START
                if (phaseEntered) {
                    phaseTime = 0;
                    phaseEntered = false;
                    for(int i = 0; i < party.size(); i++){
                        if(party.get(i).getCurrentHP() != 0){
                            party.get(i).endRound();
                        }
                    }
                    enemies.get(enemyIndex).endRound();
                    resetDamage();
                    clearAnimations();
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
                    resetDamage();
                    phaseEntered = false;
                } else {
                    phaseTime += deltaTime;
                    if (phaseTime >= WAVE_PHASE_WAIT) {
                        party.get(partyIndex).onEnemyDefeat(enemies.get(enemyIndex));
                        for(int i = 0; i < party.size(); i++){
                            party.get(i).endWave(enemies.get(enemyIndex));
                        }
                        enemyIndex++;
                        if (enemyIndex == enemies.size()) {
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
                    phaseEntered = false;
                    if(!isGameOver()){
                        //On Battle end, increment the number of completions in BattleInfo if victorious
                        battleInfo.incrementNumComplete();
                    }
                    //Here we'll unload the animations created for the battle
                    Weapon.unloadAllAnimations();
                    Element.unloadAllAnimations();
                    BattleAssets.unload();
                    game.setScreen(getCompletionScreen());
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
        if (currentPhase != BattlePhase.BATTLE_START) {
            drawEnemy();
        }
        drawSpecial();
        drawCombo();
        if(currentPhase == BattlePhase.ANIMATE_PLAYER_ACTION){
            drawPlayerCommand();
        }
        if(currentPhase == BattlePhase.ANIMATE_ENEMY_ACTION){
            drawEnemyCommand();
        }
        drawPartyAnimation();
        drawEnemyAnimation();
        drawPlayerDamage();
        drawEnemyDamage();
        if(currentPhase == BattlePhase.WAVE_START){
            drawWaveStart();
        }
    }

    private void drawBackground(){
        Graphics g = game.getGraphics();
        g.clearScreen(0xffffffff);
        g.drawImage(background, 0, 0);
        buttonList.drawImage(g);
    }

    private void drawParty(){
        Graphics g = game.getGraphics();

        //Workaround to prevent the one frame issue
        int index = partyIndex == -1 ? getFirstIndex() : partyIndex;

        for(int i = 0; i < party.size(); i++) {
            int charScale = (i == index) ? FULL_SCALE : HALF_SCALE;

            //Draw the character holder background
            int charHolderX = (i <= index) ? CHAR_HOLDER_X_DISTANCE * i : CHAR_HOLDER_X_DISTANCE * (i + 1);
            int charHolderY = (i == index) ? CHAR_HOLDER_LARGE_Y : CHAR_HOLDER_SMALL_Y;
            g.drawPercentageImage(BattleAssets.CharHolder, charHolderX, charHolderY, charScale, charScale);

            //Draw the character image
            int charImageX;
            if(i < index) {
                charImageX = CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_SMALL_X;
            }
            else if (i == index) {
                charImageX = CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_LARGE_X;
            }
            else {
                charImageX = CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_INTERIOR_SMALL_X;
            }
            int charImageY = (i == index) ? CHAR_IMAGE_LARGE_Y : CHAR_IMAGE_SMALL_Y;
            g.drawPercentageImage(party.get(i).getImage(), charImageX, charImageY, charScale, charScale);

            //Draw the elements
            int atkX, stgX, weakX;
            if(i < index) {
                atkX = CHAR_HOLDER_X_DISTANCE * i + CHAR_SMALL_BASE_ELEM_ATK_X;
                stgX = CHAR_HOLDER_X_DISTANCE * i + CHAR_SMALL_BASE_ELEM_RES_X;
                weakX = CHAR_HOLDER_X_DISTANCE * i + CHAR_SMALL_BASE_ELEM_WEAK_X;
            }
            else if(i == index) {
                atkX = CHAR_HOLDER_X_DISTANCE * i + CHAR_LARGE_BASE_ELEM_ATK_X;
                stgX = CHAR_HOLDER_X_DISTANCE * i + CHAR_LARGE_BASE_ELEM_RES_X;
                weakX = CHAR_HOLDER_X_DISTANCE * i + CHAR_LARGE_BASE_ELEM_WEAK_X;
            }
            else {
                atkX = CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_SMALL_BASE_ELEM_ATK_X;
                stgX = CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_SMALL_BASE_ELEM_RES_X;
                weakX = CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_SMALL_BASE_ELEM_WEAK_X;
            }
            int elemY = (i == index) ? CHAR_LARGE_ELEM_Y : CHAR_SMALL_ELEM_Y;
            int elemScale = (i == index) ? CHAR_LARGE_ELEM_SCALE : CHAR_SMALL_ELEM_SCALE;
            g.drawPercentageImage(party.get(i).getAttackElement().getElementImage(), atkX, elemY, elemScale, elemScale);
            g.drawPercentageImage(party.get(i).getStrongElement().getElementImage(), stgX, elemY, elemScale, elemScale);
            g.drawPercentageImage(party.get(i).getWeakElement().getElementImage(), weakX, elemY, elemScale, elemScale);

            //Draw the health bar
            Image healthBar = getPlayerHealthBar(party.get(i).getCurrentHP(), party.get(i).getMaxHP());
            Double perHealth = (party.get(i).getCurrentHP() * 1.0) / (party.get(i).getMaxHP());
            int healthX;
            if(i < index) {
                healthX = CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_SMALL_X;
            }
            else if(i == index) {
                healthX = CHAR_HOLDER_X_DISTANCE * i + CHAR_INTERIOR_LARGE_X;
            }
            else {
                healthX = CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_INTERIOR_SMALL_X;
            }
            int healthY = (i == index) ? CHAR_HEALTH_LARGE_Y : CHAR_HEALTH_SMALL_Y;
            g.drawPercentageImage(healthBar, healthX, healthY, (int) Math.ceil(perHealth * charScale), charScale);

            //Draw the CurrentHP / MaxHP
            int curX, slashX, maxX;
            if(i < index) {
                curX = CHAR_HOLDER_X_DISTANCE * i + CHAR_CUR_HP_SMALL_X;
                slashX = CHAR_HOLDER_X_DISTANCE * i + CHAR_HP_SLASH_SMALL_X;
                maxX = CHAR_HOLDER_X_DISTANCE * i + CHAR_MAX_HP_SMALL_X;
            }
            else if (i == index) {
                curX = CHAR_HOLDER_X_DISTANCE * i + CHAR_CUR_HP_LARGE_X;
                slashX = CHAR_HOLDER_X_DISTANCE * i + CHAR_HP_SLASH_LARGE_X;
                maxX = CHAR_HOLDER_X_DISTANCE * i + CHAR_MAX_HP_LARGE_X;
            }
            else {
                curX = CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_CUR_HP_SMALL_X;
                slashX = CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_HP_SLASH_SMALL_X;
                maxX = CHAR_HOLDER_X_DISTANCE * (i + 1) + CHAR_MAX_HP_SMALL_X;
            }
            int hpY = (i == index) ? CHAR_HP_LARGE_Y : CHAR_HP_SMALL_Y;
            int hpWidth = (i == index) ? CHAR_HP_LARGE_WIDTH : CHAR_HP_SMALL_WIDTH;
            int hpHeight = (i == index) ? CHAR_HP_LARGE_HEIGHT : CHAR_HP_SMALL_HEIGHT;
            int hpOffset = (i == index) ? CHAR_HP_LARGE_OFFSET : CHAR_HP_SMALL_OFFSET;
            NumberPrinter.drawNumber(g, party.get(i).getCurrentHP(), curX, hpY, hpWidth, hpHeight, hpOffset, Assets.WhiteNumbers, NumberPrinter.Align.RIGHT);
            g.drawPercentageImage(Assets.HPSlash, slashX, hpY, charScale, charScale);
            NumberPrinter.drawNumber(g, party.get(i).getMaxHP(), maxX, hpY, hpWidth, hpHeight, hpOffset, Assets.WhiteNumbers, NumberPrinter.Align.LEFT);

            //Draw the KO Overlay
            if(party.get(i).getCurrentHP() == 0) {
                int koX = (i <= index) ? CHAR_HOLDER_X_DISTANCE * i : CHAR_HOLDER_X_DISTANCE * (i + 1);
                int koY = (i == index) ? CHAR_HOLDER_KO_LARGE_Y : CHAR_HOLDER_KO_SMALL_Y;
                g.drawPercentageImage(BattleAssets.KOImages.get(i), koX, koY, charScale, charScale);
            }
        }
    }

    private void drawEnemy(){
        Graphics g = game.getGraphics();
        Double perHealth;

        if(currentPhase == AbsBattleScreen.BattlePhase.WAVE_START ){
            if(!phaseEntered) {
                g.drawImageAlpha(enemies.get(enemyIndex).getImage(), ENEMY_IMAGE_X, ENEMY_IMAGE_Y, (int) (255 * phaseTime) / WAVE_PHASE_WAIT);
            }
        }
        else if(currentPhase == BattlePhase.WAVE_END && !phaseEntered) {
            g.drawImageAlpha(enemies.get(enemyIndex).getImage(), ENEMY_IMAGE_X, ENEMY_IMAGE_Y, (int) (255 * (WAVE_PHASE_WAIT - phaseTime) / WAVE_PHASE_WAIT));
        }
        else if(currentPhase == BattlePhase.BATTLE_END){
            //Draw nothing
        }
        else {
            g.drawImage(enemies.get(enemyIndex).getImage(), ENEMY_IMAGE_X, ENEMY_IMAGE_Y);
        }
        g.drawImage(BattleAssets.EnemyHolder, ENEMY_HEALTH_HOLDER_X, ENEMY_HEALTH_HOLDER_Y);
        Image enemyHealth = getEnemyHealthBar(enemies.get(enemyIndex).getCurrentHP(), enemies.get(enemyIndex).getMaxHP());
        perHealth = (enemies.get(enemyIndex).getCurrentHP() * 1.0)/enemies.get(enemyIndex).getMaxHP();
        g.drawPercentageImage(enemyHealth, ENEMY_HEALTH_BAR_X, ENEMY_HEALTH_BAR_Y, (int) Math.ceil(FULL_SCALE * perHealth), FULL_SCALE);
        g.drawPercentageImage(enemies.get(enemyIndex).getAttackElement().getElementImage(), ENEMY_ELEM_ATK_X, ENEMY_ELEM_Y, ENEMY_ELEM_SCALE, ENEMY_ELEM_SCALE);
        g.drawPercentageImage(enemies.get(enemyIndex).getStrongElement().getElementImage(), ENEMY_ELEM_RES_X, ENEMY_ELEM_Y, ENEMY_ELEM_SCALE, ENEMY_ELEM_SCALE);
        g.drawPercentageImage(enemies.get(enemyIndex).getWeakElement().getElementImage(), ENEMY_ELEM_WEAK_X, ENEMY_ELEM_Y, ENEMY_ELEM_SCALE, ENEMY_ELEM_SCALE);

        //Display enemy CurrentHP/MaxHP
        NumberPrinter.drawNumber(g, enemies.get(enemyIndex).getCurrentHP(), ENEMY_CUR_HP_X, ENEMY_HP_Y, ENEMY_HP_WIDTH, ENEMY_HP_HEIGHT, ENEMY_HP_OFFSET, Assets.WhiteNumbers, NumberPrinter.Align.RIGHT);
        g.drawImage(Assets.HPSlash, ENEMY_HP_SLASH_X, ENEMY_HP_Y);
        NumberPrinter.drawNumber(g, enemies.get(enemyIndex).getMaxHP(), ENEMY_MAX_HP_X, ENEMY_HP_Y, ENEMY_HP_WIDTH, ENEMY_HP_HEIGHT, ENEMY_HP_OFFSET, Assets.WhiteNumbers, NumberPrinter.Align.LEFT);

        //Display Enemy Status changes
        BattleEnemy enemy = (BattleEnemy) enemies.get(enemyIndex);
        boolean defense = enemy.isDefendActive() || enemy.isWeakenActive();
        if(enemy.isDefendActive()) {
            g.drawImage(BattleAssets.DefenseUp, ENEMY_STATUS_X, ENEMY_STATUS_Y_1);
        }
        else if(enemy.isWeakenActive()) {
            g.drawImage(BattleAssets.DefenseDown, ENEMY_STATUS_X, ENEMY_STATUS_Y_1);
        }
        int atkY = defense ? ENEMY_STATUS_Y_2 : ENEMY_STATUS_Y_1;
        if(enemy.isPowerUpActive()) {
            g.drawImage(BattleAssets.AttackUp, ENEMY_STATUS_X, atkY);
        }
        else if(enemy.isPowerDownActive()) {
            g.drawImage(BattleAssets.AttackDown, ENEMY_STATUS_X, atkY);
        }
    }

    private void drawSpecial(){
        Graphics g = game.getGraphics();
        g.drawImage(BattleAssets.SpecialBarBase, SPECIAL_BAR_BASE_X, SPECIAL_BAR_BASE_Y);
        g.drawPercentageImage(BattleAssets.SpecialBar, SPECIAL_BAR_X, SPECIAL_BAR_Y, (int) ((numHits * 100.0) / MAX_HITS) , FULL_SCALE);
        g.drawImage(BattleAssets.SpecialBarTop, SPECIAL_BAR_X, SPECIAL_BAR_TOP_Y);
        int specialCount = numHits / SPECIAL_HITS;
        NumberPrinter.drawNumber(g, specialCount, SPECIAL_BAR_NUMBER_X, SPECIAL_BAR_NUMBER_Y, SPECIAL_NUMBER_WIDTH, SPECIAL_NUMBER_HEIGHT, SPECIAL_NUMBER_OFFSET, Assets.WhiteNumbers, NumberPrinter.Align.LEFT);
    }

    private void drawCombo(){
        Graphics g = game.getGraphics();
        if(comboHolder > 0){
            NumberPrinter.drawNumber(g, comboHolder, COMBO_NUMBER_X, COMBO_NUMBER_Y, COMBO_NUMBER_WIDTH, COMBO_NUMBER_HEIGHT, COMBO_NUMBER_OFFSET, Assets.YellowNumbers, NumberPrinter.Align.RIGHT);
            if(comboHolder > 1) {
                g.drawImage(BattleAssets.HitsText, COMBO_TEXT_X, COMBO_TEXT_Y);
            }
            else{
                g.drawImage(BattleAssets.HitText, COMBO_TEXT_X, COMBO_TEXT_Y);
            }
        }
        //Damage Counter
        if(damageHolder > 0){
            NumberPrinter.drawNumber(g, damageHolder, DAMAGE_HOLDER_NUMBER_X, DAMAGE_HOLDER_NUMBER_Y, DAMAGE_HOLDER_WIDTH, DAMAGE_HOLDER_HEIGHT, DAMAGE_HOLDER_OFFSET, Assets.RedNumbers, NumberPrinter.Align.RIGHT);
            g.drawImage(BattleAssets.DamageText, DAMAGE_TEXT_X, DAMAGE_TEXT_Y);
        }
    }

    private void drawPlayerCommand() {
        Graphics g = game.getGraphics();
        g.drawImage(BattleAssets.AttackBox, 0, 0);
        g.drawString(commandSelected.getName(), 400, 70, textPaint);
    }

    private void drawEnemyCommand() {
        Graphics g = game.getGraphics();
        g.drawImage(BattleAssets.AttackBox, 0, 0);
        g.drawString(((BattleEnemy) enemies.get(enemyIndex)).getActionString(), 400, 70, textPaint);
    }

    private void drawPlayerDamage() {
        Graphics g = game.getGraphics();

        //Workaround to prevent the one frame issue
        int index = partyIndex == -1 ? getFirstIndex() : partyIndex;

        for(int i = 0; i < party.size(); i++) {
            //if(partyDamage[i] == 0) {
            if(party.get(i).getDisplayDamage() == 0) {
                continue;
            }
            int x;
            int y;
            if(i < index) {
                x = CHAR_DAMAGE_BASE_X + CHAR_HOLDER_X_DISTANCE * i;
            }
            else if(i == index){
                x = (CHAR_DAMAGE_BASE_X * 2) + CHAR_HOLDER_X_DISTANCE * i;
            }
            else{
                x = CHAR_DAMAGE_BASE_X + CHAR_HOLDER_X_DISTANCE * (i + 1);
            }
            List<Image> numbers;
            //if(partyDamage[i] > 0) {
            if(party.get(i).getDisplayDamage() > 0) {
                if(i == index){
                    y = CHAR_DAMAGE_LARGE_START_Y - (int) (CHAR_DAMAGE_LARGE_INCREASE_Y * phaseTime / ATTACK_PHASE_WAIT);
                }
                else {
                    y = CHAR_DAMAGE_SMALL_START_Y - (int) (CHAR_DAMAGE_SMALL_INCREASE_Y * phaseTime / ATTACK_PHASE_WAIT);
                }
                if(enemies.get(enemyIndex).isWeaknessAttack(party.get(i))){
                    numbers = Assets.RedNumbers;
                }
                else if(enemies.get(enemyIndex).isStrongAttack(party.get(i))){
                    numbers = Assets.GreyNumbers;
                }
                else{
                    numbers = Assets.WhiteNumbers;
                }
            }
            else /*partyDamage[i] < 0 */ {
                if(i == index){
                    y = CHAR_DAMAGE_LARGE_START_Y - (int) (CHAR_DAMAGE_LARGE_INCREASE_Y * phaseTime / HEAL_PHASE_WAIT);
                }
                else {
                    y = CHAR_DAMAGE_SMALL_START_Y - (int) (CHAR_DAMAGE_SMALL_INCREASE_Y * phaseTime / HEAL_PHASE_WAIT);
                }
                numbers = Assets.GreenNumbers;
            }

            NumberPrinter.drawNumber(g, Math.abs(party.get(i).getDisplayDamage()), x, y, PLAYER_DAMAGE_WIDTH, PLAYER_DAMAGE_HEIGHT, DAMAGE_OFFSET, numbers, NumberPrinter.Align.CENTER);
        }
    }

    //This function draws the battleAnimation on party members being hit
    private void drawPartyAnimation() {
        Graphics g = game.getGraphics();
        if(battleAnimation != null) {
            for (int i = 0; i < party.size(); i++) {
                //if (partyDamage[i] > 0) {
                if(party.get(i).getDisplayDamage() > 0) {
                    int x = CHAR_INTERIOR_SMALL_X + CHAR_BATTLE_ANIMATION_BASE_OFFSET_X + (CHAR_HOLDER_X_DISTANCE * i) + battleAnimationOffsetX;
                    int y = CHAR_IMAGE_SMALL_Y + CHAR_BATTLE_ANIMATION_BASE_OFFSET_Y + battleAnimationOffsetY;
                    //g.drawScaledImage(battleAnimation.getFrame(), x, y, CHAR_BATTLE_ANIMATION_SIZE, CHAR_BATTLE_ANIMATION_SIZE);
                    g.drawImage(battleAnimation.getFrame(), x, y);
                }
            }
        }
        if(healAnimation != null){
            //Workaround to prevent the one frame issue
            int index = partyIndex == -1 ? getFirstIndex() : partyIndex;

            for (int i = 0; i < party.size(); i++) {
                //if (partyDamage[i] < 0) {
                if(party.get(i).getDisplayDamage() < 0) {
                    int x;
                    int y;
                    if(i < index) {
                        x = CHAR_INTERIOR_SMALL_X + CHAR_BATTLE_ANIMATION_BASE_OFFSET_X + (CHAR_HOLDER_X_DISTANCE * i);
                    }
                    else if(i == index){
                        x = CHAR_INTERIOR_LARGE_X + CHAR_BATTLE_ANIMATION_BASE_OFFSET_X + (CHAR_HOLDER_X_DISTANCE * i);
                    }
                    else{
                        x = CHAR_INTERIOR_SMALL_X + CHAR_BATTLE_ANIMATION_BASE_OFFSET_X + (CHAR_HOLDER_X_DISTANCE * (i + 1));
                    }
                    if(i == index){
                        y = CHAR_IMAGE_LARGE_Y + CHAR_BATTLE_ANIMATION_BASE_OFFSET_Y;
                    }
                    else {
                        y = CHAR_IMAGE_SMALL_Y + CHAR_BATTLE_ANIMATION_BASE_OFFSET_Y;
                    }
                    //g.drawScaledImage(battleAnimation.getFrame(), x, y, CHAR_BATTLE_ANIMATION_SIZE, CHAR_BATTLE_ANIMATION_SIZE);
                    if(i != index){
                        g.drawImage(healAnimation.getFrame(), x, y);
                    }
                    else {
                        g.drawScaledImage(healAnimation.getFrame(), x, y, CHAR_BATTLE_LARGE_ANIMATION_SIZE, CHAR_BATTLE_LARGE_ANIMATION_SIZE);
                    }
                }
            }
        }
    }

    private void drawEnemyDamage() {
        Graphics g = game.getGraphics();
        //if(enemyDamage == 0) {
        if(enemies.get(enemyIndex).getDisplayDamage() == 0) {
            return;
        }
        int y;
        List<Image> numbers;
        //if(enemyDamage > 0) {
        if(enemies.get(enemyIndex).getDisplayDamage() > 0) {
            y = ENEMY_DAMAGE_START_Y - (int) (ENEMY_DAMAGE_INCREASE_Y * phaseTime / ATTACK_PHASE_WAIT);
            if(party.get(partyIndex).isWeaknessAttack(enemies.get(enemyIndex))){
                numbers = Assets.RedNumbers;
            }
            else if(party.get(partyIndex).isStrongAttack(enemies.get(enemyIndex))){
                numbers = Assets.GreyNumbers;
            }
            else{
                numbers = Assets.WhiteNumbers;
            }
        }
        else /*enemyDamage < 0 */ {
            y = ENEMY_DAMAGE_START_Y - (int) (ENEMY_DAMAGE_INCREASE_Y * phaseTime / HEAL_PHASE_WAIT);
            numbers = Assets.GreenNumbers;
        }

        NumberPrinter.drawNumber(g, Math.abs(enemies.get(enemyIndex).getDisplayDamage()), ENEMY_DAMAGE_BASE_X, y, ENEMY_DAMAGE_WIDTH, ENEMY_DAMAGE_HEIGHT, DAMAGE_OFFSET, numbers, NumberPrinter.Align.CENTER);
    }

    //This function draws the battleAnimation on enemy being hit
    private void drawEnemyAnimation(){
        Graphics g = game.getGraphics();
        //if(battleAnimation != null && enemyDamage > 0){
        if(battleAnimation != null && enemies.get(enemyIndex).getDisplayDamage() > 0) {
            int x = ENEMY_IMAGE_X + ENEMY_BATTLE_ANIMATION_BASE_OFFSET_X + battleAnimationOffsetX;
            int y = ENEMY_IMAGE_Y + ENEMY_BATTLE_ANIMATION_BASE_OFFSET_Y + battleAnimationOffsetY;
            g.drawScaledImage(battleAnimation.getFrame(), x, y, ENEMY_BATTLE_ANIMATION_SIZE, ENEMY_BATTLE_ANIMATION_SIZE);
        }
        //if(healAnimation != null && enemyDamage < 0){
        if(healAnimation != null && enemies.get(enemyIndex).getDisplayDamage() < 0) {
            int x = ENEMY_IMAGE_X + ENEMY_BATTLE_ANIMATION_BASE_OFFSET_X;
            int y = ENEMY_IMAGE_Y + ENEMY_BATTLE_ANIMATION_BASE_OFFSET_Y;
            g.drawScaledImage(healAnimation.getFrame(), x, y, ENEMY_BATTLE_ANIMATION_SIZE, ENEMY_BATTLE_ANIMATION_SIZE);
        }
    }

    private void drawWaveStart() {
        Graphics g = game.getGraphics();
        if(enemyIndex == enemies.size() - 1){
            g.drawImage(BattleAssets.FinalWaveText, WAVE_FINAL_TEXT_X, WAVE_TEXT_Y);
        }
        else{
            if(enemyIndex + 1 >= 10) {
                g.drawImage(BattleAssets.WaveText, WAVE_TEXT_LARGE_X, WAVE_TEXT_Y);
                NumberPrinter.drawNumber(g, enemyIndex + 1, WAVE_NUMBER_LARGE_X, WAVE_TEXT_Y, WAVE_WIDTH, WAVE_HEIGHT, 0, Assets.YellowNumbers, NumberPrinter.Align.LEFT);
            }
            else {
                g.drawImage(BattleAssets.WaveText, WAVE_TEXT_SMALL_X, WAVE_TEXT_Y);
                NumberPrinter.drawNumber(g, enemyIndex + 1, WAVE_NUMBER_SMALL_X, WAVE_TEXT_Y, WAVE_WIDTH, WAVE_HEIGHT, 0, Assets.YellowNumbers, NumberPrinter.Align.LEFT);
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
