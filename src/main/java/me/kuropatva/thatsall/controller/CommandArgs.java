package me.kuropatva.thatsall.controller;

import me.kuropatva.thatsall.model.lobby.Lobby;
import me.kuropatva.thatsall.model.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class CommandArgs {

    private Lobby lobby;
    private Player player;
    private String commandName;
    private Map<String, String> args = new HashMap<>();

    public CommandArgs(Lobby lobby, Player player, String command) {
        this.lobby = lobby;
        this.player = player;
        var a = command.split(" ");
        commandName = a[0];
        String key = null;
        StringBuilder value = new StringBuilder();
        for (int i = 1; i < a.length; i++) {
            if (a[i].startsWith("-")) {
                if (key != null) {
                    args.put(key, value.toString().trim());
                }
                value = new StringBuilder();
                key = a[i].substring(1);
            } else {
                value.append(a[i]).append(' ');
            }
        }
        if (key != null) {
            args.put(key, value.toString().trim());
        }
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

    public Set<String> getKeys() {
        return args.keySet();
    }

    public boolean hasKey(String key) {
        return args.containsKey(key);
    }

    public String getValue(String key) {
        return args.get(key);
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
