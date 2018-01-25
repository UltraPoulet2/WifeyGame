package ultrapoulet.wifeygame.battle.enemyai;

import ultrapoulet.wifeygame.battle.BattleCharacter.EnemyAction;

/**
 * Created by John on 4/21/2016.
 */
public class OriginalBossAI extends EnemyAI {

    private int phase = 0;

    public void selectAction(){
        switch(phase){
            case 0:
                selectedAction = EnemyAction.POWER_ATTACK;
                break;
            case 1:
                selectedAction = EnemyAction.POWER_UP;
                break;
            case 2:
                selectedAction = EnemyAction.SPECIAL_ATTACK;
                break;
            case 3:
            case 4:
                selectedAction = EnemyAction.POWER_ATTACK;
                break;
            case 5:
                selectedAction = EnemyAction.WEAKEN;
                break;
            case 6:
                selectedAction = EnemyAction.POWER_ATTACK;
                break;
        }
        phase = (phase + 1) % 7;
    }

    public void reset(){
        phase = 0;
    }
}
