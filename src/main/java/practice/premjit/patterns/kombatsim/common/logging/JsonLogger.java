package practice.premjit.patterns.kombatsim.common.logging;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonLogger extends KombatLogger {
	static final String SOURCE_EVENT = "SourceEvent";
	static final String PATH = "c:/Temp/Kombat/";
	List<Map<String, Object>> allEvents;
	Map<String, Object> data;
	Gson gson;

	@Override
	protected void logit(LEVEL level, EVENT_TYPE type, Map<String, String> source, Map<String, String> event) {
		Map<String, Object> sourceEvent = new LinkedHashMap<>();
		sourceEvent.put(TYPE, type);
		sourceEvent.put(SOURCE, source);
		sourceEvent.put(EVENT, event);
		
		allEvents.add(sourceEvent);
	}
	
	@Override
	public void start() {
		super.start();
		allEvents = new ArrayList<>();
		data = new LinkedHashMap<>();
		GsonBuilder builder = new GsonBuilder();
		gson = builder.setPrettyPrinting().create();
	}
	
	@Override
	public void stop() {
		super.stop();
		processAllEvents();
		fileIt();
	}
	
	private void fileIt() {
		String fileName = processDataForFileName();
		String content = gson.toJson(data);
		Path filePath = Paths.get(PATH);
		try {
			if (!filePath.toFile().exists())
			    Files.createDirectories(Paths.get(PATH));
			Files.write(Paths.get(PATH+fileName + ".json"), content.getBytes());
			System.out.println("Json at: " + (PATH + fileName + ".json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String processDataForFileName() {
		String champion = data.get(CHAMPION).toString();
		String challenger = data.get(CHALLENGER).toString();
		
		if (data.containsKey(WINNER)) {
			if (CHAMPION.equals(data.get(WINNER).toString()))
				champion += "(W)";
			else
				challenger += "(W)";
		}
		return champion+"_"+challenger+"_"+System.currentTimeMillis();
	}
	
	private void processAllEvents() {
		List<Map<String, Object>> fighters = findEvents(FIGHTER_ADDED);
		String champion="";
		String challenger="";
		for (Map<String, Object> fighter : fighters) {
			Map<String, String> fighterEvent = (Map<String, String>) fighter.get(EVENT);
			Map<String, String> fighterSource = (Map<String, String>) fighter.get(SOURCE);
			if (fighterEvent.get(AS).equals(CHAMPION)) {
				champion = fighterSource.get(FIGHTER_TYPE)+"["+fighterSource.get(FIGHTER_SUBTYPE)+"]";
			} else {
				challenger = fighterSource.get(FIGHTER_TYPE)+"["+fighterSource.get(FIGHTER_SUBTYPE)+"]";
			}
		}
		
		data.put(CHAMPION, champion);
		data.put(CHALLENGER, challenger);
		
		Map<String, Object> arena = findEvents(FIGHT_START).get(0);
		Map<String, String> arenaSource = (Map<String, String>) arena.get(SOURCE);
		data.put("Arena", arenaSource.get(NAME));

		List<Map<String, Object>> winner = findEvents(WON);
		if (!winner.isEmpty()) {
			Map<String, String> winnerSource = ((Map<String, String>) winner.get(0).get(EVENT));
			if (winnerSource.get(AS).equals(CHAMPION)) {
				data.put(WINNER, CHAMPION);
			} else {
				data.put(WINNER, CHALLENGER);
			}
		}
		
		List<Map<String, Object>> tiks = findEvents(TIK);
		data.put("Beats", tiks.size()-1);
		
		data.put(SOURCE_EVENT, allEvents);
	}
	
	private List<Map<String, Object>> findEvents(String eventName) {
		return allEvents.stream()
				.filter(se -> {
					Map<String, String> e = ((Map<String, String>) se.get(EVENT));
					if (e == null) return false;
					return e.get(NAME).equals(eventName);
				})
				.collect(Collectors.toList());
	}

}
