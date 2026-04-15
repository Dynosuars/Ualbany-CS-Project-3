package Players;

import Characters.Moveset;
import Characters.Character;
import Characters.Stats;
import Game.Game;
import Enemies.Enemy;
import javax.swing.JOptionPane;
import Characters.Skill;

public class Player extends Character {
    // A call to the big super constructor
    public Player(String name, String desc, double balance, Moveset moveset, int skillPoints, Stats stat, int exp,
            int level) {
        super(name, desc, balance, moveset, skillPoints, stat, exp, level);
    }

    /**
     * Loots a character based on Item/droppable
     * @param enemy
     */
    public void loot(Enemy enemy) {
        int goldGained = enemy.goldDrop;
        int xpGained = enemy.xpDrop;
        balance += goldGained;
        int level = gain_xp(xpGained);

        JOptionPane.showMessageDialog(null,
                String.format("You looted %d gold and %d XP from the %s!", goldGained, xpGained, enemy.getName()),
                "Loot Acquired", JOptionPane.INFORMATION_MESSAGE);

        if (level > this.level) {
            JOptionPane.showMessageDialog(null,
                    String.format("Congratulations! You've leveled up to Level %d!", level),
                    "Level Up!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * The statue of the seven from Genshin blah blah blah I don't listen to YAPPERS calling me copy cat
     */
    public void restore() {
        this.health = this.stat.HEP;
        this.skillPoints = Character.MAX_SP;
    }

    /**
     * Not implemented but switches a skill (Sigma)
     * @param newSkill
     * @param slot
     */
    public void learnSkill(Skill newSkill, int slot) {
        if (slot < 0 || slot >= moveset.size()) {
            JOptionPane.showMessageDialog(null, "Invalid skill slot!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        moveset.setSkill(slot, newSkill);
        JOptionPane.showMessageDialog(null, "You learned a new skill: " + newSkill.toString() + "!", "New Skill",
                JOptionPane.INFORMATION_MESSAGE);

    }
    
    @Override
    /**
     * Implements the turn function from Character, allowing the player to select skills
     */
    public void turn(Game game, Character target) {
        while (true) { // Loop until the player actually uses a move or the turn ends
            // Build the current status message
            String status = String.format(
                    "%s's Turn\n\n" +
                            "Your HP: %d / %d\n" +
                            "Your SP: %d / %d\n\n" +
                            "Target: %s (HP: %d / %d)\n\n" +
                            "Choose your action:",
                    name,
                    this.health, this.stat.HEP,
                    this.skillPoints, Character.MAX_SP,
                    target.getName(),
                    target.getHealth(), target.getStat().HEP);

            // Options init
            String[] options = new String[moveset.size() + 2];

            for (int i = 0; i < moveset.size(); i++) {
                Skill skill = moveset.getSkill(i);
                options[i] = skill.toString() + " (SP: " + skill.getSkillPoints() + ")";
            }

            options[moveset.size()] = "View Stats";
            options[moveset.size() + 1] = "End Turn";

            int choice = JOptionPane.showOptionDialog(
                    null,
                    status,
                    "Player Turn - " + name, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                    options[0]);

            if (choice < 0) {
                return;
            }

            if (choice == moveset.size()) {
                String statsMessage = String.format(
                        "%s's Stats:\n\n" +
                                "HP: %d / %d\n" +
                                "SP: %d / %d\n" +
                                "ATK: %d\n" +
                                "DEF: %d\n" +
                                "EXP: %d\n" +
                                "Level: %d\n" +
                                "CRITRate: %d%%\n" +
                                "CRITDMG: %d%%",
                        name,
                        this.health, this.stat.HEP,
                        this.skillPoints, Character.MAX_SP,
                        this.stat.ATK,
                        this.stat.DEF,
                        this.exp,
                        this.level,
                        this.stat.CRR,
                        this.stat.CRD);
                JOptionPane.showMessageDialog(null, statsMessage, "Player Stats - " + name,
                        JOptionPane.INFORMATION_MESSAGE);
                continue;
            }

            if (choice == moveset.size() + 1) {
                JOptionPane.showMessageDialog(null, name + " ended their turn.");
                return;
            }

            Skill selectedSkill = moveset.getSkill(choice);

            if (this.skillPoints < selectedSkill.getSkillPoints()) {
                JOptionPane.showMessageDialog(null,
                        "Not enough Skill Points for " + selectedSkill.toString() + "!",
                        "Insufficient SP", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            int damage = hit(target, choice);

            JOptionPane.showMessageDialog(null,
                    name + " used " + selectedSkill.toString() +
                            " and dealt " + damage + " damage!",
                    "Attack Result", JOptionPane.INFORMATION_MESSAGE);

            return;
        }
    }

}
