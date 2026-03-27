package gvclib.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.FMLClientHandler;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

public class RenderAngleSystem {

    
	public static boolean angle_rote(EntityVehicleBase entity) {
		boolean flag1 = false;
		if (((Minecraft.getMinecraft()).gameSettings.thirdPersonView != 0
                || (Minecraft.getMinecraft()).player.getRidingEntity() != entity) && entity.onGround) {
            double maxX = (entity.getEntityBoundingBox()).maxX - 0.0;
            double minX = (entity.getEntityBoundingBox()).minX + 0.0;
            double maxZ = (entity.getEntityBoundingBox()).maxZ - 0.0;
            double minZ = (entity.getEntityBoundingBox()).minZ + 0.0;
            
            double hanX = (maxX - minX) / 2 + minX;
            double hanZ = (maxZ - minZ) / 2 + minZ;
            
            int offset_size = 2;
            if (entity.world.isAirBlock((new BlockPos(minX, entity.posY, minZ)).down()) 
            		//&& entity.world.getBlockState((new BlockPos(minX, entity.posY, minZ)).down()).getCollisionBoundingBox(entity.world, (new BlockPos(minX, entity.posY, minZ)).down()) != Block.NULL_AABB
            		) {
                flag1 = true;
    //            GlStateManager.rotate(entity.width * 5, -1.0F, 0.0F, 1.0F);
                if(entity.inclined_x > -entity.width * offset_size)entity.inclined_x = entity.inclined_x - 2;//--entity.inclined_x;
                if(entity.inclined_z < entity.width * offset_size)entity.inclined_z = entity.inclined_z + 2;//++entity.inclined_z;
                
                if (!entity.world.isAirBlock((new BlockPos(hanX, entity.posY, minZ)).down())) {
                	if(entity.inclined_z > -entity.width * offset_size)entity.inclined_z = entity.inclined_z - 2;//--entity.inclined_z;
             	}
                if (!entity.world.isAirBlock((new BlockPos(minX, entity.posY, hanZ)).down())) {
                	if(entity.inclined_x < entity.width * offset_size)entity.inclined_x = entity.inclined_x + 2;//++entity.inclined_x;
                 }
            }
            if (entity.world.isAirBlock((new BlockPos(maxX, entity.posY, minZ)).down())
            		//&& entity.world.getBlockState((new BlockPos(maxX, entity.posY, minZ)).down()).getCollisionBoundingBox(entity.world, (new BlockPos(maxX, entity.posY, minZ)).down()) != Block.NULL_AABB
            		) {
                flag1 = true;
     //           GlStateManager.rotate(entity.width * 5, -1.0F, 0.0F, -1.0F);
                if(entity.inclined_x > -entity.width * offset_size)entity.inclined_x = entity.inclined_x - 2;//--entity.inclined_x;
                if(entity.inclined_z > -entity.width * offset_size)entity.inclined_z = entity.inclined_z - 2;//--entity.inclined_z;
                
                if (!entity.world.isAirBlock((new BlockPos(hanX, entity.posY, minZ)).down())) {
                	if(entity.inclined_z < entity.width * offset_size)entity.inclined_z = entity.inclined_z + 2;//++entity.inclined_z;
             	}
                if (!entity.world.isAirBlock((new BlockPos(maxX, entity.posY, hanZ)).down())) {
                	if(entity.inclined_x < entity.width * offset_size)entity.inclined_x = entity.inclined_x + 2;//++entity.inclined_x;
                 }
            }
            if (entity.world.isAirBlock((new BlockPos(maxX, entity.posY, maxZ)).down())
            		//&& entity.world.getBlockState((new BlockPos(maxX, entity.posY, maxZ)).down()).getCollisionBoundingBox(entity.world, (new BlockPos(maxX, entity.posY, maxZ)).down()) != Block.NULL_AABB
            		) {
                flag1 = true;
   //             GlStateManager.rotate(entity.width * 5, 1.0F, 0.0F, -1.0F);
                if(entity.inclined_x < entity.width * offset_size)entity.inclined_x = entity.inclined_x + 2;//++entity.inclined_x;
                if(entity.inclined_z > -entity.width * offset_size)entity.inclined_z = entity.inclined_z - 2;//--entity.inclined_z;
                
                if (!entity.world.isAirBlock((new BlockPos(hanX, entity.posY, maxZ)).down())) {
                	if(entity.inclined_z < entity.width * offset_size)entity.inclined_z = entity.inclined_z + 2;//++entity.inclined_z;
             	}
                if (!entity.world.isAirBlock((new BlockPos(maxX, entity.posY, hanZ)).down())) {
                	if(entity.inclined_x > -entity.width * offset_size)entity.inclined_x = entity.inclined_x - 2;//--entity.inclined_x;
                 }
            }
            if (entity.world.isAirBlock((new BlockPos(minX, entity.posY, maxZ)).down())
            		//&& entity.world.getBlockState((new BlockPos(minX, entity.posY, maxZ)).down()).getCollisionBoundingBox(entity.world, (new BlockPos(minX, entity.posY, maxZ)).down()) != Block.NULL_AABB
            		) {
                flag1 = true;
     //           GlStateManager.rotate(entity.width * 5, 1.0F, 0.0F, 1.0F);
                if(entity.inclined_x < entity.width * offset_size)entity.inclined_x = entity.inclined_x + 2;//++entity.inclined_x;
                if(entity.inclined_z < entity.width * offset_size)entity.inclined_z = entity.inclined_z + 2;//++entity.inclined_z;
                
                if (!entity.world.isAirBlock((new BlockPos(hanX, entity.posY, maxZ)).down())) {
                	if(entity.inclined_z > -entity.width * offset_size)entity.inclined_z = entity.inclined_z - 2;//--entity.inclined_z;
             	}
                if (!entity.world.isAirBlock((new BlockPos(minX, entity.posY, hanZ)).down())) {
                	if(entity.inclined_x > -entity.width * offset_size)entity.inclined_x = entity.inclined_x - 2;//--entity.inclined_x;
                 }
            }
            
            
           /* if (entity.world.isAirBlock((new BlockPos(hanX, entity.posY, minZ)).down())) {
            	 //if(entity.inclined_z < entity.width * offset_size)entity.inclined_z = entity.inclined_z + 2;//++entity.inclined_z;
       		 }
            if (entity.world.isAirBlock((new BlockPos(hanX, entity.posY, maxZ)).down())) {
            	//if(entity.inclined_z > -entity.width * offset_size)entity.inclined_z = entity.inclined_z - 2;//--entity.inclined_z;
      		 }
            
            if (entity.world.isAirBlock((new BlockPos(minX, entity.posY, hanZ)).down())) {
               // if(entity.inclined_x > -entity.width * offset_size)entity.inclined_x = entity.inclined_x - 2;//--entity.inclined_x;
            }
            if (entity.world.isAirBlock((new BlockPos(maxX, entity.posY, hanZ)).down())) {
            	//if(entity.inclined_x < entity.width * offset_size)entity.inclined_x = entity.inclined_x + 2;//--entity.inclined_x;
            }*/
            
        }
        {
        	GlStateManager.rotate(entity.inclined_x, -1.0F, 0.0F, 0.0F);
        	
        	GlStateManager.rotate(entity.inclined_z, 0.0F, 0.0F, -1.0F);
        }
        if(!flag1){
        	 if(entity.inclined_x != 0) {
        		 if(entity.inclined_x < 0)++entity.inclined_x;
        		 if(entity.inclined_x > 0)--entity.inclined_x;
        	 }
        	 if(entity.inclined_z != 0) {
        		 if(entity.inclined_z < 0)++entity.inclined_z;
        		 if(entity.inclined_z > 0)--entity.inclined_z;
        	 }
        }
        
		return flag1;
	}
	
	public static void render_riddir(EntityVehicleBase entity, ResourceLocation Textures, boolean flag1, float entityYaw, float partialTicks) {
		//start (2/2)
        if ((Minecraft.getMinecraft()).gameSettings.thirdPersonView != 0
                || (Minecraft.getMinecraft()).player.getRidingEntity() != entity) {
            if (flag1)
            	if(entity.inclined_x != 0 || entity.inclined_z != 0)GlStateManager.translate(0.0F, -0.4F, 0.0F);
            for (Entity passenger : entity.getPassengers()) {
                if (passenger instanceof net.minecraft.entity.EntityLivingBase) {
                    mod_GVCLib.dontjumpRedner = true;
                    GlStateManager.pushMatrix();
                    GlStateManager.rotate(entityYaw, 0.0F, 1.0F, 0.0F);
                    if(!entity.ridding_invisible) Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(passenger).doRender(passenger,
                            passenger.posX - entity.posX, passenger.posY - entity.posY,
                            passenger.posZ - entity.posZ, 0.0F, partialTicks);
                    GlStateManager.popMatrix();
                }
            }
            (Minecraft.getMinecraft()).renderEngine.bindTexture(Textures);
        }
        //end (2/2)
	}
}
