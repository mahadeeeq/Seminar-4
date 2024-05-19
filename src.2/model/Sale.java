package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sale {
    private LocalDate dateOfSale;
    private LocalTime timeOfSale;
    private Amount runningTotal;
    private Amount runningVat;
    private ArrayList<SaleItem> itemList;
    private List<SaleObserver> saleObservers = new ArrayList<>();

    public Sale() {
        runningTotal = new Amount(0);
        runningVat = new Amount(0);
        itemList = new ArrayList<>();
        dateOfSale = LocalDate.now();
        timeOfSale = LocalTime.now();
    }

    public ItemAndRunningTotalDTO registerItem(ItemDTO item) throws ItemNotFoundException, DatabaseFailureException {
        if ("dbFail".equals(item.getIdentifier())) {
            throw new DatabaseFailureException("Database is not accessible for item identifier: " + item.getIdentifier());
        }

        if (item == null) {
            throw new ItemNotFoundException("Item with identifier " + item.getIdentifier() + " not found.");
        }

        int indexOfItem = indexIfAlreadyRegistered(item);
        if (indexOfItem != -1) {
            itemList.get(indexOfItem).addQuantity(1);
        } else {
            itemList.add(new SaleItem(item, 1));
        }
        updateTotals(item.getPrice(), item.getVat());
        return new ItemAndRunningTotalDTO(item, runningTotal, runningVat);
    }

    private void updateTotals(Amount price, Amount vat) {
        runningTotal = runningTotal.plus(price).plus(vat);
        runningVat = runningVat.plus(vat);
    }

    private int indexIfAlreadyRegistered(ItemDTO searchedItem) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getItem().equals(searchedItem)) {
                return i;
            }
        }
        return -1;
    }

    public Amount getRunningTotal() {
        return runningTotal;
    }

    public Amount getRunningVat() {
        return runningVat;
    }

    public List<SaleItemDTO> getItemListDTO() {
        return itemList.stream().map(SaleItemDTO::new).collect(Collectors.toList());
    }

    public List<SaleItem> getItemList() {
        return itemList;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public LocalTime getTimeOfSale() {
        return timeOfSale;
    }

    public void addSaleObserver(SaleObserver observer) {
        saleObservers.add(observer);
    }

    private void notifyObservers() {
        for (SaleObserver observer : saleObservers) {
            observer.newSale(runningTotal);
        }
    }

    public void finalizeSale() {
        notifyObservers();
    }
}