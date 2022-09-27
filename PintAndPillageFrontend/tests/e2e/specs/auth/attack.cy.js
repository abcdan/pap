// https://docs.cypress.io/api/table-of-contents

import {genRand} from "../helpers/random";



describe('Attack related tests', () => {

    let username;
    let password;
    let email;

    function setup() {
        username = "sambal" + genRand(4)
        email = "sambal" + genRand(4) + "@hotmail.nl"
        password = password = genRand(20) + "!!SSS!8888"
    }


    function login(cy, overrideUsername, overridePassword) {
        cy.visit("login")
        cy.get("#username").type(overrideUsername ?? email)

        cy.get("#password").type(overridePassword ?? password)
        cy.get("#submitButton").click()

    }

    function reset(cy) {
        cy.request('http://localhost:8080/restart')
        cy.wait(5000)

    }

    before( () => {
        // Ensure clean environment
        reset(cy)
    })

    after( () => {
        // Cleanup
        reset(cy)
    })




    it('Setting up attack tests', () => {
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

        //
        cy.visit("login")
        cy.get("#username").type(email)

        cy.get("#password").type(password)
        cy.get("#submitButton").click()

        cy.location("pathname").should("eq", "/")

    })

    it('should not be able to attack villages without enough resources', () => {
        login(cy)

        cy.get(".mapButton").eq(0).click()

        cy.get(".worldRow").eq(10).find(".villageTile").click()
        cy.contains('.compContainer', 'Derp').contains("Pillage").click()

        cy.contains("You currently don't have any units. Train some units to join the battlefields!")
    })

    it('should be able to attack with enough resources', () => {
        login(cy, 'test6@mail.com', 'Test123!')

        cy.get(".mapButton").eq(0).click()

        cy.get(".worldRow").eq(10).find(".villageTile").click()
        cy.contains('.compContainer', 'Derp').contains("Pillage").click()

        cy.get('.inputContainer').children().eq(0).type(1);

        cy.contains('.compContainer', 'Select your Long Ships').contains("Select Units").click()


        cy.get('.inputContainer').children().eq(0).type(50);

        cy.contains('.compContainer', 'Select your raiders').contains("To Battle!").click()

        cy.get(".v-toast-success").should('exist')

    })


    it('should not be able to attack self', () => {
        reset(cy)
        login(cy, 'test6@mail.com', 'Test123!')

        cy.get(".mapButton").eq(0).click()

        cy.get(".worldRow").eq(15).find(".villageTile").click()
        cy.contains('.compContainer', 'Derp').contains("Pillage").click()

        cy.get('.inputContainer').children().eq(0).type(1);

        cy.contains('.compContainer', 'Select your Long Ships').contains("Select Units").click()


        cy.get('.inputContainer').children().eq(0).type(50);

        cy.contains('.compContainer', 'Select your raiders').contains("To Battle!").click()

        cy.get(".v-toast-error").should('exist')

    })
})
