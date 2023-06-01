package au.edu.sydney.brawndo.erp.spfea.changeCommand;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Order;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {

    private List<ChangeCommand> commands = new ArrayList<>();

    public void addCommand(ChangeCommand command) {
        commands.add(command);
    }

    public void execute(Order order, AuthToken token) {
        for (int i = 0; i < commands.size(); i ++){
            commands.get(i).execute(order, token);
        }
    }
}
