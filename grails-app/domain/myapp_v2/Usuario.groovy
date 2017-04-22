package myapp_v2

class Usuario {
    String documentNumber
    String firstName
    String lastName
    String phone
    String email
    String plateNumber
    Documento documento

    //static hasMany = [documentos: Documento]

    static constraints = {
        documentNumber size: 8..20, unique: true
        firstName size: 5..50
        lastName size: 5..50
        phone size: 9..11
        email email:true, unique: true
        plateNumber size: 5..10
    }
}
