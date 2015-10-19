package com.pgs.taxidriver.utils;

import org.primefaces.model.map.LatLng;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlasota on 2015-09-08.
 */

@Service
public class XMLParser {
    // The URL shown in these examples is a static URL which should already
    // be URL-encoded. In practice, you will likely have code
    // which assembles your URL from user or web service input
    // and plugs those values into its parameters.
    private static final String DISTANCE_REQUEST_PREFIX = "https://maps.googleapis.com/maps/api/distancematrix/xml?";

    // Note: The default XPath expression "/" selects
    // all text within the XML.
    private static String XPATH_EXPRESSION = "//text()";

    public String _xpath = null;
    public Document _xml = null;

    public String travelTime(LatLng x, LatLng y) throws IOException, URISyntaxException, ParserConfigurationException,
            SAXException {

        String urlString, xPathString = null;

        String origin = String.valueOf(x.getLat());
        origin += "," + String.valueOf(x.getLng());

        String destination = String.valueOf(y.getLat());
        destination += "," + String.valueOf(y.getLng());

        urlString =
                DISTANCE_REQUEST_PREFIX + "origins=" + origin + "&destinations=" + destination
                        + "&language=en-EN&key=AIzaSyA1j7AjyeIwkuR1AOFGL_LtFRdJ3SDYhpY";

        System.out.println(urlString);

        // Convert the string to a URL so we can parse it
        URL url = new URL(urlString);

        // For testing purposes, allow user input for the XPath expression.
        // If no input is entered, use the default expression defined above.
        xPathString = XPATH_EXPRESSION;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        Document resultDocument = null;
        try {
            // open the connection and get results as InputSource.
            conn.connect();
            InputSource resultInputSource = new InputSource(conn.getInputStream());

            // read result and parse into XML Document
            resultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resultInputSource);
        } finally {
            conn.disconnect();
        }

        // Process the results
        NodeList nodes = process(resultDocument, xPathString);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < nodes.getLength(); i++) {
            list.add(nodes.item(i).getNodeValue());

        }
        String time = list.get(14);

        return time;
    }

    public NodeList process(Document xml, String xPathString) throws IOException {

        NodeList result = null;

        XPathFactory factory = XPathFactory.newInstance();

        XPath xpath = factory.newXPath();

        try {
            result = (NodeList) xpath.evaluate(xPathString, xml, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            // Deal with it
        }
        return result;
    }
}
