package au.edu.sydney.brawndo.erp.spfea.orderStrategy;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.util.Map;

public class BusinessType implements TypeStrategy{
    boolean isBusiness = true;

    @Override
    public boolean isBusiness() {
        return this.isBusiness;
    }

    @Override
    public String generateInvoiceData(Order order, Map<Product, Integer> products) {
        return String.format("Your business account has been charged: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", order.getTotalCost());
    }

    @Override
    public String generateInvoiceDataSubscription(SubscriptionOrder subscriptionOrder, Map<Product, Integer> products) {
        return String.format("Your business account will be charged: $%,.2f each week, with a total overall cost of: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", subscriptionOrder.getRecurringCost(), subscriptionOrder.getTotalCost());
    }
}
