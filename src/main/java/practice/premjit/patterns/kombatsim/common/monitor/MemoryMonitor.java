package practice.premjit.patterns.kombatsim.common.monitor;

import practice.premjit.patterns.kombatsim.beats.BeatObserver;

public class MemoryMonitor implements BeatObserver {
	static final int MB = 1024*1024;
	long minMemory = Long.MAX_VALUE;
	long maxMemory = Long.MIN_VALUE;
	
	private MemoryMonitor() { }
	
	private static class MemoryMonitorHolder {
		private static final MemoryMonitor INSTANCE = new MemoryMonitor();
	}

	@Override
	public void update() {
		Runtime runtime = Runtime.getRuntime();
		long used = (runtime.totalMemory() - runtime.freeMemory()) / MB;
		minMemory = Math.min(minMemory, used);
		maxMemory = Math.max(maxMemory, used);
	}
	
	public long minMemoryUsed() {
		return minMemory;
	}
	
	public long maxMemoryUsed() {
		return maxMemory;
	}
	
	public static MemoryMonitor getInstance() {
		return MemoryMonitorHolder.INSTANCE;
	}

}
