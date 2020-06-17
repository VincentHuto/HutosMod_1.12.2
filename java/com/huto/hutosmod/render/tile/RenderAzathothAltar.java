/*package CthulhuBlockRenders;

import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import CthulhuBlockModels.ModelEldritchAltar;
import CthulhuEntityModels.ModelAzathoth;
import Main.CthulhuVerum;

public class RenderAzathothAltar extends TileEntitySpecialRenderer {

	private static final ResourceLocation texture = new ResourceLocation(
			CthulhuVerum.MODID + ":" + "textures/blocks/ModelEldritchAltar.png");
	private static final ResourceLocation Stexture = new ResourceLocation(
			CthulhuVerum.MODID + ":" + "textures/entity/squid.png");
	private static final ResourceLocation AZtexture = new ResourceLocation(
			CthulhuVerum.MODID + ":" + "textures/entity/ModelAzathoth.png");
	
	
	private ModelSquid modelSquid = new ModelSquid();
	private ModelAzathoth modelAzathoth = new ModelAzathoth();

	private ModelEldritchAltar model;

	public RenderAzathothAltar() {
		this.model = new ModelEldritchAltar();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float f) {
		Tessellator tess = Tessellator.instance;

		GL11.glPushMatrix();

		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);
		this.bindTexture(texture);
		GL11.glScaled(1, 1, 1);
		GL11.glPushMatrix();
		this.model.renderModel(0.0625F);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScaled(.25, .25, .25);
		GL11.glPopMatrix();

		GL11.glPopMatrix();
		
		this.bindTexture(Stexture);

		float rot = 0.0F;

		if (tileentity.getWorldObj() != null)
		{
			rot = tileentity.getWorldObj().getWorldTime() % 360L;
		}

		GL11.glPushMatrix();
		
		GL11.glColor4f(0F, 1F, 0F, 1.0F);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		GL11.glRotatef(-180F, 1.0F, 90.0F, 90.0F);

		GL11.glRotatef(-180F, 1.0F, 90.0F, 90.0F);
		GL11.glTranslatef(0.0F, 0.4F, 0.0F);

		GL11.glRotatef(rot , 0.0F, 1.0F, 0.0F);

		GL11.glScaled(.5, .5, .5);

		modelSquid.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,0.0625F);
		
		GL11.glPopMatrix();		

		this.bindTexture(AZtexture);


		if (tileentity.getWorldObj() != null)
		{
			rot = tileentity.getWorldObj().getWorldTime() % 360L ;
		}

		GL11.glPushMatrix();
		
		GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

		GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, -2F, 0.0F);

	
		GL11.glRotatef(rot , 0.0F, 1.0F, 0.0F);
		GL11.glScaled(.5, .5, .5);

		modelAzathoth.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,0.0625F);
		GL11.glPopMatrix();
		
		this.bindTexture(CAtexture);


		if (tileentity.getWorldObj() != null)
		{
			rot = tileentity.getWorldObj().getWorldTime() % 360L ;
		}

		GL11.glPushMatrix();
		
		GL11.glColor4f(0.1F, 0.1F, 0.1F, 1F);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);

		GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, -2F, 0.0F);

	
		GL11.glRotatef(rot , 0.0F, 1.0F, 0.0F);
		GL11.glScaled(.5, .5, .5);

		modelCthulu.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,0.0625F);
		GL11.glPopMatrix();

	}
	
	
}


*/