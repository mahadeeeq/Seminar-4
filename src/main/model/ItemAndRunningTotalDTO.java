package main.model;

public class ItemAndRunningTotalDTO {
    private final ItemDTO item;
    private final Amount runningTotal;
    private final Amount runningVat;

    public ItemAndRunningTotalDTO(ItemDTO item, Amount runningTotal, Amount runningVat) {
        this.item = item;
        this.runningTotal = runningTotal;
        this.runningVat = runningVat;
    }

    public ItemDTO getItem() {
        return item;
    }

    public Amount getRunningTotal() {
        return runningTotal;
    }

    public Amount getRunningVat() {
        return runningVat;
    }
}
