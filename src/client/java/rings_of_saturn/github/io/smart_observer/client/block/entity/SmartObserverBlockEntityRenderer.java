package rings_of_saturn.github.io.smart_observer.client.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;
import rings_of_saturn.github.io.smart_observer.block.entity.SmartObserverBlockEntity;

public class SmartObserverBlockEntityRenderer implements BlockEntityRenderer<SmartObserverBlockEntity> {
    public SmartObserverBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(SmartObserverBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(blockEntity.getStack(0).getItem() != Items.AIR) {
            matrices.push();

            double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
            // Move the item
            matrices.translate(0.5, 1.25 + offset, 0.5);

            // Rotate the item
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((blockEntity.getWorld().getTime() + tickDelta) * 4));

            int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getStack(0), ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, blockEntity.getWorld(), 0);

            // Mandatory call after GL calls
            matrices.pop();
        }
    }
}
