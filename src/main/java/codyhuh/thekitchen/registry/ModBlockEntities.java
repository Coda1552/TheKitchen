package codyhuh.thekitchen.registry;

import codyhuh.thekitchen.TheKitchen;
import codyhuh.thekitchen.common.blockentities.LittlePlatterBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TheKitchen.MOD_ID);

    public static final RegistryObject<BlockEntityType<LittlePlatterBlockEntity>> LITTLE_PLATTER = BLOCK_ENTITIES.register("little_platter", () -> BlockEntityType.Builder.of(LittlePlatterBlockEntity::new, ModBlocks.LITTLE_PLATTER.get()).build(null));
}
