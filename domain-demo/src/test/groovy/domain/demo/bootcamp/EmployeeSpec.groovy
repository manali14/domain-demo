package domain.demo.bootcamp

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Employee)
class EmployeeSpec extends Specification {

    void "canary"() {
        expect: true
    }

    @Unroll("Executing #sno")
    void "test employee validations"() {
        setup:
        Employee employee = new Employee(firstName: fname, lastName: lname, email: email, password: password)
        when:
        Boolean result = employee.validate()

        then:
        result == valid
        where:
        sno | fname  | lname   | email            | password   | valid
        1   | ""     | "hello" | "a@b.com"        | "test123"  | false
        1   | "Test" | "hello" | "test123"        | "test123"  | false
        1   | "Test" | "hello" | "test@gmail.com" | "test1234" | true
    }

    def "Email address of employee should be unique"() {
        setup:
        String email = "test@tothenew.com"
        String password = 'password'
        Employee employee = new Employee(firstName: "Test", lastName: "Test", email: email, password: password)

        when:
        employee.save()
        then:
        employee.count() == 1

        when:
        Employee newEmployee = new Employee(firstName: "Test1", lastName: "Test1", email: email, password: password)
        newEmployee.save()

        then:
        Employee.count() == 1
        newEmployee.errors.allErrors.size() == 1
        newEmployee.errors.getFieldErrorCount('email') == 1
    }

    def "Get user full name"() {
        expect:
        new Employee(firstName: firstName, lastName: lastName).fullName == name

        where:
        firstName | lastName | name
        "Diksha"  | "Ahuja"  | "Diksha Ahuja"
        ""        | "Ahuja"  | "Ahuja"
        null      | "Ahuja"  | "Ahuja"
        "Diksha"  | ""       | "Diksha"
        "Diksha"  | null     | "Diksha"
        null      | null     | ""
    }

}
