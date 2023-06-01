package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.contactBridge.ContactMethod;

import java.util.Arrays;
import java.util.List;

public class ContactHandler {
    private List<ContactMethod> priority;

    public void setContactPriority(List<ContactMethod> priority) {
        this.priority = priority;
    }

    public boolean sendInvoice(AuthToken token, Customer customer, String data) {
        for (ContactMethod method : this.priority) {
            boolean isSend = method.sendInvoice(token, customer, data);
            if (isSend){
                return true;
            }
        }
        return false;
    }

    public static List<String> getKnownMethods() {
        return Arrays.asList(
                "Carrier Pigeon",
                "Email",
                "Mail",
                "Merchandiser",
                "Phone call",
                "SMS"
        );
    }
}
