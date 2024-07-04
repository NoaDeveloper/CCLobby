package fr.nozkay;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GradeCommand implements CommandExecutor {

    private FileManager fileManager = new FileManager("grade.txt");


    public GradeCommand() {
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(s.equalsIgnoreCase("setgrade")){
                if(args.length > 1){
                    if(fileManager.getGrade(p.getUniqueId().toString()).equalsIgnoreCase("ADMIN") || fileManager.getGrade(p.getUniqueId().toString()).equalsIgnoreCase("DEV")){
                        Player pl = Bukkit.getPlayer(args[0]);
                        if(pl != null){
                            if(args[1].equalsIgnoreCase("ADMIN")||args[1].equalsIgnoreCase("JOUEUR")||args[1].equalsIgnoreCase("RESP")||args[1].equalsIgnoreCase("MODO")||args[1].equalsIgnoreCase("DEV")||args[1].equalsIgnoreCase("STAFF")){
                                fileManager.writeToFile(pl.getUniqueId().toString(), args[1]);
                                p.sendMessage(Main.prefix() + "Vous avez bien changé le grade du joueur !");
                                pl.sendMessage(Main.prefix() + "Votre grade à été changé vous devez deco/reco");
                            }
                        }
                    }else{
                        p.sendMessage(Main.prefix() + "§cVous n'avez pas la permission pour réalisé cela...");
                    }
                }else{
                    p.sendMessage(Main.prefix() + "§cMauvaise formulation: /setgrade [joueur] [grade]");
                }
            }
        }
        return false;
    }
}
