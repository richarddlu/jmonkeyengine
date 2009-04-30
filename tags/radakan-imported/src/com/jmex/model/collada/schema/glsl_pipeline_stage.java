/**
 * glsl_pipeline_stage.java
 *
 * This file was generated by XMLSpy 2007sp2 Enterprise Edition.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the XMLSpy Documentation for further details.
 * http://www.altova.com/xmlspy
 */


package com.jmex.model.collada.schema;

import com.jmex.xml.types.SchemaString;

public class glsl_pipeline_stage extends SchemaString {
	public static final int EVERTEXPROGRAM = 0; /* VERTEXPROGRAM */
	public static final int EFRAGMENTPROGRAM = 1; /* FRAGMENTPROGRAM */

	public static String[] sEnumValues = {
		"VERTEXPROGRAM",
		"FRAGMENTPROGRAM",
	};

	public glsl_pipeline_stage() {
		super();
	}

	public glsl_pipeline_stage(String newValue) {
		super(newValue);
		validate();
	}

	public glsl_pipeline_stage(SchemaString newValue) {
		super(newValue);
		validate();
	}

	public static int getEnumerationCount() {
		return sEnumValues.length;
	}

	public static String getEnumerationValue(int index) {
		return sEnumValues[index];
	}

	public static boolean isValidEnumerationValue(String val) {
		for (int i = 0; i < sEnumValues.length; i++) {
			if (val.equals(sEnumValues[i]))
				return true;
		}
		return false;
	}

	public void validate() {

		if (!isValidEnumerationValue(toString()))
			throw new com.jmex.xml.xml.XmlException("Value of glsl_pipeline_stage is invalid.");
	}
}
