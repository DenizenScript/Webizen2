package com.denizenscript.webizen2.events.http;

import com.denizenscript.denizen2core.tags.AbstractTagObject;
import com.denizenscript.denizen2core.tags.objects.TextTag;

import java.util.HashMap;

public class HttpPostRequestScriptEvent extends HttpRequestScriptEvent {

    // <--[event]
    // @Events
    // http post request
    //
    // @Updated 2016/08/31
    //
    // @Group Webizen2
    //
    // @Addon Webizen2
    //
    // @Cancellable false
    //
    // @Triggers when an HTTP server receives a GET request.
    //
    // @Switch page checks if the page requested is the one specified.
    // @Switch port checks if the port requested is the one specified.
    //
    // @Context
    // content_type (TextTag) returns the content type of the request body.
    // address (TextTag) returns the IP address of the device that sent the request.
    // host (TextTag) returns the host that was requested (EG: www.example.com).
    // page (TextTag) returns the path of the page that was requested (EG: /page).
    // port (IntegerTag) returns the port that was requested (EG: 8080).
    // request (TextTag) returns the full requested URI (EG: www.example.com:8080/page).
    // query (TextTag) returns the query text included with the request.
    // user_info (TextTag) returns info about the authenticated user sending the request, if any.
    //
    // @Determinations
    // content_type (TextTag) sets the MIME (multi purpose mail extension) of the response (e.g. text/html).
    // status_code (IntegerTag) sets the status code of the response (e.g. 200).
    // response_text (TextTag) sets the text content of the response.
    // headers (MapTag) sets the headers of the response.
    // -->

    public static HttpPostRequestScriptEvent instance = new HttpPostRequestScriptEvent();

    @Override
    public HashMap<String, AbstractTagObject> getDefinitions(ScriptEventData data) {
        HashMap<String, AbstractTagObject> defs = super.getDefinitions(data);
        defs.put("content_type", new TextTag(request.getHeader("Content-Type")));
        return defs;
    }

    @Override
    public String getName() {
        return "HttpPostRequest";
    }

    @Override
    public boolean supportsResponseBody() {
        return true;
    }

    @Override
    public String getRequestMethod() {
        return "post";
    }
}
