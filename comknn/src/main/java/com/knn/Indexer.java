package com.knn;

import com.knn.vectors.HammingSpaceVector;
import com.knn.vectors.VectorGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

public class Indexer {

    public  static  void main(String argsv[]) {


        //var args = new String[] {"input_file"};

        for(var val : argsv) {
            System.out.println(val);
        }

        var inputFile =  argsv[1];
        var d = Integer.parseInt(argsv[2]);
        var L = Integer.parseInt(argsv[3]);
        var D = Integer.parseInt(argsv[4]);


//        var d = Integer.parseInt(Constants.d);
//        var L = Integer.parseInt(Constants.L);
//        var D = Integer.parseInt(Constants.D);

        var n = Integer.parseInt(Constants.n);

        var size_of_vector = d*n;


        try {

            var inputVectors = FileOperations.readDoubleArray(inputFile);
            VectorGenerator vectorGenerator = new VectorGenerator();

            //var inputVectors = vectorGenerator.randomUnitRealVectors(d,D);
            var unitVectors = vectorGenerator.randomUnitVectors(d,D);

            FileOperations.saveUnitVecAsObject(unitVectors,"unitVectors.obj");

            var hammingSpaceVectors = vectorGenerator
                                    .generateHammingSpaceVectors(inputVectors, unitVectors);

            ArrayList<SortedMap<HammingSpaceVector,Integer>> res = getLPermutatedHammingVectors(hammingSpaceVectors, D, L,ExecuteOperation.Index);

            System.out.println(String.format("Index size for n - %s , L - %s is %s in MB ", 100000, 150, (15000000)/1000000));

            FileOperations.saveHammingVectorAsObject(res,"hammingVector.obj");
        }
        catch (IOException ex ) {
            System.out.println(ex);
        }
    }

    public static ArrayList<SortedMap<HammingSpaceVector,Integer>> getLPermutatedHammingVectors(HammingSpaceVector[] hammingSpaceVectors,int D, int l, ExecuteOperation op) {

        var randomPermutator = new RandomPermutation(l,D);

        if(ExecuteOperation.Index == op) {
            FileOperations.saveRandomPermuteAsObject(randomPermutator,"randomPermute.obj");
        }
        else if(ExecuteOperation.Search == op) {

           randomPermutator = FileOperations.readRandomPermuteAsObject("randomPermute.obj");

        }

        ArrayList<SortedMap<HammingSpaceVector,Integer>> res = new ArrayList<>(l);

        for(int i=0;i<hammingSpaceVectors.length;i++){

            var Lpermutated = randomPermutator.shuffle(hammingSpaceVectors[i]);

            var arr = new ArrayList<>(Lpermutated.length);
            for(int j=0;j<Lpermutated.length;j++) {

                    if(i==0)
                        res.add(new TreeMap<HammingSpaceVector,Integer>());
                    res.get(j).put(Lpermutated[j],i);

            }

        }
        return res;
    }
}
