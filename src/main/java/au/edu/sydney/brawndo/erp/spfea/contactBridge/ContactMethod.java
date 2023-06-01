package au.edu.sydney.brawndo.erp.spfea.contactBridge;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;

import java.util.List;

public interface ContactMethod {
    boolean sendInvoice(AuthToken token, Customer customer, String data);
}
