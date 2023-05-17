package dev.cross.commands;

import com.bta.util.CommandHandler;
import dev.cross.manager.Utils;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.command.Command;
import net.minecraft.src.command.CommandSender;
import org.pf4j.Extension;

@Extension
public class DeleteHome implements CommandHandler {
    @Override
    public Command command() {
        return new Command("delhome") {
            @Override
            public boolean execute(net.minecraft.src.command.CommandHandler commandHandler, CommandSender sender, String[] args) {
                if (args.length != 1) {
                    return false;
                } else {
                    String homeName = args[0];
                    if (Utils.get().deleteHome(sender.getPlayer().username, homeName)) {
                        sender.sendMessage(ChatColor.lime + "You have deleted " + homeName + "!");
                        return true;
                    }
                    sender.sendMessage(ChatColor.red + "Home not found.");
                }
                return true;
            }

            @Override
            public boolean opRequired(String[] strings) {
                return false;
            }

            @Override
            public void sendCommandSyntax(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender) {
                commandSender.sendMessage("/delhome <name>");
            }
        };
    }
}
