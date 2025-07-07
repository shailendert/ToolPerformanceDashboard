package com.dashboard.service;

import com.dashboard.analysis.AnomalyDetector;
import com.dashboard.data.ToolDataReader;
import com.dashboard.domain.PredictiveModel;
import com.dashboard.domain.ToolData;
import java.util.List;

public class ToolAnalyticsService
{
    private final PredictiveModel predictiveModel = new PredictiveModel();
    private List<ToolData> currentData;
    private List<ToolData> detectedAnomalies;

    public void loadAndAnalyzeData(ToolDataReader dataReader)
    {
        this.currentData = dataReader.readData();
        if (this.currentData.isEmpty())
        {
            return;
        }
        this.detectedAnomalies = AnomalyDetector.detectCycleTimeAnomalies(this.currentData, 2.0);
        this.predictiveModel.train(this.currentData);
    }

    public double predictCycleTime(double newErrorRate, double newUsageHours)
    {
        return predictiveModel.predict(newErrorRate, newUsageHours);
    }

    // Getters to provide data to the UI
    public List<ToolData> getCurrentData() { return currentData; }
    public List<ToolData> getDetectedAnomalies() { return detectedAnomalies; }
    public PredictiveModel getPredictiveModel() { return predictiveModel; }
    public boolean isModelTrained() { return predictiveModel.isTrained(); }
}