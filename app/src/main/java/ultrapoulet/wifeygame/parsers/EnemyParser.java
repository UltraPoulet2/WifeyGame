package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.wifeygame.character.Element;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.TransformEnemy;
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
    private boolean bSkill = false;

    private boolean bTransformSec;
    private boolean bTransform;
    private boolean bAddSkill;
    private boolean bRemoveSkill;

    private boolean bAtkElement;
    private boolean bStgElement;
    private boolean bWkElement;

    private boolean error = false;

    private EnemyCharacter enemyBuilder;
    private TransformEnemy transformBuilder;
    private String enemyKey;

    private int tNumber;

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
        else if(qName.equalsIgnoreCase("skills")){
            String mode = attributes.getValue("mode");
            if(mode != null){
                if(mode.equalsIgnoreCase("add")){
                    bAddSkill = true;
                }
                else if(mode.equalsIgnoreCase("remove")){
                    bRemoveSkill = true;
                }
                else {
                    error = true;
                    System.out.println("EnemyParser:startElement(): Invalid skill mode: " + mode + " for key " + enemyKey);
                }
            }
        }
        else if(qName.equalsIgnoreCase("skill")){
            bSkill = true;
        }
        else if(qName.equalsIgnoreCase("elements")){
            //Do nothing.
        }
        else if(qName.equalsIgnoreCase("atkElement")){
            bAtkElement = true;
        }
        else if(qName.equalsIgnoreCase("stgElement")){
            bStgElement = true;
        }
        else if(qName.equalsIgnoreCase("wkElement")){
            bWkElement = true;
        }
        else if(qName.equalsIgnoreCase("transformations")){
            bTransformSec = true;
        }
        else if(qName.equalsIgnoreCase("transformation")){
            transformBuilder = new TransformEnemy();
            //transformBuilder.setImage("enemies/" + enemyKey + "-T" + tNumber = ".png", ImageFormat.RGB565));
            bTransform = true;
        }
        else{
            System.out.println("EnemyParser:startElement(): Invalid qName: " + qName + " for key: " + enemyKey);
            error = true;
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
        else if(qName.equalsIgnoreCase("transformation")){
            if(transformBuilder.validate()) {
                //enemyBuilder.addTransformation(transformBuilder);
                tNumber++;
            }

            bTransform = false;
        }
        else if(qName.equalsIgnoreCase("transformations")){
            bTransformSec = false;
            tNumber = 0;
        }
        else if(qName.equalsIgnoreCase("skills")){
            bAddSkill = false;
            bRemoveSkill = false;
        }
    }

    @Override
    public void characters(char ch[],
                           int start,
                           int length) throws SAXException {
        String temp = new String(ch, start, length);
        try {
            if (bName) {
                if(!bTransformSec) {
                    enemyBuilder.setName(temp);
                }
                else {
                    System.out.println("Transform name: " + temp);
                }
                bName = false;
            } else if (bAi) {
                if(!bTransformSec) {
                    enemyBuilder.setAI(temp);
                }
                else {
                    System.out.println("Transfom ai: " + temp);
                }
                bAi = false;
            } else if (bMaxHp) {
                if(!bTransformSec) {
                    enemyBuilder.setMaxHP(Integer.parseInt(temp));
                }
                else {
                    System.out.println("Transform HP: " + temp);
                }
                bMaxHp = false;
            } else if (bPowerDamage) {
                if(!bTransformSec) {
                    enemyBuilder.setPowerDamage(Integer.parseInt(temp));
                }
                else {
                    System.out.println("Transform Power Damage: " + temp);
                }
                bPowerDamage = false;
            } else if (bPowerHits) {
                if(!bTransformSec) {
                    enemyBuilder.setPowerHits(Integer.parseInt(temp));
                }
                else {
                    System.out.println("Transform Power Hits: " + temp);
                }
                bPowerHits = false;
            } else if (bComboDamage) {
                if(!bTransformSec) {
                    enemyBuilder.setComboDamage(Integer.parseInt(temp));
                }
                else {
                    System.out.println("Transform Combo Damage: " + temp);
                }
                bComboDamage = false;
            } else if (bComboHits) {
                if(!bTransformSec) {
                    enemyBuilder.setComboHits(Integer.parseInt(temp));
                }
                else {
                    System.out.println("Transform Combo Hits: " + temp);
                }
                bComboHits = false;
            } else if (bMagicDamage) {
                if(!bTransformSec) {
                    enemyBuilder.setMagicDamage(Integer.parseInt(temp));
                }
                else {
                    System.out.println("Transform Magic Damage: " + temp);
                }
                bMagicDamage = false;
            } else if (bHealAmount) {
                if(!bTransformSec) {
                    enemyBuilder.setHealAmount(Integer.parseInt(temp));
                }
                else {
                    System.out.println("Transform Heal Amount: " + temp);
                }
                bHealAmount = false;
            } else if (bPowerUp) {
                if(!bTransformSec) {
                    enemyBuilder.setPowerUpPercentage(Double.parseDouble(temp));
                }
                else {
                    System.out.println("Transform Power Up: " + temp);
                }
                bPowerUp = false;
            } else if (bPowerDown) {
                if(!bTransformSec) {
                    enemyBuilder.setPowerDownPercentage(Double.parseDouble(temp));
                }
                else {
                    System.out.println("Transform Power Down: " + temp);
                }
                bPowerDown = false;
            } else if (bDefend) {
                if(!bTransformSec) {
                    enemyBuilder.setDefendPercentage(Double.parseDouble(temp));
                }
                else {
                    System.out.println("Transform Defend: " + temp);
                }
                bDefend = false;
            } else if (bWeaken) {
                if(!bTransformSec) {
                    enemyBuilder.setWeakenPercentage(Double.parseDouble(temp));
                }
                else {
                    System.out.println("Transform Weaken: " + temp);
                }
                bWeaken = false;
            } else if (bSpecialDamage) {
                if(!bTransformSec) {
                    enemyBuilder.setSpecialDamage(Integer.parseInt(temp));
                }
                else {
                    System.out.println("Transform Special Damage: " + temp);
                }
                bSpecialDamage = false;
            } else if (bSpecialHits) {
                if(!bTransformSec) {
                    enemyBuilder.setSpecialHits(Integer.parseInt(temp));
                }
                else {
                    System.out.println("Transform Special Hits: " + temp);
                }
                bSpecialHits = false;
            } else if (bSkill) {
                SkillsEnum skill = SkillsEnum.getSkill(temp);
                if(skill == null){
                    System.out.println("EnemyParser:characters(): Could not find skill: " + temp);
                    error = true;
                }
                else{
                    if(!bTransformSec) {
                        enemyBuilder.addSkill(skill);
                    }
                    else if(bAddSkill) {
                        System.out.println("Transform add skill: " + skill.getSkillName());
                    }
                    else if(bRemoveSkill) {
                        System.out.println("Transform remove skill: " + skill.getSkillName());
                    }
                }
                bSkill = false;
                bAddSkill = false;
                bRemoveSkill = false;
            }
            else if(bAtkElement){
                Element elm = Element.getElement(temp);
                if(elm == null){
                    System.out.println("EnemyParser:characters(): Could not find element: " + temp);
                    error = true;
                }
                else{
                    if(!bTransformSec) {
                        enemyBuilder.setAttackElement(elm);
                    }
                    else {
                        System.out.println("Transform attack element: " + elm.getElementType());
                    }
                }
                bAtkElement = false;
            }
            else if(bStgElement){
                Element elm = Element.getElement(temp);
                if(elm == null){
                    System.out.println("EnemyParser:characters(): Could not find element: " + temp);
                    error = true;
                }
                else{
                    if(!bTransformSec) {
                        enemyBuilder.setStrongElement(elm);
                    }
                    else {
                        System.out.println("Transform strong element: " + elm.getElementType());
                    }
                }
                bStgElement = false;
            }
            else if(bWkElement){
                Element elm = Element.getElement(temp);
                if(elm == null){
                    System.out.println("EnemyParser:characters(): Could not find element: " + temp);
                    error = true;
                }
                else{
                    if(!bTransformSec) {
                        enemyBuilder.setWeakElement(elm);
                    }
                    else {
                        System.out.println("Transform weak element: " + elm.getElementType());
                    }
                }
                bWkElement = false;
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

    private boolean validate(){
        if(error == true){
            return false;
        }
        return enemyBuilder.validate();
    }
}
