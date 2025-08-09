package rings_of_saturn.github.io.smart_observer;

import net.fabricmc.api.ModInitializer;

import static rings_of_saturn.github.io.smart_observer.block.ModBlocks.registerBlocks;
import static rings_of_saturn.github.io.smart_observer.block.entity.ModBlockEntities.registerBlockEntities;

public class SmartObserver implements ModInitializer {
    public static final String MOD_ID = "smart_observer";
    @Override
    public void onInitialize() {
        registerBlocks();
        registerBlockEntities();
    }
}
