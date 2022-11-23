package fr.wilizium.pass.commands;

import fr.wilizium.pass.Main;
import fr.wilizium.pass.ui.PassUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PassCommand implements CommandExecutor {

    private final Main main;

    public PassCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 0) {
            sender.sendMessage("§cUtilisation: /pass");
            return true;
        }

        if(sender instanceof Player) {
            Player player = (Player) sender;
            PassUI passUI = new PassUI(main, player);
            passUI.openPassUI(1);
        }
        return false;
    }
}
