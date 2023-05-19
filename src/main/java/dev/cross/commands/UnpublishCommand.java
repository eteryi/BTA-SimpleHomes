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
public class UnpublishCommand implements CommandHandler {
    @Override
    public Command command() {
        return new Command("unpublish") {
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
                if (i.isPrivate()) { commandSender.sendMessage(ChatColor.red + "That home is already private."); }

                Utils.get().unpublishHome(i);
                commandSender.sendMessage(ChatColor.lime + "Your home has now been" + ChatColor.red + " un-" + ChatColor.lime + "published.");
                return true;
            }

            @Override
            public boolean opRequired(String[] strings) {
                return false;
            }

            @Override
            public void sendCommandSyntax(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender) {
                commandSender.sendMessage("/unpublish <name>");
            }
        };
    }
}
