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
            charKey = attributes.getValue("key");
            charImage = g.newImage("characters/" + charKey + ".png", ImageFormat.RGB565);
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

    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if(qName.equalsIgnoreCase("character")){
            charBuilder = new WifeyCharacter(charKey, charName, charStrength, charMagic, charImage);
            //This will change
            RecruitedCharacters.put(charKey, charBuilder);
        }

    }

    @Override
    public void characters(char ch[],
                           int start,
                           int length) throws SAXException {
        String temp = new String(ch, start, length);
        if(bName){
            charName = temp;
            bName = false;
        }
        else if(bStrength){
            charStrength = Integer.parseInt(temp);
            bStrength = false;
        }
        else if(bMagic){
            charMagic = Integer.parseInt(temp);
            bMagic = false;
        }

    }
}
