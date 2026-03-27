package gvclib.world;

import gvclib.mod_GVCLib;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GVCExplosionBase {
	
	public static void ExplosionKai(Entity entityIn, Entity en, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking){
		
		if(en == null && entityIn == null)return;
		if(en != null){
			if(strength>1)en.world.playSound((EntityPlayer)null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, 
			(1.0F + (en.world.rand.nextFloat() - en.world.rand.nextFloat()) * 0.2F) * 0.7F);
			if(mod_GVCLib.cfg_bullet_smoke && FMLCommonHandler.instance().getMinecraftServerInstance() != null) {//粒子
				for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
	            {
					GVCLPacketHandler.INSTANCE2.sendTo((new GVCLClientMessageKeyPressed(0, en.getEntityId(), strength, x, y, z)), player);
	            }
			}
		}
		/*{//粒子
			if(en.world != null && en != null){
	    		if (strength >= 2.0F)
	            {
	    			if (strength >= 4.0F)
	                {
	    				int a = 0;
	    				a = (int) (6 + strength);
	    				for (int ii = 0; ii < a; ++ii){
	    					int xx = en.world.rand.nextInt(a);
	    					int zz = en.world.rand.nextInt(a);
	    					int yy = en.world.rand.nextInt(a);
	    					en.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, en.posX - (a/2) + xx, en.posY - (a/2) + yy, en.posZ - (a/2) + zz, 1.0D, 0.0D, 0.0D, new int[0]);
	    				}
	    				en.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, en.posX, en.posY, en.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
	                }else{
	                	en.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, en.posX, en.posY, en.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
	                }
	            }
	            else
	            {
	            	en.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, en.posX, en.posY, en.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
	            }
	    	}
		}*/
		if(entityIn != null && !entityIn.world.isRemote){
			GVCExplosion explosion = new GVCExplosion(entityIn.world, entityIn, x, y, z, strength, isFlaming, isSmoking);
			explosion.doExplosionA();
			explosion.doExplosionB(true);
		}
	}
	
}