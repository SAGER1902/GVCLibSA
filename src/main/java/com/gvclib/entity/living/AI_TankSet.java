package gvclib.entity.living;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class AI_TankSet {

public static void set2(EntityGVCLivingBase entity, SoundEvent sound, float f1, float sp, float brake){
		
		if ((entity.canBeSteered() && entity.getControllingPassenger() != null) || (entity.getMobMode() == 0) && entity.getHealth() > 0.0F)
		{
			if(entity.ontick % 5 == 0 && (entity.posX != entity.prevPosX || entity.posZ != entity.prevPosZ)){
				//entity.playSound(sound, 4.0F, 1.0F);
				if(sound != null && !entity.world.isRemote)entity.world.playSound((EntityPlayer)null, entity.posX, entity.posY, entity.posZ, sound, SoundCategory.WEATHER, 4.0F, 1.0F);
			}
		}
		double x = 0;
		double y = 0;
		double z = 0;
		//if(entity.throttle >= 0.1F) 
		if(entity.throttle > 0.5F | entity.throttle < -0.2F){
			if(entity.getMoveT() == 1) {
				sp = sp * 0.25F;
			}
			
			/*if (entity.onGround) {
				x -= MathHelper.sin(f1) * sp * entity.throttle;
				z += MathHelper.cos(f1) * sp * entity.throttle;
			} else {
				x -= MathHelper.sin(f1) * sp * entity.throttle;
				z += MathHelper.cos(f1) * sp * entity.throttle;
			}*/
			x -= MathHelper.sin(f1) * sp * entity.throttle;
			z += MathHelper.cos(f1) * sp * entity.throttle;
		}
		//if(!entity.world.isRemote) 
		{
			entity.motionX = x;
			entity.motionZ = z;
			//entity.motionY = y;
			entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
		}
		/*if(!entity.world.isRemote) {
			entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
		}*/
		if(entity.throttle != 0){
			if(entity.throttle < 0.09 && entity.throttle > -0.09) {
				entity.throttle = 0;
			}
			if(entity.throttle > 0){
				entity.throttle = entity.throttle - brake;
				rotecrawler(entity, 0, true,1);
				rotecrawler(entity, 1, true,1);
			}
			if(entity.throttle < 0){
				entity.throttle = entity.throttle + brake;
				rotecrawler(entity, 0, false,1);
				rotecrawler(entity, 1, false,1);
			}
			
		}
		/*if(entity.thpower != 0){
			if(entity.thpower > 0){
				entity.thpower = entity.thpower - brake;
			}
			if(entity.thpower < 0){
				entity.thpower = entity.thpower + brake;
			}
		}*/
		
		
		float speed = MathHelper.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
		//if(speed != 0)
		{
			if(entity.throttle > 0){
				if(entity.thpera < 360){
					entity.thpera = entity.thpera + (speed*40);
				}else{
					entity.thpera = 0;
				}
			}else {
				if(entity.thpera < 0){
					entity.thpera = 360;
				}else{
					entity.thpera = entity.thpera - (speed*40);
				}
			}
		}
		
		if(entity.rotationYawHead > 360F || entity.rotationYawHead < -360F){
        	entity.rotation = 0;
			entity.rotationp = 0;
			entity.rotationYawHead = 0;
			entity.rotationYaw = 0;
			//entity.prevRotationYaw = 0;
			//entity.prevRotationYawHead = 0;
			entity.renderYawOffset = 0;
		}
		if(entity.rotationYawHead > 180F){
			entity.rotation = -179F;
			entity.rotationp = -179F;
			entity.rotationYawHead = -179F;
			entity.rotationYaw = -179F;
			//entity.prevRotationYaw = -179F;
			//entity.prevRotationYawHead = -179F;
			entity.renderYawOffset = -179F;
		}
		if(entity.rotationYawHead < -180F){
			entity.rotation = 179F;
			entity.rotationp = 179F;
			entity.rotationYawHead = 179F;
			entity.rotationYaw = 179F;
			//entity.prevRotationYaw = 179F;
			//entity.prevRotationYawHead = 179F;
			entity.renderYawOffset = 179F;
		}
		if(entity.rotationYaw > entity.prevRotationYaw){
			if(entity.throttle > 0){
				rotecrawler(entity, 1, true,1);
				rotecrawler(entity, 0, false,1);
			}else {
				rotecrawler(entity, 0, true,1);
				rotecrawler(entity, 1, false,1);
			}
		}
		if(entity.rotationYaw < entity.prevRotationYaw){
			if(entity.throttle > 0){
				rotecrawler(entity, 0, true,1);
				rotecrawler(entity, 1, false,1);
			}else {
				rotecrawler(entity, 1, true,1);
				rotecrawler(entity, 0, false,1);
			}
		}
		
		
		/*double x1 = 0;
		double z1 = 0;
		x1 -= MathHelper.sin(entity.rotationYawHead * 0.01745329252F -0.3F) * 3.0;
		z1 += MathHelper.cos(entity.rotationYawHead * 0.01745329252F -0.3F) * 3.0;
		double x2 = 0;
		double z2 = 0;
		x2 -= MathHelper.sin(entity.rotationYawHead * 0.01745329252F +0.3F) * 3.0;
		z2 += MathHelper.cos(entity.rotationYawHead * 0.01745329252F +0.3F) * 3.0;
		entity.world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX-x1, entity.posY + 1.6D, entity.posZ-z1, 0.0D, 0.0D, 0.0D, new int[0]);
		entity.world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX-x2, entity.posY + 1.6D, entity.posZ-z2, 0.0D, 0.0D, 0.0D, new int[0]);
		*/
		if(entity.getHealth() <= entity.getMaxHealth()/2){
			if(entity.getHealth() <= entity.getMaxHealth()/4){
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX-2, entity.posY + 2D, entity.posZ+2, 0.0D, 0.0D, 0.0D, new int[0]);
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX+2, entity.posY + 2D, entity.posZ-1, 0.0D, 0.0D, 0.0D, new int[0]);
				int rx = entity.world.rand.nextInt(5);
				int rz = entity.world.rand.nextInt(5);
				entity.world.spawnParticle(EnumParticleTypes.FLAME, entity.posX-2+rx, entity.posY + 2D, entity.posZ-2+rz, 0.0D, 0.0D, 0.0D, new int[0]);
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX-2+rx, entity.posY + 2D, entity.posZ-2+rz, 0.0D, 0.0D, 0.0D, new int[0]);
			}else{
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX-2, entity.posY + 2D, entity.posZ+2, 0.0D, 0.0D, 0.0D, new int[0]);
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX+2, entity.posY + 2D, entity.posZ-1, 0.0D, 0.0D, 0.0D, new int[0]);
				int rx = entity.world.rand.nextInt(5);
				int rz = entity.world.rand.nextInt(5);
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX-2+rx, entity.posY + 2D, entity.posZ-2+rz, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}
	
	public static void rotecrawler(EntityGVCLivingBase entity, int id, boolean on, int r) {
		if (id == 0) {
			if (!on) {
				if (entity.throttle_r <= 8) {
					entity.throttle_r = entity.throttle_r + (r);
				} else {
					entity.throttle_r = 0;
				}
			} else {
				if (entity.throttle_r > 0) {
					entity.throttle_r = entity.throttle_r - (r);
				} else {
					entity.throttle_r = 8;
				}
			}
		}
		if (id == 1) {
			if (!on) {
				if (entity.throttle_l <= 8) {
					entity.throttle_l = entity.throttle_l + (r);
				} else {
					entity.throttle_l = 0;
				}
			} else {
				if (entity.throttle_l > 0) {
					entity.throttle_l = entity.throttle_l - (r);
				} else {
					entity.throttle_l = 8;
				}
			}
		}
	}

	public static void set(EntityGVCLivingBase entity, SoundEvent sound){
		
		if ((entity.canBeSteered() && entity.getControllingPassenger() != null) || (entity.getMobMode() == 0) && entity.getHealth() > 0.0F)
		{
			if(entity.ontick % 5 == 0 && (entity.posX != entity.prevPosX || entity.posZ != entity.prevPosZ)){
				entity.world.playSound((EntityPlayer)null, entity.posX, entity.posY, entity.posZ, sound, SoundCategory.WEATHER, 4.0F, 1.0F);
			}
		}
		
		if(entity.rotationYawHead > 360F || entity.rotationYawHead < -360F){
        	entity.rotation = 0;
			entity.rotationp = 0;
			entity.rotationYawHead = 0;
			entity.rotationYaw = 0;
			entity.prevRotationYaw = 0;
			entity.prevRotationYawHead = 0;
			entity.renderYawOffset = 0;
		}
		if(entity.rotationYawHead > 180F){
			entity.rotation = -179F;
			entity.rotationp = -179F;
			entity.rotationYawHead = -179F;
			entity.rotationYaw = -179F;
			entity.prevRotationYaw = -179F;
			entity.prevRotationYawHead = -179F;
			entity.renderYawOffset = -179F;
		}
		if(entity.rotationYawHead < -180F){
			entity.rotation = 179F;
			entity.rotationp = 179F;
			entity.rotationYawHead = 179F;
			entity.rotationYaw = 179F;
			entity.prevRotationYaw = 179F;
			entity.prevRotationYawHead = 179F;
			entity.renderYawOffset = 179F;
		}
		
		/*double x1 = 0;
		double z1 = 0;
		x1 -= MathHelper.sin(entity.rotationYawHead * 0.01745329252F -0.3F) * 3.0;
		z1 += MathHelper.cos(entity.rotationYawHead * 0.01745329252F -0.3F) * 3.0;
		double x2 = 0;
		double z2 = 0;
		x2 -= MathHelper.sin(entity.rotationYawHead * 0.01745329252F +0.3F) * 3.0;
		z2 += MathHelper.cos(entity.rotationYawHead * 0.01745329252F +0.3F) * 3.0;
		entity.world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX-x1, entity.posY + 1.6D, entity.posZ-z1, 0.0D, 0.0D, 0.0D, new int[0]);
		entity.world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX-x2, entity.posY + 1.6D, entity.posZ-z2, 0.0D, 0.0D, 0.0D, new int[0]);
		*/
		if(entity.getHealth() <= entity.getMaxHealth()/2){
			if(entity.getHealth() <= entity.getMaxHealth()/4){
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX-2, entity.posY + 2D, entity.posZ+2, 0.0D, 0.0D, 0.0D, new int[0]);
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX+2, entity.posY + 2D, entity.posZ-1, 0.0D, 0.0D, 0.0D, new int[0]);
				int rx = entity.world.rand.nextInt(5);
				int rz = entity.world.rand.nextInt(5);
				entity.world.spawnParticle(EnumParticleTypes.FLAME, entity.posX-2+rx, entity.posY + 2D, entity.posZ-2+rz, 0.0D, 0.0D, 0.0D, new int[0]);
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX-2+rx, entity.posY + 2D, entity.posZ-2+rz, 0.0D, 0.0D, 0.0D, new int[0]);
			}else{
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX-2, entity.posY + 2D, entity.posZ+2, 0.0D, 0.0D, 0.0D, new int[0]);
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX+2, entity.posY + 2D, entity.posZ-1, 0.0D, 0.0D, 0.0D, new int[0]);
				int rx = entity.world.rand.nextInt(5);
				int rz = entity.world.rand.nextInt(5);
				entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX-2+rx, entity.posY + 2D, entity.posZ-2+rz, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}
}
