package dev.cross.manager;

import net.minecraft.src.EntityPlayer;

public class Location {

    public double x;
    public double y;
    public double z;
    public int dimension;

    public Location(double x, double y, double z, int dimension) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public static Location getLocationFromPlayer(EntityPlayer p) {
        return new Location(p.posX, p.posY, p.posZ, p.dimension);
    }

    public static void teleport(EntityPlayer p, Location t) {
        p.setPosition(t.x, t.y, t.z);
    }

}
