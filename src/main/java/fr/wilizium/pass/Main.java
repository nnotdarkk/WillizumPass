import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;

public class Main extends JavaPlugin {

    @Getter
    private Main instance;

    @Override
    public void onEnable() {
        instance = this;
        System.out.println(getDay());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
