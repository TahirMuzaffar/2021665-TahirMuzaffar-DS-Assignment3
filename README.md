# CS439 - Assignment 3
# 2021665 - Tahir Muzaffar
# Netflix EDA with Apache Spark

This repository contains the implementation of Exploratory Data Analysis (EDA) on the **Netflix TV Shows & Movies dataset** using **Apache Spark** with **Scala**. The goal is to analyze the dataset and generate various insights such as content distribution, genre analysis, and more. The project runs in a **Dockerized** environment, using Apache Spark to perform the data processing.

## Setup Instructions

### Prerequisites

- **Docker Desktop** installed on your system (Windows/Linux/macOS).
- **VS Code** or any preferred code editor.
- Clone this repository to your local machine using the following command:
   ```bash
   git clone https://github.com/your-username/netflix-eda-spark.git
   cd netflix-eda-spark
   ```

## How to Run the Project
   
1. **Create a Docker Image for Spark:**
   You need to build the Docker image with Apache Spark, Scala, and SBT installed. The repository includes a Dockerfile that defines the environment setup.
   Run the following command to build the Docker image:
   ```bash
   docker build -t ds-eda .
   ```
   
2. **Download the Netflix Dataset:**
   Download the dataset from [here]([readme.com](https://www.kaggle.com/datasets/shivamb/netflix-shows)) or use your own version of the netflix_titles.csv dataset. After downloading, place the netflix_titles.csv     file in the data directory of your project.

3. **Running the Docker Container:**
   Once the Docker image is built successfully, you can run the container using the following command:
   ```bash
   docker run -it --rm -v /path/to/your/project:/home/project ds-eda
   ```
   • /path/to/your/project should be replaced with the path to the project on your local machine.
   • This command mounts your local project directory to the /home/project directory inside the container.

4. **Set Up Scala Project with SBT:**
   Inside the running Docker container, you need to navigate to the project directory where the Scala code (DS_EDA.scala) is located. Use SBT to compile and run the Scala project:
   ```bash
   sbt compile
   sbt run
   ```
   This will execute the EDA and produce the output which will be saved in the results directory within the container. You can access this directory by mounting your local project folder when running the             container. The results will be available under the results folder on your local machine.

## Results

- **Null Value Counts:** Null values for each column saved in `results/null_value_counts.csv`. This CSV file contains the count of null or missing values for each column in the dataset.
- **Content Type Distribution:** Results saved in `results/content_type_distribution.csv`. This CSV file provides the breakdown of how many Movies vs. TV Shows are present in the dataset.
- **Top 10 Countries with Most Content:** Results saved in `results/top_countries.csv`. This CSV file lists the top 10 countries with the highest number of Netflix titles (Movies and TV Shows).
- **Distribution of Release Years:** Results saved in `results/release_year_distribution.csv`. This CSV file shows the count of titles released each year, displaying trends over time.
- **Most Common Genres:** Results saved in `results/most_common_genres.csv`. This CSV file contains the most common genres in the dataset, highlighting frequently occurring genres.
- **Movies and TV Shows Added by Year:** Results saved in `results/added_by_year.csv`. This CSV file shows the breakdown of Movies and TV Shows added to Netflix each year.
- **Average Duration of Movies:** Results saved in `results/average_duration.csv`. This CSV file contains the average duration of movies in the dataset, calculated from the `duration` column.
- **Top 10 Directors by Content Count:** Results saved in `results/top_directors.csv`. This CSV file lists the top 10 directors with the highest number of movies or TV shows in the dataset.
- **Top 10 Actors/Actresses by Appearances:** Results saved in `results/top_actors.csv`. This CSV file shows the top 10 actors and actresses based on the number of appearances in the dataset.

## Any Additional Notes

- **Docker Volumes:** The results will be available on your local machine in the `results` directory. Make sure to map the volumes correctly when running the container to ensure the results are saved in the appropriate location.
- **Dataset Modifications:** If you want to modify the dataset (e.g., cleaning missing values, transforming data), you can do so inside the `DS_EDA.scala` file before running the project. Any transformations or preprocessing steps can be added in this file.
- **Scaling Up:** For larger datasets or more complex Spark operations, you may want to use a more powerful cluster setup. Docker is primarily for local development and testing, and using a Spark cluster or cloud service would be more suitable for handling large-scale data processing tasks.
