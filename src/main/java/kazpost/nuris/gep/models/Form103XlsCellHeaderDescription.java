package kazpost.nuris.gep.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Form103XlsCellHeaderDescription {
    private int direction;
    private int typeRegisteredMail;
    private int categoryRegisteredMail;
    private String sender;
    private int appointmentsRegion;
    private int indexOPSPlace;
    private int allRegisteredMail;
    private String phoneNumberFirstSender;
    private String phoneNumberSecondSender;
    private String senderEmail;

    public Form103XlsCellHeaderDescription(){

    }

    public Form103XlsCellHeaderDescription(int direction, int sortRegisteredMail, int categoryRegisteredMail, String sender, int appointmentsRegion, int indexOPSPlace, int allRegisteredMail, String phoneNumberFirstSender, String phoneNumberSecondSender, String senderEmail) {
        this.direction = direction;
        this.typeRegisteredMail = sortRegisteredMail;
        this.categoryRegisteredMail = categoryRegisteredMail;
        this.sender = sender;
        this.appointmentsRegion = appointmentsRegion;
        this.indexOPSPlace = indexOPSPlace;
        this.allRegisteredMail = allRegisteredMail;
        this.phoneNumberFirstSender = phoneNumberFirstSender;
        this.phoneNumberSecondSender = phoneNumberSecondSender;
        this.senderEmail = senderEmail;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getTypeRegisteredMail() {
        return typeRegisteredMail;
    }

    public void setTypeRegisteredMail(int typeRegisteredMail) {
        this.typeRegisteredMail = typeRegisteredMail;
    }

    public int getCategoryRegisteredMail() {
        return categoryRegisteredMail;
    }

    public void setCategoryRegisteredMail(int categoryRegisteredMail) {
        this.categoryRegisteredMail = categoryRegisteredMail;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getAppointmentsRegion() {
        return appointmentsRegion;
    }

    public void setAppointmentsRegion(int appointmentsRegion) {
        this.appointmentsRegion = appointmentsRegion;
    }

    public int getIndexOPSPlace() {
        return indexOPSPlace;
    }

    public void setIndexOPSPlace(int indexOPSPlace) {
        this.indexOPSPlace = indexOPSPlace;
    }

    public int getAllRegisteredMail() {
        return allRegisteredMail;
    }

    public void setAllRegisteredMail(int allRegisteredMail) {
        this.allRegisteredMail = allRegisteredMail;
    }

    public String getPhoneNumberFirstSender() {
        return phoneNumberFirstSender;
    }

    public void setPhoneNumberFirstSender(String phoneNumberFirstSender) {
        this.phoneNumberFirstSender = phoneNumberFirstSender;
    }

    public String getPhoneNumberSecondSender() {
        return phoneNumberSecondSender;
    }

    public void setPhoneNumberSecondSender(String phoneNumberSecondSender) {
        this.phoneNumberSecondSender = phoneNumberSecondSender;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Override
    public String toString() {
        return "Form103XlsCellHeaderDescription{" +
                "direction=" + direction +
                ", typeRegisteredMail=" + typeRegisteredMail +
                ", categoryRegisteredMail=" + categoryRegisteredMail +
                ", sender='" + sender + '\'' +
                ", appointmentsRegion=" + appointmentsRegion +
                ", indexOPSPlace=" + indexOPSPlace +
                ", allRegisteredMail=" + allRegisteredMail +
                ", phoneNumberFirstSender='" + phoneNumberFirstSender + '\'' +
                ", phoneNumberSecondSender='" + phoneNumberSecondSender + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                '}';
    }
}
