package gvclib.entity.living;

import gvclib.entity.EntityB_Bullet;
import gvclib.entity.EntityB_BulletAA;
import gvclib.entity.EntityB_GrenadeB;
import gvclib.entity.EntityB_Shell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.MathHelper;

public class AI_Damage {

	public static float newTankArmor(EntityVehicleBase entity, Entity entityin, float part2, boolean turret, float rotehead){
				double ix = 0;
				double iy = 0;
				double iz = 0;
				float f11 = rotehead * (2 * (float) Math.PI / 360);
				
				//float rote = rotehead %360;
				float rote = rotehead;
				
				{
					double ex = entityin.posX;
					double ey = entityin.posY;
					double ez = entityin.posZ;
					ix -= MathHelper.sin(f11) * (entity.width / 2);
					iz += MathHelper.cos(f11) * (entity.width / 2);
					double px = entity.posX + ix;
					double pz = entity.posZ + iz;
					double px1 = entity.posX - ix;
					double pz1 = entity.posZ - iz;
					
					double d5 = entityin.posX - entity.posX;
					double d7 = entityin.posZ - entity.posZ;
					
					float rote_base  = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					
					if(rote_base > rote - 45 && rote_base < rote + 45) {
						//da = part2 * 0;
						if(turret) {
							if(part2 < entity.damage_turret_front) {
								if(entityin instanceof EntityB_Shell)
								{
									part2 = part2*0.5F;
								}else{
									if(entity.world.rand.nextInt(10)>6){
										part2 = part2*0.1F;
									}else{
										part2 = 0;
									}
								}
							}
						}else {
							if(part2 < entity.damage_front) {
								if(entityin instanceof EntityB_Shell)
								{
									part2 = part2*0.5F;
								}else{
									if(entity.world.rand.nextInt(10)>6){
										part2 = part2*0.1F;
									}else{
										part2 = 0;
									}
								}
							}
						}
					}else if(rote_base > rote - 135 && rote_base < rote + 135) {
						if(turret) {
							if(part2 < entity.damage_turret_side) {
								if(entityin instanceof EntityB_Shell)
								{
									part2 = part2*0.5F;
								}else{
									if(entity.world.rand.nextInt(10)>6){
										part2 = part2*0.1F;
									}else{
										part2 = 0;
									}
								}
							}
						}else {
							if(part2 < entity.damage_side) {
								if(entityin instanceof EntityB_Shell)
								{
									part2 = part2*0.5F;
								}else{
									if(entity.world.rand.nextInt(10)>6){
										part2 = part2*0.1F;
									}else{
										part2 = 0;
									}
								}
							}
						}
					}else {
						if(turret) {
							if(part2 < entity.damage_turret_rear) {
								if(entityin instanceof EntityB_Shell)
								{
									part2 = part2*0.5F;
								}else{
									if(entity.world.rand.nextInt(10)>6){
										part2 = part2*0.1F;
									}else{
										part2 = 0;
									}
								}
							}
						}else {
							if(part2 < entity.damage_rear) {
								if(entityin instanceof EntityB_Shell)
								{
									part2 = part2*0.5F;
								}else{
									if(entity.world.rand.nextInt(10)>6){
										part2 = part2*0.1F;
									}else{
										part2 = 0;
									}
								}
							}
						}
					}
					
				}
				return part2;
			}

	public static float newAntiBullet(EntityGVCLivingBase entity, Entity entityIn, float part2, float level, float level1,  float level2, float level3){
		float ab = 1;
		if(entityIn instanceof EntityArrow)
		{
			ab = level;
        }
		if(entityIn instanceof EntityB_Bullet)
		{
			EntityB_Bullet bullet = (EntityB_Bullet) entityIn;
			if(bullet.bulletDameID == 1 || bullet.bulletDameID == 3) {
				ab = level1;
			}else {
				ab = level;
			}
        }
    	if(entityIn instanceof EntityB_BulletAA)
        {
    		ab = level1;
        }
    	if(entityIn instanceof EntityB_GrenadeB)
        {
    		ab = level2;
        }
    	if(entityIn instanceof EntityB_Shell)
        {
    		ab = level3;
        }
    	return ab;
	}
	
	/*public static float TankArmor(EntityGVCLivingBase entity, Entity entityin, float part2, float min, float nom, float max, float rotehead){
		float da = 1;
		
		double ix = 0;
		double iy = 0;
		double iz = 0;
		float f11 = rotehead * (2 * (float) Math.PI / 360);
		{
			double ex = entity.posX;
			double ey = entity.posY;
			double ez = entity.posZ;
			ix -= MathHelper.sin(f11) * 2;
			iz += MathHelper.cos(f11) * 2;
			double px = entity.posX + ix;
			double pz = entity.posZ + iz;
			double px1 = entity.posX - ix;
			double pz1 = entity.posZ - iz;
			if(rotehead >= -22.5 && rotehead <= 22.5){
				if(ez >= pz){
					da = da * min;
				}
				else if(ez <= pz1){
					da = da * max;
				}
				else{
					da = da * nom;
				}
			}
			if(rotehead >= 22.5 && rotehead <= 67.5){
				if(ez >= pz && ex <= px){
					da = da * min;
				}
				else if(ez <= pz1 && ex >= px1){
					da = da * max;
				}
				else{
					da = da * nom;
				}
			}
			if(rotehead >= 67.5 && rotehead <= 112.5){
				if(ex <= px){
					da = da * min;
				}
				else if(ex >= px1){
					da = da * max;
				}
				else{
					da = da * nom;
				}
			}
			if(rotehead >= 112.5 && rotehead <= 157.5){
				if(ez <= pz && ex <= px){
					da = da * min;
				}
				else if(ez >= pz1 && ex >= px1){
					da = da * max;
				}
				else{
					da = da * nom;
				}
			}
			
			if(rotehead >= 157.5 && rotehead <= -157.5){
				if(ez <= pz){
					da = da * min;
				}
				else if(ez >= pz1){
					da = da * max;
				}
				else{
					da = da * nom;
				}
			}
			if(rotehead >= -157.5 && rotehead <= -112.5){
				if(ez <= pz && ex >= px){
					da = da * min;
				}
				else if(ez >= pz1 && ex <= px1){
					da = da * max;
				}
				else{
					da = da * nom;
				}
			}
			if(rotehead >= -112.5 && rotehead <= -67.5){
				if(ex >= px){
					da = da * min;
				}
				else if(ex <= px1){
					da = da * max;
				}
				else{
					da = da * nom;
				}
			}
			if(rotehead >= -67.5 && rotehead <= -22.5){
				if(ez >= pz && ex >= px){
					da = da * min;
				}
				else if(ez <= pz1 && ex <= px1){
					da = da * max;
				}
				else{
					da = da * nom;
				}
			}
		}
		//da = da * this.AntiBullet(entity, part2);
		if(da < 1){
		//	entity.playSound("random.anvil_land", 5F, 2F);
		}else if(da == 1){
	//		this.playSound("random.anvil_land", 5F, 1F);
		}else
		if(da > 1){
		//	entity.playSound("random.anvil_land", 5F, 1F);
		}
		return da;
		
	}
	public static float AntiBullet(EntityGVCLivingBase entity, Entity entityIn, float part2, int level){
		float ab = 1;
		if(entityIn instanceof EntityB_Bullet)
		{
			if(level >= 1){
				ab =  0;
			}else{
				ab =  0.25F;
			}
        }
    	if(entityIn instanceof EntityB_BulletAA)
        {
    		if(level >= 3){
    			ab =  0;
			}
    		else if(level >= 2){
    			ab =  0.25F;
    		}else if(level >= 1){
    			ab =  0.5F;
			}else{
				ab =  1F;
			}
        }
    	if(entityIn instanceof EntityB_Shell)
        {
    		if(level >= 5){
    			ab =  0;
			}
    		else if(level >= 3){
    			ab =  0.25F;
			}
    		else{
    			ab =  1F;
			}
        }
    	if(entityIn instanceof EntityB_GrenadeB)
        {
    		return 0.5F;
        }
    	return ab;
	}*/
}
