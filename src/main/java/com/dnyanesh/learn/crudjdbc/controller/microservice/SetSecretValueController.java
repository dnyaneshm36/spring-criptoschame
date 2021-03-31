package com.dnyanesh.learn.crudjdbc.controller.microservice;

import java.io.IOException;

import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.SetSecretValue;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

@RestController
@RequestMapping("/microservice/clpeks")
public class SetSecretValueController {
    @GetMapping(value = "/sSV",  produces = "application/json")
    public ResponseEntity<SetSecretValue> SetSecretevalue(  ) throws IOException {
                long Su_Start = System.currentTimeMillis();
                //Implamenting the pairing   
                Pairing pairing = PairingFactory.getPairing("params1.txt"); 
                //use pbc wrapper
                String DescribeString = "this Encripted element of given client secrete { identifier } ";
                PairingFactory.getInstance().setUsePBCWhenPossible(true);

                Element Su = pairing.getG1().newRandomElement();
                String Sustring = Base64.encodeBytes(Su.toBytes());
                long Su_End = System.currentTimeMillis();

                long time = Su_End - Su_Start;
        

        SetSecretValue sSV = new SetSecretValue(DescribeString,Sustring,time);
		        
        return ResponseEntity.ok().body(sSV);
    }
}
