# 2-3-4 tree

Strategy for add(), remove()

https://www.cs.purdue.edu/homes/ayg/CS251/slides/chap13a.pdf

## remove example

The first string added to a empty node is stored at the index 1 of the array. The second is stored at the index 0. The third is stored at the index 2.

For illustrative purpose, `[_,J,_]` represents the internal array position the string `J` lives. Every node has 4 children. If any child is not shown in the diagram below, it is null (also `NIL` in the `Node.java`)

```
                [_,J,_]
           /              \
       [ C,F,_]           [_,W,_]
 
    /     |    \          /     \
 [A,B,_][D,E,_][G,H,_] [_,K,_]  [_,X,_]


remove(K)

                [_,J,_]
           /              \
       [C,F,_]            [_,W,_]
 
    /     |    \          /     \
 [A,B,_][D,E,_][G,H,_] [_,_,_]  [_,X,_]


underflow -> fuse

                [_,J,_]
           /              \
       [C,F,_]           [_,_,_]
 
    /     |    \               \
 [A,B,_][D,E,_][G,H,_]        [W,X,_]


underflow -> transfer

                [_,F,_]
           /              \
       [_,C,_]           [_,J,_]
 
        /   \             /   \
  [A,B,_]  [D,E,_]   [G,H,_]   [W,X,_]


remove(X)

                [_,F,_]
           /              \
       [_,C,_]           [_,J,_]
 
        /   \             /   \
  [A,B,_]  [D,E,_]   [G,H,_]   [_,W,_]


remove(W)

                [_,F,_]
           /              \
       [_,C,_]           [_,J,_]
 
        /   \             /   \
  [A,B,_]  [D,E,_]   [G,H,_]   [_,_,_]


underflow -> transfer

                [_,F,_]
           /              \
       [_,C,_]           [_,H,_]
 
        /   \             /   \
  [A,B,_]  [D,E,_]   [_,G,_]   [_,J,_]


remove(G)

                [_,F,_]
           /              \
       [_,C,_]           [_,H,_]
 
        /   \             /   \
  [A,B,_]  [D,E,_]   [_,_,_]   [_,J,_]


underflow -> fuse

                [_,F,_]
           /              \
       [_,C,_]           [_,_,_]
 
        /   \                 \
  [A,B,_]  [D,E,_]             [H,J,_]


underflow -> fuse

           [C,F,_]
 
        /     |     \
  [A,B,_]  [D,E,_]  [H,J,_]


remove(E)

           [C,F,_]
 
        /     |     \
  [A,B,_]  [_,D,_]  [H,J,_]


remove(D)

           [C,F,_]
 
        /     |     \
  [A,B,_]  [_,_,_]  [H,J,_]


underflow -> transfer

           [B,F,_]
 
        /     |     \
  [_,A,_]  [_,C,_]  [H,J,_]


remove(C)

           [B,F,_]
 
        /     |     \
  [_,A,_]  [_,_,_]  [H,J,_]


underflow -> transfer

           [B,H,_]
 
        /     |     \
  [_,A,_]  [_,F,_]  [_,J,_]


remove(J)

           [B,H,_]
 
        /     |     \
  [_,A,_]  [_,F,_]  [_,_,_]


underflow -> fuse

           [_,B,_]
           /     \
      [_,A,_]    [F,H,_]


remove(A)

           [_,B,_]
           /     \
      [_,_,_]    [F,H,_]


underflow -> transfer

           [_,F,_]
           /     \
      [_,B,_]    [_,H,_]


remove(F) -> swap with predecessor -> remove(F)

           [_,B,_]
           /     \
      [_,_,_]    [_,H,_]


underflow -> fuse


      [B,H,_]


```
