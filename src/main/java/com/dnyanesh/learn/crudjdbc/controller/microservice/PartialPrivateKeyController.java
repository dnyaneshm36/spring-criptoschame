package com.dnyanesh.learn.crudjdbc.controller.microservice;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
  

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
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
            public static byte[] getSHA(String input) throws NoSuchAlgorithmException
            { 
                // Static getInstance method is called with hashing SHA 
                MessageDigest md = MessageDigest.getInstance("SHA-256"); 

                // digest() method called 
                // to calculate message digest of an input 
                // and return array of byte
                return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
            }
            
            public static String toHexString(byte[] hash)
            {
                // Convert byte array into signum representation 
                BigInteger number = new BigInteger(1, hash); 

                // Convert message digest into hex value 
                StringBuilder hexString = new StringBuilder(number.toString(16)); 

                // Pad with leading zeros
                while (hexString.length() < 32) 
                { 
                    hexString.insert(0, '0'); 
                } 
                return hexString.toString(); 
            }
            //H1: {0,1}*→G1*
            static Element hash1(String str,Pairing pairing)
            {
                String shastring = "";
                try {
                shastring = toHexString(getSHA(str));
                } catch (NoSuchAlgorithmException e) {
                System.out.println(" No such Algorithm exception occurred ");
                e.printStackTrace();
                }
                byte [] shatringbyte = shastring.getBytes();

                Element g1 = pairing.getG1().newElement().setFromHash(shatringbyte, 0, shatringbyte.length);
                return g1.duplicate();
            }

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

            

            Element Qu = hash1(identifierString, pairing).duplicate();
            Element Du = Qu.duplicate();
            Du.mulZn(master_key_lambda);
            // System.out.println(" Du inn the g epppk \n"+Du);
            String Qustring = Base64.encodeBytes(Qu.toBytes());
            String Dustring = Base64.encodeBytes(Du.toBytes());
            long ePPK_End = System.currentTimeMillis();
            long time = ePPK_Start -ePPK_End;
            
            PartialPrivateKey sSV = new PartialPrivateKey(Qustring,Dustring,time);
		        
        return ResponseEntity.ok().body(sSV);
    }
}
