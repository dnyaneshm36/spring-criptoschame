package com.dnyanesh.learn.crudjdbc.controller;



import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

import com.dnyanesh.learn.crudjdbc.model.ClientKey;
import com.dnyanesh.learn.crudjdbc.model.SetupParamter;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/pbc")
public class JpbcCheckController {
    
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

	@GetMapping(value = "/setup",  produces = "application/json")
	public String getSetupParam() {

		

        //Implamenting the pairing   
        long KeyGen_server_start = System.currentTimeMillis();
        Pairing pairing = PairingFactory.getPairing("params1.txt"); 
        //use pbc wrapper
        PairingFactory.getInstance().setUsePBCWhenPossible(true);
        
        
        Element P = pairing.getG1().newRandomElement();
        Element master_key_lamda = pairing.getZr().newRandomElement();
        Element PKc = P.duplicate();
        PKc.mulZn(master_key_lamda);
        Element SKs = master_key_lamda.duplicate();
        long KeyGen_server_end = System.currentTimeMillis();
        SetupParamter r = new SetupParamter(P,master_key_lamda,PKc,SKs,(KeyGen_server_end-KeyGen_server_start)) ;
        
        
        System.out.println("--------------  object "+r);
         
        
        long  time_generater_key_start  = System.currentTimeMillis();
        ClientKey sender;
        String senderId = "senderid";
        byte [] senderbyte = senderId.getBytes();

        Element Qus = pairing.getG1().newElement().setFromHash(senderbyte, 0, senderbyte.length);
        Element Dus = Qus.duplicate();
        Dus.mulZn(master_key_lamda);

        sender = new ClientKey(senderId,Qus , Dus);
        
        Element Sus = pairing.getZr().newRandomElement();
        Element SKu1s = Sus.duplicate();
        Element SKu2s = Dus.duplicate();
        Dus.mulZn(Sus);

        Element PKu1s = P.duplicate();
        PKu1s.mulZn(Sus);

        Element PKu2s = PKc.duplicate();
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

        Element Sur = pairing.getZr().newRandomElement();
        Element SKu1r = Sur.duplicate();
        Element SKu2r = Dur.duplicate();
        Dus.mulZn(Sur);

        Element PKu1r = P.duplicate();
        PKu1s.mulZn(Sur);

        Element PKu2r = PKc.duplicate();
        PKu1s.mulZn(Sur);

        receiver.setPKu1(PKu1r);
        receiver.setPKu2(PKu2r);
        receiver.setSKu1(SKu1r);
        receiver.setSKu2(SKu2r);
        receiver.setSu(Sur);


        time_generater_key_end  = System.currentTimeMillis();
        receiver.setRequredTime((time_generater_key_end-time_generater_key_start));

        // CLPKES

        //checking pair is equal.
        Element pair_sender_PKc,pair_sender_P;
        // System.out.println("pku1    "+sender.getPKu1());
        // System.out.println("PKC   "+  r.getPKc() );
        // System.out.println("getPKu2  "+sender.getPKu2());
        // System.out.println("getp   "+r.getP());
        pair_sender_PKc =  pairing.pairing(sender.getPKu1(), r.getPKc());
        pair_sender_P  = pairing.pairing(sender.getPKu2(), r.getP());

        if( pair_sender_PKc.isEqual(pair_sender_P) )
		{
			System.out.println("Pairing equal for sender"+pair_sender_P+" \n - "+pair_sender_PKc+" \n");
		}
		else
		{
			System.out.println("fail turn ⊥ and about\n"+pair_sender_P+" \n - "+pair_sender_PKc+" \n");
		}

        //checking pair is equal.
        Element pair_receiver_PKc,pair_receiver_P;

        pair_receiver_PKc =  pairing.pairing(receiver.getPKu1(), r.getPKc());
        pair_receiver_P  = pairing.pairing(receiver.getPKu2(), r.getP());


        if( pair_receiver_PKc.isEqual(pair_receiver_P) )
		{
			System.out.println("\nPairing equal for receiver "+pair_sender_P+" \n - "+pair_sender_PKc+" \n");
		}
		else
		{
			System.out.println("fail turn ⊥ and about\n"+pair_sender_P+" \n - "+pair_sender_PKc+" \n");
		}

        String word = "wordto_encrption";
        Element Ri;
        Ri = pairing.getZr().newRandomElement();

        // Element Ti,pair1,pair2,pair3,first,hash3_word;
        String data = "";

        try {
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
          
          System.out.println("returl val "+rethash);

        String ret;
        ret = r.toString();
        ret +=" \n sender key------------- \n\n"+sender.toString();
        ret +=" \n receiver key------------- \n\n"+receiver.toString();
            return ret;
	}

	
}
