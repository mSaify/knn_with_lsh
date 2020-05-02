package com.knn.vectors;

import java.io.Serializable;

class UnitVector extends Vector implements Serializable {

    double vectorSum = 0.0;

    public UnitVector() {
        super();
    };

    public UnitVector(int n, double ... range) {
        super(n);

        var min=-500000.0;
        var max=500000.0;

        if (range!= null && range.length>0) {
            min = range[0];
            max = range[1];

        }
        for(int i = 0; i < n; i++) {
            data[i]=  getRandomIntegerBetweenRange(min, max);
        }
        vectorSum = this.magnitude();
        for(int i = 0; i < n; i++) {
            data[i]= data[i]/vectorSum;
        }
    }

    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;

       // System.out.println(x);

        return x;
    }

    public double unitVectorSum() {
        double sum = 0.0;
        for (int i = 0; i < this.data.length; i++) {
            sum = sum + data[i];
        }
        return sum;
    }
}

