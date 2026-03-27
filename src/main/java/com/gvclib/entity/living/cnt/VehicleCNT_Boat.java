package gvclib.entity.living.cnt;

import gvclib.mod_GVCLib;
import gvclib.entity.living.AI_EntityMoveTank;
import gvclib.entity.living.AI_EntityWeapon;
import gvclib.entity.living.AI_TankSet;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityMobVehicleBase;
import gvclib.entity.living.PL_TankMove;
import gvclib.event.GVCSoundEvent;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class VehicleCNT_Boat {
	public static void load(EntityMobVehicleBase entity, SoundEvent sound) {
		float f1 = entity.rotationYawHead * (2 * (float) Math.PI / 360);
    	/*if (entity.world
				.getBlockState(new BlockPos(entity.posX, entity.posY  + 0.8, entity.posZ)).getMaterial() == Material.WATER 
				)
		{
			entity.motionY = 0.05F;
		}*/
    	if (entity.world
				.getBlockState(new BlockPos(entity.posX, entity.posY + 0.8, entity.posZ))
				.getMaterial() == Material.WATER) {
			//entity.motionY = 0.05F;
			entity.motionY = -entity.motionY;
			entity.motionY = entity.motionY * 0.5F;
		}
    	
    	if(entity.getArmID_S() == 1) {
			entity.render_hud_scaleup_text2 = false;
			entity.render_hud_scaleup_text3 = true;
			entity.render_hud_scaleup_text4 = false;
		}else if(entity.getArmID_S() == 2) {
			entity.render_hud_scaleup_text2 = false;
			entity.render_hud_scaleup_text3 = false;
			entity.render_hud_scaleup_text4 = true;
		}
    	else {
			entity.render_hud_scaleup_text2 = true;
			entity.render_hud_scaleup_text3 = false;
			entity.render_hud_scaleup_text4 = false;
		}
    	
    	if (entity.canBeSteered() && entity.getControllingPassenger() != null && entity.getHealth() > 0.0F)
		{
			if(entity.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) entity.getControllingPassenger();
			entity.setcanDespawn(1);
			entity.rotation  = entitylivingbase.rotationYawHead;
			entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;
			Vec3d looked = entitylivingbase.getLookVec();
			
			if(entity.weapon4true && entity.getArmID_S() == 2) {
				if (entity.getFTMode() == 1) {
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
							xx = xx + 1;
						}
						if (entitylivingbase.moveForward < 0.0F && (entity.spg_yaw > -range && entity.spg_picth > -range)) {
							xx = xx - 1;
						}
						if (entitylivingbase.moveStrafing > 0.0F && (entity.spg_yaw < range && entity.spg_picth < range)) {
							zz = zz + 1;
						}
						if (entitylivingbase.moveStrafing < 0.0F && (entity.spg_yaw > -range && entity.spg_picth > -range)) {
							zz = zz - 1;
						}
						entity.spg_yaw -= MathHelper.sin(yaw) * xx;
						entity.spg_picth += MathHelper.cos(yaw) * xx;
						entity.spg_yaw -= MathHelper.sin(yaw - 1.57F) * zz;
						entity.spg_picth += MathHelper.cos(yaw - 1.57F) * zz;
						double d5 = entity.spg_yaw;
						double d7 = entity.spg_picth;
						float yawoffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						entity.rotation = yawoffset;
						entity.rotationp = -40;
						BlockPos bp = entity.world.getHeight(new BlockPos(entity.spg_yaw + entity.posX, entity.posY, entity.spg_picth + entity.posZ));
						entity.spg_y = bp.getY();
					}
				} else {
					entity.spg_mode = false;
					entity.spg_yaw = 0;
					entity.spg_picth = 0;
					entity.rotation = entitylivingbase.rotationYawHead;
					entity.rotationp = entitylivingbase.rotationPitch;
					PL_TankMove.movecar(entitylivingbase, entity, entity.sp, entity.turnspeed);
				}
			}else {
				PL_TankMove.movecar(entitylivingbase, entity, entity.sp, entity.turnspeed);
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
			if (kg && entity.weapon4true && entity.getArmID_S() == 2) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(15, entity.getEntityId()));
				if(entity.getFTMode() >= 1){
				}else if(entity.world.isRemote)
				{
					float yaw;
					if(entity.rotationfollowing1[0]) {
						yaw = entitylivingbase.rotationYawHead * (2 * (float) Math.PI / 360);
					}else {
						yaw = entity.rotationYaw * (2 * (float) Math.PI / 360);
					}
					entity.spg_yaw -= MathHelper.sin(yaw) * (entity.spg_min_range);
					entity.spg_picth += MathHelper.cos(yaw) * (entity.spg_min_range);
				}
			}
			if (kc) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(16, entity.getEntityId()));
			}
			
			 if (entity.serverx) {
					if(entity.cooltime5 >= 5){
						if(entity.getArmID_S() == 1) {
							entity.setArmID_S(2);
						}else if(entity.getArmID_S() == 2) {
							entity.setArmID_S(0);
						}
						else {
							entity.setArmID_S(1);
						}
						entity.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
						entity.cooltime5 = 0;
					}
					entity.serverx = false;
				}
			
			if(entity.server1)
		    {
		    	{
    				if(entity.cooltime >= entity.ammo1){
    					entity.counter1 = true;
    					entity.cooltime = 0;
    				}
    				if(entity.weapon1true) {
    					if(entity.getArmID_R() == 1 && entity.weapon11true){
    						entity.weapon11active(looked, entitylivingbase);
    					}else if(entity.getArmID_R() == 2 && entity.weapon12true){
    						entity.weapon11active(looked, entitylivingbase);
    					}else {
    						entity.weapon1active(looked, entitylivingbase);
    					}
        		    }
    			}
		    	entity.server1 = false;
			}
			 if(entity.serverspace)
			    {
			    	if(entity.weapon1key == 2){
	    				if(entity.cooltime >= entity.ammo1){
	    					entity.counter1 = true;
	    					entity.cooltime = 0;
	    				}
	    				if(entity.weapon1true) {
	    					if(entity.getArmID_R() == 1 && entity.weapon11true){
	    						entity.weapon11active(looked, entitylivingbase);
	    					}else if(entity.getArmID_R() == 2 && entity.weapon12true){
	    						entity.weapon12active(looked, entitylivingbase);
	    					}else {
	    						entity.weapon1active(looked, entitylivingbase);
	    					}
	        		    }
	    			}
			    	if(entity.getArmID_S() == 1) {
			    		if(entity.weapon3key == 2){
		    				if(entity.cooltime3 >= entity.ammo3){
		    					entity.counter3 = true;
		    					entity.cooltime3 = 0;
		    				}
		    				if(entity.weapon3true) {
		        		    	entity.weapon3active(looked, entitylivingbase);
		        		    }
		    			}
			    	}else if(entity.getArmID_S() == 2) {
			    		if(entity.weapon4key == 2){
		    				if(entity.cooltime4 >= entity.ammo4){
		    					entity.counter4 = true;
		    					entity.cooltime4 = 0;
		    				}
		    				if(entity.weapon4true) {
		        		    	entity.weapon4active(looked, entitylivingbase);
		        		    }
		    			}
			    	}else {
			    		if(entity.weapon2key == 2){
		    				if(entity.cooltime2 >= entity.ammo2){
		    					entity.counter2 = true;
		    					entity.cooltime2 = 0;
		    				}
		    				if(entity.weapon2true) {
		        		    	entity.weapon2active(looked, entitylivingbase);
		        		    }
		    			}
			    	}
			    	
			    	entity.serverspace = false;
				}
			
			 if (entity.serverg && entity.weapon4true && entity.getArmID_S() == 2) {
					if (entity.cooltime6 > 1 && entity.getWeaponChange() > 0) {
						if(entity.getFTMode() >= 1){
							entity.setFTMode(0);
						}else
						{
							entity.setFTMode(1);
							float yaw;
							if(entity.rotationfollowing1[0]) {
								yaw = entitylivingbase.rotationYawHead * (2 * (float) Math.PI / 360);
							}else {
								yaw = entity.rotationYaw * (2 * (float) Math.PI / 360);
							}
							//spg_yaw = 0;
							//spg_picth = 0;
							entity.spg_yaw -= MathHelper.sin(yaw) * (entity.spg_min_range);
							entity.spg_picth += MathHelper.cos(yaw) * (entity.spg_min_range);
						}
						entity.cooltime6 = 0;
						entity.setWeaponChange(entity.getWeaponChange() - 1);
					}
					{
						entity.serverg = false;
					}
				}
			 
			/*if(AI_EntityWeapon.getRange(entity, entity.mob_w2range, entity.mob_w2range_max_y, entity.mob_w2range_min_y)){
				if(entity.cooltime2 >= entity.ammo2 && entity.getRemain_R() > 0){
					entity.counter2 = true;
					entity.cooltime2 = 0;
				}
				if(entity.weapon2true) {
			    	entity.weapon2activeMob(looked, entity, 0);
			    }
			}*/
			
			
			
			}//player
			else if(entity.getControllingPassenger() instanceof EntityGVCLivingBase && !entity.getVehicleLock()) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) entity.getControllingPassenger();
				entity.rotation  = entitylivingbase.rotationYawHead;
				entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;
				Vec3d looked = entitylivingbase.getLookVec();
				AI_EntityMoveTank.moveBoat(entity, entitylivingbase, f1, entity.sp, entity.turnspeed, entity.mob_min_range, entity.mob_max_range);
				if(AI_EntityWeapon.getRange(entitylivingbase, entity.mob_w1range, entity.mob_w1range_max_y, entity.mob_w1range_min_y)){
					if(entity.cooltime >= entity.ammo1 && entity.getRemain_L() > 0){
						entity.counter1 = true;
						entity.cooltime = 0;
					}
					if(entity.weapon1true) {
	    		    	entity.weapon1activeMob(looked, entitylivingbase, 0);
	    		    }
				}
				if(AI_EntityWeapon.getRange(entitylivingbase, entity.mob_w2range, entity.mob_w2range_max_y, entity.mob_w2range_min_y)){
					if(entity.cooltime2 >= entity.ammo2 && entity.getRemain_R() > 0){
						entity.counter2 = true;
						entity.cooltime2 = 0;
					}
					if(entity.weapon2true) {
				    	entity.weapon2activeMob(looked, entitylivingbase, 0);
				    	//System.out.println("0");
				    }
				}
			}
		}
		
		AI_TankSet.set2(entity, sound, f1, entity.sp, 0.1F);
		
		entity.catchEntityBiped();
		
        
	}
}
