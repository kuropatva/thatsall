package me.kuropatva.thatsall.controller.commands;

import me.kuropatva.thatsall.controller.CommandArgs;

public class PlayValueCommand extends WebsocketCommand {
    @Override
    public void run(CommandArgs args) {

        args.lobby().game().ready(args.getPlayer());
    }
}
