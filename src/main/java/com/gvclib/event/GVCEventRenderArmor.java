package gvclib.event;

import gvclib.item.ItemArmor_NewObj;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GVCEventRenderArmor
{
  public GVCEventRenderArmor() {}
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
	  public void renderLivingPost(RenderPlayerEvent.Post event)
	  {
		ItemStack itemstack = event.getEntityPlayer().getHeldItemMainhand();
		RenderPlayer renderplayer = event.getRenderer();
		if(itemstack != null && itemstack.getItem() instanceof ItemBow){
			{
		//	renderplayer.modelArmor.aimedBow = renderplayer.modelArmorChestplate.aimedBow = renderplayer.getMainModel().aimedBow = true;
			}
		}
		if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemBow){
			renderplayer.getMainModel().rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
			renderplayer.getMainModel().leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
		}
	  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
	  public void renderLiving(RenderPlayerEvent.Pre event)
	  {
		ItemStack itemstack = event.getEntityPlayer().getHeldItemMainhand();
		RenderPlayer renderplayer = event.getRenderer();
		if(itemstack != null && itemstack.getItem() instanceof ItemBow){
			{
		//	renderplayer.modelArmor.aimedBow = renderplayer.modelArmorChestplate.aimedBow = renderplayer.getMainModel().aimedBow = true;
			}
		}
		if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemBow){
			renderplayer.getMainModel().rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
			renderplayer.getMainModel().leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
		}
		
		renderplayer.getMainModel().bipedHead.isHidden = false;
		renderplayer.getMainModel().bipedHeadwear.isHidden = false;
		renderplayer.getMainModel().bipedBody.isHidden = false;
		renderplayer.getMainModel().bipedRightArm.isHidden = false;
		renderplayer.getMainModel().bipedLeftArm.isHidden = false;
		renderplayer.getMainModel().bipedRightLeg.isHidden = false;
		renderplayer.getMainModel().bipedLeftLeg.isHidden = false;
		renderplayer.getMainModel().bipedRightArm.offsetY = 0;
		
		if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null)
				&& (event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
			ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
			if(!armor.render_head){
				renderplayer.getMainModel().bipedHead.isHidden = true;
				renderplayer.getMainModel().bipedHeadwear.isHidden = true;
			}
			if(!armor.render_body){
				renderplayer.getMainModel().bipedBody.isHidden = true;
			}
			if(!armor.render_rarm){
				//renderplayer.getMainModel().bipedRightArm.isHidden = true;
				renderplayer.getMainModel().bipedRightArm.offsetY = -1000;
			}
			if(!armor.render_larm){
				renderplayer.getMainModel().bipedLeftArm.isHidden = true;
			}
			if(!armor.render_rleg){
				renderplayer.getMainModel().bipedRightLeg.isHidden = true;
			}
			if(!armor.render_lleg){
				renderplayer.getMainModel().bipedLeftLeg.isHidden = true;
			}
		}
		if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null)
				&& (event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
			ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
			if(!armor.render_head){
				renderplayer.getMainModel().bipedHead.isHidden = true;
				renderplayer.getMainModel().bipedHeadwear.isHidden = true;
			}
			if(!armor.render_body){
				renderplayer.getMainModel().bipedBody.isHidden = true;
			}
			if(!armor.render_rarm){
				//renderplayer.getMainModel().bipedRightArm.showModel = false;
				renderplayer.getMainModel().bipedRightArm.offsetY = -1000;
			}
			if(!armor.render_larm){
				renderplayer.getMainModel().bipedLeftArm.isHidden = true;
			}
			if(!armor.render_rleg){
				renderplayer.getMainModel().bipedRightLeg.isHidden = true;
			}
			if(!armor.render_lleg){
				renderplayer.getMainModel().bipedLeftLeg.isHidden = true;
			}
		}
		if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null)
				&& (event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
			ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
			if(!armor.render_head){
				renderplayer.getMainModel().bipedHead.isHidden = true;
				renderplayer.getMainModel().bipedHeadwear.isHidden = true;
			}
			if(!armor.render_body){
				renderplayer.getMainModel().bipedBody.isHidden = true;
			}
			if(!armor.render_rarm){
				//renderplayer.getMainModel().bipedRightArm.isHidden = true;
				renderplayer.getMainModel().bipedRightArm.offsetY = -1000;
			}
			if(!armor.render_larm){
				renderplayer.getMainModel().bipedLeftArm.isHidden = true;
			}
			if(!armor.render_rleg){
				renderplayer.getMainModel().bipedRightLeg.isHidden = true;
			}
			if(!armor.render_lleg){
				renderplayer.getMainModel().bipedLeftLeg.isHidden = true;
			}
		}
		if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.FEET) != null)
				&& (event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
			ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
			if(!armor.render_head){
				renderplayer.getMainModel().bipedHead.isHidden = true;
				renderplayer.getMainModel().bipedHeadwear.isHidden = true;
			}
			if(!armor.render_body){
				renderplayer.getMainModel().bipedBody.isHidden = true;
			}
			if(!armor.render_rarm){
				//renderplayer.getMainModel().bipedRightArm.isHidden = true;
				renderplayer.getMainModel().bipedRightArm.offsetY = -1000;
			}
			if(!armor.render_larm){
				renderplayer.getMainModel().bipedLeftArm.isHidden = true;
			}
			if(!armor.render_rleg){
				renderplayer.getMainModel().bipedRightLeg.isHidden = true;
			}
			if(!armor.render_lleg){
				renderplayer.getMainModel().bipedLeftLeg.isHidden = true;
			}
		}
		
		
		
	  }
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
	  public void renderLivingMob(RenderLivingEvent.Pre event)
	  {
	    EntityLivingBase entity = event.getEntity();
		ItemStack itemstack = event.getEntity().getHeldItemMainhand();
		RenderLivingBase render = event.getRenderer();
		if(render instanceof RenderBiped){
			RenderBiped renderplayer = (RenderBiped) render;
			ModelBiped model = (ModelBiped) renderplayer.getMainModel();
			{
				model.bipedHead.isHidden = false;
				model.bipedHeadwear.isHidden = false;
				model.bipedBody.isHidden = false;
				model.bipedRightArm.isHidden = false;
				model.bipedLeftArm.isHidden = false;
				model.bipedRightLeg.isHidden = false;
				model.bipedLeftLeg.isHidden = false;
				{
					model.bipedRightArm.offsetY = 0;
				}
				
				if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null)
						&& (event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
					ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
					if(!armor.render_head){
						model.bipedHead.isHidden = true;
						model.bipedHeadwear.isHidden = true;
					}
					if(!armor.render_body){
						model.bipedBody.isHidden = true;
					}
					if(!armor.render_rarm){
						//model.bipedRightArm.isHidden = true;
						if(Minecraft.getMinecraft().gameSettings.thirdPersonView != 0){
							model.bipedRightArm.offsetY = -1000;
						}else{
							model.bipedRightArm.offsetY = 0;
						}
					}
					if(!armor.render_larm){
						model.bipedLeftArm.isHidden = true;
					}
					if(!armor.render_rleg){
						model.bipedRightLeg.isHidden = true;
					}
					if(!armor.render_lleg){
						model.bipedLeftLeg.isHidden = true;
					}
				}
				if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null)
						&& (event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
					ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
					if(!armor.render_head){
						model.bipedHead.isHidden = true;
						model.bipedHeadwear.isHidden = true;
					}
					if(!armor.render_body){
						model.bipedBody.isHidden = true;
					}
					if(!armor.render_rarm){
						//model.bipedRightArm.showModel = false;
						if(Minecraft.getMinecraft().gameSettings.thirdPersonView != 0){
							model.bipedRightArm.offsetY = -1000;
						}else{
							model.bipedRightArm.offsetY = 0;
						}
					}
					if(!armor.render_larm){
						model.bipedLeftArm.isHidden = true;
					}
					if(!armor.render_rleg){
						model.bipedRightLeg.isHidden = true;
					}
					if(!armor.render_lleg){
						model.bipedLeftLeg.isHidden = true;
					}
				}
				if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null)
						&& (event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
					ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
					if(!armor.render_head){
						model.bipedHead.isHidden = true;
						model.bipedHeadwear.isHidden = true;
					}
					if(!armor.render_body){
						model.bipedBody.isHidden = true;
					}
					if(!armor.render_rarm){
						//model.bipedRightArm.isHidden = true;
						if(Minecraft.getMinecraft().gameSettings.thirdPersonView != 0){
							model.bipedRightArm.offsetY = -1000;
						}else{
							model.bipedRightArm.offsetY = 0;
						}
					}
					if(!armor.render_larm){
						model.bipedLeftArm.isHidden = true;
					}
					if(!armor.render_rleg){
						model.bipedRightLeg.isHidden = true;
					}
					if(!armor.render_lleg){
						model.bipedLeftLeg.isHidden = true;
					}
				}
				if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET) != null)
						&& (event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
					ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
					if(!armor.render_head){
						model.bipedHead.isHidden = true;
						model.bipedHeadwear.isHidden = true;
					}
					if(!armor.render_body){
						model.bipedBody.isHidden = true;
					}
					if(!armor.render_rarm){
						//model.bipedRightArm.isHidden = true;
						if(Minecraft.getMinecraft().gameSettings.thirdPersonView != 0){
							model.bipedRightArm.offsetY = -1000;
						}else{
							model.bipedRightArm.offsetY = 0;
						}
					}
					if(!armor.render_larm){
						model.bipedLeftArm.isHidden = true;
					}
					if(!armor.render_rleg){
						model.bipedRightLeg.isHidden = true;
					}
					if(!armor.render_lleg){
						model.bipedLeftLeg.isHidden = true;
					}
				}
			}
		}
		
	  }
  
}