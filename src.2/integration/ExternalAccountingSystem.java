package integration;

import model.SaleDTO;

public class ExternalAccountingSystem {
    public void update(SaleDTO saleDTO) {
        System.out.println("Updating accounting system with sale total: " + saleDTO.getRunningTotal());
    }
}
