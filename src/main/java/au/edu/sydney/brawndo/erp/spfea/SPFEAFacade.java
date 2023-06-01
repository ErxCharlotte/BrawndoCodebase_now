package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthModule;
import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Mail;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.changeCommand.CommandInvoker;
import au.edu.sydney.brawndo.erp.spfea.changeCommand.CreateCommand;
import au.edu.sydney.brawndo.erp.spfea.changeCommand.FinaliseCommand;
import au.edu.sydney.brawndo.erp.spfea.changeCommand.UpdateCommand;
import au.edu.sydney.brawndo.erp.spfea.contactBridge.*;
import au.edu.sydney.brawndo.erp.spfea.orderStrategy.*;
import au.edu.sydney.brawndo.erp.spfea.ordering.*;
import au.edu.sydney.brawndo.erp.spfea.products.ProductDatabase;

import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("Duplicates")
public class SPFEAFacade {

    Map<Order, CommandInvoker> invokerMap = new HashMap<>();

    private AuthToken token;

    public boolean login(String userName, String password) {
        token = AuthModule.login(userName, password);

        return null != token;
    }

    public List<Integer> getAllOrders() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();

        List<Order> orders = database.getOrders(token);

        List<Integer> result = new ArrayList<>();

        for (Order order: orders) {
            result.add(order.getOrderID());
        }

        return result;
    }

    public Integer createOrder(int customerID, LocalDateTime date, boolean isBusiness, boolean isSubscription, int discountType, int discountThreshold, int discountRateRaw, int numShipments) {
        if (null == token) {
            throw new SecurityException();
        }

        if (discountRateRaw < 0 || discountRateRaw > 100) {
            throw new IllegalArgumentException("Discount rate not a percentage");
        }

        double discountRate = 1.0 - (discountRateRaw / 100.0);

        Order order;

        if (!TestDatabase.getInstance().getCustomerIDs(token).contains(customerID)) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        int id = TestDatabase.getInstance().getNextOrderID();

        if (isSubscription) {
            if (1 == discountType) { // 1 is flat rate
                    if (isBusiness) {
                        order = new SubscriptionOrderImpl(id, customerID, date, new FlatDiscount(discountRate), new BusinessType(), numShipments);
                    } else {
                        order = new SubscriptionOrderImpl(id, customerID, date, new FlatDiscount(discountRate), new PersonalType(), numShipments);
                    }
                } else if (2 == discountType) { // 2 is bulk discount
                    if (isBusiness) {
                        order = new SubscriptionOrderImpl(id, customerID, date, new BulkDiscount(discountThreshold, discountRate), new BusinessType(), numShipments);
                    } else {
                        order = new SubscriptionOrderImpl(id, customerID, date, new BulkDiscount(discountThreshold, discountRate), new PersonalType(), numShipments);
                    }
            } else {return null;}
        } else {
            if (1 == discountType) {
                if (isBusiness) {
                    order = new OrderImpl(id, customerID, date, new FlatDiscount(discountRate), new BusinessType());
                } else {
                    order = new OrderImpl(id, customerID, date, new FlatDiscount(discountRate), new PersonalType());
                }
            } else if (2 == discountType) {
                if (isBusiness) {
                    order = new OrderImpl(id, customerID, date, new BulkDiscount(discountThreshold, discountRate), new BusinessType());
                } else {
                    order = new OrderImpl(id, customerID, date, new BulkDiscount(discountThreshold, discountRate), new PersonalType());
                }
            } else {return null;}
        }

        this.invokerMap.put(order, new CommandInvoker());
        CommandInvoker invoker = this.invokerMap.get(order);
        invoker.addCommand(new CreateCommand(order));
        invoker.execute(order, token);

        return order.getOrderID();
    }

    public List<Integer> getAllCustomerIDs() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.getCustomerIDs(token);
    }

    public Customer getCustomer(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        return new CustomerImpl(token, id);
    }

    public boolean removeOrder(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.removeOrder(token, id);
    }

    public List<Product> getAllProducts() {
        if (null == token) {
            throw new SecurityException();
        }

        return new ArrayList<>(ProductDatabase.getTestProducts());
    }

    public boolean finaliseOrder(int orderID, List<String> contactPriority) {
        if (null == token) {
            throw new SecurityException();
        }

        List<ContactMethod> contactPriorityAsMethods = new ArrayList<>();

        if (null != contactPriority) {
            for (String method: contactPriority) {
                switch (method.toLowerCase()) {
                    case "merchandiser":
                        contactPriorityAsMethods.add(new MERCHANDISER());
                        break;
                    case "email":
                        contactPriorityAsMethods.add(new EMAIL());
                        break;
                    case "carrier pigeon":
                        contactPriorityAsMethods.add(new CARRIER_PIGEON());
                        break;
                    case "mail":
                        contactPriorityAsMethods.add(new MAIL());
                        break;
                    case "phone call":
                        contactPriorityAsMethods.add(new PHONECALL());
                        break;
                    case "sms":
                        contactPriorityAsMethods.add(new SMS());
                        break;
                    default:
                        break;
                }
            }
        }

        if (contactPriorityAsMethods.size() == 0) { // needs setting to default
            contactPriorityAsMethods = Arrays.asList(
                    new MERCHANDISER(),
                    new EMAIL(),
                    new CARRIER_PIGEON(),
                    new MAIL(),
                    new PHONECALL()
            );
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        CommandInvoker invoker = this.invokerMap.get(order);
        invoker.addCommand(new FinaliseCommand(order));
        invoker.execute(order, token);

        ContactHandler contactHandler = new ContactHandler();
        contactHandler.setContactPriority(contactPriorityAsMethods);
        return contactHandler.sendInvoice(token, getCustomer(order.getCustomer()), order.generateInvoiceData());
    }

    public void logout() {
        AuthModule.logout(token);
        token = null;
    }

    public double getOrderTotalCost(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return 0.0;
        }

        return order.getTotalCost();
    }

    public void orderLineSet(int orderID, Product product, int qty) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            System.out.println("got here");
            return;
        }

        if (invokerMap.get(order) != null){
            CommandInvoker invoker = this.invokerMap.get(order);
            invoker.addCommand(new UpdateCommand(order, product, qty));
        }else{
            this.invokerMap.put(order, new CommandInvoker());
            CommandInvoker invoker = this.invokerMap.get(order);
            invoker.addCommand(new UpdateCommand(order, product, qty));
        }

    }

    public String getOrderLongDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.longDesc();
    }

    public String getOrderShortDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.shortDesc();
    }

    public List<String> getKnownContactMethods() {if (null == token) {
        throw new SecurityException();
    }

        return ContactHandler.getKnownMethods();
    }
}
