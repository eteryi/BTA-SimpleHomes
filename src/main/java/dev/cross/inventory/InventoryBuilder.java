package dev.cross.inventory;

import net.minecraft.src.ItemStack;

public class InventoryBuilder {
    private ItemStack[] contents = new ItemStack[72];
    private String name = "Chest";
    private int size = 27;
    public InventoryBuilder setItemStack(ItemStack itemStack, int i) {
        this.contents[i] = itemStack;
        return this;
    }

    public InventoryBuilder setName(String s) {
        this.name = s;
        return this;
    }

    public InventoryBuilder setSize(int i) {
        this.size = i;
        return this;
    }

    public Inventory build() {
        return new Inventory(size, name, contents);
    }
}
