// https://docs.cypress.io/api/table-of-contents

describe('Hello, Viking!', () => {
  it('Welcome Viking!', () => {
    cy.visit('/')
    cy.contains('h1', 'Welcome Viking!')
  })
})
