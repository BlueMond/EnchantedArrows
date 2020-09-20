package me.bluemond.enchantedarrows;

import me.bluemond.enchantedarrows.arrows.LightningArrow;
import me.bluemond.enchantedarrows.arrows.RidableArrow;
import me.bluemond.enchantedarrows.utils.DebugPrinter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;


public class CommandManager implements CommandExecutor {

    private final EnchantedArrows plugin;

    public CommandManager(EnchantedArrows plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandLabel, String[] args) {

        if(!commandLabel.trim().equalsIgnoreCase("enchantedarrows")
                && !commandLabel.trim().equalsIgnoreCase("ea")) return true;
        if(args.length < 2) return true;
        if(!(commandSender instanceof Player)) return true;

        DebugPrinter.print(args[0].trim());
        DebugPrinter.print(args[1].trim());

        if(args[0].trim().equalsIgnoreCase("spawn")){
            if(args[1].trim().equalsIgnoreCase("lightning")){
                DebugPrinter.print("Spawning lightning arrows");

                Player player = (Player) commandSender;

                ItemStack arrowStack =
                        plugin.getArrowManager().createAbstractArrowStack(new LightningArrow(UUID.randomUUID()), 64);

                player.getInventory().addItem(arrowStack);
            }else if(args[1].trim().equalsIgnoreCase("ridable")){
                DebugPrinter.print("Spawning ridable arrows");

                Player player = (Player) commandSender;

                ItemStack arrowStack =
                        plugin.getArrowManager().createAbstractArrowStack(new RidableArrow(UUID.randomUUID()), 64);

                player.getInventory().addItem(arrowStack);
            }
        }

        return true;
    }

}
