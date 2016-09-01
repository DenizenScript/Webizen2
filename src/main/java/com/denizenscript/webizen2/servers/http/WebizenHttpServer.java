package com.denizenscript.webizen2.servers.http;

import com.denizenscript.webizen2.events.http.HttpGetRequestScriptEvent;
import com.denizenscript.webizen2.events.http.HttpHeadRequestScriptEvent;
import com.denizenscript.webizen2.events.http.HttpPostRequestScriptEvent;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.mcmonkey.denizen2core.utilities.CoreUtilities;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebizenHttpServer {

    /**
     * Copied from private static field in java.net.HostPortrange.
     * Value is 65535 (2^16 - 1).
     */
    public static final int PORT_MAX = (1 << 16) - 1;

    private final HttpServer server;
    private boolean running;

    public final int port;

    public WebizenHttpServer(int port) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", httpHandler);
        server.setExecutor(Runnable::run);
        this.running = false;
    }

    public void start() {
        if (!running) {
            server.start();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            server.stop(0);
            running = false;
        }
    }

    private final HttpHandler httpHandler = (httpExchange) -> {
        HttpRequest request = new HttpRequest(httpExchange);
        HttpResponse response = new HttpResponse(this, httpExchange);
        String requestMethod = CoreUtilities.toUpperCase(request.getRequestMethod());
        // TODO: If configured to (default: true), force onto primary thread to avoid async issues!
        switch (requestMethod) {
            case "GET":
                HttpGetRequestScriptEvent.instance.run(request, response);
                break;
            case "HEAD":
                HttpHeadRequestScriptEvent.instance.run(request, response);
                break;
            case "POST":
                HttpPostRequestScriptEvent.instance.run(request, response);
                break;
            // Other HTTP/1.1 request methods (which we probably won't need to implement)
            // OPTIONS,
            // PUT,
            // DELETE,
            // TRACE,
            // CONNECT
            default:
                // Respond with 501 "Not Implemented"
                response.setStatusCode(501);
                response.write("501 Not Implemented".getBytes("UTF-8"));
                response.send();
                break;
        }
        if (!response.isSent()) {
            // Something went wrong if this is ever reached.
            response.close();
        }
    };
}
