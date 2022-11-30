package com.revature.ribs;

import com.revature.ribs.utils.Router;
import io.javalin.Javalin;

public class MainDriver {
    public static void main(String[] args){
        Javalin app = Javalin.create(c -> {
            c.contextPath = "/ribs";
        }).start(8080);

        Router.router(app);
    }
}
