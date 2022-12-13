package bank;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
public class bankManagement { // these class provides all
                              // bank method
    private static final int NULL = 0;
    static Connection con = connection.getConnection();
    static String sql = "";
    public static boolean createAccount(String name,int passCode){
            // create account function
        try {
            // validation
            if (name == "" || passCode == NULL) {
                System.out.println("All Field Required!");
                return false;
            }
            // query
            
            Statement st = con.createStatement();
            sql = "INSERT INTO customer(cname,balance,pass_code) values('"+ name + "',1000," + passCode + ")";
            // Execution
            if (st.executeUpdate(sql) == 1) {
                System.out.println(name + ", Now You Login!");
                return true;
            }
            // return
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username Not Available!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean loginAccount(String name, int passCode) // login method
    {
        try {
            // validation
            if (name == "" || passCode == NULL) {
                System.out.println("All Field Required!");
                return false;
            }
            // query
            sql = "select * from customer where cname='"+ name + "' and pass_code=" + passCode;
            PreparedStatement st  = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            // Execution
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            if (rs.next()) {
                // after login menu driven interface method
               // int ch = 5;
                //int amt = 0;
                int senderAc = rs.getInt("ac_no");
                int receiveAc;
                OUTER:
                while (true) {
                    try {
                        System.out.println(  "Hallo, " + rs.getString("cname"));
                        System.out.println("1)Transfer Money");
                        System.out.println("2)View Balance");
                        System.out.println("5)LogOut");
                        System.out.print("Enter Choice:");
                        int  ch = Integer.parseInt(sc.readLine());
                        switch (ch) {
                            case 1 -> {
                                System.out.print("Enter Receiver  A/c No:");
                                receiveAc = Integer.parseInt(sc.readLine());
                                System.out.print("Enter Amount:");
                               int  amt = Integer.parseInt( sc.readLine());
                                if (bankManagement.transferMoney(senderAc, receiveAc,amt)) {
                                    System.out.println("MSG : Money Sent Successfully!\n");
                                }
                                else {
                                    System.out.println("ERR :  Failed!\n");
                                }
                            }
                            case 2 -> bankManagement.getBalance(
                                        senderAc);
                            case 5 -> {
                                break OUTER;
                            }
                            default -> System.out.println(
                                        "Err : Enter Valid input!\n");
                        }
                    }catch (IOException | NumberFormatException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                return false;
            }
            // return
            return true;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username Not Available!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void getBalance(int acNo) // fetch balance method
    {
        try {
            // query
            sql = "select * from customer where ac_no="
                  + acNo;
            PreparedStatement st
                = con.prepareStatement(sql);
 
            ResultSet rs = st.executeQuery(sql);
            System.out.println(
                "-----------------------------------------------------------");
            System.out.printf("%12s %10s %10s\n",
                              "Account No", "Name",
                              "Balance");
 
            // Execution
 
            while (rs.next()) {
                System.out.printf("%12d %10s %10d.00\n",
                                  rs.getInt("ac_no"),
                                  rs.getString("cname"),
                                  rs.getInt("balance"));
            }
            System.out.println(
                "-----------------------------------------------------------\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }// transfer money method
    public static boolean transferMoney(int sender_ac,int reveiver_ac,int amount)throws SQLException {
        // validation
        if (reveiver_ac == NULL || amount == NULL) {
            System.out.println("All Field Required!");
            return false;
        }
        try {
            con.setAutoCommit(false);
            sql = "select * from customer where ac_no="+ sender_ac;
            PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("balance") < amount) {
                    System.out.println("Insufficient Balance!");
                    return false;
                }
            }
 
            Statement st = con.createStatement();
            // debit
            con.setSavepoint();
            sql = "update customer set balance=balance-"  + amount + " where ac_no=" + sender_ac;
            if (st.executeUpdate(sql) == 1) {
                System.out.println("Amount Debited!");
            }
            // credit
            sql = "update customer set balance=balance+"
                  + amount + " where ac_no=" + reveiver_ac;
            st.executeUpdate(sql);
 
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
        }
        // return
        return false;
    }
}