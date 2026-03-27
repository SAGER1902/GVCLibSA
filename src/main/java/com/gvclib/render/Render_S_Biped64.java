
package gvclib.render; 

 
import gvclib.event.gun.LayerGunBase;
//import gvclib.entity.living.EntityGVCLivingBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation; 

 

public class Render_S_Biped64 extends RenderBiped<EntityLiving>//これを入れると防具がバグる
{ 
	
	private ResourceLocation skeletonTextures;// = new ResourceLocation("gvcmob:textures/mob/guerrillamg.png"); 

	private final float scale;
 
 	public Render_S_Biped64(RenderManager p_i46193_1_, String string, float size) 
	{ 
 		 
 		super(p_i46193_1_, new ModelSoldier64(),0.5F * size); //0.5F
 		
 		//this.addLayer(new LayerHeldItem(this));
 		//this.addLayer(new LayerSoldierArmor(this));
 		this.addLayer(new LayerSoldierArmorBase(this));
 		//this.addLayer(new LayerSoldierArmor(this));
 		skeletonTextures = new ResourceLocation(string);
 		this.scale = size;
 		this.addLayer(new LayerGunBase(this));
	} 
 	public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	{
			//doRenderShadowAndFire(entity, x, y, z, entityYaw, partialTicks);
			super.doRender(entity, x, y, z, entityYaw, partialTicks);
		}
    }
 	 /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityLiving entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }
 	
 	@Override 
 	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving) 
 	{ 
 			return this.skeletonTextures; 
 		
 	} 
 } 
