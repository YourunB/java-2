import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Form {

  public static void main(String[] args) {
    // Данные для подключения к базе данных (SQL Authentication)
    String url = "jdbc:sqlserver://localhost:1433;databaseName=TestJavaDB;encrypt=true;trustServerCertificate=true";
    String user = "yury";
    String password = "yuryadmin";

    // Создание окна (формы)
    JFrame frame = new JFrame("Форма с таблицей");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 300);
    frame.setLocationRelativeTo(null); // центрирование

    // Заголовки столбцов
    String[] columnNames = {"ID", "Имя", "Возраст"};

    // Модель таблицы
    DefaultTableModel model = new DefaultTableModel(columnNames, 0); // создаем пустую модель

    // Попытка подключения к базе данных
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      // Запрос к базе данных
      String sql = "SELECT id, name, age FROM [user]";  // пример запроса
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      // Заполнение модели данными из базы данных
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        model.addRow(new Object[]{id, name, age});
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Создание JTable
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table); // добавляем прокрутку

    // Добавление таблицы в форму
    frame.add(scrollPane, BorderLayout.CENTER);

    // Отображение формы
    frame.setVisible(true);
  }
}
