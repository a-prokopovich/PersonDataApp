package com.prokopovich.persondata.webapp.controller

import com.prokopovich.persondata.webapp.IntegrationTestSpecification
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PersonControllerErrorCaseIntegrationTest extends IntegrationTestSpecification {

    def "post request by /persons should return status 400 if got invalid data"() {

        given:
            def url = getMockUrl()

        when:
            def response = mockMvc.perform(MockMvcRequestBuilders.post("/persons")
                .content("{\"url\": https://\"" + url + "\"}")
                .contentType(MediaType.APPLICATION_JSON))

        then:
            response.andExpect(status().isBadRequest())
    }

    def "get request by /persons/{id} should return status 404 if person not found"() {
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.get("/persons/1")
                .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isNotFound())

    }

    def "put request by /persons/{id} should return status 400 if get invalid person"() {
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.put("/persons/{id}", 1)
                .content("""
                    {
                        "id":1,
                        "fullName":"Ivan Ivanov",
                        "phone":"80335873405",
                        "email":"ivan",
                        "passportData":{
                            "id": 1,
                            "passportNumber": "4",
                            "identificationNumber": "11"
                        }
                    }"""
                )
                .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isBadRequest())
    }
}
