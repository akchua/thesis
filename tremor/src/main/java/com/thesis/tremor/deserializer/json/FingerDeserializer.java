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
import com.thesis.tremor.beans.FloatingPoint;
import com.thesis.tremor.enums.FingerType;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   22 Sep 2017
 */
public class FingerDeserializer extends StdDeserializer<FingerFormBean> {

	private static final long serialVersionUID = 8288031528565159118L;
	
	private static final ObjectMapper mapper = new ObjectMapper();

	public FingerDeserializer() {
		this(null);
	}
	
	public FingerDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public FingerFormBean deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode fingerNode = jp.getCodec().readTree(jp);
		final FingerFormBean fingerForm = new FingerFormBean();
		
		try {
			fingerForm.setAverageAmplitude(fingerNode.get("averageAmplitude").floatValue());
			fingerForm.setAverageFrequency(fingerNode.get("averageFrequency").floatValue());
			List<FloatingPoint> fingerPoints = new ArrayList<FloatingPoint>();
			for(JsonNode fingerPointNode : fingerNode.get("fingerPoints")) {
				FloatingPoint fingerPoint = mapper.readValue(fingerPointNode.toString(), FloatingPoint.class);
				fingerPoints.add(fingerPoint);
			}
			fingerForm.setFingerPoints(fingerPoints);
			fingerForm.setFingerType(FingerType.valueOf(fingerNode.get("fingerType").asText()));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return fingerForm;
	}
}
