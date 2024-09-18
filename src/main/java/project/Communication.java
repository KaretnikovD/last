package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import project.model.User;

import java.util.List;

@Component
public class Communication {


    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private String URL = "http://94.198.50.185:7081/api/users";
    private String cookies;
    private final StringBuilder resultContent = new StringBuilder();

    @Autowired
    public Communication(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    public List<User> getAllUsers(){
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {});
        this.cookies = responseEntity.getHeaders().getFirst("Set-Cookie");
        return responseEntity.getBody();
    }

    public void updateUser(User user){
        httpHeaders.add("Cookie", cookies);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);

        resultContent.append(responseEntity.getBody());
        System.out.println("put method " + resultContent);
    }


    public void saveUser(User user){

        httpHeaders.add("Cookie", cookies);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);

        resultContent.append(responseEntity.getBody());
        System.out.println("post method " + resultContent);
        }


    public void deleteUser(User user, Long id){
        httpHeaders.add("Cookie", cookies);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, requestEntity, String.class);

        resultContent.append(responseEntity.getBody());
        System.out.println("delete method " + resultContent);
    }
}

