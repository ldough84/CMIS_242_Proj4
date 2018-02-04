/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class InsufficientFunds extends Exception {
    InsufficientFunds() {}
    
    public InsufficientFunds(String message) {
        super(message);
    }
}
