package dev.cross.manager;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    private long backup;
    private static Utils INSTANCE;
    private ArrayList<Home> homes;
    private HashMap<String, ArrayList<Home>> values;
    private boolean listChanged;
    public static Utils get() {
        if (INSTANCE == null) { INSTANCE = new Utils(); }
        return INSTANCE;
    }
    private Utils() {
        values = new HashMap<>();
        listChanged = true;
        backup = System.currentTimeMillis() + Config.get().getBackupTimer();
    }

    public long getBackup() {
        return backup;
    }

    public void setBackup(long b) {
        backup = b;
    }
    public ArrayList<Home> getHomesFromUsername(String s) {
        ArrayList<Home> userHomes = values.get(s);

        if (userHomes == null) {userHomes = new ArrayList<>();}
        return userHomes;
    }

    public void addHome(Home i) {
        ArrayList<Home> homes = getHomesFromUsername(i.getOwnerUsername());
        homes.add(i);
        values.put(i.getOwnerUsername(), homes);
        listChanged = true;
    }

    public void setList(ArrayList<Home> homes) {
        for (Home i : homes) {
            addHome(i);
        }
    }

    public ArrayList<Home> getList() {
        ArrayList<Home> allHomes = new ArrayList<>();
        for (ArrayList<Home> i : this.values.values()) {
            allHomes.addAll(i);
        }
        return allHomes;
    }

    public int homeAmount(String username) {
        return getHomesFromUsername(username).size();
    }

    public boolean deleteHome(String owner, String homeName) {
        Home home = null;
        for (Home i : getHomesFromUsername(owner)) {
            if (i.getHomeName().equals(homeName)) {
                home = i;
                break;
            }
        }
        if (home == null) {
            return false;
        }
        ArrayList<Home> homes = getHomesFromUsername(owner);
        homes.remove(home);
        listChanged = true;
        return true;
    }

    public boolean isListChanged() {
        return listChanged;
    }

    public void setListChanged(boolean listChanged) {
        this.listChanged = listChanged;
    }

    public ArrayList<Home> getPublicHomes() {
        ArrayList<Home> publicHomes = new ArrayList<>();
        getList().forEach(i -> {
            if (!i.isPrivate()) publicHomes.add(i);
        });
        return publicHomes;
    }

    public boolean publishHome(Home i) {
        i.setPrivate(false);
        listChanged = true;
        return true;
    }

    public boolean unpublishHome(Home i) {
        if (i.isPrivate()) { return false; }
        i.setPrivate(true);
        listChanged = true;
        return true;
    }

    public boolean globalContains(String s) {
        for (Home i : getPublicHomes()) {
            if (i.getHomeName().equals(s)) { return true; }
        }
        return false;
    }

    public Home getHomeFromName(String username, String home) {
        for (Home i : getHomesFromUsername(username)) {
            if (i.getHomeName().equals(home)) {
                return i;
            }
        }
        return null;
    }

    /*
    IDEAS FOR GLOBAL HOMES:
    /publish <name> - publishes a home
    /unpublish <name>
    /neighbourhood ?<name> - lists all the homes and lets you teleport to them

    HashMap with invalid username string to store them

     */
}
