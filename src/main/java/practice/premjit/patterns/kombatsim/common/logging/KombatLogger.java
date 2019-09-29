package practice.premjit.patterns.kombatsim.common.logging;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class KombatLogger {
    
    public enum LEVEL {
        HIGH (3),
        MEDIUM (2),
        LOW (1);
        
        private final int levelCode;
        
        LEVEL(int levelCode) {
            this.levelCode = levelCode;
        }
        
        public int code() {
            return this.levelCode;
        }
    }
    
    public enum EVENT_TYPE {
        ACTION,
        REACTION,
        BEAT,
        INFO,
        UPDATE
    }
    
    protected static KombatLogger preferred;
    protected static KombatLogger basic;
    
    protected LEVEL level = LEVEL.LOW;
    protected boolean doLog = true;
    protected boolean startLog = false;
    
    public void log(LEVEL level, EVENT_TYPE type, Map<String, String> source, Map<String, String> event) {
        if (logEnabled(level))
            logit(level, type, source, event);
    }
    
    protected abstract void logit(LEVEL level, EVENT_TYPE type, Map<String, String> source, 
            Map<String, String> event);
    
    public static void setPreferredLogger(KombatLogger log) {
        preferred = log;
    }
    
    public void enableLogging() {
        doLog = true;
    }
    
    public void disableLogging() {
        doLog = false;
    }
    
    public void start() {
        System.out.println("------------START------------");
        startLog = true;
    }
    
    public void stop() {
        System.out.println("------------STOP------------");
        startLog = false;
    }
    
    public static LogMapBuilder mapBuilder() {
        return new LogMapBuilder();
    }
    
    public static KombatLogger getLogger() {
        if (preferred == null)
            return basic();
        return preferred;
    }
    
    private static KombatLogger basic() {
        if (basic == null)
            basic = new BasicLogger();
        return basic;
    }
    
    public static Map<String, String> mapWithName(String name) {
        return mapBuilder().withName(name).build();
    }
    
    public void setLevel(LEVEL level) {
        this.level = level;
    }
    
    protected boolean logEnabled(LEVEL logLevel) {
        return startLog && doLog && logLevel.code() >= this.level.code();
    }
    
    public static class LogMapBuilder {
        Map<String, String> info;
        LogMapBuilder() {
            info = new LinkedHashMap<>();
        }
        public LogMapBuilder withName(String name) {
            info.put("Name", name);
            return this;
        }
        public LogMapBuilder with(String key, String value) {
            info.put(key, value);
            return this;
        }
        public LogMapBuilder withPartial(Map<String, String> infoMap) {
            info.putAll(infoMap);
            return this;
        }
        public Map<String, String> build() {
            if (!info.containsKey(NAME))
                throw new IllegalArgumentException("Name missing");
            return info;
        }
        public Map<String, String> buildPartial() {
            return info;
        }
    }

}
