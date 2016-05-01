package ultrapoulet.wifeygame.battle.enemyai;

/**
 * Created by John on 4/19/2016.
 */
public class BasicMagicEnemyAI extends EnemyAI{

    private boolean healUsed = false;

    public void selectAction(){
        if(healUsed){
            selectedAction = EnemyAction.MAGIC_ATTACK;
            healUsed = false;
        }
        else{
            int randomSelect = (int) (Math.random() * 2);

            if(randomSelect == 0){
                selectedAction = EnemyAction.MAGIC_ATTACK;
            }
            else{
                selectedAction = EnemyAction.HEALING_MAGIC;
                healUsed = true;
            }
        }
    }
}
