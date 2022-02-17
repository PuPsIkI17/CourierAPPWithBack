package com.tazz.courier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tazz.parameters.Data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourierApplicationTests {

	@Test
	void testJSONfiles() throws JsonMappingException, JsonProcessingException {

		// number of tests
		final int N = 7;
		int i;
		for (i = 1; i < N; i++) {
			// read input
			String inputName = "./src/test/java/com/tazz/courier/input/" + i + ".json";
			String jsonString = usingBufferedReader(inputName);
			ObjectMapper objectMapper = new ObjectMapper();
			Data data = objectMapper.readValue(jsonString, Data.class);

			// read otuput
			String outputName = "./src/test/java/com/tazz/courier/output/out" + i + ".json";
			String outputjson = usingBufferedReader(outputName);

			// test the output with the expected one
			AssignCouriersController assignCouriersController = new AssignCouriersController();
			String assigned = assignCouriersController.assignCouriers(data);
			assertEquals(outputjson.trim(), assigned.trim());
		}
	}

	private static String usingBufferedReader(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				contentBuilder.append(sCurrentLine).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}

}
