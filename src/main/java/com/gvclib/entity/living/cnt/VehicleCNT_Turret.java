package gvclib.entity.living.cnt;

import java.util.List;

import gvclib.mod_GVCLib;
import gvclib.entity.living.AI_EntityWeapon;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityMobVehicleBase;
import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import gvclib.entity.living.cnt.ai.AI_EntityMove_Turret;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class VehicleCNT_Turret {
	
	public static void load(EntityMobVehicleBase entity, SoundEvent sound, boolean antiair) {
		float f1 = entity.rotationYawHead * (2 * (float) Math.PI / 360);
		
    	if (entity.canBeSteered() && entity.getControllingPassenger() != null && entity.getHealth() > 0.0F)
		{
			if(entity.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) entity.getControllingPassenger();
			entity.setcanDespawn(1);
			entity.rotation  = entitylivingbase.rotationYawHead;
			entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;
			Vec3d looked = entitylivingbase.getLookVec();
			
			{
				if (entitylivingbase.moveStrafing < 0.0F) {
					if (entitylivingbase.moveForward >= 0.0F) {
						entity.rotationYawHead = entity.rotationYawHead + entity.turnspeed;
						entity.rotationYaw = entity.rotationYaw + entity.turnspeed;
						//entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
						//entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
						//rotecrawler(entity, 1, true,1);
						//rotecrawler(entity, 0, false,1);
					}else {
						entity.rotationYawHead = entity.rotationYawHead - entity.turnspeed;
						entity.rotationYaw = entity.rotationYaw - entity.turnspeed;
						//entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
						//entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
						//rotecrawler(entity, 0, true,1);
						//rotecrawler(entity, 1, false,1);
					}
				}
				if (entitylivingbase.moveStrafing > 0.0F) {
					if (entitylivingbase.moveForward >= 0.0F) {
						entity.rotationYawHead = entity.rotationYawHead - entity.turnspeed;
						entity.rotationYaw = entity.rotationYaw - entity.turnspeed;
						//entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
						//entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
						//rotecrawler(entity, 0, true,1);
						//rotecrawler(entity, 1, false,1);
					}else {
						entity.rotationYawHead = entity.rotationYawHead + entity.turnspeed;
						entity.rotationYaw = entity.rotationYaw + entity.turnspeed;
						//entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
						//entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
						//rotecrawler(entity, 1, true,1);
						//rotecrawler(entity, 0, false,1);
					}
				}
			}
			
			boolean left = mod_GVCLib.proxy.leftclick();
			boolean right = mod_GVCLib.proxy.rightclick();
			boolean jump = mod_GVCLib.proxy.jumped();
			boolean kx = mod_GVCLib.proxy.keyx();
			boolean kg = mod_GVCLib.proxy.keyg();
			boolean kc = mod_GVCLib.proxy.keyc();
			if (left) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(11, entity.getEntityId()));
				entity.server1 = true;
			}
			if (right) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(12, entity.getEntityId()));
			}
			if (jump) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(13, entity.getEntityId()));
			}
			if (kx) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(14, entity.getEntityId()));
			}
			if (kg) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(15, entity.getEntityId()));
			}
			if (kc) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(16, entity.getEntityId()));
			}
			
			 if (entity.server1) {
					{
	    				if(entity.cooltime >= entity.ammo1){
	    					entity.counter1 = true;
	    					entity.cooltime = 0;
	    				}
	    				if(entity.weapon1true) {
	    					entity.weapon1active(looked, entitylivingbase);
	        		    }
	    			}
					entity.server1 = false;
				}
			if(entity.server2)
		    {
		    	entity.server2 = false;
			}
		    
		    if(entity.serverspace)
		    {
		    	entity.serverspace = false;
			}
			
			
			
			}//player
			else if(entity.getControllingPassenger() instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) entity.getControllingPassenger();
				entity.rotation  = entity.rotationYawHead  = entitylivingbase.rotationYawHead;
				entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;
				Vec3d looked = entitylivingbase.getLookVec();
				AI_EntityMove_Turret.move(entity, entitylivingbase, f1, entity.sp, entity.turnspeed, entity.mob_min_range, entity.mob_max_range, antiair);
				if(AI_EntityWeapon.getRangeAngle(entitylivingbase, entity, entity.mob_w1range)){
					/*if(entity.cooltime >= entity.ammo1 && entity.getRemain_L() > 0){
						entity.counter1 = true;
						entity.cooltime = 0;
					}
					if(entity.weapon1true) {
	    		    	entity.weapon1activeMob(looked, entitylivingbase, entity.weapon1type);
	    		    }*/
					if(entitylivingbase.targetentity != null) {
						double xxz = entitylivingbase.targetentity.posX - entity.posX;
						double zzx = entitylivingbase.targetentity.posZ - entity.posZ;
						double ddx = Math.abs(xxz);
						double ddz = Math.abs(zzx);
						if ((ddx > entity.mob_min_range || ddz > entity.mob_min_range)) {
							if(entity.cooltime >= entity.ammo1 && entity.getRemain_L() > 0){
								entity.counter1 = true;
								entity.cooltime = 0;
							}
							if(entity.weapon1true) {
								entity.weapon1activeMob(looked, entitylivingbase, entity.weapon1type);
			    		    }
						}
					}
				}
			}
		}
		
		entity.catchEntityBiped();
		
        
	}
	
	public static void loadSPG(EntityMobVehicleBase entity, SoundEvent sound, boolean antiair) {
		if(entity.getMoveX() == 0) {
			entity.setMoveX((int)entity.posX);
			entity.setMoveY((int)entity.posY);
			entity.setMoveZ((int)entity.posZ);
    	}
    	if(mod_GVCLib.cfg_turret_lockpoint) {
    		if(entity.getMoveX() != 0 
    				&& entity.world.getBlockState(
    						new BlockPos(entity.getMoveX() + 0.5,entity.getMoveY() - 1,entity.getMoveZ() + 0.5)).getBlock() != Blocks.AIR) {
    			entity.setPositionAndUpdate(entity.getMoveX() + 0.5,entity.getMoveY(),entity.getMoveZ() + 0.5);
    		}else {
    			entity.setPositionAndUpdate(entity.getMoveX() + 0.5,entity.posY,entity.getMoveZ() + 0.5);
    		}
    	}
		float f1 = entity.rotationYawHead * (2 * (float) Math.PI / 360);
		entity.setFTMode(1);
		entity.rotationp = entity.rotationPitch = -40;
    	if (entity.canBeSteered() && entity.getControllingPassenger() != null)
		{
			if(entity.getControllingPassenger() instanceof EntityPlayer && entity.getHealth() > 0.0F)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) entity.getControllingPassenger();
			entity.setcanDespawn(1);
			//entity.rotation  = entitylivingbase.rotationYawHead;
			//entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;
			Vec3d looked = entitylivingbase.getLookVec();
			//if (entity.getFTMode() == 1)
			{
				entity.spg_mode = true;
				{
					int range = 120;
					//double d5 = spg_yaw - entity.posX;
					//double d7 = spg_picth - entity.posZ;
					//float yawoffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					float yaw = entitylivingbase.rotationYawHead * (2 * (float) Math.PI / 360);
					float xx = 0;
					float zz = 0;
					if (entitylivingbase.moveForward > 0.0F && (entity.spg_yaw < range && entity.spg_picth < range)) {
						xx = xx + (entity.turnspeed * 0.2F);
					}
					if (entitylivingbase.moveForward < 0.0F && (entity.spg_yaw > -range && entity.spg_picth > -range)) {
						xx = xx - (entity.turnspeed * 0.2F);
					}
					if (entitylivingbase.moveStrafing > 0.0F && (entity.spg_yaw < range && entity.spg_picth < range)) {
						zz = zz + (entity.turnspeed * 0.2F);
					}
					if (entitylivingbase.moveStrafing < 0.0F && (entity.spg_yaw > -range && entity.spg_picth > -range)) {
						zz = zz - (entity.turnspeed * 0.2F);
					}
					entity.spg_yaw -= MathHelper.sin(yaw) * xx;
					entity.spg_picth += MathHelper.cos(yaw) * xx;
					entity.spg_yaw -= MathHelper.sin(yaw - 1.57F) * zz;
					entity.spg_picth += MathHelper.cos(yaw - 1.57F) * zz;
					/*if((entity.spg_yaw < entity.spg_min_range && entity.spg_yaw > 0) && (entity.spg_picth < entity.spg_min_range && entity.spg_picth > 0)) {
						entity.spg_yaw = entity.spg_yaw + 1;
						entity.spg_picth = entity.spg_picth + 1;
					}
					if((entity.spg_yaw < entity.spg_min_range && entity.spg_yaw > 0) && (entity.spg_picth > -entity.spg_min_range && entity.spg_picth < 0)) {
						entity.spg_yaw = entity.spg_yaw + 1;
						entity.spg_picth = entity.spg_picth - 1;
					}
					if((entity.spg_yaw > -entity.spg_min_range && entity.spg_yaw < 0) && (entity.spg_picth < entity.spg_min_range && entity.spg_picth > 0)) {
						entity.spg_yaw = -entity.spg_yaw - 1;
						entity.spg_picth = -entity.spg_picth + 1;
					}
					if((entity.spg_yaw > -entity.spg_min_range && entity.spg_yaw < 0) && (entity.spg_picth > -entity.spg_min_range && entity.spg_picth < 0)) {
						entity.spg_yaw = -entity.spg_yaw - 1;
						entity.spg_picth = -entity.spg_picth - 1;
					}*/
					/*if(entity.spg_picth < entity.spg_min_range && entity.spg_picth > 0) {
						entity.spg_picth = entity.spg_min_range + 1;
					}
					if(entity.spg_picth > -entity.spg_min_range && entity.spg_picth < 0) {
						entity.spg_picth = -entity.spg_min_range - 1;
					}*/
					double d5 = entity.spg_yaw;
					double d7 = entity.spg_picth;
					float yawoffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					entity.rotation = entity.rotationYaw = entity.rotationYawHead = yawoffset;
					
					BlockPos bp = entity.world.getHeight(new BlockPos(entity.spg_yaw + entity.posX, entity.posY, entity.spg_picth + entity.posZ));
					entity.spg_y = bp.getY();
				}
			}
			
			boolean left = mod_GVCLib.proxy.leftclick();
			boolean right = mod_GVCLib.proxy.rightclick();
			boolean jump = mod_GVCLib.proxy.jumped();
			boolean kx = mod_GVCLib.proxy.keyx();
			boolean kg = mod_GVCLib.proxy.keyg();
			boolean kc = mod_GVCLib.proxy.keyc();
			if (left) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(11, entity.getEntityId()));
				entity.server1 = true;
			}
			if (right) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(12, entity.getEntityId()));
			}
			if (jump) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(13, entity.getEntityId()));
			}
			if (kx) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(14, entity.getEntityId()));
			}
			if (kg) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(15, entity.getEntityId()));
			}
			if (kc) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(16, entity.getEntityId()));
			}
			
			 if (entity.server1) {
					{
	    				if(entity.cooltime >= entity.ammo1){
	    					entity.counter1 = true;
	    					entity.cooltime = 0;
	    				}
	    				if(entity.weapon1true) {
	    					entity.weapon1active(looked, entitylivingbase);
	        		    }
	    			}
					entity.server1 = false;
				}
			if(entity.server2)
		    {
		    	entity.server2 = false;
			}
		    
		    if(entity.serverspace)
		    {
		    	entity.serverspace = false;
			}
		    /*if (entity.serverg) {
				if (entity.cooltime6 > 1 && entity.getWeaponChange() > 0) {
					if(entity.getFTMode() >= 1){
						entity.setFTMode(0);
					}else
					{
						entity.setFTMode(1);
						float yaw = entity.rotationYaw * (2 * (float) Math.PI / 360);
						//spg_yaw = 0;
						//spg_picth = 0;
						entity.spg_yaw -= MathHelper.sin(yaw) * 40;
						entity.spg_picth += MathHelper.cos(yaw) * 40;
					}
					entity.cooltime6 = 0;
					entity.setWeaponChange(entity.getWeaponChange() - 1);
				}
				{
					entity.serverg = false;
				}
			}*/
			
			
			}//player
			else if(entity.getControllingPassenger() instanceof EntityGVCLivingBase && entity.getHealth() > 0.0F) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) entity.getControllingPassenger();
				//entity.rotation  = entity.rotationYawHead  = entitylivingbase.rotationYawHead;
				//entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;
				Vec3d looked = entitylivingbase.getLookVec();
				AI_EntityMove_Turret.move(entity, entitylivingbase, f1, entity.sp, entity.turnspeed, entity.mob_min_range, entity.mob_max_range, antiair);
				entity.rotationp = entity.rotationPitch = -40;
				getRange_SPG(entitylivingbase, entity, entity.mob_w1range);
				if(AI_EntityWeapon.getRangeSPG(entitylivingbase, entity, entity.mob_w1range)){
					if(entity.cooltime >= entity.ammo1 && entity.getRemain_L() > 0){
						entity.counter1 = true;
						entity.cooltime = 0;
					}
					if(entity.weapon1true) {
	    		    	entity.weapon1activeMob(looked, entitylivingbase, entity.weapon1type);
	    		    }
				}
			}
		}else {
			float yaw = entity.rotationYaw * (2 * (float) Math.PI / 360);
			//spg_yaw = 0;
			//spg_picth = 0;
			//entity.spg_yaw -= MathHelper.sin(yaw) * 40;
			//entity.spg_picth += MathHelper.cos(yaw) * 40;
		}
		
		entity.catchEntityBiped();
		
        
	}
	
	public static void getRange_SPG(EntityGVCLivingBase entity,EntityGVCLivingBase vehicle, double range){
		boolean task = false;
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),(double) (i - han), 
				(double) (k + han), (double) (l + han), (double) (i + han)))
						.grow(1);
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
				//entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
		if (llist != null) {
			for (int lj = 0; lj < llist.size(); lj++) {
				Entity entity1 = (Entity) llist.get(lj);
				if (entity1.canBeCollidedWith()) {
					if(entity1 != null && entity.getHealth() > 0.0F) {
						if(entity.CanAlly(entity1) && entity1 instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase ally = (EntityGVCLivingBase) entity1;
							if(ally.targetentity != null) {
								entity.targetentity = ally.targetentity;
								if(!entity.getattacktask())entity.setattacktask(true);
								break;
							}
						}
						
					}
					
				}
			}
		}
	}
	
}
