package me.bluemond.enchantedarrows;

import me.bluemond.enchantedarrows.arrows.ArrowManager;
import me.bluemond.enchantedarrows.arrows.LightningArrow;
import org.bukkit.plugin.java.JavaPlugin;

// add custom arrow function 

public class EnchantedArrows extends JavaPlugin {

    private static EnchantedArrows enchantedArrows;
    ArrowManager arrowManager;

    @Override
    public void onEnable() {
        // on server enabling the plugin
        enchantedArrows = this;

        arrowManager = new ArrowManager(this);
        arrowManager.registerArrowClass(LightningArrow.class);
        getServer().getPluginManager().registerEvents(new ArrowListener(this), this);
        getCommand("ea").setExecutor(new CommandManager(this));

        getLogger().info("EnchantedArrows v" + getDescription().getVersion() + " has been enabled!");
    }

    @Override
    public void onDisable() {
        // on server disabling the plugin

        getLogger().info("EnchantedArrows v" + getDescription().getVersion() + " has been disabled!");
    }

    public ArrowManager getArrowManager() {
        return arrowManager;
    }

    public static EnchantedArrows getInstance(){
        return enchantedArrows;
    }
}
