package objmodel;

import java.util.ArrayList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GroupObject_mqo2
{
	public String name;
	public ArrayList<Face_mqo> faces = new ArrayList<Face_mqo>();
	public int glDrawingMode;

	public GroupObject_mqo2()
	{
		this("");
	}

	public GroupObject_mqo2(String name)
	{
		this(name, -1);
	}

	public GroupObject_mqo2(String name, int glDrawingMode)
	{
		this.name = name;
		this.glDrawingMode = glDrawingMode;
	}

	public void render()
	{
		if (faces.size() > 0)
		{
			Tessellator2 tessellator = Tessellator2.instance;
			tessellator.startDrawing(glDrawingMode);
			render(tessellator);
			tessellator.draw();
		}
	}

	public void render(Tessellator2 tessellator)
	{
		if (faces.size() > 0)
		{
			for (Face_mqo face : faces)
			{
				face.addFaceForRender(tessellator);
			}
		}
	}
}