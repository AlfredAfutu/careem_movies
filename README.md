#CareemMovies (mvvm architecture)

#Functionality
- Fetches the data from the http://docs.themoviedb.apiary.io/ API to display latest movies on the main screen
- User click on any movie item displays a view to see the basic details of the movie.
- A filter icon at the top right of the main screen to filter the movies by date (in the format yyyy-MM-dd).
- An infinite scroll on the main screen to browse more movies.

#Testing
- MovieEntityTest to test Movie table data access objects using AndroidJUnitRunner.
- MovieListActivityTest to unit test MovieListActivity UI using Espresso.
- MoviesViewModelTest to test MovieViewModel class using Roboelectric.

