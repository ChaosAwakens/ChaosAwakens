package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelBoneData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelMetadata;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import it.unimi.dsi.fastutil.objects.Object2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectAVLTreeSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.phys.AABB;

import java.util.*;

public class GeckolibSkeleton implements Skeleton {
    protected final Set<Bone> bones = new ObjectAVLTreeSet<>(Comparator.comparingInt(bone -> bone.getDirectParents().size()));
    protected Set<Bone> rootBones = new ObjectOpenHashSet<>();
    protected Map<String, Bone> bonesByName = new Object2ObjectOpenHashMap<>();
    protected Map<Bone, Set<Bone>> boneBranches = new Object2ObjectAVLTreeMap<>(Comparator.comparingInt(bone -> bone.getDirectParents().size()));
    protected boolean needsUpdate = false;
    protected AABB generalBounds = new AABB(0, 0, 0, 0, 0, 0);
    protected ModelInfo curModelInfo;

    public GeckolibSkeleton(ModelInfo modelInfo) {
        initializeBoneTree(modelInfo);
    }

    @Override
    public Set<Bone> getAllBones() {
        return bones;
    }

    @Override
    public Set<Bone> getRootBones() {
        return rootBones;
    }

    @Override
    public Map<String, Bone> getBonesByName() {
        return bonesByName;
    }

    @Override
    public Map<Bone, Set<Bone>> getBoneBranches() {
        return boneBranches;
    }

    @Override
    public Optional<Bone> getBoneByName(String boneName) {
        return Optional.ofNullable(bonesByName.get(boneName));
    }

    @Override
    public void initializeBoneTree(ModelInfo modelInfo) {
        if (modelInfo == null) {
            throw new IllegalArgumentException("Attempted to pass null model info to GeckolibSkeleton instance!");
        }

        this.curModelInfo = modelInfo;

        Multimap<String, String> preBakedBonesByName = HashMultimap.create();

        List<ModelBoneData> modelBones = modelInfo.getGeometryInfo().getBones();

        if (modelBones.isEmpty()) {
            CAConstants.LOGGER.warn("Attempted to construct empty skeleton for model {}, skipping...", modelInfo.getGeometryInfo().getMetadata().map(ModelMetadata::getGeometryId).orElse("[Unknown Model]"));
            return;
        }

        modelBones.forEach(boneData -> {
            boneData.getParentBoneName().ifPresentOrElse(
                    parentBoneName -> preBakedBonesByName.put(parentBoneName, boneData.getBoneName()),
                    () -> preBakedBonesByName.put("root", boneData.getBoneName())
            );

        });
    }

    @Override
    public void updateBranch(Bone bone) {

    }

    @Override
    public void refresh(ModelInfo updatedModelInfo) {
        if (updatedModelInfo != null) {
            this.curModelInfo = updatedModelInfo;
            this.needsUpdate = true;
        }
    }

    @Override
    public AABB getGeneralBounds() {
        if (needsUpdate) {
            this.needsUpdate = false;
            this.generalBounds = getCoordinateData(false).getWorldAABB();
        }

        return generalBounds;
    }
}
