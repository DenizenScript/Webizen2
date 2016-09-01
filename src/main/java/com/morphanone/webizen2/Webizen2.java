package com.morphanone.webizen2;

import com.morphanone.webizen2.commands.HttpServerCommand;
import com.morphanone.webizen2.events.http.HttpGetRequestScriptEvent;
import com.morphanone.webizen2.events.http.HttpHeadRequestScriptEvent;
import com.morphanone.webizen2.events.http.HttpPostRequestScriptEvent;
import com.morphanone.webizen2.servers.http.WebizenHttpServer;
import org.mcmonkey.denizen2core.Denizen2Core;
import org.mcmonkey.denizen2core.addons.DenizenAddon;

public class Webizen2 extends DenizenAddon {

    @Override
    public void enable() {
        // Commands
        Denizen2Core.register(new HttpServerCommand());
        // ScriptEvents
        Denizen2Core.register(HttpGetRequestScriptEvent.instance);
        Denizen2Core.register(HttpHeadRequestScriptEvent.instance);
        Denizen2Core.register(HttpPostRequestScriptEvent.instance);
    }

    @Override
    public void disable() {
        // Stop all active HTTP servers
        HttpServerCommand.httpServerMap.values().forEach(WebizenHttpServer::stop);
        HttpServerCommand.httpServerMap.clear();
    }
}
