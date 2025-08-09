package rings_of_saturn.github.io.smart_observer.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import rings_of_saturn.github.io.smart_observer.block.ModBlocks;

import static rings_of_saturn.github.io.smart_observer.SmartObserver.MOD_ID;


public class ModBlockEntities {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, path), blockEntityType);
    }

    public static final BlockEntityType<SmartObserverBlockEntity> SMART_OBSERVER = register(
            "smart_observer",
            BlockEntityType.Builder.create(SmartObserverBlockEntity::new, ModBlocks.SMART_OBSERVER).build()
    );

    public static void registerBlockEntities(){

    }
}
