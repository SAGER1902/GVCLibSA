package gvclib.entity.living.cnt.ai;

import java.util.List;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.Chunk;

public class AI_EntityMove_Turret {
	public static void move(EntityVehicleBase vehicle, EntityGVCLivingBase ridding, float f1, float sp, float turnspeed, double max, double range, boolean antiair) {
		{// 1
			/*Chunk chunk = vehicle.world.getChunkFromBlockCoords(new BlockPos(vehicle.posX, vehicle.posY, vehicle.posZ));
			if (chunk.isEmpty())return;*/
			if(ridding.rotationYawHead > 360F || ridding.rotationYawHead < -360F){
				ridding.rotationYawHead = 0;
				ridding.rotationYaw = 0;
				ridding.prevRotationYaw = 0;
				ridding.prevRotationYawHead = 0;
				ridding.renderYawOffset = 0;
			}
			if(ridding.rotationYawHead > 180F){
				ridding.rotationYawHead = -179F;
				ridding.rotationYaw = -179F;
				ridding.prevRotationYaw = -179F;
				ridding.prevRotationYawHead = -179F;
				ridding.renderYawOffset = -179F;
			}
			if(ridding.rotationYawHead < -180F){
				ridding.rotationYawHead = 179F;
				ridding.rotationYaw = 179F;
				ridding.prevRotationYaw = 179F;
				ridding.prevRotationYawHead = 179F;
				ridding.renderYawOffset = 179F;
			}
			if(vehicle.rotationYawHead > 360F || vehicle.rotationYawHead < -360F){
				vehicle.rotationYawHead = 0;
				vehicle.rotationYaw = 0;
				vehicle.prevRotationYaw = 0;
				vehicle.prevRotationYawHead = 0;
				vehicle.renderYawOffset = 0;
			}
			if(vehicle.rotationYawHead > 180F){
				vehicle.rotationYawHead = -179F;
				vehicle.rotationYaw = -179F;
				vehicle.prevRotationYaw = -179F;
				vehicle.prevRotationYawHead = -179F;
				vehicle.renderYawOffset = -179F;
			}
			if(vehicle.rotationYawHead < -180F){
				vehicle.rotationYawHead = 179F;
				vehicle.rotationYaw = 179F;
				vehicle.prevRotationYaw = 179F;
				vehicle.prevRotationYawHead = 179F;
				vehicle.renderYawOffset = 179F;
			}
			boolean ta = false;
			
			Entity target = null;
			Entity[] targetlist = new Entity[1024];
			int targetplus = 0;
			
			List<Entity> llist = vehicle.world.getEntitiesWithinAABBExcludingEntity(vehicle,
					vehicle.getEntityBoundingBox().expand(vehicle.motionX, vehicle.motionY, vehicle.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith() && entity1 != null && vehicle.getHealth() > 0.0F) {
						boolean flag = vehicle.getEntitySenses().canSee(entity1);
						if (ridding.CanAttack(entity1) && entity1 != null && flag) {
							double d5 = entity1.posX - vehicle.posX;
							double d7 = entity1.posZ - vehicle.posZ;
							double d6 = entity1.posY - vehicle.posY;
							double d1 = vehicle.posY - (entity1.posY);
				            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
				            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
				            float rotep_offset = -f11 + 10;
				            if(rotep_offset <= vehicle.rotationp_min && rotep_offset >= vehicle.rotationp_max) {
				            	targetlist[targetplus] = entity1;
								++targetplus;
				            }
						}

					}
				}
			}
			for(int xs = 0; xs < targetlist.length; ++xs) {
				if(targetlist[xs] != null) {
					if(antiair) {
						if(target != null) {
							if(targetlist[xs].posY > target.posY) {
								target = targetlist[xs];
							}
						}else {
							target = targetlist[xs];
						}
					}else {
						target = targetlist[xs];
						break;
					}
				}
			}
			
			
			
			if(target != null) {
				boolean flag = vehicle.getEntitySenses().canSee(target);
				{
					double d5 = target.posX - vehicle.posX;
					double d7 = target.posZ - vehicle.posZ;
					double d6 = target.posY - vehicle.posY;
					double d1 = vehicle.posY - (target.posY);
		            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
		            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
		            ridding.rotation = ridding.rotationYaw = ridding.rotationYawHead = ridding.renderYawOffset
		            		=  vehicle.rotation = vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
		            ridding.rotationp = ridding.prevRotationPitch = vehicle.rotationp = vehicle.rotationPitch = -f11 + 10;
		            
					Vec3d look = vehicle.getLookVec();
					float f3 = (float) (vehicle.rotationYawHead - vehicle.rote);
					if(flag){
						ridding.targetentity = (EntityLivingBase) target;
					}
					ta = true;
				}
			}
			
			if(ta) {
				float f3 = (float) (vehicle.rotationYaw - vehicle.rote);
				 if(vehicle.rotationYaw != vehicle.rote){
	         		if(f3 > 1){
	 					if(f3 > 180F){
	 						vehicle.rotationYaw = vehicle.rotationYaw + vehicle.turnspeed;
	 					}else{
	 						vehicle.rotationYaw = vehicle.rotationYaw - vehicle.turnspeed;
	 					}
	 				}
	 				else if(f3 < -1){
	 					if(f3 < -180F){
	 						vehicle.rotationYaw = vehicle.rotationYaw - vehicle.turnspeed;
	 					}else{
	 						vehicle.rotationYaw = vehicle.rotationYaw + vehicle.turnspeed;
	 					}
	 				}
		            }
			}
			
			/*if(!ta){
				double xPosition = 0;
			    double yPosition = 0;
			    double zPosition = 0;
				if (vehicle.getIdleTime() >= 100)
		        {
		        }
		        else if (vehicle.getRNG().nextInt(120) != 0)
		        {
		        }
		        else
		        {
		            Vec3d vec3 = RandomPositionGenerator.findRandomTarget(vehicle, 10, 7);

		            if (vec3 == null)
		            {
		            }
		            else
		            {
		                xPosition = vec3.x;
		                yPosition = vec3.y;
		                zPosition = vec3.z;
		            }
		            vehicle.rotation = vehicle.getRNG().nextInt(120) - 60;
		        }
				vehicle.getNavigator().tryMoveToXYZ(xPosition, yPosition, zPosition, 1D);
			}*/
		} // 1
	}
}
