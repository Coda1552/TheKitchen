package codyhuh.thekitchen.registry;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.common.entities.Mouse;
import codyhuh.thekitchen.common.entities.Pinky;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheKitchen.MOD_ID);

    public static final RegistryObject<EntityType<Mouse>> MOUSE = ENTITIES.register("mouse", () -> EntityType.Builder.of(Mouse::new, MobCategory.CREATURE).sized(0.25F, 0.25F).build("mouse"));
    public static final RegistryObject<EntityType<Pinky>> PINKY = ENTITIES.register("pinky", () -> EntityType.Builder.of(Pinky::new, MobCategory.CREATURE).sized(0.3F, 0.3F).build("pinky"));
}
