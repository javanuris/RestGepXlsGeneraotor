package kazpost.nuris.gep.services;

import kazpost.nuris.gep.models.Form103XlsCellBodyDescription;
import kazpost.nuris.gep.models.Form103XlsCellHeaderDescription;
import kazpost.nuris.gep.models.Form103XlsSheet;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by User on 07.10.2017.
 */
public class Form103ConvertorService {
    private static final String XLS_FILE_FORMAT = ".xls";
    private static final String SHEET_NAME = "Form103";
    private static final int FTP_PORT = 21;

    /**
     * C 8 строки ничанаеться основная, "Body" часть, куда необходима класть данные.
     */
    private static final int BODY_ROW_ORDER = 8;
    /**
     * Количество столбцов в Excel таблице.
     */
    private static final int TOTAL_COLUMN_COUNT = 12;

    public static void main(String[] args) throws IOException {

        Form103XlsCellHeaderDescription header = new Form103XlsCellHeaderDescription();
        List<Form103XlsCellBodyDescription> bodyList = new ArrayList<Form103XlsCellBodyDescription>();
        Form103XlsSheet form103XlsSheet = new Form103XlsSheet();

        InputStream excelFileToRead = new FileInputStream("test.xls");

        XSSFWorkbook wb = new XSSFWorkbook(excelFileToRead);

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        Iterator rows = sheet.rowIterator();

        row = (XSSFRow) rows.next();
        header.setDirection(row.getCell(1).getStringCellValue());

        row = (XSSFRow) rows.next();
        header.setTypeRegisteredMail(row.getCell(1).getStringCellValue());

        row = (XSSFRow) rows.next();
        header.setCategoryRegisteredMail(row.getCell(1).getStringCellValue());

        row = (XSSFRow) rows.next();
        header.setSender(row.getCell(1).getStringCellValue());

        if (row.getCell(3) != null) {
            header.setPhoneNumberFirstSender(row.getCell(3).getStringCellValue());
        }

        row = (XSSFRow) rows.next();
        header.setAppointmentsRegion(row.getCell(1).getStringCellValue());


        if (row.getCell(3) != null) {
            header.setPhoneNumberSecondSender(row.getCell(3).getStringCellValue());
        }

        row = (XSSFRow) rows.next();
        header.setIndexOPSPlace(row.getCell(1).getStringCellValue());


        if (row.getCell(3) != null) {
            header.setSenderEmail(row.getCell(3).getStringCellValue());
        }

        row = (XSSFRow) rows.next();
        header.setAllRegisteredMail(row.getCell(1).getColumnIndex());

        rows.next();

        while (rows.hasNext()) {
            Form103XlsCellBodyDescription body = new Form103XlsCellBodyDescription();

            row = (XSSFRow) rows.next();

            body.setRecipientId((int) row.getCell(0).getNumericCellValue());
            body.setAddressee(row.getCell(1).getStringCellValue());
            body.setIndexOPSPlace(row.getCell(2).getStringCellValue());
            body.setDestinationAddress(row.getCell(3).getStringCellValue());
            body.setBarcode(row.getCell(4).getStringCellValue());
            body.setWeight(row.getCell(5).getStringCellValue());

            bodyList.add(body);
        }

        form103XlsSheet.setForm103XlsCellHeaderDescription(header);
        form103XlsSheet.setForm103XlsCellBodyDescription(bodyList);

        System.out.println(form103XlsSheet);

        Form103ConvertorService converterService = new Form103ConvertorService();
        try {
            converterService.generateForm103XlsFile(form103XlsSheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createXlsBodyPart(HSSFWorkbook workbook, HSSFSheet sheet) {

        Row rowBodyTableDescription = sheet.createRow(7);
        rowBodyTableDescription.createCell(0).setCellValue("№ п.п.");
        rowBodyTableDescription.createCell(1).setCellValue("Адресат");
        rowBodyTableDescription.createCell(2).setCellValue("Индекс ОПС места назн.");
        rowBodyTableDescription.createCell(3).setCellValue("Адрес места назначения");
        rowBodyTableDescription.createCell(4).setCellValue("ШПИ");
        rowBodyTableDescription.createCell(5).setCellValue("Вес (кг.)");
        rowBodyTableDescription.createCell(6).setCellValue("Сумма объявленной ценности");
        rowBodyTableDescription.createCell(7).setCellValue("Сумма нал. платежа");
        rowBodyTableDescription.createCell(8).setCellValue("Особые отметки");
        rowBodyTableDescription.createCell(9).setCellValue("Сотовый номер №1");
        rowBodyTableDescription.createCell(10).setCellValue("Сотовый номер №2");
        rowBodyTableDescription.createCell(11).setCellValue("E-mail");

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        rowBodyTableDescription.setRowStyle(style);

        for (int i = 0; i < TOTAL_COLUMN_COUNT; i++) {
            rowBodyTableDescription.getCell(i).setCellStyle(style);
        }

    }

    private void createXlsHeaderPart(Form103XlsSheet form103XlsSheet, HSSFSheet sheet) {

        Row rowDirection = sheet.createRow(0);
        rowDirection.createCell(0).setCellValue("Направление");
        rowDirection.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getDirection());

        Row rowTypeRegisterMail = sheet.createRow(1);
        rowTypeRegisterMail.createCell(0).setCellValue("Вид РПО");
        rowTypeRegisterMail.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getTypeRegisteredMail());

        Row rowCategoryRegisterMail = sheet.createRow(2);
        rowCategoryRegisterMail.createCell(0).setCellValue("Категория РПО");
        rowCategoryRegisterMail.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getCategoryRegisteredMail());

        Row rowSender = sheet.createRow(3);
        rowSender.createCell(0).setCellValue("Отправитель");
        rowSender.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getSender());

        rowSender.createCell(2).setCellValue("Сотовый номер №1 отпр");
        rowSender.createCell(3).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getPhoneNumberFirstSender());

        Row rowAppointmentsRegion = sheet.createRow(4);
        rowAppointmentsRegion.createCell(0).setCellValue("Регион назначения");
        rowAppointmentsRegion.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getAppointmentsRegion());

        rowAppointmentsRegion.createCell(2).setCellValue("Сотовый номер №2 отпр.");
        rowAppointmentsRegion.createCell(3).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getPhoneNumberSecondSender());

        Row rowIndexOPSPlace = sheet.createRow(5);
        rowIndexOPSPlace.createCell(0).setCellValue("Индекс места ОПС приема");
        rowIndexOPSPlace.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getIndexOPSPlace());

        rowIndexOPSPlace.createCell(2).setCellValue("e-mail отпр.");
        rowIndexOPSPlace.createCell(3).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getSenderEmail());

        Row rowAllRegisteredMail = sheet.createRow(6);
        rowAllRegisteredMail.createCell(0).setCellValue("Всего РПО");
        form103XlsSheet.getForm103XlsCellHeaderDescription().setAllRegisteredMail(form103XlsSheet.getForm103XlsCellBodyDescription().size());
        rowAllRegisteredMail.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getAllRegisteredMail());
    }

    public void generateForm103XlsFile(Form103XlsSheet form103XlsSheet) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(SHEET_NAME);

        createXlsHeaderPart(form103XlsSheet, sheet);
        createXlsBodyPart(workbook, sheet);

        int rowOrder = BODY_ROW_ORDER;
        for (Form103XlsCellBodyDescription f : form103XlsSheet.getForm103XlsCellBodyDescription()) {

            Row row = sheet.createRow(rowOrder);

            row.createCell(0).setCellValue(f.getRecipientId());
            row.createCell(1).setCellValue(f.getAddressee());
            row.createCell(2).setCellValue(f.getIndexOPSPlace());
            row.createCell(3).setCellValue(f.getDestinationAddress());
            row.createCell(4).setCellValue(f.getBarcode());
            row.createCell(5).setCellValue(f.getWeight());
            row.createCell(6).setCellValue(f.getAmountDeclaredValue());
            row.createCell(7).setCellValue(f.getAmountTaxPayment());
            row.createCell(8).setCellValue(f.getSpecialNotes());
            row.createCell(9).setCellValue(f.getPhoneNumberFirst());
            row.createCell(10).setCellValue(f.getPhoneNumberSecond());
            row.createCell(11).setCellValue(f.getEmail());

            rowOrder++;
        }

        for (int i = 0; i < TOTAL_COLUMN_COUNT; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("testAfter" + XLS_FILE_FORMAT));
            workbook.write(out);


        } catch (FileNotFoundException e) {
        }
        try {
            out.close();
            workbook.close();
        } catch (IOException e) {
        }


    }
}

