package com.kasama.dactylctl;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DactylCtl.MODID)
public class DactylCtl {
  // Define mod id in a common place for everything to reference
  public static final String MODID = "dactylctl";
  // Directly reference a slf4j logger
  private static final Logger LOGGER = LogUtils.getLogger();

  public DactylCtl() {
    // Register ourselves for server and other game events we are interested in
    GUIEventHandler guiEventHandler = new GUIEventHandler(LOGGER);
    Command commands = new Command(guiEventHandler);
    MinecraftForge.EVENT_BUS.register(guiEventHandler);
    MinecraftForge.EVENT_BUS.register(commands);
  }
}
