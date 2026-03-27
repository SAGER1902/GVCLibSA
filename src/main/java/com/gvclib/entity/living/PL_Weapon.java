package gvclib.entity.living;

import java.util.List;

import gvclib.entity.EntityBBase;
import gvclib.entity.EntityB_Bullet;
import gvclib.entity.EntityB_BulletAA;
import gvclib.entity.EntityB_BulletFire;
import gvclib.entity.EntityB_GrenadeB;
import gvclib.entity.EntityB_Missile;
import gvclib.entity.EntityB_Shell;
import gvclib.entity.EntityTNTBase;
import gvclib.entity.EntityT_Flash;
import gvclib.entity.EntityT_Grenade;
import gvclib.entity.EntityT_TNT;
import gvclib.entity.Entity_Flare;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PL_Weapon {

	public static void AttacktaskGunner(EntityGVCLivingBase entity, int id, EntityPlayer player, double range,
    		String model, String tex,String modelf, String texf,int ftime, SoundEvent sound
    		, float f, double w, double h, double z, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid){
    		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						{
							if (entity1 instanceof IMob && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
							{
								double ddy = Math.abs(entity1.posY - entity.posY);
								double dyp = entity.posY + maxy;
								double dym = entity.posY - miny;
								double d5 = entity1.posX - entity.posX;
								double d7 = entity1.posZ - entity.posZ;
								double d6 = entity1.posY - entity.posY;
								double d1 = entity.posY - (entity1.posY);
								double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
								rote
										= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
								if (dyp > entity1.posY && dym < entity1.posY) {
									Weapon(entity, id, player, model, tex, modelf, texf, ftime, sound, 
											f, w, h, z, px, py, pz, lock, rote, maxy, miny,
											dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid);
									break;
								}
								
							}
						}
						
					}
				}
			}
    }
	
	public static void Weapon(EntityGVCLivingBase entity, int id, EntityPlayer player, 
			String model, String tex, String modelf, String texf,int ftime,
			SoundEvent sound,
			float f, double w, double h, double z, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny,
    		int power, float speed, float bure, float ex, boolean extrue,int kazu, double gra, int maxtime, int dameid)
	{
		WeaponAttack(entity, id,  player, 
				 model,  tex,  modelf,  texf, ftime,
				 sound,
				 f,  w,  h,  z, 0,  px,  py,  pz,  lock,  rote,  maxy,  miny,
	    		 power,  speed,  bure,  ex,  extrue, kazu,  gra,  maxtime,  dameid);
	}
	
	public static void WeaponAttack(EntityGVCLivingBase entity, int id, EntityPlayer player, 
			String model, String tex, String modelf, String texf,int ftime,
			SoundEvent sound,
			float f, double w, double h, double z, double yoffset, double px, double py, double pz,  Vec3d lock, float rote, double maxy, double miny,
    		int power, float speed, float bure, float ex, boolean extrue,int kazu, double gra, int maxtime, int dameid)
	{
		int ra = entity.world.rand.nextInt(10) + 1;
    	float val = ra * 0.02F;
		entity.world.playSound((EntityPlayer) null, entity.posX, entity.posY,
				entity.posZ, sound, SoundCategory.NEUTRAL, 5.0F, 0.9F + val);
    	double xx11 = 0;
		double zz11 = 0;
		xx11 -= MathHelper.sin(rote * 0.01745329252F) * z;
		zz11 += MathHelper.cos(rote * 0.01745329252F) * z;
		xx11 -= MathHelper.sin(rote * 0.01745329252F + f) * w;
		zz11 += MathHelper.cos(rote * 0.01745329252F + f) * w;
		double kaku = 0;
		//kaku += lock.y * 2;
		kaku = MathHelper.sqrt(z* z + w*w) * Math.tan(Math.toRadians(-entity.rotationPitch)) * 0.4D;
		Vec3d locked = player.getLookVec();
		if(id < 10){
			for (int ka = 0; ka < kazu; ++ka) {
				EntityBBase var3;
				{
					if (id == 1) {
						var3 = new EntityB_BulletAA(player.world, player);
					} else if (id == 2) {
						var3 = new EntityB_GrenadeB(player.world, player);
					} else if (id == 3) {
						var3 = new EntityB_Shell(player.world, player);
					} else if (id == 4) {
						var3 = new EntityB_Missile(player.world, player);
						EntityB_Missile mi = (EntityB_Missile) var3;
						mi.speedd = speed;
					} else if (id == 5) {
						var3 = new EntityB_BulletAA(player.world, player);
						var3.bulletDameID = 1;
					} else if (id == 6) {
						var3 = new EntityB_BulletFire(player.world, player);
						var3.timemax = 10;
					} else if (id == 7) {
						var3 = new EntityB_BulletAA(player.world, entity);
						EntityB_BulletAA aa = (EntityB_BulletAA) var3;
						aa.exnear = true;
					} else if (id == 8) {
						var3 = new EntityB_Missile(player.world, entity);
						EntityB_Missile mi = (EntityB_Missile) var3;
						mi.speedd = speed;
						mi.autoaim = false;
					} else if (id == 9) {
						var3 = new EntityT_Grenade(player.world, player);
					} else {
						var3 = new EntityB_Bullet(player.world, player);
					}
					//载具武器-AI
					if(entity.tgfx!=null){
					var3.setTexF(entity.tgfx);
					}else{
						if(var3 instanceof EntityB_Bullet){
						var3.setTexF("SABulletTrail");
						}else if(var3 instanceof EntityB_Shell){
						var3.setTexF("SAAPTrail");
						}else if(var3 instanceof EntityB_BulletFire){
						var3.setTexF("FlamethrowerTrail");
						}else if(var3 instanceof EntityB_Missile){
						var3.setTexF("SAMissileTrail");
						}else if(var3 instanceof EntityB_BulletAA){
							if(ex<2F){
								var3.setTexF("SAAATrail");
							}else{
								var3.setTexF("LSAAATrail");
							}
						}
					}
					
					var3.Bdamege = power;
					var3.gra = gra;
					var3.friend = entity;
					var3.exlevel = ex;
					var3.ex = extrue;
					var3.muteki = true;
					var3.mitarget = entity.mitarget;
					var3.setModel(model);
					var3.setTex(tex);
					var3.timemax = maxtime;
					//var3.setModelF("gvclib:textures/entity/mfire.mqo");
					//var3.setTexF("gvclib:textures/entity/mfire.png");
					var3.bulletDameID = dameid;
					var3.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11, player.rotationYaw,
							player.rotationPitch);
					var3.setThrowableHeading(lock.x, lock.y + 0.03 + yoffset, lock.z, speed, bure);
					if (!player.world.isRemote) {
						player.world.spawnEntity(var3);
					}
				}
				{
					EntityT_Flash flash = new EntityT_Flash(player.world, player);
					flash.gra = 0.03;
					flash.timemax = ftime;
					flash.setModel(modelf);
					flash.setTex(texf);
					flash.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11, player.rotationYaw,
							player.rotationPitch);
					flash.setThrowableHeading(lock.x, lock.y + 0.03 + yoffset, lock.z, 0.1F, 0);
					if (!player.world.isRemote) {
						player.world.spawnEntity(flash);
					}
				}
			}
		}else if(id >= 10 && id < 20){
			EntityTNTBase var3;
			if(id == 0){
				var3 = new EntityT_TNT(player.world, player);
			}else{
				var3 = new EntityT_TNT(player.world, player);
			}
			var3.Bdamege = power;
			var3.friend = entity;
			var3.exlevel = ex;
			var3.ex = extrue;
			var3.muteki = true;
			var3.exinground = true;
		//	var3.exnear = true;
			var3.extime = 200;
			var3.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11,
					player.rotationYaw, player.rotationPitch);
			var3.setThrowableHeading(lock.x, locked.y + 0.03, lock.z, speed, bure);
			if (!player.world.isRemote) {
				player.world.spawnEntity(var3);
			}
		}
		else if(id == 21){
			for (int ka = 0; ka < kazu; ++ka) {
				int xx = player.world.rand.nextInt(6);
				int zz = player.world.rand.nextInt(6);
				Entity_Flare var8 = new Entity_Flare(player.world);
				var8.motionY = speed;
				var8.setLocationAndAngles(px - 3 + xx, py, pz - 3 + zz,
						var8.rotationYawHead, var8.rotationPitch);
				if (!player.world.isRemote) {
					player.world.spawnEntity(var8);
				}
			}
		}
		else if(id == 51){
			for (int ka = 0; ka < kazu; ++ka) {
				int xx = player.world.rand.nextInt(6);
				int zz = player.world.rand.nextInt(6);
				EntityTippedArrow var3 = new EntityTippedArrow(player.world);
				var3.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11,
						player.rotationYaw, player.rotationPitch);
				var3.shoot(lock.x, locked.y + 0.03, lock.z, speed, bure);
				if (!player.world.isRemote) {
					player.world.spawnEntity(var3);
				}
			}
		}
		else if(id == 30){
			double x2 = 0;
			double z2 = 0;
			x2 -= MathHelper.sin(entity.rotation * 0.01745329252F) * 2.0;
			z2 += MathHelper.cos(entity.rotation * 0.01745329252F) * 2.0;
			AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(
					player.posX + x2, player.posY, player.posZ + z2, 
					player.posX + x2, player.posY, player.posZ + z2)
	        		.expand(3, 5, 3);
			List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,axisalignedbb2);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith() && entity1 instanceof EntityLivingBase && entity1 != null) {
						if(entity1 != player && entity1 != player.getControllingPassenger()){
							entity1.attackEntityFrom(DamageSource.causeMobDamage(player), power);
							
						}
					}
				}
			}
		}
	}

	public static void EntityLock(EntityGVCLivingBase entity, EntityPlayer player, boolean aam) {
		{
			Vec3d looken = player.getLookVec();
			Vec3d locken = player.getLookVec();
			float d = 100;
			boolean lock = false;
			//if (entity.ontick % 2 == 0)
			{
				//List llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb);
				List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
						player.getEntityBoundingBox()
								.expand(locken.x * d, locken.y * d, locken.z * d)
								.grow(0.25D));
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {

						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith()) {
							boolean flag = entity.getEntitySenses().canSee(entity1);
							if (entity1 instanceof IMob && entity1 != null && entity1 != player
									&& entity1 != entity && flag) {
								BlockPos bp = entity1.world.getHeight(new BlockPos(entity1.posX, entity1.posY, entity1.posZ));
								if(aam) {
									if(entity1.posY > bp.getY() + 5){
										entity.mitarget = (EntityLivingBase) entity1;
										lock = true;
										break;
									}
								}else {
									 if(entity1.posY < bp.getY() + 5 || entity1.onGround){
										entity.mitarget = (EntityLivingBase) entity1;
										lock = true;
										break;
									}
								}
							}
						}
					}
				}
			}
			if(lock && entity.mitarget !=null){
				if (entity.ontick % 3 == 0) {
					//NBTTagCompound nbt = entity.mitarget.getEntityData();
					//nbt.setInteger("LockOnTime", 200);
					entity.mitarget.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 10));
				}
				if (entity.ontick % 10 == 0) {
		//			entity.playSound(GVCSoundEvent.sound_lock, 0.4F, 1.0F);
				}
			}
		}
	}
}
