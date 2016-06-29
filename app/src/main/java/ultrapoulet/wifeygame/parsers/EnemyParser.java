package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI;
import ultrapoulet.wifeygame.character.EnemyCharacter;
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

    private boolean error = false;

    private EnemyCharacter enemyBuilder;
    private String enemyKey;

    public void setGraphics(Graphics g){
        this.g = g;
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException{
        if(qName.equalsIgnoreCase("enemy")){
            error = false;
            enemyKey = attributes.getValue("key");
            if(enemyKey != null) {
                enemyBuilder = new EnemyCharacter();
                enemyBuilder.setImage(g.newImage("enemies/" + enemyKey + ".png", ImageFormat.RGB565));
            }
            else{
                System.out.println("EnemyParser:startElement(): Error parsing enemy key");
                error = true;
            }

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
        else if(qName.equalsIgnoreCase("enemies")){
            //Do nothing
        }
        else{
            System.out.println("EnemyParser:startElement(): Invalid qName: " + qName + " for key: " + enemyKey);
            error = true;
        }
    }

    @Override
    public void characters(char ch[],
                           int start,
                           int length) throws SAXException {
        String temp = new String(ch, start, length);
        try {
            if (bName) {
                enemyBuilder.setName(temp);
                bName = false;
            } else if (bAi) {
                enemyBuilder.setAI(temp);
                bAi = false;
            } else if (bMaxHp) {
                enemyBuilder.setMaxHP(Integer.parseInt(temp));
                bMaxHp = false;
            } else if (bPowerDamage) {
                enemyBuilder.setPowerDamage(Integer.parseInt(temp));
                bPowerDamage = false;
            } else if (bPowerHits) {
                enemyBuilder.setPowerHits(Integer.parseInt(temp));
                bPowerHits = false;
            } else if (bComboDamage) {
                enemyBuilder.setComboDamage(Integer.parseInt(temp));
                bComboDamage = false;
            } else if (bComboHits) {
                enemyBuilder.setComboHits(Integer.parseInt(temp));
                bComboHits = false;
            } else if (bMagicDamage) {
                enemyBuilder.setMagicDamage(Integer.parseInt(temp));
                bMagicDamage = false;
            } else if (bHealAmount) {
                enemyBuilder.setHealAmount(Integer.parseInt(temp));
                bHealAmount = false;
            } else if (bPowerUp) {
                enemyBuilder.setPowerUpPercentage(Double.parseDouble(temp));
                bPowerUp = false;
            } else if (bPowerDown) {
                enemyBuilder.setPowerDownPercentage(Double.parseDouble(temp));
                bPowerDown = false;
            } else if (bDefend) {
                enemyBuilder.setDefendPercentage(Double.parseDouble(temp));
                bDefend = false;
            } else if (bWeaken) {
                enemyBuilder.setWeakenPercentage(Double.parseDouble(temp));
                bWeaken = false;
            } else if (bSpecialDamage) {
                enemyBuilder.setSpecialDamage(Integer.parseInt(temp));
                bSpecialDamage = false;
            } else if (bSpecialHits) {
                enemyBuilder.setSpecialHits(Integer.parseInt(temp));
                bSpecialHits = false;
            }
        }
        catch(NumberFormatException e){
            System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey);
            error = true;
            bMaxHp = false;
            bPowerDamage = false;
            bPowerHits = false;
            bComboDamage = false;
            bComboHits = false;
            bMagicDamage = false;
            bHealAmount = false;
            bPowerUp = false;
            bPowerDown = false;
            bDefend = false;
            bWeaken = false;
            bSpecialDamage = false;
            bSpecialHits = false;
        }

    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if(qName.equalsIgnoreCase("enemy")){
            if(validate()){
                Enemies.put(enemyKey, enemyBuilder);
            }
            else{
                System.out.println("EnemyParser:endElement(): Error parsing: " + enemyKey);
            }
        }
    }

    private boolean validate(){
        if(error == true){
            return false;
        }
        return enemyBuilder.validate();
    }
}
