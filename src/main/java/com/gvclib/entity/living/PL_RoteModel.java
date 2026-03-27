package gvclib.entity.living;

public class PL_RoteModel {
	
	public static void rotemodel(EntityGVCLivingBase entity, float f){
		entity.rotationYawHead = entity.rotationYaw + f;
		entity.rotationYaw = entity.rotationYawHead + f;
	//	entity.prevRotationYaw = entity.prevRotationYawHead + f;
	//	entity.prevRotationYawHead = entity.prevRotationYawHead + f;
		entity.renderYawOffset = entity.prevRotationYawHead + f;
    	//entity.rotation =entity.prevRotationYawHead + f;
	}
}
