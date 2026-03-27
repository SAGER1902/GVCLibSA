package gvclib.util;

import net.minecraftforge.common.MinecraftForge;

public class ForgeEvent {
    public ForgeEvent() {
        MinecraftForge.EVENT_BUS.register(this);
    }

}
