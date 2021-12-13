


public class CollectionFacadeSet extends java.lang.Object implements SimpleSet {

	protected java.util.Collection<java.lang.String> collection;

	CollectionFacadeSet(java.util.Collection<java.lang.String> collection){
		this.collection = collection;
	}


	@Override
	public boolean add(String newValue) {
		if (!contains(newValue)){
			collection.add(newValue);
			return true;
		}
		return false;
	}


	@Override
	public boolean contains(String searchVal) {
		return collection.contains(searchVal);
	}

	@Override
	public boolean delete(String toDelete) {
		if (contains(toDelete)){
			collection.remove(toDelete);
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return collection.size();
	}
}
