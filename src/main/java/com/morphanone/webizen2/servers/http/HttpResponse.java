package com.morphanone.webizen2.servers.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {

    private final HttpExchange httpExchange;
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private int statusCode = 404;
    private boolean sent = false;

    public HttpResponse(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    public void setHeader(String name, String value) {
        httpExchange.getResponseHeaders().set(name, value);
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void write(byte[] bytes) throws IOException {
        buffer.write(bytes);
        buffer.flush();
    }

    public boolean isSent() {
        return sent;
    }

    public void send() throws IOException {
        try {
            int bufferSize = buffer.size();
            httpExchange.sendResponseHeaders(statusCode, bufferSize);
            if (bufferSize > 0) {
                OutputStream responseBody = httpExchange.getResponseBody();
                responseBody.write(buffer.toByteArray());
                responseBody.flush();
                responseBody.close();
            }
        }
        finally {
            httpExchange.close();
            sent = true;
        }
    }
}
