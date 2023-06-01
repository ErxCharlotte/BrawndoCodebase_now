package au.edu.sydney.brawndo.erp.spfea.changeCommand;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;

public class CreateCommand implements ChangeCommand {
    private Order order;

    public CreateCommand(Order order) {
        this.order = order;
    }

    @Override
    public void execute(Order order, AuthToken token) {
        TestDatabase.getInstance().saveOrder(token, order);
    }
}
