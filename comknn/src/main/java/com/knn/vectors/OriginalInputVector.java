package com.knn.vectors;

import com.knn.FileOperations;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OriginalInputVector implements Serializable {

    private static VectorArray vec;

    private ArrayList<Integer> x = new ArrayList<>();

    List<Integer> y = new ArrayList<Integer>();


    public static VectorArray getInstance(String inputFile) throws IOException {
        if(vec == null){
            vec = FileOperations.readDoubleArray(inputFile);
        }

        return vec;
    }

    private OriginalInputVector(String inputFile) throws IOException {

         vec = FileOperations.readDoubleArray(inputFile);

    }

    public static VectorArray getVector() {
        return vec;
    }

    public static Integer length() {
        return vec.Vectors.length;
    }

    public static Vector get(int index){

        return (Vector) vec.Vectors[index];

    }


}
