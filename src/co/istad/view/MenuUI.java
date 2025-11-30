package co.istad.view;
import co.istad.entity.User;
import co.istad.dao.impl.ProductFileDao;
import co.istad.service.AuthService;
import co.istad.service.CartService;
import co.istad.service.ProductService;
import co.istad.dao.ProductDao;

public class MenuUI {
    private final ProductFileDao productFileDao = new ProductFileDao();
    private final ProductService productService = new ProductService();
    private final AuthService authService = new AuthService();
    private final CartService cartService = new CartService(productFileDao);
    public void start() {
        // if (user == null) {
        //                printinfo(Exiting program.....);
        //                System.exit(0);
        //            }
    }
    private void adminMenu() {
    }
    private void stockMenu() {

    }
    private void sellerMenu() {
    }

    public CartService getCartService() {
        return this.cartService;
    }
    
}