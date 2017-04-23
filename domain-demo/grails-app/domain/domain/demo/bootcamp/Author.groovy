package domain.demo.bootcamp

class Author {

    String name
    static hasMany = [books: Book]

    static constraints = {
    }
}
