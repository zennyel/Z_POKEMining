package com.cobblemonmasters.zennyel;

import com.cobblemonmasters.zennyel.listeners.BlockBreak;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Mining extends JavaPlugin {

    @Override
    public void onEnable() {
        sendStartMessage();
        registerListener();
        saveDefaultConfig();
    }

    public void sendStartMessage(){
        List<String> messages = new ArrayList<>();
        messages.add("§aZMining Starting  §7v0.3");
        messages.add("§aPlugin loaded successfully");
        for(String msg : messages){
            Bukkit.getConsoleSender().sendMessage(msg);
        }
    }

    public void registerListener(){
        Bukkit.getPluginManager().registerEvents(new BlockBreak(this.getConfig()), this);
    }

}
