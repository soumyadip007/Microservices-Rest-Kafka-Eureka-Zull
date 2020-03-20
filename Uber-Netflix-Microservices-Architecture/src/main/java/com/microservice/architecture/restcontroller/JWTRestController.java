package com.microservice.architecture.restcontroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.architecture.jwt.JWTUtil;
import com.microservice.architecture.models.AuthenticationRequest;
import com.microservice.architecture.models.AuthenticationResponse;
import com.microservice.architecture.security.MyUserDetailsService;

@RestController
public class JWTRestController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Autowired
	JWTUtil jwtutil;
	
	@RequestMapping("/start")
	public String start()
	{
		return "micro";
	}
	
	@RequestMapping(value="/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken
				(@RequestBody AuthenticationRequest authenticateRequest) throws Exception
	{

		System.out.println(authenticateRequest.getUsername());
		System.out.println(authenticateRequest.getUsername());
		System.out.println(authenticateRequest.getPassword());
		
		try {
			
			UsernamePasswordAuthenticationToken obj=new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(), 
					authenticateRequest.getPassword());
			
			System.out.println(obj.toString());
			
			authenticationManager.authenticate(obj);
		}
		catch (BadCredentialsException e) {

				e.printStackTrace();
				throw new Exception("Bad Credentioals");
				
		}
		catch (Exception e) {
				
				e.printStackTrace();
			
		}

		final UserDetails userDetails=myUserDetailsService
				.loadUserByUsername(authenticateRequest.getUsername());
	
		final String jwt=jwtutil.generateToken(userDetails);
		
		return  ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	
}
