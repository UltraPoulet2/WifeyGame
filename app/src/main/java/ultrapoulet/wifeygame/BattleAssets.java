package ultrapoulet.wifeygame;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;

/**
 * Created by John on 11/14/2017.
 */

public class BattleAssets {

    public static Image WaveText, FinalWaveText;
    public static Image AttackBox;
    public static Image HitsText, HitText, DamageText;
    public static Image AttackUp, AttackDown;
    public static Image DefenseUp, DefenseDown;
    public static List<Image> KOImages;
    public static Image CharHolder;
    public static Image EnemyHolder;
    public static Image SpecialBar, SpecialBarBase, SpecialBarTop;

    public static void load(Graphics g) {
        WaveText = g.newImage("objects/battleMisc/WaveText.png", ImageFormat.RGB565);
        FinalWaveText = g.newImage("objects/battleMisc/WaveFinalText.png", ImageFormat.RGB565);
        AttackBox = g.newImage("objects/battleMisc/AttackBox.png", ImageFormat.ARGB8888);
        HitsText = g.newImage("objects/text/Hits.png", ImageFormat.ARGB8888);
        HitText = g.newImage("objects/text/Hit.png", ImageFormat.ARGB8888);
        DamageText = g.newImage("objects/text/Damage.png", ImageFormat.ARGB8888);
        AttackUp = g.newImage("objects/battleMisc/StatusAttackUp.png", ImageFormat.ARGB8888);
        AttackDown = g.newImage("objects/battleMisc/StatusAttackDown.png", ImageFormat.ARGB8888);
        DefenseUp = g.newImage("objects/battleMisc/StatusDefenseUp.png", ImageFormat.ARGB8888);
        DefenseDown = g.newImage("objects/battleMisc/StatusDefenseDown.png", ImageFormat.ARGB8888);
        CharHolder = g.newImage("objects/holders/CharacterHolder.png", ImageFormat.ARGB8888);
        EnemyHolder = g.newImage("objects/bars/EnemyHealthHolder.png", ImageFormat.ARGB8888);
        SpecialBar = g.newImage("objects/bars/SpecialBar.png", ImageFormat.RGB565);
        SpecialBarBase = g.newImage("objects/bars/SpecialBarBase.png", ImageFormat.ARGB8888);
        SpecialBarTop = g.newImage("objects/bars/SpecialBarTop.png", ImageFormat.ARGB8888);

        KOImages = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            KOImages.add(g.newImage("objects/battleMisc/KOImage" + i + ".png", ImageFormat.ARGB8888));
        }
    }

    public static void unload() {
        WaveText = null;
        FinalWaveText = null;
        AttackBox = null;
        HitsText = null;
        HitText = null;
        DamageText = null;
        AttackUp = null;
        AttackDown = null;
        DefenseUp = null;
        DefenseDown = null;
        CharHolder = null;
        EnemyHolder = null;
        SpecialBar = null;
        SpecialBarBase = null;
        SpecialBarTop = null;
        KOImages = null;
    }
}
