package com.knn;

import com.knn.vectors.HammingSpaceVector;
import com.knn.vectors.VectorGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

public class Indexer {


    public  static  void main(String argsv[]) {

        Constants.InitializeVariables(argsv);

        try {

            var inputVectors = FileOperations.readDoubleArray(Constants.inputFile);
            VectorGenerator vectorGenerator = new VectorGenerator();

            //var inputVectors = vectorGenerator.randomUnitRealVectors(d,D);
            var unitVectors = vectorGenerator.randomUnitVectors(Constants.d,Constants.D);

            FileOperations.saveUnitVecAsObject(unitVectors,"unitVectors.obj");

            var hammingSpaceVectors = vectorGenerator
                                    .generateHammingSpaceVectors(inputVectors, unitVectors);

            ArrayList<SortedMap<HammingSpaceVector,Integer>> res = getLPermutatedHammingVectors(hammingSpaceVectors,
                    Constants.D, Constants.L,ExecuteOperation.Index);

            FileOperations.saveHammingVectorAsObject(res,"hammingVector.obj");

            System.out.println(String.format("generated indexed file hammingVector.obj and unitVectors.obj"));


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
