package gvclib.entity.living.cnt.ai;

import java.util.List;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.ISoldier;
import gvclib.entity.living.PL_RoteModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.entity.MoverType;
import gvclib.entity.living.EntityVehicleBase;
public class AI_EntityMove_Tank_Squad {
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
			
			if (!ridding.getTargetEntity_isLiving()){
				double x = ridding.posX;
				double y = ridding.posY;
				double z = ridding.posZ;
				double han = range;
				AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double) (x - han), (double) (y - han),
						(double) (z - han), (double) (x + han), (double) (y + han), (double) (z + han)))
								.grow(han);
				List<Entity> llist = ridding.world.getEntitiesWithinAABBExcludingEntity(vehicle, axisalignedbb2);
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
										double d5 = entity1.posX - ridding.posX;
										double d7 = entity1.posZ - ridding.posZ;
										double d6 = entity1.posY - ridding.posY;
										double d1 = ridding.posY - (entity1.posY);
										double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
										float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
										{
											 ridding.rotation = ridding.rotationYaw = ridding.rotationYawHead = ridding.renderYawOffset
									            		=  vehicle.rotation = vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
									            float angle = -f11 + 10;
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
												Vec3d look = vehicle.getLookVec();
												float f3 = (float) (vehicle.rotationYawHead - vehicle.rote);
												if(vehicle.rotationYawHead != vehicle.rote){
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
										double ddx = Math.abs(d5);
										double ddz = Math.abs(d7);
										/*if ((ddx > 30 || ddz > 30)) {
											ridding.setPositionAndUpdate(player.posX, player.posY, player.posZ);
										} 
										else */
										{
											if ((ddx > ridding.squad_followrange*2 || ddz > ridding.squad_followrange*2)) {
												if(vehicle.throttle < vehicle.thmax * 0.75f){
													 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
													}
											}
										}
										//System.out.println(String.format("move"));
									}
								}
							}else if(ridding.getMoveT() == 2) {//pointattack
											{//target
												{
													double d51 = ridding.getMoveX() - ridding.posX;
													double d71 = ridding.getMoveZ()- ridding.posZ;
													double d61 = ridding.getMoveY() - ridding.posY;
													double d11 = ridding.posY - (ridding.getMoveY());
													double d31 = (double) MathHelper.sqrt(d51 * d51 + d71 * d71);
													float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
													{
														float f3 = (float) (vehicle.rotationYawHead - vehicle.rote);
														vehicle.rote =  -((float) Math.atan2(d51, d71)) * 180.0F / (float) Math.PI;
														double ddx1 = Math.abs(d51);
														double ddz1 = Math.abs(d71);
														if ((ddx1 > 3 || ddz1 > 3)) {
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
																	}else{
																		if(vehicle.throttle < vehicle.thmax * 0.75f){
																				 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
																		}
																	}
																	
																if((ddx1 > 25 || ddz1 > 25)) {
																	if(vehicle.throttle < vehicle.thmax * 0.75f){
																			 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
																	}
																}
																	
																}
														 vehicle.rotationYaw  = vehicle.rotationYawHead;
													}else {
															ridding.setMoveT(3);
													}
														ta = true;
													}
													ta = true;
													break;
											} 
									}//target
							}
							if (entity1 != null && entity1 instanceof EntityVehicleBase) {//间距
								//System.out.println(String.format("en"));
								double d5 = entity1.posX - ridding.posX;
								double d7 = entity1.posZ - ridding.posZ;
								double d6 = entity1.posY - ridding.posY;
								double d1 = ridding.posY - (entity1.posY);
								double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								double ddx = Math.abs(d5);
								double ddz = Math.abs(d7);
								if ((ddx < 8 && ddz < 8)) {
									MoveS(vehicle, -sp, 4, entity1.posX, entity1.posY, entity1.posZ, 0);
								}
								//break;
							}
						}
					}
					//player
				}
			}else {
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
										if(ridding.getMoveT() == 2) {//pointattack
												{//target
													{
														double d51 = ridding.getMoveX() - ridding.posX;
														double d71 = ridding.getMoveZ()- ridding.posZ;
														double d61 = ridding.getMoveY() - ridding.posY;
														double d11 = ridding.posY - (ridding.getMoveY());
														double d31 = (double) MathHelper.sqrt(d51 * d51 + d71 * d71);
														float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
														{
															float f3 = (float) (vehicle.rotationYawHead - vehicle.rote);
															vehicle.rote =  -((float) Math.atan2(d51, d71)) * 180.0F / (float) Math.PI;
															double ddx1 = Math.abs(d51);
															double ddz1 = Math.abs(d71);
															if ((ddx1 > 3 || ddz1 > 3)) {
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
																		}else{
																			if(vehicle.throttle < vehicle.thmax * 0.75f){
																					 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
																			}
																		}
																		
																	if((ddx1 > 25 || ddz1 > 25)) {
																		if(vehicle.throttle < vehicle.thmax * 0.75f){
																				 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
																		}
																	}
																		
																	}
															 vehicle.rotationYaw  = vehicle.rotationYawHead;
														}else 
															{
																ridding.setMoveT(3);
															}
															ta = true;
														}
														ta = true;
														break;
												} 
											}//target
										}else if(ridding.getMoveT() == 3){
											
										}else{
										double d5 = entity1.posX - vehicle.posX;
										double d7 = entity1.posZ - vehicle.posZ;
										double d6 = entity1.posY - vehicle.posY;
										double d1 = vehicle.posY - (entity1.posY);
							            double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7);
							            float f11 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
							            ridding.rotation = ridding.rotationYaw = ridding.rotationYawHead = ridding.renderYawOffset
							            		=  vehicle.rotation = vehicle.rote =  -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
							            float angle = -f11 + 10;
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
										if(ridding.getMoveT() == 3) {//wait
											
										}
										else if(ridding.getMoveT() == 2) {//pointattack
											{//target
												{
													double d51 = vehicle.getMoveX() - vehicle.posX;
													double d71 = vehicle.getMoveZ()- vehicle.posZ;
													double d61 = vehicle.getMoveY() - vehicle.posY;
													double d11 = vehicle.posY - (vehicle.getMoveY());
													double d31 = (double) MathHelper.sqrt(d51 * d51 + d71 * d71);
													float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
													{
														vehicle.rote =  -((float) Math.atan2(d51, d71)) * 180.0F / (float) Math.PI;
														 if(vehicle.rotationYawHead != vehicle.rote){
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
														double ddx1 = Math.abs(d51);
														double ddz1 = Math.abs(d71);
														if ((ddx1 > 1 || ddz1 > 1)){
															if(vehicle.throttle < vehicle.thmax * 0.75f){
																 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
																}
														}else 
														{
															vehicle.setMoveT(3);
														}
														ta = true;
													}
													ta = true;
													break;
												} 
											}//target
										}
										else if ((ddx > max || ddz > max)) {
											if(vehicle.throttle < vehicle.thmax * 0.75f){
												 vehicle.throttle = vehicle.throttle + vehicle.thmaxa;
												}
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
	
	public static void MoveS(EntityGVCLivingBase entity, double speed, double han, double ex, double ey, double ez, int id){
		{// 1
			//if(!entity.world.isRemote)
			{
				double d5 = ex - entity.posX;
				double d7 = ez - entity.posZ;
				double root = Math.sqrt(d5 * d5 + d7 * d7);
				//entity.rotationYaw = entity.renderYawOffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				float yawset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				float yaw = yawset * (2 * (float) Math.PI / 360);
				double mox = 0;
				double moy = entity.motionY;
				double moz = 0;
				mox -= MathHelper.sin(yaw) * speed * 1.5;
				moz += MathHelper.cos(yaw) * speed * 1.5;
				if (entity.onGround) {
					
				}
				entity.stepHeight = entity.height * 0.8F;
				//if(entity.world.rand.nextInt(4) == 0) 
				{
					//entity.getNavigator().tryMoveToXYZ(ex, ey, ez, speed * 10);
					if(id == 0 || !(entity.getHealth() > 0)){
						entity.motionX = mox;
						entity.motionZ = moz;
						//entity.motionY = y;
						entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
					}else 
					{
						if(entity.cooltime3 > 20) {
							entity.getNavigator().tryMoveToXYZ(ex, ey, ez, speed * 15);
							entity.cooltime3 = 0;
						}
						
					}
				}
			}
		} // 1
	}

}
