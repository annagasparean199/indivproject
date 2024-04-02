package org.example.utils;

public class UtilVariables {

    public static final String CSV_FILE_PATH = "importedcsv/annual-enterprise-survey-2021-financial-year-provisional-csv.csv";
    public static final String FOLDER_PATH_AFTER_EXECUTION = "processedcsv";
    public static final String FAILED_RECORDS_PATH = "src/main/resources/failed_records.csv";
    public static final String RESOURCES_FOLDER_PATH = "src/main/resources/";
    public static final String CSV_FIELD_NAMES = "Year,Industry_aggregation_NZSIOC,Industry_code_NZSIOC,Industry_name_NZSIOC,Units,Variable_code,Variable_name,Variable_category,Value,Industry_code_ANZSIC06";

    public static final String SPLITTED_ENTITIES_DATA_CSV_PATH = "src/main/resources/spliterators.csv";
    public static final String COMBINE_VARIABLE_AND_FINANCIALDATA_QUERY = "SELECT f.year, f.industry_aggregationnzsioc, f.industry_codenzsioc, " +
            "       f.industry_namenzsioc, f.units, f.value, f.industry_codeanzsi06, " +
            "        v.variable_name, v.variable_category, v.variable_code FROM variable v JOIN financial_data f ON f.variable_code = v.variable_code;";

    public static final String[] FINANCIAL_INDUSTRY_DB_FIELDS = {"year", "industryAggregationNZSIOC", "industryCodeNZSIOC",
            "industryNameNZSIOC", "units", "variableCode", "variableName",
            "variableCategory", "value", "industryCodeANZSIC06"};
}

