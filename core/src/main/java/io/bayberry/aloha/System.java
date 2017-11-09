package io.bayberry.aloha;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class System {

    private static final Map<String, System> SYSTEMS = new ConcurrentHashMap<>();
    private final String name;
    private final SystemConfig config;

    private System(String name, SystemConfig config) {
        this.name = name;
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public static synchronized System create(String name, SystemConfig config) {
        if (SYSTEMS.containsKey(name)) {
            return SYSTEMS.get(name);
        }

        System system = new System(name, config);
        SYSTEMS.put(name, system);
        return system;
    }
}
