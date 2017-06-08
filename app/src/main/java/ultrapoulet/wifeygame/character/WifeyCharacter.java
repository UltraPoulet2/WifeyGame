package ultrapoulet.wifeygame.character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.BattleWifey;
import ultrapoulet.wifeygame.gamestate.RecruitableCharacters;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;
import ultrapoulet.wifeygame.recruiting.RecruitInfo;

/**
 * Created by John on 5/5/2016.
 */
public class WifeyCharacter {

    private String name;
    private String hashKey;
    private String title;
    private int strength;
    private int magic;
    private ArrayList<SkillsEnum> skills;

    private int level = 1;
    private int experience = 0;
    private int nextLevelExp = 1000;
    private static final double NEXT_LEVEL_MULT = 1.25;
    private static final int STAT_INCREASE = 2;

    private Weapon weapon;
    private WeaponSkillsEnum weaponSkill;
    private UniqueSkillsEnum uniqueSkill;

    private Element attackElement;
    private Element strongElement;
    private Element weakElement;

    private String image;

    private ArrayList<TransformWifey> transformations;

    private boolean favorite = false;

    private boolean dropped = false;
    private boolean recruited = false;

    private RecruitInfo recruitingInfo = new RecruitInfo();

    public WifeyCharacter(){
        skills = new ArrayList<>();
        transformations = new ArrayList<>();
    }

    public BattleWifey getBattleCharacter(Graphics g){
        return new BattleWifey(this, g);
    }

    public String getName(){
        return this.name;
    }

    public String getHashKey(){
        return this.hashKey;
    }

    public Image getImage(Graphics g){
        return g.newImage("characters/" + this.image + ".png", Graphics.ImageFormat.ARGB8888);
    }

    public int getHP(){
        return BattleWifey.calculateHP(this.strength);
    }

    public int getStrength(){
        return this.strength;
    }

    public int getMagic(){
        return this.magic;
    }

    public Weapon getWeapon() { return this.weapon; }

    public Element getAttackElement() { return this.attackElement; }

    public Element getStrongElement() { return this.strongElement; }

    public Element getWeakElement() { return this.weakElement; }

    public ArrayList<SkillsEnum> getSkills() { return this.skills; }

    public WeaponSkillsEnum getWeaponSkill() { return this.weaponSkill; }

    public UniqueSkillsEnum getUniqueSkill() { return this.uniqueSkill; }

    public int getLevel() { return this.level; }

    public String getExperienceString(){
        return experience + "/" + nextLevelExp;
    }

    public double getExperiencePercent(){
        return 1.0 * experience / nextLevelExp;
    }

    public ArrayList<TransformWifey> getTransformations(){
        return this.transformations;
    }

    public String getTitle(){
        //For now, it's fine for title to not be set
        return this.title;
    }

    public RecruitInfo getRecruitingInfo(){
        return recruitingInfo;
    }

    public boolean isRecruited(){
        return this.recruited;
    }

    public boolean isDropped(){
        return this.dropped;
    }

    public void setHashKey(String hashKey){
        this.hashKey = hashKey;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public void setMagic(int magic){
        this.magic = magic;
    }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public void setAttackElement(Element element){ this.attackElement = element; }

    public void setStrongElement(Element element) { this.strongElement = element; }

    public void setWeakElement(Element element) { this.weakElement = element; }

    public void setTitle(String inTitle){
        this.title = inTitle;
    }

    public void setRecruitingInfo(RecruitInfo input){
        this.recruitingInfo = input;
    }

    public void recruit(){
        this.recruited = true;
        if(this.dropped){
            RecruitableCharacters.remove(this.hashKey);
        }
        RecruitedCharacters.put(this.hashKey, this);
    }

    public void drop(){
        this.dropped = true;
        RecruitableCharacters.put(this.hashKey, this);
    }

    public void addSkill(SkillsEnum skill) {
        if (!this.skills.contains(skill)) {
            this.skills.add(skill);
        }
        Collections.sort(this.skills, SkillsEnum.SKILLS_ENUM_COMPARATOR);
    }

    public void setWeaponSkill(WeaponSkillsEnum skill) {
        this.weaponSkill = skill;
    }

    public void setUniqueSkill(UniqueSkillsEnum skill) {
        this.uniqueSkill = skill;
    }

    public void addTransformation(TransformWifey t){
        this.transformations.add(t);
    }

    public void toggleFavorite(){
        this.favorite = !this.favorite;
    }

    public boolean isFavorite() {
        return this.favorite;
    }

    public int compareName(WifeyCharacter other){
        int result = this.getName().compareTo(other.getName());
        if(result != 0){
            return result;
        }
        else{
            return this.getHashKey().compareTo(other.getHashKey());
        }
    }

    public int compareStrength(WifeyCharacter other){
        int result = other.getStrength() - this.getStrength();
        if(result != 0){
            return result;
        }
        else{
            return this.compareName(other);
        }
    }

    public int compareMagic(WifeyCharacter other){
        int result = other.getMagic() - this.getMagic();
        if(result != 0){
            return result;
        }
        else{
            return this.compareName(other);
        }
    }

    public int compareFavorite(WifeyCharacter other){
        if(this.isFavorite() && !other.isFavorite()){
            return -1;
        }
        else if(!this.isFavorite() && other.isFavorite()){
            return 1;
        }
        return this.compareName(other);
    }

    public boolean validate(){
        if(name.length() == 0){
            return false;
        }
        if(strength <= 0){
            return false;
        }
        if(magic <= 0){
            return false;
        }
        if(weapon == null){
            return false;
        }
        if(attackElement == null || strongElement == null | weakElement == null){
            return false;
        }
        return true;
    }

    //Return true if level increased
    public boolean addExperience(int addedExperience){
        boolean leveled = false;
        experience += addedExperience;
        while(experience > nextLevelExp){
            leveled = true;
            level++;
            experience -= nextLevelExp;
            nextLevelExp *= NEXT_LEVEL_MULT;
            strength += STAT_INCREASE;
            magic += STAT_INCREASE;
            System.out.println(name + " Level " + level + " Next exp: " + nextLevelExp);
            for(int i = 0; i < transformations.size(); i++){
                TransformWifey transformation = transformations.get(i);
                transformation.levelUp();
            }
        }
        return leveled;
    }

    public static Comparator<WifeyCharacter> getNameComparator(){
        return nameComparator;
    }

    private static Comparator<WifeyCharacter> nameComparator = new Comparator<WifeyCharacter>() {
        @Override
        public int compare(WifeyCharacter a, WifeyCharacter b) {
            return a.compareName(b);
        }
    };
}
