package gvclib.entity.living.cnt;

import gvclib.mod_GVCLib;
import gvclib.entity.living.AI_AirCraftSet;
import gvclib.entity.living.AI_EntityMoveAirCraft_old;
import gvclib.entity.living.AI_EntityWeapon;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityMobVehicleBase;
import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import gvclib.entity.living.PL_AirCraftMove;
import gvclib.entity.living.cnt.ai.AI_EntityMove_Heli;
import gvclib.entity.living.cnt.ai.AI_EntityMove_Heli_Squad;
import gvclib.entity.living.cnt.ai.AI_EntityMove_Tank;
import gvclib.entity.living.cnt.ai.AI_EntityMove_Tank_Squad;
import gvclib.entity.living.cnt.ai.AI_GetTarget_OnRidding;
import gvclib.event.GVCSoundEvent;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import gvclib.network.GVCLClientMessageKeyPressed;
public class VehicleCNT_Kirov {

	public static void load(EntityMobVehicleBase entity, SoundEvent sound) {
		float f1 = entity.rotationYawHead * (2 * (float) Math.PI / 360);
		/*if(entity.world.isRemote) {
    		System.out.println("load_front");
    		System.out.println(String.format("%1$.2f", entity.rotationPitch));
    	}*/
		
		entity.aitypemax = 100;
		entity.aitypemax2 = 60;
		
		if(entity.weapon3true) {
			if(entity.getArmID_S() == 1) {
				entity.render_hud_scaleup_text1 = false;
				entity.render_hud_scaleup_text2 = true;
				entity.render_hud_scaleup_text3 = false;
			}
			else if(entity.getArmID_S() == 2) {
				entity.render_hud_scaleup_text1 = false;
				entity.render_hud_scaleup_text2 = false;
				entity.render_hud_scaleup_text3 = true;
			}
			else {
				entity.render_hud_scaleup_text1 = true;
				entity.render_hud_scaleup_text2 = false;
				entity.render_hud_scaleup_text3 = false;
			}
		}else {
			if(entity.getArmID_S() == 1) {
				entity.render_hud_scaleup_text1 = false;
				entity.render_hud_scaleup_text2 = true;
			}else {
				entity.render_hud_scaleup_text1 = true;
				entity.render_hud_scaleup_text2 = false;
			}
		}
		
    	
    	if (entity.canBeSteered() && entity.getControllingPassenger() != null && entity.getHealth() > 0.0F)
		{
			if(entity.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) entity.getControllingPassenger();
			entity.setcanDespawn(1);
	//		entity.rotation  = entitylivingbase.rotationYawHead;
	//		entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;
			Vec3d looked = entitylivingbase.getLookVec();
			if(entity.getFTMode() != 1){
				PL_AirCraftMove.moveheli_NEW(entitylivingbase, entity, entity.sp, entity.turnspeed);
			}else {
				PL_AirCraftMove.moveheligunner(entitylivingbase, entity, entity.sp, entity.turnspeed);
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
			
			 if (entity.serverg) {
				 if(entity.cooltime6 >= 5){
						if(entity.getFTMode() >= 1){
							entity.setFTMode(0);
						}else
						{
							entity.setFTMode(1);
						}
						entity.cooltime6 = 0;
						entity.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
					}
					{
						entity.serverg = false;
					}
				}
			
			 if (entity.serverx) {
					if(entity.cooltime5 >= 5){
						 if(entity.weapon3true) {
							 if(entity.getArmID_S() == 1) {
									entity.setArmID_S(2);
							}
							 else if(entity.getArmID_S() == 2) {
									entity.setArmID_S(0);
							}
							 else {
									entity.setArmID_S(1);
								}
						 }else {
							 if(entity.getArmID_S() == 1) {
									entity.setArmID_S(0);
								}else {
									entity.setArmID_S(1);
								}
						 }
						entity.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
						entity.cooltime5 = 0;
					}
					entity.serverx = false;
				}
			
			 if (entity.server1) {
				 if(entity.weapon3true) {
					 if(entity.getArmID_S() == 1){
		    				if(entity.cooltime2 >= entity.ammo2){
		    					entity.counter2 = true;
		    					entity.cooltime2 = 0;
		    				}
		    				if(entity.weapon2true) {
		    					entity.weapon2active(looked, entitylivingbase);
		        		    }
		    			}
					 else if(entity.getArmID_S() == 2){
		    				if(entity.cooltime3 >= entity.ammo3){
		    					entity.counter3 = true;
		    					entity.cooltime3 = 0;
		    				}
		    				if(entity.weapon3true) {
		    					entity.weapon3active(looked, entitylivingbase);
		        		    }
		    			}
					 else {
		    				if(entity.cooltime >= entity.ammo1){
		    					entity.counter1 = true;
		    					entity.cooltime = 0;
		    				}
		    				if(entity.weapon1true) {
		    					entity.weapon1active(looked, entitylivingbase);
		        		    }
		    			}
				 }else {
					 if(entity.getArmID_S() == 1){
		    				if(entity.cooltime2 >= entity.ammo2){
		    					entity.counter2 = true;
		    					entity.cooltime2 = 0;
		    				}
		    				if(entity.weapon2true) {
		    					entity.weapon2active(looked, entitylivingbase);
		        		    }
		    			}else {
		    				if(entity.cooltime >= entity.ammo1){
		    					entity.counter1 = true;
		    					entity.cooltime = 0;
		    				}
		    				if(entity.weapon1true) {
		    					entity.weapon1active(looked, entitylivingbase);
		        		    }
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
		    if (entity.serverf) {
					if(entity.weapon2type == 1) {
						if(entity.cooltime2 >= entity.ammo2){
	    					entity.counter2 = true;
	    					entity.cooltime2 = 0;
	    				}
	    				if(entity.weapon2true) {
	    					entity.weapon2active(looked, entitylivingbase);
	        		    }
					}
					else if(entity.weapon4type == 1) {
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
				
				if(entitylivingbase.getMoveT() == 1) {
					entity.thpower = entity.thmax;
					entity.throttle = entity.thmax;
				}
				
				//entity.rotation  = entitylivingbase.rotationYawHead;
				//entitylivingbase.rotationPitch = entity.rotationPitch;
				//entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;
				Vec3d looked = entitylivingbase.getLookVec();
				
				//AI_EntityVehicle.getTargetEntity(this, 50, 20, 20);
				AI_GetTarget_OnRidding.load(entitylivingbase, entity.mob_max_range, entity.mob_w1range_max_y, entity.mob_w1range_min_y, entity.rotationYaw, -180, 180);
				
				if(entitylivingbase.getOwner() != null) {
					AI_EntityMove_Heli_Squad.moveheli(entity, entitylivingbase, f1, entity.sp, entity.turnspeed, entity.mob_min_range, entity.mob_max_range, entity.mob_min_height);
				}else {
					AI_EntityMove_Heli.moveheli(entity, entitylivingbase, f1, entity.sp, entity.turnspeed, entity.mob_min_range, entity.mob_max_range, entity.mob_min_height);
				}
				
				
				if(AI_EntityWeapon.getRange(entitylivingbase, entity.mob_w1range, entity.mob_w1range_max_y, entity.mob_w1range_min_y)){
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
						double d1 = entity.posY - (entitylivingbase.targetentity.posY);
			            double d3 = (double)MathHelper.sqrt(xxz * xxz + zzx * zzx);
			            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			            float rotep_offset = -f11 + 10;
//直升机武器
			            /*if(entity.getAIType2() <= 3) {
			            	if(rotep_offset <= entity.rotationPitch + 20 && rotep_offset >= entity.rotationPitch - 20) {
								if(entity.cooltime >= entity.ammo1 && entity.getRemain_L() > 0){
									entity.counter1 = true;
									entity.cooltime = 0;
								}
								if(entity.weapon1true) {
									entity.weapon1activeMob(looked, entitylivingbase, entity.weapon1type);
									//System.out.println("debug");
				    		    }
							}
			            }else {
			            	if(d1 >= -1) {
								if(entity.cooltime2 >= entity.ammo2 && entity.getRemain_R() > 0){
									entity.counter2 = true;
									entity.cooltime2 = 0;
								}
								if(entity.weapon2true) {
									entity.weapon2activeMob(looked, entitylivingbase, entity.weapon2type);
									//System.out.println("debug");
				    		    }
							}
			            }*/
				if(AI_EntityWeapon.getRange(entitylivingbase, entity.mob_w1range, entity.mob_w1range_max_y, entity.mob_w1range_min_y)){
					if(entity.cooltime >= entity.ammo1 && entity.getRemain_L() > 0){
						entity.counter1 = true;
						entity.cooltime = 0;
						GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(2001, entity.getEntityId(), 0));
					}
					if(entity.weapon1true) {
	    		    	entity.weapon1activeMob(looked, entitylivingbase, entity.weapon1type);
	    		    }
				}
//武器2
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
//武器3
						if(AI_EntityWeapon.getRange(entitylivingbase, entity.mob_w3range, entity.mob_w3range_max_y, entity.mob_w3range_min_y)){
							if(entity.cooltime3 >= entity.ammo3 && entity.getRemain_R() > 0){
								entity.counter3 = true;
								entity.cooltime3 = 0;
							}
							if(entity.weapon3true) {
								entity.weapon3activeMob(looked, entitylivingbase, 0);
								//System.out.println("0");
							}
						}
					}
				}
			}
		}
		
    	if(entity.getFTMode() != 1){
			AI_AirCraftSet.setheli_NEW(entity, sound, f1, entity.sp, 0.1F);
		}else {
			AI_AirCraftSet.setheligunner(entity, sound, f1, entity.sp, 0.1F);
		}
		
    	/*if(entity.world.isRemote) {
    		System.out.println("load_ato");
    		System.out.println(String.format("%1$.2f", entity.rotationPitch));
    	}*/
    	//System.out.println("load_rotationYaw");
		//System.out.println(String.format("%1$.2f", entity.rotationYaw));
    	
		entity.catchEntityBiped();
	}
}
