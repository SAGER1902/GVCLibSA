package gvclib.gui;

import gvclib.entity.living.EntityMobVehicle_Inv_Base;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GVCContainerInventory_vehicle extends Container
{
    private final IInventory horseInventory;
    private final EntityMobVehicle_Inv_Base horse;
    private final int numRows;

    public GVCContainerInventory_vehicle(IInventory playerInventory, IInventory horseInventoryIn, final EntityMobVehicle_Inv_Base horse, EntityPlayer player)
    {
        this.horseInventory = horseInventoryIn;
        this.horse = horse;
        this.numRows = horse.slot_max / 9;
        //int i = 3;
        int i = (this.numRows - 4) * 18;
        horseInventoryIn.openInventory(player);
        //int j = -18;

        if(horse.can_fuel) {
        	this.addSlotToContainer(new Slot(horseInventoryIn, 63, 80, 18));
        }
        if(horse.can_weapon1) {
        	this.addSlotToContainer(new Slot(horseInventoryIn, 40, 80, 36));
        	
        	this.addSlotToContainer(new Slot(horseInventoryIn, 41, 98, 36));
        	this.addSlotToContainer(new Slot(horseInventoryIn, 42, 116, 36));
        	this.addSlotToContainer(new Slot(horseInventoryIn, 43, 134, 36));
        	this.addSlotToContainer(new Slot(horseInventoryIn, 44, 152, 36));
        }
        if(horse.can_weapon2) {
        	this.addSlotToContainer(new Slot(horseInventoryIn, 50, 80, 54));
        	this.addSlotToContainer(new Slot(horseInventoryIn, 51, 98, 54));
        	this.addSlotToContainer(new Slot(horseInventoryIn, 52, 116, 54));
        	this.addSlotToContainer(new Slot(horseInventoryIn, 53, 134, 54));
        	this.addSlotToContainer(new Slot(horseInventoryIn, 54, 152, 54));
        }
        
        for (int i1 = 0; i1 < 3; ++i1)
        {
            for (int k1 = 0; k1 < 9; ++k1)
            {
                this.addSlotToContainer(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
            }
        }

        for (int j1 = 0; j1 < 9; ++j1)
        {
            this.addSlotToContainer(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
        }
        
        /*for (int j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(horseInventoryIn, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for (int i1 = 0; i1 < 3; ++i1)
        {
            for (int k1 = 0; k1 < 9; ++k1)
            {
                this.addSlotToContainer(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
            }
        }

        for (int j1 = 0; j1 < 9; ++j1)
        {
            this.addSlotToContainer(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
        }*/
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.horseInventory.isUsableByPlayer(playerIn) && this.horse.isEntityAlive() && this.horse.getDistance(playerIn) < 8.0F;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < this.horseInventory.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.horseInventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack())
            {
                if (!this.mergeItemStack(itemstack1, 1, 2, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (this.getSlot(0).isItemValid(itemstack1))
            {
                if (!this.mergeItemStack(itemstack1, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (this.horseInventory.getSizeInventory() <= 2 || !this.mergeItemStack(itemstack1, 2, this.horseInventory.getSizeInventory(), false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
            
            /*{
            	ItemStack is = this.horseInventory.getStackInSlot(10);

                if (!is.isEmpty() && is.getItem() == Items.EMERALD)
                {
                	this.horseInventory.setInventorySlotContents(11, new ItemStack(Items.EMERALD));
                	if(playerIn.inventory.addItemStackToInventory(this.horseInventory.getStackInSlot(11))) {
                		is.shrink(1);
                	}
                }
            }*/
            
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        this.horseInventory.closeInventory(playerIn);
    }
}