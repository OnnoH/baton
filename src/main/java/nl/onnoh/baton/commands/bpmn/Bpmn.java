package nl.onnoh.baton.commands.bpmn;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bpmn {

    @SerializedName("tasks")
    List<BpmnElement> bpmnElements;

    public Bpmn() {
    }

    public List<BpmnElement> getBpmnElements() {
        return bpmnElements;
    }

    public void setBpmnElements(List<BpmnElement> bpmnElements) {
        this.bpmnElements = bpmnElements;
    }
}

