package gvclib.event.gun;

import gvclib.mod_GVCLib;
import gvclib.ClientProxyGVClib;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_SR;
import gvclib.item.ItemGun_AR;
import gvclib.item.ItemGun_Shield;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.Minecraft;

import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import com.google.common.base.Predicate;

import java.util.Random;
import net.minecraft.client.entity.EntityPlayerSP;

import gvclib.entity.living.EntityGVCLivingBase;

import gvclib.RenderParameters;
import static gvclib.RenderParameters.*;

public class GVCGunEvents {
	@SubscribeEvent
	public void LivingDigEvent(PlayerEvent.BreakSpeed event) {
		EntityLivingBase target = event.getEntityLiving();
		ItemStack itemstackl = target.getHeldItemOffhand();
		if(target instanceof EntityPlayer && target != null){
			if (target != null &&! itemstackl.isEmpty() 
					//&& itemstackl.getItem() instanceof ItemGun_Shield 
					&& itemstackl.getItem() instanceof ItemGunBase 
					&& itemstackl.getItemDamage() != itemstackl.getMaxDamage() && itemstackl.hasTagCompound()) {
				NBTTagCompound nbtgun = itemstackl.getTagCompound();
				boolean left = nbtgun.getBoolean("LeftClick");
				if(left || target.isSneaking())
				{
					event.setNewSpeed(0);
				}
			}
		}
	}

	@SubscribeEvent
    @SideOnly(Side.CLIENT)
	public void applyRecoil(EntityViewRenderEvent.CameraSetup event) {
		if(event.getEntity() != null && event.getEntity() instanceof EntityLivingBase){
			EntityLivingBase target = (EntityLivingBase)event.getEntity();
			ItemStack itemstack = target.getHeldItemMainhand();
			if(target instanceof EntityPlayer && target != null){
				EntityPlayer entityplayer =(EntityPlayer)target;
				if(target != null &&! itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase && itemstack.hasTagCompound()){//枪械开火抖动
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				NBTTagCompound nbt = itemstack.getTagCompound();
				float time = 6-gun.cooltime;
				double recoil1 = gun.recoil;
				double recoil2 = gun.recoil_ads;
				float shock_size = mod_GVCLib.gvclibsa_shock_size;
				
				float recoil = 0;
				recoil -= playerRecoilPitch;
				recoil += antiRecoilPitch * 0.25F;
				if(!itemstack.hasTagCompound())return;
					if(shock_size>0){//摄像机后座
						if(target.isSneaking()||entityplayer.getActiveItemStack() == itemstack){//玩家瞄准（蹲下）时
						//event.setPitch(event.getPitch() - time*0.25F*recoil2*shock_size);
							if (Math.random() > 0.5f) {
								event.setRoll(event.getRoll() - recoil*0.25F*shock_size);
							} else {
								event.setRoll(event.getRoll() + recoil*0.25F*shock_size);
							}
						}else{//玩家腰射时
						//event.setPitch(event.getPitch() - time*0.25F*recoil1*shock_size);
							if (Math.random() > 0.5f) {
								event.setRoll(event.getRoll() - recoil*0.4F*shock_size);
							} else {
								event.setRoll(event.getRoll() + recoil*0.4F*shock_size);
							}
						}
					}
				}
				if (target.getRidingEntity() instanceof EntityGVCLivingBase && target.getRidingEntity() != null) {//载具开火抖动
					EntityGVCLivingBase ve = (EntityGVCLivingBase) target.getRidingEntity();
					float rote = ve.throte;
					if(ve.throttle > ve.thmax*0.4F){//飞机旋转
						event.setRoll(event.getRoll() + rote);
						rote = Math.max((rote * 0.8f) - 0.06f, 0);
					}
					if(/*!ve.counter1 && */ve.getRemain_L() != 0){
						if(ve.cooltime > 0 && ve.cooltime < 5){//ve.ammo1
							event.setPitch(event.getPitch() + (ve.cooltime-5)* 0.25F);
						}
					}
					if(/*!ve.counter2 && */ve.getRemain_L() != 0){
						if(ve.cooltime2 > 0 && ve.cooltime2 < 5){//ve.ammo2
							event.setPitch(event.getPitch() + (ve.cooltime2-5)* 0.25F);
						}
					}
				}
			}
		}
	}
	
	/*@SubscribeEvent
	public void onGunMainEvent(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		ItemStack itemstack = target.getHeldItemMainhand();
		if(target instanceof EntityPlayer  && target != null){
			EntityPlayer entityplayer = (EntityPlayer)target;
			World world = target.world;
			if (target != null && !itemstack.isEmpty()&& itemstack.getItem() instanceof ItemGunBase && itemstack.hasTagCompound()) {
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				NBTTagCompound nbtgun = itemstack.getTagCompound();
				
				//nbtgun.setBoolean("LeftClick", false);
			//	boolean left = nbtgun.getBoolean("LeftClick");
			//	boolean right = nbtgun.getBoolean("RightClick");
				
				//1/19なんか鯖でエラー起きてる
				if (mod_GVCLib.proxy.leftclick()) 
				//if(Mouse.isButtonDown(0))
				{
					nbtgun.setBoolean("LeftClick", true);
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(102));
				}else{
					nbtgun.setBoolean("LeftClick", false);
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1021));
				}
				if (mod_GVCLib.proxy.rightclick()) {
					nbtgun.setBoolean("RightClick", true);
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(112));
				}else{
					nbtgun.setBoolean("RightClick", false);
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1121));
				}
			}
		}
	}*/
}
