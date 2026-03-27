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

public class AI_EntityMove_AirCraft_Squad {
	public static void movefighter(EntityGVCLivingBase entity, EntityGVCLivingBase ridding, float f1, float sp,
			float turnspeed,
			double max, double range, int minheight) {
		Vec3d look = entity.getLookVec();
		boolean can_flight = false;
		float rop = 0;
		VehicleAI_RotationYawOffset.offset(entity, ridding);
		
		BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
		int genY = bp.getY() + minheight;
		if (entity.posY > genY - 5) {
			can_flight = true;
		}
		if(!can_flight && entity.thpower > 10) {
			if (entity.rotationp > -30)entity.rotationp = -20;
			if (entity.rotationPitch > -30)entity.rotationPitch = -20;
			if (entity.prevRotationPitch > -30)entity.prevRotationPitch = -20;
		}
		{
			if (entity.throttle < entity.thmax) {
				++entity.throttle;
			}
			if (entity.thpower < entity.thmax) {
				entity.thpower = entity.thpower + entity.thmaxa;
			}
		}
		
		if (!ridding.getTargetEntity_isLiving()){
			float f11 = 0;
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
						if (ridding.getMoveT() == 0)//跟随状态
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
									f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
									{
										entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F
												/ (float) Math.PI;

										double ddx = Math.abs(d5);
										double ddz = Math.abs(d7);
										entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
										rop = -f11;
										if(entity.rote > 360 || entity.rote < -360) {
											entity.rote = entity.rote %360;
										}
										if (entity.rote > 180F) {
											entity.rote = -179F;
										}
										if (entity.rote < -180F) {
											entity.rote = 179F;
										}
										float f3 = (float) (entity.rotationYawHead - entity.rote);
										//if(!entity.world.isRemote)
										if ((ddx > ridding.squad_followrange*2 || ddz > ridding.squad_followrange*2) && can_flight) {
											if (entity.rotationYawHead != entity.rote) {
												if (f3 > turnspeed) {
													if (f3 > 180F) {
														PL_RoteModel.rotemodel(entity, +turnspeed);
														if (entity.throte < 50) {
															entity.throte = entity.throte + 2;
														}
													} else {
														PL_RoteModel.rotemodel(entity, -turnspeed);
														if (entity.throte > -50) {
															entity.throte = entity.throte - 2;
														}
													}
												} else if (f3 < -turnspeed) {
													if (f3 < -180F) {
														PL_RoteModel.rotemodel(entity, -turnspeed);
														if (entity.throte > -50) {
															entity.throte = entity.throte - 2;
														}
													} else {
														PL_RoteModel.rotemodel(entity, +turnspeed);
														if (entity.throte < 50) {
															entity.throte = entity.throte + 2;
														}
													}
												}
											}
										}
									}
									break;
								}
							}
						}
					}
				}
				//player
			}
			movePitch(entity, f11, can_flight, minheight, turnspeed, bp);
			
			if (entity.getHealth() > 0.0F) {
				movestay(entity, ridding, turnspeed, turnspeed, turnspeed, max, range);
			}
		}else {
//////////////////////////////////////////////////////////////////////////
			Entity tagetentity = null;
			Entity[] targetlist = new Entity[1024];
			int targetplus = 0;
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity
					.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entity.getEntitySenses().canSee(entity1);
						if (ridding.CanAttack(entity1) && entity1 != null) {
							if(ridding.targetentity == entity1 && ((EntityLivingBase) entity1).getHealth() > 0.0F)
							{
								tagetentity = entity1;
								if (flag) {
									ridding.targetentity = (EntityLivingBase) entity1;
								}
								ridding.target = true;
								break;
							}else {
								if(ridding.targetentity == null)
								{
									if (ridding.CanAttack(entity1) && flag) 
									{
										targetlist[targetplus] = entity1;
										++targetplus;
										ridding.targetentity = (EntityLivingBase) entity1;
										ridding.target = true;
										//break;
									}
									ridding.target = false;
								}
								//break;
							}
						}
					}
				}
			}
			if(tagetentity == null) {
				for(int xs = 0; xs < targetlist.length; ++xs) {
					if(targetlist[xs] != null) {
							if(ridding.targetentity != null) {
								if(!targetlist[xs].onGround) {
									ridding.targetentity = (EntityLivingBase)  targetlist[xs];
									ridding.target = true;
								}
							}else {
								ridding.targetentity = (EntityLivingBase)  targetlist[xs];
								ridding.target = true;
							}
					}
				}
			}
			
//////////////////////////////////////////////////////////////////////////
			float f11 = 0;
			if(tagetentity != null && ridding.getMoveT() != 3 && ridding.getMoveT() != 3) {//非等待状态
				double d5 = tagetentity.posX - entity.posX;
				double d7 = tagetentity.posZ - entity.posZ;
				double d6 = tagetentity.posY - entity.posY;
				double d1 = entity.posY - (tagetentity.posY);
				double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
				f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
				entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F
						/ (float) Math.PI;

				double ddx = Math.abs(d5);
				double ddz = Math.abs(d7);
				entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				rop = -f11;
				if(entity.rote > 360 || entity.rote < -360) {
					entity.rote = entity.rote %360;
				}
				if (entity.rote > 180F) {
					entity.rote = -179F;
				}
				if (entity.rote < -180F) {
					entity.rote = 179F;
				}
				float f3 = (float) (entity.rotationYawHead - entity.rote);
				//if(!entity.world.isRemote)
				if (entity.getAIType() != 3 && can_flight) {
					if (entity.rotationYawHead != entity.rote) {
						if (f3 > turnspeed) {
							if (f3 > 180F) {
								PL_RoteModel.rotemodel(entity, +turnspeed);
								if (entity.throte < 50) {
									entity.throte = entity.throte + 2;
								}
							} else {
								PL_RoteModel.rotemodel(entity, -turnspeed);
								if (entity.throte > -50) {
									entity.throte = entity.throte - 2;
								}
							}
						} else if (f3 < -turnspeed) {
							if (f3 < -180F) {
								PL_RoteModel.rotemodel(entity, -turnspeed);
								if (entity.throte > -50) {
									entity.throte = entity.throte - 2;
								}
							} else {
								PL_RoteModel.rotemodel(entity, +turnspeed);
								if (entity.throte < 50) {
									entity.throte = entity.throte + 2;
								}
							}
						}
					}
				}
			}
			if(ridding.getMoveT() == 2) {//前往地点
				{
					double d5 = ridding.getMoveX() - ridding.posX;
					double d7 = ridding.getMoveZ() - ridding.posZ;
					double d6 = ridding.getMoveY() - ridding.posY;
					double d1 = ridding.posY - (ridding.getMoveY());
					double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
					f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
					{
						entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F
								/ (float) Math.PI;

						double ddx = Math.abs(d5);
						double ddz = Math.abs(d7);
						entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
						rop = -f11;
						if(entity.rote > 360 || entity.rote < -360) {
							entity.rote = entity.rote %360;
						}
						if (entity.rote > 180F) {
							entity.rote = -179F;
						}
						if (entity.rote < -180F) {
							entity.rote = 179F;
						}
						float f3 = (float) (entity.rotationYawHead - entity.rote);
						//if(!entity.world.isRemote)
						if ((ddx > ridding.squad_followrange*2 || ddz > ridding.squad_followrange*2) && can_flight) {
							if (entity.rotationYawHead != entity.rote) {
								if (f3 > turnspeed) {
									if (f3 > 180F) {
										PL_RoteModel.rotemodel(entity, +turnspeed);
										if (entity.throte < 50) {
											entity.throte = entity.throte + 2;
										}
									} else {
										PL_RoteModel.rotemodel(entity, -turnspeed);
										if (entity.throte > -50) {
											entity.throte = entity.throte - 2;
										}
									}
								} else if (f3 < -turnspeed) {
									if (f3 < -180F) {
										PL_RoteModel.rotemodel(entity, -turnspeed);
										if (entity.throte > -50) {
											entity.throte = entity.throte - 2;
										}
									} else {
										PL_RoteModel.rotemodel(entity, +turnspeed);
										if (entity.throte < 50) {
											entity.throte = entity.throte + 2;
										}
									}
								}
							}
						}
					}
				}
				/*double d5 = ridding.getMoveX() - ridding.posX;
				double d7 = ridding.getMoveZ() - ridding.posZ;
				double d6 = ridding.getMoveY() - ridding.posY;
				double d1 = ridding.posY - (tagetentity.posY);
				double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
				f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
				entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F
						/ (float) Math.PI;

				double ddx = Math.abs(d5);
				double ddz = Math.abs(d7);
				entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				rop = -f11;
				if(entity.rote > 360 || entity.rote < -360) {
					entity.rote = entity.rote %360;
				}
				if (entity.rote > 180F) {
					entity.rote = -179F;
				}
				if (entity.rote < -180F) {
					entity.rote = 179F;
				}
				float f3 = (float) (entity.rotationYawHead - entity.rote);
				if (can_flight) {
					if (entity.rotationYawHead != entity.rote) {
						if (f3 > turnspeed) {
							if (f3 > 180F) {
								PL_RoteModel.rotemodel(entity, +turnspeed);
								if (entity.throte < 50) {
									entity.throte = entity.throte + 2;
								}
							} else {
								PL_RoteModel.rotemodel(entity, -turnspeed);
								if (entity.throte > -50) {
									entity.throte = entity.throte - 2;
								}
							}
						} else if (f3 < -turnspeed) {
							if (f3 < -180F) {
								PL_RoteModel.rotemodel(entity, -turnspeed);
								if (entity.throte > -50) {
									entity.throte = entity.throte - 2;
								}
							} else {
								PL_RoteModel.rotemodel(entity, +turnspeed);
								if (entity.throte < 50) {
									entity.throte = entity.throte + 2;
								}
							}
						}
					}
				}*/
			}
			
			movePitch(entity, f11, can_flight, minheight, turnspeed, bp);
			
			if (entity.getHealth() > 0.0F && ridding.getMoveT() == 3) {//等待状态
				movestay(entity, ridding, turnspeed, turnspeed, turnspeed, max, range);
			}
		}

		//if( entity.throttle >= 1)
		{
			if (entity.thpera < 360) {
				entity.thpera = entity.thpera + (entity.throttle * 2);
			} else {
				entity.thpera = 0;
			}
		}
	}
	
	public static void movePitch(EntityGVCLivingBase entity, float f11, boolean can_flight, int minheight, float turnspeed, BlockPos bp) {
		/*float rotep_offset = -f11 + 10;
		entity.rotep = rotep_offset;
		if(can_flight) {
			if(entity.posY < bp.getY() + minheight) {
				if (entity.rotationPitch > -20) {
					entity.rotationp = entity.rotationp - turnspeed*2;
					entity.rotationPitch = entity.rotationPitch - turnspeed*2;
					entity.prevRotationPitch = entity.prevRotationPitch - turnspeed*2;
				}
			}else {
				entity.rotationp = entity.rotep;
				entity.rotationPitch = entity.rotep;
				entity.prevRotationPitch = entity.rotep;
			}
		}*/
	}
	
	public static void movestay(EntityGVCLivingBase entity, EntityGVCLivingBase ridding, float f1, float sp,
			float turnspeed, double max, double range) {
		Vec3d look = entity.getLookVec();
		if (entity.getMoveX() != 0) {//没有前往地点时
			double d5 = entity.getMoveX() - entity.posX;
			double d7 = entity.getMoveZ() - entity.posZ;

			entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

			double ddx = Math.abs(d5);
			double ddz = Math.abs(d7);
			entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
			if(entity.rote > 360 || entity.rote < -360) {
				entity.rote = entity.rote %360;
			}
			if (entity.rote > 180F) {
				entity.rote = -179F;
			}
			if (entity.rote < -180F) {
				entity.rote = 179F;
			}
			
			float f3 = (float) (entity.rotationYawHead - entity.rote);

			{
				if (entity.rotationYawHead != entity.rote) {
					if (f3 > turnspeed) {
						if (f3 > 180F) {
							PL_RoteModel.rotemodel(entity, +turnspeed);
							if (entity.throte < 50) {
								entity.throte = entity.throte + 2;
							}
						} else {
							PL_RoteModel.rotemodel(entity, -turnspeed);
							if (entity.throte > -50) {
								entity.throte = entity.throte - 2;
							}
						}
					} else if (f3 < -turnspeed) {
						if (f3 < -180F) {
							PL_RoteModel.rotemodel(entity, -turnspeed);
							if (entity.throte > -50) {
								entity.throte = entity.throte - 2;
							}
						} else {
							PL_RoteModel.rotemodel(entity, +turnspeed);
							if (entity.throte < 50) {
								entity.throte = entity.throte + 2;
							}
						}
					}
				}
			}
		} else {
			if (!entity.world.isRemote) {
				entity.rotationYawHead = entity.rotationYawHead + turnspeed*0.5F;
				entity.rotationYaw = entity.rotationYaw + turnspeed*0.5F;
				entity.prevRotationYaw = entity.prevRotationYaw + turnspeed*0.5F;
				entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed*0.5F;
			}
		}

		if (entity.throttle < entity.thmax * 0.25) {
			++entity.throttle;
			entity.thpower = 1;
		}

	}
}
