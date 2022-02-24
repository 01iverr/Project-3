public class Listt {
    private String[] heap;
    private int size;//size of List

    private static final int DEFAULT_CAPACITY = 4; // default capacity
    private static final int AUTOGROW_SIZE = 4; // default auto grow

    public Listt() {
        this.heap = new String[DEFAULT_CAPACITY + 1];//we start adding elements from 1
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(String x) {
		if (size > heap.length * 0.75f) {//if 75% is full grow
            resize();
        }
        heap[++size] = x;
        swim(size);//for the heap property to be valid
    }
	public boolean search(String id){
		if (isEmpty())
            return false;
        for(int d=1; d<=size; d++){
            if (heap[d] == id){
                return true;
            }
        }
	return false;		
	}
    public String remove(String id) {
        if (isEmpty())
            return null;
        int fid=-1;
        for(int d=1; d<=size; d++){
            if (heap[d] == id){
                fid = d;
                break;
            }
        }
        if(fid == -1)
            return null;
        String element = heap[fid];//keep element with pointer id
        size--;//correct the size
        if (fid == size)
            return element;
        heap[fid] = heap[size];//make id equal last
        sink(fid);//for the heap property to be valid
		remove(id);
        return element;
    }

    private void resize() {
        String[] newHeap = new String[heap.length + AUTOGROW_SIZE];
        for (int j = 0; j <= size; j++) {
            newHeap[j] = heap[j];//copy elements of heap in newHeap
        }
        heap = newHeap;
    }

    private void swim(int j) {
        if (j == 1)//it is the root
            return;
        while (j > 1 && heap[j / 2].compareTo(heap[j]) < 0) {//if bigger than father
            swap(j, j / 2);//swap
            j = j / 2;
        }
    }

    private void sink(int j) {
        int left = 2 * j;//left child
        int right = left + 1;//right child
        if (left > size)
            return;

        while (left <= size) {
            int max = left;
            if (right <= size) {//define bigger child
                if (heap[left].compareTo(heap[right]) < 0)
                    max = right;
            }
            if (heap[j].compareTo(heap[max]) > 0)//if the heap property is valid
                return;
            else {
                //child smaller than father -> swap
                swap(j, max);
                j = max;
                left = j * 2;
                right = left + 1;
            }
        }

    }

    public void swap(int k, int l) {
        //swap elements k and l
        String temp = heap[k];
        heap[k] = heap[l];
        heap[l] = temp;
    }
    public String getElement(int m){
        return heap[m];
    }

}