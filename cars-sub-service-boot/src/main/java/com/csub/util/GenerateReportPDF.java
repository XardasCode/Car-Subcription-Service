package com.csub.util;

import com.csub.entity.Car;
import com.csub.entity.Subscription;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class GenerateReportPDF {


    public byte[] generatePdf(Car car, Subscription subscription) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        float cellHeight = 28;
        document.open();

        BaseFont baseFont = BaseFont.createFont("fonts/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont,14);
        Paragraph preface = new Paragraph();



        document.add(new Paragraph());
        PdfPTable pdfPTable = new PdfPTable(1);
        PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("Звіт по авто",font));
        pdfPCell1.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell1.setMinimumHeight(cellHeight);
        pdfPTable.addCell(pdfPCell1);

        document.add(pdfPTable);

        PdfPTable pdfPTable2 = new PdfPTable(2);

        PdfPCell pdfPCell2 = new PdfPCell(new Paragraph("Марка",font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);

        PdfPCell pdfPCell3 = new PdfPCell(new Paragraph(car.getBrand(),font));
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);

        pdfPCell2 = new PdfPCell(new Paragraph("Модель",font));
        pdfPCell3 = new PdfPCell(new Paragraph(car.getName()+" "+car.getModel(),font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);

        pdfPCell2 = new PdfPCell(new Paragraph("Номер шасі",font));
        pdfPCell3 = new PdfPCell(new Paragraph(car.getChassisNumber(),font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);

        pdfPCell2 = new PdfPCell(new Paragraph("Тип пального",font));
        pdfPCell3 = new PdfPCell(new Paragraph(car.getFuelType(),font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);


        pdfPCell2 = new PdfPCell(new Paragraph("Колір",font));
        pdfPCell3 = new PdfPCell(new Paragraph(car.getColor(),font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);

        pdfPCell2 = new PdfPCell(new Paragraph("Реєстраційний номер",font));
        pdfPCell3 = new PdfPCell(new Paragraph(car.getRegNumber(),font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);

        pdfPCell2 = new PdfPCell(new Paragraph("Рік випуску",font));
        pdfPCell3 = new PdfPCell(new Paragraph(String.valueOf(car.getYear()),font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);

        pdfPCell2 = new PdfPCell(new Paragraph("Дата реєстрації",font));
        pdfPCell3 = new PdfPCell(new Paragraph(car.getRegDate(),font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);

        pdfPCell2 = new PdfPCell(new Paragraph("Пробіг",font));
        pdfPCell3 = new PdfPCell(new Paragraph(String.valueOf(car.getMileage()),font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);

        pdfPCell2 = new PdfPCell(new Paragraph("Останнє ТО",font));
        pdfPCell3 = new PdfPCell(new Paragraph(car.getLastServiceDate(),font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);

        String lastPayDate = subscription.getLastPayDate();
        if(lastPayDate == null){
            lastPayDate = "Не було";
        }else{
            lastPayDate = lastPayDate.substring(0, lastPayDate.indexOf("."));
        }
        pdfPCell2 = new PdfPCell(new Paragraph("Останній платіж",font));
        pdfPCell3 = new PdfPCell(new Paragraph(lastPayDate,font));
        pdfPCell2.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell2.setMinimumHeight(cellHeight);
        pdfPCell3.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell3.setMinimumHeight(cellHeight);

        pdfPTable2.addCell(pdfPCell2);
        pdfPTable2.addCell(pdfPCell3);
        pdfPTable2.setPaddingTop(40);
        document.add(pdfPTable2);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        Paragraph paragraph = new Paragraph("Звіт видано: "+ formattedDateTime,font);
        paragraph.setSpacingBefore(30f);

        document.add(paragraph);

        document.close();

        return outputStream.toByteArray();
    }
}
