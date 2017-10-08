package kazpost.nuris.gep.services;

import kazpost.nuris.gep.models.Form103XlsCellBodyDescription;
import kazpost.nuris.gep.models.Form103XlsInfo;
import kazpost.nuris.gep.models.Form103XlsSheet;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Класс служит для генераций XLS файлов и сохранений их на FTP сервере.
 *
 * @version 1.0
 * @autor Nurislam Kalenov
 */

@Service
public class Form103XlsService {

    Logger log = LoggerFactory.getLogger(Form103XlsService.class);
    /**
     * Формат в котором необходимо сохранять Excel файлы.
     */
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

    @Autowired
    private HSSFWorkbook workbook;

    public void generateForm103XlsFile(Form103XlsSheet form103XlsSheet) throws Exception {
        workbook = new HSSFWorkbook();
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
            out = new FileOutputStream(new File(form103XlsSheet.getForm103XlsInfo().getFileName() + XLS_FILE_FORMAT));
            workbook.write(out);


        } catch (FileNotFoundException e) {
            log.error("Файл не найден в системе.(При генераций файла, файл временно создается локально, далее отправляеться в FTP и удаляеться с локали)", e);
        }
        try {
            out.close();
            workbook.close();
        } catch (IOException e) {
            log.error("Ошибка при закрытий Stream", e);
        }

        uploadFile(form103XlsSheet.getForm103XlsInfo());

    }

    public void createXlsBodyPart(HSSFWorkbook workbook, HSSFSheet sheet) {

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

    public void createXlsHeaderPart(Form103XlsSheet form103XlsSheet, HSSFSheet sheet) {

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

    private void uploadFile(Form103XlsInfo form103XlsInfo) {
        File file = null;
        FileInputStream uploadFile = null;
        FTPClient ftpClient = new FTPClient();

        String user = form103XlsInfo.getFtpUser();
        String password = form103XlsInfo.getFtpPassword();
        String ftpServer = form103XlsInfo.getFtpAddress();

        try {

            ftpClient.connect(ftpServer, FTP_PORT);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            file = new File(form103XlsInfo.getFileName() + XLS_FILE_FORMAT);
            uploadFile = new FileInputStream(file);

            String pathAndNameUploadFile = form103XlsInfo.getFilePath() + "/" + form103XlsInfo.getFileName() + XLS_FILE_FORMAT;

            boolean done = ftpClient.storeFile(pathAndNameUploadFile, uploadFile);
            if (done) {
                log.info("Файл " + pathAndNameUploadFile + " загружен.");
            } else {
                log.error("Файл " + pathAndNameUploadFile + " не загружен.");
            }

        } catch (IOException e) {
            log.error("Ошибка FTP, загрузка/выгрузка. ", e);
        } finally {
            try {
                uploadFile.close();
                file.delete();
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error("Ошибка при закрытий соедениний с FTP либо при закрытий InputStream. ", e);
            }
        }
    }
}


