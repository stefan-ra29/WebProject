package comparators;

import dto.CustomerWorkoutHistoryDTO;

import java.util.Comparator;

public class PriceComparatorForWorkoutHistory implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        double g1 = ((CustomerWorkoutHistoryDTO) o1).getSupplement();
        double g2 = ((CustomerWorkoutHistoryDTO) o2).getSupplement();

        if (g1 < g2) {
            return -1;
        }
        else if (g1 > g2) {
            return 1;
        }
        return 0;
    }

    @Override
    public Comparator reversed() {
        return Comparator.super.reversed();
    }
}