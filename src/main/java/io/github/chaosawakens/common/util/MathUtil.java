package io.github.chaosawakens.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public final class MathUtil {
	
	private MathUtil() {
		throw new IllegalAccessError("Utility Class");
	}
	
	/**
	 * Checks if a certain relative angle across 360° is within a specific angle restriction/range
	 * @param relAngle The relative angle to check
	 * @param angleRestriction The angle restriction
	 * @return True if the specified relative angle is within the specified angle restriction, else returns False
	 */
	public static boolean isWithinAngleRestriction(double relAngle, double angleRestriction) {
		return (relAngle <= angleRestriction / 2 && relAngle >= -angleRestriction / 2) || (relAngle >= 360 - angleRestriction / 2 || relAngle <= -360 + angleRestriction / 2);
	}
	
	/**
	 * Checks if a specified number is between 2 other numbers
	 * @param num Number to check
	 * @param min Minimum threshold for check
	 * @param max Maximum threshold for check
	 * @return True if the specified number is between 2 other specified numbers, else returns False
	 */
	public static boolean isBetween(int num, int min, int max) {
		return num >= min && num <= max;
	}
	
	/**
	 * Checks if a specified number is between 2 other numbers
	 * @param num Number to check
	 * @param min Minimum threshold for check
	 * @param max Maximum threshold for check
	 * @return True if the specified number is between 2 other specified numbers, else returns False
	 */
	public static boolean isBetween(double num, double min, double max) {
		return num >= min && num <= max;
	}
	
	/**
	 * Checks if a specified number is between 2 other numbers
	 * @param num Number to check
	 * @param min Minimum threshold for check
	 * @param max Maximum threshold for check
	 * @return True if the specified number is between 2 other specified numbers, else returns False
	 */
	public static boolean isBetween(float num, float min, float max) {
		return num >= min && num <= max;
	}
	
	/**
	 * Checks if a specified number is between 2 other numbers
	 * @param num Number to check
	 * @param min Minimum threshold for check
	 * @param max Maximum threshold for check
	 * @return True if the specified number is between 2 other specified numbers, else returns False
	 */
	public static boolean isBetween(long num, long min, long max) {
		return num >= min && num <= max;
	}
	
	/**
	 * Returns an angle between 2 entities via the use of polar coordinate conversion
	 * @param first The first entity to get its angle
	 * @param second The second entity to get its angle
	 * @return The angle between both entities
	 */
	public static double getAngleBetweenEntities(Entity first, Entity second) {
        return Math.atan2(second.getZ() - first.getZ(), second.getX() - first.getX()) * (180 / Math.PI) + 90;
    }
	
	/**
	 * Converts 1 experience orb to its equivalent durability value.
	 * @param xp XP value
	 * @return XP to durability (XP * 2)
	 */
	public static int convertXPToDurability(int xp) {
		return xp * 2;
	}
	
	/**
	 * Converts durability to its equivalent xp value.
	 * @param durability Durability
	 * @return Durability to XP (durability / 2)
	 */
	public static int convertDurabilityToXP(int durability) {
		return durability / 2;
	}
	
	/**
	 * Gets the horizontal distance (on the x and z axis) between 2 coordinate points
	 * @param x1 First X coordinate
	 * @param z1 First Z coordinate
	 * @param x2 Second X coordinate
	 * @param z2 Second Z coordinate
	 * @return The distance (on the x and z axis) between 2 coordinate points
	 */
	public static double getHorizontalDistanceBetween(double x1, double z1, double x2, double z2) {
		double x = Math.abs(x1 - x2);
		double z = Math.abs(z1 - z2);
		
		return Math.sqrt(x * x + z * z);
	}
	
	/**
	 * Gets the vertical distance (on the y axis) between 2 coordinate points
	 * @param y1 First Y coordinate
	 * @param y2 Second Y coordinate
	 * @return The distance (on the y axis) between 2 coordinate points
	 */
	public static double getVerticialDistanceBetween(double y1, double y2) {
		double y = Math.abs(y1 - y2);
		
		return Math.sqrt(y * y);
	}
	
	/**
	 * Gets the distance between 2 coordinate points
	 * @param x1 First X coordinate
	 * @param y1 First Y coordinate
	 * @param z1 First Z coordinate
	 * @param x2 Second X coordinate
	 * @param y2 Second Y coordinate
	 * @param z2 Second Z coordinate
	 * @return The distance between 2 coordinate points
	 */
	public static double getDistanceBetween(double x1, double y1, double z1, double x2, double y2, double z2) {
		double x = Math.abs(x1 - x2);
		double y = Math.abs(y1 - y2);
		double z = Math.abs(z1 - z2);
		
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Gets the distance between 2 block positions on the x and z axis
	 * 
	 * @param a Starting {@link BlockPos}
	 * @param b Finishing {@link BlockPos}
	 * @return The distance between a and b
	 */
	public static double getHorizontalDistanceBetween(BlockPos a, BlockPos b) {
		return getHorizontalDistanceBetween(a.getX(), a.getZ(), b.getX(), b.getZ());
	}

	/**
	 * Gets the distance between 2 block positions on the y axis
	 * 
	 * @param a Starting {@link BlockPos}
	 * @param b Finishing {@link BlockPos}
	 * @return The distance between a and b
	 */
	public static double getVerticalDistanceBetween(BlockPos a, BlockPos b) {
		return getVerticialDistanceBetween(a.getY(), b.getY());
	}

	/**
	 * Gets the distance between 2 block positions on all axis
	 * 
	 * @param a Starting {@link BlockPos}
	 * @param b Finishing {@link BlockPos}
	 * @return The distance between a and b
	 */
	public static double getDistanceBetween(BlockPos a, BlockPos b) {
		return getDistanceBetween(a.getX(), a.getZ(), a.getY(), b.getY(), b.getX(), b.getZ());
	}
	
	/**
	 * Gets the distance between 2 vector positions on the x and z axis
	 * 
	 * @param a Starting {@link Vector3d}
	 * @param b Finishing {@link Vector3d}
	 * @return The distance between a and b
	 */
	public static double getHorizontalDistanceBetween(Vector3d a, Vector3d b) {
		return getHorizontalDistanceBetween(a.x(), a.z(), b.x(), b.z());
	}

	/**
	 * Gets the distance between 2 vector positions on the y axis
	 * 
	 * @param a Starting {@link Vector3d}
	 * @param b Finishing {@link Vector3d}
	 * @return The distance between a and b
	 */
	public static double getVerticalDistanceBetween(Vector3d a, Vector3d b) {
		return getVerticialDistanceBetween(a.y(), b.y());
	}

	/**
	 * Gets the distance between 2 vector positions on all axis
	 * 
	 * @param a Starting {@link Vector3d}
	 * @param b Finishing {@link Vector3d}
	 * @return The distance between a and b
	 */
	public static double getDistanceBetween(Vector3d a, Vector3d b) {
		return getDistanceBetween(a.x(), a.z(), a.y(), b.y(), b.x(), b.z());
	}
	
	/**
	 * Gets the horizontal distance (on the x and z axis) between 2 entities
	 * 
	 * @param entityA Entity A, starting pos to check
	 * @param entityB Entity B, finishing pos to check
	 * @return The distance (on the x and z axis) between the 2 entities
	 */
	public static double getHorizontalDistanceBetween(Entity entityA, Entity entityB) {
		return getHorizontalDistanceBetween(entityA.blockPosition(), entityB.blockPosition());
	}

	/**
	 * Gets the vertical distance (on the y axis) between 2 entities
	 * 
	 * @param entityA Entity A, starting pos to check
	 * @param entityB Entity B, finishing pos to check
	 * @return The distance (on the y axis) between the 2 entities
	 */
	public static double getVerticalDistanceBetween(Entity entityA, Entity entityB) {
		return getVerticalDistanceBetween(entityA.blockPosition(), entityB.blockPosition());
	}

	/**
	 * Gets distance between 2 entities
	 * 
	 * @param entityA Entity A, starting pos to check
	 * @param entityB Entity B, finishing pos to check
	 * @return The total distance between the 2 entities
	 */
	public static double getDistanceBetween(Entity entityA, Entity entityB) {
		return getDistanceBetween(entityA.blockPosition(), entityB.blockPosition());
	}

}
