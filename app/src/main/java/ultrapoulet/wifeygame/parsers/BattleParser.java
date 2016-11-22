package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.battle.requirements.AbsRequirement;
import ultrapoulet.wifeygame.battle.requirements.RequirementFactory;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.gamestate.Battles;
import ultrapoulet.wifeygame.gamestate.Enemies;
/**
 * Created by John on 6/22/2016.
 */
public class BattleParser extends DefaultHandler {

    private boolean bName = false;
    private boolean bEnemy = false;
    private boolean bPartySize = false;
    private boolean bRequirement = false;
    private boolean bRValue = false;

    private boolean error = false;

    private BattleInfo battleBuilder;
    private AbsRequirement reqBuilder;
    private String battleKey;

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase("battles")){
            //Do nothing, but it's valid
        }
        else if(qName.equalsIgnoreCase("battle")){
            resetValues();
            battleKey = attributes.getValue("key");
            battleBuilder = new BattleInfo();
        }
        else if(qName.equalsIgnoreCase("name")){
            bName = true;
        }
        else if(qName.equalsIgnoreCase("enemies")){
            //Do nothing, but it's valid
        }
        else if(qName.equalsIgnoreCase("enemy")){
            bEnemy = true;
        }
        else if(qName.equalsIgnoreCase("partysize")){
            bPartySize = true;
        }
        else if(qName.equalsIgnoreCase("requirements")){
            //Do nothing, but it's valid
        }
        else if(qName.equalsIgnoreCase("requirement")){
            //Create a new requirement
            reqBuilder = RequirementFactory.getRequirement(attributes.getValue("type"));
            if(reqBuilder == null){
                System.out.println("BattleParser:startElement(): Invalid Requirement: " + attributes.getValue("type"));
                bRequirement = false;
            }
            else{
                bRequirement = true;
            }
            bRequirement = true;
        }
        else if(qName.equalsIgnoreCase("value")){
            System.out.println("Creating value");
            bRValue = true;
        }
        else{
            System.out.println("BattleParser:startElement(): Invalid qName: " + qName + " for key " + battleKey);
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if(qName.equalsIgnoreCase("battle")){
            if(validate()){
                Battles.put(battleKey, battleBuilder);
                System.out.println("BattleParser:endElement(): Adding battle: " + battleKey);
            }
            else{
                System.out.println("BattleParser:endElement(): Error parsing for key: " + battleKey);
            }
        }
        else if(qName.equalsIgnoreCase("requirement")){
            if(reqBuilder != null){
                battleBuilder.addRequirement(reqBuilder);
            }
            reqBuilder = null;
            bRequirement = false;
        }
    }

    @Override
    public void characters(char ch[],
                           int start,
                           int length) throws SAXException {
        String temp = new String(ch, start, length);
        if(bName){
            battleBuilder.setName(temp);
            bName = false;
        }
        else if(bEnemy){
            EnemyCharacter tempEn = Enemies.get(temp);
            if(tempEn == null){
                System.out.println("BattleParser:characters(): BattleEnemy not found: " + temp);
                error = true;
            }
            else{
                battleBuilder.addEnemy(tempEn);
            }
            bEnemy = false;
        }
        else if(bPartySize){
            try{
                battleBuilder.setPartyMax(Integer.parseInt(temp));
                bPartySize = false;
            }
            catch(NumberFormatException e){
                System.out.println("BattleParser:characters(): NumberFormatException for key: " + battleKey);
                error = true;
                bPartySize = false;
            }
        }
        else if(bRValue){
            System.out.println("Value for requirement: " + temp);
            if(reqBuilder != null){
                reqBuilder.addValue(temp);
            }
            bRValue = false;
        }
    }

    private void resetValues(){
        error = false;
        bName = false;
        bEnemy = false;
        bRValue = false;
    }

    private boolean validate(){
        if(error == true){
            return false;
        }
        if(battleBuilder.getName().length() == 0){
            return false;
        }
        if(battleBuilder.getCharacterEnemies().length == 0){
            return false;
        }
        if(battleBuilder.getPartyMax() <= 0 || battleBuilder.getPartyMax() > 7){
            return false;
        }

        return true;
    }


}
