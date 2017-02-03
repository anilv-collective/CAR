package Core.ApiCore;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.testng.util.Strings;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by anilv on 2/3/17.
 *
 * This class is used to manage rest calls.
 * i.e. to create web request, web target, contact type, headers, parameters, authentication etc and get the response.
 */

public class RestApiHelper {

    private String baseUrl;
    private Client client;
    private WebTarget target;

    public RestApiHelper(String baseUri, Client client) {
        super();
        baseUrl = baseUri;
        this.client = client;
    }


    /**
     * Enum for Rest Methods
     */
    public enum RestMethod {
        POST, GET, PUT, DELETE
    }

    /**
     * This method is used to performs a rest call based on the information in the RestApiBase object
     * @param RestApiBase
     *            the object which contains the details of the rest call
     * @return The response from the REST API call
     */
    public Response GetRestAPIResponse(RestApiBase RestApiBase) {
        System.out.println("*********************");
        Invocation.Builder request = BuildRequest(RestApiBase, BuildWebTarget(RestApiBase));
        Response response = GetRestAPIResponse(RestApiBase, request);
        System.out.println("*********************");
        return response;
    }


    /**
     * This method is used to make a REST API call using the method and uri specified in the RestApiBase object and returns the response.
     * @param RestApiBase
     *            the object containing the details of the rest call
     * @param builder
     *            the request builder object that will be used for making REST API call
     * @return the response from REST API call
     */
    protected Response GetRestAPIResponse(RestApiBase RestApiBase, Invocation.Builder builder) {
        try {
            RestMethod method = RestMethod.valueOf(RestApiBase.getMethod());
            System.out.println("Method:  " + method.toString() + "\n");

            switch (method) {
                case POST:
                    System.out.println("Request body: " + RestApiBase.getBody() + "\n");
                    return builder.post(Entity.entity(RestApiBase.getBody(), RestApiBase.getSourceType()));

                case GET:
                    return builder.get();

                case PUT:
                    System.out.println("Request body: " + RestApiBase.getBody() + "\n");
                    return builder.put(Entity.entity(RestApiBase.getBody(), RestApiBase.getSourceType()));
                case DELETE:
                    return builder.delete();

                default:
                    throw new Exception("No such method supported '" + method.toString() + "'.");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t.getMessage());
        }
    }

    /**
     * This method is used to create WebTarget based on RestApiBase object
     * @param RestApiBase
     *          the object containing the details of the rest call
     * @return web target
     */
    private WebTarget BuildWebTarget(RestApiBase RestApiBase) {
        System.out.println("Rest Call Base URI: " + baseUrl);
        target = client.target(baseUrl);
        target = SetAuthentication(RestApiBase, target);
        System.out.println("Rest Call URI: " + RestApiBase.getUri());

        target = target.path(RestApiBase.getUri());
        target = setParameters(RestApiBase, target);
        return target;
    }

    /**
     * This method is used to create web request based on RestApiBase object and the web target
     * @param RestApiBase
     *              the object containing the details of the rest call
     * @param target
     *          web target object
     * @return builder request
     */
    private Invocation.Builder BuildRequest(RestApiBase RestApiBase, WebTarget target) {
        Invocation.Builder request = SetRequestContentType(RestApiBase, target);
        request = SetHeaders(RestApiBase, request);
        request = SetResponseContentType(RestApiBase, request);
        return request;
    }


    /**
     * This method takes the information about request content type, and sets it in the passed web target object.
     * @param RestApiBase
     *            the object containing the details of the rest call
     * @param target
     *            the target object which is to be modified with the request content type
     * @return  target modified with request content type
     */
    protected Invocation.Builder SetRequestContentType(RestApiBase RestApiBase, WebTarget target) {
        if (RestApiBase.getSourceType() != null) {
            System.out.println("Content Type for the request is:  " + RestApiBase.getSourceType().toString());
            return target.request(RestApiBase.getResponseType());
        }
        return target.request();
    }


    /**
     * This method takes the information about request content type, and sets it in the passed web target object.
     * @param RestApiBase
     *            the object containing the details of the rest call
     * @param builder
     *            the builder object which is to be modified with the response content type
     * @return  builder object modified with response content type
     */
    protected Invocation.Builder SetResponseContentType(RestApiBase RestApiBase, Invocation.Builder builder) {
        if (RestApiBase.getResponseType() != null) {
            System.out.println("Content Type for the response is: " + RestApiBase.getResponseType().toString());
            builder = builder.accept(RestApiBase.getResponseType());
            return builder;
        }
        return builder;
    }


    /**
     * This method sets the header values for the rest call
     * @param restTestCall
     *            the rest call object containing the information about the headers
     * @param builder
     *           the builder object where the headers will be set.
     * @return builder object modified with headers
     */
    protected Invocation.Builder SetHeaders(RestApiBase restTestCall, Invocation.Builder builder) {
        if (restTestCall.getHeaders() != null && !restTestCall.getHeaders().isEmpty()) {
            System.out.println("Headers are:  " + restTestCall.getHeaders().toString());
            builder = builder.headers(restTestCall.getHeaders());
        }
        return builder;
    }

    /**
     * This method takes the information about parameters and sets it in the passed webTarget object.
     * @param RestApiBase
     *           the rest call object containing the information about the parameters
     * @param webTarget
     *            webTarget object to be modified with specified parameters
     * @return the webTarget object along with parameters
     */
    protected WebTarget setParameters(RestApiBase RestApiBase, WebTarget webTarget) {
        if (RestApiBase.getParameters() != null && !RestApiBase.getParameters().isEmpty()) {
            System.out.println("Rest Call Parameters are:");
            for (Map.Entry<String, String> param : RestApiBase.getParameters().entrySet()) {
                System.out.println(param.getKey() + " : " + param.getValue());
                webTarget = webTarget.queryParam(param.getKey(), param.getValue());
            }
            return webTarget;
        }
        return webTarget;
    }

    /**
     * Adds authentication to mentioned we target
     * @param restTestCall
     *            the object containing the details of the rest call
     * @param webTarget
     *            the webTarget where auth information is to be added
     * @return the web target with the added auth specification
     */
    protected WebTarget SetAuthentication(RestApiBase restTestCall, WebTarget webTarget) {
        boolean isUserNameEmpty = (Strings.isNullOrEmpty(restTestCall.getUserName()));
        boolean isPasswordEmpty = (Strings.isNullOrEmpty(restTestCall.getPassword()));

        if (isUserNameEmpty || isPasswordEmpty) {
            System.out.println("Making rest call with default username & password.");
            return webTarget;
        } else {
            System.out.println(
                    "Making rest Call with User/Password: " + restTestCall.getUserName() + "/" + restTestCall.getPassword());
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(restTestCall.getUserName(),
                    restTestCall.getPassword());
            webTarget = webTarget.register(feature);
            return webTarget;
        }
    }
    
}
