package co.istad.view;

import co.istad.entity.Customer;
import co.istad.entity.Product;
import org.nocrala.tools.texttablefmt.*;

import java.util.List;
import static co.istad.utils.PrintUtils.*;
import static co.istad.view.Color.*;

public class TableUtils {

    public static void renderProducts(List<Product> products) {
        if (products.isEmpty()) {
            printInfo("No products found.");
            return;
        }

        Table t = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX);

        // Header
        t.addCell(GREEN+" ID "+RESET);
        t.addCell(GREEN+" NAME "+RESET);
        t.addCell(GREEN+" COST "+RESET);
        t.addCell(GREEN+" PRICE "+RESET);
        t.addCell(GREEN+" QTY "+RESET);
        t.addCell(GREEN+" CATEGORY "+RESET);
        t.addCell(GREEN+" STATUS "+RESET);

        for (Product p : products) {
            if (!p.getStatus().equalsIgnoreCase("Deleted")) {
                t.addCell(CYAN+" " + p.getId() + " "+RESET);
                t.addCell(CYAN+" " + p.getName() + " "+RESET);
                t.addCell(CYAN+" " + String.format("%.2f", p.getCost()) + " "+RESET);
                t.addCell(CYAN+" " + String.format("%.2f", p.getPrice()) + " "+RESET);

                if (p.getQty() <= 0) {
                    t.addCell(RED+" OUT OF STOCK "+RESET);
                } else {
                    t.addCell(CYAN+" " + p.getQty() + " "+RESET);
                }

                t.addCell(CYAN+" " + p.getCategory() + " "+RESET);
                t.addCell(CYAN+" " + p.getStatus() + " "+RESET);
            }
        }
        println(t.render());
    }
    public static void renderCustomers(List<Customer> customers) {

        if (customers.isEmpty()) {
            printInfo("No customers found.");
            return;
        }

        Table t = new Table(4, BorderStyle.UNICODE_DOUBLE_BOX);

        // Header
        t.addCell(GREEN+" ID "+RESET);
        t.addCell(GREEN+" NAME "+RESET);
        t.addCell(GREEN+" PHONE "+RESET);
        t.addCell(GREEN+" TYPE "+RESET);
        for (Customer c : customers) {
            t.addCell(CYAN+" " + c.getId() + " "+RESET);
            t.addCell(CYAN+" " + c.getName() + " "+RESET);
            t.addCell(CYAN+" " + c.getPhone() + " "+RESET);
            t.addCell(CYAN+" " + c.getType() + " "+RESET);
        }
        println(t.render());
    }
    public static void renderCustinfo(Customer c) {
        if (c == null) return;

        Table t = new Table(2, BorderStyle.UNICODE_DOUBLE_BOX);

        // Header
        t.addCell(YELLOW+" FIELD "+RESET);
        t.addCell(YELLOW+" DATA "+RESET);

        // Data Rows
        t.addCell(GREEN+" ID "+RESET);
        t.addCell(" " + c.getId() + " ");

        t.addCell(GREEN+" Name "+RESET);
        t.addCell(" " + c.getName() + " ");

        t.addCell(GREEN+" Phone "+RESET);
        t.addCell(" " + c.getPhone() + " ");

        t.addCell(GREEN+" Type "+RESET);
        t.addCell(" " + c.getType() + " ");

        println(t.render());
    }
    public static void renderMenu(String title, String[] options) {
        Table t = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX);

        // Title centered
        t.addCell(" " + title + " ", new CellStyle(CellStyle.HorizontalAlign.center));

        // Options
        for (String opt : options) {
            t.addCell(" " + opt + " ");
        }
        println(t.render());
    }

    public static void renderUser(String title, String[] options) {
        Table t = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX);



        println(t.render());
    }
}