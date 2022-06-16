package controller;
import static spark.Spark.get;

public class TestController {
    public static void test()
    {
        get("rest/products/", (req, res) -> {
            res.type("application/json");
            return "Hellloooo";
        });

    }
}
