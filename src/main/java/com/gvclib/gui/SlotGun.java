package gvclib.gui;
 
import gvclib.item.ItemAttachment;
import gvclib.item.ItemGunBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
 
public class SlotGun extends Slot
{
	
	private IInventory inv;
	private int id = 0;
	private ItemStack main;
	
    public SlotGun(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, ItemStack ma)
    {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        id = p_i1824_2_;
        inv = p_i1824_1_;
        main = ma;
    }
 
    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(ItemStack stack)
    {
    	boolean idd = false;
    	if(id == 0) {
   		 //idd = (getHasStack() && getStack().getItem() == mod_GirlFront.idoll_hp);
    		if((!stack.isEmpty() && stack.getItem() instanceof ItemAttachment)) {
    			ItemAttachment am = (ItemAttachment) stack.getItem();
    			if(am.id == 101 || am.id == 4 || am.id == 5) {
    				idd = true;
    			}
    		}
   		 }
		if (id == 1) {
			if((!stack.isEmpty() && stack.getItem() instanceof ItemAttachment)) {
    			ItemAttachment am = (ItemAttachment) stack.getItem();
    			if(am.id == 101 || am.id == 4 || am.id == 5) {
    				idd = getdesignated(am, id);
    			}
    		}
		}
		if (id == 2) {
			if((!stack.isEmpty() && stack.getItem() instanceof ItemAttachment)) {
    			ItemAttachment am = (ItemAttachment) stack.getItem();
    			if(am.id == 102 || am.id == 6 || am.id == 7) {
    				idd = getdesignated(am, id);
    			}
    		}
		}
		if (id == 3) {
			if((!stack.isEmpty() && stack.getItem() instanceof ItemAttachment)) {
    			ItemAttachment am = (ItemAttachment) stack.getItem();
    			if(am.id == 8) {
    				idd = getdesignated(am, id);
    			}
    		}
		}
		if (id == 4) {
			if((!stack.isEmpty() && stack.getItem() instanceof ItemAttachment)) {
    			ItemAttachment am = (ItemAttachment) stack.getItem();
    			if(am.id == 9) {
    				idd = getdesignated(am, id);
    			}
    		}
			if((!stack.isEmpty() && stack.getItem() instanceof ItemGunBase)) {
				idd = true;
    		}
		}
		if (id == 5) {
			if((!stack.isEmpty() && stack.getItem() instanceof ItemAttachment)) {
    			ItemAttachment am = (ItemAttachment) stack.getItem();
    			if(am.id == 50 || am.id == 51 || am.id == 52|| am.id == 53|| am.id == 54|| am.id == 55) {
    				idd = true;
    			}
    		}
		}
		if (id > 5 && id < 10) {//新格子
			if((!stack.isEmpty() && stack.getItem() instanceof ItemAttachment)) {
    			ItemAttachment am = (ItemAttachment) stack.getItem();
    			if(am.id == 56){
    				idd = true;
    			}
    		}
		}
		if (id > 9 && id < 14) {//新格子
			if((!stack.isEmpty() && stack.getItem() instanceof ItemAttachment)) {
    			ItemAttachment am = (ItemAttachment) stack.getItem();
    			if(am.id == 57){
    				idd = true;
    			}
    		}
		}
        //return TileEntityFurnace.isItemFuel(stack) || isBucket(stack);
    	return idd;
    }
    
    private boolean getdesignated(ItemAttachment am, int id) {
    	boolean flag = false;
		if(!main.isEmpty() && main.getItem() instanceof ItemGunBase){
			ItemGunBase gun = (ItemGunBase) main.getItem();
			if(gun.designated_kazu != 0) {
				if(id == 1 && gun.designated_mat4){
					flag = true;
				}
				if(id == 2 && gun.designated_mat6){
					flag = true;
				}
				if(id == 3 && gun.designated_mat8){
					flag = true;
				}
				if(id == 4 && gun.designated_mat9){
					flag = true;
				}
				for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
					if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
						flag = true;
						break;
					}
				}
			}else if(am.designated_attachment_name == null){
				flag = true;
			}
		}
		return flag;
    }
    
}