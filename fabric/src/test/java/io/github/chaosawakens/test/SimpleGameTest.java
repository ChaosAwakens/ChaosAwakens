package io.github.chaosawakens.test;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;

/**
 * A simple test class demonstrating the Fabric Game Test API.
 */
public class SimpleGameTest implements FabricGameTest {

    /**
     * A simple test that checks if a chicken can be spawned.
     */
    @GameTest(template = EMPTY_STRUCTURE)
    public void testSpawnChicken(GameTestHelper helper) {
        // Get the center position of the test structure
        BlockPos pos = new BlockPos(0, 2, 0);

        // Place a grass block at the center
        helper.setBlock(pos, Blocks.GRASS_BLOCK);

        // Spawn a chicken on the grass block
        helper.spawn(EntityType.CHICKEN, pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f);

        // Check if the chicken exists
        helper.assertEntityPresent(EntityType.CHICKEN);

        // Mark the test as successful
        helper.succeed();
    }

    /**
     * A simple test that checks block placement.
     */
    @GameTest(template = EMPTY_STRUCTURE)
    public void testBlockPlacement(GameTestHelper helper) {
        // Get the center position of the test structure
        BlockPos pos = new BlockPos(0, 2, 0);

        // Place a diamond block at the center
        helper.setBlock(pos, Blocks.DIAMOND_BLOCK);

        // Check if the block is a diamond block
        helper.assertBlockPresent(Blocks.DIAMOND_BLOCK, pos);

        // Mark the test as successful
        helper.succeed();
    }
}
