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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AI_EntityWeapon_new {
	
	public EntityGVCLivingBase player;
	public EntityGVCLivingBase vehicle;
	public EntityLivingBase target;
	
	public String we_tgfx;
	
	public int id;
	public String model;
	public String tex;
	public String modelf;
	public String texf;
	
	public int ftime;
	public SoundEvent sound;
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
	/**モブが向いている方向(rotationYawHead or rotation)に撃つ
	 * false時はrotationYaw方向*/
	public boolean follow_rote = true;
	
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
	public int P_ID = 0;
    public int P_LEVEL = 5;
    public int P_TIME = 200;
	
    /**falseで無敵時間発生*/
	public boolean damegetime = false;
	
	public int kazu;
	public double gra;
	public int maxtime;
	public int dameid;
	
	//10/19
		public boolean fly_sound = true;
		public boolean spg_fly_sound = false;
	
		public int mob_min_range = 0;
		
		
	public AI_EntityWeapon_new(EntityGVCLivingBase v, EntityGVCLivingBase p) {
		this.player  = p;
		this.vehicle = v;
	}
	
	public AI_EntityWeapon_new(EntityGVCLivingBase v, EntityLivingBase p) {
		this.player  = v;
		this.vehicle = v;
	}
	
	public void Attacktask(EntityGVCLivingBase entity, EntityGVCLivingBase onride, double range){
		//System.out.println("1");
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),(double) (i - han), 
				(double) (k + han), (double) (l + han), (double) (i + han)))
						.grow(1);
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
    		if(entity.getMoveT() == 5) {
    			double x1 = entity.getMoveX();
    			double y1 = entity.getMoveY();
    			double z1 = entity.getMoveZ();
    			double dyp = onride.posY + maxy;
				double dym = onride.posY - miny;
				if (dyp > y1 && dym < y1) {
					double d5 = x1 - onride.posX;
					double d7 = z1 - onride.posZ;
					double ddx = Math.abs(d5);
					double ddz = Math.abs(d7);
					if ((ddx < 50 && ddz < 50)) {
						WeaponAttack();
					}
				}
    		}else 
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity.targetentity == entity1 && entity.CanAttack(entity1) && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						//if (entity.CanAttack(entity1) && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							boolean flag = onride.getEntitySenses().canSee(entity1);
							double ddy = Math.abs(entity1.posY - onride.posY);
							double dyp = onride.posY + maxy;
							double dym = onride.posY - miny;
							//if (dyp > entity1.posY && dym < entity1.posY) 
							{
								double d5 = entity1.posX - onride.posX;
								double d7 = entity1.posZ - onride.posZ;
								double d11 = entity.posY - (entity1.posY);
								double d31 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
								float angle = -f111+ 10;
								entity.rotation_1 = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
								if(angle >= entity.rotationp_max && angle <= entity.rotationp_min) {
									if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
										EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
										target = (EntityLivingBase)en;
										WeaponAttack();
									}else {
										target = (EntityLivingBase)entity1;
										WeaponAttack();
									}
								}
								break;
							}
							
						}
					}
				}
			}
    }
	
	public void Attacktask_LivingBase(EntityLivingBase entity, EntityGVCLivingBase onride, double range){
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - miny),(double) (i - han), 
				(double) (k + han), (double) (l + maxy), (double) (i + han)))
						.grow(1);
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity1 instanceof IMob && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							boolean flag = onride.getEntitySenses().canSee(entity1);
							double ddy = Math.abs(entity1.posY - onride.posY);
							double dyp = onride.posY + maxy;
							double dym = onride.posY - miny;
							if (dyp > entity1.posY && dym < entity1.posY) {
								double d5 = entity1.posX - onride.posX;
								double d7 = entity1.posZ - onride.posZ;
								double d11 = entity.posY - (entity1.posY);
								double d31 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
								float angle = -f111+ 10;
								if(angle >= -60 && angle <= 60) {
									if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
										EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
										target = (EntityLivingBase)en;
										onride.targetentity = (EntityLivingBase) entity1;
										WeaponAttack();
									}else {
										target = (EntityLivingBase)entity1;
										onride.targetentity = (EntityLivingBase) entity1;
										WeaponAttack();
									}
								}
								break;
							}
							
						}
					}
				}
			}
    }
	
	public void AttacktaskAngle(EntityGVCLivingBase entity, EntityGVCLivingBase onride, double range){
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),(double) (i - han), 
				(double) (k + han), (double) (l + han), (double) (i + han)))
						.grow(1);
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
    		if(entity.getMoveT() == 5) {
    			double x1 = entity.getMoveX();
    			double y1 = entity.getMoveY();
    			double z1 = entity.getMoveZ();
    			double dyp = onride.posY + maxy;
				double dym = onride.posY - miny;
				if (dyp > y1 && dym < y1) {
					double d5 = x1 - onride.posX;
					double d7 = z1 - onride.posZ;
					double ddx = Math.abs(d5);
					double ddz = Math.abs(d7);
					if ((ddx < 50 && ddz < 50)) {
						WeaponAttack();
					}
				}
    		}else 
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity.targetentity == entity1 && entity.CanAttack(entity1) && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							boolean flag = onride.getEntitySenses().canSee(entity1);
							double d5 = entity1.posX - vehicle.posX;
							double d7 = entity1.posZ - vehicle.posZ;
							double d6 = entity1.posY - vehicle.posY;
							double d1 = vehicle.posY - (entity1.posY);
				            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
				            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
				            float rotep_offset = -f11 + 10;
				            if(rotep_offset <= vehicle.rotationp_min && rotep_offset >= vehicle.rotationp_max) {
								entity.rotation_1 = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
								if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
									EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
									target = (EntityLivingBase)en;
									WeaponAttack();
								}else {
									target = (EntityLivingBase)entity1;
									WeaponAttack();
								}
								break;
							}
							
						}
					}
				}
			}
    }
	
	public void AttacktaskAir(EntityGVCLivingBase entity, EntityGVCLivingBase onride, double range){
		boolean ta = false;
		double ix = 0;
		double iy = 0;
		double iz = 0;
		double yy = 0;
		
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - miny),(double) (i - han), 
				(double) (k + han), (double) (l + maxy), (double) (i + han)))
						.grow(1);
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
		if (llist != null) {
			for (int lj = 0; lj < llist.size(); lj++) {
				Entity entity1 = (Entity) llist.get(lj);
				if (entity1.canBeCollidedWith()) {
					if (entity.targetentity == entity1 && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
					{
						double d5 = entity1.posX - onride.posX;
						double d7 = entity1.posZ - onride.posZ;
			            double ro =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
			            double ro2 = Math.abs(ro);
			            if(ro > 180F){
			            	ro = -179F;
						}
						if(ro < -180F){
							ro = 179F;
						}
			            float f3 = (float) (onride.rotationYawHead - ro);
			            double dd = Math.abs(f3);
			            float dx = (float)Math.tan((onride.posX * onride.posX) + (onride.posZ * onride.posZ));
			    		float dy = (float) (dx * Math.tan(onride.prevRotationPitch)) + (float)onride.posY;
			    		double dyp = onride.posY + maxy;
						double dym = onride.posY - miny;
						//if (dyp > entity1.posY && dym < entity1.posY && dd < 5) 
						double d1 = onride.posY - (entity1.posY);
						double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
						float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
						float rotep_offset = -f11 + 10;
						//if (rotep_offset < onride.rotationPitch + 2 && rotep_offset > onride.rotationPitch - 2 && dd < 5) 
						if (dd < 5) 
						{
							if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
								EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
								target = (EntityLivingBase)en;
								WeaponAttack();
							}else {
								target = (EntityLivingBase)entity1;
								WeaponAttack();
							}
							break;
						}
						
					}
				}
			}
		}
	}
	
	public void AttacktaskB(EntityGVCLivingBase entity, EntityGVCLivingBase onride, double range){
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - miny),(double) (i - han), 
				(double) (k + han), (double) (l + maxy), (double) (i + han)))
						.grow(1);
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity.targetentity == entity1 && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							double d5 = entity1.posX - onride.posX;
							double d7 = entity1.posZ - onride.posZ;
				            double ro =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				            if(ro > 180F){
				            	ro = -179F;
							}
							if(ro < -180F){
								ro = 179F;
							}
				            float f3 = (float) (onride.rotationYawHead - ro);
				            double dd = Math.abs(f3);
				            BlockPos bp = onride.world.getHeight(new BlockPos(onride.posX, onride.posY, onride.posZ));
				            BlockPos bp2 = entity1.world.getHeight(new BlockPos(entity1.posX, entity1.posY, entity1.posZ));
				            double genY = bp.getY() - bp2.getY();
							
							
							double ddx = Math.abs(d5);
							double ddz = Math.abs(d7);
						//if ((ddx < genY && ddz < genY)) 
						{
							if (entity1.posY < onride.posY && dd < 10) {
								if (entity1.isRiding() && entity1.getRidingEntity() != null
										&& entity1.getRidingEntity() instanceof EntityLivingBase) {
									EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
									target = (EntityLivingBase) en;
									WeaponAttack();
								} else {
									target = (EntityLivingBase) entity1;
									WeaponAttack();
								}
								break;
							}
						}
						}
					}
				}
			}
    }
	
	
	public void WeaponAttack()
	{
		double xxz = target.posX - px;
		double zzx = target.posZ - pz;
		double ddx = Math.abs(xxz);
		double ddz = Math.abs(zzx);
		//if ((ddx < mob_min_range && ddz < mob_min_range) && !follow_rote) 
		if ((ddx < mob_min_range && ddz < mob_min_range) && vehicle.ridding_rotation_lock) 
		{
			return;
		}
		//System.out.println("2");
		
		int ra = player.world.rand.nextInt(10) + 1;
    	float val = ra * 0.02F;
    	//if(sound != null && !player.world.isRemote) 
    	if(sound != null) 
    	{
    		player.playSound(sound, 5.0F, 0.9F + val);
    	}
    	if(player instanceof EntityGVCLivingBase) {
    		EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) player;
    		gvcentity.firetrue = true;
    	}else {
    		vehicle.firetrue = true;
    	}
		
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
		//kaku += lock.y * 2;
		//kaku = MathHelper.sqrt((z - bz)* (z - bz) + (w - bx)*(w - bx)) * Math.tan(Math.toRadians(-vehicle.rotationPitch)) * 0.4D;
		kaku = MathHelper.sqrt((z - bz)* (z - bz) + (w - bx)*(w - bx)) * MathHelper.sin(-vehicle.rotationPitch  * (1 * (float) Math.PI / 180));//PL_Weapon_new
		Vec3d locked = player.getLookVec();
		if(id < 10  || id == 31){
			for (int ka = 0; ka < kazu; ++ka) {
				
				EntityBBase var3;
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
				} else if (id == 6) {
					var3 = new EntityB_BulletFire(player.world, player);
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
					
				} else if (id == 31) {
					var3 = new EntityB_Bullet(player.world, player);
					var3.inwater = true;
				} else {
					var3 = new EntityB_Bullet(player.world, player);
				}
					//新武器-AI
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
				var3.gra = gra;// 0.025
				//var3.friend = entity;
				var3.friend = vehicle;
				var3.ex = extrue;
				var3.exfire = exfire;
				var3.exsmoke = exsmoke;
				var3.exflash = exflash;
				var3.exlevel = ex;
				var3.setModel(model);
				var3.setTex(tex);
				var3.timemax = maxtime;
				var3.bulletDameID = dameid;
				var3.muteki = damegetime;
				 var3.mitarget = target;
				 var3.P_ID = P_ID;
				 var3.P_TIME = P_TIME;
				 var3.P_LEVEL = P_LEVEL;
				 
				 var3.fly_sound = fly_sound;
				 var3.spg_fly_sound = spg_fly_sound;
				 
				var3.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11, player.rotationYaw,
						player.rotationPitch);
				
				Vec3d elock = target.getLookVec();
				
				double ta_x = target.posX;
				double ta_y = target.posY;
				double ta_z = target.posZ;
				/*if(!player.getEntitySenses().canSee(target)) {
					ta_x = player.targetentity_lastposX;
					ta_y = player.targetentity_lastposY;
					ta_z = player.targetentity_lastposZ;
				}*/
				double d5 = ta_x - px;
				double d7 = ta_z - pz;
	            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
				//double xx = elock.x * d3;
				//double zz = elock.z * d3;
				//double xx = (entity1.posX - entity1.prevPosX) * 1;
			    //double zz = (entity1.posZ - entity1.prevPosZ) * 1;
	            double xx = target.motionX * d3;
				double zz = target.motionZ * d3;
				double var4 = (ta_x + xx) - px - xx11 - offx;
				double var6 = ta_y + (double) target.getEyeHeight()
						+ 0.200000023841858D - var3.posY - (h / 2);
				double var8 = (ta_z + zz) - pz - zz11 - offz;
				float var10 = MathHelper.sqrt(var4 * var4 + var8 * var8) * 0.01F;
				float var11 = var10 * 100;
				if(player.ai_bullet_speed_over4f) {
					
				}else {
					if(speed > 4F)speed = 4F;
				}
				float t = var11 / speed;
				float hg = (float) (((0.03 - gra)* t*t) / 2);
				float bbure = bure;
				if (player.isPotionActive(MobEffects.BLINDNESS))
		        {
					bbure = bbure * 10F;
		        }
				if(follow_rote) {
					var3.setThrowableHeading(var4, (var6 + (double) var10) * 1 + kaku + hg, var8, speed, bbure);
				}else {
					if(player == vehicle) {
						//var3.setThrowableHeading(var4, (var6 + (double) var10) * 1 + kaku, var8, speed, bure);
						var3.setHeadingFromThrower(player, (float)player.rotationPitch + (float)yoffset, player.rotationYaw + (float)xoffset, 0.0F,
								speed, bure);
					}else {
						var3.setHeadingFromThrower(vehicle, (float)vehicle.rotationPitch + (float)yoffset, vehicle.rotationYaw + (float)xoffset, 0.0F,
								speed, bure);
					}
					
				}
				
				if (!player.world.isRemote) {
					player.world.spawnEntity(var3);
				}
				EntityT_Flash flash = new EntityT_Flash(player.world, player);
				flash.gra = 0.03;
				flash.timemax = ftime;
				flash.setModel(modelf);
				flash.setTex(texf);
				flash.setLocationAndAngles(px + xx11, py + h, pz + zz11, player.rotationYaw,
						player.rotationPitch);
				flash.setThrowableHeading(var4, (var6 + (double) var10) * 1 + kaku, var8, 0.3F, 0);
				if (!player.world.isRemote) {
					player.world.spawnEntity(flash);
				}
			}
		}else if(id >= 10 && id < 20){
			EntityBBase var3;
			if(id == 14 || id == 11) {
				var3 = new EntityB_Shell(player.world, player);
			}else {
				var3 = new EntityB_GrenadeB(player.world, player);
			}
			//曲射炮弹
			var3.setTexF("SAAPTrail");
			var3.Bdamege = power;
			var3.gra = 0.0F;// 0.025
			//var3.friend = entity;
			var3.friend = vehicle;
			var3.ex = extrue;
			var3.exfire = exfire;
			var3.exsmoke = exsmoke;
			var3.exflash = exflash;
			var3.exlevel = ex;
			var3.setModel(model);
			var3.setTex(tex);
			var3.timemax = 8000;
			var3.bulletDameID = dameid;
			var3.muteki = damegetime;
			 var3.mitarget = target;
			var3.setLocationAndAngles(px + xx11, py + h + kaku, pz + zz11, player.rotationYaw,
					player.rotationPitch);
			var3.spg_fly_sound = true;//true
			
			Vec3d elock = target.getLookVec();
			double d5 = target.posX - px;
			double d7 = target.posZ - pz;
            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
			//double xx = elock.x * d3;
			//double zz = elock.z * d3;
			//double xx = (entity1.posX - entity1.prevPosX) * 1;
		    //double zz = (entity1.posZ - entity1.prevPosZ) * 1;
            double xx = target.motionX * d3;
			double zz = target.motionZ * d3;
			double var4 = (target.posX + xx) - px - xx11 - offx;
			double var6 = target.posY + (double) target.getEyeHeight()
					+ 0.200000023841858D - var3.posY - (h / 2);
			double var8 = (target.posZ + zz) - pz - zz11 - offz;
			float var10 = MathHelper.sqrt(var4 * var4 + var8 * var8) * 0.01F;
			
			double xrange = Math.abs(target.posX) -Math.abs(px);
			double zrange = Math.abs(target.posZ) -Math.abs(pz);
			double range = (double)MathHelper.sqrt(xrange * xrange + zrange * zrange) * 1;
			
			double rangespeed = (double)MathHelper.sqrt((0.022 * range) / (2 * Math.sin(60) * Math.cos(60)));
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
				flash.setLocationAndAngles(px + xx11, py + h, pz + zz11, player.rotationYaw,
						player.rotationPitch);
				flash.setThrowableHeading(var4, (var6 + (double) var10) * 1 + kaku, var8, 0.3F, 0);
				if (!player.world.isRemote) {
					player.world.spawnEntity(flash);
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
		else if(id == 51){//箭
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
		else if(id == 30){//近战
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

	public void EntityLock(boolean aam) {
		{
			Vec3d looken = player.getLookVec();
			Vec3d locken = player.getLookVec();
			float d = 100;
			boolean lock = false;
			//if (vehicle.ontick % 2 == 0)
			{
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
								if(aam && entity1.posY > bp.getY() + 5){
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
			}
			if(lock && vehicle.mitarget !=null){
				if (vehicle.ontick % 3 == 0) {
					//NBTTagCompound nbt = vehicle.mitarget.getEntityData();
					//nbt.setInteger("LockOnTime", 200);
					vehicle.mitarget.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 10));
				}
				if (vehicle.ontick % 10 == 0) {
		//			vehicle.playSound(GVCSoundEvent.sound_lock, 0.4F, 1.0F);
				}
			}
		}
		
	}
}