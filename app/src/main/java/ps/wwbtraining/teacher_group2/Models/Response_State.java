package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eman on 10/15/2017.
 */

public class Response_State {

    @SerializedName("api")
    private String api;
    @SerializedName("status")
    private String status;

    public Response_State(String API, String status) {
        this.api = API;
        this.status = status;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
