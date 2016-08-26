package com.morphanone.webizen2.commands;

import org.mcmonkey.denizen2core.commands.AbstractCommand;
import org.mcmonkey.denizen2core.commands.CommandEntry;
import org.mcmonkey.denizen2core.commands.CommandQueue;
import org.mcmonkey.denizen2core.tags.objects.NumberTag;
import org.mcmonkey.denizen2core.utilities.CoreUtilities;

public class SocketCommand extends AbstractCommand {

    // <--[command]
    // @Name socket
    // @Arguments "open"/"close" <port> ["http"/"https"]
    // @Short Controls a web socket.
    // @Updated 2016/08/26
    // @Authors Morphan1
    // @Group Webizen2
    // @Procedural true
    // @Minimum 2
    // @Maximum 2
    // @Description
    // Opens or closes a web socket on the specified port. The socket will begin listening for incoming
    // connections and will fire off ScriptEvents. // TODO: more detail
    // @Example
    // # Opens an http socket on port 25566
    // - socket open 25566 http
    // -->

    @Override
    public String getName() {
        return "socket";
    }

    @Override
    public String getArguments() {
        return "'open'/'close' <port>";
    }

    @Override
    public int getMinimumArguments() {
        return 2;
    }

    @Override
    public int getMaximumArguments() {
        return 2;
    }

    @Override
    public boolean isProcedural() {
        return true;
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
            queue.handleError(entry, "Invalid action in Socket command!");
        }
    }
}
