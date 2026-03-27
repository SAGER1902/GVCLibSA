package gvclib.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

public class RenderLaserSight {

	private static final ResourceLocation t0 = new ResourceLocation("gvclib:textures/marker/lasersight.png");
    private static final IModelCustom marker = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/marker/lasersight.mqo"));
    
    public static void can(EntityGVCLivingBase entity, float x, float y, float z, float bai, ResourceLocation maintex) {
		GL11.glPushMatrix();//glstart
		//GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		{
			float value = (float)Math.sin(entity.ontick100sec * 0.1) * 0.1F;
			GL11.glTranslatef(x, y, z);
			GL11.glScalef(bai, bai, bai);
			GL11.glScalef(1F + value, 1F + value, 1F + value);
			//GL11.glTranslatef(0.0F, value, 0.0F);
			GlStateManager.disableLighting();
			GL11.glColor4f(1F, 1F, 1F, 0.75F);
			Minecraft.getMinecraft().renderEngine.bindTexture(t0);
	        marker.renderPart("mat1");
	        GlStateManager.enableLighting();
		}
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();//glend
		
		Minecraft.getMinecraft().renderEngine.bindTexture(maintex);
	}
	
}
