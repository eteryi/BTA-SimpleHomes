package dev.cross.manager;

import org.simple.JSONArray;
import org.simple.JSONObject;
import org.simple.parser.JSONParser;
import org.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class HomeList {

    private static HomeList INSTANCE;

    public static HomeList get() {
        if (INSTANCE == null) { INSTANCE = new HomeList(); }
        return INSTANCE;
    }
    private HomeList() {

    }

    public boolean save() {
        JSONArray homesArray = new JSONArray();
        ArrayList<Home> homes = Utils.get().getList();

        for (Home home : homes) {
            JSONObject homeJSON = new JSONObject();
            homeJSON.put("name", home.getHomeName());
            homeJSON.put("x", home.getLocation().x);
            homeJSON.put("y", home.getLocation().y);
            homeJSON.put("z", home.getLocation().z);
            homeJSON.put("dimension", home.getLocation().dimension);
            homeJSON.put("owner", home.getOwnerUsername());
            homeJSON.put("uuid", home.getHomeUUID().toString());
            homeJSON.put("private", home.isPrivate());

            homesArray.add(homeJSON);
        }

        File f = new File("world/homes/homes.json");
        if (f.exists()) {
            f.delete();
        }

        try (FileWriter file = new FileWriter("world/homes/homes.json")) {
            file.write(homesArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Home parseHome(JSONObject obj) {
        double x = (double) obj.get("x");
        double y = (double) obj.get("y");
        double z = (double) obj.get("z");
        int d = Math.toIntExact((Long)obj.get("dimension"));

        Location loc = new Location(x,y,z,d);

        String homeName = (String) obj.get("name");
        String ownerUsername = (String) obj.get("owner");
        UUID uuid = UUID.fromString((String) obj.get("uuid"));
        boolean isPrivate = (boolean) obj.get("private");

        return new Home(homeName, loc, isPrivate, ownerUsername, uuid);
    }
    public boolean load() {
        File f = new File("world/homes/homes.json");
        if (!f.exists()) {
            return true;
        }

        JSONParser parser = new JSONParser();

        ArrayList<Home> homes = new ArrayList<>();
        try (FileReader reader = new FileReader("world/homes/homes.json")) {
            Object object = parser.parse(reader);
            JSONArray homesJSON = (JSONArray) object;

            homesJSON.forEach(it -> {
                homes.add(parseHome((JSONObject) it));
            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Utils.get().setList(homes);
        return true;
    }
}
