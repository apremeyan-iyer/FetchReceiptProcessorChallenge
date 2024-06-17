package com.receipt.processor.services.impl;

import com.receipt.processor.data.Item;
import com.receipt.processor.data.Points;
import com.receipt.processor.data.Receipt;
import com.receipt.processor.services.pointCalculatorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class pointCalculatorServiceImpl implements pointCalculatorService {

    /**
     * One point for every alphanumeric character in the retailer name.
     * @param retailer retailer name
     * @return points calculated from the retailer name
     */
    private double pointsFromRetailer(String retailer){
        int count = 0;
        for (int i = 0; i < retailer.length(); i++) {
            char ch = retailer.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 6 points if the day in the purchase date is odd.
     * @param purchaseDate date of purchase
     * @return points calculated from the date
     */
    private double pointsFromPurchaseDate(LocalDate purchaseDate){
        if (purchaseDate.getDayOfMonth()%2==1)
            return 6;
        return 0;
    }

    /**
     * 10 points if the time of purchase is after 2:00pm and before 4:00pm.
     * @param purchaseTime time of purchase
     * @return points calculated from the time
     */
    private double pointsFromPurchaseTime(String purchaseTime){
        final LocalTime localTime = LocalTime.parse(purchaseTime);
        final LocalTime startTime = LocalTime.of(14, 0); // 2:00 PM
        final LocalTime endTime = LocalTime.of(16, 0);   // 4:00 PM
        if (localTime.isAfter(startTime) && localTime.isBefore(endTime))
            return 10;
        return 0;
    }

    /**
     * 50 points if the total is a round dollar amount with no cents.
     * 25 points if the total is a multiple of 0.25.
     * @param totalCost cost of the purchase in the receipt
     * @return points calculated from the total cost of purchase
     */
    private double pointsFromTotal(double totalCost){
        double points = 0;
        // 50 points if the total is a round dollar amount with no cents.
        if (totalCost % 1.00 == 0)
            points += 50;
        // 25 points if the total is a multiple of 0.25.
        if (totalCost % 0.25 == 0)
            points += 25;
        return points;
    }

    /**
     * 5 points for every two items on the receipt.
     * If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
     * @param listOfItems list of the items purchased
     * @return points calculated from the items
     */
    private double pointsFromItems(List<Item> listOfItems){
       double points = 0;
       // 5 points for every two items on the receipt.
       points += ((int) (listOfItems.size() / 2)) * 5;
        // If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
       for (Item item:listOfItems){
           if (item.getShortDescription().trim().length() % 3 == 0){
               points += Math.ceil(item.getPrice() * 0.2);
           }
       }
       return points;
    }

    /**
     * Summing up all the points gained from the receipt
     * @param receipt Receipt object
     * @return total points
     */
    @Override
    public Points doTheMath(Receipt receipt){
        double points = 0;
        points += this.pointsFromRetailer(receipt.getRetailer());
        points += this.pointsFromPurchaseDate(receipt.getPurchaseDate());
        points += this.pointsFromPurchaseTime(receipt.getPurchaseTime());
        points += this.pointsFromTotal(receipt.getTotal());
        points += this.pointsFromItems(receipt.getItems());
        return Points.builder().points((int)points).build();
    }
}
