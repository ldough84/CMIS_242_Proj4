/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @filename ATM_Machine
 * @author Luke Dougherty
 * @date 02/04/2018
 * This program implements a GUI simulation of an ATM Machine with four buttons 
 * for interacting with an account class.  It provides for input validation, 
 * and handles a service fee for every four transactions.
 */
import java.awt.*;
import javax.swing.*;

public class GUI_Display extends JFrame {
    
    static Account checkingAccount;
    static Account savingsAccount;
    private JButton withdrawButton, depositButton, transferButton, balanceButton;
    private JRadioButton checkingButton, savingsButton;
    protected JTextField inputField;
    
    public GUI_Display() {
        initComponents();
    }
    
    //Set up the GUI display
    private void initComponents() {
        setTitle("ATM Machine");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        
        //Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        Dimension dim = new Dimension(100,20);
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(dim);
        buttonPanel.add(withdrawButton, getConstraints(0,0));
        depositButton = new JButton("Deposit");
        depositButton.setPreferredSize(dim);
        buttonPanel.add(depositButton, getConstraints(1,0));
        transferButton = new JButton("Transfer");
        transferButton.setPreferredSize(dim);
        buttonPanel.add(transferButton, getConstraints(0,1));
        balanceButton = new JButton("Balance");
        balanceButton.setPreferredSize(dim);
        buttonPanel.add(balanceButton, getConstraints(1,1));
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.LINE_END;
       
        withdrawButton.addActionListener(e -> withdrawButtonClicked());
        depositButton.addActionListener(e -> depositButtonClicked());
        transferButton.addActionListener(e -> transferButtonClicked());
        balanceButton.addActionListener(e -> balanceButtonClicked());
        
        //Account Choice Panel
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ButtonGroup accountButtonGroup = new ButtonGroup();
        checkingButton = new JRadioButton("Checking");
        checkingButton.setSelected(true);
        savingsButton = new JRadioButton("Savings");
        accountButtonGroup.add(checkingButton);
        accountButtonGroup.add(savingsButton);
        accountPanel.add(checkingButton);
        accountPanel.add(savingsButton);
        
        //Input monetary amount panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputField = new JTextField();
        inputField.setPreferredSize(dim);
        inputField.setEditable(true);
        inputPanel.add(inputField);
        
        add(buttonPanel, BorderLayout.NORTH);
        add(accountPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 0, 10);
        c.gridx = x;
        c.gridy = y;
        return c;
    }
    
    private void withdrawButtonClicked() {
        String errorMsg = "";
        String accountType = accountTypeCheck();
        errorMsg += isNumeric(inputField.getText(), "Input");
        if (errorMsg.isEmpty()) {
            errorMsg += isIn20DollarIncrements(inputField.getText(), "Input");
        
            if (accountType.equals("Checking")) {
                try {
                    sufficientFundsCheck(inputField.getText(), checkingAccount);
                }
                catch (InsufficientFunds e) {
                errorMsg += "There is not enough money in the account";
                }
            } else if (accountType.equals("Savings")) {
                try {
                    sufficientFundsCheck(inputField.getText(), savingsAccount);
                }
                catch (InsufficientFunds e) {
                errorMsg += "There is not enough money in the account";
                }
            }
        }

        if (errorMsg.isEmpty()) {
            if (accountType.equals("Checking")) {
                boolean needsServiceCharge = checkingAccount.serviceCharge();
                checkingAccount.changeValueByAmount(-(Double.parseDouble(inputField.getText())));
                if (needsServiceCharge == true) {
                    displayServiceChargeAndBalance(checkingAccount);
                } else {
                    displaySuccessAndBalance(checkingAccount);
                }
            } else if (accountType.equals("Savings")) {
                boolean needsServiceCharge = savingsAccount.serviceCharge();
                savingsAccount.changeValueByAmount(-(Double.parseDouble(inputField.getText())));
                if (needsServiceCharge == true) {
                    displayServiceChargeAndBalance(savingsAccount);
                } else {
                    displaySuccessAndBalance(savingsAccount);
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, errorMsg, 
                    "Invalid Data", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void depositButtonClicked() {
        String errorMsg = "";
        errorMsg += isPresent(inputField.getText(), "Input");
        errorMsg += isNumeric(inputField.getText(), "Input");
        if (errorMsg.isEmpty()) {
            String accountType = accountTypeCheck();
            if (accountType.equals("Checking")) {
                checkingAccount.changeValueByAmount(Double.parseDouble(inputField.getText()));
                displaySuccessAndBalance(checkingAccount);
            } else if (accountType.equals("Savings")) {
                savingsAccount.changeValueByAmount(Double.parseDouble(inputField.getText()));
                displaySuccessAndBalance(savingsAccount);
            }    
        } else {
            JOptionPane.showMessageDialog(this, errorMsg, 
                    "Invalid Data", JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    private void transferButtonClicked() {
        String errorMsg = "";
        String accountType = accountTypeCheck();
        errorMsg += isPresent(inputField.getText(), "Input");
        errorMsg += isNumeric(inputField.getText(), "Input");
        if (errorMsg.isEmpty()) {
            if (accountType.equals("Checking")) {
                try {
                    sufficientFundsCheck(inputField.getText(), checkingAccount);
                }
                catch (InsufficientFunds e) {
                    errorMsg += "There is not enough money in the account";
                }
            }
            else if (accountType.equals("Savings")) { 
                try {
                    sufficientFundsCheck(inputField.getText(), savingsAccount);
                }
                catch (InsufficientFunds e) {
                    errorMsg += "There is not enough money in the account";
                }
            }
        }

        if (errorMsg.isEmpty()) {
            if (accountType.equals("Checking")) {
                savingsAccount.changeValueByAmount(-(Double.parseDouble(inputField.getText())));
                checkingAccount.changeValueByAmount(Double.parseDouble(inputField.getText()));
                displaySuccessAndBalance(checkingAccount);
            } else if (accountType.equals("Savings")) {
                savingsAccount.changeValueByAmount(Double.parseDouble(inputField.getText()));
                checkingAccount.changeValueByAmount(-(Double.parseDouble(inputField.getText())));
                displaySuccessAndBalance(savingsAccount);
            }    
        } else {
            JOptionPane.showMessageDialog(this, errorMsg, 
                    "Invalid Data", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    private void balanceButtonClicked() {
        String accountType = accountTypeCheck();
        if (accountType.equals("Checking")) {
            System.out.println("Retrieving checking balance");
            String value = Double.toString(checkingAccount.getValue());
            JOptionPane.showMessageDialog(this, "Balance: " + value, 
                    "Checking Account Balance", 
                    JOptionPane.PLAIN_MESSAGE);
        } else if (accountType.equals("Savings")) {
            System.out.println("Retrieving savings balance");
            String value = Double.toString(savingsAccount.getValue());
            JOptionPane.showMessageDialog(this, "Balance: " + value, 
                    "Savings Account Balance", 
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public String isPresent(String value, String name) {
        String msg = "";
        if (value.isEmpty()) {
            msg = name + " is required.\n";
        }
        return msg;
    }

    public String isNumeric(String value, String name) {
        String msg = "";
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            msg = name + " must be a valid number.\n";
        }
        return msg;
    }
    
    public String isIn20DollarIncrements(String value, String name) {
        String msg = "";
        if (Double.parseDouble(value) % 20 != 0) {
            msg = name + " must be in increments of 20.\n";
        }
        return msg;
    }
    
    public void sufficientFundsCheck(String value, Account account) throws InsufficientFunds {
        String msg = "";
        if (account.getValue() - Double.parseDouble(value) < 0) {
            throw new InsufficientFunds("There is not enough money in the account");
        }
    }
    
    public String accountTypeCheck() {
        String accountType = "";
        if (checkingButton.isSelected()) {
            accountType = "Checking";
        } else if (savingsButton.isSelected()) {
            accountType = "Savings";
        }
        return accountType;
    }   
    
    public void displaySuccessAndBalance(Account account) {
        JOptionPane.showMessageDialog(this, "New Value: " + Double.toString(account.getValue()), 
        "Success!", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void displayServiceChargeAndBalance(Account account) {
        JOptionPane.showMessageDialog(this, "Incurred Service Charge of $1.50\nNew Value: "
            + Double.toString(account.getValue()), 
            "Success!", JOptionPane.PLAIN_MESSAGE);        
    }
    
    public static void main(String arg[]) {
        checkingAccount  = new Account(100, "Checking");
        savingsAccount = new Account(100, "Savings");
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new GUI_Display();
        });
    }
}
