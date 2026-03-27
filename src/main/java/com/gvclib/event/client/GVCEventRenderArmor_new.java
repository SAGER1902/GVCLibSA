package gvclib.event.client;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.GL11;

import gvclib.entity.EntityT_Flash;
import gvclib.item.ItemArmor_NewObj;
import gvclib.render.model.GVCLayerArmorBase;
import gvclib.render.model.GVCModelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.relauncher.Side;
import objmodel.IModelCustom;

public class GVCEventRenderArmor_new {
    public static final HashMap<RenderLivingBase<EntityLivingBase>, List> rendererList = new HashMap<RenderLivingBase<EntityLivingBase>, List>();
    Field layerRenderersField;

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre event) {
        if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            return;
        }

        if (layerRenderersField == null) {
            Class clazz = RenderLivingBase.class;
            try {
                layerRenderersField = clazz.getDeclaredField("layerRenderers");
            } catch (NoSuchFieldException | SecurityException e) {
                // TODO 自?生成的 catch ?
                try {
                    layerRenderersField = clazz.getDeclaredField("field_177097_h");
                } catch (NoSuchFieldException | SecurityException e1) {
                    // TODO 自?生成的 catch ?
                    e1.printStackTrace();
                    return;
                }
            }
            layerRenderersField.setAccessible(true);
        }
        ModelBase model = null;
        ModelBase modelD = null;
        List list = rendererList.get(event.getRenderer());
        if (list == null) {
            try {
                list = (List) layerRenderersField.get(event.getRenderer());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO 自?生成的 catch ?
                e.printStackTrace();
                return;
            }
        }
        if (list == null) {
            return;
        }
        LayerBipedArmor layerBipedArmor = null;
        rendererList.put(event.getRenderer(), list);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof LayerBipedArmor) {
                model = ((LayerBipedArmor) list.get(i)).getModelFromSlot(EntityEquipmentSlot.CHEST);
                modelD = ((LayerBipedArmor) list.get(i)).getModelFromSlot(EntityEquipmentSlot.LEGS);
                final ModelBiped modelLeggingsTemp = (ModelBiped) modelD;
                final ModelBiped modelArmorTemp = (ModelBiped) model;
                list.set(i, new GVCLayerArmorBase(event.getRenderer()) {
                    protected void initArmor() {
                        this.modelLeggings = modelLeggingsTemp;
                        this.modelArmor = modelArmorTemp;
                    }
                });
                layerBipedArmor = (LayerBipedArmor) list.get(i);
            }
        }
        if (layerBipedArmor == null) {
            return;
        }

        //head
        if (!(((ModelBiped) model).bipedHead instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedHead = new GVCModelRenderer(((ModelBiped) model), 0, 0);
            bipedHead.cubeList = ((ModelBiped) model).bipedHead.cubeList;
            bipedHead.setRotationPoint(((ModelBiped) model).bipedHead.rotationPointX,
                    ((ModelBiped) model).bipedHead.rotationPointY, ((ModelBiped) model).bipedHead.rotationPointZ);
            bipedHead.offset_x = 0;
            bipedHead.offset_y = 0;
            bipedHead.offset_z = 0;
            bipedHead.offset_x_ = 0;
            bipedHead.offset_y_ = 1.5f;
            bipedHead.offset_z_ = 0;
            bipedHead.part = "head";
            ((ModelBiped) model).bipedHead = bipedHead;
        }
        GVCModelRenderer bipedHead = (GVCModelRenderer) ((ModelBiped) model).bipedHead;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD)
                    .getItem();
            bipedHead.obj = armor.obj_model;
            bipedHead.tex = new ResourceLocation(armor.obj_tex);
            bipedHead.skin = layerBipedArmor.getArmorResource(event.getEntity(),
                    event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD), EntityEquipmentSlot.HEAD, null);
        } else {
            bipedHead.obj = null;
            bipedHead.tex = null;
        }
        ItemStack[] items=null;
       /* if(GVCLIBPlugin.enableTwoArmor) {
            items = TwoArmor.instance.getRenderItemStack(event.getEntity().getEntityId(), 3);
        }*/
      /*  if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedHead.secondObjs = listModel;
            bipedHead.secondTexs = listTex;
        } else*/
        {
            bipedHead.secondObjs = null;
            bipedHead.secondTexs = null;
        }

        //body
        if (!(((ModelBiped) model).bipedBody instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedBody = new GVCModelRenderer(((ModelBiped) model), 16, 16);
            bipedBody.cubeList = ((ModelBiped) model).bipedBody.cubeList;
            bipedBody.setRotationPoint(((ModelBiped) model).bipedBody.rotationPointX,
                    ((ModelBiped) model).bipedBody.rotationPointY, (((ModelBiped) model)).bipedBody.rotationPointZ);
            bipedBody.offset_x = 0;
            bipedBody.offset_y = 0;
            bipedBody.offset_z = 0;
            bipedBody.offset_x_ = 0;
            bipedBody.offset_y_ = 1.5f;
            bipedBody.offset_z_ = 0;
            bipedBody.part = "body";
            ((ModelBiped) model).bipedBody = bipedBody;
        }
        GVCModelRenderer bipedBody = (GVCModelRenderer) ((ModelBiped) model).bipedBody;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity()
                    .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            bipedBody.obj = armor.obj_model;
            bipedBody.tex = new ResourceLocation(armor.obj_tex);
            bipedBody.skin = layerBipedArmor.getArmorResource(event.getEntity(),
                    event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST), EntityEquipmentSlot.CHEST, null);
        } else {
            bipedBody.obj = null;
            bipedBody.tex = null;
        }
        float smallArmsOffest=0;
        if (event.getRenderer().getMainModel() instanceof ModelPlayer) {
            smallArmsOffest = ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftArm.rotationPointY;
            if(smallArmsOffest==2.5f) {
                smallArmsOffest=0.5f;
            }
        }
        //rightarm
        if (!(((ModelBiped) model).bipedRightArm instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedRightArm = new GVCModelRenderer(((ModelBiped) model), 40, 16);
            bipedRightArm.cubeList = ((ModelBiped) model).bipedRightArm.cubeList;
            bipedRightArm.setRotationPoint(((ModelBiped) model).bipedRightArm.rotationPointX,
                    ((ModelBiped) model).bipedRightArm.rotationPointY + smallArmsOffest,
                    ((ModelBiped) model).bipedRightArm.rotationPointZ);
            bipedRightArm.offset_x = 0;
            bipedRightArm.offset_y = 0;
            bipedRightArm.offset_z = 0;
            bipedRightArm.offset_x_ = 0.32f;
            bipedRightArm.offset_y_ = 1.35f;
            bipedRightArm.offset_z_ = 0;
            bipedRightArm.part = "rightarm";
            ((ModelBiped) model).bipedRightArm = bipedRightArm;
        }
        GVCModelRenderer bipedRightArm = (GVCModelRenderer) ((ModelBiped) model).bipedRightArm;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity()
                    .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            bipedRightArm.obj = armor.obj_model;
            bipedRightArm.tex = new ResourceLocation(armor.obj_tex);
            bipedRightArm.skin = layerBipedArmor.getArmorResource(event.getEntity(),
                    event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST), EntityEquipmentSlot.CHEST, null);
        } else {
            bipedRightArm.obj = null;
            bipedRightArm.tex = null;
        }

        // leftarm
        if (!(((ModelBiped) model).bipedLeftArm instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedLeftArm = new GVCModelRenderer(((ModelBiped) model), 32, 48);
            bipedLeftArm.cubeList = ((ModelBiped) model).bipedLeftArm.cubeList;
            bipedLeftArm.setRotationPoint(((ModelBiped) model).bipedLeftArm.rotationPointX,
                    ((ModelBiped) model).bipedLeftArm.rotationPointY + smallArmsOffest,
                    ((ModelBiped) model).bipedLeftArm.rotationPointZ);
            bipedLeftArm.offset_x = 0;
            bipedLeftArm.offset_y = 0;
            bipedLeftArm.offset_z = 0;
            bipedLeftArm.offset_x_ = -0.32f;
            bipedLeftArm.offset_y_ = 1.35f;
            bipedLeftArm.offset_z_ = 0;
            bipedLeftArm.part = "leftarm";
            ((ModelBiped) model).bipedLeftArm = bipedLeftArm;
        }
        GVCModelRenderer bipedLeftArm = (GVCModelRenderer) ((ModelBiped) model).bipedLeftArm;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity()
                    .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            bipedLeftArm.obj = armor.obj_model;
            bipedLeftArm.tex = new ResourceLocation(armor.obj_tex);
            bipedLeftArm.skin = layerBipedArmor.getArmorResource(event.getEntity(),
                    event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST), EntityEquipmentSlot.CHEST, null);
        } else {
            bipedLeftArm.obj = null;
            bipedLeftArm.tex = null;
        }

       /* if(GVCLIBPlugin.enableTwoArmor) {
            items = TwoArmor.instance.getRenderItemStack(event.getEntity().getEntityId(), 2);
        }*/
      /*  if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedBody.secondObjs = listModel;
            bipedBody.secondTexs = listTex;
            bipedRightArm.secondObjs = listModel;
            bipedRightArm.secondTexs = listTex;
            bipedLeftArm.secondObjs = listModel;
            bipedLeftArm.secondTexs = listTex;
        } else */
        {
            bipedBody.secondObjs = null;
            bipedBody.secondTexs = null;
            bipedRightArm.secondObjs = null;
            bipedRightArm.secondTexs = null;
            bipedLeftArm.secondObjs = null;
            bipedLeftArm.secondTexs = null;
        }

        //rightleg
        if (!(((ModelBiped) model).bipedRightLeg instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedRightLeg = new GVCModelRenderer(((ModelBiped) model), 0, 16);
            bipedRightLeg.cubeList = ((ModelBiped) model).bipedRightLeg.cubeList;
            bipedRightLeg.setRotationPoint(((ModelBiped) model).bipedRightLeg.rotationPointX,
                    ((ModelBiped) model).bipedRightLeg.rotationPointY,
                    ((ModelBiped) model).bipedRightLeg.rotationPointZ);
            bipedRightLeg.offset_x = 0;
            bipedRightLeg.offset_y = 0;
            bipedRightLeg.offset_z = 0;
            bipedRightLeg.offset_x_ = 0.125f;
            bipedRightLeg.offset_y_ = 0.75f;
            bipedRightLeg.offset_z_ = 0;
            bipedRightLeg.part = "rightleg";
            ((ModelBiped) model).bipedRightLeg = bipedRightLeg;
        }

        GVCModelRenderer bipedRightLeg = (GVCModelRenderer) ((ModelBiped) model).bipedRightLeg;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS)
                    .getItem();
            bipedRightLeg.obj = armor.obj_model;
            bipedRightLeg.tex = new ResourceLocation(armor.obj_tex);
            bipedRightLeg.skin = layerBipedArmor.getArmorResource(event.getEntity(),
                    event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS), EntityEquipmentSlot.LEGS, null);
        } else {
            bipedRightLeg.obj = null;
            bipedRightLeg.tex = null;
        }
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET)
                    .getItem();
            bipedRightLeg.objD = armor.obj_model;
            bipedRightLeg.texD = new ResourceLocation(armor.obj_tex);
        } else {
            bipedRightLeg.objD = null;
            bipedRightLeg.texD = null;
        }

        //leftleg
        if (!(((ModelBiped) model).bipedLeftLeg instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedLeftLeg = new GVCModelRenderer(((ModelBiped) model), 16, 48);
            bipedLeftLeg.cubeList = ((ModelBiped) model).bipedLeftLeg.cubeList;
            bipedLeftLeg.setRotationPoint(((ModelBiped) model).bipedLeftLeg.rotationPointX,
                    ((ModelBiped) model).bipedLeftLeg.rotationPointY, ((ModelBiped) model).bipedLeftLeg.rotationPointZ);
            bipedLeftLeg.offset_x = 0;
            bipedLeftLeg.offset_y = 0;
            bipedLeftLeg.offset_z = 0;
            bipedLeftLeg.offset_x_ = -0.125f;
            bipedLeftLeg.offset_y_ = 0.75f;
            bipedLeftLeg.offset_z_ = 0;
            bipedLeftLeg.part = "leftleg";
            ((ModelBiped) model).bipedLeftLeg = bipedLeftLeg;
        }

        GVCModelRenderer bipedLeftLeg = (GVCModelRenderer) ((ModelBiped) model).bipedLeftLeg;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS)
                    .getItem();
            bipedLeftLeg.obj = armor.obj_model;
            bipedLeftLeg.tex = new ResourceLocation(armor.obj_tex);
            bipedLeftLeg.skin = layerBipedArmor.getArmorResource(event.getEntity(),
                    event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS), EntityEquipmentSlot.LEGS, null);
        } else {
            bipedLeftLeg.obj = null;
            bipedLeftLeg.tex = null;
        }
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET)
                    .getItem();
            bipedLeftLeg.objD = armor.obj_model;
            bipedLeftLeg.texD = new ResourceLocation(armor.obj_tex);
        } else {
            bipedLeftLeg.objD = null;
            bipedLeftLeg.texD = null;
        }

      /*  if(GVCLIBPlugin.enableTwoArmor) {
            items = TwoArmor.instance.getRenderItemStack(event.getEntity().getEntityId(), 1);
        }*/
    /*    if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedRightLeg.secondObjs = listModel;
            bipedRightLeg.secondTexs = listTex;
            bipedLeftLeg.secondObjs = listModel;
            bipedLeftLeg.secondTexs = listTex;
        } else */
        {
            bipedRightLeg.secondObjs = null;
            bipedRightLeg.secondTexs = null;
            bipedLeftLeg.secondObjs = null;
            bipedLeftLeg.secondTexs = null;
        }

        /*if(GVCLIBPlugin.enableTwoArmor) {
            items = TwoArmor.instance.getRenderItemStack(event.getEntity().getEntityId(), 0);
        }*/
      /*  if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedRightLeg.secondObjsD = listModel;
            bipedRightLeg.secondTexsD = listTex;
            bipedLeftLeg.secondObjsD = listModel;
            bipedLeftLeg.secondTexsD = listTex;
        } else */
        {
            bipedRightLeg.secondObjsD = null;
            bipedRightLeg.secondTexsD = null;
            bipedLeftLeg.secondObjsD = null;
            bipedLeftLeg.secondTexsD = null;
        }
    }

    
    /*
    //@SubscribeEvent
    public void onRender(RenderLivingEvent.Pre event) {
        if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            return;
        }

        if (!(event.getRenderer().getMainModel() instanceof ModelPlayerAlt)) {
            return;
        }
        //head
        if (!(((ModelPlayer) event.getRenderer().getMainModel()).bipedHead instanceof GVCModelRenderer)) {
            NPCModelScaleRenderer bipedHead = new NPCModelScaleRenderer(
                    ((ModelPlayer) event.getRenderer().getMainModel()), 0, 0,
                    ((ModelScaleRenderer) (((ModelPlayer) event.getRenderer().getMainModel())).bipedRightArm).part);
            bipedHead.cubeList = ((ModelPlayer) event.getRenderer().getMainModel()).bipedHead.cubeList;
            bipedHead.setRotationPoint(((ModelPlayer) event.getRenderer().getMainModel()).bipedHead.rotationPointX,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedHead.rotationPointY,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedHead.rotationPointZ);
            bipedHead.offset_x = 0;
            bipedHead.offset_y = 0;
            bipedHead.offset_z = 0;
            bipedHead.offset_x_ = 0;
            bipedHead.offset_y_ = 1.5f;
            bipedHead.offset_z_ = 0;
            bipedHead.part = "head";
            ((ModelPlayer) event.getRenderer().getMainModel()).bipedHead = bipedHead;
        }
        NPCModelScaleRenderer bipedHead = (NPCModelScaleRenderer) ((ModelPlayer) event.getRenderer()
                .getMainModel()).bipedHead;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.HEAD)
                    .getItem();
            bipedHead.obj = armor.obj_model;
            bipedHead.tex = new ResourceLocation(armor.obj_tex);
            bipedHead.skin = new ResourceLocation(((EntityCustomNpc) event.getEntity()).display.getSkinTexture());
        } else {
            bipedHead.obj = null;
            bipedHead.tex = null;
        }

        //body
        if (!(((ModelPlayer) event.getRenderer().getMainModel()).bipedBody instanceof GVCModelRenderer)) {
            NPCModelScaleRenderer bipedBody = new NPCModelScaleRenderer(
                    ((ModelPlayer) event.getRenderer().getMainModel()), 16, 16,
                    ((ModelScaleRenderer) (((ModelPlayer) event.getRenderer().getMainModel())).bipedRightArm).part);
            bipedBody.cubeList = ((ModelPlayer) event.getRenderer().getMainModel()).bipedBody.cubeList;
            bipedBody.setRotationPoint(((ModelPlayer) event.getRenderer().getMainModel()).bipedBody.rotationPointX,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedBody.rotationPointY,
                    ((ModelPlayer) ((ModelPlayer) event.getRenderer().getMainModel())).bipedBody.rotationPointZ);
            bipedBody.offset_x = 0;
            bipedBody.offset_y = 0;
            bipedBody.offset_z = 0;
            bipedBody.offset_x_ = 0;
            bipedBody.offset_y_ = 1.5f;
            bipedBody.offset_z_ = 0;
            bipedBody.part = "body";
            ((ModelPlayer) event.getRenderer().getMainModel()).bipedBody = bipedBody;
        }
        NPCModelScaleRenderer bipedBody = (NPCModelScaleRenderer) ((ModelPlayer) event.getRenderer()
                .getMainModel()).bipedBody;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity()
                    .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            bipedBody.obj = armor.obj_model;
            bipedBody.tex = new ResourceLocation(armor.obj_tex);
            bipedBody.skin = new ResourceLocation(((EntityCustomNpc) event.getEntity()).display.getSkinTexture());
        } else {
            bipedBody.obj = null;
            bipedBody.tex = null;
        }

        //rightarm
        if (!(((ModelPlayer) event.getRenderer().getMainModel()).bipedRightArm instanceof NPCModelScaleRenderer)) {
            NPCModelScaleRenderer bipedRightArm = new NPCModelScaleRenderer(
                    ((ModelPlayer) event.getRenderer().getMainModel()), 40, 16,
                    ((ModelScaleRenderer) (((ModelPlayer) event.getRenderer().getMainModel())).bipedRightArm).part);
            bipedRightArm.cubeList = ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightArm.cubeList;
            bipedRightArm.setRotationPoint(
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightArm.rotationPointX,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightArm.rotationPointY,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightArm.rotationPointZ);
            bipedRightArm.offset_x = 0;
            bipedRightArm.offset_y = 0;
            bipedRightArm.offset_z = 0;
            bipedRightArm.offset_x_ = 0.32f;
            bipedRightArm.offset_y_ = 1.35f;
            bipedRightArm.offset_z_ = 0;
            bipedRightArm.part = "rightarm";
            ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightArm = bipedRightArm;
        }
        NPCModelScaleRenderer bipedRightArm = (NPCModelScaleRenderer) ((ModelPlayer) event.getRenderer()
                .getMainModel()).bipedRightArm;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity()
                    .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            bipedRightArm.obj = armor.obj_model;
            bipedRightArm.tex = new ResourceLocation(armor.obj_tex);
            bipedRightArm.skin = new ResourceLocation(((EntityCustomNpc) event.getEntity()).display.getSkinTexture());
        } else {
            bipedRightArm.obj = null;
            bipedRightArm.tex = null;
        }

        // leftarm
        if (!(((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftArm instanceof NPCModelScaleRenderer)) {
            NPCModelScaleRenderer bipedLeftArm = new NPCModelScaleRenderer(
                    ((ModelPlayer) event.getRenderer().getMainModel()), 32, 48,
                    ((ModelScaleRenderer) (((ModelPlayer) event.getRenderer().getMainModel())).bipedLeftArm).part);
            bipedLeftArm.cubeList = ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftArm.cubeList;
            bipedLeftArm.setRotationPoint(
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftArm.rotationPointX,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftArm.rotationPointY,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftArm.rotationPointZ);
            bipedLeftArm.offset_x = 0;
            bipedLeftArm.offset_y = 0;
            bipedLeftArm.offset_z = 0;
            bipedLeftArm.offset_x_ = -0.32f;
            bipedLeftArm.offset_y_ = 1.35f;
            bipedLeftArm.offset_z_ = 0;
            bipedLeftArm.part = "leftarm";
            ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftArm = bipedLeftArm;
        }
        NPCModelScaleRenderer bipedLeftArm = (NPCModelScaleRenderer) ((ModelPlayer) event.getRenderer()
                .getMainModel()).bipedLeftArm;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity()
                    .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            bipedLeftArm.obj = armor.obj_model;
            bipedLeftArm.tex = new ResourceLocation(armor.obj_tex);
            bipedLeftArm.skin = new ResourceLocation(((EntityCustomNpc) event.getEntity()).display.getSkinTexture());
        } else {
            bipedLeftArm.obj = null;
            bipedLeftArm.tex = null;
        }

        //rightleg
        if (!(((ModelPlayer) event.getRenderer().getMainModel()).bipedRightLeg instanceof NPCModelScaleRenderer)) {
            NPCModelScaleRenderer bipedRightLeg = new NPCModelScaleRenderer(
                    ((ModelPlayer) event.getRenderer().getMainModel()), 0, 16,
                    ((ModelScaleRenderer) (((ModelPlayer) event.getRenderer().getMainModel())).bipedRightLeg).part);
            bipedRightLeg.cubeList = ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightLeg.cubeList;
            bipedRightLeg.setRotationPoint(
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightLeg.rotationPointX,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightLeg.rotationPointY,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightLeg.rotationPointZ);
            bipedRightLeg.offset_x = 0;
            bipedRightLeg.offset_y = 0;
            bipedRightLeg.offset_z = 0;
            bipedRightLeg.offset_x_ = 0.125f;
            bipedRightLeg.offset_y_ = 0.75f;
            bipedRightLeg.offset_z_ = 0;
            bipedRightLeg.part = "rightleg";
            ((ModelPlayer) event.getRenderer().getMainModel()).bipedRightLeg = bipedRightLeg;
        }

        NPCModelScaleRenderer bipedRightLeg = (NPCModelScaleRenderer) ((ModelPlayer) event.getRenderer()
                .getMainModel()).bipedRightLeg;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS)
                    .getItem();
            bipedRightLeg.obj = armor.obj_model;
            bipedRightLeg.tex = new ResourceLocation(armor.obj_tex);
            bipedRightLeg.skin = new ResourceLocation(((EntityCustomNpc) event.getEntity()).display.getSkinTexture());
        } else {
            bipedRightLeg.obj = null;
            bipedRightLeg.tex = null;
        }
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET)
                    .getItem();
            bipedRightLeg.objD = armor.obj_model;
            bipedRightLeg.texD = new ResourceLocation(armor.obj_tex);
            bipedRightLeg.skin = new ResourceLocation(((EntityCustomNpc) event.getEntity()).display.getSkinTexture());
        } else {
            bipedRightLeg.objD = null;
            bipedRightLeg.texD = null;
        }

        //leftleg
        if (!(((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftLeg instanceof NPCModelScaleRenderer)) {
            NPCModelScaleRenderer bipedLeftLeg = new NPCModelScaleRenderer(
                    ((ModelPlayer) event.getRenderer().getMainModel()), 16, 48,
                    ((ModelScaleRenderer) (((ModelPlayer) event.getRenderer().getMainModel())).bipedLeftLeg).part);
            bipedLeftLeg.cubeList = ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftLeg.cubeList;
            bipedLeftLeg.setRotationPoint(
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftLeg.rotationPointX,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftLeg.rotationPointY,
                    ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftLeg.rotationPointZ);
            bipedLeftLeg.offset_x = 0;
            bipedLeftLeg.offset_y = 0;
            bipedLeftLeg.offset_z = 0;
            bipedLeftLeg.offset_x_ = -0.125f;
            bipedLeftLeg.offset_y_ = 0.75f;
            bipedLeftLeg.offset_z_ = 0;
            bipedLeftLeg.part = "leftleg";
            ((ModelPlayer) event.getRenderer().getMainModel()).bipedLeftLeg = bipedLeftLeg;
        }

        NPCModelScaleRenderer bipedLeftLeg = (NPCModelScaleRenderer) ((ModelPlayer) event.getRenderer()
                .getMainModel()).bipedLeftLeg;
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.LEGS)
                    .getItem();
            bipedLeftLeg.obj = armor.obj_model;
            bipedLeftLeg.tex = new ResourceLocation(armor.obj_tex);
            bipedLeftLeg.skin = new ResourceLocation(((EntityCustomNpc) event.getEntity()).display.getSkinTexture());
        } else {
            bipedLeftLeg.obj = null;
            bipedLeftLeg.tex = null;
        }
        if ((event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) && (event.getEntity()
                .getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.FEET)
                    .getItem();
            bipedLeftLeg.objD = armor.obj_model;
            bipedLeftLeg.texD = new ResourceLocation(armor.obj_tex);
            bipedLeftLeg.skin = new ResourceLocation(((EntityCustomNpc) event.getEntity()).display.getSkinTexture());
        } else {
            bipedLeftLeg.objD = null;
            bipedLeftLeg.texD = null;
        }
    }

    //@SubscribeEvent
    public void onRender(RenderPlayerEvent.Pre event) {
        if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            return;
        }

        if (event.getRenderer().getMainModel() instanceof ModelPlayerAlt) {
            return;
        }

        //head
        if (!(event.getRenderer().getMainModel().bipedHead instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedHead = new GVCModelRenderer(event.getRenderer().getMainModel(), 0, 0);
            bipedHead.cubeList = event.getRenderer().getMainModel().bipedHead.cubeList;
            bipedHead.setRotationPoint(event.getRenderer().getMainModel().bipedHead.rotationPointX,
                    event.getRenderer().getMainModel().bipedHead.rotationPointY,
                    event.getRenderer().getMainModel().bipedHead.rotationPointZ);
            bipedHead.offset_x = 0;
            bipedHead.offset_y = 0;
            bipedHead.offset_z = 0;
            bipedHead.offset_x_ = 0;
            bipedHead.offset_y_ = 1.5f;
            bipedHead.offset_z_ = 0;
            bipedHead.part = "head";
            event.getRenderer().getMainModel().bipedHead = bipedHead;
        }
        GVCModelRenderer bipedHead = (GVCModelRenderer) event.getRenderer().getMainModel().bipedHead;
        if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null) && (event.getEntityPlayer()
                .getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer()
                    .getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
            bipedHead.obj = armor.obj_model;
            bipedHead.tex = new ResourceLocation(armor.obj_tex);
            bipedHead.skin = ((AbstractClientPlayer) event.getEntityPlayer()).getLocationSkin();
        } else {
            bipedHead.obj = null;
            bipedHead.tex = null;
        }
        ItemStack[] items = TwoArmor.instance.getRenderItemStack(event.getEntityPlayer().getEntityId(), 4);
        if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedHead.secondObjs = listModel;
            bipedHead.secondTexs = listTex;
        } else {
            bipedHead.secondObjs = null;
            bipedHead.secondTexs = null;
        }

        //body
        if (!(event.getRenderer().getMainModel().bipedBody instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedBody = new GVCModelRenderer(event.getRenderer().getMainModel(), 16, 16);
            bipedBody.cubeList = event.getRenderer().getMainModel().bipedBody.cubeList;
            bipedBody.setRotationPoint(event.getRenderer().getMainModel().bipedBody.rotationPointX,
                    event.getRenderer().getMainModel().bipedBody.rotationPointY,
                    event.getRenderer().getMainModel().bipedBody.rotationPointZ);
            bipedBody.offset_x = 0;
            bipedBody.offset_y = 0;
            bipedBody.offset_z = 0;
            bipedBody.offset_x_ = 0;
            bipedBody.offset_y_ = 1.5f;
            bipedBody.offset_z_ = 0;
            bipedBody.part = "body";
            event.getRenderer().getMainModel().bipedBody = bipedBody;
        }
        GVCModelRenderer bipedBody = (GVCModelRenderer) event.getRenderer().getMainModel().bipedBody;
        if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null)
                && (event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST)
                        .getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer()
                    .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            bipedBody.obj = armor.obj_model;
            bipedBody.tex = new ResourceLocation(armor.obj_tex);
            bipedBody.skin = ((AbstractClientPlayer) event.getEntityPlayer()).getLocationSkin();
        } else {
            bipedBody.obj = null;
            bipedBody.tex = null;
        }
        items = TwoArmor.instance.getRenderItemStack(event.getEntityPlayer().getEntityId(), 3);
        if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedBody.secondObjs = listModel;
            bipedBody.secondTexs = listTex;
        } else {
            bipedBody.secondObjs = null;
            bipedBody.secondTexs = null;
        }

        //rightarm
        if (!(event.getRenderer().getMainModel().bipedRightArm instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedRightArm = new GVCModelRenderer(event.getRenderer().getMainModel(), 40, 16);
            bipedRightArm.cubeList = event.getRenderer().getMainModel().bipedRightArm.cubeList;
            bipedRightArm.setRotationPoint(event.getRenderer().getMainModel().bipedRightArm.rotationPointX,
                    event.getRenderer().getMainModel().bipedRightArm.rotationPointY,
                    event.getRenderer().getMainModel().bipedRightArm.rotationPointZ);
            bipedRightArm.offset_x = 0;
            bipedRightArm.offset_y = 0;
            bipedRightArm.offset_z = 0;
            bipedRightArm.offset_x_ = 0.32f;
            bipedRightArm.offset_y_ = 1.35f;
            bipedRightArm.offset_z_ = 0;
            bipedRightArm.part = "rightarm";
            event.getRenderer().getMainModel().bipedRightArm = bipedRightArm;
        }
        GVCModelRenderer bipedRightArm = (GVCModelRenderer) event.getRenderer().getMainModel().bipedRightArm;
        if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null)
                && (event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST)
                        .getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer()
                    .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            bipedRightArm.obj = armor.obj_model;
            bipedRightArm.tex = new ResourceLocation(armor.obj_tex);
            bipedRightArm.skin = ((AbstractClientPlayer) event.getEntityPlayer()).getLocationSkin();
        } else {
            bipedRightArm.obj = null;
            bipedRightArm.tex = null;
        }
        items = TwoArmor.instance.getRenderItemStack(event.getEntityPlayer().getEntityId(), 3);
        if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedRightArm.secondObjs = listModel;
            bipedRightArm.secondTexs = listTex;
        } else {
            bipedRightArm.secondObjs = null;
            bipedRightArm.secondTexs = null;
        }

        // leftarm
        if (!(event.getRenderer().getMainModel().bipedLeftArm instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedLeftArm = new GVCModelRenderer(event.getRenderer().getMainModel(), 32, 48);
            bipedLeftArm.cubeList = event.getRenderer().getMainModel().bipedLeftArm.cubeList;
            bipedLeftArm.setRotationPoint(event.getRenderer().getMainModel().bipedLeftArm.rotationPointX,
                    event.getRenderer().getMainModel().bipedLeftArm.rotationPointY,
                    event.getRenderer().getMainModel().bipedLeftArm.rotationPointZ);
            bipedLeftArm.offset_x = 0;
            bipedLeftArm.offset_y = 0;
            bipedLeftArm.offset_z = 0;
            bipedLeftArm.offset_x_ = -0.32f;
            bipedLeftArm.offset_y_ = 1.35f;
            bipedLeftArm.offset_z_ = 0;
            bipedLeftArm.part = "leftarm";
            event.getRenderer().getMainModel().bipedLeftArm = bipedLeftArm;
        }
        GVCModelRenderer bipedLeftArm = (GVCModelRenderer) event.getRenderer().getMainModel().bipedLeftArm;
        if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null)
                && (event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST)
                        .getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer()
                    .getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            bipedLeftArm.obj = armor.obj_model;
            bipedLeftArm.tex = new ResourceLocation(armor.obj_tex);
            bipedLeftArm.skin = ((AbstractClientPlayer) event.getEntityPlayer()).getLocationSkin();
        } else {
            bipedLeftArm.obj = null;
            bipedLeftArm.tex = null;
        }
        items = TwoArmor.instance.getRenderItemStack(event.getEntityPlayer().getEntityId(), 3);
        if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedLeftArm.secondObjs = listModel;
            bipedLeftArm.secondTexs = listTex;
        } else {
            bipedLeftArm.secondObjs = null;
            bipedLeftArm.secondTexs = null;
        }

        //rightleg
        if (!(event.getRenderer().getMainModel().bipedRightLeg instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedRightLeg = new GVCModelRenderer(event.getRenderer().getMainModel(), 0, 16);
            bipedRightLeg.cubeList = event.getRenderer().getMainModel().bipedRightLeg.cubeList;
            bipedRightLeg.setRotationPoint(event.getRenderer().getMainModel().bipedRightLeg.rotationPointX,
                    event.getRenderer().getMainModel().bipedRightLeg.rotationPointY,
                    event.getRenderer().getMainModel().bipedRightLeg.rotationPointZ);
            bipedRightLeg.offset_x = 0;
            bipedRightLeg.offset_y = 0;
            bipedRightLeg.offset_z = 0;
            bipedRightLeg.offset_x_ = 0.125f;
            bipedRightLeg.offset_y_ = 0.75f;
            bipedRightLeg.offset_z_ = 0;
            bipedRightLeg.part = "rightleg";
            event.getRenderer().getMainModel().bipedRightLeg = bipedRightLeg;
        }

        GVCModelRenderer bipedRightLeg = (GVCModelRenderer) event.getRenderer().getMainModel().bipedRightLeg;
        if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null) && (event.getEntityPlayer()
                .getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer()
                    .getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
            bipedRightLeg.obj = armor.obj_model;
            bipedRightLeg.tex = new ResourceLocation(armor.obj_tex);
            bipedRightLeg.skin = ((AbstractClientPlayer) event.getEntityPlayer()).getLocationSkin();
        } else {
            bipedRightLeg.obj = null;
            bipedRightLeg.tex = null;
        }
        items = TwoArmor.instance.getRenderItemStack(event.getEntityPlayer().getEntityId(), 2);
        if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedRightLeg.secondObjs = listModel;
            bipedRightLeg.secondTexs = listTex;
        } else {
            bipedRightLeg.secondObjs = null;
            bipedRightLeg.secondTexs = null;
        }
        if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) && (event.getEntityPlayer()
                .getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer()
                    .getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
            bipedRightLeg.objD = armor.obj_model;
            bipedRightLeg.texD = new ResourceLocation(armor.obj_tex);
            bipedRightLeg.skin = ((AbstractClientPlayer) event.getEntityPlayer()).getLocationSkin();
        } else {
            bipedRightLeg.objD = null;
            bipedRightLeg.texD = null;
        }
        items = TwoArmor.instance.getRenderItemStack(event.getEntityPlayer().getEntityId(), 1);
        if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedRightLeg.secondObjsD = listModel;
            bipedRightLeg.secondTexsD = listTex;
        } else {
            bipedRightLeg.secondObjsD = null;
            bipedRightLeg.secondTexsD = null;
        }

        //leftleg
        if (!(event.getRenderer().getMainModel().bipedLeftLeg instanceof GVCModelRenderer)) {
            GVCModelRenderer bipedLeftLeg = new GVCModelRenderer(event.getRenderer().getMainModel(), 16, 48);
            bipedLeftLeg.cubeList = event.getRenderer().getMainModel().bipedLeftLeg.cubeList;
            bipedLeftLeg.setRotationPoint(event.getRenderer().getMainModel().bipedLeftLeg.rotationPointX,
                    event.getRenderer().getMainModel().bipedLeftLeg.rotationPointY,
                    event.getRenderer().getMainModel().bipedLeftLeg.rotationPointZ);
            bipedLeftLeg.offset_x = 0;
            bipedLeftLeg.offset_y = 0;
            bipedLeftLeg.offset_z = 0;
            bipedLeftLeg.offset_x_ = -0.125f;
            bipedLeftLeg.offset_y_ = 0.75f;
            bipedLeftLeg.offset_z_ = 0;
            bipedLeftLeg.part = "leftleg";
            event.getRenderer().getMainModel().bipedLeftLeg = bipedLeftLeg;
        }

        GVCModelRenderer bipedLeftLeg = (GVCModelRenderer) event.getRenderer().getMainModel().bipedLeftLeg;
        if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null) && (event.getEntityPlayer()
                .getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer()
                    .getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
            bipedLeftLeg.obj = armor.obj_model;
            bipedLeftLeg.tex = new ResourceLocation(armor.obj_tex);
            bipedLeftLeg.skin = ((AbstractClientPlayer) event.getEntityPlayer()).getLocationSkin();
        } else {
            bipedLeftLeg.obj = null;
            bipedLeftLeg.tex = null;
        }
        items = TwoArmor.instance.getRenderItemStack(event.getEntityPlayer().getEntityId(), 2);
        if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedLeftLeg.secondObjs = listModel;
            bipedLeftLeg.secondTexs = listTex;
        } else {
            bipedLeftLeg.secondObjs = null;
            bipedLeftLeg.secondTexs = null;
        }
        if ((event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) && (event.getEntityPlayer()
                .getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
            ItemArmor_NewObj armor = (ItemArmor_NewObj) event.getEntityPlayer()
                    .getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
            bipedLeftLeg.objD = armor.obj_model;
            bipedLeftLeg.texD = new ResourceLocation(armor.obj_tex);
            bipedLeftLeg.skin = ((AbstractClientPlayer) event.getEntityPlayer()).getLocationSkin();
        } else {
            bipedLeftLeg.objD = null;
            bipedLeftLeg.texD = null;
        }
        items = TwoArmor.instance.getRenderItemStack(event.getEntityPlayer().getEntityId(), 1);
        if (items != null) {
            ArrayList<IModelCustom> listModel = new ArrayList<IModelCustom>();
            ArrayList<ResourceLocation> listTex = new ArrayList<ResourceLocation>();
            ItemStack stack;
            for (int i = 0; i < items.length; i++) {
                stack = items[i];
                if (stack.getItem() instanceof ItemArmor_NewObj) {
                    listModel.add(((ItemArmor_NewObj) stack.getItem()).obj_model);
                    listTex.add(new ResourceLocation(((ItemArmor_NewObj) stack.getItem()).obj_tex));
                }
            }
            bipedLeftLeg.secondObjsD = listModel;
            bipedLeftLeg.secondTexsD = listTex;
        } else {
            bipedLeftLeg.secondObjsD = null;
            bipedLeftLeg.secondTexsD = null;
        }
    }

    @SubscribeEvent
    public void onTick(ClientTickEvent event) {
        if (event.phase != Phase.START) {
            return;
        }
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player == null) {
            return;
        }
        if (Minecraft.getSystemTime() >= GVCLIBPlugin.rrecoiltime) {
            GVCLIBPlugin.rrecoil = 0;
            return;
        }
        if (player.rotationPitch < player.prevRotationPitch) {
            GVCLIBPlugin.rrecoil = 0;
            return;
        }
        player.rotationPitch += GVCLIBPlugin.rrecoil / 5;
    }

    @SubscribeEvent
    public void onHandler(ClientCustomPacketEvent event) {
        PacketBuffer buffer = new PacketBuffer(event.getPacket().payload());
        if (buffer.readByte() == 1) {
            GVCLIBPlugin.rrecoiltime = Minecraft.getSystemTime() + 200;
            GVCLIBPlugin.rrecoil += buffer.readDouble();
            buffer.release();
            return;
        }
        long deadTime = Minecraft.getSystemTime() + 100;
        EntityT_Flash entity = new EntityT_Flash(Minecraft.getMinecraft().world);
        try {
            entity.readEntityFromNBT(GVCLIBPlugin.readNBT(buffer));
        } catch (IOException e) {
            // TODO 自?生成的 catch ?
            e.printStackTrace();
            return;
        }
        EntityLivingBase player = (EntityLivingBase) Minecraft.getMinecraft().world.getEntityByID(buffer.readInt());
        if (player == null) {
            buffer.release();
            return;
        }
        float bbure = buffer.readFloat();
        float xz = buffer.readFloat();
        double fire_posy = buffer.readDouble();
        double fire_posy_ads = buffer.readDouble();
        double fire_posz = buffer.readDouble();
        double fire_posx = buffer.readDouble();
        float fire_roteoffset_y = buffer.readFloat();
        double fire_posx_third = buffer.readDouble();
        double fire_posy_third = buffer.readDouble();
        double fire_posz_third = buffer.readDouble();
        Task task = new Task(deadTime, entity, player, bbure, xz, fire_posy, fire_posy_ads, fire_posz, fire_posx,
                fire_roteoffset_y, fire_posx_third, fire_posy_third, fire_posz_third);
        if (xz < 0) {
            FlashRenderer.tasksDeputy.put(player, task);
        } else {
            FlashRenderer.tasks.put(player, task);
        }
        buffer.release();
    }
    */
}
