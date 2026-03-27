package gvclib.item;
 
import gvclib.ClientProxyGVClib;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
 
public class ItemArmor_NewVanilla extends ItemArmor {
 
	//public static final String armor_layer1 = "hmggvc:textures/armor/praarmor_layer_1.png";
    //public static final String armor_layer2 = "hmggvc:textures/armor/praarmor_layer_2.png";
	public String armor_layer1;
    public String armor_layer2;
    public IModelCustom obj_model = null;//AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/armor/gas.obj"));
    public String obj_texh = "gvclib:textures/armor/gas.png";
    public String obj_texc = "gvclib:textures/armor/gas.png";
    public String obj_texl = "gvclib:textures/armor/gas.png";
    public String obj_texb = "gvclib:textures/armor/gas.png";
    public String obj_tex = "gvclib:textures/armor/nulll.png";
    
    //public IModelCustom obj_model = null;
    //public String obj_tex = "su01.obj";
    
    public boolean render_head = false;
    public boolean render_body = false;
    public boolean render_rarm = false;
    public boolean render_larm = false;
    public boolean render_rleg = false;
    public boolean render_lleg = false;
    
    public double motion = 1D;
    public boolean booster = false;
    public boolean para = false;
    public boolean fall = false;
    public double paraspeed = 0.4;
    
    //0305
    public boolean gazo = false;
    public String gazotex = null;
    public float tou = 1.0F;
    public boolean tps = true;

	public ItemArmor_NewVanilla(ItemArmor.ArmorMaterial armorMaterial, EntityEquipmentSlot type) {
		super(armorMaterial, 0, type);
	}
 
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return obj_tex;
	}
 
}
