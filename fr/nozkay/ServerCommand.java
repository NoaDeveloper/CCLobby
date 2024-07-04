package fr.nozkay;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerCommand implements CommandExecutor {

    private FileManager fileManager = new FileManager("grade.txt");


    public ServerCommand() {
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(s.equalsIgnoreCase("openserv")){
                if(args.length > 0){
                    if(fileManager.getGrade(p.getUniqueId().toString()).equalsIgnoreCase("ADMIN") || fileManager.getGrade(p.getUniqueId().toString()).equalsIgnoreCase("DEV") ||  fileManager.getGrade(p.getUniqueId().toString()).equalsIgnoreCase("STAFF") || fileManager.getGrade(p.getUniqueId().toString()).equalsIgnoreCase("RESP") ||
                            fileManager.getGrade(p.getUniqueId().toString()).equalsIgnoreCase("HOST") || fileManager.getGrade(p.getUniqueId().toString()).equalsIgnoreCase("MODO")){
                        test(args[0]);
                    }else{
                        p.sendMessage(Main.prefix() + "§cVous n'avez pas la permission pour réalisé cela...");
                    }
                }else{
                    p.sendMessage(Main.prefix() + "§cMauvaise formulation: /openserv [Nom du serveur(UHC, UHC-2, UHC-3)]");
                }
            }
        }
        return false;
    }

    public void test(String name){
        BungeeUtil.openningServeur(name);
    }
}
