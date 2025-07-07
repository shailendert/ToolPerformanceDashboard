package com.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ToolData
{
    private final String toolID;
    private final double cycleTime;
    private final double errorRate;
    private final double usageHours;
    private final LocalDate timestamp;

    /**
     * Constructor for ToolData
     *
     * @param toolID
     * @param cycleTime
     * @param errorRate
     * @param usageHours
     * @param timestamp
     */
    @JsonCreator
    public ToolData(@JsonProperty("toolID") String toolID,
                    @JsonProperty("cycleTime") double cycleTime,
                    @JsonProperty("errorRate") double errorRate,
                    @JsonProperty("usageHours") double usageHours,
                    @JsonProperty("timestamp") LocalDate timestamp)
    {
        this.toolID = toolID;
        this.cycleTime = cycleTime;
        this.errorRate = errorRate;
        this.usageHours = usageHours;
        this.timestamp = timestamp;
    }

    // Getters
    public String getToolID() { return toolID; }
    public double getCycleTime() { return cycleTime; }
    public double getErrorRate() { return errorRate; }
    public double getUsageHours() { return usageHours; }
    public LocalDate getTimestamp() { return timestamp; }
}