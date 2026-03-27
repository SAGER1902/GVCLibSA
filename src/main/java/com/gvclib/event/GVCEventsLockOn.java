package gvclib.event;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GVCEventsLockOn {
	
	
	
	@SubscribeEvent
	public void onRiddingMountEvent(LivingUpdateEvent event) {
		Entity target = event.getEntityLiving();
		//Entity ride = event.getEntityBeingMounted();
		if (target != null) 
		{
			NBTTagCompound target_nbt = target.getEntityData();
			if(target_nbt != null) {
				Entity[] targetlist = new Entity[1024];
				int targetplus = 0;
				int lockon = target_nbt.getInteger("lockon");
				/*List<Entity> llist = target.world.getEntitiesWithinAABBExcludingEntity(target,
						target.getEntityBoundingBox().expand(target.motionX, target.motionY, target.motionZ).grow(120));
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith() && entity1 != null && entity1 instanceof IMob) {
							{
								targetlist[targetplus] = entity1;
								++targetplus;
							}
						}
					}
				}
				for(int xs = 0; xs < targetlist.length; ++xs) {
					if(targetlist[xs] != null && targetlist[xs].getEntityData() != null) {
						if(targetlist[xs].getEntityData().getInteger("lockon") > lockon) {
							target_nbt.setInteger("lockon", -1);
						}
					}
				}
				*/
				if(lockon > 0)target_nbt.setInteger("lockon", --lockon);
				// System.out.println("2");
			}
		}
	}
}
