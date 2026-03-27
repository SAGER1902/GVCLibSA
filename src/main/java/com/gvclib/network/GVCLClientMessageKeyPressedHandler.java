package gvclib.network;
import gvclib.mod_GVCLib;
import gvclib.block.tile.TileEntityInvasion;
import gvclib.entity.EntityBBase;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import gvclib.entity.trader.EntityNPCBase;
import gvclib.item.ItemGunBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import net.minecraftforge.fml.common.Loader;
import ra2sa.entity.NewSoldierBase;

public class GVCLClientMessageKeyPressedHandler implements IMessageHandler<GVCLClientMessageKeyPressed, IMessage> {
 
    @Override
    public IMessage onMessage(GVCLClientMessageKeyPressed message, MessageContext ctx) {
        EntityPlayer player = mod_GVCLib.proxy.getEntityPlayerInstance();
    	 //EntityPlayer player = ctx.getServerHandler().player;
        
        if(player == null)return null;
        
        
      //受け取ったMessageクラスのkey変数の数字をチャットに出力
        if(message.key == 1){//开火减少耐久
        	ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemMainhand();
            int li = itemstack.getMaxDamage() - itemstack.getItemDamage();
            if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
            	ItemGunBase gun = (ItemGunBase) itemstack.getItem();
            	gun.zandan = gun.getDamage(itemstack);
    			}
    	    itemstack.damageItem(li, player);
        }/**/
        
        if(message.key == 2){
        	if(player.getEntityId() == message.fre) {
        		player.rotationPitch = (float) (message.posx);
          }
        }/**/
        
        if(message.key == 3){
        	if(player.getEntityId() == message.fre) {
        		//player.rotationPitch = (float) (message.posx);
                ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemMainhand();
                if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
                	ItemGunBase gun = (ItemGunBase) itemstack.getItem();
                	NBTTagCompound nbt = itemstack.getTagCompound();
                	nbt.setBoolean("Recoiled", message.boolean_hantei);
        		}
            }
        }/**/
        if(message.key == 31){
        	if(player.getEntityId() == message.fre) {
        		//player.rotationPitch = (float) (message.posx);
                ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemOffhand();
                if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
                	ItemGunBase gun = (ItemGunBase) itemstack.getItem();
                	NBTTagCompound nbt = itemstack.getTagCompound();
                	nbt.setBoolean("Recoiled", message.boolean_hantei);
        		}
            }
        }/**/
        if(message.key == 4){
        	if(player.getEntityId() == message.fre) {
                ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemMainhand();
                if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
                	ItemGunBase gun = (ItemGunBase) itemstack.getItem();
                	NBTTagCompound nbt = itemstack.getTagCompound();
                	nbt.setBoolean("Cocking", message.boolean_hantei);
        		}
            }
        }/**/
        if(message.key == 41){
        	if(player.getEntityId() == message.fre) {
                ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemOffhand();
                if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
                	ItemGunBase gun = (ItemGunBase) itemstack.getItem();
                	NBTTagCompound nbt = itemstack.getTagCompound();
                	nbt.setBoolean("Cocking", message.boolean_hantei);
        		}
            }
        }/**/
        if(message.key == 5){//开火减少耐久
        	if(player.getEntityId() == message.fre) {
                ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemMainhand();
                if(!itemstack.isEmpty()){
                	//itemstack.shrink(1);
                	int li = itemstack.getMaxDamage() - itemstack.getItemDamage();
                	if (li > 0)itemstack.damageItem(1, (EntityLivingBase) player);
        		}
            }
        }/**/
        if(message.key == 6){
        	if(player.getEntityId() == message.fre) {
            	ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemMainhand();
                if(!itemstack.isEmpty()){
                	//itemstack.shrink(1);
        		}
            }
        }/**/
        
        if(message.key == 10){
    		if(player.getEntityId() == message.fre)
    		{
				Entity hitTarget = player.world.getEntityByID(message.hit_name);
    			NBTTagCompound nbt = player.getEntityData();
    			nbt.setInteger("hitentity", 100);
				nbt.setFloat("hitdamage", message.count);
				nbt.setInteger("hitentity_id", message.hit_name);
    			{
    				//player.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F);
    			}
    		}
    	}
        if(message.key == 9){
    		if(player.getEntityId() == message.fre)
    		{
				Entity hitTarget = player.world.getEntityByID(message.hit_name);
    			NBTTagCompound nbt = player.getEntityData();
    			nbt.setInteger("hitentitydead", 200);
				nbt.setFloat("hitdamage", message.count);
				nbt.setInteger("hitentity_id", message.hit_name);
    		}
    	}
        if(message.key == 11){
    		if(player.getEntityId() == message.fre)
    		{
				Entity hitTarget = player.world.getEntityByID(message.hit_name);
    			NBTTagCompound nbt = player.getEntityData();
    			nbt.setInteger("hitentity_headshot", 200);
				nbt.setFloat("hitdamage", message.count);
				nbt.setInteger("hitentity_id", message.hit_name);
    			{
    				//player.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F);
    			}
    		}
    	}
        
        
        if(message.key == 321){
        	World world;
        	//System.out.println(String.format("world"));
//          System.out.println("debug");
          if(ctx.side.isServer()) {
              world = ctx.getServerHandler().player.world;
          }else{
              world = mod_GVCLib.proxy.getCilentWorld();
          }
          try {
              if(world != null){
            	  //System.out.println(String.format("world"));
                  Entity tgtEntity = world.getEntityByID(message.fre);
                  if(message.data != null && tgtEntity != null && tgtEntity instanceof EntityBBase) {
                      tgtEntity.motionX = message.data.motionX;
                      tgtEntity.motionY = message.data.motionY;
                      tgtEntity.motionZ = message.data.motionZ;
                      tgtEntity.posX = message.data.posX;
                      tgtEntity.posY = message.data.posY;
                      tgtEntity.posZ = message.data.posZ;
                      //System.out.println(String.format("chach"));
                  }
              }
//          bullet = message.bullet.setdata(bullet);
//          System.out.println("bullet "+ bullet);
          }catch (ClassCastException e) {
              e.printStackTrace();
          }catch (Exception e){
              e.printStackTrace();
          }
        }
        
        if(message.key == 61){
        	World world;
          if(ctx.side.isServer()) {
              world = ctx.getServerHandler().player.world;
          }else{
              world = mod_GVCLib.proxy.getCilentWorld();
          }
          try {
              if(world != null){
            	  //System.out.println(String.format("world"));
                  Entity tgtEntity = world.getEntityByID(message.fre);
                  if(tgtEntity != null && tgtEntity instanceof EntityLivingBase) {
                	  NBTTagCompound target_nbt = tgtEntity.getEntityData();
          			if(target_nbt != null) {
          				target_nbt.setInteger("lockon", 100);
          				//System.out.println("2");
          				ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemMainhand();
          	            if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
          	            	ItemGunBase gun = (ItemGunBase) itemstack.getItem();
          	            	gun.mitarget = (EntityLivingBase) tgtEntity;
          	            	NBTTagCompound nbt = itemstack.getTagCompound();
          	            	nbt.setInteger("missile_target", message.fre);
          	    		}
          			}
                  }
              }
          }catch (ClassCastException e) {
              e.printStackTrace();
          }catch (Exception e){
              e.printStackTrace();
          }
        }
        if(message.key == 62){
        	ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemMainhand();
	            if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
	            	ItemGunBase gun = (ItemGunBase) itemstack.getItem();
	            	gun.mitarget = null;
	    		}
        }
        
        {
       	 if(message.key == 1310){
       		if(player.getEntityId() == message.fre) {
       			ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemMainhand();
             	if(!itemstack.isEmpty()){
     				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
     				NBTTagCompound nbtgun = itemstack.getTagCompound();
     				nbtgun.setInteger("RecoiledTime", 0);
     				nbtgun.setBoolean("Recoiled", true);
     			}
       		}
             	
         }
       	 if(message.key == 1311){
       		 if(player.getEntityId() == message.fre) {
       			ItemStack itemstack = ((EntityPlayer)(player)).getHeldItemMainhand();
              	if(!itemstack.isEmpty()){
      				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
      				NBTTagCompound nbtgun = itemstack.getTagCompound();
      				nbtgun.setInteger("RecoiledTime", message.da);
      				nbtgun.setBoolean("Recoiled", false);
      			}
              }
       		}
       }
        
        
        
        
        
        
        
        
        if(message.key == 12){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null && player.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.server2 = true;
        	}
        }
        if(message.key == 13){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null && player.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverspace = true;
        	}
        }
        if(message.key == 14){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null && player.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverx = true;
        	}
        }
        if(message.key == 15){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null && player.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverg = true;
        	}
        }
        if(message.key == 16){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null && player.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverc = true;
        	}
        }
        if(message.key == 17){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null && player.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverz = true;
        	}
        }
        if(message.key == 18){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null && player.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverh = true;
        	}
        } if(message.key == 20){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null && player.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverv = true;
        	}
        }
        
        if(message.key == 100){
        	BlockPos pos = new BlockPos(message.posx, message.posy, message.posz);
        	if(pos != null && player.world.getTileEntity(pos) instanceof TileEntityInvasion) {
        		TileEntityInvasion tile  = (TileEntityInvasion) player.world.getTileEntity(pos);
				tile.spwan_range = message.enid;
        	}
    	}
        if(message.key == 101){
        	BlockPos pos = new BlockPos(message.posx, message.posy, message.posz);
        	if(pos != null && player.world.getTileEntity(pos) instanceof TileEntityInvasion) {
        		TileEntityInvasion tile  = (TileEntityInvasion) player.world.getTileEntity(pos);
				tile.setHP(message.enid);
        	}
    	}
        if(message.key == 102){
        	BlockPos pos = new BlockPos(message.posx, message.posy, message.posz);
        	if(pos != null && player.world.getTileEntity(pos) instanceof TileEntityInvasion) {
        		TileEntityInvasion tile  = (TileEntityInvasion) player.world.getTileEntity(pos);
        		tile.setLevel(message.enid);
        	}
    	}
        if(message.key == 103){
        	BlockPos pos = new BlockPos(message.posx, message.posy, message.posz);
        	if(pos != null && player.world.getTileEntity(pos) instanceof TileEntityInvasion) {
        		TileEntityInvasion tile  = (TileEntityInvasion) player.world.getTileEntity(pos);
        		tile.setTicks(message.enid);
        	}
    	}
        
        
        
        if(message.key == 201){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.setattacktask(true);
        	}
        }
        if(message.key == 202){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.setattacktask(false);
        		base.targetentity = null;
        	}
        }
        
        if(message.key == 203){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		Entity ta = player.world.getEntityByID(message.da);
        		if (ta != null && ta instanceof EntityLivingBase) {
        			base.targetentity = (EntityLivingBase) ta;
        		}
        	}
        }
        
        
        if(message.key == 621){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (player.getRidingEntity() instanceof EntityGVCLivingBase && player.getRidingEntity() != null && player.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.thpera = message.posx;
        	}
    	}
        
        
        if(message.key == 0){
        	if(player != null){
            	Entity en = (Entity) player.world.getEntityByID(message.enid);
    			if(en != null){
    				if(en.world != null) {
    					if (message.strength >= 2.0F) {
    						if (message.strength >= 4.0F) {
    							int a = 0;
    							a = (int) (6 + message.strength);
    							for (int ii = 0; ii < a; ++ii) {
    								int xx = en.world.rand.nextInt(a);
    								int zz = en.world.rand.nextInt(a);
    								int yy = en.world.rand.nextInt(a);
    								if (en.world.isRemote)en.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, true, message.posx - (a / 2) + xx,
    										message.posy - (a / 2) + yy, message.posz - (a / 2) + zz, 1.0D, 0.0D, 0.0D, new int[0]);
    							}
    							if (en.world.isRemote)en.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, true, message.posx, message.posy, message.posz, 1.0D,
    									0.0D, 0.0D, new int[0]);
    						} else {
    							if (en.world.isRemote)en.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, true, message.posx, message.posy, message.posz, 1.0D,
    									0.0D, 0.0D, new int[0]);
    						}
    					} else {
    						if (en.world.isRemote)en.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, true, message.posx, message.posy, message.posz, 1.0D, 0.0D,
    								0.0D, new int[0]);
    					}
    				}
    	    	}
    		}
        }
        
        if(message.key == 2000){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.startuptime = message.da;
        	}
        }
        if(message.key == 2001){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
			if(Loader.isModLoaded("ra2sa")){
				if (en != null && en instanceof NewSoldierBase) {// 1
					NewSoldierBase base = (NewSoldierBase) en;
					base.cooltime = message.da;
				}
			}
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime = message.da;
        	}
        }
        if(message.key == 2002){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime2 = message.da;
        	}
        }
        if(message.key == 2003){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime3 = message.da;
        	}
        }
        if(message.key == 2004){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime4 = message.da;
        	}
        }
        if(message.key == 2005){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime5 = message.da;
        	}
        }
        if(message.key == 2006){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime6 = message.da;
        	}
        }
        if(message.key == 2007){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.gun_count1 = message.da;
        	}
        }
        if(message.key == 2008){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.gun_count2 = message.da;
        	}
        }
        if(message.key == 2009){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.gun_count3 = message.da;
        	}
        }
        if(message.key == 2010){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.gun_count4 = message.da;
        	}
        }
        if(message.key == 2011){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.aitypetime = message.da;
        	}
        }
        if(message.key == 2012){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.aitypetime2 = message.da;
        	}
        }
        if(message.key == 2013){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.aitypetime3 = message.da;
        	}
        }
        if(message.key == 2014){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.counter1 = message.boolean_hantei;
        	}
        }
        if(message.key == 2015){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.counter2 = message.boolean_hantei;
        	}
        }
        if(message.key == 2016){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.counter3 = message.boolean_hantei;
        	}
        }
        if(message.key == 2017){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.counter4 = message.boolean_hantei;
        	}
        }
        
        
        if(message.key == 1500){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityNPCBase) {// 1
        		EntityNPCBase base = (EntityNPCBase) en;
        		if(base.sell_now_id < base.sell_page - 1) {
        			++base.sell_now_id;
        		}
        		int page = 10 * base.sell_now_id;
        		for(int x = 0; x < 9; ++x) {
        			base.hand[0 + x + page] = false;
	        	}
        	}
        }
        if(message.key == 1501){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityNPCBase) {// 1
        		EntityNPCBase base = (EntityNPCBase) en;
        		if(base.sell_now_id > 0) {
        			--base.sell_now_id;
        		}
        		int page = 10 * base.sell_now_id;
        		for(int x = 0; x < 9; ++x) {
        			base.hand[0 + x + page] = false;
	        	}
        	}
        }
        
        if(message.key == 1502){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityNPCBase) {// 1
        		EntityNPCBase base = (EntityNPCBase) en;
        		if(base.buy_now_id < base.buy_page - 1) {
        			++base.buy_now_id;
        		}
        	}
        }
        if(message.key == 1503){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityNPCBase) {// 1
        		EntityNPCBase base = (EntityNPCBase) en;
        		if(base.buy_now_id > 0) {
        			--base.buy_now_id;
        		}
        	}
        }
        if(message.key == 801){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityLivingBase) {// 1
        		
        		for (int k = 0; k < 5; ++k) {
					double d2 = en.world.rand.nextGaussian() * 0.02D;
					double d0 = en.world.rand.nextGaussian() * 0.02D;
					double d1 = en.world.rand.nextGaussian() * 0.02D;
					en.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
							en.posX + (double) (en.world.rand.nextFloat() * en.width * 2.0F) - (double) en.width,
							en.posY + (double) (en.world.rand.nextFloat() * en.height),
							en.posZ + (double) (en.world.rand.nextFloat() * en.width * 2.0F) - (double) en.width,
							d2, d0, d1);
				}
        	}
        }
        
        if (message.key == 3063) {
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityMobVehicle_Inv_Base) {// 1
        		EntityMobVehicle_Inv_Base base = (EntityMobVehicle_Inv_Base) en;
				Item item = new Item().getItemById(message.da);
				if(item != null) {
					base.horseChest.setInventorySlotContents(message.enid, new ItemStack(item, (int)message.kaku));
					//System.out.println(String.format("0"));
				}
        	}
		}
        
        if(message.key == 422){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityLivingBase) {// 1
        		EntityLivingBase base = (EntityLivingBase) en;
        		base.onGround = message.boolean_hantei;
        	}
        }
        
        if(message.key == 444){
        	EntityLivingBase en = (EntityLivingBase) player.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase gvc_en = (EntityGVCLivingBase) en;
        		if(en.getControllingPassenger() != null) {
        			en.getControllingPassenger().setPositionAndUpdate(gvc_en.getMoveX(), gvc_en.getMoveY(), gvc_en.getMoveZ());
        			en.getControllingPassenger().dismountRidingEntity();
        			
        		}
        	}
        }
        
		return null;
    }
}