package ultrapoulet.wifeygame.battle.enemyai;

/**
 * Created by John on 4/18/2016.
 */
public class BasicPhysicalEnemyAI extends EnemyAI {

    public void selectAction(){
        int randomSelect = (int) (Math.random() * 2);

        if(randomSelect == 0){
            selectedAction = EnemyAction.POWER_ATTACK;
        }
        else if(randomSelect == 1){
            selectedAction = EnemyAction.COMBO_ATTACK;
        }
        else{
        }
    }

    public void reset(){
        //Do nothing
    }

}
