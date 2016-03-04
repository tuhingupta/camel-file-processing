package io.tuhin.route;

import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileLoaderRoute  extends RouteBuilder{
	

	
	private static final String CLASS_NAME = FileLoaderRoute.class.getName();

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
	
		onException(Throwable.class)
        //.logStackTrace(true)
        .to("log:" + CLASS_NAME + exceptionMessage() +"?level=ERROR&showAll=true")
        .handled(true)
    ;
    
    
    /* 
     * route for reading the file - read the file, split and queue the message into activemq:queue:LOAD_FILE_DATA for processing
     */
    
    from("file:/Users/tgupt24/Documents/temp/in?readLock=changed&readLockCheckInterval=10")
    .routeId("LOAD_FILE")
    .process(new Processor() {
        public void process(Exchange exchange) throws Exception {

            String fileNameOnly = (String)exchange.getIn().getHeader(Exchange.FILE_NAME_ONLY);
            String sourceName = new String();

                            
            exchange.getIn().setHeader("OPERATION_NAME", "OPERATION_NAME_BATCH");
            
            
            exchange.setProperty("OPERATION_NAME", "OPERATION_NAME_BATCH");
            

        }
        })
        .split().tokenize("\n", 2).streaming()
        .throttle(50).timePeriodMillis(10000)
        .inOnly("activemq:queue:LOAD_FILE_DATA")
   .to("file:/Users/tgupt24/Documents/temp/out?fileName=outfile&fileExist=Append&flatten=true")
   ;
		
	}
}
