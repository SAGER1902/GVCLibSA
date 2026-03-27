package gvclib.entity.living;

import java.util.List;

import gvclib.entity.EntityBBase;
import gvclib.entity.EntityB_Bullet;
import gvclib.entity.EntityB_BulletAA;
import gvclib.entity.EntityB_BulletFire;
import gvclib.entity.EntityB_GrenadeB;
import gvclib.entity.EntityB_Missile;
import gvclib.entity.EntityB_Shell;
import gvclib.entity.EntityT_Flash;
import gvclib.entity.EntityT_Grenade;
import gvclib.entity.Entity_Flare;
import gvclib.event.GVCSoundEvent;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
//import gvcr2.entity.pmc.EntityGVCR2_PMC_S;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PL_Weapon_new {
	
	public EntityPlayer player;
	public EntityGVCLivingBase vehicle;
	
	public int id;
	public String model;
	public String tex;
	public String modelf;
	public String texf;
	
	public String we_tgfx;
	
	public int ftime;
	public SoundEvent sound;
	public float sound_val = 5.0F;
	public float f;
	public double w;
	public double h;
	public double z;
	public double xoffset;
	public double yoffset;
	public double px;
	public double py;
	public double pz;
	public double bx;
	public double by;
	public double bz;
	public Vec3d lock;
	public float rote;
	
	public boolean lock_player = true;
	public boolean lock_pitch_vehicle= false;
	
	public double maxy;
	public double miny;
	
	public int power;
	public float speed;
	public float bure;
	public float ex;
	public boolean exfire;
	public boolean exsmoke;
	public boolean exflash;
	public boolean extrue;
	
	public int kazu;
	public double gra;
	public int maxtime;
	public int dameid;
	
	
	public PL_Weapon_new(EntityGVCLivingBase v, EntityPlayer p) {
		this.player  = p;
		this.vehicle = v;
	}
	
	public static void AttacktaskAIGunner(EntityGVCLivingBase entity, int rotationid, EntityPlayer player, double range, double maxy, double miny, PL_Weapon_new we){
		
		Entity target = null;
		Entity[] targetlist = new Entity[1024];
		int targetplus = 0;
		
    		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						{
							if (entity1 instanceof IMob && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
							{
								boolean flag = entity.getEntitySenses().canSee(entity1);
								double dyp = entity.posY + maxy;
								double dym = entity.posY - miny;
								if (dyp > entity1.posY && dym < entity1.posY && flag) {
									targetlist[targetplus] = entity1;
									++targetplus;
								}
							}
					}
				}
			}
			
				for(int xs = 0; xs < targetlist.length; ++xs) {
					if(targetlist[xs] != null) {
						
						{
							if(target != null) {
								double d5 = targetlist[xs].posX - entity.posX;
								double d7 = targetlist[xs].posZ - entity.posZ;
								double ddx = Math.abs(d5);
								double ddz = Math.abs(d7);
								double xz = Math.sqrt(ddx * ddx + ddz * ddz);
								
								double d51 = target.posX - entity.posX;
								double d71 = target.posZ - entity.posZ;
								double ddx1 = Math.abs(d51);
								double ddz1 = Math.abs(d71);
								double xz1 = Math.sqrt(ddx1 * ddx1 + ddz1 * ddz1);
								if(xz < xz1) {
									target = targetlist[xs];
								}
							}else {
								target = targetlist[xs];
							}
						}
					}
				}
				
				
					if(target != null) {
						double ddy = Math.abs(target.posY - entity.posY);
						
						double xx11 = 0;
						double zz11 = 0;
						xx11 -= MathHelper.sin(we.rote * 0.01745329252F) * we.z;
						zz11 += MathHelper.cos(we.rote * 0.01745329252F) * we.z;
						xx11 -= MathHelper.sin(we.rote * 0.01745329252F + we.f) * we.w;
						zz11 += MathHelper.cos(we.rote * 0.01745329252F + we.f) * we.w;
						
						double d5 = target.posX - (we.px + xx11);
						double d7 = target.posZ - (we.pz + zz11);
						double d1 = (we.py + we.h) - (target.posY);
						double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
						float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
						float roteYaw = 0;
						float rotePitch = 0;
						
						roteYaw = -((float) Math.atan2(d5, d7)) * 180.0F/ (float) Math.PI;
						rotePitch = -f11 + 0;
						{
							if(rotationid == 1) {
								entity.rotation_1 = roteYaw;
								entity.rotationp_1 = rotePitch;
							}
							else if(rotationid == 2) {
								entity.rotation_2 = roteYaw;
								entity.rotationp_2 = rotePitch;
							}
							else if(rotationid == 4) {
								entity.rotation_4 = roteYaw;
								entity.rotationp_4 = rotePitch;
							}
							else {
								entity.rotation_3 = roteYaw;
								entity.rotationp_3 = rotePitch;
							}
							we.lock = Vec3d.fromPitchYaw(rotePitch, roteYaw);
							we.lock_player = true;
							we.WeaponAttack();
						}
						
					}
				}
			
    }
	
	public void WeaponAttack()
	{
		int ra = vehicle.world.rand.nextInt(10) + 1;
    	float val = ra * 0.02F;
    	
    	
		vehicle.world.playSound((EntityPlayer) null, vehicle.posX, vehicle.posY,
				vehicle.posZ, sound, SoundCategory.NEUTRAL, sound_val, 0.9F + val);
		
    	/*
    	vehicle.world.playSound((EntityPlayer) null, vehicle.posX, vehicle.posY,
				vehicle.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 5.0F, 0.9F + val);
		*/
    	
    	double xx11 = 0;
		double zz11 = 0;
		xx11 -= MathHelper.sin(rote * 0.01745329252F) * z;
		zz11 += MathHelper.cos(rote * 0.01745329252F) * z;
		xx11 -= MathHelper.sin(rote * 0.01745329252F + f) * w;
		zz11 += MathHelper.cos(rote * 0.01745329252F + f) * w;
		double offx = 0;
		double offz = 0;
		offx -= MathHelper.sin(rote * 0.01745329252F + 1.57F) * xoffset;
		offz += MathHelper.cos(rote * 0.01745329252F + 1.57F) * xoffset;
		double kaku = 0;
		kaku = MathHelper.sqrt((z - bz)* (z - bz) + (w - bx)*(w - bx)) * MathHelper.sin(-vehicle.rotationPitch  * (1 * (float) Math.PI / 180));
		
		Vec3d locked = player.getLookVec();
		if(id < 13){
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
						var3 = new EntityB_BulletAA(player.world, player);
						EntityB_BulletAA aa = (EntityB_BulletAA) var3;
						aa.exnear = true;
					} else if (id == 8) {
						var3 = new EntityB_Missile(player.world, player);
						EntityB_Missile mi = (EntityB_Missile) var3;
						mi.speedd = speed;
						mi.autoaim = false;
					} else if (id == 9) {
						var3 = new EntityT_Grenade(player.world, player);
					} else if (id == 10) {
						var3 = new EntityT_Grenade(player.world, player);
					} else if (id == 11) {
						var3 = new EntityB_GrenadeB(player.world, player);
					} else if (id == 12) {
						var3 = new EntityB_Shell(player.world, player);
					} else {
						var3 = new EntityB_Bullet(player.world, player);
					}
					
					//载具武器-玩家
					if(this.we_tgfx!=null){
					var3.setTexF(this.we_tgfx);
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
					var3.friend = vehicle;
					var3.exlevel = ex;
					var3.exfire = exfire;
					var3.exsmoke = exsmoke;
					var3.exflash = exflash;
					var3.ex = extrue;
					var3.muteki = true;
					var3.mitarget = vehicle.mitarget;
					var3.setModel(model);
					var3.setTex(tex);
					var3.timemax = maxtime;
					//var3.setModelF(modelf);
					//var3.setTexF("gvclib:textures/entity/mfire.png");
					var3.bulletDameID = dameid;
					var3.setLocationAndAngles(px + xx11  - offx, py + h + kaku, pz + zz11  - offz, player.rotationYaw,
							player.rotationPitch);
					if(lock_player) {
						var3.setThrowableHeading(lock.x, lock.y + 0.03 + yoffset, lock.z, speed, bure);
					}else {
						if(lock_pitch_vehicle) {
							var3.setHeadingFromThrower(player, vehicle.rotationPitch + 0.03F, vehicle.rotationYaw, 0.0F, speed, bure);//vehicle.rotationPitchだとなぜか反映されない
						}else {
							var3.setHeadingFromThrower(player, player.rotationPitch + 0.03F, vehicle.rotationYaw, 0.0F, speed, bure);//vehicle.rotationPitchだとなぜか反映されない
						}
					}
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
					flash.setThrowableHeading(lock.x, lock.y + 0.03 + yoffset, lock.z, 0.3F, 0);
					if (!player.world.isRemote) {
						player.world.spawnEntity(flash);
					}
				}
			}
		}
		else if(id == 12){
			EntityBBase var3;
			{
				var3 = new EntityB_GrenadeB(player.world, player);
				var3.Bdamege = power;
				var3.friend = vehicle;
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
				var3.Bdamege = power;
				var3.gra = gra;
				var3.friend = vehicle;
				var3.exlevel = ex;
				var3.ex = extrue;
				var3.muteki = true;
				var3.mitarget = vehicle.mitarget;
				var3.setModel(model);
				var3.setTex(tex);
				var3.timemax = maxtime;
				//var3.setModelF(modelf);
				//var3.setTexF("gvclib:textures/entity/mfire.png");
				var3.bulletDameID = dameid;
				var3.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11, player.rotationYaw,
						player.rotationPitch);
				var3.setThrowableHeading(lock.x, lock.y + 0.03 + yoffset, lock.z, speed, bure);
				//曲射榴弹
				var3.setTexF("SAAPTrail");
				
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
				flash.setThrowableHeading(lock.x, lock.y + 0.03 + yoffset, lock.z, 0.3F, 0);
				if (!player.world.isRemote) {
					player.world.spawnEntity(flash);
				}
			}
		}else if(id == 15){
		for (int ka = 0; ka < kazu; ++ka) {
			EntityBBase var3;
			{
				var3 = new EntityB_Shell(player.world, player);
				
				var3.Bdamege = power;
				var3.gra = 0.0F;
				var3.friend = vehicle;
				var3.exlevel = ex;
				var3.exfire = exfire;
				var3.exsmoke = exsmoke;
				var3.exflash = exflash;
				var3.ex = extrue;
				var3.muteki = true;
				var3.mitarget = vehicle.mitarget;
				var3.setModel(model);
				var3.setTex(tex);
				var3.timemax = maxtime;
				var3.bulletDameID = dameid;
				var3.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11, player.rotationYaw,
						player.rotationPitch);
				//曲射穿甲弹
				var3.setTexF("SAAPTrail");
				{
					Vec3d lock = player.getLookVec();
					double ix = 0;
					double iy = 0;
					double iz = 0;
					for(int x = 0; x < 120; ++x) {
						boolean geten = false;
						{
							ix = (player.posX + lock.x * x);
							iy = (player.posY + 1.5 + lock.y * x);
							iz =  (player.posZ + lock.z * x);
							if(ix != 0 && iz != 0 && iy != 0) {
								if (player.world
										.getBlockState(new BlockPos(ix,iy,iz)).getBlock() != Blocks.AIR) {
									break;
								}
	    						EntityLivingBase target = null;
	    						/*{
	    							int han = 1;
	    			            	AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double)(ix-han), (double)(iy-han), (double)(iz-han), 
	    			            			(double)(ix + han), (double)(iy + han), (double)(iz+ han)))
	    			                		.grow(1);
	    			                List llist2 = player.world.getEntitiesWithinAABBExcludingEntity(player, axisalignedbb2);
	    			                if(llist2!=null){
	    			                    for (int lj = 0; lj < llist2.size(); lj++) {
	    			                    	
	    			                    	Entity entity1 = (Entity)llist2.get(lj);
	    			                    	if (entity1.canBeCollidedWith())
	    			                        {
	    			                    		if (entity1 instanceof EntityLivingBase && entity1 != null && entity1 != vehicle && entity1 != player)
	    			                            {
	    			                    			ix = entity1.posX;
	    			                    			iy = entity1.posY;
	    			                    			iz = entity1.posZ;
	    			                    			geten = true;
	    			                    			break;
	    			                            }
	    			                        }
	    			                    }
	    			                }
	    						}*/
	    					}
						}
						if(geten)break;
					}
					System.out.println(String.format("%1$.2f", ix));
					System.out.println(String.format("%1$.2f", iy));
					System.out.println(String.format("%1$.2f", iz));
					player.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, 
							ix,  iy, iz, 0, 0, 0);
					double d5 = ix - px;
					double d7 = iz - pz;
			        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					double var4 = ix - px - xx11;
					double var6 = iy + 0.200000023841858D - var3.posY - (h / 2);
					double var8 = iz - pz - zz11;
					float var10 = MathHelper.sqrt(var4 * var4 + var8 * var8) * 0.01F;
					
					double xrange = Math.abs(ix) -Math.abs(px);
					double zrange = Math.abs(iz) -Math.abs(pz);
					double range = (double)MathHelper.sqrt(xrange * xrange + zrange * zrange) * 1;
					
					double rangespeed = (double)MathHelper.sqrt((0.0205 * range) / (2 * Math.sin(60) * Math.cos(60)));
					float yawaa = -((float) Math.atan2(var4, var8)) * 180.0F / (float) Math.PI;
					float yaw = yawaa * (2 * (float) Math.PI / 360);
					{
						float rotationPitchIn = -60;
						float rotationYawIn = yawaa;
						float f = -MathHelper.sin(rotationYawIn * 0.017453292F)
								* MathHelper.cos(rotationPitchIn * 0.017453292F);
						float f1 = -MathHelper.sin((rotationPitchIn + 0) * 0.017453292F);
						float f2 = MathHelper.cos(rotationYawIn * 0.017453292F)
								* MathHelper.cos(rotationPitchIn * 0.017453292F);
						 var3.setThrowableHeading((double)f, (double)f1, (double)f2, (float)rangespeed, bure);
					}
				}
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
				flash.setThrowableHeading(lock.x, lock.y + 0.03 + yoffset, lock.z, 0.3F, 0);
				if (!player.world.isRemote) {
					player.world.spawnEntity(flash);
				}
			}
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
			x2 -= MathHelper.sin(vehicle.rotation * 0.01745329252F) * 2.0;
			z2 += MathHelper.cos(vehicle.rotation * 0.01745329252F) * 2.0;
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
	
	
	public float spg_target_x;
	public float spg_target_y;
	public float spg_target_z;
	
	public void WeaponAttackSPG()
	{
		int ra = vehicle.world.rand.nextInt(10) + 1;
    	float val = ra * 0.02F;
		vehicle.world.playSound((EntityPlayer) null, vehicle.posX, vehicle.posY,
				vehicle.posZ, sound, SoundCategory.NEUTRAL, 5.0F, 0.9F + val);
    	double xx11 = 0;
		double zz11 = 0;
		xx11 -= MathHelper.sin(rote * 0.01745329252F) * z;
		zz11 += MathHelper.cos(rote * 0.01745329252F) * z;
		xx11 -= MathHelper.sin(rote * 0.01745329252F + f) * w;
		zz11 += MathHelper.cos(rote * 0.01745329252F + f) * w;
		double kaku = 0;
		//kaku += lock.y * 2;
		kaku = MathHelper.sqrt((z - bz)* (z - bz) + (w - bx)*(w - bx)) * Math.tan(Math.toRadians(-vehicle.rotationPitch)) * 0.4D;
		
		Vec3d locked = player.getLookVec();
		EntityBBase var3;
		if(id == 14) {
			var3 = new EntityB_Shell(player.world, player);
		}else {
			var3 = new EntityB_GrenadeB(player.world, player);
		}
		
		var3.Bdamege = power;
		var3.gra = 0.0F;// 0.025
		//var3.friend = entity;
		var3.friend = vehicle;
		var3.ex = extrue;
		var3.exlevel = ex;
		var3.setModel(model);
		var3.setTex(tex);
		var3.timemax = 8000;
		var3.bulletDameID = dameid;
		var3.muteki = true;
		var3.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11, player.rotationYaw,
				player.rotationPitch);
		var3.spg_fly_sound = true;
		//自行火炮炮弹
		var3.setTexF("SAAPTrail");
		//Vec3d elock = target.getLookVec();
		double d5 = spg_target_x - px;
		double d7 = spg_target_z - pz;
        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
		double var4 = spg_target_x - px - xx11;
		double var6 = spg_target_y + 0.200000023841858D - var3.posY - (h / 2);
		double var8 = spg_target_z - pz - zz11;
		float var10 = MathHelper.sqrt(var4 * var4 + var8 * var8) * 0.01F;
		
		double xrange = Math.abs(spg_target_x) -Math.abs(px);
		double zrange = Math.abs(spg_target_z) -Math.abs(pz);
		double range = (double)MathHelper.sqrt(xrange * xrange + zrange * zrange) * 1;
		
		double rangespeed = (double)MathHelper.sqrt((0.0205 * range) / (2 * Math.sin(60) * Math.cos(60)));
		float yawaa = -((float) Math.atan2(var4, var8)) * 180.0F / (float) Math.PI;
		float yaw = yawaa * (2 * (float) Math.PI / 360);
		{
			//var3.setThrowableHeading(var4, (var6 + (double) var10) * 1 + kaku, var8, speed, bure);
			//var3.setHeadingFromThrower(player, -60, yaw, 0.0F,(float)rangespeed, bure);
			float rotationPitchIn = -60;
			float rotationYawIn = yawaa;
			float f = -MathHelper.sin(rotationYawIn * 0.017453292F)
					* MathHelper.cos(rotationPitchIn * 0.017453292F);
			float f1 = -MathHelper.sin((rotationPitchIn + 0) * 0.017453292F);
			float f2 = MathHelper.cos(rotationYawIn * 0.017453292F)
					* MathHelper.cos(rotationPitchIn * 0.017453292F);
			 var3.setThrowableHeading((double)f, (double)f1, (double)f2, (float)rangespeed, bure);
			//var3.setThrowableHeading(var4, f1, var8, (float)rangespeed, bure);
		}
		
		if (!player.world.isRemote) {
			player.world.spawnEntity(var3);
		}
		
		{
			EntityT_Flash flash = new EntityT_Flash(player.world, player);
			flash.gra = 0.03;
			flash.timemax = ftime;
			flash.setModel(modelf);
			flash.setTex(texf);
			flash.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11, player.rotationYaw,
					player.rotationPitch);
			flash.setThrowableHeading(lock.x, lock.y + 0.03 + yoffset, lock.z, 0.3F, 0);
			if (!player.world.isRemote) {
				player.world.spawnEntity(flash);
			}
		}
	}
	

	public void EntityLock(boolean aam) {
		{
			Vec3d looken = player.getLookVec();
			Vec3d locken = player.getLookVec();
			float d = 120;
			boolean lock = false;
			{
				Entity getTarget = null;
				for(int xxx = 0; xxx < d; ++xxx) {
					//vehicle.world.spawnParticle(EnumParticleTypes.CRIT, locken.x * xxx-1, locken.y * xxx-1, locken.z * xxx-1, 0.0D, 0.0D, 0.0D, new int[0]);
					AxisAlignedBB axisalignedbb = (new AxisAlignedBB(vehicle.posX + locken.x * xxx-1, vehicle.posY + locken.y * xxx-1, vehicle.posZ + locken.z * xxx-1, 
							vehicle.posX + locken.x * xxx+1, vehicle.posY + locken.y * xxx+1, vehicle.posZ + locken.z * xxx+1))
                    		.grow(1);
					List<Entity> llist = vehicle.world.getEntitiesWithinAABBExcludingEntity(vehicle,axisalignedbb);
    				if (llist != null) {
    					for (int lj = 0; lj < llist.size(); lj++) {
    						Entity entity1 = (Entity) llist.get(lj);
    						if (entity1.canBeCollidedWith()) {
    							boolean flag = vehicle.getEntitySenses().canSee(entity1);
    							if (entity1 instanceof IMob && entity1 != null && entity1 != player
    									&& entity1 != vehicle && flag) {
    								getTarget = entity1;
    								break;
    							}
    						}
    					}
    				}
				}
				if(getTarget != null) {
					BlockPos bp = getTarget.world.getHeight(new BlockPos(getTarget.posX, getTarget.posY, getTarget.posZ));
					if(aam){
						if(getTarget.posY > bp.getY() + 10 && !getTarget.onGround) {
							vehicle.mitarget = (EntityLivingBase) getTarget;
							lock = true;
						}
					}else
						if(getTarget.posY < bp.getY() + 5){
						vehicle.mitarget = (EntityLivingBase) getTarget;
						lock = true;
					}
				}
			}
			//if (vehicle.ontick % 2 == 0)
			/*{
				//List llist = vehicle.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb);
				List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
						player.getEntityBoundingBox()
								.expand(locken.x * d, locken.y * d, locken.z * d)
								.grow(0.25D));
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {

						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith()) {
							boolean flag = vehicle.getEntitySenses().canSee(entity1);
							if (entity1 instanceof IMob && entity1 != null && entity1 != player
									&& entity1 != vehicle && flag) {
								BlockPos bp = entity1.world.getHeight(new BlockPos(entity1.posX, entity1.posY, entity1.posZ));
								if(aam && entity1.posY > bp.getY() + 10 && !entity1.onGround){
									vehicle.mitarget = (EntityLivingBase) entity1;
									lock = true;
									break;
								}else if(entity1.posY < bp.getY() + 5){
									vehicle.mitarget = (EntityLivingBase) entity1;
									lock = true;
									break;
								}
							}
						}
					}
				}
			}*/
			if(lock && vehicle.mitarget !=null){
	//			if (vehicle.ontick % 3 == 0) 
				{
					//NBTTagCompound nbt = vehicle.mitarget.getEntityData();
					//nbt.setInteger("LockOnTime", 200);
			//		vehicle.mitarget.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 10));
					NBTTagCompound target_nbt = vehicle.mitarget.getEntityData();
	    			if(target_nbt != null) {
	    				target_nbt.setInteger("lockon", 100);
	 //   				 System.out.println("1");
	    				if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
	    					for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
	    						GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(61, vehicle.mitarget.getEntityId()), playermp);
	    					}
	    				}
	    			}else {
	    			}
				}
				
			}
			
			if(vehicle.mitarget !=null){
				
				NBTTagCompound target_nbt = vehicle.mitarget.getEntityData();
    			if(target_nbt != null && vehicle.mitarget !=null) {
    				if(target_nbt.getInteger("lockon") <= 0) {
    					vehicle.mitarget =null;
    				}
    			}
			}
			if(vehicle.mitarget !=null){
    			if(vehicle.mitarget.isDead) {
					vehicle.mitarget =null;
				}
			}
			if(vehicle.mitarget !=null){
    			if (vehicle.world.getWorldTime() % 2 == 0) {
					vehicle.playSound(GVCSoundEvent.sound_lock, 0.4F, 1.0F);
				}
			}

			
		}
		
	}
}