package Common;

import java.io.Serializable;

public class Request implements Serializable {

    private RequestType requestType;
    private String requestMessage;
    private String username;

    public Request(RequestType requestType, String requestMessage) {
        this.requestType = requestType;
        this.requestMessage = requestMessage;
    }

    public Request(RequestType requestType){
        this.requestType = requestType;
        this.requestMessage = "";
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
