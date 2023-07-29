package pak;

import java.util.Objects;

public class Pair<K, V> {

	  private K key;
	    private V value;
	    public Pair(K key, V value) {
	        this.key = key;
	        this.value = value;
	    }
	    public K getKey() {
	        return key;
	    }
	    public V getValue() {
	        return value;
	    }
		public void setKey(K key) {
			this.key = key;
		}
		public void setValue(V value) {
			this.value = value;
		}
		@Override
		public int hashCode() {
			return Objects.hash(key, value);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			return Objects.equals(key, other.key) && Objects.equals(value, other.value);
		}
		@Override
		public String toString() {
			return "Pair [key=" + key + ", value=" + value + "]";
		}
	    

}
