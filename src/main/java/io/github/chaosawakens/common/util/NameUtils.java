package io.github.chaosawakens.common.util;

import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

public final class NameUtils {
	private static final Pattern PATTERN = Pattern.compile("([a-z0-9._-]+:)?[a-z0-9/._-]+");

	private NameUtils() {
		throw new IllegalAccessError("Utility class");
	}

	public static boolean isValid(CharSequence name) {
		return PATTERN.matcher(name).matches();
	}

	/**
	 * Verify name is not null, throwing an exception if it is.
	 *
	 * @param name Possibly null ResourceLocation
	 * @return name
	 * @throws NullPointerException if name is null
	 */
	public static ResourceLocation checkNotNull(@Nullable ResourceLocation name) {
		Preconditions.checkNotNull(name, "Name is null, make sure the object has been registered correctly");
		return name;
	}

	/**
	 * Get a ResourceLocation with namespace "forge". Does not handle exceptions.
	 *
	 * @param path The path (must be /[a-z0-9/._-]+/)
	 * @return A new ResourceLocation
	 * @throws net.minecraft.util.ResourceLocationException if path is invalid
	 */
	public static ResourceLocation forgeId(String path) {
		return new ResourceLocation("forge", path);
	}

	/**
	 * Gets the registry name, throwing an exception if it is null
	 *
	 * @param entry The registry object
	 * @return The registry name
	 * @throws NullPointerException if registry name is null
	 */
	public static ResourceLocation from(IForgeRegistryEntry<?> entry) {
		return checkNotNull(entry.getRegistryName());
	}

	/**
	 * Gets the item's registry name, throwing an exception if it is null
	 *
	 * @param item The item
	 * @return The registry name
	 * @throws NullPointerException if registry name is null
	 */
	public static ResourceLocation fromItem(IItemProvider item) {
		Preconditions.checkNotNull(item.asItem(), "asItem() is null, has object not been fully constructed?");
		return checkNotNull(item.asItem().getRegistryName());
	}

	/**
	 * Gets the registry name of the stack's item, throwing an exception if it is
	 * null
	 *
	 * @param stack The ItemStack
	 * @return The registry name
	 * @throws NullPointerException if registry name is null
	 */
	public static ResourceLocation fromItem(ItemStack stack) {
		return checkNotNull(stack.getItem().getRegistryName());
	}
}