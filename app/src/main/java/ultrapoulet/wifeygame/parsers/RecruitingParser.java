package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Characters;
import ultrapoulet.wifeygame.recruiting.RecruitInfo;

/**
 * Created by John on 5/4/2017.
 */

public class RecruitingParser extends DefaultHandler {

    private boolean bQuote;
    private boolean bSteps;
    private boolean bStep;

    private boolean error = false;

    private RecruitInfo infoBuilder;
    private WifeyCharacter recruit;

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("recruiting")) {
            //Valid. Do nothing
        }
        else if(qName.equalsIgnoreCase("character")){
            String key = attributes.getValue("key");
            resetValues();
            if(key != null){
                recruit = Characters.get(key);
            }
            if(recruit == null){
                error = true;
                System.out.println("RecruitingParser:startElement(): Invalid character key: " + key);
            }
            infoBuilder = new RecruitInfo();
        }
        else if(qName.equalsIgnoreCase("quote")){
            bQuote = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if(qName.equalsIgnoreCase("character")){
            if(infoBuilder != null){
                recruit.setRecruitingInfo(infoBuilder);
            }
        }
    }

    @Override
    public void characters(char ch[],
                           int start,
                           int length) throws SAXException {
        String temp = new String(ch, start, length);
        if(bQuote){
            infoBuilder.setQuote(temp);
            bQuote = false;
        }
    }

    private void resetValues(){
        infoBuilder = null;
        recruit = null;
        bQuote = false;
        bSteps = false;
        bStep = false;
        error = false;
    }
}
