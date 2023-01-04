package comparators;

import dto.CustomerWorkoutHistoryDTO;

import java.util.Comparator;

public class NameComparatorForWorkouts implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        String n1 = ((CustomerWorkoutHistoryDTO) o1).getFacilityName().toLowerCase();
        String n2 = ((CustomerWorkoutHistoryDTO) o2).getFacilityName().toLowerCase();

        if(	n1.strip().compareTo(n2.strip()) < 0 ) {
            return -1;
        }
        else if( n1.strip().compareTo(n2.strip()) > 0){
            return 1;
        }
        else
            return 0;
    }

    @Override
    public Comparator reversed() {
        return Comparator.super.reversed();
    }
}
