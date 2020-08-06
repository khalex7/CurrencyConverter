package com.khalex7.currencyconverter.controller.currencyparser;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class SAXReadData {
    private static ArrayList<Valute> valutes = new ArrayList<>();

    private static String date;

    public static void readCurrency() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        parser.parse(new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream(), handler);
    }

    private static class XMLHandler extends DefaultHandler {
        private String numCodeStr;
        private String charCode;
        private String nominalStr;
        private String name;
        private String value;

        private String lastElementName;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            lastElementName = qName;
            if (qName.equals("ValCurs")) {
                date = attributes.getValue("Date");
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {
                if (lastElementName.equals("NumCode"))
                    numCodeStr = information;
                if (lastElementName.equals("CharCode"))
                    charCode = information;
                if (lastElementName.equals("Nominal"))
                    nominalStr = information;
                if (lastElementName.equals("Name"))
                    name = information;
                if (lastElementName.equals("Value"))
                    value = information;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (    (numCodeStr != null && !numCodeStr.isEmpty()) &&
                    (charCode != null && !charCode.isEmpty()) &&
                    (nominalStr != null && !nominalStr.isEmpty()) &&
                    (name != null && !name.isEmpty()) &&
                    (value != null && !value.isEmpty()) ) {
                valutes.add(new Valute(Long.parseLong(numCodeStr), charCode, Long.parseLong(nominalStr), name, value));

                numCodeStr = null;
                charCode = null;
                nominalStr = null;
                name = null;
                value = null;
            }
        }
    }
}
