package dev.cross.events;


import dev.cross.manager.Config;
import dev.cross.manager.HomeList;
import dev.cross.manager.Utils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.command.ChatColor;

import org.pf4j.Extension;

@Extension
public class TickEvent implements com.bta.events.TickEvent {
    @Override
    public void onEvent() {
        if (System.currentTimeMillis() >= Utils.get().getBackup()) {
            System.out.println(ChatColor.gray + "[" + ChatColor.orange + "Homes" + ChatColor.gray + "] " + ChatColor.orange + "Realized backup for all the homes.");
            Utils.get().setBackup(System.currentTimeMillis() + Config.get().getBackupTimer());
            HomeList.get().save();
            MinecraftServer.getInstance().serverCommandHandler.sendMessageToAllPlayers(ChatColor.gray + "[" + ChatColor.orange + "Homes" + ChatColor.gray + "] " + ChatColor.orange + "Realized backup for all the homes.");
        }

    }
}
