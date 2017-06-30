package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.battle.requirements.AbsRequirement;
import ultrapoulet.wifeygame.battle.requirements.RequirementFactory;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.gamestate.Characters;
import ultrapoulet.wifeygame.gamestate.Enemies;
import ultrapoulet.wifeygame.gamestate.StoryArea;
import ultrapoulet.wifeygame.gamestate.StoryBattles;

/**
 * Created by John on 6/22/2016.
 */
public class BattleParser extends DefaultHandler {

    private boolean error = false;

    private BattleInfo battleBuilder;
    private AbsRequirement reqBuilder;
    private StoryArea areaBuilder;

    private String wifeyDrop;
    private int chanceDrop = 0;

    private String battleKey;
    private StringBuffer currentText = new StringBuffer();

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase("battles")){
            //Do nothing, but it's valid
        }
        else if(qName.equalsIgnoreCase("area")){
            String temp = attributes.getValue("name");
            if(temp == null || temp.length() == 0){
                System.out.println("BattleParser:startElement(): No area name provided");
                temp = "Invalid name";
            }
            areaBuilder = new StoryArea(temp);
        }
        else if(qName.equalsIgnoreCase("battle")){
            battleKey = attributes.getValue("key");
            battleBuilder = new BattleInfo();
            battleBuilder.setKey(battleKey);
        }
        else if(qName.equalsIgnoreCase("name")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("enemies")){
            //Do nothing, but it's valid
        }
        else if(qName.equalsIgnoreCase("enemy")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("partysize")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("requirements")){
            //Do nothing, but it's valid
        }
        else if(qName.equalsIgnoreCase("requirement")){
            //Create a new requirement
            reqBuilder = RequirementFactory.getRequirement(attributes.getValue("type"));
            if(reqBuilder == null){
                System.out.println("BattleParser:startElement(): Invalid Requirement: " + attributes.getValue("type"));
            }
            else{
                currentText = new StringBuffer();
            }
        }
        else if(qName.equalsIgnoreCase("value")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("background")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("energy")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("drops")){
            //Do nothing, valid
        }
        else if(qName.equalsIgnoreCase("drop")){
            wifeyDrop = null;
            chanceDrop = 0;
        }
        else if(qName.equalsIgnoreCase("wifey")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("chance")){
            currentText = new StringBuffer();
        }
        else{
            System.out.println("BattleParser:startElement(): Invalid qName: " + qName + " for key " + battleKey);
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if(qName.equalsIgnoreCase("area")){
            StoryBattles.addArea(areaBuilder);
            System.out.println("BattleParser:endElement(): Adding area: " + areaBuilder.getAreaName());
        }
        else if(qName.equalsIgnoreCase("battle")){
            if(validate()){
                areaBuilder.addBattle(battleBuilder);
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
        }
        else if(qName.equalsIgnoreCase("drop")){
            if(wifeyDrop == null || Characters.get(wifeyDrop) == null || chanceDrop <= 0 || chanceDrop > 100){
                System.out.println("BattleParser:endElement(): Invalid wifey drop provided. Ignoring");
            }
            else {
                battleBuilder.addDrop(Characters.get(wifeyDrop), chanceDrop);
            }
        }
        else if(qName.equalsIgnoreCase("name")){
            battleBuilder.setName(currentText.toString());
        }
        else if(qName.equalsIgnoreCase("enemy")){
            EnemyCharacter tempEn = Enemies.get(currentText.toString());
            if(tempEn == null){
                System.out.println("BattleParser:endElement(): BattleEnemy not found: " + currentText.toString());
                error = true;
            }
            else{
                battleBuilder.addEnemy(tempEn);
            }
        }
        else if(qName.equalsIgnoreCase("partysize")){
            try{
                battleBuilder.setPartyMax(Integer.parseInt(currentText.toString()));
            }
            catch(NumberFormatException e){
                System.out.println("BattleParser:endElement(): NumberFormatException for key: " + battleKey);
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("value")){
            if(reqBuilder != null){
                reqBuilder.addValue(currentText.toString());
            }
        }
        else if(qName.equalsIgnoreCase("background")){
            battleBuilder.setBackground(currentText.toString());
        }
        else if(qName.equalsIgnoreCase("energy")){
            try{
                battleBuilder.setEnergyRequirement(Integer.parseInt(currentText.toString()));
            }
            catch(NumberFormatException e){
                System.out.println("BattleParser:endElement(): NumberFormatException for key: " + battleKey);
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("wifey")){
            wifeyDrop = currentText.toString();
        }
        else if(qName.equalsIgnoreCase("chance")){
            try{
                chanceDrop = Integer.parseInt(currentText.toString());
            }
            catch(NumberFormatException e){
                System.out.println("BattleParser:endElement(): NumberFormatException for key: " + battleKey);
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
        return battleBuilder.getEnergyRequirement() != 0;

    }


}
