package fr.wilizium.pass.commands;

import fr.wilizium.pass.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResetPlayers implements CommandExecutor {

    private final Main main;

    public ResetPlayers(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if(!player.hasPermission("pass.reset")){
            player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
            return true;
        }
        if(args.length != 0) {
            sender.sendMessage("§cUtilisation: /resetplayers");
            return true;
        }


        main.getFileManager().removeFile("userdata");
        main.getFileManager().createFile("userdata");
        for (Player p : main.getServer().getOnlinePlayers()) {
            YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata"));
            List<Integer> list = new ArrayList<>();
            f.set(p.getName() + ".classic", list);
            f.set(p.getName() + ".premium", list);
            try {
                f.save(main.getFileManager().getFile("userdata"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
