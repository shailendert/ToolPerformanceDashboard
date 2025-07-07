package com.dashboard.data;

import com.dashboard.domain.ToolData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class JsonToolDataReader implements ToolDataReader
{
    private final String resourcePath;

    public JsonToolDataReader(String resourcePath)
    {
        this.resourcePath = resourcePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ToolData> readData()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeReference<List<ToolData>> typeReference = new TypeReference<>() {};

        try (InputStream inputStream = JsonToolDataReader.class.getResourceAsStream(resourcePath))
        {
            if (inputStream == null)
            {
                System.err.println("Cannot find resource file: " + resourcePath);
                return Collections.emptyList();
            }
            return mapper.readValue(inputStream, typeReference);
        }
        catch (Exception e)
        {
            System.err.println("Error reading or parsing JSON file: " + resourcePath);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}