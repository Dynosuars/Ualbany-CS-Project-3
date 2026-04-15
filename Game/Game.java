// Game/Game.java
package Game;

import Characters.Character;
import Enemies.Enemy;
import Players.Player;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.Random;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    public Storyline storyline;
    public Player player;
    public Enemy currentEnemy;
    private static final Random random = new Random();
    private int storyProgress = 0;

    public Game(Storyline storyline, Player player, Enemy enemy) {
        this.storyline = storyline;
        this.player = player;
        this.currentEnemy = enemy;
        this.storyProgress = 0;
    }

    public int getStoryProgress() {
        return storyProgress;
    }

    public void setStoryProgress(int progress) {
        this.storyProgress = Math.max(0, progress);
    }

    public String getCurrentStoryEvent() {
        return storyline.getEvent(storyProgress);   // or use your getCurrentEvent if updated
    }

    public void advanceStory() {
        storyProgress++;
    }

    public void startBattle(Enemy enemy) {
        this.currentEnemy = enemy;
        JOptionPane.showMessageDialog(null, 
            "A Sigma " + enemy.getName() + " appears!", 
            "Battle Start!", JOptionPane.INFORMATION_MESSAGE);

        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            // MY TURN
            int playerActions = player.getActionCount();
            for (int i = 0; i < playerActions; i++) {
                if (enemy.getHealth() <= 0) break;
                
                JOptionPane.showMessageDialog(null,
                    "=== " + player.getName() + "'s Turn (" + (i+1) + "/" + playerActions + ") ===\n" +
                    "Your HP: " + player.getHealth() + " | Enemy HP: " + enemy.getHealth(),
                    "Battle", JOptionPane.INFORMATION_MESSAGE);

                player.turn(this, enemy);   // your existing player turn method
            }

            if (enemy.getHealth() <= 0) break;

            int enemyActions = enemy.getActionCount();
            for (int i = 0; i < enemyActions; i++) {
                if (player.getHealth() <= 0) break;
                
                JOptionPane.showMessageDialog(null,
                    "=== " + enemy.getName() + "'s Turn (" + (i+1) + "/" + enemyActions + ") ===",
                    "Battle", JOptionPane.INFORMATION_MESSAGE);

                enemy.turn(this, player); 
            }

            if(player.getHealth() <= 0) {
                JOptionPane.showMessageDialog(null, "You were defeated by the " + enemy.getName() + "!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }

    public String getCurrentEvent() {
        return this.storyline.getEvent(storyProgress);
    }

    // Saves a object YAY
    public void save(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            JOptionPane.showMessageDialog(null, "Game saved successfully!", "Saving", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Save failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
    }

    /**
     * LOADS THE DAMN GAME WHICH IDK WHY IT WOULDN"T WORK
     * @param filename
     */
    public void load(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Game loadedGame = (Game) ois.readObject();
            

            this.storyline = loadedGame.storyline;
            this.player = loadedGame.player;
            this.storyProgress = loadedGame.storyProgress;

            //JOptionPane.showMessageDialog(null, "Game loaded - Story at index: " + storyProgress, "Load", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Load failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}