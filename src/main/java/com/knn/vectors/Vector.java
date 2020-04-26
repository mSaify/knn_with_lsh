package com.knn.vectors;

import java.io.Serializable;

public class Vector implements Serializable {



    private  int n;         // length of the vector
    public double[] data;       // array of vector's components

    public Vector(){

    }

    // create the zero vector of length n
    public Vector(int n) {
        this.n = n;
        this.data = new double[n];
    }

    // create a vector from an array
    public Vector(double[] data) {
        n = data.length;

        // defensive copy so that client can't alter our copy of data[]
        this.data = new double[n];
        for (int i = 0; i < n; i++)
            this.data[i] = data[i];
    }

    public void print() {

        for (double d : data) {

            //System.out.println(d);
        }
    }

    // create a vector from either an array or a vararg list
    // this constructor uses Java's vararg syntax to support
    // a constructor that takes a variable number of arguments, such as
    // Vector x = new Vector(1.0, 2.0, 3.0, 4.0);
    // Vector y = new Vector(5.0, 2.0, 4.0, 1.0);
/*
    public Vector(double... data) {
        n = data.length;

        // defensive copy so that client can't alter our copy of data[]
        this.data = new double[n];
        for (int i = 0; i < n; i++)
            this.data[i] = data[i];
    }
*/
    // return the length of the vector
    public int length() {
        return n;
    }

    // return the inner product of this Vector a and b
    public double innerProduct(Vector that) {
        if (this.length() != that.length())
            throw new IllegalArgumentException("dimensions disagree");
        double sum = 0.0;
        for (int i = 0; i < n; i++)
            sum = sum + (this.data[i] * that.data[i]);
        return sum;
    }

    public double dot(Vector that) {
        var val = innerProduct(that);
        return (val)/(this.magnitude()*that.magnitude());
    }

    public double angularDistance(Vector that) {
        //System.out.println((Math.acos(this.dot(that)))/(Math.PI));
        return (Math.acos(this.innerProduct(that)))/(Math.PI);
    }



    // return the Euclidean norm of this Vector
    public double magnitude() {
        return Math.sqrt(this.innerProduct(this));
    }

}