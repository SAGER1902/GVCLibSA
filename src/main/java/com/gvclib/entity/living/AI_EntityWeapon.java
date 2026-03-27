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
import gvclib.entity.EntityT_Morter;
import gvclib.entity.EntityT_TNT;
import gvclib.entity.Entity_Flare;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_Shield;
import gvclib.item.ItemWeaponBase;
import gvclib.item.ItemWeapon_HSword;
import gvclib.item.ItemWeapon_Rapier;
import gvclib.item.ItemWeapon_Spear;
import gvclib.item.gunbase.IGun_Sword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class AI_EntityWeapon
{
	public static void Biped_Gun(EntityGVCLivingBase entity, ItemStack itemstack, boolean ex){
		Vec3d looked = entity.getLookVec();
		if (itemstack.getItem() instanceof ItemGunBase && !(itemstack.getItem() instanceof ItemGun_Shield)) 
    	{
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			entity.magazine = gun.getMaxDamage();
			entity.reload_time1 = gun.reloadtime * 2;
    		String model = gun.bullet_model;
    		String tex = gun.bullet_tex;
    		String modelf = gun.bulletf_model;
    		String texf = gun.bulletf_tex;
			float bure = 1.0F;
			if(entity.getMoveT() == 3||entity.sneak){//瞄准散布
				bure = gun.bure_ads;
			}else{
				bure = gun.bure;
			}
			
        	{
        		if(entity.cooltime > gun.mobammo && entity.getRemain_L() > 0)
    			{
        			entity.counter1 = true;
        			entity.cooltime = 0;
    			}
        		if(entity.counter1 && entity.gun_count1 > gun.mobcycle && entity.getRemain_L() > 0){
        			SoundEvent sounds = SoundEvents.ENTITY_SKELETON_SHOOT;
        			if(gun.fires != null) {
        				sounds = gun.fires;
        			}else if(gun.sound != null){
        				sounds = SoundEvent.REGISTRY.getObject(new ResourceLocation(gun.modid, gun.sound));
        			}
        			AI_EntityWeapon.Attacktask(entity, entity, gun.mobbullettype, gun.mobrange, 
        					model, tex, modelf, texf,2, 
        					sounds, 
        					0, 0, 1.5, 1.0,entity.posX, entity.posY, entity.posZ,looked,entity.rotationYawHead, 20, 10,
    						gun.powor*3/4, gun.speed * 0.9F, bure, gun.exlevel, ex, gun.shotgun_pellet, gun.gra, gun.bullet_time_max, 0);
    				entity.gun_count1 = 0;
    				if(entity.firetrue){
    					entity.setRemain_L(entity.getRemain_L() - 1);
    				}
    				++entity.countlimit1;
    			}
    			if(entity.countlimit1 >= gun.mobbarst){
    				entity.gun_count1 = 0;
    				entity.counter1 = false;
    				entity.countlimit1 = 0;
    			}
        	}
    		//max = gun.mobmax;
    		//range = gun.mobrange;
    	}else if(!(itemstack.getItem() instanceof ItemGun_Shield)){
    		ItemBow bow = (ItemBow) itemstack.getItem();
    		{
        		if(entity.cooltime > 50)
    			{
	        			AI_EntityWeapon.Attacktask(entity, entity, 51, 20, 
	        					"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png",
	        					"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png",2, 
	        					SoundEvents.ENTITY_SKELETON_SHOOT, 
	        					0, 0, 1.5, 0.5,entity.posX, entity.posY, entity.posZ,looked,entity.rotationYawHead, 20, 10,
	    						6, 1.6F, 5, 0, false, 1, 0.025D, 80, 0);
	        			entity.cooltime = 0;
    			}
        	}
    	}
	}
	
	public static void Biped_Gun_new(EntityGVCLivingBase entity, ItemStack itemstack, boolean ex, int powor_offset, float speed_offset){
		Vec3d looked = entity.getLookVec();
		if (itemstack.getItem() instanceof ItemGunBase && !(itemstack.getItem() instanceof ItemGun_Shield)) 
    	{
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			entity.magazine = gun.getMaxDamage();
			entity.reload_time1 = gun.reloadtime * 2;
    		String model = gun.bullet_model;
    		String tex = gun.bullet_tex;
    		String modelf = gun.bulletf_model;
    		String texf = gun.bulletf_tex;
			
			float bure = 1.0F;
			if(entity.getMoveT() == 3||entity.sneak){//瞄准散布
				bure = gun.bure_ads;
			}else{
				bure = gun.bure;
			}
			
        	{
        		if(entity.cooltime > gun.mobammo && entity.getRemain_L() > 0)
    			{
        			entity.counter1 = true;
        			entity.cooltime = 0;
    			}
        		if(entity.counter1 && entity.gun_count1 > gun.mobcycle && entity.getRemain_L() > 0){
        			SoundEvent sounds = SoundEvents.ENTITY_SKELETON_SHOOT;
        			if(gun.fires != null) {
        				sounds = gun.fires;
        			}else if(gun.sound != null){
        				sounds = SoundEvent.REGISTRY.getObject(new ResourceLocation(gun.modid, gun.sound));
        			}
        			float speed = gun.speed * speed_offset;
        			if(speed > 4F)speed = 4F;
        			AI_EntityWeapon.Attacktask(entity, entity, gun.mobbullettype, gun.mobrange, 
        					model, tex, modelf, texf,2, 
        					sounds, 
        					0, 0, 1.5, 1.0,entity.posX, entity.posY, entity.posZ,looked,entity.rotationYawHead, 20, 10,
    						gun.powor / powor_offset, speed, bure, gun.exlevel, ex, gun.shotgun_pellet, gun.gra, gun.bullet_time_max, 0);
    				entity.gun_count1 = 0;
    				if(entity.firetrue){
    					entity.setRemain_L(entity.getRemain_L() - 1);
    				}
    				++entity.countlimit1;
    			}
    			if(entity.countlimit1 >= gun.mobbarst){
    				entity.gun_count1 = 0;
    				entity.counter1 = false;
    				entity.countlimit1 = 0;
    			}
        	}
    		//max = gun.mobmax;
    		//range = gun.mobrange;
    	}else if(!(itemstack.getItem() instanceof ItemGun_Shield)){
    		ItemBow bow = (ItemBow) itemstack.getItem();
    		{
        		if(entity.cooltime > 50)
    			{
	        			AI_EntityWeapon.Attacktask(entity, entity, 51, 20, 
	        					"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png",
	        					"gvclib:textures/entity/BulletAAA.obj","gvclib:textures/entity/BulletAAA.png",2, 
	        					SoundEvents.ENTITY_SKELETON_SHOOT, 
	        					0, 0, 1.5, 0.5,entity.posX, entity.posY, entity.posZ,looked,entity.rotationYawHead, 20, 10,
	    						6, 1.6F, 5, 0, false, 1, 0.025D, 80, 0);
	        			entity.cooltime = 0;
    			}
        	}
    	}
	}
	
	
	public static void Biped_Sword(EntityGVCLivingBase entity, ItemStack itemstack){
		Vec3d looked = entity.getLookVec();
		float damege = 1.0F;
		float endame = 1.0F;
		float swdame = 1.0F;
		float movesp = 1.0F;
		int cool = 20;
		double ran = 3.0D;
		double han = 0.2D;
		if(itemstack.getItem() instanceof IGun_Sword) {
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			swdame = gun.attackDamage;
			cool = (int) gun.attackSpeed * (-15);
		}
		if(itemstack.getItem() instanceof ItemSword) {
			swdame = 3.0F;
			cool = 20;
			//System.out.println(String.format("sword"));
			if("WOOD".equals(((ItemSword)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
			if("STONE".equals(((ItemSword)itemstack.getItem()).getToolMaterialName())) {
				endame = 1F;
			}
			if("IRON".equals(((ItemSword)itemstack.getItem()).getToolMaterialName())) {
				endame = 2F;
				//System.out.println(String.format("iron"));
			}
			if("DIAMOND".equals(((ItemSword)itemstack.getItem()).getToolMaterialName())) {
				endame = 3F;
			}
			if("GOLD".equals(((ItemSword)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
		}
		if(itemstack.getItem() instanceof ItemAxe) {
			swdame = 5.0F;
			cool = 40;
			movesp = 0.8F;
		}
		if(itemstack.getItem() instanceof ItemSpade) {
			swdame = 2.0F;
			cool = 15;
		}
		if(itemstack.getItem() instanceof ItemWeapon_Spear) {
			swdame = 2.0F;
			cool = 20;
			ran = 5.0D;
			movesp = 1.0F;
			if("WOOD".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
			if("STONE".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 1F;
			}
			if("IRON".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 2F;
				//System.out.println(String.format("iron"));
			}
			if("DIAMOND".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 3F;
			}
			if("GOLD".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
		}
		if(itemstack.getItem() instanceof ItemWeapon_Rapier) {
			swdame = 0.0F;
			cool = 10;
			ran = 3.0D;
			movesp = 1.0F;
			if("WOOD".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
			if("STONE".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 1F;
			}
			if("IRON".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 2F;
				//System.out.println(String.format("iron"));
			}
			if("DIAMOND".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 3F;
			}
			if("GOLD".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
		}
		if(itemstack.getItem() instanceof ItemWeapon_HSword) {
			swdame = 6.0F;
			cool = 60;
			ran = 3.0D;
			han = 3D;
			movesp = 0.5F;
			if("WOOD".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
			if("STONE".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 1F;
			}
			if("IRON".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 2F;
				//System.out.println(String.format("iron"));
			}
			if("DIAMOND".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 3F;
			}
			if("GOLD".equals(((ItemWeaponBase)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
		}
		
		if(itemstack.getItem() instanceof ItemTool) {
			ItemTool tool = (ItemTool) itemstack.getItem();
			if("WOOD".equals(((ItemTool)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
			if("STONE".equals(((ItemTool)itemstack.getItem()).getToolMaterialName())) {
				endame = 1F;
			}
			if("IRON".equals(((ItemTool)itemstack.getItem()).getToolMaterialName())) {
				endame = 2F;
				//System.out.println(String.format("iron"));
			}
			if("DIAMOND".equals(((ItemTool)itemstack.getItem()).getToolMaterialName())) {
				endame = 3F;
			}
			if("GOLD".equals(((ItemTool)itemstack.getItem()).getToolMaterialName())) {
				endame = 0F;
			}
		}
		damege = swdame + endame;
		entity.setCoolDownBase(cool);
		entity.setMoveSpeedBase(movesp);
		/*if(itemstack.getItem() == Items.IRON_SWORD) {
			damege = 5.0F;
		}
		if(itemstack.getItem() == Items.IRON_AXE) {
			damege = 6.0F;
		}/**/
		boolean task = AI_EntityWeapon.KnifeAttack(entity, 0, ran, ran, 1, 1);
		if(task && !entity.getSneak()){//1
			if(entity.cooltime4 > cool){
				entity.swingArm(EnumHand.MAIN_HAND);
				double x2 = 0;
				double z2 = 0;
				x2 -= MathHelper.sin(entity.rotationYawHead * 0.01745329252F) * ran;
				z2 += MathHelper.cos(entity.rotationYawHead * 0.01745329252F) * ran;
				AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(
						entity.posX - x2, entity.posY, entity.posZ - z2, 
						entity.posX + x2, entity.posY, entity.posZ + z2)
		        		.expand(han, han, han);
				Vec3d lock = Vec3d.fromPitchYaw(entity.rotationPitch, entity.rotationYaw);
				float d = 3;
				//List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb2);
				List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
						entity.getEntityBoundingBox()
								.expand(lock.x * d, lock.y * d, lock.z * d)
								.grow(0.02D));
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith() && entity1 instanceof EntityLivingBase && entity1 != null) {
							if(entity1 != entity && entity1 != entity.getControllingPassenger()){
								entity1.attackEntityFrom(DamageSource.causeMobDamage(entity), damege);
							}
						}
					}
				}
				entity.cooltime4 = 0;
			}
		}//1
	}
	
	public static boolean KnifeAttack(EntityGVCLivingBase entity, int id, double range, double max, double maxy, double miny){
		boolean task = false;
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
				entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
		if (llist != null) {
			for (int lj = 0; lj < llist.size(); lj++) {
				Entity entity1 = (Entity) llist.get(lj);
				if (entity1.canBeCollidedWith()) {
					if (entity.targetentity == entity1 && entity.CanAttack(entity1) && entity1 != null 
							&& ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
					{
						boolean flag = entity.getEntitySenses().canSee(entity1);
						double ddy = Math.abs(entity1.posY - entity.posY);
						double dyp = entity.posY + maxy;
						double dym = entity.posY - miny;
						if (dyp > entity1.posY && dym < entity1.posY) {
							double d5 = entity1.posX - entity.posX;
							double d7 = entity1.posZ - entity.posZ;
							double ddx = Math.abs(d5);
							double ddz = Math.abs(d7);
						//	if (!(ddx > max || ddz > max))
							{
								task = true;
								break;
							}
						}
						
					}
				}
			}
		}
		return task;
	}
	
	public static void getTargetEntity(EntityGVCLivingBase entity, double range, double maxy, double miny){
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		
		if(entity.biped) {
			if (entity.isPotionActive(MobEffects.BLINDNESS)) {
				entity.targetentity = null;
				entity.setMobMode(1);
				entity.sneak = false;
				entity.setattacktask(false);
			}
		}
		
		if(!entity.world.isRemote && entity.targetentity == null)
		{
			
			Entity target = null;
			Entity[] targetlist = new Entity[1024];
			int targetplus = 0;
			
			AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - miny),(double) (i - han), 
					(double) (k + han), (double) (l + maxy), (double) (i + han)))
							.grow(1);
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity.CanAttack(entity1) && entity1 != null && entity1 instanceof EntityLivingBase
								&& ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							/*if(entity.targetentity == null) {
								boolean flag = entity.getEntitySenses().canSee(entity1);
								double ddy = Math.abs(entity1.posY - entity.posY);
								double dyp = entity.posY + maxy;
								double dym = entity.posY - miny;
								if (dyp > entity1.posY && dym < entity1.posY && flag) {
									{
										entity.targetentity = (EntityLivingBase) entity1;
										entity.setMobMode(1);
										entity.sneak = true;
										entity.setattacktask(true);
										break;
									}
								}
							}*/
							if(entity.targetentity == null) {
								boolean flags = true;
								if (entity1.getRidingEntity() instanceof EntityGVCLivingBase && entity1.getRidingEntity() != null) {// 1
									EntityGVCLivingBase vehicle = (EntityGVCLivingBase) entity1.getRidingEntity();
									if(vehicle.ridding_nottarget) {
										flags = false;
									}
								}
								if(flags) {
									boolean flag = entity.getEntitySenses().canSee(entity1);
									double d5 = entity1.posX - entity.posX;
									double d7 = entity1.posZ - entity.posZ;
									double d6 = entity1.posY - entity.posY;
									double d1 = entity.posY - (entity1.posY);
						            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
						            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
						            float rotep_offset = -f11 + 10;
						            if(rotep_offset <= entity.rotationp_min && rotep_offset >= entity.rotationp_max && flag) {
						            	if (!((EntityLivingBase) entity1).isPotionActive(MobEffects.INVISIBILITY) && targetplus < 1023)
						            	{
						            		targetlist[targetplus] = entity1;
											++targetplus;
						            		/*
											entity.targetentity = (EntityLivingBase) entity1;
											entity.setMobMode(1);
											entity.sneak = true;
											
											if(!entity.getattacktask())
											{
												entity.world.spawnParticle(EnumParticleTypes.NOTE, 
														entity.posX,  entity.posY + entity.height + 1, entity.posZ, 0, 0, 0);
												
												entity.setattacktask(true);
			                    			}*/
											break;
										}
									}
								}
							}
						}
					}
				}
			}
			
			
			
			for(int xs = 0; xs < targetlist.length; ++xs) {
				if(targetlist[xs] != null) {
					{
						//target = targetlist[xs];
						if(target != null) {
							double d5 = targetlist[xs].posX - entity.posX;
							double d7 = targetlist[xs].posZ - entity.posZ;
							double ddx = Math.abs(d5);
							double ddz = Math.abs(d7);
							
							double d51 = target.posX - entity.posX;
							double d71 = target.posZ - entity.posZ;
							double ddx1 = Math.abs(d51);
							double ddz1 = Math.abs(d71);
							if((ddx < ddx1 || ddz < ddz1)) {
								target = targetlist[xs];
							}
						}else {
							target = targetlist[xs];
						}
	//					break;
					}
				}
			}
			if(target != null) {
				entity.targetentity = (EntityLivingBase) target;
				entity.setMobMode(1);
				entity.sneak = true;
				
				if(!entity.getattacktask())
				{
					entity.world.spawnParticle(EnumParticleTypes.NOTE, 
							entity.posX,  entity.posY + entity.height + 1, entity.posZ, 0, 0, 0);
					
					entity.setattacktask(true);
    			}
			}
		}
	}
	
	public static void getTargetEntity_Bomber(EntityGVCLivingBase entity, double range, double maxy, double miny){
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		if(!entity.world.isRemote){
			AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - miny),(double) (i - han), 
					(double) (k + han), (double) (l + maxy), (double) (i + han)))
							.grow(1);
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity.CanAttack(entity1) && entity1 != null 
								&& ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							if(entity.targetentity == null) {
								boolean flag = entity.getEntitySenses().canSee(entity1);
								double ddy = Math.abs(entity1.posY - entity.posY);
								double dyp = entity.posY + maxy;
								double dym = entity.posY - miny;
								if (dyp > entity1.posY && dym < entity1.posY && flag) {
									{
										entity.targetentity = (EntityLivingBase) entity1;
										entity.setMobMode(1);
										entity.sneak = true;
										entity.setattacktask(true);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	public static boolean getRange(EntityGVCLivingBase entity, double range, double maxy, double miny){
		boolean task = false;
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),(double) (i - han), 
				(double) (k + han), (double) (l + han), (double) (i + han)))
						.grow(1);
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
				//entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
		if (llist != null) {
			for (int lj = 0; lj < llist.size(); lj++) {
				Entity entity1 = (Entity) llist.get(lj);
				if (entity1.canBeCollidedWith()) {
					if (entity.targetentity == entity1 && entity.CanAttack(entity1) && entity1 != null 
							&& ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
					{
						boolean flag = entity.getEntitySenses().canSee(entity1);
						double ddy = Math.abs(entity1.posY - entity.posY);
					//	double dyp = entity.posY + maxy;
					//	double dym = entity.posY - miny;
						double d5 = entity1.posX - entity.posX;
						double d7 = entity1.posZ - entity.posZ;
						double d6 = entity1.posY - entity.posY;
						double d1 = entity.posY - (entity1.posY);
			            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
			            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			            float rotep_offset = -f11 + 10;
			            if(rotep_offset <= entity.rotationp_min && rotep_offset >= entity.rotationp_max) {
							if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
								EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
								if(en.getHealth()> 0.0F ) {
									task = true;
									entity.setattacktask(true);
									break;
								}
							}else{
								task = true;
								entity.setattacktask(true);
								break;
							}
						}
						
					}
				}
			}
		}
		return task;
	}
	
	public static boolean getRangeAngle(EntityGVCLivingBase entity,EntityGVCLivingBase vehicle, double range){
		boolean task = false;
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),(double) (i - han), 
				(double) (k + han), (double) (l + han), (double) (i + han)))
						.grow(1);
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
				//entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
		if (llist != null) {
			for (int lj = 0; lj < llist.size(); lj++) {
				Entity entity1 = (Entity) llist.get(lj);
				if (entity1.canBeCollidedWith()) {
					if (entity.targetentity == entity1 && entity.CanAttack(entity1) && entity1 != null 
							&& ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
					{
						boolean flag = entity.getEntitySenses().canSee(entity1);
						double d5 = entity1.posX - vehicle.posX;
						double d7 = entity1.posZ - vehicle.posZ;
						double d6 = entity1.posY - vehicle.posY;
						double d1 = vehicle.posY - (entity1.posY);
			            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
			            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			            float rotep_offset = -f11 + 10;
			            if(rotep_offset <= vehicle.rotationp_min && rotep_offset >= vehicle.rotationp_max) {
							if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
								EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
								if(en.getHealth()> 0.0F ) {
									task = true;
									entity.setattacktask(true);
									break;
								}
							}else{
								task = true;
								entity.setattacktask(true);
								break;
							}
						}
						
					}
				}
			}
		}
		return task;
	}
	
	public static boolean getRangeSPG(EntityGVCLivingBase entity,EntityGVCLivingBase vehicle, double range){
		boolean task = false;
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),(double) (i - han), 
				(double) (k + han), (double) (l + han), (double) (i + han)))
						.grow(1);
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
				//entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
		if (llist != null) {
			for (int lj = 0; lj < llist.size(); lj++) {
				Entity entity1 = (Entity) llist.get(lj);
				if (entity1.canBeCollidedWith()) {
					if(entity1 != null && entity.getHealth() > 0.0F) {
						if (entity.targetentity == entity1 && entity.CanAttack(entity1)) 
						{
							boolean flag = entity.getEntitySenses().canSee(entity1);
							double d5 = entity1.posX - vehicle.posX;
							double d7 = entity1.posZ - vehicle.posZ;
							double d6 = entity1.posY - vehicle.posY;
							double d1 = vehicle.posY - (entity1.posY);
				            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
				            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
				            float rotep_offset = -f11 + 10;
				            {
								if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
									EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
									if(en.getHealth()> 0.0F ) {
										task = true;
										entity.setattacktask(true);
										break;
									}
								}else{
									task = true;
									entity.setattacktask(true);
									break;
								}
							}
							
						}
					}
					
				}
			}
		}
		return task;
	}
	
	public static void Attacktask_More(EntityGVCLivingBase entity, EntityGVCLivingBase onride, int id, double range,
    		String model, String tex,String modelf, String texf,int ftime,SoundEvent sound
    		, float f, double w, double h, double z, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid, int target_id){
				
			EntityLivingBase targetentity_x = null;
			
			if(target_id==2){
				targetentity_x = entity.targetentity2;
			}else if(target_id==3){
				targetentity_x = entity.targetentity3;
			}else if(target_id==4){
				targetentity_x = entity.targetentity4;
			}else{
				targetentity_x = entity.targetentity5;
			}

    		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
    		if(entity.getMoveT() == 5 || (entity.getMoveT() == 1 && entity.getAIType() == 1)) {
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
						AIWeapon_new2( entity,  onride,  id,  range,
					    		 model,  tex,  modelf,  texf, ftime,sound,
					    		 f,  w,  h,  z, 0,0,  px,  py,  pz,  lock,  rote,  maxy,  miny,  
					    		 dame,  speed,  recoil,  ex,  extrue,  kazu,   gra,  maxtime,  dameid);
					}
				}
    		}else 
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (targetentity_x == entity1 && entity.CanAttack(entity1) && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							boolean flag = onride.getEntitySenses().canSee(entity1);
							double ddy = Math.abs(entity1.posY - onride.posY);
							double dyp = onride.posY + maxy;
							double dym = onride.posY - miny;
							if (dyp > entity1.posY && dym < entity1.posY) {
								double d5 = entity1.posX - onride.posX;
								double d7 = entity1.posZ - onride.posZ;
								entity.rotation_1 = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
								if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
									EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
									AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
											f, w, h, z, px, py, pz, lock, rote, 1, 1, 
											dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)en);
								}else {
									AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
											f, w, h, z, px, py, pz, lock, rote, 1, 1, 
											dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)entity1);
								}
								break;
							}
							
						}
					}
				}
			}
    }
	
	public static void Attacktask(EntityGVCLivingBase entity, EntityGVCLivingBase onride, int id, double range,
    		String model, String tex,String modelf, String texf,int ftime,SoundEvent sound
    		, float f, double w, double h, double z, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid){
    		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
    		if(entity.getMoveT() == 5 || (entity.getMoveT() == 1 && entity.getAIType() == 1)) {
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
						AIWeapon_new2( entity,  onride,  id,  range,
					    		 model,  tex,  modelf,  texf, ftime,sound,
					    		 f,  w,  h,  z, 0,0,  px,  py,  pz,  lock,  rote,  maxy,  miny,  
					    		 dame,  speed,  recoil,  ex,  extrue,  kazu,   gra,  maxtime,  dameid);
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
							double ddy = Math.abs(entity1.posY - onride.posY);
							double dyp = onride.posY + maxy;
							double dym = onride.posY - miny;
							if (dyp > entity1.posY && dym < entity1.posY) {
								double d5 = entity1.posX - onride.posX;
								double d7 = entity1.posZ - onride.posZ;
								entity.rotation_1 = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
								if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
									EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
									AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
											f, w, h, z, px, py, pz, lock, rote, 1, 1, 
											dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)en);
								}else {
									AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
											f, w, h, z, px, py, pz, lock, rote, 1, 1, 
											dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)entity1);
								}
								break;
							}
							
						}
					}
				}
			}
    }
	
	public static void Attacktaskoffset(EntityGVCLivingBase entity, EntityGVCLivingBase onride, int id, double range,
    		String model, String tex,String modelf, String texf,int ftime,SoundEvent sound
    		, float f, double w, double h, double z, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny, float offset, float maxoffset,int roteid
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid){
    		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity.CanAttack(entity1) && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							boolean flag = entity.getEntitySenses().canSee(entity1);
							double ddy = Math.abs(entity1.posY - entity.posY);
							double dyp = entity.posY + maxy;
							double dym = entity.posY - miny;
							double d5 = entity1.posX - entity.posX;
							double d7 = entity1.posZ - entity.posZ;
							
							float angle = entity.rotationYawHead + offset;
							if(angle > 180){
								angle = - angle;
							}
							if(angle < -180){
								angle = angle * -1;
							}
							double ro =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
							double d1 = entity.posY - (entity1.posY);
				            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
				            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
				            if(roteid == 2){
				            	entity.rotation_2 = (float) ro;
				            	entity.rotationp_2 = -f11 + 10;
				            }
				            if(roteid == 3){
				            	entity.rotation_3 = (float) ro;
				            	entity.rotationp_3 = -f11 + 10;
				            }
				            if(roteid == 4){
				            	entity.rotation_4 = (float) ro;
				            	entity.rotationp_4 = -f11 + 10;
				            }
				            if(roteid == 5){
				            	entity.rotation_5 = (float) ro;
				            	entity.rotationp_5 = -f11 + 10;
				            }
				            
				            float f3 = (float) (angle - ro);
				            double dd = Math.abs(f3);
							if (dyp > entity1.posY && dym < entity1.posY && dd < maxoffset) {
								if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
									EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
									AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
											f, w, h, z, px, py, pz, lock, rote, 1, 1, 
											dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)en);
								}else {
									AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
											f, w, h, z, px, py, pz, lock, rote, 1, 1, 
											dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)entity1);
								}
								break;
							}
							
						}
					}
				}
			}
    }
	
	public static void AttacktaskB(EntityGVCLivingBase entity, EntityGVCLivingBase onride, int id, double range,
    		String model, String tex,String modelf, String texf,int ftime,SoundEvent sound
    		, float f, double w, double h, double z, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid){
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity
				.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
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
				            BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
							int genY = bp.getY();
							if (entity1.posY < genY + 5 && dd < 10) {
								if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
									EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
									AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
											f, w, h, z, px, py, pz, lock, rote, maxy, miny, 
											dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)en);
								}else {
									AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
											f, w, h, z, px, py, pz, lock, rote, maxy, miny, 
											dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)entity1);
								}
								break;
							}
							
						}
					}
				}
			}
    }
	
	public static void AttacktaskAir(EntityGVCLivingBase entity, EntityGVCLivingBase onride, int id, double range,
    		String model, String tex,String modelf, String texf,int ftime,SoundEvent sound
    		, float f, double w, double h, double z, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid){
		boolean ta = false;
		double ix = 0;
		double iy = 0;
		double iz = 0;
		double yy = 0;
		
		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity
				.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
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
						if (dyp > entity1.posY && dym < entity1.posY && dd < 5) {
							if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
								EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
								AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
										f, w, h, z, px, py, pz, lock, rote, 1, 1, 
										dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)en);
							}else {
								AIWeapon(entity,onride, id, range, model, tex, modelf, texf, ftime, sound, 
										f, w, h, z, px, py, pz, lock, rote, 1, 1, 
										dame, speed, recoil, ex,extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)entity1);
							}
							break;
						}
						
					}
				}
			}
		}
		/*for (int l = 0; l < 80; l++) {

			float f11 = entity.rotationYawHead * (2 * (float) Math.PI / 360);
			float f21 = entity.rotationPitch * (2 * (float) Math.PI / 360);
			ix -= MathHelper.sin(f11) * l;
			iz += MathHelper.cos(f11) * l;
			Vec3d look = entity.getLookVec();
			yy = look.yCoord * l + 1;

			{
				List llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
						entity.getEntityBoundingBox().expand(ix, yy, iz).expand(3, 6, 3));
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith()) {
							if (entity.targetentity == entity1.getEntityId() && entity1 != null
									&& ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) {
								boolean flag = entity.getEntitySenses().canSee(entity1);
								double ddy = Math.abs(entity1.posY - entity.posY);
								double dyp = entity.posY + maxy;
								double dym = entity.posY - miny;
								if (dyp > entity1.posY && dym < entity1.posY) {
									ta = true;
									break;
								}
							}
						}
					}
				}
			}
		}
		if(ta){
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity.targetentity == entity1.getEntityId() && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							boolean flag = entity.getEntitySenses().canSee(entity1);
							double ddy = Math.abs(entity1.posY - entity.posY);
							double dyp = entity.posY + maxy;
							double dym = entity.posY - miny;
							if (dyp > entity1.posY && dym < entity1.posY) {
								AIWeapon(entity, id, range, model, tex, sound, 
										f, w, h, z, px, py, pz, maxy, miny, 
										dame, speed, recoil, ex, extrue, kazu, gra, maxtime, dameid, (EntityLivingBase)entity1);
								break;
							}
							
						}
					}
				}
			}
		}*/
	}
	
	public static void AIWeapon(EntityGVCLivingBase entity, EntityGVCLivingBase onride, int id, double range,
    		String model, String tex, String modelf, String texf,int ftime,
    		SoundEvent sound
    		, float f, double w, double h, double z, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid,
    		EntityLivingBase entity1){
		AIWeapon_new1( entity,  onride,  id,  range,
	    		 model,  tex,  modelf,  texf, ftime,sound,
	    		 f,  w,  h,  z, 0,0,  px,  py,  pz,  lock,  rote,  maxy,  miny,  
	    		 dame,  speed,  recoil,  ex,  extrue,  kazu,   gra,  maxtime,  dameid, entity1);
	}
	
	public static void Attacktask_new(EntityGVCLivingBase entity, EntityGVCLivingBase onride, int id, double range,
    		String model, String tex, String modelf, String texf,int ftime,
    		SoundEvent sound
    		, float f, double w, double h, double z, float xoffset, double yoffset, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid){
    		List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity.targetentity == entity1 && entity.CanAttack(entity1) && entity1 != null && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							boolean flag = onride.getEntitySenses().canSee(entity1);
							double ddy = Math.abs(entity1.posY - onride.posY);
							double dyp = onride.posY + maxy;
							double dym = onride.posY - miny;
							if (dyp > entity1.posY && dym < entity1.posY) {
								double d5 = entity1.posX - onride.posX;
								double d7 = entity1.posZ - onride.posZ;
								entity.rotation_1 = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
								if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
									EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
									AIWeapon_new1( entity,  onride,  id,  range,
								    		 model,  tex,  modelf,  texf, ftime,sound,
								    		 f,  w,  h,  z, xoffset,yoffset,  px,  py,  pz,  lock,  rote,  maxy,  miny,  
								    		 dame,  speed,  recoil,  ex,  extrue,  kazu,   gra,  maxtime,  dameid, (EntityLivingBase)en);
								}else {
									AIWeapon_new1( entity,  onride,  id,  range,
								    		 model,  tex,  modelf,  texf, ftime,sound,
								    		 f,  w,  h,  z, xoffset,yoffset,  px,  py,  pz,  lock,  rote,  maxy,  miny,  
								    		 dame,  speed,  recoil,  ex,  extrue,  kazu,   gra,  maxtime,  dameid,(EntityLivingBase)entity1);
								}
								break;
							}
							
						}
					}
				}
			}
    }
	
	public static void AIWeapon_new1(EntityGVCLivingBase entity, EntityGVCLivingBase onride, int id, double range,
    		String model, String tex, String modelf, String texf,int ftime,
    		SoundEvent sound
    		, float f, double w, double h, double z, float xoffset, double yoffset, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid,
    		EntityLivingBase entity1){
		//entity.isSneaking();
		//entity.setFlag(1, true);
    	if(id < 10){
			for (int ka = 0; ka < kazu; ++ka) {
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
				EntityBBase var3;
				if (id == 1) {
					var3 = new EntityB_BulletAA(entity.world, entity);
				} else if (id == 2) {
					var3 = new EntityB_GrenadeB(entity.world, entity);
				} else if (id == 3) {
					var3 = new EntityB_Shell(entity.world, entity);
				} else if (id == 4) {
					var3 = new EntityB_Missile(entity.world, entity);
					EntityB_Missile mi = (EntityB_Missile) var3;
					mi.speedd = speed;
				} else if (id == 6) {
					var3 = new EntityB_BulletFire(entity.world, entity);
				} else if (id == 7) {
					var3 = new EntityB_BulletAA(entity.world, entity);
					EntityB_BulletAA aa = (EntityB_BulletAA) var3;
					aa.exnear = true;
				} else if (id == 8) {
					var3 = new EntityB_Bullet(entity.world, entity);
					var3.bulletDameID = 1;
				} else if (id == 9) {
					var3 = new EntityT_Grenade(entity.world, entity);
				} else {
					var3 = new EntityB_Bullet(entity.world, entity);
				}
				
				if(entity.tgfx!=null){
				var3.setTexF(entity.tgfx);
				}
				boolean spawnflash = true;
				ItemStack main = entity.getHeldItemMainhand();
				if (main != null && main.getItem() instanceof ItemGunBase && !(main.getItem() instanceof ItemGun_Shield)) {
					ItemGunBase gun = (ItemGunBase) main.getItem();
					String fx = gun.techgunfx;
					
					if(gun.muzzle_flash){
						spawnflash = true;
					}else{
						spawnflash = false;
					}
					
					if(var3.usetgfx){
						//武器-AI-1
						if(fx!=null){
						var3.setTexF(fx);
						}else{
							if(var3 instanceof EntityB_Bullet){
							var3.setTexF("SABulletTrail");
							}else if(var3 instanceof EntityB_Shell){
							var3.setTexF("SAAPTrail");
							}else if(var3 instanceof EntityB_Missile){
							var3.setTexF("SAMissileTrail");
							}else if(var3 instanceof EntityB_BulletFire){
							var3.setTexF("FlamethrowerTrail");
							}else if(var3 instanceof EntityB_BulletAA){
								if(ex<2F){
									var3.setTexF("SAAATrail");
								}else{
									var3.setTexF("LSAAATrail");
								}
							}
						}
					}
				}else if(entity.tgfx==null){
							if(var3 instanceof EntityB_Bullet){
							var3.setTexF("SABulletTrail");
							}else if(var3 instanceof EntityB_Shell){
							var3.setTexF("SAAPTrail");
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

				var3.Bdamege = dame;
				var3.gra = gra;// 0.025
				//var3.friend = entity;
				var3.friend = onride;
				var3.ex = extrue;
				var3.exlevel = ex;
				var3.setModel(model);
				var3.setTex(tex);
				var3.timemax = maxtime;
				var3.bulletDameID = dameid;
				var3.muteki = onride.fire_muteki;/***/
				 var3.mitarget = entity1;
				var3.setLocationAndAngles(px + xx11, py + h, pz + zz11, entity.rotationYaw,
						entity.rotationPitch);
				
				Vec3d elock = entity1.getLookVec();
				double d5 = entity1.posX - entity.posX;
				double d7 = entity1.posZ - entity.posZ;
	            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
				//double xx = elock.x * d3;
				//double zz = elock.z * d3;
				//double xx = (entity1.posX - entity1.prevPosX) * 1;
			    //double zz = (entity1.posZ - entity1.prevPosZ) * 1;
	            double xx = entity1.motionX * d3;
				double zz = entity1.motionZ * d3;
				double var4 = (entity1.posX + xx) - entity.posX - xx11 - offx;
				double var6 = entity1.posY + (double) entity1.getEyeHeight()
						+ 0.200000023841858D - var3.posY - (h / 2);
				double var8 = (entity1.posZ + zz) - entity.posZ - zz11 - offz;
				float var10 = MathHelper.sqrt(var4 * var4 + var8 * var8) * 0.01F;
				if (entity.isPotionActive(MobEffects.BLINDNESS))
		        {
					recoil = recoil * 10F;
		        }
				if(speed > 4F)speed = 4F;
				var3.setThrowableHeading(var4, (var6 + (double) var10) * 1, var8, speed, recoil);
				if (!entity.world.isRemote) {
					entity.world.spawnEntity(var3);
				}
				EntityT_Flash flash = new EntityT_Flash(entity.world, entity);
				flash.gra = 0.03;
				flash.timemax = ftime;
				flash.setModel(modelf);
				flash.setTex(texf);
				flash.setLocationAndAngles(px + xx11, py + h, pz + zz11, entity.rotationYaw,entity.rotationPitch);
				flash.setThrowableHeading(var4, (var6 + (double) var10) * 1, var8, 0.1F, 0);
				if (!entity.world.isRemote && spawnflash) {
					entity.world.spawnEntity(flash);
				}
			}
		}else if(id >= 10 && id < 20){
			{
				Vec3d look = entity.getLookVec();
				double xx11 = 0;
				double zz11 = 0;
				xx11 -= MathHelper.sin(rote * 0.01745329252F) * z;
				zz11 += MathHelper.cos(rote * 0.01745329252F) * z;
				xx11 -= MathHelper.sin(rote * 0.01745329252F + f) * w;
				zz11 += MathHelper.cos(rote * 0.01745329252F + f) * w;
				
				EntityTNTBase var3;
				if(id == 12){
					var3 = new EntityT_Morter(entity.world, entity);
				}
				else{
					var3 = new EntityT_TNT(entity.world, entity);
				}
				var3.Bdamege = dame;
				var3.friend = entity;
				var3.exlevel = ex;
				var3.ex = extrue;
				var3.exinground = true;
			//	var3.exnear = true;
				var3.extime = 200;
				double var4 = entity1.posX - entity.posX - xx11;
				double var6 = entity1.posY + (double) entity1.getEyeHeight()
						+ 0.200000023841858D - var3.posY;
				double var8 = entity1.posZ - entity.posZ - zz11;
				
				double g = 0.10D;
				float var11 = MathHelper.sqrt(var4 * var4 + var8 * var8);
				double sin2s = Math.sin(2*80);
				double vo = Math.sqrt((var11 * g) / sin2s) / 2;
				
				float var10 = MathHelper.sqrt(var4 * var4 + var8 * var8) * 0.01F;
				var3.setThrowableHeading(var4, 70D, var8, (float)vo, 10.0F);
				var3.setLocationAndAngles(px + xx11, py + h, pz + zz11, entity.rotationYaw,
						entity.rotationPitch);
				if (!entity.world.isRemote) {
					entity.world.spawnEntity(var3);
				}
				if (!entity.world.isRemote) {
			//		this.world.createExplosion(this, px + xx11, py + h, pz + zz11, 0.0F, false);
				}
		//		this.motionX += MathHelper.sin(this.rotationYaw * 0.01745329252F)*2;
	    //	    this.motionZ -= MathHelper.cos(this.rotationYaw * 0.01745329252F)*2;
			}
		}else if( id == 21){
			for (int ka = 0; ka < kazu; ++ka) {
				int xx = entity.world.rand.nextInt(6);
				int zz = entity.world.rand.nextInt(6);
				Entity_Flare var8 = new Entity_Flare(entity.world);
				var8.motionY = -2;
				var8.setLocationAndAngles(entity.posX - 3 + xx, entity.posY - 1, entity.posZ - 3 + zz,
						var8.rotationYawHead, var8.rotationPitch);
				if (!entity.world.isRemote) {
					entity.world.spawnEntity(var8);
				}
			}
		}
		else if( id == 51){
			for (int ka = 0; ka < kazu; ++ka) {
				EntityTippedArrow entityarrow =  new EntityTippedArrow(entity.world, entity);
		        double d0 = entity1.posX - entity.posX;
		        double d1 = entity1.getEntityBoundingBox().minY + (double)(entity1.height / 3.0F) - entityarrow.posY;
		        double d2 = entity1.posZ - entity.posZ;
		        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
		        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - entity.world.getDifficulty().getDifficultyId() * 4));
		        if (!entity.world.isRemote) {
		        	entity.world.spawnEntity(entityarrow);
		        }
			}
		}
		
		
    	int ra = entity.world.rand.nextInt(10) + 1;
    	float val = ra * 0.02F;
    	if(sound != null)entity.playSound(sound, 5.0F, 0.9F + val);
		entity.firetrue = true;
    }
	public static void AIWeapon_new2(EntityGVCLivingBase entity, EntityGVCLivingBase onride, int id, double range,
    		String model, String tex, String modelf, String texf,int ftime,
    		SoundEvent sound
    		, float f, double w, double h, double z, float xoffset, double yoffset, double px, double py, double pz, Vec3d lock, float rote, double maxy, double miny
    		, int dame, float speed, float recoil, float ex, boolean extrue, int kazu,  double gra, int maxtime, int dameid
    		){
		//entity.isSneaking();
		//entity.setFlag(1, true);
    	if(id < 10){
			for (int ka = 0; ka < kazu; ++ka) {
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
				EntityBBase var3;
				if (id == 1) {
					var3 = new EntityB_BulletAA(entity.world, entity);
				} else if (id == 2) {
					var3 = new EntityB_GrenadeB(entity.world, entity);
				} else if (id == 3) {
					var3 = new EntityB_Shell(entity.world, entity);
				} else if (id == 4) {
					var3 = new EntityB_Missile(entity.world, entity);
					EntityB_Missile mi = (EntityB_Missile) var3;
					mi.speedd = speed;
				} else if (id == 6) {
					var3 = new EntityB_BulletFire(entity.world, entity);
				} else if (id == 7) {
					var3 = new EntityB_BulletAA(entity.world, entity);
					EntityB_BulletAA aa = (EntityB_BulletAA) var3;
					aa.exnear = true;
				} else if (id == 8) {
					var3 = new EntityB_Missile(entity.world, entity);
					EntityB_Missile mi = (EntityB_Missile) var3;
					mi.speedd = speed;
					mi.autoaim = false;
				} else if (id == 9) {
					var3 = new EntityT_Grenade(entity.world, entity);
				} else {
					var3 = new EntityB_Bullet(entity.world, entity);
				}
				var3.Bdamege = dame;
				var3.gra = gra;// 0.025
				//var3.friend = entity;
				var3.friend = onride;
				var3.ex = extrue;
				var3.exlevel = ex;
				var3.setModel(model);
				var3.setTex(tex);
				var3.timemax = maxtime;
				var3.bulletDameID = dameid;
				var3.muteki = onride.fire_muteki;/***/
				var3.setLocationAndAngles(px + xx11, py + h, pz + zz11, entity.rotationYaw,entity.rotationPitch);
				
				if(entity.tgfx!=null){
				var3.setTexF(entity.tgfx);
				}
				boolean spawnflash = true;
				ItemStack main = entity.getHeldItemMainhand();
				if (main != null && main.getItem() instanceof ItemGunBase && !(main.getItem() instanceof ItemGun_Shield)) {
					ItemGunBase gun = (ItemGunBase) main.getItem();
					String fx = gun.techgunfx;
					if(gun.muzzle_flash){
						spawnflash = true;
					}else{
						spawnflash = false;
					}
					if(var3.usetgfx){
						//武器-AI-2
						if(fx!=null){
						var3.setTexF(fx);
						}else{
							if(var3 instanceof EntityB_Bullet){
							var3.setTexF("SABulletTrail");
							}else if(var3 instanceof EntityB_Shell){
							var3.setTexF("SAAPTrail");
							}else if(var3 instanceof EntityB_Missile){
							var3.setTexF("SAMissileTrail");
							}else if(var3 instanceof EntityB_BulletFire){
							var3.setTexF("FlamethrowerTrail");
							}else if(var3 instanceof EntityB_BulletAA){
								if(ex<2F){
									var3.setTexF("SAAATrail");
								}else{
									var3.setTexF("LSAAATrail");
								}
							}
						}
					}
				}else if(entity.tgfx==null){
							if(var3 instanceof EntityB_Bullet){
							var3.setTexF("SABulletTrail");
							}else if(var3 instanceof EntityB_Shell){
							var3.setTexF("SAAPTrail");
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
				
				double var4 = (entity.getMoveX()) - entity.posX - xx11 - offx;
				double var6 = entity.getMoveY() + (double)0.5
						+ 0.200000023841858D - var3.posY - (h / 2);
				double var8 = (entity.getMoveZ()) - entity.posZ - zz11 - offz;
				float var10 = MathHelper.sqrt(var4 * var4 + var8 * var8) * 0.01F;
				if (entity.isPotionActive(MobEffects.BLINDNESS))
		        {
					recoil = recoil * 10F;
		        }
				if(speed > 4F)speed = 4F;
				var3.setThrowableHeading(var4, (var6 + (double) var10) * 1, var8, speed, recoil);
				if (!entity.world.isRemote) {
					entity.world.spawnEntity(var3);
				}
				EntityT_Flash flash = new EntityT_Flash(entity.world, entity);
				flash.gra = 0.03;
				flash.timemax = ftime;
				flash.setModel(modelf);
				flash.setTex(texf);
				flash.setLocationAndAngles(px + xx11, py + h, pz + zz11, entity.rotationYaw,entity.rotationPitch);
				flash.setThrowableHeading(var4, (var6 + (double) var10) * 1, var8, 0.1F, 0);
				if (!entity.world.isRemote && spawnflash) {
					entity.world.spawnEntity(flash);
				}
			}
		}else if(id >= 10 && id < 20){
			{
				Vec3d look = entity.getLookVec();
				double xx11 = 0;
				double zz11 = 0;
				xx11 -= MathHelper.sin(rote * 0.01745329252F) * z;
				zz11 += MathHelper.cos(rote * 0.01745329252F) * z;
				xx11 -= MathHelper.sin(rote * 0.01745329252F + f) * w;
				zz11 += MathHelper.cos(rote * 0.01745329252F + f) * w;
				
				EntityTNTBase var3;
				if(id == 12){
					var3 = new EntityT_Morter(entity.world, entity);
				}
				else{
					var3 = new EntityT_TNT(entity.world, entity);
				}
				var3.Bdamege = dame;
				var3.friend = entity;
				var3.exlevel = ex;
				var3.ex = extrue;
				var3.exinground = true;
			//	var3.exnear = true;
				var3.extime = 200;
				double var4 = entity.getMoveX() - entity.posX - xx11;
				double var6 = entity.getMoveY() + (double)0.5
						+ 0.200000023841858D - var3.posY;
				double var8 = entity.getMoveZ() - entity.posZ - zz11;
				
				double g = 0.10D;
				float var11 = MathHelper.sqrt(var4 * var4 + var8 * var8);
				double sin2s = Math.sin(2*80);
				double vo = Math.sqrt((var11 * g) / sin2s) / 2;
				
				float var10 = MathHelper.sqrt(var4 * var4 + var8 * var8) * 0.01F;
				var3.setThrowableHeading(var4, 70D, var8, (float)vo, 10.0F);
				var3.setLocationAndAngles(px + xx11, py + h, pz + zz11, entity.rotationYaw,
						entity.rotationPitch);
				if (!entity.world.isRemote) {
					entity.world.spawnEntity(var3);
				}
				if (!entity.world.isRemote) {
			//		this.world.createExplosion(this, px + xx11, py + h, pz + zz11, 0.0F, false);
				}
		//		this.motionX += MathHelper.sin(this.rotationYaw * 0.01745329252F)*2;
	    //	    this.motionZ -= MathHelper.cos(this.rotationYaw * 0.01745329252F)*2;
			}
		}else if( id == 21){
			for (int ka = 0; ka < kazu; ++ka) {
				int xx = entity.world.rand.nextInt(6);
				int zz = entity.world.rand.nextInt(6);
				Entity_Flare var8 = new Entity_Flare(entity.world);
				var8.motionY = -2;
				var8.setLocationAndAngles(entity.posX - 3 + xx, entity.posY - 1, entity.posZ - 3 + zz,
						var8.rotationYawHead, var8.rotationPitch);
				if (!entity.world.isRemote) {
					entity.world.spawnEntity(var8);
				}
			}
		}
		else if( id == 51){
			for (int ka = 0; ka < kazu; ++ka) {
				EntityTippedArrow entityarrow =  new EntityTippedArrow(entity.world, entity);
		        double d0 = entity.getMoveX() - entity.posX;
		        double d1 = entity.getMoveY() + (double)(0.5 / 3.0F) - entityarrow.posY;
		        double d2 = entity.getMoveZ() - entity.posZ;
		        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
		        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - entity.world.getDifficulty().getDifficultyId() * 4));
		        if (!entity.world.isRemote) {
		        	entity.world.spawnEntity(entityarrow);
		        }
			}
		}
		
		
    	int ra = entity.world.rand.nextInt(10) + 1;
    	float val = ra * 0.02F;
    	if(sound != null)entity.playSound(sound, 5.0F, 0.9F + val);
		entity.firetrue = true;
    }
}