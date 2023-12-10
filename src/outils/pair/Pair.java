package outils.pair;

import java.util.Objects;

public class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return this.first;
    }

    public B getSecond() {
        return this.second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + this.first +
                ", second=" + this.second +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Pair pair = (Pair) obj;
        return first == pair.getFirst() && second == pair.getSecond();
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
