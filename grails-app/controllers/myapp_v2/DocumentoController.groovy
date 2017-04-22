package myapp_v2

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DocumentoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Documento.list(params), model:[documentoCount: Documento.count()]
    }

    def show(Documento documento) {
        respond documento
    }

    def create() {
        respond new Documento(params)
    }

    @Transactional
    def save(Documento documento) {
        if (documento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (documento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond documento.errors, view:'create'
            return
        }

        documento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'documento.label', default: 'Documento'), documento.id])
                redirect documento
            }
            '*' { respond documento, [status: CREATED] }
        }
    }

    def edit(Documento documento) {
        respond documento
    }

    @Transactional
    def update(Documento documento) {
        if (documento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (documento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond documento.errors, view:'edit'
            return
        }

        documento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'documento.label', default: 'Documento'), documento.id])
                redirect documento
            }
            '*'{ respond documento, [status: OK] }
        }
    }

    @Transactional
    def delete(Documento documento) {

        if (documento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        documento.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'documento.label', default: 'Documento'), documento.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documento.label', default: 'Documento'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
