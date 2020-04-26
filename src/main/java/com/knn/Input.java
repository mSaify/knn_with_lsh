package com.knn;

import com.knn.vectors.VectorGenerator;

import java.io.IOException;
import java.util.Arrays;



public class Input {

    public  static  void main(String argsv[]) {
        try {

            var queries= Integer.parseInt(Constants.queries);

            var d = Integer.parseInt(Constants.d);
           // var d = argsv[0];
            var n = Integer.parseInt(Constants.n);
           // var n = argsv[0];

            VectorGenerator v = new VectorGenerator();
            var res = v.randomUnitRealVectorsDouble(d,n);
            var query = Arrays.copyOfRange(res,res.length/2,(res.length/2) + queries);

            System.out.println(query.length);

            FileOperations.saveDoubleArray(res, "input_file");
            FileOperations.saveDoubleArray(query, "query_file");

        }
        catch (IOException ex ) {
            System.out.println(ex);
        }

    }

}
