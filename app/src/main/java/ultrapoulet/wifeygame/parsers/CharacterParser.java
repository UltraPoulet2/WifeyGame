package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.wifeygame.character.Element;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.TransformWifey;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 6/8/2016.
 */
public class CharacterParser extends DefaultHandler{

    private Graphics g;

    private boolean bName;
    private boolean bTitle;
    private boolean bStrength;
    private boolean bMagic;
    private boolean bWeapon;
    private boolean bSkill;

    private boolean bTransformSec;
    private boolean bTransform;
    private boolean bAddSkill;
    private boolean bRemoveSkill;
    private boolean bNum;

    private boolean bAtkElement;
    private boolean bStgElement;
    private boolean bWkElement;

    private boolean error;

    private WifeyCharacter charBuilder;
    private TransformWifey transformBuilder;
    private String charKey;

    private int charNumber = 0;
    private int tNumber = 0;

    public void setGraphics(Graphics g){
        this.g = g;
    }

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
            bName = true;
        }
        else if(qName.equalsIgnoreCase("title")){
            bTitle = true;
        }
        else if(qName.equalsIgnoreCase("strength")){
            bStrength = true;
        }
        else if(qName.equalsIgnoreCase("magic")){
            bMagic = true;
        }
        else if(qName.equalsIgnoreCase("weapon")){
            bWeapon = true;
        }
        else if(qName.equalsIgnoreCase("characters")){
            //Do nothing.
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
                else{
                    error = true;
                    System.out.println("CharacterParser:startElement(): Invalid skill mode: " + mode + " for key " + charKey);
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
            transformBuilder = new TransformWifey();
            transformBuilder.setImage(charKey + "-T" + tNumber);
            bTransform = true;
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
                RecruitedCharacters.put(charKey, charBuilder);
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
                if (!bTransformSec) {
                    charBuilder.setName(temp);
                } else {
                    transformBuilder.setName(temp);
                }
                bName = false;
            } else if (bTitle) {
                //For now, we are not changing the title for transformations, this could change later
                charBuilder.setTitle(temp);
                bTitle = false;
            } else if (bStrength) {
                if(!bTransformSec) {
                    charBuilder.setStrength(Integer.parseInt(temp));
                }
                else {
                    transformBuilder.setStrength(Integer.parseInt(temp));
                }
                bStrength = false;
            } else if (bMagic) {
                if(!bTransformSec) {
                    charBuilder.setMagic(Integer.parseInt(temp));
                }
                else {
                    transformBuilder.setMagic(Integer.parseInt(temp));
                }
                bMagic = false;
            } else if (bWeapon) {
                if(!bTransformSec) {
                    charBuilder.setWeapon(Weapon.getWeapon(temp));
                }
                else {
                    transformBuilder.setWeapon(Weapon.getWeapon(temp));
                }
                bWeapon = false;
            } else if (bSkill) {
                SkillsEnum skill = SkillsEnum.getSkill(temp);
                if (skill == null) {
                    System.out.println("CharacterParser:characters(): Could not find skill: " + temp);
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
                bSkill = false;
                bAddSkill = false;
                bRemoveSkill = false;
            } else if (bAtkElement) {
                Element elm = Element.getElement(temp);
                if (elm == null) {
                    System.out.println("CharacterParser:characters(): Could not find element: " + temp);
                    error = true;
                } else {
                    if(!bTransformSec) {
                        charBuilder.setAttackElement(elm);
                    }
                    else {
                        transformBuilder.setAttackElement(elm);
                    }
                }
                bAtkElement = false;
            } else if (bStgElement) {
                Element elm = Element.getElement(temp);
                if (elm == null) {
                    System.out.println("CharacterParser:characters(): Could not find element: " + temp);
                    error = true;
                } else {
                    if(!bTransformSec) {
                        charBuilder.setStrongElement(elm);
                    }
                    else {
                        transformBuilder.setStrongElement(elm);
                    }
                }
                bStgElement = false;
            } else if (bWkElement) {
                Element elm = Element.getElement(temp);
                if (elm == null) {
                    System.out.println("CharacterParser:characters(): Could not find element: " + temp);
                    error = true;
                } else {
                    if(!bTransformSec) {
                        charBuilder.setWeakElement(elm);
                    }
                    else {
                        transformBuilder.setWeakElement(elm);
                    }
                }
                bWkElement = false;
            }
        }
        catch(NumberFormatException e){
            System.out.println("CharacterParser:characters(): NumberFormatException for key: " + charKey);
            error = true;
            bStrength = false;
            bMagic = false;
        }
    }

    private boolean validate(){

        if(error == true){
            return false;
        }
        return charBuilder.validate();
    }
}
