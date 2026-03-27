package gvclib.gui;
 
import java.util.List;

import gvclib.block.tile.TileEntityInvasion;
import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import gvclib.entity.living.EntityVehicleBase;
import gvclib.entity.trader.EntityNPCBase;
import gvclib.item.ItemGunBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
 
public class GVCGuiHandler implements IGuiHandler
{
    /*
        ServerでGUIが開かれたときに呼ばれる
        通常はContainerを生成する。
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	ItemStack itemstack = player.getHeldItemMainhand();
        if(ID == 0){
            return new GVCContainerInventoryItem(player.inventory, itemstack);
        }
        if(ID == 3){
        	if(itemstack.getItem() instanceof ItemGunBase) {
            return new GVCGunReloadtestContainer();
        	}
        }
        BlockPos pos = new BlockPos(x, y, z);
		if (ID == 1) {
			if (!world.isBlockLoaded(pos)) {
				return null;
			}else {
				TileEntity tileentity = world.getTileEntity(pos);
				if (tileentity instanceof TileEntityInvasion) {
					//System.out.println(String.format("2"));
					return new ContainerInventoryEntityGVC(player.inventory, (TileEntityInvasion) tileentity);
				}
			}
		}
		if(ID == 2){
        	NBTTagCompound nbt = player.getEntityData();
        	int enid = nbt.getInteger("vi");
        	EntityNPCBase en = null;
        	List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
        			player.getEntityBoundingBox().expand(player.motionX, player.motionY, player.motionZ).grow(2));
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith() && entity1.getEntityId() == enid) {
    					en = (EntityNPCBase) entity1;
    				}
    			}
    		}
    		if(en != null) {
    			return new TraderContainerInventoryItem(player.inventory, en.horseChest,en,  player);
    		}else {
    			return null;
    		}
        }
		if(ID == 4){
        	NBTTagCompound nbt = player.getEntityData();
        	int enid = nbt.getInteger("vi");
        	EntityMobVehicle_Inv_Base en = null;
        	List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
        			player.getEntityBoundingBox().expand(player.motionX, player.motionY, player.motionZ).grow(2));
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith() && entity1.getEntityId() == enid) {
    					en = (EntityMobVehicle_Inv_Base) entity1;
    				}
    			}
    		}
    		if(en != null) {
    			return new GVCContainerInventory_vehicle(player.inventory, en.horseChest,en,  player);
    		}else {
    			return null;
    		}
        }
		
		if(ID == 5){
        	NBTTagCompound nbt = player.getEntityData();
        	int enid = nbt.getInteger("vehi");
        	EntityVehicleBase en = null;
        	List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
        			player.getEntityBoundingBox().expand(player.motionX, player.motionY, player.motionZ).grow(2));
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith() && entity1.getEntityId() == enid) {
    					en = (EntityVehicleBase) entity1;
    				}
    			}
    		}
    		if(en != null) {
    			return new GVCContainerVehicle_state();
    		}else {
    			return null;
    		}
        }
		/*if(ID == 5){
        	NBTTagCompound nbt = player.getEntityData();
        	int enid = nbt.getInteger("vi");
        	EntityVehicleBase en = null;
        	List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
        			player.getEntityBoundingBox().expand(player.motionX, player.motionY, player.motionZ).grow(2));
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith() && entity1.getEntityId() == enid) {
    					en = (EntityVehicleBase) entity1;
    				}
    			}
    		}
    		if(en != null) {
    			return new GVCContainerVehicle_Motion();
    		}else {
    			return null;
    		}
        }*/
        return null;
    }
 
    /*
        ClientでGUIが開かれたときに呼ばれる
        通常はGUIを生成する
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	ItemStack itemstack = player.getHeldItemMainhand();
        if(ID == 0){
            return new GVCGuiInventoryItem(player.inventory, itemstack);
        }
        if(ID == 3){
        	if(itemstack.getItem() instanceof ItemGunBase) {
        		ItemGunBase gun = (ItemGunBase) itemstack.getItem();
        		 return new GVCGunReloadtestGuiInventory(player.inventory, itemstack, gun);
        	}
        }
        BlockPos pos = new BlockPos(x, y, z);
        if (ID == 1) {
			if (!world.isBlockLoaded(pos)) {
				return null;
			}else {
				TileEntity tileentity = world.getTileEntity(pos);
				if (tileentity instanceof TileEntityInvasion) {
					//System.out.println(String.format("1"));
					return new GuiInventoryEntityGVC(player.inventory, (TileEntityInvasion) tileentity, (TileEntityInvasion) tileentity);
				}
			}
		}
        if(ID == 2){
        	NBTTagCompound nbt = player.getEntityData();
        	int enid = nbt.getInteger("vi");
        	EntityNPCBase en = null;
        	List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
        			player.getEntityBoundingBox().expand(player.motionX, player.motionY, player.motionZ).grow(2));
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith() && entity1.getEntityId() == enid) {
    					en = (EntityNPCBase) entity1;
    				}
    			}
    		}
    		if(en != null) {
    			return new TraderGuiInventoryItem(player.inventory, en.horseChest,en);
    		}else {
    			return null;
    		}
        }
        if(ID == 4){
        	NBTTagCompound nbt = player.getEntityData();
        	int enid = nbt.getInteger("vi");
        	EntityMobVehicle_Inv_Base en = null;
        	List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
        			player.getEntityBoundingBox().expand(player.motionX, player.motionY, player.motionZ).grow(2));
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith() && entity1.getEntityId() == enid) {
    					en = (EntityMobVehicle_Inv_Base) entity1;
    				}
    			}
    		}
    		if(en != null) {
    			return new GVCGuiInventoryItem_vehicle(player.inventory, en.horseChest,en);
    		}else {
    			return null;
    		}
        }
        
        if(ID == 5){
        	NBTTagCompound nbt = player.getEntityData();
        	int enid = nbt.getInteger("vehi");
        	EntityVehicleBase en = null;
        	List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
        			player.getEntityBoundingBox().expand(player.motionX, player.motionY, player.motionZ).grow(2));
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith() && entity1.getEntityId() == enid) {
    					en = (EntityVehicleBase) entity1;
    				}
    			}
    		}
    		if(en != null) {
    			//System.out.println("2");
    			return new GVCGuiInventoryVehicle_state(player, en);
    		}else {
    			return null;
    		}
        }
       /* if(ID == 5){
        	NBTTagCompound nbt = player.getEntityData();
        	int enid = nbt.getInteger("vi");
        	EntityVehicleBase en = null;
        	List<Entity> llist = player.world.getEntitiesWithinAABBExcludingEntity(player,
        			player.getEntityBoundingBox().expand(player.motionX, player.motionY, player.motionZ).grow(2));
    		if (llist != null) {
    			for (int lj = 0; lj < llist.size(); lj++) {
    				Entity entity1 = (Entity) llist.get(lj);
    				if (entity1.canBeCollidedWith() && entity1.getEntityId() == enid) {
    					en = (EntityVehicleBase) entity1;
    				}
    			}
    		}
    		if(en != null) {
    			return new GVCGuiInventoryVehicle_Motion(player, en);
    		}else {
    			return null;
    		}
        }*/
        return null;
    }
}