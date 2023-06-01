package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class CustomerImpl implements Customer {

    private final int id;
    private String fName = null;
    private String lName = null;
    private String phoneNumber = null;
    private String emailAddress = null;
    private String address = null;
    private String suburb = null;
    private String state = null;
    private String postCode = null;
    private String merchandiser = null;
    private String businessName = null;
    private String pigeonCoopID = null;

    private AuthToken token;

    public CustomerImpl(AuthToken token, int id) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getfName() {
        if (this.fName == null) {
            this.fName = TestDatabase.getInstance().getCustomerField(token, id, "fName");
        }
        return fName;
    }

    @Override
    public String getlName() {
        if (this.lName == null) {
            this.lName = TestDatabase.getInstance().getCustomerField(token, id, "lName");
        }
        return lName;
    }

    @Override
    public String getPhoneNumber() {
        if (this.phoneNumber == null) {
            this.phoneNumber = TestDatabase.getInstance().getCustomerField(token, id, "phoneNumber");
        }
        return phoneNumber;
    }

    @Override
    public String getEmailAddress() {
        if (this.emailAddress == null) {
            this.emailAddress = TestDatabase.getInstance().getCustomerField(token, id, "emailAddress");
        }
        return emailAddress;
    }

    @Override
    public String getAddress() {
        if (this.address == null) {
            this.address = TestDatabase.getInstance().getCustomerField(token, id, "address");
        }
        return address;
    }

    @Override
    public String getSuburb() {
        if (this.suburb == null) {
            this.suburb = TestDatabase.getInstance().getCustomerField(token, id, "suburb");
        }
        return suburb;
    }

    @Override
    public String getState() {
        if (this.state == null) {
            this.state = TestDatabase.getInstance().getCustomerField(token, id, "state");
        }
        return state;
    }

    @Override
    public String getPostCode() {
        if (this.postCode == null) {
            this.postCode = TestDatabase.getInstance().getCustomerField(token, id, "postCode");
        }
        return postCode;
    }

    @Override
    public String getMerchandiser() {
        if (this.merchandiser == null) {
            this.merchandiser = TestDatabase.getInstance().getCustomerField(token, id, "merchandiser");
        }
        return merchandiser;
    }

    @Override
    public String getBusinessName() {
        if (this.businessName == null) {
            this.businessName = TestDatabase.getInstance().getCustomerField(token, id, "businessName");
        }
        return businessName;
    }

    @Override
    public String getPigeonCoopID() {
        if (this.pigeonCoopID == null) {
            this.pigeonCoopID = TestDatabase.getInstance().getCustomerField(token, id, "pigeonCoopID");
        }
        return pigeonCoopID;
    }
}

