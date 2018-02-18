

package ir.maktab.UserInterFace;

import ir.maktab.model.author.manager.AuthorManager;
import ir.maktab.model.book.Book;
import ir.maktab.model.book.manager.BookManager;
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
public class BookGUI extends JFrame {
    @Autowired
    private MainGUI mainGUI;

    @Autowired
    private BookManager bookManager;

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
                table.setModel(bookManager.getAllAsModel());
                break;
            case "load":
                if (!isbn.getText().isEmpty())
                    if (bookManager.exist(new Book(Long.parseLong(isbn.getText())))) {
                        table.setModel(bookManager.getAsModel(new Book(Long.parseLong(isbn.getText()))));
                    } else
                        JOptionPane.showMessageDialog(this, "Not Found!");
                else
                    JOptionPane.showMessageDialog(this, "Empty Field!");

                break;
            case "delete":
                if (!isbn.getText().isEmpty()) {
                    if (JOptionPane.showConfirmDialog(this, "Are you sure?") == JOptionPane.OK_OPTION) {
                        if (bookManager.delete(Long.parseLong(isbn.getText())) < 1)
                            JOptionPane.showMessageDialog(this, "Not Found!");
                        table.setModel(bookManager.getAllAsModel());
                    }
                } else
                    JOptionPane.showMessageDialog(this, "Empty Field!");
                break;
            case "edit":
                if (checkField() >= 0) {
                    Book b = new Book(Long.parseLong(isbn.getText()), title.getText()
                            , Integer.parseInt(pageNumber.getText()), isReference.isSelected() ? 1 : 0
                            , authorManager.load(Long.parseLong(author_code.getText())));
                    if (bookManager.update(b) < 1)
                        JOptionPane.showMessageDialog(this, "Not Found!");
                    table.setModel(bookManager.getAllAsModel());

                } else
                    JOptionPane.showMessageDialog(this, "Empty Fields!");
                break;
            case "add":
                Book b = null;
                if (checkField() == 1)
                    b = new Book(Long.parseLong(isbn.getText()), title.getText()
                            , Integer.parseInt(pageNumber.getText()), isReference.isSelected() ? 1 : 0
                            , authorManager.load(Long.parseLong(author_code.getText())));
                else if (checkField() == 0)
                    b = new Book(Long.parseLong(isbn.getText()), title.getText(), isReference.isSelected() ? 1 : 0
                            , authorManager.load(Long.parseLong(author_code.getText())));

                if (b == null)
                    JOptionPane.showMessageDialog(this, "Empty Fields!");
                else if (bookManager.insert(b) < 1)
                    JOptionPane.showMessageDialog(this, "Already Exist");
                table.setModel(bookManager.getAllAsModel());
                break;
        }

    }

    private int checkField() {
        if (!isbn.getText().isEmpty() && !title.getText().isEmpty() && !author_code.getText().isEmpty()
                && !pageNumber.getText().isEmpty())
            return 1;
        else if (!isbn.getText().isEmpty() && !title.getText().isEmpty() && !author_code.getText().isEmpty()
                && pageNumber.getText().isEmpty())
            return 0;
        else
            return -1;
    }

    public void selectionChanged(ListSelectionEvent e) {
        if (table.getSelectedRow() >= 0) {
            isbn.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
            title.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
            pageNumber.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
            author_code.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
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
        isbn = new HintTextField("isbn");
        title = new HintTextField("Name");
        pageNumber = new HintTextField("Pages");
        isReference = new JRadioButton("IsReference");
        author_code = new HintTextField("authCode");
        addButton = new JButton();

        //======== this ========
        setTitle("Book Menu");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            model = bookManager.getAllAsModel();
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

        contentPane.add(isbn);
        isbn.setBounds(330, 25, 55, isbn.getPreferredSize().height);
        contentPane.add(title);
        title.setBounds(330, 65, 55, title.getPreferredSize().height);
        contentPane.add(isReference);
        isReference.setBounds(330, 105, 55, isReference.getPreferredSize().height);
        contentPane.add(pageNumber);
        pageNumber.setBounds(330, 145, 55, pageNumber.getPreferredSize().height);
        contentPane.add(author_code);
        author_code.setBounds(330, 185, 55, author_code.getPreferredSize().height);

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
    private JTextField isbn;
    private JTextField title;
    private JTextField pageNumber;
    private JRadioButton isReference;
    private JTextField author_code;
    private JButton addButton;
    private ListSelectionModel select;
    private DefaultTableModel model;


    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

