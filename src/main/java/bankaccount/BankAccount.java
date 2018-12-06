/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankaccount;

import java.text.NumberFormat;

/**
 *
 * @author Emmett
 */
public class BankAccount {

    /**
     * @param args the command line arguments
     */
    
    private int accountNumber;
    private int PIN;
    private double openingBalance;
    private double overdraft;
    
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    
    public BankAccount(){
        accountNumber = 0;
        PIN = 0;
        openingBalance = 0.0;
        overdraft = 0.0;  
    }   
    
    public BankAccount(double balance){
        this.openingBalance = balance;
    }
    
    public BankAccount(int accountNumber, int PIN, double openingBalance, double overdraft){
        this.accountNumber = accountNumber;
        this.PIN = PIN;
        this.openingBalance = openingBalance;
        this.overdraft = overdraft;
    } 
    public int getAccountNumber(){
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber){
        this.accountNumber = accountNumber;
    }
     
    public int getPIN(){
        return PIN;
    }
    public void setPIN(int PIN){
        this.PIN = PIN;
    }
    /**
     * @return the balance
     */
    public double getBalance(){
        return openingBalance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }
    
        /**
     * @return the balance
     */
    public double getOverdraft() {
        return overdraft;
    }

    /**
     * @param balance the balance to set
     */
    public void setOvedraft(double overdraft) {
        this.overdraft = overdraft;
    }
    
    @Override
    public String toString() {
        return this.getAccountNumber() + "   " +
               this.getOverdraft() + "   " +
               this.formatter.format(getBalance());
    } 
    
    public void debit(double debitAmount){
        this.openingBalance -= debitAmount;  //this gets the balance and does a calc on it
    }//balance = balance - debitAmount

}
