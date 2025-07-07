package com.dashboard.domain;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import java.util.List;

public class PredictiveModel
{
    private double intercept;
    private double errorRateCoeff;
    private double usageHoursCoeff;
    private boolean isTrained = false;

    /**
     * Trains the multiple linear regression model using historical data.
     * This version includes error handling for the regression calculation.
     * @param dataList The historical data to train the model.
     */
    public void train(List<ToolData> dataList)
    {
        // The model requires at least 3 data points to calculate 3 parameters.
        if (dataList == null || dataList.size() < 3)
        {
            System.err.println("Model training skipped: Not enough data points (requires at least 3).");
            this.isTrained = false;
            return;
        }

        try
        {
            double[] y = dataList.stream().mapToDouble(ToolData::getCycleTime).toArray();
            double[][] x = new double[dataList.size()][2];
            for (int i = 0; i < dataList.size(); i++) {
                x[i][0] = dataList.get(i).getErrorRate();
                x[i][1] = dataList.get(i).getUsageHours();
            }

            OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
            regression.newSampleData(y, x);

            double[] coefficients = regression.estimateRegressionParameters();
            this.intercept = coefficients[0];
            this.errorRateCoeff = coefficients[1];
            this.usageHoursCoeff = coefficients[2];
            this.isTrained = true; // Mark as trained ONLY if successful
            System.out.println("Predictive model trained successfully.");

        }
        catch (Exception e)
        {
            // If the math library throws an error (e.g., due to unsuitable data), catch it.
            System.err.println("Failed to train the predictive model. The dataset may be mathematically unsuitable for regression.");
            e.printStackTrace();
            this.isTrained = false; // Ensure the model is not marked as trained
        }
    }

    /**
     * Predicts the CycleTime for a new set of inputs.
     * @param newErrorRate The error rate of the new data point.
     * @param newUsageHours The usage hours of the new data point.
     * @return The predicted CycleTime, or -1 if the model is not trained.
     */
    public double predict(double newErrorRate, double newUsageHours)
    {
        if (!isTrained)
        {
            return -1; // Return an error code if the model hasn't been trained
        }
        // Apply the learned formula
        return intercept + (errorRateCoeff * newErrorRate) + (usageHoursCoeff * newUsageHours);
    }

    /**
     * Returns a string representation of the learned model formula.
     * @return The formula as a string, or an info message if not trained.
     */
    public String getModelFormula()
    {
        if (!isTrained)
        {
            return "Model not trained (check data size or suitability).";
        }
        return String.format(
                "Learned Model: Predicted_CycleTime = %.2f + (%.2f * ErrorRate) + (%.2f * UsageHours)",
                intercept, errorRateCoeff, usageHoursCoeff);
    }

    public boolean isTrained()
    {
        return isTrained;
    }
}