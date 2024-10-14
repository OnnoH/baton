package nl.onnoh.baton.commands.bpmn;

import java.util.List;
import java.util.Map;

public class BpmnElement {
    private String name;
    private String type;
    private String worker;
    private Map<String, String> input;
    private Map<String, String> output;
    private Map<String, String> headers;

    public BpmnElement(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public void setInput(Map<String, String> input) {
        this.input = input;
    }

    public void setOutput(Map<String, String> output) {
        this.output = output;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
