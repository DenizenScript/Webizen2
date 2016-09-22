package com.denizenscript.webizen2.servers.http;

import com.sun.net.httpserver.HttpExchange;
import com.denizenscript.denizen2core.utilities.CoreUtilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class HttpRequest {

    private final HttpExchange httpExchange;
    private final byte[] fullRequest;

    public HttpRequest(HttpExchange httpExchange) throws IOException {
        this.httpExchange = httpExchange;
        InputStream requestBody = httpExchange.getRequestBody();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[CoreUtilities.buff10k];
        while (true) {
            int rsz = requestBody.read(buffer, 0, buffer.length);
            if (rsz < 0) {
                break;
            }
            output.write(buffer, 0, rsz);
        }
        this.fullRequest = output.toByteArray();
        requestBody.close();
    }

    public String getRequestMethod() {
        return httpExchange.getRequestMethod();
    }

    public String getHeader(String name) {
        return httpExchange.getRequestHeaders().getFirst(name);
    }

    public InetSocketAddress getRemoteAddress() {
        return httpExchange.getRemoteAddress();
    }

    public URI getRequestURI() {
        return httpExchange.getRequestURI();
    }

    public byte[] getFullRequest() {
        return fullRequest;
    }
}
