package com.knn.vectors;

import java.io.Serializable;
import java.util.BitSet;

public class HammingSpaceVector extends BitSet implements Comparable<HammingSpaceVector>, Serializable {


    public  HammingSpaceVector(int n) {
        super(n);
    }


    @Override
    public int compareTo(HammingSpaceVector hammingSpaceVector) {
        int i=0;
        while( i<this.size() && i<hammingSpaceVector.size() ) {

            if( this.get(i) == hammingSpaceVector.get(i) )
            {
                i++;
                continue;
            }

            var orig = this.get(i)==true?1:0;
            var comp = hammingSpaceVector.get(i)==true?1:0;

            if (orig<comp)  {
                return -1;
            }
            else {
                return 1;

            }
        }

        return 0;
    }
}
