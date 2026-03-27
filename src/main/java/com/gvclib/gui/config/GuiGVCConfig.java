package gvclib.gui.config;

import gvclib.mod_GVCLib;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiGVCConfig extends GuiConfig {
    public GuiGVCConfig(GuiScreen parent) {
        super(parent, new ConfigElement(mod_GVCLib.config.getCategory("all")).getChildElements(), 
        		mod_GVCLib.MOD_ID, false, false, "GVCLibConfig");
        titleLine2 = mod_GVCLib.config.getConfigFile().getAbsolutePath();
    }

   @Override
    public void onGuiClosed(){
        super.onGuiClosed();
    }
}