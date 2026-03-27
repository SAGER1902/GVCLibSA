package gvclib.entity.living;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.Chunk;

public abstract class AI_EntityMoveTank
{
	public static void move(EntityGVCLivingBase vehicle, EntityGVCLivingBase ridding, float f1, float sp, float turnspeed, double max, double range) {
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
			if(ridding.getMoveT() == 1)
			{
				{
					double d5 = ridding.getMoveX() - vehicle.posX;
					double d7 = ridding.getMoveZ() - vehicle.posZ;
					double d6 = ridding.getMoveY() - vehicle.posY;
					double d1 = vehicle.posY - (vehicle.getMoveY());
		            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
		            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
		            ridding.rotation = ridding.rotationYaw = ridding.rotationYawHead = ridding.renderYawOffset
		            		=  vehicle.rotation = vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
		            ridding.rotationp = ridding.prevRotationPitch = vehicle.rotationp = vehicle.rotationPitch = -f11 + 10+ vehicle.angle_offset;
					double ddx = Math.abs(d5);
					double ddz = Math.abs(d7);
					if(vehicle.rote > 180F){
						vehicle.rote = -179F;
					}
					if(vehicle.rote < -180F){
						vehicle.rote = 179F;
					}
					//System.out.println(String.format("input"));
					Vec3d look = vehicle.getLookVec();
					float f3 = (float) (vehicle.rotationYawHead - vehicle.rote);
					
					if ((ddx > max || ddz > max)) {
						if(vehicle.throttle < vehicle.thmax * 0.75f){
							 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
							}
						/*vehicle.throttle = vehicle.thmax;
						 if(vehicle.thpower < vehicle.thmax){
							 vehicle.thpower = vehicle.thpower + vehicle.thmaxa;
							}*/
						 if(vehicle.rotationYawHead != vehicle.rote){
							 //System.out.println(String.format("input"));
			            		if(f3 > turnspeed){
			    					if(f3 > 180F){
			    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
			    					}else{
			    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
			    					}
			    				}
			    				else if(f3 < -turnspeed){
			    					if(f3 < -180F){
			    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
			    					}else{
			    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
			    					}
			    				}
				            }
						 vehicle.rotationYaw  = vehicle.rotationYawHead;
					}
					{
						List<Entity> llist = vehicle.world.getEntitiesWithinAABBExcludingEntity(vehicle,
								vehicle.getEntityBoundingBox().expand(vehicle.motionX, vehicle.motionY, vehicle.motionZ)
										.grow(range));
						if (llist != null) {
							for (int lj = 0; lj < llist.size(); lj++) {
								Entity entity1 = (Entity) llist.get(lj);
								if (entity1.canBeCollidedWith() && entity1 != null && vehicle.getHealth() > 0.0F) {
									boolean flag = vehicle.getEntitySenses().canSee(entity1);
									if (vehicle.CanAttack(entity1) && entity1 != null) {
										if (vehicle.targetentity == entity1) {
											{
												double d51 = entity1.posX - vehicle.posX;
												double d71 = entity1.posZ - vehicle.posZ;
												double d61 = entity1.posY - vehicle.posY;
												double d11 = vehicle.posY - (entity1.posY);
												double d31 = (double) MathHelper.sqrt(d51 * d51 + d71 * d71);
												float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
												vehicle.rotation = -((float) Math.atan2(d51, d71)) * 180.0F
														/ (float) Math.PI;
												vehicle.rotationp = vehicle.prevRotationPitch = vehicle.rotationp = vehicle.rotationPitch = -f11
														+ 10 + vehicle.angle_offset;
												ridding.rotation = ridding.rotationYaw = ridding.rotationYawHead = ridding.renderYawOffset 
														= -((float) Math.atan2(d51, d71)) * 180.0F / (float) Math.PI;
												ridding.rotationp = ridding.prevRotationPitch = ridding.rotationp = ridding.rotationPitch = -f11
														+ 10 + vehicle.angle_offset;
												if (flag) {
													vehicle.targetentity = (EntityLivingBase) entity1;
												}
												ta = true;
											}
											break;
										} else if (vehicle.targetentity == null) {
											if (vehicle.CanAttack(entity1) && flag) {
												vehicle.targetentity = (EntityLivingBase) entity1;
												ta = true;
												break;
											}
										}
									}
								}
							}
						}
					}
					ta = true;
				}
				/*if((vehicle.motionX <= 0.1 && vehicle.motionX >= - 0.1) && (vehicle.motionZ <= 0.1 && vehicle.motionZ >= - 0.1) && vehicle.motionY <= 0){
					++ridding.stoptime;
				}else{
					ridding.stoptime = 0;
				}
				if(ridding.stoptime > 1200){
					if (!ridding.world.isRemote) {
						ridding.setDead();
					}
				}*/
				
			}///MoveT == 1
			
			List<Entity> llist = vehicle.world.getEntitiesWithinAABBExcludingEntity(vehicle,
					vehicle.getEntityBoundingBox().expand(vehicle.motionX, vehicle.motionY, vehicle.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith() && entity1 != null && vehicle.getHealth() > 0.0F) {
						boolean flag = vehicle.getEntitySenses().canSee(entity1);
						if (ridding.CanAttack(entity1) && entity1 != null ) {
							
							if(ridding.targetentity == entity1)
							 {
								{
									double d5 = entity1.posX - vehicle.posX;
									double d7 = entity1.posZ - vehicle.posZ;
									double d6 = entity1.posY - vehicle.posY;
									double d1 = vehicle.posY - (entity1.posY);
						            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
						            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
						            //ridding.rotationYawHead = vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						            vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						            if(vehicle.ridding_rotation_lock) {
						            	vehicle.rotation = ridding.rotationYaw;
						            }else {
						            	ridding.rotation = ridding.rotationYaw = ridding.renderYawOffset =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						            	 vehicle.rotation =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						            }
						            float angle = -f11 + 10 + vehicle.angle_offset;
						            if(angle < vehicle.rotationp_max) {
						            	angle = vehicle.rotationp_max;
						            }
						            if(angle > vehicle.rotationp_min) {
						            	angle = vehicle.rotationp_min;
						            }
						            ridding.rotationp = ridding.prevRotationPitch = vehicle.rotationp = vehicle.rotationPitch = angle;
						            
									double ddx = Math.abs(d5);
									double ddz = Math.abs(d7);
									if(vehicle.rote > 180F){
										vehicle.rote = -179F;
									}
									if(vehicle.rote < -180F){
										vehicle.rote = 179F;
									}
									//System.out.println(String.format("input"));
									Vec3d look = vehicle.getLookVec();
									float f3 = (float) (vehicle.rotationYawHead - vehicle.rote);
									
									if ((ddx > max || ddz > max)) {
										if(vehicle.throttle < vehicle.thmax * 0.75f){
											 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
											}
										/*vehicle.throttle = vehicle.thmax;
										 if(vehicle.thpower < vehicle.thmax){
											 vehicle.thpower = vehicle.thpower + vehicle.thmaxa;
											}*/
										 if(vehicle.rotationYawHead != vehicle.rote){
											 //System.out.println(String.format("input"));
							            		if(f3 > turnspeed){
							    					if(f3 > 180F){
							    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
							    					}else{
							    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
							    					}
							    				}
							    				else if(f3 < -turnspeed){
							    					if(f3 < -180F){
							    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
							    					}else{
							    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
							    					}
							    				}
								            }
										 vehicle.rotationYaw  = vehicle.rotationYawHead;
									}
									if(flag){
										ridding.targetentity = (EntityLivingBase) entity1;
									}
									ta = true;
								}
								break;
							}
							else 
							if(ridding.targetentity == null){
								if (ridding.CanAttack(entity1) && flag) 
								{
									ridding.targetentity = (EntityLivingBase) entity1;
									ta = true;
									break;
								}
							}
							
						}

					}
				}
			}
			if(!ta){
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
			}
		} // 1
}
	
	public static void movespg(EntityGVCLivingBase vehicle, EntityGVCLivingBase ridding, float f1, float sp, float turnspeed, double max, double range) {
		{// 1
			boolean ta = false;
			if(ridding.getMoveT() == 1)
			{
				{
					double d5 = vehicle.getMoveX() - vehicle.posX;
					double d7 = vehicle.getMoveZ() - vehicle.posZ;
					double d6 = vehicle.getMoveY() - vehicle.posY;
					double d1 = vehicle.posY - (vehicle.getMoveY());
		            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
		            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
		            ridding.rotation = ridding.rotationYaw = ridding.rotationYawHead = ridding.renderYawOffset
		            		=  vehicle.rotation = vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
		            ridding.rotationp = ridding.prevRotationPitch = vehicle.rotationp = vehicle.rotationPitch = -f11 + 10;
					double ddx = Math.abs(d5);
					double ddz = Math.abs(d7);
					if(vehicle.rote > 180F){
						vehicle.rote = -179F;
					}
					if(vehicle.rote < -180F){
						vehicle.rote = 179F;
					}
					//System.out.println(String.format("input"));
					Vec3d look = vehicle.getLookVec();
					float f3 = (float) (vehicle.rotationYawHead - vehicle.rote);
					
					if ((ddx > max || ddz > max)) {
						 /*if(vehicle.throttle < vehicle.thmax * 0.75f){
							 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
							}*/
						/*vehicle.throttle = vehicle.thmax;
						 if(vehicle.thpower < vehicle.thmax){
							 vehicle.thpower = vehicle.thpower + vehicle.thmaxa;
							}*/
						 if(vehicle.rotationYawHead != vehicle.rote){
							 //System.out.println(String.format("input"));
			            		if(f3 > turnspeed){
			    					if(f3 > 180F){
			    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
			    					}else{
			    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
			    					}
			    				}
			    				else if(f3 < -turnspeed){
			    					if(f3 < -180F){
			    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
			    					}else{
			    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
			    					}
			    				}
				            }
						 vehicle.rotationYaw  = vehicle.rotationYawHead;
					}
					ta = true;
				}
			}
			
			List<Entity> llist = vehicle.world.getEntitiesWithinAABBExcludingEntity(vehicle,
					vehicle.getEntityBoundingBox().expand(vehicle.motionX, vehicle.motionY, vehicle.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith() && entity1 != null && vehicle.getHealth() > 0.0F) {
						boolean flag = vehicle.getEntitySenses().canSee(entity1);
						if(vehicle.getMoveT() == 1)
						{
							if(vehicle.targetentity == entity1){
								double d5 = entity1.posX - vehicle.posX;
								double d7 = entity1.posZ - vehicle.posZ;
								double d51 = vehicle.getMoveX() - vehicle.posX;
								double d71 = vehicle.getMoveZ() - vehicle.posZ;
								
								double d6 = entity1.posY - vehicle.posY;
								double d1 = vehicle.posY - (entity1.posY);
					            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
					            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
					            vehicle.rotation =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					            vehicle.rote =  -((float) Math.atan2(d51, d71)) * 180.0F / (float) Math.PI;
					            vehicle.rotationp = vehicle.rotationPitch = -f11 + 10;
								double ddx = Math.abs(d51);
								double ddz = Math.abs(d71);
								if ((ddx > 10 || ddz > 10)) {
									if (!vehicle.world.isRemote) {
										if (vehicle.rotationYawHead != vehicle.rote) {
											if (vehicle.rotationYawHead < vehicle.rote) {
												vehicle.rotationYawHead = vehicle.rotationYawHead + turnspeed;
												vehicle.rotationYaw = vehicle.rotationYaw + turnspeed;
												vehicle.prevRotationYaw = vehicle.prevRotationYaw + turnspeed;
												vehicle.prevRotationYawHead = vehicle.prevRotationYawHead + turnspeed;
											} else if (vehicle.rotationYawHead > vehicle.rote) {
												vehicle.rotationYawHead = vehicle.rotationYawHead - turnspeed;
												vehicle.rotationYaw = vehicle.rotationYaw - turnspeed;
												vehicle.prevRotationYaw = vehicle.prevRotationYaw - turnspeed;
												vehicle.prevRotationYawHead = vehicle.prevRotationYawHead - turnspeed;
											}
										}
									}
									if(!vehicle.world.isRemote)
									{
										vehicle.motionX -= MathHelper.sin(f1) * sp;
										vehicle.motionZ += MathHelper.cos(f1) * sp;
										vehicle.move(MoverType.SELF,vehicle.motionX, vehicle.motionY, vehicle.motionZ);
									}
								}
								if(flag){
									ridding.targetentity = (EntityLivingBase) entity1;
								}
								ta = true;
							}
							else if(ridding.targetentity == null){
								if (ridding.CanAttack(entity1) && flag) 
								{
									ridding.targetentity = (EntityLivingBase) entity1;
									ta = true;
									break;
								}
							}
						}else
						if(ridding.targetentity == entity1){
							{
								double d5 = entity1.posX - vehicle.posX;
								double d7 = entity1.posZ - vehicle.posZ;
								double d6 = entity1.posY - vehicle.posY;
								double d1 = vehicle.posY - (entity1.posY);
					            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
					            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
					            vehicle.rotation = vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					            vehicle.rotationp = vehicle.rotationPitch = -f11 + 10;
								double ddx = Math.abs(d5);
								double ddz = Math.abs(d7);
								if ((ddx > max || ddz > max)) {
									if (!vehicle.world.isRemote) {
										if (vehicle.rotationYawHead != vehicle.rote) {
											if (vehicle.rotationYawHead < vehicle.rote) {
												vehicle.rotationYawHead = vehicle.rotationYawHead + turnspeed;
												vehicle.rotationYaw = vehicle.rotationYaw + turnspeed;
												vehicle.prevRotationYaw = vehicle.prevRotationYaw + turnspeed;
												vehicle.prevRotationYawHead = vehicle.prevRotationYawHead + turnspeed;
											} else if (vehicle.rotationYawHead > vehicle.rote) {
												vehicle.rotationYawHead = vehicle.rotationYawHead - turnspeed;
												vehicle.rotationYaw = vehicle.rotationYaw - turnspeed;
												vehicle.prevRotationYaw = vehicle.prevRotationYaw - turnspeed;
												vehicle.prevRotationYawHead = vehicle.prevRotationYawHead - turnspeed;
											}
										}
									}
									if(!vehicle.world.isRemote)
									{
										vehicle.motionX -= MathHelper.sin(f1) * sp;
										vehicle.motionZ += MathHelper.cos(f1) * sp;
										vehicle.move(MoverType.SELF,vehicle.motionX, vehicle.motionY, vehicle.motionZ);
									}
								}
								if(flag){
									ridding.targetentity = (EntityLivingBase) entity1;
								}
								ta = true;
							}
							break;
						}
						else if(ridding.targetentity == null){
							if (ridding.CanAttack(entity1) && flag) 
							{
								ridding.targetentity = (EntityLivingBase) entity1;
								ta = true;
								break;
							}
						}
						
					}
				}
			}
			if(!ta){
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
			}
		} // 1
}
	
	
	public static void moveBoat(EntityGVCLivingBase vehicle, EntityGVCLivingBase ridding, float f1, float sp, float turnspeed, double max, double range) {
		{// 1
			Chunk chunk = vehicle.world.getChunkFromBlockCoords(new BlockPos(vehicle.posX, vehicle.posY, vehicle.posZ));
			if (chunk.isEmpty())return;
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
			
			if(ridding.targetentity != null) {
				if(vehicle.throttle < vehicle.thmax * 0.75f){
					 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
					}
			}
			
			boolean ta = false;
			if(ridding.getMoveT() == 1)
			{
				{
					double d5 = ridding.getMoveX() - vehicle.posX;
					double d7 = ridding.getMoveZ() - vehicle.posZ;
					double d6 = ridding.getMoveY() - vehicle.posY;
					double d1 = vehicle.posY - (vehicle.getMoveY());
		            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
		            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
		            ridding.rotation = ridding.rotationYaw = ridding.rotationYawHead = ridding.renderYawOffset
		            		=  vehicle.rotation = vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
		            ridding.rotationp = ridding.prevRotationPitch = vehicle.rotationp = vehicle.rotationPitch = -f11 + 10+ vehicle.angle_offset;
					double ddx = Math.abs(d5);
					double ddz = Math.abs(d7);
					if(vehicle.rote > 180F){
						vehicle.rote = -179F;
					}
					if(vehicle.rote < -180F){
						vehicle.rote = 179F;
					}
					//System.out.println(String.format("input"));
					Vec3d look = vehicle.getLookVec();
					float f3 = (float) (vehicle.rotationYawHead - vehicle.rote);
					
					if ((ddx > max || ddz > max)) {
						/*if(vehicle.throttle < vehicle.thmax * 0.75f){
							 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
							}
						/*vehicle.throttle = vehicle.thmax;
						 if(vehicle.thpower < vehicle.thmax){
							 vehicle.thpower = vehicle.thpower + vehicle.thmaxa;
							}*/
						 if(vehicle.rotationYawHead != vehicle.rote){
							 //System.out.println(String.format("input"));
			            		if(f3 > turnspeed){
			    					if(f3 > 180F){
			    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
			    					}else{
			    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
			    					}
			    				}
			    				else if(f3 < -turnspeed){
			    					if(f3 < -180F){
			    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
			    					}else{
			    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
			    					}
			    				}
				            }
						 vehicle.rotationYaw  = vehicle.rotationYawHead;
					}
					{
						List<Entity> llist = vehicle.world.getEntitiesWithinAABBExcludingEntity(vehicle,
								vehicle.getEntityBoundingBox().expand(vehicle.motionX, vehicle.motionY, vehicle.motionZ)
										.grow(range));
						if (llist != null) {
							for (int lj = 0; lj < llist.size(); lj++) {
								Entity entity1 = (Entity) llist.get(lj);
								if (entity1.canBeCollidedWith() && entity1 != null && vehicle.getHealth() > 0.0F) {
									boolean flag = vehicle.getEntitySenses().canSee(entity1);
									if (vehicle.CanAttack(entity1) && entity1 != null) {
										if (vehicle.targetentity == entity1) {
											{
												double d51 = entity1.posX - vehicle.posX;
												double d71 = entity1.posZ - vehicle.posZ;
												double d61 = entity1.posY - vehicle.posY;
												double d11 = vehicle.posY - (entity1.posY);
												double d31 = (double) MathHelper.sqrt(d51 * d51 + d71 * d71);
												float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
												vehicle.rotation = -((float) Math.atan2(d51, d71)) * 180.0F
														/ (float) Math.PI;
												vehicle.rotationp = vehicle.prevRotationPitch = vehicle.rotationp = vehicle.rotationPitch = -f11
														+ 10 + vehicle.angle_offset;
												ridding.rotation = ridding.rotationYaw = ridding.rotationYawHead = ridding.renderYawOffset 
														= -((float) Math.atan2(d51, d71)) * 180.0F / (float) Math.PI;
												ridding.rotationp = ridding.prevRotationPitch = ridding.rotationp = ridding.rotationPitch = -f11
														+ 10 + vehicle.angle_offset;
												if (flag) {
													vehicle.targetentity = (EntityLivingBase) entity1;
												}
												ta = true;
											}
											break;
										} else if (vehicle.targetentity == null) {
											if (vehicle.CanAttack(entity1) && flag) {
												vehicle.targetentity = (EntityLivingBase) entity1;
												ta = true;
												break;
											}
										}
									}
								}
							}
						}
					}
					ta = true;
				}
				
			}///MoveT == 1
			
			List<Entity> llist = vehicle.world.getEntitiesWithinAABBExcludingEntity(vehicle,
					vehicle.getEntityBoundingBox().expand(vehicle.motionX, vehicle.motionY, vehicle.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith() && entity1 != null && vehicle.getHealth() > 0.0F) {
						boolean flag = vehicle.getEntitySenses().canSee(entity1);
						if (ridding.CanAttack(entity1) && entity1 != null ) {
							
							if(ridding.targetentity == entity1)
							 {
								{
									double d5 = entity1.posX - vehicle.posX;
									double d7 = entity1.posZ - vehicle.posZ;
									double d6 = entity1.posY - vehicle.posY;
									double d1 = vehicle.posY - (entity1.posY);
						            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
						            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
						            //ridding.rotationYawHead = vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						            vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						            if(vehicle.ridding_rotation_lock) {
						            	vehicle.rotation = ridding.rotationYaw;
						            }else {
						            	ridding.rotation = ridding.rotationYaw = ridding.renderYawOffset =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						            	 vehicle.rotation =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						            }
						            float angle = -f11 + 10 + vehicle.angle_offset;
						            if(angle < vehicle.rotationp_max) {
						            	angle = vehicle.rotationp_max;
						            }
						            if(angle > vehicle.rotationp_min) {
						            	angle = vehicle.rotationp_min;
						            }
						            ridding.rotationp = ridding.prevRotationPitch = vehicle.rotationp = vehicle.rotationPitch = angle;
						            
									double ddx = Math.abs(d5);
									double ddz = Math.abs(d7);
									if(vehicle.rote > 180F){
										vehicle.rote = -179F;
									}
									if(vehicle.rote < -180F){
										vehicle.rote = 179F;
									}
									//System.out.println(String.format("input"));
									Vec3d look = vehicle.getLookVec();
									float f3 = (float) (vehicle.rotationYawHead - vehicle.rote);
									
									if ((ddx > max || ddz > max)) {
										/*if(vehicle.throttle < vehicle.thmax * 0.75f){
											 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
											}*/
										/*vehicle.throttle = vehicle.thmax;
										 if(vehicle.thpower < vehicle.thmax){
											 vehicle.thpower = vehicle.thpower + vehicle.thmaxa;
											}*/
										 if(vehicle.rotationYawHead != vehicle.rote){
											 //System.out.println(String.format("input"));
							            		if(f3 > turnspeed){
							    					if(f3 > 180F){
							    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
							    					}else{
							    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
							    					}
							    				}
							    				else if(f3 < -turnspeed){
							    					if(f3 < -180F){
							    						PL_RoteModel.rotemodel(vehicle,- turnspeed);
							    					}else{
							    						PL_RoteModel.rotemodel(vehicle,+ turnspeed);
							    					}
							    				}
								            }
										 vehicle.rotationYaw  = vehicle.rotationYawHead;
									}else {
										
									}
									if(flag){
										ridding.targetentity = (EntityLivingBase) entity1;
									}
									ta = true;
								}
								break;
							}
							else 
							if(ridding.targetentity == null){
								if (ridding.CanAttack(entity1) && flag) 
								{
									ridding.targetentity = (EntityLivingBase) entity1;
									ta = true;
									break;
								}
							}
							
						}

					}
				}
			}
			if(!ta){
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
			}
		} // 1
}
}