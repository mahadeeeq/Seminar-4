package main.model;

public class SaleItem {
    private ItemDTO item;
    private int quantity;

    public SaleItem(ItemDTO item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public ItemDTO getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        Amount totalPrice = item.getPrice().plus(item.getVat()).multiply(quantity);
        return String.format("%s x%d @ %s each", item.getName(), quantity, item.getPrice().plus(item.getVat()));
    }
}
