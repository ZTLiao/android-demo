package org.android.spdy;

public class NetSparseArray implements Cloneable {
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;
    private static Object DELETED;

    static {
        NetSparseArray.DELETED = new Object();
    }

    public NetSparseArray() {
        this(10);
    }

    public NetSparseArray(int p0) {
        super();
        this.mGarbage = false;
        this.mKeys = new int[p0];
        this.mValues = new Object[p0];
        this.mSize = 0;
    }

    private static int binarySearch(int[] p0, int p1, int p2, int p3) {
        int i1;
        p2 = p2 + p1;
        int i = p1 - 1;
        p1 = p2;
        while ((i1 = p1 - i) > 1) {
            i1 = p1 + i;
            i1 = i1 / 2;
            if (p0[i1] < p3) {
                i = i1;
            } else {
                p1 = i1;
            }
        }
        if (p1 == p2) {
            return (~p2);
        }
        if (p0[p1] == p3) {
            return p1;
        }
        return (~p1);
    }

    private void gc() {
        Object oobject;
        int tmSize = this.mSize;
        int[] tmKeys = this.mKeys;
        Object[] tmValues = this.mValues;
        int i1 = 0;
        int i2 = 0;
        while (i1 < tmSize) {
            if ((oobject = tmValues[i1]) != NetSparseArray.DELETED) {
                if (i1 != i2) {
                    tmKeys[i2] = tmKeys[i1];
                    tmValues[i2] = oobject;
                    tmValues[i1] = null;
                }
                i2 = i2 + 1;
            }
            i1 = i1 + 1;
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public void append(int p0, Object p1) {
        int tmSize;
        int i = 0;
        if ((tmSize = this.mSize) != 0 && p0 <= this.mKeys[(tmSize - 1)]) {
            this.put(p0, p1);
            return;
        } else if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
        }
        tmSize = this.mSize;
        int[] tmKeys = this.mKeys;
        if (tmSize >= tmKeys.length) {
            int i1 = tmSize + 1;
            int[] ointArray = new int[i1];
            System.arraycopy(tmKeys, i, ointArray, i, tmKeys.length);
            Object[] objArray1 = new Object[i1];
            Object[] tmpMValues = this.mValues;
            System.arraycopy(tmpMValues, i, objArray1, i, tmpMValues.length);
            this.mKeys = ointArray;
            this.mValues = objArray1;
        }
        this.mKeys[tmSize] = p0;
        this.mValues[tmSize] = p1;
        this.mSize = tmSize + 1;
    }

    public void clear() {
        int i = 0;
        int tmSize = this.mSize;
        Object[] tmValues = this.mValues;
        for (int i1 = 0; i1 < tmSize; i1 = i1 + 1) {
            tmValues[i1] = null;
        }
        this.mSize = i;
        this.mGarbage = false;
    }

    public void delete(int p0) {
        int i = 0;
        if ((p0 = NetSparseArray.binarySearch(this.mKeys, i, this.mSize, p0)) >= 0) {
            Object[] tmValues = this.mValues;
            Object dELETED = NetSparseArray.DELETED;
            if (tmValues[p0] != dELETED) {
                tmValues[p0] = dELETED;
                this.mGarbage = true;
            }
        }
    }

    public Object get(int p0) {
        return this.get(p0, null);
    }

    public Object get(int p0, Object p1) {
        int i = 0;
        if ((p0 = NetSparseArray.binarySearch(this.mKeys, i, this.mSize, p0)) >= 0) {
            Object[] tmValues = this.mValues;
            if (tmValues[p0] != NetSparseArray.DELETED) {
                return tmValues[p0];
            }
        }
        return p1;
    }

    public int indexOfKey(int p0) {
        int i = 0;
        if (this.mGarbage) {
            this.gc();
        }
        return NetSparseArray.binarySearch(this.mKeys, i, this.mSize, p0);
    }

    public int indexOfValue(Object p0) {
        int i = 0;
        if (this.mGarbage) {
            this.gc();
        }
        while (true) {
            if (i >= this.mSize) {
                return -1;
            }
            if (this.mValues[i] == p0) {
                break;
            } else {
                i = i + 1;
            }
        }
        return i;
    }

    public int keyAt(int p0) {
        if (this.mGarbage) {
            this.gc();
        }
        return this.mKeys[p0];
    }

    public void put(int p0, Object p1) {
        Object[] objArray;
        int i2;
        Object[] tmValues;
        int i = 1;
        int i1 = 0;
        if ((i2 = NetSparseArray.binarySearch(this.mKeys, i1, this.mSize, p0)) >= 0) {
            this.mValues[i2] = p1;
            return;
        } else if ((i2 = ~i2) < this.mSize) {
            tmValues = this.mValues;
            if (tmValues[i2] == NetSparseArray.DELETED) {
                this.mKeys[i2] = p0;
                tmValues[i2] = p1;
                return;
            }
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
            i2 = ~NetSparseArray.binarySearch(this.mKeys, i1, this.mSize, p0);
        }
        int tmSize = this.mSize;
        int[] tmKeys = this.mKeys;
        if (tmSize >= tmKeys.length) {
            int i3 = tmSize + 20;
            int[] ointArray = new int[i3];
            objArray = new Object[i3];
            System.arraycopy(tmKeys, i1, ointArray, i1, tmKeys.length);
            tmValues = this.mValues;
            System.arraycopy(tmValues, i1, objArray, i1, tmValues.length);
            this.mKeys = ointArray;
            this.mValues = objArray;
        }
        tmSize = this.mSize;
        if ((tmSize - i2) != 0) {
            int i4 = i2 + 1;
            System.arraycopy(this.mKeys, i2, this.mKeys, i4, (tmSize - i2));
            System.arraycopy(this.mValues, i2, this.mValues, i4, (this.mSize - i2));
        }
        this.mKeys[i2] = p0;
        this.mValues[i2] = p1;
        this.mSize = this.mSize + i;
    }

    public void remove(int p0) {
        this.delete(p0);
    }

    public void removeAt(int p0) {
        Object[] tmValues = this.mValues;
        Object dELETED = NetSparseArray.DELETED;
        if (tmValues[p0] != dELETED) {
            tmValues[p0] = dELETED;
            this.mGarbage = true;
        }
    }

    public void setValueAt(int p0, Object p1) {
        if (this.mGarbage) {
            this.gc();
        }
        this.mValues[p0] = p1;
    }

    public int size() {
        if (this.mGarbage) {
            this.gc();
        }
        return this.mSize;
    }

    public void toArray(Object[] p0) {
        for (int i = 0; i < p0.length; i = i + 1) {
            p0[i] = this.mValues[i];
        }
    }

    public Object valueAt(int p0) {
        if (this.mGarbage) {
            this.gc();
        }
        return this.mValues[p0];
    }
}