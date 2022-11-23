package fr.wilizium.pass.ui;

import fr.wilizium.pass.Main;
import fr.wilizium.pass.config.user.UserManager;
import fr.wilizium.pass.tools.CustomHead;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PassUI {

    private final Main main;
    private Player player;

    public PassUI(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    private Integer initialiseNum(int i){
        if(i == 1) return 1;
        if(i == 2) return 6;
        if(i == 3) return 11;
        if(i == 4) return 16;
        if(i == 5) return 21;
        if(i == 6) return 26;
        return null;
    }

    public String isItemDisponible(int i){
        int day = main.getDay();
        if (day == i) return "§aDisponible";
        return "§cIndisponible";
    }

    public String isClassicItemCollected(int i){
        UserManager userManager = new UserManager(main, player);
        if(userManager.hasClassicNum(i)) return "§aOui";
        return "§cNon";
    }

    public String isPremiumItemCollected(int i){
        UserManager userManager = new UserManager(main, player);
        if(!player.hasPermission("pass.premium")) return "§cPas de pass";
        if(userManager.hasPremiumNum(i)) return "§aOui";
        return "§cNon";
    }

    public void openPassUI(int page) {
        Inventory inv = Bukkit.createInventory(null, 54, "§5WilliziumPass §7- §fPage " + page);

        ItemStack vitre = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        ItemMeta vitreMeta = vitre.getItemMeta();
        vitreMeta.setDisplayName(" ");
        vitre.setItemMeta(vitreMeta);

        inv.setItem(0, vitre);
        inv.setItem(1, vitre);
        inv.setItem(2, vitre);
        inv.setItem(6, vitre);
        inv.setItem(7, vitre);
        inv.setItem(8, vitre);
        inv.setItem(9, vitre);
        inv.setItem(17, vitre);
        inv.setItem(36, vitre);
        inv.setItem(44, vitre);
        inv.setItem(45, vitre);
        inv.setItem(46, vitre);
        inv.setItem(52, vitre);
        inv.setItem(53, vitre);

        int i2 = initialiseNum(page);
        for (int i = 29; i < 34; i++) {
            ItemStack item = new ItemStack(main.getConfigManager().getItem(i2, false));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§7Classique - Jour " + i2);
            itemMeta.setLore(Arrays.asList(
                    "",
                    "§8▪ §7Disponibilité: " + isItemDisponible(i2),
                    "§8▪ §7Récupéré: " + isClassicItemCollected(i2),
                    " ",
                    "§eCliquez pour obtenir !"
            ));
            item.setItemMeta(itemMeta);

            inv.setItem(i, item);

            i2++;
        }

        i2 = initialiseNum(page);
        for (int i = 20; i < 25; i++) {
            ItemStack item = new ItemStack(main.getConfigManager().getItem(i2, true));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§6Premium - Jour " + i2);
            itemMeta.setLore(Arrays.asList(
                    "",
                    "§8▪ §7Disponible: " + isItemDisponible(i2),
                    "§8▪ §7Récupéré: " + isPremiumItemCollected(i2),
                    " ",
                    "§eCliquez pour obtenir !"
            ));
            item.setItemMeta(itemMeta);

            inv.setItem(i, item);

            i2++;
        }

        inv.setItem(49, new CustomHead().create("§cRetour", null, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJkN2E3NTFlYjA3MWUwOGRiYmM5NWJjNWQ5ZDY2ZTVmNTFkYzY3MTI2NDBhZDJkZmEwM2RlZmJiNjhhN2YzYSJ9fX0="));

        if(page != 1) {
            inv.setItem(47, new CustomHead().create("§aPage précédente", null, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjZkYWI3MjcxZjRmZjA0ZDU0NDAyMTkwNjdhMTA5YjVjMGMxZDFlMDFlYzYwMmMwMDIwNDc2ZjdlYjYxMjE4MCJ9fX0="));
        }

        if(page != 6) {
            inv.setItem(51, new CustomHead().create("§aPage suivante", null, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGFhMTg3ZmVkZTg4ZGUwMDJjYmQ5MzA1NzVlYjdiYTQ4ZDNiMWEwNmQ5NjFiZGM1MzU4MDA3NTBhZjc2NDkyNiJ9fX0="));
        }

        player.openInventory(inv);
    }

}