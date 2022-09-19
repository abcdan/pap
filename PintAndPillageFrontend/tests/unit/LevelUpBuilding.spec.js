import {createLocalVue, shallowMount} from "@vue/test-utils";
import Vuex from "vuex";
import LevelUpBuilding from "../../src/components/ui/LevelUpBuilding";
import TimeFrame from "@/components/ui/TimeFrame";
import ResourceItem from "@/components/ui/ResourceItem";
import PopulationFrame from "@/components/ui/PopulationFrame";

let levelUpBuildingWrapper;
let store;
let getters;
const localVue = createLocalVue()
localVue.component('time-frame', TimeFrame)
localVue.component('population-frame', PopulationFrame)
localVue.component('resource-item', ResourceItem)
localVue.use(Vuex)


const state = {
    village: {
        data: require("./mockData/test_village_data.json")
    }
}

afterAll(() =>{
    levelUpBuildingWrapper.destroy()
})

describe('LevelUpBuilding', () => {
    it('is under construction', function () {
        getters = {
            building: () => () => {
                return require("./mockData/test_building_data.json")
            }
        }

        store = new Vuex.Store({
            getters,
            state
        })

        levelUpBuildingWrapper = shallowMount(LevelUpBuilding, {
            store,
            localVue,
        });

        const message = levelUpBuildingWrapper.find('#building-is-under-construction').text()
        expect(message).toBe("Building is under construction")
    });

    it('is not under construction',  () => {

        getters = {
            building: () => () => {
                return require("./mockData/test_building_data_not_under_construction.json")
            }
        }

        store = new Vuex.Store({
            getters,
            state
        })

        levelUpBuildingWrapper = shallowMount(LevelUpBuilding, {
            store,
            localVue,
        });


        const message = levelUpBuildingWrapper.find('#building-is-under-construction')

        console.log(message)
        // expect(message).toBe("Building is under construction")
    });

});