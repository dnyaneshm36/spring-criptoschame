package com.dnyanesh.learn.crudjdbc.controller.microservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.SetupParameter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

@RestController
@RequestMapping("/microservice/clpeks")
public class SetupController {
    @GetMapping("/home")
	public String home(){
		return "this my pbc jpbc home";
	}
    @GetMapping(value = "/setup",  produces = "application/json")
    public ResponseEntity<SetupParameter> Setupvariable( ) throws IOException{
        long Setup_time_start = System.currentTimeMillis();        
        
        
                   
        //Implamenting the pairing   
        Pairing pairing = PairingFactory.getPairing("params1.txt"); 
            //use pbc wrapper
         PairingFactory.getInstance().setUsePBCWhenPossible(true);



         Element P = pairing.getG1().newRandomElement();
    //                     try {
    //     FileWriter fw = new FileWriter("P.txt");
    //     String PStr = Base64.encodeBytes(P.toBytes());
    //     fw.write(PStr);
    //     fw.flush();
    //     fw.close();

    // } catch (IOException e) {
    //     System.out.println("the we are genpr wrigi in P ");
    //     e.printStackTrace();
    // }
    // System.out.println("P is ---- "+P);
    try {                 // taking q from param
            File myObj = new File("P.txt");
            Scanner myReader = new Scanner(myObj);
    
            String Pbytestr;
            Pbytestr = myReader.nextLine();
            
            P = pairing.getG1().newElementFromBytes(Base64.decode(Pbytestr));
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    // System.out.println("P is ---- "+P);
        Element master_key_lambda = pairing.getZr().newRandomElement();
                // try {

                //     FileWriter fw = new FileWriter("master_key_lambda.txt");
                //     String master_key_lambdaStr = Base64.encodeBytes(master_key_lambda.toBytes());
                //     fw.write(master_key_lambdaStr);
                //     fw.flush();
                //     fw.close();

                // } catch (IOException e) {
                //     System.out.println("the master key ");
                //     e.printStackTrace();
                // }
                // System.out.println("P is ---- "+P);
                try {                 // taking q from param
                    File myObj = new File("master_key_lambda.txt");
                    Scanner myReader = new Scanner(myObj);

                    String Pbytestr;
                    Pbytestr = myReader.nextLine();
                    
                    master_key_lambda = pairing.getZr().newElementFromBytes(Base64.decode(Pbytestr));
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                Element PKc = P.duplicate();
                PKc.mulZn(master_key_lambda);
                // try {

                //     FileWriter fw = new FileWriter("PKc.txt");
                //     String PKcStr = Base64.encodeBytes(master_key_lambda.toBytes());
                //     fw.write(PKcStr);
                //     fw.flush();
                //     fw.close();

                // } catch (IOException e) {
                //     System.out.println("the PKc failed  ");
                //     e.printStackTrace();
                // }
                // System.out.println("P is ---- "+P);
                        // try {                 // taking q from param
                        //         File myObj = new File("PKc.txt");
                        //         Scanner myReader = new Scanner(myObj);

                        //         String Pbytestr;
                        //         Pbytestr = myReader.nextLine();
                                
                        //         PKc = pairing.getG1().newElementFromBytes(Base64.decode(Pbytestr));
                        //         myReader.close();
                        // } catch (FileNotFoundException e) {
                        //     System.out.println("An error occurred.");
                        //     e.printStackTrace();
                        // }


                Element SKc = master_key_lambda.duplicate();
                // try {

                //     FileWriter fw = new FileWriter("SKc.txt");
                //     String SKcStr = Base64.encodeBytes(SKc.toBytes());
                //     fw.write(SKcStr);
                //     fw.flush();
                //     fw.close();

                // } catch (IOException e) {
                //     System.out.println("the SKc failed  ");
                //     e.printStackTrace();
                // }
                // System.out.println("P is ---- "+P);
                    // try {                 // taking q from param
                    //         File myObj = new File("SKc.txt");
                    //         Scanner myReader = new Scanner(myObj);

                    //         String Pbytestr;
                    //         Pbytestr = myReader.nextLine();
                            
                    //         SKc = pairing.getZr().newElementFromBytes(Base64.decode(Pbytestr));
                    //         myReader.close();
                    // } catch (FileNotFoundException e) {
                    //     System.out.println("An error occurred.");
                    //     e.printStackTrace();
                    // }
                String pstring = Base64.encodeBytes(P.toBytes());
                String masterString = Base64.encodeBytes(master_key_lambda.toBytes());
                String pksString = Base64.encodeBytes(PKc.toBytes());
                String sksString = Base64.encodeBytes(SKc.toBytes());
            
                long Setup_time_end = System.currentTimeMillis(); 
                long time = Setup_time_end - Setup_time_start;
        SetupParameter setup = new SetupParameter(pstring,masterString,pksString,sksString,time);
    
        return ResponseEntity.ok().body(setup);
        // System.out.println(" kensnerd");
    }
}
