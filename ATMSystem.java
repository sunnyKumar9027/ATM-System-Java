import java.util.Scanner;

interface Receipt {
    void printReceipt(String type);
}

class ATM {
    public static final double USD = 94.01;
    protected Account account = new Account(5000);
    public int demoOtp = 1234;

    class Account {
        private double balance;

        Account(double balance) {
            this.balance = balance;
        }

        void withdraw(double amount) {
            balance -= amount;
            System.out.println(" Withdrawal successful. Remaining balance :Rs. " + balance);
        }

        void deposit(Scanner sc) {
            System.out.println(" Enter amount in INR to deposit");
            double amount = sc.nextDouble();
            sc.nextLine();
            if (amount > 0) {
                balance += amount;
                System.out.println("Rs: " + amount + " Deposit successful. New balance : Rs." + balance);
            } else
                System.out.println("please enter valid amount not " + amount);
        }

        double getBalance() {
            return balance;
        }
    }

    static class CurrencyConverter {
        static void convertToUSD(ATM atm) {
            double bal = atm.account.getBalance();
            System.out.println("Converted amount INR to USD : Rs." + bal + " = $" + bal / USD);
        }
    }

    void generateReceipt() {
        Receipt receipt = new Receipt() {
            @Override
            public void printReceipt(String type) {
                System.out.println("_____Transaction Receipt __________");
                System.out.println("Transaction type: " + type);
                System.out.println("Remaining balance: Rs. " + account.getBalance());
                System.out.println("__________________________");
            }
        };
        receipt.printReceipt("Balance Inquiry");
    }

    void withdrawMoney(Scanner scanner) {
        System.out.println("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        if (amount <= 0 || amount > account.getBalance())
            System.out.println(" Invalid amount or Insufficient Balance!");
        // Local inner class -otp verification
        else {
            class OTPVerifier {
                boolean verifyOTP() {
                    System.out.println(" Enter OTP(" + demoOtp + " for demo): ");
                    int otp = scanner.nextInt();
                    return otp == demoOtp;
                }
            }
            OTPVerifier otpVerifier = new OTPVerifier();
            if (otpVerifier.verifyOTP())
                account.withdraw(amount);
            else
                System.out.println(" Incorrect OTP! Transaction cancelled. ");
        }
    }

    void otpUpdate(Scanner sc1) {
        class UpdateOtp {
            public void update() {
                System.out.println(" Enter custom OTP for update : ");
                int otp = sc1.nextInt();
                sc1.nextLine();
                demoOtp = otp;
                System.out.println(" OTP updated successfully : ");
            }
        }
        UpdateOtp newotp = new UpdateOtp();
        newotp.update();

    }
}

public class ATMSystem {
    public static void main(String args[]) {
        ATM atm = new ATM();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("________MSI Academy ATM Machine ________");
            System.out.println("1.Withdraw money ");
            System.out.println("2.Deposit money ");
            System.out.println("3.Check balance ");
            System.out.println("4.Convert to USD ");
            System.out.println("5.Enter new custom otp");
            System.out.println("6.Exit ");
            System.out.println("Choose an option :");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> atm.withdrawMoney(sc);
                case 2 -> atm.account.deposit(sc);
                case 3 -> atm.generateReceipt();
                case 4 -> ATM.CurrencyConverter.convertToUSD(atm);
                case 5 -> atm.otpUpdate(sc);
                case 6 -> {
                    System.out.println("Thank you for using the Console based ATM Machine!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice! Try again ");
            }
        }
    }
}