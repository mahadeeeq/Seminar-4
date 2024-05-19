package model;

public class SaleItemDTO {
    private final ItemDTO productDetail;
    private final int unitsPurchased;

    public SaleItemDTO(SaleItem saleItem) {
        this.productDetail = saleItem.getItem();
        this.unitsPurchased = saleItem.getQuantity();
    }

    public ItemDTO getProductDetail() {
        return productDetail;
    }

    public int getUnitsPurchased() {
        return unitsPurchased;
    }
}
