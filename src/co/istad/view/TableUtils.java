package co.istad.view;

import co.istad.entity.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class TableUtils {

    public static void renderProducts(List<Product> products) {

        Table t = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);

        t.addCell("ID");
        t.addCell("Name");
        t.addCell("Price");
        t.addCell("Qty");
        t.addCell("Category");

        for (Product p : products) {
            t.addCell(String.valueOf(p.getId()));
            t.addCell(p.getName());
            t.addCell(String.valueOf(p.getPrice()));
            t.addCell(String.valueOf(p.getQty()));
            t.addCell(String.valueOf(p.getCategory()));
        }

        System.out.println(t.render());  // Print the table
    }
}

