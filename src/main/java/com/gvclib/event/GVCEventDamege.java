package gvclib.event;

import gvclib.mod_GVCLib;
import gvclib.entity.EntityBBase;
import gvclib.entity.EntityB_BulletAA;
import gvclib.entity.EntityB_Shell;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GVCEventDamege {
	
	@SubscribeEvent
	public void onKnockBackEvent(LivingKnockBackEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		
		if (target instanceof EntityLivingBase && target != null) 
		{
			Entity entity = event.getAttacker();
			DamageSource source = target.getLastDamageSource();
	    	//if(event.getAttacker() instanceof EntityBBase && entity != null)
			if(source != null)
	        {
	    		//EntityBBase bullet = (EntityBBase) event.getAttacker();
	    		if(source.getDamageType().equals("thrown") || source.getDamageType().equals("newapbullet")) {
	    			event.setStrength(0);
		    		//event.isCanceled();
	    		}
	        }
		}
	}
	
	@SubscribeEvent
	public void onRiddingMountEvent(LivingUpdateEvent event) {//下车键
		Entity target = event.getEntityLiving();
		//Entity ride = event.getEntityBeingMounted();
		if (target != null && target instanceof EntityPlayer) 
		{
			EntityPlayer player = (EntityPlayer) target;
			if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null) {// 1
				EntityGVCLivingBase vehicle = (EntityGVCLivingBase) player.getRidingEntity();
				if(!vehicle.ridding_sneakdismount) {
					boolean kz = mod_GVCLib.proxy.keyz();
					if (kz) {
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(17, vehicle.getEntityId()));
						if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
							for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(17, vehicle.getEntityId()), playermp);
							}
						}
					}
					if(vehicle.serverz) {
						if (player.isRiding())
				        {
							player.dismountRidingEntity();
				        }
						vehicle.serverz = false;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRiddingDisMountEvent(EntityMountEvent event) {//生物强制下车
		Entity target = event.getEntityMounting();
		Entity ride = event.getEntityBeingMounted();
		if (target != null && target instanceof EntityPlayer) 
		{
			EntityPlayer player = (EntityPlayer) target;
			if(ride != null && ride instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase vehicle = (EntityGVCLivingBase)ride;
				if(!vehicle.ridding_sneakdismount && vehicle.getHealth() > 0.0F) {
					if(player.isSneaking()) {
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onDifficulty_level_adjustment(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		DamageSource source = event.getSource();
		float damage = event.getAmount();
		if (target instanceof EntityGVCLivingBase && target != null) 
		{
			EntityGVCLivingBase en = (EntityGVCLivingBase) target;
			if(target.getControllingPassenger() != null && target.getControllingPassenger() instanceof EntityPlayer) {//玩家载具的额外免伤
				if(en.difficulty_adjustment) {
    				float v = 0.5F;
    				int level = target.world.getDifficulty().getDifficultyId();
    				if(level == 1) {
    					v = 0.1F;//0.2F
    				}else if(level == 2) {
    					v = 0.5F;//0.5F
    				}else if(level == 3) {
    					v = 0.75F;//1F
    				}
    				event.setAmount(event.getAmount() * v);
    			}
			}
			/*if (target.getRidingEntity() instanceof EntityGVCLivingBase && target.getRidingEntity() != null) {//GVC生物驾驶员的额外免伤
    			EntityGVCLivingBase en = (EntityGVCLivingBase) target.getRidingEntity();
    			if(en.difficulty_adjustment) {
    				float v = 0.5F;
    				int level = target.world.getDifficulty().getDifficultyId();
    				if(level == 1) {
    					v = 0.5F;
    				}else if(level == 2) {
    					v = 0.75F;
    				}else if(level == 3) {
    					v = 1F;
    				}
    				event.setAmount(event.getAmount() * v);
    			}
    		}*/
		}
	}
	
	
	@SubscribeEvent
	public void onRidding_from_ally(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		DamageSource source = event.getSource();
		float damage = event.getAmount();
		if (target instanceof EntityLivingBase && target != null) 
		{
			Entity entity = source.getTrueSource();
			if (entity != null && entity instanceof EntityLivingBase) {
				EntityLivingBase en2 = (EntityLivingBase) entity;
				
				EntityLivingBase vehicle1 = null;
				//if(en.isRiding()) 
				{
					if (target.getRidingEntity() != null && target.getRidingEntity() instanceof EntityLivingBase) {
						vehicle1 = (EntityLivingBase) target.getRidingEntity();
//						System.out.println("1");
					}
				}
				EntityLivingBase vehicle2 = null;
				//if(en2.isRiding()) 
				{
					if (en2.getRidingEntity() != null && en2.getRidingEntity() instanceof EntityLivingBase) {
						vehicle2 = (EntityLivingBase) en2.getRidingEntity();
//						System.out.println("2");
					}
				}
				if(vehicle1 != null && vehicle2 != null && vehicle1 == vehicle2) {
					event.setAmount(0);
					System.out.println("debug");
				}
			}
			
			if (target instanceof EntityTameable && target.isRiding()) 
			{
				event.setAmount(0);
			}
		}
	}
	
	@SubscribeEvent
	public void onHeadShotEvent(LivingHurtEvent event) {//爆头事件
		EntityLivingBase target = event.getEntityLiving();
		DamageSource source = event.getSource();
		float damage = event.getAmount() * 1.5F;
		if (target instanceof EntityLivingBase && target != null) 
		{
			Entity entity = source.getTrueSource();
	    	if(source.getImmediateSource() instanceof EntityBBase && entity != null)
	        {
	    		EntityBBase bullet = (EntityBBase) source.getImmediateSource();
	    		double target_eye = target.getEyeHeight() + target.posY;
	    		if(target.height >= 1.6F && target.height <= 2.0F && target.width >= 0.4F && target.width <= 0.7F) {
	    			if(bullet.posY >= target_eye - 0.25F) {
	    				event.setAmount(event.getAmount() * 1.5F);
	    				if(entity != null && entity instanceof EntityPlayer){
	    		    		EntityPlayer player = (EntityPlayer) entity;
	    		    		if(player != null){
	    		    			if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
	    							for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
	    								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(11, player.getEntityId(), target.getEntityId(), damage), playermp);
	    							}
	    		    			}
	    		    		}
	    		    	}
	    			}
	    		}
	        }
		}
	}
	
	@SubscribeEvent
	public void onDeadEvent(LivingDeathEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		DamageSource source = event.getSource();
		if (target instanceof EntityLivingBase && target != null) 
		{
			float health = target.getMaxHealth()/2;
			Entity entity = source.getTrueSource();
			if(entity != null && entity instanceof EntityPlayer){
	    		EntityPlayer player = (EntityPlayer) entity;
	    		if(player != null){
	    			if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
						for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
							GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(9, player.getEntityId(), target.getEntityId(), health), playermp);
						}
	    			}
	    		}
	    	}
		}
	}
	
	@SubscribeEvent
	public void onHurtEvent(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		DamageSource source = event.getSource();
		float damage = event.getAmount();
		if (target instanceof EntityLivingBase && target != null) 
		{
			Entity entity = source.getTrueSource();
	    	if(source.getImmediateSource() instanceof EntityBBase && entity != null)
	        {
	    		EntityBBase bullet = (EntityBBase) source.getImmediateSource();
	    		if(bullet.getThrower() != null){
	    	//		GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(10, bullet.getThrower().getEntityId()));
	    		}
	        }
	    	if(mod_GVCLib.cfg_Instant_death_avoidance){
	    		if(source != null && source.isExplosion())
		        {
		    		{
		    			if(target instanceof EntityPlayer){
		    				float hp = target.getHealth();
		    				float dame = target.getHealth() - event.getAmount();
		    				//System.out.println(String.format("5"));
		    				if(hp >= target.getMaxHealth() * 0.75 && dame <= 0) 
		    				{
		    					event.setAmount(target.getMaxHealth() - 1);
		    					//System.out.println(String.format("6"));
		    				}
		    			}
		    		}
		        }
		    	//if(entity instanceof EntityBBase && entity != null)
		    	if(source.getImmediateSource() instanceof EntityBBase)
		        {
					if (target instanceof EntityPlayer) {
						float hp = target.getHealth();
						float dame = target.getHealth() - event.getAmount();
						//System.out.println(String.format("7"));
						if (target.getLastDamageSource() != null && target.getLastDamageSource().isExplosion()) {
							event.setAmount(0);
							//System.out.println(String.format("10"));
						} else if (hp >= target.getMaxHealth() * 0.75 && dame <= 0) {
							event.setAmount(event.getAmount() * 0.25F);
							//System.out.println(String.format("8"));
						}
					}
		        }
	    	}
	    	if(entity != null && entity instanceof EntityPlayer){
	    		EntityPlayer player = (EntityPlayer) entity;
	    		if(player != null){
	    			float hp = target.getHealth() - event.getAmount();
					float health = target.getMaxHealth()/2;
	    			if(hp <= 0F) 
	    			{
	    				if(event.getAmount() > 0) {
	    					if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
    							for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
    								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(9, player.getEntityId(), target.getEntityId(), health), playermp);//击杀
    							}
    		    			}
	    				}
	    			}else {
	    				if(event.getAmount() > 0) {
	    					if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
    							for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
    								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(10, player.getEntityId(), target.getEntityId(), damage), playermp);//命中
    							}
    		    			}
	    				}
	    			}
	    			
	    		}
	    	}
	    	{
	    		if (target.getRidingEntity() instanceof EntityGVCLivingBase && target.getRidingEntity() != null) {
	    			EntityGVCLivingBase en = (EntityGVCLivingBase) target.getRidingEntity();
	    			if(entity != null) {
	    				if(!en.riddng_opentop && !(source.getImmediateSource() instanceof EntityB_Shell 
	    						|| source.getImmediateSource() instanceof EntityB_BulletAA)) {
	    					if(source.getImmediateSource() instanceof EntityBBase) {
	    						EntityBBase bullet = (EntityBBase) source.getImmediateSource();
	    						if(bullet.bulletDameID == 1 || bullet.bulletDameID == 3) {
	    							event.setAmount(event.getAmount() * en.ridding_damege);
	    						}else {
	    							event.setAmount(0);
	    						}
	    					}else {
	    						event.setAmount(0);
			    				//event.isCancelable();
	    					}
		    			}else if(en.riddng_opentop){
		    				event.setAmount(event.getAmount() * en.ridding_damege);
		    			}
		    			if(!en.riddng_opentop && source.getImmediateSource() instanceof EntityB_Shell 
		    					|| source.getImmediateSource() instanceof EntityB_BulletAA){
		    				event.setAmount(event.getAmount() * en.ridding_damege);
		    			}
	    			}
	    			if(source != null && source.damageType.equals("explosion")) {
	    				event.setAmount(event.getAmount() * en.ridding_damege);
	    			}
	    			
	    		}
	    	}
		}
	}

	@SubscribeEvent
	public void onLivingFromHurtEvent(LivingAttackEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		DamageSource source = event.getSource();
		float damage = event.getAmount();
		if (target instanceof EntityLivingBase && target != null) 
		{
			Entity entity = source.getTrueSource();

			if (target.getRidingEntity() instanceof EntityGVCLivingBase && target.getRidingEntity() != null) {
				EntityGVCLivingBase en = (EntityGVCLivingBase) target.getRidingEntity();
				if (entity != null) {
					if (!en.riddng_opentop && !(source.getImmediateSource() instanceof EntityB_Shell
							|| source.getImmediateSource() instanceof EntityB_BulletAA)) {
						if (source.getImmediateSource() instanceof EntityBBase) {
							EntityBBase bullet = (EntityBBase) source.getImmediateSource();
							if (bullet.bulletDameID == 1 || bullet.bulletDameID == 3) {
								//event.setAmount(event.getAmount() * en.ridding_damege);
							} else {
								event.setCanceled(true);
							}
						} else {
							event.setCanceled(true);
							//event.isCancelable();
						}
					} else if (en.riddng_opentop) {
						//event.setAmount(event.getAmount() * en.ridding_damege);
					}
					if (!en.riddng_opentop && source.getImmediateSource() instanceof EntityB_Shell
							|| source.getImmediateSource() instanceof EntityB_BulletAA) {
						//event.setAmount(event.getAmount() * en.ridding_damege);
					}
				}
				if (source != null && source.damageType.equals("explosion")) {
					//event.setAmount(event.getAmount() * en.ridding_damege);
				}

			}
		}
	}
}
