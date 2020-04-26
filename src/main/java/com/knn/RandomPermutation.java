package com.knn;

import com.knn.vectors.HammingSpaceVector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class RandomPermutation implements Serializable {


    ArrayList<ArrayList<Integer>> val;

    Integer L;
    Integer d;

    public  static  void main(String argsv[]) {
        var L=4;
        var d=5;
        var obj = new RandomPermutation(L,d);
        obj.print();

    }


    public RandomPermutation(int L, int d) {

        this.L=L;
        this.d=d;

        val = new ArrayList<>(L);

        for(int i=0; i<L;i++) {
            var perArr = new ArrayList<Integer>();
            for(int j=0; j<d;j++) {

                perArr.add(j);
            }

            Collections.shuffle(perArr);

            val.add(perArr);

        }
    }

    public void print(){
        for(int i=0; i<L;i++) {

            System.out.println(String.format("printing %s th array",i));

            for(int j=0; j<d;j++) {

                System.out.println(this.val.get(i).get(j));
            }

        }
    }

    public HammingSpaceVector[] shuffle(HammingSpaceVector origVector) {

        var resultVector = new HammingSpaceVector[L];

        for(int i=0;i<L;i++){

            var currL = val.get(i);
            var newVector = new HammingSpaceVector(origVector.size());

            for(int j=0;j<currL.size();j++){

                if (origVector.get(currL.get(j)))
                    newVector.set(j);
            }

            resultVector[i]=newVector;
        }
        return resultVector;
    }
}
