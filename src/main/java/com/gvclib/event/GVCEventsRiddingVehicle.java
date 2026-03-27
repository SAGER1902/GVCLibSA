package gvclib.event;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import gvclib.entity.living.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GVCEventsRiddingVehicle {

	boolean key_true = false;
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	  public void onRender(RenderPlayerEvent.Pre event) {
	    if (event.getEntityPlayer() != (Minecraft.getMinecraft()).player) {
	      return;
	    }
	    if ((Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory) {
	    	mod_GVCLib.dontjumpRedner = true;
	    }
	  }
	  
	  @SubscribeEvent
	  public void onRedner(RenderLivingEvent.Pre event) {
	    Entity rider = event.getEntity().getRidingEntity();
	    if (mod_GVCLib.dontjumpRedner) {
	    	mod_GVCLib.dontjumpRedner = false;
	      return;
	    } 
	    if (rider == null) {
	      return;
	    }
	    if (!(rider instanceof gvclib.entity.living.EntityVehicleBase)) {
	      return;
	    }
	    if (rider instanceof gvclib.entity.living.EntityVehicleBase) {
	    	EntityVehicleBase vahicle = (EntityVehicleBase) rider;
	    	if(!vahicle.ridding_roteangle)
		      return;
		}
	    event.setCanceled(true);
	  }
	
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEventRidding(RenderGameOverlayEvent.Text event) {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		World world = FMLClientHandler.instance().getWorldClient();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		//OpenGlHelper.

		boolean cx = true;

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			Render_HUDVehicleEvent renderevent = new Render_HUDVehicleEvent(minecraft);
			if (entityplayer.getRidingEntity() instanceof EntityMobVehicle_Inv_Base
					&& entityplayer.getRidingEntity() != null) {// 1
				EntityMobVehicle_Inv_Base balaam = (EntityMobVehicle_Inv_Base) entityplayer.getRidingEntity();
				GuiIngame g = minecraft.ingameGUI;
				renderevent.render_fuel(minecraft, balaam, entityplayer, i, j);
				if(balaam.spg_mode){
					renderevent.render_spg(minecraft, balaam, entityplayer, i, j);
				}
			} // 1

		}

	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEventRidding2(RenderGameOverlayEvent.Text event) {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		World world = FMLClientHandler.instance().getWorldClient();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		//OpenGlHelper.

		boolean cx = true;

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			Render_HUDVehicleEvent renderevent = new Render_HUDVehicleEvent(minecraft);
			if (entityplayer.getRidingEntity() instanceof EntityVehicleBase
					&& entityplayer.getRidingEntity() != null) {// 1
				EntityVehicleBase balaam = (EntityVehicleBase) entityplayer.getRidingEntity();
				GuiIngame g = minecraft.ingameGUI;
				
				{
					boolean kc = mod_GVCLib.proxy.keyc();
					if (kc) {
						if(!key_true) {
							key_true = true;
							if(minecraft.gameSettings.thirdPersonView == 0) {
								minecraft.gameSettings.thirdPersonView = 1;
							}
							else if(minecraft.gameSettings.thirdPersonView == 1) {
								minecraft.gameSettings.thirdPersonView = 0;
							}
						}
					}else{
						key_true = false;
					}
				}
				
			} // 1

		}

	}
}
