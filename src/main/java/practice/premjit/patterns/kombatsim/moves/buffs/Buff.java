package practice.premjit.patterns.kombatsim.moves.buffs;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;

import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.moves.Move;

public abstract class Buff implements Move, Loggable, Cloneable {
	protected String buffType;

	@Override
	public Map<String, String> mapify() {
		return KombatLogger.mapBuilder()
				.with(BUFF_TYPE, buffType)
				.buildPartial();
	}

}
