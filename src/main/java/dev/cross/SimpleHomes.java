package dev.cross;

import dev.cross.manager.Config;
import dev.cross.manager.HomeList;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class SimpleHomes extends Plugin
{
    public SimpleHomes(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start()
    {
        // Plugin startup logic.
        Config.get();
        HomeList.get().load();
    }

    @Override

    public void stop()
    {
        // Plugin shutdown logic.
    }
}
