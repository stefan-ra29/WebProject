package comparators;

import dto.CustomerWorkoutHistoryDTO;

import java.time.LocalDateTime;
import java.util.Comparator;

public class DateComparator  implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        LocalDateTime g1 = ((CustomerWorkoutHistoryDTO) o1).getStartTime();
        LocalDateTime g2 = ((CustomerWorkoutHistoryDTO) o2).getStartTime();

        if (g1.isBefore(g2)) {
            return -1;
        }
        else if (g1.isAfter(g2)) {
            return 1;
        }
        return 0;
    }

    @Override
    public Comparator reversed() {
        return Comparator.super.reversed();
    }
}