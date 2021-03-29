package com.dnyanesh.learn.crudjdbc.controller;



import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

import com.dnyanesh.learn.crudjdbc.model.CipherWord;
import com.dnyanesh.learn.crudjdbc.model.ClientKey;
import com.dnyanesh.learn.crudjdbc.model.SetupParamter;
import com.dnyanesh.learn.crudjdbc.model.TrapdoorWord;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/pbc/newclpeks")
public class NewClepks {
    
    static BigInteger hash2_asscii(String str, BigInteger q)
    {
        int l = str.length();
        int convert;
        BigInteger sum = new BigInteger("0");
        for ( int i = 0 ; i < l ; i++ )
        {
            convert = (int) str.charAt(i) ;

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

    @GetMapping("/home")
	public String home(){
		return "this my pbc jpbc home";
	}

	@GetMapping(value = "/demo",  produces = "application/json")
	public String getSetupParam() {

        // int rBits = 7;
        // int qBits = 20;
        // PairingParametersGenerator pg = new TypeACurveGenerator(rBits, qBits);
        // PairingParameters params = pg.generate();

        // try {
        //     FileWriter fw = new FileWriter("params1.txt");
        //     String paramsStr = params.toString();
        //     fw.write(paramsStr);
        //     fw.flush();
        //     fw.close();

        // } catch (IOException e) {
        //     System.out.println("the we get problem in writering ");
        //     e.printStackTrace();
        // }


        // //Implamenting the pairing   

        // Pairing pairing = PairingFactory.getPairing("params1.txt"); 
        // //use pbc wrapper
        // PairingFactory.getInstance().setUsePBCWhenPossible(true);


        //Implamenting the pairing   
        long KeyGen_server_start = System.currentTimeMillis();
        Pairing pairing = PairingFactory.getPairing("params1.txt"); 
        //use pbc wrapper
        PairingFactory.getInstance().setUsePBCWhenPossible(true);
        
        /* Return Zr */
        Field Zr = pairing.getZr();

        // /* Return G1 */
        // Field G1 = pairing.getG1();

        // /* Return G2 */
        // Field G2 = pairing.getG2();

        // /* Return GT */
        // Field GT = pairing.getGT();
        
        Element P = pairing.getG1().newRandomElement();
        Element master_key_lamda = pairing.getZr().newRandomElement();
        Element PKc = P.duplicate();
        PKc.mulZn(master_key_lamda);
        Element SKs = master_key_lamda.duplicate();
        long KeyGen_server_end = System.currentTimeMillis();
        SetupParamter r = new SetupParamter(P,master_key_lamda,PKc,SKs,(KeyGen_server_end-KeyGen_server_start)) ;
        
        
        // System.out.println("--------------  object "+r);
         
        
        long  time_generater_key_start  = System.currentTimeMillis();
        ClientKey sender;
        String senderId = "senderid";
        byte [] senderbyte = senderId.getBytes();

        Element Qus = pairing.getG1().newElement().setFromHash(senderbyte, 0, senderbyte.length);
        Element Dus = Qus.duplicate();
        Dus.mulZn(master_key_lamda);

        sender = new ClientKey(senderId,Qus , Dus);
        
        Element Sus = pairing.getZr().newRandomElement(); //clent id
        Element SKu1s = Sus.duplicate();
        Element SKu2s = Dus.duplicate();
        SKu2s.mulZn(Sus);   

        Element PKu1s = r.getP().duplicate();
        PKu1s.mulZn(Sus);
        Element PKu2s = r.getPKc().duplicate();
        PKu2s.mulZn(Sus);      
        sender.setPKu1(PKu1s);
        sender.setPKu2(PKu2s);
        sender.setSKu1(SKu1s);
        sender.setSKu2(SKu2s);
        sender.setSu(Sus);
        long  time_generater_key_end  = System.currentTimeMillis();
        
        sender.setRequredTime((time_generater_key_end-time_generater_key_start));
        
        time_generater_key_start  = System.currentTimeMillis();
        
        ClientKey receiver;
        String receiverId = "receiverid";
        byte [] receiverbyte = receiverId.getBytes();
        Element Qur = pairing.getG1().newElement().setFromHash(receiverbyte, 0, receiverbyte.length);
        Element Dur = Qur.duplicate();
        Dur.mulZn(master_key_lamda);
        receiver = new ClientKey(receiverId, Qur, Dur);

        Element Sur = pairing.getZr().newRandomElement();    //client secrete.
        Element SKu1r = Sur.duplicate();
        Element SKu2r = Dur.duplicate();
        SKu2r.mulZn(Sur);

        Element PKu1r = r.getP().duplicate();
        PKu1r.mulZn(Sur);

        Element PKu2r = r.getPKc().duplicate();
        PKu2r.mulZn(Sur);

        receiver.setPKu1(PKu1r);
        receiver.setPKu2(PKu2r);
        receiver.setSKu1(SKu1r);
        receiver.setSKu2(SKu2r);
        receiver.setSu(Sur);


        time_generater_key_end  = System.currentTimeMillis();
        receiver.setRequredTime((time_generater_key_end-time_generater_key_start));

        // CLPKES

        //checking pair is equal.

        time_generater_key_start  = System.currentTimeMillis();

        Element pair_sender_PKc,pair_sender_P;
        // System.out.println("pku1    "+sender.getPKu1());
        // System.out.println("PKC   "+  r.getPKc() );
        // System.out.println("getPKu2  "+sender.getPKu2());
        // System.out.println("getp   "+r.getP());
        pair_sender_PKc =  pairing.pairing(sender.getPKu1(), r.getPKc());
        pair_sender_P  = pairing.pairing(sender.getPKu2(), r.getP());

        if( pair_sender_PKc.isEqual(pair_sender_P) )
        {
          System.out.println("\nPairing equal for receiver ");
        }
        else
        {
          System.out.println("\nfail turn ⊥ and about\n");
        }

        //checking pair is equal.
        Element pair_receiver_PKc,pair_receiver_P;

        pair_receiver_PKc =  pairing.pairing(receiver.getPKu1(), r.getPKc());
        pair_receiver_P  = pairing.pairing(receiver.getPKu2(), r.getP());


        if( pair_receiver_PKc.isEqual(pair_receiver_P) )
        {
          System.out.println("\nPairing equal for receiver ");
        }
        else
        {
          System.out.println("\nfail turn ⊥ and about\n");
        }

        String word = "wordto_encrption";
        Element Ri;
        Ri = pairing.getZr().newRandomElement();

        
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
          BigInteger rethash = hash2_asscii(word, q);
          Element hash = Zr.newElement(rethash);
          Element first = receiver.getQu().duplicate();
          first.mulZn(hash);
          first.mulZn(Ri);
          Element pair1 = pairing.pairing( first , receiver.getPKu2() );
          Element QsRi = sender.getQu().duplicate();
          QsRi.mulZn(Ri);    
          Element pair2 = pairing.pairing( QsRi , sender.getPKu2() );
          
          byte [] wordByte = word.getBytes();
          Element hash3_word = pairing.getG1().newElement().setFromHash(wordByte, 0, wordByte.length);
          hash3_word.mulZn(Ri);

          Element pair3 = pairing.pairing( hash3_word , r.getP() );

          Element Ti = pair1.duplicate();
          Ti.mul(pair2);
          Ti.mul(pair3);

          byte [ ] byteVi = Ti.toBytes();
          Element Ui =  r.getP().duplicate();
          Ui.mulZn(Ri);
          BigInteger Vi = new BigInteger(byteVi);
          CipherWord cipherword = new CipherWord(Ui,Vi);

          time_generater_key_end  = System.currentTimeMillis();

          cipherword.setRequiredTime((time_generater_key_end-time_generater_key_start));

          // Trapdoor()

          time_generater_key_start  = System.currentTimeMillis();

          Element T1 = r.getP().duplicate();
          T1.mulZn(r.getMaster_key_lamda());
          Element H2wSKR = receiver.getSKu2().duplicate();
          H2wSKR.mulZn(hash);
          Element lambdaPKs = sender.getPKu1().duplicate() ;
        //   System.out.println("lamsdf  H2wSKR  t2 -dash-"+H2wSKR);
          lambdaPKs.mulZn(r.getMaster_key_lamda());
        //  System.out.println("lamsdf  2 "+lambdaPKs);
          byte [] H2wSKRByte = H2wSKR.toBytes();
        //   Element e = pairing.getG1().newElement();
        //   int bythread = e.setFromBytes(H2wSKRByte);
        //   System.out.println("e--- "+e);
        //   System.out.println("H2wSKR--- "+H2wSKR);
          byte [] lambdaPKsByte = lambdaPKs.toBytes();
          int lenFinal = Math.max(H2wSKRByte.length, lambdaPKsByte.length);
          int[] T2intxor = new int[lenFinal];
          for( int  i = 0 ; i < lenFinal ; i++ )
          {
              int x = (int) H2wSKRByte[i] ^ (int) lambdaPKsByte[i];
              T2intxor[i] = x;
          }
          byte[] T2bytexor = new byte[lenFinal];
          for( int  i = 0 ; i < lenFinal ; i++ )
          {
            T2bytexor[i] = (byte) T2intxor[i] ;
          }
          Element T2 = H2wSKR.duplicate();
          T2.add(lambdaPKs);
          // int bythread = T2.setFromBytes(T2bytexor);
          byte [] wordByte2 = word.getBytes();
          Element hash3_word2 = pairing.getG1().newElement().setFromHash(wordByte2, 0, wordByte2.length);
         // System.out.println("hash3_word2 should be equal to t3 dash "+hash3_word2);
          byte [] hash3WordByte = hash3_word2.toBytes();
          lenFinal = Math.max(hash3WordByte.length, lambdaPKsByte.length);
          int[] T3intxor = new int[lenFinal];
          for( int  i = 0 ; i < lenFinal ; i++ )
          {
              int x = (int) hash3WordByte[i] ^ (int) lambdaPKsByte[i];
              T3intxor[i] = (byte)x;
          }
          byte[] T3bytexor = new byte[lenFinal];
          for( int  i = 0 ; i < lenFinal ; i++ )
          {
            T3bytexor[i] = (byte) T3intxor[i] ;
          }
          Element T3 = hash3_word.duplicate();
          T3.add(lambdaPKs);
          // Element T3 = pairing.getG1().newElement();
          // bythread = T3.setFromBytes(T3bytexor);

          TrapdoorWord wordSearch = new TrapdoorWord();
          wordSearch.setT1(T1);
          wordSearch.setT2(T2);
          wordSearch.setT3(T3);
          time_generater_key_end  = System.currentTimeMillis();

          wordSearch.setRequiredTime((time_generater_key_end-time_generater_key_start));
          // TEST()

          Element SKsT1 = wordSearch.getT1().duplicate();
          SKsT1.mulZn(sender.getSKu1());
          byte [] SKsT1byte = SKsT1.toBytes();
          byte [] T2byte = wordSearch.getT2().toBytes();
          byte [] T3byte = wordSearch.getT3().toBytes();
        //   int [] T2byte = T2bytexor;
        //   int [] T3byte = T3bytexor;
          lenFinal = Math.max(T2byte.length, SKsT1byte.length);
          int [] T2dashbyte = new int[lenFinal];
          for( int  i = 0 ; i < lenFinal ; i++ )
          {
              int x = (int) T2byte[i] ^ (int) SKsT1byte[i];
              T2dashbyte[i] = x;
          }

          lenFinal = Math.max(T3byte.length, SKsT1byte.length);
          int [] T3dashbyte = new int[lenFinal];
          for( int  i = 0 ; i < lenFinal ; i++ )
          {
              int x = (int) T3byte[i] ^ (int) SKsT1byte[i];
              T3dashbyte[i] = x;
          }

          byte [] T2dashintobyte = new byte[T2dashbyte.length];
          for ( int i = 0 ; i < T2dashbyte.length ; i++)
          {
                T2dashintobyte[i] = (byte)T2dashbyte[i];
          }       
          byte [] T3dashintobyte = new byte[T3dashbyte.length];
          for ( int i = 0 ; i < T2dashbyte.length ; i++)
          {
                T3dashintobyte[i] = (byte)T3dashbyte[i];
          } 
          // Element T2dash = pairing.getG1().newElement();
          // bythread = T2dash.setFromBytes(T2dashintobyte);
          // Element T3dash = pairing.getG1().newElement();
          // bythread = T3dash.setFromBytes(T3dashintobyte);
          Element T2dash = T2.duplicate();
          T2dash.sub(SKsT1);
          Element T3dash = T3.duplicate();
          T3dash.sub(SKsT1);
        //   Element T2dash = H2wSKR.duplicate();
        //   Element T3dash = hash3_word2.duplicate();
        //   System.out.println(" -----------T2dash --- "+ T2dash);
        //   System.out.println(" -----------T3dash --- "+ T3dash);
          Element firstElementPair = T2dash.duplicate();
        //   System.out.println("firstElementPair "+firstElementPair);
          firstElementPair.add(sender.getSKu2()); 
        //   System.out.println("firstElementPair  1  "+firstElementPair);
          firstElementPair.add(T3dash);
        //   System.out.println("firstElementPair 2  "+firstElementPair);
          Element hash4pair =  pairing.pairing(firstElementPair, cipherword.getUi());
          
          byte [ ] hash4pairbyte = hash4pair.toBytes();
  
          BigInteger hash4pairbig = new BigInteger(hash4pairbyte);
          String equality;
          if( hash4pairbig.equals(Vi) )
          {
              System.out.println("\nYes !!! \nsuccessfully  find the word\n");
              equality="\nYes !!! \nsuccessfully  find the word\n"+"\n hash4pairbig  : "+hash4pairbig+"\n Vi()          : "+cipherword.getVi();
          }
          else
          {
                System.out.println("\n oh No oh NO oh No !!! \nsuccessfully didn't find  the word\n");
                equality = "\n oh No oh NO oh No !!! \nsuccessfully didn't find  the word\n"+"\n hash4pairbig  : "+hash4pairbig+"\n Vi()          : "+cipherword.getVi();
          }
          System.out.println(" hash4pairbig  q--- "+hash4pairbig);
          System.out.println("cipherword.getVi()--- "+cipherword.getVi());
          System.out.println("word tarpdoor --- \n"+wordSearch);
          
          System.out.println("word --- \n"+cipherword);

          
        String ret;
        ret = r.toString();
        ret +=" \n sender key------------- \n\n"+sender.toString();
        ret +=" \n receiver key------------- \n\n"+receiver.toString();
        ret +=" \n cipher of word ------------- \n\n"+cipherword.toString();
        ret +=" \n Trapdoor of word ------------- \n\n"+wordSearch.toString();
        ret += "\n Checking Trapdoor gives same reasult or not \n\n";
        ret += equality ;
            return ret;
	}

	
}
