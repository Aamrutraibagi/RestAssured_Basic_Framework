package api.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)  //This we add when we have any unknown mapping from test class
public class PostRequest {

    private String title;
    private String body;
    private int userId;

    public PostRequest(){}

    public PostRequest(String title,String body, int userId){
        this.title=title;
        this.body=body;
        this.userId=userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}