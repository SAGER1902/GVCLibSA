package gvclib.entity.living;

import gvclib.event.GVCSoundEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AI_AirCraftSet {

	public static void setentityrote(EntityGVCLivingBase entity) {
		if(entity.rotationYawHead > 360F || entity.rotationYawHead < -360F){
        	entity.rotation = 0;
			//entity.rotationp = 0;
			entity.rotationYawHead = 0;
			entity.rotationYaw = 0;
			entity.prevRotationYaw = 0;
			entity.prevRotationYawHead = 0;
			entity.renderYawOffset = 0;
		}
		if(entity.rotationYawHead > 180F){
			entity.rotation = -179F;
			//entity.rotationp = -179F;
			entity.rotationYawHead = -179F;
			entity.rotationYaw = -179F;
			entity.prevRotationYaw = -179F;
			entity.prevRotationYawHead = -179F;
			entity.renderYawOffset = -179F;
		}
		if(entity.rotationYawHead < -180F){
			entity.rotation = 179F;
			//entity.rotationp = 179F;
			entity.rotationYawHead = 179F;
			entity.rotationYaw = 179F;
			entity.prevRotationYaw = 179F;
			entity.prevRotationYawHead = 179F;
			entity.renderYawOffset = 179F;
		}
	}
	
	public static void set(EntityGVCLivingBase entity){
		float f1 = 0.999999F;
		entity.motionX *= (double)f1;
        //this.motionY *= (double)f1;
		entity.motionZ *= (double)f1;
		if(entity.throttle > 1){
			if(entity.ontick % 5 == 0)
			{
				//entity.playSound(GVCSoundEvent.sound_heli, 5.0F, 1.0F);
				entity.world.playSound((EntityPlayer)null, entity.posX, entity.posY, entity.posZ, GVCSoundEvent.sound_heli, SoundCategory.WEATHER, 4.0F, 1.0F);
			}
		}
		setentityrote(entity);
		if(entity.th > 1){
		entity.motionY *= 0.5;
		}
		if(entity.throte >= 0){
			--entity.throte;
		}
		if(entity.throte <= 0){
			++entity.throte;
		}
	}

	public static void setfighter(EntityGVCLivingBase entity, SoundEvent sound, float f1, float sp, float brake){
		Vec3d look = entity.getLookVec();
		double dx = Math.abs(entity.posX - entity.prevPosX);
		double dz = Math.abs(entity.posZ - entity.prevPosZ);
		float dd = (float)Math.sqrt((dx * dx) + (dz * dz)) * 20;
		if(dd > 5 && (entity.collidedHorizontally) && entity != null){
			entity.attackEntityFrom(DamageSource.causeIndirectDamage(entity, entity), 10F * dd);
		}
		if(entity.throttle > 1){
			//entity.motionY *= 0.2;
		}
		double dxX = Math.abs(entity.posX - entity.prevPosX);
		double dzX = Math.abs(entity.posZ - entity.prevPosZ);
		float ddXZ = (float)Math.sqrt((dx * dx) + (dz * dz)) * 20;
		
		double x = 0;
		double y = entity.motionY;
		double z = 0;
		{
			x -= MathHelper.sin(f1) * sp * entity.thpower;
			z += MathHelper.cos(f1) * sp * entity.thpower;
			if(entity.onGround) {
				//if( entity.thpower >= entity.thmax/2)
				if(entity.thpower >= entity.thmax){
					y = look.y * sp * (entity.thpower);
				}
			}else {
				if(entity.thpower > 15) {
					y = look.y * sp * (entity.thpower);
				}else {
					y = y * ((15 - entity.thpower) * 0.1);
				}
			}
		}
		/*if(entity.onGround) {
			{
				entity.motionX = x;
				entity.motionZ = z;
				if(entity.thpower >= entity.thmax) entity.motionY = y;
				entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
			}
		}else */
		{
			if(entity.thpower > 5) 
			{
				entity.motionX = x;
				entity.motionZ = z;
				entity.motionY = y;
				entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
			}
		}
		
		if(entity.getControllingPassenger() == null && entity.getHealth() > 0.0F) 
		{
			if(entity.thpower > 1){
				entity.thpower = entity.thpower - brake;
			}
			if(entity.throttle > 1){
				--entity.throttle;
			}
			//entity.rotationPitch = entity.rotationPitch + 0.5F;
			//entity.setVelocity(look.x, MathHelper.sin(-entity.rotationPitch * 0.017453292F), look.z);
		}
		if(entity.getHealth() <= 0.0F) {
			//entity.rotationPitch = entity.rotationPitch + 0.5F;
			//entity.setVelocity(look.x, MathHelper.sin(-entity.rotationPitch * 0.017453292F), look.z);
		}
		if(entity.throttle > 1){
			if(entity.ontick % 8 == 0)
			{
				entity.world.playSound((EntityPlayer)null, entity.posX, entity.posY, entity.posZ, sound, SoundCategory.WEATHER, 5.0F, 1.0F);
			}
		}
		setentityrote(entity);
		
		if(entity.throte >= 0){
			--entity.throte;
		}
		if(entity.throte <= 0){
			++entity.throte;
		}
		if(entity.throttle >= 1){
			if(entity.thpera < 360){
				entity.thpera = entity.thpera + (entity.throttle*2);
			}else{
				entity.thpera = 0;
			}
		}
		//System.out.println(String.format("%1$.2f", entity.rotationPitch));
	}
	
	public static void setheli(EntityGVCLivingBase entity, SoundEvent sound, float f1, float sp, float brake){
		Vec3d look = entity.getLookVec();
		double dx = Math.abs(entity.posX - entity.prevPosX);
		double dz = Math.abs(entity.posZ - entity.prevPosZ);
		float dd = (float)Math.sqrt((dx * dx) + (dz * dz)) * 20;
		if(dd > 5 && (entity.collidedHorizontally) && entity != null){
			entity.attackEntityFrom(DamageSource.causeIndirectDamage(entity, entity), 10F);
		}
		if(entity.throttle > 1){
			//entity.motionY *= 0.2;
		}
		double x = 0;
		double y = -0.1;
		double z = 0;
		//if( entity.throttle >= 1)
		{
			double speed = -entity.rotationPitch * entity.thpower;
			x += MathHelper.sin(entity.rotationYawHead * 0.01745329252F) * sp * speed * 0.015;
			z -= MathHelper.cos(entity.rotationYawHead * 0.01745329252F) * sp * speed *0.015;
		}
		if(entity.moveangle == 3) {
			x += MathHelper.sin(entity.rotationYawHead * 0.01745329252F - 1.57F) * sp;
			z -= MathHelper.cos(entity.rotationYawHead * 0.01745329252F - 1.57F) * sp;
		}
		if(entity.moveangle == 4) {
			x += MathHelper.sin(entity.rotationYawHead * 0.01745329252F + 1.57F) * sp;
			z -= MathHelper.cos(entity.rotationYawHead * 0.01745329252F + 1.57F) * sp;
		}
		//if(entity.thpower > entity.thmax / 2)
		if(entity.thpower > 1)
		{
			if(entity.rotationPitch > -25 && entity.rotationPitch < 25){
				y += 0.01 * (entity.thpower + (25 - Math.abs(entity.rotationPitch))  * 0.1);
			}
			else if(entity.rotationPitch > -45 && entity.rotationPitch < 45){
				double yy = 0.01 * (entity.thpower + (45 - Math.abs(entity.rotationPitch))  * 0.1);
				if(yy > 0.1) {
					y += 0.1;
				}else {
					y += yy;
				}
				//y += 0.1;
				//System.out.println(String.format("%1$.2f", y));
			}else {
				//y -= (90 - Math.abs(entity.rotationPitch)) * 0.01 * entity.thpower;
			}
		}
		if(entity.thpower > 1) 
		{
			entity.motionX = x;
			entity.motionZ = z;
			entity.motionY = y;
			entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
		}
		
		
		
		if(entity.getControllingPassenger() == null  && entity.getHealth() > 0.0F) 
		{
			if(entity.thpower > 1){
				entity.thpower = entity.thpower - brake;
			}
			if(entity.throttle > 1){
				--entity.throttle;
			}
			if(!entity.onGround)entity.rotationPitch = entity.rotationPitch + 0.5F;
			//entity.setVelocity(look.x, MathHelper.sin(-entity.rotationPitch * 0.017453292F), look.z);
		}
		if(entity.getHealth() <= 0.0F && entity.rotationPitch < 60) {
			if(!entity.onGround)entity.rotationPitch = entity.rotationPitch + 0.5F;
			//entity.setVelocity(look.x, MathHelper.sin(-entity.rotationPitch * 0.017453292F), look.z);
		}
		if(entity.throttle > 1){
			if(entity.ontick % 4 == 0)
			{
				entity.world.playSound((EntityPlayer)null, entity.posX, entity.posY, entity.posZ, sound, SoundCategory.WEATHER, 5.0F, 1.0F);
			}
		}
		setentityrote(entity);
		
		if(entity.onGround) {
			if(entity.rotationPitch > 1) {
				--entity.rotationPitch;
			}
			else if(entity.rotationPitch < -1) {
				++entity.rotationPitch;
			}
			if(entity.rotationPitch > 15 || entity.rotationPitch < -15) {
				entity.attackEntityFrom(DamageSource.causeIndirectDamage(entity, entity), 1F);
			}
			
		}
		
		if(entity.throte >= 0){
			--entity.throte;
		}
		if(entity.throte <= 0){
			++entity.throte;
		}
		if( entity.throttle >= 1){
			if(entity.thpera < 360){
				entity.thpera = entity.thpera + (entity.throttle*2);
			}else{
				entity.thpera = 0;
			}
		}
		entity.moveangle = 0;
		
		
		
	}
	
	public static void setheligunner(EntityGVCLivingBase entity, SoundEvent sound, float f1, float sp, float brake){
		Vec3d look = entity.getLookVec();
		double dx = Math.abs(entity.posX - entity.prevPosX);
		double dz = Math.abs(entity.posZ - entity.prevPosZ);
		float dd = (float)Math.sqrt((dx * dx) + (dz * dz)) * 20;
		if(dd > 5 && (entity.collidedHorizontally) && entity != null){
			entity.attackEntityFrom(DamageSource.causeIndirectDamage(entity, entity), 10F);
		}
		double x = 0;
		double y = 0;
		double z = 0;
		if(entity.moveangle == 1) {
			entity.rotationPitch = 10;
			x -= MathHelper.sin(f1) * sp * 1;
			z += MathHelper.cos(f1) * sp * 1;
		}
		if(entity.moveangle == 2) {
			entity.rotationPitch = -10;
			x -= MathHelper.sin(f1) * sp * -1;
			z += MathHelper.cos(f1) * sp * -1;
		}
		if(entity.thpower > 1) 
		{
			entity.motionX = x;
			entity.motionZ = z;
			entity.motionY = y;
			entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
		}
		if(entity.getControllingPassenger() == null) 
		{
		}
		if(entity.getHealth() <= 0.0F) {
			entity.rotationPitch = entity.rotationPitch + 0.5F;
		}
		if(entity.throttle > 1){
			if(entity.ontick % 4 == 0)
			{
				entity.world.playSound((EntityPlayer)null, entity.posX, entity.posY, entity.posZ, sound, SoundCategory.WEATHER, 5.0F, 1.0F);
			}
		}
		setentityrote(entity);
		
		if(entity.throte >= 0){
			--entity.throte;
		}
		if(entity.throte <= 0){
			++entity.throte;
		}
		if( entity.throttle >= 1){
			if(entity.thpera < 360){
				entity.thpera = entity.thpera + (entity.throttle*2);
			}else{
				entity.thpera = 0;
			}
		}
		entity.moveangle = 0;
	}
	
	
	public static void setheli_NEW(EntityVehicleBase entity, SoundEvent sound, float f1, float sp, float brake){
		Vec3d look = entity.getLookVec();
		double dx = Math.abs(entity.posX - entity.prevPosX);
		double dz = Math.abs(entity.posZ - entity.prevPosZ);
		float dd = (float)Math.sqrt((dx * dx) + (dz * dz)) * 20;
		if(dd > 5 && (entity.collidedHorizontally) && entity != null){
			entity.attackEntityFrom(DamageSource.causeIndirectDamage(entity, entity), 10F * dd);
		}
		if(entity.throttle > 1){
			//entity.motionY *= 0.2;
		}
		double x = 0;
		double y = -0.1;
		double z = 0;
		//if( entity.throttle >= 1)
		{
			double speed = -entity.rotationPitch * entity.thpower;
			x += MathHelper.sin(entity.rotationYawHead * 0.01745329252F) * sp * speed * 0.015;
			z -= MathHelper.cos(entity.rotationYawHead * 0.01745329252F) * sp * speed *0.015;
		}
		{
			double speed_rotez = entity.throte * 0.25;
			x += MathHelper.sin(entity.rotationYawHead * 0.01745329252F - 1.57F) * sp * speed_rotez;
			z -= MathHelper.cos(entity.rotationYawHead * 0.01745329252F - 1.57F) * sp * speed_rotez;
		}
		if(entity.moveangle == 3) {
			x += MathHelper.sin(entity.rotationYawHead * 0.01745329252F - 1.57F) * sp;
			z -= MathHelper.cos(entity.rotationYawHead * 0.01745329252F - 1.57F) * sp;
		}
		if(entity.moveangle == 4) {
			x += MathHelper.sin(entity.rotationYawHead * 0.01745329252F + 1.57F) * sp;
			z -= MathHelper.cos(entity.rotationYawHead * 0.01745329252F + 1.57F) * sp;
		}
		//if(entity.thpower > entity.thmax / 2)
		if(entity.thpower > 10  && entity.getHealth() > 0.0F)
		{
			
			//y += 0.025 * entity.thpower;
			if(entity.throttle_up == 1 && entity.thpower > 5) {
				y += 0.01 * entity.thpower * sp * 50;
				//System.out.println(String.format("%1$.2f", y));
			}else if(entity.throttle_up == -1) {
				y -=  0.01 * entity.thpower * sp * 30;
				//System.out.println(String.format("%1$.2f", y));
			}else {
				y += 0.1;
				//System.out.println(String.format("%1$.2f", y));
			}
		}else {
			if(entity.throttle_up == -1) {
				y -=  0.01 * entity.thpower * sp * 30;
				//System.out.println(String.format("%1$.2f", y));
			}else {
				y = y * ((10 - entity.thpower) * 0.1);
			}
		}
		if(entity.thpower > 5) 
		{
			entity.motionX = x;
			entity.motionZ = z;
			entity.motionY = y;
			entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
			//entity.motionY *= 0.1;
		}
		
		
		
		if(entity.getControllingPassenger() == null  && entity.getHealth() > 0.0F) 
		{
			if(entity.thpower > 1){
				entity.thpower = entity.thpower - brake;
			}
			if(entity.throttle > 1){
				--entity.throttle;
			}
			//if(!entity.onGround)entity.rotationPitch = entity.rotationPitch + 0.5F;
			//entity.setVelocity(look.x, MathHelper.sin(-entity.rotationPitch * 0.017453292F), look.z);
		}
		if(entity.getHealth() <= 0.0F && entity.rotationPitch < 60) {
			//if(!entity.onGround)entity.rotationPitch = entity.rotationPitch + 0.5F;
			//entity.setVelocity(look.x, MathHelper.sin(-entity.rotationPitch * 0.017453292F), look.z);
		}
		if(entity.throttle > 1){
			if(entity.ontick % 4 == 0 && sound != null)
			{
				//entity.world.playSound((EntityPlayer)null, entity.posX, entity.posY, entity.posZ, sound, SoundCategory.WEATHER, 3.0F, 1.0F);
				entity.playSound(sound, 3.0F, 1.0F);
			}
		}
//		setentityrote(entity);
		
		/*if(entity.onGround) {
			if(entity.rotationPitch > 1) {
				--entity.rotationPitch;
			}
			else if(entity.rotationPitch < -1) {
				++entity.rotationPitch;
			}
			if((entity.rotationPitch > 15 || entity.rotationPitch < -15) && entity.throttle > 1) {
				entity.attackEntityFrom(DamageSource.causeIndirectDamage(entity, entity), 1F);
			}
			
		}*/
		boolean rote_true = false;
		if(entity.moveangle != 3 && entity.moveangle != 4) {
			rote_true = true;
		}
		 if(entity.getControllingPassenger() != null && entity.getControllingPassenger() instanceof EntityGVCLivingBase) {
			 if(entity.getAIType() != 1 && entity.getAIType() != 2) {
				 rote_true = true;
			 }
		 }
		 EntityPlayer player = null;
		 if (entity.canBeSteered() && entity.getControllingPassenger() != null && entity.getHealth() > 0.0F)
			{
				if(entity.getControllingPassenger() instanceof EntityPlayer)
				{
					player = (EntityPlayer) entity.getControllingPassenger();
				}
				
			}
		 
		if(rote_true){
			if(entity.throte >= 0){
				//--entity.throte;
				if(player != null) {
					if (!(player.moveStrafing < 0F)) {
						entity.throte =  entity.throte - (float)(entity.turnspeed * entity.thpower * 0.01F);
					}
				}else {
					entity.throte =  entity.throte - (float)(entity.turnspeed * entity.thpower * 0.01F);
				}
			}
			if(entity.throte <= 0){
				//++entity.throte;
				if(player != null) {
					if (!(player.moveStrafing > 0F)) {
						entity.throte =  entity.throte + (float)(entity.turnspeed * entity.thpower * 0.01F);
					}
				}else {
					entity.throte =  entity.throte + (float)(entity.turnspeed * entity.thpower * 0.01F);
				}
			}
		}
		
		if( entity.throttle >= 1){
			if(entity.thpera < 360){
				entity.thpera = entity.thpera + (entity.throttle*2);
			}else{
				entity.thpera = 0;
			}
		}
		entity.moveangle = 0;
		
		
		//downwosh
		BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
		int genY = bp.getY();
		
		if(entity.throttle >= 1 && entity.posY < genY + 5){
					//for(int r = 0; r < 15; ++r)//半径
			int th = (int) (entity.thmax - entity.throttle);
			if(th < 5)th = 5;
			if(entity.ontick % th == 0){
		        		//for(int s = 0; s < 360; ++s) 
		        		{
			        		for(int c = 0; c < 360; ++c) {
			        			double rad = (2 * (float) Math.PI / 360);
				        		/*int xx = (int) (r * Math.sin(s * rad) * Math.cos(c * rad));
				        		int yy = (int) (r * Math.sin(s * rad) * Math.sin(c * rad));
				        		int zz = (int) (r * Math.cos(s * rad));*/
				        		double xx = (2 * Math.sin(c * rad));
				        		double yy = 0;
				        		double zz = (2 * Math.cos(c * rad));
				        		
				        		double xp = 0;
				        		double zp = 0;
				        		
				        		
				        		xp -= MathHelper.sin(entity.rotationYawHead * 0.01745329252F) * xx * 0.20 * (entity.throttle * 0.1);
				    			zp += MathHelper.cos(entity.rotationYawHead * 0.01745329252F) * xx * 0.20 * (entity.throttle * 0.1);
				    			
				    			xp -= MathHelper.sin(entity.rotationYawHead * 0.01745329252F + 1.57F) * zz * 0.20 * (entity.throttle * 0.1);
				    			zp += MathHelper.cos(entity.rotationYawHead * 0.01745329252F + 1.57F) * zz * 0.20 * (entity.throttle * 0.1);
				        		
				        		
				        		entity.world.spawnParticle(EnumParticleTypes.CLOUD, 
				        				entity.posX + xx, genY, entity.posZ + zz, 
				        				xp, 0.0D, zp, new int[0]);
			        		}
		        		}
					}
				}
	}
}
