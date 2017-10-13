package kazpost.nuris.gep.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Form103XlsSheet {

    private Form103XlsCellHeaderDescription form103XlsCellHeaderDescription;
    private List<Form103XlsCellBodyDescription> form103XlsCellBodyDescription;

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


    @Override
    public String toString() {
        return "Form103XlsSheet{" +
                "form103XlsCellHeaderDescription=" + form103XlsCellHeaderDescription +
                ", form103XlsCellBodyDescription=" + form103XlsCellBodyDescription +
                '}';
    }
}
