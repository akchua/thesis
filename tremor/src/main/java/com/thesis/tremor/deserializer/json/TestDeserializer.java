package com.thesis.tremor.deserializer.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.thesis.tremor.beans.HandFormBean;
import com.thesis.tremor.beans.TestFormBean;
import com.thesis.tremor.enums.TestType;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   16 Sep 2017
 */
public class TestDeserializer extends StdDeserializer<TestFormBean> {

	private static final long serialVersionUID = 8550106600343479684L;
	
	private static final ObjectMapper mapper = new ObjectMapper();

	public TestDeserializer() {
		this(null);
	}
	
	public TestDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public TestFormBean deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode testNode = jp.getCodec().readTree(jp);
		final TestFormBean testForm = new TestFormBean();
		
		try {
			testForm.setTestType(TestType.valueOf(testNode.get("testType").asText()));
			testForm.setName(testNode.get("name").asText());
			testForm.setDuration(testNode.get("duration").asLong());
			testForm.setLeftHand(mapper.readValue(testNode.get("leftHand").toString(), HandFormBean.class));
			testForm.setRightHand(mapper.readValue(testNode.get("rightHand").toString(), HandFormBean.class));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return testForm;
	}
}
