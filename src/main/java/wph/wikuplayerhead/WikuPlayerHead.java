package wph.wikuplayerhead;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class WikuPlayerHead extends JavaPlugin {
    public String version = getDescription().getVersion();
    public String rutaConfig;
    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();
        registerConfig();
        Bukkit.getConsoleSender().sendMessage("§a[WPH]: WikuPlayerHead enabled!");
        Bukkit.getConsoleSender().sendMessage("§a[WPH]: Plugin version "+version);
        Bukkit.getConsoleSender().sendMessage("§a[WPH]: Running on "+Bukkit.getVersion()+" (Suported!)");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§cWikuPlayerHead disabled!");
    }

    public void registerEvents() {
        /* Event registering */
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PLayerListener(this), this);

    }
    public void registerConfig(){
        /* Config registering */
        File config = new File(this.getDataFolder(),"config.yml");
        rutaConfig = config.getPath();
        if(!config.exists()){
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public void registerCommands(){
        /* Registering reload command */
        this.getCommand("wphreloadconfig").setExecutor(new reloadconfig(this));
    }

}
