package kazpost.nuris.gep.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
public class Form103Model {

    public Form103XlsSheet getSheet() {
        Form103XlsSheet form103XlsSheet = new Form103XlsSheet();

        Form103XlsInfo form103XlsInfo = new Form103XlsInfo();
        form103XlsInfo.setFileName("form103");
        form103XlsInfo.setFilePath("/test");
        form103XlsInfo.setFtpUser("gepadmin");
        form103XlsInfo.setFtpPassword("6Wh6gzLX");
        form103XlsInfo.setFtpAddress("172.30.75.125");

        form103XlsSheet.setForm103XlsInfo(form103XlsInfo);

        Form103XlsCellHeaderDescription headerDescription = new Form103XlsCellHeaderDescription(
                1,
                2,
                1,
                "ВЕРХОВНЫЙ СУД РК",
                16,
                160000,
                165,
                "874751399203",
                "87016547899",
                "somebody@gmail.com"
        );
        form103XlsSheet.setForm103XlsCellHeaderDescription(headerDescription);

        List<Form103XlsCellBodyDescription> descriptions = new ArrayList<Form103XlsCellBodyDescription>();

        Form103XlsCellBodyDescription bodyDescription1 = new Form103XlsCellBodyDescription(
                1,
                "КАПБАРОВА ХАТИРА ЖАРЫЛКАСЫНОВНА",
                160002,
                "МКР.САУЛЕ НОВОСТРОЙКИ 29, -,Абайский район,Шымкент Г.А./Южно-Казахстанская область,160002",
                "WW123321123321",
                0.020,
                0,
                0,
                "non",
                "87475132264",
                "87412323684",
                "kalenov.n@gmail.com"
        );

        Form103XlsCellBodyDescription bodyDescription2 = new Form103XlsCellBodyDescription(
                2,
                "КАПБАРОВА ХАТИРА ЖАРЫЛКАСЫНОВНА",
                160002,
                "МКР.САУЛЕ НОВОСТРОЙКИ 29, -,Абайский район,Шымкент Г.А./Южно-Казахстанская область,160002",
                "WW123321123321",
                0.020,
                0,
                0,
                "non",
                "87475132264",
                "87412323684",
                "kalenov.n@gmail.com"

        );

        Form103XlsCellBodyDescription bodyDescription3 = new Form103XlsCellBodyDescription(
                3,
                "КАПБАРОВА ХАТИРА ЖАРЫЛКАСЫНОВНА",
                160002,
                "МКР.САУЛЕ НОВОСТРОЙКИ 29, -,Абайский район,Шымкент Г.А./Южно-Казахстанская область,160002",
                "WW123321123321",
                0.020,
                0,
                0,
                "non",
                "87475132264",
                "87412323684",
                "kalenov.n@gmail.com"

        );
        Form103XlsCellBodyDescription bodyDescription4 = new Form103XlsCellBodyDescription(
                4,
                "КАПБАРОВА ХАТИРА ЖАРЫЛКАСЫНОВНА",
                160002,
                "МКР.САУЛЕ НОВОСТРОЙКИ 29, -,Абайский район,Шымкент Г.А./Южно-Казахстанская область,160002",
                "WW123321123321",
                0.020,
                0,
                0,
                "non",
                "87475132264",
                "87412323684",
                "kalenov.n@gmail.com"

        );
        descriptions.add(bodyDescription1);
        descriptions.add(bodyDescription2);
        descriptions.add(bodyDescription3);
        descriptions.add(bodyDescription4);

        form103XlsSheet.setForm103XlsCellBodyDescription(descriptions);

        return form103XlsSheet;
    }
}
