package gvclib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;


public class Createjson
{
	
	public static void addjson(Item item, String modname,  String name, boolean debag) {
		/*if(debag) {
		Createjson.load(modname, name);
		}*/
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modname + ":" + name, "inventory"));
	}
	public static void addjson_domain(Item item, String modname,  String name, String domain, boolean debag) {
		/*if(debag) {
		Createjson.load_domain(modname, name, domain);
		}*/
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modname + ":" + name, "inventory"));
	}
	public static void addjsonblock(Block block, String modname,  String name, boolean debag) {
		/*if(debag) {
		Createjson.loadblock(modname, name);
		}*/
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modname + ":" + name, "inventory"));
	}
	public static void addjsongun(Item item, String modname,  String name, boolean debag) {
		/*if(debag) {
			Createjson.loadgun(modname, name);
		}*/
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modname + ":" + name, "inventory"));
	}
	public static void addjsongun_domain(Item item, String modname,  String name, String domain, boolean debag) {
		/*if(debag) {
			Createjson.loadgun_domain(modname, name, domain);
		}*/
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modname + ":" + name, "inventory"));
	}
	public static void addjson_tool(Item item, String modname,  String name, boolean debag) {
		/*if(debag) {
		Createjson.load_tool(modname, name);
		}*/
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modname + ":" + name, "inventory"));
	}
	
	
	public static void addjsonblock_tile(Block block, String modname,  String name, boolean debag) {
		/*if(debag) {
		Createjson.loadblock_tile(modname, name);
		}*/
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modname + ":" + name, "inventory"));
	}
	
	public static void addjsonblock_tile_facing(Block block, String modname,  String name, boolean debag) {
		/*if(debag) {
		Createjson.loadblock_tile_facing(modname, name,"");
		}*/
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modname + ":" + name, "inventory"));
	}
	
	public static void addjsonblock_tile_facing_domain(Block block, String modname,  String name, String domain, boolean debag) {
		/*if(debag) {
		Createjson.loadblock_tile_facing(modname, name, domain);
		}*/
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(modname + ":" + name, "inventory"));
	}
	
	public static void load(String modname,  String itemname)
	 {
		//if(pEvent.getSide().isClient()) 
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " + "\"item/generated\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("\"layer0\"" + ": " +"\"" +modname +  ":items/" + itemname + "\"");
		    		  bw.newLine();
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		
   }
	
	public static void load_domain(String modname,  String itemname, String domain)
	 {
		//if(pEvent.getSide().isClient()) 
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " + "\"item/generated\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  //bw.write("\"layer0\"" + ": " +"\"" +modname +  ":items/" + itemname + "\"");
		    		  bw.write("\"layer0\"" + ": " +"\"" +modname +  ":items/" + domain + "/"+itemname + "\"");
		    		  bw.newLine();
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		
  }
	
	public static void load_tool(String modname,  String itemname)
	 {
		//if(pEvent.getSide().isClient()) 
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " + "\"item/handheld\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("\"layer0\"" + ": " +"\"" +modname +  ":items/" + itemname + "\"");
		    		  bw.newLine();
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		
  }
	
	public static void loadblock(String modname,  String itemname)
	 {
		//if(pEvent.getSide().isClient()) 
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "item");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "item"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " +"\"" +modname +  ":block/" + itemname + "\"";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "block");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "block"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " + "\"block/cube_all\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("\"all\"" + ": " +"\"" +modname +  ":blocks/" + itemname + "\"");
		    		  bw.newLine();
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "blockstates");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "blockstates"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"forge_marker\"" + ": " + "1,";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"defaults\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("\"particle\"" + ": " +"\"" +modname +  ":blocks/" + itemname + "\"");
		    		  bw.newLine();
		    		  bw.write("},");
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"model\"" + ": " +"\"" +modname +  ":" + itemname + "\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"uvlock\"" + ": " + "false";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "},";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"variants\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"normal\"" + ": " + "[{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("}],");
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"inventory\"" + ": " + "[{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("}]");
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "}";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		
  }
	
	public static void loadblock_tile(String modname,  String itemname)
	 {
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "item");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "item"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " + "\"item/generated\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("\"layer0\"" + ": " +"\"" +modname +  ":items/" + itemname + "\"");
		    		  bw.newLine();
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "block");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "block"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " + "\"block/cube_all\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("\"all\"" + ": " +"\"" +modname +  ":blocks/" + "nulls" + "\"");
		    		  bw.newLine();
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "blockstates");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "blockstates"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"variants\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"normal\"" + ": " + "{ ";
		    		  bw.write(s);
		    		  String s2 = "\"model\"" + ": " +"\"" +modname +  ":" + itemname + "\" }";
		    		  bw.write(s2);
		    		  bw.newLine();
		    	  }
		    	 
		    	  {
		    		  String s = "}";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		
 }
	
	
	public static void loadblock_tile_facing(String modname,  String itemname, String domain)
	 {
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "item");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "item"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " + "\"item/generated\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  if(domain.equals("")) {
		    			  bw.write("\"layer0\"" + ": " +"\"" +modname +  ":items/" + itemname + "\"");
		    		  }else {
		    			  bw.write("\"layer0\"" + ": " +"\"" +modname +  ":items/" + domain + "/"+itemname + "\"");
		    		  }
		    		  bw.newLine();
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "block");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "block"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  /*{
		    		  String s = "\"parent\"" + ": " + "\"block/orientable\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }*/
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("\"top\"" + ": " +"\"" +modname +  ":blocks/" + "nulls" + "\"" + ",");
		    		  bw.write("\"front\"" + ": " +"\"" +modname +  ":blocks/" + "nulls" + "\"" + ",");
		    		  bw.write("\"side\"" + ": " +"\"" +modname +  ":blocks/" + "nulls" + "\"");
		    		  bw.newLine();
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "blockstates");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "json"+ File.separatorChar + "blockstates"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"variants\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"facing=north\"" + ": " + "{ ";
		    		  bw.write(s);
		    		  String s2 = "\"model\"" + ": " +"\"" +modname +  ":" + itemname + "\" },";
		    		  bw.write(s2);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"facing=south\"" + ": " + "{ ";
		    		  bw.write(s);
		    		  String s2 = "\"model\"" + ": " +"\"" +modname +  ":" + itemname + "\" },";
		    		  bw.write(s2);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"facing=west\"" + ": " + "{ ";
		    		  bw.write(s);
		    		  String s2 = "\"model\"" + ": " +"\"" +modname +  ":" + itemname + "\" },";
		    		  bw.write(s2);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"facing=east\"" + ": " + "{ ";
		    		  bw.write(s);
		    		  String s2 = "\"model\"" + ": " +"\"" +modname +  ":" + itemname + "\" }";
		    		  bw.write(s2);
		    		  bw.newLine();
		    	  }
		    	 
		    	  {
		    		  String s = "}";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
		
}
	
	public static void loadgun(String modname,  String itemname)
	 {
		//if(pEvent.getSide().isClient()) 
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " + "\"item/generated\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("\"layer0\"" + ": " +"\"" +modname +  ":items/" + itemname + "\"");
		    		  bw.newLine();
		    		  bw.write("},");
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"display\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  {
		    			  bw.write("\"thirdperson_righthand\"" + ": " + "{");
			    		  bw.newLine();
			    		  bw.write("\"rotation\"" + ": " + "[ -80, 260, -40 ],");
			    		  bw.newLine();
			    		  bw.write("\"translation\"" + ": " + "[ -1, 0, -1 ],");
			    		  bw.newLine();
			    		  bw.write("\"scale\"" + ": " + "[ 0.01, 0.01, 0.01 ]");
			    		  bw.newLine();
			    		  bw.write("},");
			    		  bw.newLine();
		    		  }
		    		  {
		    			  bw.write("\"thirdperson_lefthand\"" + ": " + "{");
			    		  bw.newLine();
			    		  bw.write("\"rotation\"" + ": " + "[ -80, -280, 40 ],");
			    		  bw.newLine();
			    		  bw.write("\"translation\"" + ": " + "[ -1, 0, -1 ],");
			    		  bw.newLine();
			    		  bw.write("\"scale\"" + ": " + "[ 0.01, 0.01, 0.01 ]");
			    		  bw.newLine();
			    		  bw.write("},");
			    		  bw.newLine();
		    		  }
		    		  {
		    			  bw.write("\"firstperson_righthand\"" + ": " + "{");
			    		  bw.newLine();
			    		  bw.write("\"rotation\"" + ": " + "[ 0, -90, 25 ],");
			    		  bw.newLine();
			    		  bw.write("\"translation\"" + ": " + "[ 1.13, -10, 1.13],");
			    		  bw.newLine();
			    		  bw.write("\"scale\"" + ": " + "[ 0.68, 0.68, 0.68 ]");
			    		  bw.newLine();
			    		  bw.write("},");
			    		  bw.newLine();
		    		  }
		    		  {
		    			  bw.write("\"firstperson_lefthand\"" + ": " + "{");
			    		  bw.newLine();
			    		  bw.write("\"rotation\"" + ": " + "[ 0, 90, -25 ],");
			    		  bw.newLine();
			    		  bw.write("\"translation\"" + ": " + "[ 1.13, -10, 1.13],");
			    		  bw.newLine();
			    		  bw.write("\"scale\"" + ": " + "[ 0.68, 0.68, 0.68 ]");
			    		  bw.newLine();
			    		  bw.write("}");
			    		  bw.newLine();
		    		  }
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
  }
	
	public static void loadgun_domain(String modname,  String itemname, String domain)
	 {
		//if(pEvent.getSide().isClient()) 
		{
			try {
				File dir = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson");
				dir.mkdirs();
				
		    	File newfile = new File(mod_GVCLib.proxy.ProxyFile(), "mods" + File.separatorChar + modname + File.separatorChar + "itemjson"
		    	+ File.separatorChar + itemname + ".json");
		    	  newfile.createNewFile();
		    	  BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
		    	  bw.write("{");
		    	  bw.newLine();
		    	  {
		    		  String s = "\"parent\"" + ": " + "\"item/generated\",";
		    		  bw.write(s);
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"textures\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  bw.write("\"layer0\"" + ": " +"\"" +modname +  ":items/" + domain + "/"+itemname + "\"");
		    		  bw.newLine();
		    		  bw.write("},");
		    		  bw.newLine();
		    	  }
		    	  {
		    		  String s = "\"display\"" + ": " + "{";
		    		  bw.write(s);
		    		  bw.newLine();
		    		  {
		    			  bw.write("\"thirdperson_righthand\"" + ": " + "{");
			    		  bw.newLine();
			    		  bw.write("\"rotation\"" + ": " + "[ -80, 260, -40 ],");
			    		  bw.newLine();
			    		  bw.write("\"translation\"" + ": " + "[ -1, 0, -1 ],");
			    		  bw.newLine();
			    		  bw.write("\"scale\"" + ": " + "[ 0.01, 0.01, 0.01 ]");
			    		  bw.newLine();
			    		  bw.write("},");
			    		  bw.newLine();
		    		  }
		    		  {
		    			  bw.write("\"thirdperson_lefthand\"" + ": " + "{");
			    		  bw.newLine();
			    		  bw.write("\"rotation\"" + ": " + "[ -80, -280, 40 ],");
			    		  bw.newLine();
			    		  bw.write("\"translation\"" + ": " + "[ -1, 0, -1 ],");
			    		  bw.newLine();
			    		  bw.write("\"scale\"" + ": " + "[ 0.01, 0.01, 0.01 ]");
			    		  bw.newLine();
			    		  bw.write("},");
			    		  bw.newLine();
		    		  }
		    		  {
		    			  bw.write("\"firstperson_righthand\"" + ": " + "{");
			    		  bw.newLine();
			    		  bw.write("\"rotation\"" + ": " + "[ 0, -90, 25 ],");
			    		  bw.newLine();
			    		  bw.write("\"translation\"" + ": " + "[ 1.13, -10, 1.13],");
			    		  bw.newLine();
			    		  bw.write("\"scale\"" + ": " + "[ 0.68, 0.68, 0.68 ]");
			    		  bw.newLine();
			    		  bw.write("},");
			    		  bw.newLine();
		    		  }
		    		  {
		    			  bw.write("\"firstperson_lefthand\"" + ": " + "{");
			    		  bw.newLine();
			    		  bw.write("\"rotation\"" + ": " + "[ 0, 90, -25 ],");
			    		  bw.newLine();
			    		  bw.write("\"translation\"" + ": " + "[ 1.13, -10, 1.13],");
			    		  bw.newLine();
			    		  bw.write("\"scale\"" + ": " + "[ 0.68, 0.68, 0.68 ]");
			    		  bw.newLine();
			    		  bw.write("}");
			    		  bw.newLine();
		    		  }
		    		  bw.write("}");
		    		  bw.newLine();
		    	  }
		    	  
		    	  bw.write("}");
		    	bw.close();
		  } catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		  } catch (IOException ex) {
		    ex.printStackTrace();
		  }
		}
 }
}

