package ultrapoulet.wifeygame.parsers;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Characters;
import ultrapoulet.wifeygame.gamestate.RecruitBattles;
import ultrapoulet.wifeygame.gamestate.StoryBattles;
import ultrapoulet.wifeygame.recruiting.RecruitInfo;
import ultrapoulet.wifeygame.recruiting.RecruitRequirement;
import ultrapoulet.wifeygame.recruiting.RecruitRequirementBattle;
import ultrapoulet.wifeygame.recruiting.RecruitRequirementGold;
import ultrapoulet.wifeygame.recruiting.RecruitRequirementRecruitBattle;
import ultrapoulet.wifeygame.recruiting.RecruitRequirementWifey;
import ultrapoulet.wifeygame.recruiting.RecruitRequirementWifeyNumber;

/**
 * Created by John on 5/4/2017.
 */

public class RecruitingParser extends DefaultHandler {

    private boolean error = false;

    private RecruitInfo infoBuilder;
    private RecruitRequirement reqBuilder;
    private WifeyCharacter recruit;

    private String recKey;

    private StringBuffer currentText = new StringBuffer();

    private ArrayList<String> errorKeys = new ArrayList<>();

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("recruiting")) {
            //Valid. Do nothing
        }
        else if(qName.equalsIgnoreCase("character")){
            error = false;
            recKey = attributes.getValue("key");
            currentText = new StringBuffer();
            if(recKey != null){
                recruit = Characters.get(recKey);
            }
            if(recruit == null){
                error = true;
                //System.out.println("RecruitingParser:startElement(): Invalid character key: " + recKey);
                Log.e("RecruitingParser", "Invalid character key: " + recKey);
            }
            infoBuilder = new RecruitInfo();
        }
        else if(qName.equalsIgnoreCase("quote")){
            currentText = new StringBuffer();
        }
        else if(qName.equalsIgnoreCase("requirements")){
            //Do nothing
        }
        else if(qName.equalsIgnoreCase("requirement")){
            reqBuilder = null;
            String type = attributes.getValue("type");
            currentText = new StringBuffer();
            if(type == null){
                //System.out.println("RecruitingParser:startElement(): No requirement type provided.");
                Log.e("RecruitingParser", "No requirement type provided.");
                error = true;
                return;
            }
            switch(type){
                case "wifey":
                    reqBuilder = new RecruitRequirementWifey();
                    break;
                case "wifeyNumber":
                    String skill = attributes.getValue("skill");
                    reqBuilder = new RecruitRequirementWifeyNumber(skill);
                    break;
                case "battle":
                    reqBuilder = new RecruitRequirementBattle();
                    break;
                case "gold":
                    reqBuilder = new RecruitRequirementGold();
                    break;
                case "recruitBattle":
                    reqBuilder = new RecruitRequirementRecruitBattle();
                    break;
                default:
                    //System.out.println("RecruitingParser:startElement(): Invalid requirement type: " + type);
                    Log.e("RecruitingParser", "Invalid requirement type: " + type);
                    error = true;
                    break;
            }
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        if(qName.equalsIgnoreCase("character")){
            if(infoBuilder != null && !error){
                recruit.setRecruitingInfo(infoBuilder);
            }
            if(error){
                errorKeys.add(recKey != null ? recKey : "INV-KEY");
            }
        }
        else if(qName.equalsIgnoreCase("quote")){
            infoBuilder.setQuote(currentText.toString());
        }
        else if(qName.equalsIgnoreCase("requirement")){
            if(reqBuilder == null){
                return;
            }
            if(reqBuilder instanceof RecruitRequirementWifey){
                WifeyCharacter wifey = Characters.get(currentText.toString());
                ((RecruitRequirementWifey) reqBuilder).setRequiredWifey(wifey);
            }
            else if(reqBuilder instanceof RecruitRequirementWifeyNumber){
                try{
                    ((RecruitRequirementWifeyNumber) reqBuilder).setNumber(Integer.parseInt(currentText.toString()));
                }
                catch(NumberFormatException e) {
                    if(recruit != null) {
                        //System.out.println("RecruitingParser:endElement(): NumberFormatException for key: " + recruit.getHashKey());
                        Log.e("RecruitingParser", "NumberFormatException for key: " + recruit.getHashKey());
                    }
                    else {
                        //System.out.println("RecruitingParser:endElement(): NumberFormatException for unknown key.");
                        Log.e("RecruitingParser", "NumberFormatException for unknown key.");
                    }
                }
            }
            else if(reqBuilder instanceof RecruitRequirementBattle){
                BattleInfo info = StoryBattles.getBattle(currentText.toString());
                ((RecruitRequirementBattle) reqBuilder).setRequiredBattle(info);
            }
            else if(reqBuilder instanceof RecruitRequirementRecruitBattle){
                BattleInfo info = RecruitBattles.getBattle(currentText.toString());
                ((RecruitRequirementRecruitBattle) reqBuilder).setRequiredBattle(info);
            }
            else if(reqBuilder instanceof RecruitRequirementGold){
                try{
                    ((RecruitRequirementGold) reqBuilder).setGoldAmount(Integer.parseInt(currentText.toString()));
                }
                catch(NumberFormatException e) {
                    if(recruit != null) {
                        //System.out.println("RecruitingParser:endElement(): NumberFormatException for key: " + recruit.getHashKey());
                        Log.e("RecruitingParser", "NumberFormatException for key: " + recruit.getHashKey());
                    }
                    else {
                        //System.out.println("RecruitingParser:endElement(): NumberFormatException for unknown key.");
                        Log.e("RecruitingParser", "NumberFormatException for unknown key.");
                    }
                }
            }
            if(reqBuilder.validate()){
                infoBuilder.addRequirement(reqBuilder);
            }
            else {
                errorKeys.add(recKey != null ? recKey : "INV-KEY");
            }
        }
    }

    @Override
    public void characters(char ch[],
                           int start,
                           int length) throws SAXException {
        currentText.append(new String(ch, start, length));
    }

    public int getNumberErrors(){
        return errorKeys.size();
    }

    public String getErrorString() {
        StringBuilder builder = new StringBuilder();
        builder.append("There was an error parsing the following Recruiting Requirements:");
        for (String key : errorKeys) {
            builder.append("\n" + key);
        }
        return builder.toString();
    }
}
