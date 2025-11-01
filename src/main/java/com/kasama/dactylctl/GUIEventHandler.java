package com.kasama.dactylctl;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = "dactylctl", bus = Bus.FORGE, value = Dist.CLIENT)
public class GUIEventHandler {

  static final String VENDOR_ID = "0x4B41";
  static final String PRODUCT_ID = "0x636D";
  static final int BASE_LAYER = 0;
  static final int GAME_LAYER = 2;

  public AtomicInteger openedTimes;
  private Logger logger;

  public GUIEventHandler(Logger logger) {
    this.openedTimes = new AtomicInteger(0);
    this.logger = logger;
  }

  private static void changeLayer(int layer) {
    ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(
        "dactyl-remote-control",
        "--vid", VENDOR_ID,
        "--pid", PRODUCT_ID,
        "change-keyboard-layer",
        Integer.toString(layer)));

    try {
      processBuilder.start();
    } catch (IOException e) {
    }
  }

  @SubscribeEvent
  public void screenOpen(ScreenEvent.Opening event) {
    int openedTimes = this.openedTimes.incrementAndGet();
    String title = event.getScreen().getTitle().getString();
    this.logger.info("Screen event: {} on window {}, opened: {} times", event.toString(), title, openedTimes);
    this.logger.info("Changed keyboard to base layer");
    changeLayer(BASE_LAYER);
  }

  @SubscribeEvent
  public void screenClose(ScreenEvent.Closing event) {
    int openedTimes = this.openedTimes.decrementAndGet();
    this.openedTimes.set(Math.max(0, openedTimes));
    openedTimes = this.openedTimes.get();
    String title = event.getScreen().getTitle().getString();
    this.logger.info("Screen event: {} on window {}, opened: {} times", event.toString(), title, openedTimes);
    if (openedTimes <= 0) {
      this.logger.info("Changed keyboard to game layer");
      changeLayer(GAME_LAYER);
    }
  }

  public void reset_counter() {
    this.openedTimes.set(0);
    this.logger.info("Reset opened times to 0");
    changeLayer(GAME_LAYER);
  }
}
