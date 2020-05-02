package com.knn.search;

import com.knn.vectors.OriginalInputVector;
import com.knn.vectors.Vector;
import com.knn.vectors.VectorArray;

import java.util.Collection;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class LinearSearch {




    public static double GetAngularDistance(Vector v1, Vector v2) {
        return v1.dot(v2);
    }


    public static Collection<Integer> TopKSearch(Vector query, Integer k) {

        SortedMap<Double,Integer> result = new TreeMap<>();

        for (int i=0;i<OriginalInputVector.length();i++) {

            var res =  query.angularDistance(OriginalInputVector.get(i));

            if(i<k) {
                result.put(res,i);
            }
            else if (result.lastKey() > res) {
                result.remove(result.lastKey());
                result.put(res,i);
            }
        }

        return result.values();

    }
}
