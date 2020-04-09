//------------------------------------------------------------------------------------------------------------------------------
// Dictionary.java
// My Dictionary ADT.
// Tristan Clark
//------------------------------------------------------------------------------------------------------------------------------

public class Dictionary implements DictionaryInterface {

// private types and functions--------------------------------------------------------------------------------------------

    // Node
    private class Node {
        String key;
        String value;
        Node left;
        Node right;
        
        Node(String newKey, String newValue) {
            key = newKey;
            value = newValue;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int numPairs;

    // findKey()
    // returns the Node containing key k in the subtree rooted at R, or returns
    // NULL if no such Node exist
    private Node findKey(Node R, String k) {
        if(R==null || k.compareTo(R.key)==0) return R;
        if(k.compareTo(R.key)<0) 
            return findKey(R.left, k);
        else 
            return findKey(R.right, k);
    }

    // findParent()
    // returns the parent of N in the subtree rooted at R, or returns NULL 
    // if N is equal to R. (pre: R != NULL)
    private Node findParent(Node N, Node R){
        Node P = null;
        if( N!=R ){
            P = R;
            while( P.left!=N && P.right!=N ){
                if(N.key.compareTo(P.key)<0)
                    P = P.left;
                else
                    P = P.right;
            }
        }
        return P;
    }

    // findLeftmost()
    // returns the leftmost Node in the subtree rooted at R, or NULL if R is NULL.
    private Node findLeftmost(Node R){
        Node L = R;
        if( L!=null ) for( ; L.left!=null; L=L.left);
        return L;
    }

    // printInOrder()
    // prints the (key, value) pairs belonging to the subtree rooted at R in order
    // of increasing keys to file pointed to by out.
    private static void printInOrder(StringBuffer sb, Node R){
        if( R!=null ){
            printInOrder(sb, R.left);
            sb.append(R.key).append(" ").append(R.value).append("\n");
            printInOrder(sb, R.right);
        }
    }

// public functions---------------------------------------------------------------------------------------------

    // Dictionary()
    // constructor for the Dictionary type
    public Dictionary(){
        root = null;
        numPairs = 0;
    }

    // isEmpty()
    // returns 1 (true) if D is empty, 0 (false) otherwise
    // pre: none
    public boolean isEmpty() { return(numPairs==0); }

    // size()
    // pre: none
    // returns the number of entries in this Dictionary
    public int size() { return numPairs; }

    // lookup()
    // pre: none
    // returns value associated key, or null reference if no such key exists
    public String lookup(String key) {
        Node N = findKey(root, key);
        return ( N==null ? null : N.value );
    }

    // insert()
    // inserts new (key,value) pair into this Dictionary
    // pre: lookup(key)==null
    public void insert(String key, String value) throws DuplicateKeyException {
        if(lookup(key)==null) {
            Node N, A, B;
            N = new Node(key, value);
            B = null;
            A = root;
            while( A!=null ){
                B = A;
                if(key.compareTo(A.key)<0) A = A.left;
                else A = A.right;
            }
            if( B==null ) root = N;
            else if( key.compareTo(B.key)<0 ) B.left = N;
            else B.right = N;
            numPairs++;
        } else {
            throw new DuplicateKeyException("cannot insert duplicate keys");
        }
    }

    // delete()
    // deletes pair with the given key
    // pre: lookup(key)!=null
    public void delete(String key) throws KeyNotFoundException {
        if(lookup(key)!=null) {
            Node N, P, S;
            N = findKey(root, key);
            if( N.left==null && N.right==null ){  // case 1 (no children)
                if( N==root ){
                    root = null;
                }else{
                    P = findParent(N, root);
                    if( P.right==N ) P.right = null;
                    else P.left = null;
                }
            }else if( N.right==null ){  // case 2 (left but no right child)
                if( N==root ){
                    root = N.left;
                }else{
                    P = findParent(N, root);
                    if( P.right==N ) P.right = N.left;
                    else P.left = N.left;
                }
            }else if( N.left==null ){  // case 2 (right but no left child)
                if( N==root ){
                    root = N.right;
                }else{
                    P = findParent(N, root);
                    if( P.right==N ) P.right = N.right;
                    else P.left = N.right;
                }
            }else{                     // case3: (two children: N->left!=NULL && N->right!=NULL)
                S = findLeftmost(N.right);
                N.key = S.key;
                N.value = S.value;
                P = findParent(S, N);
                if( P.right==S ) P.right = S.right;
                else P.left = S.right;
            }
            numPairs--;
        } else {
            throw new KeyNotFoundException("cannot delete non-existent key");
        }
    }

    // makeEmpty()
    // pre: none
    public void makeEmpty() {
        root = null;
        numPairs = 0;
    }

    // toString()
    // returns a String representation of this Dictionary
    // overrides Object's toString() method
    // pre: none
    public String toString() {
        StringBuffer sb = new StringBuffer();
        printInOrder(sb, root);
        return new String(sb);
    }
}