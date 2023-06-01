package au.edu.sydney.brawndo.erp.spfea.contactBridge;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.ordering.Order;

import java.util.List;

public class SMS implements ContactMethod {
    @Override
    public boolean sendInvoice(AuthToken token, Customer customer, String data) {
        String smsPhone = customer.getPhoneNumber();
        if (null != smsPhone) {
            au.edu.sydney.brawndo.erp.contact.SMS.sendInvoice(token, customer.getfName(), customer.getlName(), data, smsPhone);
            return true;
        }
        return false;
    }
}
