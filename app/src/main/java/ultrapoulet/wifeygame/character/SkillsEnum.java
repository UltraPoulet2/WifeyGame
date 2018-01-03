package ultrapoulet.wifeygame.character;

import java.util.Comparator;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.AthleteSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.BikerSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.ChuuniSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.DetectiveSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.DullahanSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.FujoshiSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.GhostSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.HyperSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.IdolSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.KillerSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.MagicalGirlSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.MagicianSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.MaidSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.MasochistSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.MechanicSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.MediumSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.MusicianSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.NurseSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.PilotSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.PoliceSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.PravdaSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.ProgrammerSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.ProtagonistSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.RacerSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.RobotSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.SadistSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.SlugabedSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.SportsManagerSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.SurvivalSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.TeacherSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.TimeTravelerSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.TrapSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.TsundereSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.VampireSkill;
import ultrapoulet.wifeygame.battle.skills.basicskills.WitchSkill;

/**
 * Created by John on 7/14/2016.
 */
public enum SkillsEnum {
    ATHLETE("Athlete", "Multiplies physical damage dealt by 1.50x. Increases multiplier by 0.50x for each other Athlete wifey in the party."){
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new AthleteSkill(owner);
        }
    },
    BIKER("Biker", "Increases combo hits by 1 for each other Biker wifey in the party."){
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new BikerSkill(owner);
        }
    },
    CHUUNI("Chuunibyou", "Multiplies special attack damage dealt by 5.00x."){
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new ChuuniSkill(owner);
        }
    },
    DETECTIVE("Detective", "Multiplies damage dealt by 1.00x at the start of a wave. Multiplier increases by 0.50x at the end of each turn, up to a maximum of 5.00x.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new DetectiveSkill(owner);
        }
    },
    DULLAHAN("Dullahan", "Multiplies damage dealt by 0.50x. Each percentage point below 75% the enemy's health is at increases the multiplier by 0.40x."){
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new DullahanSkill(owner);
        }
    },
    FUJOSHI("Fujoshi", "Increases damage dealt multiplier by 0.33x for each Trap wifey in the party. Damage increased by a bonus 1.00x if there are more than two Trap wifeys."){
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new FujoshiSkill(owner);
        }
    },
    GHOST("Ghost", "Increases physical attack defense by 0.50x."){
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new GhostSkill(owner);
        }
    },
    HYPER("Hyper", "Increases combo hits by 1.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new HyperSkill(owner);
        }
    },
    IDOL("Idol", "Defeating an enemy with this wifey increases chance to find a wifey after the battle by 2%.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new IdolSkill(owner);
        }
    },
    KILLER("Killer", "Defeating an enemy with this wifey increases damage dealt multiplier by 1.00x for the rest of the battle.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new KillerSkill(owner);
        }
    },
    MAGICALGIRL("Magical Girl", "Multiplies magical damage dealt, healing, and special attack damage dealt by 1.50x. Multiplier increases by 0.25x for each other Magical Girl wifey in the party.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new MagicalGirlSkill(owner);
        }
    },
    MAGICIAN("Magician", "Multiplies magical damage dealt by 3.00x at the start of a wave. Multiplier decreases by 0.50x each time a magical attack is used, to a minimum of 1.00x.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new MagicianSkill(owner);
        }
    },
    MAID("Maid", "Multiplies damage dealt by 2.00x if this wifey healed the previous turn.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new MaidSkill(owner);
        }
    },
    MASOCHIST("Masochist", "Multiplies damage dealt up to 4.00x the lower this wifey's health is. Healing received multiplied by 0.50x.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new MasochistSkill(owner);
        }
    },
    MECHANIC("Mechanic", "Fully heals Robot wifeys.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new MechanicSkill(owner);
        }
    },
    MEDIUM("Medium", "Multiplies magical and special damage dealt to Ghosts by 2.00x.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new MediumSkill(owner);
        }
    },
    MUSICIAN("Musician", "Multiplies damage dealt by 4.00x every eighth hit.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new MusicianSkill(owner);
        }
    },
    NURSE("Nurse", "Multiplies healing by 2.00x. Gains health at the start of every round.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new NurseSkill(owner);
        }
    },
    PILOT("Pilot", "Increases magical and special defense against AIR sources by 0.50x.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new PilotSkill(owner);
        }
    },
    POLICE("Police", "Gives bonus gold by defeating an enemy with a criminal skill.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new PoliceSkill(owner);
        }
    },
    PRAVDA("Pravda", "Increases damage dealt multiplier by 1.00x for each other Pravda wifey.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new PravdaSkill(owner, false);
        }
    },
    PRAVDAPRES("Pravda President", "Increases damage dealt multiplier by 2.00x for each other Pravda wifey.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new PravdaSkill(owner, true);
        }
    },
    PROGRAMMER("Programmer", "Increases damage dealt multiplier by 0.5x to a Robot for each round that it has been attacked.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new ProgrammerSkill(owner);
        }
    },
    PROTAGONIST("Protagonist", "When this wifey suffers lethal damage the first time, prevent the death and heal self.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new ProtagonistSkill(owner);
        }
    },
    RACER("Racer", "Multiplies damage dealt by 4.00x at the start of a wave. Multiplier decreases by 0.50x for each turn the wave lasts, to a minimum of 0.50x.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new RacerSkill(owner);
        }
    },
    ROBOT("Robot", "Increases physical damage taken by 0.20x. Decreases magical and special defense against WATER sources by 0.50x.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new RobotSkill(owner);
        }
    },
    SADIST("Sadist", "Increases damage dealt multiplier by 0.05x for each hit. The number of hits resets to 0 if an enemy is defeated by this wifey.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new SadistSkill(owner);
        }
    },
    SLUGABED("Slugabed", "Multiplies damage dealt by 6.00x. Reduces multiplier by 0.25x for each turn, to a minimum of 0.25x.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new SlugabedSkill(owner);
        }
    },
    SPORTSMANAGER("Sports Manager", "Increases physical damage dealt multiplier by 0.05x for each Athlete wifey. Increases the physical damage multiplier of each Athlete wifey by 1.00x. This does not stack.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new SportsManagerSkill(owner);
        }
    },
    SURVIVAL("Sabagebu", "Increases physical, magical, and special defense by 0.01x each turn.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new SurvivalSkill(owner, false);
        }
    },
    SURVIVALPRES("Sabagebu President", "Increases physical, magical, and special defense by 0.02x each turn.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new SurvivalSkill(owner, true);
        }
    },
    TEACHER("Teacher", "Increases experience received by party by 10%") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new TeacherSkill(owner);
        }
    },
    TIMETRAVELER("Time Traveler", "When this wifey suffers lethal damage the first time, prevent the death and set health to the wifey's health at the start of the round.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new TimeTravelerSkill(owner);
        }
    },
    TRAP("Trap", "Increases physical, magical, and special defense by 0.15x.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new TrapSkill(owner);
        }
    },
    TSUNDERE("Tsundere", "Multiplies damage dealt by 2.00x if there are only 3 party members, 3.00x if there are only 2, 4.00x if this is the only wifey.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new TsundereSkill(owner);
        }
    },
    VAMPIRE("Vampire", "Dealing damage heals self by 10% of the damage dealt.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new VampireSkill(owner);
        }
    },
    WITCH("Witch", "Increases magical damage dealt multiplier by 0.20x each time a magical attack is used.") {
        @Override
        public AbsSkill getBattleSkill(BattleCharacter owner) {
            return new WitchSkill(owner);
        }
    };


    private String skillName;
    private String skillDesc;
    public abstract AbsSkill getBattleSkill(BattleCharacter owner);

    public static Comparator<SkillsEnum> SKILLS_ENUM_COMPARATOR = new Comparator<SkillsEnum>(){
        @Override
        public int compare(SkillsEnum a, SkillsEnum b) {
            return a.getSkillName().compareTo(b.getSkillName());
        }
    };

    SkillsEnum(String skillName, String skillDesc){
        this.skillName = skillName;
        this.skillDesc = skillDesc;
    }

    public String getSkillName(){
        return this.skillName;
    }

    public String getSkillDesc(){
        return this.skillDesc;
    }
}
