package ultrapoulet.wifeygame;

import java.util.List;

import ultrapoulet.androidgame.framework.Image;

/**
 * Created by John on 3/12/2016.
 */
public class Assets {

    //BattleScreen Stuff
    public static Image PowerAttackEnabled, PowerAttackDisabled;
    public static Image ComboAttackEnabled, ComboAttackDisabled;
    public static Image MagicAttackEnabled, MagicAttackDisabled;
    public static Image HealMagicEnabled, HealMagicDisabled;
    public static Image SpecialAttackEnabled, SpecialAttackDisabled;
    public static Image TransformEnabled, TransformDisabled;
    public static Image DefendEnabled, DefendDisabled;
    public static Image attackBox;
    public static Image charHolder;
    public static Image pHealthG, pHealthY, pHealthR;
    public static Image eHealthG, eHealthY, eHealthR;
    public static Image enemyHolder;
    public static Image specialBar, specialBarBase, specialBarTop;
    public static Image hitsText, hitText, damageText;
    public static Image AttackUp, AttackDown;
    public static Image DefenseUp, DefenseDown;
    public static Image WaveText, FinalWaveText;

    public static List<Image> KOImages;
    public static Image HPSlash;
    public static List<Image> WhiteNumbers;
    public static List<Image> GreenNumbers;
    public static List<Image> YellowNumbers;
    public static List<Image> RedNumbers;
    public static List<Image> GreyNumbers;

    //PartySelectScreen Stuff
    public static Image PartySelectScreen;
    public static Image AcceptEnable, AcceptDisable;
    public static Image NextPageEnable, NextPageDisable;
    public static Image PrevPageEnable, PrevPageDisable;
    public static Image LockSelection;
    public static Image InvalidChar;
    public static Image RequiredCharHolder;

    //CharacterInfoScreen Stuff
    public static Image CharacterInfoScreen;
    public static Image BattleCharacterInfoScreen;
    public static Image ScrollBarFull;
    public static Image ScrollBarShort;
    public static Image TransformNextEnable, TransformNextDisable;
    public static Image TransformPrevEnable, TransformPrevDisable;
    public static Image TransformHolder;

    //BattleInfoScreen Stuff
    public static Image BattleInfoScreen;
    public static Image BattleEnable, BattleDisable;

    //Element Image stuff
    public static List<Image> ElementImages;
    public static final int AIR = 0;
    public static final int DARK = 1;
    public static final int EARTH = 2;
    public static final int FIRE = 3;
    public static final int LIGHT = 4;
    public static final int WATER = 5;

}
