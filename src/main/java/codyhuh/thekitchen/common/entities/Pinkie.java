package codyhuh.thekitchen.common.entities;

import codyhuh.thekitchen.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

// todo - rename to Pinky
public class Pinkie extends Animal {

    public Pinkie(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        // todo - move to pinkie goal (so they group together)
    }

    public static AttributeSupplier.Builder createPinkieAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D).add(Attributes.MOVEMENT_SPEED, 0.0F);
    }

    @Override
    public void tick() {
        super.tick();

        if (age == 0) {
            if (level() instanceof ServerLevel serverlevel) {
                Mouse mouse = ModEntities.MOUSE.get().create(this.level());

                if (mouse != null) {
                    mouse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                    mouse.finalizeSpawn(serverlevel, this.level().getCurrentDifficultyAt(mouse.blockPosition()), MobSpawnType.CONVERSION, null, null);
                    mouse.setNoAi(this.isNoAi());
                    if (this.hasCustomName()) {
                        mouse.setCustomName(this.getCustomName());
                        mouse.setCustomNameVisible(this.isCustomNameVisible());
                    }

                    mouse.setPersistenceRequired();
                    //this.playSound(SoundEvents.TADPOLE_GROW_UP, 0.15F, 1.0F);
                    serverlevel.addFreshEntityWithPassengers(mouse);
                    this.discard();
                }
            }
        }
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.MOUSE.get().create(pLevel);
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.5F;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        if (tag == null) {
            setAge(-24000);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
    }
}
