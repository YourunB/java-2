import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Form {

  public static void main(String[] args) {
    String url = "jdbc:sqlserver://localhost:1433;databaseName=TestJavaDB;encrypt=true;trustServerCertificate=true";
    String user = "yury";
    String password = "yuryadmin";

    JFrame frame = new JFrame("Форма с таблицей");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700, 400);
    frame.setLocationRelativeTo(null);

    String[] columnNames = {"ID", "Имя", "Возраст", "Образование", "Работа"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);

    // Панель ввода
    JPanel inputPanel = new JPanel(new GridLayout(3, 4, 5, 5));
    JTextField nameField = new JTextField();
    JTextField ageField = new JTextField();
    JTextField educationField = new JTextField();
    JTextField worksField = new JTextField();

    inputPanel.add(new JLabel("Имя"));
    inputPanel.add(new JLabel("Возраст"));
    inputPanel.add(new JLabel("Образование"));
    inputPanel.add(new JLabel("Работа"));

    inputPanel.add(nameField);
    inputPanel.add(ageField);
    inputPanel.add(educationField);
    inputPanel.add(worksField);

    JButton addButton = new JButton("Добавить");
    inputPanel.add(addButton);

    // Загрузка данных из БД
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      String sql = "SELECT id, name, age, education, works FROM [user]";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        String education = resultSet.getString("education");
        String works = resultSet.getString("works");
        model.addRow(new Object[]{id, name, age, education, works});
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Обработчик кнопки добавления
    addButton.addActionListener(e -> {
      String name = nameField.getText().trim();
      String ageText = ageField.getText().trim();
      String education = educationField.getText().trim();
      String works = worksField.getText().trim();

      if (name.isEmpty() || ageText.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Имя и возраст обязательны!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        int age = Integer.parseInt(ageText);

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
          String insertSql = "INSERT INTO [user] (name, age, education, works) VALUES (?, ?, ?, ?)";
          PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
          preparedStatement.setString(1, name);
          preparedStatement.setInt(2, age);
          preparedStatement.setString(3, education);
          preparedStatement.setString(4, works);
          preparedStatement.executeUpdate();

          ResultSet keys = preparedStatement.getGeneratedKeys();
          int newId = -1;
          if (keys.next()) {
            newId = keys.getInt(1); // Получаем сгенерированный ID
          }

          model.addRow(new Object[]{newId, name, age, education, works});
          nameField.setText("");
          ageField.setText("");
          educationField.setText("");
          worksField.setText("");
        }

      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Возраст должен быть числом!", "Ошибка", JOptionPane.ERROR_MESSAGE);
      } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Ошибка при добавлении в базу данных", "Ошибка", JOptionPane.ERROR_MESSAGE);
      }
    });

    JButton deleteButton = new JButton("Удалить");
    inputPanel.add(deleteButton);  // Добавляем кнопку на панель

// Обработчик кнопки удаления
    deleteButton.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow == -1) {
        JOptionPane.showMessageDialog(frame, "Выберите строку для удаления!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        return;
      }

      int confirm = JOptionPane.showConfirmDialog(frame, "Вы уверены, что хотите удалить эту запись?", "Подтверждение", JOptionPane.YES_NO_OPTION);
      if (confirm != JOptionPane.YES_OPTION) return;

      int id = (int) model.getValueAt(selectedRow, 0); // Получаем ID из первой колонки

      try (Connection connection = DriverManager.getConnection(url, user, password)) {
        String deleteSql = "DELETE FROM [user] WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

        model.removeRow(selectedRow); // Удаляем из таблицы

      } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Ошибка при удалении из базы данных", "Ошибка", JOptionPane.ERROR_MESSAGE);
      }
    });

    frame.setLayout(new BorderLayout(10, 10));
    frame.add(scrollPane, BorderLayout.CENTER);
    frame.add(inputPanel, BorderLayout.SOUTH);
    frame.setVisible(true);
  }
}
