package api.pojo;

public class Herokuapp_POJO1_Booking {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private Herokuapp_POJO2_BookingDates bookingdates;
    private String additionalneeds;

    public Herokuapp_POJO1_Booking(String firstname,String lastname, int totalprice,boolean depositpaid,
                                   Herokuapp_POJO2_BookingDates bookingdates,String additionalneeds){
        this.firstname=firstname;
        this.lastname=lastname;
        this.totalprice=totalprice;
        this.depositpaid=depositpaid;
        this.bookingdates=bookingdates;
        this.additionalneeds=additionalneeds;
    }


    public boolean isDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public Herokuapp_POJO2_BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(Herokuapp_POJO2_BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }


}
