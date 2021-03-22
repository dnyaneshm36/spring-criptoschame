package com.dnyanesh.learn.crudjdbc.controller;



import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;


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

	@GetMapping(value = "/param",  produces = "application/json")
	public String getAllparam() {

		

        //Implamenting the pairing   

        Pairing pairing = PairingFactory.getPairing("params1.txt"); 
        //use pbc wrapper
        PairingFactory.getInstance().setUsePBCWhenPossible(true);
        




    Element P = pairing.getG1().newRandomElement();
    System.out.println("P------------is "+P);
    Element Q = pairing.getG1().newRandomElement();
    Element R = P.add( Q);
    
    
    //KeyGen-Server

    //sks = a; pks. =(A,B)

     long KeyGen_server_start = System.currentTimeMillis();

     Element a = pairing.getZr().newRandomElement(); //sks


         
         System.out.println("P------------is "+P);
         System.out.println("Q------------is "+Q);
         System.out.println("R------------is "+R);
         System.out.println("A------------is "+a);

		 String ans=" P : [ "+ P +" ]"+
		 " , Q : [ "+Q+" ]     --element in java";
         
         return ans;
	}

	
}
