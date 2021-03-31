package com.dnyanesh.learn.crudjdbc.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.KengenSender;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

@RestController
@RequestMapping("/mpkes")
public class KengenController {
    @GetMapping(value = "/keygensender/{secrete}",  produces = "application/json")
    public ResponseEntity<KengenSender> Keygensender(@PathVariable(value = "secrete") String Secretstring ) throws IOException {
        // System.out.println(" kensnerd");
        long KeyGen_server_start = System.currentTimeMillis();        
        
        
                   
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
                    
                    BigInteger num = new BigInteger(Secretstring);
                    // Element a = pairing.getZr().newRandomElement(); //sks
                    Element a = pairing.getZr().newElement();
                    a.set(num);
                    Element A = P.duplicate();
                    A.mulZn(a); //A
                    // System.out.println("aa -- "+a);
                    // String PStr = Base64.encodeBytes(a.toBytes());

                    // Element x = pairing.getG1().newElementFromBytes(Base64.decode(PStr));
                    // System.out.println("aa -- "+x);
                    // Element y = pairing.getZr().newElementFromBytes(Base64.decode(PStr));
                    // System.out.println("aa -- "+y);


                    Element B = pairing.getG1().newRandomElement(); // B

                    String sks = Base64.encodeBytes(A.toBytes());;
                    String pks = Base64.encodeBytes(B.toBytes());;
                    long KenyGen_server_end = System.currentTimeMillis();

                    KengenSender senderkey = new KengenSender(sks,pks,(KenyGen_server_end-KeyGen_server_start));
		        
                    return ResponseEntity.ok().body(senderkey);
    }
    
    @GetMapping(value = "/keygenreceiver/{secrete}",  produces = "application/json")
    public ResponseEntity<KengenSender> Keygenreceiver(@PathVariable(value = "secrete") String Secretstring ) throws IOException
    {
            // System.out.println(" kensnerd");
            long KeyGen_server_start = System.currentTimeMillis();        
            
            
                   
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
                    
                    BigInteger num = new BigInteger(Secretstring);

                    Element c = pairing.getZr().newElement();  //skr
                    c.set(num);
                    Element C = P.duplicate();
                    C.mulZn(c);

                    // Element B = pairing.getG1().newRandomElement(); // B

                    String sks = Base64.encodeBytes(c.toBytes());;
                    String pks = Base64.encodeBytes(C.toBytes());;
                    long KenyGen_server_end = System.currentTimeMillis();

                    KengenSender senderkey = new KengenSender(sks,pks,(KenyGen_server_end-KeyGen_server_start));
		        
                    return ResponseEntity.ok().body(senderkey);
    }
}
