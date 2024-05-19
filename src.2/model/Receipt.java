package model;

import java.util.List;

public class Receipt {
    private final Sale sale;
    private final CashPayment payment;

    public Receipt(Sale sale, CashPayment payment) {
        this.sale = sale;
        this.payment = payment;
    }

    @Override
    public String toString() {
        StringBuilder receiptText = new StringBuilder();
        receiptText.append("----------------------- BEGIN RECEIPT -----------------------\n");
        receiptText.append("Sale Time: ").append(sale.getDateOfSale()).append(" ").append(sale.getTimeOfSale()).append("\n");

        List<SaleItem> items = sale.getItemList();
        for (SaleItem item : items) {
            receiptText.append(String.format("%s x%d @ %s each", item.getItem().getName(), item.getQuantity(), item.getItem().getPrice().plus(item.getItem().getVat()))).append("\n");
        }

        receiptText.append("Total: ").append(sale.getRunningTotal()).append("\n");
        receiptText.append("VAT: ").append(sale.getRunningVat()).append("\n");
        receiptText.append("Cash: ").append(payment.getPaidAmount()).append("\n");
        receiptText.append("Change: ").append(payment.getChange()).append("\n");
        receiptText.append("----------------------- END RECEIPT -----------------------");
        return receiptText.toString();
    }
}
