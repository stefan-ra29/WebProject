package comparators;

import beans.Customer;

import java.util.Comparator;

public class PointsComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        double g1 = ((Customer) o1).getPoints();
        double g2 = ((Customer) o2).getPoints();

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