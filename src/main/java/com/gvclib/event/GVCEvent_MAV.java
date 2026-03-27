package gvclib.event;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityMAVBase;
import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GVCEvent_MAV {
	
	@SubscribeEvent
	public void onRiddingMountEvent(LivingUpdateEvent event) {
		Entity target = event.getEntityLiving();
		//Entity ride = event.getEntityBeingMounted();
		if (target != null && target instanceof EntityPlayer) 
		{
			EntityPlayer player = (EntityPlayer) target;
			if (player.getRidingEntity() instanceof EntityMAVBase && player.getRidingEntity() != null) {// 1
				EntityMAVBase vehicle = (EntityMAVBase) player.getRidingEntity();
				{
					boolean kz = mod_GVCLib.proxy.keyz();
					if (kz) {
						vehicle.serverz = true;
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(17, vehicle.getEntityId()));
						if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
							for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(17, vehicle.getEntityId()), playermp);
							}
						}
						
					}
					if(vehicle.serverz) {
						player.setPositionAndUpdate(vehicle.getMoveX(), vehicle.getMoveY(), vehicle.getMoveZ());
						if (player.isRiding())
				        {
							
							player.dismountRidingEntity();
							if(!vehicle.world.isRemote && vehicle.getThrower() != null) {
								vehicle.getThrower().setDead();
							}
				        }
						player.setPositionAndUpdate(vehicle.getMoveX(), vehicle.getMoveY(), vehicle.getMoveZ());
						if(vehicle.return_basepoint) {
							vehicle.setPositionAndUpdate(vehicle.getMoveX(), vehicle.getMoveY(), vehicle.getMoveZ());
						}
						vehicle.serverz = false;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRiddingDisMountEvent(EntityMountEvent event) {
		Entity target = event.getEntityMounting();
		Entity ride = event.getEntityBeingMounted();
		if (target != null && target instanceof EntityPlayer) 
		{
			EntityPlayer player = (EntityPlayer) target;
			if(ride != null && ride instanceof EntityMAVBase) {
				EntityMAVBase vehicle = (EntityMAVBase)ride;
				{
					if(player.isSneaking()) {
						event.setCanceled(true);
						/*player.setPositionAndUpdate(vehicle.getMoveX(), vehicle.getMoveY(), vehicle.getMoveZ());
						if(!vehicle.world.isRemote && vehicle.getThrower() != null) {
							vehicle.getThrower().setDead();
						}*/
					}
				}
			}
		}
	}
	
	/*@SubscribeEvent
	public void onRiddingDisMountEvent(EntityMountEvent event) {
		Entity target = event.getEntityMounting();
		Entity ride = event.getEntityBeingMounted();
		if (target != null && target instanceof EntityPlayer) 
		{
			EntityPlayer player = (EntityPlayer) target;
			if(ride != null && ride instanceof EntityMAV) {
				EntityMAV vehicle = (EntityMAV)ride;
				{
					boolean kz = mod_GVCLib.proxy.keyz();
					if (kz) {
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(17, vehicle.getEntityId()));
					}
					if(vehicle.serverz) {
						player.setPositionAndUpdate(vehicle.getMoveX(), vehicle.getMoveY(), vehicle.getMoveZ());
						if(!vehicle.world.isRemote && vehicle.getThrower() != null) {
							vehicle.getThrower().setDead();
						}
						vehicle.serverz = false;
					}
					///if(player.isSneaking()) {
						player.setPositionAndUpdate(vehicle.getMoveX(), vehicle.getMoveY(), vehicle.getMoveZ());
						if(!vehicle.world.isRemote && vehicle.getThrower() != null) {
							vehicle.getThrower().setDead();
						}
					}///
				}
			}
		}
	}*/
	
	
	@SubscribeEvent
	public void onHurtEvent(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		if (target instanceof EntityPlayer && target != null) 
		{
			if (target.getRidingEntity() != null && target.getRidingEntity() instanceof EntityMAVBase) {
				event.setAmount(0);
	    		//amount = 0.0F;
	            event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	  public void onLivingFromDollEvent(LivingAttackEvent event)
	  {
		EntityLivingBase target = event.getEntityLiving();
		if (target instanceof EntityPlayer && target != null) 
		{
			if (target.getRidingEntity() != null && target.getRidingEntity() instanceof EntityMAVBase) {
	            event.setCanceled(true);
			}
		}
	  }
	
}
