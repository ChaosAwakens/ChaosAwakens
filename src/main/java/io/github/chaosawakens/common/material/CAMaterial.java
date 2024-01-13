package io.github.chaosawakens.common.material;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class CAMaterial {
	public static final Material FLOWER_BLOCK = new Builder(MaterialColor.PLANT).build();
	public static final Material HEAVY_GLASS = new Builder(MaterialColor.NONE).notSolidBlocking().notPushable().build();

	private final MaterialColor color;
	private final PushReaction pushReaction;
	private final boolean blocksMotion;
	private final boolean flammable;
	private final boolean liquid;
	private final boolean solidBlocking;
	private final boolean replaceable;
	private final boolean solid;

	public CAMaterial(MaterialColor color, boolean isLiquid, boolean isSolid, boolean motionBlocking, boolean solidBlocking, boolean isFlammable, boolean isReplacable, PushReaction pushReaction) {
		this.color = color;
		this.liquid = isLiquid;
		this.solid = isSolid;
		this.blocksMotion = motionBlocking;
		this.solidBlocking = solidBlocking;
		this.flammable = isFlammable;
		this.replaceable = isReplacable;
		this.pushReaction = pushReaction;
	}

	public boolean isLiquid() {
		return this.liquid;
	}

	public boolean isSolid() {
		return this.solid;
	}

	public boolean blocksMotion() {
		return this.blocksMotion;
	}

	public boolean isFlammable() {
		return this.flammable;
	}

	public boolean isReplaceable() {
		return this.replaceable;
	}

	public boolean isSolidBlocking() {
		return this.solidBlocking;
	}

	public PushReaction getPushReaction() {
		return this.pushReaction;
	}

	public MaterialColor getColor() {
		return this.color;
	}

	public static class Builder {
		private PushReaction pushReaction = PushReaction.NORMAL;
		private boolean blocksMotion = true;
		private boolean flammable;
		private boolean liquid;
		private boolean replaceable;
		private boolean solid = true;
		private final MaterialColor color;
		private boolean solidBlocking = true;

		public Builder(MaterialColor color) {
			this.color = color;
		}

		public Builder liquid() {
			this.liquid = true;
			return this;
		}

		public Builder nonSolid() {
			this.solid = false;
			return this;
		}

		public Builder noCollider() {
			this.blocksMotion = false;
			return this;
		}

		public Builder notSolidBlocking() {
			this.solidBlocking = false;
			return this;
		}

		public Builder flammable() {
			this.flammable = true;
			return this;
		}

		public Builder replaceable() {
			this.replaceable = true;
			return this;
		}

		public Builder destroyOnPush() {
			this.pushReaction = PushReaction.DESTROY;
			return this;
		}

		public Builder notPushable() {
			this.pushReaction = PushReaction.BLOCK;
			return this;
		}

		public Material build() {
			return new Material(this.color, this.liquid, this.solid, this.blocksMotion, this.solidBlocking, this.flammable, this.replaceable, this.pushReaction);
		}
	}
}
