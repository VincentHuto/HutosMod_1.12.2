/*package com.huto.hutosmod.depricated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.gui.pages.GuiTomePage;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class GuiPageTome0 extends GuiTomePage {
 
    public GuiPageTome0(int pageNum, String title, String subtitle, ItemStack icon, String text) {
		super(pageNum, title, subtitle, icon, text);
		// TODO Auto-generated constructor stub
	}

	String title = getClass().getSimpleName().replace("Gui", "").replace("Tome", " ");
    String subtitle = "In the beginning";

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	
        drawDefaultBackground();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        int centerX = (width / 2) - guiWidth / 2;
        int centerY = (height / 2) - guiHeight / 2;
        //drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
        //drawString(fontRendererObj, "Tutorial", centerX, centerY, 0x6028ff);
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.color(1, 1, 1, 1);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
        }
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate((width / 2) - fontRenderer.getStringWidth(title), centerY + 10, 0);
            GlStateManager.scale(2, 2, 2);
            fontRenderer.drawString(title, 0, 0, 0x6028ff);
            GlStateManager.scale(0.5, 0.5, 0.5);
            fontRenderer.drawString(subtitle, 0, 15, 0x6028ff);

        }
        GlStateManager.popMatrix();
        //super.drawScreen(mouseX, mouseY, partialTicks);
        buttonclose.drawButton(mc, mouseX, mouseY, 111);
        arrowF.drawButton(mc, mouseX, mouseY, 111);

        GlStateManager.translate(3, 0, 0);
        ItemStack icon = new ItemStack(ItemRegistry.channeling_ingot);
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(centerX, centerY, 0);
            GlStateManager.scale(2, 2, 2);
            mc.getRenderItem().renderItemAndEffectIntoGUI(icon, 2, 1);
        }
        GlStateManager.popMatrix();
        textBox.drawTextBox();
        List<String> text = new ArrayList<String>();
        text.add(I18n.format(icon.getDisplayName()));
        text.add(I18n.format("gui.tooltip69", mc.world.provider.getDimension()));
        drawTooltip(text, mouseX, mouseY, centerX, centerY, 16 * 2, 16 * 2);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case BUTTONCLOSE:
                mc.displayGuiScreen(null);
                break;
            case ARROWF:
                mc.displayGuiScreen(new GuiPageTome1());
                break;
         
        }
        updateButtons();
        super.actionPerformed(button);
    }


}*/