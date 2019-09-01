package practice.premjit.patterns.kombatsim.common.logging;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;

public class BasicLogger extends KombatLogger {

	@Override
	public void logit(LEVEL level, EVENT_TYPE type, Map<String, String> source, Map<String, String> event) {
		StringBuilder sb = new StringBuilder();
		sb.append(TYPE).append(": ").append(type).append("\n");
		if (source != null) {
			sb.append(SOURCE).append(":").append("\n");
			source.entrySet().forEach( e -> sb.append(" "+e.getKey()+": "+e.getValue()+"\n"));
		}
		if (event != null) {
			sb.append(EVENT).append(":").append("\n");
			event.entrySet().forEach( e -> sb.append(" "+e.getKey()+": "+e.getValue()+"\n"));
		}
		System.out.println(sb.toString());
	}

}
