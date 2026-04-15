package Enemies;

import java.util.Random;

import javax.swing.JOptionPane;

import Characters.Character;
import Characters.Moveset;
import Characters.Stats;
import Game.Game;
import Characters.Skill;

public class Enemy extends Character {
    private static final long serialVersionUID = 1L;

    private static final Random random = new Random();
    public final int goldDrop;
    public final int xpDrop;
    
    /**
     * Ez enemy constructor
     * @param name
     * @param desc
     * @param balance
     * @param moveset
     * @param skillPoints
     * @param stat
     * @param exp
     * @param level
     * @param goldDrop
     * @param xpDrop
     */
    public Enemy(String name, String desc, double balance, Moveset moveset, 
             int skillPoints, Stats stat, int exp, int level, int goldDrop, int xpDrop) {
                super(name, desc, balance, moveset, skillPoints, stat, exp, level);
                this.goldDrop = goldDrop;
                this.xpDrop = xpDrop;
            }

    /**
     * Implements the turn using simple AI
     */
    @Override
    public void turn(Game game, Character target) {
        // Simple AI: choose a random available move
        int numMoves = moveset.size();
        if (numMoves == 0) {
            JOptionPane.showMessageDialog(null, 
                name + " has no moves available!", 
                "AI Turn", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int choice = random.nextInt(numMoves);
        Skill selectedSkill = moveset.getSkill(choice);

        if (this.skillPoints >= selectedSkill.getSkillPoints()) {
            int damage = hit(target, choice);

            JOptionPane.showMessageDialog(null,
                    name + " used " + selectedSkill.toString() 
                    + " and dealt " + damage + " damage!",
                    "AI Turn - " + name, 
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            // If not enough SP, try to find another move or skip
            boolean moveUsed = false;
            
            // Try up to 5 times to find a usable move (simple retry logic)
            for (int attempt = 0; attempt < 5 && !moveUsed; attempt++) {
                choice = random.nextInt(numMoves);
                selectedSkill = moveset.getSkill(choice);
                
                if (this.skillPoints >= selectedSkill.getSkillPoints()) {
                    int damage = hit(target, choice);
                    
                    JOptionPane.showMessageDialog(null,
                            name + " used " + selectedSkill.toString() 
                            + " and dealt " + damage + " damage!",
                            "AI Turn - " + name, 
                            JOptionPane.INFORMATION_MESSAGE);
                    moveUsed = true;
                }
            }

            if (!moveUsed) {
                JOptionPane.showMessageDialog(null,
                        name + " couldn't use any move due to insufficient SP.",
                        "AI Turn - " + name, 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
