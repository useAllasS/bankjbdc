package bank;
import java.io.*;
public class Bank {
    public static void main(String args[]) throws IOException{

        try (BufferedReader sc = new BufferedReader( new InputStreamReader(System.in))) {
            String name = "";
            int pass_code;
            int ac_no;
            int ch;
            
            while (true) {
                System.out.println("\n ->||    Welcome to InBank    ||<- \n");
                System.out.println("1)Create Account");
                System.out.println("2)Login Account");
                try {
                    System.out.print("\n    Enter Input:"); //user input
                    ch = Integer.parseInt(sc.readLine());
                    switch (ch) {
                        case 1 -> {
                            try {
                                System.out.print("Enter Unique UserName:");
                                name = sc.readLine();
                                System.out.print("Enter New Password:");
                                pass_code = Integer.parseInt(sc.readLine());
                                
                                if (bankManagement.createAccount( name, pass_code)) {
                                    System.out.println("MSG : Account Created Successfully!\n");
                                }
                                else {
                                    System.out.println("ERR : Account Creation Failed!\n");
                                }
                            }
                            catch (IOException | NumberFormatException e) {
                                System.out.println(" ERR : Enter Valid Data::Insertion Failed!\n");
                            }
                        }
                        case 2 -> {
                            try {
                                System.out.print("Enter  UserName:");
                                name = sc.readLine();
                                System.out.print("Enter  Password:");
                                pass_code = Integer.parseInt(sc.readLine());
                                if (bankManagement.loginAccount(name, pass_code)) {
                                    System.out.println("MSG : Logout Successfully!\n");
                                }
                                else {
                                    System.out.println("ERR : login Failed!\n");
                                }
                            }
                            catch (IOException | NumberFormatException e) {
                                System.out.println(" ERR : Enter Valid Data::Login Failed!\n");
                            }
                        }
                            
                        default -> System.out.println("Invalid Entry!\n");
                    }

                    if (ch == 5) {
                        System.out.println("Exited Successfully!\n\n Thank You :)");
                        break;
                    }
                }
                catch (IOException | NumberFormatException e) {
                    System.out.println("Enter Valid Entry!");
                }
            }
        }
    }
}