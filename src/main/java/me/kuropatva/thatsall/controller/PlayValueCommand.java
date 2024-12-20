package me.kuropatva.thatsall.controller;

public class PlayValueCommand extends WebsocketCommand {
    @Override
    public void run(CommandArgs args) {

        args.lobby().game().ready(args.getPlayer());
    }
}
