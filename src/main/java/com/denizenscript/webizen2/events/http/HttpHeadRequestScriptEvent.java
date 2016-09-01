package com.denizenscript.webizen2.events.http;

public class HttpHeadRequestScriptEvent extends HttpRequestScriptEvent {

    // <--[event]
    // @Events
    // http head request
    //
    // @Updated 2016/08/31
    //
    // @Addon Webizen2
    //
    // @Cancellable false
    //
    // @Triggers when an HTTP server receives a HEAD request.
    //
    // @Switch page checks if the page requested is the one specified.
    // @Switch port checks if the port requested is the one specified.
    //
    // @Context
    // address (TextTag) returns the IP address of the device that sent the request.
    // host (TextTag) returns the host that was requested (EG: www.example.com).
    // page (TextTag) returns the path of the page that was requested (EG: /page).
    // port (IntegerTag) returns the port that was requested (EG: 8080).
    // request (TextTag) returns the full requested URI (EG: www.example.com:8080/page).
    // query (TextTag) returns the query text included with the request.
    // user_info (TextTag) returns info about the authenticated user sending the request, if any.
    //
    //
    // @Determinations
    // content_type (TextTag) sets the MIME (multi purpose mail extension) of the response (e.g. text/html).
    // status_code (IntegerTag) sets the status code of the response (e.g. 200).
    // headers (MapTag) sets the headers of the response.
    // -->

    public static HttpHeadRequestScriptEvent instance = new HttpHeadRequestScriptEvent();

    @Override
    public String getName() {
        return "HttpHeadRequest";
    }

    @Override
    public boolean supportsResponseBody() {
        return false;
    }

    @Override
    public String getRequestMethod() {
        return "head";
    }
}
