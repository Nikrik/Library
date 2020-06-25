package krikun.rksi.library;

public class Book
{
    public int number;
    public String autor;
    public String name;
    public int year;
    public String publisher;
    public int pages;

    public Book(int number, String autor, String name, int year, String publisher, int pages)
    {
        this.set(number,autor,name,year,publisher,pages);
    }

    public void set(int number, String autor, String name, int year, String publisher, int pages)
    {
        this.number = number;
        this.autor = autor;
        this.name = name;
        this.year = year;
        this.publisher = publisher;
        this.pages = pages;
    }


}
