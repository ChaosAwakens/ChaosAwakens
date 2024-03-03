package io.github.chaosawakens.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public final class MathUtil {
	
	private MathUtil() {
		throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
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
	 * Decrements a specified {@code int} until it reaches 0. Sets the specified {@code int} to 0 if it tries to decrement further.
	 * @param targetToDecrement The {@code int} to decrement
	 */
	public static void decrementToZero(int targetToDecrement) {
		if (targetToDecrement > 0) targetToDecrement--;
		if (targetToDecrement < 0) targetToDecrement = 0;
	}
	
	/**
	 * Decrements a specified {@code double} until it reaches 0. Sets the specified {@code double} to 0 if it tries to decrement further.
	 * @param targetToDecrement The {@code double} to decrement
	 */
	public static void decrementToZero(double targetToDecrement) {
		if (targetToDecrement > 0) targetToDecrement--;
		if (targetToDecrement < 0) targetToDecrement = 0;
	}
	
	/**
	 * Decrements a specified {@code float} until it reaches 0. Sets the specified {@code float} to 0 if it tries to decrement further.
	 * @param targetToDecrement The {@code float} to decrement
	 */
	public static void decrementToZero(float targetToDecrement) {
		if (targetToDecrement > 0) targetToDecrement--;
		if (targetToDecrement < 0) targetToDecrement = 0;
	}
	
	public static double getRelativeAngleBetween(double x1, double z1, double x2, double z2) {
        return (Math.atan2(z2 - z1, x2 - x1) * (180 / Math.PI) - 90) % 360;
    }
	
	public static double getRelativeAngleBetweenBlockPositions(BlockPos posA, BlockPos posB) {
        return getRelativeAngleBetween(posA.getX(), posA.getZ(), posB.getX(), posB.getZ());
    }
	
	/**
	 * Returns the relative angle between 2 entities (in degrees) via the use of polar coordinate conversion
	 * @param first The first entity to get its angle
	 * @param second The second entity to get its angle
	 * @return The angle (in degrees) between both entities
	 */
	public static double getRelativeAngleBetweenEntities(Entity first, Entity second) {
        return getRelativeAngleBetween(first.getX(), first.getZ(), second.getX(), second.getZ());
    }
	
	public static double getAngleBetweenEntities(Entity first, Entity second) {
		return Math.atan2(second.getZ() - first.getZ(), second.getX() - first.getX()) * (180 / Math.PI) + 90;
	}
	
	/**
	 * Increases/increments a specified {@code int} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageIncrease The percentage by which to increase the specified number
	 * @return The percentage-wise increased {@code int}/specified number
	 */
	public static int increaseByPercentage(int numToReduce, int percentageIncrease) {
		int percIncActual = percentageIncrease / 100;
		
		if (percIncActual > 1) percIncActual = 1;
		if (percIncActual < 0) percIncActual = 0;
		
		return numToReduce + (numToReduce * percIncActual);
	}
	
	/**
	 * Reduces a specified {@code int} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageReduction The percentage by which to reduce the specified number
	 * @return The percentage-wise reduced {@code int}/specified number
	 */
	public static int reduceByPercentage(int numToReduce, int percentageReduction) {
		int percRedActual = percentageReduction / 100;
		
		if (percRedActual > 1) percRedActual = 1;
		if (percRedActual < 0) percRedActual = 0;
		
		return numToReduce - (numToReduce * percRedActual);
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
	 * Increases/increments a specified {@code double} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageIncrease The percentage by which to increase the specified number
	 * @return The percentage-wise increased {@code double}/specified number
	 */
	public static double increaseByPercentage(double numToReduce, double percentageIncrease) {
		double percIncActual = percentageIncrease / 100D;
		
		if (percIncActual > 1) percIncActual = 1;
		if (percIncActual < 0) percIncActual = 0;
		
		return numToReduce + (numToReduce * percIncActual);
	}
	
	/**
	 * Reduces a specified {@code double} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageReduction The percentage by which to reduce the specified number
	 * @return The percentage-wise reduced {@code double}/specified number
	 */
	public static double reduceByPercentage(double numToReduce, double percentageReduction) {
		double percRedActual = percentageReduction / 100D;
		
		if (percRedActual > 1) percRedActual = 1;
		if (percRedActual < 0) percRedActual = 0;
		
		return numToReduce - (numToReduce * percRedActual);
	}

	/**
	 * Gets the Manhattan horizontal distance (on the x and z-axis) between 2 coordinate points
	 *
	 * @param x1 First X coordinate
	 * @param z1 First Z coordinate
	 * @param x2 Second X coordinate
	 * @param z2 Second Z coordinate
	 *
	 * @return The Manhattan distance (on the x and z-axis) between 2 coordinate points
	 */
	public static double getManhattanHorizontalDistanceBetween(double x1, double z1, double x2, double z2) {
		double x = Math.abs(x1 - x2);
		double z = Math.abs(z1 - z2);

		return x + z;
	}

	/**
	 * Gets the Manhattan vertical distance (on the y-axis) between 2 coordinate points
	 *
	 * @param y1 First Y coordinate
	 * @param y2 Second Y coordinate
	 *
	 * @return The Manhattan distance (on the y-axis) between 2 coordinate points
	 */
	public static double getManhattanVerticialDistanceBetween(double y1, double y2) {
		double y = Math.abs(y1 - y2);

		return y;
	}

	/**
	 * Gets the Manhattan distance between 2 coordinate points
	 *
	 * @param x1 First X coordinate
	 * @param y1 First Y coordinate
	 * @param z1 First Z coordinate
	 * @param x2 Second X coordinate
	 * @param y2 Second Y coordinate
	 * @param z2 Second Z coordinate
	 *
	 * @return The Manhattan distance between 2 coordinate points
	 */
	public static double getManhattanDistanceBetween(double x1, double y1, double z1, double x2, double y2, double z2) {
		double x = Math.abs(x1 - x2);
		double y = Math.abs(y1 - y2);
		double z = Math.abs(z1 - z2);

		return x + y + z;
	}

	/**
	 * Gets the Manhattan distance between 2 block positions on the x and z-axis
	 *
	 * @param a Starting {@link BlockPos}
	 * @param b Finishing {@link BlockPos}
	 *
	 * @return The Manhattan distance between a and b
	 */
	public static double getManhattanHorizontalDistanceBetween(BlockPos a, BlockPos b) {
		return getHorizontalDistanceBetween(a.getX(), a.getZ(), b.getX(), b.getZ());
	}

	/**
	 * Gets the Manhattan distance between 2 block positions on the y-axis
	 *
	 * @param a Starting {@link BlockPos}
	 * @param b Finishing {@link BlockPos}
	 *
	 * @return The Manhattan distance between a and b
	 */
	public static double getManhattanVerticalDistanceBetween(BlockPos a, BlockPos b) {
		return getVerticialDistanceBetween(a.getY(), b.getY());
	}

	/**
	 * Gets the Manhattan distance between 2 block positions on all axis
	 *
	 * @param a Starting {@link BlockPos}
	 * @param b Finishing {@link BlockPos}
	 *
	 * @return The Manhattan distance between a and b
	 */
	public static double getManhattanDistanceBetween(BlockPos a, BlockPos b) {
		return getDistanceBetween(a.getX(), a.getZ(), a.getY(), b.getY(), b.getX(), b.getZ());
	}

	/**
	 * Gets the Manhattan distance between 2 vector positions on the x and z-axis
	 *
	 * @param a Starting {@link Vector3d}
	 * @param b Finishing {@link Vector3d}
	 *
	 * @return The Manhattan distance between a and b
	 */
	public static double getManhattanHorizontalDistanceBetween(Vector3d a, Vector3d b) {
		return getHorizontalDistanceBetween(a.x(), a.z(), b.x(), b.z());
	}

	/**
	 * Gets the Manhattan distance between 2 vector positions on the y-axis
	 *
	 * @param a Starting {@link Vector3d}
	 * @param b Finishing {@link Vector3d}
	 *
	 * @return The Manhattan distance between a and b
	 */
	public static double getManhattanVerticalDistanceBetween(Vector3d a, Vector3d b) {
		return getVerticialDistanceBetween(a.y(), b.y());
	}

	/**
	 * Gets the Manhattan distance between 2 vector positions on all axis
	 *
	 * @param a Starting {@link Vector3d}
	 * @param b Finishing {@link Vector3d}
	 *
	 * @return The Manhattan distance between a and b
	 */
	public static double getManhattanDistanceBetween(Vector3d a, Vector3d b) {
		return getDistanceBetween(a.x(), a.z(), a.y(), b.y(), b.x(), b.z());
	}

	/**
	 * Gets the Manhattan horizontal distance (on the x and z-axis) between 2 entities
	 *
	 * @param entityA Entity A, starting pos to check
	 * @param entityB Entity B, finishing pos to check
	 *
	 * @return The Manhattan distance (on the x and z-axis) between the 2 entities
	 */
	public static double getManhattanHorizontalDistanceBetween(Entity entityA, Entity entityB) {
		return getHorizontalDistanceBetween(entityA.blockPosition(), entityB.blockPosition());
	}

	/**
	 * Gets the Manhattan vertical distance (on the y-axis) between 2 entities
	 *
	 * @param entityA Entity A, starting pos to check
	 * @param entityB Entity B, finishing pos to check
	 *
	 * @return The Manhattan distance (on the y-axis) between the 2 entities
	 */
	public static double getManhattanVerticalDistanceBetween(Entity entityA, Entity entityB) {
		return getVerticalDistanceBetween(entityA.blockPosition(), entityB.blockPosition());
	}

	/**
	 * Gets Manhattan distance between 2 entities
	 *
	 * @param entityA Entity A, starting pos to check
	 * @param entityB Entity B, finishing pos to check
	 *
	 * @return The total Manhattan distance between the 2 entities
	 */
	public static double getManhattanDistanceBetween(Entity entityA, Entity entityB) {
		return getDistanceBetween(entityA.blockPosition(), entityB.blockPosition());
	}
	
	/**
	 * Gets the Euclidean horizontal distance (on the x and z-axis) between 2 coordinate points
	 *
	 * @param x1 First X coordinate
	 * @param z1 First Z coordinate
	 * @param x2 Second X coordinate
	 * @param z2 Second Z coordinate
	 *
	 * @return The Euclidean distance (on the x and z-axis) between 2 coordinate points
	 */
	public static double getHorizontalDistanceBetween(double x1, double z1, double x2, double z2) {
		double x = Math.abs(x1 - x2);
		double z = Math.abs(z1 - z2);
		
		return Math.sqrt(x * x + z * z);
	}
	
	/**
	 * Gets the Euclidean vertical distance (on the y-axis) between 2 coordinate points
	 *
	 * @param y1 First Y coordinate
	 * @param y2 Second Y coordinate
	 *
	 * @return The Euclidean distance (on the y-axis) between 2 coordinate points
	 */
	public static double getVerticialDistanceBetween(double y1, double y2) {
		double y = Math.abs(y1 - y2);
		
		return Math.sqrt(y * y);
	}
	
	/**
	 * Gets the Euclidean distance between 2 coordinate points
	 *
	 * @param x1 First X coordinate
	 * @param y1 First Y coordinate
	 * @param z1 First Z coordinate
	 * @param x2 Second X coordinate
	 * @param y2 Second Y coordinate
	 * @param z2 Second Z coordinate
	 *
	 * @return The Euclidean distance between 2 coordinate points
	 */
	public static double getDistanceBetween(double x1, double y1, double z1, double x2, double y2, double z2) {
		double x = Math.abs(x1 - x2);
		double y = Math.abs(y1 - y2);
		double z = Math.abs(z1 - z2);
		
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Gets the Euclidean distance between 2 block positions on the x and z-axis
	 * 
	 * @param a Starting {@link BlockPos}
	 * @param b Finishing {@link BlockPos}
	 *
	 * @return The Euclidean distance between a and b
	 */
	public static double getHorizontalDistanceBetween(BlockPos a, BlockPos b) {
		return getHorizontalDistanceBetween(a.getX(), a.getZ(), b.getX(), b.getZ());
	}

	/**
	 * Gets the Euclidean distance between 2 block positions on the y-axis
	 * 
	 * @param a Starting {@link BlockPos}
	 * @param b Finishing {@link BlockPos}
	 *
	 * @return The Euclidean distance between a and b
	 */
	public static double getVerticalDistanceBetween(BlockPos a, BlockPos b) {
		return getVerticialDistanceBetween(a.getY(), b.getY());
	}

	/**
	 * Gets the Euclidean distance between 2 block positions on all axis
	 * 
	 * @param a Starting {@link BlockPos}
	 * @param b Finishing {@link BlockPos}
	 *
	 * @return The Euclidean distance between a and b
	 */
	public static double getDistanceBetween(BlockPos a, BlockPos b) {
		return getDistanceBetween(a.getX(), a.getZ(), a.getY(), b.getY(), b.getX(), b.getZ());
	}
	
	/**
	 * Gets the Euclidean distance between 2 vector positions on the x and z-axis
	 * 
	 * @param a Starting {@link Vector3d}
	 * @param b Finishing {@link Vector3d}
	 *
	 * @return The Euclidean distance between a and b
	 */
	public static double getHorizontalDistanceBetween(Vector3d a, Vector3d b) {
		return getHorizontalDistanceBetween(a.x(), a.z(), b.x(), b.z());
	}

	/**
	 * Gets the Euclidean distance between 2 vector positions on the y-axis
	 * 
	 * @param a Starting {@link Vector3d}
	 * @param b Finishing {@link Vector3d}
	 *
	 * @return The Euclidean distance between a and b
	 */
	public static double getVerticalDistanceBetween(Vector3d a, Vector3d b) {
		return getVerticialDistanceBetween(a.y(), b.y());
	}

	/**
	 * Gets the Euclidean distance between 2 vector positions on all axis
	 * 
	 * @param a Starting {@link Vector3d}
	 * @param b Finishing {@link Vector3d}
	 *
	 * @return The Euclidean distance between a and b
	 */
	public static double getDistanceBetween(Vector3d a, Vector3d b) {
		return getDistanceBetween(a.x(), a.z(), a.y(), b.y(), b.x(), b.z());
	}
	
	/**
	 * Gets the Euclidean horizontal distance (on the x and z-axis) between 2 entities
	 * 
	 * @param entityA Entity A, starting pos to check
	 * @param entityB Entity B, finishing pos to check
	 *
	 * @return The Euclidean distance (on the x and z-axis) between the 2 entities
	 */
	public static double getHorizontalDistanceBetween(Entity entityA, Entity entityB) {
		return getHorizontalDistanceBetween(entityA.blockPosition(), entityB.blockPosition());
	}

	/**
	 * Gets the Euclidean vertical distance (on the y-axis) between 2 entities
	 * 
	 * @param entityA Entity A, starting pos to check
	 * @param entityB Entity B, finishing pos to check
	 *
	 * @return The Euclidean distance (on the y-axis) between the 2 entities
	 */
	public static double getVerticalDistanceBetween(Entity entityA, Entity entityB) {
		return getVerticalDistanceBetween(entityA.blockPosition(), entityB.blockPosition());
	}

	/**
	 * Gets Euclidean distance between 2 entities
	 * 
	 * @param entityA Entity A, starting pos to check
	 * @param entityB Entity B, finishing pos to check
	 *
	 * @return The total Euclidean distance between the 2 entities
	 */
	public static double getDistanceBetween(Entity entityA, Entity entityB) {
		return getDistanceBetween(entityA.blockPosition(), entityB.blockPosition());
	}
	
	/**
	 * Increases/increments a specified {@code float} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageIncrease The percentage by which to increase the specified number
	 * @return The percentage-wise increased {@code float}/specified number
	 */
	public static float increaseByPercentage(float numToReduce, float percentageIncrease) {
		float percIncActual = percentageIncrease / 100F;
		
		if (percIncActual > 1) percIncActual = 1;
		if (percIncActual < 0) percIncActual = 0;
		
		return numToReduce + (numToReduce * percIncActual);
	}
	
	/**
	 * Reduces a specified {@code float} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageReduction The percentage by which to reduce the specified number
	 * @return The percentage-wise reduced {@code float}/specified number
	 */
	public static float reduceByPercentage(float numToReduce, float percentageReduction) {
		float percRedActual = percentageReduction / 100F;
		
		if (percRedActual > 1) percRedActual = 1;
		if (percRedActual < 0) percRedActual = 0;
		
		return numToReduce - (numToReduce * percRedActual);
	}
	
	/**
	 * Increases/increments a specified {@code short} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageIncrease The percentage by which to increase the specified number
	 * @return The percentage-wise increased {@code short}/specified number
	 */
	public static short increaseByPercentage(short numToReduce, short percentageIncrease) {
		short percIncActual = (short) (percentageIncrease / 100);
		
		if (percIncActual > 1) percIncActual = 1;
		if (percIncActual < 0) percIncActual = 0;
		
		return (short) (numToReduce + (numToReduce * percIncActual));
	}
	
	/**
	 * Reduces a specified {@code short} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageReduction The percentage by which to reduce the specified number
	 * @return The percentage-wise reduced {@code short}/specified number
	 */
	public static short reduceByPercentage(short numToReduce, short percentageReduction) {
		short percRedActual = (short) (percentageReduction / 100);
		
		if (percRedActual > 1) percRedActual = 1;
		if (percRedActual < 0) percRedActual = 0;
		
		return (short) (numToReduce - (numToReduce * percRedActual));
	}
	
	/**
	 * Increases/increments a specified {@code long} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageIncrease The percentage by which to increase the specified number
	 * @return The percentage-wise increased {@code long}/specified number
	 */
	public static long increaseByPercentage(long numToReduce, long percentageIncrease) {
		long percIncActual = percentageIncrease / 100L;
		
		if (percIncActual > 1) percIncActual = 1;
		if (percIncActual < 0) percIncActual = 0;
		
		return numToReduce + (numToReduce * percIncActual);
	}
	
	/**
	 * Reduces a specified {@code long} by the specified percentage (between 0 ~ 100%, values higher than 100% default to 100%, same goes for 0).
	 * @param numToReduce The number to reduce
	 * @param percentageReduction The percentage by which to reduce the specified number
	 * @return The percentage-wise reduced {@code long}/specified number
	 */
	public static long reduceByPercentage(long numToReduce, long percentageReduction) {
		long percRedActual = percentageReduction / 100L;
		
		if (percRedActual > 1) percRedActual = 1;
		if (percRedActual < 0) percRedActual = 0;
		
		return numToReduce - (numToReduce * percRedActual);
	}

	/**
	 * Normalizes the specified {@code int} value between the specified {@code min} and {@code max} values to the specified {@code normalizedMin} and
	 * {@code normalizedMax} values. For example, if you wanted to normalize values {@code [10, 20]} to scale according to where the input {@code value}
	 * field is between the {@code normalizedMin} and {@code normalizedMax} values (E.G. Assume both aforementioned normalized values are set to {@code [200, 400]}),
	 * you'd typically want to use this method.
	 *
	 * @param value The value to normalize.
	 * @param min The minimum resulting value after normalization.
	 * @param max The maximum resulting value after normalization.
	 * @param normalizedMin The minimum normalization value.
	 * @param normalizedMax The maximum normalization value.
	 *
	 * @return The resulting normalized value.
	 */
	public static int normalizeValues(int value, int min, int max, int normalizedMin, int normalizedMax) {
		int range = max - min;
		int normalizedRange = normalizedMax - normalizedMin;
		int normalizedValue = (value - min) / range * normalizedRange + normalizedMin;

		return normalizedValue;
	}

	/**
	 * Normalizes the specified {@code double} value between the specified {@code min} and {@code max} values to the specified {@code normalizedMin} and
	 * {@code normalizedMax} values. For example, if you wanted to normalize values {@code [10, 20]} to scale according to where the input {@code value}
	 * field is between the {@code normalizedMin} and {@code normalizedMax} values (E.G. Assume both aforementioned normalized values are set to {@code [200, 400]}),
	 * you'd typically want to use this method.
	 *
	 * @param value The value to normalize.
	 * @param min The minimum resulting value after normalization.
	 * @param max The maximum resulting value after normalization.
	 * @param normalizedMin The minimum normalization value.
	 * @param normalizedMax The maximum normalization value.
	 *
	 * @return The resulting normalized value.
	 */
	public static double normalizeValues(double value, double min, double max, double normalizedMin, double normalizedMax) {
		double range = max - min;
		double normalizedRange = normalizedMax - normalizedMin;
		double normalizedValue = (value - min) / range * normalizedRange + normalizedMin;

		return normalizedValue;
	}

	/**
	 * Normalizes the specified {@code float} value between the specified {@code min} and {@code max} values to the specified {@code normalizedMin} and
	 * {@code normalizedMax} values. For example, if you wanted to normalize values {@code [10, 20]} to scale according to where the input {@code value}
	 * field is between the {@code normalizedMin} and {@code normalizedMax} values (E.G. Assume both aforementioned normalized values are set to {@code [200, 400]}),
	 * you'd typically want to use this method.
	 *
	 * @param value The value to normalize.
	 * @param min The minimum resulting value after normalization.
	 * @param max The maximum resulting value after normalization.
	 * @param normalizedMin The minimum normalization value.
	 * @param normalizedMax The maximum normalization value.
	 *
	 * @return The resulting normalized value.
	 */
	public static float normalizeValues(float value, float min, float max, float normalizedMin, float normalizedMax) {
		float range = max - min;
		float normalizedRange = normalizedMax - normalizedMin;
		float normalizedValue = (value - min) / range * normalizedRange + normalizedMin;

		return normalizedValue;
	}

	/**
	 * Normalizes the specified {@code short} value between the specified {@code min} and {@code max} values to the specified {@code normalizedMin} and
	 * {@code normalizedMax} values. For example, if you wanted to normalize values {@code [10, 20]} to scale according to where the input {@code value}
	 * field is between the {@code normalizedMin} and {@code normalizedMax} values (E.G. Assume both aforementioned normalized values are set to {@code [200, 400]}),
	 * you'd typically want to use this method.
	 *
	 * @param value The value to normalize.
	 * @param min The minimum resulting value after normalization.
	 * @param max The maximum resulting value after normalization.
	 * @param normalizedMin The minimum normalization value.
	 * @param normalizedMax The maximum normalization value.
	 *
	 * @return The resulting normalized value.
	 */
	public static short normalizeValues(short value, short min, short max, short normalizedMin, short normalizedMax) {
		short range = (short) (max - min);
		short normalizedRange = (short) (normalizedMax - normalizedMin);
		short normalizedValue = (short) ((value - min) / range * normalizedRange + normalizedMin);

		return normalizedValue;
	}

	/**
	 * Normalizes the specified {@code long} value between the specified {@code min} and {@code max} values to the specified {@code normalizedMin} and
	 * {@code normalizedMax} values. For example, if you wanted to normalize values {@code [10, 20]} to scale according to where the input {@code value}
	 * field is between the {@code normalizedMin} and {@code normalizedMax} values (E.G. Assume both aforementioned normalized values are set to {@code [200, 400]}),
	 * you'd typically want to use this method.
	 *
	 * @param value The value to normalize.
	 * @param min The minimum resulting value after normalization.
	 * @param max The maximum resulting value after normalization.
	 * @param normalizedMin The minimum normalization value.
	 * @param normalizedMax The maximum normalization value.
	 *
	 * @return The resulting normalized value.
	 */
	public static long normalizeValues(long value, long min, long max, long normalizedMin, long normalizedMax) {
		long range = max - min;
		long normalizedRange = normalizedMax - normalizedMin;
		long normalizedValue = (value - min) / range * normalizedRange + normalizedMin;

		return normalizedValue;
	}

	/**
	 * Approximates the horizontal (X-Z) angle (in degrees) from the specified {@linkplain LivingEntity owner entity's} position to the specified {@link Vector3d}.
	 *
	 * @param ownerEntity The {@link LivingEntity} to get the initial position from and calculate the angle for.
	 * @param wantedPos The {@link Vector3d} to get the angle (in degrees) towards.
	 *
	 * @return The horizontal angle (in degrees) between the specified {@link LivingEntity} and the specified {@link Vector3d}.
	 */
	public static double getHorizontalAngleTowards(LivingEntity ownerEntity, Vector3d wantedPos) {
		double dx = wantedPos.x - ownerEntity.getX();
		double dz = wantedPos.z - ownerEntity.getZ();

		return Math.toDegrees(MathHelper.atan2(dz, dx)) - 90.0D;
	}

	/**
	 * Approximates the total angle (in degrees) from the specified {@linkplain LivingEntity owner entity's} position to the specified {@link Vector3d}.
	 *
	 * @param ownerEntity The {@link LivingEntity} to get the initial position from and calculate the angle for.
	 * @param wantedPos The {@link Vector3d} to get the angle (in degrees) towards.
	 *
	 * @return The total angle (in degrees) between the specified {@link LivingEntity} and the specified {@link Vector3d}.
	 */
	public static double getAngleTowards(LivingEntity ownerEntity, Vector3d wantedPos) {
		double totalHorizontalDistance = Math.sqrt(Math.pow(wantedPos.x, 2) + Math.pow(wantedPos.z, 2));
		double dy = wantedPos.y - ownerEntity.getY();

		return Math.toDegrees(MathHelper.atan2(dy, totalHorizontalDistance));
	}
}
