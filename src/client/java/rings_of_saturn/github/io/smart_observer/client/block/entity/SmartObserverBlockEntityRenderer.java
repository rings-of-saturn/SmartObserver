package rings_of_saturn.github.io.smart_observer.client.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import rings_of_saturn.github.io.smart_observer.block.entity.SmartObserverBlockEntity;

public class SmartObserverBlockEntityRenderer implements BlockEntityRenderer<SmartObserverBlockEntity> {
    public SmartObserverBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(SmartObserverBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(blockEntity.getStack(0) != null) {
            matrices.push();

            // Move the item
            matrices.translate(0.5, 0.5, 0.5);

            MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getStack(0), ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);

            // Mandatory call after GL calls
            matrices.pop();
        }
    }
}
