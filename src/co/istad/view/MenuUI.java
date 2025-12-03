
package co.istad.view;

import co.istad.dao.ProductDao;
import co.istad.service.ProductService;

import static co.istad.utils.InputUtils.*;
import static co.istad.utils.PrintUtils.*;

public class MenuUI {
    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public void start() {
        while (true){
            printHead("welcome to pos system");
            printCase("1. Sale Menu");
            printCase("2. Admin Menu");
            printCase("3. Stock Menu");
            printCase("0. Exit");
            int option = readInt("Choose option");
            while (true){
                switch (option) {
                    case 1 ->
                            saleMenu();

                    case 2 ->
                            adminMenu();

                    case 3 ->
                            stockMenu();

                    case 0 ->
                            System.exit(0);

                    default ->
                            printErr("Invalid Input..!");
                }
            }
        }
    }

    private void adminMenu() {

        printHead("Admin Menu");
        printCase("1. List Product");
        printCase("2. Add Product");
        printCase("3. Update Product");
        printCase("4. Delete Product");
        printCase("5. Search Product");
        printCase("6. View Transition History");
        printCase("0. Exit");
        int option = readInt("Choose option");

        while (true){
            switch (option) {
                case 1 ->  {
//                    ProductDao.selectAll();
                }

                case 2 -> {
//                    ProductService.AddProduct();
                }

                case 3 -> {
//                    stockMenu();
                }

                case 4 -> {
//                    delete
                }

                case 5 -> {
//                    search
                }

                case 0 ->
                        System.exit(0);

                default ->
                        printErr("Invalid Input..!");

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
