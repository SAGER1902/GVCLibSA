package objmodel;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

public interface IModelCustom
{
    String getType();
    @SideOnly(Side.CLIENT)
    void renderAll();
    @SideOnly(Side.CLIENT)
    void renderOnly(String... groupNames);
    @SideOnly(Side.CLIENT)
    void renderPart(String partName);
    @SideOnly(Side.CLIENT)
    void renderAllExcept(String... excludedGroupNames);
}