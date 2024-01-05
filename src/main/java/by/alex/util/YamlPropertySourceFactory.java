package by.alex.util;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {

        if (resource.getResource().getFilename() != null && resource.getResource().getFilename().endsWith(".yml")||resource.getResource().getFilename().endsWith(".yaml") ) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlProperties = yaml.load(resource.getResource().getInputStream());
            return new MapPropertySource(resource.getResource().getFilename(), yamlProperties);
        }
        return null;
    }
//ToDo позже проработать метод для выборки значений
    private static class MapPropertySource extends PropertySource<Map<String, Object>> {
        MapPropertySource(String name, Map<String, Object> source) {
            super(name, source);
        }

        @Override
        public Object getProperty(String name) {
            return this.source.get(name);
        }
    }
}
