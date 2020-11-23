package net.mckitsu.configsync.server.loader;

import net.mckitsu.lib.file.FileManager;
import net.mckitsu.lib.file.ResourceManager;
import net.mckitsu.lib.file.YamlManager;

import java.io.IOException;

public class YamlLoader<T> {
    private final YamlManager<T> yamlManager;
    private final ResourceManager resourcePath;
    private final FileManager filePath;
    public T yaml;

    public YamlLoader(Class<?> theRoot, ResourceManager resourcePath, FileManager filePath){
        this.yamlManager = new YamlManager<T>(theRoot);
        this.resourcePath = resourcePath;
        this.filePath = filePath;
    }

    public boolean load(){
        if(!filePath.exists())
            filePath.copy(resourcePath);

        try {
            yaml = yamlManager.load(new String(filePath.readAsByte()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
