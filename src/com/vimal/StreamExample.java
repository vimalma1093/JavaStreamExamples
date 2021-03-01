package com.vimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamExample {

    public static void main(String[] args) {
    System.out.println("vimal here");
    StreamExample se = new StreamExample();
    se.printExamples();

}

    public void printExamples(){
        Theatre columbia = new Theatre("zAMC", "Columbia");
        Theatre columbia1 = new Theatre("GrandCentral", "Columbia");
        Theatre chennai = new Theatre("Satyam", "Chennai");
        Theatre mumbai = new Theatre("PVR", "Mumbai");
        Theatre mumbai1 = new Theatre("PVRAlto", "Mumbai");
        Theatre dallas = new Theatre("AMCDallas", "Dallas");
        Theatre milan = new Theatre("Loem", "Milan");

        List<Movie> movieList = Arrays.asList(
                new Movie("Lupin",columbia,2010,1000),
                new Movie("Billa", chennai,2011,200),
                new Movie("vanished", columbia1,2013,2000),
                new Movie("vantagepoint", mumbai,2014,7890),
                new Movie("ava",mumbai1,2011,1000),
                new Movie("Mollys Game",dallas,2020,1000),
                new Movie("Crown",milan,2018,1000)
        );

        //Find all movies in the year 2011 and sort them by collection (small to high).
        List<String> m1 = movieList.stream()
                .filter(m -> m.getYear() == 2011)
                .sorted(Comparator.comparing(Movie::getCollection))
                .map(m -> m.getName())
                .collect(Collectors.toList());
        System.out.println("movies in the year 2011 and sort them by collection (small to high): ");
        m1.forEach(System.out::println);

        // Unique cities having a Theatre
        List<String> m2 = movieList.stream()
                .map(m -> m.getTheatre().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Unique cities having a Theatre");
        m2.forEach(System.out::println);

        //Finds all Theatres from Columbia and sort them by name

        List<String> columbiaTheatres
                = movieList.stream()
                .filter(m -> m.getTheatre().getCity() == "Columbia")
                .map(m -> m.getTheatre().getName())
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Theatres from Columbia");
        columbiaTheatres.forEach(System.out::println);

        //Returns a string of all Theatre’ names sorted alphabetically and concatenate to one string
        String theathreNamesSorted
                =  movieList.stream()
                .map(m -> m.getTheatre().getName())
                .sorted().reduce("", (name1, name2) -> name1 + " " + name2);
        System.out.println("All Theathre names: " + theathreNamesSorted);

        //Are any Theatres based in Milan?
        boolean milanTheatre = movieList.stream()
                .anyMatch( m -> m.getTheatre().getCity().equals("Milan"));
        System.out.println("Any theatre in Milan: " + milanTheatre);

        //Prints total Movies’ collections from the theatre present in Columbia
        int totalCollection =
                movieList.stream()
                .filter(m -> m.getTheatre().getCity().equals("Columbia"))
                .map(Movie::getCollection)
                .reduce(0, (n1,n2)-> n1+n2);
        System.out.println("Collection for theatres in Columbia: " + totalCollection);

        //What’s the highest collected Movie?
        Optional<String> maxOptional
                = movieList.stream()
                .max(Comparator.comparing(Movie::getCollection))
                .map(Movie::getName);
        System.out.println("Max Collected Movie: " + maxOptional.get());

        //What’s the lowest collected Movie? . Instead of min, I am using reduce.
        Optional<Movie> minOptional
                = movieList.stream()
                .reduce( (a, b) -> a.getCollection() < b.getCollection() ? a : b);
        System.out.println("Min Collected Movie: " + minOptional.get().getName());

        // Prior to Java 8
        List<String> output = new ArrayList<>();
        for(Movie m: movieList){
            if(m.getTheatre().getCity() == "Columbia"){
                output.add(m.getName());
            }
        }
        for(String s: output){
            System.out.println(s);
        }

        // With Java 8
        movieList.stream()
                .filter(m -> m.getTheatre().getCity() == "Columbia")
                .map(Movie::getName)
                .forEach(System.out::println);


    }
}

//Trader
class Theatre{
    private final String name;
    private final String city;
    public Theatre(String name, String city){
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}

//Transactions
class Movie{
    private final String name;
    private final Theatre theatre;
    private final int year;
    private final int collection;

    public Movie(String name, Theatre theatre, int year, int collection) {
        this.name = name;
        this.theatre = theatre;
        this.year = year;
        this.collection = collection;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public int getYear() {
        return year;
    }

    public int getCollection() {
        return collection;
    }

    public String getName() {
        return name;
    }

}