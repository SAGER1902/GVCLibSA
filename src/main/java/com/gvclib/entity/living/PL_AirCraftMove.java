package gvclib.entity.living;

import gvclib.mod_GVCLib;
import gvclib.entity.living.cnt.ai.VehicleAI_RotationYawOffset;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PL_AirCraftMove {
	public static void moveheli(EntityLivingBase player, EntityVehicleBase entity, float sp, float turnspeed){
		if(player.rotationYawHead > 360F || player.rotationYawHead < -360F){
			player.rotationYawHead = 0;
			player.rotationYaw = 0;
			player.prevRotationYaw = 0;
			player.prevRotationYawHead = 0;
			player.renderYawOffset = 0;
		}
		if(player.rotationYawHead > 180F){
			player.rotationYawHead = -179F;
			player.rotationYaw = -179F;
			player.prevRotationYaw = -179F;
			player.prevRotationYawHead = -179F;
			player.renderYawOffset = -179F;
		}
		if(player.rotationYawHead < -180F){
			player.rotationYawHead = 179F;
			player.rotationYaw = 179F;
			player.prevRotationYaw = 179F;
			player.prevRotationYawHead = 179F;
			player.renderYawOffset = 179F;
		}
		float f = Math.abs(entity.rotationYawHead - player.rotationYawHead);
		float f2 = entity.rotationYawHead - player.rotationYawHead;
		entity.rotation = player.prevRotationYawHead;
		entity.rotationp = player.prevRotationPitch;
		if(entity.onGround)
		{
			if(entity.thpower > 1){
			if(f <= turnspeed && f >= -turnspeed){
				entity.rotationYawHead = player.rotationYaw;
				entity.rotationYaw = player.rotationYawHead;
				entity.prevRotationYaw = player.prevRotationYawHead;
				entity.prevRotationYawHead = player.prevRotationYawHead;
				entity.renderYawOffset = player.prevRotationYawHead;
			}
			else if(f2 > 0.1F){
				if(f2 > 180F){
					PL_RoteModel.rotemodel(entity, + turnspeed/5);
				}else{
					PL_RoteModel.rotemodel(entity,- turnspeed/5);
				}
			}
			else if(f2 < -0.1F){
				if(f2 < -180F){
					PL_RoteModel.rotemodel(entity,- turnspeed/5);
				}else{
					PL_RoteModel.rotemodel(entity,+ turnspeed/5);
				}
			}
			entity.rotationPitch = player.rotationPitch;
			entity.prevRotationPitch = player.prevRotationPitch;
			}
		}else{
			if(f <= turnspeed*2 && f >= -turnspeed*2){
				entity.rotationYawHead = player.rotationYaw;
				entity.rotationYaw = player.rotationYawHead;
				entity.prevRotationYaw = player.prevRotationYawHead;
				entity.prevRotationYawHead = player.prevRotationYawHead;
				entity.renderYawOffset = player.prevRotationYawHead;
			}else
			if(f2 > 0.1F){
				if(f2 > 180F){
					PL_RoteModel.rotemodel(entity,+ turnspeed);
					if(entity.throte < 50){
						entity.throte = entity.throte + 2;
					}
				}else{
					PL_RoteModel.rotemodel(entity,- turnspeed);
					if(entity.throte > -50){
						entity.throte = entity.throte - 2;
					}
				}
			}
			else if(f2 < -0.1F){
				if(f2 < -180F){
					PL_RoteModel.rotemodel(entity,- turnspeed);
					if(entity.throte > -50){
						entity.throte = entity.throte - 2;
					}
				}else{
					PL_RoteModel.rotemodel(entity,+ turnspeed);
					if(entity.throte < 50){
						entity.throte = entity.throte + 2;
					}
				}
			}
			//entity.rotationPitch = player.rotationPitch;
			//entity.prevRotationPitch = player.prevRotationPitch;
			if(player.rotationPitch > entity.angle_max) {
				entity.rotationPitch = entity.angle_max;
				entity.prevRotationPitch = entity.angle_max;
			}else if(player.rotationPitch < entity.angle_min) {
				entity.rotationPitch = entity.angle_min;
				entity.prevRotationPitch = entity.angle_min;
			}else {
				entity.rotationPitch = player.rotationPitch;
				entity.prevRotationPitch = player.prevRotationPitch;
			}
		}
		

		if (player.moveForward > 0.0F) {
			if(entity.throttle < entity.thmax){
				++entity.throttle;
			}
		}
		if (player.moveForward < 0.0F) {
			if(entity.throttle >= 1){
				--entity.throttle;
			}
		}
		if( entity.throttle >= 0){
			if(entity.thpower < entity.throttle){
				if( entity.throttle > entity.thmax-5){
					entity.thpower = entity.thpower + entity.thmaxa;
				}else{
					entity.thpower = entity.thpower + entity.thmaxa*0.5;
				}
			}else{
				entity.thpower = entity.thpower + entity.thmina;
			}
			if(entity.throttle <= 0 && entity.throttle > 0){
				entity.thpower = entity.thpower + entity.thmina * 2;
			}
		}
		if (player.moveStrafing < 0.0F && entity.thpower >= 1) {
			entity.moveangle = 3;
		}
		if (player.moveStrafing > 0.0F && entity.thpower >= 1) {
			entity.moveangle = 4;
		}
		
		
		
	}
	
	public static void moveheligunner(EntityLivingBase player, EntityGVCLivingBase entity, float sp, float turnspeed){
		if(player.rotationYawHead > 360F || player.rotationYawHead < -360F){
			player.rotationYawHead = 0;
			player.rotationYaw = 0;
			player.prevRotationYaw = 0;
			player.prevRotationYawHead = 0;
			player.renderYawOffset = 0;
		}
		if(player.rotationYawHead > 180F){
			player.rotationYawHead = -179F;
			player.rotationYaw = -179F;
			player.prevRotationYaw = -179F;
			player.prevRotationYawHead = -179F;
			player.renderYawOffset = -179F;
		}
		if(player.rotationYawHead < -180F){
			player.rotationYawHead = 179F;
			player.rotationYaw = 179F;
			player.prevRotationYaw = 179F;
			player.prevRotationYawHead = 179F;
			player.renderYawOffset = 179F;
		}
		float f = Math.abs(entity.rotationYawHead - player.rotationYawHead);
		float f2 = entity.rotationYawHead - player.rotationYawHead;
		entity.rotation = player.prevRotationYawHead;
		entity.rotationp = player.prevRotationPitch;
		Vec3d look = entity.getLookVec();
		if (player.moveForward > 0.0F) {
			entity.moveangle = 1;
		}
		if (player.moveForward < 0.0F) {
			entity.moveangle = 2;
		}
		
		if (player.moveStrafing < 0.0F) {
			entity.rotationYawHead = entity.rotationYawHead + turnspeed;
			entity.rotationYaw = entity.rotationYaw + turnspeed;
			entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
			entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
		}
		if (player.moveStrafing > 0.0F) {
			entity.rotationYawHead = entity.rotationYawHead - turnspeed;
			entity.rotationYaw = entity.rotationYaw - turnspeed;
			entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
			entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
		}
//		entity.rotationPitch = 0;
	}
	
	public static void movefighter(EntityLivingBase player, EntityGVCLivingBase entity, float sp, float turnspeed){
		if(player.rotationYawHead > 360F || player.rotationYawHead < -360F){
			player.rotationYawHead = 0;
			player.rotationYaw = 0;
			player.prevRotationYaw = 0;
			player.prevRotationYawHead = 0;
			player.renderYawOffset = 0;
		}
		if(player.rotationYawHead > 180F){
			player.rotationYawHead = -179F;
			player.rotationYaw = -179F;
			player.prevRotationYaw = -179F;
			player.prevRotationYawHead = -179F;
			player.renderYawOffset = -179F;
		}
		if(player.rotationYawHead < -180F){
			player.rotationYawHead = 179F;
			player.rotationYaw = 179F;
			player.prevRotationYaw = 179F;
			player.prevRotationYawHead = 179F;
			player.renderYawOffset = 179F;
		}
		float f = Math.abs(entity.prevRotationYawHead - player.rotationYawHead);
		float f2 = entity.rotationYawHead - player.rotationYawHead;
		if(entity.getHealth() > 0.0F) {
			if(entity.onGround)
			{
				if(entity.thpower > 1){
				if(f <= turnspeed && f >= -turnspeed){
					entity.rotation = player.prevRotationYawHead;
//					entity.rotationp = player.prevRotationPitch;
					entity.rotationYawHead = player.rotationYaw;
					entity.rotationYaw = player.rotationYawHead;
					entity.prevRotationYaw = player.prevRotationYawHead;
					entity.prevRotationYawHead = player.prevRotationYawHead;
					entity.renderYawOffset = player.prevRotationYawHead;
				}
				else if(f2 > 0.1F){
					if(f2 > 180F){
						PL_RoteModel.rotemodel(entity,+ turnspeed/5);
					}else{
						PL_RoteModel.rotemodel(entity,- turnspeed/5);
					}
				}
				else if(f2 < -0.1F){
					if(f2 < -180F){
						PL_RoteModel.rotemodel(entity,- turnspeed/5);
					}else{
						PL_RoteModel.rotemodel(entity,+ turnspeed/5);
					}
				}
				//entity.rotationPitch = player.rotationPitch;
				//entity.prevRotationPitch = player.prevRotationPitch;
				}
			}else{
				if(f2 > turnspeed){
					if(f2 > 180F){
						PL_RoteModel.rotemodel(entity,+ turnspeed);
						if(entity.throte < 50){
							entity.throte = entity.throte + 2;
						}
					}else{
						PL_RoteModel.rotemodel(entity,- turnspeed);
						if(entity.throte > -50){
							entity.throte = entity.throte - 2;
						}
					}
				}
				else if(f2 < -turnspeed){
					if(f2 < -180F){
						PL_RoteModel.rotemodel(entity,- turnspeed);
						if(entity.throte > -50){
							entity.throte = entity.throte - 2;
						}
					}else{
						PL_RoteModel.rotemodel(entity,+ turnspeed);
						if(entity.throte < 50){
							entity.throte = entity.throte + 2;
						}
					}
				}else {
					entity.rotation = player.prevRotationYawHead;
//					entity.rotationp = player.prevRotationPitch;
					entity.rotationYawHead = player.rotationYaw;
					entity.rotationYaw = player.rotationYawHead;
					entity.prevRotationYaw = player.prevRotationYawHead;
					entity.prevRotationYawHead = player.prevRotationYawHead;
					entity.renderYawOffset = player.prevRotationYawHead;
				}
				//entity.rotationPitch = player.rotationPitch;
				//entity.prevRotationPitch = player.prevRotationPitch;
			}
			if(!entity.onGround) {
				entity.rotep = player.prevRotationPitch;
				if ((entity.rotationPitch > (entity.rotep + turnspeed)) || (entity.rotationPitch < entity.rotep - turnspeed)) {
					if (entity.rotationPitch < entity.rotep) {
						entity.rotationp = entity.rotationp + turnspeed*1;
						entity.rotationPitch = entity.rotationPitch + turnspeed*1;
						entity.prevRotationPitch = entity.prevRotationPitch + turnspeed*1;
					} else if (entity.rotationPitch > entity.rotep) {
						entity.rotationp = entity.rotationp - turnspeed*1;
						entity.rotationPitch = entity.rotationPitch - turnspeed*1;
						entity.prevRotationPitch = entity.prevRotationPitch - turnspeed*1;
					}
				}
				if(entity.world.isRemote)GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(421, entity.getEntityId(), entity.rotationYaw, entity.rotationPitch));
			}
			
//			System.out.println(String.format("%1$.2f", entity.rotationPitch));
			
			//entity.rotationPitch = player.rotationPitch;
			//entity.prevRotationPitch = player.prevRotationPitch;
		}
		
		
		
		
		
		
		
		float ix1 = 0;
		float iz1 = 0;
		float f111 = entity.rotationYaw * (2 * (float) Math.PI / 360);
		ix1 -= MathHelper.sin(f111) * 2;
		iz1 += MathHelper.cos(f111) * 2;
		if( entity.throttle >= 1){
//		entity.world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX - ix1, entity.posY + 1D, entity.posZ - iz1, 0.0D, 0.0D, 0.0D, new int[0]);//20/4/4用途不明
		}
		//Vec3d looked = player.getLookVec();
		Vec3d look = entity.getLookVec();
		//Vec3 looke = player.getLookVec();
		

		/*if(entity.onGround)
		{
			if( entity.thpower > entity.thmax-10  && entity.getHealth() > 0.0F){
			entity.motionY = look.y * sp * (entity.thpower/2);
			}
		}else{
			if( entity.thpower > entity.thmax-30  && entity.getHealth() > 0.0F){
				entity.motionY = look.y * sp * (entity.thpower/2);
			}else
			if (!entity.onGround)
	        {
				entity.motionY *= 0.9D;
	        }
		}*/
		
		if (player.moveForward > 0.0F) {
			if(entity.throttle < entity.thmax){
				++entity.throttle;
			}
		}
		if (player.moveForward < 0.0F) {
			if(entity.throttle >= 1){
				--entity.throttle;
			}
		}
		if (player.moveStrafing < 0.0F) {
			if(entity.throte < 50){
				entity.throte = entity.throte + 2;
			}
		}
		if (player.moveStrafing > 0.0F) {
			if(entity.throte > -50){
				entity.throte = entity.throte - 2;
			}
		}
		if( entity.throttle >= 0){
			if(entity.thpower < entity.throttle){
				if( entity.throttle > entity.thmax-5){
					entity.thpower = entity.thpower + entity.thmaxa;
				}else{
					entity.thpower = entity.thpower + entity.thmaxa*0.5;
				}
			}else{
				entity.thpower = entity.thpower + entity.thmina;
			}
			if(entity.throttle <= 0 && entity.throttle > 0){
				entity.thpower = entity.thpower + entity.thmina * 2;
			}
		}
	}
	
	
	public static void moveheli_NEW(EntityLivingBase player, EntityGVCLivingBase entity, float sp, float turnspeed){
		/*if(player.rotationYawHead > 360F || player.rotationYawHead < -360F){
			player.rotationYawHead = 0;
			player.rotationYaw = 0;
			player.prevRotationYaw = 0;
			player.prevRotationYawHead = 0;
			player.renderYawOffset = 0;
		}
		if(player.rotationYawHead > 180F){
			player.rotationYawHead = -179F;
			player.rotationYaw = -179F;
			player.prevRotationYaw = -179F;
			player.prevRotationYawHead = -179F;
			player.renderYawOffset = -179F;
		}
		if(player.rotationYawHead < -180F){
			player.rotationYawHead = 179F;
			player.rotationYaw = 179F;
			player.prevRotationYaw = 179F;
			player.prevRotationYawHead = 179F;
			player.renderYawOffset = 179F;
		}*/
		VehicleAI_RotationYawOffset.offset(entity, player);
		float f = Math.abs(entity.rotationYawHead - player.rotationYawHead);
		float f2 = entity.rotationYawHead - player.rotationYawHead;
		entity.rotation = player.prevRotationYawHead;
		entity.rotationp = player.prevRotationPitch;
		if(entity.onGround)
		{
			if(entity.thpower > 1){
			if(f <= turnspeed && f >= -turnspeed){
				entity.rotationYawHead = player.rotationYaw;
				entity.rotationYaw = player.rotationYawHead;
				entity.prevRotationYaw = player.prevRotationYawHead;
				entity.prevRotationYawHead = player.prevRotationYawHead;
				entity.renderYawOffset = player.prevRotationYawHead;
			}
			else if(f2 > 0.1F){
				if(f2 > 180F){
					PL_RoteModel.rotemodel(entity, + turnspeed/5);
				}else{
					PL_RoteModel.rotemodel(entity,- turnspeed/5);
				}
			}
			else if(f2 < -0.1F){
				if(f2 < -180F){
					PL_RoteModel.rotemodel(entity,- turnspeed/5);
				}else{
					PL_RoteModel.rotemodel(entity,+ turnspeed/5);
				}
			}
			}
		}else{
			/*if(f < turnspeed && f > -turnspeed){
				entity.rotationYawHead = player.rotationYaw;
				entity.rotationYaw = player.rotationYawHead;
				entity.prevRotationYaw = player.prevRotationYawHead;
				entity.prevRotationYawHead = player.prevRotationYawHead;
				entity.renderYawOffset = player.prevRotationYawHead;
			}else*/
			if(f2 > (turnspeed+0.1F)){
				if(f2 > 180F){
					PL_RoteModel.rotemodel(entity,+ turnspeed);
					if(entity.throte < 50){
						entity.throte = entity.throte + 2;
					}
				}else{
					PL_RoteModel.rotemodel(entity,- turnspeed);
					if(entity.throte > -50){
						entity.throte = entity.throte - 2;
					}
				}
			}
			else if(f2 < -(turnspeed+0.1F)){
				if(f2 < -180F){
					PL_RoteModel.rotemodel(entity,- turnspeed);
					if(entity.throte > -50){
						entity.throte = entity.throte - 2;
					}
				}else{
					PL_RoteModel.rotemodel(entity,+ turnspeed);
					if(entity.throte < 50){
						entity.throte = entity.throte + 2;
					}
				}
			}else {
				entity.rotationYawHead = player.rotationYaw;
				entity.rotationYaw = player.rotationYawHead;
				entity.prevRotationYaw = player.prevRotationYawHead;
				entity.prevRotationYawHead = player.prevRotationYawHead;
				entity.renderYawOffset = player.prevRotationYawHead;
			}
			/*
			if(player.rotationPitch > 30) {
				entity.rotationPitch = 30;
				entity.prevRotationPitch = 30;
			}else if(player.rotationPitch < -20) {
				entity.rotationPitch = -20;
				entity.prevRotationPitch = -20;
			}
			else {
				entity.rotationPitch = player.rotationPitch;
				entity.prevRotationPitch = player.prevRotationPitch;
			}*/
			/*
			float fz = player.rotationPitch;
	        float f11 = MathHelper.clamp(fz, entity.rotationp_max, entity.rotationp_min);
	        player.prevRotationPitch += f11 - fz;
	        player.rotationPitch += f11 - fz;
	        entity.prevRotationPitch += f11 - fz;
	        entity.rotationPitch += f11 - fz;
	        */
			/*entity.rotep = player.rotationPitch;
			if ((entity.rotationPitch > (entity.rotep + turnspeed)) || (entity.rotationPitch < entity.rotep - turnspeed)) {
				if (entity.rotationPitch < entity.rotep) {
					entity.rotationp = entity.rotationp + turnspeed*1;
					entity.rotationPitch = entity.rotationPitch + turnspeed*1;
					entity.prevRotationPitch = entity.prevRotationPitch + turnspeed*1;
				} else if (entity.rotationPitch > entity.rotep) {
					entity.rotationp = entity.rotationp - turnspeed*1;
					entity.rotationPitch = entity.rotationPitch - turnspeed*1;
					entity.prevRotationPitch = entity.prevRotationPitch - turnspeed*1;
				}
			}*/
			if(!entity.onGround) {
				entity.rotep = player.prevRotationPitch;
				if ((entity.rotationPitch > (entity.rotep + turnspeed)) || (entity.rotationPitch < entity.rotep - turnspeed)) {
					if (entity.rotationPitch < entity.rotep) {
						entity.rotationp = entity.rotationp + turnspeed*1;
						entity.rotationPitch = entity.rotationPitch + turnspeed*1;
						entity.prevRotationPitch = entity.prevRotationPitch + turnspeed*1;
					} else if (entity.rotationPitch > entity.rotep) {
						entity.rotationp = entity.rotationp - turnspeed*1;
						entity.rotationPitch = entity.rotationPitch - turnspeed*1;
						entity.prevRotationPitch = entity.prevRotationPitch - turnspeed*1;
					}
				}
				if(entity.world.isRemote)GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(421, entity.getEntityId(), entity.rotationYaw, entity.rotationPitch));
			}
		}
		

		if (player.moveForward > 0.0F) {
			if(entity.throttle < entity.thmax){
				++entity.throttle;
			}
		}
		if (player.moveForward < 0.0F) {
			if(entity.throttle >= 1){
				--entity.throttle;
			}
		}
		if( entity.throttle >= 0){
			if(entity.thpower < entity.throttle){
				if( entity.throttle > entity.thmax-5){
					entity.thpower = entity.thpower + entity.thmaxa;
				}else{
					entity.thpower = entity.thpower + entity.thmaxa*0.5;
				}
			}else{
				entity.thpower = entity.thpower + entity.thmina;
			}
			if(entity.throttle <= 0 && entity.throttle > 0){
				entity.thpower = entity.thpower + entity.thmina * 2;
			}
		}
		if (player.moveStrafing < 0.0F && entity.thpower >= 1) {
			//entity.moveangle = 3;
			if(entity.throte < 50)entity.throte =  entity.throte + (float)(turnspeed * entity.thpower * 0.05F);
		}
		if (player.moveStrafing > 0.0F && entity.thpower >= 1) {
			//entity.moveangle = 4;
			if(entity.throte > -50)entity.throte = entity.throte - (float)(turnspeed * entity.thpower * 0.05F);
		}
		boolean jump = mod_GVCLib.proxy.jumped();
		if (jump) {
			entity.serverspace = true;
			GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(13, entity.getEntityId()));
			if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
				for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
					GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(13, entity.getEntityId()), playermp);
				}
			}
		}
		if (entity.serverspace) {
			entity.throttle_up = 1;
			entity.serverspace = false;
		}else if(player.isSneaking()) {
			entity.throttle_up = -1;
		}else {
			entity.throttle_up = 0;
		}
		
	}
}
