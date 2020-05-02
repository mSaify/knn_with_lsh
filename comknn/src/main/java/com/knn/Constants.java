package com.knn;

import java.nio.file.Paths;

public class Constants {

    public static Integer d=50;
    public static Integer D=150;
    public static Integer L =70;
    public static Integer K = 50;
    public static Integer C = 100;
    public static Integer n = 100;

    public static String inputFile = "";
    public  static String queryFile = "";


    public static String queries = "10";


    public static void  InitializeVariables(String[] argsv){

        // initialize d where d is
        if(argsv.length>1) {
            inputFile = argsv[1];
            queryFile = argsv[1];
        }

        else {
            inputFile = Paths.get("").toAbsolutePath().toString() + "/comknn/input_file";
            queryFile = Paths.get("").toAbsolutePath().toString() + "/comknn/query_file";
        }

        // initialize d where d is dimension of input vector
        if(argsv.length>2)
            d= Integer.parseInt(argsv[2]);

        // initialize L where L is random permutations
        if(argsv.length>3)
            L= Integer.parseInt(argsv[3]);


        // initialize D where D is hamming space vector dimension
        if(argsv.length>4)
            D= Integer.parseInt(argsv[4]);


        // initialize K where K is number of nearest neighbors
        if(argsv.length>5)
            D= Integer.parseInt(argsv[5]);

        // initialize C where C is an hyper parameter
        if(argsv.length>6)
            D= Integer.parseInt(argsv[6]);

    }


}
