import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import java.awt.datatransfer.DataFlavor;
import java.io.FileReader;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.*;



public class TextEditor extends JFrame {

    // Create a text area
    public final JTextArea textArea = new JTextArea();
    private final Clipboard clipboard;
    public JMenuItem pasteMenuItem;
    private JMenuItem saveMenuItem;
    public JMenuItem openMenuItem;

    public TextEditor() {
        super("Text Editor");

        // Set the theme to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a "File" menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        // Create an "Open" menu item
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setMnemonic(KeyEvent.VK_O);
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle open file action
            }
        });
        fileMenu.add(openMenuItem);

        // Create a "Save" menu item
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle save file action
            }
        });
        fileMenu.add(saveMenuItem);

        // Add a separator
        fileMenu.addSeparator();

        // Create an "Exit" menu item
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        // Add the "File" menu to the menu bar
        menuBar.add(fileMenu);


        // Create a "Open" button for the toolbar
        JButton openButton = new JButton(new ImageIcon("icons/open.png"));
        openButton.setToolTipText("Open");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                if (fileChooser.showOpenDialog(TextEditor.this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        FileReader reader = new FileReader(file);
                        textArea.read(reader, null);
                        reader.close();
                    } catch (IOException ex) {
                        // Handle the exception here
                    }
                }
            }
        });
        JPanel toolbarPanel = new JPanel();
        toolbarPanel.add(openButton);

         // Create a "Save" button for the toolbar
        JButton saveButton = new JButton(new ImageIcon("icons/save.png"));
        saveButton.setToolTipText("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                if (fileChooser.showSaveDialog(TextEditor.this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(file);
                        writer.write(textArea.getText());
                        writer.close();
                    } catch (IOException ex) {
                        // Handle the exception here
                    }
                }
            }
        });
        toolbarPanel.add(saveButton);



        // Create an "Exit" menu item
        JMenuItem exitMenuIte = new JMenuItem("Exit");
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuIte);

//        // Add the "File" menu to the menu bar
//        menuBar.add(fileMenu);
//
//        // Create an "Edit" menu
//        JMenu editMenu = new JMenu("Edit");
//        editMenu.setMnemonic('E');
//
//        // Create a "Copy" menu item
//        JMenuItem copyMenuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
//        copyMenuItem.setText("Copy");
//        copyMenuItem.setMnemonic('C');
//        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke("control C"));
//        editMenu.add(copyMenuItem);
//
//        // Create a "Paste" menu item
//        pasteMenuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
//        pasteMenuItem.setText("Paste");
//        pasteMenuItem.setMnemonic('P');
//        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke("control V"));
//        editMenu.add(pasteMenuItem);

//        // Add the "Edit" menu to the menu bar
//        menuBar.add(editMenu);

        // Create a panel for the toolbar
        toolbarPanel = new JPanel();

        // Create a "Copy" button for the toolbar
        JButton copyButton = new JButton("Copy");
        copyButton.setToolTipText("Copy");
        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
                clipboard.setContents(stringSelection, null);
            }
        });
        toolbarPanel.add(copyButton);

        // Create a "Paste" button for the toolbar
        JButton pasteButton = new JButton("Paste");
        pasteButton.setToolTipText("Paste");
        pasteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the contents of the clipboard as a Transferable object
                Transferable contents = clipboard.getContents(null);

                // Check if the contents are available in the expected data flavor
                if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        // Get the contents of the clipboard as a String
                        String text = (String) ((Transferable) contents).getTransferData(DataFlavor.stringFlavor);
                        textArea.insert(text, textArea.getCaretPosition());
                    } catch (UnsupportedFlavorException | IOException ex) {
                        // Handle the exception here
                    }
                }
            }
        });
        toolbarPanel.add(pasteButton);

        // Create an "Erase All" button for the toolbar
        JButton eraseAllButton = new JButton("Erase All");
        eraseAllButton.setToolTipText("Erase All");
        eraseAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set the text area's text to an empty string
                textArea.setText("");
            }
        });
        toolbarPanel.add(eraseAllButton);



        // theme
        JToggleButton themeButton = new JToggleButton("Dark Mode");
        themeButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // dark mode
                    themeButton.setText("Light Mode");
                    // set colors for dark mode
                    // for example:
                    textArea.setBackground(Color.BLACK);
                    textArea.setForeground(Color.WHITE);
                } else {
                    // light mode
                    themeButton.setText("Dark Mode");
                    // set colors for light mode
                    // for example:
                    textArea.setBackground(Color.WHITE);
                    textArea.setForeground(Color.BLACK);
                }
            }
        });
        toolbarPanel.add(themeButton);

///


        // Create button
        saveButton = new JButton("Save As");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show "Save As" dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save As");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                int userSelection = fileChooser.showSaveDialog(TextEditor.this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(fileToSave);
                        writer.write(textArea.getText());
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Add components to content pane
        getContentPane().add(new JScrollPane(textArea));
        getContentPane().add(saveButton, "South");



        // Add the toolbar to the main panel
        add(toolbarPanel, BorderLayout.NORTH);

        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Set a border for the scroll pane
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(scrollPane.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        add(scrollPane, BorderLayout.CENTER);

        // Get the system clipboard
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Set the size of the window
        setSize(1000, 800);

        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

        // Show the window
        setVisible(true);
    }

    public static void main(String[] args) {
        new TextEditor();
    }
}


