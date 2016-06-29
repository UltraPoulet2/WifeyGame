package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.gamestate.Battles;
import ultrapoulet.wifeygame.gamestate.Enemies;

/**
 * Created by John on 6/22/2016.
 */
public class BattleParser extends DefaultHandler {

    private boolean bName = false;
    private boolean bEnemy = false;

    private boolean error = false;

    private BattleInfo battleBuilder;
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
    }

    private void resetValues(){
        error = false;
        bName = false;
        bEnemy = false;
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

        return true;
    }


}
