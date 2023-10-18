package wph.wikuplayerhead;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class PLayerListener implements Listener {
    private WikuPlayerHead mainClass;
    public PLayerListener(WikuPlayerHead mainClass) {
        this.mainClass = mainClass;
    }


    @EventHandler
    public void OnJoin (PlayerJoinEvent event) {
        /*
        Every time a player joins, give to the player a head with the player's skin
        the item cannot be droppeable or moveable in inventory.

         */
        FileConfiguration config = mainClass.getConfig();

        Player player = event.getPlayer();
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(player.getName());
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("headconfig.headname")));

        List<String> lore = config.getStringList("headconfig.headlore");
        //lore.add(ChatColor.translateAlternateColorCodes('&', config.getString("headconfig.headlore")));
        meta.setLore(lore);

        item.setItemMeta(meta);
        player.getInventory().setItem(config.getInt("headconfig.headslot"), item);

    }

    @EventHandler
    public void invInteract (InventoryClickEvent event) {
        /*
        Every time the player interacts with the head, cancell event and put head on config slot
         */
        FileConfiguration config = mainClass.getConfig();
        //Player player = (Player) event.getWhoClicked();
        if(event.getInventory().getType().equals(InventoryType.CRAFTING) && event.getSlotType() != null && event.getCurrentItem() != null && !event.getCurrentItem().getType().equals(Material.AIR)) {
            if(event.getSlot() == config.getInt("headconfig.headslot")) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void dropHead (PlayerDropItemEvent event) {
        /*
        * Cancells the dropHead event
        * */
        ItemStack item = event.getItemDrop().getItemStack();
        if(item.getType() == Material.PLAYER_HEAD) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void headCommand (PlayerInteractEvent event) {
        /*
        Executes the command when the head is clicked
         */
        FileConfiguration config = mainClass.getConfig();
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            ItemStack item = event.getItem();
            if(item != null && item.getType() == Material.PLAYER_HEAD && player.getInventory().getHeldItemSlot() == config.getInt("headconfig.headslot")) {
                event.setCancelled(true);
                player.chat(config.getString("headconfig.headcommand"));
            }
        }
    }


}
