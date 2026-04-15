package Characters;

import java.io.Serializable;

public class Moveset implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Skill[] moves;

    /**
     * Constructs a new Moveset with the given moves.
     * 
     * @param moves
     */
    @SafeVarargs
    public Moveset(Skill... moves) {
        this.moves = moves;
    }

    /**
     * Returns the damage dealt by the move at the given index when used by the user
     * on the target.
     * 
     * @param index
     * @param user
     * @param target
     * @return
     */
    public double Use(int index, Character user, Character target) {
        return this.moves[index].getFunc().apply(user, target);
    }

    public int size() {
        return this.moves.length;
    }

    /**
     * Base movesets
     */
    public static final Moveset baseMoveset = new Moveset(
            new Skill("Normal", -1, 0), // Basic attack
            new Skill("Heavy", 1, 1), // Heavy attack
            new Skill("Special", 3, 2) // Special attack
    );

    /**
     * Getters
     */
    public void setSkill(int index, Skill skill) {
        if (index >= 0 && index < moves.length) {
            moves[index] = skill;
        }
    }

    public Skill getSkill(int index) {
        return this.moves[index];
    }

}
