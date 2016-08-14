package ultrapoulet.wifeygame.character;

import java.util.HashMap;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.battle.skills.AthleteSkill;
import ultrapoulet.wifeygame.battle.skills.BikerSkill;
import ultrapoulet.wifeygame.battle.skills.ChuuniSkill;
import ultrapoulet.wifeygame.battle.skills.DetectiveSkill;
import ultrapoulet.wifeygame.battle.skills.DullahanSkill;
import ultrapoulet.wifeygame.battle.skills.FujoshiSkill;
import ultrapoulet.wifeygame.battle.skills.GhostSkill;
import ultrapoulet.wifeygame.battle.skills.HyperSkill;
import ultrapoulet.wifeygame.battle.skills.KillerSkill;
import ultrapoulet.wifeygame.battle.skills.MagicalGirlSkill;
import ultrapoulet.wifeygame.battle.skills.MaidSkill;
import ultrapoulet.wifeygame.battle.skills.MasochistSkill;
import ultrapoulet.wifeygame.battle.skills.MechanicSkill;
import ultrapoulet.wifeygame.battle.skills.MediumSkill;
import ultrapoulet.wifeygame.battle.skills.MusicianSkill;
import ultrapoulet.wifeygame.battle.skills.NurseSkill;
import ultrapoulet.wifeygame.battle.skills.PravdaSkill;
import ultrapoulet.wifeygame.battle.skills.ProgrammerSkill;
import ultrapoulet.wifeygame.battle.skills.ProtagonistSkill;
import ultrapoulet.wifeygame.battle.skills.RacerSkill;
import ultrapoulet.wifeygame.battle.skills.RobotSkill;
import ultrapoulet.wifeygame.battle.skills.SadistSkill;
import ultrapoulet.wifeygame.battle.skills.SlugabedSkill;
import ultrapoulet.wifeygame.battle.skills.SportsManagerSkill;
import ultrapoulet.wifeygame.battle.skills.SurvivalSkill;
import ultrapoulet.wifeygame.battle.skills.TimeTravelerSkill;
import ultrapoulet.wifeygame.battle.skills.TrapSkill;
import ultrapoulet.wifeygame.battle.skills.TsundereSkill;
import ultrapoulet.wifeygame.battle.skills.VampireSkill;
import ultrapoulet.wifeygame.battle.skills.WitchSkill;

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
            case "Trap":
                return new TrapSkill(owner);
            case "Tsundere":
                return new TsundereSkill(owner);
            case "Fujoshi":
                return new FujoshiSkill(owner);
            case "Ghost":
                return new GhostSkill(owner);
            case "Medium":
                return new MediumSkill(owner);
            case "Robot":
                return new RobotSkill(owner);
            case "Detective":
                return new DetectiveSkill(owner);
            case "Dullahan":
                return new DullahanSkill(owner);
            case "Programmer":
                return new ProgrammerSkill(owner);
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
            case "Witch":
                return new WitchSkill(owner);
            case "Magical Girl":
                return new MagicalGirlSkill(owner);
            case "Time Traveler":
                return new TimeTravelerSkill(owner);
            case "Vampire":
                return new VampireSkill(owner);
            case "Survival Game Club President":
                return new SurvivalSkill(owner, true);
            case "Survival Game Club":
                return new SurvivalSkill(owner, false);
            case "Athlete":
                return new AthleteSkill(owner);
            case "Biker":
                return new BikerSkill(owner);
            case "Sports Manager":
                return new SportsManagerSkill(owner);
            case "Musician":
                return new MusicianSkill(owner);
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
        skillsList.put("ATHLETE", new SkillsEnum("Athlete", "Increases physical damage by 50%. Increases physical damage by 50% for each other Athlete wifey."));
        skillsList.put("BIKER", new SkillsEnum("Biker", "Each other Biker wifey increases combo hits by 1."));
        skillsList.put("CHUUNI", new SkillsEnum("Chuunibyou", "Increases special attack damage by 400%."));
        skillsList.put("DETECTIVE", new SkillsEnum("Detective", "Decreases damage by 50% at the start of a wave. Damage increases by 50% for each turn the wave lasts."));
        skillsList.put("DULLAHAN", new SkillsEnum("Dullahan", "Damage reduced by 25%. Each percentage point below 75% the enemy's health is increases damage by 4%."));
        skillsList.put("FUJOSHI", new SkillsEnum("Fujoshi", "Damage increased by 33% for each Trap wifey in party. Damage increased by a bonus 100% if there are more than two Trap wifeys."));
        skillsList.put("GHOST", new SkillsEnum("Ghost", "Physical damage taken reduced by 50%."));
        skillsList.put("HYPER", new SkillsEnum("Hyper", "Increases the number of combo attack hits by 1."));
        skillsList.put("KILLER", new SkillsEnum("Killer", "Defeating an enemy with this wifey increases damage by 100% for the rest of the battle."));
        skillsList.put("MAGICALGIRL", new SkillsEnum("Magical Girl", "Increases magical damage, healing, and special attack damage by 50%. Multiplier increases by 25% for each other Magical Girl wifey."));
        skillsList.put("MAID", new SkillsEnum("Maid", "Increases damage by 100% if this wifey healed the previous turn."));
        skillsList.put("MASOCHIST", new SkillsEnum("Masochist", "Increases damage up to 300% the lower this wifey's health is. Healing received reduced by 50%."));
        skillsList.put("MECHANIC", new SkillsEnum("Mechanic", "Healing a Robot wifey fully heals the Robot wifey."));
        skillsList.put("MEDIUM", new SkillsEnum("Medium", "Increases magical damage by 100% against Ghosts."));
        skillsList.put("MUSICIAN", new SkillsEnum("Musician", "Increases damage by 300% every eighth hit."));
        skillsList.put("NURSE", new SkillsEnum("Nurse", "Increases healing by 100%. Gains health at the start of every round."));
        skillsList.put("PRAVDA", new SkillsEnum("Pravda", "Increases damage by 100% for each other Pravda wifey."));
        skillsList.put("PRAVDAPRES", new SkillsEnum("Pravda President", "Increases damage by 200% for each other Pravda wifey."));
        skillsList.put("PROGRAMMER", new SkillsEnum("Programmer", "Increases damage by 75% each time this wifey attacks a robot. Resets at the start of the round."));
        skillsList.put("PROTAGONIST", new SkillsEnum("Protagonist", "When this wifey suffers lethal damage the first time, prevent the death and heal."));
        skillsList.put("RACER", new SkillsEnum("Racer", "Increases damage by 300% at the start of a wave. Damage decreases by 50% for each turn the wave lasts, to a minimum of 50% damage dealt."));
        skillsList.put("ROBOT", new SkillsEnum("Robot", "Decreases physical damage taken by 20%. Increases magical damage taken by 20%."));
        skillsList.put("SADIST", new SkillsEnum("Sadist", "Increases damage by 5% for each hit. The number of hits resets to 0 if an enemy is defeated by this wifey."));
        skillsList.put("SLUGABED", new SkillsEnum("Slugabed", "Increases damage by 500%. Reduces damage by 25% for each turn, to a minimum of 25% damage dealt."));
        skillsList.put("SPORTSMANAGER", new SkillsEnum("Sports Manager", "Increases physical damage by 5% for each Athlete wifey. Increases the physical damage of each Athlete wifey by 100%. This does not stack."));
        skillsList.put("SURVIVAL", new SkillsEnum("Survival Game Club", "Decreases physical, magical, and special damage taken by 1% each turn."));
        skillsList.put("SURVIVALPRES", new SkillsEnum("Survival Game Club President", "Decreases physical, magical, and special damage taken by 2% each turn."));
        skillsList.put("TIMETRAVELER", new SkillsEnum("Time Traveler", "When this wifey suffers lethal damage the first time, prevent the death and set health to the wifey's health at the start of the round."));
        skillsList.put("TRAP", new SkillsEnum("Trap", "TBD"));
        skillsList.put("TSUNDERE", new SkillsEnum("Tsundere", "Increases damage by 100% if there are only 4 party members, 200% if there are only 3, 300% if there are only 2, and 400% if this is the only wifey."));
        skillsList.put("VAMPIRE", new SkillsEnum("Vampire", "Dealing damage increases health by 10% of the damage dealt."));
        skillsList.put("WITCH", new SkillsEnum("Witch", "Increases magical damage by 20% each time a magical attack is used."));

        //Skills that need to be implemented
        skillsList.put("ROYALTY", new SkillsEnum("Royalty", "Royalty description"));
        skillsList.put("PILOT", new SkillsEnum("Pilot", "Pilot description"));
        skillsList.put("YANDERE", new SkillsEnum("Yandere", "Yandere description"));
        skillsList.put("STUCOPRES", new SkillsEnum("Student Council President", "Student Council President description"));
        skillsList.put("STUCO", new SkillsEnum("Student Council", "Student Council description"));
        skillsList.put("ANIMAL", new SkillsEnum("Animal Girl", "Animal description"));
        skillsList.put("ANGEL", new SkillsEnum("Angel", "Angel description"));
        skillsList.put("COOK", new SkillsEnum("Cook", "Cook description"));
        skillsList.put("SCIENTIST", new SkillsEnum("Scientist", "Scientist description"));
        skillsList.put("GODDESS", new SkillsEnum("Goddess", "Goddess description"));
        skillsList.put("FOODIE", new SkillsEnum("Foodie", "Foodie description"));
        skillsList.put("BIRDBRAIN", new SkillsEnum("Birdbrain", "Birdbrain description")); //Extra damage to bookworms
        skillsList.put("CLUMSY", new SkillsEnum("Clumsy", "Clumsy description"));
        skillsList.put("GAMER", new SkillsEnum("Gamer", "Gamer description"));
        skillsList.put("MARTIALARTSPRES", new SkillsEnum("Martial Arts Club President", "Martial Arts Club President description"));
        skillsList.put("MARTIALARTS", new SkillsEnum("Martial Artist", "Martial Artist description"));
        skillsList.put("DEMON", new SkillsEnum("Demon", "Demon description"));
        skillsList.put("TEACHER", new SkillsEnum("Teacher", "Teacher description"));
        skillsList.put("MONSTER", new SkillsEnum("Monster", "Monster description"));
        skillsList.put("MILITARY", new SkillsEnum("Military", "Military description"));
        skillsList.put("CRIMINAL", new SkillsEnum("Criminal", "Criminal description"));
        skillsList.put("BOOKWORM", new SkillsEnum("Bookworm", "Bookworm description"));
        skillsList.put("POLICE", new SkillsEnum("Police", "Police description")); //Increase damage to criminals
        skillsList.put("LOVECRAFT", new SkillsEnum("Lovecraft", "Lovecraft description"));
        skillsList.put("SELECTOR", new SkillsEnum("Selector", "Selector description"));
        skillsList.put("KUUDERE", new SkillsEnum("Kuudere", "Kuudere description"));
        skillsList.put("CAPTAIN", new SkillsEnum("Captain", "Captain description"));
        skillsList.put("WRITER", new SkillsEnum("Writer", "Writer description"));
        skillsList.put("PERSONA", new SkillsEnum("Persona", "Persona description"));
        skillsList.put("SPIRIT", new SkillsEnum("Spirit", "Spirit description"));
        skillsList.put("NINJA", new SkillsEnum("Ninja", "Ninja description"));
        skillsList.put("MAGICIAN", new SkillsEnum("Magician", "Magician description"));
    }
}
