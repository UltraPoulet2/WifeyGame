package ultrapoulet.wifeygame.parsers;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

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

    private boolean bRequirement = false;

    private String battleKey;
    private BattleInfo battleBuilder;
    private AbsRequirement reqBuilder;

    private boolean error = false;

    private StringBuffer currentText = new StringBuffer();

    private ArrayList<String> errorKeys = new ArrayList<>();

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase("recruitbattles")){
            //Do nothing, but it's valid
        }
        else if(qName.equalsIgnoreCase("battle")){
            error = false;
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
                Log.e("RecruitingBattleParser", "Invalid Requirement: " + attributes.getValue("type"));
                bRequirement = false;
            }
            else{
                bRequirement = true;
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
        else if(qName.equalsIgnoreCase("recPower")){
            currentText = new StringBuffer();
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if(qName.equalsIgnoreCase("battle")){
            if(validate()){
                RecruitBattles.put(battleKey, battleBuilder);
                Log.i("RecruitingBattleParser", "Adding battle: " + battleKey);
            }
            else{
                Log.e("RecruitingBattleParser", "Error parsing for key: " + battleKey);
                errorKeys.add(battleKey != null ? battleKey : "INV-KEY");
            }
        }
        else if(qName.equalsIgnoreCase("requirement")){
            if(bRequirement) {
                if (reqBuilder != null) {
                    battleBuilder.addRequirement(reqBuilder);
                }
                reqBuilder = null;
                bRequirement = false;
            }
        }
        if(qName.equalsIgnoreCase("name")){
            battleBuilder.setName(currentText.toString());
        }
        else if(qName.equalsIgnoreCase("enemy")){
            EnemyCharacter tempEn = Enemies.get(currentText.toString());
            if(tempEn == null){
                Log.e("RecruitingBattleParser", "BattleEnemy not found: " + currentText.toString());
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
                Log.e("RecruitingBattleParser", "NumberFormatException for key: " + battleKey);
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
                Log.e("RecruitingBattleParser", "NumberFormatException for key: " + battleKey);
                error = true;
            }
        }
        else if(qName.equalsIgnoreCase("recPower")){
            try{
                battleBuilder.setRecommendedPower(Integer.parseInt(currentText.toString()));
            }
            catch(NumberFormatException e){
                Log.e("RecruitingBattleParser", "NumberFormatException for key: " + battleKey);
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
        if(error){
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
        if(battleBuilder.getEnergyRequirement() <= 0) {
            return false;
        }
        if(battleBuilder.getRecommendedPower() <= 0) {
            return false;
        }
        return true;
    }

    public int getNumberErrors(){
        return errorKeys.size();
    }

    public String getErrorString() {
        StringBuilder builder = new StringBuilder();
        builder.append("There was an error parsing the following Recruiting Battles:");
        for (String key : errorKeys) {
            builder.append("\n" + key);
        }
        return builder.toString();
    }
}
