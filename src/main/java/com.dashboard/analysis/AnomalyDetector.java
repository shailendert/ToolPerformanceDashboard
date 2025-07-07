package com.dashboard.analysis;

import com.dashboard.domain.ToolData;

import java.util.ArrayList;
import java.util.List;

public class AnomalyDetector
{
    private AnomalyDetector()
    {
        // Private constructor to prevent instantiation
    }

    public static List<ToolData> detectCycleTimeAnomalies(List<ToolData> dataList, double threshold)
    {
        List<ToolData> anomalies = new ArrayList<>();
        if (dataList == null || dataList.size() < 2)
        {
            return anomalies;
        }

        double sum = dataList.stream().mapToDouble(ToolData::getCycleTime).sum();
        double mean = sum / dataList.size();

        double standardDeviation = Math.sqrt(dataList.stream()
                .mapToDouble(d -> Math.pow(d.getCycleTime() - mean, 2))
                .sum() / dataList.size());

        for (ToolData data : dataList)
        {
            if (Math.abs(data.getCycleTime() - mean) > (threshold * standardDeviation))
            {
                anomalies.add(data);
            }
        }
        return anomalies;
    }
}