package com.dnyanesh.learn.crudjdbc.controller;




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
        String P ="first";
        String Q = "seconkd" ;
		 String ans="{ P : [ "+ P +" ]"+
		 "Q : [ "+Q+" ]"+
		 "}";
		 return ans;
	}

	
}
