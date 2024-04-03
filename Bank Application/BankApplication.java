import java.sql.*;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

class Bank {
    static Scanner s1 = new Scanner(System.in);
    private static String acno, cname, actype;
    private static int balance, amount;
    static Connection con = null;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bank?user=root&password=");
        } catch (Exception e) {
        }
    }

    // RANDOM ACCOUNT NUMBER
    public static String generateAcno() {
        Random r1 = new Random();
        int a = r1.nextInt(1000, 9999);
        return "HDFC" + a;
    }

    public static void openAccount() {
        acno = generateAcno();
        System.out.println("Account number : " + acno);
        System.out.println("Enter name : ");
        cname = s1.next();
        System.out.println("Account type : ");
        actype = s1.next();
        System.out.println("Enter balance : ");
        balance = s1.nextInt();
        try {
            PreparedStatement ps = con.prepareStatement("insert into customer values (?,?,?,?)");
            ps.setString(1, acno);
            ps.setString(2, cname);
            ps.setString(3, actype);
            ps.setInt(4, balance);
            int a = ps.executeUpdate();
            if (a == 1) {
                System.out.println("Record updated");
            } else {
                System.out.println("Record not updated");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void depositMoney() {
        System.out.println("Enter account Number : ");
        acno = s1.next();
        System.out.println("Enter the amount to be deposit : ");
        amount = s1.nextInt();
        try {
            PreparedStatement ps = con.prepareStatement("update customer set balance = balance + ? where acno=?");
            ps.setInt(1, amount);
            ps.setString(2, acno);
            int a = ps.executeUpdate();
            if (a == 1) {
                System.out.println("amount updated");
            } else {
                System.out.println("account not found");
            }
        } catch (Exception e) {
        }
    }

    public static void withdrawMoney() {
        System.out.println("Enter account Number : ");
        acno = s1.next();
        System.out.println("Enter the amount to be withdraw : ");
        amount = s1.nextInt();
        try {
            PreparedStatement ps = con.prepareStatement("update customer set balance = balance - ? where acno=?");
            ps.setInt(1, amount);
            ps.setString(2, acno);
            int a = ps.executeUpdate();
            if (a == 1) {
                System.out.println("amount updated");
            } else {
                System.out.println("account not found");
            }
        } catch (Exception e) {
        }
    }

    public static void tranferMoney() {
        System.out.println("Enter account 1 (nikalne vala): ");
        String acno1 = s1.next();
        System.out.println("Enter account 2 (dalne vala): ");
        String acno2 = s1.next();
        System.out.println("Enter amount : ");
        amount = s1.nextInt();
        try {
            PreparedStatement ps = con.prepareStatement("update customer set balance = balance - ? where acno=?");
            ps.setInt(1, amount);
            ps.setString(2, acno1);
            int a = ps.executeUpdate();
            if (a == 1) {
                PreparedStatement ps2 = con.prepareStatement("update customer set balance = balance + ? where acno=?");
                ps2.setInt(1, amount);
                ps2.setString(2, acno2);
                int b = ps2.executeUpdate();
                if (b == 1) {
                    System.out.println("amount updated");
                } else {
                    System.out.println("account not found");
                }
            } else {
                System.out.println("account not found");
            }
        } catch (Exception e) {
        }
    }

    public static void searchAccount() {
        System.out.println("Enter account number : ");
        acno = s1.next();
        try {
            PreparedStatement ps = con.prepareStatement("select * from customer where acno=?");
            ps.setString(1, acno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Account Number : " + rs.getString(1));
                System.out.println("Name : " + rs.getString(2));
                System.out.println("Account Type : " + rs.getString(3));
                System.out.println("Balance : " + rs.getInt(4));
                System.out.println("==============================================");
            } else {
                System.out.println("Account not found");
            }
        } catch (Exception e) {
        }
    }

    public static void displayAll() {
        try {
            PreparedStatement ps = con.prepareStatement("select * from customer");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Account Number : " + rs.getString(1));
                System.out.println("Name : " + rs.getString(2));
                System.out.println("Account Type : " + rs.getString(3));
                System.out.println("Balance : " + rs.getInt(4));
                System.out.println("==============================================");
            }
        } catch (Exception e) {
        }
    }

    public static void closeAccount() {
        System.out.println("Enter account number : ");
        acno = s1.next();
        try {
            PreparedStatement ps = con.prepareStatement("delete from customer where acno=?");
            ps.setString(1, acno);
            int a = ps.executeUpdate();
            if (a == 1) {
                System.out.println("Account deleted");
            } else {
                System.out.println("Account not found");
            }
        } catch (Exception e) {
        }
    }

}

public class BankApplication {
    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        Bank bank = new Bank();
        int k, choice;
        do {
            System.out.println("Main menu");
            System.out.println("1. open account");
            System.out.println("2. deposit money");
            System.out.println("3. withdraw money");
            System.out.println("4. transfer money");
            System.out.println("5. search account");
            System.out.println("6. display all account");
            System.out.println("7. close account");
            System.out.println("8. exit");
            System.out.println("Enter choice : ");
            choice = s1.nextInt();
            switch (choice) {
                case 1:
                    bank.openAccount();
                    break;
                case 2:
                    bank.depositMoney();
                    break;
                case 3:
                    bank.withdrawMoney();
                    break;
                case 4:
                    bank.tranferMoney();
                    break;
                case 5:
                    bank.searchAccount();
                    break;
                case 6:
                    bank.displayAll();
                    break;
                case 7:
                    bank.closeAccount();
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            System.out.println("do you want to cont.. press 1 for no : ");
            k = s1.nextInt();
        } while (k != 1);
    }
}
