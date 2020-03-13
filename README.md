# Movie Recommendation System using Java AWT, MySQL and JDBC
In this program, we take three to five favorite movies as an input from the user. Then we find the similarities among the movie attributes. We take the movie's Actors, Director, Studio, Genre, Year, Duration, and Rate into consideration. We construct a content-based recommendation system. We count the occurrence of each attribute value among the three movies. Then we pick the ones with the highest count.

## Data Collection and Preprocessing
In this project, we collected data from IMDb [1] Interface. The data is composed of 7 tables with a total of 35 attributes ranging from the name of movies , movie and cast identifiers, year, categories, etc. We selected some attributes in later algorithms, but also did some pre-selection on tables. 
We chose the tables title.akas, title.basics, title.crew, title.principles, title.ratings, and joined the tables together to make the later queries easier. The data format from the official document is .tsv file, and we used Python to do the preprocessing. Pandas, pyMySQL, and SQLAlchemy packages were used in the processing.
First we use pandas to read in the text from .tsv files, and then select the needed attributes from the DataFrame. After getting the DataFrame, we use the tool from SQLAlchemy in Python to import the DataFrame into MySQL database, and the linkage from Python to MySQL is achieved by SQLAlchemy.


### Team Members:
1. Rana Alshemali
2. Yang Shi 
3. Lei Xian
