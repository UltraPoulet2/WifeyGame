package ultrapoulet.wifeygame.battle.enemyai;

import android.util.Log;
import ultrapoulet.wifeygame.battle.BattleCharacter.EnemyAction;

/**
 * Created by John on 4/16/2016.
 */
public abstract class EnemyAI {

    public EnemyAction selectedAction = null;

    public abstract void selectAction();

    public EnemyAction getAction(){
        return selectedAction;
    }

    public abstract void reset();

    public static EnemyAI getAI(String name){
        if(name.equalsIgnoreCase("BasicMagicEnemyAI")){
            return new BasicMagicEnemyAI();
        }
        else if(name.equalsIgnoreCase("BasicPhysicalEnemyAI")){
            return new BasicPhysicalEnemyAI();
        }
        else if(name.equalsIgnoreCase("OriginalBossAI")){
            return new OriginalBossAI();
        }
        else if(name.equalsIgnoreCase("BasicTransformEnemyAI")){
            return new BasicTransformEnemyAI();
        }

        //System.out.println("EnemyAI:getAI(): No AI found for: " + name);
        Log.e("EnemyAI", "No AI found for: " + name);
        return null;
    }
}
