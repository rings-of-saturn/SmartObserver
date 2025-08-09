package rings_of_saturn.github.io.smart_observer.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import rings_of_saturn.github.io.smart_observer.block.entity.ModBlockEntities;
import rings_of_saturn.github.io.smart_observer.client.block.entity.SmartObserverBlockEntityRenderer;

public class SmartObserverClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.SMART_OBSERVER, SmartObserverBlockEntityRenderer::new);
    }
}
