package ru.nickbesk.myapplication;

import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class WebActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView webView = findViewById(R.id.web_view);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("https://jsonplaceholder.typicode.com/todos/50").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Запрос к серверу не был успешен: " +
                                response.code() + " " + response.message());
                    }

                    assert responseBody != null;
                    String body = responseBody.string();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> data =mapper.readValue(body,
                            new TypeReference<Map<String, Object>>(){});
                    String uHtml = "<p>Результат запроса на сайт: <b>https://jsonplaceholder.typicode.com/todos/50</b>" +
                            "</p><p>ID: "+data.get("id")+"</p><p>Title: " + data.get("title") + "</p>" +
                            "<p>Body: " + data.get("body") + "</p>";
                    String eHtml = Base64.getEncoder().encodeToString(uHtml.getBytes());
                    webView.post(() -> webView.loadData(eHtml, "text/html", "base64"));

                }
            }
        });
        /*try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            String uHtml = "<h1>Server: "+ response.header("Server") + "</h1>"+
                    "<p>Body: " + response.body().string() + "</p>";
            String eHtml = Base64.getEncoder().encodeToString(uHtml.getBytes());
            webView.loadData(eHtml, "text/html", "base64");
        } catch (IOException e) {
            System.out.println("Ошибка подключения: " + e);
        }*/


    }
}
