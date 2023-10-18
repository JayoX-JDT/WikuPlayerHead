package wph.wikuplayerhead;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class reloadconfig implements CommandExecutor {

    private WikuPlayerHead mainClass;
    public reloadconfig(WikuPlayerHead mainClass) {
        this.mainClass = mainClass;
    }


    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lConfig reloaded!"));
            mainClass.reloadConfig();
        }else {
            Player player = (Player) commandSender;
            if (player.hasPermission("wph.reloadconfig")){
                mainClass.reloadConfig();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Config reloaded!"));
            } else {
                FileConfiguration config = mainClass.getConfig();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("plugin.nopermission")));

            }

        }

        return false;
    }
}
