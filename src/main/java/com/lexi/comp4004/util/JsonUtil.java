package com.lexi.comp4004.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

	static final ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
			.configure(JsonParser.Feature.ALLOW_COMMENTS, true)
			.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	static final JsonFactory factory = objectMapper.getFactory();

	public static String stringify(Object object) throws Exception {
		if (object == null) {
			return null;
		}
		StringWriter sw = new StringWriter();
		JsonGenerator jgen = factory.createGenerator(sw);
		jgen.writeObject(object);
		jgen.close();
		return sw.toString();
	}

	public static <O> O parse(String json, Class<O> objectClass) throws JsonParseException, IOException {
		if (json == null) {
			return null;
		}
		JsonParser jp = factory.createParser(json);
		return jp.readValueAs(objectClass);
	}

	public static <O> O parseList(String json, Class<?> objectClass) throws JsonParseException, IOException {
		if (json == null) {
			return null;
		}
		return objectMapper.readValue(json,
				objectMapper.getTypeFactory().constructCollectionType(List.class, objectClass));
	}

}