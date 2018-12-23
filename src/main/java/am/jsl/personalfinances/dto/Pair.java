package am.jsl.personalfinances.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Pair class is used for storing parametrized key value pairs.
 * @author hamlet
 */
public class Pair<K, V> implements Serializable {
    private K key = null;
    private V value = null;

    /**
     * Default construct.
     */
    public Pair() {
    }

    /**
     * Constructs new Pairs with the given key and value.
     * @param key the parametrized key
     * @param value the parametrized value
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Getter for property 'key'.
     *
     * @return Value for property 'key'.
     */
    public K getKey() {
        return key;
    }

    /**
     * Setter for property 'key'.
     *
     * @param key Value to set for property 'key'.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Getter for property 'value'.
     *
     * @return Value for property 'value'.
     */
    public V getValue() {
        return value;
    }

    /**
     * Setter for property 'value'.
     *
     * @param value Value to set for property 'value'.
     */
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Pair other = (Pair) obj;
        return Objects.equals(this.key, other.key)
                && Objects.equals(this.value, other.value);
    }
}
