package gvclib.world;

import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class GVCEnumDifficulty_int
{
	public GVCEnumDifficulty_int() {
		
	}
	
	public static int intid(World world) {
		int en = 0;
		if(world.getDifficulty() == EnumDifficulty.PEACEFUL) {
			en = 0;
		}
		if(world.getDifficulty() == EnumDifficulty.EASY) {
			en = 1;
		}
		if(world.getDifficulty() == EnumDifficulty.NORMAL) {
			en = 2;
		}
		if(world.getDifficulty() == EnumDifficulty.HARD) {
			en = 3;
		}
		
		return en;
	}
}