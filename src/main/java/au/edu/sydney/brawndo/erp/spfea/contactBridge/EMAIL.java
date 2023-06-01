package au.edu.sydney.brawndo.erp.spfea.contactBridge;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Email;
import au.edu.sydney.brawndo.erp.ordering.Customer;

import java.util.List;

public class EMAIL implements ContactMethod {
    @Override
    public boolean sendInvoice(AuthToken token, Customer customer, String data) {
        String email = customer.getEmailAddress();
        if (null != email) {
            Email.sendInvoice(token, customer.getfName(), customer.getlName(), data, email);
            return true;
        }
        return false;
    }
}
