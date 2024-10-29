import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class VisualizationLayer {

    private List<EconomicData> economicDataList;
    private JTable table;
    private JTextArea detailsArea;

    public VisualizationLayer(List<EconomicData> data) {
        this.economicDataList = data;
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Economic Data Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Table Panel
        String[] columnNames = {"State", "Description", "Personal Income", "Population", "Per Capita Income"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        updateTableData();

        // Create a TableRowSorter and set it on the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                EconomicData data = economicDataList.get(selectedRow);
                detailsArea.setText(data.toString());

            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Details Panel
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        mainPanel.add(detailsArea, BorderLayout.SOUTH);

        // Add main panel to frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void updateTableData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing data
        for (EconomicData data : economicDataList) {
            model.addRow(new Object[]{
                    data.getState(),
                    data.getDescription(),
                    data.getPersonalIncome(),
                    data.getPopulation(),
                    data.getPerCapitaPersonalIncome()
            });
        }
    }

    public static void main(String[] args) {
        try {
            String csvFile = "C:\\Users\\lukej\\IdeaProjects\\Lab3\\Economic Data 1980-2014.csv"; //Path for Economic Data
            List<EconomicData> data = CSVReader.readCSV(csvFile);
            SwingUtilities.invokeLater(() -> new VisualizationLayer(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

    /* Custom JPanel for drawing the chart
    class ChartPanel extends JPanel {
        private EconomicData currentData;

        public void setData(EconomicData data) {
            this.currentData = data;
            repaint(); // Repaint to show the new chart
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (currentData != null) {
                drawChart(g, currentData);
            }
        }

        private void drawChart(Graphics g, EconomicData data) {
            int width = getWidth();
            int height = getHeight();
            int barWidth = width / 4; // For four bars: Personal Income, Population, Per Capita Income
            int maxBarHeight = height - 50; // Leave space for labels

            // Values to be plotted
            int[] values = {
                    Integer.parseInt(data.getPersonalIncome()),
                    Integer.parseInt(data.getPopulation()),
                    Integer.parseInt(data.getPerCapitaPersonalIncome())
            };

            // Find the maximum value
            int maxValue = 0;
            for (int value : values) {
                if (value > maxValue) {
                    maxValue = value;
                }
            }

            // Draw bars
            String[] labels = {"Personal Income", "Population", "Per Capita Income"};
            for (int i = 0; i < values.length; i++) {
                int barHeight = (int) ((values[i] / (double) maxValue) * maxBarHeight);
                g.setColor(Color.BLUE);
                g.fillRect(i * barWidth + 10, maxBarHeight - barHeight, barWidth - 20, barHeight);
                g.setColor(Color.BLACK);
                g.drawString(labels[i], i * barWidth + 10, maxBarHeight + 15);
                g.drawString(String.valueOf(values[i]), i * barWidth + 10, maxBarHeight - barHeight - 5);
            }
        }
    }
}
*/


