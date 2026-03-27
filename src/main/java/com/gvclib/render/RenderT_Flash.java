package gvclib.render;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import gvclib.mod_GVCLib;
import gvclib.entity.EntityT_Flash;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

import net.minecraft.client.renderer.OpenGlHelper;
import gvclib.render.SARenderHelper;
import gvclib.render.SARenderHelper.RenderType;
@SideOnly(Side.CLIENT)
public class RenderT_Flash<T extends EntityT_Flash> extends Render<T>
{
    private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/entity/mflash.mqo"));
    private static final ResourceLocation boatTextures0 = new ResourceLocation("gvclib:textures/entity/mflash.png");

    public RenderT_Flash(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        //this.rendermanager = renderManagerIn;
    }

   // public  int moi = 0;
	//public  String[] modelt = new String[64];
	//public  IModelCustom[] model = new IModelCustom[64];
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	
    	Minecraft minecraft = Minecraft.getMinecraft();
        GlStateManager.pushMatrix();

		minecraft.getTextureManager().bindTexture(new ResourceLocation(entity.getTex()));
		
        if(entity.model == null && entity.getModel() != null){
        	boolean mo = false;
        	for(int ii = 0; ii < 1024; ++ii) {
        		if(mod_GVCLib.modelt[ii] != null && mod_GVCLib.modelt[ii].equals(entity.getModel())) {
        			entity.model  = mod_GVCLib.model[ii];
        			mo = true;
        			break;
        		}
        	}
        	if(!mo) 
        	{
        		ResourceLocation resource = new ResourceLocation(entity.getModel());
        		try {
					IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resource);
					if(res != null) {
	        			entity.model = AdvancedModelLoader.loadModel(resource);
	            		++mod_GVCLib.entityid;
	            		mod_GVCLib.model[mod_GVCLib.entityid] = entity.model;
	            		mod_GVCLib.modelt[mod_GVCLib.entityid] = entity.getModel();
	            		System.out.println(String.format("HMGGVC-" + mod_GVCLib.entityid));
	        		}
				} catch (IOException e) {
					//e.printStackTrace();
					//System.out.println(String.format("warning! not exist model!::::" + entity.getModel()));
				}
        		
        	}
			
		}
		
		GL11.glPushMatrix();//glstart
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
		
		if(entity.model != null){
			float prog = ((float)entity.time / (float)entity.timemax); 
			double mainColor = 1.0-Math.pow(prog, 2.0);
			double mainAlpha = Math.pow(1.0-prog, 2.0);
			double overlayColor = 0.5+(Math.sin((Math.sqrt(prog)+0.75)*2.0*Math.PI)/2);
			GL11.glPushMatrix();//glstart
			SARenderHelper.enableBlendMode(RenderType.ADDITIVE);//ALPHA
			//GlStateManager.color(1.0f, (float)mainColor, (float)mainColor, (float)mainAlpha);
			if(entity.time > 0 && entity.time < entity.timemax) {
				float size = partialTicks * 0.4F + 0.5F;
				//GL11.glTranslatef(0, 8.4F, 1.1F);
				//GL11.glRotatef(entity.time * 60, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(entity.time * 60, 0.0F, 0.0F, 1.0F);
				if(entity.getLong()>1){
					GlStateManager.scale(size, size, entity.getLong());
				}else{
					GlStateManager.scale(size, size, 2f);
				}
				if(entity.time<entity.timemax*0.3f){
					entity.model.renderPart("beam1");
				}else if(entity.time<entity.timemax*0.4f){
					entity.model.renderPart("beam2");
				}else if(entity.time<entity.timemax*0.5f){
					entity.model.renderPart("beam3");
				}else if(entity.time<entity.timemax*0.6f){
					entity.model.renderPart("beam4");
				}else if(entity.time<entity.timemax*0.7f){
					entity.model.renderPart("beam5");
				}else if(entity.time<entity.timemax*0.8f){
					entity.model.renderPart("beam6");
				}else if(entity.time<entity.timemax*0.9f){
					entity.model.renderPart("beam7");
				}else if(entity.time<entity.timemax){
					entity.model.renderPart("beam8");
				}
			}
			//GlStateManager.color((float)overlayColor, (float)overlayColor, (float)overlayColor);
			SARenderHelper.disableBlendMode(RenderType.ADDITIVE);//ADDITIVE
			GL11.glPopMatrix();//glend
		}
		
		GL11.glPopMatrix();//glend
		
        //GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
        
        if(entity.model != null){
			GL11.glColor4f(1F, 1F, 1F, 0.6F);
            {
				SARenderHelper.enableBlendMode(RenderType.ALPHA);//ALPHA
				GL11.glPushMatrix();//glstart
            	String tu1 = String.valueOf(entity.time);
            	entity.model.renderPart("mat_" + tu1);
            	float size = partialTicks * 0.4F + 1;
            	GlStateManager.scale(size, size, size);
            	entity.model.renderPart("mat1");
    			entity.model.renderPart("bullet");
				GL11.glPopMatrix();//glend
				SARenderHelper.disableBlendMode(RenderType.ALPHA);//ALPHA
            }

			SARenderHelper.enableBlendMode(RenderType.ADDITIVE);//ALPHA
			{
				GlStateManager.pushMatrix();
				if(entity.time<entity.timemax){
				float size = (float)(entity.timemax-entity.time) / (float)entity.timemax;
				GlStateManager.scale(size, size, size);
				GlStateManager.rotate(entity.time * 10, 0.0F, 0.0F, 1.0F);
				entity.model.renderPart("beam");
				}
				GlStateManager.popMatrix();
			}
			
			{
				GL11.glPushMatrix();//glstart
            	float size2 = 1;
				if(entity.time > 0 && entity.time < 10){
					if(entity.time>0 && entity.time<4){
						size2 = entity.time * 0.5F + 1;
					}
					if(entity.time>3 && entity.time<8){//10 11 12 ... 19 <---
						size2 = (8 - entity.time) * 0.3F + 1;
					}
					GlStateManager.scale(size2*0.8F, size2*0.8F, size2*1.2F);
					entity.model.renderPart("mat2");
				}
				GL11.glPopMatrix();//glend
			}
        }
		SARenderHelper.disableBlendMode(RenderType.ADDITIVE);//ALPHA
        GlStateManager.translate((float)-x, (float)-y, (float)-z);
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	protected static int lastBlendFuncSrc=0;
	protected static int lastBlendFuncDest=0;
	
	protected static float lastBrightnessX=0;
	protected static float lastBrightnessY=0;
	
    public static void enableBlendMode() {
		//RenderType.ADDITIVE
		lastBlendFuncSrc = GlStateManager.glGetInteger(GL11.GL_BLEND_SRC);
		lastBlendFuncDest = GlStateManager.glGetInteger(GL11.GL_BLEND_DST);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		//光照START
		lastBrightnessX= OpenGlHelper.lastBrightnessX;
		lastBrightnessY= OpenGlHelper.lastBrightnessY;
		GlStateManager.disableLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
	}

	public static void disableBlendMode() {
		//光照END
    	GlStateManager.enableLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
		//RenderType.ADDITIVE
		GlStateManager.blendFunc(lastBlendFuncSrc, lastBlendFuncDest);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return boatTextures0;
	}
}