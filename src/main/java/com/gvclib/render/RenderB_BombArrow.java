package gvclib.render;

import gvclib.entity.EntityB_BombArrow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

@SideOnly(Side.CLIENT)
public class RenderB_BombArrow<T extends EntityB_BombArrow> extends Render<T>
{
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/smoke.obj"));
    private static final ResourceLocation boatTextures0 = new ResourceLocation("gvclib:textures/model/smoke0.png");
    public RenderB_BombArrow(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        //this.bindEntityTexture(new ResourceLocation(entity.getModel()));
        GlStateManager.pushMatrix();
        //GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
        
        if(entity.model == null && entity.getModel() != null){
			{
				entity.model = AdvancedModelLoader
						.loadModel(new ResourceLocation(entity.getModel()));
			}
		}
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.getTextureManager().bindTexture(new ResourceLocation(entity.getTex()));
        entity.model.renderPart("bullet");
        {
        	GlStateManager.disableLighting();
        	float size = partialTicks * 0.4F + 1;
        	GlStateManager.scale(size, size, size);
        	entity.model.renderPart("bullet_e_1");
        	 GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return boatTextures0;
	}
}