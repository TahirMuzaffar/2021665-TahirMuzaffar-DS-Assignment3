import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object DS_EDA {
    def main(args: Array[String]): Unit = {
        // Initialize Spark Session
        val spark = SparkSession.builder
            .appName("EDA for Netflix TV shows & Movies dataset")
            .master("local[*]")
            .getOrCreate()

        // Load Dataset
        val netflixData = spark.read
            .option("header", "true")
            .option("inferSchema", "true")
            .csv("data/netflix_titles.csv")

        // Basic Schema and Overview
        println("Schema of Dataset:")
        netflixData.printSchema()

        println("First 10 Rows of Dataset:")
        netflixData.show(10)

        println("Total Records in Dataset:")
        println(s"Count: ${netflixData.count()}")

        // Save to Results Directory
        netflixData.printSchema()
        netflixData.show(10)

        // Save Null Value Counts for Each Column
        println("Null Value Counts by Column:")
        val nullCounts = netflixData.columns.map { col =>
            val nullCount = netflixData.filter(netflixData(col).isNull || netflixData(col) === "").count()
            (col, nullCount)
        }

        // Save as CSV to 'results' directory
        val nullCountDF = spark.createDataFrame(nullCounts).toDF("Column", "NullCount")
        nullCountDF.write.option("header", "true").csv("/home/project/results/null_value_counts.csv")

        // Distribution of Content Type
        println("Content Type Distribution:")
        val contentTypeDist = netflixData.groupBy("type").count()
        contentTypeDist.show()

        // Save Content Type Distribution
        contentTypeDist.write.option("header", "true").csv("/home/project/results/content_type_distribution.csv")

        // Top 10 Countries with the Most Content
        println("Top 10 Countries by Content Count:")
        val topCountries = netflixData.groupBy("country")
            .count()
            .orderBy(desc("count"))
        topCountries.show(10)

        // Save Top 10 Countries by Content Count
        topCountries.write.option("header", "true").csv("/home/project/results/top_countries.csv")

        // Distribution of Release Years
        println("Distribution of Release Years:")
        val releaseYearDist = netflixData.groupBy("release_year")
            .count()
            .orderBy(desc("release_year"))
        releaseYearDist.show(10)

        // Save Release Year Distribution
        releaseYearDist.write.option("header", "true").csv("/home/project/results/release_year_distribution.csv")

        // Most Common Genres
        println("Most Common Genres:")
        val mostCommonGenres = netflixData.groupBy("listed_in")
            .count()
            .orderBy(desc("count"))
        mostCommonGenres.show(10)

        // Save Most Common Genres
        mostCommonGenres.write.option("header", "true").csv("/home/project/results/most_common_genres.csv")

        // Count of Movies and TV Shows Added Each Year
        println("Movies and TV Shows Added by Year:")
        val addedByYear = netflixData.withColumn("year_added", year(to_date(col("date_added"), "MMMM d, yyyy")))
        val addedByYearDist = addedByYear.groupBy("year_added", "type")
            .count()
            .orderBy(desc("year_added"))
        addedByYearDist.show(10)

        // Save Movies and TV Shows Added by Year
        addedByYearDist.write.option("header", "true").csv("/home/project/results/added_by_year.csv")

        // Average Duration of Movies
        println("Average Duration of Movies:")
        val movies = netflixData.filter(col("type") === "Movie")
        val moviesWithDuration = movies.withColumn("duration", regexp_extract(col("duration"), "\\d+", 0).cast("int"))
        val avgDuration = moviesWithDuration.agg(avg("duration").alias("average_duration"))
        avgDuration.show()

        // Save Average Duration of Movies
        avgDuration.write.option("header", "true").csv("/home/project/results/average_duration.csv")

        // Top 10 Directors by Content Count
        println("Top 10 Directors by Content Count:")
        val topDirectors = netflixData.groupBy("director")
            .count()
            .orderBy(desc("count"))
        topDirectors.show(10)

        // Save Top 10 Directors by Content Count
        topDirectors.write.option("header", "true").csv("/home/project/results/top_directors.csv")

        // Top 10 Actors/Actresses by Appearances
        println("Top 10 Actors/Actresses by Appearances:")
        val topActors = netflixData.select(explode(split(col("cast"), ", ")).alias("actor"))
            .groupBy("actor")
            .count()
            .orderBy(desc("count"))
        topActors.show(10)

        // Save Top 10 Actors/Actresses by Appearances
        topActors.write.option("header", "true").csv("/home/project/results/top_actors.csv")

        // Stop Spark Session
        spark.stop()
    }
}