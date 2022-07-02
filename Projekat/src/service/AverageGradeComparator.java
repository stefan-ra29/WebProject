package service;

import beans.SportFacility;

import java.util.Comparator;

public class AverageGradeComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        double g1 = ((SportFacility) o1).getAverageGrade();
        double g2 = ((SportFacility) o2).getAverageGrade();

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
