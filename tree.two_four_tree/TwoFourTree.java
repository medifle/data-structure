package tree.two_four_tree;

import java.util.*;

/**
 * The TwoFourTree class is my implementation of the 2-4 tree from comp2402 fall2018.
 * The tree stores Strings as values.
 * It extends the (modified) sorted set interface (for strings).
 * It implements the LevelOrderTraversal interface.
 * @author medifle
 *
 */
public class TwoFourTree extends StringSSet implements LevelOrderTraversal {
    /**
     * A version of assert that doesn't need -ea java flag
     *
     * @param b the boolean assertion value (must be true)
     * @throws AssertionError
     */
    private static void myassert(boolean b) throws AssertionError {
        if (!b) {
            throw new AssertionError();
        }
    }

    /**
     * The root of this tree.
     */
    protected Node r;

    /**
     * The number of nodes (elements) currently in the tree
     * default is 0
     */
    protected int n;

    /**
     * The 2-4 tree constructor.
     */
    public TwoFourTree() {
        this.r = NIL;
    }

    /**
     * Performs a level order traversal of the tree and outputs a string representation of it.
     *
     * @return a string that represents the level order traversal of the tree
     */
    public String levelOrder() {
        Queue<Node> q = new LinkedList<>();
        StringBuffer sb = new StringBuffer();
        if (r != NIL) {
            q.add(r);
        }
        while (!q.isEmpty()) {
            Node u = q.remove();
            // add children of u to queue
            for (int i = 0; i < u.children.length; i++) {
                if (u.children[i] != NIL) q.add(u.children[i]);
            }
            // add u data to result
            for (int i = 0; i < u.data.length; i++) {
                if (u.data[i] != EMPTY) {
                    sb.append(u.data[i]);
                    // i != 2 AND next data is not EMPTY
                    if (i != u.data.length - 1 && u.data[i + 1] != EMPTY) {
                        sb.append(",");
                    }
                    // if it is the last node in the same level, add "|", else add ":"
                    else { // i == 2
                        Node nextNode = q.peek();
                        if (nextNode != null) {
                            if (nextNode.data[1].compareTo(u.data[1]) < 0) {
                                sb.append("|");
                            } else {
                                sb.append(":");
                            }
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * Gets the size of this SSet.
     *
     * @return the number of elements in this SSet
     */
    public int size() {
        return n;
    }

    /**
     * Check if string x is in node u
     *
     * @param u the node to check
     * @param x the data to check
     * @return true if string x is in node u, otherwise false
     */
    protected boolean containData(Node u, String x) {
        for (int i = 0; i < u.data.length; i++) {
            if (u.data[i] != EMPTY) {
                if (u.data[i].compareTo(x) == 0) return true;
            }
        }
        return false;
    }

    /**
     * Attempts to find the value x in the tree.
     *
     * @param x the data to search
     * @return the node with x if successful, otherwise the last node on the search path for x
     */
    protected Node findLast(String x) {
        Node w = r, prev = NIL;
        while (w != NIL) {
            prev = w;
            // compare x with values in the current node
            for (int i = 0; i < w.data.length; i++) {
                if (w.data[i] == EMPTY) continue;
                int comp = w.data[i].compareTo(x);
                if (comp > 0) { // i-th element is larger than x
                    w = w.children[i];
                    break;
                } else if (comp == 0) {  // find x
                    return w;
                } else { // comp < 0
                    if (i < w.data.length - 1 && w.data[i + 1] == EMPTY) {  // example: data: [EMPTY, "C", EMPTY]
                        w = w.children[i + 1];
                        break;
                    }
                }
                // if reach the last data of the node
                if (i == w.data.length - 1) {
                    w = w.children[3];
                    break;
                }
            }
        }
        return prev;
    }

    /**
     * Finds the smallest element in the SSet that is greater than or equal to x.
     *
     * @param x the data to search
     * @return the smallest element in the SSet that is greater than or equal to
     * x or null if no such element exists
     */
    public String find(String x) {
        Node w = r;  //w: current node. z: sucessor
        String str = null;

        while (w != NIL) {
            // compare x with values in the current node
            for (int i = 0; i < w.data.length; i++) {
                if (w.data[i] == EMPTY) continue;

                int comp = w.data[i].compareTo(x);
                if (comp > 0) {
                    str = w.data[i];
                    w = w.children[i];
                    break;
                } else if (comp == 0) {  // find x
                    return w.data[i];
                } else { // comp < 0
                    if (i < w.data.length - 1 && w.data[i + 1] == EMPTY) {  // example: data: [EMPTY, "C", EMPTY]
                        w = w.children[i + 1];
                        break;
                    }
                    // if reach the last data
                    if (i == w.data.length - 1) {
                        w = w.children[3];
                        break;
                    }
                }
            }
        }

        return str;
    }


    /**
     * Creates a new node with data x
     *
     * @param x the data to add
     * @return the new node with data x
     */
    protected Node createNode(String x) {
        Node u = new Node();
        u.data[1] = x;  // always put the first data to the middle of array
        return u;
    }

    /**
     * Checks if data in node u is empty
     *
     * @param u the node to check
     * @return true if node u data is empty, otherwise false
     */
    protected boolean isDataEmpty(Node u) {
        if (u.data[1] == EMPTY) return true;
        return false;
    }

    /**
     * Checks if data in node u is full
     *
     * @param u the node to check
     * @return true if node u data is full, otherwise false
     */
    protected boolean isDataFull(Node u) {
        if (u.data[0] != EMPTY && u.data[2] != EMPTY) {
            return true;
        }
        return false;
    }

    /**
     * Adds data x to node u
     *
     * @param u the node that x is added to
     * @param x the data to add
     * @return true if added successfully, otherwise false
     */
    protected boolean addData(Node u, String x) {
        if (isDataFull(u)) return false;
        if (isDataEmpty(u)) { // This implementation always add the first data to the middle of the data array
            u.data[1] = x;
            return true;
        } else {
            if (u.data[0] == EMPTY) {
                u.data[0] = x;
            } else {
                u.data[2] = x;
            }
            arrSort(u);
            return true;
        }
    }


    /**
     * Gets the largest string(right-most) in node u
     *
     * @param u the node where max data come from
     * @return the largest string in node u
     */
    protected String getMaxData(Node u) {
        String x = null;
        for (int i = u.data.length - 1; i >= 0; i--) {
            if (u.data[i] != EMPTY) {
                x = u.data[i];
                break;
            }
        }
        return x;
    }


    /**
     * Adds the element x to the SSet
     *
     * @param x the data added to the tree
     * @return true if the element was added or false if x was already in the
     * set
     */
    public boolean add(String x) {
        if (r == NIL) {
            r = createNode(x);
            n++;
            return true;
        }
        Node p = findLast(x);

        if (containData(p, x)) return false;
        if (!isDataFull(p)) {
            addData(p, x);
        } else { // p.data is full, overflow
            String[] tempData = new String[4];
            System.arraycopy(p.data, 0, tempData, 0, 3);
            tempData[p.data.length] = x;
            Arrays.sort(tempData);
//            myassert(tempData[3] != null);

            // p node will be the left child of the overflowed node
            System.arraycopy(tempData, 0, p.data, 0, 2);
            p.data[2] = EMPTY;
            // q node will be the right child of the overflowed node
            Node q = createNode(tempData[3]);

            split(tempData[2], p.parent, p, q);
        }
        n++;
        return true;
    }

    /**
     * Splits node to solve overflow issue
     *
     * @param x        the overflowed data
     * @param overflow the node where x is overflowed to
     * @param p        the left child of the overflowed node
     * @param q        the right child of the overflowed node
     */
    protected void split(String x, Node overflow, Node p, Node q) {
        if (overflow == NIL) {  // base case: if parent does not exist
            Node ovf = createNode(x);  // create the overflowed node
            // set ovf children
            ovf.children[1] = p;
            ovf.children[2] = q;
            p.parent = ovf;
            q.parent = ovf;
            r = ovf;  // set ovf to be the new root
        } else if (!isDataFull(overflow)) {  // base case: parent is not full
            addData(overflow, x);

            // resolve children binding
            Node[] tempChildren = new Node[4];
            System.arraycopy(overflow.children, 0, tempChildren, 0, 4);
            tempChildren[3] = q;  // tempChildren[childrenSize(overflow)] = q;
            Arrays.fill(overflow.children, NIL);
            for (int i = 0; i < tempChildren.length; i++) {
                if (tempChildren[i] != NIL) {
                    addChild(overflow, tempChildren[i]);
                }
            }
        } else { // parent is full, then recursively split()
            // resolve overflow data
            // the current "overflow" node will be the left child of the next overflowed node
            String[] tempData = new String[4];
            System.arraycopy(overflow.data, 0, tempData, 0, 3);
            tempData[overflow.data.length] = x;
            Arrays.sort(tempData);
            // myassert(tempData[3] != null);
            System.arraycopy(tempData, 0, overflow.data, 0, 2);
            overflow.data[2] = EMPTY;

            // resolve "overflow" node children binding
            Node[] tempChildren = new Node[4];
            System.arraycopy(overflow.children, 0, tempChildren, 0, 4);
            Arrays.fill(overflow.children, NIL);
            for (int i = 0; i < tempChildren.length; i++) {
                if (tempChildren[i] != NIL) {
                    int addIndex = addChild(overflow, tempChildren[i]);
                    // if add successfully, remove the node in tempChildren array
                    // the purpose is for "right" node children binding
                    if (addIndex != -1) {
                        tempChildren[i] = NIL;
                    }
                }
            }

            // "right" node will be the right child of the next overflowed node
            Node right = createNode(tempData[3]);
            for (int i = 0; i < tempChildren.length; i++) { // tempChildren should have only one child left
                if (tempChildren[i] != NIL) {
                    addChild(right, tempChildren[i]);
                }
            }
            addChild(right, q);

            split(tempData[2], overflow.parent, overflow, right);
        }

    }

    /**
     * Adds child node c to node u
     *
     * @param u the parent node
     * @param c the child node
     * @return if added successfully, the index of the children array added to, otherwise -1
     */
    protected int addChild(Node u, Node c) {
        if (c == NIL) return -1;
        c.parent = u;
        String cMaxData = getMaxData(c);
        for (int i = 0; i < u.data.length; i++) {
            if (u.data[i] != EMPTY) {
                int comp = u.data[i].compareTo(cMaxData);
                if (comp > 0) {
                    u.children[i] = c;
                    return i;
                } else if (comp < 0) { // comp cannot == 0, add() need to check duplicate
                    if (i < u.data.length - 1 && u.data[i + 1] == EMPTY) {  // example: data: [EMPTY, "C", EMPTY]
                        if (u.children[i + 1] == NIL) { // only add the child when it is NIL
                            u.children[i + 1] = c;
                            return i + 1;
                        } else {
                            return -1;
                        }
                    }
                    // if reach the last data
                    if (i == u.data.length - 1) {
                        u.children[3] = c;
                        return 3;
                    }
                }

            }
        }
        return -1;
    }

    /**
     * Gets the actual number of data (not including EMPTY)
     *
     * @param u the node to check
     * @return number of data elements
     */
    protected int dataSize(Node u) {
        int count = 0;
        for (int i = 0; i < u.data.length; i++) {
            if (u.data[i] != EMPTY) {
                count++;
            }
        }
        return count;
    }


    /**
     * Removes the right-most data(not including EMPTY) in the node u
     *
     * @param u the node where the right-most data is removed
     * @return the string removed, otherwise null
     */
    protected String removeLast(Node u) {
        String removed = null;
        int ds = dataSize(u);
        // contains only 1 data
        if (ds == 1) {
            removed = u.data[1];
            u.data[1] = EMPTY;
        } else {
            for (int i = u.data.length - 1; i >= 0; i--) {
                if (u.data[i] != EMPTY) {
                    removed = u.data[i];
                    u.data[i] = EMPTY;
                    break;
                }
            }
            // if only one element left, place it to the middle
            if (u.data[1] == EMPTY) {
                u.data[1] = u.data[0];
                u.data[0] = EMPTY;
            }
        }
        return removed;
    }

    /**
     * Removes the left-most data(not including EMPTY) in the node u
     *
     * @param u the node where the left-most data is removed
     * @return the string removed, otherwise null
     */
    protected String removeFirst(Node u) {
        String removed = null;
        int ds = dataSize(u);
        // contains only 1 data
        if (ds == 1) {
            removed = u.data[1];
            u.data[1] = EMPTY;
        } else {
            removed = u.data[0];
            u.data[0] = EMPTY;
            if (ds == 3) { // 2 data left the remove above
                arrSort(u);
            }
        }
        return removed;
    }

    /**
     * Removes string x in the node u
     *
     * @param u the node where x is removed
     * @param x the data to remove
     * @return the removed string x if successful, otherwise null
     */
    protected String removeData(Node u, String x) {
        if (x == null) return null;

        String removed = null;
        // get the number of data in node u
        int ds = dataSize(u);
        // contains only 1 data
        if (ds == 1) {
            if (u.data[1].equals(x)) {
                removed = u.data[1];
                u.data[1] = EMPTY;
            }
        } else {
            for (int i = 0; i < u.data.length; i++) {
                if (u.data[i] != EMPTY && u.data[i].equals(x)) {
                    removed = u.data[i];
                    u.data[i] = EMPTY;
                    break;
                }
            }
            arrSort(u);
            if (u.data[1] == EMPTY) {
                u.data[1] = u.data[0];
                u.data[0] = EMPTY;
            }
        }

        return removed;
    }

    /**
     * Removes the element x from the SSet
     *
     * @param x the data to remove
     * @return true if x was removed and false if x was not removed (because x
     * was not present)
     */
    public boolean remove(String x) {
        Node u = findNode(x); // get the node u where x lives
        if (u == null) return false; // x not found

        String temp = null;
        // if leaf
        if (isLeaf(u)) {
            removeLeaf(u, x);
        } else {
            // find predecessor
            Node pred = findPred(u, x);

            // swap
            if (pred.data[2] != EMPTY) {
                temp = pred.data[2];
                pred.data[2] = EMPTY;
            } else if (pred.data[1] != EMPTY) {
                if (pred.data[0] != EMPTY) {
                    temp = pred.data[1];
                    pred.data[1] = pred.data[0];
                    pred.data[0] = EMPTY;
                } else {
                    temp = pred.data[1];
                    pred.data[1] = EMPTY;
                }
            }

            removeData(u, x);
            addData(u, temp);
            // does not need to add x to pred again
            removeLeaf(pred, EMPTY);
        }

        return true;
    }


    /**
     * Removes x at the leaf node u
     *
     * @param u the node where x is removed
     * @param x the data to remove
     * @return true if remove successfully, otherwise false
     */
    protected boolean removeLeaf(Node u, String x) {
        // check: if it is not leaf, return false
        if (!isLeaf(u)) return false;

        // be consistent in remove(String x)
        // if arg x is EMPTY, indicating x is removed beforehand
        // if not, remove x in node u
        if (x != EMPTY) {
            removeData(u, x);
        }

        // underflow
        if (dataSize(u) == 0) {
            underflow(u, NIL);
        }

        return true;
    }

    /**
     * Handles underflow case: transfer or fuse
     *
     * @param u the node where underflow occurs
     */
    protected void underflow(Node u, Node uc) {
        if (u.parent == NIL) { // base case: reach root
            r = uc;
            r.parent = NIL;
            return;
        }

        int ci = getChildIndex(u); // the index of u in u.parent children array
        myassert(ci != -1);
        Node[] pChildren = u.parent.children;

        // if one of immediate siblings has more than one data, transfer, otherwise fuse
        if (ci == 0) {
            if (dataSize(pChildren[1]) > 1) {
                transfer(pChildren[1], u);
            } else {
                fuse(pChildren[1], u, uc);
            }
        } else if (ci == 3) {
            if (dataSize(pChildren[2]) > 1) {
                transfer(pChildren[2], u);
            } else {
                fuse(pChildren[2], u, uc);
            }
        } else {
            Node lsb = pChildren[ci - 1]; // left immediate sibling
            Node rsb = pChildren[ci + 1]; // right immediate sibling

            if (lsb == NIL) {
                if (dataSize(rsb) > 1) {
                    transfer(rsb, u);
                } else {
                    fuse(rsb, u, uc);
                }
            } else if (rsb == NIL) {
                if (dataSize(lsb) > 1) {
                    transfer(lsb, u);
                } else {
                    fuse(lsb, u, uc);
                }
            } else { // both lsb and rsb are not NIL
                int lsbSize = dataSize(lsb);
                int rsbSize = dataSize(rsb);
                if (lsbSize == 1 && rsbSize == 1) {
                    fuse(lsb, u, uc);
                } else if (lsbSize >= rsbSize) {
                    transfer(lsb, u);
                } else {
                    transfer(rsb, u);
                }
            }

        }
    }

    /**
     * Transfers data from one of immediate siblings whose number of data is larger than 1.
     * If there is a tie, always transfer from the left sibling.
     *
     *<pre>
     *           [ C , I , _]
     *           /    |   \
     *     [A,B,_] [_,E,_] [_,J,_]
     *
     *   remove(E)
     *
     *           [ B , I , _]
     *           /    |   \
     *     [_,A,_] [_,C,_] [_,J,_]
     *</pre>
     *
     * @param from the child node where new data is transferred to parent
     * @param to the child node where data is transferred from parent
     */
    protected void transfer(Node from, Node to) {
        int fi = getChildIndex(from); // "from" index in its parent children array
        int ti = getChildIndex(to); // "to" index in its parent children array
        Node transferChild = NIL;

        if (fi < ti) {
            addData(to, removeData(from.parent, from.parent.data[fi]));
            addData(from.parent, removeLast(from));
            transferChild = removeLastChild(from); // resolve "from" node child binding
        } else if (ti < fi) {
            addData(to, removeData(from.parent, from.parent.data[ti]));
            addData(from.parent, removeFirst(from));
            transferChild = removeFirstChild(from); // // resolve "from" node child binding
        }

        // resolve "to" node child binding
        Node[] toChildren = new Node[4];
        System.arraycopy(to.children, 0, toChildren, 0, 4);
        Arrays.fill(to.children, NIL);
        toChildren[3] = transferChild;
        for (int i = 0; i < to.children.length; i++) {
            if (toChildren[i] != NIL) {
                addChild(to, toChildren[i]);
            }
        }
    }

    /**
     * Removes the first(smallest) child of node u
     *
     * @param u the node where first child is removed
     * @return the removed node
     */
    protected Node removeFirstChild(Node u) {
        Node[] tempChildren = new Node[4];
        System.arraycopy(u.children, 0, tempChildren, 0, 4);
        Arrays.fill(u.children, NIL);
        Node removed = NIL;
        for (int i = 0; i < tempChildren.length; i++) {
            if (tempChildren[i] != NIL) {
                removed = tempChildren[i];
                tempChildren[i] = NIL;
                break;
            }
        }
        for (int i = 0; i < tempChildren.length; i++) {
            addChild(u, tempChildren[i]);
        }
        return removed;
    }

    /**
     * Removes the last(biggest) child of node u
     *
     * @param u
     * @return the removed node
     */
    protected Node removeLastChild(Node u) {
        Node[] tempChildren = new Node[4];
        System.arraycopy(u.children, 0, tempChildren, 0, 4);
        Arrays.fill(u.children, NIL);
        Node removed = NIL;
        for (int i = tempChildren.length - 1; i >= 0; i--) {
            if (tempChildren[i] != NIL) {
                removed = tempChildren[i];
                tempChildren[i] = NIL;
                break;
            }
        }
        for (int i = 0; i < tempChildren.length; i++) {
            addChild(u, tempChildren[i]);
        }
        return removed;
    }


    /**
     *  Merges data from parent to one of immediate siblings.
     *  If there is a tie, always choose the left sibling if applicable
     *<pre>
     *           [ B , I , _]
     *           /    |   \
     *     [_,A,_] [_,C,_] [_,J,_]
     *
     *  remove(C)
     *
     *             [_,I,_]
     *              /   \
     *       [A,B,_]    [_,J,_]
     *</pre>
     *
     * @param sib the sibling of u to fuse
     * @param u the underflow node
     * @param uc the child of u
     */
    protected void fuse(Node sib, Node u, Node uc) {
        int ui = getChildIndex(u); // "u" index in its parent children array
        int si = getChildIndex(sib); // "sib" index in its parent children array
        if (ui < si) {
            addData(sib, removeData(u.parent, u.parent.data[ui]));
        } else if (si < ui) {
            addData(sib, removeData(u.parent, u.parent.data[si]));
        }
        u.parent.children[ui] = NIL;
        u.parent = NIL;

        // resolve "sib" node child binding
        Node[] sibChildren = new Node[4];
        System.arraycopy(sib.children, 0, sibChildren, 0, 4);
        Arrays.fill(sib.children, NIL);
        sibChildren[3] = uc;
        for (int i = 0; i < sibChildren.length; i++) {
            if (sibChildren[i] != NIL) {
                addChild(sib, sibChildren[i]);
            }
        }

        // resolve sib.parent child binding
        /*
         *     [B, H, _]
         *    /   |   \
         *  <A>  <F>  <J>
         *
         *  remove(J)
         *
         *     [_, B, _]
         *       /   \
         *     <A>  [F, H, _]
         */
        if (dataSize(sib.parent) != 0) {
            Node[] sibPaChildren = new Node[4];
            System.arraycopy(sib.parent.children, 0, sibPaChildren, 0, 4);

            Arrays.fill(sib.parent.children, NIL);
            for (int i = 0; i < sibPaChildren.length; i++) {
                if (sibPaChildren[i] != NIL) {
                    addChild(sib.parent, sibPaChildren[i]);
                }
            }

            //test
//            if (sib.data[1].equals("H") && sib.parent.data[1].equals("B")) {
//                System.out.println(Arrays.toString(sib.parent.children[0].data));
//                System.out.println(Arrays.toString(sib.parent.children[1].data));
//                System.out.println(Arrays.toString(sib.parent.children[2].data));
//                System.out.println(Arrays.toString(sib.parent.children[3].data));
//                System.out.println("-----------");
//            }
        }


        if (dataSize(sib.parent) == 0) {
            underflow(sib.parent, sib);
        }
    }


    /**
     * Searches the node with data x
     *
     * @param x the data to search
     * @return the node with data x, otherwise null
     */
    protected Node findNode(String x) {
        Node w = r;
        while (w != NIL) {
            // compare x with values in the current node
            for (int i = 0; i < w.data.length; i++) {
                if (w.data[i] == EMPTY) continue;
                int comp = w.data[i].compareTo(x);

                if (comp > 0) { // i-th element is larger than x
                    w = w.children[i];
                    break;
                } else if (comp == 0) {  // find x
                    return w;
                } else { // comp < 0
                    if (i < w.data.length - 1 && w.data[i + 1] == EMPTY) {  // example: data: [EMPTY, "C", EMPTY]
                        w = w.children[i + 1];
                        break;
                    }
                    // if reach the last data of the node
                    if (i == w.data.length - 1) {
                        w = w.children[3];
                        break;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Searches the predecessor of x from node u
     *
     * @param u the node containing x
     * @param x the data to search
     * @return the predecessor node of x if successful, otherwise null
     */
    protected Node findPred(Node u, String x) {
        if (u == NIL || x == null || !containData(u, x)) return null;
        Node w = u, pred = NIL;
        for (int i = 0; i < w.data.length; i++) {
            if (w.data[i] == EMPTY) continue;
            int comp = w.data[i].compareTo(x);
            if (comp >= 0) { // i-th element is larger than x
                w = w.children[i];
                break;
            }
        }
        while (w != NIL) {
            pred = w;
            if (w.data[2] != EMPTY) {
                w = w.children[3];
            } else {
                w = w.children[2];
            }

        }
        return pred;
    }

    /**
     * Checkes if the node u is a leaf node
     *
     * @param u the node to check
     * @return true if node u is a leaf node, otherwise false
     */
    protected boolean isLeaf(Node u) {
        if (u.children[1] == NIL) return true;
        return false;
    }

    /**
     * Get the child index in parent children array
     *
     * @param c the child node
     * @return the child index in parent children array if successful, otherwise -1
     */
    protected int getChildIndex(Node c) {
        if (c == r) return -1;
        for (int i = 0; i < c.parent.children.length; i++) {
            if (c == c.parent.children[i]) return i;
        }
        return -1;
    }

    /**
     * Sorts the node data array including nulls
     *
     * @param u the node to sort
     */
    protected void arrSort(Node u) {
        Arrays.sort(u.data, new Comparator<String>() { // use custom comparator to be able to compare EMPTY
            @Override
            public int compare(String o1, String o2) {
                if (o1 == EMPTY && o2 == EMPTY) {
                    return 0;
                }
                if (o1 == EMPTY) {
                    return 1;
                }
                if (o2 == EMPTY) {
                    return -1;
                }
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * Clears the SSet, removing all elements from the set
     */
    public void clear() {
        r = NIL;
        n = 0;
    }
}
