package app.roque.moviesfeed.movies;

public class Movies {
    private String title;
    private String country;

    public Movies(){}

    public Movies(String title, String country) {
        this.title = title;
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
