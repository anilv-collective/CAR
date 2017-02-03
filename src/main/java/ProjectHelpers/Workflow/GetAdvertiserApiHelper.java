package ProjectHelpers.Workflow;

import Core.ApiCore.RestApiBase;
import Core.Utils.PropertyUtils;

import javax.print.DocFlavor;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;


/**
 * Created by anilv on 2/3/17.
 */
public class GetAdvertiserApiHelper extends RestApiBase{

    RestApiBase getTestCall;

    PropertyUtils propertyHelper = new PropertyUtils("./src/main/resources/Workflow/WorkflowApi.properties");
    String apiBaseUrl = propertyHelper.getProperty("workflowBaseUri");
    String aPIEndPoint = propertyHelper.getProperty("advertiserApiEndPoint");

    String token = "2-aiikU0r7jL2OMf4t6te4ibCMqCxjQToFGw9AldgTXgSbgDPM5wokLmRRjrXx39ZNP6V3BykZFbRWHQCf6Sd9noybuEA+Z83CM+EgEl0I3E1cAuszfA==";

    public Response GetAdvertiserApiResponse() {
        init(apiBaseUrl, "", "");

        HashMap<String, String>  params = new HashMap<String, String>();
        params.put("clientId", "23");
        params.put("pageSize", "10");
        params.put("pageNo", "1");

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
        headers.add("Authorization", token );

        getTestCall = RestApiBase.prepareRestApiCall("Get Advertisers API Test", MediaType.APPLICATION_JSON_TYPE, null, MediaType.APPLICATION_JSON_TYPE, aPIEndPoint, "GET", null, null, params, headers);

        Response response = GetRestAPIResponse(getTestCall);

//        String strRes = response.readEntity(String.class);
//
//        System.out.println(strRes);
        return response;
    }
}
