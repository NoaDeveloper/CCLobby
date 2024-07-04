package fr.nozkay;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class BungeeMessageListener implements PluginMessageListener {

    Lobby lobby = new Lobby();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(channel.equals("CCChannel")){
            final ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            final String sub = in.readUTF();
            if(sub.equals("ServerStatus")){
                String serv = in.readUTF();
                String status = in.readUTF();
                lobby.handleStatus(serv,status);
            }if(sub.equals("ServerCount")){
                String server = in.readUTF();
                int playercount = in.readInt();
                lobby.handlePlayerCount(server, playercount);
            }
        }
    }
}
