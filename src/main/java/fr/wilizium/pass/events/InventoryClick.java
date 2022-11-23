package fr.wilizium.pass.events;

import fr.wilizium.pass.Main;
import fr.wilizium.pass.config.user.UserManager;
import fr.wilizium.pass.ui.PassUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    private final Main main;

    public InventoryClick(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null) return;
        if(e.getCurrentItem() == null) return;

        if(!e.getView().getTitle().startsWith("§5WilliziumPass")){
            return;
        }


        Player player = (Player) e.getWhoClicked();

        PassUI passUI = new PassUI(main, player);

        String pageString = e.getView().getTitle().split("Page ")[1];
        int page = Integer.parseInt(pageString);

        e.setCancelled(true);


        switch (e.getSlot()){
            case 49:
                player.closeInventory();
                break;
            case 47:
                if(page == 1) return;
                passUI.openPassUI(page - 1);
                break;
            case 51:
                if(page == 6) return;
                passUI.openPassUI(page + 1);
                break;
        }

        if(e.getSlot() >= 20 && e.getSlot() <= 24){
            int num = Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName().split("Jour ")[1]);
            givePremiumItem(player, num);
            return;
        }

        if(e.getSlot() >= 29 && e.getSlot() <= 33) {
            int num = Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName().split("Jour ")[1]);
            giveClassicItem(player, num);
            return;
        }

    }

    public boolean hasAvaliableSlot(Player player){
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if(item == null) {
                return true;
            }
        }
        return false;
    }

    public void giveClassicItem(Player player, int i){
        UserManager userManager = new UserManager(main, player);

        if(main.getDay() != i){
            player.sendMessage("§cCe n'est pas le bon jour pour récuperer cet objet !");
            return;
        }

        if(userManager.hasClassicNum(i)){
            player.sendMessage("§cVous avez déjà récupéré ce cadeau !");
            return;
        }

        if(!hasAvaliableSlot(player)){
            player.sendMessage("§cVotre inventaire est plein !");
            return;
        }

        player.sendMessage("§aVous avez récupéré le cadeau !");
        player.getInventory().addItem(main.getConfigManager().getItem(i, false));
        userManager.addClassicNum(i);
    }

    public void givePremiumItem(Player player, int i){
        UserManager userManager = new UserManager(main, player);
        if(!player.hasPermission("pass.premium")){
            player.sendMessage("§cVous n'avez pas de pass premium !");
            return;
        }

        if(main.getDay() != i){
            player.sendMessage("§cCe n'est pas le bon jour pour récuperer cet objet !");
            return;
        }

        if(userManager.hasPremiumNum(i)){
            player.sendMessage("§cVous avez déjà récupéré ce cadeau !");
            return;
        }

        if(!hasAvaliableSlot(player)){
            player.sendMessage("§cVotre inventaire est plein !");
            return;
        }

        player.sendMessage("§aVous avez récupéré le cadeau !");
        player.getInventory().addItem(main.getConfigManager().getItem(i, true));
        userManager.addPremiumNum(i);
    }


}
