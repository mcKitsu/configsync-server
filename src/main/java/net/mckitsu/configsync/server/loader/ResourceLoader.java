package net.mckitsu.configsync.server.loader;

import net.mckitsu.configsync.server.yaml.Config;
import net.mckitsu.lib.file.FileManager;
import net.mckitsu.lib.file.ResourceManager;

import java.net.URISyntaxException;

public class ResourceLoader {
    public YamlLoader<Config> config;

    public ResourceLoader(){
        try {
            this.config = new YamlLoader<Config>(Config.class,
                    new ResourceManager(ClassLoader.getSystemClassLoader(), "",  "config.yml"),
                    new FileManager("", "config.yml")){
                @Override
                public boolean load(){
                    boolean result = super.load();

                    if(!result)
                        return false;

                    if(this.yaml.getTerminal().getIpAddress()==null)
                        this.yaml.getTerminal().setIpAddress("localhost");

                    if(this.yaml.getService().getIpAddress()==null)
                        this.yaml.getService().setIpAddress("localhost");

                    return true;
                }
            };
        } catch (URISyntaxException e) {
            this.config = null;
            e.printStackTrace();
        }
    }

    public void load(){
        config.load();
    }
}
