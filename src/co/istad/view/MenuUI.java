package co.istad.view;

import co.istad.entity.User;
import co.istad.service.*;
import co.istad.service.impl.CartServiceImpl;

import static co.istad.utils.InputUtils.*;
import static co.istad.utils.PrintUtils.*;
import static co.istad.view.Color.*;

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
                    CYAN+"1. Product Center"+RESET,
                    CYAN+"2. Customer Center"+RESET,
                    CYAN+"3. View Sales History"+RESET,
                    CYAN+"4. User Management"+RESET,
                    RED+"0. Logout"+RESET
            };
            TableUtils.renderMenu(GREEN+"ADMIN DASHBOARD"+RESET, options);

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
                    CYAN+"1. Product Management"+RESET,
                    CYAN+"2. View Full Inventory"+RESET,
                    RED+"0. Logout"+RESET
            };
            TableUtils.renderMenu(GREEN+"STOCK MANAGER"+RESET, options);

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
                    CYAN+"1. New Sale (Checkout)"+RESET,
                    CYAN+"2. Check Product Price"+RESET,
                    CYAN+"3. Register New Customer"+RESET,
                    CYAN+"4. View My Sales"+RESET,
                    RED+"0. Logout"+RESET
            };
            TableUtils.renderMenu(GREEN+"POINT OF SALE"+RESET, options);

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
                    CYAN+"1. Add / Restock"+RESET,
                    CYAN+"2. Edit Details"+RESET,
                    CYAN+"3. Delete Product"+RESET,
                    CYAN+"4. View All"+RESET,
                    CYAN+"5. Search"+RESET,
                    RED+"0. Back"+RESET
            };
            TableUtils.renderMenu(GREEN+"PRODUCT CENTER (" + role + ")"+RESET, options);

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
                    CYAN+"1. Add New Customer"+RESET,
                    CYAN+"2. View All Customers"+RESET,
                    CYAN+"3. Edit Customer"+RESET,
                    CYAN+"4. Search Customer"+RESET,
                    RED+"0. Back"+RESET
            };

            TableUtils.renderMenu(GREEN+"CUSTOMER CENTER"+RESET, options);

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
                    CYAN+"1. Create New Staff"+RESET,
                    CYAN+"2. View All Users"+RESET,
                    CYAN+"3. Edit User / Reset Password"+RESET,
                    CYAN+"4. Disable / Ban User"+RESET,
                    RED+"0. Back"+RESET
            };
            TableUtils.renderMenu(GREEN+"USER MANAGEMENT"+RESET, options);

            int choice = readInt("Option");

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
        printHead(GREEN+"SHOPPING CART MODE"+RESET);
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
