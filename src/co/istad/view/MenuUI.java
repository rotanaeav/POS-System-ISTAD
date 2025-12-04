
package co.istad.view;

import co.istad.dao.ProductDao;
import co.istad.entity.User;
import co.istad.service.AuthService;
import co.istad.service.CartService;
import co.istad.service.ProductService;

import static co.istad.utils.InputUtils.*;
import static co.istad.utils.PrintUtils.*;

public class MenuUI {

    private final AuthService authService = new AuthService();
    private final ProductService productService = new ProductService();
    private final CartService cartService = new CartService();

    public void start() {
        while (true) {
            User user = authService.login();

            if (user == null) {
                printInfo("Goodbye! System closing...");
                System.exit(0);
            }

            String role = user.getRole().toUpperCase();

            switch (role) {
                case "ADMIN": adminMenu(); break;
                case "STOCK": stockMenu(); break;
                case "SALE":  saleMenu(); break;
                default: printErr("Role [" + role + "] is not recognized.");
            }
        }
    }

    private void adminMenu() {
        while (true) {
            println();
            String[] options = {
                    "1. 📦 Product Center",
                    "2. 👥 Customer Center",
                    "3. 📊 View Sales History",
                    "4. 👤 User Management",
                    "0. 🚪 Logout"
            };
            TableUtils.renderMenu("ADMIN DASHBOARD", options);

            int choice = readInt(">> Choose option: ");

            switch (choice) {
                case 1: productMenu("ADMIN"); break;
                case 2: customerMenu(); break;
                case 3: historyService.viewSalesHistory(); break; // Member C feature
                case 4: println("⚠️ User Feature coming soon..."); break;
                case 0: authService.logout(); return;
                default: printErr("Invalid option.");
            }
        }
    }

    private void stockMenu() {
        printHead("Stock Menu");
        printCase("1. List Product");
        printCase("2. Add Product");
        printCase("3. Update Product");
        printCase("4. Delete Product");
        printCase("5. Search Product");
        printCase("0. Exit");
        int option = readInt("Choose option");

        while (true){
            switch (option) {
                case 1 ->  {
                    ProductService pro = new ProductService();
                }

                case 2 -> {
//                    ProductService.AddProduct();
                }

                case 3 ->
                        stockMenu();

                case 0 ->
                        System.exit(0);

                default ->
                        printErr("Invalid Input..!");

            }
        }

    }

    private void saleMenu() {
        while (true) {
            printHead("Sale Menu");
            printCase("1. Add to Cart");
            printCase("2. Calculate Total");
            printCase("3. Checkout");
            printCase("0. Exit");
            int option = readInt("Choose");

            switch (option) {

                case 1 -> {}

                case 2 -> {}

                case 3 -> {}

                case 0 ->
                        System.exit(0);

                default ->
                        printErr("Invalid Input..!");

            }
        }
    }
}
