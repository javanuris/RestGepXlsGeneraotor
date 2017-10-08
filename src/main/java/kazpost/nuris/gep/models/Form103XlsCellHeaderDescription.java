package kazpost.nuris.gep.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Form103XlsCellHeaderDescription {
    private String direction;
    private String typeRegisteredMail;
    private String categoryRegisteredMail;
    private String sender;
    private String appointmentsRegion;
    private String indexOPSPlace;
    private int allRegisteredMail;
    private String phoneNumberFirstSender;
    private String phoneNumberSecondSender;
    private String senderEmail;

    public Form103XlsCellHeaderDescription(){

    }

    public Form103XlsCellHeaderDescription(String direction, String typeRegisteredMail, String categoryRegisteredMail, String sender, String appointmentsRegion, String indexOPSPlace, int allRegisteredMail, String phoneNumberFirstSender, String phoneNumberSecondSender, String senderEmail) {
        this.direction = direction;
        this.typeRegisteredMail = typeRegisteredMail;
        this.categoryRegisteredMail = categoryRegisteredMail;
        this.sender = sender;
        this.appointmentsRegion = appointmentsRegion;
        this.indexOPSPlace = indexOPSPlace;
        this.allRegisteredMail = allRegisteredMail;
        this.phoneNumberFirstSender = phoneNumberFirstSender;
        this.phoneNumberSecondSender = phoneNumberSecondSender;
        this.senderEmail = senderEmail;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTypeRegisteredMail() {
        return typeRegisteredMail;
    }

    public void setTypeRegisteredMail(String typeRegisteredMail) {
        this.typeRegisteredMail = typeRegisteredMail;
    }

    public String getCategoryRegisteredMail() {
        return categoryRegisteredMail;
    }

    public void setCategoryRegisteredMail(String categoryRegisteredMail) {
        this.categoryRegisteredMail = categoryRegisteredMail;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAppointmentsRegion() {
        return appointmentsRegion;
    }

    public void setAppointmentsRegion(String appointmentsRegion) {
        this.appointmentsRegion = appointmentsRegion;
    }

    public String getIndexOPSPlace() {
        return indexOPSPlace;
    }

    public void setIndexOPSPlace(String indexOPSPlace) {
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
