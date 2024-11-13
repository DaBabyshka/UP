import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class CertificateSystemApp extends Frame {
    private CertificateService certificateService = new CertificateService();
    private TextField idField = new TextField(20);
    private TextField typeField = new TextField(20);
    private TextField requesterField = new TextField(20);
    private TextField searchIdField = new TextField(20);
    private TextArea outputArea = new TextArea(10, 50);

    public CertificateSystemApp() {
        setTitle("Справочное бюро");
        setSize(600, 400);
        setLayout(new FlowLayout());

        add(new Label("ID справки:"));
        add(idField);

        add(new Label("Тип справки:"));
        add(typeField);

        add(new Label("Имя заявителя:"));
        add(requesterField);

        add(new Label("ID для поиска:"));
        add(searchIdField);

        Button addButton = new Button("Запросить справку");
        Button searchButton = new Button("Найти справку");
        Button updateButton = new Button("Обновить статус");

        add(addButton);
        add(searchButton);
        add(updateButton);

        outputArea.setEditable(false);
        add(outputArea);

        addButton.addActionListener(e -> addCertificate());
        searchButton.addActionListener(e -> searchCertificate());
        updateButton.addActionListener(e -> updateCertificateStatus());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    private void addCertificate() {
        String id = idField.getText();
        String type = typeField.getText();
        String requesterName = requesterField.getText();

        if (!id.isEmpty() && !type.isEmpty() && !requesterName.isEmpty()) {
            Certificate existingCert = certificateService.findCertificateById(id);
            if (existingCert == null) {
                certificateService.addCertificate(id, type, requesterName);
                outputArea.append("Справка добавлена: ID=" + id + ", Тип=" + type + ", Имя=" + requesterName + "\n");
                idField.setText("");
                typeField.setText("");
                requesterField.setText("");
                displayCertificates();
            } else {
                showMessage("Ошибка: Справка с таким ID уже существует.");
            }
        } else {
            showMessage("Ошибка: заполните все поля для добавления справки.");
        }
    }

    private void searchCertificate() {
        String id = searchIdField.getText();
        if (!id.isEmpty()) {
            Certificate foundCert = certificateService.findCertificateById(id);
            if (foundCert != null) {
                showMessage("Найдена справка:\nID: " + foundCert.getId() + "\nТип: " + foundCert.getType() +
                        "\nИмя: " + foundCert.getRequesterName() + "\nСтатус: " + foundCert.getStatus());
            } else {
                showMessage("Справка с ID " + id + " не найдена.");
            }
        } else {
            showMessage("Введите ID для поиска справки.");
        }
    }

    private void updateCertificateStatus() {
        String id = searchIdField.getText();
        if (!id.isEmpty()) {
            certificateService.updateCertificateStatus(id, "Завершено");
            showMessage("Статус справки с ID " + id + " обновлен на 'Завершено'.");
            displayCertificates();
        } else {
            showMessage("Введите ID для обновления статуса.");
        }
    }

    private void displayCertificates() {
        outputArea.setText("");
        List<Certificate> certificates = certificateService.getAllCertificates();
        outputArea.append("Текущие справки:\n");
        for (Certificate cert : certificates) {
            outputArea.append("ID: " + cert.getId() + ", Тип: " + cert.getType() + ", Имя: " + cert.getRequesterName() + ", Статус: " + cert.getStatus() + "\n");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        CertificateSystemApp app = new CertificateSystemApp();
        app.setVisible(true);
    }
}
