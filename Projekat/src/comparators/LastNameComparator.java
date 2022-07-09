package comparators;

import beans.User;

import java.util.Comparator;

public class LastNameComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        String n1 = ((User) o1).getLastName().toLowerCase();
        String n2 = ((User) o2).getLastName().toLowerCase();

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