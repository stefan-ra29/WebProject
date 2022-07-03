package service;

import beans.SportFacility;

import java.util.Comparator;

public class NameComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        String n1 = ((SportFacility) o1).getName();
        String n2 = ((SportFacility) o2).getName();

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
