package Core.ApiCore;

import Core.Utils.PropertyUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by anilv on 2/3/17.
 * This class is used for initializing (and get/set properties) the rest call API with required resources.
 * e.g. uri, body, type, method etc.
 */
public class RestApiBase {

    static PropertyUtils propertyHelper = new PropertyUtils("./src/main/resources/VistoResources.properties");
    static String httpProtocol = propertyHelper.getProperty("httpProtocol");

    protected String name;
    protected String uri;
    protected String body;
    protected MediaType sourceType;
    protected MediaType responseType;
    protected String method;
    protected Map<String, String> parameters;
    protected MultivaluedMap<String, Object> headers;
    protected String userName;
    protected String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MediaType getSourceType() {
        return sourceType;
    }

    public void setSourceType(MediaType sourceType) {
        this.sourceType = sourceType;
    }

    public MediaType getResponseType() {
        return responseType;
    }

    public void setResponseType(MediaType responseType) {
        this.responseType = responseType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public MultivaluedMap<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(MultivaluedMap<String, Object> headers) {
        this.headers = headers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    protected static String sBaseUrl;
    protected static RestApiHelper sRestApiHelper;


    protected static String getBaseURL(String baseurl) {
        String sBaseUrl = "http://" + baseurl;
        return sBaseUrl;
    }

    protected static String getBaseURL(String baseurl, String httpProtocol) {
        String sBaseUrl = httpProtocol + "://" + baseurl;
        return sBaseUrl;
    }

    protected static void init(String baseURL, String user, String pwd) {
        sBaseUrl = getBaseURL(baseURL, httpProtocol);
        sRestApiHelper = new RestApiHelper(sBaseUrl, createRestClient(user, pwd));
    }


    /**
     * This method is used to initiate rest client with mentioned username and password.
     * @param user username for authentication
     * @param pwd password for authentication
     * @return client object
     */
    public static Client createRestClient(String user, String pwd) {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(user, pwd);
        Client client = ClientBuilder.newClient();
        client.register(feature);
        return client;
    }


    /**
     * This method performs a rest call based on the information in the test case object and
     * returns the response object.
     *
     * @param restTestCase the object containing the details of the rest call
     * @return the response object from the rest call
     */
    public static Response GetRestAPIResponse(RestApiBase restTestCase) {
        Response response = sRestApiHelper.GetRestAPIResponse(restTestCase);

        return response;
    }

    public static RestApiBase prepareRestApiCall(String name, MediaType sourceType, String body, MediaType responseType,
                                          String uri, String method, String userName, String password, Map<String, String> params,
                                          MultivaluedMap<String, Object> headers) {
        RestApiBase api = new RestApiBase();
        api.setName(name);
        api.setSourceType(sourceType);
        api.setBody(body);
        api.setResponseType(responseType);
        api.setUri(uri);
        api.setMethod(method);
        api.setUserName(userName);
        api.setPassword(password);
        api.setParameters(params);
        api.setHeaders(headers);
        return api;
    }

}
