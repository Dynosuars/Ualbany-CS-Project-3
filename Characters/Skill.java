package Characters;

import java.util.function.BiFunction;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.IOException;

public class Skill implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private transient BiFunction<Character, Character, Double> func;
    private final int skillPoints;
    private final int type;
    private double customDmgBase;
    private double customDmgScale;

    /**
     * Constructs a new Skill with the given name and function.
     * 
     * @param name
     * @param func
     */
    public Skill(String name, int skillPoints, BiFunction<Character, Character, Double> func) {
        this.name = name;
        this.func = func;
        this.skillPoints = skillPoints;
        this.type = -1; // Custom skill
        this.customDmgBase = 0;
        this.customDmgScale = 0;
    }

    // Constructor for predefined skills
    public Skill(String name, int skillPoints, int type) {
        this.name = name;
        this.skillPoints = skillPoints;
        this.type = type;
        this.func = createFunc(type);
        this.customDmgBase = 0;
        this.customDmgScale = 0;
    }

    private BiFunction<Character, Character, Double> createFunc(int type) {
        switch (type) {
            case 0:
                return (user, target) -> 100 + user.stat.ATK * 0.5; // Basic attack
            case 1:
                return (user, target) -> 150 + user.stat.ATK * 0.8; // Heavy attack
            case 2:
                return (user, target) -> 200 + user.stat.ATK * 1.2; // Special attack
            default:
                return (user, target) -> 0.0;
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (type >= 0) {
            this.func = createFunc(type);
        } else {
            this.func = recreateCustomFunc();
        }
    } 

    /**
     * Read bin file and recreates the function, function is really SHIT at being stored
     * @return
     */
    private BiFunction<Character, Character, Double> recreateCustomFunc() {
        if ("Normal ATK".equals(name)) {
            return (user, target) -> 100 + user.stat.ATK * 0.5;
        } else if ("Heavy ATK".equals(name)) {
            return (user, target) -> 150 + user.stat.ATK * 0.8;
        } else if ("ULT".equals(name)) {
            return (user, target) -> 670 + user.stat.ATK * 2.5;
        } else if ("Scratch".equals(name)) {
            return (user, target) -> 60 + user.stat.ATK * 0.4;
        } else if ("Bite".equals(name)) {
            return (user, target) -> 90 + user.stat.ATK * 0.7;
        } else if ("Hammer Smash".equals(name)) {
            return (user, target) -> 150 + user.stat.ATK * 0.1;
        } else if ("Build Wall".equals(name)) {
            return (user, target) -> 0.0;
        } else if ("Sigma Punch".equals(name)) {
            return (user, target) -> 300 + user.stat.ATK * 0.4;
        }
        return (user, target) -> 0.0;
    }

    /**
     * @return Returns the name of this skill.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns the function
     * 
     * @return
     */
    public BiFunction<Character, Character, Double> getFunc() {
        return this.func;
    }

    /**
     * Returns the skill points for this skill.
     * 
     * @return
     */
    public int getSkillPoints() {
        return this.skillPoints;
    }

}
