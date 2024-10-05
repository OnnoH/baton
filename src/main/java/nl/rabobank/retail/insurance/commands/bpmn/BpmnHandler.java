package nl.rabobank.retail.insurance.commands.bpmn;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class BpmnHandler extends DefaultHandler {

    private static final String BPMN_NAMESPACE = "bpmn";
    private static final String ZEEBE_NAMESPACE = "zeebe";
    private static final String SERVICE_TASK = "serviceTask";
    private static final String SEND_TASK = "sendTask";
    private static final String CALL_ACTIVITY = "callActivity";
    private static final String BUSINESS_RULE_TASK = "businessRuleTask";
    private static final String USER_TASK = "userTask";
    private static final String RECEIVE_TASK = "receiveTask";
    private static final String MANUAL_TASK = "manualTask";
    private static final String SCRIPT_TASK = "scriptTask";


    private BpmnElement bpmnElement;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        bpmnElement = new BpmnElement();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        super.startElement(uri, localName, qName, attributes);
        switch (qName) {
            case BPMN_NAMESPACE + ":" + SERVICE_TASK:
                String taskName = attributes.getValue("name");
                if (bpmnElement.getTasks() == null) {
                    bpmnElement.setTasks(new ArrayList<>());
                }
                bpmnElement.getTasks().add(taskName);
                break;
        }
    }
//
//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//        super.endElement(uri, localName, qName);
//    }

    public BpmnElement getBpmnElement() {
        return bpmnElement;
    }
}
