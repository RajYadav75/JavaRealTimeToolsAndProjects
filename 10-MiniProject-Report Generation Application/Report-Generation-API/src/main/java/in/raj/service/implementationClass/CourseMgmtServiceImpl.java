package in.raj.service.implementationClass;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import in.raj.entity.CourseDetails;
import in.raj.model.SearchInputs;
import in.raj.model.SearchResults;
import in.raj.repo.ICourseDetailsRepository;
import in.raj.service.ICourseMgmt;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Service
public class CourseMgmtServiceImpl implements ICourseMgmt {
    @Autowired
    private ICourseDetailsRepository courseRepo;
    @Override
    public Set<String> showAllCourseCategories() {
        return courseRepo.getUniqueCourseCategories();
    }

    @Override
    public Set<String> showAllTrainingModes() {
        return courseRepo.getUniqueTraniningModes();
    }

    @Override
    public Set<String> showAllFaculties() {
        return courseRepo.getUniqueFacultyNames();
    }

    @Override
    public List<SearchResults> showCoursesByFilters(SearchInputs inputs) {
        //TODO:- Get NonNull and non empty String  values  from  the inputs  object  and prepare  Entity
        //TODO:- Obj  having that non null data and  also place that entity object  inside Example obj
        CourseDetails courseDetails = new CourseDetails();
        String courseCategory = inputs.getCourseCategory();
        if (StringUtils.hasLength(courseCategory)) {
            courseDetails.setCourseCategory(courseCategory);
        }
        String facultyName = inputs.getFacultyName();
        if (StringUtils.hasLength(facultyName)){
            courseDetails.setFacultyName(facultyName);
        }
        String trainingMode = inputs.getTrainingMode();
        if (StringUtils.hasLength(trainingMode)){
            courseDetails.setTrainingMode(trainingMode);
        }
        LocalDateTime startDate = inputs.getStartsOn();
        if (!ObjectUtils.isEmpty(startDate)){
            courseDetails.setStartDate(startDate);
        }
        Example<CourseDetails> courseDetailsExample = Example.of(courseDetails);
        //TODO:- perform  the   search operation  with Filters data of the Example Entity obj
        List<CourseDetails> courseDetailsList = courseRepo.findAll(courseDetailsExample);
        //TODO:- convert  List<Entity obj>  to List<SearchResult> obj
        List<SearchResults> searchResultsList = new ArrayList<>();
        searchResultsList.forEach(course->{
            SearchResults searchResults = new SearchResults();
            BeanUtils.copyProperties(course, searchResults);
            searchResultsList.add(searchResults);
        });
        return searchResultsList;
    }

    @Override
    public void generatePDFReport(SearchInputs inputs, HttpServletResponse response) throws Exception{
        //TODO:- Get the SearchResult
        List<SearchResults> searchResultsList = showCoursesByFilters(inputs);
        //TODO:- Create Document Obj(OpenPdf)
        Document document = new Document(PageSize.A4);
        //TODO:- Get PdfWriter to write to the document and response obj
        PdfWriter.getInstance(document,response.getOutputStream());
        //TODO:- Open the document
        document.open();
        //TODO:- Define Font for the Paragraph
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
        font.setSize(30);
        font.setColor(Color.CYAN);
        //TODO:- Create the paragraph having content and above font style
        Paragraph para = new Paragraph("Search Report of Courses",font);
        para.setAlignment(Paragraph.ALIGN_CENTER);
        //TODO:-Add the paragraph to  document
        document.add(para);
        //TODO:- Display search results as the pdf table
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(70);
        table.setWidths(new float[]{3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f});
        table.setSpacingBefore(2.0f);

        //TODO:- Prepare heading row cells in the pdf table
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        cellFont.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("courseID",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("courseName",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Category",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("facultyName",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Location",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Fee",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Course Status",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("TrainingMode",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("adminContant",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("StartDate",cellFont));
        table.addCell(cell);

        //TODO:- add   data cells  to   pdftable
        searchResultsList.forEach(result->{
            table.addCell(String.valueOf(result.getCourseId()));
            table.addCell(result.getCourseName());
            table.addCell(result.getCourseCategory());
            table.addCell(result.getFacultyName());
            table.addCell(result.getLocation());
            table.addCell(String.valueOf(result.getFee()));
            table.addCell(result.getCourseStatus());
            table.addCell(result.getTrainingMode());
            table.addCell(String.valueOf(result.getAdminContact()));
            table.addCell(result.getStartDate().toString());
        });
        //TODO:- add table  to  document
        document.add(table);
        //TODO:- close the document
        document.close();
    }

    @Override
    public void generateExcelReport(SearchInputs inputs, HttpServletResponse response) throws IOException {
        //TODO:- get  the SearchResult
        List<SearchResults> listResults=showCoursesByFilters(inputs);

        //TODO:- create ExcelWorkBook  (apache poi  api)
        HSSFWorkbook workbook=new HSSFWorkbook();
        //TODO:- create  Sheet in the  Work book
        HSSFSheet sheet1=workbook.createSheet("CourseDetails");
        //TODO:- create  Heading Row in sheet1
        HSSFRow headerRow=sheet1.createRow(0);
        headerRow.createCell(0).setCellValue("CourseID");
        headerRow.createCell(1).setCellValue("CourseName");
        headerRow.createCell(2).setCellValue("Location");
        headerRow.createCell(3).setCellValue("CouseCategory");
        headerRow.createCell(4).setCellValue("FacultyName");
        headerRow.createCell(5).setCellValue("fee");
        headerRow.createCell(6).setCellValue("adminContact");
        headerRow.createCell(7).setCellValue("trainingMode");
        headerRow.createCell(8).setCellValue("startDate");
        headerRow.createCell(9).setCellValue("CourseStatus");
        //TODO:- add  data rows to the sheet
        int i=1;
        for(SearchResults result:listResults) {
            HSSFRow   dataRow=sheet1.createRow(i);
            dataRow.createCell(0).setCellValue(result.getCourseId());
            dataRow.createCell(1).setCellValue(result.getCourseName());
            dataRow.createCell(2).setCellValue(result.getLocation());
            dataRow.createCell(3).setCellValue(result.getCourseCategory());
            dataRow.createCell(4).setCellValue(result.getFacultyName());
            dataRow.createCell(5).setCellValue(result.getFee());
            dataRow.createCell(6).setCellValue(result.getAdminContact());
            dataRow.createCell(7).setCellValue(result.getTrainingMode());
            dataRow.createCell(8).setCellValue(result.getStartDate());
            dataRow.createCell(9).setCellValue(result.getCourseStatus());
            i++;
        }

        //TODO:- get OutputStream  pointing to  response obj
        ServletOutputStream outputStream=response.getOutputStream();
        //TODO:-  write the Excel work book  data  response  object using the above stream
        workbook.write(outputStream);
        //TODO:- close  the stream
        outputStream.close();
        workbook.close();
    }

    @Override
    public void generateExcelReportAllData(HttpServletResponse res) throws Exception {
        //TODO:- get  All  the records from Db table
        List<CourseDetails> list=courseRepo.findAll();
        //TODO:- copy  List<CourseDetails> to List<SearchResults>
        List<SearchResults> listResults=new ArrayList();
        list.forEach(course->{
            SearchResults  result=new SearchResults();
            BeanUtils.copyProperties(course, result);
            listResults.add(result);
        });

        //TODO:- create ExcelWorkBook  (apache poi  api)
        HSSFWorkbook  workbook=new HSSFWorkbook();
        //TODO:- create  Sheet in the  Work book
        HSSFSheet  sheet1=workbook.createSheet("CourseDetails");
        //TODO:- create  Heading Row in sheet1
        HSSFRow   headerRow=sheet1.createRow(0);
        headerRow.createCell(0).setCellValue("CourseID");
        headerRow.createCell(1).setCellValue("CourseName");
        headerRow.createCell(2).setCellValue("Location");
        headerRow.createCell(3).setCellValue("CouseCategory");
        headerRow.createCell(4).setCellValue("FacultyName");
        headerRow.createCell(5).setCellValue("fee");
        headerRow.createCell(6).setCellValue("adminContact");
        headerRow.createCell(7).setCellValue("trainingMode");
        headerRow.createCell(8).setCellValue("startDate");
        headerRow.createCell(9).setCellValue("CourseStatus");
        //TODO:- add  data rows to the sheet
        int i=1;
        for(SearchResults result:listResults) {
            HSSFRow   dataRow=sheet1.createRow(i);
            dataRow.createCell(0).setCellValue(result.getCourseId());
            dataRow.createCell(1).setCellValue(result.getCourseName());
            dataRow.createCell(2).setCellValue(result.getLocation());
            dataRow.createCell(3).setCellValue(result.getCourseCategory());
            dataRow.createCell(4).setCellValue(result.getFacultyName());
            dataRow.createCell(5).setCellValue(result.getFee());
            dataRow.createCell(6).setCellValue(result.getAdminContact());
            dataRow.createCell(7).setCellValue(result.getTrainingMode());
            dataRow.createCell(8).setCellValue(result.getStartDate());
            dataRow.createCell(9).setCellValue(result.getCourseStatus());
            i++;
        }

        //TODO:- get OutputStream  pointing to  response obj
        ServletOutputStream  outputStream=res.getOutputStream();
        //TODO:-  write the Excel work book  data  response  object using the above stream
        workbook.write(outputStream);
        //TODO:- close  the stream
        outputStream.close();
        workbook.close();
    }

    @Override
    public void generatePdfReportAllData(HttpServletResponse res) throws Exception {
        //TODO:- get  All  the records from Db table
        List<CourseDetails> list=courseRepo.findAll();
        //TODO:- copy  List<CourseDetails> to List<SearchResults>
        List<SearchResults> listResults=new ArrayList();
        list.forEach(course->{
            SearchResults  result=new SearchResults();
            BeanUtils.copyProperties(course, result);
            listResults.add(result);
        });
        //TODO:-  create Document  obj (openPdf)
        Document  document=new Document(PageSize.A4);
        //TODO:- get  PdfWriter  to  write  to the document and response obj
        PdfWriter.getInstance(document,res.getOutputStream());
        //TODO:- open the document
        document.open();
        //TODO:- Define  Font  for the Paragraph
        Font font=FontFactory.getFont(FontFactory.TIMES_BOLD);
        font.setSize(30);
        font.setColor(Color.CYAN);

        //TODO:- create the paragraph having  content  and above font  style
        Paragraph para=new Paragraph("Search Report of Courses", font);
        para.setAlignment(Paragraph.ALIGN_CENTER);
        //TODO:- add paragraph  to  document
        document.add(para);


        //TODO:-   Display search results as the pdf table
        PdfPTable table=new PdfPTable(10);
        table.setWidthPercentage(70);
        table.setWidths(new  float[] {3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f});
        table.setSpacingBefore(2.0f);

        //TODO:- prepare  heading  row cells  in the pdf table
        PdfPCell  cell=new  PdfPCell();
        cell.setBackgroundColor(Color.gray);
        cell.setPadding(5);
        Font  cellFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        cellFont.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("courseID",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("courseName",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Category",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("facultyName",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Location",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Fee",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Course Status",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("TrainingMode",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("adminContant",cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("StartDate",cellFont));
        table.addCell(cell);

        //TODO:-  add   data cells  to   pdftable
        listResults.forEach(result->{
            table.addCell(String.valueOf(result.getCourseId()));
            table.addCell(result.getCourseName());
            table.addCell(result.getCourseCategory());
            table.addCell(result.getFacultyName());
            table.addCell(result.getLocation());
            table.addCell(String.valueOf(result.getFee()));
            table.addCell(result.getCourseStatus());
            table.addCell(result.getTrainingMode());
            table.addCell(String.valueOf(result.getAdminContact()));
            table.addCell(result.getStartDate().toString());
        });
        //TODO:-  add table  to  document
        document.add(table);
        //TODO:- close the document
        document.close();
    }
}
