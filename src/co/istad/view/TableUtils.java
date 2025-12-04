package co.istad.view;

import co.istad.entity.Customer;
import co.istad.entity.Product;
import org.nocrala.tools.texttablefmt.*;

import java.util.List;
import static co.istad.utils.PrintUtils.*;

public class TableUtils {

    public static void renderProducts(List<Product> products) {
        if (products.isEmpty()) {
            printInfo("No products found.");
            return;
        }

        Table t = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);

        // Header
        t.addCell(" ID ");
        t.addCell(" NAME ");
        t.addCell(" COST ");
        t.addCell(" PRICE ");
        t.addCell(" QTY ");
        t.addCell(" CATEGORY ");
        t.addCell(" STATUS ");

        for (Product p : products) {
            if (!p.getStatus().equalsIgnoreCase("Deleted")) {
                t.addCell(" " + p.getId() + " ");
                t.addCell(" " + p.getName() + " ");
                t.addCell(" " + String.format("%.2f", p.getCost()) + " ");
                t.addCell(" " + String.format("%.2f", p.getPrice()) + " ");

                if (p.getQty() <= 0) {
                    t.addCell(" OUT OF STOCK ");
                } else {
                    t.addCell(" " + p.getQty() + " ");
                }

                t.addCell(" " + p.getCategory() + " ");
                t.addCell(" " + p.getStatus() + " ");
            }
        }
        println(t.render());
    }
    public static void renderCustomers(List<Customer> customers) {

        if (customers.isEmpty()) {
            printInfo("No customers found.");
            return;
        }

        Table t = new Table(4, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);

        // Header
        t.addCell(" ID ");
        t.addCell(" NAME ");
        t.addCell(" PHONE ");
        t.addCell(" TYPE ");
        for (Customer c : customers) {
            t.addCell(" " + c.getId() + " ");
            t.addCell(" " + c.getName() + " ");
            t.addCell(" " + c.getPhone() + " ");
            t.addCell(" " + c.getType() + " ");
        }
        println(t.render());
    }
    public static void renderCustinfo(Customer c) {
        if (c == null) return;

        Table t = new Table(2, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);

        // Header
        t.addCell(" FIELD ");
        t.addCell(" DATA ");

        // Data Rows
        t.addCell(" ID ");
        t.addCell(" " + c.getId() + " ");

        t.addCell(" Name ");
        t.addCell(" " + c.getName() + " ");

        t.addCell(" Phone ");
        t.addCell(" " + c.getPhone() + " ");

        t.addCell(" Type ");
        t.addCell(" " + c.getType() + " ");

        println(t.render());
    }
    public static void renderMenu(String title, String[] options) {
        Table t = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);

        // Title centered
        t.addCell(" " + title + " ", new CellStyle(CellStyle.HorizontalAlign.center));

        // Options
        for (String opt : options) {
            t.addCell(" " + opt + " ");
        }
        println(t.render());
    }
}