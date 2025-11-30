package co.istad;

import co.istad.view.MenuUI;
import co.istad.service.CartService;

public class Main {

    public static void main(String[] args) {

        // MenuUI menu = new MenuUI();
        // menu.start();

        testCartServiceIntegration();
    }

    public static void testCartServiceIntegration() {
        MenuUI menu = new MenuUI();

        CartService cartService = menu.getCartService();

        System.out.println("--- Starting CartService Test ---");

        System.out.println("\n[Test 1] Adding 3 of Product ID 1...");
        cartService.addItemToCart(1, 3);

        System.out.println("\n[Test 2] Adding 5 of Product ID 2...");
        cartService.addItemToCart(2, 5);

        System.out.println("\n[Test 3] Adding Product ID 99 (Non-existent)...");
        cartService.addItemToCart(99, 1);

        System.out.println("\n[Test 4] Executing Checkout...");
        cartService.checkout();

        System.out.println("\n--- Test Complete. Check products.csv for updated stock levels! ---");
    }
}