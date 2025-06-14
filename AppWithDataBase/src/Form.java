import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Form {

  public static void main(String[] args) {
    // Создание окна (формы)
    JFrame frame = new JFrame("Форма с таблицей");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 300);
    frame.setLocationRelativeTo(null); // центрирование

    // Заголовки столбцов
    String[] columnNames = {"ID", "Имя", "Возраст"};

    // Данные таблицы
    Object[][] data = {
        {1, "тест", 11},
        {2, "тест1", 22},
        {3, "тест2", 33}
    };

    // Модель таблицы
    DefaultTableModel model = new DefaultTableModel(data, columnNames);

    // Создание JTable
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table); // добавляем прокрутку

    // Добавление таблицы в форму
    frame.add(scrollPane, BorderLayout.CENTER);

    // Отображение формы
    frame.setVisible(true);
  }
}
