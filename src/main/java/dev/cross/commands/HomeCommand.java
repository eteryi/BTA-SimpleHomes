package dev.cross.commands;

import com.bta.util.CommandHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.command.Command;
import net.minecraft.src.command.CommandSender;
import net.minecraft.src.command.ServerCommandHandler;
import dev.cross.manager.Config;
import dev.cross.manager.Home;
import dev.cross.manager.Utils;
import org.pf4j.Extension;

@Extension
public class HomeCommand implements CommandHandler {
    @Override
    public Command command() {
        return new Command("home") {
            @Override
            public boolean execute(net.minecraft.src.command.CommandHandler handler, CommandSender sender, String[] args) {
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.orange + "Your Homes " + "(" + (Config.get().getHomesPerPlayer() - Utils.get().homeAmount(sender.getPlayer().username)) + "/" + Config.get().getHomesPerPlayer() + ") " + ":");

                    for (Home i : Utils.get().getHomesFromUsername(sender.getPlayer().username)) {
                        sender.sendMessage(ChatColor.orange + "     - " + i.getHomeName() + " (" + (int) i.getLocation().x + " " + (int) i.getLocation().y + " " + (int) i.getLocation().z + ")" );
                    }
                } else {
                    String homeName = args[0];
                    Home home = null;

                    for (Home i : Utils.get().getHomesFromUsername(sender.getPlayer().username)) {
                        if (i.getHomeName().equalsIgnoreCase(homeName)) {
                            home = i;
                            break;
                        }
                    }
                    if (home == null) {
                        sender.sendMessage(ChatColor.red + "Invalid Home!");
                        return true;
                    }
                    sender.sendMessage(ChatColor.orange + "You have teleported to " + home.getHomeName() + "!");

                    // Mostly taken from the TeleportCommand.class. Props to them.
                    if (sender.getPlayer() instanceof EntityPlayerMP) {
                        EntityPlayerMP p1MP = (EntityPlayerMP)sender.getPlayer();
                        if (p1MP.dimension != home.getLocation().dimension && handler instanceof ServerCommandHandler) {
                            ServerCommandHandler serverCommandHandler = (ServerCommandHandler)handler;
                            serverCommandHandler.minecraftServer.configManager.sendPlayerToOtherDimension(p1MP, home.getLocation().dimension);
                            p1MP.playerNetServerHandler.teleportTo(home.getLocation().x, home.getLocation().y, home.getLocation().z, p1MP.rotationYaw, p1MP.rotationPitch);
                            return true;
                        }
                        p1MP.playerNetServerHandler.teleportTo(home.getLocation().x, home.getLocation().y, home.getLocation().z, p1MP.rotationYaw, p1MP.rotationPitch);
                    } else {
                        sender.getPlayer().setLocationAndAngles(home.getLocation().x, home.getLocation().y, home.getLocation().z, sender.getPlayer().rotationYaw, sender.getPlayer().rotationPitch);
                    }
                }
                return true;
            }

            @Override
            public boolean opRequired(String[] strings) {
                return false;
            }

            @Override
            public void sendCommandSyntax(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender) {
                commandSender.sendMessage("/home <name>");
            }
        };
    }
}