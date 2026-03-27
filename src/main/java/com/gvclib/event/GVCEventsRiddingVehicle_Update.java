package gvclib.event;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.EntityEvent;

import techguns.client.ClientProxy;
import techguns.packets.PacketSpawnParticle;
import techguns.damagesystem.TGExplosion;
import techguns.Techguns;

import jp.mc.ancientred.jointblock.entity.EntityBullet;
import noppes.npcs.entity.EntityProjectile;

import gvclib.ClientProxyGVClib;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.Loader;
public class GVCEventsRiddingVehicle_Update {
	@SubscribeEvent
	public void onRiddingMountEvent(LivingUpdateEvent event) {
		Entity target = event.getEntityLiving();
		//Entity ride = event.getEntityBeingMounted();
		if (target != null && target instanceof EntityPlayer) 
		{
			EntityPlayer player = (EntityPlayer) target;
			if (player.getRidingEntity() instanceof EntityVehicleBase && player.getRidingEntity() != null) {// 1
				EntityVehicleBase vehicle = (EntityVehicleBase) player.getRidingEntity();
				if (vehicle.night_vision) 
				{
					if (vehicle.getBoosttime() == 1) 
					{
						//if (!player.isPotionActive(MobEffects.NIGHT_VISION))
							player.addPotionEffect(
									new PotionEffect(MobEffects.NIGHT_VISION, 401, 1, false, false));
					}else {
						if (player.isPotionActive(MobEffects.NIGHT_VISION) && player.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration() < 400) {
							player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
						}
					}
				}
			}
		}
	}
	
	public boolean usetgfx = mod_GVCLib.gvclibsa_tgfx;
	//@Optional.Method(modid = "customnpcs")
	@SideOnly(Side.CLIENT)//客户端
	@SubscribeEvent
	public void CustomNpcTGFXBullet(EntityJoinWorldEvent event) {
		if(Loader.isModLoaded("customnpcs")) {
			Entity target = event.getEntity();
			if (target != null && target instanceof EntityProjectile) 
			{//机器方块子弹宽度数值读取科技枪特效
				if(usetgfx){
				EntityProjectile bullet = (EntityProjectile)target;
				String fxname = null;

				if(bullet.effect>0){
					fxname = "FlamethrowerTrail";
				}else if(bullet.explosiveRadius>=2){	
					//fxname = "SAAPTrail";
				}else if(bullet.damage>=2){
					fxname = "SABulletTrail";
				}else{
					fxname = null;
				}

				if(fxname!=null)ClientProxy.get().createFXOnEntity(fxname, target);//客户端
				//if(fxname!=null)Techguns.proxy.createFXOnEntity("dushe", target, 1f);//服务端
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)//客户端
	@SubscribeEvent
	public void JointBlockTGFXBullet(EntityJoinWorldEvent event) {
		if(Loader.isModLoaded("jointblock")) {
			Entity target = event.getEntity();
			if (target != null && target instanceof EntityBullet) 
			{//机器方块子弹宽度数值读取科技枪特效
				if(usetgfx){
				EntityBullet bullet = (EntityBullet)target;
				String fxname = null;
				if(bullet.lazerWidth==43){
					fxname = "dushe";
				}else if(bullet.lazerWidth==44){
					fxname = "leishe";
				}else if(bullet.lazerWidth==45){
					fxname = "kuangtu";
				}else if(bullet.lazerWidth==46){
					fxname = "juxiang";
				}else if(bullet.lazerWidth==47){
					fxname = "fengxinzi";
				}else if(bullet.lazerWidth==48){	
					fxname = "rockettrail";
				}
				else if(bullet.lazerWidth==49){
					fxname = "laserminiEnergyFuhe2";}
				else if(bullet.lazerWidth==50){
					fxname = "leiqiu1";}
				else if(bullet.lazerWidth==51){
					fxname = "xiashiFuheTrail";}
				
				if(fxname!=null)ClientProxy.get().createFXOnEntity(fxname, target);//客户端
				//if(fxname!=null)Techguns.proxy.createFXOnEntity("dushe", target, 1f);//服务端
				}
			}
		}
	}
}