// https://docs.cypress.io/api/table-of-contents

import {genRand} from "../helpers/random";



describe('Authentication tests', () => {

    let username;
    let password;
    let email;

    function setup() {
        username = "sambal" + genRand(4)
        email = "sambal" + genRand(4) + "@hotmail.nl"
        password = password = genRand(20) + "!!SSS!8888"
    }


    before( () => {
        // Ensure clean environment
        cy.request('http://localhost:8080/restart')
        cy.wait(5000)
    })

    after( () => {
        // Cleanup
        cy.request('http://localhost:8080/restart')
        cy.wait(5000)
    })


    it('should throw error when password too short', () => {
        setup();
        cy.visit("/")
        cy.get("#login-button").click()
        cy.get("#register-button").click({force: true})
        cy.get("#username").type(username)
        cy.get("#email").type(email)

        cy.get("#password").type("1234")
        cy.get("#repeatPassword").type("1234")
        cy.get("#submitButton").click()

        cy.get(".v-toast-error").should('exist')
    })

    it('should throw error when passwords do not match', () => {
        setup();
        cy.visit("/")
        cy.get("#login-button").click()
        cy.get("#register-button").click()
        cy.get("#username").type(username)
        cy.get("#email").type(email)

        cy.get("#password").type("12342222@@@")
        cy.get("#repeatPassword").type("1234")
        cy.get("#submitButton").click()

        cy.get(".v-toast-error").should('exist')
    })

    it('should throw error when not strong enough password', () => {
        setup();
        cy.visit("/")
        cy.get("#login-button").click()
        cy.get("#register-button").click()
        cy.get("#username").type(username)
        cy.get("#email").type(email)

        cy.get("#password").type("qwert123!")
        cy.get("#repeatPassword").type("qwert123!")
        cy.get("#submitButton").click()

        cy.get(".v-toast-error").should('exist')
    })

    it('should register an account with proper password and unique email', () => {
        setup();
        cy.visit("/")
        cy.get("#login-button").click()
        cy.get("#register-button").click()
        cy.get("#username").type(username)
        cy.get("#email").type(email)

        cy.get("#password").type(password)
        cy.get("#repeatPassword").type(password)
        cy.get("#submitButton").click()

        cy.location("pathname").should("eq", "/login")

    })

    it('should show tutorial on first login', () => {
        cy.visit("/login")
        cy.get("#username").type(email)

        cy.get("#password").type(password)
        cy.get("#submitButton").click()

        cy.location("pathname").should("eq", "/")


        cy.get('.tutorialBaseModal').find('h2')
    })

    it('should not show tutorial on second login', () => {
        cy.visit("/login")
        cy.get("#username").type(email)

        cy.get("#password").type(password)
        cy.get("#submitButton").click()

        cy.location("pathname").should("eq", "/")


        cy.get('.tutorialBaseModal').should('not.exist')

        cy.wait(2000)
    })

})
