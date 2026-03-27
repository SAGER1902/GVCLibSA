package gvclib.render;

import org.lwjgl.opengl.GL11;

import gvclib.mod_GVCLib;
import gvclib.entity.EntityT_Smoke;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

@SideOnly(Side.CLIENT)
public class RenderT_Smoke<T extends EntityT_Smoke> extends Render<T>
{
    private final RenderItem itemRenderer;
    //private final RenderManager rendermanager;
    private static final ResourceLocation boatTextures = new ResourceLocation("gvclib:textures/model/smoke1.png");
    private static final ResourceLocation boatTextures2 = new ResourceLocation("gvclib:textures/model/smoke2.png");
    private static final ResourceLocation boatTextures3 = new ResourceLocation("gvclib:textures/model/smoke3.png");
    private static final ResourceLocation boatTextures4 = new ResourceLocation("gvclib:textures/model/smoke4.png");
    private static final ResourceLocation boatTextures5 = new ResourceLocation("gvclib:textures/model/smoke5.png");
    private static final ResourceLocation boatTextures0 = new ResourceLocation("gvclib:textures/model/smoke0.png");
    private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/smoke.obj"));

    public RenderT_Smoke(RenderManager renderManagerIn, RenderItem itemRendererIn)
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
    	
        this.bindEntityTexture(entity);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableRescaleNormal();
        GL11.glColor4f(1F, 1F, 1F, 1F);
        if(entity.time <= 1){
        	GL11.glScalef(15F, 15F, 15F);
        }
        else{
        GL11.glScalef(2F, 2F, 2F);
        GL11.glScalef(entity.time/4, entity.time/4, entity.time/4);
        }
        GL11.glPushMatrix();//glstart
        GL11.glColor4f(1F, 1F, 1F, 1F);
        //GlStateManager.enableColorMaterial();
        //GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        
        tankk.renderPart("mat1");
        tankk.renderPart("mat2");
        tankk.renderPart("mat3");
        tankk.renderPart("mat4");
        tankk.renderPart("mat5");
        tankk.renderPart("mat6");
        GL11.glPopMatrix();//glend
        /*GlStateManager.rotate(-playerViewY1, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(mc.gameSettings.thirdPersonView == 2 ? -1 : 1) * playerViewX1, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(10F, 10F, 10F);
        
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        //GL11.glScalef(2F, 2F, 2F);
        this.itemRenderer.renderItem(this.getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }*/

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    	
    }

    public ItemStack getStackToRender(T entityIn)
    {
    	/*if(entityIn.worldObj.rand.nextInt(2) == 0){
    		return new ItemStack(mod_GVCLib.smoke,0,entityIn.time/8);
    	}else*/
    	{
    		return new ItemStack(mod_GVCLib.smoke,0,entityIn.time/4);
    	}
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityT_Smoke entity)
    {
    	if(entity.time <= 1){
    		return boatTextures0;
    	}else
    	if(entity.time >=2 && entity.time < 20){
    		return boatTextures;
    	}else if(entity.time >=20 && entity.time < 40){
        		return boatTextures2;
    	}else if(entity.time >=40 && entity.time < 60){
    		return boatTextures3;
    	}else if(entity.time >=60 && entity.time < 80){
    		return boatTextures4;
    	}else if(entity.time >=80){
    		return boatTextures5;
    	}else{
        return boatTextures;
    	}
    }
}