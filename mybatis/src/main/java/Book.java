import jdk.nashorn.internal.objects.annotations.Getter;

public class Book {
    int ID;
    String name;
    String title;
    int year;


    public int getPrice() {
        return year;
    }

    public void setPrice(int price) {
        this.year = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}
