package comparators;

import beans.Location;
import beans.SportFacility;

import java.util.Comparator;

public class LocationComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Location l1 = ((SportFacility) o1).getLocation();
        Location l2 = ((SportFacility) o2).getLocation();

        //prvo po gradu, pa po ulicii pa broju

        if(	l1.getCity().strip().toLowerCase().compareTo(l2.getCity().strip().toLowerCase()) < 0 ) {
            return -1;
        }
        else if( l1.getCity().strip().toLowerCase().compareTo(l2.getCity().strip().toLowerCase()) > 0){
            return 1;
        }
        //ako su iz istog grada, gledamo ulicu
        else {
            if (l1.getStreet().strip().toLowerCase().compareTo(l2.getStreet().strip().toLowerCase()) < 0) {
                return -1;
            } else if (l1.getStreet().strip().toLowerCase().compareTo(l2.getStreet().strip().toLowerCase()) > 0) {
                return 1;
            }
            //ako su iz iste ulice, gledamo broj
            else {

                if (Integer.parseInt(l1.getStreetNumber()) < Integer.parseInt(l2.getStreetNumber())) {
                    return -1;
                } else if (Integer.parseInt(l1.getStreetNumber()) > Integer.parseInt(l2.getStreetNumber())) {
                    return 1;
                }

            }
        }
        return 0;
    }

    @Override
    public Comparator reversed() {
        return Comparator.super.reversed();
    }
}
