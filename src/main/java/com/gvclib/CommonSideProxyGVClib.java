package gvclib;

import java.io.File;

import gvclib.block.tile.TileEntityB_Fire;
import gvclib.item.ItemGunBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonSideProxyGVClib{

	public EntityPlayer getPlayerClient() {
		return null;
	}
	
    public void onShootAnimation(EntityPlayer player, ItemStack itemstack) {}
	
    public void load() {}
	
	/*@Override
	public void preInit(FMLPreInitializationEvent event) {
		//this.registerEntityRenderers();
		//this.registerCapabilities();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		//this.registerItemRenderers();
		//NetworkRegistry.INSTANCE.registerGuiHandler(Techguns.MODID, new TechgunsGuiHandler());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}*/
	
	public boolean getClient() {
		return false;
	}
	
	public File ProxyFile(){
		return new File(".");
	}
	
	public void dropItem(int i, Entity entity) {
		if(i == 0) {
			if(entity != null) {
				entity.entityDropItem(new ItemStack(Items.APPLE), 1);
        	}
		}
	}
	
	public EntityPlayer getEntityPlayerInstance() {return null;}
	
	public EntityPlayerMP getEntityPlayerMP() {
		return null;
		}
	
	//public ItemGunBase load_gun_model(){return null;}
	
	public void registerClientInfo(){}
	
	public void IGuiHandler(){}
	
    public void reisterRenderers(){}
    
    public void reisterModel(){}
	
	public World getCilentWorld(){
		return null;}

	public void InitRendering() {
		
	}

	public int getFPS() {
		return 60;
	}
	
	
	public void registerTileEntity() {		
		//GameRegistry.registerTileEntity(GVCTileEntityItemG36.class, "GVCTileEntitysample");
		GameRegistry.registerTileEntity(TileEntityB_Fire.class, "TileEntityB_Fire");
	}
	
	public boolean keyq(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	
	public boolean keyx(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	public boolean keyg(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	public boolean keyc(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	public boolean keyh(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	public boolean keyf(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	public boolean keyl(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	public boolean keyz(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	public boolean keyb(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	public boolean keyv(){
		return false;
		//return Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_X);
	}
	
	public boolean reload(){
		return false;
	}
	
	public boolean jumped(){
		return false;
	}
	
	public boolean leftclick(){
		return false;
	}
	
	public boolean rightclick(){
		return false;
	}
	
	public boolean xclick(){
		return false;
	}
	
	public int mcbow(){
		return 1;
	}
 
	public void ExRender(World wl, Entity en, float i, boolean ex){
		if(wl != null && en != null){
    		if (i >= 2.0F)
            {
    			if (i >= 4.0F)
                {
    				int a = 0;
    				a = (int) (6 + i);
    				for (int ii = 0; ii < a; ++ii){
    					int xx = wl.rand.nextInt(a);
    					int zz = wl.rand.nextInt(a);
    					int yy = wl.rand.nextInt(a);
    					wl.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, en.posX - (a/2) + xx, en.posY - (a/2) + yy, en.posZ - (a/2) + zz, 1.0D, 0.0D, 0.0D, new int[0]);
    				}
    				wl.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, en.posX, en.posY, en.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
                }else{
                	wl.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, en.posX, en.posY, en.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
                }
            }
            else
            {
            	wl.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, en.posX, en.posY, en.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
            }
    	}
	}
	public void ExRender(){
	}
	
	
	//
	public boolean left(){
		return false;
	}
	public boolean right(){
		return false;
	}
	public boolean up(){
		return false;
	}
	public boolean down(){
		return false;
	}
	public boolean nine(){
		return false;
	}
	public boolean three(){
		return false;
	}
	public boolean LBRACKET(){
		return false;
	}
	public boolean RBRACKET(){
		return false;
	}
	public boolean setting(){
		return false;
	}
	
	
	public boolean numpad_1(){
		return false;
	}
	public boolean numpad_2(){
		return false;
	}
	public boolean numpad_3(){
		return false;
	}
	public boolean numpad_4(){
		return false;
	}
	public boolean numpad_5(){
		return false;
	}
	public boolean numpad_6(){
		return false;
	}
	public boolean numpad_7(){
		return false;
	}
	public boolean numpad_8(){
		return false;
	}
	public boolean numpad_9(){
		return false;
	}
	public boolean numpad_0(){
		return false;
	}
	
	
	public boolean tab(){
		return false;
	}
}