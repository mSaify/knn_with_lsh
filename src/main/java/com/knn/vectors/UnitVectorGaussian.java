package com.knn.vectors;

import java.io.Serializable;
import java.util.Random;

class UnitVectorGaussian extends UnitVector implements Serializable {

    Random r = new Random();

    public UnitVectorGaussian() {
        super();
    };

    public UnitVectorGaussian(int n, double mean, double deviation) {
        super(n);
        for(int i = 0; i < n; i++) {
            data[i]= r.nextGaussian();
        }

        var mag = this.magnitude();
        for(int i = 0; i < n; i++) {
            data[i]= data[i]/mag;
        }
    }
}
