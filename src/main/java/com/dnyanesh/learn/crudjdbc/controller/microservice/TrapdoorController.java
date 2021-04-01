package com.dnyanesh.learn.crudjdbc.controller.microservice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject.TrapdoorReciever;
import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.TrapdoorSender;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

@RestController
@RequestMapping("/microservice/clpeks")

public class TrapdoorController {

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

            //H2: {0,1}*→Zq*
            static BigInteger hash2_asscii(String str, BigInteger q)
            {

            String shastring = "";
            try {
                shastring = toHexString(getSHA(str));
            } catch (NoSuchAlgorithmException e) {
                System.out.println(" No such Algorithm exception occurred ");
                e.printStackTrace();
            }
            int l = shastring.length();
            int convert;
            BigInteger sum = new BigInteger("0");
            for ( int i = 0 ; i < l ; i++ )
            {
                convert = (int) shastring.charAt(i) ;

                // convert int to BigInteger
                BigInteger bigconv = BigInteger.valueOf(convert);
                sum = sum.add(bigconv);
            }
            sum = sum.mod(q);

            // // converting String to ASCII value in Java 
            // try {
            //      String text = "ABCDEFGHIJKLMNOP"; 
            // // translating text String to 7 bit ASCII encoding 
            // byte[] bytes = text.getBytes("US-ASCII"); 
            // System.out.println("ASCII value of " + text + " is following"); 
            // System.out.println(Arrays.toString(bytes)); 
            // } catch (java.io.UnsupportedEncodingException e)
            //  {
            //       e.printStackTrace(); 
            // }
            return sum;
            }
            //H3: {0,1}*→G1*
            static Element hash3(String str,Pairing pairing)
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
            // H4:G2→{0,1}f
            static BigInteger hash4(Element G2pair)
            {
                byte [ ] G2pairbyte = G2pair.toBytes();

                BigInteger G2pairbiginterger = new BigInteger(G2pairbyte);
                return G2pairbiginterger;
            }



            @GetMapping(value = "/trapdoor/{wordchecking}",  
            consumes = {MediaType.APPLICATION_JSON_VALUE },
            produces = {MediaType.APPLICATION_JSON_VALUE }  )
            public ResponseEntity<TrapdoorSender> GeneratePrivatekey (@PathVariable(value = "wordchecking") String wordchecking, @RequestBody TrapdoorReciever receiverbody ) throws IOException {

            long Trapdoor_Start = System.currentTimeMillis();
            //Implamenting the pairing   
            Pairing pairing = PairingFactory.getPairing("params1.txt"); 
            //use pbc wrapper


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



            String sKr2String                             = receiverbody.getSKr2();           
            String pKs1String                             = receiverbody.getPKs1();


            Element PKs1                = pairing.getG1().newElementFromBytes(Base64.decode(pKs1String));
            Element SKr2                = pairing.getG1().newElementFromBytes(Base64.decode(sKr2String));

                String data = "";
                
                try {                 // taking q from param
                    File myObj = new File("params1.txt");
                    Scanner myReader = new Scanner(myObj);
        
                
                    data = myReader.nextLine();
                    data = myReader.nextLine();
                    data = data.substring(2);
                    
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                String str = data;
                BigInteger q = new BigInteger(str);

                BigInteger hash2big = hash2_asscii(wordchecking, q);
                Element hash2 = pairing.getZr().newElement(hash2big);
                Element T1 = P.duplicate();
                T1.mulZn(master_key_lambda);
                Element H2wSKR = SKr2.duplicate();
                H2wSKR.mulZn(hash2);
                Element lambdaPKs = PKs1.duplicate() ;
                //   System.out.println("lamsdf  H2wSKR  t2 -dash-"+H2wSKR);
                lambdaPKs.mulZn(master_key_lambda);
                //  System.out.println("lamsdf  2 "+lambdaPKs);
                byte [] H2wSKRByte = H2wSKR.toBytes();
                //   Element e = pairing.getG1().newElement();
                //   int bythread = e.setFromBytes(H2wSKRByte);
                //   System.out.println("e--- "+e);
                //   System.out.println("H2wSKR--- "+H2wSKR);
                byte [] lambdaPKsByte = lambdaPKs.toBytes();
                int lenFinal = Math.max(H2wSKRByte.length, lambdaPKsByte.length);
                byte[] T2bytexor = new byte[lenFinal];
                for( int  i = 0 ; i < lenFinal ; i++ )
                {
                    byte x =  H2wSKRByte[i];
                    byte y =  lambdaPKsByte[i];
                    int xint = x;
                    int yint = y;
                    int ans = ( xint ^ yint );
                    T2bytexor[i] = (byte) ans;
                }
                
                Element T2 = pairing.getG1().newElement();
                int bythread = T2.setFromBytes(T2bytexor);
                System.out.println(bythread);

                Element hash3_word2 = hash3(wordchecking, pairing).duplicate();
                // System.out.println("hash3_word2 should be equal to t3 dash "+hash3_word2);
                byte [] hash3WordByte = hash3_word2.toBytes();
                lenFinal = Math.max(hash3WordByte.length, lambdaPKsByte.length);
                byte[] T3bytexor = new byte[lenFinal];
                for( int  i = 0 ; i < lenFinal ; i++ )
                {
                    byte x =  hash3WordByte[i];
                    byte y =  lambdaPKsByte[i];
                    int xint = x;
                    int yint = y;
                    int ans = ( xint ^ yint );
                    T3bytexor[i] = (byte) ans;
                }
                Element T3 = pairing.getG1().newElement();
                bythread = T3.setFromBytes(T3bytexor);


            String t1string = Base64.encodeBytes(T1.toBytes());
            String t2string = Base64.encodeBytes(T2.toBytes());
            String t3string = Base64.encodeBytes(T3.toBytes());


            long Trapdoor_End = System.currentTimeMillis();

            long time = Trapdoor_End - Trapdoor_Start;


            TrapdoorSender trapdoorword = new TrapdoorSender(t1string,t2string,t3string,T2bytexor,T3bytexor,time);

            return ResponseEntity.ok().body(trapdoorword);
            }
}
