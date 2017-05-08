import java.util.List;

/**
 * Movie database that stores a list of MovieRecord and index trees on individual
 * fields of MovieRecord using IndexTree class.
 *
 * @author CS367
 */
public class MovieDb {

	// Store a list of MovieRecord.
	private List<MovieRecord> movies;

	// Root Node for title IndexTree.
	private IndexTreeADT<String, MovieRecord> titleIndexRoot;

	// Root Node for releaseYear IndexTree.
	private IndexTreeADT<String, MovieRecord> releaseYearIndexRoot;
	
	// Root Node for director IndexTree.
	private IndexTreeADT<String, MovieRecord> directorIndexRoot;

	// Root Node for rating IndexTree.
	private IndexTreeADT<String, MovieRecord> ratingIndexRoot;


	/**
	 * Constructs the MovieDB object so that it loads movies and builds all the
	 * IndexTrees using IndexTree class.
	 *
	 * @param movies
	 *            the movie record data stored in database.
	 */
	public MovieDb(List<MovieRecord> movies) {
		this.movies = movies;
		titleIndexRoot = new IndexTree<String, MovieRecord>();
		releaseYearIndexRoot = new IndexTree<String, MovieRecord>();
		directorIndexRoot = new IndexTree<String, MovieRecord>();
		ratingIndexRoot = new IndexTree<String, MovieRecord>();
		buildIndexTrees();
	}

	/**
	 * Returns the list of movies stored in the database.
	 * 
	 * @return the list of MovieRecord.
	 */
	public List<MovieRecord> getMovies() {
		return movies;
	}

	/**
	 * Returns the IndexTree specific for the corresponding index.
	 * 
	 * @param index
	 *            attribute for which IndexTree is to be retrieved.
	 * @return the IndexTree.
	 */
	public IndexTreeADT<String, MovieRecord> getIndexTree(String index) {
		if (index.equals(Config.TITLE))
			return titleIndexRoot;
		else if (index.equals(Config.RELEASE_YEAR))
			return releaseYearIndexRoot;
		else if (index.equals(Config.DIRECTOR))
			return directorIndexRoot;
		else if (index.equals(Config.RATING))
			return ratingIndexRoot;
		else
			throw new InvalidIndexException(index);
	}

	/**
	 * Creates four IndexTree one each for title, releaseYear, director and
	 * rating. Iterate over each movie record and add the key for each index
	 * (title, releaseYear, director, rating) to the respective IndexTree.
	 */
	private void buildIndexTrees() {
 		for (MovieRecord movie : movies) {
			titleIndexRoot.insert(movie.getTitle().toLowerCase(), movie);
			releaseYearIndexRoot.insert(movie.getReleaseYear(), movie);
			directorIndexRoot.insert(movie.getDirector().toLowerCase(), movie);
			ratingIndexRoot.insert(movie.getRating(), movie);
		}
	}

	/**
	 * Returns height of the index tree corresponding to the input index.
	 * 
	 * @param index
	 *            attribute for which index tree height is to be retrieved.
	 * @return the height.
	 * @throws InvalidIndexException
	 *            if index is invalid.
	 */
	public int getHeightIndexTree(String index) {
		if (index.equals(Config.TITLE))
			return titleIndexRoot.getHeight();
		else if (index.equals(Config.RELEASE_YEAR))
			return releaseYearIndexRoot.getHeight();
		else if (index.equals(Config.DIRECTOR))
			return directorIndexRoot.getHeight();
		else if (index.equals(Config.RATING))
			return ratingIndexRoot.getHeight();
		else
			throw new InvalidIndexException(index);
	}

	/**
	 * Returns number of nodes in the index tree corresponding to the
	 * input index.
	 * 
	 * @param index
	 *            name of index for which number of nodes is to be returned.
	 * @return the number of nodes in the IndexTree.
	 * @throws InvalidIndexException
	 *            if index is invalid.
	 */            
	public int getNumNodesIndexTree(String index) {
		if (index.equals(Config.TITLE))
			return titleIndexRoot.size();
		else if (index.equals(Config.RELEASE_YEAR))
			return releaseYearIndexRoot.size();
		else if (index.equals(Config.DIRECTOR))
			return directorIndexRoot.size();
		else if (index.equals(Config.RATING))
			return ratingIndexRoot.size();
		else
			throw new InvalidIndexException(index);
	}

	/**
	 * Returns average number of data values per node in the index tree
	 * corresponding to the input index.
	 * 
	 * @param index
	 * 		name of index tree for which average data count is to be returned.
	 * @return the average data count.
	 * @throws InvalidIndexException
	 *            if index is invalid.
	 */            
	public double getAvgDataCountIndexTree(String index) {
		if (index.equals(Config.TITLE))
			return titleIndexRoot.getAvgDataCount();
		else if (index.equals(Config.RELEASE_YEAR))
			return releaseYearIndexRoot.getAvgDataCount();
		else if (index.equals(Config.DIRECTOR))
			return directorIndexRoot.getAvgDataCount();
		else if (index.equals(Config.RATING))
			return ratingIndexRoot.getAvgDataCount();
		else
			throw new InvalidIndexException(index);
	}

	/**
	 * Returns string representation of the MovieDb in the form of statistics.
	 * DO NOT CHANGE THIS!
	 * 
	 * @return the string representation of stats.
	 */
	public String toString() {
		String indices[] = { Config.TITLE, Config.RELEASE_YEAR, Config.DIRECTOR, Config.RATING };
		String str = "[\n";
		str += "Total Number of Movie Records=" + movies.size() + "\n";
		for (int i = 0; i < indices.length; i++) {
			str += "Index=" + indices[i] + "\n";
			str += "\tNumber of nodes in tree=" + getNumNodesIndexTree(indices[i]) + "\n";
			str += "\tHeight of tree=" + getHeightIndexTree(indices[i]) + "\n";
			str += "\tAverage of data values in tree=" + getAvgDataCountIndexTree(indices[i]) + "\n";
		}
		str += "]\n";
		return str;
	}
}
