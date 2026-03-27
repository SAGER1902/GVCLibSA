package gvclib.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.entity.living.EntityGVCLivingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

public class RenderCanMobRidding {

	private static final ResourceLocation t0 = new ResourceLocation("gvclib:textures/marker/marker0.png");
    private static final IModelCustom marker = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/marker/newmarker.mqo"));
	
	public static void can(EntityGVCLivingBase entity) {
		GL11.glPushMatrix();//glstart
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		if(!entity.getVehicleLock() && entity.getControllingPassenger() == null && entity.getHealth() > 0.0F)
		{
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			//GL11.glTranslatef(0, entity.height + 1F, 0);
			//GL11.glTranslatef(0, entity.height / 2, 0);
			GL11.glScalef(1F * entity.width, 1F, 1F * entity.width);
			GL11.glColor4f(1F, 1F, 1F, 0.3F);
			Minecraft.getMinecraft().renderEngine.bindTexture(t0);
	        marker.renderPart("mat1");
			
			/*if(entity.wrench_lock){
				marker.renderPart("mat2");
			}*/
			
			if(entity.getControllingPassenger() != null && entity.getControllingPassenger() instanceof EntityGVCLivingBase){
				EntityGVCLivingBase ridding = (EntityGVCLivingBase) entity.getControllingPassenger();
				if(ridding.getArmID_A()==1){
					marker.renderPart("mat2");
				}
			}
	        GL11.glDisable(GL11.GL_BLEND);
		}
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();//glend
	}
	
}
