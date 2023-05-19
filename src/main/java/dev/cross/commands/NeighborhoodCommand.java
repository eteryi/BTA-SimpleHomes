package dev.cross.commands;

import com.bta.util.CommandHandler;
import dev.cross.manager.Config;
import dev.cross.manager.Home;
import dev.cross.manager.Utils;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.command.Command;
import net.minecraft.src.command.CommandSender;
import net.minecraft.src.command.ServerCommandHandler;
import org.pf4j.Extension;


@Extension
public class NeighborhoodCommand implements CommandHandler {
    @Override
    public Command command() {
        return new Command("neighborhood") {
            @Override
            public boolean execute(net.minecraft.src.command.CommandHandler commandHandler, CommandSender sender, String[] args) {
                if (!Config.get().isGlobalHomes()) {
                    sender.sendMessage(ChatColor.red + "Neighborhood is not allowed on this server.");
                    return true;
                }
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.orange + "Neighbors:");

                    for (Home i : Utils.get().getPublicHomes()) {
                        sender.sendMessage(ChatColor.orange + "     - " + i.getHomeName() + " (" + (int) i.getLocation().x + " " + (int) i.getLocation().y + " " + (int) i.getLocation().z + ")" + " - " + i.getOwnerUsername());
                    }
                    return true;
                }
                if (args.length == 1) {
                    String homeName = args[0];
                    Home tp = null;

                    for (int i = 0, n = Utils.get().getPublicHomes().size(); i < n; i++) {
                        if (Utils.get().getPublicHomes().get(i).getHomeName().equals(homeName)) {
                            tp = Utils.get().getPublicHomes().get(i);
                            break;
                        }
                    }

                    if (tp == null) { sender.sendMessage(ChatColor.red + "Neighbor not found.");return true; }

                    sender.sendMessage(ChatColor.orange + "You have teleported to " + tp.getHomeName() + "!");

                    // Mostly taken from the TeleportCommand.class. Props to them.
                    if (sender.getPlayer() instanceof EntityPlayerMP) {
                        EntityPlayerMP p1MP = (EntityPlayerMP)sender.getPlayer();
                        if (p1MP.dimension != tp.getLocation().dimension && commandHandler instanceof ServerCommandHandler) {
                            ServerCommandHandler serverCommandHandler = (ServerCommandHandler)commandHandler;
                            serverCommandHandler.minecraftServer.configManager.sendPlayerToOtherDimension(p1MP, tp.getLocation().dimension);
                            p1MP.playerNetServerHandler.teleportTo(tp.getLocation().x, tp.getLocation().y, tp.getLocation().z, p1MP.rotationYaw, p1MP.rotationPitch);
                            return true;
                        }
                        p1MP.playerNetServerHandler.teleportTo(tp.getLocation().x, tp.getLocation().y, tp.getLocation().z, p1MP.rotationYaw, p1MP.rotationPitch);
                    } else {
                        sender.getPlayer().setLocationAndAngles(tp.getLocation().x, tp.getLocation().y, tp.getLocation().z, sender.getPlayer().rotationYaw, sender.getPlayer().rotationPitch);
                    }
                    return true;
                }
                return false;
            }

            @Override
            public boolean opRequired(String[] strings) {
                return false;
            }

            @Override
            public void sendCommandSyntax(net.minecraft.src.command.CommandHandler commandHandler, CommandSender commandSender) {
                commandSender.sendMessage("/neighborhood <name>");
            }
        };
    }
}
