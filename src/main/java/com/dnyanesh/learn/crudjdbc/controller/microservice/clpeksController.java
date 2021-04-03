package com.dnyanesh.learn.crudjdbc.controller.microservice;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject.ClpeksReceiver;
import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.ClpeksSender;

import org.springframework.http.MediaType;
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
public class clpeksController {

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



                @GetMapping(value = "/encript",  
                consumes = {MediaType.APPLICATION_JSON_VALUE },
                produces = {MediaType.APPLICATION_JSON_VALUE }  )
                public ArrayList<ClpeksSender> GeneratePrivatekey ( @RequestBody ClpeksReceiver receiverbody ) throws IOException {

                  ArrayList<ClpeksSender> encriptedwords      = new ArrayList<ClpeksSender>();

                  ArrayList<String> words                     = receiverbody.getWords();
                  String Qsstring                             = receiverbody.getQs();           
                  String Qrstring                             = receiverbody.getQr();
                  String PKs2string                           = receiverbody.getPKs2();
                  String PKr2string                           = receiverbody.getPKr2();
                  Pairing pairing = PairingFactory.getPairing("params1.txt"); 
                  //use pbc wrapper
                  PairingFactory.getInstance().setUsePBCWhenPossible(true);
                  //use pbc wrapper

                  Element Qr                  = pairing.getG1().newElementFromBytes(Base64.decode(Qrstring));
                  Element Qs                  = pairing.getG1().newElementFromBytes(Base64.decode(Qsstring));
                  Element PKs2                = pairing.getG1().newElementFromBytes(Base64.decode(PKs2string));
                  Element PKr2                = pairing.getG1().newElementFromBytes(Base64.decode(PKr2string));
                 
                  Element P                   = pairing.getG1().newRandomElement();
  
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

                  for( String word: words)
                  {


                      long clpeks_Start = System.currentTimeMillis();
                      //Implamenting the pairing   
                              //Implamenting the pairing   

                      Element Ri;
                      Ri = pairing.getZr().newRandomElement();
            
                      BigInteger rethash = hash2_asscii(word, q);
                      Element hash = pairing.getZr().newElement(rethash);
                      Element first = Qr.duplicate();
                      first.mulZn(hash);
                      first.mulZn(Ri);
                      Element pair1 = pairing.pairing( first , PKr2 );
                      Element QsRi = Qs.duplicate();
                      QsRi.mulZn(Ri);    
                      Element pair2 = pairing.pairing( QsRi , PKs2 );
                      
                      
                      Element hash3_word = hash3(word, pairing).duplicate();
                      hash3_word.mulZn(Ri);
            
                      Element pair3 = pairing.pairing( hash3_word , P );
            
                      Element Ti = pair1.duplicate();
                      Ti.mul(pair2);
                      Ti.mul(pair3);
            
                      Element Ui =  P.duplicate();
                      Ui.mulZn(Ri);
                      BigInteger Vi = hash4(Ti);

                      String Uistring = Base64.encodeBytes(Ui.toBytes());


                      long Clpeks_End = System.currentTimeMillis();

                      long time = Clpeks_End - clpeks_Start;
              
                      ClpeksSender encriptedword = new ClpeksSender(Uistring,Vi,time);
                      encriptedwords.add(encriptedword);
                  }     
            return encriptedwords;
        }
}
