package fr.wilizium.pass;

import fr.wilizium.pass.commands.PassCommand;
import fr.wilizium.pass.commands.ResetPlayers;
import fr.wilizium.pass.config.ConfigManager;
import fr.wilizium.pass.config.FileManager;
import fr.wilizium.pass.events.InventoryClick;
import fr.wilizium.pass.events.PlayerJoin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private FileManager fileManager;

    @Getter
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        fileManager = new FileManager(this);
        (configManager = new ConfigManager(this)).createConfig();
        Bukkit.getPluginManager().registerEvents(new InventoryClick(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
        getCommand("pass").setExecutor(new PassCommand(this));
        getCommand("resetplayers").setExecutor(new ResetPlayers(this));
    }

    public int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
}
