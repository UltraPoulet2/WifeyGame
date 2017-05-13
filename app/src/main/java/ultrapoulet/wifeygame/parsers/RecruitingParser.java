package ultrapoulet.wifeygame.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Battles;
import ultrapoulet.wifeygame.gamestate.Characters;
import ultrapoulet.wifeygame.recruiting.RecruitInfo;
import ultrapoulet.wifeygame.recruiting.RecruitRequirement;
import ultrapoulet.wifeygame.recruiting.RecruitRequirementBattle;
import ultrapoulet.wifeygame.recruiting.RecruitRequirementWifey;
import ultrapoulet.wifeygame.recruiting.RecruitRequirementWifeyNumber;

/**
 * Created by John on 5/4/2017.
 */

public class RecruitingParser extends DefaultHandler {

    private boolean bQuote;
    private boolean bRequirements;
    private boolean bRequirement;

    private boolean error = false;

    private RecruitInfo infoBuilder;
    private RecruitRequirement reqBuilder;
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
        else if(qName.equalsIgnoreCase("requirements")){
            //Do nothing
        }
        else if(qName.equalsIgnoreCase("requirement")){
            bRequirement = true;
            reqBuilder = null;
            String type = attributes.getValue("type");
            if(type == null){
                System.out.println("RecruitingParser:startElement(): No requirement type provided.");
                bRequirement = false;
                return;
            }
            switch(type){
                case "wifey":
                    reqBuilder = new RecruitRequirementWifey();
                    break;
                case "wifeyNumber":
                    String skill = attributes.getValue("skill");
                    SkillsEnum skillEnum = SkillsEnum.getSkill(skill);
                    if(skill != null && skillEnum == null){
                        System.out.println("RecruitingParser:startElement(): Could not find skill: " + skill);
                    }
                    reqBuilder = new RecruitRequirementWifeyNumber(skillEnum);
                    break;
                case "battle":
                    reqBuilder = new RecruitRequirementBattle();
                    break;
                default:
                    System.out.println("RecruitingParser:startElement(): Invalid requirement type: " + type);
                    bRequirement = false;
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
        }
        else if(qName.equalsIgnoreCase("requirement")){
            if(reqBuilder != null && reqBuilder.validate()){
                infoBuilder.addRequirement(reqBuilder);
                System.out.println("Added a requirement");
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
        else if(bRequirement){
            if(reqBuilder == null){
                bRequirement = false;
                return;
            }
            if(reqBuilder instanceof RecruitRequirementWifey){
                WifeyCharacter wifey = Characters.get(temp);
                ((RecruitRequirementWifey) reqBuilder).setRequiredWifey(wifey);
                bRequirement = false;
            }
            else if(reqBuilder instanceof RecruitRequirementWifeyNumber){
                try{
                    ((RecruitRequirementWifeyNumber) reqBuilder).setNumber(Integer.parseInt(temp));
                }
                catch(NumberFormatException e) {
                    if(recruit != null) {
                        System.out.println("RecruitingParser:characters(): NumberFormatException for key: " + recruit.getHashKey());
                    }
                    else {
                        System.out.println("RecruitingParser:characters(): NumberFormatException for unknown key.");
                    }
                }
                bRequirement = false;
            }
            else if(reqBuilder instanceof RecruitRequirementBattle){
                BattleInfo info = Battles.get(temp);
                ((RecruitRequirementBattle) reqBuilder).setRequiredBattle(info);
                bRequirement = false;
            }
        }
    }

    private void resetValues(){
        infoBuilder = null;
        recruit = null;
        bQuote = false;
        bRequirements = false;
        bRequirement = false;
        error = false;
    }
}
