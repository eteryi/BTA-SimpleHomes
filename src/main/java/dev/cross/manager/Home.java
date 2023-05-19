package dev.cross.manager;

import java.util.UUID;

public class Home {

    private final String homeName;
    private final Location location;
    private boolean isPrivate;
    private final String ownerUsername;
    private final UUID homeUUID;
    public Home(String homeName, Location homeLocation, boolean isPrivate, String ownerUsername) {
        this.homeName = homeName;
        this.location = homeLocation;
        this.isPrivate = isPrivate;
        this.ownerUsername = ownerUsername;
        this.homeUUID = UUID.randomUUID();
    }
    public Home(String homeName, Location homeLocation, boolean isPrivate, String ownerUsername, UUID uuid) {
        this.homeName = homeName;
        this.location = homeLocation;
        this.isPrivate = isPrivate;
        this.ownerUsername = ownerUsername;
        this.homeUUID = UUID.randomUUID();
    }

    public Location getLocation() {
        return location;
    }

    public String getOwnerUsername() {
        return this.ownerUsername;
    }

    public String getHomeName() {
        return homeName;
    }

    public UUID getHomeUUID() {
        return homeUUID;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean b) {
        this.isPrivate = b;
    }
}
