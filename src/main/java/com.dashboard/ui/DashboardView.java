package com.dashboard.ui;

import com.dashboard.analysis.BarChartRenderer;
import com.dashboard.analysis.TrendChart;
import com.dashboard.data.CsvToolDataReader;
import com.dashboard.data.JsonToolDataReader;
import com.dashboard.data.ToolDataReader;
import com.dashboard.service.ToolAnalyticsService;
import org.jfree.chart.ChartPanel;
import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame
{
    private final ToolAnalyticsService service = new ToolAnalyticsService();

    // --- 1. Declare UI components as class fields ---
    private final JTabbedPane tabbedPane;
    private final JTextField errorRateInput;
    private final JTextField usageHoursInput;
    private final JButton predictButton;
    private final JButton loadCsvButton;
    private final JButton loadJsonButton;

    public DashboardView()
    {
        super("Tool Performance Dashboard - Shailender Tiwari");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 2. Instantiate components in the constructor ---
        tabbedPane = new JTabbedPane();
        errorRateInput = new JTextField(5);
        usageHoursInput = new JTextField(5);
        predictButton = new JButton("Predict Cycle Time");
        loadCsvButton = new JButton("Load CSV Data");
        loadJsonButton = new JButton("Load JSON Data");

        setupUI();
        setupListeners();
    }

    private void setupUI()
    {
        // Create the top panel and add the button fields
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(loadCsvButton);
        buttonPanel.add(loadJsonButton);

        // Create the bottom prediction panel
        JPanel predictionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        predictionPanel.setBorder(BorderFactory.createTitledBorder("Get New Prediction"));
        predictionPanel.add(new JLabel("New Error Rate:"));
        predictionPanel.add(errorRateInput);
        predictionPanel.add(new JLabel("New Usage Hours:"));
        predictionPanel.add(usageHoursInput);
        predictionPanel.add(predictButton);
        predictButton.setEnabled(false); // Initially disabled

        // Add all panels to the frame
        add(buttonPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(predictionPanel, BorderLayout.SOUTH);

        showWelcomeMessage();
    }

    private void setupListeners()
    {
        // --- 3. Use the field variables directly to add listeners ---
        loadCsvButton.addActionListener(e -> processData(new CsvToolDataReader("/sample_tool_data.csv")));
        loadJsonButton.addActionListener(e -> processData(new JsonToolDataReader("/live_data.json")));
        predictButton.addActionListener(e -> performPrediction());
    }

    private void processData(ToolDataReader dataReader)
    {
        service.loadAndAnalyzeData(dataReader);

        if (service.getCurrentData() == null || service.getCurrentData().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Failed to load data.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        updateCharts();
        predictButton.setEnabled(service.isModelTrained());
        JOptionPane.showMessageDialog(this, "Data loaded and model trained successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateCharts()
    {
        tabbedPane.removeAll();
        tabbedPane.addTab("Metrics Bar Chart", new ChartPanel(BarChartRenderer.createChart(service.getCurrentData())));
        tabbedPane.addTab("Trends & Anomalies", new ChartPanel(TrendChart.createChart(service.getCurrentData(), service.getDetectedAnomalies())));
    }

    private void performPrediction()
    {
        try
        {
            double newErrorRate = Double.parseDouble(errorRateInput.getText());
            double newUsageHours = Double.parseDouble(usageHoursInput.getText());
            double predictedCycleTime = service.predictCycleTime(newErrorRate, newUsageHours);

            if (predictedCycleTime < 0)
            {
                JOptionPane.showMessageDialog(this, "Model is not trained. Please load data first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String resultMessage = String.format("Predicted CycleTime is %.2f.", predictedCycleTime);
            JOptionPane.showMessageDialog(this, resultMessage, "Prediction Result", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showWelcomeMessage()
    {
        tabbedPane.removeAll();
        JLabel welcomeLabel = new JLabel("Welcome! Please load a dataset to begin.", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        tabbedPane.addTab("Welcome", welcomeLabel);
    }
}