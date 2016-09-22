package com.denizenscript.webizen2;

import com.denizenscript.webizen2.commands.HttpServerCommand;
import com.denizenscript.webizen2.events.http.HttpPostRequestScriptEvent;
import com.denizenscript.webizen2.events.http.HttpGetRequestScriptEvent;
import com.denizenscript.webizen2.events.http.HttpHeadRequestScriptEvent;
import com.denizenscript.webizen2.scripts.webtypes.WebPageScript;
import com.denizenscript.webizen2.servers.http.WebizenHttpServer;
import com.denizenscript.denizen2core.Denizen2Core;
import com.denizenscript.denizen2core.addons.DenizenAddon;
import com.denizenscript.webizen2.tags.web.WebPageTagBase;

public class Webizen2 extends DenizenAddon {

    @Override
    public void enable() {
        // Commands
        Denizen2Core.register(new HttpServerCommand());
        // ScriptEvents
        Denizen2Core.register(HttpGetRequestScriptEvent.instance);
        Denizen2Core.register(HttpHeadRequestScriptEvent.instance);
        Denizen2Core.register(HttpPostRequestScriptEvent.instance);
        // Script Types
        Denizen2Core.register("webpage", WebPageScript::new);
        // Tags
        Denizen2Core.register(new WebPageTagBase());
    }

    @Override
    public void disable() {
        // Stop all active HTTP servers
        HttpServerCommand.httpServerMap.values().forEach(WebizenHttpServer::stop);
        HttpServerCommand.httpServerMap.clear();
    }
}
