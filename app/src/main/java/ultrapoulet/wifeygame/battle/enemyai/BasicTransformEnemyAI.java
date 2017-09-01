package ultrapoulet.wifeygame.battle.enemyai;

import android.util.Log;

/**
 * Created by John on 9/22/2016.
 */

public class BasicTransformEnemyAI extends EnemyAI {

    private int phase = 0;

    public void selectAction() {
        switch(phase){
            case 0:
            case 1:
            case 2:
            case 3:
                selectedAction = EnemyAction.MAGIC_ATTACK;
                break;
            case 4:
                selectedAction = EnemyAction.TRANSFORM;
                break;
            default:
                //System.out.println("BasicTransformEnemyAI: Transform did not work");
                Log.e("BasicTansformEnemyAI", "Transform did not work");
                selectedAction = EnemyAction.MAGIC_ATTACK;
                break;
        }
        phase++;
    }

    public void reset() {
        phase = 0;
    }
}
