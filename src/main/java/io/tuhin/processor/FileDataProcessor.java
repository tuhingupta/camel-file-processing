package io.tuhin.processor;

import io.tuhin.model.Record;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tuhin Gupta
 *
 */
public class FileDataProcessor implements Processor {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	 
	@Override
	public void process(Exchange exchange) throws Exception {
		
		
		ArrayList arylist = exchange.getIn().getBody(ArrayList.class);
		
		if(null != arylist){
			System.out.println("FileDataProcessor List Size "+arylist.size());
		
			for (Iterator iterator = arylist.iterator(); iterator.hasNext();) {
				Record object = (Record) iterator.next();
				
				System.out.println("Record - "+object.getId()+" - "+object.getInvoiceId());
				
			}
		
		}//if
		
		exchange.getOut().setBody(arylist);
		
	}
}
