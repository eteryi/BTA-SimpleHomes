package dev.cross.commands;

import com.bta.util.CommandHandler;
import dev.cross.manager.Config;
import dev.cross.manager.Home;
import dev.cross.manager.Utils;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.command.Command;
import net.minecraft.src.command.CommandSender;
import org.pf4j.Extension;


@Extension
public class PublicCommand implements CommandHandler {
    @Override
    public Command command() {
        return new Command("publish") {
            @Override
            public boolean execute(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender, String[] args) {
                if (!Config.get().isGlobalHomes()) {
                    commandSender.sendMessage(ChatColor.red + "Neighbourhood is not allowed on this server.");
                    return true;
                }
                if (args.length != 1) return false;

                String homeName = args[0];
                Home i = Utils.get().getHomeFromName(commandSender.getPlayer().username, homeName);
                if (i == null) { commandSender.sendMessage(ChatColor.red + "You don't have any home called " + homeName + "."); return true; }
                if (Utils.get().globalContains(homeName)) { commandSender.sendMessage(ChatColor.red + "Oops! Someone already is using that public home name."); return true; }

                Utils.get().publishHome(i);
                commandSender.sendMessage(ChatColor.lime + "Your home has now been published.");
                return true;
            }

            @Override
            public boolean opRequired(String[] strings) {
                return false;
            }

            @Override
            public void sendCommandSyntax(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender) {
                commandSender.sendMessage("/publish <name>");
            }
        };
    }
}
