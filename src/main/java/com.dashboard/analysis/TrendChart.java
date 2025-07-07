package com.dashboard.analysis;

import com.dashboard.domain.ToolData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.ui.TextAnchor;
import java.awt.Color;
import java.util.List;

public class TrendChart
{
    public static JFreeChart createChart(List<ToolData> dataList, List<ToolData> anomalies)
    {
        TimeSeries cycleTimeSeries = new TimeSeries("Cycle Time");
        TimeSeries errorTimeSeries = new TimeSeries("Error Rate");
        TimeSeries usageTimeSeries = new TimeSeries("Usage Hours");

        for (ToolData data : dataList)
        {
            Day day = new Day(data.getTimestamp().getDayOfMonth(), data.getTimestamp().getMonthValue(), data.getTimestamp().getYear());
            cycleTimeSeries.addOrUpdate(day, data.getCycleTime());
            errorTimeSeries.addOrUpdate(day, data.getErrorRate());
            usageTimeSeries.addOrUpdate(day, data.getUsageHours());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(cycleTimeSeries);
        dataset.addSeries(errorTimeSeries);
        dataset.addSeries(usageTimeSeries);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Performance Trends Over Time (with Anomaly Detection)", "Date", "Value",
                dataset, true, true, false
        );

        XYPlot plot = chart.getXYPlot();
        for (ToolData anomaly : anomalies)
        {
            Day day = new Day(anomaly.getTimestamp().getDayOfMonth(), anomaly.getTimestamp().getMonthValue(), anomaly.getTimestamp().getYear());
            XYPointerAnnotation annotation = new XYPointerAnnotation(
                    "  Cycle Time Anomaly!", day.getFirstMillisecond(), anomaly.getCycleTime(), -Math.PI / 4.0
            );
            annotation.setPaint(Color.RED);
            annotation.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
            plot.addAnnotation(annotation);
        }
        return chart;
    }
}