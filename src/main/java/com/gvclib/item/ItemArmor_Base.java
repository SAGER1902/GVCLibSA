package gvclib.item;
 
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
 
public class ItemArmor_Base extends ItemArmor {
 
	//public static final String armor_layer1 = "hmggvc:textures/armor/praarmor_layer_1.png";
    //public static final String armor_layer2 = "hmggvc:textures/armor/praarmor_layer_2.png";
	public String armor_layer1;
    public String armor_layer2;

  //0305
    public boolean gazo = false;
    public String gazotex = null;
    public float tou = 1.0F;
    public boolean tps = true;
    
    public EntityEquipmentSlot armorType1;
	public ItemArmor_Base(ItemArmor.ArmorMaterial armorMaterial, EntityEquipmentSlot type, String layer1, String layer2) {
		super(armorMaterial, 0, type);
		armor_layer1 = layer1;
		armor_layer2 = layer2;
	}
 
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (this.armorType == slot.LEGS) {
			return armor_layer2;
		}else{
		return armor_layer1;
		}
	}
 
}
