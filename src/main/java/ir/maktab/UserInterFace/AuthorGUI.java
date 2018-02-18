/*
 * Created by JFormDesigner on Tue Nov 28 11:47:03 IRST 2017
 */

package ir.maktab.UserInterFace;

import ir.maktab.model.author.Author;
import ir.maktab.model.author.manager.AuthorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author nader abolfazli
 */
@Component
@Scope("prototype")
public class AuthorGUI extends JFrame {

    @Autowired
    private MainGUI mainGUI;

    @Autowired
    private AuthorManager authorManager;

    public void run() {
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainGUI.run();
                getContentPane().removeAll();
                super.windowClosed(e);
            }
        });
        setVisible(true);

    }

    private void ButtonActionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "getAll":
                table.setModel(authorManager.getAllAsModel());
                break;

            case "load":
                if (!authorCode.getText().isEmpty())
                    if (authorManager.exist(new Author(Long.parseLong(authorCode.getText())))) {
                        table.setModel(authorManager.getAsModel(new Author(Long.parseLong(authorCode.getText()))));
                    } else
                        JOptionPane.showMessageDialog(this, "Not Found!");
                else
                    JOptionPane.showMessageDialog(this, "Empty Field!");
                break;

            case "delete":
                if (!authorCode.getText().isEmpty()) {
                    if (JOptionPane.showConfirmDialog(this, "Are you sure?") == JOptionPane.OK_OPTION) {
                        if (authorManager.delete(Long.parseLong(authorCode.getText())) < 1)
                            JOptionPane.showMessageDialog(this, "Not Found!");
                        table.setModel(authorManager.getAllAsModel());
                    }
                } else
                    JOptionPane.showMessageDialog(this, "Empty Field!");
                break;

            case "edit":
                if (checkField() >= 0) {
                    Author a = new Author(Long.parseLong(authorCode.getText()), name.getText(), Integer.parseInt(age.getText()));
                    if (authorManager.update(a) < 1)
                        JOptionPane.showMessageDialog(this, "Not Found!");
                    table.setModel(authorManager.getAllAsModel());
                } else
                    JOptionPane.showMessageDialog(this, "Empty Fields!");
                break;

            case "add":
                Author a = null;
                if (checkField() == 1)
                    a = new Author(Long.parseLong(authorCode.getText()), name.getText()
                            , Integer.parseInt(age.getText()));
                else if (checkField() == 0)
                    a = new Author(Long.parseLong(authorCode.getText()), name.getText());

                if (a == null)
                    JOptionPane.showMessageDialog(this, "Empty Fields!");
                else if (authorManager.insert(a) < 1)
                    JOptionPane.showMessageDialog(this, "Already Exist");
                authorCode.setText("");
                name.setText("");
                age.setText("");
                table.setModel(authorManager.getAllAsModel());
                break;

            case "books":
                if (!authorCode.getText().isEmpty())
                    if (authorManager.exist(new Author(Long.parseLong(authorCode.getText())))) {
                        Author author = new Author(Long.parseLong(authorCode.getText()));
                        JOptionPane.showMessageDialog(this, authorManager.getBooks(author));
                    } else
                        JOptionPane.showMessageDialog(this, "Not Found!");
                else
                    JOptionPane.showMessageDialog(this, "Empty Field!");
                break;
        }

    }

    private int checkField() {
        if (!authorCode.getText().isEmpty() && !name.getText().isEmpty() && !age.getText().isEmpty())
            return 1;
        else if (!authorCode.getText().isEmpty() && !name.getText().isEmpty() && age.getText().isEmpty())
            return 0;
        else
            return -1;
    }

    public void selectionChanged(ListSelectionEvent e) {
        if (table.getSelectedRow() >= 0) {
            authorCode.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
            name.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
            age.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - nader abolfazli
        scrollPane1 = new JScrollPane();
        table = new JTable();
        getAll = new JButton();
        load = new JButton();
        delete = new JButton();
        edit = new JButton();
        authorCode = new HintTextField("authorCode");
        name = new HintTextField("Name");
        age = new HintTextField("Age");
        books = new JButton();
        addButton = new JButton();

        //======== this ========
        setTitle("Author Menu");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            model = authorManager.getAllAsModel();
            scrollPane1.setViewportView(table);
            table.setModel(model);

            select = table.getSelectionModel();
            select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            select.addListSelectionListener(e -> selectionChanged(e));
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(20, 20, 300, 195);

        //---- getAll ----
        getAll.setText("get All");
        getAll.setActionCommand("getAll");
        getAll.addActionListener(e -> ButtonActionPerformed(e));
        contentPane.add(getAll);
        getAll.setBounds(20, 225, 75, getAll.getPreferredSize().height);

        //---- load ----
        load.setText("Load");
        load.setActionCommand("load");
        load.addActionListener(e -> ButtonActionPerformed(e));
        contentPane.add(load);
        load.setBounds(95, 225, 75, load.getPreferredSize().height);

        //---- delete ----
        delete.setText("Delete");
        delete.setActionCommand("delete");
        delete.addActionListener(e -> ButtonActionPerformed(e));
        contentPane.add(delete);
        delete.setBounds(170, 225, 75, delete.getPreferredSize().height);

        //---- edit ----
        edit.setText("Edit");
        edit.setActionCommand("edit");
        edit.addActionListener(e -> ButtonActionPerformed(e));
        contentPane.add(edit);
        edit.setBounds(245, 225, 75, edit.getPreferredSize().height);

        //---- TextFields ----
        contentPane.add(authorCode);
        authorCode.setBounds(330, 40, 55, authorCode.getPreferredSize().height);
        contentPane.add(name);
        name.setBounds(330, 80, 55, name.getPreferredSize().height);
        contentPane.add(age);
        age.setBounds(330, 120, 55, age.getPreferredSize().height);

        // ---- booksButton ----
        books.setText("Books");
        books.setActionCommand("books");
        books.addActionListener(e -> ButtonActionPerformed(e));
        contentPane.add(books);
        books.setBounds(330, 160, 55, 50);

        //---- addButton ----
        addButton.setText("Add");
        addButton.setActionCommand("add");
        addButton.addActionListener(e -> ButtonActionPerformed(e));
        contentPane.add(addButton);
        addButton.setBounds(330, 215, addButton.getPreferredSize().width, 42);
        getRootPane().setDefaultButton(addButton);

        contentPane.setPreferredSize(new Dimension(400, 270));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - nader abolfazli
    private JScrollPane scrollPane1;
    private JTable table;
    private JButton getAll;
    private JButton load;
    private JButton delete;
    private JButton edit;
    private JTextField authorCode;
    private JTextField name;
    private JTextField age;
    private JButton books;
    private JButton addButton;
    private ListSelectionModel select;
    private DefaultTableModel model;


    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
