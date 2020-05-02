package com.knn.vectors;

import java.io.Serializable;

public class VectorGenerator implements Serializable {

    public VectorArray randomUnitRealVectors(int d, int size) {

        UnitVector[] uv = new UnitVector[size];
        for (int i=0; i<size;i++) {
            uv[i]= new UnitVector(d); //this is real vector generator
            //uv[i].print();
        }

        return new VectorArray(uv);
    }

    public VectorArray randomUnitVectors(int d, int size) {
        UnitVector[] uv = new UnitVector[size];
        for (int i=0; i<size;i++) {
            uv[i]= new UnitVectorGaussian(d,0,1);
        }
        return new VectorArray(uv);
    }

    public double[][] randomUnitRealVectorsDouble(int d, int size) {

        return randomUnitRealVectors(d,size).toDoubleArray();

    }

    public HammingSpaceVector[] generateHammingSpaceVectors(VectorArray inputVectors, VectorArray unitVector) {

        var hammingSpaceVectors = new  HammingSpaceVector[inputVectors.Vectors.length];

        for(int i = 0 ; i<inputVectors.Vectors.length;i++) {

            hammingSpaceVectors[i]= new HammingSpaceVector(unitVector.Vectors.length);
            for(int j = 0 ; j<unitVector.Vectors.length;j++) {

                var val = inputVectors.Vectors[i].dot(unitVector.Vectors[j]);

                if(val>0.0) {
                    hammingSpaceVectors[i].set(j);
                }
            }
        }
        return hammingSpaceVectors;
    }

}
