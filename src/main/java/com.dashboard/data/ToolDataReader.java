package com.dashboard.data;

import com.dashboard.domain.ToolData;
import java.util.List;

/**
 * An interface defining the contract for reading tool data.
 */
public interface ToolDataReader
{
    List<ToolData> readData();
}