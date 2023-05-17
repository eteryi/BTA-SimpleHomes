package dev.cross.commands;

import com.bta.util.CommandHandler;
import dev.cross.manager.Config;
import dev.cross.manager.Home;
import dev.cross.manager.Location;
import dev.cross.manager.Utils;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.command.Command;
import net.minecraft.src.command.CommandSender;
import org.pf4j.Extension;

@Extension
public class SetHomeCommand implements CommandHandler {
    @Override
    public Command command() {
        return new Command("sethome") {
            @Override
            public boolean execute(net.minecraft.src.command.CommandHandler handler, CommandSender sender, String[] args) {
                if (args.length != 1) {
                    return false;
                } else {
                    String homeName = args[0];
                    if (Utils.get().homeAmount(sender.getPlayer().username) > Config.get().getHomesPerPlayer()) {
                        sender.sendMessage(ChatColor.red + "You have already reached the limit of homes allowed by this server!");
                        return true;
                    }
                    for (Home i : Utils.get().getHomesFromUsername(sender.getPlayer().username)) {
                        if (i.getHomeName().equals(homeName)) {
                            sender.sendMessage(ChatColor.red + "You have already a home with that name!");
                            return true;
                        }
                    }
                    sender.sendMessage(ChatColor.lime + "You have set " + homeName + "!");
                    Home home = new Home(homeName, Location.getLocationFromPlayer(sender.getPlayer()), true, sender.getPlayer().username);

                    Utils.get().addHome(home);
                }
                return true;
            }

            @Override
            public boolean opRequired(String[] strings) {
                return false;
            }

            @Override
            public void sendCommandSyntax(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender) {
                commandSender.sendMessage("/sethome <name>");
            }
        };
    }
}
