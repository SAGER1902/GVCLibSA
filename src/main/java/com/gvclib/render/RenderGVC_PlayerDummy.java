
package gvclib.render; 

 
import gvclib.entity.living.EntityGVC_PlayerDummy;
import gvclib.event.gun.LayerGunBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
//import gvclib.entity.living.EntityGVCLivingBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation; 

 

public class RenderGVC_PlayerDummy extends RenderBiped<EntityGVC_PlayerDummy>//これを入れると防具がバグる
{ 
	
	private ResourceLocation skeletonTextures= new ResourceLocation("textures/entity/steve.png"); 

	private final float scale;
 
 	public RenderGVC_PlayerDummy(RenderManager p_i46193_1_) 
	{ 
 		 
 		super(p_i46193_1_, new ModelPlayer(0.0F, false),0.5F * 1); //0.5F
 		
 		this.addLayer(new LayerHeldItem(this));
 		this.addLayer(new LayerSoldierArmor(this));
 		this.addLayer(new LayerBipedArmor(this));
 		//skeletonTextures = new ResourceLocation(string);
 		this.scale = 1;
 		this.addLayer(new LayerGunBase(this));
	} 
 	public void doRender(EntityGVC_PlayerDummy entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	{
			//doRenderShadowAndFire(entity, x, y, z, entityYaw, partialTicks);
    		ResourceLocation tex = new ResourceLocation("textures/entity/steve.png");
    		if(entity != null && entity instanceof EntityGVC_PlayerDummy) {
    			EntityGVC_PlayerDummy vehicle = (EntityGVC_PlayerDummy) entity;
     			if(vehicle.getOwner() != null && vehicle.getOwner() instanceof EntityPlayer) {
     	 			EntityPlayer player = (EntityPlayer) vehicle.getOwner();
     	 			tex = ((AbstractClientPlayer)player).getLocationSkin();
     	 			if (tex == null)
     	 	        {
     	 				tex = new ResourceLocation("textures/entity/steve.png");
     	 	        }
     	 			//System.out.println("0");
     	 		}
     			//System.out.println("1");
    		}else {
    			//System.out.println("2");
    		}
    		Minecraft minecraft = Minecraft.getMinecraft();
    		 minecraft.getTextureManager().bindTexture(tex);
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
 	protected ResourceLocation getEntityTexture(EntityGVC_PlayerDummy par1EntityLiving) 
 	{ 
 		if(par1EntityLiving != null && par1EntityLiving instanceof EntityGVC_PlayerDummy) {
 			EntityGVC_PlayerDummy vehicle = (EntityGVC_PlayerDummy) par1EntityLiving;
 			if(vehicle.getOwner() != null && vehicle.getOwner() instanceof EntityPlayer) {
 	 			EntityPlayer player = (EntityPlayer) vehicle.getOwner();
 	 			ResourceLocation resourcelocation = ((AbstractClientPlayer)player).getLocationSkin();
 	 			if (resourcelocation == null)
 	 	        {
 	 	            resourcelocation = new ResourceLocation("textures/entity/steve.png");
 	 	        }

 	 			//System.out.println("0");
 	 			return resourcelocation; 
 	 		}else {
 	 			//System.out.println("1");
 	 			return this.skeletonTextures; 
 	 		}
 		}else 
 		{
 			//System.out.println("2");
	 			return this.skeletonTextures; 
	 	}
 	} 
 } 
