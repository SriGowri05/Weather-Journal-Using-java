package weatherjournal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class WeatherJournal extends JFrame {
    private JTextField dateField, tempField, humidityField, dayField;
    private JTextArea entriesArea, averagesArea;
    private ArrayList<String> entries;

    public WeatherJournal() {
        setTitle("Weather Journal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);

        entries = new ArrayList<>();

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("image.jpg"));
        Image img = backgroundIcon.getImage().getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
        backgroundIcon = new ImageIcon(img);
        
        JLabel background = new JLabel(backgroundIcon);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        setContentPane(background);
        
        JLabel titleLabel = new JLabel("WEATHER JOURNAL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        background.add(Box.createRigidArea(new Dimension(0, 20)));
        background.add(titleLabel);

        Font fieldFont = new Font("Arial", Font.PLAIN, 25);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 15, 15));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(fieldFont);
        dateLabel.setForeground(Color.DARK_GRAY);
        dateField = new JTextField();
        dateField.setFont(fieldFont);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);

        JLabel tempLabel = new JLabel("Temperature:");
        tempLabel.setFont(fieldFont);
        tempLabel.setForeground(Color.DARK_GRAY);
        tempField = new JTextField();
        tempField.setFont(fieldFont);
        inputPanel.add(tempLabel);
        inputPanel.add(tempField);

        JLabel humidityLabel = new JLabel("Humidity:");
        humidityLabel.setFont(fieldFont);
        humidityLabel.setForeground(Color.DARK_GRAY);
        humidityField = new JTextField();
        humidityField.setFont(fieldFont);
        inputPanel.add(humidityLabel);
        inputPanel.add(humidityField);

        JLabel dayLabel = new JLabel("How is the Day:");
        dayLabel.setFont(fieldFont);
        dayLabel.setForeground(Color.DARK_GRAY);
        dayField = new JTextField();
        dayField.setFont(fieldFont);
        inputPanel.add(dayLabel);
        inputPanel.add(dayField);

        background.add(inputPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 250, 10, 250));

        JButton addButton = new JButton("ADD ENTRY");
        JButton showButton = new JButton("SHOW ENTRIES");
        addButton.setPreferredSize(new Dimension(150, 70));
        showButton.setPreferredSize(new Dimension(150, 70));
        addButton.setFont(new Font("Arial", Font.PLAIN, 20));
        showButton.setFont(new Font("Arial", Font.PLAIN, 20));
        buttonPanel.add(addButton);
        buttonPanel.add(showButton);
        background.add(buttonPanel);

        JPanel avgPanel = new JPanel();
        avgPanel.setOpaque(false);
        avgPanel.setBorder(BorderFactory.createEmptyBorder(10, 250, 10, 250));
        JButton avgButton = new JButton("CALCULATE AVERAGE");
        avgButton.setFont(new Font("Arial", Font.PLAIN, 20));
        avgButton.setPreferredSize(new Dimension(450, 70));
        avgPanel.add(avgButton);
        background.add(avgPanel);
        
        entriesArea = new JTextArea(10, 40);
        averagesArea = new JTextArea(10, 40);

        entriesArea.setFont(new Font("Arial", Font.PLAIN, 20));
        averagesArea.setFont(new Font("Arial", Font.PLAIN, 20));

        entriesArea.setEditable(false);
        averagesArea.setEditable(false);

        entriesArea.setBorder(BorderFactory.createTitledBorder("Weather Entries"));
        averagesArea.setBorder(BorderFactory.createTitledBorder("Averages"));
        
        JPanel splitPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        splitPanel.setOpaque(false);
        splitPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        splitPanel.add(entriesArea);
        splitPanel.add(averagesArea);
        
        background.add(Box.createRigidArea(new Dimension(0, 20)));
        background.add(splitPanel);
        
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = dateField.getText().trim();
                String temp = tempField.getText().trim();
                String humidity = humidityField.getText().trim();
                String day = dayField.getText().trim();

                if (date.isEmpty() || temp.isEmpty() || humidity.isEmpty() || day.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.");
                } else {
                    String entry = "Date: " + date + ", Temperature: " + temp + ", Humidity: " + humidity + ", Day: " + day;
                    entries.add(entry);
                    JOptionPane.showMessageDialog(null, "Entry added successfully!");
                    dateField.setText("");
                    tempField.setText("");
                    humidityField.setText("");
                    dayField.setText("");
                }
            }
        });

        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                entriesArea.setText("");
                for (String entry : entries) {
                    entriesArea.append(entry + "\n");
                }
            }
        });

        avgButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (entries.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No entries to calculate average.");
                    return;
                }

                double totalTemp = 0;
                double totalHumidity = 0;
                int count = 0;

                for (String entry : entries) {
                    try {
                        String[] parts = entry.split(",");
                        double temp = Double.parseDouble(parts[1].split(":")[1].trim());
                        double humidity = Double.parseDouble(parts[2].split(":")[1].trim());
                        totalTemp += temp;
                        totalHumidity += humidity;
                        count++;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error in entry format.");
                        return;
                    }
                }

                double avgTemp = totalTemp / count;
                double avgHumidity = totalHumidity / count;

                averagesArea.setText("Average Temperature: " + avgTemp + "\nAverage Humidity: " + avgHumidity);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WeatherJournal().setVisible(true));
    }
}
