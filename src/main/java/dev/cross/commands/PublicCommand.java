package dev.cross.commands;

import com.bta.util.CommandHandler;
import net.minecraft.src.command.Command;
import net.minecraft.src.command.CommandSender;
import org.pf4j.Extension;


@Extension
public class PublicCommand implements CommandHandler {
    @Override
    public Command command() {
        return new Command("publish") {
            @Override
            public boolean execute(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
                return true;
            }

            @Override
            public boolean opRequired(String[] strings) {
                return false;
            }

            @Override
            public void sendCommandSyntax(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender) {

            }
        };
    }
}
