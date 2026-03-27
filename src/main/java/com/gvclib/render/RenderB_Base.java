package gvclib.render;

import java.io.IOException;
import org.lwjgl.opengl.GL11;
import gvclib.mod_GVCLib;
import gvclib.entity.EntityBBase;
import gvclib.entity.EntityB_Shell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

import gvclib.render.SARenderHelper;
import gvclib.render.SARenderHelper.RenderType;
import net.minecraft.client.renderer.OpenGlHelper;
@SideOnly(Side.CLIENT)
public class RenderB_Base<T extends EntityBBase> extends Render<T>
{
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/entity/mflash.mqo"));
    private static final ResourceLocation boatTextures0 = new ResourceLocation("gvclib:textures/entity/mflash.png");
    public RenderB_Base(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    //public  int moi = 0;
	//public  String[] modelt = new String[64];
	//public  IModelCustom[] model = new IModelCustom[64];
    /**
     * Renders the desired {@code T} type Entity.
     */
    //@Override
    public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ)
    {
    	return true;
    }
    
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	
    	Minecraft minecraft = Minecraft.getMinecraft();
    	
    	/*
    	if(minecraft.getDebugFPS() < 15)return;
    	{
    		double xx = entity.motionX;
    		double yy = entity.motionY;
    		double zz = entity.motionZ;
    		double xy = Math.sqrt(xx * xx + yy * yy);
    		double xyz = Math.sqrt(xy * xy + zz * zz);
    		if(xyz > 5)return;
    	}
    	*/
    	
    	
        //this.bindEntityTexture(new ResourceLocation(entity.getModel()));
        GlStateManager.pushMatrix();
        //GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);
        
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
        
        if(entity.model != null && entity.time > 1){
            minecraft.getTextureManager().bindTexture(new ResourceLocation(entity.getTex()));
        	//minecraft.getTextureManager().bindTexture(entity.entity_tex);
            {
            	 GlStateManager.pushMatrix();
                 entity.model.renderPart("bullet_no_rote");
            	 GlStateManager.popMatrix();
            }
            {
            	 GlStateManager.pushMatrix();
            	 if(!entity.onGround) {
            		GlStateManager.rotate(entity.time * 10, 0.0F, 0.0F, 1.0F);
					if(!entity.inGround)entity.model.renderPart("bullet");
            	 }
				entity.model.renderPart("bullet_stick");
				entity.model.renderPart("mat1");
            	 GlStateManager.popMatrix();
            }
            {
           	 GlStateManager.pushMatrix();
           	 if(!entity.onGround) {
           		 GlStateManager.rotate(entity.time * 10, 1.0F, 0.0F, 0.0F);
           	 }
                entity.model.renderPart("bullet_x");
           	 GlStateManager.popMatrix();
           }
            {
              	 GlStateManager.pushMatrix();
              	 if(!entity.onGround) {
              		 GlStateManager.rotate(entity.time * 10, 0.0F, 1.0F, 0.0F);
              	 }
                   entity.model.renderPart("bullet_y");
              	 GlStateManager.popMatrix();
              }
            
            {
            	GlStateManager.disableLighting();
            	float size = partialTicks * 0.8F + 1;//0.4F
            	 if(!entity.onGround) {
            		GlStateManager.rotate(entity.time * 10, 0.0F, 0.0F, 1.0F);
					GlStateManager.scale(size, size, size);
					SARenderHelper.enableBlendMode(RenderType.ADDITIVE);//ALPHA
					if(!entity.inGround)entity.model.renderPart("bullet_e_1");
					SARenderHelper.disableBlendMode(RenderType.ADDITIVE);//ADDITIVE
            	 }

            	 GlStateManager.enableLighting();
            }
			
			{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDepthMask(false);
            	GlStateManager.disableLighting();
            	float size1 = partialTicks * 0.2F + 1;
            	GlStateManager.scale(size1, size1, size1);
				this.enableBlendMode();
				entity.model.renderPart("bullet_blade");
				this.disableBlendMode();
				GL11.glColor4f(1F, 1F, 1F, 0.6F);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_LIGHT1);
            	GlStateManager.enableLighting();
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
			}
			
        }
        GlStateManager.translate((float)-x, (float)-y, (float)-z);
        GlStateManager.popMatrix();
        
        //*/
        //if(entity instanceof EntityB_Shell)
        {
        	this.bindTexture(boatTextures0);
        }
        
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
    
    public static void renderCrystalBeams(double p_188325_0_, double p_188325_2_, double p_188325_4_, float p_188325_6_, 
    		double p_188325_7_, double p_188325_9_, double p_188325_11_, int p_188325_13_, double p_188325_14_, double p_188325_16_, double p_188325_18_)
    {
        float f = (float)(p_188325_14_ - p_188325_7_);
        float f1 = (float)(p_188325_16_ - 1.0D - p_188325_9_);
        float f2 = (float)(p_188325_18_ - p_188325_11_);
        float f3 = MathHelper.sqrt(f * f + f2 * f2);
        float f4 = MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)p_188325_0_, (float)p_188325_2_, (float)p_188325_4_);
        GlStateManager.rotate((float)(-Math.atan2((double)f2, (double)f)) * (180F / (float)Math.PI) - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(-Math.atan2((double)f3, (double)f1)) * (180F / (float)Math.PI) - 90.0F, 1.0F, 0.0F, 0.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        //RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.shadeModel(7425);
        float f5 = 0.0F - ((float)p_188325_13_ + p_188325_6_) * 0.01F;
        float f6 = MathHelper.sqrt(f * f + f1 * f1 + f2 * f2) / 32.0F - ((float)p_188325_13_ + p_188325_6_) * 0.01F;
        bufferbuilder.begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);
        int i = 8;

        for (int j = 0; j <= 8; ++j)
        {
            float f7 = MathHelper.sin((float)(j % 8) * ((float)Math.PI * 2F) / 8.0F) * 0.25F;
            float f8 = MathHelper.cos((float)(j % 8) * ((float)Math.PI * 2F) / 8.0F) * 0.25F;
            float f9 = (float)(j % 8) / 8.0F;
            bufferbuilder.pos((double)(f7 * 0.2F), (double)(f8 * 0.2F), 0.0D).tex((double)f9, (double)f5).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos((double)f7, (double)f8, (double)f4).tex((double)f9, (double)f6).color(255, 255, 255, 255).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableCull();
        GlStateManager.shadeModel(7424);
        //RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return boatTextures0;
	}
}