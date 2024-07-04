package fr.nozkay;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeUtil {

    public static void requestPlayerCount(Player p,String server) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ServerCount");
        out.writeUTF(server);
        new BukkitRunnable(){
            @Override
            public void run(){
                p.sendPluginMessage(Main.instance,"CCChannel",out.toByteArray());
            }
        }.runTaskLater(Main.instance,10);
    }

    public static void requestServerStatus(String serverName) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ServerStatus");
        out.writeUTF(serverName);
        new BukkitRunnable(){
            @Override
            public void run(){
                Main.instance.getServer().sendPluginMessage(Main.instance,"CCChannel",out.toByteArray());
            }
        }.runTaskLater(Main.instance,2);
    }

    public static void openningServeur(String serverName){
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ServerOpenning");
        out.writeUTF(serverName);
        new BukkitRunnable(){
            @Override
            public void run(){
                Main.instance.getServer().sendPluginMessage(Main.instance,"CCChannel",out.toByteArray());
            }
        }.runTaskLater(Main.instance,2);
    }



    public static void sendPlayerToServer(Player player, String targetServer) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(targetServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(Main.getPlugin(Main.class), "BungeeCord", b.toByteArray());
    }
}
