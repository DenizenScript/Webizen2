package com.morphanone.webizen2.events.http;

public class HttpHeadRequestScriptEvent extends HttpRequestScriptEvent {

    // <--[event]
    // @Events
    // http head request
    //
    // @Updated 2016/08/28
    //
    // @Cancellable false
    //
    // @Triggers when an HTTP web socket receives a HEAD request.
    //
    // @Switch page the page being requested (optional).
    //
    // @Context
    // address (TextTag) returns the IP address of the device that sent the request.
    // request (TextTag) returns the path that was requested.
    // query (TextTag) returns the query included with the request.
    // user_info (TextTag) returns info about the authenticated user sending the request, if any.
    //
    // @Determinations
    // content_type (TextTag) sets the MIME (multi purpose mail extension) of the response (e.g. text/html).
    // status_code (IntegerTag) sets the status code of the response (e.g. 200).
    // headers (MapTag) sets the headers of the response.
    //
    // @Addon Webizen2
    // -->

    public static HttpHeadRequestScriptEvent instance = new HttpHeadRequestScriptEvent();

    @Override
    public boolean supportsResponseBody() {
        return false;
    }

    @Override
    public String getRequestMethod() {
        return "Head";
    }
}
