import {createLocalVue, mount} from "@vue/test-utils";
import Vuex from "vuex";
import TutorialModal from "@/components/ui/modals/TutorialModal";
import Village from "@/views/Village";
import VillageGrid from "@/components/VillageGrid";
import CombatModal from "@/components/ui/modals/CombatModal";
import SettingsModal from "@/components/ui/modals/SettingsModal";

let villageWrapper;
let store;
let getters;
const localVue = createLocalVue()
localVue.component('tutorial-modal', TutorialModal)
localVue.component('villagegrid-component', VillageGrid)
localVue.component('settings-modal', SettingsModal)
localVue.component('combat-component', CombatModal)
localVue.use(Vuex)

// beforeEach(() => {
//
// });

function setup(firstTimeLoggedIn) {
    getters = {
        building: () => () => {
            return require("../mockData/test_building_data.json")
        },
        firstLogin: () => {
            return firstTimeLoggedIn
        }
    }

    const state = {
        village: {
            data: require("../mockData/test_village_data.json")
        },
    }

    store = new Vuex.Store({
        state,
        getters
    })

    villageWrapper = mount(Village, {
        store,
        localVue,
    });
}
afterAll(() =>{
    villageWrapper.destroy()
})

describe('Village', () => {
    it('should show tutorial on first login', async () => {
        const FIRST_LOGIN = true
        setup(FIRST_LOGIN)

        const ACTUAL_TEXT = villageWrapper.find("#elwrick-pintbreaker").text()
        const EXPECTED_TEXT = "Elwrick Pintbreaker"

        expect(ACTUAL_TEXT).toBe(EXPECTED_TEXT)
    })

    it('should now show tutorial on second login', async () => {
        const FIRST_LOGIN = false
        setup(FIRST_LOGIN)

        expect(villageWrapper.find("#elwrick-pintbreaker").exists()).toBeFalsy()

        //
        // const ACTUAL_TEXT = villageWrapper.find("#elwrick-pintbreaker").text()
        // const EXPECTED_TEXT = "Elwrick Pintbreaker"


    })


});