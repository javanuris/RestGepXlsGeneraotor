package kazpost.nuris.gep.models;

import java.util.ArrayList;
import java.util.List;

public class Form103XlsSheet {

    private Form103XlsCellHeaderDescription form103XlsCellHeaderDescription;
    private List<Form103XlsCellBodyDescription> form103XlsCellBodyDescription;
    private Form103XlsInfo form103XlsInfo;

    public Form103XlsSheet() {

    }

    public Form103XlsCellHeaderDescription getForm103XlsCellHeaderDescription() {
        return form103XlsCellHeaderDescription;
    }

    public void setForm103XlsCellHeaderDescription(Form103XlsCellHeaderDescription form103XlsCellHeaderDescription) {
        this.form103XlsCellHeaderDescription = form103XlsCellHeaderDescription;
    }

    public List<Form103XlsCellBodyDescription> getForm103XlsCellBodyDescription() {
        return form103XlsCellBodyDescription;
    }

    public void setForm103XlsCellBodyDescription(List<Form103XlsCellBodyDescription> form103XlsCellBodyDescription) {
        this.form103XlsCellBodyDescription = form103XlsCellBodyDescription;
    }

    public Form103XlsInfo getForm103XlsInfo() {
        return form103XlsInfo;
    }

    public void setForm103XlsInfo(Form103XlsInfo form103XlsInfo) {
        this.form103XlsInfo = form103XlsInfo;
    }

    @Override
    public String toString() {
        return "Form103XlsSheet{" +
                "form103XlsCellHeaderDescription=" + form103XlsCellHeaderDescription +
                ", form103XlsCellBodyDescription=" + form103XlsCellBodyDescription +
                ", form103XlsInfo=" + form103XlsInfo +
                '}';
    }
}
