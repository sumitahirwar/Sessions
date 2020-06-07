
package com.lti.smooks.example;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.SmooksException;
import org.milyn.container.ExecutionContext;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.io.StreamUtils;
import org.milyn.payload.JavaResult;
import org.xml.sax.SAXException;

import com.lti.smooks.example.Model.Order;
import com.lti.smooks.example.Model.OrderItem;

public class Main {

	private static byte[] messageIn = readInputMessage();

	protected static Order runSmooks() throws IOException, SAXException, SmooksException {

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

	public static void main(String[] args) throws IOException, SAXException, SmooksException {
		System.out.println("\n\n");
		System.out.println("==============Message In==============");
		System.out.println(new String(messageIn));
		System.out.println("======================================\n");

		Order order = Main.runSmooks();

		System.out.println("============Order Javabeans===========");
		System.out.println("Header - Customer Name: " + order.getHeader().getCustomerName());
		System.out.println("       - Customer Num:  " + order.getHeader().getCustomerNumber());
		System.out.println("       - Order Date:    " + order.getHeader().getDate());
		System.out.println("\n");
		System.out.println("Order Items:");
		for (int i = 0; i < order.getOrderItems().size(); i++) {
			OrderItem orderItem = order.getOrderItems().get(i);
			System.out.println("       (" + (i + 1) + ") Product ID:  " + orderItem.getProductId());
			System.out.println("       (" + (i + 1) + ") Quantity:    " + orderItem.getQuantity());
			System.out.println("       (" + (i + 1) + ") Price:       " + orderItem.getPrice());
		}
		System.out.println("======================================");
		System.out.println("\n\n");
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
