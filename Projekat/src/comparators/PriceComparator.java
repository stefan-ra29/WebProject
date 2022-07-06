package comparators;

import beans.Workout;

import java.util.Comparator;

public class PriceComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        double g1 = ((Workout) o1).getSupplement();
        double g2 = ((Workout) o2).getSupplement();

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