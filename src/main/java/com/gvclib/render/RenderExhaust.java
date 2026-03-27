package gvclib.render;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

public class RenderExhaust {

	private static final ResourceLocation t0 = new ResourceLocation("gvclib:textures/marker/exhaust.png");
    private static final IModelCustom marker = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/marker/exhaust.mqo"));
    
	public static void can(EntityVehicleBase entity, float x, float y, float z, float bai, boolean ab, ResourceLocation maintex) {
		
		{
			 if(entity.model_exhaust == null && entity.getModel_exhaust() != null){
		        	boolean mo = false;
		        	for(int ii = 0; ii < 1024; ++ii) {
		        		if(mod_GVCLib.modelt[ii] != null && mod_GVCLib.modelt[ii].equals(entity.getModel_exhaust())) {
		        			entity.model_exhaust  = mod_GVCLib.model[ii];
		        			mo = true;
		        			break;
		        		}
		        	}
		        	if(!mo) 
		        	{
		        		ResourceLocation resource = new ResourceLocation(entity.getModel_exhaust());
		        		try {
							IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
							if(res != null) {
			        			entity.model_exhaust = AdvancedModelLoader.loadModel(resource);
			            		++mod_GVCLib.entityid;
			            		mod_GVCLib.model[mod_GVCLib.entityid] = entity.model_exhaust;
			            		mod_GVCLib.modelt[mod_GVCLib.entityid] = entity.getModel_exhaust();
			            		System.out.println(String.format("HMGGVC-" + mod_GVCLib.entityid));
			        		}
						} catch (IOException e) {
							//e.printStackTrace();
							System.out.println(String.format("warning! not exist model!::::" + entity.getModel_exhaust()));
							entity.setModel_exhaust("gvclib:textures/marker/exhaust.mqo");
							entity.setTex_exhaust("gvclib:textures/marker/exhaust.png");
						}
		        		
		        	}
					
				}
		}
		
		
		
		GL11.glPushMatrix();//glstart
		//GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_BLEND);
		if(entity.model_exhaust != null){
			float value = (float)Math.sin(entity.ontick100sec * 0.1) * 0.1F;
			
			
			
			GL11.glTranslatef(x, y, z);
			GL11.glRotatef((float)mod_GVCLib.proxy.getCilentWorld().getWorldTime()  * 100F, 0F, 0F, 1F);
			GL11.glTranslatef(-x, -y, -z);
			
			GL11.glTranslatef(x, y, z);
			GL11.glScalef(bai, bai, bai);
			//GL11.glScalef(1F + value, 1F + value, 1F + value);
			//GL11.glTranslatef(0.0F, value, 0.0F);
			GlStateManager.disableLighting();
			boolean can = false;
			boolean canab = false;
			if(entity instanceof EntityVehicleBase) {
				EntityVehicleBase vehicle = (EntityVehicleBase) entity;
				if (vehicle.throttle > 0) {
					can = true;
					if (vehicle.throttle >= vehicle.thmax - 2) {
						canab = true;
					}
				}
				
			}
			GL11.glColor4f(1F, 1F, 1F, entity.throttle * 0.03F);
			if(entity.getTex_exhaust() != null)Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(entity.getTex_exhaust()));
			 if(can) {
				 int i = 15728880;
			        int j = i % 65536;
			        int k = i / 65536;
			        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
			        entity.model_exhaust.renderPart("mat2");
			        if(canab && ab) {
			        	entity.model_exhaust.renderPart("mat1");
			        }
			 }
	        GlStateManager.enableLighting();
		}
		GL11.glColor4f(1F, 1F, 1F, 1F);
		 GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();//glend
		
		Minecraft.getMinecraft().renderEngine.bindTexture(maintex);
	}
	
}
