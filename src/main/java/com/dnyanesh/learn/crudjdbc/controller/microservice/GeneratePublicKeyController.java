package com.dnyanesh.learn.crudjdbc.controller.microservice;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject.GeneratePublicKeyReceiver;
import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.GeneratePublicKey;

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
public class GeneratePublicKeyController {

    @GetMapping(value = "/gPubK",  
    consumes = {MediaType.APPLICATION_JSON_VALUE },
    produces = {MediaType.APPLICATION_JSON_VALUE }  )
    public ResponseEntity<GeneratePublicKey> GeneratePublickey ( @RequestBody GeneratePublicKeyReceiver receiverbody  ) throws IOException {
    
            long gPubK_Start = System.currentTimeMillis();
            //Implamenting the pairing   
            Pairing pairing = PairingFactory.getPairing("params1.txt"); 
            //use pbc wrapper
            PairingFactory.getInstance().setUsePBCWhenPossible(true);

            
            Element P = pairing.getG1().newRandomElement();

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
            Element PKu1 = P.duplicate();
            Element PKu2 = PKc.duplicate();
            String sustring = receiverbody.getSu();
            Element Su = pairing.getZr().newElementFromBytes(Base64.decode(sustring));
            
            PKu1.mulZn(Su);
            PKu2.mulZn(Su);
            // System.out.println("pubclic  keyes");
            // System.out.println("PKu1 \n"+PKu1);
            // System.out.println("PKu2 \n"+PKu2);


            String pku1string = Base64.encodeBytes(PKu1.toBytes());;
            String pku2string = Base64.encodeBytes(PKu2.toBytes());;
            long gPubK_End = System.currentTimeMillis();
            long time = gPubK_Start -gPubK_End;
        GeneratePublicKey gPubK = new GeneratePublicKey(pku1string,pku2string,time);
		        
        return ResponseEntity.ok().body(gPubK);
    }
}
