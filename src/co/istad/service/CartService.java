package co.istad.service;

import co.istad.dao.ProductDao;
import co.istad.dao.impl.ProductFileDao;
import co.istad.entity.Product;
import static co.istad.utils.PrintUtils.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {

    private final ProductDao productDao = new ProductFileDao();
    private final List<Product> cartItems = new ArrayList<>();

    public boolean addItemToCart(Integer productId, Integer quantity) {

        if (quantity == null || quantity <= 0) {
            printErr("Quantity must be a valid number greater than zero.");
            return false;
        }
        Product productInStock = productDao.findById(productId);
        if (productInStock == null) {
            printErr("Product ID " + productId + " not found.");
            return false;
        }

        if (productInStock.getQty() < quantity) {
            printWarm("Out of Stock! Only " + productInStock.getQty() + " available.");
            return false;
        }

        for (Product item : cartItems) {
            if (item.getId().equals(productId)) {
                if (item.getQty() + quantity > productInStock.getQty()) {
                    printWarm("Cannot add more. You already have " + item.getQty() + " in cart.");
                    return false;
                }
                item.setQty(item.getQty() + quantity);
                printTrue("Updated cart: " + item.getName() + " (Total: " + item.getQty() + ")");
                return true;
            }
        }
        
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
        return true;
    }

    public double checkout() {
        if (this.cartItems.isEmpty()) {
            printInfo("Cart is empty. Nothing to checkout.");
            return 0.0;
        }
        double totalAmount = 0.0;
        
        printHead("CHECKOUT SUMMARY");
        printf("%-20s %-5s %-10s %-10s%n", "NAME", "QTY", "PRICE", "TOTAL");
        
        for (Product item : this.cartItems) {
            double Total = item.getPrice() * item.getQty();
            totalAmount += Total;

            printf("%-20s %-5d %-9.2f$ %-9.2f$%n",
                    item.getName(), item.getQty(), item.getPrice(), Total);

            Product realProduct = productDao.findById(item.getId());

            if (realProduct == null) {
                printWarm("Product ID " + item.getId() + " was deleted. Stock update skipped.");
                continue;
            }

            int newStockQty = realProduct.getQty() - item.getQty();
            realProduct.setQty(Math.max(newStockQty, 0));

            productDao.insert(realProduct);
        }
        this.cartItems.clear();


        printf("%nTOTAL PAYABLE: %.2f$%n", totalAmount);

        printTrue("Checkout Successful!.");

        return totalAmount;
    }

    public void clearCart() {
        this.cartItems.clear();
        printInfo("Cart cleared.");
    }

    public List<Product> getCartItems() {
        return this.cartItems;
    }
}