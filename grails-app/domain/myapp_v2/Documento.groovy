package myapp_v2

class Documento {

    Integer id
    String description

    static constraints = {
        id(unique: true)
        description(size: 3..20)
    }
}
