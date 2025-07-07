package com.dashboard.analysis;

import com.dashboard.domain.ToolData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.List;

public class BarChartRenderer
{
    public static JFreeChart createChart(List<ToolData> dataList)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ToolData data : dataList)
        {
            dataset.addValue(data.getCycleTime(), "Cycle Time", data.getToolID());
            dataset.addValue(data.getErrorRate() * 100, "Error Rate (%)", data.getToolID());
            dataset.addValue(data.getUsageHours(), "Usage Hours", data.getToolID());
        }

        return ChartFactory.createBarChart(
                "Tool Performance Metrics", "Tool ID", "Value",
                dataset, PlotOrientation.VERTICAL, true, true, false
        );
    }
}