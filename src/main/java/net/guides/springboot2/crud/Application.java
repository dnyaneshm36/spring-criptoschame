package net.guides.springboot2.crud;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.jpbc.PairingParametersGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

@SpringBootApplication
@EnableJpaAuditing
public class Application {
	public static void main(String[] args) {
		System.out.println("hello world !!!!");

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
    int j = 1;
    
    //KeyGen-Server

    //sks = a; pks. =(A,B)

     long KeyGen_server_start = System.currentTimeMillis();

     Element a = pairing.getZr().newRandomElement(); //sks


         
         System.out.println("P------------is "+P);
         System.out.println("Q------------is "+Q);
         System.out.println("R------------is "+R);
         System.out.println("A------------is "+a);

		SpringApplication.run(Application.class, args);
	}
}
