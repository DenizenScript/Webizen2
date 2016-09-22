package com.denizenscript.webizen2.scripts.webtypes;

import com.denizenscript.denizen2core.Denizen2Core;
import com.denizenscript.denizen2core.arguments.Argument;
import com.denizenscript.denizen2core.scripts.CommandScript;
import com.denizenscript.denizen2core.utilities.ErrorInducedException;
import com.denizenscript.denizen2core.utilities.debugging.Debug;
import com.denizenscript.denizen2core.utilities.yaml.YAMLConfiguration;

public class WebPageScript extends CommandScript {

    // <--[explanation]
    // @Name WebPage Scripts
    // @Group Script Types
    // @Addon Webizen2
    // @Description
    // A webpage script can be used to represent a page in Webizen2.
    // Page data is under the 'page' key, with tags marked as `<{tag}>` rather than `<tag>`.
    // TODO: More detail!
    // -->

    public WebPageScript(String name, YAMLConfiguration config) {
        super(name, config);
        String pdat = config.getString("page");
        Debug.info(pdat);
        pdat = pdat.replaceAll("<(?!\\{)", "<{unescape[&lt]}>");
        Debug.info(pdat);
        pdat = pdat.replaceAll("(?<!\\})>", "<{unescape[&gt]}>");
        Debug.info(pdat);
        pdat = pdat.replace("<{", "<");
        Debug.info(pdat);
        pdat = pdat.replace("}>", ">");
        Debug.info(pdat);
        pageContents = Denizen2Core.splitToArgument(pdat, false, false, (e) -> {
            throw new ErrorInducedException("Parsing a webpage script: " + e);
        });

    }
    public final Argument pageContents;

    @Override
    public boolean isExecutable(String s) {
        return false;
    }
}
