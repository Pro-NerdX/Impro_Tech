package logic;

public class Pair<U, V> {
    
    public U first;
    public V second;

    public Pair(final U first, final V second) {
        this.first = first;
        this.second = second;
    }

    public Pair<V, U> getFliped() {
        final Pair<V, U> res = new Pair<>(this.second, this.first);
        return res;
    }

    public void setFirst(final U first) {
        this.first = first;
    }

    public void setSecond(final V second) {
        this.second = second;
    }
}
