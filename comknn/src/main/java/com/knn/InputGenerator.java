package com.knn;

import com.knn.vectors.VectorGenerator;

import java.io.IOException;
import java.util.Arrays;



public class InputGenerator {

    public  static  void main(String argsv[]) {
        try {

            var queries= Integer.parseInt(Constants.queries);

            VectorGenerator v = new VectorGenerator();
            var res = v.randomUnitRealVectorsDouble(Constants.d,Constants.n);
            var query = Arrays.copyOfRange(res,res.length/2,(res.length/2) + queries);

            System.out.println(query.length);

            FileOperations.saveDoubleArray(res, "com/knn/input_file");
            FileOperations.saveDoubleArray(query, "query_file");

        }
        catch (IOException ex ) {
            System.out.println(ex);
        }

    }

}
