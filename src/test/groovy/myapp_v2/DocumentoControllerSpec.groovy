package myapp_v2

import grails.test.mixin.*
import spock.lang.*

@TestFor(DocumentoController)
@Mock(Documento)
class DocumentoControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.documentoList
            model.documentoCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.documento!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def documento = new Documento()
            documento.validate()
            controller.save(documento)

        then:"The create view is rendered again with the correct model"
            model.documento!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            documento = new Documento(params)

            controller.save(documento)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/documento/show/1'
            controller.flash.message != null
            Documento.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def documento = new Documento(params)
            controller.show(documento)

        then:"A model is populated containing the domain instance"
            model.documento == documento
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def documento = new Documento(params)
            controller.edit(documento)

        then:"A model is populated containing the domain instance"
            model.documento == documento
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/documento/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def documento = new Documento()
            documento.validate()
            controller.update(documento)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.documento == documento

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            documento = new Documento(params).save(flush: true)
            controller.update(documento)

        then:"A redirect is issued to the show action"
            documento != null
            response.redirectedUrl == "/documento/show/$documento.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/documento/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def documento = new Documento(params).save(flush: true)

        then:"It exists"
            Documento.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(documento)

        then:"The instance is deleted"
            Documento.count() == 0
            response.redirectedUrl == '/documento/index'
            flash.message != null
    }
}
