import {createLocalVue, mount, shallowMount} from "@vue/test-utils";
import Vuex from "vuex";
import ResourcesModal from "@/components/ui/modals/ResourcesModal";
import LevelUpBuilding from "@/components/ui/LevelUpBuilding";

let resourceModalWrapper;
let store;
let getters;
const localVue = createLocalVue()
localVue.component('LevelUpBuilding', LevelUpBuilding)
localVue.use(Vuex)

beforeEach(() => {
    getters = {
        building: () => () => {
                return require("../mockData/test_building_data.json")
            }
    }

    store = new Vuex.Store({
        getters
    })

    resourceModalWrapper = shallowMount(ResourcesModal, {
        store,
        localVue,
    });

});

afterAll(() =>{
    resourceModalWrapper.destroy()
})

describe('ResourcesModal', () => {
    it('should displays buildingName correctly when created', async () => {
        const expectedText = "Mine - Lv 1"
        const actualText = resourceModalWrapper.find("#buildingName").text()

        expect(actualText).toBe(expectedText)
    });

    it('should displays resources per hour correctly when resources 80 stone per hour', async () => {
        const expectedText = "80 Stone / Hour"
        const actualText = resourceModalWrapper.find("#buildingDescription").text()

        expect(actualText).toBe(expectedText)
    });
});