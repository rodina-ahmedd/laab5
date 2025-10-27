/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package laab5;

import Backend.StudentDataBase;
import Controller.Controller;
import Frontend.Login;
import java.io.IOException;

/**
 *
 * @author mo
 */
public class Laab5 {

    private static Controller control ;
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        StudentDataBase Data  = new StudentDataBase();
      
      control = new Controller(Data);
      
      new Login().setVisible(true);
       
    }
    
    public static Controller getController(){
        return control;
    }
    
}
