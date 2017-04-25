package domain.demo

import domain.demo.bootcamp.Author
import domain.demo.bootcamp.Book
import domain.demo.bootcamp.Employee
import domain.demo.bootcamp.Face
import domain.demo.bootcamp.Nose

class UtilController {

    def index() { }

    def manyToOne(){
        Nose nose=new Nose()
        Face face1= new Face(nose: nose)
        Face face2=new Face(nose: nose)
        face1.save()
        face2.save()
        render "done"
    }

    def oneToOne() {
        Face face=new Face(nose: new Nose()).save()
//        new Face(nose: Nose.get(1)).save(failOnError: true)
        render "done"
    }

    def deleteWithOneToOne() {
        Face f = Face.get(1L)
        f.delete(failOnError: true, flush: true)
        render "done"
    }

    def oneToMany() {
        new Author(name: "Stephen King")
                .addToBooks(new Book(title: "The Stand"))
                .addToBooks(new Book(title: "The Shining"))
                .save(flush: true)
        render "done"
    }

    def deleteOneToMany(){
        Author.get(1).delete(flush: true);
        render "done"
    }

    def manyToManyWithOwner() {
        new Author(name: "Stephen King")
                .addToBooks(new Book(title: "The Stand"))
                .addToBooks(new Book(title: "The Shining"))
                .save(flush: true)
        render "done"
    }

    def manyToManyWithOwned() {
        new Book(title: "Groovy in Action")
                .addToAuthors(new Author(name: "Dierk Koenig"))
                .addToAuthors(new Author(name: "Guillaume Laforge"))
                .save(flush: true)
        render "done"
    }

    def testConstraint() {
        Employee employee = new Employee(firstName: "Diksha", lastName: "Ahuja", email: "diksha.ahuja@tothenew.com",
                password: "diksha")
        employee.validate()
        employee.errors.allErrors.each {
            println "Errors" + it
        }
        render employee.hasErrors()
    }

    def saveFlow() {
        Employee employee = new Employee(firstName: "Diksha", lastName: "Ahuja", email: "diksha.ahuja@tothenew.com",
                password: "diksha")
        employee.save(flush: true)
        render employee

    }

}
