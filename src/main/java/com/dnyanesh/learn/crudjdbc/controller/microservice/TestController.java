package com.dnyanesh.learn.crudjdbc.controller.microservice;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject.TestReceiver;
import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.TestSender;

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
public class TestController {
 
               // H4:G2→{0,1}f
              static BigInteger hash4(Element G2pair)
              {
                    byte [ ] G2pairbyte = G2pair.toBytes();

                    BigInteger G2pairbiginterger = new BigInteger(G2pairbyte);
                    return G2pairbiginterger;
              }
            @GetMapping(value = "/test",  
            consumes = {MediaType.APPLICATION_JSON_VALUE },
            produces = {MediaType.APPLICATION_JSON_VALUE }  )
            public ResponseEntity<TestSender> GeneratePrivatekey ( @RequestBody TestReceiver receiverbody ) throws IOException {

                long Test_Start = System.currentTimeMillis();
                        //Implamenting the pairing   
                        Pairing pairing = PairingFactory.getPairing("params1.txt"); 
                        //use pbc wrapper
                        PairingFactory.getInstance().setUsePBCWhenPossible(true);

                ArrayList<Byte> T2bytelist                                = receiverbody.getT2byte();           
                ArrayList<Byte> T3bytelist                                = receiverbody.getT3byte();

                byte [] T2byte = new byte[T2bytelist.size()];
                int k = 0;
                for (Byte b : T2bytelist) {
                    T2byte[k] = b;
                    k++;
                }
                byte [] T3byte = new byte[T3bytelist.size()];
                k = 0;
                for (Byte b : T3bytelist) {
                    T3byte[k] = b;
                    k++;
                }
                String t1String                               = receiverbody.getT1();
                String sKs1String                             = receiverbody.getSKs1();
                String sKs2String                             = receiverbody.getSKs2();
                
                BigInteger Vi                                 = receiverbody.getVi();
                String uiString                               = receiverbody.getUi();

                Element T1                  = pairing.getG1().newElementFromBytes(Base64.decode(t1String));
                Element SKs1                = pairing.getZr().newElementFromBytes(Base64.decode(sKs1String));
                Element Sks2                = pairing.getG1().newElementFromBytes(Base64.decode(sKs2String));

                Element Ui                  = pairing.getG1().newElementFromBytes(Base64.decode(uiString));

                Element SKsT1 = T1.duplicate();
                SKsT1.mulZn(SKs1);
                byte [] SKsT1byte = SKsT1.toBytes();

                // byte [] T2byte = wordSearch.getT2().toBytes();
                // byte [] T3byte = wordSearch.getT3().toBytes();

                //    byte [] T2byte = wordSearch.getByteArrayT2().clone();
                //    byte [] T3byte = wordSearch.getBythArrayT3().clone();

                //   int [] T2byte = T2bytexor;
              //   int [] T3byte = T3bytexor;
                int lenFinal = Math.max(T2byte.length, SKsT1byte.length);
                byte [] T2dashintobyte = new byte[lenFinal];
                for( int  i = 0 ; i < lenFinal ; i++ )
                {
                    byte x =  T2byte[i];
                    byte y =  SKsT1byte[i];
                    int xint = x;
                    int yint = y;
                    int ans = ( xint ^ yint );
                    T2dashintobyte[i] = (byte) ans;
                    
                    // if(H2wSKRByte[i]!=T2byte[i])
                      // {
                      //   System.out.println(i+"   this    "+T2dashintobyte[i]+"   --==--   "+T2byte[i]+"   ^   "+SKsT1byte[i] +"  ---  ");
                      // }
                }
      
                lenFinal = Math.max(T3byte.length, SKsT1byte.length);
                byte [] T3dashintobyte = new byte[lenFinal];
                for( int  i = 0 ; i < lenFinal ; i++ )
                {
                    byte x =  T3byte[i];
                    byte y =  SKsT1byte[i];
                    int xint = x;
                    int yint = y;
                    int ans = ( xint ^ yint );
                    T3dashintobyte[i] = (byte) ans;
                      // if(hash3WordByte[i]!=T3dashbyte[i])
                      // {
                      //   System.out.println(i+"   1111    "+T3dashintobyte[i]+"   --==--   "+T3byte[i]+"   ^   "+SKsT1byte[i] +" ");
                      // }
                }
                // if you comment bellow fourlines uncommment  t2dash line then it run perfect
                int byteread;
                Element T2dash = pairing.getG1().newElement();
                byteread = T2dash.setFromBytes(T2dashintobyte);
                Element T3dash = pairing.getG1().newElement();
                byteread = T3dash.setFromBytes(T3dashintobyte);   
                                                                              //-------------------------------------
                System.out.println(byteread);
                // Element T2dash = H2wSKR.duplicate();
                // Element T3dash = hash3_word2.duplicate();
      
              //   System.out.println(" -----------T2dash --- "+ T2dash);
              //   System.out.println(" -----------T3dash --- "+ T3dash);
                Element firstElementPair = T2dash.duplicate();
              //   System.out.println("firstElementPair "+firstElementPair);
                firstElementPair.add(Sks2); 
              //   System.out.println("firstElementPair  1  "+firstElementPair);
                firstElementPair.add(T3dash);
              //   System.out.println("firstElementPair 2  "+firstElementPair);
                Element hash4pair =  pairing.pairing(firstElementPair, Ui);
        
                BigInteger hash4pairbig = hash4(hash4pair);

      
                // System.out.println("------------------H2wSKRByte-----------------"+H2wSKRByte.length);
                // for(int i=0; i< H2wSKRByte.length ; i++) {
                //   System.out.print(H2wSKRByte[i] +" ");
                // }
                // System.out.println("\n------------------lambdaPKsByte-----------------"+lambdaPKsByte.length);
                // for(int i=0; i< lambdaPKsByte.length ; i++) {
                //   System.out.print(lambdaPKsByte[i] +" ");
                // }
                // System.out.println("\n------------------T2bytexor-----------------"+T2bytexor.length);
                // for(int i=0; i< T2bytexor.length ; i++) {
                //   System.out.print(T2bytexor[i] +" ");
                // }
                
                // System.out.println("\n------------------T2dashintobyte-----------------"+T2dashintobyte.length);
                // for(int i=0; i< T2dashintobyte.length ; i++) {
                //   System.out.print(T2dashintobyte[i] +" ");
                // }
                // System.out.println("\n------------------hash3WordByte-----------------"+hash3WordByte.length);
                // for(int i=0; i< hash3WordByte.length ; i++) {
                //   System.out.print(hash3WordByte[i] +" ");
                // }
                // System.out.println("\n------------------SKsT1byte-----------------"+SKsT1byte.length);
                // for(int i=0; i< SKsT1byte.length ; i++) {
                //   System.out.print(SKsT1byte[i] +" ");
                // }
                // System.out.println("\n------------------T3bytexor-----------------"+T3bytexor.length);
                // for(int i=0; i< T3bytexor.length ; i++) {
                //   System.out.print(T3bytexor[i] +" ");
                // }
                // System.out.println("\n------------------T3dashintobyte-----------------"+T3dashintobyte.length);
                // for(int i=0; i< T3dashintobyte.length ; i++) {
                //   System.out.print(T3dashintobyte[i] +" ");
                // }
      
                String descriptString = "";
                boolean Test = false;
                if( hash4pairbig.equals(Vi) )
                { 
                    Test = true;
                    System.out.println("\nYes !!! \nsuccessfully  find the word\n");
                    descriptString+="\nYes !!! \nsuccessfully  find the word\n";
                }
                else
                {
                      Test = false;
                      System.out.println("\n oh No oh NO oh No !!! \nsuccessfully didn't find  the word\n");
                      descriptString+="\n oh No oh NO oh No !!! \nsuccessfully didn't find  the word\n";
                }
                System.out.println(" hash4pairbig  q--- "+hash4pairbig);
                System.out.println("cipherword.getVi()--- "+ Vi);
                

                
                long Test_End = System.currentTimeMillis();
                long time = Test_End - Test_Start;
                TestSender test = new TestSender(Test,descriptString,time);
                 return ResponseEntity.ok().body(test);
            }
}
