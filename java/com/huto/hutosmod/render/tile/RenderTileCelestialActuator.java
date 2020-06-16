package com.huto.hutosmod.render.tile;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Random;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.tileentity.TileEntityCelestialActuator;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileCelestialActuator extends TileEntitySpecialRenderer<TileEntityCelestialActuator> {
	private static final Random RANDOM = new Random();

	@Override
	public void render(@Nonnull TileEntityCelestialActuator te, double x, double y, double z, float partticks,
			int digProgress, float unused) {
		   if (!(te instanceof TileEntityCelestialActuator)) return; // should never happen
		   TileEntityCelestialActuator celest = (TileEntityCelestialActuator) te;
		    double powerLevel = celest.getSmoothedNeedlePosition();
		    renderNeedleOnFace(powerLevel, x, y, z, EnumFacing.UP);
		
		//RENDER MANA VALUE
		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);
		DecimalFormat df = new DecimalFormat("0.00");
		String text = df.format(te.getManaValue());
		GlStateManager.translate(0, 1.75, -0.5);
		GlStateManager.rotate(180, 1, 0, 1);
		GlStateManager.scale(0.1, 0.1, 0.1);
		FontRenderer fontRenderer = this.getFontRenderer();
		FontRenderer akloRenderer = ModTextFormatting.getAkloFont();
		akloRenderer.drawString(text, 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFF00FF);
		GlStateManager.popMatrix();
	}
	/**
	   * Render the needle at the current power level
	   * @param powerLevel 0 - 1
	   * @param relativeX the x/y/z distance from the player's eye to the tileentity
	   * @param relativeY
	   * @param relativeZ
	   * @param whichFace which face of the block should the needle render on?
	   */
	  private void renderNeedleOnFace(double powerLevel, double relativeX, double relativeY, double relativeZ,
	                                  EnumFacing whichFace)
	  {
	    double needleSpindleX = 0.5;
	    double needleSpindleY = 0.5;
	    double needleSpindleZ = 0.5;
	    float faceRotationAngle = 0;
	    final float RENDER_NUDGE = 0.001F; // nudge out the needle from its face by a small amount.  If you try to render the
	                                       //  needle directly on top of the face, it leads to 'z-fighting' and looks
	                                      // awful
	    switch (whichFace) {
	      case SOUTH: {
	        needleSpindleZ = 1.0 + RENDER_NUDGE;
	        faceRotationAngle = 0;
	        break;
	      }
	      case NORTH: {
	        needleSpindleZ = 0.0 - RENDER_NUDGE;
	        faceRotationAngle = 180;
	        break;
	      }
	      case EAST: {
	        needleSpindleX = 1.0 + RENDER_NUDGE;
	        faceRotationAngle = 90;
	        break;
	      }
	      case WEST: {
	        needleSpindleX = 0.0 - RENDER_NUDGE;
	        faceRotationAngle = 270;
	        break;
	      }
	      case UP: {
		        needleSpindleX = 0.0 - RENDER_NUDGE;
		        faceRotationAngle = 270;
		        break;
		      }
	      default: {
	        System.err.println("Illegal face in renderNeedleOnFace:" + whichFace);
	      }
	    }

	    try {
	      GL11.glPushMatrix();
	      GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
	      GlStateManager.translate(relativeX + needleSpindleX, relativeY + needleSpindleY, relativeZ + needleSpindleZ);

	      // we now need to rotate the needle:
	      // 1) around the z axis, to spin the needle to show the correct power level
	      // 2) around the vertical axis, to move it to the correct face (EAST, WEST, SOUTH, NORTH)
	      // Rendering transformations take effect in "reverse order" so we have to do needle second

	      // zero degrees is down, increases clockwise with increasing power level
	      final double ZERO_ANGLE = 45;
	      final double MAX_ANGLE = 315;
	      float needleAngle = (float)interpolate(powerLevel, 0, 1, ZERO_ANGLE, MAX_ANGLE);

	      GlStateManager.rotate(faceRotationAngle, 0, 1, 0);   // rotate around the vertical axis to make face correct direction

	      GlStateManager.rotate(-needleAngle, 0, 0, 1);   // rotate around the Z axis (our model is designed that way).  -ve because
	                                                      //  we need to rotate clockwise but rotate expects anticlockwise

	      Tessellator tessellator = Tessellator.getInstance();
	      BufferBuilder bufferBuilder = tessellator.getBuffer();
//	      this.bindTexture(needleTexture);         // we don't need a texture for the needle appearance - we're just using
	                                                 //  solid colour

	      GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)
	      GL11.glDisable(GL11.GL_BLEND);        // turn off "alpha" transparency blending
	      GL11.glDepthMask(true);               // needle is hidden behind other objects
	      GL11.glDisable(GL11.GL_TEXTURE_2D);   // turn off texturing - we're just going to draw a single-colour needle.
	                                            //   See MBE21 for rendering with texture.
	      Color needleColour = Color.RED;
	      GlStateManager.color(needleColour.getRed()/255.0F,
	                           needleColour.getGreen()/255.0F,
	                           needleColour.getBlue()/255.0F);     // change the rendering colour
	      final int SKY_LIGHT_VALUE = 15;
	      final int BLOCK_LIGHT_VALUE = 0;
	      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, SKY_LIGHT_VALUE * 16.0F, BLOCK_LIGHT_VALUE * 16.0F);
	      bufferBuilder.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION);
	      addNeedleVertices(bufferBuilder);
	      tessellator.draw();
	    } finally {
	      GL11.glPopAttrib();
	      GL11.glPopMatrix();
	    }
	  }

	  // add the vertices for drawing the needle.  Normally you would use a model loader for a more complicated shape
	  private void addNeedleVertices(BufferBuilder vertexBuffer) {
	    // needle is a triangle pointing down, the rotation spindle is at [0,0,0] and the spindle points north-south,
	    //  i.e. the needle renders in the z=0 plane; rotating the needle around the spindle will make the needle point
	    //  down, east, up, west.
	    // anticlockwise vertices -> the spindle is facing south, i.e. the needle will display on the south side of a block

	    vertexBuffer.pos(0.00, -0.45, 0.00).endVertex();  // tip (pointer) of needle
	    vertexBuffer.pos(0.05, 0.05, 0.00).endVertex();  // stub - one side
	    vertexBuffer.pos(-0.05, 0.05, 0.00).endVertex();  // stub - other side
	  }
	  
	  /** linearly interpolate for y between [x1, y1] to [x2, y2] using x
	   *  y = y1 + (y2 - y1) * (x - x1) / (x2 - x1)
	   *  For example:  if [x1, y1] is [0, 100], and [x2,y2] is [1, 200], then as x increases from 0 to 1, this function
	   *    will increase from 100 to 200
	   * @param x  the x value to linearly interpolate on
	   * @param x1
	   * @param x2
	   * @param y1
	   * @param y2
	   * @return linearly interpolated value.  If x is outside the range, clip it to the nearest end
	   */
	  public static double interpolate(double x, double x1, double x2, double y1, double y2)
	  {
	    if (x1 > x2) {
	      double temp = x1; x1 = x2; x2 = temp;
	      temp = y1; y1 = y2; y2 = temp;
	    }

	    if (x <= x1) return y1;
	    if (x >= x2) return y2;
	    double xFraction = (x - x1) / (x2 - x1);
	    return y1 + xFraction * (y2 - y1);
	  }
}
