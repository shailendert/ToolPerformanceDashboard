package com.dashboard.data;

import com.dashboard.domain.ToolData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvToolDataReader implements ToolDataReader
{

    private final String resourcePath;

    public CsvToolDataReader(String resourcePath)
    {
        this.resourcePath = resourcePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ToolData> readData()
    {
        try(InputStream is = CsvToolDataReader.class.getResourceAsStream(resourcePath);
            Reader reader = new InputStreamReader(is);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            List<ToolData> dataList = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser)
            {
                ToolData data = new ToolData(
                        csvRecord.get("ToolID"),
                        Double.parseDouble(csvRecord.get("CycleTime")),
                        Double.parseDouble(csvRecord.get("ErrorRate")),
                        Double.parseDouble(csvRecord.get("UsageHours")),
                        LocalDate.parse(csvRecord.get("Timestamp"))
                );
                dataList.add(data);
            }
            return dataList;
        }
        catch (Exception e)
        {
            System.err.println("Error reading or parsing CSV file: " + resourcePath);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}