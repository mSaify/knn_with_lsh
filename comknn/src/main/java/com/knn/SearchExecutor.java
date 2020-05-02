package com.knn;

import com.knn.search.KnnSearch;
import com.knn.search.LinearSearch;
import com.knn.vectors.OriginalInputVector;
import com.knn.vectors.Vector;
import com.knn.vectors.VectorArray;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SearchExecutor {


    static String hammVecObjPath = "hammingVector.obj";


    public  static  void main(String argsv[]) throws IOException {

        Constants.InitializeVariables(argsv);

        var inputVectors =  OriginalInputVector.getInstance(Constants.inputFile);

        var queries = FileOperations.readDoubleArray(Constants.queryFile);

        var knnSearchResult = TopknnSearch(queries,inputVectors, Constants.K, Constants.C);

        var linearSearchResult = TopkLinearSearch(queries, Constants.K);

         GenerateReport(queries,knnSearchResult,linearSearchResult);

    }

    private static void GenerateReport(VectorArray queries,
                                       HashMap<Integer, Collection<Integer>> knnSearchResult,
                                       HashMap<Integer, Collection<Integer>> linearSearchResult) {

        long count=0;

        for (int i=0;i<knnSearchResult.size(); i++) {

            System.out.println(String.format(" for query %s ", queries.Vectors[i]));
            var res = knnSearchResult.get(i).stream().sorted().collect(Collectors.toList());
            var resl = linearSearchResult.get(i).stream().sorted().collect(Collectors.toList());
            System.out.println(String.format("knn search matches %s ", res));
            System.out.println(String.format(" linear search matches %s ", resl));
            var f = res.stream().filter(x-> resl.contains(x) ).count();
            System.out.println(String.format("elements matched %s", f));
            count=count+f;

        }

        System.out.println(String.format("recall rate %s ", Math.abs ((count  * 200)/(((Constants.K))* knnSearchResult.size()))));
    }

    private static  HashMap<Integer, Collection<Integer>> TopkLinearSearch(VectorArray queries,Integer k) {

        HashMap<Integer,Collection<Integer>> queryResult = new HashMap<>();

        int qIndex=0;
        System.out.println("running linear search ... ");
        for(Vector query : queries.Vectors) {
            var res = LinearSearch.TopKSearch(query,k);
            queryResult.put(qIndex++,res);
        }

        return queryResult;
    }

    private static HashMap<Integer, Collection<Integer>> TopknnSearch(VectorArray queries, VectorArray inputVectors,
                                                                      Integer k, Integer c) throws IOException {

        HashMap<Integer,Collection<Integer>> queryResult = new HashMap<>();

        var knn = new KnnSearch(hammVecObjPath,
                Constants.d.toString(), Constants.D.toString(),
                Constants.L.toString(),c.toString(), k.toString());


        int qIndex=0;

        System.out.println("running knn search ... ");
        for(Vector query : queries.Vectors) {
            System.out.println("running knn search query "+ qIndex);
            var res = knn.Search(query);
            queryResult.put(qIndex++, (Collection<Integer>) res);
        }

        return queryResult;
    }


}
