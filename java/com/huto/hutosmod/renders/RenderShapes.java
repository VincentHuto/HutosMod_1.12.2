package com.huto.hutosmod.renders;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)

public class RenderShapes {

	public static void drawSphere(double r, int lats, int longs) {
	    int i, j;
	    
	    
	    for(i = 0; i <= lats; i++) {
	        double lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
	        double z0  = Math.sin(lat0);
	        double zr0 =  Math.cos(lat0);

	        double lat1 = Math.PI * (-0.5 + (double) i / lats);
	        double z1 = Math.sin(lat1);
	        double zr1 = Math.cos(lat1);

	        GL11.glBegin(0x8);
	        for(j = 0; j <= longs; j++) {
	            double lng = 2 * Math.PI * (double) (j - 1) / longs;
	            double x = Math.cos(lng);
	            double y = Math.sin(lng);

	        GL11.glNormal3d(x * zr0, y * zr0, z0);
	        GL11.glVertex3d(r * x * zr0, r * y * zr0, r * z0);
	        GL11.glNormal3d(x * zr1, y * zr1, z1);
	        GL11.glVertex3d(r * x * zr1, r * y * zr1, r * z1);
	        }
	        GL11.glEnd();
	    }
	}
	
}
