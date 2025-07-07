# 🚀 Tool Performance Dashboard

A Java desktop application demonstrating data analysis, AI-driven predictions, and clean software architecture. This project visualizes machine tool performance data and uses machine learning to provide diagnostic and predictive insights.

---

### ## Core Features

* **Layered Architecture**: Clean separation between UI, service, and data layers for improved maintainability, testability, and scalability.
* **Multiple Data Sources**: Supports loading performance data from both **CSV** and **JSON** files using a flexible data reader interface.
* **AI-Powered Analytics**:
    * **Diagnostic AI**: Automatically performs anomaly detection to flag unusual operational data points.
    * **Predictive AI**: Implements a Multiple Linear Regression model to understand the factors driving performance and to predict outcomes for new data.
* **Interactive Predictions**: A user-friendly interface to enter new data points and receive instant predictions from the trained model.
* **Data Visualization**: Dynamically generates bar charts and time-series trend charts using JFreeChart.

---

### ## 🏛️ Architectural Overview

This project follows a clean, layered architecture to separate concerns, making the application robust and easy to extend.

* **UI Layer (`ui`)**: Contains the Swing-based user interface. Its sole responsibility is to display information and capture user input, delegating all logic to the service layer.
* **Service Layer (`service`)**: The brain of the application. It orchestrates all operations, acting as the bridge between the UI and the underlying data and domain logic.
* **Data Layer (`data`)**: Manages all data access through a common `ToolDataReader` interface, allowing for easy integration of new data sources like databases.
* **Domain Layer (`domain`)**: Holds the core business objects (`ToolData`) and the AI logic (`PredictiveModel`).
* **Analysis Layer (`analysis`)**: Contains stateless utility classes that perform specific tasks like chart rendering or anomaly detection.

---

### ## 📂 Class Structure

```
com/dashboard/
├── MainApp.java             # Main application entry point
├── ui/
│   └── DashboardView.java     # The main Swing JFrame (View)
├── service/
│   └── ToolAnalyticsService.java # Application logic (Controller/Service)
├── domain/
│   ├── ToolData.java          # Core data entity (Model)
│   └── PredictiveModel.java   # Machine learning model logic
├── analysis/
│   ├── AnomalyDetector.java     # Algorithm for finding outliers
│   ├── BarChartRenderer.java  # Utility for creating bar charts
│   └── TrendChart.java        # Utility for creating line charts
└── data/
    ├── ToolDataReader.java      # Interface for all data readers
    ├── CsvToolDataReader.java   # Implementation for reading CSV files
    └── JsonToolDataReader.java  # Implementation for reading JSON files
```

---

### ## ⚙️ Technology Stack

* **Core**: Java 11
* **GUI**: Java Swing
* **Build & Dependencies**: Apache Maven
* **Charting**: JFreeChart
* **AI & Statistics**: Apache Commons Math
* **Data Parsing**: Apache Commons CSV (for CSV), Jackson (for JSON)

---

### ## 🏃 How to Run

1.  **Prerequisites**: JDK 11 and Apache Maven must be installed.
2.  **Open Project**: Open the project's root folder in your IDE (e.g., IntelliJ IDEA). The IDE will automatically detect the `pom.xml` and download the required libraries.
3.  **Run Application**: Navigate to the `src/main/java/com/dashboard/MainApp.java` file.
4.  Right-click on the file and select **Run 'MainApp.main()'**.

---

### ## 💡 How to Use the Application

1.  **Load Data & Train Model**: Click **"Load CSV Data"** or **"Load JSON Data"**. This action reads the corresponding file, displays the analytical charts, and trains the AI model in the background.
2.  **View Analysis**: Observe the charts for visual trends and anomalies. An initial pop-up will confirm that the model has been trained.
3.  **Get a Prediction**: Use the panel at the bottom of the window to enter new values for **"New Error Rate"** and **"New Usage Hours"**.
4.  Click the **"Predict Cycle Time"** button to receive an instant prediction from the trained model in a pop-up dialog.