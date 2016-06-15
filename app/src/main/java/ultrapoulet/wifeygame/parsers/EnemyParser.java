package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.Enemy;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI;
import ultrapoulet.wifeygame.gamestate.Enemies;

/**
 * Created by John on 6/14/2016.
 */
public class EnemyParser extends DefaultHandler{

    private Graphics g;

    private boolean bName = false;
    private boolean bAi = false;
    private boolean bMaxHp = false;
    private boolean bPowerDamage = false;
    private boolean bPowerHits = false;
    private boolean bComboDamage = false;
    private boolean bComboHits = false;
    private boolean bMagicDamage = false;
    private boolean bHealAmount = false;
    private boolean bPowerUp = false;
    private boolean bPowerDown = false;
    private boolean bDefend = false;
    private boolean bWeaken = false;
    private boolean bSpecialDamage = false;
    private boolean bSpecialHits = false;

    private Enemy enemyBuilder;
    private String enemyKey;
    private Image enemyImage;
    private String enemyName;
    private EnemyAI enemyAI;
    private int enemyHP;
    private int enemyPowerDamage;
    private int enemyPowerHits;
    private int enemyComboDamage;
    private int enemyComboHits;
    private int enemyMagicDamage;
    private int enemyHealAmount;
    private double enemyPowerUp;
    private double enemyPowerDown;
    private double enemyDefend;
    private double enemyWeaken;
    private int enemySpecialDamage;
    private int enemySpecialHits;

    public void setGraphics(Graphics g){
        this.g = g;
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException{
        if(qName.equalsIgnoreCase("enemy")){
            resetValues();
            enemyKey = attributes.getValue("key");
            enemyImage = g.newImage("TemplateEnemy.png", ImageFormat.ARGB8888);
        }
        else if(qName.equalsIgnoreCase("name")){
            bName = true;
        }
        else if(qName.equalsIgnoreCase("ai")){
            bAi = true;
        }
        else if(qName.equalsIgnoreCase("maxHP")){
            bMaxHp = true;
        }
        else if(qName.equalsIgnoreCase("powerDamage")){
            bPowerDamage = true;
        }
        else if(qName.equalsIgnoreCase("powerHits")){
            bPowerHits = true;
        }
        else if(qName.equalsIgnoreCase("comboDamage")){
            bComboDamage = true;
        }
        else if(qName.equalsIgnoreCase("comboHits")){
            bComboHits = true;
        }
        else if(qName.equalsIgnoreCase("magicDamage")){
            bMagicDamage = true;
        }
        else if(qName.equalsIgnoreCase("healAmount")){
            bHealAmount = true;
        }
        else if(qName.equalsIgnoreCase("powerUp")){
            bPowerUp = true;
        }
        else if(qName.equalsIgnoreCase("powerDown")){
            bPowerDown = true;
        }
        else if(qName.equalsIgnoreCase("defend")){
            bDefend = true;
        }
        else if(qName.equalsIgnoreCase("weaken")){
            bWeaken = true;
        }
        else if(qName.equalsIgnoreCase("specialDamage")){
            bSpecialDamage = true;
        }
        else if(qName.equalsIgnoreCase("specialHits")){
            bSpecialHits = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if(qName.equalsIgnoreCase("enemy")){
            if(validate()){
                enemyBuilder = new Enemy(
                        enemyName,
                        enemyHP,
                        enemyPowerDamage,
                        enemyPowerHits,
                        enemyComboDamage,
                        enemyComboHits,
                        enemyMagicDamage,
                        enemyHealAmount,
                        enemyPowerUp,
                        enemyPowerDown,
                        enemyDefend,
                        enemyWeaken,
                        enemySpecialDamage,
                        enemySpecialHits,
                        enemyImage,
                        enemyAI);
                Enemies.put(enemyKey, enemyBuilder);
            }
        }
    }

    private void resetValues(){
        enemyName = "";
        enemyImage = null;
        enemyAI = null;
        enemyHP = 0;
        enemyPowerDamage = 0;
        enemyPowerHits = 0;
        enemyComboDamage = 0;
        enemyComboHits = 0;
        enemyMagicDamage = 0;
        enemyHealAmount = 0;
        enemyPowerUp = 0.0;
        enemyPowerDown = 0.0;
        enemyDefend = 0.0;
        enemyWeaken = 0.0;
        enemySpecialDamage = 0;
        enemySpecialHits = 0;
    }

    private boolean validate(){
        if(enemyName.length() == 0){
            return false;
        }
        if(enemyImage == null){
            return false;
        }
        if(enemyAI == null){
            return false;
        }
        if(enemyHP == 0){
            return false;
        }
        return true;
    }
}
