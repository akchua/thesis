package com.thesis.tremor.deserializer.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.thesis.tremor.beans.SessionFormBean;
import com.thesis.tremor.beans.TestFormBean;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   16 Sep 2017
 */
public class SessionDeserializer extends StdDeserializer<SessionFormBean> {

	private static final long serialVersionUID = 8288031528565159118L;
	
	private static final ObjectMapper mapper = new ObjectMapper();

	public SessionDeserializer() {
		this(null);
	}
	
	public SessionDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public SessionFormBean deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		final SessionFormBean sessionForm = new SessionFormBean();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		try {
			JsonNode sessionNode = node.get("session");
			sessionForm.setDateDone(sdf.parse(sessionNode.get("dateDone").asText()));
			List<TestFormBean> testForms = new ArrayList<TestFormBean>();
			for(JsonNode testNode : sessionNode.get("tests")) {
				TestFormBean testForm = mapper.readValue(testNode.toString(), TestFormBean.class);
				testForms.add(testForm);
			}
			sessionForm.setTests(testForms);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return sessionForm;
	}
}
