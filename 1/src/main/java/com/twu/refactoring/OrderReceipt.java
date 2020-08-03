package com.twu.refactoring;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 * 
 */
public class OrderReceipt {
	public static final double TAX_RATE = .10;
	public static final String SALES_TAX = "Sales Tax";
	private Order order;

    public OrderReceipt(Order order) {
        this.order = order;
	}

	public String printReceipt() {
		StringBuilder output = new StringBuilder();

		printHeaders(output);

		printDate_Bill_Name(output);

		// prints lineItems
		double totalSalesTax = 0d;
		double totalAmountOfLineItems = 0d;
		for (LineItem lineItem : order.getLineItems()) {
			printLineItems(output, lineItem);

			totalSalesTax += calculateSalesTax(lineItem);

            totalAmountOfLineItems += calculateTotalAmountOfLineItems(lineItem);
		}

		printStateTax(output, totalSalesTax);

		printTotalAmount(output, totalAmountOfLineItems);
		return output.toString();
	}

	private void printTotalAmount(StringBuilder output, double totalAmountOfLineItems) {
		output.append("Total Amount").append('\t').append(totalAmountOfLineItems);
	}

	private void printStateTax(StringBuilder output, double totalSalesTax) {
		output.append(SALES_TAX).append('\t').append(totalSalesTax);
	}

	private double calculateTotalAmountOfLineItems(LineItem lineItem) {
		return lineItem.totalAmount() + calculateSalesTax(lineItem);
	}

	private double calculateSalesTax(LineItem lineItem) {
		return lineItem.totalAmount() * TAX_RATE;
	}

	private void printLineItems(StringBuilder output, LineItem lineItem) {
		PrintString(output, lineItem.getDescription());
		output.append('\t');
		output.append(lineItem.getPrice());
		output.append('\t');
		output.append(lineItem.getQuantity());
		output.append('\t');
		output.append(lineItem.totalAmount());
		output.append('\n');
	}

	private void printDate_Bill_Name(StringBuilder output) {
		PrintString(output, order.getCustomerName());
		PrintString(output, order.getCustomerAddress());
	}

	private void printHeaders(StringBuilder outputStringBuilder) {
		PrintString(outputStringBuilder, getHeaderString());
	}

	private void PrintString(StringBuilder outputStringBuilder, String string) {
		outputStringBuilder.append(string);
	}

	private String getHeaderString() {
		return "======Printing Orders======\n";
	}
}