package fr.nozkay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main instance;

    public FileManager fileManager;

    public Main() {
    }

    public void onEnable() {
        instance = this;
        fileManager = new FileManager("grade.txt");
        this.RegisterEvents(fileManager);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "CCChannel");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "CCChannel", new BungeeMessageListener());
    }


    public void RegisterEvents(FileManager file) {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Lobby(), this);
        getCommand("setgrade").setExecutor(new GradeCommand());
        getCommand("openserv").setExecutor(new ServerCommand());
    }


    public static String prefix() {
        return "§9§lLa Garderie §7» §f";
    }
}