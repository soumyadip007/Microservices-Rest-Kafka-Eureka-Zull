package com.microservice.architecture.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.microservice.architecture.handler.GA4GHReactiveHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;


@Configuration
public class RouterFunctionConfig {
	
	@Bean
	public RouterFunction<ServerResponse> route(GA4GHReactiveHandler reactiveHandler)
	{
		return RouterFunctions
			.route(GET("/functional/flux")
					.and(accept(MediaType.APPLICATION_JSON))
							,reactiveHandler::flux)
			.andRoute(GET("/functional/flux")
					.and(accept(MediaType.APPLICATION_JSON))
							,reactiveHandler::mono);
	
	}

}