package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;
import au.edu.sydney.brawndo.erp.spfea.orderStrategy.DiscountStrategy;
import au.edu.sydney.brawndo.erp.spfea.orderStrategy.TypeStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SubscriptionOrderImpl extends OrderImpl implements SubscriptionOrder {
    private int numShipments;

    public SubscriptionOrderImpl(int id, int customerID, LocalDateTime date, DiscountStrategy discountStrategy, TypeStrategy orderType, int numShipments) {
        super(id, customerID, date, discountStrategy, orderType);
        this.numShipments = numShipments;
    }

    @Override
    public double getRecurringCost() {
        return super.getTotalCost();
    }

    @Override
    public int numberOfShipmentsOrdered() {
        return numShipments;
    }

    @Override
    public double getTotalCost() {
        return super.getTotalCost() * numShipments;
    }

    @Override
    public String generateInvoiceData() {
        return this.getOrderType().generateInvoiceDataSubscription(this, this.getProducts());
    }

    @Override
    public Order copy() {
        Map<Product, Integer> products = super.getProducts();

        Order copy = new SubscriptionOrderImpl(super.getOrderID(), super.getCustomer(), super.getOrderDate(), super.getDiscountStrategy(), super.getOrderType(), numShipments);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f per shipment, $%,.2f total", super.getOrderID(), getRecurringCost(), super.getTotalCost());
    }

    @Override
    public String longDesc() {
        double fullCost = 0.0;
        double discountedCost = super.getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(super.getProducts().keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * super.getProducts().get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    super.getProducts().get(product),
                    product.getCost(),
                    subtotal));
        }

        return String.format(super.isFinalised() ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of shipments: %d\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                super.getOrderID(),
                super.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                numShipments,
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost,
                getTotalCost()
        );
    }
}