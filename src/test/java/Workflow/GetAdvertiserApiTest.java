package Workflow;

import Core.Utils.JsonUtils;
import ProjectHelpers.Workflow.GetAdvertiserApiHelper;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.ws.rs.core.Response;

/**
 * Created by anilv on 2/3/17.
 */
public class GetAdvertiserApiTest {

    SoftAssert softAssert;
    @BeforeClass
    public void SetUp()
    {
        softAssert = new SoftAssert();
    }

    @Test
    public void GetAdvertisersApiTest()
    {
        Response response = new GetAdvertiserApiHelper().GetAdvertiserApiResponse();

        Assert.assertEquals(response.getStatus(), 200, "Response code is not correct!");

        String strResponse = response.readEntity(String.class);

        System.out.println("Response from API is: " + strResponse);

        softAssert.assertEquals(JsonUtils.GetStringValue(strResponse, "status", null), "OK", "status is not as expected.");
        softAssert.assertEquals(JsonUtils.GetStringValue(strResponse, "message", null), "All advertisers", "message is not as expected.");

        softAssert.assertAll();

    }
}
