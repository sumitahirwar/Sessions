package com.lti.smooks.example;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.io.StreamUtils;
import org.milyn.payload.JavaResult;
import org.xml.sax.SAXException;

import com.lti.smooks.example.Model.Order;

public class OrderConverter {

	private static byte[] messageIn = readInputMessage();

	protected static Order convertOrderXMLToOrderObject() throws IOException, SAXException {

		Smooks smooks = new Smooks("smooks-mapping.xml");
		try {
			ExecutionContext executionContext = smooks.createExecutionContext();
			JavaResult result = new JavaResult();
			executionContext.setEventListener(new HtmlReportGenerator("target/report/report.html"));
			smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageIn)), result);
			return (Order) result.getBean("order");
		} finally {
			smooks.close();
		}
	}

	public static void main(String[] args) throws IOException, SAXException {

		System.out.println("\n\n");
		System.out.println("==============Message In==============");
		System.out.println(new String(messageIn));
		System.out.println("======================================\n");

		Order order = OrderConverter.convertOrderXMLToOrderObject();

		System.out.println("\n\n");
		System.out.println("==============Message Out==============");
		System.out.println("Order Creation Date:" + order.getCreationDate());
		System.out.println("Order Number: " + order.getNumber());
		System.out.println("Order Status: " + order.getStatus());
		System.out.println("======================================\n");
	}

	private static byte[] readInputMessage() {
		try {
			return StreamUtils.readStream(new FileInputStream("order.xml"));
		} catch (IOException e) {
			e.printStackTrace();
			return "<no-message/>".getBytes();
		}
	}

}


