package in.raj.ms;

import in.raj.model.SearchInputs;
import in.raj.model.SearchResults;
import in.raj.service.ICourseMgmt;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/reporting-api")
@OpenAPIDefinition(info =
@Info(
        title = "Reporting API",
        version = "1.0",
        description = "Reporting API supporting File Download operations",
        license = @License(name = "NARESH IT", url = "http://raj.com"),
        contact = @Contact(url = "http://gigantic-server.com", name = "Raj", email = "Raj@gigagantic-server.com")
)
)
public class ReportGenerateRestController {
    @Autowired
    private ICourseMgmt courseService;
    @Operation(summary = "Get Courses Information",
            responses = {
                    @ApiResponse(description = "coursesInfo",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Wrong url")})

    @GetMapping("/courses")
    public ResponseEntity<?> fetchCourseCategories() {
        try {
            //TODO:- Use Service
            Set<String> courseInfo = courseService.showAllCourseCategories();
            return new ResponseEntity<Set<String>>(courseInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trainingMode")
    public ResponseEntity<?> fetchTrainingMode() {
        try {
            //TODO:- Use Service
            Set<String> allTrainingModes = courseService.showAllTrainingModes();
            return new ResponseEntity<>(allTrainingModes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/faculties")
    public ResponseEntity<?> fetchFaculties() {
        try {
            //TODO-: Use Service
            Set<String> allFaculties = courseService.showAllFaculties();
            return new ResponseEntity<>(allFaculties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> fetchCourseByFilter(@RequestBody SearchInputs searchInputs) {
        try {
            //TODO-: Use Service
            List<SearchResults> searchResultsList = courseService.showCoursesByFilters(searchInputs);
            return new ResponseEntity<>(searchResultsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pdfReport")
    public void generatePDFReport(@RequestBody SearchInputs searchInputs, HttpServletResponse response) throws Exception {
        try {
            //TODO:- Set the response content type
            response.setContentType("application/pdf");
            //TODO:- Set the content-disposition header  to  response content  going to browser as downloadable file
            response.setHeader("Content-Disposition", "attachment; filename=courses.pdf");
            //TODO:- Use the services
            courseService.generatePDFReport(searchInputs, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/excelReport")
    public void generateExcelReport(@RequestBody SearchInputs searchInputs, HttpServletResponse response) throws Exception {
        try {
            //TODO:- Set the response content type
            response.setContentType("application/vnd.ms-excel");
            //TODO:- Set the content-disposition header  to  response content  going to browser as downloadable file
            response.setHeader("Content-Disposition", "attachment; filename=courses.xls");
            //TODO:- Use the services
            courseService.generateExcelReport(searchInputs, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/allPdfReport")
    public void showPdfReportAllData(HttpServletResponse res) {
        try {
            //TODO:-set the response content type
            res.setContentType("application/pdf");
            //TODO:- set the content-disposition header  to  response content  going to browser as downloadable file
            res.setHeader("Content-Disposition", "attachment;fileName=courses.pdf");
            //TODO:-use service
            courseService.generatePdfReportAllData(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/allExcelReport")
    public void showExcelReportAllData(HttpServletResponse res) {
        try {
            //TODO:-set the response content type
            res.setContentType("application/vnd.ms-excel");
            //TODO:-set the content-disposition header  to  response content  going to browser as downloadable file
            res.setHeader("Content-Disposition", "attachment;fileName=courses.xls");
            //TODO:-use the service
            courseService.generateExcelReportAllData(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
