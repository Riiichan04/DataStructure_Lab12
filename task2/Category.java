package lab12.task2;

import lab12.task2.Magazine;

import java.util.*;
import java.util.stream.Collectors;

public class Category {
    private List<Book> bookList;
    public Category() {
        this.bookList = new LinkedList<>();
    }

    private void add(Book book, Book... other) {
        this.bookList.add(book);
        this.bookList.addAll(Arrays.asList(other));
    }

    //Câu 6
    public int sumMoney() {
        return this.bookList.stream().mapToInt(Book::getPrice).sum();
    }
    //Câu 7
    public Book maxChapter() {
        //Filter ReferenceBook
        return bookList.stream().filter(obj -> obj instanceof RefBook)
                //Compare Max Chapter of each RefBook
                .max(Comparator.comparingInt
                        //Get a Stream of Chapter in RefBook
                        (o -> ((RefBook) o).getChapters().stream()
                                //Get Chapter that have max page
                                .max(Comparator.comparingInt(Chapter::getChapterPage))
                                //Get Chapter class to compare
                                .get().getChapterPage()
                        )
                )
                //Get Book that have maximum Chapter::getChapterPage()
                .get();
    }

    //Câu 8
    public boolean isExistBookWithName(String name) {
        return bookList.stream().anyMatch(obj -> obj.getTitle().equals(name));
    }

    //Câu 9
    public boolean isExistMagazineWithName(String magazineName) {
        return bookList.stream().anyMatch(obj -> obj instanceof Magazine && obj.getTitle().equals(magazineName));
    }

    //Câu 10
    public void sortingCategory() {
        this.bookList.sort((o1, o2) -> (o1.getTitle().compareTo(o2.getTitle())) == 0 ? o2.getPublishYear() - o1.getPublishYear() : o1.getTitle().compareTo(o2.getTitle()));
    }

    //Câu 11
    public Map<Integer, Integer> mappingCategoryWithYear() {
        return this.bookList.stream().collect(Collectors.toMap(Book::getPublishYear, obj -> 1, Integer::sum));
    }

    public static void main(String[] args) {
        lab12.task2.Book b1 = new lab12.task2.Magazine("A", 100, 2021, "ABC", 5000, "AAAA");
        lab12.task2.Book b2 = new lab12.task2.Magazine("B", 100, 2022, "ABC", 5000, "BBBB");
        lab12.task2.Book b3 = new Magazine("C", 100, 2023, "BCD", 5000, "CCCC");

        lab12.task2.Chapter c1 = new lab12.task2.Chapter("S", 30);
        lab12.task2.Chapter c2 = new lab12.task2.Chapter("T", 400);
        lab12.task2.Chapter c3 = new lab12.task2.Chapter("U", 50);
        lab12.task2.Chapter c4 = new lab12.task2.Chapter("V", 20);
        lab12.task2.Chapter c5 = new lab12.task2.Chapter("W", 10);
        lab12.task2.Chapter c6 = new lab12.task2.Chapter("X", 60);

        lab12.task2.Book b4 = new lab12.task2.RefBook("C", 121, 2021, "BCD", 5000, "CCCC");
        lab12.task2.Book b5 = new lab12.task2.RefBook("E", 200, 2021, "BCD", 5000, "DDDD");
        lab12.task2.Book b6 = new lab12.task2.RefBook("F", 300, 2022, "BCD", 5000, "EEEE");
        b4.addChapter(c1, c2);
        b5.addChapter(c3, c4);
        b6.addChapter(c5, c6);

        lab12.task2.Category category = new lab12.task2.Category();
        category.add(b1, b2, b3, b4, b5, b6);

        //Only test using 100% Java 8
        //C6
        System.out.println(category.sumMoney());
        //C7
        System.out.println(category.maxChapter());
        //C8
        System.out.println(category.isExistBookWithName("S"));
        //C9
        System.out.println(category.isExistMagazineWithName("A"));
        //C10
        category.sortingCategory();
        System.out.println(Arrays.toString(category.bookList.toArray()));
        //C11
        System.out.println(category.mappingCategoryWithYear());

    }


}
