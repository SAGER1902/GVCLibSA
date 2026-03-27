package gvclib.entity.living.cnt.ai;

import java.util.List;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.ISoldier;
import gvclib.entity.living.PL_RoteModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AI_EntityMove_Heli_Squad {
	public static void moveheli(EntityGVCLivingBase entity, EntityGVCLivingBase ridding, float f1, float sp,
			float turnspeed, double max, double range, int gy) {
		entity.target = false;
		boolean can_flight = false;
		BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
		int genY = bp.getY() + gy;
		if (entity.posY <= genY) {
			if (entity.throttle < entity.thmax) {
				++entity.throttle;
			}
			entity.thpower = entity.thpower + entity.thmaxa;
			entity.throttle_up = 1;
		}
		else if (entity.posY > genY + 5) {
			if (entity.thpower > entity.thmax / 2) {
				entity.thpower = entity.thpower + entity.thmina;
			}
			if (entity.throttle > entity.thmax / 2) {
				--entity.throttle;
			}
			entity.throttle_up = -1;
		}else {
			entity.throttle_up = 0;
		}
		
		if (entity.posY > genY - 5) {
			can_flight = true;
		}
		
		boolean aiflag = false;
		
		VehicleAI_RotationYawOffset.offset(entity, ridding);
//		ridding.rotationYaw = ridding.rotationYawHead = entity.rotationYaw;
		if (!ridding.getTargetEntity_isLiving()){
			double x = ridding.posX;
			double y = ridding.posY;
			double z = ridding.posZ;
			double han = range;
			AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double) (x - han), (double) (y - han),
					(double) (z - han), (double) (x + han), (double) (y + han), (double) (z + han)))
							.grow(han);
			List<Entity> llist = ridding.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb2);
			if (llist != null) {
				//player
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (ridding.getMoveT() == 0)//follow
						{
							if (entity1 != null && entity1 instanceof EntityPlayerMP) {
								EntityPlayer player = (EntityPlayer) entity1;
								//System.out.println(String.format("m"));
								if (ridding.isOwner(player)) {
									double d5 = entity1.posX - entity.posX;
									double d7 = entity1.posZ - entity.posZ;
									double d6 = entity1.posY - entity.posY;
									double d1 = entity.posY - (entity1.posY);
									double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
									float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
									entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

									{
										rote(entity, turnspeed);
									}

									double ddx = Math.abs(d5);
									double ddz = Math.abs(d7);
									if ((ddx > 15 || ddz > 15)) {
										entity.setAIType(0);
										aiflag = true;
									}else {
										
									}
								}
							}
						}
					}
				}
				//player
			}
		}else
		{
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entity.getEntitySenses().canSee(entity1);
						if (ridding.targetentity == entity1 && ridding.CanAttack(entity1)) {
							double d5 = entity1.posX - entity.posX;
							double d7 = entity1.posZ - entity.posZ;
							double d6 = entity1.posY - entity.posY;
							double d1 = entity.posY - (entity1.posY);
							double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
							float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
							entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

							{
								rote(entity, turnspeed);
							}

							double ddx = Math.abs(d5);
							double ddz = Math.abs(d7);
							if ((ddx > max || ddz > max)) {
								entity.setAIType(0);
							}else {
							//	entity.setAIType(1);
								float angle = -f11 + 10 + entity.angle_offset;
								if(angle < entity.rotationp_max/2) {//近すぎる時
									if(entity.aitypetime > 40) {
										entity.aitypetime = 0;
										entity.setAIType(3);
									}
								}
								if ((ddx < 15 && ddz < 15)) {
									if(entity.aitypetime > 40) {
										entity.aitypetime = 0;
										entity.setAIType(3);
									}
								}
								/*if(angle > entity.rotationp_min/2) {
									
								}*/
							}

							if (flag) {
								ridding.targetentity = (EntityLivingBase) entity1;
							}
							ridding.target = true;
							break;
						} else
						if (ridding.CanAttack(entity1) && flag) {
							{
								ridding.targetentity = (EntityLivingBase) entity1;
								ridding.target = true;
								break;
							}
						}
					}
				}
			}
		}
		if(ridding.getMoveT() == 2){
			{
				double d51 = ridding.getMoveX() - ridding.posX;
				double d71 = ridding.getMoveZ()- ridding.posZ;
				double d61 = ridding.getMoveY() - ridding.posY;
				double d11 = entity.posY - (entity.getMoveY());
				double d31 = (double) MathHelper.sqrt(d51 * d51 + d71 * d71);
				float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
				entity.rote = -((float) Math.atan2(d51, d71)) * 180.0F / (float) Math.PI;

				{
					rote(entity, turnspeed);
				}

				double ddx = Math.abs(d51);
				double ddz = Math.abs(d71);
				if ((ddx > 5 || ddz > 5)) {
					entity.setAIType(0);
					aiflag = true;
				}else {
					
				}
			} 
		}
			
			if(can_flight ){
				if(ridding.getTargetEntity_isLiving()) {
					if(ridding.getMoveT() == 3) {
					}
					//else if(ridding.getMoveT() == 0) {
					//}
					else {
						if(entity.getAIType() == 1) {
							if(entity.throte < 50){
								entity.throte = entity.throte + 2;
							}
						}else if(entity.getAIType() == 2) {
							if(entity.throte > -50){
								entity.throte = entity.throte - 2;
							}
						}else if(entity.getAIType() == 3) {
							entity.rotationPitch = entity.rotationp_max/2;
						}else{
							entity.rotationPitch = entity.rotationp_min/2;
						}
					}
				}else {
					//if(entity.getAIType3() == 0)entity.rotationPitch = entity.rotationp_min/2;
					//entity.rotationPitch = 0;
					if(aiflag)entity.rotationPitch = entity.rotationp_min/2;
				}
				
			}
			entity.prevRotationPitch = entity.rotationPitch;
	}
	
	private static void rote(EntityGVCLivingBase entity, float turnspeed) {
		if(entity.rote > 360 || entity.rote < -360) {
			entity.rote = entity.rote %360;
		}
		float f3 = (float) (entity.rotationYawHead - entity.rote);
		if(entity.rotationYawHead != entity.rote){
    		if(f3 > turnspeed){
				if(f3 > 180F){
					PL_RoteModel.rotemodel(entity,+ turnspeed);
				}else{
					PL_RoteModel.rotemodel(entity,- turnspeed);
				}
			}
			else if(f3 < -turnspeed){
				if(f3 < -180F){
					PL_RoteModel.rotemodel(entity,- turnspeed);
				}else{
					PL_RoteModel.rotemodel(entity,+ turnspeed);
				}
			}
        }
		entity.rotationYaw  = entity.rotationYawHead;
	}
	
}
