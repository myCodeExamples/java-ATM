/*
 * This program functions as a simple ATM machine
 * The following tests have been sucessful:
 *	The ATM cannot dispense more money than it holds: tested amount > 2000
	The ATM has a starting number of notes €5, €10, €20, €50 which decrease as customers withdraw cash
	The customer cannot withdraw more funds then they have access to: tested for the balance + overdraft facility.
	The ATM should not dispense funds if the pin is incorrect: tested for both accounts account number + PIN
	The ATM should not expose the customer balance if the pin is incorrect: the customer has no access to functions when not logged in successfully
	The ATM should only dispense the exact amounts requested: tested for amounts including non multiples of 5 Euro notes
	The ATM should dispense the minimum number of notes per withdrawal: the ATM dispenses the minimum number of notes which are currently available in the machine
	The ATM should initialize with €2000 made up of 20 x €50s, 30 x €20s, 30 x €10s and 20 x €5s: the ATM has been initialised with these amounts
	The ATM should also initialize with the following accounts: the ATM is initialised with the two accounts.  The second accounts opening balance is larger than specified so that the ATM amount of 2000 can be tested
Input / Output
	The user can request a balance check and also gives the maximum withdrawal amount.
	The user can request a withdrawal and if sufficient funds are available full details of the notes that would be dispensed along with remaining balance is given on the screen.
	User is provided with meaningful error messages.
 * 
 */
package bankaccount;

import java.text.NumberFormat;
import java.util.Scanner;

/**
 *
 * @author Emmett
 */
public class AccountTester {

    static NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private static BankAccount accountList[] = new BankAccount[2];
    private static int ATMbalance = 2000;
    private static int noOfFifties = 20;
    private static int noOfTwenties = 30;
    private static int noOfTens = 30;
    private static int noOfFives = 20;
    private static int index = -1;
    private static int attempts = 1;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //These accounts would not be initialised here but taken from a file that would be updated for each transaction
        BankAccount JohnDoeAccount = new BankAccount(123456789, 1234 , 800 , 200); //Account Number, PIN, Opening Balance, Overdraft
        accountList[0] = JohnDoeAccount;
        
        BankAccount JoeBloggsAccount = new BankAccount(987654321, 4321, 10230, 150); //Account Number, PIN, Opening Balance, Overdraft
        accountList[1] = JoeBloggsAccount;
        
        logIn();
    }
 
    public static void logIn() {//log in to the ATM machine using both Account number and PIN
        Scanner input = new Scanner(System.in);
        index = -1;
        int attemptsLeft = 3;
        do{
            System.out.println("Enter your account Number and then press enter: ");
            int accountNum = input.nextInt();
            System.out.println("Enter your Pin number and then press enter: ");
            int PIN = input.nextInt();

            for (int i = 0; i < accountList.length; i++) {
                if((accountList[i].getAccountNumber() == accountNum) && (accountList[i].getPIN() == PIN)) {
                    index = i;
                    mainMenu();
                    break;
                }
            }
                
            if(index == -1){
                System.out.println("Incorrect Account number or PIN, please try again!");
                attempts++;
                attemptsLeft--;
                System.out.println("Number of attempts left: " + attemptsLeft);
                
            }
            if(attempts > 3){
                System.out.println("You have exceeded your number of attempts.  Your account has been locked.  Please contact your bank.");
                System.exit(0);
                break;
            }
 
        }while(index == -1 || attempts < 2);
    }//end Login
    
    
    
      //main menu method
public static void mainMenu() {
    Scanner input = new Scanner(System.in);
    System.out.println("Main menu" + "\n1:withdraw cash" + "\n2:check balance" + "\n3:exit" + "\nMake your selection and press enter");
    int menuChoice = input.nextInt(); 
    processRequest(menuChoice);
}//end main menu

public static void processRequest(int menuChoice){
    Scanner input = new Scanner(System.in);
    switch(menuChoice){
        case 1://the customer chose to withdraw cash        
            System.out.println("Enter an amount to withdraw ");
            int amount = input.nextInt();
            if(ATMbalance < amount){//check to see if the ATM machine has enough funds to give to the customer
                System.out.println("there is insufficient funds in this ATM for the amount you wish to withdraw.  Max withdrawal is: " + ATMbalance);
                mainMenu();
            }
            if(amount > (accountList[index].getBalance() + accountList[index].getOverdraft())){//check to see if the customer has sufficient funds in their account + their overdraft facility
                System.out.println("The amount exceeds your available funds");
                System.out.println("Maximum withdrawal is: " + (accountList[index].getBalance() + accountList[index].getOverdraft()));
                mainMenu();
            }
            if(amount % 5 >0){//ensure the amount chosen is multiples of 5 Euro, i.e. the machine does not give coins only cash notes
                System.out.println("The amount must be multiples of 5 Euro notes!");
                mainMenu();
            }else{//calculate the notes to give the customer according to how many notes are available. i.e. the number of 50, 20, 10 and 5 cash notes in the machine.  These amounts are reduced as cash is withdrawn.
                int remainder =0;
                accountList[index].debit(amount); 
                int dispenseFifties = amount / 50;
                if(dispenseFifties > noOfFifties){
                    dispenseFifties = noOfFifties;
                    noOfFifties = 0;
                    remainder = amount - (dispenseFifties*50);
                }else{
                    remainder = amount%50;
                    noOfFifties = noOfFifties - dispenseFifties;
                }
                int dispenseTwenties = remainder/20;
                if(dispenseTwenties > noOfTwenties){
                    dispenseTwenties = noOfTwenties;
                    noOfTwenties = 0;
                    remainder = remainder - (dispenseTwenties*20);
                }else{
                    remainder = remainder%20;
                    noOfTwenties = noOfTwenties - dispenseTwenties;
                }
                
                int dispenseTens = remainder/10;
                if(dispenseTens > noOfTens){
                    dispenseTens = noOfTens;
                    noOfTens = 0;
                    remainder = remainder - (dispenseTens*10);
                }else{
                    remainder = remainder%10;
                    noOfTens = noOfTens - dispenseTens;
                }   
                
                int dispenseFives = remainder/5;
                if(dispenseFives >= noOfFives){
                    dispenseFives = noOfFives;
                    noOfFives = 0;
                }else{
                    noOfFives = noOfFives - dispenseFives;
                }
                ATMbalance = ATMbalance - amount;
                    
                
                //int calcTens = (amount%50);
                System.out.println("You have withrawn " + amount + " Euro. " + dispenseFifties + " 50s, " + dispenseTwenties + " 20s, " + dispenseTens + " 10s, " + dispenseFives + " 5s.");
                System.out.println("Your balance is: " + accountList[index].getBalance());
                mainMenu();
            } 
        break; 

        case 2:
            System.out.println("Your balance is: " + accountList[index].getBalance());//good to go
            System.out.println("Maximum withdrawal is: " + (accountList[index].getBalance() + accountList[index].getOverdraft()) + " which includes your overdraft facility.");
            mainMenu();
        break; 

        case 3:
            System.out.println("Thanks for banking with us. Good Bye!\n\n\n");
            logIn();
        break;

        default:
            System.out.println("Invalid option:\n\n"); 
            mainMenu();
        break;
    }
  }//end process request
}//end AccountTester
