package co.istad.service;

import co.istad.dao.CustomerDao;
import co.istad.dao.ProductDao;
import co.istad.dao.impl.CustomerFileDao;
import co.istad.dao.impl.ProductFileDao;
import co.istad.entity.Customer;
import co.istad.entity.Product;

import static co.istad.utils.PrintUtils.*;
import static co.istad.utils.InputUtils.*;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    private final ProductDao productDao = new ProductFileDao();
    private final CustomerDao customerDao = new CustomerFileDao();
    private final List<Product> cartItems = new ArrayList<>();
    public void addItemToCart(Integer productId, Integer quantity) {

        if (quantity == null || quantity <= 0) {
            printErr("Quantity must be a valid number greater than zero.");
            return;
        }
        Product productInStock = productDao.findById(productId);

        if (productInStock == null) {
            printErr("Product ID [" + productId + "] not found.");
            return;
        }

        if (productInStock.getQty() < quantity) {
            printWarm("Out of Stock! Only " + productInStock.getQty() + " available.");
            return;
        }
        for (Product item : cartItems) {
            if (item.getId().equals(productId)) {
                if (item.getQty() + quantity > productInStock.getQty()) {
                    printWarm("Cannot add more. You already have " + item.getQty() + " in cart.");
                    return;
                }
                item.setQty(item.getQty() + quantity);
                printTrue("Updated cart: " + item.getName() + " (Total: " + item.getQty() + ")");
                return;
            }
        }

        // Add New Item
        Product cartItem = new Product(
                productInStock.getId(),
                productInStock.getName(),
                productInStock.getPrice(),
                quantity,
                productInStock.getCategory(),
                productInStock.getStatus(),
                productInStock.getCost()
        );

        this.cartItems.add(cartItem);
        printTrue("Added " + quantity + " x " + productInStock.getName() + " to cart.");
    }

    public double checkout() {
        if (this.cartItems.isEmpty()) {
            printInfo("Cart is empty. Nothing to checkout.");
            return 0.0;
        }

        printHead("CHECKOUT PROCESS");
        Customer customer = null;
        double discountRate = 0.0;

        while (true) {
            String custId = readText(">> Enter Customer ID (or 0 for Walk-in): ");

            if (custId.equals("0")) {
                printInfo("Proceeding as Walk-in Customer.");
                break;
            }

            customer = customerDao.searchById(custId);

            if (customer != null) {
                printTrue("Customer Found: " + customer.getName() + " (" + customer.getType() + ")");
                if (customer.getType().equalsIgnoreCase("VIP")) {
                    discountRate = 0.10;
                    printInfo("VIP Discount (10%) Applied!");
                }
                break;
            } else {
                printErr("Customer ID [" + custId + "] not found. Please try again.");
            }
        }
        double subTotal = 0.0;
        for (Product item : this.cartItems) {
            subTotal += (item.getPrice() * item.getQty());
        }
        double discountAmount = subTotal * discountRate;
        double grandTotal = subTotal - discountAmount;
        println("----------------------------------------");
        println(String.format("Total to Pay: $%.2f", grandTotal));
        println("----------------------------------------");

        if (!readconfirm(">> Confirm Payment?")) {
            printInfo("Checkout cancelled. Items remain in cart.");
            return 0.0;
        }

        printHead("OFFICIAL RECEIPT");
        String customerName = (customer != null) ? customer.getName() : "Walk-in Customer";
        println("Customer: " + customerName);
        println("------------------------------------------------");

        printf("%-20s %-5s %-10s %-10s%n", "NAME", "QTY", "PRICE", "TOTAL");
        println("------------------------------------------------");

        for (Product item : this.cartItems) {
            double lineTotal = item.getPrice() * item.getQty();
           printf("%-20s %-5d $%-9.2f $%-9.2f%n",
                    item.getName(), item.getQty(), item.getPrice(), lineTotal);


            Product realProduct = productDao.findById(item.getId());
            if (realProduct != null) {
                int newStockQty = realProduct.getQty() - item.getQty();
                realProduct.setQty(Math.max(newStockQty, 0));
                productDao.insert(realProduct);
            }
        }
        println("------------------------------------------------");

        println(String.format("SUBTOTAL:      $%.2f", subTotal));

        if (discountRate > 0) {
            println(String.format("DISCOUNT (10%%):-$%.2f", discountAmount));
        }

        println(String.format("GRAND TOTAL:   $%.2f", grandTotal));

        println("================================================");
        println();

        this.cartItems.clear();
        printTrue("Checkout Successful!");

        return grandTotal;
    }
    public void clearCart() {
        this.cartItems.clear();
        printInfo("Cart cleared.");
    }

    public List<Product> getCartItems() {
        return this.cartItems;
    }
}