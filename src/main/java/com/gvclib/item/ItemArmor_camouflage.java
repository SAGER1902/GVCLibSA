package gvclib.item;
 
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeSavanna;
import net.minecraft.world.biome.BiomeSnow;
 
public class ItemArmor_camouflage extends ItemArmor_Base {
	
	
	public ItemArmor_camouflage(ItemArmor.ArmorMaterial armorMaterial, EntityEquipmentSlot type, String layer1, String layer2) {
		super(armorMaterial, type, layer1, layer2);
	}
 
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if(entity.world.getBiome(entity.getPosition()) instanceof BiomeDesert)
		//if(entity.world.getBlockState(new BlockPos(entity.posX, entity.posX, entity.posX)).getBlock() == Blocks.SAND) 
		{
			if (this.armorType == slot.LEGS) {
				return armor_layer2 + "_desert.png";
			}else{
			return armor_layer1 + "_desert.png";
			}
		}
		else if(entity.world.getBiome(entity.getPosition()) instanceof BiomeSnow)
		{
				if (this.armorType == slot.LEGS) {
					return armor_layer2 + "_snow.png";
				}else{
				return armor_layer1 + "_snow.png";
				}
		}
		else if(entity.world.getBiome(entity.getPosition()) instanceof BiomeSavanna)
		{
				if (this.armorType == slot.LEGS) {
					return armor_layer2 + "_savanna.png";
				}else{
				return armor_layer1 + "_savanna.png";
				}
		}
		else {
			if (this.armorType == slot.LEGS) {
				return armor_layer2 + ".png";
			}else{
			return armor_layer1 + ".png";
			}
		}
		
	}
 
}
