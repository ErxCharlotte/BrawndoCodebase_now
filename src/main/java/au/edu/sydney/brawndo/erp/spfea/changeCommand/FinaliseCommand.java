package au.edu.sydney.brawndo.erp.spfea.changeCommand;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;

public class FinaliseCommand implements ChangeCommand {

    private Order order;

    public FinaliseCommand(Order order) {
        this.order = order;
        order.finalise();
    }

    @Override
    public void execute(Order order, AuthToken token) {
        TestDatabase.getInstance().saveOrder(token, order);
    }
}
