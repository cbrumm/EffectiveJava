import java.util.HashMap;
import java.util.Map;

/**
 * Implements typesafe collection of one favorite instance per class.
 * Typesafe heterogeneous container pattern example from Item 29 of
 * Effective Java, 2nd edition.
 *
 */
public class Favorites {
    // The internal map cannot enforce type safety, only the methods can.
    private final Map<Class<?>, Object> favorites = new HashMap<Class<?>, Object>();

    /** Put your favorite instance of the passed type into the container. */
    public <T> void putFavorite(Class<T> type, T instance) {
	if (type == null) {
	    throw new NullPointerException("Passed type cannot be null.");
	}
	favorites.put(type, instance);
    }

    /** Retrieve your favorite instance of the passed type from the container. */
    public <T> T getFavorite(Class<T> type) {
	// This cast may never fail as the methods maintain the invariant.
	return type.cast(favorites.get(type));
    }

    public static void main(String[] args) {
	Favorites favorites = new Favorites();
	favorites.putFavorite(Integer.class, new Integer(42));
	favorites.putFavorite(String.class, "Hello World!");
	System.out.println("Favorite Integer:" + favorites.getFavorite(Integer.class));
	System.out.println("Favorite String:" + favorites.getFavorite(String.class));
    }
}