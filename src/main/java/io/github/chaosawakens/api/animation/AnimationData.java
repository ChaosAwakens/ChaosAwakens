package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.common.util.FileUtil;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Unit;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * TODO Unused
 * Credits to DerToaster for foundational implementation/idea. Code reworked and refactored (by me, duh) in accordance to 1.16 conventions and API.
 */
public class AnimationData implements IReloadableResourceManager {
    private final List<AnimationDataHolder> animationInformation;
    private final File serverAnimAssetDir = createServerAnimDirectory();
    private final File clientAnimAssetDir = createClientAnimDirectory();
    private final File serverModelAssetDir = createServerModelDirectory();
    private final File clientModelAssetDir = createClientModelDirectory();
    private final LazyLoadedField<Set<String>> namespaces = new LazyLoadedField<>(this::extractNamespaces);
    private final Set<ResourceLocation> currentlyEnforcedAssets = new ObjectOpenHashSet<>();

    public AnimationData(List<AnimationDataHolder> animationInformation) {
        this.animationInformation = animationInformation;
    }

    public byte[] encodeAnimationData(ResourceLocation targetAnimLoc) {
        return new byte[0];
    }

    public byte[] encodeModelData(ResourceLocation targetModelLoc) {
        return new byte[0];
    }

    public byte[] decodeAnimationData() {
        return new byte[0];
    }

    public byte[] decodeModelData() {
        return new byte[0];
    }

    protected static byte[] compressData(ResourceLocation targetDataLoc) {
        return new byte[0];
    }

    protected static byte[] decompressData(ResourceLocation targetDataLoc) {
        return new byte[0];
    }

    protected File createServerAnimDirectory() {
        return new File(FileUtil.CA_SERVER_ASSET_DIR, "animations");
    }

    protected File createClientAnimDirectory() {
        return new File(FileUtil.CA_CLIENT_ASSET_DIR, "animations");
    }

    protected File createServerModelDirectory() {
        return new File(FileUtil.CA_SERVER_ASSET_DIR, "models");
    }

    protected File createClientModelDirectory() {
        return new File(FileUtil.CA_CLIENT_ASSET_DIR, "models");
    }

    @Override
    public IAsyncReloader createFullReload(Executor p_219537_1_, Executor p_219537_2_, CompletableFuture<Unit> p_219537_3_, List<IResourcePack> p_219537_4_) {
        return null;
    }

    @Override
    public void registerReloadListener(IFutureReloadListener pListener) {

    }

    @Override
    public void close() {

    }

    @Override
    public Set<String> getNamespaces() {
        return namespaces.get();
    }

    private Set<String> extractNamespaces() {
        Set<String> result = new HashSet<>();

        this.currentlyEnforcedAssets.forEach(rs -> result.add(rs.getNamespace()));

        return result;
    }

    @Override
    public IResource getResource(ResourceLocation targetResource) throws IOException {
        return null;
    }

    @Override
    public boolean hasResource(ResourceLocation pPath) {
        return false;
    }

    @Override
    public List<IResource> getResources(ResourceLocation pResourceLocation) throws IOException {
        return null;
    }

    @Override
    public Collection<ResourceLocation> listResources(String pPath, Predicate<String> pFilter) {
        return null;
    }

    @Override
    public Stream<IResourcePack> listPacks() {
        return null;
    }
}

