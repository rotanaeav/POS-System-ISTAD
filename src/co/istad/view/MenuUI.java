package co.istad.view;

import co.istad.entity.User;
import co.istad.service.*;
import co.istad.service.impl.CartServiceImpl;

import static co.istad.utils.InputUtils.*;
import static co.istad.utils.PrintUtils.*;

public class MenuUI {

    private final AuthService authService = new AuthService();
    private final ProductService productService = new ProductService();
    private final CartService cartService = new CartServiceImpl();
    private final CustomerService customerService = new CustomerService();
    private final UserService userService = new UserService();
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
                case "SALE":  sellerMenu(); break;
                default: printErr("Role [" + role + "] is not recognized.");
            }
            pressEnter();
        }
    }

    private void adminMenu() {
        while (true) {
            println();
            String[] options = {
                    "1. Product Center",
                    "2. Customer Center",
                    "3. View Sales History",
                    "4. User Management",
                    "0. Logout"
            };
            TableUtils.renderMenu("ADMIN DASHBOARD", options);

            int choice = readInt("Choose option");

            switch (choice) {
                case 1: productMenu("ADMIN"); break;
                case 2: customerMenu(); break;
                case 3: println("Sale history Feature coming soon..."); break;
                case 4: userMenu(); break;
                case 0: authService.logout(); return;
                default: printErr("Invalid option.");
            }
            pressEnter();
        }
    }
    private void stockMenu() {
        while (true) {
            println();
            String[] options = {
                    "1. Product Management",
                    "2. View Full Inventory",
                    "0. Logout"
            };
            TableUtils.renderMenu("STOCK MANAGER", options);

            int choice = readInt("Choose option");

            switch (choice) {
                case 1: productMenu("STOCK"); break;
                case 2: productService.viewProducts(); break;
                case 0: authService.logout(); return;
                default: printErr("Invalid option.");
            }
            pressEnter();
        }
    }
    private void sellerMenu() {
        while (true) {
            println();
            String[] options = {
                    "1. New Sale (Checkout)",
                    "2. Check Product Price",
                    "3. Register New Customer",
                    "4. View My Sales",
                    "0. Logout"
            };
            TableUtils.renderMenu("POINT OF SALE", options);

            int choice = readInt("Choose option");
            switch (choice) {
                case 1: startSalesProcess(); break;
                case 2: productService.searchProduct(); break;
                case 3: customerService.addCustomer(); break;
                case 4: println("Sale history Feature coming soon..."); break;
                case 0: authService.logout(); return;
                default: printErr("Invalid option.");
            }
            pressEnter();
        }
    }
    private void productMenu(String role) {
        while (true) {
            println();
            String[] options = {
                    "1. Add / Restock",
                    "2. Edit Details",
                    "3. Delete Product",
                    "4. View All",
                    "5. Search",
                    "0. Back"
            };
            TableUtils.renderMenu("PRODUCT CENTER (" + role + ")", options);

            int choice = readInt("Option");

            switch (choice) {
                case 1: productService.addProduct(); break;
                case 2: productService.editProduct(); break;
                case 3: productService.deleteProduct(); break;
                case 4: productService.viewProducts(); break;
                case 5: productService.searchProduct(); break;
                case 0: return;
                default: printErr("Invalid option.");
            }
            pressEnter();
        }
    }
    private void customerMenu() {
        while (true) {
            println();
            String[] options = {
                    "1. Add New Customer",
                    "2. View All Customers",
                    "3. Edit Customer",
                    "4. Search Customer",
                    "0. Back"
            };
            TableUtils.renderMenu("CUSTOMER CENTER", options);

            int choice = readInt("Option");

            switch (choice) {
                case 1: customerService.addCustomer(); break;
                case 2: customerService.viewCustomers(); break;
                case 3: customerService.editCustomer(); break;
                case 4: customerService.searchCustomer(); break;
                case 0: return;
                default: printErr("Invalid option.");
            }
            pressEnter();
        }
    }
    private void userMenu() {
        while (true) {
            println();
            String[] options = {
                    "1. Create New Staff",
                    "2. View All Users",
                    "3. Edit User / Reset Password",
                    "4. Disable / Ban User",
                    "0. Back"
            };
            TableUtils.renderMenu("USER MANAGEMENT", options);

            int choice = readInt(">> Option: ");

            switch (choice) {
                case 1:
                    //  userService.createUser();
                    break;
                case 2:
                   // userService.viewUsers();
                    break;
                case 3:
                   // userService.editUser();
                    break;
                case 4:
                  //  userService.disableUser();
                    break;
                case 0:
                    return;
                default:
                    printErr("Invalid option.");
            }
            pressEnter();
        }
    }

    private void startSalesProcess() {
        printHead("SHOPPING CART MODE");
        cartService.clearCart();

        while (true) {
            println("----------------------------------------");
            println("INSTRUCTIONS: Scan ID to Add. Type '0' to Checkout.");

            int id = readInt("Scan Product ID (or 0 to Checkout, -1 to Cancel)");

            if (id == -1) {
                cartService.clearCart();
                printInfo("Sale cancelled.");
                return;
            }

            if (id == 0) {
                cartService.checkout();
                return;
            }

            int qty = readInt("Quantity");
            cartService.addItemToCart(id, qty);

            pressEnter();
        }
    }
}
