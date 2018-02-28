/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmis242_project4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class GUI_Display extends JFrame {

    public GUI_Display() {
        initComponents();
    }
    
    //Create text fields, ratdio buttons, and compute button
    private JButton processRequestButton;
    private JTextField idField, nameField, majorField;
    private JComboBox dropDown;
    
    private void initComponents() {
        setTitle("Project 4");
        setSize(340, 220);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);  

        //Set up GridBagLayout defaults for use below
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;        
        Dimension dim = new Dimension(150,20);
        Insets labelInsets = new Insets(10, 2, 0, 10);
        Insets textBoxInsets = new Insets (10, 5, 0, 2);
        
        //Create editable text fields and labels
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        idField = new JTextField();
        idField.setPreferredSize(dim);
        idField.setEditable(true);
        nameField = new JTextField();
        nameField.setPreferredSize(dim);
        nameField.setEditable(true);
        majorField = new JTextField();
        majorField.setPreferredSize(dim);
        majorField.setEditable(true);
        inputPanel.add(new JLabel("Id: "), getConstraints(0, 0, labelInsets));
        inputPanel.add(idField, getConstraints(1, 0, textBoxInsets));
        inputPanel.add(new JLabel("Name: "), getConstraints(0, 1, labelInsets));
        inputPanel.add(nameField, getConstraints(1, 1, textBoxInsets));
        inputPanel.add(new JLabel("Major: "), getConstraints(0, 2, labelInsets));
        inputPanel.add(majorField, getConstraints(1, 2, textBoxInsets));
        
        //Create combo box drop down menu and Process Request Button    
        String[] actions = {"Insert", "Delete", "Find", "Update"};
        dropDown = new JComboBox(actions);
        dropDown.setPreferredSize(dim);
        dropDown.addActionListener(event -> dropDownItemChanged());
        processRequestButton = new JButton("Process Request");
        inputPanel.add(new JLabel("Choose Selection: "), getConstraints(0,3, labelInsets));
        inputPanel.add(dropDown, getConstraints(1, 3, textBoxInsets));
        inputPanel.add(processRequestButton, getConstraints(0, 4, new Insets(10, 0, 10, 10)));
      
        //Set visible
        add(inputPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    private GridBagConstraints getConstraints(int x, int y, Insets i) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = i;
        c.gridx = x;
        c.gridy = y;
        return c;
    }
    
    public void dropDownItemChanged() {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
         });
    }
    
}
