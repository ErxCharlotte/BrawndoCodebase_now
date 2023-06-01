package au.edu.sydney.brawndo.erp.spfea.changeCommand;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Order;

public interface ChangeCommand {
    void execute(Order order, AuthToken token);
}
