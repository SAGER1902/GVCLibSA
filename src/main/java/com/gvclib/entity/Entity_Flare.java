package gvclib.entity;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Entity_Flare extends EntityGolem implements IAnimals
{
    public Entity_Flare(World worldIn)
    {
        super(worldIn);
        this.setSize(1F, 1F);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1D);
    }

    protected void entityInit()
    {
        super.entityInit();
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float partialTicks)
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float partialTicks)
    {
        return 1.0F;
    }
    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }
    
    public int id = 0;
    public double gra = 0.7D;
    public int time;
    public void onUpdate()
    {
        super.onUpdate();
        this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX, this.posY + 0D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        this.motionY *= gra;
        ++time;
        if(time > 600){
        	if (!this.world.isRemote)
            {
                this.setDead();
            }
        }
        if(id == 1 && this.onGround) {
        	BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        	if (!this.world.isRemote) {
        		this.world.setBlockState(blockpos, Blocks.TORCH.getDefaultState(), 2);
				this.playSound(SoundEvents.BLOCK_WOOD_PLACE, 1.0F, 1.0F);
				 this.setDead();
			}
        }
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
    	{
        return false;
    	}
    }
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
}