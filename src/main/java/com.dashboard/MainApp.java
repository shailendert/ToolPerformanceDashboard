package com.dashboard;

import com.dashboard.ui.DashboardView;
import javax.swing.SwingUtilities;

public class MainApp
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            DashboardView dashboard = new DashboardView();
            dashboard.setVisible(true);
        });
    }
}