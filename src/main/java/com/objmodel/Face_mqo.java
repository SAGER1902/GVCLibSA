package objmodel;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Face_mqo
{
	public int[] verticesID;
	public Vertex_mqo[] vertices;
	public Vertex_mqo[] vertexNormals;
	public Vertex_mqo faceNormal;
	public TextureCoordinate_mqo[] textureCoordinates;

	public Face_mqo copy()
	{
		Face_mqo f = new Face_mqo();

		return f;
	}

	public void addFaceForRender(Tessellator2 tessellator)
	{
		addFaceForRender(tessellator, 0.000F);
	}

	public void addFaceForRender(Tessellator2 tessellator, float textureOffset)
	{
		if (faceNormal == null)
		{
			faceNormal = this.calculateFaceNormal();
		}

		tessellator.setNormal(faceNormal.x, faceNormal.y, faceNormal.z);

		float averageU = 0F;
		float averageV = 0F;

		if ((textureCoordinates != null) && (textureCoordinates.length > 0))
		{
			for (int i = 0; i < textureCoordinates.length; ++i)
			{
				averageU += textureCoordinates[i].u;
				averageV += textureCoordinates[i].v;
			}

			averageU = averageU / textureCoordinates.length;
			averageV = averageV / textureCoordinates.length;
		}

		float offsetU, offsetV;

		for (int i = 0; i < vertices.length; ++i)
		{

			if ((textureCoordinates != null) && (textureCoordinates.length > 0))
			{
				offsetU = textureOffset;
				offsetV = textureOffset;

				if (textureCoordinates[i].u > averageU)
				{
					offsetU = -offsetU;
				}
				if (textureCoordinates[i].v > averageV)
				{
					offsetV = -offsetV;
				}

				if(this.vertexNormals!=null && i<this.vertexNormals.length)
				{
					tessellator.setNormal(this.vertexNormals[i].x, this.vertexNormals[i].y, this.vertexNormals[i].z);
				}

				tessellator.addVertexWithUV(vertices[i].x, vertices[i].y, vertices[i].z, textureCoordinates[i].u + offsetU, textureCoordinates[i].v + offsetV);
			}
			else
			{
				tessellator.addVertex(vertices[i].x, vertices[i].y, vertices[i].z);
			}
		}
	}

	public Vertex_mqo calculateFaceNormal()
	{
		Vec3 v1 = Vec3.createVectorHelper(vertices[1].x - vertices[0].x, vertices[1].y - vertices[0].y, vertices[1].z - vertices[0].z);
		Vec3 v2 = Vec3.createVectorHelper(vertices[2].x - vertices[0].x, vertices[2].y - vertices[0].y, vertices[2].z - vertices[0].z);
		Vec3 normalVector = null;

		normalVector = v1.crossProduct(v2).normalize();

		return new Vertex_mqo((float) normalVector.xCoord, (float) normalVector.yCoord, (float) normalVector.zCoord);
	}
}