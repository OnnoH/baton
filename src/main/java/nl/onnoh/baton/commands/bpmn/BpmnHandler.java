package nl.onnoh.baton.commands.bpmn;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static nl.onnoh.baton.commands.bpmn.BpmnModelConstants.*;
import static nl.onnoh.baton.commands.bpmn.BpmnModelZeebeConstants.*;

public class BpmnHandler extends DefaultHandler {

    private Bpmn bpmn;
    private BpmnElement bpmnElement;
    private List<BpmnElement> bpmnElements = new ArrayList<>();
    private StringBuilder elementValue;
    private boolean rootElementProcessed = false;
    private String bpmnNamespace = "";
    private String zeebeNamespace = "";

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

//    @Override
//    public void startDocument() throws SAXException {
//        bpmnElement = new BpmnElement();
//    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (!rootElementProcessed) {
            rootElementProcessed = true;
            bpmnNamespace = getNamespace(BPMN20_NS, attributes).replaceAll("xmlns:", "");
            zeebeNamespace = getNamespace(ZEEBE_NS, attributes).replaceAll("xmlns:", "");
        }

        if (getBpmnElement(BPMN_ELEMENT_DEFINITIONS).equals(qName)) {
            bpmn = new Bpmn();
        } else if (getBpmnElement(BPMN_ELEMENT_SERVICE_TASK).equals(qName)
                    || getBpmnElement(BPMN_ELEMENT_RECEIVE_TASK).equals(qName)
                    || getBpmnElement(BPMN_ELEMENT_SEND_TASK).equals(qName)
                    || getBpmnElement(BPMN_ELEMENT_BUSINESS_RULE_TASK).equals(qName)
                    || getBpmnElement(BPMN_ELEMENT_USER_TASK).equals(qName)
        ) {
            String taskName = attributes.getValue("name");
            bpmnElement = new BpmnElement(taskName, qName);
        } else if (getZeebeElement(ZEEBE_TASK_DEFINITION).equals(qName)) {
            String workerName = attributes.getValue("type");
            bpmnElement.setWorker(workerName);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (getBpmnElement(BPMN_ELEMENT_DEFINITIONS).equals(qName)) {
            bpmn.setBpmnElements(bpmnElements);
        } else if (getBpmnElement(BPMN_ELEMENT_SERVICE_TASK).equals(qName)
                || getBpmnElement(BPMN_ELEMENT_RECEIVE_TASK).equals(qName)
                || getBpmnElement(BPMN_ELEMENT_SEND_TASK).equals(qName)
                || getBpmnElement(BPMN_ELEMENT_BUSINESS_RULE_TASK).equals(qName)
                || getBpmnElement(BPMN_ELEMENT_USER_TASK).equals(qName)
        ) {
            bpmnElements.add(bpmnElement);
        }

    }

    public Bpmn getBpmn() {
        return bpmn;
    }

    private String getNamespace(String uri, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            if (attributes.getValue(i).equals(uri)) {
                return attributes.getQName(i).replaceAll("xmlns:", "");
            };
        }
        return "";
    }

    private String getBpmnElement(String elementName) {
        return bpmnNamespace + ":" + elementName;
    }

    private String getZeebeElement(String elementName) {
        return zeebeNamespace + ":" + elementName;
    }

}
