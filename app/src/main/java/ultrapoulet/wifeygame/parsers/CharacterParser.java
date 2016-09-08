package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.wifeygame.character.Element;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 6/8/2016.
 */
public class CharacterParser extends DefaultHandler{

    private Graphics g;

    private boolean bName;
    private boolean bStrength;
    private boolean bMagic;
    private boolean bWeapon;
    private boolean bSkill;

    private boolean bAtkElement;
    private boolean bStgElement;
    private boolean bWkElement;

    private boolean error;

    private WifeyCharacter charBuilder;
    private String charKey;

    private int charNumber = 0;

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
                charBuilder.setImage(g.newImage("characters/" + charKey + ".png", ImageFormat.RGB565));
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
            //Do nothing.
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
    }

    @Override
    public void characters(char ch[],
                           int start,
                           int length) throws SAXException {
        String temp = new String(ch, start, length);
        try {
            if (bName) {
                charBuilder.setName(temp);
                bName = false;
            } else if (bStrength) {
                charBuilder.setStrength(Integer.parseInt(temp));
                bStrength = false;
            } else if (bMagic) {
                charBuilder.setMagic(Integer.parseInt(temp));
                bMagic = false;
            }
            else if (bWeapon) {
                charBuilder.setWeapon(Weapon.getWeapon(temp));
                bWeapon = false;
            }
            else if(bSkill){
                SkillsEnum skill = SkillsEnum.getSkill(temp);
                if(skill == null){
                    System.out.println("CharacterParser:characters(): Could not find skill: " + temp);
                    error = true;
                }
                else{
                    System.out.println("CharacterParser:characters(): Adding skill: " + temp);
                    charBuilder.addSkill(skill);
                }
                bSkill = false;
            }
            else if(bAtkElement){
                Element elm = Element.getElement(temp);
                if(elm == null){
                    System.out.println("CharacterParser:characters(): Could not find element: " + temp);
                    error = true;
                }
                else{
                    charBuilder.setAttackElement(elm);
                }
                bAtkElement = false;
            }
            else if(bStgElement){
                Element elm = Element.getElement(temp);
                if(elm == null){
                    System.out.println("CharacterParser:characters(): Could not find element: " + temp);
                    error = true;
                }
                else{
                    charBuilder.setStrongElement(elm);
                }
                bStgElement = false;
            }
            else if(bWkElement){
                Element elm = Element.getElement(temp);
                if(elm == null){
                    System.out.println("CharacterParser:characters(): Could not find element: " + temp);
                    error = true;
                }
                else{
                    charBuilder.setWeakElement(elm);
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
        if(charBuilder.getName().length() == 0){
            return false;
        }
        if(charBuilder.getStrength() <= 0){
            return false;
        }
        if(charBuilder.getMagic() <= 0){
            return false;
        }
        if(charBuilder.getWeapon() == null){
            return false;
        }
        /* For now, remove this check
        if(charBuilder.getAttackElement() == null || charBuilder.getStrongElement() == null | charBuilder.getWeakElement() == null){
            return false;
        }
        */
        if(error == true){
            return false;
        }
        return true;
    }
}
