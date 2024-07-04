package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileWriter;
import java.io.IOException;

public class Service {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String POST_URL = "https://jsonplaceholder.typicode.com/posts";
    private final CloseableHttpClient httpClient;

    public Service() {
        this.httpClient = HttpClients.createDefault();
    }

    public String createNewUser(String jsonUser) throws Exception {
        HttpPost request = new HttpPost(BASE_URL);
        request.setEntity(new StringEntity(jsonUser));

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    public String updateUser(int userId, String jsonUser) throws Exception {
        HttpPut httpPut = new HttpPut(BASE_URL + "/" + userId);

        StringEntity stringEntity = new StringEntity(jsonUser, "UTF-8");
        stringEntity.setContentType("application/json");
        httpPut.setEntity(stringEntity);

        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    public int deleteUser(int userId) throws Exception {
        HttpDelete request = new HttpDelete(BASE_URL + "/" + userId);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return response.getStatusLine().getStatusCode();
        }
    }

    public String getAllUsers() throws Exception {
        HttpGet request = new HttpGet(BASE_URL);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    public String getUserById(int userId) throws Exception {
        HttpGet request = new HttpGet(BASE_URL + "/" + userId);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    public String getUserByUsername(String username) throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "?username=" + username);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    private String getPostsByUserId(int userId) throws IOException {
        HttpGet httpGet = new HttpGet(BASE_URL + "/" + userId + "/posts");

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }


    private int findMaxIdPost(String jsonString) {
        JsonArray posts = JsonParser.parseString(jsonString).getAsJsonArray();

        int maxId = -1;

        for (int i = 0; i < posts.size(); i++) {
            JsonObject post = posts.get(i).getAsJsonObject();
            int postId = post.get("id").getAsInt();

            if (postId > maxId) {
                maxId = postId;
            }
        }

        return maxId;
    }

    private String getCommentsInPostById(int postId) throws IOException {
        HttpGet httpGet = new HttpGet(POST_URL + "/" + postId + "/comments");

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    private void writeStringToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extractAndSaveComments(int userId) {
        try {
            String userPostsJson = getPostsByUserId(userId);

            int maxPostId = findMaxIdPost(userPostsJson);

            if (maxPostId != -1) {
                String postCommentsJson = getCommentsInPostById(maxPostId);

                String fileName = "user-" + userId + "-post-" + maxPostId + "-comments.json";

                writeStringToFile(fileName, postCommentsJson);

                System.out.println("Комментарии сохранены в файл: " + fileName);
            } else {
                System.out.println("Постов не найдено");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void extractAndPrintOpenTodos(int userId) {
        try {
            String userTodosJson = getTodosByUserId(userId);

            printOpenTodos(userTodosJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTodosByUserId(int userId) throws IOException {
        HttpGet httpGet = new HttpGet(BASE_URL + "/" + userId + "/todos");

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

    public void printOpenTodos(String jsonString) {
        JsonArray todos = JsonParser.parseString(jsonString).getAsJsonArray();

        for (int i = 0; i < todos.size(); i++) {
            JsonObject todo = todos.get(i).getAsJsonObject();
            boolean completed = todo.get("completed").getAsBoolean();

            if (!completed) {
                int todoId = todo.get("id").getAsInt();
                String todoTitle = todo.get("title").getAsString();
                System.out.println("Todo ID: " + todoId + ", Title: " + todoTitle);
            }
        }
    }
}

