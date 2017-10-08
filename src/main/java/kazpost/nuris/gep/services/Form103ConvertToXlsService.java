package kazpost.nuris.gep.services;

import kazpost.nuris.gep.models.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
public class Form103ConvertToXlsService {
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
        Form103ConvertToXlsService convert = new Form103ConvertToXlsService();
        Form103Path path = new Form103Path();

        path.setToPath("test");
        path.setFileNameAfter("testAfter");

        path.setFormPath("/160000/160000/COMMON");
        path.setFileNameBefore("test");

        convert.convertForm103Xls(path);

    }


    public void convertForm103Xls(Form103Path path) {
        downloadFile(path);

        Form103XlsService form103XlsService = new Form103XlsService();
        Form103XlsCellHeaderDescription header = new Form103XlsCellHeaderDescription();
        List<Form103XlsCellBodyDescription> bodyList = new ArrayList<Form103XlsCellBodyDescription>();
        Form103XlsSheet form103XlsSheet = new Form103XlsSheet();

        InputStream excelFileToRead = null;
        try {
            excelFileToRead = new FileInputStream(path.getFileNameBefore() + XLS_FILE_FORMAT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(excelFileToRead);
        } catch (IOException e) {
            e.printStackTrace();
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

        System.out.println(form103XlsSheet);

        Form103ConvertToXlsService converterService = new Form103ConvertToXlsService();
        try {
            converterService.generateForm103XlsFile(form103XlsSheet, path, form103XlsService);
        } catch (Exception e) {
            e.printStackTrace();
        }

        uploadFile(path);
    }


    public void generateForm103XlsFile(Form103XlsSheet form103XlsSheet, Form103Path path, Form103XlsService form103XlsService) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(SHEET_NAME);

        form103XlsService.createXlsHeaderPart(form103XlsSheet, sheet);
        form103XlsService.createXlsBodyPart(workbook, sheet);

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
            out = new FileOutputStream(new File(path.getFileNameAfter()+ XLS_FILE_FORMAT));
            workbook.write(out);


        } catch (FileNotFoundException e) {
        }
        try {
            out.close();
            workbook.close();
        } catch (IOException e) {
        }
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
            file = new File(path.getFileNameAfter()+XLS_FILE_FORMAT);
            uploadFile = new FileInputStream(file);

            String pathAndNameUploadFile = path.getToPath() + "/" + path.getFileNameAfter() + XLS_FILE_FORMAT;

            boolean done = ftpClient.storeFile(pathAndNameUploadFile, uploadFile);
            if (done) {
                System.out.println("Файл загружен! "+path.getToPath() + "/" + path.getFileNameAfter() + XLS_FILE_FORMAT);
            } else {
                System.out.println("Файл НЕ загружен! "+path.getToPath() + "/" + path.getFileNameAfter() + XLS_FILE_FORMAT);

            }

        } catch (IOException e) {
        } finally {
            try {
                uploadFile.close();
                file.delete();
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
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

                File downloadFile1 = new File(path.getFileNameBefore()+XLS_FILE_FORMAT);
                OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
                boolean success = ftpClient.retrieveFile(path.getFormPath()+"/"+path.getFileNameBefore() + XLS_FILE_FORMAT, outputStream1);
                outputStream1.close();

                if (success) {
                    System.out.println("Файл успешно выгружен! " + path.getFormPath()+"/"+path.getFileNameBefore()+XLS_FILE_FORMAT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


