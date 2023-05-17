package dev.cross.manager;

import java.util.ArrayList;

public class Utils {

    private long backup;
    private static Utils INSTANCE;
    private ArrayList<Home> homes;

    public static Utils get() {
        if (INSTANCE == null) { INSTANCE = new Utils(); }
        return INSTANCE;
    }
    private Utils() {
        homes = new ArrayList<>();
        backup = System.currentTimeMillis() + Config.get().getBackupTimer();
    }

    public long getBackup() {
        return backup;
    }

    public void setBackup(long b) {
        backup = b;
    }
    public ArrayList<Home> getHomesFromUsername(String s) {
        ArrayList<Home> userHomes = new ArrayList<>();

        for (Home i : homes) {
            if (i.getOwnerUsername().equalsIgnoreCase(s)) {
                userHomes.add(i);
            }
        }
        return userHomes;
    }

    public void addHome(Home i) {
        homes.add(i);
    }

    public void setList(ArrayList<Home> homes) {
        this.homes = homes;
    }

    public ArrayList<Home> getList() {
        return this.homes;
    }

    public int homeAmount(String username) {
        int r = 0;
        for (int i = 0; i < homes.size(); i++) {
            if (homes.get(i).getOwnerUsername().equalsIgnoreCase(username)) {
                r++;
            }
        }
        return r;
    }

    public boolean deleteHome(String owner, String homeName) {
        Home home = null;
        for (Home i : getHomesFromUsername(owner)) {
            if (i.getHomeName().equals(homeName)) {
                home = i;
            }
        }
        if (home == null) {
            return false;
        }
        homes.remove(home);
        return true;
    }
}
