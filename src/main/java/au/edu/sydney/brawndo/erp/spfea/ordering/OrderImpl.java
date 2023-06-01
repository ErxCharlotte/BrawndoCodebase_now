package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.orderStrategy.DiscountStrategy;
import au.edu.sydney.brawndo.erp.spfea.orderStrategy.TypeStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderImpl implements Order {
    private Map<Product, Integer> products = new HashMap<>();
    private final int id;
    private LocalDateTime date;
    private int customerID;
    private double discountRate;
    private int discountThreshold;
    private boolean finalised = false;
    private DiscountStrategy discountStrategy;
    private TypeStrategy orderType;

    public OrderImpl(int id, int customerID, LocalDateTime date, DiscountStrategy discountStrategy, TypeStrategy orderType) {
        this.id = id;
        this.customerID = customerID;
        this.date = date;
        this.discountStrategy = discountStrategy;
        this.orderType = orderType;
        this.discountThreshold = discountStrategy.getDiscountThreshold();
        this.discountRate = discountStrategy.getDiscountRate();
    }

    @Override
    public int getOrderID() {
        return id;
    }

    @Override
    public double getTotalCost() {
        return this.discountStrategy.discountCalculate(this.products);
    }

    @Override
    public LocalDateTime getOrderDate() {
        return date;
    }

    @Override
    public void setProduct(Product product, int qty) {
        if (finalised) throw new IllegalStateException("Order was already finalised.");

        // We can't rely on like products having the same object identity since they get
        // rebuilt over the network, so we had to check for presence and same values

        for (Product contained: products.keySet()) {
            if (contained.equals(product)) {
                product = contained;
                break;
            }
        }

        products.put(product, qty);
    }

    @Override
    public Set<Product> getAllProducts() {
        return products.keySet();
    }

    @Override
    public int getProductQty(Product product) {
        for (Product contained: products.keySet()) {
            if (contained.equals(product)) {
                product = contained;
                break;
            }
        }
        Integer result = products.get(product);
        return null == result ? 0 : result;
    }

    @Override
    public String generateInvoiceData() {
        return this.orderType.generateInvoiceData(this, this.getProducts());
    }

    @Override
    public int getCustomer() {
        return customerID;
    }

    @Override
    public void finalise() {
        this.finalised = true;
    }

    @Override
    public Order copy() {
        Order copy = new OrderImpl(id, customerID, date, discountStrategy, orderType);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f", id, getTotalCost());
    }

    @Override
    public String longDesc() {
        double fullCost = 0.0;
        double discountedCost = getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(products.keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * products.get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    products.get(product),
                    product.getCost(),
                    subtotal));
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost
        );
    }

    public int getDiscountThreshold() {
        return discountThreshold;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public TypeStrategy getOrderType() {
        return orderType;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public boolean isFinalised() {
        return finalised;
    }
}
