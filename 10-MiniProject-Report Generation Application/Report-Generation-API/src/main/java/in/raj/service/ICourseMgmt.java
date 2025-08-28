package in.raj.service;


import in.raj.model.SearchInputs;
import in.raj.model.SearchResults;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Set;

public interface ICourseMgmt {
    Set<String> showAllCourseCategories();
    Set<String> showAllTrainingModes();
    Set<String> showAllFaculties();
    List<SearchResults> showCoursesByFilters(SearchInputs inputs);

    void generatePDFReport(SearchInputs inputs, HttpServletResponse response) throws Exception;
    void generateExcelReport(SearchInputs inputs, HttpServletResponse response)throws Exception;

    void generatePdfReportAllData(HttpServletResponse  res)throws Exception;
    void generateExcelReportAllData(HttpServletResponse  res)throws Exception;
}
