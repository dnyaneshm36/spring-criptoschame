package com.dnyanesh.learn.crudjdbc.controller.microservice;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.PartialPrivateKey;

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
@RequestMapping("/microservice/clpeks")
public class PartialPrivateKeyController {
    @GetMapping(value = "/ePPK/{identifier}",  produces = "application/json")
    public ResponseEntity<PartialPrivateKey> PartialPrivatekey(@PathVariable(value = "identifier") String identifierString ) throws IOException {

            long ePPK_Start = System.currentTimeMillis();
            //Implamenting the pairing   
            Pairing pairing = PairingFactory.getPairing("params1.txt"); 
            //use pbc wrapper
            PairingFactory.getInstance().setUsePBCWhenPossible(true);

            Element master_key_lambda = pairing.getZr().newRandomElement();

                try 
                {                 // taking q from param
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

            byte [] identifierByte = identifierString.getBytes();

            Element Qu = pairing.getG1().newElement().setFromHash(identifierByte, 0, identifierByte.length);
            Element Du = Qu.duplicate();
            Du.mulZn(master_key_lambda);
            
            String Qustring = Base64.encodeBytes(Qu.toBytes());
            String Dustring = Base64.encodeBytes(Du.toBytes());
            long ePPK_End = System.currentTimeMillis();
            long time = ePPK_Start -ePPK_End;
            
            PartialPrivateKey sSV = new PartialPrivateKey(Qustring,Dustring,time);
		        
        return ResponseEntity.ok().body(sSV);
    }
}
