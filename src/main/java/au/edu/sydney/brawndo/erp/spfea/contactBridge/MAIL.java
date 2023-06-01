package au.edu.sydney.brawndo.erp.spfea.contactBridge;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Mail;
import au.edu.sydney.brawndo.erp.ordering.Customer;

import java.util.List;

public class MAIL implements ContactMethod {
    @Override
    public boolean sendInvoice(AuthToken token, Customer customer, String data) {
        String address = customer.getAddress();
        String suburb = customer.getSuburb();
        String state = customer.getState();
        String postcode = customer.getPostCode();
        if (null != address && null != suburb &&
                null != state && null != postcode) {
            Mail.sendInvoice(token, customer.getfName(), customer.getlName(), data, address, suburb, state, postcode);
            return true;
        }
        return false;
    }
}
