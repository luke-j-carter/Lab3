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



