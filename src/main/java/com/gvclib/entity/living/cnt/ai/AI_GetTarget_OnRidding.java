package gvclib.entity.living.cnt.ai;

import java.util.List;

import gvclib.entity.living.EntityGVCLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class AI_GetTarget_OnRidding {
	public static void load(EntityGVCLivingBase riddingentity, double range, double maxy, double miny, float yawbase,  float maxyaw, float minyaw){
		double k = riddingentity.posX;
		double l = riddingentity.posY;
		double i = riddingentity.posZ;
		double han = range;
		
		AxisAlignedBB axisalignedbb;
		
		if(riddingentity.getMoveT() == 1) {
			double xx = riddingentity.getMoveX();
			double yy = riddingentity.getMoveY();
			double zz = riddingentity.getMoveZ();
			axisalignedbb	= (new AxisAlignedBB((double) (xx - han), (double) (yy - miny),(double) (zz - han), 
					(double) (xx + han), (double) (yy + maxy), (double) (zz + han)))
							.grow(1);
		}else {
			axisalignedbb	= (new AxisAlignedBB((double) (k - han), (double) (l - miny),(double) (i - han), 
					(double) (k + han), (double) (l + maxy), (double) (i + han)))
							.grow(1);
		}
		
		
		if(!riddingentity.world.isRemote){
			List<Entity> llist = riddingentity.world.getEntitiesWithinAABBExcludingEntity(riddingentity,axisalignedbb);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (riddingentity.CanAttack(entity1) && entity1 != null 
								&& ((EntityLivingBase) entity1).getHealth() > 0.0F && riddingentity.getHealth() > 0.0F) 
						{
							if(riddingentity.targetentity == null) {
								boolean flag = riddingentity.getEntitySenses().canSee(entity1);
								double d5 = entity1.posX - riddingentity.posX;
								double d7 = entity1.posZ - riddingentity.posZ;
								double d6 = entity1.posY - riddingentity.posY;
								double d1 = riddingentity.posY - (entity1.posY);
					            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
					            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
					            float rotep_offset = -f11 + 10;
					            float yaw= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					            if(rotep_offset <= riddingentity.rotationp_min && rotep_offset >= riddingentity.rotationp_max && flag) {
					            	boolean ok = false;
					            	if(yaw > yawbase - minyaw && yaw < yawbase + maxyaw){
					            		ok = true;
					            	}
					            	if(yawbase - minyaw < -180){
					            		float y = yawbase - minyaw + 360;
					            		if(yaw < yawbase + maxyaw && yaw + 360 > y) {
					            			ok = true;
					            		}
					            	}
					            	if(yawbase + maxyaw > 180){
					            		float y = yawbase + maxyaw - 360;
					            		if(yaw > yawbase - minyaw && yaw - 360 < y) {
					            			ok = true;
					            		}
					            	}
									if(ok){
										riddingentity.targetentity = (EntityLivingBase) entity1;
										riddingentity.setMobMode(1);
										riddingentity.sneak = true;
										riddingentity.setattacktask(true);
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
	
	public static boolean getRange(EntityGVCLivingBase entity, double range, double maxy, double miny, float yawbase,  float maxyaw, float minyaw){
		boolean task = false;
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - miny),(double) (i - han), 
				(double) (k + han), (double) (l + maxy), (double) (i + han)))
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
						double dyp = entity.posY + maxy;
						double dym = entity.posY - miny;
						double d5 = entity1.posX - entity.posX;
						double d7 = entity1.posZ - entity.posZ;
						float yaw= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						if (dyp > entity1.posY && dym < entity1.posY) {
							boolean ok = false;
			            	if(yaw > yawbase - minyaw && yaw < yawbase + maxyaw){
			            		ok = true;
			            	}
			            	if(yawbase - minyaw < -180){
			            		float y = yawbase - minyaw + 360;
			            		if(yaw < yawbase + maxyaw && yaw + 360 > y) {
			            			ok = true;
			            		}
			            	}
			            	if(yawbase + maxyaw > 180){
			            		float y = yawbase + maxyaw - 360;
			            		if(yaw > yawbase - minyaw && yaw - 360 < y) {
			            			ok = true;
			            		}
			            	}
			            	if(ok) {
			            		if(entity1.isRiding() && entity1.getRidingEntity() != null && entity1.getRidingEntity() instanceof EntityLivingBase) {
									EntityLivingBase en = (EntityLivingBase) entity1.getRidingEntity();
									if(en.getHealth()> 0.0F ) {
										task = true;
										break;
									}
								}else{
									task = true;
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
	
	
	
	public static boolean load_LivingBase(EntityLivingBase entity, EntityGVCLivingBase vehicle,  int id,  double range, double maxy, double miny, float yawbase,  float maxyaw, float minyaw){
		double k = entity.posX;
		double l = entity.posY;
		double i = entity.posZ;
		double han = range;
		boolean ok = false;
		//if(!entity.world.isRemote)
		{
			AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - miny),(double) (i - han), 
					(double) (k + han), (double) (l + maxy), (double) (i + han)))
							.grow(1);
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,axisalignedbb);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity1 instanceof IMob && entity1 != null 
								&& ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) 
						{
							{
								boolean flag = vehicle.getEntitySenses().canSee(entity1);
								double d5 = entity1.posX - entity.posX;
								double d7 = entity1.posZ - entity.posZ;
								double d6 = entity1.posY - entity.posY;
								double d1 = entity.posY - (entity1.posY);
					            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
					            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
					            float rotep_offset = -f11 + 10;
					            float yaw= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					            if(rotep_offset <= 60 && rotep_offset >= -60 && flag) {
					            	
					            	if(yaw > yawbase - minyaw && yaw < yawbase + maxyaw){
					            		ok = true;
					            	}
					            	if(yawbase - minyaw < -180){
					            		float y = yawbase - minyaw + 360;
					            		if(yaw < yawbase + maxyaw && yaw + 360 > y) {
					            			ok = true;
					            		}
					            	}
					            	if(yawbase + maxyaw > 180){
					            		float y = yawbase + maxyaw - 360;
					            		if(yaw > yawbase - minyaw && yaw - 360 < y) {
					            			ok = true;
					            		}
					            	}
									if(ok){
										if(id == 3) {
											vehicle.rotation_3  = yaw;
											vehicle.rotationp_3 = rotep_offset;
										}
										if(id == 4) {
											vehicle.rotation_4  = yaw;
											vehicle.rotationp_4 = rotep_offset;
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return ok;
	}
	
	
}
