package codyhuh.thekitchen.common;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.common.entities.Mouse;
import codyhuh.thekitchen.common.entities.Pinky;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = TheKitchen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    private static final Predicate<LivingEntity> MOUSE_ATTACKABLE_SELECTOR = LivingEntity::isAlive;

    @SubscribeEvent
    public static void addEntityGoals(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof Cat cat) {
            cat.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(cat, Mouse.class, false, MOUSE_ATTACKABLE_SELECTOR));
            cat.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(cat, Pinky.class, false, MOUSE_ATTACKABLE_SELECTOR));
        }
    }
}
