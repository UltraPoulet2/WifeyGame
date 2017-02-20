package ultrapoulet.wifeygame.character;

import java.util.Comparator;
import java.util.HashMap;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.AthleteSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.BikerSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.ChuuniSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.DetectiveSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.DullahanSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.FujoshiSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.GhostSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.HyperSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.KillerSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.MagicalGirlSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.MagicianSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.MaidSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.MasochistSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.MechanicSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.MediumSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.MusicianSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.NurseSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.PilotSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.PravdaSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.ProgrammerSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.ProtagonistSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.RacerSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.RemSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.RobotSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.SadistSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.SlugabedSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.SportsManagerSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.SurvivalSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.TimeTravelerSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.TrapSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.TsundereSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.VampireSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.WitchSkill;

/**
 * Created by John on 7/14/2016.
 */
public class SkillsEnum {

    private String skillName;
    private String skillDesc;

    public static Comparator<SkillsEnum> SKILLS_ENUM_COMPARATOR = new Comparator<SkillsEnum>(){
        @Override
        public int compare(SkillsEnum a, SkillsEnum b) {
            return a.getSkillName().compareTo(b.getSkillName());
        }
    };

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
            case "Athlete":
                return new AthleteSkill(owner);
            case "Biker":
                return new BikerSkill(owner);
            case "Chuunibyou":
                return new ChuuniSkill(owner);
            case "Detective":
                return new DetectiveSkill(owner);
            case "Dullahan":
                return new DullahanSkill(owner);
            case "Fujoshi":
                return new FujoshiSkill(owner);
            case "Ghost":
                return new GhostSkill(owner);
            case "Hyper":
                return new HyperSkill(owner);
            case "Killer":
                return new KillerSkill(owner);
            case "Magical Girl":
                return new MagicalGirlSkill(owner);
            case "Magician":
                return new MagicianSkill(owner);
            case "Maid":
                return new MaidSkill(owner);
            case "Masochist":
                return new MasochistSkill(owner);
            case "Mechanic":
                return new MechanicSkill(owner);
            case "Medium":
                return new MediumSkill(owner);
            case "Musician":
                return new MusicianSkill(owner);
            case "Nurse":
                return new NurseSkill(owner);
            case "Pilot":
                return new PilotSkill(owner);
            case "Pravda":
                return new PravdaSkill(owner, false);
            case "Pravda President":
                return new PravdaSkill(owner, true);
            case "Programmer":
                return new ProgrammerSkill(owner);
            case "Protagonist":
                return new ProtagonistSkill(owner);
            case "Racer":
                return new RacerSkill(owner);
            case "Robot":
                return new RobotSkill(owner);
            case "Sabagebu":
                return new SurvivalSkill(owner, false);
            case "Sabagebu President":
                return new SurvivalSkill(owner, true);
            case "Sadist":
                return new SadistSkill(owner);
            case "Slugabed":
                return new SlugabedSkill(owner);
            case "Sports Manager":
                return new SportsManagerSkill(owner);
            case "Time Traveler":
                return new TimeTravelerSkill(owner);
            case "Trap":
                return new TrapSkill(owner);
            case "Tsundere":
                return new TsundereSkill(owner);
            case "Vampire":
                return new VampireSkill(owner);
            case "Witch":
                return new WitchSkill(owner);
            case "Rem":
                return new RemSkill(owner);
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
        skillsList.put("ATHLETE", new SkillsEnum("Athlete", "Multiplies physical damage dealt by 1.50x. Increases multiplier by 0.50x for each other Athlete wifey in the party."));
        skillsList.put("BIKER", new SkillsEnum("Biker", "Increases combo hits by 1 for each other Biker wifey in the party."));
        skillsList.put("CHUUNI", new SkillsEnum("Chuunibyou", "Multiplies special attack damage dealt by 5.00x."));
        skillsList.put("DETECTIVE", new SkillsEnum("Detective", "Multiplies damage dealt by 0.50x at the start of a wave. Multiplier increases by 0.25x at the end of each turn, up to a maximum of 5.00x."));
        skillsList.put("DULLAHAN", new SkillsEnum("Dullahan", "Multiplies damage dealt by 0.75x. Each percentage point below 75% the enemy's health is at increases the multiplier by 0.40x."));
        skillsList.put("FUJOSHI", new SkillsEnum("Fujoshi", "Increases damage dealt multiplier by 0.33x for each Trap wifey in the party. Damage increased by a bonus 1.00x if there are more than two Trap wifeys."));
        skillsList.put("GHOST", new SkillsEnum("Ghost", "Multiplies physical attack damage taken by 0.50x"));
        skillsList.put("HYPER", new SkillsEnum("Hyper", "Increases combo hits by 1."));
        skillsList.put("KILLER", new SkillsEnum("Killer", "Defeating an enemy with this wifey increases damage dealt multiplier by 1.00x for the rest of the battle."));
        skillsList.put("MAGICALGIRL", new SkillsEnum("Magical Girl", "Multiplies magical damage dealt, healing, and special attack damage dealt by 1.50x. Multiplier increases by 0.25x for each other Magical Girl wifey in the party."));
        skillsList.put("MAGICIAN", new SkillsEnum("Magician", "Multiplies magical damage dealt by 3.00x at the start of a wave. Multiplier decreases by 0.50x each time a magical attack is used, to a minimum of 1.00x."));
        skillsList.put("MAID", new SkillsEnum("Maid", "Multiplies damage dealt by 2.00x if this wifey healed the previous turn."));
        skillsList.put("MASOCHIST", new SkillsEnum("Masochist", "Multiplies damage dealt up to 4.00x the lower this wifey's health is. Healing received multiplied by 0.50x."));
        skillsList.put("MECHANIC", new SkillsEnum("Mechanic", "Fully heals Robot wifeys."));
        skillsList.put("MEDIUM", new SkillsEnum("Medium", "Multiplies magical and special damage dealt by 2.00x against Ghosts."));
        skillsList.put("MUSICIAN", new SkillsEnum("Musician", "Multiplies damage dealt by 4.00x every eighth hit."));
        skillsList.put("NURSE", new SkillsEnum("Nurse", "Multiplies healing by 2.00x. Gains health at the start of every round."));
        skillsList.put("PILOT", new SkillsEnum("Pilot", "Multiplies magical and special damage taken from AIR sources by 0.50x"));
        skillsList.put("PRAVDA", new SkillsEnum("Pravda", "Increases damage dealt multiplier by 1.00x for each other Pravda wifey."));
        skillsList.put("PRAVDAPRES", new SkillsEnum("Pravda President", "Increases damage dealt multiplier by 2.00x for each other Pravda wifey."));
        skillsList.put("PROGRAMMER", new SkillsEnum("Programmer", "Increases damage dealt multiplier by 0.75x each time this wifey attacks a Robot. Resets at the start of the round."));
        skillsList.put("PROTAGONIST", new SkillsEnum("Protagonist", "When this wifey suffers lethal damage the first time, prevent the death and heal self."));
        skillsList.put("RACER", new SkillsEnum("Racer", "Multiplies damage dealt by 4.00x at the start of a wave. Multiplier decreases by 0.50x for each turn the wave lasts, to a minimum of 0.50x."));
        skillsList.put("ROBOT", new SkillsEnum("Robot", "Multiplies physical damage taken by 0.80x. Multiplies magical and special damage taken from WATER sources by 1.50x."));
        skillsList.put("SADIST", new SkillsEnum("Sadist", "Increases damage dealt multiplier by 0.05x for each hit. The number of hits resets to 0 if an enemy is defeated by this wifey."));
        skillsList.put("SLUGABED", new SkillsEnum("Slugabed", "Multiplies damage dealt by 6.00x. Reduces multiplier by 0.25x for each turn, to a minimum of 0.25x."));
        skillsList.put("SPORTSMANAGER", new SkillsEnum("Sports Manager", "Increases physical damage dealt multiplier by 0.05x for each Athlete wifey. Increases the physical damage multiplier of each Athlete wifey by 1.00x. This does not stack."));
        skillsList.put("SURVIVAL", new SkillsEnum("Sabagebu", "Decreases physical, magical, and special damage taken multiplier by 0.01x each turn."));
        skillsList.put("SURVIVALPRES", new SkillsEnum("Sabagebu President", "Decreases physical, magical, and special damage taken multiplier by 0.02x each turn."));
        skillsList.put("TIMETRAVELER", new SkillsEnum("Time Traveler", "When this wifey suffers lethal damage the first time, prevent the death and set health to the wifey's health at the start of the round."));
        skillsList.put("TRAP", new SkillsEnum("Trap", "Multiplies physical, magical, and special damage taken by 0.15x."));
        skillsList.put("TSUNDERE", new SkillsEnum("Tsundere", "Multiplies damage dealt by 2.00x if there are only 3 party members, 3.00x if there are only 2, 4.00x if this is the only wifey."));
        skillsList.put("VAMPIRE", new SkillsEnum("Vampire", "Dealing damage heals self by 10% of the damage dealt."));
        skillsList.put("WITCH", new SkillsEnum("Witch", "Increases magical damage dealt multiplier by 0.20x each time a magical attack is used."));

        skillsList.put("REM", new SkillsEnum("Rem", "Rem gets a special skill because she is Rem. Multiplies damage dealt and healing by 2.00x.")); //This skill will be removed.

        //Skills that need to be implemented
        skillsList.put("ROYALTY", new SkillsEnum("Royalty", "Royalty description"));
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
    }
}
