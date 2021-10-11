package com.prokopovich.persondata.webapp

import com.prokopovich.persondata.domain.dao.repository.PersonRepository
import com.prokopovich.persondata.domain.model.PassportData
import com.prokopovich.persondata.domain.model.Person
import com.prokopovich.persondata.domain.service.PersonService
import org.junit.jupiter.api.AfterEach
import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.containers.MockServerContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import spock.lang.Shared
import spock.lang.Specification


import static org.mockserver.integration.ClientAndServer.startClientAndServer
import static org.mockserver.model.HttpResponse.response

@SpringBootTest
@Testcontainers
@WebAppConfiguration
@Transactional
@ContextConfiguration(classes = [IntegrationTestSpecification.class, WebApp.class])
class IntegrationTestSpecification extends Specification {

    @Shared
    public static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
        .withDatabaseName("persons_db")
        .withUsername("test")
        .withPassword("1234")

    public static final MockServerContainer mockServerContainer = new MockServerContainer("5.11.2")

    @Autowired
    private PersonService personService

    @Autowired
    protected PersonRepository personRepository

    @Autowired
    private WebApplicationContext webApplicationContext

    protected MockMvc mockMvc

    void setup() {

        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build()

        postgreSQLContainer.start()

        mockServerContainer.start()

    }

    private ClientAndServer mockServer

    @BeforeClass
    void startServer() {
        mockServer = startClientAndServer(1080)
    }

    @AfterClass
    void stopServer() {
        mockServer.stop()
    }

    void insertTestData() {

        def firstPerson = Person.builder()
            .id(1)
            .fullName("Ivan Ivanov")
            .phone("80335873405")
            .email("ivan@gmail.com")
            .passportData(
                PassportData.builder()
                    .id(1)
                    .passportNumber("HB3452111")
                    .identificationNumber("1234564A001PB5")
                    .build()
            )
            .build()
        firstPerson.passportData.personInfo = firstPerson
        personRepository.save(firstPerson)

        def secondPerson = Person.builder()
            .id(2)
            .fullName("Peter Petrov")
            .phone("80335873111")
            .email("peter@gmail.com")
            .passportData(
                PassportData.builder()
                    .id(2)
                    .passportNumber("HB3452758")
                    .identificationNumber("495564A001PB5")
                    .build()
            )
            .build()
        secondPerson.passportData.personInfo = secondPerson
        personRepository.save(secondPerson)
    }

    @AfterEach
    void cleanupDb() {
        personRepository.deleteAll()
    }

    String getMockUrl() {

        new MockServerClient(mockServerContainer.getHost(), mockServerContainer.getServerPort())
            .when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/person-data"))
            .respond(response()
                .withStatusCode(200)
                .withHeaders(
                    new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                )
                .withBody("""{
                        "id":1,
                        "fullName":"Ivan Ivanov",
                        "phone":"80335873405",
                        "email":"ivan@gmail.com",
                        "passportData":{
                            "id":1,
                            "passportNumber":"HB3452111",
                            "identificationNumber":"7548564A001PB5"
                        }
                    }"""
                )
            )
        return "http://" + mockServerContainer.getHost() + ":" + mockServerContainer.getServerPort() + "/person-data"
    }
}
