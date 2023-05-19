package dev.cross.manager;

import net.minecraft.server.MinecraftServer;
import org.simple.JSONObject;
import org.simple.parser.JSONParser;
import org.simple.parser.ParseException;


import java.io.*;

public class Config {

    private static Config INSTANCE;

    private long backupTimer = (long) (10 * 60000);
    private int maxHomesPerPlayer = 20;
    private boolean globalHomes = true;

    private static JSONObject configObject;

    private static boolean load() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("world/homes/config.json")) {
            Object object = parser.parse(reader);
            JSONObject config = (JSONObject) object;

            configObject = config;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
    private Config() {
        if (exists()) {
            load();
            if (configObject != null) {
                this.globalHomes = (boolean) configObject.get("global_homes");
                this.maxHomesPerPlayer = Math.toIntExact((Long)configObject.get("homes_per_player"));
                this.backupTimer = ((Long) configObject.get("backup_time") * (long) 60000);
            }
        } else {
            create();
        }
    }

    private static void create() {
        JSONObject config = new JSONObject();
        config.put("homes_per_player", 20);
        config.put("global_homes", true);
        config.put("backup_time", 10);

        File f = new File("world/homes");
        if (!f.exists()) {
            if (!f.mkdir()) {
                MinecraftServer.logger.warning("Couldn't create config directory!");
            }
        }
        try (FileWriter file = new FileWriter("world/homes/config.json")) {
            file.write(config.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config get() {
        if (INSTANCE == null) { INSTANCE = new Config(); }
        return INSTANCE;
    }

    private static boolean exists() {
        File f = new File("world/homes/config.json");

        return f.exists();
    }

    public int getHomesPerPlayer() {
        return maxHomesPerPlayer;
    }

    public boolean isGlobalHomes() {
        return globalHomes;
    }

    public long getBackupTimer() {
        return backupTimer;
    }
}
