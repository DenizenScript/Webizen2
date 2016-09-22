package com.denizenscript.webizen2.tags.web;

import com.denizenscript.denizen2core.tags.AbstractTagBase;
import com.denizenscript.denizen2core.tags.AbstractTagObject;
import com.denizenscript.denizen2core.tags.TagData;
import com.denizenscript.denizen2core.tags.objects.NullTag;
import com.denizenscript.denizen2core.tags.objects.ScriptTag;
import com.denizenscript.denizen2core.tags.objects.TextTag;
import com.denizenscript.webizen2.scripts.webtypes.WebPageScript;

public class WebPageTagBase extends AbstractTagBase {

    // <--[tagbase]
    // @Base webpage[<ScriptTag>]
    // @Group Webizen2
    // @Addon Webizen2
    // @ReturnType TextTag
    // @Returns the input WebPage's page contents.
    // -->

    @Override
    public String getName() {
        return "webpage";
    }

    @Override
    public AbstractTagObject handle(TagData data) {
        ScriptTag st = ScriptTag.getFor(data.error, data.getNextModifier());
        if (!(st.getInternal() instanceof WebPageScript)) {
            data.error.run("Specified script is not a webpage!");
            return new NullTag();
        }
        AbstractTagObject got = ((WebPageScript) st.getInternal()).pageContents
                .parse(data.currentQueue, data.variables, data.dbmode, data.error);
        return new TextTag(got.toString()).handle(data.shrink());
    }
}
