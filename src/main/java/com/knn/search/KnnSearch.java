package com.knn.search;

import com.knn.ExecuteOperation;
import com.knn.FileOperations;
import com.knn.Indexer;
import com.knn.vectors.*;
import com.knn.vectors.Vector;

import java.util.*;
import java.util.stream.Collectors;

import static com.knn.search.BinarySearch.GetMatchedIndex;

public class KnnSearch {

    private ArrayList<SortedMap<HammingSpaceVector,Integer>> indexVectors;
    private int d,L,D,C,k;
    private Collection<Integer> LpermutedMatches;

    public KnnSearch(String indexFile,String ... args) {

        d = Integer.parseInt(args[0]);
        D = Integer.parseInt(args[1]);
        L = Integer.parseInt(args[2]);
        C = Integer.parseInt(args[3]);
        k = Integer.parseInt(args[4]);

        GenerateIndex(indexFile);
    }

    private void GenerateIndex(String inputFile) {
        indexVectors = FileOperations.readHammingVectorAsObject(inputFile);
        //VectorGenerator vectorGenerator = new VectorGenerator();
        //inputVectors = FileOperations.readDoubleArray(inputFile);
        //indexVectors = GetIndex(inputVectors,10,10,10);
    }

    public Iterable<Integer> Search(Vector v) {

        //SortedMap<HammingSpaceVector,Integer> sortedVectors = new TreeMap<>();
        SortedMap<Integer,HashSet<Integer>> sortedOnMatchingPrefix = new TreeMap<>();



        var query = GetIndex(new VectorArray(v),d,L,D);

        ArrayList<Integer> result = new ArrayList<>();

        Integer [] y = new Integer[result.size()];

        result.toArray(y);

        for(int i=0;i<query.size();i++) {
            var currVectors = indexVectors.get(i);

            //System.out.println("this is the vector " + currVectors);

            var eachQuery = query.get(i).firstKey();

            var finalBinaryFilteredVectors = GetMatchedIndex(eachQuery, currVectors, C);

            for (var filVec : finalBinaryFilteredVectors.entrySet()) {
                var tempVec = filVec.getKey();
                 tempVec.and(eachQuery);
                 if(!sortedOnMatchingPrefix.containsKey(tempVec.cardinality()*-1)) {
                     HashSet<Integer> l = new HashSet<>();
                     for (Integer x:l) {
                     }
                     l.add(filVec.getValue());
                     sortedOnMatchingPrefix.put(tempVec.cardinality()*-1,l);
                 }
                 else {
                     sortedOnMatchingPrefix.get(tempVec.cardinality()*-1).add(filVec.getValue());
                 }

            }

//           for(Integer ele : indexes) {
//
//               System.out.println("this is the vector " + ele);
//               System.out.println(currVectors[ele]);
//               var prefixToMatch = currVectors[ele];
//               prefixToMatch.and(eachQuery);
//
//               sortedOnMatchingPrefix.put(prefixToMatch.cardinality()*-1,ele);
//           }
        }

        var topElements=(2*L)+C;
        for(var s : sortedOnMatchingPrefix.entrySet()) {

            if(topElements<1)
                break;
            //System.out.println(String.format(" the vector key %s and its value %s", s, sortedOnMatchingPrefix.get(s)));
            var val = sortedOnMatchingPrefix.get(s);
            for(var ea : val){

                result.add(ea);
                topElements--;
            }
        }

        var res = result.stream().filter(x->!(x==null)).distinct();



        return GetTopkMatchingElements(res.collect(Collectors.toList()), k,v);
    }

    private Collection<Integer> GetTopkMatchingElements(Collection<Integer> inputCollection, int k, Vector query ) {

        SortedMap<Double,Integer> result = new TreeMap<>();

        var cnt=0;
        for (int i : inputCollection) {

            var res =  query.dot(OriginalInputVector.get(i));
            if(cnt<k) {
                result.put(res,i);
            }
            else if (result.firstKey() < res) {
                result.remove(result.firstKey());
                result.put(res,i);
            }
            cnt++;
        }
        return result.values();

    }


    private static ArrayList<SortedMap<HammingSpaceVector,Integer>> GetIndex(VectorArray inputVectors, int d, int L, int D) {

        VectorGenerator vectorGenerator = new VectorGenerator();
        //var inputVectors = vectorGenerator.randomUnitRealVectors(d,D);
        //var unitVectors = vectorGenerator.randomUnitVectors(d, D);

        var unitVectors = FileOperations.readsaveUnitVecAsObject("unitVectors.obj");

        var hammingSpaceVectors = vectorGenerator
                .generateHammingSpaceVectors(inputVectors, unitVectors);

        return Indexer.getLPermutatedHammingVectors(hammingSpaceVectors,d, L, ExecuteOperation.Search);

    }

}
