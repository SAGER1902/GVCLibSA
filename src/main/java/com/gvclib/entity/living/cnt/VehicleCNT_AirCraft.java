package gvclib.entity.living.cnt;

import gvclib.mod_GVCLib;
import gvclib.entity.living.AI_AirCraftSet;
import gvclib.entity.living.AI_EntityMoveAirCraft;
import gvclib.entity.living.AI_EntityWeapon;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityMobVehicleBase;
import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import gvclib.entity.living.PL_AirCraftMove;
import gvclib.entity.living.PL_Weapon_new;
import gvclib.entity.living.cnt.ai.AI_EntityMove_AirCraft;
import gvclib.entity.living.cnt.ai.AI_EntityMove_AirCraft_Squad;
import gvclib.entity.living.cnt.ai.AI_EntityMove_Tank;
import gvclib.entity.living.cnt.ai.AI_EntityMove_Tank_Squad;
import gvclib.event.GVCSoundEvent;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class VehicleCNT_AirCraft {
	
	public static void load(EntityMobVehicleBase entity, SoundEvent sound, boolean true_subweapon) {
		float f1 = entity.rotationYawHead * (2 * (float) Math.PI / 360);
    	if(entity.getArmID_S() == 1) {
			entity.render_hud_scaleup_text2 = false;
			entity.render_hud_scaleup_text3 = true;
		}else {
			entity.render_hud_scaleup_text2 = true;
			entity.render_hud_scaleup_text3 = false;
		}
    	if (entity.canBeSteered() && entity.getControllingPassenger() != null && entity.getHealth() > 0.0F)
		{
			if(entity.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) entity.getControllingPassenger();
			entity.setcanDespawn(1);
			Vec3d looked = entitylivingbase.getLookVec();
			if(entity.getFTMode() != 1 && entity.getFTMode() != 2){
				PL_AirCraftMove.movefighter(entitylivingbase, entity, entity.sp, entity.turnspeed);
			}
			else if(entity.getFTMode() == 1){
				entity.rotep = 0;
				if ((entity.rotationPitch > (entity.rotep + entity.turnspeed)) || (entity.rotationPitch < entity.rotep - entity.turnspeed)) {
					if (entity.rotationPitch < entity.rotep) {
						entity.rotationp = entity.rotationp + entity.turnspeed*1;
						entity.rotationPitch = entity.rotationPitch + entity.turnspeed*1;
						entity.prevRotationPitch = entity.prevRotationPitch + entity.turnspeed*1;
					} else if (entity.rotationPitch > entity.rotep) {
						entity.rotationp = entity.rotationp - entity.turnspeed*1;
						entity.rotationPitch = entity.rotationPitch - entity.turnspeed*1;
						entity.prevRotationPitch = entity.prevRotationPitch - entity.turnspeed*1;
					}
				}
			}
			else if(entity.getFTMode() == 2){
				if (entitylivingbase.moveForward > 0.0F) {
					entity.rotationPitch = entity.rotationPitch + 0.2F;
				}
				if (entitylivingbase.moveForward < 0.0F) {
					entity.rotationPitch = entity.rotationPitch - 0.2F;
				}
				if (entitylivingbase.moveStrafing < 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead + entity.turnspeed* 1F;
					entity.rotationYaw = entity.rotationYaw + entity.turnspeed* 1F;
				}
				if (entitylivingbase.moveStrafing > 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead - entity.turnspeed* 1F;
					entity.rotationYaw = entity.rotationYaw - entity.turnspeed* 1F;
				}
			}
			
			boolean left = mod_GVCLib.proxy.leftclick();
			boolean right = mod_GVCLib.proxy.rightclick();
			boolean jump = mod_GVCLib.proxy.jumped();
			boolean kx = mod_GVCLib.proxy.keyx();
			boolean kg = mod_GVCLib.proxy.keyg();
			boolean kc = mod_GVCLib.proxy.keyc();
			boolean kf = mod_GVCLib.proxy.keyf();
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
			if (kf) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(19, entity.getEntityId()));
			}
			 if (entity.serverx) {
					if(entity.cooltime3 >= 5){
						if(entity.getArmID_S() == 1) {
							entity.setArmID_S(0);
						}else {
							entity.setArmID_S(1);
						}
						entity.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
						entity.cooltime3 = 0;
						
					}
					entity.serverx = false;
				}
			 if (entity.serverg) {
					if(entity.cooltime4 >= 5){
						if(entity.getFTMode() == 1) {
							entity.setFTMode(2);
						}
						if(entity.getFTMode() == 2) {
							entity.setFTMode(0);
						}
						else {
							entity.setFTMode(1);
						}
						entity.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
						entity.cooltime4 = 0;
					}
					entity.serverg = false;
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
		    	if(entity.getArmID_S() == 1){
    				if(entity.cooltime3 >= entity.ammo3){
    					entity.counter3 = true;
    					entity.cooltime3 = 0;
    				}
    				if(entity.weapon3true) {
    					entity.weapon3active(looked, entitylivingbase);
        		    }
    			}else {
    				if(entity.cooltime2 >= entity.ammo2){
    					entity.counter2 = true;
    					entity.cooltime2 = 0;
    				}
    				if(entity.weapon2true) {
    					entity.weapon2active(looked, entitylivingbase);
        		    }
    			}
		    	entity.serverspace = false;
			}
			
		    if (entity.serverf) {
				{
    				if(entity.cooltime4 >= entity.ammo4){
    					entity.counter4 = true;
    					entity.cooltime4 = 0;
    				}
    				if(entity.weapon4true) {
    					entity.weapon4active(looked, entitylivingbase);
        		    }
    			}
				entity.serverf = false;
			}

			}//player
			else if(entity.getControllingPassenger() instanceof EntityGVCLivingBase && !entity.getVehicleLock()) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) entity.getControllingPassenger();
				Vec3d looked = entitylivingbase.getLookVec();
				if(entitylivingbase.getOwner() != null) {
					AI_EntityMove_AirCraft_Squad.movefighter(entity, entitylivingbase, f1, entity.sp, entity.turnspeed * 0.5F, entity.mob_min_range, entity.mob_max_range, entity.mob_min_height);
				}else {
					if(entity.vehicletype == 4) {
						AI_EntityMove_AirCraft.movebomber(entity, entitylivingbase, f1, entity.sp, entity.turnspeed * 0.5F, entity.mob_min_range, entity.mob_max_range, entity.mob_min_height);
					}else {
						AI_EntityMove_AirCraft.movefighter(entity, entitylivingbase, f1, entity.sp, entity.turnspeed * 0.5F, entity.mob_min_range, entity.mob_max_range, entity.mob_min_height);
					}
				}
				if(AI_EntityWeapon.getRange(entitylivingbase, entity.mob_w1range, entity.mob_w1range_max_y, entity.mob_w1range_min_y)){
					if(entity.cooltime >= entity.ammo1 && entity.getRemain_L() > 0){
						entity.counter1 = true;
						entity.cooltime = 0;
						GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(2001, entity.getEntityId(), 0));
					}
					if(entity.weapon1true) {
	    		    	entity.weapon1activeMob(looked, entitylivingbase, entity.weapon1type);
	    		    }
					
					if(entity.cooltime2 >= entity.ammo2 && entity.getRemain_R() > 0){
						entity.counter2 = true;
						entity.cooltime2 = 0;
					}
					if(entity.weapon2true) {
						entity.weapon2activeMob(looked, entitylivingbase, entity.weapon2type);
					}
				}
				if(AI_EntityWeapon.getRange(entitylivingbase, entity.mob_w3range, entity.mob_w3range_max_y, entity.mob_w3range_min_y)){
					if(entity.cooltime3 >= entity.ammo3 && entity.getRemain_R() > 0){
						entity.counter3 = true;
						entity.cooltime3 = 0;
					}
					if(entity.weapon3true) {
						entity.weapon3activeMob(looked, entitylivingbase, 0);
					}
				}
			}
		}
    	AI_AirCraftSet.setfighter(entity, sound, f1, entity.sp, 0.1F);
		entity.catchEntityBiped();
	}
}
