import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ModernNotepad extends JFrame implements ActionListener {
    JTextArea textArea;
    JLabel statusLabel;
    JFileChooser fileChooser;

    ModernNotepad() {
        // Use system native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        setTitle("Modern Notepad");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Text area
        textArea = new JTextArea();
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Status bar
        statusLabel = new JLabel(" Ready");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusLabel, BorderLayout.SOUTH);

        // File chooser
        fileChooser = new JFileChooser();

        // Menu
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");

        open.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(e -> System.exit(0));

        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(exit);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");

        cut.addActionListener(e -> textArea.cut());
        copy.addActionListener(e -> textArea.copy());
        paste.addActionListener(e -> textArea.paste());

        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Open")) {
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    textArea.read(reader, null);
                    statusLabel.setText(" Opened: " + file.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to open file.");
                }
            }
        } else if (command.equals("Save")) {
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    textArea.write(writer);
                    statusLabel.setText(" Saved: " + file.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save file.");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ModernNotepad::new);
    }
}
