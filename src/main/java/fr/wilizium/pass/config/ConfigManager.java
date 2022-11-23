package fr.wilizium.pass.config;

import fr.wilizium.pass.Main;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    private final Main main;

    public ConfigManager(Main main) {
        this.main = main;

        if(!main.getDataFolder().exists()){
            main.getDataFolder().mkdir();
        }
    }

    @SneakyThrows
    public void createConfig() {
        if(!main.getFileManager().getFile("items").exists()){
            main.getFileManager().createFile("items");

            YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("items"));


            List<String> list = new ArrayList<>();
            list.add("DAMAGE_ALL:1");
            for(int i = 1; i < 31; i++) {
                f.set(i + ".classic.item", "DIRT");
                f.set(i + ".classic.value", 2);
                f.set(i + ".classic.enchantments", list);

                f.set(i + ".premium.item", "DIRT");
                f.set(i + ".premium.value", 2);
                f.set(i + ".premium.enchantments", list);
            }

            f.save(main.getFileManager().getFile("items"));
        }
    }

    public ItemStack getItem(int i, boolean premium) {
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("items"));
        if(premium) {
            Material material = Material.getMaterial(f.getString(i + ".premium.item"));
            if(material == null) return null;
            ItemStack premItem = new ItemStack(material);
            premItem.setAmount(f.getInt(i + ".premium.value"));

            HashMap<Enchantment, Integer> enchants = new HashMap<>();
            for(String s : f.getStringList(i + ".premium.enchantments")) {
                String[] split = s.split(":");
                Enchantment enchantment = Enchantment.getByName(split[0]);
                if(enchantment == null) continue;
                enchants.put(enchantment, Integer.parseInt(split[1]));
            }
            premItem.addUnsafeEnchantments(enchants);

            return premItem;

        } else {
            Material material = Material.getMaterial(f.getString(i + ".classic.item"));
            if(material == null) return null;
            ItemStack clasicItem = new ItemStack(material);
            clasicItem.setAmount(f.getInt(i + ".classic.value"));

            HashMap<Enchantment, Integer> enchants = new HashMap<>();
            for(String s : f.getStringList(i + ".classic.enchantments")) {
                String[] split = s.split(":");
                Enchantment enchantment = Enchantment.getByName(split[0]);
                if(enchantment == null) continue;
                enchants.put(enchantment, Integer.parseInt(split[1]));
            }
            clasicItem.addUnsafeEnchantments(enchants);

            return clasicItem;
        }
    }
}
