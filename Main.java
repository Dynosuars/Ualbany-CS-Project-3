import javax.swing.JOptionPane;
import Characters.Moveset;
import Characters.Stats;
import Players.Player;
import Characters.Skill;
import Game.Game;
import Game.Storyline;
import Enemies.Enemy;

public class Main {
    public static void main(String[] args) {
        final String title = "RPG SIM 2.0 (ORIGINALLY MARCH 7TH's ADVENTURE)";
        final String saveFileBIN = "./Assets/savefile.bin";

        Storyline storyline = new Storyline("./Assets/storyline.txt");

        int options = JOptionPane.showOptionDialog(null,
                "Welcome to " + title + "! Do you want to start a new game or load an existing one?",
                title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[] { "New Game", "Load Game" }, "New Game");

        Game game;

        if (options == 0) { // New Game
            String playerName;
            do {
                playerName = JOptionPane.showInputDialog(null,
                        "Rules:\n1. You can't use Dyno\n2. You can't be sigma cuz I AM\n3. Your name must not mention \"JOB\"\nEnter your character's name:",
                        title, JOptionPane.QUESTION_MESSAGE);

                if (playerName == null || playerName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid name.", title, JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                String lower = playerName.toLowerCase();
                if (lower.contains("job")) {
                    JOptionPane.showMessageDialog(null, "Your name must not mention the word \"JOB\".", title,
                            JOptionPane.ERROR_MESSAGE);
                } else if (lower.contains("dyno")) {
                    JOptionPane.showMessageDialog(null, "You can't use Dyno as a name.", title,
                            JOptionPane.ERROR_MESSAGE);
                } else if (lower.contains("sigma")) {
                    JOptionPane.showMessageDialog(null, "You can't be sigma because I AM.", title,
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    break;
                }
            } while (true);

            Player player = new Player(playerName, "A bum who just started this fire game",
                    0.0,
                    new Moveset(
                            new Skill("Normal ATK", -1, (user, target) -> 100 + user.getStat().ATK * 0.5),
                            new Skill("Heavy ATK", 1, (user, target) -> 150 + user.getStat().ATK * 0.8),
                            new Skill("ULT", 3, (user, target) -> 670 + user.getStat().ATK * 2.5)),
                    7,
                    new Stats(250, 1000, 100, 80, 100, 5),
                    0, 1);

            game = new Game(storyline, player, null);

            for (int i = 0; i < 3; i++) {
                JOptionPane.showMessageDialog(null, storyline.getEvent(i), title, JOptionPane.INFORMATION_MESSAGE);
            }

            Enemy goblinBaby = new Enemy("Goblin Baby", "A LITERAL BABY BRO",
                    0.0,
                    new Moveset(
                            new Skill("Scratch", 0, (user, target) -> 60 + user.getStat().ATK * 0.4),
                            new Skill("Bite", 1, (user, target) -> 90 + user.getStat().ATK * 0.7)),
                    5,
                    new Stats(80, 300, 30, 60, 50, 3),
                    0, 1,
                    100,
                    200);

            game.startBattle(goblinBaby);
            if (player.getHealth() <= 0) {
                JOptionPane.showMessageDialog(null, "You were defeated by the " + goblinBaby.getName() + "!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Might aswell reset your entire thing", title,
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            JOptionPane.showMessageDialog(null,
                    "WOW YOU DEFEATED A LITERAL BABY, WHAT ARE YOU? A DEMON???? Congrats bro", title,
                    JOptionPane.INFORMATION_MESSAGE);
            player.loot(goblinBaby);
            JOptionPane.showMessageDialog(null, "Tutorial ended now!", title, JOptionPane.INFORMATION_MESSAGE);
            game.setStoryProgress(3);
            game.save(saveFileBIN);
            JOptionPane.showMessageDialog(null, "Game saved successfully!", title, JOptionPane.INFORMATION_MESSAGE);

            options = JOptionPane.showOptionDialog(null,
                    "Do you want to continue to Stage 2 or exit the game?",
                    title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[] { "Continue to Stage 2", "Exit Game" }, "Continue to Stage 2");
            if (options != 0) {
                JOptionPane.showMessageDialog(null, "Thanks for playing! See you next time!", title, JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

        } else { // Load Game
            game = new Game(storyline, null, null);
            game.load(saveFileBIN);

            if (game.player != null) {
                JOptionPane.showMessageDialog(null,
                        "Game loaded successfully!\nWelcome back, " + game.player.getName() +
                                "\nStory Progress: " + game.getStoryProgress() + " / " + game.storyline.getLength(),
                        title, JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                JOptionPane.showMessageDialog(null, "Failed to load game. Please start a new game.", title,
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }

        // Stage 2
            Player player = game.player;
            for (int i = 3; i < 6; i++) {
                JOptionPane.showMessageDialog(null, storyline.getEvent(i), title, JOptionPane.INFORMATION_MESSAGE);
            }

            Enemy sigmaBuilder = new Enemy("Sigma Builder", "A tough sigma builder",
                    0.0,
                    new Moveset(
                            new Skill("Hammer Smash", 0, (user, target) -> 150 + user.getStat().ATK * 0.1),
                            new Skill("Build Wall", 2, (user, target) -> 0.0), // maybe defensive, and stats are too op
                            new Skill("Sigma Punch", 3, (user, target) -> 300 + user.getStat().ATK * 0.4)),
                    7,
                    new Stats(100, 5000, 100, 20, 10, 5),
                    0, 1, 500, 1000);

            game.startBattle(sigmaBuilder);
            if (player.getHealth() <= 0) {
                JOptionPane.showMessageDialog(null, "You were defeated by the " + sigmaBuilder.getName() + "!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Better luck next time!", title, JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            JOptionPane.showMessageDialog(null, "You defeated the Sigma Builder! Stage 2 complete!", title,
                    JOptionPane.INFORMATION_MESSAGE);
            player.loot(sigmaBuilder);
            game.setStoryProgress(6);
            game.save(saveFileBIN);

            for (int i = 6; i < 16; i++) {
                JOptionPane.showMessageDialog(null, storyline.getEvent(i), title, JOptionPane.INFORMATION_MESSAGE);
            }

            JOptionPane.showMessageDialog(null, "Game update SOON( NEVER )!!!!", title, JOptionPane.INFORMATION_MESSAGE);
            int option = JOptionPane.showOptionDialog(null, "Since it's the end of the game, do you want to hear my rant this time", title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[] { "Yes (Continue)", "No" }, "Yes (Continue)");
            
            if(option == 0) {
                JOptionPane.showMessageDialog(null, "Why is it so hard to use a external library in java?????", title, JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "I wanted to USE GLFW but ig not", title, JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "This is why C++ is superior", title, JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "and yes I TAKE SHOWERS AND TOUCH GRASS WITH A GOOD SOCIAL LIFE", title, JOptionPane.INFORMATION_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null, "BYE SEE YOU NEVER!", title, JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }



    }
}