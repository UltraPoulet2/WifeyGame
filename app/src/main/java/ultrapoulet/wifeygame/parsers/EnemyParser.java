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

    private boolean bTransformSec;
    private boolean bAddSkill;
    private boolean bRemoveSkill;

    private boolean error = false;

    private EnemyCharacter enemyBuilder;
    private TransformEnemy transformBuilder;
    private String enemyKey;

    private int tNumber = 0;

    private StringBuffer currentText = new StringBuffer();

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
                enemyBuilder.setImage(enemyKey);
            }
            else{
                System.out.println("EnemyParser:startElement(): Error parsing enemy key");
                error = true;
            }

        }
        else if(qName.equalsIgnoreCase("name")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("ai")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("maxHP")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("powerDamage")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("powerHits")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("comboDamage")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("comboHits")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("magicDamage")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("healAmount")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("powerUp")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("powerDown")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("defend")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("weaken")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("specialDamage")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("specialHits")){
            currentText = new StringBuffer();
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
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("elements")){
            //Do nothing.
        }
        else if(qName.equalsIgnoreCase("atkElement")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("stgElement")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("wkElement")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("transformations")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("transformation")){
            transformBuilder = new TransformEnemy();
            transformBuilder.setImage(enemyKey + "-T" + tNumber);
            bTransformSec = true;
        }
        else if(qName.equalsIgnoreCase("gold")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("exp")){
            currentText = new StringBuffer();
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
                enemyBuilder.addTransformation(transformBuilder);
                tNumber++;
            }
            else{
                error = true;
                System.out.println("EnemyParser:endElement(): Error adding transformation: " + tNumber);
            }
        }
        else if(qName.equalsIgnoreCase("transformations")){
            bTransformSec = false;
            tNumber = 0;
        }
        else if(qName.equalsIgnoreCase("skills")){
            bAddSkill = false;
            bRemoveSkill = false;
        }
        else if(qName.equalsIgnoreCase("name")){
            if(!bTransformSec) {
                enemyBuilder.setName(currentText.toString());
            }
            else {
                transformBuilder.setName(currentText.toString());
            }
        }
        else if(qName.equalsIgnoreCase("ai")){
            if(!bTransformSec) {
                enemyBuilder.setAI(currentText.toString());
            }
            else {
                transformBuilder.setAi(currentText.toString());
            }
        }
        else if(qName.equalsIgnoreCase("maxHP")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setMaxHP(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setHP(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for maxHP");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("powerDamage")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setPowerDamage(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setPowerDamage(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for powerDamage");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("powerHits")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setPowerHits(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setPowerHits(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for powerHits");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("comboDamage")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setComboDamage(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setComboDamage(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for comboDamage");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("comboHits")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setComboHits(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setComboHits(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for comboHits");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("magicDamage")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setMagicDamage(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setMagicDamage(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for magicDamage");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("healAmount")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setHealAmount(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setHealAmount(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for healAmount");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("powerUp")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setPowerUpPercentage(Double.parseDouble(currentText.toString()));
                } else {
                    transformBuilder.setPowerUpPercentage(Double.parseDouble(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for powerUp");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("powerDown")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setPowerDownPercentage(Double.parseDouble(currentText.toString()));
                } else {
                    transformBuilder.setPowerDownPercentage(Double.parseDouble(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for powerDown");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("defend")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setDefendPercentage(Double.parseDouble(currentText.toString()));
                } else {
                    transformBuilder.setDefendPercentage(Double.parseDouble(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for defend");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("weaken")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setWeakenPercentage(Double.parseDouble(currentText.toString()));
                } else {
                    transformBuilder.setWeakenPercentage(Double.parseDouble(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for weakne");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("specialDamage")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setSpecialDamage(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setSpecialDamage(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for specialDamage");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("specialHits")){
            try {
                if (!bTransformSec) {
                    enemyBuilder.setSpecialHits(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setSpecialHits(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for specialHits");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("skill")){
            SkillsEnum skill = SkillsEnum.getSkill(currentText.toString());
            if(skill == null){
                System.out.println("EnemyParser:characters(): Could not find skill: " + currentText.toString());
                error = true;
            }
            else{
                if(!bTransformSec) {
                    enemyBuilder.addSkill(skill);
                }
                else if(bAddSkill) {
                    transformBuilder.addSkill(skill);
                }
                else if(bRemoveSkill) {
                    transformBuilder.removeSkill(skill);
                }
            }
            bAddSkill = false;
            bRemoveSkill = false;
        }
        else if(qName.equalsIgnoreCase("atkElement")){
            Element elm = Element.getElement(currentText.toString());
            if(elm == null){
                System.out.println("EnemyParser:characters(): Could not find atk element: " + currentText.toString());
                error = true;
            }
            else{
                if(!bTransformSec) {
                    enemyBuilder.setAttackElement(elm);
                }
                else {
                    transformBuilder.setAttackElement(elm);
                }
            }
        }
        else if(qName.equalsIgnoreCase("stgElement")){
            Element elm = Element.getElement(currentText.toString());
            if(elm == null){
                System.out.println("EnemyParser:characters(): Could not find stg element: " + currentText.toString());
                error = true;
            }
            else{
                if(!bTransformSec) {
                    enemyBuilder.setStrongElement(elm);
                }
                else {
                    transformBuilder.setStrongElement(elm);
                }
            }
        }
        else if(qName.equalsIgnoreCase("wkElement")){
            Element elm = Element.getElement(currentText.toString());
            if(elm == null){
                System.out.println("EnemyParser:characters(): Could not find wk element: " + currentText.toString());
                error = true;
            }
            else{
                if(!bTransformSec) {
                    enemyBuilder.setWeakElement(elm);
                }
                else {
                    transformBuilder.setWeakElement(elm);
                }
            }
        }
        else if(qName.equalsIgnoreCase("gold")){
            try {
                enemyBuilder.setGold(Integer.parseInt(currentText.toString()));
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for gold");
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("exp")){
            try {
                enemyBuilder.setExperience(Integer.parseInt(currentText.toString()));
            }
            catch(NumberFormatException e){
                System.out.println("EnemyParser:characters(): NumberFormatException for key: " + enemyKey + " for exp");
                error = true;
            }
        }
    }

    @Override
    public void characters(char ch[],
                           int start,
                           int length) throws SAXException {
        currentText.append(new String(ch, start, length));
    }

    private boolean validate(){
        if(error == true){
            return false;
        }
        return enemyBuilder.validate();
    }
}
