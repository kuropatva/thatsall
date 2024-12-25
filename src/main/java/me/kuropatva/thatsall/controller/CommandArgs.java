package me.kuropatva.thatsall.controller;

import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;


public class CommandArgs {

    private final Lobby lobby;
    private final Player player;
    private final String commandName;
    //    private Map<String, String> args = new HashMap<>();
    private final String[] args;

    public CommandArgs(Lobby lobby, Player player, String command) {
        this.lobby = lobby;
        this.player = player;
        var a = command.split(" ");
        commandName = a[0];
//        String key = null;
//        StringBuilder value = new StringBuilder();
//        for (int i = 1; i < a.length; i++) {
//            if (a[i].startsWith("-")) {
//                if (key != null) {
//                    args.put(key, value.toString().trim());
//                }
//                value = new StringBuilder();
//                key = a[i].substring(1);
//            } else {
//                value.append(a[i]).append(' ');
//            }
//        }
//        if (key != null) {
//            args.put(key, value.toString().trim());
//        }
        args = a;
    }

    public Lobby lobby() {
        return lobby;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCommandName() {
        return commandName;
    }

//    public Set<String> getKeys() {
//        return args.keySet();
//    }
//
//    public boolean hasKey(String key) {
//        return args.containsKey(key);
//    }
//
//    public String getValue(String key) {
//        return args.get(key);
//    }

    public String getArg(int num) {
        if (num < 0 || args.length - 1 < num) return null;
        return args[num - 1];
    }

    @Override
    public String toString() {
        return "ExecutableCommand{" +
                "game=" + lobby +
                ", player=" + player +
                ", commandName='" + commandName + '\'' +
                ", args=" + args +
                '}';
    }
}
