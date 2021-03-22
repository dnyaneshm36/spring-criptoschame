package com.dnyanesh.learn.crudjdbc.controller;


import java.io.FileWriter;
import java.io.IOException;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;


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

	@GetMapping("/param")
	public String getAllparam() {

		int rBits = 7;
        int qBits = 20;
        PairingParametersGenerator pg = new TypeACurveGenerator(rBits, qBits);
        PairingParameters params = pg.generate();

        try {
            FileWriter fw = new FileWriter("params1.txt");
            String paramsStr = params.toString();
            fw.write(paramsStr);
            fw.flush();
            fw.close();

        } catch (IOException e) {
            System.out.println("the we get problem in writering ");
            e.printStackTrace();
        }


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

		 String ans="{--- P : [ "+ P +" ]"+
		 "Q : [ "+Q+" ]"+
		 "----}";
		 return ans;
	}

	
}
