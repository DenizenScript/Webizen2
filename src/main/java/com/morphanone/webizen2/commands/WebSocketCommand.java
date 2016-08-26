package com.morphanone.webizen2.commands;

import org.mcmonkey.denizen2core.commands.AbstractCommand;
import org.mcmonkey.denizen2core.commands.CommandEntry;
import org.mcmonkey.denizen2core.commands.CommandQueue;
import org.mcmonkey.denizen2core.tags.objects.NumberTag;
import org.mcmonkey.denizen2core.utilities.CoreUtilities;

public class WebSocketCommand extends AbstractCommand {

    // <--[command]
    // @Name websocket
    // @Arguments "open"/"close" <port> "http"/"https"
    // @Short Controls a web socket.
    // @Updated 2016/08/26
    // @Authors Morphan1
    // @Group Webizen2
    // @Addon Webizen2
    // @Minimum 3
    // @Maximum 3
    // @Description
    // Opens or closes a web socket on the specified port. The socket will begin listening for incoming
    // web requests and will fire off related ScriptEvents.
    // TODO: Explain more!
    // @Example
    // # This example opens an HTTP web socket on port 8080.
    // - socket open 8080 http
    // -->

    @Override
    public String getName() {
        return "websocket";
    }

    @Override
    public String getArguments() {
        return "'open'/'close' <port> 'http'/'https'";
    }

    @Override
    public int getMinimumArguments() {
        return 3;
    }

    @Override
    public int getMaximumArguments() {
        return 3;
    }

    @Override
    public boolean isProcedural() {
        return false;
    }

    @Override
    public void execute(CommandQueue queue, CommandEntry entry) {
        String action = CoreUtilities.toLowerCase(entry.getArgumentObject(queue, 0).toString());
        NumberTag port = NumberTag.getFor(queue.error, entry.getArgumentObject(queue, 1));
        String protocol = CoreUtilities.toLowerCase(entry.getArgumentObject(queue, 2).toString());
        if (action.equals("open")) {
            // TODO: implement
        }
        else if (action.equals("close")) {
            // TODO: implement
        }
        else {
            queue.handleError(entry, "Invalid action in WebSocket command!");
        }
    }
}
