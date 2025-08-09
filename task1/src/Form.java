import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Form extends JFrame {
  private JTable categoryTable;
  private JTable itemTable;
  private JButton loadButton;
  private Connection conn;

  private DefaultTableModel categoryModel;
  private DefaultTableModel itemModel;

  public Form() {
    setTitle("Категории и Предметы");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(800, 600);
    setLayout(new BorderLayout());

    categoryModel = new DefaultTableModel(new Object[]{"ID", "Название"}, 0);
    itemModel = new DefaultTableModel(new Object[]{"ID", "Категория ID", "Описание"}, 0);

    categoryTable = new JTable(categoryModel);
    itemTable = new JTable(itemModel);

    loadButton = new JButton("Загрузить из БД");

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
      new JScrollPane(categoryTable),
      new JScrollPane(itemTable));
    splitPane.setDividerLocation(250);

    add(loadButton, BorderLayout.NORTH);
    add(splitPane, BorderLayout.CENTER);

    loadButton.addActionListener(e -> loadData());

    categoryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
          int selectedRow = categoryTable.getSelectedRow();
          if (selectedRow >= 0) {
            int categoryId = (int) categoryModel.getValueAt(selectedRow, 0);
            loadItems(categoryId);
          }
        }
      }
    });
  }

  private void loadData() {
    try {
      String url = "jdbc:sqlserver://localhost:1433;databaseName=task1DB;encrypt=true;trustServerCertificate=true";
      String user = "yury";
      String password = "yuryadmin";

      conn = DriverManager.getConnection(url, user, password);
      categoryModel.setRowCount(0); // очистка

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM categories");

      while (rs.next()) {
        categoryModel.addRow(new Object[]{
          rs.getInt("id"),
          rs.getString("name")
        });
      }

      rs.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "Ошибка подключения к БД: " + e.getMessage());
    }
  }

  private void loadItems(int categoryId) {
    try {
      itemModel.setRowCount(0); // очистка
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE category_id = ?");
      stmt.setInt(1, categoryId);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        itemModel.addRow(new Object[]{
          rs.getInt("id"),
          rs.getInt("category_id"),
          rs.getString("name")
        });
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "Ошибка загрузки дочерних записей: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new Form().setVisible(true);
    });
  }
}