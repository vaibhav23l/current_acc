package com.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class RestObjectConvertor {

	public String convertToJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			

			// Convert object to JSON string
			json = mapper.writeValueAsString(o);
			System.out.println(json);

			// Convert object to JSON string and pretty print
			json = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(o);
			System.out.println(json);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
}
