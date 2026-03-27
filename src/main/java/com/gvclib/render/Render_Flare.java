
package gvclib.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.entity.Entity_Flare;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

public class Render_Flare extends Render {

	private static final ResourceLocation skeletonTexturesz = new ResourceLocation("gvclib:textures/entity/flare.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/entity/flare.mqo"));
	private float scale;

	public Render_Flare(RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		this.doRender((Entity_Flare) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/*private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_)
    {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
	*/
	
	float xsxs;
	public void doRender(Entity_Flare entity, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glRotatef(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
		tankk.renderPart("bullet");
        {
        	GlStateManager.disableLighting();
        	float size = partialTicks * 0.4F + 1;
        	GlStateManager.scale(size, size, size);
        	tankk.renderPart("bullet_e_1");
        	 GlStateManager.enableLighting();
        }
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		
		
	}
	
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float p_76986_8_, float p_76986_9_) {
		this.doRender((Entity_Flare) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving) {
		return this.skeletonTexturesz;
	}

}
