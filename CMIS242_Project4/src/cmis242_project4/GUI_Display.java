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
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class GUI_Display extends JFrame {

    public GUI_Display() {
        initComponents();
        GUI_Display.studentDatabase = new HashMap<>();
    }
    
    //Create text fields, ratdio buttons, and compute button
    private JButton processRequestButton;
    private JTextField idField, nameField, majorField;
    private JComboBox dropDown;
    private static HashMap<Integer, Student> studentDatabase;
    
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
        processRequestButton = new JButton("Process Request");
        processRequestButton.addActionListener(event -> processRequest());
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
    
    private void processRequest() {
        String userChoice = (String) dropDown.getSelectedItem();
        if  (userChoice.equals("Insert")) {
            performInsert();
        }
        else if  (userChoice.equals("Delete")) {
            performDelete();
        }
        else if  (userChoice.equals("Find")) {
            performFind();
        }
        else if  (userChoice.equals("Update")) {
            performUpdate();
        }
    }
    
    private void performInsert() {
        try {
            Integer studentID = Integer.parseInt(idField.getText());
        
 
            if (studentDatabase.containsKey(studentID)) {
                JOptionPane.showMessageDialog(this, 
                    "Student ID " + studentID.toString() + " is already in the database.", 
                    "Database Error: ", 
                    JOptionPane.PLAIN_MESSAGE);
            }
            else {
                String name = nameField.getText();
                String major = majorField.getText();
                Student newStudent = new Student(name, major, 0, 0);
                studentDatabase.put(studentID, newStudent);
                JOptionPane.showMessageDialog(this, 
                    "Student ID " + studentID.toString() + " added to database "
                            + "with:\nName: " + name + "\nMajor: " + major, 
                    "Success: ", 
                    JOptionPane.PLAIN_MESSAGE);
            }
        }
        catch (NumberFormatException e) {
            idError();
        }
    }
    
    private void performDelete() {
        try {
            Integer studentID = Integer.parseInt(idField.getText());
        
            if (!checkExists(studentID)) {
                dbError();
            }
            else {
                studentDatabase.remove(studentID);
                JOptionPane.showMessageDialog(this, 
                    "Student ID " + studentID.toString() + " removed from database.", 
                    "Success: ", 
                    JOptionPane.PLAIN_MESSAGE);
            }
        }
        catch (NumberFormatException e) {
            idError();
        }
    }
    
    private void performFind() {
        try {
            Integer studentID = Integer.parseInt(idField.getText());       
            if (!checkExists(studentID)) {
                dbError();
            }
            else {
                Student s = studentDatabase.get(studentID);
                studentDatabase.get(studentID);
                JOptionPane.showMessageDialog(this, 
                "Student ID: " + studentID.toString() + "\n " + s.toString(),
                    "Student found: ", 
                    JOptionPane.PLAIN_MESSAGE);
            }
        }
        catch (NumberFormatException e) {
                idError();
        }
    }
    
    private void performUpdate() {
        try {
            Integer studentID = Integer.parseInt(idField.getText());
               
            if (!checkExists(studentID)) {
                dbError();
            }
            else {
                getOptionsAndUpdateStudent(studentID);
            }
        }
        catch (NumberFormatException e) {
            idError();
        }
    }
        
    private boolean checkExists(int dbkey) {
        if (!studentDatabase.containsKey(dbkey)) {return false;} 
        else {return true;}
    }
    
    private void dbError() {
        JOptionPane.showMessageDialog(this, 
                "Student ID is not in the database.", 
                "Database Error: ", 
                JOptionPane.INFORMATION_MESSAGE);        
    }
    
    private void idError() {
        JOptionPane.showMessageDialog(this, 
                "Not a valid ID. Please input a number", 
                "Database Error: ", 
                JOptionPane.ERROR_MESSAGE);   
    }
    
    private void getOptionsAndUpdateStudent(int studentID) {
        Student s = studentDatabase.get(studentID);
        String[] grades= {"A","B","C","D","F"};
        String[] credits= {"3","6"};

        
        JPanel dialogPanel = new JPanel();
        JPanel dialogPanel2 = new JPanel();
        

        //create a JOptionPane
        Object[] options = new Object[]{};
        JOptionPane jopGrade = new JOptionPane();
        Object gradeInput = JOptionPane.showInputDialog(dialogPanel, "Choose grade:", //parentComponent, message
                                        null, //Title of window
                                        JOptionPane.QUESTION_MESSAGE, //messageType
                                        null, grades, null); //icon, selectionValues, initialSelectionValue
        JOptionPane jopCredit = new JOptionPane();
        Object creditInput = JOptionPane.showInputDialog(dialogPanel2, "Choose credits:", //parentComponent, message
                                        null, //Title of window
                                        JOptionPane.QUESTION_MESSAGE, //messageType
                                        null, credits, null);
        System.out.println(gradeInput.toString());
        System.out.println(creditInput.toString());
        //add combos to JOptionPane
        dialogPanel.setVisible(true);
        dialogPanel2.setVisible(true);
        double credit = Double.parseDouble(creditInput.toString());
        String grade = gradeInput.toString();
        switch (grade) {
            case "A":
                s.courseComplete(credit, 12.0);
                break;
            case "B":
                s.courseComplete(credit, 9.0);
                break;
            case "C":
                s.courseComplete(credit, 6.0);
                break;
            case "D":
                s.courseComplete(credit, 3.0);
                break;
            case "F":
                s.courseComplete(credit, 0.0);
                break;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new GUI_Display();
         });
    }
    
}
