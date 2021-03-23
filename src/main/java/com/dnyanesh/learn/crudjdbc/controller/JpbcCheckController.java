package com.dnyanesh.learn.crudjdbc.controller;



import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

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

         System.out.println("  object "+r.toString());
        
         System.out.println("--------------  object "+r);
         

        // String content = "{"id":1,"name":"ram"}";
            // Gson gson = new Gson();    
            // String jsobog =  gson.toJson(r); 
            // System.out.println("-----**********??????json poject"+ jsobog);

           
            return r.toString();
	}

	
}
