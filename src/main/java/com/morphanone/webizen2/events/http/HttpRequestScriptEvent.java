package com.morphanone.webizen2.events.http;

import com.morphanone.webizen2.servers.http.HttpRequest;
import com.morphanone.webizen2.servers.http.HttpResponse;
import org.mcmonkey.denizen2core.events.ScriptEvent;
import org.mcmonkey.denizen2core.tags.AbstractTagObject;
import org.mcmonkey.denizen2core.tags.objects.IntegerTag;
import org.mcmonkey.denizen2core.tags.objects.MapTag;
import org.mcmonkey.denizen2core.tags.objects.TextTag;
import org.mcmonkey.denizen2core.utilities.CoreUtilities;
import org.mcmonkey.denizen2core.utilities.debugging.Debug;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpRequestScriptEvent extends ScriptEvent {

    public HttpRequest request;
    public HttpResponse response;

    public TextTag contentType;
    public MapTag headers;
    public IntegerTag statusCode;
    public TextTag responseText;

    private final String requestMethod;
    private final String lowerRequestMethod;

    public HttpRequestScriptEvent() {
        this.requestMethod = getRequestMethod();
        this.lowerRequestMethod = CoreUtilities.toLowerCase(requestMethod);
    }

    public void run(HttpRequest request, HttpResponse response) {
        HttpRequestScriptEvent event = (HttpRequestScriptEvent) clone();
        event.request = request;
        event.response = response;
        event.run();
        try {
            if (event.contentType != null) {
                response.setHeader("Content-Type", event.contentType.getInternal());
            }
            if (event.headers != null) {
                for (Map.Entry<String, AbstractTagObject> entry : event.headers.getInternal().entrySet()) {
                    response.setHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            if (event.statusCode != null) {
                response.setStatusCode((int) event.statusCode.getInternal());
            }
            if (event.responseText != null) {
                response.write(event.responseText.getInternal().getBytes("UTF-8"));
            }
        }
        catch (IOException e) {
            Debug.exception(e);
        }
        finally {
            try {
                response.send();
            }
            catch (IOException e) {
                Debug.exception(e);
            }
        }
    }

    @Override
    public String getName() {
        return "Http" + requestMethod + "Request";
    }

    @Override
    public boolean couldMatch(ScriptEventData data) {
        return data.eventPath.startsWith("http " + lowerRequestMethod + " request");
    }

    @Override
    public boolean matches(ScriptEventData data) {
        return !data.switches.containsKey("page") || data.switches.get("page").equals(request.getRequestURI().getPath());
    }

    @Override
    public HashMap<String, AbstractTagObject> getDefinitions(ScriptEventData data) {
        HashMap<String, AbstractTagObject> defs = super.getDefinitions(data);
        defs.put("address", new TextTag(request.getRemoteAddress().toString()));
        URI uri = request.getRequestURI();
        defs.put("request", new TextTag(uri.getPath()));
        defs.put("query", new TextTag(uri.getQuery()));
        defs.put("user_info", new TextTag(uri.getUserInfo()));
        return defs;
    }

    @Override
    public void applyDetermination(boolean errors, String determination, AbstractTagObject value) {
        if (determination.equals("content_type")) {
            contentType = new TextTag(value.toString());
        }
        else if (determination.equals("headers")) {
            headers = MapTag.getFor(this::error, value);
        }
        else if (determination.equals("status_code")) {
            statusCode = IntegerTag.getFor(this::error, value);
        }
        else if (determination.equals("response_text") && supportsResponseBody()) {
            responseText = new TextTag(value.toString());
        }
        else {
            super.applyDetermination(errors, determination, value);
        }
    }

    public abstract boolean supportsResponseBody();

    public abstract String getRequestMethod();
}
