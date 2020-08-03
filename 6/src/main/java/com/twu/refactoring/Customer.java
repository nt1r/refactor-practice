package com.twu.refactoring;

import java.util.ArrayList;
import java.util.Iterator;

public class Customer {

    private String name;
    private ArrayList<Rental> rentalList = new ArrayList<Rental>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentalList.add(arg);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Iterator<Rental> rentals = rentalList.iterator();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasNext()) {
            Rental rental = rentals.next();

            // add frequent renter points
            frequentRenterPoints += rental.addBonusForTwoDayNewReleaseRental();

            result = getFiguresForRental(result, determineAmountsForEachLine(rental), rental);
            totalAmount += determineAmountsForEachLine(rental);

        }
        // add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints)
                + " frequent renter points";
        return result;
    }

    private String getFiguresForRental(String result, double thisAmount, Rental each) {
        result += "\t" + each.getMovie().getTitle() + "\t"
                + String.valueOf(thisAmount) + "\n";
        return result;
    }

    private double determineAmountsForEachLine(Rental rental) {
        return rental.getCharge();
    }
}
