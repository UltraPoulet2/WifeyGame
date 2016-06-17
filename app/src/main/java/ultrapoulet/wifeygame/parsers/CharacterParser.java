package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 6/8/2016.
 */
public class CharacterParser extends DefaultHandler{

    private Graphics g;

    private boolean bName;
    private boolean bStrength;
    private boolean bMagic;

    private boolean error;

    private WifeyCharacter charBuilder;
    private String charKey;
    private String charName;
    private int charStrength;
    private int charMagic;
    private Image charImage;

    public void setGraphics(Graphics g){
        this.g = g;
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("character")) {
            resetValues();
            charKey = attributes.getValue("key");
            if(charKey != null) {
                charImage = g.newImage("characters/" + charKey + ".png", ImageFormat.RGB565);
            }
            else{
                System.out.println("CharacterParser:startElement(): Error parsing character key");
            }
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
        else if(qName.equalsIgnoreCase("characters")){
            //Do nothing.
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
                charBuilder = new WifeyCharacter(charKey, charName, charStrength, charMagic, charImage);
                //This will change
                RecruitedCharacters.put(charKey, charBuilder);
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
                charName = temp;
                bName = false;
            } else if (bStrength) {
                charStrength = Integer.parseInt(temp);
                bStrength = false;
            } else if (bMagic) {
                charMagic = Integer.parseInt(temp);
                bMagic = false;
            }
        }
        catch(NumberFormatException e){
            System.out.println("CharacterParser:characters(): NumberFormatException for key: " + charKey);
            error = true;
        }
    }

    private boolean validate(){
        if(charName.length() == 0){
            return false;
        }
        if(charStrength <= 0){
            return false;
        }
        if(charMagic <= 0){
            return false;
        }
        if(error == true){
            return false;
        }
        return true;
    }

    private void resetValues(){
        error = false;
        charName = "";
        charStrength = 0;
        charMagic = 0;
    }
}
