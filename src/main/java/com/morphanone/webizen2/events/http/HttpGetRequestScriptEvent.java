package com.morphanone.webizen2.events.http;

public class HttpGetRequestScriptEvent extends HttpRequestScriptEvent {

    // <--[event]
    // @Events
    // http get request
    //
    // @Updated 2016/08/28
    //
    // @Cancellable false
    //
    // @Triggers when an HTTP web socket receives a GET request.
    //
    // @Context
    // address TextTag returns the IP address of the device that sent the request.
    // request TextTag returns the path that was requested.
    // query TextTag returns the query included with the request.
    // user_info TextTag returns info about the authenticated user sending the request, if any.
    //
    // @Determinations
    // content_type TextTag sets the MIME (multi purpose mail extension) of the response (e.g. text/html).
    // status_code IntegerTag sets the status code of the response (e.g. 200).
    // response_text TextTag sets the text content of the response.
    // headers MapTag sets the headers of the response.
    //
    // @Addon Webizen2
    // -->

    public static HttpGetRequestScriptEvent instance = new HttpGetRequestScriptEvent();

    @Override
    public boolean supportsResponseBody() {
        return true;
    }

    @Override
    public String getRequestMethod() {
        return "Get";
    }
}
