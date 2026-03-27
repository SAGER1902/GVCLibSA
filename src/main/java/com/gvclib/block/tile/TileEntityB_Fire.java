package gvclib.block.tile;
 
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
 
public class TileEntityB_Fire extends TileEntity implements ITickable
{
	public boolean spawn = false;
	
	public int count = 0;
	
	public int id = 0;
    
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536.0D;
    }
	
    @Override
	public void update() {
    	Block block = this.world.getBlockState(this.pos).getBlock();
        
        int x = this.pos.getX();
        int y= this.pos.getY();
        int z = this.pos.getZ();
        ++count;
       if(count > 200 + this.world.rand.nextInt(60)) {
    	   if(!this.world.isRemote){
           	this.world.setBlockToAir(this.pos);
           }
       }
	}
	
}