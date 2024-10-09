package nl.onnoh.baton.commands.bpmn;

import java.util.List;

public class BpmnElement {
    private String name;
    private String type;
    private String worker;

    public BpmnElement(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
