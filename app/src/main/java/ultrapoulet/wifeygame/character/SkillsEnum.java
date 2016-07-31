package ultrapoulet.wifeygame.character;

import java.util.HashMap;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.battle.skills.ChuuniSkill;
import ultrapoulet.wifeygame.battle.skills.DullahanSkill;
import ultrapoulet.wifeygame.battle.skills.GhostSkill;
import ultrapoulet.wifeygame.battle.skills.HyperSkill;
import ultrapoulet.wifeygame.battle.skills.KillerSkill;
import ultrapoulet.wifeygame.battle.skills.MaidSkill;
import ultrapoulet.wifeygame.battle.skills.MasochistSkill;
import ultrapoulet.wifeygame.battle.skills.MechanicSkill;
import ultrapoulet.wifeygame.battle.skills.MediumSkill;
import ultrapoulet.wifeygame.battle.skills.NurseSkill;
import ultrapoulet.wifeygame.battle.skills.PravdaSkill;
import ultrapoulet.wifeygame.battle.skills.ProtagonistSkill;
import ultrapoulet.wifeygame.battle.skills.RacerSkill;
import ultrapoulet.wifeygame.battle.skills.RobotSkill;
import ultrapoulet.wifeygame.battle.skills.SadistSkill;
import ultrapoulet.wifeygame.battle.skills.SlugabedSkill;
import ultrapoulet.wifeygame.battle.skills.TsundereSkill;

/**
 * Created by John on 7/14/2016.
 */
public class SkillsEnum {

    private String skillName;
    private String skillDesc;

    private SkillsEnum(String skillName, String skillDesc){
        this.skillName = skillName;
        this.skillDesc = skillDesc;
    }

    public String getSkillName(){
        return this.skillName;
    }

    public String getSkillDesc(){
        return this.skillDesc;
    }

    public AbsSkill getBattleSkill(BattleCharacter owner){
        switch(this.skillName){
            case "Sadist":
                return new SadistSkill(owner);
            case "Masochist":
                return new MasochistSkill(owner);
            case "Tsundere":
                return new TsundereSkill(owner);
            case "Ghost":
                return new GhostSkill(owner);
            case "Medium":
                return new MediumSkill(owner);
            case "Robot":
                return new RobotSkill(owner);
            case "Dullahan":
                return new DullahanSkill(owner);
            case "Protagonist":
                return new ProtagonistSkill(owner);
            case "Pravda President":
                return new PravdaSkill(owner, true);
            case "Pravda":
                return new PravdaSkill(owner, false);
            case "Mechanic":
                return new MechanicSkill(owner);
            case "Nurse":
                return new NurseSkill(owner);
            case "Slugabed":
                return new SlugabedSkill(owner);
            case "Racer":
                return new RacerSkill(owner);
            case "Killer":
                return new KillerSkill(owner);
            case "Chuunibyou":
                return new ChuuniSkill(owner);
            case "Maid":
                return new MaidSkill(owner);
            case "Hyper":
                return new HyperSkill(owner);
            default:
                System.out.println("SkillsEnum:getBattleSkill(): Skill not implemented: " + this.skillName);
                return new AbsSkill(owner);
        }
    }

    public static SkillsEnum getSkill(String skillName){
        if(skillsList == null){
            createSkillList();
        }
        return skillsList.get(skillName);
    }

    private static HashMap<String, SkillsEnum> skillsList;

    private static void createSkillList(){
        skillsList = new HashMap<>();
        skillsList.put("SADIST", new SkillsEnum("Sadist", "Sadist description"));
        skillsList.put("MASOCHIST", new SkillsEnum("Masochist", "Masochist description"));
        skillsList.put("TSUNDERE", new SkillsEnum("Tsundere", "Tsundere description"));
        skillsList.put("GHOST", new SkillsEnum("Ghost", "Ghost description"));
        skillsList.put("MEDIUM", new SkillsEnum("Medium", "Medium description"));
        skillsList.put("ROBOT", new SkillsEnum("Robot", "Robot description"));
        skillsList.put("DULLAHAN", new SkillsEnum("Dullahan", "Dullahan description"));
        skillsList.put("PROTAGONIST", new SkillsEnum("Protagonist", "Protagonist description"));
        skillsList.put("PRAVDAPRES", new SkillsEnum("Pravda President", "Pravda President description"));
        skillsList.put("PRAVDA", new SkillsEnum("Pravda", "Pravda description"));
        skillsList.put("MECHANIC", new SkillsEnum("Mechanic", "Mechanic description"));
        skillsList.put("NURSE", new SkillsEnum("Nurse", "Nurse description"));
        skillsList.put("SLUGABED", new SkillsEnum("Slugabed", "Slugabed description"));
        skillsList.put("RACER", new SkillsEnum("Racer", "Racer description"));
        skillsList.put("KILLER", new SkillsEnum("Killer", "Killer description"));
        skillsList.put("CHUUNI", new SkillsEnum("Chuunibyou", "Chuunibyou description"));
        skillsList.put("MAID", new SkillsEnum("Maid", "Maid description"));
        skillsList.put("HYPER", new SkillsEnum("Hyper", "Hyper description"));

        //Skills that need to be implemented
        skillsList.put("ROYALTY", new SkillsEnum("Royalty", "Royalty description"));
        skillsList.put("PILOT", new SkillsEnum("Pilot", "Pilot description"));
        skillsList.put("WITCH", new SkillsEnum("Witch", "Witch description"));
        skillsList.put("YANDERE", new SkillsEnum("Yandere", "Yandere description"));
        skillsList.put("STUCOPRES", new SkillsEnum("Student Council President", "Student Council President description"));
        skillsList.put("STUCO", new SkillsEnum("Student Council", "Student Council description"));
        skillsList.put("ANIMAL", new SkillsEnum("Animal Girl", "Animal description"));
        skillsList.put("BIKER", new SkillsEnum("Biker", "Biker description"));
        skillsList.put("ANGEL", new SkillsEnum("Angel", "Angel description"));
        skillsList.put("COOK", new SkillsEnum("Cook", "Cook description"));
        skillsList.put("MUSICIAN", new SkillsEnum("Musician", "Musician description"));
        skillsList.put("SPORTSMANAGER", new SkillsEnum("Sports Manager", "Sports Manager description"));
        skillsList.put("TRAP", new SkillsEnum("Trap", "Trap description"));
        skillsList.put("SCIENTIST", new SkillsEnum("Scientist", "Scientist description"));
        skillsList.put("MAGICALGIRL", new SkillsEnum("Magical Girl", "Magical Girl description"));
        skillsList.put("TIMETRAVELER", new SkillsEnum("Time Traveler", "Time Traveler description"));
        skillsList.put("GODDESS", new SkillsEnum("Goddess", "Goddess description"));
        skillsList.put("FOODIE", new SkillsEnum("Foodie", "Foodie description"));
        skillsList.put("BIRDBRAIN", new SkillsEnum("Birdbrain", "Birdbrain description")); //Extra damage to bookworms
        skillsList.put("CLUMSY", new SkillsEnum("Clumsy", "Clumsy description"));
        skillsList.put("GAMER", new SkillsEnum("Gamer", "Gamer description"));
        skillsList.put("DETECTIVE", new SkillsEnum("Detective", "Detective description"));
        skillsList.put("MARTIALARTSPRES", new SkillsEnum("Martial Arts Club President", "Martial Arts Club President description"));
        skillsList.put("MARTIALARTS", new SkillsEnum("Martial Artist", "Martial Artist description"));
        skillsList.put("DEMON", new SkillsEnum("Demon", "Demon description"));
        skillsList.put("TEACHER", new SkillsEnum("Teacher", "Teacher description"));
        skillsList.put("MONSTER", new SkillsEnum("Monster", "Monster description"));
        skillsList.put("MILITARY", new SkillsEnum("Military", "Military description"));
        skillsList.put("SURVIVALPRES", new SkillsEnum("Survival Game Club President", "Survival Game Club President description"));
        skillsList.put("SURVIVAL", new SkillsEnum("Survival Game Club", "Survival Game Club description"));
        skillsList.put("CRIMINAL", new SkillsEnum("Criminal", "Criminal description"));
        skillsList.put("BOOKWORM", new SkillsEnum("Bookworm", "Bookworm description"));
        skillsList.put("POLICE", new SkillsEnum("Police", "Police description"));
        skillsList.put("LOVECRAFT", new SkillsEnum("Lovecraft", "Lovecraft description"));
        skillsList.put("SELECTOR", new SkillsEnum("Selector", "Selector description"));
        skillsList.put("ATHLETE", new SkillsEnum("Athlete", "Athlete description"));
        skillsList.put("KUUDERE", new SkillsEnum("Kuudere", "Kuudere description"));
        skillsList.put("CAPTAIN", new SkillsEnum("Captain", "Captain description"));
        skillsList.put("WRITER", new SkillsEnum("Writer", "Writer description"));
        skillsList.put("PERSONA", new SkillsEnum("Persona", "Persona description"));
        skillsList.put("SPIRIT", new SkillsEnum("Spirit", "Spirit description"));
        skillsList.put("FUJOSHI", new SkillsEnum("Fujoshi", "Fujoshi description"));
        skillsList.put("VAMPIRE", new SkillsEnum("Vampire", "Vampire description"));
        skillsList.put("PROGRAMMER", new SkillsEnum("Programmer", "Programmer description"));
        skillsList.put("NINJA", new SkillsEnum("Ninja", "Ninja description"));
    }
}
