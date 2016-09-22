package com.denizenscript.webizen2.commands;

import com.denizenscript.webizen2.servers.http.WebizenHttpServer;
import com.denizenscript.denizen2core.commands.AbstractCommand;
import com.denizenscript.denizen2core.commands.CommandEntry;
import com.denizenscript.denizen2core.commands.CommandQueue;
import com.denizenscript.denizen2core.tags.objects.IntegerTag;
import com.denizenscript.denizen2core.utilities.CoreUtilities;
import com.denizenscript.denizen2core.utilities.debugging.Debug;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpServerCommand extends AbstractCommand {

    // <--[command]
    // @Name httpserver
    // @Arguments "start"/"stop" <port>
    // @Short starts or stops an HTTP server on the specified port.
    // @Updated 2016/08/28
    // @Authors Morphan1, mcmonkey
    // @Group Webizen2
    // @Addon Webizen2
    // @Minimum 2
    // @Maximum 2
    // @Description
    // Starts or stops an HTTP server on the specified port. The server will listen for incoming
    // HTTP requests and will fire off related ScriptEvents.
    // Note that a valid port must be in the (inclusive) range of [0, 65535] (the latter number is calculated as 2^16 - 1).
    // TODO: Explain more!
    // @Example
    // # This example starts an HTTP server on port 8080.
    // - httpserver start 8080
    // -->

    @Override
    public String getName() {
        return "httpserver";
    }

    @Override
    public String getArguments() {
        return "'start'/'stop' <port>";
    }

    @Override
    public int getMinimumArguments() {
        return 2;
    }

    @Override
    public int getMaximumArguments() {
        return 2;
    }

    public static final Map<Integer, WebizenHttpServer> httpServerMap = new HashMap<>();

    @Override
    public void execute(CommandQueue queue, CommandEntry entry) {
        String action = CoreUtilities.toLowerCase(entry.getArgumentObject(queue, 0).toString());
        int port = (int) IntegerTag.getFor(queue.error, entry.getArgumentObject(queue, 1)).getInternal();
        switch (action) {
            case "start":
                if (port < 0 || port > WebizenHttpServer.PORT_MAX) {
                    queue.handleError(entry, "Port " + port + " is invalid, should be between 0 and " + WebizenHttpServer.PORT_MAX);
                }
                else if (httpServerMap.containsKey(port)) {
                    queue.handleError(entry, "Server is already running on port " + port + ".");
                }
                else {
                    try {
                        WebizenHttpServer httpServer = new WebizenHttpServer(port);
                        httpServer.start();
                        httpServerMap.put(port, httpServer);
                    }
                    catch (IOException e) {
                        Debug.exception(e);
                        queue.handleError(entry, "Failed to create new HTTP server due to an exception.");
                    }
                }
                break;
            case "stop":
                if (httpServerMap.containsKey(port)) {
                    httpServerMap.get(port).stop();
                    httpServerMap.remove(port);
                }
                else {
                    queue.handleError(entry, "There is no server running on port " + port + ".");
                }
                break;
            default:
                queue.handleError(entry, "Invalid action in HttpServer command!");
                break;
        }
    }
}
