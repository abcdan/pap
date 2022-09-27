// https://docs.cypress.io/api/table-of-contents

import {genRand} from "../helpers/random";



describe('Alle mooie tests op een stokje', () => {

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


    it('should register an account', () => {
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

    it('first login should show tutorial', () => {
        cy.visit("login")
        cy.get("#username").type(email)

        cy.get("#password").type(password)
        cy.get("#submitButton").click()

        cy.location("pathname").should("eq", "/")


        cy.get('.tutorialBaseModal').find('h2')
    })


    it('second login should not show tutorial', () => {
        cy.visit("login")
        cy.get("#username").type(email)

        cy.get("#password").type(password)
        cy.get("#submitButton").click()

        cy.location("pathname").should("eq", "/")


        cy.contains(".tutorialBaseModal").should('not.exist');

    })



    it('should build a house', () => {
        cy.visit("login")
        // NATTE LOGIN CODE
        cy.get("#username").type(email)
        cy.get("#password").type(password)
        cy.get("#submitButton").click()
        // NATTE LOGIN CODE
        cy.wait(500)
        cy.get(".clickableTile").eq(15).click({ force: true})
        cy.contains('.buildingInformationContainer', 'House').parent().contains("Build").click()
        cy.wait(1000);
        cy.contains("Construction Time").should('not.exist');
    })


    it('below level 10 building should be normal image', () => {
        cy.visit("login")
        cy.get("#username").type(email)
        cy.get("#password").type(password)
        cy.get("#submitButton").click()
        cy.wait(500)

        const houseLevelBelowLevel10 = '/img/house.be5e1bc6.png'
        cy.get('.tileImgHouse').eq(0).should('have.attr', 'src', houseLevelBelowLevel10)

    })

    it('level 10 or above building should create visual change', () => {
        cy.visit("login")
        cy.get("#username").type(email)
        cy.get("#password").type(password)
        cy.get("#submitButton").click()
        cy.wait(500)

        for (let i = 0; i < 10; i++) {
            cy.get(".clickableTile").eq(15).click({ force: true})
            cy.contains("Level Up").click()
            cy.wait(500);
            cy.reload()
        }

        const houseLevel10Asset = '/img/house_10.05022f4d.png'
        cy.get('.tileImgHouse').eq(0).should('have.attr', 'src', houseLevel10Asset)
    })
})
