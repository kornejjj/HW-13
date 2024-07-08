package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import entity.User;

import javax.xml.stream.events.Comment;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String TODOS_URL = "https://jsonplaceholder.typicode.com/todos";

    private final CloseableHttpClient httpClient;
    private final Gson gson;

    public Service() {
        this.httpClient = HttpClients.createDefault();
        this.gson = new Gson();
    }

    // Method to create a new user
    public User createNewUser(User user) throws IOException {
        HttpPost request = new HttpPost(BASE_URL);
        String jsonUser = gson.toJson(user);
        request.setEntity(new StringEntity(jsonUser));

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return gson.fromJson(EntityUtils.toString(entity), User.class);
        }
    }

    // Method to update an existing user
    public User updateUser(int userId, User updatedUser) throws IOException {
        HttpPut request = new HttpPut(BASE_URL + "/" + userId);
        String jsonUser = gson.toJson(updatedUser);
        request.setEntity(new StringEntity(jsonUser));

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return gson.fromJson(EntityUtils.toString(entity), User.class);
        }
    }

    // Method to delete a user
    public int deleteUser(int userId) throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + "/" + userId);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return response.getStatusLine().getStatusCode();
        }
    }

    // Method to fetch all users
    public List<User> getAllUsers() throws IOException {
        HttpGet request = new HttpGet(BASE_URL);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            JsonArray jsonArray = JsonParser.parseString(EntityUtils.toString(entity)).getAsJsonArray();
            List<User> users = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                User user = gson.fromJson(jsonObject, User.class);
                users.add(user);
            }
            return users;
        }
    }

    // Method to fetch a user by ID
    public User getUserById(int userId) throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/" + userId);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return gson.fromJson(EntityUtils.toString(entity), User.class);
        }
    }

    // Method to fetch a user by username
    public User getUserByUsername(String username) throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "?username=" + username);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            JsonArray jsonArray = JsonParser.parseString(EntityUtils.toString(entity)).getAsJsonArray();
            if (!jsonArray.isEmpty()) {
                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                return gson.fromJson(jsonObject, User.class);
            } else {
                return null; // Return null if user with the specified username is not found
            }
        }
    }

    // Method to fetch posts by user ID
    public List<Post> getPostsByUserId(int userId) throws IOException {
        HttpGet request = new HttpGet(BASE_URL + "/" + userId + "/posts");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            JsonArray jsonArray = JsonParser.parseString(EntityUtils.toString(entity)).getAsJsonArray();
            List<Post> posts = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                Post post = gson.fromJson(jsonObject, Post.class);
                posts.add(post);
            }
            return posts;
        }
    }

    // Method to fetch comments by post ID
    public List<Comment> getCommentsByPostId(int postId) throws IOException {
        HttpGet request = new HttpGet(POSTS_URL + "/" + postId + "/comments");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            JsonArray jsonArray = JsonParser.parseString(EntityUtils.toString(entity)).getAsJsonArray();
            List<Comment> comments = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                Comment comment = gson.fromJson(jsonObject, Comment.class);
                comments.add(comment);
            }
            return comments;
        }
    }

    // Method to fetch todos by user ID
    public List<Todo> getTodosByUserId(int userId) throws IOException {
        HttpGet request = new HttpGet(TODOS_URL + "?userId=" + userId);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            JsonArray jsonArray = JsonParser.parseString(EntityUtils.toString(entity)).getAsJsonArray();
            List<Todo> todos = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                Todo todo = gson.fromJson(jsonObject, Todo.class);
                todos.add(todo);
            }
            return todos;
        }
    }

    // Method to write JSON content to a file
    private void writeJsonToFile(String fileName, String jsonContent) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to extract and save comments for the latest post of a user
    public void extractAndSaveComments(int userId) {
        try {
            List<Post> posts = getPostsByUserId(userId);
            if (!posts.isEmpty()) {
                Post latestPost = posts.getLast();
                List<Comment> comments = getCommentsByPostId(latestPost.getId());
                String fileName = "user-" + userId + "-post-" + latestPost.getId() + "-comments.json";
                writeJsonToFile(fileName, gson.toJson(comments));
                System.out.println("Comments saved to file: " + fileName);
            } else {
                System.out.println("No posts found for the user.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to print open todos for a user
    public void extractAndPrintOpenTodos(int userId) {
        try {
            List<Todo> todos = getTodosByUserId(userId);
            for (Todo todo : todos) {
                if (!todo.isCompleted()) {
                    System.out.println("Todo ID: " + todo.getId() + ", Title: " + todo.getTitle());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
