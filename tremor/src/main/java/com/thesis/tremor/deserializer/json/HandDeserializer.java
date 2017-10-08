package com.thesis.tremor.deserializer.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.thesis.tremor.beans.FingerFormBean;
import com.thesis.tremor.beans.HandFormBean;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   22 Sep 2017
 */
public class HandDeserializer extends StdDeserializer<HandFormBean> {

	private static final long serialVersionUID = 8288031528565159118L;
	
	private static final ObjectMapper mapper = new ObjectMapper();

	public HandDeserializer() {
		this(null);
	}
	
	public HandDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public HandFormBean deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode leftHandNode = jp.getCodec().readTree(jp);
		final HandFormBean handForm = new HandFormBean();
		
		try {
			List<FingerFormBean> fingers = new ArrayList<FingerFormBean>();
			for(JsonNode fingersNode : leftHandNode.get("fingers")) {
				System.out.println("here");
				FingerFormBean finger = mapper.readValue(fingersNode.toString(), FingerFormBean.class);
				fingers.add(finger);
			}
			handForm.setFingers(fingers);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return handForm;
	}
}
