package com.knn;

import com.knn.search.KnnSearch;
import com.knn.search.LinearSearch;
import com.knn.vectors.OriginalInputVector;
import com.knn.vectors.Vector;
import com.knn.vectors.VectorArray;
import com.knn.vectors.VectorGenerator;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SearchExecutor {

    static String K = Constants.K;
    static String C = Constants.C;
    static String d = Constants.d;
    static String D = Constants.D;
    static String L = Constants.L;

    static String hammVecObjPath = "hammingVector.obj";

    public  static  void main(String argsv[]) throws IOException {

        var args = new String[]{"query_file", "3", "2"};

         var queryFile = args[0];
           K = args[1];
         var C = args[2];


         var inputVectors =  OriginalInputVector.getInstance("input_file");

        var queries = FileOperations.readDoubleArray(queryFile);

         var knnSearchResult = TopknnSearch(queries,inputVectors, K, C);

         var linearSearchResult = TopkLinearSearch(queries, Integer.parseInt(K));

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

        System.out.println(String.format("recall rate %s ", Math.abs ((count  * 200)/((Integer.parseInt(K))* knnSearchResult.size()))));
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
                                                                      String k, String c) throws IOException {

        HashMap<Integer,Collection<Integer>> queryResult = new HashMap<>();

        var knn = new KnnSearch(hammVecObjPath,d, D, L,c, k);


        int qIndex=0;

        System.out.println("running knn search ... ");
        for(Vector query : queries.Vectors) {
            System.out.println("running knn search query "+ qIndex);
            var res = knn.Search(query);



            //res.forEach(x-> System.out.println(String.format(" for query %s matched value %s",query,x)));
            //System.out.println();

            queryResult.put(qIndex++,res);
        }



        return queryResult;
    }


}
