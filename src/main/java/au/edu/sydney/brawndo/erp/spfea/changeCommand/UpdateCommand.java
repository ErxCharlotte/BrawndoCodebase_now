package au.edu.sydney.brawndo.erp.spfea.changeCommand;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

public class UpdateCommand implements ChangeCommand {

    private Order order;

    public UpdateCommand(Order order, Product product, int qty) {
        this.order = order;
        order.setProduct(product, qty);
    }

    @Override
    public void execute(Order order, AuthToken token) {
        TestDatabase.getInstance().saveOrder(token, order);
    }
}
