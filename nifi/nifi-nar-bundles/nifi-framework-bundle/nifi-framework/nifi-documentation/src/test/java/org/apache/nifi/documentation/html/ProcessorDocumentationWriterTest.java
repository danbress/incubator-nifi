/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.documentation.html;

import static org.apache.nifi.documentation.html.XmlValidator.assertContains;
import static org.apache.nifi.documentation.html.XmlValidator.assertNotContains;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.documentation.DocumentationWriter;
import org.apache.nifi.documentation.example.FullyDocumentedProcessor;
import org.apache.nifi.documentation.mock.MockProcessorInitializationContext;
import org.junit.Test;

public class ProcessorDocumentationWriterTest {

	@Test
	public void testFullyDocumentedProcessor() throws IOException {
		FullyDocumentedProcessor processor = new FullyDocumentedProcessor();
		processor.initialize(new MockProcessorInitializationContext());

		DocumentationWriter writer = new HtmlProcessorDocumentationWriter();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		writer.write(processor, baos, false);

		String results = new String(baos.toByteArray());

		assertContains(results, FullyDocumentedProcessor.DIRECTORY.getDisplayName());
		assertContains(results, FullyDocumentedProcessor.DIRECTORY.getDescription());
		assertContains(results, FullyDocumentedProcessor.OPTIONAL_PROPERTY.getDisplayName());
		assertContains(results, FullyDocumentedProcessor.OPTIONAL_PROPERTY.getDescription());
		assertContains(results, FullyDocumentedProcessor.POLLING_INTERVAL.getDisplayName());
		assertContains(results, FullyDocumentedProcessor.POLLING_INTERVAL.getDescription());
		assertContains(results, FullyDocumentedProcessor.POLLING_INTERVAL.getDefaultValue());
		assertContains(results, FullyDocumentedProcessor.RECURSE.getDisplayName());
		assertContains(results, FullyDocumentedProcessor.RECURSE.getDescription());

		assertContains(results, FullyDocumentedProcessor.REL_SUCCESS.getName());
		assertContains(results, FullyDocumentedProcessor.REL_SUCCESS.getDescription());
		assertContains(results, FullyDocumentedProcessor.REL_FAILURE.getName());
		assertContains(results, FullyDocumentedProcessor.REL_FAILURE.getDescription());

		assertNotContains(results, "iconSecure.png");
		assertContains(results, FullyDocumentedProcessor.class.getAnnotation(CapabilityDescription.class).value());
		assertNotContains(results, "This component has no required or optional properties.");
		assertNotContains(results, "No description provided.");
		assertNotContains(results, "No Tags provided.");
		assertNotContains(results, "Additional Details...");
	}

}