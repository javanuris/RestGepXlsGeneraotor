package kazpost.nuris.gep.services;

import kazpost.nuris.gep.models.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by User on 07.10.2017.
 */
@Service
public class Form103ConvertToXlsService {

    Logger log = LoggerFactory.getLogger(Form103ConvertToXlsService.class);


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

    public void convertForm103Xls(Form103Path path) {

        downloadFile(path);

        Form103XlsCellHeaderDescription header = new Form103XlsCellHeaderDescription();
        List<Form103XlsCellBodyDescription> bodyList = new ArrayList<>();
        Form103XlsSheet form103XlsSheet = new Form103XlsSheet();

        InputStream excelFileToRead = null;
        try {
            excelFileToRead = new FileInputStream(path.getFileNameBefore() + XLS_FILE_FORMAT);
        } catch (FileNotFoundException e) {
            log.error("File not uploaded ", e);
        }

        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(excelFileToRead);
        } catch (IOException e) {
            log.error("Error ", e);
        }

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

        Form103ConvertToXlsService converterService = new Form103ConvertToXlsService();
        try {
            converterService.generateForm103XlsFile(form103XlsSheet, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        uploadFile(path);
    }

    private void generateForm103XlsFile(Form103XlsSheet form103XlsSheet, Form103Path path) throws Exception {
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
            out = new FileOutputStream(new File(path.getFileNameAfter() + XLS_FILE_FORMAT));
            workbook.write(out);
        } catch (FileNotFoundException e) {
            log.error("The flow is not closed! ", e);
        }
        try {
            out.close();
            workbook.close();
        } catch (IOException e) {
            log.error("The flow is not closed! ", e);
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

    private void uploadFile(Form103Path path) {
        File file = null;
        FileInputStream uploadFile = null;
        FTPClient ftpClient = new FTPClient();

        String user = "gepadmin";
        String password = "6Wh6gzLX";
        String ftpServer = "172.30.75.125";

        try {

            ftpClient.connect(ftpServer, FTP_PORT);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            file = new File(path.getFileNameAfter() + XLS_FILE_FORMAT);
            uploadFile = new FileInputStream(file);

            String pathAndNameUploadFile = path.getToPath() + "/" + path.getFileNameAfter() + XLS_FILE_FORMAT;

            boolean done = ftpClient.storeFile(pathAndNameUploadFile, uploadFile);
            if (done) {
                log.info("File downloaded!" + path.getToPath() + "/" + path.getFileNameAfter() + XLS_FILE_FORMAT);

            } else {
                log.error("The file is NOT loaded! " + path.getToPath() + "/" + path.getFileNameAfter() + XLS_FILE_FORMAT);

            }

        } catch (IOException e) {
           log.error("Error when closing IO connections " , e);
        } finally {
            try {
                uploadFile.close();
                file.delete();
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error("Error when closing FTP connections ", e);
            }
        }
    }

    private void downloadFile(Form103Path path) {
        FTPClient ftpClient = new FTPClient();

        String user = "gepadmin";
        String password = "6Wh6gzLX";
        String ftpServer = "172.30.75.125";

        try {
            ftpClient.connect(ftpServer, FTP_PORT);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File downloadFile1 = new File(path.getFileNameBefore() + XLS_FILE_FORMAT);
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(path.getFromPath() + "/" + path.getFileNameBefore() + XLS_FILE_FORMAT, outputStream1);
            outputStream1.close();

            if (success) {
                log.info( "The file was successfully unloaded! " + path.getFromPath() + "/" + path.getFileNameBefore() + XLS_FILE_FORMAT);

            }
        } catch (IOException e) {
            log.error("Error when closing FTP connections ", e);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error("Error when closing FTP connections ", e);
            }
        }

    }
}


