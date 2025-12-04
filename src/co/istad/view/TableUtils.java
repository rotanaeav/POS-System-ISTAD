package co.istad.view;

import co.istad.entity.Customer;
import co.istad.entity.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

import static co.istad.utils.PrintUtils.printInfo;
import static co.istad.utils.PrintUtils.println;

public class TableUtils{

    public static void renderProducts(List <Product> products){
        if (products.isEmpty()){
            printInfo("Product not found");
            return;
        }

        Table table = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);

        table.addCell("ID");
        table.addCell("NAME");
        table.addCell("COST");
        table.addCell("PRICE");
        table.addCell("QTY");
        table.addCell("CATEGORY");
        table.addCell("STATUS");

        for (Product product : products) {
            if (!product.getStatus().equalsIgnoreCase("Deleted")) {
                table.addCell(" " + product.getId() + " ");
                table.addCell(" " + product.getName() + " ");
                table.addCell(" " + String.format("%.2f", product.getCost()) + " ");
                table.addCell(" " + String.format("%.2f", product.getPrice()) + " ");

                if (product.getQty() <= 0) {
                    table.addCell(" OUT OF STOCK ");
                } else {
                    table.addCell(" " + product.getQty() + " ");
                }

                table.addCell(" " + product.getCategory() + " ");
                table.addCell(" " + product.getStatus() + " ");
            }
        }
        println(table.render());
    }

    public static void renderCustomers(List<Customer> customers) {
        if (customers.isEmpty()) {
            printInfo("No customers found.");
            return;
        }

        Table t = new Table(4, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);

        t.addCell(" ID ");
        t.addCell(" NAME ");
        t.addCell(" PHONE ");
        t.addCell(" TYPE ");

        for (Customer customer : customers) {
            t.addCell(" " + customer.getId() + " ");
            t.addCell(" " + customer.getName() + " ");
            t.addCell(" " + customer.getPhone() + " ");
            t.addCell(" " + customer.getType() + " ");
        }
        println(t.render());
    }

    public static void renderCustomerInfo(Customer customer) {
        if (customer == null) return;

        Table table = new Table(2, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);

        table.addCell(" FIELD ");
        table.addCell(" DATA ");

        table.addCell(" ID ");
        table.addCell(" " + customer.getId() + " ");

        table.addCell(" Name ");
        table.addCell(" " + customer.getName() + " ");

        table.addCell(" Phone ");
        table.addCell(" " + customer.getPhone() + " ");

        table.addCell(" Type ");
        table.addCell(" " + customer.getType() + " ");

        println(table.render());
    }

    public static void renderMenu(String title, String[] options) {
        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);

        table.addCell(" " + title + " ", new CellStyle(CellStyle.HorizontalAlign.center));

        for (String opt : options) {
            table.addCell(" " + opt + " ");
        }
        println(table.render());
    }
}
