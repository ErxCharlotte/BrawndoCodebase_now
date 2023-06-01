package au.edu.sydney.brawndo.erp.spfea.contactBridge;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.PhoneCall;
import au.edu.sydney.brawndo.erp.ordering.Customer;

import java.util.List;

public class PHONECALL implements ContactMethod {
    @Override
    public boolean sendInvoice(AuthToken token, Customer customer, String data) {
        String phone = customer.getPhoneNumber();
        if (null != phone) {
            PhoneCall.sendInvoice(token, customer.getfName(), customer.getlName(), data, phone);
            return true;
        }
        return false;
    }
}
