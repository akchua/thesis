package com.thesis.tremor.serializer.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thesis.tremor.database.entity.Hand;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   10 Sep 2017
 */
public class HandSerializer extends JsonSerializer<Hand> {

	@Override
	public void serialize(Hand hand, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeObject(hand);
	}
}
