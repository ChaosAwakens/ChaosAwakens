package io.github.chaosawakens.api.animation;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class AdvancedAnimationInformation {
	private final Set<ResourceLocation> animationDirectories = new ObjectArraySet<>();
	private final Set<ResourceLocation> modelDirectories = new ObjectArraySet<>();
	private final Set<ResourceLocation> textureDirectories = new ObjectArraySet<>();

	public AdvancedAnimationInformation(Set<ResourceLocation> animationDirectories, Set<ResourceLocation> modelDirectories, Set<ResourceLocation> textureDirectories) {
		this.animationDirectories.addAll(animationDirectories);
		this.modelDirectories.addAll(modelDirectories);
		this.textureDirectories.addAll(textureDirectories);
	}

	public AdvancedAnimationInformation() {

	}

	public ImmutableSet<ResourceLocation> getAnimationDirectories() {
		return ImmutableSet.copyOf(animationDirectories);
	}

	public ImmutableSet<ResourceLocation> getModelDirectories() {
		return ImmutableSet.copyOf(modelDirectories);
	}

	public ImmutableSet<ResourceLocation> getTextureDirectories() {
		return ImmutableSet.copyOf(textureDirectories);
	}

	public ResourceLocation appendTexture(ResourceLocation texLoc) {
		this.textureDirectories.add(texLoc);
		return texLoc;
	}

	public ResourceLocation appendAnimation(ResourceLocation animLoc) {
		this.animationDirectories.add(animLoc);
		return animLoc;
	}

	public ResourceLocation appendModel(ResourceLocation modelLoc) {
		this.modelDirectories.add(modelLoc);
		return modelLoc;
	}

	public Set<ResourceLocation> appendTextures(Set<ResourceLocation> texLocs) {
		this.textureDirectories.addAll(texLocs);
		return texLocs;
	}

	public Set<ResourceLocation> appendAnimations(Set<ResourceLocation> animLocs) {
		this.animationDirectories.addAll(animLocs);
		return animLocs;
	}

	public Set<ResourceLocation> appendModels(Set<ResourceLocation> modelLocs) {
		this.modelDirectories.addAll(modelLocs);
		return modelLocs;
	}

	public void reset() {
		this.animationDirectories.clear();
		this.modelDirectories.clear();
		this.textureDirectories.clear();
	}
}
