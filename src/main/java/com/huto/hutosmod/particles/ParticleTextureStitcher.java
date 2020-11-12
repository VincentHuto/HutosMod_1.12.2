package com.huto.hutosmod.particles;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by TGG on 19/06/2015.
 * Inserts our custom flame texture into the blocks+items texture sheet.
 */
public class ParticleTextureStitcher
{
  @SubscribeEvent
  public void stitcherEventPre(TextureStitchEvent.Pre event) {
    ResourceLocation flameRL = new ResourceLocation(Reference.MODID + ":particle/flame_fx");
    event.getMap().registerSprite(flameRL);
    ResourceLocation smoothBubbleRL = new ResourceLocation(Reference.MODID + ":particle/SmoothBuble1");
    event.getMap().registerSprite(smoothBubbleRL);
    ResourceLocation BaseFxRL = new ResourceLocation(Reference.MODID + ":particle/smoothbuble1");
    event.getMap().registerSprite(BaseFxRL);
    ResourceLocation ringEffectRL = new ResourceLocation(Reference.MODID + ":particle/ring_particle");
    event.getMap().registerSprite(ringEffectRL);
    
  }
}
