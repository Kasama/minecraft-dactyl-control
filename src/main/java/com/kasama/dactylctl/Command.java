package com.kasama.dactylctl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Command {
  private GUIEventHandler guiEventHandler;

  private enum ActionType {
    RESET,
  }

  Command(GUIEventHandler guiEventHandler) {
    this.guiEventHandler = guiEventHandler;
  }

  @SubscribeEvent
  public void onRegisterClientCommandsEvent(RegisterClientCommandsEvent event) {
    CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
    this.register(dispatcher);
  }

  public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
    LiteralArgumentBuilder<CommandSourceStack> cmd = Commands.literal("dactylctl")
        .then(Commands.literal("reset").executes((ctx) -> this.handle(ctx, ActionType.RESET)));
    dispatcher.register(cmd);
  }

  private int handle(CommandContext<CommandSourceStack> ctx, ActionType actionType) {
    switch (actionType) {
      case RESET:
        this.guiEventHandler.reset_counter();
        return 0;
      default:
        break;
    }
    return 1;
  }
}
