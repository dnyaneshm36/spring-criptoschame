package com.dnyanesh.learn.crudjdbc.controller;



import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import com.dnyanesh.learn.crudjdbc.model.ClientKey;
import com.dnyanesh.learn.crudjdbc.model.SetupParamter;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/pbc")
public class JpbcCheckController {
  
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
        Element PKs = P.duplicate();
        PKs.mulZn(master_key_lamda);
        Element SKs = master_key_lamda.duplicate();
        long KeyGen_server_end = System.currentTimeMillis();
        SetupParamter r = new SetupParamter(P,master_key_lamda,PKs,SKs,(KeyGen_server_end-KeyGen_server_start)) ;

        
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

        Element PKu2s = PKs.duplicate();
        PKu1s.mulZn(Sus);

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

        Element PKu2r = PKs.duplicate();
        PKu1s.mulZn(Sur);

        receiver.setPKu1(PKu1r);
        receiver.setPKu2(PKu2r);
        receiver.setSKu1(SKu1r);
        receiver.setSKu2(SKu2r);
        receiver.setSu(Sur);


        time_generater_key_end  = System.currentTimeMillis();
        receiver.setRequredTime((time_generater_key_end-time_generater_key_start));

        String ret;
        ret = r.toString();
        ret +=" \n sender key------------- \n\n"+sender.toString();
        ret +=" \n receiver key------------- \n\n"+receiver.toString();
            return ret;
	}

	
}
