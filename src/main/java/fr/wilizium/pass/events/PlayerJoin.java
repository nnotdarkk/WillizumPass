package fr.wilizium.pass.events;

import fr.wilizium.pass.Main;
import fr.wilizium.pass.config.user.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final Main main;

    public PlayerJoin(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        new UserManager(main, e.getPlayer()).createPlayer();
    }
}
