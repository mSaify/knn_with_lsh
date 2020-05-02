package com.knn;

import com.knn.vectors.HammingSpaceVector;
import com.knn.vectors.Vector;
import com.knn.vectors.VectorArray;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;


public class FileOperations {

    public static void main(String[] args) throws Exception
    {
        ArrayList<String> namesList = new ArrayList<String>();

        namesList.add("alex");
        namesList.add("brian");
        namesList.add("charles");
        namesList.add("charles");
        namesList.add("charles");
        namesList.add("charles");
        namesList.add("charles");
        namesList.add("charles");
        namesList.add("charles"); namesList.add("charles");


       // saveObject(namesList);

        saveStringBytes(namesList);

    }

    public static void saveStringBytes( ArrayList<String> namesList) {

        try
        {
            FileOutputStream fos = new FileOutputStream("listDataBytes");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(String name : namesList) {
                oos.writeBytes(name);
            }
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }

    public static void saveDoubleArray(double [][] arr, String inputPath) throws IOException {
        try {
            File file = new File(inputPath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file),65000000);
            System.out.print("Writing raw... ");
            write(arr, writer);
        }
        catch (IOException ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    private static void write(double[][] records, Writer writer) throws IOException {
        long start = System.currentTimeMillis();
        for (double[] record: records) {
            writer.write(Arrays.toString(record));
            writer.write("\n");
        }
        writer.flush();
        writer.close();
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000f + " seconds");
    }


    public static VectorArray readDoubleArray(String inputpath) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(inputpath));
        String line = "";
        ArrayList<Vector> inputVector = new ArrayList<>();

        while ((line = br.readLine()) != null ) {
                var v = new Vector(getArray(line));
                //System.out.println(Arrays.toString(v.data));
                inputVector.add(v);

            }

        return new VectorArray(inputVector);
    }


//    public static void saveHammingVectorObject(ArrayList<HammingSpaceVector[]> vectors,String vectorPath) {
//        try {
//            File file = new File(vectorPath);
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file),65000000);
//            System.out.print("Writing raw... ");
//            write(arr, writer);
//        }
//        catch (IOException ex) {
//            System.out.println(ex);
//            throw ex;
//        }
//    }

    public static void saveHammingVectorAsObject(ArrayList<SortedMap<HammingSpaceVector,Integer>> hammingList, String fileName) {
        try
        {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hammingList);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static void saveRandomPermuteAsObject(RandomPermutation randomPermutation,String fileName) {
        try
        {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(randomPermutation);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static ArrayList<SortedMap<HammingSpaceVector,Integer>> readHammingVectorAsObject(String hammingFile) {
        try
        {
            FileInputStream streamIn = new FileInputStream(hammingFile);
            var objectinputstream = new ObjectInputStream(streamIn);
            ArrayList<SortedMap<HammingSpaceVector,Integer>> readData = (ArrayList<SortedMap<HammingSpaceVector,Integer>>) objectinputstream.readObject();
        //    System.out.println(readData);
            return readData;
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            ioe.printStackTrace();
        }
        return null;
    }

    public static void saveUnitVecAsObject(VectorArray arr,String fileName) {
        try
        {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arr);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static VectorArray readsaveUnitVecAsObject(String fileName) {
        try
        {
            FileInputStream streamIn = new FileInputStream(fileName);
            var objectinputstream = new ObjectInputStream(streamIn);
            VectorArray readData = (VectorArray) objectinputstream.readObject();
          //  System.out.println(readData);
            return readData;
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            ioe.printStackTrace();
        }
        return null;
    }

    public static RandomPermutation readRandomPermuteAsObject(String randomPermute) {
        try
        {
            FileInputStream streamIn = new FileInputStream(randomPermute);
            var objectinputstream = new ObjectInputStream(streamIn);
            RandomPermutation readData = (RandomPermutation) objectinputstream.readObject();
         //   System.out.println(readData);
            return readData;
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            ioe.printStackTrace();
        }
        return null;
    }



    public static double[] getArray(String line) {

        var splitLine = line.split(",");
        double [] arr = new double[splitLine.length];
        for(int i=0;i<splitLine.length;i++) {
            if(splitLine[i]!=null) {
                arr[i] = Double.parseDouble(splitLine[i]
                        .replace(" ", "")
                        .replace("]", "")
                        .replace("[", ""));
            }
        }
        return arr;
    }

}
