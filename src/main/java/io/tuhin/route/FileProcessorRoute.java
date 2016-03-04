package io.tuhin.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.apache.camel.spi.DataFormat;

/**
 * @author Tuhin Gupta
 *
 */
public class FileProcessorRoute  extends RouteBuilder {
	
	DataFormat format = new BeanIODataFormat("classpath:beanio/CSVFileToJava.xml", "csvFile");
	
	private static final String CLASS_NAME = FileProcessorRoute.class.getName();
	
	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
		
		 from("activemq:queue:LOAD_FILE_DATA"+"?concurrentConsumers=3")
         .onException(Throwable.class)
             .logStackTrace(true)
             .to("log:" + CLASS_NAME + exceptionMessage() +"?level=ERROR&showAll=true")
            // .beanRef("exceptionBean")
             .handled(true)
        .end()
        .routeId("PROCESS_MESSAGES")
            .unmarshal(format)
            .processRef("fileDataProcessor")
        .end()
        .inOnly("activemq:queue:STORE_DATA")
                .setHeader("OPERATION_NAME", simple("${header.OPERATION_NAME}"))
                .setHeader("SOURCE_NAME", simple("${header.SOURCE_NAME}"))
        .end()
        ;
		
	}

}
