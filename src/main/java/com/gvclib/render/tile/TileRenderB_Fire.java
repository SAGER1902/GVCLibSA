package gvclib.render.tile;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.block.tile.TileEntityB_Fire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import net.minecraft.client.renderer.GlStateManager;

@SideOnly(Side.CLIENT)
public class TileRenderB_Fire extends TileEntitySpecialRenderer<TileEntityB_Fire>
{
    private static final ResourceLocation box1 = new ResourceLocation("gvclib:textures/blocks/b_fire1.png");
    private static final ResourceLocation box2 = new ResourceLocation("gvclib:textures/blocks/b_fire2.png");
    private static final ResourceLocation box3 = new ResourceLocation("gvclib:textures/blocks/b_fire3.png");
    private static final ResourceLocation box4 = new ResourceLocation("gvclib:textures/blocks/b_fire4.png");
    private static final IModelCustom box_model = AdvancedModelLoader.loadModel(
    		new ResourceLocation("gvclib:textures/blocks/b_fire.mqo"));

    public TileRenderB_Fire()
    {
    }

    public void render(TileEntityB_Fire tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
    	Minecraft minecraft = Minecraft.getMinecraft();
    	
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef((float)x + 0.5F, (float)y + 0F, (float)z + 0.5F);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		if (tile.count %4 == 4) {
			this.bindTexture(box2);
		} else if (tile.count %4 == 3) {
			this.bindTexture(box3);
		} else if (tile.count %4 == 2) {
			this.bindTexture(box4);
		} else {
			this.bindTexture(box1);
		}
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDepthMask(false);
			GL11.glColor4f(1F, 1F, 1F, 0.6F);
            GlStateManager.disableLighting();
			box_model.renderPart("mat1");
            GlStateManager.enableLighting();
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        
    }
}