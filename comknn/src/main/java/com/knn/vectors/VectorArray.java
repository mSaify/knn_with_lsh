package com.knn.vectors;

import java.io.Serializable;
import java.util.ArrayList;

public class VectorArray extends ArrayList implements Serializable {

    public Vector[] Vectors;

    public VectorArray(Vector[] v) {
        this.Vectors = v;
    }

    public VectorArray(Vector v) {
        this.Vectors = new Vector[] {v};
    }

    public VectorArray(ArrayList<Vector> v) {
        this.Vectors = new Vector[v.size()];
        v.toArray(this.Vectors);
    }

    public VectorArray(double[][] d) {
        Vectors = new Vector[d.length];
        for (int i=0;i<d.length;i++) {
            Vectors[i] = new Vector(d.length);
            for(int j=0; j<d[0].length; j++) {
                Vectors[i].data[j] = d[i][j];
            }
        }
    }

    public double[][] toDoubleArray() {
        double[][] d=null;
        if (this.Vectors[0] != null) {
            d = new double[this.Vectors.length][this.Vectors[0].length()];
            for (int i=0;i<this.Vectors.length;i++) {
                for(int j=0; j<this.Vectors[0].length(); j++) {
                    d[i][j]= Vectors[i].data[j];
                }
            }
        }

        return d;

    }

}
