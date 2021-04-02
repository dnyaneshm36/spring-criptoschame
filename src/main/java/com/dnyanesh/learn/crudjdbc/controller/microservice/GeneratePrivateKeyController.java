package com.dnyanesh.learn.crudjdbc.controller.microservice;

import java.io.IOException;

import com.dnyanesh.learn.crudjdbc.model.microservice.receiverobject.GeneratePrivateKeyReceiver;
import com.dnyanesh.learn.crudjdbc.model.microservice.senderobject.GeneratePrivateKey;


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
public class GeneratePrivateKeyController {
    

    @GetMapping(value = "/gPriK",  
    consumes = {MediaType.APPLICATION_JSON_VALUE },
    produces = {MediaType.APPLICATION_JSON_VALUE }  )
    public ResponseEntity<GeneratePrivateKey> GeneratePrivatekey ( @RequestBody GeneratePrivateKeyReceiver receiverbody ) throws IOException {

            long gPriK_Start = System.currentTimeMillis();
            //Implamenting the pairing   
            Pairing pairing = PairingFactory.getPairing("params1.txt"); 
            //use pbc wrapper
            PairingFactory.getInstance().setUsePBCWhenPossible(true);

            String suString = receiverbody.getSu();
            String duString = receiverbody.getDu();

            Element Su = pairing.getZr().newElementFromBytes(Base64.decode(suString));
            Element SKu1 = Su.duplicate();
            Element Du = pairing.getG1().newElementFromBytes(Base64.decode(duString));
            // System.out.println(" D u in the private key gnen\n"+Du);
            
            Element SKu2 = Du.duplicate();
            SKu2.mulZn(Su); 

            // System.out.println("secrete keyes");
            // System.out.println("SKu1 \n"+SKu1);
            // System.out.println("SKu2 \n"+SKu2);
            String sku1string = Base64.encodeBytes(SKu1.toBytes());;
            String sku2string = Base64.encodeBytes(SKu2.toBytes());;
            long gPriK_End = System.currentTimeMillis();
            long time = gPriK_Start -gPriK_End;
            GeneratePrivateKey gPriK = new GeneratePrivateKey(sku1string,sku2string,time);
		        
        return ResponseEntity.ok().body(gPriK);
    }
}
