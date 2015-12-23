package jos



import grails.test.mixin.*
import spock.lang.*

@TestFor(HouseController)
@Mock(House)
class HouseControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.houseInstanceList
            model.houseInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.houseInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def house = new House()
            house.validate()
            controller.save(house)

        then:"The create view is rendered again with the correct model"
            model.houseInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            house = new House(params)

            controller.save(house)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/house/show/1'
            controller.flash.message != null
            House.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def house = new House(params)
            controller.show(house)

        then:"A model is populated containing the domain instance"
            model.houseInstance == house
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def house = new House(params)
            controller.edit(house)

        then:"A model is populated containing the domain instance"
            model.houseInstance == house
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/house/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def house = new House()
            house.validate()
            controller.update(house)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.houseInstance == house

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            house = new House(params).save(flush: true)
            controller.update(house)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/house/show/$house.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/house/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def house = new House(params).save(flush: true)

        then:"It exists"
            House.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(house)

        then:"The instance is deleted"
            House.count() == 0
            response.redirectedUrl == '/house/index'
            flash.message != null
    }
}
