package au.edu.sydney.brawndo.erp.spfea.orderStrategy;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.util.Map;

public interface TypeStrategy {
    boolean isBusiness();
    String generateInvoiceData(Order order, Map<Product, Integer> products);

    String generateInvoiceDataSubscription(SubscriptionOrder subscriptionOrder, Map<Product, Integer> products);
}
