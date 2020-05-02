package com.knn.search;

import com.knn.vectors.HammingSpaceVector;

import java.util.*;

public class BinarySearch {

    public static SortedMap<HammingSpaceVector,Integer> GetMatchedIndex(HammingSpaceVector query,
                                                SortedMap<HammingSpaceVector,Integer> indexVectorMap, int C) {

        HammingSpaceVector[] hammingSpaceVectors = new HammingSpaceVector[indexVectorMap.size()];
        var indexVector = indexVectorMap.keySet().toArray(hammingSpaceVectors);

        Integer pos = BinarySearch(query, indexVector, 0, indexVector.length - 1);
        SortedMap<HammingSpaceVector, Integer> finalSortedResult = new TreeMap<>();
        HammingSpaceVector filteredVec = indexVector[pos];

        var forward = 0;
        var backward = 0;
        ArrayList<Integer> result = new ArrayList<>();

        var res = indexVectorMap.get(indexVector[pos]);

        finalSortedResult.put(indexVector[pos],res);
        forward=backward=pos;
        for (int i = 1; i <= C; i++) {

            forward++;
            backward--;

            if (forward < indexVector.length - 1) {
                var forwardMap = indexVectorMap.get(indexVector[forward]);
                finalSortedResult.put(indexVector[forward],forwardMap);
            }

            if (backward > 0) {
                var backwardMap = indexVectorMap.get(indexVector[backward]);
                finalSortedResult.put(indexVector[backward],backwardMap);
            }
        }

        return finalSortedResult;

    }



    private static Integer BinarySearch(HammingSpaceVector query,
                                     HammingSpaceVector[] indexVector, int start, int end) {

        var mid = (start+end)/2;

        if(query.equals(indexVector[mid]) || (start>=end) )
            return mid;

        else if (query.compareTo(indexVector[mid]) < 0) {
            return BinarySearch(query, indexVector, start, mid - 1);
        }
        else
            return BinarySearch(query, indexVector, mid+1, end);
    }

}
