package org.oregami.gamingEnvironments.model;

import java.util.*;

/**
 * see http://wiki.oregami.org/display/OR/Data+List+5+-+Game+Entry+Type
 * Used in Game-Entity
 */
public enum Region {

    WORLDWIDE,
        EUROPE,
            GREAT_BRITAIN,
            GERMANY,
        NORTH_AMERICA,
        ASIA,
            JAPAN,
        USA,


    ;


    public static boolean isSubRegionOf(Region parent, Region sub) {
        Set<Region> subr = getSubRegions().get(parent);
        return subr!=null && subr.contains(sub);
    }

    private static Map<Region, Set<Region>> subRegions = null;

    public static Map<Region, Set<Region>> getSubRegions() {
        if (subRegions==null) {
            subRegions = new HashMap<>();
            subRegions.put(EUROPE, new HashSet<>(Arrays.asList(GERMANY, GREAT_BRITAIN)));
            subRegions.put(ASIA, new HashSet<>(Arrays.asList(JAPAN)));


            Set<Region> allRegionsWithoutWorldwide = new HashSet<>(Arrays.asList(Region.values()));
            allRegionsWithoutWorldwide.remove(WORLDWIDE);
            subRegions.put(WORLDWIDE, allRegionsWithoutWorldwide);

        }
        return subRegions;
    }
}
