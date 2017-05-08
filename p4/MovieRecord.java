/**
 * Contains data about a movie record.
 *
 * @author CS367
 */
public class MovieRecord {

	// Attributes that describe a MovieRecord.
	private String title;
	private String releaseYear;
	private String director;
	private String rating;

	// A movie can have more than one genre and they are separated by
	// pipe(|) symbol.
	private String genre;

	// A movie can have more than one actor and they are separated by
	// pipe(|) symbol.
	private String cast;

	// Language of the movie.
	private String lang;

	/**
	 * Constructs the MovieRecord object with the given attributes.
	 *
	 * @param title
	 *            title of the movie.
	 * @param releaseYear
	 *            year the movie was released.
	 * @param director
	 *            director of the movie.
	 * @param rating
	 *            average rating of the movie.
	 * @param genre
	 *            genre of the movie.
	 * @param cast
	 *            names of actors.
	 * @param lang
	 *            language of the movie.
	 */
	public MovieRecord(String title, String releaseYear, String director,
			String rating, String genre, String cast, String lang) {
		this.title = title;
		this.releaseYear = releaseYear;
		this.director = director;
		this.rating = rating;
		this.genre = genre;
		this.cast = cast;
		this.lang = lang;
	}

	/**
	 * Returns the title of the movie.
	 * 
	 * @return title of the movie.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the director of the movie.
	 * 
	 * @return director of the movie.
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Returns the year of release of the movie.
	 * 
	 * @return releaseYear of the movie.
	 */
	public String getReleaseYear() {
		return releaseYear;
	}

	/**
	 * Returns the rating of the movie
	 * 
	 * @return rating of the movie.
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * Returns the actors of the movie
	 * 
	 * @return cast of the movie.
	 */
	public String getCast() {
		return cast;
	}

	/**
	 * Returns the genre of the movie.
	 * 
	 * @return genre of the movie.
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Returns the language of the movie.
	 * 
	 * @return language of the movie.
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * Returns the appropriate field of the movie record.
	 * 
	 * @param attribute
	 *            field name of the movie to be retrieved.
	 * @throws UnknownMovieRecordAttributeException
	 *             if invalid fieldName
	 * @return field of the movie that matches the param fieldName.
	 */
	public String getValByAttribute(String attribute) {
		switch (attribute) {
		case Config.GENRE:
			return genre;
		case Config.CAST:
			return cast;
		case Config.RATING:
			return rating;
		case Config.RELEASE_YEAR:
			return releaseYear;
		case Config.DIRECTOR:
			return director;
		case Config.TITLE:
			return title;
		case Config.LANG:
			return lang;
		default:
			throw new UnknownMovieRecordAttributeException(attribute);
		}
	}

	/**
	 * Returns a string representation of movie record.
	 * 
	 * @return the string representation.
	 */
	@Override
	public String toString() {
		String result = "\tTitle: " + title + "\n\tDirector: " +
						director + "\n\tYear: " + releaseYear +
						"\n\tRating: " + rating + "\n\tCast: " +
						cast + "\n\tGenre: " + genre + "\n\tLang: " + lang;
		return result;
	}
}
