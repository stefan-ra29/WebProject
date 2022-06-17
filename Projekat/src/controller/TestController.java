package controller;
import com.google.gson.Gson;

import java.time.LocalDate;

import static spark.Spark.get;

public class TestController {
    public static void test()
    {
        get("rest/products/", (req, res) -> {
            res.type("application/json");
            Gson gson = new Gson();
            return  gson.toJson(LocalDate.now());
        });

    }
}
