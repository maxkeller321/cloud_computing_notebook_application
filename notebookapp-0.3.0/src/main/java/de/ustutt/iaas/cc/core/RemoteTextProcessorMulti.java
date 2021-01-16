package de.ustutt.iaas.cc.core;

import java.util.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;

/**
 * A text processor that sends the text to one of a set of remote REST API for
 * processing (and balances the load between them round-robin).
 * 
 * @author hauptfn
 *
 */
public class RemoteTextProcessorMulti implements ITextProcessor {

	private List<WebTarget> targets = new ArrayList<WebTarget>();

	public RemoteTextProcessorMulti(List<String> textProcessorResources, Client client) {
		super();
		for (int i = 0; i < textProcessorResources.size(); i++) {
            targets.add(client.target(textProcessorResources.get(i)));
        }
	}

	@Override
	public String process(String text) {
		// TODO send request to "next" text processor endpoint (following some load balancing strategy)
		// Round Robin

		String processedText = " ";
		for (int i = 0; i < targets.size(); i++) {
			try {
				processedText = targets.get(i).request(MediaType.TEXT_PLAIN).post(Entity.entity(text, MediaType.TEXT_PLAIN), String.class);
				break;
			  }
			  catch(Exception e) {
				
				continue;
			  }     
		} 
		return processedText;
	}

}
