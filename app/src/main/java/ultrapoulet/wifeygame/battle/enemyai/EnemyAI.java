package ultrapoulet.wifeygame.battle.enemyai;

/**
 * Created by John on 4/16/2016.
 */
public abstract class EnemyAI {

    public enum EnemyAction{
        POWER_ATTACK,
        COMBO_ATTACK,
        MAGIC_ATTACK,
        HEALING_MAGIC,
        POWER_UP,
        POWER_DOWN,
        DEFEND,
        WEAKEN,
        TRANSFORM,
        SPECIAL_ATTACK,
        WAIT
    };

    public EnemyAction selectedAction = null;

    public abstract void selectAction();

    public EnemyAction getAction(){
        return selectedAction;
    }
}
