package fr.wilizium.pass.config.user;

import fr.wilizium.pass.Main;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private final Main main;

    private Player player;

    public UserManager(Main main, Player player) {
        this.main = main;
        this.player = player;

        if(!main.getFileManager().fileExists("userdata")){
            main.getFileManager().createFile("userdata");
        }
    }

    public boolean isPlayerCreated(){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata"));
        return f.contains(player.getName());
    }

    @SneakyThrows
    public void createPlayer(){
        if(!isPlayerCreated()){
            YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata"));
            List<Integer> list = new ArrayList<>();
            f.set(player.getName() + ".classic", list);
            f.set(player.getName() + ".premium", list);
            f.save(main.getFileManager().getFile("userdata"));
        }
    }

    @SneakyThrows
    public void addClassicNum(int num){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata"));
        List<Integer> list = f.getIntegerList(player.getName() + ".classic");
        list.add(num);
        f.set(player.getName() + ".classic", list);
        f.save(main.getFileManager().getFile("userdata"));
    }

    public boolean hasClassicNum(int num){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata"));
        List<Integer> list = f.getIntegerList(player.getName() + ".classic");
        return list.contains(num);
    }

    @SneakyThrows
    public void addPremiumNum(int num){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata"));
        List<Integer> list = f.getIntegerList(player.getName() + ".premium");
        list.add(num);
        f.set(player.getName() + ".premium", list);
        f.save(main.getFileManager().getFile("userdata"));
    }

    public boolean hasPremiumNum(int num){
        YamlConfiguration f = YamlConfiguration.loadConfiguration(main.getFileManager().getFile("userdata"));
        List<Integer> list = f.getIntegerList(player.getName() + ".premium");
        return list.contains(num);
    }
}
