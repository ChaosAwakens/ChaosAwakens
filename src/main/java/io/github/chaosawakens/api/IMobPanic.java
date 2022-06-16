package io.github.chaosawakens.api;

public interface IMobPanic {
	
	/**
	 * Used to determine whether certain animal entities can panic (affects them in herds, if present).
	 * @return true if animal is set to panic, else returns false.
	 */
	 boolean canAnimalPanic();
	 
	 /**
	  * Used only for certain monster entities that can be set to panic (affects them in herds, if present).
	  * @return true if mob/monster is classified as "passive" and monster is set to panic, else returns false.
	  */
	 boolean canMonsterPanic();
	 
	 /**
	  * Should run after the entity starts panicking.
	  * NOTE: This runs after the entity has started panicking, NOT the moment it starts panicking. This is simply to modify the entity's actions as it panics, not HOW it starts panicking.
	  * Also, this affects the entity in herds (if present).
	  */
	 void panic();

}
