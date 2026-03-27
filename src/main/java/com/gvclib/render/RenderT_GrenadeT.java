package gvclib.render;

import org.lwjgl.opengl.GL11;

import gvclib.mod_GVCLib;
import gvclib.entity.EntityT_GrenadeT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderT_GrenadeT<T extends EntityT_GrenadeT> extends Render<T>
{
    private final RenderItem itemRenderer;
    //private final RenderManager rendermanager;

    public RenderT_GrenadeT(RenderManager renderManagerIn, RenderItem itemRendererIn)
    {
        super(renderManagerIn);
        this.itemRenderer = itemRendererIn;
        //this.rendermanager = renderManagerIn;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	RenderManager rendermanager = new RenderManager(mc.renderEngine, mc.getRenderItem());
    	float playerViewY1 = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * partialTicks;
        float playerViewX1 = mc.player.prevRotationPitch + (mc.player.rotationPitch - mc.player.prevRotationPitch) * partialTicks;
    	
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.rotate(-playerViewY1, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(mc.gameSettings.thirdPersonView == 2 ? -1 : 1) * playerViewX1, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.2F, 1.2F, 1.2F);
        
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.itemRenderer.renderItem(this.getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    	
    }

    public ItemStack getStackToRender(T entityIn)
    {
    		return new ItemStack(mod_GVCLib.e_grenadet);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(T entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}