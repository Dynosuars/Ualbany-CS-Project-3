package Characters;

import java.io.Serializable;
import Game.Game;

public abstract class Character implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Character constructor
     * @param name
     * @param desc
     * @param balance of the playe
     * @param Moveset
     * @param skillpoints
     * @param stat
     * @param exp STARTING EXP
     */
    public Character(String name, String desc, double balance, Moveset moveset, int skillPoints, Stats stat, int exp,
            int level) {
        this.name = name;
        this.desc = desc;
        this.balance = balance;
        this.moveset = moveset;
        this.skillPoints = skillPoints;
        this.stat = stat;
        this.exp = exp;
        this.level = level;
        this.health = stat.HEP;
        this.isAlive = true;
    }

    /*
     * Real functionality
     */

    /**
     * Find the amount of moves based on speed 
     * @return
     */
    public int getActionCount() {
        int spd = this.stat.SPD;

        if (spd >= 200) {
            return 3;
        } else if (spd >= 120) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Gain_xp function so I can set a it better
     * @param amount
     * @return
     */
    public int gain_xp(int amount) {
        int levels = 0;
        this.exp += amount;
        // (50 + 40n)(1.02^n) where n = level - 1
        // (BASE_XP + 40 * (this.level - 1) * Math.pow(1 + XP_GROWTH_RATE, this.level - 1))
        int levelUpThreshold = (int) Math.round((BASE_XP + 40 * (this.level - 1)) * Math.pow(1 + XP_GROWTH_RATE, this.level - 1));
        
        // Leveling up
        while (this.exp >= levelUpThreshold) {
            levels++;
            this.exp -= levelUpThreshold;
        }
        this.level += levels;
        return levels;
    }
    
    /**
     * The api on getting hit
     * @param damage
     */
    public void getHit(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }

    /**
     * The API for attacking another character.
     * @param target, another character to attack
     * @param move, The index of moves
     * @return
     */
    public int hit(Character target, int move) {
        Skill skill = this.moveset.getSkill(move);
        int spCost = skill.getSkillPoints();

        if (spCost >= 0) {
            if (!this.canHit(move)) {
                throw new IllegalArgumentException("Not enough skill points to use this move.");
            }
            this.skillPoints -= spCost;
        } else {
            this.skillPoints += Math.abs(spCost);
            this.skillPoints = Math.min(this.skillPoints, Character.MAX_SP);
        }

        int damage = (int) Math.round(skill.getFunc().apply(this, target));
        target.getHit(damage);

        return damage;
    }
    public boolean canHit(int move) {
        int cost = this.moveset.getSkill(move).getSkillPoints();
        return cost < 0 || this.skillPoints >= cost;   // negative cost = always allowed
    }

    /**
     * A virtual function for turn
     * @param game
     * @param target
     */
    public abstract void turn(Game game, Character target);

    /*
     * Getters
     */
    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public double getBalance() {
        return balance;
    }

    public Moveset getMoveset() {
        return moveset;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public Stats getStat() {
        return stat;
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    /*
     * STATS
     */

    // Basic
    protected String name;
    protected String desc;
    protected double balance;

    // Moveset
    protected Moveset moveset;
    protected int skillPoints;

    // Stats
    protected Stats stat;

    // Levels system
    protected int exp;
    protected int level;

    // Updated State
    protected int health;
    protected boolean isAlive;

    // Constants
    public final static int MAX_LEVEL = 100;
    public final static int MAX_SP = 7;
    public final static int BASE_XP = 50;
    public final static double XP_GROWTH_RATE = 0.02;

}
