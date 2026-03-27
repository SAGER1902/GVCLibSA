package gvclib;


import gvclib.item.ItemAttachment;
import gvclib.item.ItemGunBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class LoadAttachmentStausBase {
	
	  
	  
	public static void load(ItemAttachment am, RegistryEvent.Register<Item> event) {
		boolean mo = false;
    	for(int ii = 0; ii < 1024; ++ii) {
    		if(mod_GVCLib.am_name[ii] != null && mod_GVCLib.am_name[ii].equals(am.obj_model_string)) {
    			mo = true;
    			break;
    		}
    	}
    	if(!mo) 
    	{
    		if (am.obj_model_string != null) {
				// entity.model = AdvancedModelLoader.loadModel(resource);
				++mod_GVCLib.am_id;
				mod_GVCLib.am_model[mod_GVCLib.am_id] = am.obj_model;
				mod_GVCLib.am_tex[mod_GVCLib.am_id] = new ResourceLocation(am.obj_tex);
				mod_GVCLib.am_name[mod_GVCLib.am_id] = am.obj_model_string;
				System.out.println(String.format("GVC_attachment_model-" + mod_GVCLib.am_id));
			}
    	}
	}
}