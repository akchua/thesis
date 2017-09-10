package com.thesis.tremor.serializer.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thesis.tremor.database.entity.Session;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   10 Sep 2017
 */
public class SessionSerializer extends JsonSerializer<Session> {

	@Override
	public void serialize(Session session, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeObject(session);
	}
}
