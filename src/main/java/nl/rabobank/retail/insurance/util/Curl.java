package nl.rabobank.retail.insurance.util;

import java.util.Properties;

public class Curl {

    public static void main(String[] args) {
        String workItemId = args[0];
        System.out.println(workItemId);
        Properties properties = ReadProperties.load("/application.yaml");
        if (properties == null) {
            System.err.println("Error reading properties");
            System.exit(1);
        }
        System.out.println(properties);
        String accessToken = properties.getProperty("azdo.api-version");
        System.out.println(accessToken);
//        String URL = "/wit/workitems/" +workItemId + "?" + apiVersiom;
//        String curlOptions = "--silent --request GET --user :" + accessToken;
//        String jsonString = curl().lUpperCase().$("https://dog.ceo/api/breeds/list/all");
//        System.out.println(jsonString);
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            JsonNode actualObj = mapper.readTree(jsonString);
//            JsonNode jsonNode1 = actualObj.get("message").get("bulldog");
//            System.out.println(jsonNode1);
//        } catch (JsonProcessingException e) {
//            System.err.println("Error parsing JSON");
//        }
    }
}
