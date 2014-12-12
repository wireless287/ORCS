/*
CS530
12/5/14
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.JTabbedPane;
import java.util.Random;

public class Search extends JPanel {

    private JTextField findText;
    private JButton search;
    private DefaultListModel<String> model;
    private DefaultListModel<String> model2;
    private JTree tree;
    private JTree tree2;
    DefaultMutableTreeNode root;
    DefaultMutableTreeNode rootHistory;
    //create the child nodes
    DefaultMutableTreeNode rootSearch;
    DefaultMutableTreeNode keyWord;
    DefaultMutableTreeNode rootSearchHistory;
    DefaultMutableTreeNode keyWordHistory;
    double i;

    public Search() {
        //create search layout
        setLayout(new BorderLayout());
        JPanel searchPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.insets = new Insets(2, 2, 2, 2);
        searchPane.add(new JLabel("Find: "), gbc1);
        gbc1.gridx++;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.weightx = 1;
        findText = new JTextField(20);
        searchPane.add(findText, gbc1);
        gbc1.gridx++;
        gbc1.fill = GridBagConstraints.NONE;
        gbc1.weightx = 0;
        search = new JButton("Search");
        searchPane.add(search, gbc1);
        add(searchPane, BorderLayout.NORTH);
        model = new DefaultListModel<>();
        JList list = new JList(model);
        add(new JScrollPane(list));
        JTabbedPane jtp = new JTabbedPane();

        //create the root node
        root = new DefaultMutableTreeNode("Pending");
        rootHistory = new DefaultMutableTreeNode("History");
        //create the child nodes
        rootSearch = new DefaultMutableTreeNode("Search");
        //keyWord = new DefaultMutableTreeNode("");

        //create the tree by passing in the root node
        tree = new JTree(root);
        tree2 = new JTree(rootHistory);
        jtp.addTab("Pending", tree);
        jtp.addTab("History", tree2);

        jtp.setVisible(true);

        add(jtp, BorderLayout.WEST);

        ActionHandler handler = new ActionHandler();

        search.addActionListener(handler);
        findText.addActionListener(handler);
    }

    //Handle Search Button
    public class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.removeAllElements();
            String searchText = findText.getText();

            rootSearch = new DefaultMutableTreeNode("Search");
            root.add(rootSearch);
            keyWord = new DefaultMutableTreeNode(searchText);
            rootSearch.add(keyWord);
            root.add(rootSearch);
            Random randomGenerator = new Random();
            double randomInt = randomGenerator.nextInt(1000000000);

            while (i < randomInt) {
                i++;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(new File("search.txt")))) {

                String text = null;
                while ((text = reader.readLine()) != null) {
                    if (text.contains(searchText)) {
                        model.addElement(text);
                    }
                }
                keyWordHistory = new DefaultMutableTreeNode(model);
                rootSearchHistory = new DefaultMutableTreeNode("Search");
                rootSearchHistory.add(keyWordHistory);
                rootHistory.add(rootSearchHistory);

            } catch (IOException exp) {

                JOptionPane.showMessageDialog(Search.this, "Could not read file", "Error", JOptionPane.ERROR_MESSAGE);
                /*
                 FileWriter f; 
                 try {
                 f = new FileWriter("test.txt");
                 f.write("hello"); 
                 f.close(); 
                 } catch (IOException ex) {
                 Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
                 JOptionPane.showMessageDialog(Search.this, "Could not write file", "Error", JOptionPane.ERROR_MESSAGE);
                 }
                 */

            }
        }
    }
}
