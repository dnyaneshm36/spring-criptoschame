package com.dnyanesh.learn.crudjdbc.controller.microservice;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject.CheckingPairingPublicKey;
import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.CheckingPairingPublicKeysender;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

@RestController
@RequestMapping("/microservice/clpeks")
public class CheckingPairingPublicKeyController {
        @GetMapping(value = "/pairingcheck",  
        consumes = {MediaType.APPLICATION_JSON_VALUE },
        produces = {MediaType.APPLICATION_JSON_VALUE }  )
        public ResponseEntity<CheckingPairingPublicKeysender> GeneratePrivatekey ( @RequestBody CheckingPairingPublicKey receiverbody ) throws IOException {

                long pairingcheck_Start = System.currentTimeMillis();
                //Implamenting the pairing   
                Pairing pairing = PairingFactory.getPairing("params1.txt"); 
                //use pbc wrapper
                PairingFactory.getInstance().setUsePBCWhenPossible(true);

                Element P       = pairing.getG1().newRandomElement();
              

                try 
                {                 // taking q from param
                        File myObj = new File("P.txt");
                        Scanner myReader = new Scanner(myObj);
                
                        String Pbytestr;
                        Pbytestr = myReader.nextLine();
                        
                        P = pairing.getG1().newElementFromBytes(Base64.decode(Pbytestr));
                        myReader.close();
                } 
                catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                }
                Element master_key_lambda = pairing.getZr().newRandomElement();

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

                String PKr1string = receiverbody.getPKr1();
                String PKr2string = receiverbody.getPKr2();
                Element PKr1 = pairing.getG1().newElementFromBytes(Base64.decode(PKr1string));
                Element PKr2 = pairing.getG1().newElementFromBytes(Base64.decode(PKr2string));
                Element pair_sender_PKc =  pairing.pairing(PKr1, PKc);
                Element pair_sender_P  = pairing.pairing(PKr2, P);
                String description = "";
                Boolean pair = false;
                if( pair_sender_PKc.isEqual(pair_sender_P) )
                {       
                        pair = true;
                        description = "Equal, This check the pairing property of the public key of client.";
                }
                else
                {
                        pair = false;
                        description = "Unequal fail turn ⊥ and about";
                }
                
                long pairingcheck_End = System.currentTimeMillis();
                long time = pairingcheck_Start -pairingcheck_End;
                CheckingPairingPublicKeysender check = new CheckingPairingPublicKeysender(pair,description,time);
                    
            return ResponseEntity.ok().body(check);
        }
}
