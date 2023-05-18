package dev.cross.inventory;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class Inventory implements IInventory {

    private int size;
    private String name;
    private ItemStack[] contents;

    public Inventory(int i, String s, ItemStack[] b) {
        this.size = i;
        this.name = s;
        this.contents = b;
    }

    @Override
    public int getSizeInventory() {
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return contents[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int i1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.contents[i] = itemStack;
        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return name;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {

    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return false;
    }
}
