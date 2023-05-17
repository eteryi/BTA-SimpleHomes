package dev.cross.commands;

import com.bta.util.CommandHandler;
import dev.cross.manager.Home;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.command.Command;
import net.minecraft.src.command.CommandSender;
import dev.cross.manager.Config;
import dev.cross.manager.Utils;
import org.pf4j.Extension;

@Extension
public class ListHomesCommand implements CommandHandler {
    @Override
    public Command command() {
        return new Command("homes") {
            @Override
            public boolean execute(net.minecraft.src.command.CommandHandler commandHandler, CommandSender sender, String[] args) {
                sender.sendMessage(ChatColor.orange + "Your Homes " + "(" + (Config.get().getHomesPerPlayer() - Utils.get().homeAmount(sender.getPlayer().username)) + "/" + Config.get().getHomesPerPlayer() + ") " + ":");

                for (Home i : Utils.get().getHomesFromUsername(sender.getPlayer().username)) {
                    sender.sendMessage(ChatColor.orange + "     - " + i.getHomeName() + " (" + (int) i.getLocation().x + " " + (int) i.getLocation().y + " " + (int) i.getLocation().z + ")" );
                }
                return true;
            }

            @Override
            public boolean opRequired(String[] strings) {
                return false;
            }

            @Override
            public void sendCommandSyntax(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender) {
                commandSender.sendMessage("/homes");
            }
        };
    }
}
