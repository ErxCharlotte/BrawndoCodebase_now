package au.edu.sydney.brawndo.erp.spfea.contactBridge;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.CarrierPigeon;
import au.edu.sydney.brawndo.erp.ordering.Customer;

import java.util.List;

public class CARRIER_PIGEON implements ContactMethod {

    @Override
    public boolean sendInvoice(AuthToken token, Customer customer, String data) {
        String pigeonCoopID = customer.getPigeonCoopID();
        if (null != pigeonCoopID) {
            CarrierPigeon.sendInvoice(token, customer.getfName(), customer.getlName(), data, pigeonCoopID);
            return true;
        }
        return false;
    }
}
