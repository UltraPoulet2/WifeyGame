package ultrapoulet.wifeygame;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;

/**
 * Created by John on 3/12/2016.
 */
public class Assets {

    //StandardBattleScreen Stuff
    public static Image PowerAttackEnabled, PowerAttackDisabled;
    public static Image ComboAttackEnabled, ComboAttackDisabled;
    public static Image MagicAttackEnabled, MagicAttackDisabled;
    public static Image HealMagicEnabled, HealMagicDisabled;
    public static Image SpecialAttackEnabled, SpecialAttackDisabled;
    public static Image TransformEnabled, TransformDisabled;
    public static Image DefendEnabled, DefendDisabled;

    public static Image SmallGreenBar, SmallYellowBar, SmallRedBar;

    public static Image HPSlash;
    public static Image Colon;
    public static Image YellowPlus;
    public static Image BluePlus;
    public static List<Image> WhiteNumbers;
    public static List<Image> GreenNumbers;
    public static List<Image> YellowNumbers;
    public static List<Image> RedNumbers;
    public static List<Image> GreyNumbers;
    public static List<Image> BlueNumbers;

    //BattleSelectScreen stuff
    public static Image BattleSelectScreen;
    public static Image BattleDivider;
    public static Image PartyButton;
    public static Image UpgradeButton;
    public static Image InfoButton;
    public static Image StoryButtonActive, StoryButtonInactive;
    public static Image RecruitButtonActive, RecruitButtonInactive;
    public static Image SpecialButtonActive, SpecialButtonInactive;
    public static Image StoryBattleEnabled, StoryBattleSelected;
    public static Image BattleSelectPageUpEnabled, BattleSelectPageUpDisabled;
    public static Image BattleSelectPageDownEnabled, BattleSelectPageDownDisabled;
    public static Image EnergyImage;
    public static Image NewBattleIndicator, CompletedBattleIndicator;
    public static Image RecruitBattleButton;
    public static Image RecruitPageUpEnabled, RecruitPageUpDisabled;
    public static Image RecruitPageDownEnabled, RecruitPageDownDisabled;

    //PartyInfo Holder Stuff
    public static Image StatusHolder;
    public static Image Hourglass;
    public static Image NextLevel;
    public static Image NextLevelDialogLeft, NextLevelDialogCenter, NextLevelDialogRight;

    //PartySelectScreen Stuff
    public static Image PartySelectScreen;
    public static Image AcceptEnable, AcceptDisable;
    public static Image NextPageEnable, NextPageDisable;
    public static Image PrevPageEnable, PrevPageDisable;
    public static Image LockSelection;
    public static Image InvalidChar;
    public static Image RequiredCharHolder;
    public static Image DropdownMenuTop;
    public static Image DropdownMenuOption;

    //CharacterInfoScreen Stuff
    public static Image CharacterInfoScreen;
    public static Image BattleCharacterInfoScreen;
    public static Image ScrollBarFull;
    public static Image ScrollBarShort;
    public static Image TransformNextEnable, TransformNextDisable;
    public static Image TransformPrevEnable, TransformPrevDisable;
    public static Image TransformHolder;
    public static Image Favorite;
    public static List<Image> NumberHits;
    public static Image WeaponTypeLeft, WeaponTypeCenter, WeaponTypeRight;

    //EnemyInfoScreen
    public static Image EnemyInfoScreenTop;
    public static Image EnemyInfoScreenMid;
    public static Image EnemyInfoScreenBot;

    //BattleInfoScreen Stuff
    public static Image BattleInfoScreen;
    public static Image BattleEnable, BattleDisable;

    //RecruitingBattleInfoScreen Stuff
    public static Image RecruitBattleInfoScreen;

    //BattleResultScreen Stuff
    public static Image BattleResultScreen;
    public static Image BattleResultVictory;
    public static Image BattleResultDefeat;
    public static Image BattleResultExp;
    public static Image BattleResultGold;
    public static Image LevelUp;
    public static Image BattleResultCharHolder;

    //RecruitingScreen stuff
    public static Image RecruitingScreen;
    public static Image RecruitingButtonEnable, RecruitingButtonDisable;
    public static Image CheckboxComplete, CheckboxIncomplete;

    //Dialog Stuff
    public static Image AbsAdjustableDialogTop;
    public static Image AbsAdjustableDialogMid;
    public static Image AbsAdjustableDialogBot;
    public static Image DialogBackground;
    public static Image OptionYes;
    public static Image OptionNo;
    public static Image OptionOk;

    //Element Image stuff
    public static List<Image> ElementImages;
    public static final int AIR = 0;
    public static final int DARK = 1;
    public static final int EARTH = 2;
    public static final int FIRE = 3;
    public static final int LIGHT = 4;
    public static final int WATER = 5;

    public static void load(Graphics g) {
        PowerAttackEnabled = g.newImage("buttons/PowerAttackEnabled.png", ImageFormat.RGB565);
        PowerAttackDisabled = g.newImage("buttons/PowerAttackDisabled.png", ImageFormat.RGB565);
        ComboAttackEnabled = g.newImage("buttons/ComboAttackEnabled.png", ImageFormat.RGB565);
        ComboAttackDisabled = g.newImage("buttons/ComboAttackDisabled.png", ImageFormat.RGB565);
        MagicAttackEnabled = g.newImage("buttons/MagicAttackEnabled.png", ImageFormat.RGB565);
        MagicAttackDisabled = g.newImage("buttons/MagicAttackDisabled.png", ImageFormat.RGB565);
        HealMagicEnabled = g.newImage("buttons/HealMagicEnabled.png", ImageFormat.RGB565);
        HealMagicDisabled = g.newImage("buttons/HealMagicDisabled.png", ImageFormat.RGB565);
        SpecialAttackEnabled = g.newImage("buttons/SpecialAttackEnabled.png", ImageFormat.RGB565);
        SpecialAttackDisabled = g.newImage("buttons/SpecialAttackDisabled.png", ImageFormat.RGB565);
        TransformEnabled = g.newImage("buttons/TransformEnabled.png", ImageFormat.RGB565);
        TransformDisabled = g.newImage("buttons/TransformDisabled.png", ImageFormat.RGB565);
        DefendEnabled = g.newImage("buttons/DefendButtonEnabled.png", ImageFormat.RGB565);
        DefendDisabled = g.newImage("buttons/DefendButtonDisabled.png", ImageFormat.RGB565);

        SmallGreenBar = g.newImage("objects/bars/HealthBarGreen.png", ImageFormat.RGB565);
        SmallYellowBar = g.newImage("objects/bars/HealthBarYellow.png", ImageFormat.RGB565);
        SmallRedBar = g.newImage("objects/bars/HealthBarRed.png", ImageFormat.RGB565);

        WhiteNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            WhiteNumbers.add(g.newImage("numbers/white/" + i + ".png", ImageFormat.ARGB8888));
        }
        HPSlash = g.newImage("numbers/HPSlash.png", ImageFormat.ARGB8888);
        Colon = g.newImage("numbers/Colon.png", ImageFormat.ARGB8888);
        YellowPlus = g.newImage("numbers/yellow/Plus.png", ImageFormat.ARGB8888);
        BluePlus = g.newImage("numbers/blue/Plus.png", ImageFormat.ARGB8888);

        GreenNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            GreenNumbers.add(g.newImage("numbers/green/" + i + ".png", ImageFormat.ARGB8888));
        }

        YellowNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            YellowNumbers.add(g.newImage("numbers/yellow/" + i + ".png", ImageFormat.ARGB8888));
        }

        RedNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            RedNumbers.add(g.newImage("numbers/red/" + i + ".png", ImageFormat.ARGB8888));
        }

        GreyNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            GreyNumbers.add(g.newImage("numbers/grey/" + i + ".png", ImageFormat.ARGB8888));
        }

        BlueNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            BlueNumbers.add(g.newImage("numbers/blue/" + i + ".png", ImageFormat.ARGB8888));
        }

        StatusHolder = g.newImage("objects/status/StatusHolder.png", ImageFormat.ARGB8888);
        Hourglass = g.newImage("objects/status/Hourglass.png", ImageFormat.ARGB8888);
        NextLevel = g.newImage("objects/status/NextLevel.png", ImageFormat.ARGB8888);
        NextLevelDialogLeft = g.newImage("objects/status/NextLevelDialogLeft.png", ImageFormat.ARGB8888);
        NextLevelDialogCenter = g.newImage("objects/status/NextLevelDialogCenter.png", ImageFormat.ARGB8888);
        NextLevelDialogRight = g.newImage("objects/status/NextLevelDialogRight.png", ImageFormat.ARGB8888);

        //BattleSelectScreen stuff
        BattleSelectScreen = g.newImage("BattleSelect/BattleSelectScreen.png", ImageFormat.ARGB8888);
        BattleDivider = g.newImage("BattleSelect/BattleDivider.png", ImageFormat.ARGB8888);
        PartyButton = g.newImage("BattleSelect/PartyButton.png", ImageFormat.ARGB8888);
        UpgradeButton = g.newImage("BattleSelect/UpgradeButton.png", ImageFormat.ARGB8888);
        InfoButton = g.newImage("BattleSelect/InfoButton.png", ImageFormat.ARGB8888);
        StoryButtonActive = g.newImage("BattleSelect/StoryButtonActive.png", ImageFormat.ARGB8888);
        StoryButtonInactive = g.newImage("BattleSelect/StoryButtonInactive.png", ImageFormat.ARGB8888);
        RecruitButtonActive = g.newImage("BattleSelect/RecruitButtonActive.png", ImageFormat.ARGB8888);
        RecruitButtonInactive = g.newImage("BattleSelect/RecruitButtonInactive.png", ImageFormat.ARGB8888);
        SpecialButtonActive = g.newImage("BattleSelect/SpecialButtonActive.png", ImageFormat.ARGB8888);
        SpecialButtonInactive = g.newImage("BattleSelect/SpecialButtonInactive.png", ImageFormat.ARGB8888);
        StoryBattleEnabled = g.newImage("BattleSelect/StoryBattleButtonEnabled.png", ImageFormat.ARGB8888);
        StoryBattleSelected = g.newImage("BattleSelect/StoryBattleButtonSelected.png", ImageFormat.ARGB8888);
        BattleSelectPageUpEnabled = g.newImage("BattleSelect/BattleSelectPageUpEnabled.png", ImageFormat.ARGB8888);
        BattleSelectPageUpDisabled = g.newImage("BattleSelect/BattleSelectPageUpDisabled.png", ImageFormat.ARGB8888);
        BattleSelectPageDownEnabled = g.newImage("BattleSelect/BattleSelectPageDownEnabled.png", ImageFormat.ARGB8888);
        BattleSelectPageDownDisabled = g.newImage("BattleSelect/BattleSelectPageDownDisabled.png", ImageFormat.ARGB8888);
        EnergyImage = g.newImage("BattleSelect/EnergyImage.png", ImageFormat.ARGB8888);
        NewBattleIndicator = g.newImage("BattleSelect/NewBattleIndicator.png", ImageFormat.ARGB8888);
        CompletedBattleIndicator = g.newImage("BattleSelect/CompletedBattleIndicator.png", ImageFormat.ARGB8888);
        RecruitBattleButton = g.newImage("BattleSelect/RecruitBattleButton.png", ImageFormat.ARGB8888);
        RecruitPageUpEnabled = g.newImage("BattleSelect/RecruitPageUpEnabled.png", ImageFormat.ARGB8888);
        RecruitPageUpDisabled = g.newImage("BattleSelect/RecruitPageUpDisabled.png", ImageFormat.ARGB8888);
        RecruitPageDownEnabled = g.newImage("BattleSelect/RecruitPageDownEnabled.png", ImageFormat.ARGB8888);
        RecruitPageDownDisabled = g.newImage("BattleSelect/RecruitPageDownDisabled.png", ImageFormat.ARGB8888);

        PartySelectScreen = g.newImage("screens/PartySelectScreen.png", ImageFormat.RGB565);
        AcceptEnable = g.newImage("buttons/AcceptEnabled.png", ImageFormat.ARGB8888);
        AcceptDisable = g.newImage("buttons/AcceptDisabled.png", ImageFormat.ARGB8888);
        NextPageEnable = g.newImage("buttons/NextPageEnabled.png", ImageFormat.ARGB8888);
        NextPageDisable = g.newImage("buttons/NextPageDisabled.png", ImageFormat.ARGB8888);
        PrevPageEnable = g.newImage("buttons/PrevPageEnabled.png", ImageFormat.ARGB8888);
        PrevPageDisable = g.newImage("buttons/PrevPageDisabled.png", ImageFormat.ARGB8888);
        LockSelection = g.newImage("objects/holders/LockSelection.png", ImageFormat.ARGB8888);
        BattleEnable = g.newImage("buttons/BattleEnabled.png", ImageFormat.ARGB8888);
        BattleDisable = g.newImage("buttons/BattleDisabled.png", ImageFormat.ARGB8888);
        ScrollBarFull = g.newImage("buttons/ScrollBarFull.png", ImageFormat.ARGB8888);
        ScrollBarShort = g.newImage("buttons/ScrollBarShort.png", ImageFormat.ARGB8888);
        TransformNextEnable = g.newImage("buttons/TransformNextPageEnabled.png", ImageFormat.ARGB8888);
        TransformNextDisable = g.newImage("buttons/TransformNextPageDisabled.png", ImageFormat.ARGB8888);
        TransformPrevEnable = g.newImage("buttons/TransformPrevPageEnabled.png", ImageFormat.ARGB8888);
        TransformPrevDisable = g.newImage("buttons/TransformPrevPageDisabled.png", ImageFormat.ARGB8888);
        TransformHolder = g.newImage("objects/holders/TransformationHolder.png", ImageFormat.ARGB8888);
        DropdownMenuTop = g.newImage("buttons/DropdownMenuTop.png", ImageFormat.ARGB8888);
        DropdownMenuOption = g.newImage("buttons/DropdownMenuOption.png", ImageFormat.ARGB8888);
        Favorite = g.newImage("buttons/FavoriteButton.png", ImageFormat.ARGB8888);

        NumberHits = new ArrayList<>();
        for(int i = 2; i <= 10; i++){
            NumberHits.add(g.newImage("objects/numberHits/" + i + "hits.png", ImageFormat.RGB565));
        }

        WeaponTypeLeft = g.newImage("CharacterInfo/WeaponTypeLeft.png", ImageFormat.ARGB8888);
        WeaponTypeCenter = g.newImage("CharacterInfo/WeaponTypeCenter.png", ImageFormat.ARGB8888);
        WeaponTypeRight = g.newImage("CharacterInfo/WeaponTypeRight.png", ImageFormat.ARGB8888);

        InvalidChar = g.newImage("objects/holders/InvalidChar.png", ImageFormat.ARGB8888);
        RequiredCharHolder = g.newImage("objects/holders/RequiredCharHolder.png", ImageFormat.ARGB8888);

        CharacterInfoScreen = g.newImage("screens/CharacterInfoScreen.png", ImageFormat.RGB565);
        BattleCharacterInfoScreen = g.newImage("screens/BattleCharacterInfoScreen.png", ImageFormat.RGB565);
        BattleInfoScreen = g.newImage("screens/BattleInfoScreen.png", ImageFormat.RGB565);
        RecruitBattleInfoScreen = g.newImage("screens/RecruitBattleInfoScreen.png", ImageFormat.RGB565);
        EnemyInfoScreenTop = g.newImage("screens/EnemyInfoScreenTop.png", ImageFormat.RGB565);
        EnemyInfoScreenMid = g.newImage("screens/EnemyInfoScreenMid.png", ImageFormat.RGB565);
        EnemyInfoScreenBot = g.newImage("screens/EnemyInfoScreenBot.png", ImageFormat.RGB565);

        BattleResultScreen = g.newImage("screens/BattleResultScreen.png", ImageFormat.ARGB8888);
        BattleResultVictory = g.newImage("objects/battleResult/BattleResultVictory.png", ImageFormat.ARGB8888);
        BattleResultDefeat = g.newImage("objects/battleResult/BattleResultDefeat.png", ImageFormat.ARGB8888);
        BattleResultExp = g.newImage("objects/battleResult/BattleResultExp.png", ImageFormat.ARGB8888);
        BattleResultGold = g.newImage("objects/battleResult/BattleResultGold.png", ImageFormat.ARGB8888);
        LevelUp = g.newImage("BattleResult/LevelUpImage.png", ImageFormat.ARGB8888);
        BattleResultCharHolder = g.newImage("BattleResult/BattleResultCharHolder.png", ImageFormat.ARGB8888);

        RecruitingScreen = g.newImage("screens/RecruitingScreen.png", ImageFormat.ARGB8888);
        RecruitingButtonEnable = g.newImage("Recruiting/RecruitButtonEnabled.png", ImageFormat.ARGB8888);
        RecruitingButtonDisable = g.newImage("Recruiting/RecruitButtonDisabled.png", ImageFormat.ARGB8888);
        CheckboxComplete = g.newImage("Recruiting/CheckboxComplete.png", ImageFormat.ARGB8888);
        CheckboxIncomplete = g.newImage("Recruiting/CheckboxIncomplete.png", ImageFormat.ARGB8888);

        AbsAdjustableDialogTop = g.newImage("Dialogs/AbsAdjustableDialogTop.png", ImageFormat.ARGB8888);
        AbsAdjustableDialogMid = g.newImage("Dialogs/AbsAdjustableDialogMid.png", ImageFormat.ARGB8888);
        AbsAdjustableDialogBot = g.newImage("Dialogs/AbsAdjustableDialogBottom.png", ImageFormat.ARGB8888);
        DialogBackground = g.newImage("Dialogs/DialogBackground.png", ImageFormat.ARGB8888);
        OptionYes = g.newImage("Dialogs/OptionYes.png", ImageFormat.ARGB8888);
        OptionNo = g.newImage("Dialogs/OptionNo.png", ImageFormat.ARGB8888);
        OptionOk = g.newImage("Dialogs/OptionOK.png", ImageFormat.ARGB8888);

        ElementImages = new ArrayList<>();
        ElementImages.add(g.newImage("elements/AirElement.png", ImageFormat.ARGB8888));
        ElementImages.add(g.newImage("elements/DarkElement.png", ImageFormat.ARGB8888));
        ElementImages.add(g.newImage("elements/EarthElement.png", ImageFormat.ARGB8888));
        ElementImages.add(g.newImage("elements/FireElement.png", ImageFormat.ARGB8888));
        ElementImages.add(g.newImage("elements/LightElement.png", ImageFormat.ARGB8888));
        ElementImages.add(g.newImage("elements/WaterElement.png", ImageFormat.ARGB8888));
    }

}
