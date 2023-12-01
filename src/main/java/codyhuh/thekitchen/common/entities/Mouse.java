package codyhuh.thekitchen.common.entities;

import codyhuh.thekitchen.common.entities.goal.MousePanicGoal;
import codyhuh.thekitchen.registry.ModEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class Mouse extends Animal {
    private static final EntityDataAccessor<Boolean> IS_FLEEING = SynchedEntityData.defineId(Mouse.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FLEEING_TICKS = SynchedEntityData.defineId(Mouse.class, EntityDataSerializers.INT);

    public Mouse(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new MousePanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D, Ingredient.of(Tags.Items.SEEDS), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.25D, 1.25D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D, 40));
    }

    public static AttributeSupplier.Builder createMouseAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 5.0D).add(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    public void tick() {
        super.tick();

        if (getFleeingTicks() > 0 && isFleeing()) {
            setFleeingTicks(getFleeingTicks() - 1);
        }

        if (getFleeingTicks() == 0 && isFleeing()) {
            setFleeing(false);
        }

    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        for (Mouse nearbyMouse : level().getNearbyEntities(Mouse.class, TargetingConditions.forCombat(), this, getBoundingBox().inflate(8.0D))) {
            nearbyMouse.setFleeing(true);
            nearbyMouse.setFleeingTicks(100 + random.nextInt(50, 100));
        }
        setFleeing(true);
        setFleeingTicks(100 + random.nextInt(50, 100));

        return super.hurt(source, amount);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public void setBaby(boolean pBaby) {
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.PINKIE.get().create(pLevel);
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.8F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_FLEEING, false);
        this.entityData.define(FLEEING_TICKS, 0);
    }

    public boolean isFleeing() {
        return this.entityData.get(IS_FLEEING);
    }

    public void setFleeing(boolean fleeing) {
        this.entityData.set(IS_FLEEING, fleeing);
    }

    public int getFleeingTicks() {
        return this.entityData.get(FLEEING_TICKS);
    }

    public void setFleeingTicks(int fleeingTicks) {
        this.entityData.set(FLEEING_TICKS, fleeingTicks);
    }
}
