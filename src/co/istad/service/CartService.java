package co.istad.service;

import co.istad.dao.ProductDao;
import co.istad.entity.Product;
import co.istad.utils.PrintUtils;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    private final ProductDao productDao;
    private final List<Product> cartItems;

    public CartService(ProductDao productDao) {
        this.productDao = productDao;
        this.cartItems = new ArrayList<>();
    }

    public boolean addItemToCart(Integer productId, Integer quantity) {
        Product productInStock = productDao.findById(productId);

        if (productInStock == null) {
            PrintUtils.printErr("Error: Product ID " + productId + " not found.");
            return false;
        }

        if (quantity == null || quantity <= 0) {
            PrintUtils.printErr("Error: Quantity must be a valid number greater than zero.");
            return false;
        }

        if (productInStock.getQty() < quantity) {
            PrintUtils.printWarm("Out of Stock! Only " + productInStock.getQty() + " of " + productInStock.getName() + " available.");
            return false;
        }

        Product cartItem = new Product();
        cartItem.setId(productInStock.getId());
        cartItem.setName(productInStock.getName());
        cartItem.setPrice(productInStock.getPrice());
        cartItem.setCost(productInStock.getCost());
        cartItem.setQty(quantity);
        cartItem.setCategory(productInStock.getCategory());
        cartItem.setStatus(productInStock.getStatus());

        this.cartItems.add(cartItem);
        PrintUtils.printTrue("Added " + quantity + " x " + productInStock.getName() + " to cart.");
        return true;
    }

    public List<Product> getCartItems() {
        return this.cartItems;
    }

    public double checkout() {
        if (this.cartItems.isEmpty()) {
            PrintUtils.printInfo("Cart is empty. Nothing to checkout.");
            return 0.0;
        }

        double totalAmount = 0.0;

        PrintUtils.println("\n--- 🧾 Checkout Summary ---");

        for (Product item : this.cartItems) {
            double lineTotal = item.getPrice() * item.getQty();
            totalAmount += lineTotal;

            PrintUtils.printf("%-20s %3d x $%.2f = $%.2f%n", item.getName(), item.getQty(), item.getPrice(), lineTotal);

            Product productInStock = productDao.findById(item.getId());

            if (productInStock == null) {
                PrintUtils.printWarm("Product ID " + item.getId() + " was deleted from stock. Stock update skipped.");
                continue;
            }

            int newStockQty = productInStock.getQty() - item.getQty();
            productInStock.setQty(Integer.valueOf(newStockQty));

            productDao.insert(productInStock);
        }

        this.cartItems.clear();

        PrintUtils.println("---------------------------");
        PrintUtils.printf("Total Payable: $%.2f%n", totalAmount);
        PrintUtils.println("---------------------------");

        return totalAmount;
    }

    public void addItemToCart(int i, int i1) {
    }
}