package nl.onnoh.baton.commands.bpmn;

import com.google.gson.Gson;
import picocli.CommandLine;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "analyse", description = "Analyse a BPMN file.")
public class AnalyseCommand implements Callable<Integer> {

    private static final String ZEEBE_TEMPLATE = "modelerTemplate";

    @CommandLine.Parameters(index = "0", description = "The BPMN file to analyse.")
    private String bpmnFile;

    public String getBpmnFile() {
        return bpmnFile;
    }

    public void setBpmnFile(String bpmnFile) {
        this.bpmnFile = bpmnFile;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new AnalyseCommand()).execute("src/main/resources/process.bpmn");
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Analyzing " + bpmnFile);
        File file = new File(bpmnFile);
        try {
            if (!file.exists()) {
                System.err.println("File not found: " + bpmnFile);
                return 1;
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + bpmnFile);
            return 1;
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        BpmnHandler bpmnHandler = new BpmnHandler();
        saxParser.parse(bpmnFile, bpmnHandler);
        Bpmn bpmn = bpmnHandler.getBpmn();
        Gson gson = new Gson();
        System.out.println(gson.toJson(bpmn));

//        System.out.println("File found: " + bpmnFile);
//        BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);
////        BpmnModelInstance modelInstance = Bpmn.readModelFromStream(Bpmn.class.getClassLoader().getResourceAsStream("process.bpmn"));
//        System.out.println("Model instance: " + modelInstance);
//        ModelElementType taskType = modelInstance.getModel().getType(Task.class);
//        Collection<ModelElementInstance> elementInstances = modelInstance.getModelElementsByType(taskType);
//        for (ModelElementInstance elementInstance : elementInstances) {
//            Task task = (Task) elementInstance;
//            String elementTypeName =task.getDomElement().getLocalName();
//            System.out.println("=== " + task.getName());
//            System.out.println("type : "+ elementTypeName);
//            switch (elementTypeName) {
//                case BpmnModelConstants.BPMN_ELEMENT_SERVICE_TASK : {
//                    ServiceTask st = (ServiceTask) task;
//                    getTemplate(st);
//                    getWorker(st);
//                    break;
//                }
//                case BpmnModelConstants.BPMN_ELEMENT_SEND_TASK : {
//                    SendTask st = (SendTask) task;
//                    getTemplate(st);
//                    getWorker(st);
//                    break;
//                }
//                case BpmnModelConstants.BPMN_ELEMENT_BUSINESS_RULE_TASK : {
//                    BusinessRuleTask st = (BusinessRuleTask) task;
//                    getTemplate(st);
//                    getWorker(st);
//                    break;
//                }
//                default:
//                    System.out.println("template : none");
//                    System.out.println("worker : none");
//                    break;
//            }
//            System.out.println();
//            System.out.println("\n=================================\n");
//            ModelElementType callActivityType = modelInstance.getModel().getType(CallActivity.class);
//            Collection<ModelElementInstance> callActivities = modelInstance.getModelElementsByType(callActivityType);
//            for (ModelElementInstance callActivity : callActivities) {
//                BaseElement be = (BaseElement) callActivity;
//                System.out.println("=== " + callActivity.getAttributeValue(BpmnModelConstants.BPMN_ATTRIBUTE_NAME));
//                for (ModelElementInstance ee : be.getExtensionElements().getElements()) {
//                    if (ZeebeConstants.ELEMENT_CALLED_ELEMENT.equals(ee.getElementType().getTypeName())) {
//                        System.out.println("calledElement");
//                    }
//                    System.out.println("subprocess : " + ee.getAttributeValue("processId"));
//                }
//            }
//        }
//
        return 0;
    }

//    private void getWorker(Task task) {
//        String worker = "none";
//        for (ModelElementInstance ee : task.getExtensionElements().getElements()) {
//            if (ZeebeConstants.ELEMENT_TASK_DEFINITION.equals(ee.getElementType().getTypeName())) {
//                worker = ee.getAttributeValue(BpmnModelConstants.BPMN_ATTRIBUTE_TYPE);
//            }
//        }
//        System.out.println("worker : " + worker);
//    }
//
//    private void getTemplate(Task task) {
//        String template = task.getAttributeValueNs(BpmnModelConstants.ZEEBE_NS, ZEEBE_TEMPLATE);
//        if (template == null || template.isEmpty() || template.isBlank()) {
//            template = "none";
//        }
//        System.out.println("template : " + template);
//    }


}
