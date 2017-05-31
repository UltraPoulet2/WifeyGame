package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.battle.requirements.AbsRequirement;
import ultrapoulet.wifeygame.battle.requirements.RequirementFactory;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.gamestate.Enemies;
import ultrapoulet.wifeygame.gamestate.RecruitBattles;

/**
 * Created by John on 5/21/2017.
 */

public class RecruitingBattleParser extends DefaultHandler {

    private boolean bName = false;
    private boolean bEnemy = false;
    private boolean bPartySize = false;
    private boolean bRequirement = false;
    private boolean bRValue = false;
    private boolean bBackground = false;
    private boolean bEnergy = false;

    private String battleKey;
    private BattleInfo battleBuilder;
    private AbsRequirement reqBuilder;

    private boolean error = false;

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase("recruitbattles")){
            //Do nothing, but it's valid
        }
        else if(qName.equalsIgnoreCase("battle")){
            resetValues();
            battleKey = attributes.getValue("key");
            battleBuilder = new BattleInfo();
            battleBuilder.setKey(battleKey);
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
                System.out.println("RecruitingBattleParser:startElement(): Invalid Requirement: " + attributes.getValue("type"));
                bRequirement = false;
            }
            else{
                bRequirement = true;
            }
        }
        else if(qName.equalsIgnoreCase("value")){
            bRValue = true;
        }
        else if(qName.equalsIgnoreCase("background")){
            bBackground = true;
        }
        else if(qName.equalsIgnoreCase("energy")){
            bEnergy = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if(qName.equalsIgnoreCase("battle")){
            if(validate()){
                RecruitBattles.put(battleKey, battleBuilder);

                System.out.println("RecruitingBattleParser:endElement(): Adding battle: " + battleKey);
            }
            else{
                System.out.println("RecruitingBattleParser:endElement(): Error parsing for key: " + battleKey);
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
                System.out.println("RecruitingBattleParser:characters(): BattleEnemy not found: " + temp);
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
                System.out.println("RecruitingBattleParser:characters(): NumberFormatException for key: " + battleKey);
                error = true;
                bPartySize = false;
            }
        }
        else if(bRValue){
            if(reqBuilder != null){
                reqBuilder.addValue(temp);
            }
            bRValue = false;
        }
        else if(bBackground){
            battleBuilder.setBackground(temp);
            bBackground = false;
        }
        else if(bEnergy){
            try{
                battleBuilder.setEnergyRequirement(Integer.parseInt(temp));
                bEnergy = false;
            }
            catch(NumberFormatException e){
                System.out.println("RecruitingBattleParser:characters(): NumberFormatException for key: " + battleKey);
                error = true;
                bEnergy = false;
            }
        }
    }

    private void resetValues(){
        error = false;
        bName = false;
        bEnemy = false;
        bRValue = false;
        bBackground = false;
        bEnergy = false;
    }

    private boolean validate(){
        if(error == true){
            return false;
        }
        if(battleBuilder.getName().length() == 0){
            return false;
        }
        if(battleBuilder.getCharacterEnemies().size() == 0){
            return false;
        }
        if(battleBuilder.getPartyMax() <= 0 || battleBuilder.getPartyMax() > 7){
            return false;
        }
        if(battleBuilder.getBackgroundName() == null){
            return false;
        }
        if(battleBuilder.getEnergyRequirement() == 0){
            return false;
        }

        return true;
    }
}
