/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class Account {
    
    private double value;
    private String type;
    protected static int transactionCount = 0;
    
    public Account(double value, String type) {
        this.value = value;
        this.type = type;
    }
    
    public double getValue() {
        return value;
    }
    public String getType() {
        return this.type;
    }
    
    public double changeValueByAmount(double amount) {
        value = value + amount;
        return value;
    }
    
    public boolean serviceCharge() {
        this.transactionCount += 1;
        if (this.transactionCount % 4 == 0) {
            this.value -= 1.5;
            return true;
        } else {
            return false;
        }
    }

}
