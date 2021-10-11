package com.prokopovich.persondata.webapp.controller

import com.prokopovich.persondata.domain.model.Person
import com.prokopovich.persondata.webapp.IntegrationTestSpecification
import org.mockserver.model.Header
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import static org.mockserver.model.HttpResponse.response
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

class PersonControllerSuccessCaseIntegrationTest extends IntegrationTestSpecification {

    def "get request by /persons should return persons list in success case"() {
        given:
            insertTestData()

        when:
            def response = mockMvc.perform(MockMvcRequestBuilders.get("/persons")
                .contentType(MediaType.APPLICATION_JSON))

        then:
            response.andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                            "id":1,
                            "fullName":"Ivan Ivanov",
                            "phone":"80335873405",
                            "email":"ivan@gmail.com",
                            "passportData":{
                                "id": 1,
                                "passportNumber": "HB3452111",
                                "identificationNumber": "1234564A001PB5"
                            }
                        },
                        {
                            "id":2,
                            "fullName":"Peter Petrov",
                            "phone":"80335873111",
                            "email":"peter@gmail.com",
                            "passportData":{
                                "id": 2,
                                "passportNumber": "HB3452758",
                                "identificationNumber": "495564A001PB5"
                            }
                        }]"""))
    }

    def "get request by /persons should return message if persons list is empty"() {

        when:
            def response = mockMvc.perform(MockMvcRequestBuilders.get("/persons")
                .contentType(MediaType.APPLICATION_JSON))

        then:
            response.andExpect(status().isOk())
                .andExpect(content().json(
                    """{
                        "message": "Persons list is empty"
                    }"""
                ))
    }

    def "post request by /persons should return person without passport data in success case"() {

        given:
            def url = getMockUrl()
            def body = "{\"url\": \"" + url + "\"}"

        when:
            def response = mockMvc.perform(MockMvcRequestBuilders.post("/persons")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))

        then:
            response.andExpect(status().isOk())
                .andExpect(content().json(
                        """{
                            "id":1,
                            "fullName":"Ivan Ivanov",
                            "phone":"80335873405",
                            "email":"ivan@gmail.com",
                            "passportData":null
                        }"""))

    }

    def "get request by /persons/{id} should return person in success case"() {
        given:
           insertTestData()

        when:
            def response = mockMvc.perform(MockMvcRequestBuilders.get("/persons/${id}")
                                  .contentType(MediaType.APPLICATION_JSON))

        then:
            response.andExpect(status().isOk())
                    .andExpect(content().json("""
                        {
                            "id":${id},
                            "fullName":"${fullName}",
                            "phone":"${phone}",
                            "email":"${email}",
                            "passportData":{
                                "id": ${id},
                                "passportNumber": "${passportNumber}",
                                "identificationNumber": "${identificationNumber}"
                            }
                        }"""
                    ))

        where:
            id | fullName       | phone         | email             | passportNumber | identificationNumber
            1  | "Ivan Ivanov"  | "80335873405" | "ivan@gmail.com"  | "HB3452111"    | "1234564A001PB5"
            2  | "Peter Petrov" | "80335873111" | "peter@gmail.com" | "HB3452758"    | "495564A001PB5"
    }

    def "put request by /persons/{id} should return message in success case"() {
        given:
            insertTestData()

        when:
            def response = mockMvc.perform(MockMvcRequestBuilders.put("/persons/{id}", 1)
                .content("""
                    {
                        "id":1,
                        "fullName":"Ivan Ivanov",
                        "phone":"80335873405",
                        "email":"ivan@gmail.com",
                        "passportData":{
                            "id": 1,
                            "passportNumber": "HB999999",
                            "identificationNumber": "11111A001PB5"
                        }
                    }"""
                )
                .contentType(MediaType.APPLICATION_JSON))

        then:
            response.andExpect(status().isOk())
                    .andExpect(content().json("""
                        {
                            "message": "Person updated successfully"
                        }"""
                    ))
    }

    def "delete request by /persons/{id} should return message in success case"() {
        given:
            insertTestData()

        when:
            def response = mockMvc.perform(MockMvcRequestBuilders.delete("/persons/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))

        then:
            response.andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "message": "Person deleted successfully"
                        }"""
                ))
    }
}
