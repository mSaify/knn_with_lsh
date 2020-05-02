# K Nearest Neighbour Implementation using locality sensitive hashing


Input to the algorithm is the set of n unit real vectors, which are hashed and indexed.
The Indexed data is to find a nearest neighbour for a given query

## Algorithm Steps

### Indexing

 1. Input Parameters
    ~~~~
    input file - containing n unit vectors 
    d - dimension of the input vectors
    D - dimension of locality sensitive hash
    L - number of Locality sensitive hash permutations
    
    executor /executors/nn_index.sh input_file d D L
    
  2. Steps 
  
    ~~~~
    1. Generate D independent random unit vectors, 
       components drawn from normal distribution N(0,1)
       and divided by norm to obtain vectors.
    
    2. Create binary hamming space vector from input vectors
        a. Do dot product of input vector with unit vector from previous step.
        b. Map result to R = {0,1} where -ve val 0 and +ve 1
    
    3. Create L random permutations with random shuffled vector indexes.
       like if D = 5 valid random indexes {3,4,2,5,1} or {4,2,5,3,1}. 
       L of these random hash.
    
    4. Hash the hamming space binary vectors based on 
       these permutations to generate T1..TL list each of size n
    
    5. Sort each T1..TL list in lexographichal order 
       for binary vectors if vectors are {1,0,1,1}, {0,1,0,1},{1,1,0,1}
       lexographically sorted vectors will be
       {0,1,0,1}
       {1,0,1,1}
       {1,1,0,1}
    
     ~~~~
    

   3.Execution and artifacts
  
     executor /executors/nn_index.sh input_file d D L
     
     will generate serialized 
     **_unitVector.obj 
     L permutations shuffling indexes - randompermute.obj 
     L permutated hammingVector.obj_** 
     vector.info - static info like d D L n
     these will  be used for while searching
   
  
### Searching

   1. Input Parameters
  
       ~~~~
       query file - containing unit vectors 
       K - no of nearest neighbors for the given query
       C - hyper parameter to decide how 
           many vectors to be collected from 
           each L permutated vector
       
       executor /executors/nn_index.sh input_file d D L
       
       ~~~~
      
   2. Search Steps
    
  
     1. Create binary hamming space vector from query vectors
         a. Do dot product of input vector with unit vector from previous step.
         b. Map result to R = {0,1} where -ve val 0 and +ve 1
         
     2.  Load the T1..TL permutated vectors from hammingVector.obj
     
     3. Based on L random indexes shuffle input query
        Tq1..TqL
     
     4. Do binary search in each T to find where the input vector Tq matches.
     
     5. Use the C parameters to expand and collect all 
        2C neigbors where Tq matched in T
        
     6. From L*(2C) choose the Top K vector which are the best match 
        The index position of these vectors are K nearest neighbors.
     
     
   
   3 . Execution
  
    executors/nn_search.sh input file d L D K C
    
    
   4 . Result / Output of search
    
    for each query the index of matched vectors from input
    
    knn_search_matches
    
    query 1 - [ n1, n2 ... nk]
    


TO DO : 

1. from the indexer code store the value of d D L
2. Fix error - ava.lang.ClassCastException: class java.util.TreeMap$Entry cannot be cast to class java.lang.Comparable
3. search result proper printing
4. Fix dimensions disagree while running index executor
5. Code commenting
6. Fix all extra java files

