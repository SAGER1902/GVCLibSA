package gvclib.entity.living.cnt;

import gvclib.mod_GVCLib;
import gvclib.entity.living.AI_EntityMoveTank;
import gvclib.entity.living.AI_EntityWeapon;
import gvclib.entity.living.AI_TankSet;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityMobVehicleBase;
import gvclib.entity.living.PL_TankMove;
import gvclib.entity.living.cnt.ai.AI_EntityMove_Tank;
import gvclib.entity.living.cnt.ai.AI_EntityMove_Tank_Squad;
import gvclib.event.GVCSoundEvent;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class VehicleCNT_Tank {

	
	public static void load(EntityMobVehicleBase entity, SoundEvent sound) {
    	float f1 = entity.rotationYawHead * (2 * (float) Math.PI / 360);
    	if (entity.canBeSteered() && entity.getControllingPassenger() != null && entity.getHealth() > 0.0F)
		{
			if(entity.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) entity.getControllingPassenger();
			entity.setcanDespawn(1);
			/*entity.rotation  = entitylivingbase.rotationYawHead;
			entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;*/
			
			float f3 = (float) (entitylivingbase.rotationYawHead - entity.rotation);// -180 ~ 0 ~ 180
			if(f3>0){//右移 +1
				if(f3>180F){
					entity.rotation_turret-=entity.turnspeed;
				}else{
					entity.rotation_turret+=entity.turnspeed;
				}
			}else if(f3<0){//左移 -1
				if(f3<-180F){
					entity.rotation_turret+=entity.turnspeed;
				}else{
					entity.rotation_turret-=entity.turnspeed;
				}
			}
			if(entity.rotation>180||entity.rotation<-180||f3<2&&f3>0||f3<0&&f3>-2){
				entity.rotation_turret = entitylivingbase.rotationYawHead;
			}
			entity.rotation = entity.rotation_turret;//yaw
			
			float f2 = (float) (entitylivingbase.rotationPitch - entity.rotationp);// -180 ~ 0 ~ 180
			if(entity.rotationp_turret<entitylivingbase.rotationPitch){
				entity.rotationp_turret+=1;
			}else if(entity.rotationp_turret>entitylivingbase.rotationPitch){
				entity.rotationp_turret-=1;
			}
			if(f2<2&&f2>0||f2<0&&f2>-2){
				entity.rotationp_turret = entitylivingbase.rotationPitch;
			}
			entity.rotationp = entity.rotationPitch = entity.rotationp_turret;//pitch
			
			Vec3d looked = entitylivingbase.getLookVec();
			PL_TankMove.move2(entitylivingbase, entity, entity.sp, entity.turnspeed);
			
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
			
			/*if (entity.serverx) {
				if (entity.cooltime5 > 1 && entity.getRemain_A() > 0) {
					if(entity.getArmID_R() == 0 && entity.weapon11true){
						entity.setArmID_R(1);
					}else if(entity.getArmID_R() == 1 && entity.weapon12true){
						entity.setArmID_R(2);
					}else
					{
						entity.setArmID_R(0);
					}
					entity.cooltime5 = 0;
					entity.setRemain_A(entity.getRemain_A() - 1);
					entity.playSound(GVCSoundEvent.reload_shell, 1.0F, 1.0F);
				}
				{
					entity.serverx = false;
				}
			}*/
			
			if(entity.server1)
		    {
		    	{
    				if(entity.cooltime >= entity.ammo1){
    					entity.counter1 = true;
    					entity.cooltime = 0;
    					//GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(2001, entity.getEntityId(), entity.cooltime));
    					//GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(2001, entity.getEntityId(), entity.cooltime));
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
		    	entity.server1 = false;
			}
		    
		    if(entity.serverspace)
		    {
		    	{
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
			
			
			
			}//player
			else if(entity.getControllingPassenger() instanceof EntityGVCLivingBase && !entity.getVehicleLock()) {
				EntityGVCLivingBase entitylivingbase = (EntityGVCLivingBase) entity.getControllingPassenger();
				entity.CNT_MobRidding_getHead(entitylivingbase, entity.mob_w1range_max_y);
				/*entity.rotation  = entitylivingbase.rotationYawHead;
				entity.rotationp = entity.rotationPitch = entitylivingbase.rotationPitch;*/
				
				float f3 = (float) (entitylivingbase.rotationYawHead - entity.rotation);// -180 ~ 0 ~ 180
				if(f3>0){//右移 +1
					if(f3>180F){
						entity.rotation_turret-=entity.turnspeed;
					}else{
						entity.rotation_turret+=entity.turnspeed;
					}
				}else if(f3<0){//左移 -1
					if(f3<-180F){
						entity.rotation_turret+=entity.turnspeed;
					}else{
						entity.rotation_turret-=entity.turnspeed;
					}
				}
				if(entity.rotation>180||entity.rotation<-180||f3<2&&f3>0||f3<0&&f3>-2){
					entity.rotation_turret = entitylivingbase.rotationYawHead;
				}
				entity.rotation = entity.rotation_turret;//yaw
				
				float f2 = (float) (entitylivingbase.rotationPitch - entity.rotationp);// -180 ~ 0 ~ 180
				if(entity.rotationp_turret<entitylivingbase.rotationPitch){
					entity.rotationp_turret+=1;
				}else if(entity.rotationp_turret>entitylivingbase.rotationPitch){
					entity.rotationp_turret-=1;
				}
				if(f2<2&&f2>0||f2<0&&f2>-2){
					entity.rotationp_turret = entitylivingbase.rotationPitch;
				}
				entity.rotationp = entity.rotationPitch = entity.rotationp_turret;//pitch

				Vec3d looked = entitylivingbase.getLookVec();
				if(entitylivingbase.getOwner() != null) {
					AI_EntityMove_Tank_Squad.move(entity, entitylivingbase, f1, entity.sp, entity.turnspeed, entity.mob_min_range, entity.mob_max_range);
				}else {
					AI_EntityMove_Tank.move(entity, entitylivingbase, f1, entity.sp, entity.turnspeed, entity.mob_min_range, entity.mob_max_range);
				}
				if(AI_EntityWeapon.getRange(entitylivingbase, entity.mob_w1range, entity.mob_w1range_max_y, entity.mob_w1range_min_y)){
					if(entity.cooltime >= entity.ammo1 && entity.getRemain_L() > 0){
						entity.counter1 = true;
						entity.cooltime = 0;
		//				GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(2001, entity.getEntityId(), entity.cooltime));
    	//				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(2001, entity.getEntityId(), entity.cooltime));
					}
					if(entity.weapon1true) {
	    		    	entity.weapon1activeMob(looked, entitylivingbase, 0);
	    		    }
				}
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
						if(AI_EntityWeapon.getRange(entitylivingbase, entity.mob_w2range, entity.mob_w2range_max_y, entity.mob_w2range_min_y)){
							if(entity.cooltime2 >= entity.ammo2 && entity.getRemain_R() > 0 && entity.world.rand.nextInt(6)>3){
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
