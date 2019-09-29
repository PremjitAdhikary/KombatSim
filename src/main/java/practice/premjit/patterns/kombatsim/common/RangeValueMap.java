package practice.premjit.patterns.kombatsim.common;

import java.util.TreeMap;

/**
 * Best to explain is through an example. Suppose we have to grade students
 * according to their marks. We have the grades as follows:
 * <p>
 * <ul>
 * <li>Marks between 90-100, Grade A+
 * <li>Marks between 80-89, Grade A
 * <li>Marks between 70-79, Grade B+
 * <li>Marks between 60-69, Grade B
 * <li>Marks between 50-59, Grade C
 * <li>Marks between 40-49, Grade D
 * <li>Marks between 30-39, Grade E
 * <li>Marks between 0-29, Grade F
 * </ul>
 * 
 * One way is to have a map of keys from 0-100 and corresponding grades as
 * values. This is inefficient as there are lots of redundant values.
 * <p>
 * 
 * RangeValueMap solves this problem by letting you add the lower value of the
 * range and the corresponding data against it. So for the above, it would as
 * simple as:
 * <p>
 * 
 * <pre>
 * RangeValueMap gradeMap = RangeValueMap.builder()
 *         .addRangeValue(0, "F")
 *         .addRangeValue(30, "E")
 *         .addRangeValue(40, "D")
 *         .addRangeValue(50, "C")
 *         .addRangeValue(60, "B")
 *         .addRangeValue(70, "B+")
 *         .addRangeValue(80, "A")
 *         .addRangeValue(90, "A+")
 *         .build();
 * String grade = gradeMap.getValue(65); // returns B
 * </pre>
 * 
 * Note that RangeValueMap gives back an unmodifiable data structure. <br>
 * 
 * @author Premjit Adhikary
 *
 */
public class RangeValueMap {
    
    private TreeMap<Double, Double> map;
    
    private RangeValueMap() {
        map = new TreeMap<>();
    }
    
    public boolean hasValue(Double rangeKey) {
        return map.floorEntry(rangeKey) != null;
    }
    
    public Double getValue(Double rangeKey) {
        if (!hasValue(rangeKey))
            throw new IllegalArgumentException("Key not in Range.");
        return map.floorEntry(rangeKey).getValue();
    }
    
    public static RangeValueMapBuilder builder() {
        return new RangeValueMapBuilder();
    }
    
    public static class RangeValueMapBuilder {
        RangeValueMap valueMap;
        
        private RangeValueMapBuilder() {
            valueMap = new RangeValueMap();
        }
        
        public RangeValueMapBuilder addRangeValue(Double rangeKeyTo, Double value) {
            valueMap.map.put(rangeKeyTo, value);
            return this;
        }
        
        public RangeValueMap build() {
            return valueMap;
        }
    }

}
