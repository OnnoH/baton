package nl.onnoh.baton.commands.bpmn;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bpmn {

    @SerializedName("tasks")
    List<BpmnElement> bpmnElements;

    public Bpmn() {
    }

    public void setBpmnElements(List<BpmnElement> bpmnElements) {
        this.bpmnElements = bpmnElements;
    }
}

