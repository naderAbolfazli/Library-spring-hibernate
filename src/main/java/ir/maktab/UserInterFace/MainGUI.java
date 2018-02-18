/*
 * Created by JFormDesigner on Mon Nov 27 21:07:27 IRST 2017
 */

package ir.maktab.UserInterFace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * @author nader abolfazli
 */
@Component
public class MainGUI extends JFrame {

    @Autowired
    BookGUI bookGUI;

    @Autowired
    AuthorGUI authorGUI;

    public void run(){
        initComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                getContentPane().removeAll();
                super.windowClosed(e);
            }
        });
        setVisible(true);
    }

    private void ButtonActionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("book")) {
            bookGUI.run();
            dispose();
        }
        else if (e.getActionCommand().equals("author")) {
            authorGUI.run();
            dispose();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - nader abolfazli
        bookButton = new JButton();
        authorButton = new JButton();

        //======== this ========
        setTitle("Main Menu");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- bookButton ----
        bookButton.setText("Books");
        bookButton.setActionCommand("book");
        bookButton.addActionListener(e -> ButtonActionPerformed(e));
        contentPane.add(bookButton);
        bookButton.setBounds(25, 20, 100, bookButton.getPreferredSize().height);

        //---- authorButton ----
        authorButton.setText("Authors");
        authorButton.setActionCommand("author");
        authorButton.addActionListener(e -> ButtonActionPerformed(e));
        contentPane.add(authorButton);
        authorButton.setBounds(145, 20, 100, authorButton.getPreferredSize().height);

        contentPane.setPreferredSize(new Dimension(270, 40+ authorButton.getPreferredSize().height));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - nader abolfazli
    private JButton bookButton;
    private JButton authorButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
