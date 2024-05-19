package model;

import java.util.List;

public class SaleDTO {
    private final Amount runningTotal;
    private final Amount runningVat;
    private final List<SaleItemDTO> itemList;

    public SaleDTO(Sale sale) {
        this.runningTotal = sale.getRunningTotal();
        this.runningVat = sale.getRunningVat();
        this.itemList = sale.getItemListDTO();
    }

    public Amount getRunningTotal() {
        return runningTotal;
    }

    public Amount getRunningVat() {
        return runningVat;
    }

    public List<SaleItemDTO> getItemList() {
        return itemList;
    }
}
