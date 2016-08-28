package com.morphanone.webizen2.servers.http;

import com.morphanone.webizen2.events.http.HttpGetRequestScriptEvent;
import com.morphanone.webizen2.events.http.HttpHeadRequestScriptEvent;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.mcmonkey.denizen2core.utilities.CoreUtilities;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebizenHttpServer {

    private final HttpServer server;
    private boolean running;

    public WebizenHttpServer(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", httpHandler);
        server.setExecutor(Runnable::run);
        this.running = false;
    }

    public void start() {
        if (!running) {
            server.start();
        }
    }

    public void stop() {
        if (running) {
            server.stop(0);
        }
    }

    private static final HttpHandler httpHandler = httpExchange -> {
        HttpRequest request = new HttpRequest(httpExchange);
        HttpResponse response = new HttpResponse(httpExchange);
        String requestMethod = CoreUtilities.toUpperCase(request.getRequestMethod());
        switch (requestMethod) {
            case "GET":
                HttpGetRequestScriptEvent.instance.run(request, response);
                break;
            case "HEAD":
                HttpHeadRequestScriptEvent.instance.run(request, response);
                break;
            // TODO: implement these
            // case "POST":
            //     break;
            // case "OPTIONS":
            //     break;
            // Other HTTP/1.1 request methods (which we probably won't need to implement)
            // PUT,
            // DELETE,
            // TRACE,
            // CONNECT
            default:
                // Respond with 501 "Not Implemented"
                response.setStatusCode(501);
                response.send();
                break;
        }
        if (!response.isSent()) {
            response.send();
        }
    };
}
