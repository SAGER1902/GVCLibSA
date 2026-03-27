package gvclib;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import gvclib.item.ItemGunBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class ReloadMotionReader {

	static int mat0 = 0;
	static int mat1 = 0;
	static int mat2 = 0;
	static int mat3 = 0;
	static int mat22 = 0;
	static int mat24 = 0;
	static int mat25 = 0;
	static int mat31 = 0;
	static int mat32 = 0;
	static int matlefthand = 0;
	static int matrighthand = 0;
	
	public static void read(ItemGunBase gun, ResourceLocation reloadmotion) {
		
		mat0 = 0;
		mat1 = 0;
		mat2 = 0;
		mat3 = 0;
		mat22 = 0;
		mat24 = 0;
		mat25 = 0;
		mat31 = 0;
		mat32 = 0;
		matlefthand = 0;
		matrighthand = 0;
		
		//System.out.println(String.format("modelroad"));
		
		LinkedList<String> list = new LinkedList<>();
		
		try
		{
			System.out.println(String.format("modelroad" + reloadmotion.getResourcePath()));
		IResource res = Minecraft.getMinecraft().getResourceManager().getResource(reloadmotion);
		InputStream inputStream = res.getInputStream();
		try {
			//InputStreamReader packFile = new InputStreamReader(classs.getClassLoader().getResourceAsStream(reloadmotion), "UTF-8");
			//BufferedReader br = new BufferedReader(packFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			
			String str;
			while ((str = br.readLine()) != null) { // 1行ずつ読み込む
				
				/*{
					list.add(str);
				}*/
				
				
				String[] type = str.split(",");
				if (type.length != 0) {
					//System.out.println(String.format("ok"));
					if (type[0].equals("Remat0") && mat0 < 30) {
						gun.remat0_time[mat0] = Integer.parseInt(type[1]);
						gun.remat0_xpoint[mat0] = Float.parseFloat(type[2]);
						gun.remat0_ypoint[mat0] = Float.parseFloat(type[3]);
						gun.remat0_zpoint[mat0] = Float.parseFloat(type[4]);
						gun.remat0_xrote[mat0] = Float.parseFloat(type[5]);
						gun.remat0_yrote[mat0] = Float.parseFloat(type[6]);
						gun.remat0_zrote[mat0] = Float.parseFloat(type[7]);
						gun.remat0_xmove[mat0] = Float.parseFloat(type[8]);
						gun.remat0_ymove[mat0] = Float.parseFloat(type[9]);
						gun.remat0_zmove[mat0] = Float.parseFloat(type[10]);
						++mat0;
						//System.out.println(String.format("0"));
					}
					if (type[0].equals("Remat1") && mat1 < 30) {
						gun.remat1_time[mat1] = Integer.parseInt(type[1]);
						gun.remat1_xpoint[mat1] = Float.parseFloat(type[2]);
						gun.remat1_ypoint[mat1] = Float.parseFloat(type[3]);
						gun.remat1_zpoint[mat1] = Float.parseFloat(type[4]);
						gun.remat1_xrote[mat1] = Float.parseFloat(type[5]);
						gun.remat1_yrote[mat1] = Float.parseFloat(type[6]);
						gun.remat1_zrote[mat1] = Float.parseFloat(type[7]);
						gun.remat1_xmove[mat1] = Float.parseFloat(type[8]);
						gun.remat1_ymove[mat1] = Float.parseFloat(type[9]);
						gun.remat1_zmove[mat1] = Float.parseFloat(type[10]);
						++mat1;
					}
					if (type[0].equals("Remat2") && mat2 < 30) {
						gun.remat2_time[mat2] = Integer.parseInt(type[1]);
						gun.remat2_xpoint[mat2] = Float.parseFloat(type[2]);
						gun.remat2_ypoint[mat2] = Float.parseFloat(type[3]);
						gun.remat2_zpoint[mat2] = Float.parseFloat(type[4]);
						gun.remat2_xrote[mat2] = Float.parseFloat(type[5]);
						gun.remat2_yrote[mat2] = Float.parseFloat(type[6]);
						gun.remat2_zrote[mat2] = Float.parseFloat(type[7]);
						gun.remat2_xmove[mat2] = Float.parseFloat(type[8]);
						gun.remat2_ymove[mat2] = Float.parseFloat(type[9]);
						gun.remat2_zmove[mat2] = Float.parseFloat(type[10]);
						++mat2;
					}
					if (type[0].equals("Remat3") && mat3 < 30) {
						gun.remat3_time[mat3] = Integer.parseInt(type[1]);
						gun.remat3_xpoint[mat3] = Float.parseFloat(type[2]);
						gun.remat3_ypoint[mat3] = Float.parseFloat(type[3]);
						gun.remat3_zpoint[mat3] = Float.parseFloat(type[4]);
						gun.remat3_xrote[mat3] = Float.parseFloat(type[5]);
						gun.remat3_yrote[mat3] = Float.parseFloat(type[6]);
						gun.remat3_zrote[mat3] = Float.parseFloat(type[7]);
						gun.remat3_xmove[mat3] = Float.parseFloat(type[8]);
						gun.remat3_ymove[mat3] = Float.parseFloat(type[9]);
						gun.remat3_zmove[mat3] = Float.parseFloat(type[10]);
						++mat3;
					}
					if (type[0].equals("Remat22") && mat22 < 30) {
						gun.remat22_time[mat22] = Integer.parseInt(type[1]);
						gun.remat22_xpoint[mat22] = Float.parseFloat(type[2]);
						gun.remat22_ypoint[mat22] = Float.parseFloat(type[3]);
						gun.remat22_zpoint[mat22] = Float.parseFloat(type[4]);
						gun.remat22_xrote[mat22] = Float.parseFloat(type[5]);
						gun.remat22_yrote[mat22] = Float.parseFloat(type[6]);
						gun.remat22_zrote[mat22] = Float.parseFloat(type[7]);
						gun.remat22_xmove[mat22] = Float.parseFloat(type[8]);
						gun.remat22_ymove[mat22] = Float.parseFloat(type[9]);
						gun.remat22_zmove[mat22] = Float.parseFloat(type[10]);
						++mat22;
					}
					if (type[0].equals("Remat24") && mat24 < 30) {
						gun.remat24_time[mat24] = Integer.parseInt(type[1]);
						gun.remat24_xpoint[mat24] = Float.parseFloat(type[2]);
						gun.remat24_ypoint[mat24] = Float.parseFloat(type[3]);
						gun.remat24_zpoint[mat24] = Float.parseFloat(type[4]);
						gun.remat24_xrote[mat24] = Float.parseFloat(type[5]);
						gun.remat24_yrote[mat24] = Float.parseFloat(type[6]);
						gun.remat24_zrote[mat24] = Float.parseFloat(type[7]);
						gun.remat24_xmove[mat24] = Float.parseFloat(type[8]);
						gun.remat24_ymove[mat24] = Float.parseFloat(type[9]);
						gun.remat24_zmove[mat24] = Float.parseFloat(type[10]);
						++mat24;
					}
					if (type[0].equals("Remat25") && mat25 < 30) {
						gun.remat25_time[mat25] = Integer.parseInt(type[1]);
						gun.remat25_xpoint[mat25] = Float.parseFloat(type[2]);
						gun.remat25_ypoint[mat25] = Float.parseFloat(type[3]);
						gun.remat25_zpoint[mat25] = Float.parseFloat(type[4]);
						gun.remat25_xrote[mat25] = Float.parseFloat(type[5]);
						gun.remat25_yrote[mat25] = Float.parseFloat(type[6]);
						gun.remat25_zrote[mat25] = Float.parseFloat(type[7]);
						gun.remat25_xmove[mat25] = Float.parseFloat(type[8]);
						gun.remat25_ymove[mat25] = Float.parseFloat(type[9]);
						gun.remat25_zmove[mat25] = Float.parseFloat(type[10]);
						++mat25;
					}
					if (type[0].equals("Remat31") && mat31 < 30) {
						gun.remat31_time[mat31] = Integer.parseInt(type[1]);
						gun.remat31_xpoint[mat31] = Float.parseFloat(type[2]);
						gun.remat31_ypoint[mat31] = Float.parseFloat(type[3]);
						gun.remat31_zpoint[mat31] = Float.parseFloat(type[4]);
						gun.remat31_xrote[mat31] = Float.parseFloat(type[5]);
						gun.remat31_yrote[mat31] = Float.parseFloat(type[6]);
						gun.remat31_zrote[mat31] = Float.parseFloat(type[7]);
						gun.remat31_xmove[mat31] = Float.parseFloat(type[8]);
						gun.remat31_ymove[mat31] = Float.parseFloat(type[9]);
						gun.remat31_zmove[mat31] = Float.parseFloat(type[10]);
						++mat31;
					}
					if (type[0].equals("Remat32") && mat32 < 30) {
						gun.remat32_time[mat32] = Integer.parseInt(type[1]);
						gun.remat32_xpoint[mat32] = Float.parseFloat(type[2]);
						gun.remat32_ypoint[mat32] = Float.parseFloat(type[3]);
						gun.remat32_zpoint[mat32] = Float.parseFloat(type[4]);
						gun.remat32_xrote[mat32] = Float.parseFloat(type[5]);
						gun.remat32_yrote[mat32] = Float.parseFloat(type[6]);
						gun.remat32_zrote[mat32] = Float.parseFloat(type[7]);
						gun.remat32_xmove[mat32] = Float.parseFloat(type[8]);
						gun.remat32_ymove[mat32] = Float.parseFloat(type[9]);
						gun.remat32_zmove[mat32] = Float.parseFloat(type[10]);
						++mat32;
					}
					if (type[0].equals("Rematlefthand") && matlefthand < 30) {
						gun.rematlefthand_time[matlefthand] = Integer.parseInt(type[1]);
						gun.rematlefthand_xpoint[matlefthand] = Float.parseFloat(type[2]);
						gun.rematlefthand_ypoint[matlefthand] = Float.parseFloat(type[3]);
						gun.rematlefthand_zpoint[matlefthand] = Float.parseFloat(type[4]);
						gun.rematlefthand_xrote[matlefthand] = Float.parseFloat(type[5]);
						gun.rematlefthand_yrote[matlefthand] = Float.parseFloat(type[6]);
						gun.rematlefthand_zrote[matlefthand] = Float.parseFloat(type[7]);
						gun.rematlefthand_xmove[matlefthand] = Float.parseFloat(type[8]);
						gun.rematlefthand_ymove[matlefthand] = Float.parseFloat(type[9]);
						gun.rematlefthand_zmove[matlefthand] = Float.parseFloat(type[10]);
						++matlefthand;
					}
					if (type[0].equals("Rematrighthand") && matrighthand < 30) {
						gun.rematrighthand_time[matrighthand] = Integer.parseInt(type[1]);
						gun.rematrighthand_xpoint[matrighthand] = Float.parseFloat(type[2]);
						gun.rematrighthand_ypoint[matrighthand] = Float.parseFloat(type[3]);
						gun.rematrighthand_zpoint[matrighthand] = Float.parseFloat(type[4]);
						gun.rematrighthand_xrote[matrighthand] = Float.parseFloat(type[5]);
						gun.rematrighthand_yrote[matrighthand] = Float.parseFloat(type[6]);
						gun.rematrighthand_zrote[matrighthand] = Float.parseFloat(type[7]);
						gun.rematrighthand_xmove[matrighthand] = Float.parseFloat(type[8]);
						gun.rematrighthand_ymove[matrighthand] = Float.parseFloat(type[9]);
						gun.rematrighthand_zmove[matrighthand] = Float.parseFloat(type[10]);
						++matrighthand;
					}
					
				}
			}
			br.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		}catch (IOException e)
		{
		}
		
		
		
		
		/*if (list != null) {
			for (int lj = 0; lj < list.size(); lj++) {
				String str = list.get(lj);
				String[] type = str.split(",");
				if (type.length != 0) {
					//System.out.println(String.format("ok"));
					if (type[0].equals("Remat0") && mat0 < 30) {
						gun.remat0_time[mat0] = Integer.parseInt(type[1]);
						gun.remat0_xpoint[mat0] = Float.parseFloat(type[2]);
						gun.remat0_ypoint[mat0] = Float.parseFloat(type[3]);
						gun.remat0_zpoint[mat0] = Float.parseFloat(type[4]);
						gun.remat0_xrote[mat0] = Float.parseFloat(type[5]);
						gun.remat0_yrote[mat0] = Float.parseFloat(type[6]);
						gun.remat0_zrote[mat0] = Float.parseFloat(type[7]);
						gun.remat0_xmove[mat0] = Float.parseFloat(type[8]);
						gun.remat0_ymove[mat0] = Float.parseFloat(type[9]);
						gun.remat0_zmove[mat0] = Float.parseFloat(type[10]);
						++mat0;
						//System.out.println(String.format("0"));
					}
					if (type[0].equals("Remat1") && mat1 < 30) {
						gun.remat1_time[mat1] = Integer.parseInt(type[1]);
						gun.remat1_xpoint[mat1] = Float.parseFloat(type[2]);
						gun.remat1_ypoint[mat1] = Float.parseFloat(type[3]);
						gun.remat1_zpoint[mat1] = Float.parseFloat(type[4]);
						gun.remat1_xrote[mat1] = Float.parseFloat(type[5]);
						gun.remat1_yrote[mat1] = Float.parseFloat(type[6]);
						gun.remat1_zrote[mat1] = Float.parseFloat(type[7]);
						gun.remat1_xmove[mat1] = Float.parseFloat(type[8]);
						gun.remat1_ymove[mat1] = Float.parseFloat(type[9]);
						gun.remat1_zmove[mat1] = Float.parseFloat(type[10]);
						++mat1;
					}
					if (type[0].equals("Remat2") && mat2 < 30) {
						gun.remat2_time[mat2] = Integer.parseInt(type[1]);
						gun.remat2_xpoint[mat2] = Float.parseFloat(type[2]);
						gun.remat2_ypoint[mat2] = Float.parseFloat(type[3]);
						gun.remat2_zpoint[mat2] = Float.parseFloat(type[4]);
						gun.remat2_xrote[mat2] = Float.parseFloat(type[5]);
						gun.remat2_yrote[mat2] = Float.parseFloat(type[6]);
						gun.remat2_zrote[mat2] = Float.parseFloat(type[7]);
						gun.remat2_xmove[mat2] = Float.parseFloat(type[8]);
						gun.remat2_ymove[mat2] = Float.parseFloat(type[9]);
						gun.remat2_zmove[mat2] = Float.parseFloat(type[10]);
						++mat2;
					}
					if (type[0].equals("Remat3") && mat3 < 30) {
						gun.remat3_time[mat3] = Integer.parseInt(type[1]);
						gun.remat3_xpoint[mat3] = Float.parseFloat(type[2]);
						gun.remat3_ypoint[mat3] = Float.parseFloat(type[3]);
						gun.remat3_zpoint[mat3] = Float.parseFloat(type[4]);
						gun.remat3_xrote[mat3] = Float.parseFloat(type[5]);
						gun.remat3_yrote[mat3] = Float.parseFloat(type[6]);
						gun.remat3_zrote[mat3] = Float.parseFloat(type[7]);
						gun.remat3_xmove[mat3] = Float.parseFloat(type[8]);
						gun.remat3_ymove[mat3] = Float.parseFloat(type[9]);
						gun.remat3_zmove[mat3] = Float.parseFloat(type[10]);
						++mat3;
					}
					if (type[0].equals("Remat22") && mat22 < 30) {
						gun.remat22_time[mat22] = Integer.parseInt(type[1]);
						gun.remat22_xpoint[mat22] = Float.parseFloat(type[2]);
						gun.remat22_ypoint[mat22] = Float.parseFloat(type[3]);
						gun.remat22_zpoint[mat22] = Float.parseFloat(type[4]);
						gun.remat22_xrote[mat22] = Float.parseFloat(type[5]);
						gun.remat22_yrote[mat22] = Float.parseFloat(type[6]);
						gun.remat22_zrote[mat22] = Float.parseFloat(type[7]);
						gun.remat22_xmove[mat22] = Float.parseFloat(type[8]);
						gun.remat22_ymove[mat22] = Float.parseFloat(type[9]);
						gun.remat22_zmove[mat22] = Float.parseFloat(type[10]);
						++mat22;
					}
					if (type[0].equals("Remat24") && mat24 < 30) {
						gun.remat24_time[mat24] = Integer.parseInt(type[1]);
						gun.remat24_xpoint[mat24] = Float.parseFloat(type[2]);
						gun.remat24_ypoint[mat24] = Float.parseFloat(type[3]);
						gun.remat24_zpoint[mat24] = Float.parseFloat(type[4]);
						gun.remat24_xrote[mat24] = Float.parseFloat(type[5]);
						gun.remat24_yrote[mat24] = Float.parseFloat(type[6]);
						gun.remat24_zrote[mat24] = Float.parseFloat(type[7]);
						gun.remat24_xmove[mat24] = Float.parseFloat(type[8]);
						gun.remat24_ymove[mat24] = Float.parseFloat(type[9]);
						gun.remat24_zmove[mat24] = Float.parseFloat(type[10]);
						++mat24;
					}
					if (type[0].equals("Remat25") && mat25 < 30) {
						gun.remat25_time[mat25] = Integer.parseInt(type[1]);
						gun.remat25_xpoint[mat25] = Float.parseFloat(type[2]);
						gun.remat25_ypoint[mat25] = Float.parseFloat(type[3]);
						gun.remat25_zpoint[mat25] = Float.parseFloat(type[4]);
						gun.remat25_xrote[mat25] = Float.parseFloat(type[5]);
						gun.remat25_yrote[mat25] = Float.parseFloat(type[6]);
						gun.remat25_zrote[mat25] = Float.parseFloat(type[7]);
						gun.remat25_xmove[mat25] = Float.parseFloat(type[8]);
						gun.remat25_ymove[mat25] = Float.parseFloat(type[9]);
						gun.remat25_zmove[mat25] = Float.parseFloat(type[10]);
						++mat25;
					}
					if (type[0].equals("Remat31") && mat31 < 30) {
						gun.remat31_time[mat31] = Integer.parseInt(type[1]);
						gun.remat31_xpoint[mat31] = Float.parseFloat(type[2]);
						gun.remat31_ypoint[mat31] = Float.parseFloat(type[3]);
						gun.remat31_zpoint[mat31] = Float.parseFloat(type[4]);
						gun.remat31_xrote[mat31] = Float.parseFloat(type[5]);
						gun.remat31_yrote[mat31] = Float.parseFloat(type[6]);
						gun.remat31_zrote[mat31] = Float.parseFloat(type[7]);
						gun.remat31_xmove[mat31] = Float.parseFloat(type[8]);
						gun.remat31_ymove[mat31] = Float.parseFloat(type[9]);
						gun.remat31_zmove[mat31] = Float.parseFloat(type[10]);
						++mat31;
					}
					if (type[0].equals("Remat32") && mat32 < 30) {
						gun.remat32_time[mat32] = Integer.parseInt(type[1]);
						gun.remat32_xpoint[mat32] = Float.parseFloat(type[2]);
						gun.remat32_ypoint[mat32] = Float.parseFloat(type[3]);
						gun.remat32_zpoint[mat32] = Float.parseFloat(type[4]);
						gun.remat32_xrote[mat32] = Float.parseFloat(type[5]);
						gun.remat32_yrote[mat32] = Float.parseFloat(type[6]);
						gun.remat32_zrote[mat32] = Float.parseFloat(type[7]);
						gun.remat32_xmove[mat32] = Float.parseFloat(type[8]);
						gun.remat32_ymove[mat32] = Float.parseFloat(type[9]);
						gun.remat32_zmove[mat32] = Float.parseFloat(type[10]);
						++mat32;
					}
					if (type[0].equals("Rematlefthand") && matlefthand < 30) {
						gun.rematlefthand_time[matlefthand] = Integer.parseInt(type[1]);
						gun.rematlefthand_xpoint[matlefthand] = Float.parseFloat(type[2]);
						gun.rematlefthand_ypoint[matlefthand] = Float.parseFloat(type[3]);
						gun.rematlefthand_zpoint[matlefthand] = Float.parseFloat(type[4]);
						gun.rematlefthand_xrote[matlefthand] = Float.parseFloat(type[5]);
						gun.rematlefthand_yrote[matlefthand] = Float.parseFloat(type[6]);
						gun.rematlefthand_zrote[matlefthand] = Float.parseFloat(type[7]);
						gun.rematlefthand_xmove[matlefthand] = Float.parseFloat(type[8]);
						gun.rematlefthand_ymove[matlefthand] = Float.parseFloat(type[9]);
						gun.rematlefthand_zmove[matlefthand] = Float.parseFloat(type[10]);
						++matlefthand;
					}
					if (type[0].equals("Rematrighthand") && matrighthand < 30) {
						gun.rematrighthand_time[matrighthand] = Integer.parseInt(type[1]);
						gun.rematrighthand_xpoint[matrighthand] = Float.parseFloat(type[2]);
						gun.rematrighthand_ypoint[matrighthand] = Float.parseFloat(type[3]);
						gun.rematrighthand_zpoint[matrighthand] = Float.parseFloat(type[4]);
						gun.rematrighthand_xrote[matrighthand] = Float.parseFloat(type[5]);
						gun.rematrighthand_yrote[matrighthand] = Float.parseFloat(type[6]);
						gun.rematrighthand_zrote[matrighthand] = Float.parseFloat(type[7]);
						gun.rematrighthand_xmove[matrighthand] = Float.parseFloat(type[8]);
						gun.rematrighthand_ymove[matrighthand] = Float.parseFloat(type[9]);
						gun.rematrighthand_zmove[matrighthand] = Float.parseFloat(type[10]);
						++matrighthand;
					}
					
				}
			}
		}
		
		
		/***/
	}
	
}
