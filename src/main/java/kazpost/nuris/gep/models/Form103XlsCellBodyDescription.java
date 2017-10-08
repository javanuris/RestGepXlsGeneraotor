package kazpost.nuris.gep.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Form103XlsCellBodyDescription {
    private int recipientId;
    private String addressee;
    private String indexOPSPlace;
    private String destinationAddress;
    private String barcode;
    private String weight;
    private int amountDeclaredValue;
    private int amountTaxPayment;
    private String specialNotes;
    private String phoneNumberFirst;
    private String phoneNumberSecond;
    private String email;

    public Form103XlsCellBodyDescription(){

    }

    public Form103XlsCellBodyDescription(int recipientId, String addressee, String indexOPSPlace, String destinationAddress, String barcode, String weight, int amountDeclaredValue, int amountTaxPayment, String specialNotes, String phoneNumberFirst, String phoneNumberSecond, String email) {
        this.recipientId = recipientId;
        this.addressee = addressee;
        this.indexOPSPlace = indexOPSPlace;
        this.destinationAddress = destinationAddress;
        this.barcode = barcode;
        this.weight = weight;
        this.amountDeclaredValue = amountDeclaredValue;
        this.amountTaxPayment = amountTaxPayment;
        this.specialNotes = specialNotes;
        this.phoneNumberFirst = phoneNumberFirst;
        this.phoneNumberSecond = phoneNumberSecond;
        this.email = email;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getIndexOPSPlace() {
        return indexOPSPlace;
    }

    public void setIndexOPSPlace(String indexOPSPlace) {
        this.indexOPSPlace = indexOPSPlace;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getAmountDeclaredValue() {
        return amountDeclaredValue;
    }

    public void setAmountDeclaredValue(int amountDeclaredValue) {
        this.amountDeclaredValue = amountDeclaredValue;
    }

    public int getAmountTaxPayment() {
        return amountTaxPayment;
    }

    public void setAmountTaxPayment(int amountTaxPayment) {
        this.amountTaxPayment = amountTaxPayment;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public String getPhoneNumberFirst() {
        return phoneNumberFirst;
    }

    public void setPhoneNumberFirst(String phoneNumberFirst) {
        this.phoneNumberFirst = phoneNumberFirst;
    }

    public String getPhoneNumberSecond() {
        return phoneNumberSecond;
    }

    public void setPhoneNumberSecond(String phoneNumberSecond) {
        this.phoneNumberSecond = phoneNumberSecond;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Form103XlsCellBodyDescription{" +
                "recipientId=" + recipientId +
                ", addressee='" + addressee + '\'' +
                ", indexOPSPlace=" + indexOPSPlace +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", barcode='" + barcode + '\'' +
                ", weight=" + weight +
                ", amountDeclaredValue=" + amountDeclaredValue +
                ", amountTaxPayment=" + amountTaxPayment +
                ", specialNotes='" + specialNotes + '\'' +
                ", phoneNumberFirst='" + phoneNumberFirst + '\'' +
                ", phoneNumberSecond='" + phoneNumberSecond + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
