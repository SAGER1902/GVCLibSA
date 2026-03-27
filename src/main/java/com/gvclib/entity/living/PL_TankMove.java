package gvclib.entity.living;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class PL_TankMove {
	
	public static void move2(EntityLivingBase player, EntityGVCLivingBase entity, float sp, float turnspeed){
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
		if (player.moveForward > 0.0F) {
			if(entity.throttle < entity.thmax){
				entity.throttle = entity.throttle + entity.thmaxa;
			}
			rotecrawler(entity, 0, true,1);
			rotecrawler(entity, 1, true,1);
		}
		if (player.moveForward < 0.0F) {
			if(entity.throttle > entity.thmin){
				entity.throttle = entity.throttle + entity.thmina;
			}
			rotecrawler(entity, 0, false,1);
			rotecrawler(entity, 1, false,1);
		}
		/*if (player.moveForward > 0.0F) {
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
		}*/
		//if(tro != 0)
		{
			if (player.moveStrafing < 0.0F) {
				if (player.moveForward >= 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead + turnspeed;
					entity.rotationYaw = entity.rotationYaw + turnspeed;
					//entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
					//entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
					//rotecrawler(entity, 1, true,1);
					//rotecrawler(entity, 0, false,1);
				}else {
					entity.rotationYawHead = entity.rotationYawHead - turnspeed;
					entity.rotationYaw = entity.rotationYaw - turnspeed;
					//entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
					//entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
					//rotecrawler(entity, 0, true,1);
					//rotecrawler(entity, 1, false,1);
				}
			}
			if (player.moveStrafing > 0.0F) {
				if (player.moveForward >= 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead - turnspeed;
					entity.rotationYaw = entity.rotationYaw - turnspeed;
					//entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
					//entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
					//rotecrawler(entity, 0, true,1);
					//rotecrawler(entity, 1, false,1);
				}else {
					entity.rotationYawHead = entity.rotationYawHead + turnspeed;
					entity.rotationYaw = entity.rotationYaw + turnspeed;
					//entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
					//entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
					//rotecrawler(entity, 1, true,1);
					//rotecrawler(entity, 0, false,1);
				}
			}
		}
	}
	public static void rotecrawler(EntityGVCLivingBase entity, int id, boolean on, int r){
		if(id == 0) {
			if(!on) {
				if(entity.throttle_r <= 7){
					entity.throttle_r = entity.throttle_r + (r);
				}else{
					entity.throttle_r = 0;
				}
			}else {
				if(entity.throttle_r > 0){
					entity.throttle_r = entity.throttle_r - (r);
				}else{
					entity.throttle_r = 7;
				}
			}
		}
		if(id == 1) {
			if(!on) {
				if(entity.throttle_l <= 7){
					entity.throttle_l = entity.throttle_l + (r);
				}else{
					entity.throttle_l = 0;
				}
			}else {
				if(entity.throttle_l > 0){
					entity.throttle_l = entity.throttle_l - (r);
				}else{
					entity.throttle_l = 7;
				}
			}
		}
	}
	
	
	public static void move(EntityLivingBase player, EntityGVCLivingBase entity, float sp, float turnspeed){
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
		
		Vec3d look = entity.getLookVec();
		if (player.moveForward > 0.0F) {
			entity.motionX = look.x * sp * 5;
			entity.motionZ = look.z * sp * 5;
			
		}
		if (player.moveForward < 0.0F) {
			entity.motionX = - look.x * sp * 2;
			entity.motionZ = - look.z * sp * 2;
			
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
	}
	
	public static void movecar(EntityLivingBase player, EntityGVCLivingBase entity, float sp, float turnspeed){
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
		boolean handle = false;
		boolean handle_front = true;
		if (player.moveForward > 0.0F) {
			if(entity.throttle < entity.thmax){
				entity.throttle = entity.throttle + entity.thmaxa;
			}
			rotecrawler(entity, 0, true,1);
			rotecrawler(entity, 1, true,1);
			handle = true;
			handle_front = true;
		}
		if (player.moveForward < 0.0F) {
			if(entity.throttle > entity.thmin){
				entity.throttle = entity.throttle + entity.thmina;
			}
			rotecrawler(entity, 0, false,1);
			rotecrawler(entity, 1, false,1);
			handle = true;
			handle_front = false;
		}
		if(entity.throttle > 0) {
			handle = true;
			handle_front = true;
		}else if(entity.throttle < 0) {
			handle = true;
			handle_front = false;
		}else {
			handle = false;
		}
		if(handle) {
			if(handle_front) {
				if (player.moveStrafing < 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead + turnspeed;
					entity.rotationYaw = entity.rotationYaw + turnspeed;
					//player.rotationYawHead = player.rotationYawHead + turnspeed;
					//player.rotationYaw = player.rotationYaw+ turnspeed;
					//player.rotationYawHead += turnspeed;
					//player.rotationYaw += turnspeed;
					/*player.setRenderYawOffset(entity.rotationYawHead);
					player.rotationYaw += turnspeed;
					player.setRotationYawHead(player.rotationYaw);*/
				}
				if (player.moveStrafing > 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead - turnspeed;
					entity.rotationYaw = entity.rotationYaw - turnspeed;
					//player.rotationYawHead = player.rotationYawHead - turnspeed;
					//player.rotationYaw = player.rotationYaw - turnspeed;
					//player.rotationYawHead -= turnspeed;
					//player.rotationYaw -= turnspeed;
					/*player.setRenderYawOffset(entity.rotationYawHead);
					player.rotationYaw -= turnspeed;
					player.setRotationYawHead(player.rotationYaw);*/
				}
			}else {
				if (player.moveStrafing < 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead - turnspeed;
					entity.rotationYaw = entity.rotationYaw - turnspeed;
					//player.rotationYawHead = player.rotationYawHead - turnspeed;
					//player.rotationYaw = player.rotationYaw - turnspeed;
					//player.rotationYawHead -= turnspeed;
					//player.rotationYaw -= turnspeed;
					/*player.setRenderYawOffset(entity.rotationYawHead);
					player.rotationYaw -= turnspeed;
					player.setRotationYawHead(player.rotationYaw);*/
				}
				if (player.moveStrafing > 0.0F) {
					entity.rotationYawHead = entity.rotationYawHead + turnspeed;
					entity.rotationYaw = entity.rotationYaw + turnspeed;
					//player.rotationYawHead = player.rotationYawHead + turnspeed;
					//player.rotationYaw = player.rotationYaw+ turnspeed;
					//player.rotationYawHead -= turnspeed;
					//player.rotationYaw -= turnspeed;
					/*player.setRenderYawOffset(entity.rotationYawHead);
					player.rotationYaw += turnspeed;
					player.setRotationYawHead(player.rotationYaw);*/
				}
			}
			/*player.setRenderYawOffset(entity.rotationYawHead);
	        float f = MathHelper.wrapDegrees(player.rotationYaw - entity.rotationYawHead);
	        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
	        player.prevRotationYaw += f1 - f;
	        player.rotationYaw += f1 - f;
	        player.setRotationYawHead(player.rotationYaw);*/
		}
		
	}
}
