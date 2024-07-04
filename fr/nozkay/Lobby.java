package fr.nozkay;

import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Lobby implements Listener {

    private FileManager fileManager= new FileManager("grade.txt");
    public Lobby() {
        ;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        e.setJoinMessage("");
        String grade = fileManager.getGrade(p.getUniqueId().toString());
        p.getWorld().setDifficulty(Difficulty.PEACEFUL);
        p.getInventory().setItem(4,Utils.getItem(Material.COMPASS,"§cMenu Principal",null));
        p.getInventory().setItem(8,Utils.getItem(Material.DIAMOND,"§eJump",null));
        if(grade == null){
            fileManager.writeToFile(p.getUniqueId().toString(),"JOUEUR");
            grade = "JOUEUR";
        }
        setGrade(p,grade);
        scoreboard(p);
        new BukkitRunnable(){
            @Override
            public void run(){
                p.teleport(new Location(p.getWorld(),0 ,73 ,0));
            }
        }.runTaskLater(Main.instance,2);
        for(Player pl: Bukkit.getOnlinePlayers()){
            PacketPlayOutPlayerInfo playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ((CraftPlayer) pl).getHandle());
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(playerInfo);
        }
        Bukkit.broadcastMessage("§a[+] §f" + p.getDisplayName() + " §aà rejoint le serveur.");
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        e.setQuitMessage("");
        Bukkit.broadcastMessage("§c[-] §f" + p.getName() + " §cà quitter le serveur.");
    }

    @EventHandler
    public void OnBreak(BlockBreakEvent e){
        e.setCancelled(true);
        e.getPlayer().sendMessage(Main.prefix() + "§cVous n'avez pas l'autorisation de cassez des blocs.");
    }

    @EventHandler
    public void OnDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    public HashMap<Player,Inventory> inventory = new HashMap<>();
    public static int uhcp = 0;
    public static int arcadep = 0;

    public static String status1 = "OFFLINE";
    public static String status2 = "OFFLINE";
    public static String status3 = "OFFLINE";
    public static Inventory inven;

    public void handlePlayerCount(String server, int playerCount) {
        if (server.equalsIgnoreCase("UHC")) {
            uhcp = playerCount;
        } else if (server.equalsIgnoreCase("Arcade")) {
            arcadep = playerCount;
        }
    }
    public void handleStatus(String server, String status) {
        if (server.equalsIgnoreCase("UHC")) {
            status1 = status;
            String r = "§9Status: §f" + status1;
            if(status1.equalsIgnoreCase("Ouvert")){
                inven.setItem(11,Utils.createTintedGlass(DyeColor.GREEN,"§eUHC-1",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            } else if (status1.equalsIgnoreCase("En Attente") || status1.equalsIgnoreCase("En Démarrage")) {
                inven.setItem(11,Utils.createTintedGlass(DyeColor.ORANGE,"§eUHC-1",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if (status1.equalsIgnoreCase("Fermee")) {
                inven.setItem(11,Utils.createTintedGlass(DyeColor.RED,"§eUHC-1",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if(status1.equalsIgnoreCase("En Jeu")){
                inven.setItem(11,Utils.createTintedGlass(DyeColor.BLUE,"§eUHC-1",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            }
        } else if (server.equalsIgnoreCase("UHC-2")) {
            status2 = status;
            String a = "§9Status: §f" + status2;
            if(status2.equalsIgnoreCase("Ouvert")){
                inven.setItem(13,Utils.createTintedGlass(DyeColor.GREEN,"§eUHC-2",Arrays.asList(" ", a, "§9Clic Gauche: §fRejoindre le serveur")));
            } else if (status2.equalsIgnoreCase("En Attente") || status2.equalsIgnoreCase("En Démarrage")) {
                inven.setItem(13,Utils.createTintedGlass(DyeColor.ORANGE,"§eUHC-2",Arrays.asList(" ", a, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if (status2.equalsIgnoreCase("Fermee")) {
                inven.setItem(13,Utils.createTintedGlass(DyeColor.RED,"§eUHC-2",Arrays.asList(" ", a, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if(status2.equalsIgnoreCase("En Jeu")){
                inven.setItem(13,Utils.createTintedGlass(DyeColor.BLUE,"§eUHC-2",Arrays.asList(" ", a, "§9Clic Gauche: §fRejoindre le serveur")));
            }
        }else if (server.equalsIgnoreCase("UHC-3")) {
            status3 = status;
            String b = "§9Status: §f" + status3;
            if(status3.equalsIgnoreCase("Ouvert")){
                inven.setItem(15,Utils.createTintedGlass(DyeColor.GREEN,"§eUHC-3",Arrays.asList(" ", b, "§9Clic Gauche: §fRejoindre le serveur")));
            } else if (status3.equalsIgnoreCase("En Attente") || status3.equalsIgnoreCase("En Démarrage")) {
                inven.setItem(15,Utils.createTintedGlass(DyeColor.ORANGE,"§eUHC-3",Arrays.asList(" ", b, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if (status3.equalsIgnoreCase("Fermee")) {
                inven.setItem(15,Utils.createTintedGlass(DyeColor.RED,"§eUHC-3",Arrays.asList(" ", b, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if(status3.equalsIgnoreCase("En Jeu")){
                inven.setItem(15,Utils.createTintedGlass(DyeColor.BLUE,"§eUHC-3",Arrays.asList(" ", b, "§9Clic Gauche: §fRejoindre le serveur")));
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cMenu Principal")){
            Inventory inv = inventory.getOrDefault(p,Bukkit.createInventory(null,27,"§cMenu Principal"));
            inv = Bukkit.createInventory(null,27,"§cMenu Principal");
            BungeeUtil.requestPlayerCount(p,"Arcade");
            String r = "§9Joueurs: §f" + arcadep;
            BungeeUtil.requestPlayerCount(p,"UHC");
            String c = "§9Joueurs: §f" + uhcp;
            inv.setItem(11,Utils.getItem(Material.DIAMOND_SWORD,"§eGame UHC",Arrays.asList(" ", "§9Développeur: §6rolan10001"
                    ,"§9Concept: §fVenez vous affrontez dans un battle royale\n avec plusieurs particularités choisi par l'host !",
                    c)));
            inv.setItem(15,Utils.getItem(Material.GOLD_BOOTS,"§9§kArcade Games",Arrays.asList(" ", "§9Développeur: §6§kNozkay",
                    "§9Concept: §f§kVenez vous amusez dans plusieurs mini-jeux à la suite !",
                    r)));
            p.openInventory(inv);
            inventory.put(p,inv);
        }if(e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eJump")){

        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Material itemType = event.getItemDrop().getItemStack().getType();

        if (itemType == Material.COMPASS && event.getItemDrop().getItemStack().hasItemMeta() && event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("§cMenu Principal") ) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(!e.getInventory().getTitle().equalsIgnoreCase("§eMenu UHC")) return;
        e.setCancelled(true);
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().equals(Material.AIR)) return;
        Player p = (Player) e.getWhoClicked();
        if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§eUHC-1")){
            if(status1.equalsIgnoreCase("Ouvert")){
                BungeeUtil.sendPlayerToServer(p,"UHC");
            }else{
                p.sendMessage(Main.prefix() + "§cVous ne pouvez pas vous connecter à ce serveur.");
            }
            p.openInventory(inven);
        }
        if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§eUHC-2") && e.getClick().isLeftClick()){
            if(status2.equalsIgnoreCase("Ouvert")){
                BungeeUtil.sendPlayerToServer(p,"UHC-2");
            }else{
                p.sendMessage(Main.prefix() + "§cVous ne pouvez pas vous connecter à ce serveur.");
            }
            p.openInventory(inven);
        }if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§eUHC-3") && e.getClick().isLeftClick()){
            if(status3.equalsIgnoreCase("Ouvert")){
                BungeeUtil.sendPlayerToServer(p,"UHC-3");
            }else{
                p.sendMessage(Main.prefix() + "§cVous ne pouvez pas vous connecter à ce serveur.");
            }
            p.openInventory(inven);
        }
    }

    @EventHandler
    public void OnInvClic(InventoryClickEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onInterac(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock().getType() == Material.TRAP_DOOR) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = inventory.get(e.getWhoClicked());
        if (!e.getInventory().equals(inv)) return;


        e.setCancelled(true);
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType().equals(Material.AIR)) return;
        Player p = (Player) e.getWhoClicked();
        e.setCancelled(true);
        if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§eGame UHC")){
            inven = Bukkit.createInventory(null,27,"§eMenu UHC");
            BungeeUtil.requestServerStatus("UHC");
            BungeeUtil.requestServerStatus("UHC-2");
            BungeeUtil.requestServerStatus("UHC-3");
            String r = "§9Status: §f" + status1;
            String a = "§9Status: §f" + status2;
            String b = "§9Status: §f" + status3;
            if(status1.equalsIgnoreCase("Ouvert")){
                inven.setItem(11,Utils.createTintedGlass(DyeColor.GREEN,"§eUHC-1",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            } else if (status1.equalsIgnoreCase("En Attente") || status1.equalsIgnoreCase("En Démarrage")) {
                inven.setItem(11,Utils.createTintedGlass(DyeColor.ORANGE,"§eUHC-1",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if (status1.equalsIgnoreCase("Fermee")) {
                inven.setItem(11,Utils.createTintedGlass(DyeColor.RED,"§eUHC-1",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if(status1.equalsIgnoreCase("En Jeu")){
                inven.setItem(11,Utils.createTintedGlass(DyeColor.BLUE,"§eUHC-1",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            }
            if(status2.equalsIgnoreCase("Ouvert")){
                inven.setItem(13,Utils.createTintedGlass(DyeColor.GREEN,"§eUHC-2",Arrays.asList(" ", a, "§9Clic Gauche: §fRejoindre le serveur")));
            } else if (status2.equalsIgnoreCase("En Attente") || status2.equalsIgnoreCase("En Démarrage")) {
                inven.setItem(13,Utils.createTintedGlass(DyeColor.ORANGE,"§eUHC-2",Arrays.asList(" ", a, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if (status2.equalsIgnoreCase("Fermee")) {
                inven.setItem(13,Utils.createTintedGlass(DyeColor.RED,"§eUHC-2",Arrays.asList(" ", a, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if(status2.equalsIgnoreCase("En Jeu")){
                inven.setItem(13,Utils.createTintedGlass(DyeColor.BLUE,"§eUHC-2",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            }
            if(status3.equalsIgnoreCase("Ouvert")){
                inven.setItem(15,Utils.createTintedGlass(DyeColor.GREEN,"§eUHC-3",Arrays.asList(" ", b, "§9Clic Gauche: §fRejoindre le serveur")));
            } else if (status3.equalsIgnoreCase("En Attente") || status2.equalsIgnoreCase("En Démarrage")) {
                inven.setItem(15,Utils.createTintedGlass(DyeColor.ORANGE,"§eUHC-3",Arrays.asList(" ", b, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if (status3.equalsIgnoreCase("Fermee")) {
                inven.setItem(15,Utils.createTintedGlass(DyeColor.RED,"§eUHC-3",Arrays.asList(" ", b, "§9Clic Gauche: §fRejoindre le serveur")));
            }else if(status3.equalsIgnoreCase("En Jeu")){
                inven.setItem(15,Utils.createTintedGlass(DyeColor.BLUE,"§eUHC-3",Arrays.asList(" ", r, "§9Clic Gauche: §fRejoindre le serveur")));
            }
            p.openInventory(inven);
        }

        if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§9Arcade Games")){
            BungeeUtil.sendPlayerToServer(p,"Arcade");
        }
    }

    public void setGrade(Player p, String r) {
        String grade = getGrade(r);
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Team team = scoreboard.getTeam(grade);
        if (team == null) {
            team = scoreboard.registerNewTeam(grade);
        }

        team.setPrefix(grade);
        team.addPlayer(p);
        team.addEntry(p.getName());
        p.setDisplayName(team.getPrefix() + p.getName());
        p.setPlayerListName(team.getPrefix() + p.getName());
        p.setCustomNameVisible(true);
        p.setCustomName(team.getPrefix() + p.getName());


        PacketPlayOutPlayerInfo playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ((CraftPlayer) p).getHandle());
        for (Player players : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) players).getHandle().playerConnection.sendPacket(playerInfo);
        }
    }

    public String getGrade(String r){
        String grade = "";
        if(r.equalsIgnoreCase("ADMIN")){
            grade = "§c§oAdmin §r§c";
        }if(r.equalsIgnoreCase("MODO")){
            grade = "§1§oModo §r§1";
        }if(r.equalsIgnoreCase("RESP")){
            grade = "§e§oResp. §r§e";
        }if(r.equalsIgnoreCase("DEV")){
            grade = "§b§oDev §r§b";
        }if(r.equalsIgnoreCase("STAFF")){
            grade = "§9§oStaff §r§9";
        }if(r.equalsIgnoreCase("HOST")){
            grade = "§a§oHost §r§9";
        }if(r.equalsIgnoreCase("JOUEUR")){
            grade = "§7§oJoueur §r§7";
        }
        return grade;
    }

    @EventHandler
    public void onChat(PlayerChatEvent e){
        Player p = e.getPlayer();
        String grade = getGrade(fileManager.getGrade(p.getUniqueId().toString()));
        e.setCancelled(true);
        String mess = e.getMessage();
        Bukkit.broadcastMessage(grade + p.getName() + " §7» §f" + mess);
    }

    public void scoreboard(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = manager.getNewScoreboard();

                Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName("§9§lLa Garderie");

                Score score11 = objective.getScore("§f§m------------------ ");
                score11.setScore(6);

                Score score3 = objective.getScore("§7» §9Joueurs: §f" + Bukkit.getOnlinePlayers().size());
                score3.setScore(5);

                Score score4 = objective.getScore("§7» §9Grade: §f" + getGrade(fileManager.getGrade(player.getUniqueId().toString())));
                score4.setScore(4);

                Score score5 = objective.getScore("§7» §9Status: §aOuvert");
                score5.setScore(3);

                Score score7 = objective.getScore("§f§m------------------  ");
                score7.setScore(1);

                Score score8 = objective.getScore("§6lagarderiemc.fr");
                score8.setScore(0);

                player.setScoreboard(scoreboard);
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
    }
}
