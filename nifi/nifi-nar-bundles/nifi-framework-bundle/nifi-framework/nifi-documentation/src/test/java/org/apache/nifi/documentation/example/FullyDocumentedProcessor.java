package org.apache.nifi.documentation.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.annotation.CapabilityDescription;
import org.apache.nifi.processor.annotation.Tags;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;

@Tags({ "one", "two", "three" })
@CapabilityDescription("This is a processor that is used to test documentation.")
public class FullyDocumentedProcessor extends AbstractProcessor {

	public static final PropertyDescriptor DIRECTORY = new PropertyDescriptor.Builder().name("Input Directory")
			.description("The input directory from which to pull files").required(true)
			.addValidator(StandardValidators.createDirectoryExistsValidator(true, false))
			.expressionLanguageSupported(true).build();

	public static final PropertyDescriptor RECURSE = new PropertyDescriptor.Builder().name("Recurse Subdirectories")
			.description("Indicates whether or not to pull files from subdirectories").required(true)
			.allowableValues("true", "false").defaultValue("true").build();

	public static final PropertyDescriptor POLLING_INTERVAL = new PropertyDescriptor.Builder().name("Polling Interval")
			.description("Indicates how long to wait before performing a directory listing").required(true)
			.addValidator(StandardValidators.TIME_PERIOD_VALIDATOR).defaultValue("0 sec").build();

	public static final PropertyDescriptor OPTIONAL_PROPERTY = new PropertyDescriptor.Builder()
			.name("Optional Property").description("This is a property you can use or not").required(false)
			.build();

	public static final Relationship REL_SUCCESS = new Relationship.Builder().name("success")
			.description("Successful files").build();
	public static final Relationship REL_FAILURE = new Relationship.Builder().name("failure")
			.description("Failing files").build();

	private List<PropertyDescriptor> properties;
	private Set<Relationship> relationships;

	@Override
	protected void init(ProcessorInitializationContext context) {
		final List<PropertyDescriptor> properties = new ArrayList<>();
		properties.add(DIRECTORY);
		properties.add(RECURSE);
		properties.add(POLLING_INTERVAL);
		properties.add(OPTIONAL_PROPERTY);
		this.properties = Collections.unmodifiableList(properties);

		final Set<Relationship> relationships = new HashSet<>();
		relationships.add(REL_SUCCESS);
		relationships.add(REL_FAILURE);
		this.relationships = Collections.unmodifiableSet(relationships);
	}

	@Override
	protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
		return properties;
	}

	@Override
	public Set<Relationship> getRelationships() {
		return relationships;
	}

	@Override
	public void onTrigger(ProcessContext context, ProcessSession session) throws ProcessException {

	}

	@Override
	protected PropertyDescriptor getSupportedDynamicPropertyDescriptor(String propertyDescriptorName) {
		return new PropertyDescriptor.Builder()
		.name(propertyDescriptorName).description("This is a property you can use or not").dynamic(true)
		.build();
	}
	
	
}