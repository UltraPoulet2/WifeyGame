package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.wifeygame.character.Element;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.TransformWifey;
import ultrapoulet.wifeygame.character.UniqueSkillsEnum;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.character.WeaponSkillsEnum;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Characters;

/**
 * Created by John on 6/8/2016.
 */
public class CharacterParser extends DefaultHandler{

    private boolean bTransformSec;
    private boolean bAddSkill;
    private boolean bRemoveSkill;

    private boolean error;

    private WifeyCharacter charBuilder;
    private TransformWifey transformBuilder;
    private String charKey;

    private int charNumber = 0;
    private int tNumber = 0;

    private StringBuffer currentText = new StringBuffer();

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("character")) {
            error = false;
            charKey = attributes.getValue("key");
            if(charKey != null) {
                charBuilder = new WifeyCharacter();
                charBuilder.setImage(charKey);
                charBuilder.setHashKey(charKey);
            }
            else{
                System.out.println("CharacterParser:startElement(): Error parsing character key #" + charNumber);
                error = true;
            }
            charNumber++;
        }
        else if(qName.equalsIgnoreCase("name")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("title")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("strength")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("magic")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("weapon")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("weaponSkill")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("uniqueSkill")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("characters")){
            //Do nothing.
        }
        else if(qName.equalsIgnoreCase("skills")){
            String mode = attributes.getValue("mode");
            if(mode != null){
                if(mode.equalsIgnoreCase("add")){
                    bAddSkill = true;
                    currentText = new StringBuffer();
                }
                else if(mode.equalsIgnoreCase("remove")){
                    bRemoveSkill = true;
                    currentText = new StringBuffer();
                }
                else{
                    error = true;
                    System.out.println("CharacterParser:startElement(): Invalid skill mode: " + mode + " for key " + charKey);
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
            bTransformSec = true;
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("transformation")){
            transformBuilder = new TransformWifey();
            transformBuilder.setImage(charKey + "-T" + tNumber);
        }
        else{
            System.out.println("CharacterParser:startElement(): Invalid qName: " + qName + " for key " + charKey);
        }

    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if (qName.equalsIgnoreCase("character")) {
            if(validate()) {
                Characters.put(charKey, charBuilder);
                System.out.println("CharacterParser:endElement(): Adding character: " + charKey);
            }
            else{
                System.out.println("CharacterParser:endElement(): Error parsing for key: " + charKey);
            }
        }
        else if(qName.equalsIgnoreCase("transformation")){
            //Do things for validating a transformation
            if(transformBuilder.validate()) {
                charBuilder.addTransformation(transformBuilder);
                tNumber++;
            }
            else{
                error = true;
                System.out.println("CharacterParser:endElement(): Error adding transformation: " + tNumber);
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
            if (!bTransformSec) {
                charBuilder.setName(currentText.toString());
            } else {
                transformBuilder.setName(currentText.toString());
            }
        }
        else if(qName.equalsIgnoreCase("title")){
            //For now, we are not changing the title for transformations, this could change later
            charBuilder.setTitle(currentText.toString());
        }
        else if(qName.equalsIgnoreCase("strength")){
            try {
                if (!bTransformSec) {
                    charBuilder.setStrength(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setStrength(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("CharacterParser:characters(): NumberFormatException for key: " + charKey);
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("magic")){
            try {
                if (!bTransformSec) {
                    charBuilder.setMagic(Integer.parseInt(currentText.toString()));
                } else {
                    transformBuilder.setMagic(Integer.parseInt(currentText.toString()));
                }
            }
            catch(NumberFormatException e){
                System.out.println("CharacterParser:characters(): NumberFormatException for key: " + charKey);
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("weapon")){
            if (!bTransformSec) {
                charBuilder.setWeapon(Weapon.getWeapon(currentText.toString()));
            } else {
                transformBuilder.setWeapon(Weapon.getWeapon(currentText.toString()));
            }
        }
        else if(qName.equalsIgnoreCase("weaponSkill")){
            WeaponSkillsEnum skill;
            try {
                skill = WeaponSkillsEnum.valueOf(currentText.toString());
            }
            catch(IllegalArgumentException e){
                skill = null;
            }
            if (skill == null) {
                System.out.println("CharacterParser:characters(): Could not find skill: " + currentText.toString());
                error = true;
            } else {
                if (!bTransformSec) {
                    charBuilder.setWeaponSkill(skill);
                } else {
                    transformBuilder.setWeaponSkill(skill);
                }
            }
        }
        else if(qName.equalsIgnoreCase("uniqueSkill")){
            UniqueSkillsEnum skill;
            try {
                skill = UniqueSkillsEnum.valueOf(currentText.toString());
            }
            catch(IllegalArgumentException e){
                skill = null;
            }
            if(skill == null){
                System.out.println("CharacterParser:characters(): Could not find skill: " + currentText.toString());
                error = true;
            } else {
                if (!bTransformSec){
                    charBuilder.setUniqueSkill(skill);
                }
                else {
                    transformBuilder.setUniqueSkill(skill);
                }
            }
        }
        else if(qName.equalsIgnoreCase("skill")){
            SkillsEnum skill;
            try {
                skill = SkillsEnum.valueOf(currentText.toString());
            }
            catch(IllegalArgumentException e){
                skill = null;
            }
            if (skill == null) {
                System.out.println("CharacterParser:characters(): Could not find skill: " + currentText.toString());
                error = true;
            } else {
                if(!bTransformSec) {
                    charBuilder.addSkill(skill);
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
            if (elm == null) {
                System.out.println("CharacterParser:characters(): Could not find Attack element: " + currentText.toString());
                error = true;
            } else {
                if(!bTransformSec) {
                    charBuilder.setAttackElement(elm);
                }
                else {
                    transformBuilder.setAttackElement(elm);
                }
            }
        } else if (qName.equalsIgnoreCase("stgElement")) {
            Element elm = Element.getElement(currentText.toString());
            if (elm == null) {
                System.out.println("CharacterParser:characters(): Could not find Strong element: " + currentText.toString());
                error = true;
            } else {
                if(!bTransformSec) {
                    charBuilder.setStrongElement(elm);
                }
                else {
                    transformBuilder.setStrongElement(elm);
                }
            }
        } else if (qName.equalsIgnoreCase("wkElement")) {
            Element elm = Element.getElement(currentText.toString());
            if (elm == null) {
                System.out.println("CharacterParser:characters(): Could not find  Weak element: " + currentText.toString());
                error = true;
            } else {
                if(!bTransformSec) {
                    charBuilder.setWeakElement(elm);
                }
                else {
                    transformBuilder.setWeakElement(elm);
                }
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
        return charBuilder.validate();
    }
}
